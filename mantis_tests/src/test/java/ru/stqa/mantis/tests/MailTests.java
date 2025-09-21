package ru.stqa.mantis.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MailTests extends TestBase {

    @Test
    public void canRecieve() {
        var message = app.mail().recieve("user1@localhost", "password");
        Assertions.assertEquals(1, message.size());
        System.out.println(message);
    }
}
