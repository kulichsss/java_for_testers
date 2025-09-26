package ru.stqa.mantis.manger;

import okhttp3.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.ArrayList;
import java.util.List;

public class HttpSessionHelper extends HelperBase {
    protected OkHttpClient httpClient;
    private final CookieManager cookieManager;
    private String capturedSessionId;

    public HttpSessionHelper(ApplicationManager manager) {
        super(manager);
        this.cookieManager = new CookieManager();
        this.cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);

        // Создаём перехватчик, который сохраняет куку
        Interceptor cookieCaptureInterceptor = chain -> {
            Response response = chain.proceed(chain.request());
            List<String> cookies = response.headers("Set-Cookie");
            for (String cookie : cookies) {
                if (cookie.startsWith("PHPSESSID=")) {
                    // Сохраняем куку в поле класса
                    this.capturedSessionId = cookie.split(";")[0];
                    System.out.println("🍪 Interceptor captured and SAVED: " + this.capturedSessionId);
                }
            }
            return response;
        };
        httpClient = new OkHttpClient.Builder().cookieJar(new JavaNetCookieJar(new CookieManager()))
                .addInterceptor(cookieCaptureInterceptor)
                .build();
    }


    public void login(String username, String password) {
        RequestBody formBody = new FormBody.Builder()
                .add("username", username)
                .add("password", password)
                .build();
        Request request = new Request.Builder()
                .url(String.format("%s/login.php", manager.property("web.baseUrl")))
                .post(formBody)
                .build();

        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new RuntimeException("Unexpected code " + response);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isLoggedIn() {
        Request request = new Request.Builder()
                .url(manager.property("web.baseUrl"))
                .build();

        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new RuntimeException("Unexpected code " + response);
            var body = response.body().string();
            return body.contains("<span class=\"user-info\">");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void registration(String email, String username, String tokenRegistration) {
        RequestBody formBody = new FormBody.Builder()
                .add("signup_token", tokenRegistration)
                .add("username", username)
                .add("email", email)
                .build();
        Request request = new Request.Builder()
                .url(String.format("%s/signup.php", manager.property("web.baseUrl")))
                .post(formBody)
                .build();

        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new RuntimeException("Unexpected code " + response);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getSignupToken() {
        Request request = new Request.Builder()
                .url(manager.property("web.baseUrl") + "/signup_page.php")
                .build();

        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new RuntimeException("Failed to load signup page " + response);
            String html = response.body().string();
            Document doc = Jsoup.parse(html);  // ← Парсим HTML

            // Находим скрытое поле с именем signup_token
            Element tokenElement = doc.selectFirst("input[name=signup_token]");

            if (tokenElement == null) {
                throw new RuntimeException("signup_token field not found in HTML");
            }

            String signupToken = tokenElement.attr("value");  // ← Получаем значение атрибута "value"

            if (signupToken == null || signupToken.trim().isEmpty()) {
                throw new RuntimeException("signup_token is empty or null");
            }

            return signupToken;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void accountUpdate(String realname, String password, String password_confirm, String id, String hash, String accountUpdateToken) {
        RequestBody formBody = new FormBody.Builder()
                .add("verify_user_id", id)
                .add("confirm_hash", hash)
                .add("account_update_token", accountUpdateToken)
                .add("realname", realname)
                .add("password", password)
                .add("password_confirm", password_confirm)
                .build();
        Request request = new Request.Builder()
                .url(String.format("%s/account_update.php", manager.property("web.baseUrl")))
                .post(formBody)
                .build();


        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                String body = response.body() != null ? response.body().string() : "No body";
                throw new RuntimeException("Account update failed: " + response + "\nResponse body: " + body);
            }
            System.out.println("Account updated successfully!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void confirmRegistration(String url) {
        Request request = new Request.Builder()
                .url(url)
                .build();
        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new RuntimeException("Failed to verify account: " + response.code() + " " + response.message());
            }
            String body = response.body().string();
            System.out.println("Account updated successfully!");
            System.out.println("📍 Final URL after verify.php: " + response.request().url());


        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> accountUpdateToken(String url) {
        List<String> tokenList = new ArrayList<>();
        Request request = new Request.Builder()
                .url(url)
                .build();
        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new RuntimeException("Failed to verify account: " + response.code() + " " + response.message());
            }
            String body = response.body().string();
            String headers = response.headers().toString();
            System.out.println("Account updated successfully!" + body + "headers " + headers);
            System.out.println("📍 Final URL after verify.php: " + response.request().url());
            Document doc = Jsoup.parse(body);  // ← Парсим HTML

            // Находим скрытое поле с именем account_update_token
            Element updateToken = doc.selectFirst("input[name=account_update_token]");
            Element userId = doc.selectFirst("input[name=verify_user_id]");
            Element confirmHash = doc.selectFirst("input[name=confirm_hash]");

            if (updateToken == null || userId == null || confirmHash == null) {
                throw new RuntimeException("token field not found in HTML");
            }

            String accountUpdateToken = updateToken.attr("value");  // ← Получаем значение атрибута "value"
            String id = userId.attr("value");
            String hash = confirmHash.attr("value");

            if (accountUpdateToken == null || accountUpdateToken.trim().isEmpty()) {
                throw new RuntimeException("account_update_token is empty or null");
            }
            tokenList.add(id);
            tokenList.add(hash);
            tokenList.add(accountUpdateToken);

            return tokenList;


        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}