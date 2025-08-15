package tests;

import model.GroupData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class GroupCreationTest extends TestBase{

    public static List<GroupData> groupProvider() {
        var result = new ArrayList<GroupData>();
        for (var name: List.of("", "group name")) {
            for (var header: List.of("", "group header")) {
                for (var footer: List.of("", "group footer")) {
                    result.add(new GroupData("", name, header, footer));
                }
            }
        }
        for (int i = 1; i < 5; i++) {
            result.add(new GroupData("", randomString(i * 10), randomString(i * 10), randomString(i * 10)));
        }
        return result;

    }

    public static List<GroupData> negativeGroupProvider() {
        var result = new ArrayList<GroupData>(List.of(
                new GroupData("", "group name'", "", "" )
        ));
        return result;

    }

    @Test
    public void canCreateGroup() {
        var groupCount = app.groups().countGroups();
        app.groups().createGroup(new GroupData().withName("some name"));
        var newGroupCount = app.groups().countGroups();
        Assertions.assertEquals(groupCount + 1, newGroupCount);
    }

    @ParameterizedTest
    @MethodSource("groupProvider")
    public void canCreateSomeGroups(GroupData group) {
        var groupList = app.groups().getList();
        app.groups().createGroup(group);
        var newGroupList = app.groups().getList();
        Comparator<GroupData> compareById = (o1, o2) -> {
            return Integer.compare(Integer.parseInt(o1.id()), Integer.parseInt(o2.id()));
        };
        newGroupList.sort(compareById);
        var expectedList = new ArrayList<>(groupList);
        expectedList.add(group.withId(newGroupList.get(newGroupList.size() - 1).id()).withHeader("").withFooter(""));
        expectedList.sort(compareById);
        Assertions.assertEquals(newGroupList, expectedList);

    }

    @ParameterizedTest
    @MethodSource("negativeGroupProvider")
    public void canNotCreateGroup(GroupData group) {
        var groupCount = app.groups().getList();
        app.groups().createGroup(group);
        var newGroupCount = app.groups().getList();
        Assertions.assertEquals(newGroupCount, groupCount);

    }


}
