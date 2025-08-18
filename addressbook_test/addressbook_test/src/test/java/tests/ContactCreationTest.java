package tests;


import model.ContactData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ContactCreationTest extends TestBase {

  public static List<ContactData> contactProvider() {
    var listContacts = new ArrayList<ContactData>();
    for (var name: List.of("", randomString(4))) {
      for (var middlename: List.of("", randomString(6))) {
        for (var lastname: List.of("", randomString(5))) {
          listContacts.add(new ContactData("", name, middlename, lastname));
            }
          }
        }
    return listContacts;
  }

  public static List<ContactData> negativeContact() {
    var listContacts = new ArrayList<ContactData>(List.of(
            new ContactData("", "Danila1'", "", "")
    ));
    return listContacts;
  }

  @Test
  public void canCreateContact() {
    app.contacts().createContact(new ContactData().withRequiredFields("Danila1", "Usupov1", "Andreevich"));
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
            .withMiddlename(contactFinish.get(contactFinish.size() - 1).middlename())
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


}
