package tests;

import model.GroupData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

public class GroupModificationTest extends TestBase {

    @Test
    public void canModifyGroup() {
        if (app.hbm().getCountGroups() == 0) {
            app.hbm().createGroup(new GroupData("", "Name1", "Logo header", "Comment footer"));
        }
        var groupDataList = app.hbm().getGroupsList();
        var rnd = new Random();
        var index = rnd.nextInt(groupDataList.size());
        GroupData testData = new GroupData()
                .withId(groupDataList.get(index).id())
                .withName("modify name")
                .withHeader(groupDataList.get(index).header())
                .withFooter(groupDataList.get(index).footer());
        app.groups().modifyGroup(groupDataList.get(index), testData);
        var newGroup = app.hbm().getGroupsList();
        var expectedList = new ArrayList<>(groupDataList);
        expectedList.set(index, testData.withId(groupDataList.get(index).id()));
        Comparator<GroupData> compareById = (o1, o2) -> {
            return Integer.compare(Integer.parseInt(o1.id()), Integer.parseInt(o2.id()));
        };
        newGroup.sort(compareById);
        expectedList.sort(compareById);
        Assertions.assertEquals(expectedList, newGroup);
    }
}
