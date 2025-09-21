package ru.stqa.mantis.tests;

import org.junit.jupiter.api.Test;
import ru.stqa.mantis.common.CommonFunction;

public class JamesTests extends TestBase {

    @Test
    public void canCreateUser() {
        app.jamesCli().addUser(String.format("%s@localhost", CommonFunction.randomString(7)),"password");
    }
}
