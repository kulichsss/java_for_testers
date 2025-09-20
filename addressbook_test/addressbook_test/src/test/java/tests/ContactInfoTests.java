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
        var phone = app.contacts().getPhone(contact);
        var expected = Stream.of(contact.home(), contact.mobile(), contact.work(), contact.fax())
                .filter(s -> s != null && !"".equals(s))
                .collect(Collectors.joining("\n"));
        Assertions.assertEquals(expected, phone);

    }

    @Test
    public void TestPhones() {
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
        var contacts = app.hbm().getContactsList();
        var expected = contacts.stream().collect(Collectors.toMap(ContactData::id, contact ->
                Stream.of(contact.home(), contact.mobile(), contact.work(), contact.fax())
                        .filter(s -> s != null && !"".equals(s))
                        .collect(Collectors.joining("\n"))
        ));
        var phones = app.contacts().getPhones();
        Assertions.assertEquals(expected, phones);
    }
}
