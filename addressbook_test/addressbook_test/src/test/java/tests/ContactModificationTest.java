package tests;

import model.ContactData;
import model.GroupData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

public class ContactModificationTest extends TestBase {
    @Test
    public void canModifyContact() {
        if (app.contacts().countContacts() == 0) {
            app.contacts().createContact(new ContactData().withRequiredFields("Danila1", "Usupov1", "Andreevich", "г. Санкт-Петербург, ул. Колотушкина д .7а", "dan@yandex.ru", "89994442525"));
        }
        var contactOld = app.contacts().getList();
        var rnd = new Random();
        var index = rnd.nextInt(contactOld.size());
        ContactData testData = new ContactData().withName("modify name");
        app.contacts().modifyContact(contactOld.get(index), testData);
        var newGroup = app.contacts().getList();
        var expectedList = new ArrayList<>(contactOld);
        expectedList.set(index, testData.withId(contactOld.get(index).id()));
        Comparator<ContactData> compareById = (o1, o2) -> {
            return Integer.compare(Integer.parseInt(o1.id()), Integer.parseInt(o2.id()));
        };
        newGroup.sort(compareById);
        expectedList.sort(compareById);
        Assertions.assertEquals(expectedList, newGroup);
    }
}

