package tests;

import model.GroupData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Random;

public class GroupDeletedTest extends TestBase {


    @Test
    public void canDeletedGroup() {

        if (app.groups().countGroups() == 0) {
            app.groups().createGroup(new GroupData("", "Name1", "Logo header", "Comment footer"));
        }
        var groupStart = app.groups().countGroups();
        app.groups().deleteGroup(null);
        Assertions.assertEquals(groupStart - 1, app.groups().countGroups());
    }


    @Test
    public void canDeletedAllGroups() {
        if (app.groups().countGroups() == 0) {
            app.groups().createGroup(new GroupData("", "Name1", "Logo header", "Comment footer"));
        }
        app.groups().deletedAllGroups();
        Assertions.assertEquals(0, app.groups().countGroups());
    }

    @Test
    public void canDeletedGroupByList() {

        if (app.groups().countGroups() == 0) {
            app.groups().createGroup(new GroupData("", "Name1", "Logo header", "Comment footer"));
        }
        var groupDataList = app.groups().getList();
        var rnd = new Random();
        var index = rnd.nextInt(groupDataList.size());
        app.groups().deleteGroup(groupDataList.get(index));
        var newGroup = app.groups().getList();
        var expectedList = new ArrayList<>(groupDataList);
        expectedList.remove(index);
        Assertions.assertEquals(newGroup, expectedList);
    }

}
