package tests;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import common.CommonFunction;
import manager.hbm.GroupRecord;
import model.GroupData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Stream;


public class GroupCreationTest extends TestBase{

    public static List<GroupData> groupProvider() throws IOException {
        var result = new ArrayList<GroupData>();
        for (var name: List.of("", "group name")) {
            for (var header: List.of("", "group header")) {
                for (var footer: List.of("", "group footer")) {
                    result.add(new GroupData("", name, header, footer));
                }
            }
        }
        // var reader = Files.readString(Paths.get("groups.xml"));
        var mapper = new XmlMapper();
        var value = mapper.readValue(new File("groups.xml"), new TypeReference<List<GroupData>>() {});
        result.addAll(value);
        return result;

    }

    public static List<GroupData> negativeGroupProvider() {
        var result = new ArrayList<GroupData>(List.of(
                new GroupData("", "group name'", "", "" )
        ));
        return result;

    }

    public static Stream<GroupData> groupJdbcProvider() {
        Supplier<GroupData> randomGroup = () -> new GroupData()
                .withName(CommonFunction.randomString(5))
                .withHeader(CommonFunction.randomString(5))
                .withFooter(CommonFunction.randomString(5));
        return Stream.generate(randomGroup).limit(1);
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
        var expectedList = new ArrayList<>(groupList);
        expectedList.add(group.withId(newGroupList.get(newGroupList.size() - 1).id()).withHeader("").withFooter(""));
        Assertions.assertEquals(Set.copyOf(newGroupList), Set.copyOf(expectedList));

    }

    @ParameterizedTest
    @MethodSource("negativeGroupProvider")
    public void canNotCreateGroup(GroupData group) {
        var groupCount = app.groups().getList();
        app.groups().createGroup(group);
        var newGroupCount = app.groups().getList();
        Assertions.assertEquals(newGroupCount, groupCount);

    }

    @ParameterizedTest
    @MethodSource("groupJdbcProvider")
    public void canCreateGroupsByJdbc(GroupData group) {
        var groupList = app.jdbc().getGroupsList();
        app.groups().createGroup(group);
        var newGroupList = app.jdbc().getGroupsList();
        var extraGroupList = newGroupList.stream().filter(g -> !groupList.contains(g)).toList();
        var lastId = extraGroupList.get(0).id();
        var expectedList = new ArrayList<>(groupList);
        expectedList.add(group.withId(lastId));
        Assertions.assertEquals(Set.copyOf(newGroupList), Set.copyOf(expectedList));

    }

    @ParameterizedTest
    @MethodSource("groupJdbcProvider")
    public void canCreateGroupsByHmb(GroupData group) {
        var groupList = app.hbm().getGroupsList();
        app.groups().createGroup(group);
        var newGroupList = app.hbm().getGroupsList();
        var extraGroupList = newGroupList.stream().filter(g -> !groupList.contains(g)).toList();
        var lastId = extraGroupList.get(0).id();
        var expectedList = new ArrayList<>(groupList);
        expectedList.add(group.withId(lastId));
        Assertions.assertEquals(Set.copyOf(newGroupList), Set.copyOf(expectedList));

    }


}
