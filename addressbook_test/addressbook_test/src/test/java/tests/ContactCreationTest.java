package tests;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import common.CommonFunction;
import model.ContactData;
import model.GroupData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.xml.sax.XMLReader;

import javax.sql.rowset.spi.XmlReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ContactCreationTest extends TestBase {

  public static List<ContactData> contactProvider() throws IOException {
    var listContacts = new ArrayList<ContactData>();
    for (var name: List.of("", CommonFunction.randomString(4))) {
        for (var lastname: List.of("", CommonFunction.randomString(5))) {
          listContacts.add(new ContactData("", name, "", lastname, ""));
            }
          }

    XmlMapper mapper = new XmlMapper();
    var reader = mapper.readValue(new File("contacts.xml"), new TypeReference<List<ContactData>>() {});
    listContacts.addAll(reader);
    return listContacts;
  }

  public static List<ContactData> negativeContact() {
    var listContacts = new ArrayList<ContactData>(List.of(
            new ContactData("", "Danila1'", "", "", "")
    ));
    return listContacts;
  }

  @Test
  public void canCreateContact() {
    app.contacts().createContact(new ContactData()
            .withLastname("Usupov1")
            .withName("Danila1")
            .withMiddlename("Andreevich")
            .withPhoto(randomFile("src/test/resources/images")));
  }

  @ParameterizedTest
  @MethodSource("contactProvider")
  public void canCreateSomeContacts(ContactData contact) {
    var contactStart = app.contacts().getList();
    app.contacts().createContact(contact);
    var contactFinish = app.contacts().getList();
    Comparator<ContactData> compareById = (o1, o2) -> {
      return Integer.compare(Integer.parseInt(o1.id()), Integer.parseInt(o2.id()));
    };
    contactFinish.sort(compareById);
    var expectedList = new ArrayList<>(contactStart);
    expectedList.add(contact.withId(contactFinish.get(contactFinish.size() - 1).id())
            .withName(contactFinish.get(contactFinish.size() - 1).firstname())
            .withLastname(contactFinish.get(contactFinish.size() - 1).lastname()));
    expectedList.sort(compareById);
    Assertions.assertEquals(expectedList, contactFinish);
  }

  @ParameterizedTest
  @MethodSource("negativeContact")
  public void negativeCreateContact(ContactData contact) {
    var contactStart = app.contacts().getList();
    app.contacts().createContact(contact);
    var contactFinish = app.contacts().getList();
    Assertions.assertEquals(contactFinish, contactStart);
  }

  @Test
  public void canCreateContactInGroup() {
    var contact = new ContactData()
            .withLastname("Usupov1")
            .withName("Danila1")
            .withMiddlename("Andreevich")
            .withPhoto(randomFile("src/test/resources/images"));
    if (app.hbm().getCountGroups() == 0) {
      app.hbm().createGroup(new GroupData("", "Name1", "Logo header", "Comment footer"));
    }

    var group = app.hbm().getGroupsList().get(0);
    var oldRelated = app.hbm().getContactsListInGroup(group);
    app.contacts().createContactInGroup(contact, group);
    var newRelated = app.hbm().getContactsListInGroup(group);
    Comparator<ContactData> compareById = (o1, o2) -> {
      return Integer.compare(Integer.parseInt(o1.id()), Integer.parseInt(o2.id()));
    };
    newRelated.sort(compareById);
    var expectedList = new ArrayList<>(oldRelated);
    expectedList.add(contact.withId(newRelated.get(newRelated.size() - 1).id())
            .withName(newRelated.get(newRelated.size() - 1).firstname())
            .withMiddlename(newRelated.get(newRelated.size() - 1).middlename())
            .withLastname(newRelated.get(newRelated.size() - 1).lastname())
            .withPhoto(newRelated.get(newRelated.size() - 1).photo()));
    expectedList.sort(compareById);
    Assertions.assertEquals(expectedList, newRelated);
  }

  @Test
  public void canCreateContactInGroupByAddTo() {
    // Создаём контакт, если нет
    if (app.hbm().getCountContacts() == 0) {
      app.hbm().createContact(new ContactData("", "Usupov1", "Danila1", "Andreevich", randomFile("src/test/resources/images")));
    }
    // Создаём группу, если нет
    if (app.hbm().getCountGroups() == 0) {
      app.hbm().createGroup(new GroupData("", "Name1", "Logo header", "Comment footer"));
    }
    var group = app.hbm().getGroupsList().get(0);
    var oldRelated = app.hbm().getContactsListInGroup(group);
    // Создаем контакт, если все контакты уже в других группах
    if (app.hbm().findContactNotInGroup(group) == null) {
      app.hbm().createContact(new ContactData("", "Usupov1", "Danila1", "Andreevich", randomFile("src/test/resources/images")));
    }
    var contact = app.hbm().findContactNotInGroup(group).get(0);
    app.contacts().createContactInGroupByAddTo(contact, group);
    // Получаем контакт, который добавили
    var contactToAdd = app.hbm().getLastAddContact(group);
    var newRelated = app.hbm().getContactsListInGroup(group);
    Comparator<ContactData> compareById = (o1, o2) -> {
      return Integer.compare(Integer.parseInt(o1.id()), Integer.parseInt(o2.id()));
    };
    newRelated.sort(compareById);
    var expectedList = new ArrayList<>(oldRelated);
    expectedList.add(contactToAdd);
    expectedList.sort(compareById);
    Assertions.assertEquals(expectedList, newRelated);

  }
}