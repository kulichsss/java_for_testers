package manager;

import model.ContactData;
import model.GroupData;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;

import java.util.ArrayList;
import java.util.List;

public class ContactHelper extends HelperBase {

    public ContactHelper(ApplicationManager manager) {
        super(manager);
    }

    public void deletedContact(ContactData contact) {
        openHomePage();
        click(By.cssSelector(String.format("input[value='%s']", contact.id())));
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
        //type(By.name("middlename"), contact.middlename());
        type(By.name("lastname"), contact.lastname());
        if (!contact.photo().equals(""))
                attach(By.name("photo"), contact.photo());
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

    public List<ContactData> getList() {
        openHomePage();
        var contact = new ArrayList<ContactData>();
        var tds = manager.driver.findElements(By.cssSelector("tr[name='entry']"));
        for (var td: tds) {
            var checkbox = td.findElement(By.name("selected[]"));
            var id = checkbox.getAttribute("value");
            var column = td.findElements(By.tagName("td"));
            var name = column.get(1).getText().trim();
            var lastname = column.get(2).getText().trim();
//            click(By.xpath("//a[@href='edit.php?id=" + id + "']"));
//            var name = manager.driver.findElement(By.name("firstname")).getAttribute("value");
//            var lastName = manager.driver.findElement(By.name("lastname")).getAttribute("value");
            contact.add(new ContactData().withId(id).withName(name).withMiddlename("").withLastname(lastname).withPhoto(""));
            //openHomePage();
        }
        return contact;
    }

    public void modifyContact(ContactData contact, ContactData testData) {
        openHomePage();
        click(By.xpath("//a[@href='edit.php?id=" + contact.id() + "']"));
        manager.driver.findElement(By.name("firstname")).clear();
        type(By.name("firstname"), testData.firstname());
        click(By.xpath("(//input[@name='update'])[2]"));
        click(By.linkText("home page"));
    }

}
