package tests;


import model.ContactData;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

public class ContactCreationTest extends TestBase {

  @Test
  public void canCreateContact() {
    app.contacts().createContact(new ContactData().withRequiredFields("Danila1", "Usupov1", "Andreevich", "г. Санкт-Петербург, ул. Колотушкина д .7а", "dan@yandex.ru", "89994442525"));
  }

}
