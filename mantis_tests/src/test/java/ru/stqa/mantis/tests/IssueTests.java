package ru.stqa.mantis.tests;

import org.junit.jupiter.api.Test;
import ru.stqa.mantis.common.CommonFunction;
import ru.stqa.mantis.model.IssueData;

public class IssueTests extends TestBase {

    @Test
    public void canIssue() {
        app.rest().createIssue(new IssueData()
                .withSummary(CommonFunction.randomString(7))
                .withDescription(CommonFunction.randomString(50))
                .withProject(1L)
        );
    }
}
