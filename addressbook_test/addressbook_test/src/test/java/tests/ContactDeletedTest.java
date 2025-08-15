package tests;

import model.ContactData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ContactDeletedTest extends TestBase {


    @Test
    public void canDeletedContact() {
        if (app.contacts().countContacts() == 0) {
            app.contacts().createContact(new ContactData().withRequiredFields("Danila1", "Usupov1", "Andreevich", "г. Санкт-Петербург, ул. Колотушкина д .7а", "dan@yandex.ru", "89994442525"));
        }
        var contactStart = app.contacts().countContacts();
        app.contacts().deletedContact();
        var contactFinish = app.contacts().countContacts();
        Assertions.assertEquals(contactStart - 1, contactFinish);
    }

    @Test
    public void canDeletedAllContacts() {
        if (app.contacts().countContacts() == 0) {
            app.contacts().createContact(new ContactData().withRequiredFields("Danila1", "Usupov1", "Andreevich", "г. Санкт-Петербург, ул. Колотушкина д .7а", "dan@yandex.ru", "89994442525"));
        }
        app.contacts().deletedAllContacts();
        var contactFinish = app.contacts().countContacts();
        Assertions.assertEquals(0, contactFinish);
    }
}
