import org.junit.jupiter.api.Test;

public class GroupDeletedTest extends TestBase {


    @Test
    public void canDeletedGroup() {
        openGroupPage();
        if (!isGroupPresent()) {
            createGroup("Name1", "Logo header", "Comment footer");
        }
        deleteGroup();
    }

}
