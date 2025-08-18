package tests;

import model.ContactData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Random;

public class ContactDeletedTest extends TestBase {


    @Test
    public void canDeletedContact() {
        if (app.contacts().countContacts() == 0) {
            app.contacts().createContact(new ContactData().withRequiredFields("Danila1", "Usupov1", "Andreevich"));
        }
        var contactStart = app.contacts().countContacts();
        app.contacts().deletedContact(null);
        var contactFinish = app.contacts().countContacts();
        Assertions.assertEquals(contactStart - 1, contactFinish);
    }

    @Test
    public void canDeletedAllContacts() {
        if (app.contacts().countContacts() == 0) {
            app.contacts().createContact(new ContactData().withRequiredFields("Danila1", "Usupov1", "Andreevich"));
        }
        app.contacts().deletedAllContacts();
        var contactFinish = app.contacts().countContacts();
        Assertions.assertEquals(0, contactFinish);
    }

    @Test
    public void canDeletedContactById() {
        if (app.contacts().countContacts() == 0) {
            app.contacts().createContact(new ContactData().withRequiredFields("Danila1", "Usupov1", "Andreevich"));
        }
        var contactOld = app.contacts().getList();
        var rnd = new Random();
        var index = rnd.nextInt(contactOld.size());
        app.contacts().deletedContact(contactOld.get(index));
        var contactNew= app.contacts().getList();
        var expectedList = new ArrayList<>(contactOld);
        expectedList.remove(index);
        Assertions.assertEquals(expectedList, contactNew);
    }
}
