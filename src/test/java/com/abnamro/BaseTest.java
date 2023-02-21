package com.abnamro;

import model.Issue;
import org.testng.annotations.AfterTest;
import util.RequestUtil;

import java.util.List;

import static config.Config.getProject;
import static util.RequestUtil.getIssues;

public class BaseTest {

    /**
     * This method is used to cleanup the test data after a tests have been executed. It retrieves all issues
     * associated with the project, and deletes them one by one using the deleteIssue() method. If no issues
     * are present, the method will not take any action.
     */
    @AfterTest
    public void cleanupDataAfterTest() {
        List<Issue> issues = getIssues(getProject());
        if (!issues.isEmpty()) {
            issues.forEach(RequestUtil::deleteIssue);
        }
    }
}
