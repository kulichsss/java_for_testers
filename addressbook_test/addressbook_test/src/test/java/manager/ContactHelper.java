package manager;

import model.ContactData;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;

public class ContactHelper extends HelperBase {

    public ContactHelper(ApplicationManager manager) {
        super(manager);
    }

    public void deletedContact() {
        openHomePage();
        click(By.name("selected[]"));
        click(By.xpath("//input[@value=\'Delete\']"));
        findAlert();
        click(By.linkText("home"));
    }

    public void deletedAllContacts() {
        openHomePage();
        selectAllContacts();
        click(By.xpath("//input[@value=\'Delete\']"));
        findAlert();
        click(By.linkText("home"));
    }

    public void selectAllContacts() {
        var checkboxes = manager.driver.findElements(By.name("selected[]"));
        for (var checkbox: checkboxes) {
            checkbox.click();
        }
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

    public int countContacts() {
        openHomePage();
        return manager.driver.findElements(By.name("selected[]")).size();
    }

    public void findAlert() {
        try {
            manager.driver.switchTo().alert().accept();
            System.out.println("Алерт принят.");
        } catch (NoAlertPresentException e) {
            System.out.println("Алерта не было — всё нормально, продолжаем.");
        }
    }
}
