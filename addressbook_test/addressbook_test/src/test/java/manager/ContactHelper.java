package manager;

import model.ContactData;
import model.GroupData;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.List;

public class ContactHelper extends HelperBase {

    public ContactHelper(ApplicationManager manager) {
        super(manager);
    }

    // Переход на стартовые страницы
    public void openContactPage() {
        if (!manager.isElementPresent(By.name("new"))) {
            click(By.linkText("add new"));
        }
    }

    public void openHomePage() {
        if (!manager.isElementPresent(By.name("new"))) {
            manager.driver.findElement(By.name("group")).click();
            var dropdown = manager.driver.findElement(By.name("group"));
            dropdown.findElement(By.xpath("//option[. = '[all]']")).click();
            click(By.linkText("home"));
        }
    }

    // Создание контактов
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

    public void createContactInGroup(ContactData contact, GroupData group) {
        openContactPage();
        type(By.name("firstname"), contact.firstname());
        //type(By.name("middlename"), contact.middlename());
        type(By.name("lastname"), contact.lastname());
        if (!contact.photo().equals(""))
            attach(By.name("photo"), contact.photo());
        selectGroup(group);
        click(By.xpath("(//input[@name=\'submit\'])[2]"));
        click(By.linkText("home page"));
    }

    public void createContactInGroupByAddTo(GroupData group) {
        openHomePage();
        click(By.cssSelector("tr[name='entry'] input[name='selected[]']"));
        click(By.name("to_group"));
        var groups = manager.driver.findElement(By.name("to_group"));
        Select groupSelect = new Select(groups);
        groupSelect.selectByValue(group.id());
        click(By.name("add"));
    }


    //Удаление контактов
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

    public void deleteContactFromGroup(ContactData contact, GroupData group) {
        openHomePage();
        displayedGroups(group);
        deletedContact(contact);
    }



    //Модификация контактов
    public void modifyContact(ContactData contact, ContactData testData) {
        openHomePage();
        click(By.xpath("//a[@href='edit.php?id=" + contact.id() + "']"));
        manager.driver.findElement(By.name("firstname")).clear();
        type(By.name("firstname"), testData.firstname());
        click(By.xpath("(//input[@name='update'])[2]"));
        click(By.linkText("home page"));
    }



    //Количество и список контактов
    public void selectAllContacts() {
        var checkboxes = manager.driver.findElements(By.name("selected[]"));
        for (var checkbox: checkboxes) {
            checkbox.click();
        }
    }

    public int countContacts() {
        openHomePage();
        return manager.driver.findElements(By.name("selected[]")).size();
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


    //Вспомогательные методы
    private void selectGroup(GroupData group) {
        new Select(manager.driver.findElement(By.name("new_group"))).selectByValue(group.id());
    }

    private void displayedGroups(GroupData group) {
        click(By.name("group"));
        var dropdown = manager.driver.findElement(By.name("group"));
        Select groupSelect = new Select(dropdown);
        groupSelect.selectByValue(group.id());
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
