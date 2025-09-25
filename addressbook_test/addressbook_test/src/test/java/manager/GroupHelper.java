package manager;

import io.qameta.allure.Step;
import model.GroupData;
import org.openqa.selenium.By;

import java.util.ArrayList;
import java.util.List;

public class GroupHelper  extends HelperBase {

    public GroupHelper(ApplicationManager manager) {
        super(manager);
    }

    // Переход на стартовые страницы
    public void openGroupPage() {
        if (!manager.isElementPresent(By.name("new"))) {
            click(By.linkText("groups"));
        }
    }



    // Создание групп
    public void createGroup(GroupData group) {
        openGroupPage();
        click(By.name("new"));
        type(By.name("group_name"), group.name());
        type(By.name("group_header"), group.header());
        type(By.name("group_footer"), group.footer());
        click(By.name("submit"));
        click(By.linkText("group page"));
    }



    //Удаление групп
    @Step
    public void deleteGroup(GroupData group) {
        openGroupPage();
        click(By.cssSelector(String.format("input[value='%s']", group.id())));
        click(By.name("delete"));
        click(By.linkText("group page"));
    }


    public void deletedAllGroups() {
        openGroupPage();
        selectAllGroups();
        click(By.name("delete"));
    }



    //Модификация групп
    public void modifyGroup(GroupData group, GroupData modifyName) {
        openGroupPage();
        click(By.cssSelector(String.format("input[value='%s']", group.id())));
        click(By.name("edit"));
        clearField();
        type(By.name("group_name"), modifyName.name());
        click(By.name("update"));
        click(By.linkText("group page"));
    }



    // Количество и список групп
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

    public int countGroups() {
        openGroupPage();
        return manager.driver.findElements(By.name("selected[]")).size();
    }



    //Вспомогательные методы
    private void selectAllGroups() {
        var checkboxes = manager.driver.findElements(By.name("selected[]"));
        for (var checkbox: checkboxes) {
            checkbox.click();
        }
    }

    private void clearField() {
        manager.driver.findElement(By.name("group_name")).clear();
    }
}