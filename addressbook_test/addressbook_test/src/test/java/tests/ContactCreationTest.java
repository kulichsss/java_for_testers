package tests;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import common.CommonFunction;
import model.ContactData;
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



}
