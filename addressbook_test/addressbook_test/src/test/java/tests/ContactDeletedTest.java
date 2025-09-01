package tests;

import model.ContactData;
import model.GroupData;
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
            app.contacts().createContact(new ContactData()
                    .withLastname("Usupov1")
                    .withName("Danila1")
                    .withMiddlename("Andreevich")
                    .withPhoto(randomFile("src/test/resources/images")));
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

    @Test
    public void canDeletedContactByHbm() {
        if (app.hbm().getCountContacts() == 0) {
            app.hbm().createContact(new ContactData()
                    .withLastname("Usupov1")
                    .withName("Danila1")
                    .withMiddlename("Andreevich")
                    .withPhoto(randomFile("src/test/resources/images")));
        }
        var contactOld = app.hbm().getContactsList();
        var rnd = new Random();
        var index = rnd.nextInt(contactOld.size());
        app.contacts().deletedContact(contactOld.get(index));
        var contactNew= app.hbm().getContactsList();
        var expectedList = new ArrayList<>(contactOld);
        expectedList.remove(index);
        Assertions.assertEquals(expectedList, contactNew);
    }

    @Test
    public void canDeletedContactFromGroupByHbm() {
        // Создаём контакт, если нет
        if (app.hbm().getCountContacts() == 0) {
            app.hbm().createContact(new ContactData()
                    .withLastname("Usupov1")
                    .withName("Danila1")
                    .withMiddlename("Andreevich")
                    .withPhoto(randomFile("src/test/resources/images")));
        }
        // Создаём группу, если нет
        if (app.hbm().getCountGroups() == 0) {
            app.hbm().createGroup(new GroupData("", "Name1", "Logo header", "Comment footer"));
        }
        var group = app.hbm().getGroupsList().get(0);
        // Добавляем контакт в группу, если группа пустая
        if (app.hbm().getCountContactInGroup(group) == 0) {
            app.contacts().createContactInGroupByAddTo(group);
        }
        var oldRelated = app.hbm().getContactsListInGroup(group);
        var rnd = new Random();
        var index = rnd.nextInt(oldRelated.size());
        app.contacts().deleteContactFromGroup(oldRelated.get(index), group);
        var newRelated= app.hbm().getContactsListInGroup(group);
        var expectedList = new ArrayList<>(oldRelated);
        expectedList.remove(index);
        Assertions.assertEquals(expectedList, newRelated);
    }
}
