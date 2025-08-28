package manager;

import model.GroupData;
import org.openqa.selenium.By;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcHelper extends HelperBase {
    public JdbcHelper(ApplicationManager manager) {
        super(manager);
    }

    public List<GroupData> getGroupsList() {
        var group = new ArrayList<GroupData>();
        try (var conn = DriverManager.getConnection("jdbc:mysql://localhost/addressbook", "root", ""); var statement = conn.createStatement(); var query = statement.executeQuery("SELECT group_id, group_name, group_header, group_footer FROM group_list")) {
            while (query.next()) {
                group.add(new GroupData()
                        .withId(query.getString("group_id"))
                        .withName(query.getString("group_name"))
                        .withHeader(query.getString("group_header"))
                        .withFooter(query.getString("group_footer")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return group;
    }
}
