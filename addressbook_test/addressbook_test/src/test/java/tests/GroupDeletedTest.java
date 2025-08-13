package tests;

import model.GroupData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GroupDeletedTest extends TestBase {


    @Test
    public void canDeletedGroup() {

        if (app.groups().countGroups() == 0) {
            app.groups().createGroup(new GroupData("Name1", "Logo header", "Comment footer"));
        }
        var groupStart = app.groups().countGroups();
        app.groups().deleteGroup();
        Assertions.assertEquals(groupStart - 1, app.groups().countGroups());
    }


    @Test
    public void canDeletedAllGroups() {
        if (app.groups().countGroups() == 0) {
            app.groups().createGroup(new GroupData("Name1", "Logo header", "Comment footer"));
        }
        app.groups().deletedAllGroups();
        Assertions.assertEquals(0, app.groups().countGroups());
    }

}
