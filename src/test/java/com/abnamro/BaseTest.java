package com.abnamro;

import model.Issue;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.DataProvider;
import util.RequestUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static config.Config.getProject;
import static util.RequestUtil.getIssues;

public class BaseTest {

    @DataProvider(name = "updateIssue")
    public Object[][] dataProvider() {
        Map<String, String> updateIssueWith1Parameter = new HashMap<>() {
            {
                put("title", "Updated Title");
            }
        };
        Map<String, String> updateIssueWith2Parameters = new HashMap<>() {
            {
                put("title", "Updated Title");
                put("issue_type", "incident");
            }
        };
        Map<String, String> updateIssueWith3Parameters = new HashMap<>() {
            {
                put("title", "Updated Title");
                put("issue_type", "incident");
                put("description", "Updated Description");

            }
        };
        return new Object[][]{
                {updateIssueWith1Parameter},
                {updateIssueWith2Parameters},
                {updateIssueWith3Parameters}
        };
    }

    /**
     * This method is used to cleanup the test data after a tests have been executed. It retrieves all issues
     * associated with the project, and deletes them one by one using the deleteIssue() method. If no issues
     * are present, the method will not take any action.
     */
    @AfterSuite(alwaysRun = true)
    public void cleanupDataAfterTest() {
        List<Issue> issues = getIssues(getProject());
        if (!issues.isEmpty()) {
            issues.forEach(RequestUtil::deleteIssue);
        }
    }
}
