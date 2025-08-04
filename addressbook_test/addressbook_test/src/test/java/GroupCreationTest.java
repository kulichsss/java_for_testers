import org.junit.jupiter.api.Test;


public class GroupCreationTest extends TestBase{

    @Test
    public void canCreateGroup() {
        openGroupPage();
        createGroup("Name1", "Logo header","Comment footer");
    }


}
