package ru.stqa.mantis.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.regex.Pattern;

public class MailTests extends TestBase {

    @Test
    public void canDrainInbox() {
        app.mail().drain("user1@localhost", "password");
    }
    @Test
    public void canRecieve() {
        var messages = app.mail().recieve("user5@localhost", "password", Duration.ofSeconds(10));
        Assertions.assertEquals(1, messages.size());
        System.out.println(messages);
    }

    @Test
    public void canExtractUrl() {
        app.mail().exctractUrl("user5@localhost", "password");
    }
}
