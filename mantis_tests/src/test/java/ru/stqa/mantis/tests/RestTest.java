package ru.stqa.mantis.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.stqa.mantis.common.CommonFunction;

public class RestTest extends TestBase {

    @Test
    public void canRegisterUserByRest() {
        var username = CommonFunction.randomString(6);
        app.jamesApi().addUser(String.format("%s@localhost", username),"password");
        app.rest().createUser(username, username, "password", String.format("%s@localhost", username)); //Зарегистрироваться и заполнить форму создания и отправить ее
        var registerUrl = app.mail().exctractUrl(username, "password");// Извлекаем ссылку из письма
        app.registration().completedRegistration(username, "password", "password", registerUrl); //Проходим по ссылке и завершаем регистрацию (браузер)
        app.httpSession().login(username, "password"); // проверяем, что пользователь может залогиниться (HttpSessionHelper)
        Assertions.assertTrue(app.httpSession().isLoggedIn());
    }

    @Test
    public void canCreateMailBox() {
        var username = CommonFunction.randomString(6);
        var nameBox = CommonFunction.randomString(6);
        app.jamesCli().addUser(String.format("%s@localhost", username),"password");
        app.jamesApi().createMailbox(String.format("%s@localhost", username), "password", nameBox);
        app.jamesApi().listingMailBox(String.format("%s@localhost", username), "password");
    }
}
