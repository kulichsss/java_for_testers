package ru.stqa.mantis.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class LoginTests extends TestBase {
    @Test
    public void canLogin() {
        app.session().login("administrator", "root");
        Assertions.assertTrue(app.session().isLoggedIn());
    }

    @Test
    public void canLoginByHttp() {
        app.httpSession().login("administrator", "root");
        Assertions.assertTrue(app.httpSession().isLoggedIn());
    }
}
