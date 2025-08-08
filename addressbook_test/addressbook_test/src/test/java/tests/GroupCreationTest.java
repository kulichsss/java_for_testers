package tests;

import model.GroupData;
import org.junit.jupiter.api.Test;


public class GroupCreationTest extends TestBase{

    @Test
    public void canCreateGroup() {
        app.openGroupPage();
        app.createGroup(new GroupData("Name1", "Logo header", "Comment footer"));
    }


}
