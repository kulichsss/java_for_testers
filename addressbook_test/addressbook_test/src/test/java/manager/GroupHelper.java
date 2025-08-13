package manager;

import model.GroupData;
import org.openqa.selenium.By;

public class GroupHelper  extends HelperBase {

    public GroupHelper(ApplicationManager manager) {
        super(manager);
    }

    public void openGroupPage() {
        if (!manager.isElementPresent(By.name("new"))) {
            click(By.linkText("groups"));
        }
    }

    public void deleteGroup() {
        openGroupPage();
        click(By.xpath("//input[@name='selected[]']"));
        click(By.name("delete"));
        click(By.linkText("group page"));
    }

    public void createGroup(GroupData group) {
        openGroupPage();
        click(By.name("new"));
        type(By.name("group_name"), group.name());
        type(By.name("group_header"), group.header());
        type(By.name("group_footer"), group.footer());
        click(By.name("submit"));
        click(By.linkText("group page"));
    }

    public int countGroups() {
        openGroupPage();
        return manager.driver.findElements(By.name("selected[]")).size();
    }

    public void deletedAllGroups() {
        openGroupPage();
        selectAllGroups();
        click(By.name("delete"));
    }

    private void selectAllGroups() {
        var checkboxes = manager.driver.findElements(By.name("selected[]"));
        for (var checkbox: checkboxes) {
            checkbox.click();
        }
    }
}