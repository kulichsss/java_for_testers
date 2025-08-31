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
            app.contacts().createContact(new ContactData()
                    .withLastname("Usupov1")
                    .withName("Danila1")
                    .withMiddlename("")
                    .withPhoto(""));
        }
        var contactOld = app.contacts().getList();
        var rnd = new Random();
        var index = rnd.nextInt(contactOld.size());
        ContactData testData = new ContactData()
                .withId(contactOld.get(index).id())
                .withName("modify name")
                .withMiddlename(contactOld.get(index).middlename())
                .withLastname(contactOld.get(index).lastname())
                .withPhoto(contactOld.get(index).photo());
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

    @Test
    public void canModifyContactByHbm() {
        if (app.hbm().getCountGroups() == 0) {
            app.hbm().createContact(new ContactData()
                    .withLastname("Usupov1")
                    .withName("Danila1")
                    .withMiddlename("Andreevich")
                    .withPhoto(randomFile("src/test/resources/images")));
        }
        var contactOld = app.hbm().getContactsList();
        var rnd = new Random();
        var index = rnd.nextInt(contactOld.size());
        ContactData testData = new ContactData()
                .withId(contactOld.get(index).id())
                .withName("modify name")
                .withMiddlename(contactOld.get(index).middlename())
                .withLastname(contactOld.get(index).lastname())
                .withPhoto(contactOld.get(index).photo());
        app.contacts().modifyContact(contactOld.get(index), testData);
        var newGroup = app.hbm().getContactsList();
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

