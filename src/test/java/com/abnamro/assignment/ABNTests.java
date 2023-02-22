package com.abnamro.assignment;


import com.abnamro.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import model.Issue;
import org.testng.annotations.Test;

import java.util.*;

import static checker.Checkers.*;
import static constants.TestGroups.IMPLEMENTED;
import static constants.TestGroups.IN_DEVELOPMENT;
import static org.testng.Assert.fail;
import static util.RequestUtil.*;

public class ABNTests extends BaseTest {

    @Description("" +
            " THIS CASE COVER 2 SCENARIOS - CREATE and READ\n" +
            " 1. Initialize the Random issue with Iid, Title, Description and Type and save it to expectedIssue\n" +
            " 2. Complete post request 'Create Issue' and save it to createdIssue\n" +
            " 3. Do API Get request to get added Issue by expectedIssue and save it to receivedIssue\n" +
            " 4. Check expectedIssue, createdIssue and receivedIssue objects are the same")
    @Severity(SeverityLevel.BLOCKER)
    @Test(groups = IMPLEMENTED)
    public static void checkIssueIsCreatedSuccessfully() {
        Issue expectedIssue = new Issue(new Random());
        Issue createdIssue = createIssue(expectedIssue);
        Issue receivedIssue = getIssue(expectedIssue);
        checkIssuesAreTheSame(expectedIssue, createdIssue, receivedIssue);
    }

    @Description("" +
            " 1. Initialize the Random expected issue with Iid, Title, Description and Type\n" +
            " 2. Do CREATE request to create new issue based on initialized issue\n" +
            " 3. Complete DELETE request\n" +
            " 4. Check deleted issue does not exist anymore")
    @Severity(SeverityLevel.CRITICAL)
    @Test(groups = IMPLEMENTED)
    public static void checkIssueIsDeletedSuccessfully() {
        Issue expectedIssue = new Issue(new Random());
        createIssue(expectedIssue);
        deleteIssue(expectedIssue);
        checkIssueDoesNotExist(expectedIssue);
    }

    @Description("" +
            " 1. Initialize the Random expected issue with Iid, Title, Description and Type\n" +
            " 2. Do CREATE request to create new issue based on initialized issue\n" +
            " 3. Do UPDATE request to update current issue using the data from @DataProvider\n" +
            " 4. Do GET request to get updated issue by previously randomly created issue\n" +
            " 5. Check the updated issue has updated fields\n" +
            " 6. Check the updated issue and the received issue are the same")
    @Severity(SeverityLevel.CRITICAL)
    @Test(groups = IMPLEMENTED, dataProvider = "updateIssue")
    public static void checkIssueIsEditedSuccessfully(Map<String, String> data) {
        Issue issue = new Issue(new Random());
        createIssue(issue);
        Issue updatedIssue = updateIssue(issue, data);
        Issue receivedIssue = getIssue(issue);
        checkIssueIsUpdated(data, updatedIssue);
        checkIssueIsUpdated(data, receivedIssue);
        checkIssuesAreTheSame(updatedIssue, receivedIssue);
    }

    @Description("" +
            " 1. Initialize the Random expected issue with Iid, Title, Description and Type\n" +
            " 2. Remove the header related to Private Token" +
            " 3. Do project CREATE, UPDATE, DELETE requests\n" +
            " 4. Add all this responses to new ArrayList" +
            " 5. Check that response has status code 401\n" +
            " 6. Return back the header related to Private Token")
    @Severity(SeverityLevel.CRITICAL)
    @Test(groups = IMPLEMENTED)
    public static void checkImpossibilityToCompletePostPutAndDeleteRequestsWithoutAuthorizationToken() {
        checkAuthorizationErrorForPostDeleteAndPutRequests();
    }

    @Description("" +
            " 1. Initialize the Random expected issue with Iid, Title, Description and Type\n" +
            " 2. Remove the header related to Private Token" +
            " 3. Do project GET requests\n" +
            " 4. Add all this responses to new ArrayList" +
            " 5. Check that response has status code 404\n" +
            " 6. Return back the header related to Private Token")
    @Severity(SeverityLevel.CRITICAL)
    @Test(groups = IMPLEMENTED)
    public static void checkImpossibilityToCompleteGETRequestsWithoutAuthorizationToken() {
        checkGetRequestCannotFindProjectWithoutAuthorization();
    }

    /**
     * This test was added to show that it should not be added to test run because of group = IN_DEVELOPMENT
     */
    @Test(groups = IN_DEVELOPMENT)
    public static void unfinishedTest() {
        fail();
    }
}