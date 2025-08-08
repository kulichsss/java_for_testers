package tests;

import model.GroupData;
import org.junit.jupiter.api.Test;

public class GroupDeletedTest extends TestBase {


    @Test
    public void canDeletedGroup() {
        app.openGroupPage();
        if (!app.isGroupPresent()) {
            app.createGroup(new GroupData("Name1", "Logo header", "Comment footer"));
        }
        app.deleteGroup();
    }

}
