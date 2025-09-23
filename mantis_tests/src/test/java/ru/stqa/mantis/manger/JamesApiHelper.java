package ru.stqa.mantis.manger;


import okhttp3.*;

import java.io.IOException;
import java.net.CookieManager;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class JamesApiHelper extends HelperBase{
    public static final MediaType JSON = MediaType.get("application/json");
    protected OkHttpClient httpClient;

    public JamesApiHelper(ApplicationManager manager) {
        super(manager);
        httpClient = new OkHttpClient.Builder().cookieJar(new JavaNetCookieJar(new CookieManager())).build();
    }


    public void addUser(String username, String password) {
        RequestBody body = RequestBody.create(String.format("{\"password\":\"%s\"}", password), JSON);
        Request request = new Request.Builder()
                .url(String.format("%s/users/%s", manager.property("web.jamesBaseApi"), username))
                .put(body)
                .build();
        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new RuntimeException("Unexpected code " + response);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void createMailbox(String username, String password, String nameBox) {
        RequestBody body = RequestBody.create(String.format("{\"password\":\"%s\"}", password), JSON);
        Request request = new Request.Builder()
                .url(String.format("%s/users/%s/mailboxes/%s", manager.property("web.jamesBaseApi"), username, nameBox))
                .put(body)
                .build();
        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new RuntimeException("Unexpected code " + response);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String listingMailBox(String username, String password) {
        Request request = new Request.Builder()
                .url(String.format("%s/users/%s/mailboxes", manager.property("web.jamesBaseApi"), username))
                .build();
        try (Response response = httpClient.newCall(request).execute()) {
            var messages = response.body().string();
            System.out.println(messages);
            return messages;

//            if (!response.isSuccessful()) throw new RuntimeException("Unexpected code " + response);
//            var body = response.body().string();
//            var pattern = Pattern.compile("http://\\S*");
//            var matcher = pattern.matcher(body);
//            if (matcher.find()) {
//                var url = body.substring(matcher.start(), matcher.end());
//                System.out.println(url);
//                return url;
//            }
//            throw new RuntimeException("No find URL in mail");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
