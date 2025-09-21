package tests;


import model.ContactData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
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

    @Test
    public void TestPhoneAndAddressAndEmail() {
        if (app.hbm().getCountContacts() == 0) {
            app.hbm().createContact(
                    new ContactData()
                            .withId("")
                            .withName("Danila3")
                            .withMiddlename("Andreevich")
                            .withLastname("Usupov3")
                            .withHome("12444")
                            .withWork("4212")
                            .withAddress("Улица Колотушкина д.10. кв 22\nСПБ")
                            .withEmail("dan@yandex.ru")
                            .withEmail2("fasdf@gmail.com")
                            .withEmail3("qwr21@gmail.com")
            );
        }
        var contact = app.hbm().getContactsList().get(0);
        var phone = app.contacts().getPhone(contact);
        var address = app.contacts().getAddress(contact);
        var email = app.contacts().getEmail(contact);
        var expectedPhone = Stream.of(contact.home(), contact.mobile(), contact.work(), contact.fax())
                .filter(s -> s != null && !"".equals(s))
                .collect(Collectors.joining("\n"));
        Assertions.assertEquals(expectedPhone, phone);

        var expectedAddress = Stream.of(contact.address())
                .filter(s -> s != null && !"".equals(s))
                .flatMap(add -> Arrays.stream(add.split("\r\n")))
                .collect(Collectors.joining("\n"));
        Assertions.assertEquals(expectedAddress, address);

        var expectedEmail = Stream.of(contact.email(), contact.email2(), contact.email3())
                .filter(s -> s != null && !"".equals(s))
                .collect(Collectors.joining("\n"));
        Assertions.assertEquals(expectedEmail, email);

    }
}
