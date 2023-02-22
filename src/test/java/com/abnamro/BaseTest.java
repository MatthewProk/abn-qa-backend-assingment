package com.abnamro;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;

import java.util.HashMap;
import java.util.Map;

import static util.RequestUtil.*;

public class BaseTest {

    /**
     * Data provider method for the updateIssue test case,
     * providing different sets of data to test with.
     * Each set of data is represented as a Map object,
     * with each key-value pair representing a parameter to update.
     *
     * @return an array of Object arrays, where each inner array represents a set of data for the updateIssue test case.
     */
    @DataProvider(name = "updateIssue")
    public Object[][] dataProvider() {
        Map<String, String> updateIssueWith1Parameter = new HashMap<>() {
            {
                put("title", "First Updated Title");
            }
        };
        Map<String, String> updateIssueWith2Parameters = new HashMap<>() {
            {
                put("title", "Second Updated Title");
                put("issue_type", "incident");
            }
        };
        Map<String, String> updateIssueWith3Parameters = new HashMap<>() {
            {
                put("title", "Third Updated Title");
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
        System.setProperty("logback.configurationFile", "src/main/resources/log4j2.xml");
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