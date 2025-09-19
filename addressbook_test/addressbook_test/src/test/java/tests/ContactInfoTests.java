package tests;


import model.ContactData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ContactInfoTests extends TestBase {

    @Test
    public void TestPhone() {
        if (app.hbm().getCountContacts() == 0) {
            app.hbm().createContact(
                    new ContactData()
                            .withId("")
                            .withName("Danila3")
                            .withMiddlename("Andreevich")
                            .withLastname("Usupov3")
                            .withHome("12444")
                            .withWork("4212")
            );
        }
        var contact = app.hbm().getContactsList().get(0);
        System.out.println(contact);
        var phone = app.contacts().getPhones(contact);
        var expected = Stream.of(contact.home(), contact.mobile(), contact.work(), contact.fax())
                .filter(s -> s != null && !"".equals(s))
                .collect(Collectors.joining("\n"));
        Assertions.assertEquals(expected, phone);

    }
}
