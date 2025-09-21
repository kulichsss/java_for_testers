package ru.stqa.mantis.manger;

import jakarta.mail.Folder;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Store;
import ru.stqa.mantis.model.MailData;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class MailHelper extends HelperBase {
    public MailHelper(ApplicationManager manager) {
        super(manager);
    }

    public List<MailData> recieve(String username, String password) {

        try {
            var session = Session.getInstance(new Properties());
            Store store = session.getStore("pop3");
            store.connect("localhost", username, password);
            var inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);
            var messages = inbox.getMessages();
            var result = Arrays.stream(messages)
                    .map(m -> {
                        try {
                            return new MailData()
                                    .withFrom(m.getFrom()[0].toString())
                                    .withContent((String) m.getContent());
                        } catch (MessagingException | IOException e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .toList();
            inbox.close();
            store.close();
            return result;
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }
}
