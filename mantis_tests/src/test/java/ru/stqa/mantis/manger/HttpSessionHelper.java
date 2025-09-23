package ru.stqa.mantis.manger;

import okhttp3.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;
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

    public void accountUpdate(String realname, String password, String password_confirm, String updateToken) {
        RequestBody formBody = new FormBody.Builder()
                .add("account_update_token", updateToken)
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

    public void confirmRegistration(String realname, String password, String password_confirm, String url) {
        Request request = new Request.Builder()
                .url(url)
                .build();
        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new RuntimeException("Failed to verify account: " + response.code() + " " + response.message());
            }
            System.out.println("📍 Final URL after verify.php: " + response.request().url());

        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}