package tests;

import model.GroupData;
import org.junit.jupiter.api.Test;

public class GroupDeletedTest extends TestBase {


    @Test
    public void canDeletedGroup() {
        if (!app.groups().isGroupPresent()) {
            app.groups().createGroup(new GroupData("Name1", "Logo header", "Comment footer"));
        }
        app.groups().deleteGroup();
    }

}
