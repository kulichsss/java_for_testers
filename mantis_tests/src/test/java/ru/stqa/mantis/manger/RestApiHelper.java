package ru.stqa.mantis.manger;

import io.swagger.client.ApiClient;
import io.swagger.client.ApiException;
import io.swagger.client.Configuration;
import io.swagger.client.api.IssuesApi;
import io.swagger.client.api.UserApi;
import io.swagger.client.auth.ApiKeyAuth;
import io.swagger.client.model.Identifier;
import io.swagger.client.model.Issue;
import io.swagger.client.model.User;
import io.swagger.client.model.UserAddResponse;
import ru.stqa.mantis.common.CommonFunction;
import ru.stqa.mantis.model.IssueData;

public class RestApiHelper extends HelperBase {



    public RestApiHelper(ApplicationManager manager) {
        super(manager);
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        ApiKeyAuth Authorization = (ApiKeyAuth) defaultClient.getAuthentication("Authorization");
        Authorization.setApiKey(manager.property("rest.apiToken"));
    }


    public void createIssue(IssueData issueData) {
        Issue issue = new Issue(); // Issue | The issue to add.
        issue.setSummary(issueData.summary());
        issue.setDescription(issueData.description());
        var projectId = new Identifier();
        projectId.setId(issueData.project());
        issue.setProject(projectId);
        var categoryId = new Identifier();
        categoryId.setId(issueData.category());
        issue.setCategory(categoryId);
        IssuesApi apiInstance = new IssuesApi();
        try {
            apiInstance.issueAdd(issue);
        } catch (ApiException e) {
            System.err.println("Exception when calling IssuesApi#issueAdd");
            e.printStackTrace();
        }
    }

    public void createUser(String username, String realname, String password, String email) {
        UserApi apiInstance = new UserApi();
        User user = new User();
        user.setUsername(username);
        user.setRealName(realname);
        user.setPassword(password);
        user.setEmail(email);
        // User | The user to add.
        try {
            UserAddResponse result = apiInstance.userAdd(user);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling UserApi#userAdd");
            e.printStackTrace();
        }
    }
}
