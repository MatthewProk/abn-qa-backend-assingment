package com.abnamro;

import model.Issue;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import util.RequestUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static config.Config.getProject;
import static util.RequestUtil.cleanupTestIssues;
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
     * This method is used as a TestNG @BeforeSuite method with the alwaysRun attribute set to true,
     * meaning it will always be executed even if the tests fail. The method calls the static
     * cleanupTestIssues() method from the RequestUtil class to delete all the issues created during
     * the test run.
     */
    @BeforeSuite(alwaysRun = true)
    public void cleanupDataBeforeTest() {
        cleanupTestIssues();
    }

    /**
     * This method is used as a TestNG @AfterSuite method with the alwaysRun attribute set to true,
     * meaning it will always be executed even if the tests fail. The method calls the static
     * cleanupTestIssues() method from the RequestUtil class to delete all the issues created during
     * the test run.
     */
    @AfterSuite(alwaysRun = true)
    public void cleanupDataAfterTest() {
        cleanupTestIssues();
    }
}
