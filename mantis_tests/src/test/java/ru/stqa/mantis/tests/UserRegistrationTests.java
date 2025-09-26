package ru.stqa.mantis.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.stqa.mantis.common.CommonFunction;

import java.time.Duration;

public class UserRegistrationTests extends TestBase {

    @Test
    public void canRegisterUser() {
        var username = CommonFunction.randomString(7);
        var tokenRegistration = app.httpSession().getSignupToken();
        app.jamesCli().addUser(String.format("%s@localhost", username),"password");// создать пользователя на почтовом сервере (JamesHelper)
        app.httpSession().registration(String.format("%s@localhost", username), username, tokenRegistration);// заполнить форму создания и отправить ее (браузер)
        var registerUrl = app.mail().exctractUrl(username, "password");// Извлекаем ссылку из письма
        app.registration().completedRegistration(username, "password", "password", registerUrl); //Проходим по ссылке и завершаем регистрацию (браузер)
        app.httpSession().login(username, "password"); // проверяем, что пользователь может залогиниться (HttpSessionHelper)
        Assertions.assertTrue(app.httpSession().isLoggedIn());
    }

    @Test
    public void canRegister() {
        var username = CommonFunction.randomString(7);
        var tokenRegistration = app.httpSession().getSignupToken();
        app.httpSession().registration(String.format("%s@localhost", username), username, tokenRegistration);
    }

    @Test
    public void canRegisterUserByApi() {
        var username = CommonFunction.randomString(7);
        var tokenRegistration = app.httpSession().getSignupToken();
        app.jamesApi().addUser(String.format("%s@localhost", username),"password");// создать пользователя на почтовом сервере (JamesHelper)
        app.httpSession().registration(String.format("%s@localhost", username), username, tokenRegistration);// заполнить форму создания и отправить ее (браузер)
        var registerUrl = app.mail().exctractUrl(username, "password");// Извлекаем ссылку из письма
        app.registration().completedRegistration(username, "password", "password", registerUrl); //Проходим по ссылке и завершаем регистрацию (браузер)
        app.httpSession().login(username, "password"); // проверяем, что пользователь может залогиниться (HttpSessionHelper)
        Assertions.assertTrue(app.httpSession().isLoggedIn());
    }

    @Test
    public void canRegisterUserByHttpSession() {
        var username = CommonFunction.randomString(7);
        var tokenRegistration = app.httpSession().getSignupToken();
        app.jamesCli().addUser(String.format("%s@localhost", username),"password");// создать пользователя на почтовом сервере (JamesHelper)
        app.httpSession().registration(String.format("%s@localhost", username), username, tokenRegistration);// заполнить форму создания и отправить ее (браузер)
        var registerUrl = app.mail().exctractUrl(username, "password");// Извлекаем ссылку из письма
        var accountUpdateToken = app.httpSession().accountUpdateToken(registerUrl);
        app.httpSession().accountUpdate(username, "password", "password", accountUpdateToken.get(0), accountUpdateToken.get(1), accountUpdateToken.get(2));
        app.httpSession().login(username, "password"); // проверяем, что пользователь может залогиниться (HttpSessionHelper)
        Assertions.assertTrue(app.httpSession().isLoggedIn());
    }
}
