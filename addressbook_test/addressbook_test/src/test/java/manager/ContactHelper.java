package manager;

import model.ContactData;
import org.openqa.selenium.By;

public class ContactHelper extends HelperBase {

    public ContactHelper(ApplicationManager manager) {
        super(manager);
    }

    public void deletedContact() {
        openHomePage();
        click(By.xpath("//input[@value=\'Delete\']"));
        manager.driver.switchTo().alert().accept();
        click(By.linkText("home"));
    }

    public void openContactPage() {
        if (!manager.isElementPresent(By.name("new"))) {
            click(By.linkText("add new"));
        }
    }

    public void openHomePage() {
        if (!manager.isElementPresent(By.name("new"))) {
            click(By.linkText("home"));
        }
    }

    public boolean isContactPresent() {
        if (!manager.isElementPresent(By.name("new"))) {
            click(By.linkText("home"));
        }
        return manager.isElementPresent(By.name("selected[]"));
    }


    public void createContact(ContactData contact) {
        openContactPage();
        type(By.name("firstname"), contact.firstname());
        type(By.name("middlename"), contact.middlename());
        type(By.name("lastname"), contact.lastname());
        type(By.name("address"), contact.address());
        type(By.name("email"), contact.email());
        type(By.name("mobile"), contact.mobile());
        click(By.xpath("(//input[@name=\'submit\'])[2]"));
        click(By.linkText("home page"));
    }

}
