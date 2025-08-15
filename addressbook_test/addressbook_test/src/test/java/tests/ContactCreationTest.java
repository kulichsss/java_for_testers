package tests;


import model.ContactData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.By;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class ContactCreationTest extends TestBase {

  public static List<ContactData> contactProvider() {
    var listContacts = new ArrayList<ContactData>();
    for (var name: List.of("", randomString(4))) {
      for (var middlename: List.of("", randomString(6))) {
        for (var lastname: List.of("", randomString(5))) {
          for (var address: List.of("", "г. Санкт-Петербург, ул. Колотушкина д .7а")) {
            for (var email: List.of("", "email@yandex.ru")) {
              for (var mobile: List.of("", "89994442525")) {
                listContacts.add(new ContactData(name, middlename, lastname, "", "", "", address, email, "", "", "", mobile));
              }
            }
          }
        }
      }
    }
    return listContacts;
  }

  public static List<ContactData> negativeContact() {
    var listContacts = new ArrayList<ContactData>(List.of(
            new ContactData("Danila1'", "", "", "", "", "", "", "", "", "", "", "")
    ));
    return listContacts;
  }

  @Test
  public void canCreateContact() {
    app.contacts().createContact(new ContactData().withRequiredFields("Danila1", "Usupov1", "Andreevich", "г. Санкт-Петербург, ул. Колотушкина д .7а", "email@yandex.ru", "89994442525"));
  }

  @ParameterizedTest
  @MethodSource("contactProvider")
  public void canCreateSomeContacts(ContactData contact) {
    var contactStart = app.contacts().countContacts();
    app.contacts().createContact(contact);
    var contactFinish = app.contacts().countContacts();
    Assertions.assertEquals(contactStart + 1, contactFinish);
  }

  @ParameterizedTest
  @MethodSource("negativeContact")
  public void negativeCreateContact(ContactData contact) {
    var contactStart = app.contacts().countContacts();
    app.contacts().createContact(contact);
    var contactFinish = app.contacts().countContacts();
    Assertions.assertEquals(contactStart, contactFinish);
  }


}
