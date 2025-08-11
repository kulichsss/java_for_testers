package tests;

import model.ContactData;
import org.junit.jupiter.api.Test;

public class ContactDeletedTest extends TestBase {


    @Test
    public void canDeletedContact() {
        if (!app.contacts().isContactPresent()) {
            app.contacts().createContact(new ContactData().withRequiredFields("Danila1", "Usupov1", "Andreevich", "г. Санкт-Петербург, ул. Колотушкина д .7а", "dan@yandex.ru", "89994442525"));
        }
        app.contacts().deletedContact();
    }


}
