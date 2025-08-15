package manager;

import model.GroupData;
import org.openqa.selenium.By;

import java.util.ArrayList;
import java.util.List;

public class GroupHelper  extends HelperBase {

    public GroupHelper(ApplicationManager manager) {
        super(manager);
    }

    public void openGroupPage() {
        if (!manager.isElementPresent(By.name("new"))) {
            click(By.linkText("groups"));
        }
    }

    public void deleteGroup(GroupData group) {
        openGroupPage();
        click(By.cssSelector(String.format("input[value='%s']", group.id())));
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

    public List<GroupData> getList() {
        openGroupPage();
        var group = new ArrayList<GroupData>();
        var spans = manager.driver.findElements(By.cssSelector("span.group"));
        for (var span: spans) {
            var name = span.getText();
            var checkbox = span.findElement(By.name("selected[]"));
            var id = checkbox.getAttribute("value");
            group.add(new GroupData().withId(id).withName(name));
        }
        return group;
    }
}