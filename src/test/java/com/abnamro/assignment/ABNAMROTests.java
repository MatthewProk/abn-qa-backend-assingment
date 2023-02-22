package com.abnamro.assignment;


import com.abnamro.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.response.Response;
import model.Issue;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.*;

import static checker.Checkers.*;
import static config.Config.getInvalidPrivateTokenValue;
import static config.Config.getPrivateTokenValue;
import static constants.TestGroups.*;
import static org.testng.Assert.fail;
import static util.RequestUtil.*;

public class ABNAMROTests extends BaseTest {

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
    public static void checkIssueIsUpdatedSuccessfully(Map<String, String> data) {
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
            " 2. Do CREATE request to create new issue based on initialized issue\n" +
            " 3. Do UPDATE request to update current issue title to NULL\n" +
            " 4. Check the update causes 404 error and message that a title cannot be updated")
    @Severity(SeverityLevel.CRITICAL)
    @Test(groups = IMPLEMENTED)
    public static void checkIssueTitleCannotBeUpdatedWithNull() {
        Issue issue = new Issue(new Random());
        createIssue(issue);
        Response response = updateIssueTitleWithNull(issue);
        checkIssueTitleCannotBeNull(response);
    }

    @Description("" +
            " 1. Initialize the Random expected issue with Iid, Title, Description and Type\n" +
            " 2. Remove the header related to Private Token\n" +
            " 3. Do project CREATE, UPDATE, DELETE requests\n" +
            " 4. Add all this responses to new ArrayList\n" +
            " 5. Check that response has status code 401\n" +
            " 6. Return back the header related to Private Token")
    @Severity(SeverityLevel.CRITICAL)
    @Test(groups = {IMPLEMENTED, SECURITY})
    public static void checkImpossibilityToCompletePostPutAndDeleteRequestsWithoutPrivateToken() {
        SoftAssert softAssert = new SoftAssert();
        Issue issue = new Issue(new Random());

        removeAuthorizationToken();
        checkAuthorizationError(softAssert, getListOfDeletePostAndPutRequests(issue));
        addAuthorizationToken(getPrivateTokenValue());

        softAssert.assertAll();
    }

    @Description("" +
            " 1. Initialize the Random expected issue with Iid, Title, Description and Type\n" +
            " 2. Remove the header related to Private Token\n" +
            " 3. Do project GET requests\n" +
            " 4. Add all this responses to new ArrayList\n" +
            " 5. Check that response has status code 404\n" +
            " 6. Return back the header related to Private Token")
    @Severity(SeverityLevel.NORMAL)
    @Test(groups = {IMPLEMENTED, SECURITY})
    public static void checkImpossibilityToCompleteGETRequestsWithoutPrivateToken() {
        SoftAssert softAssert = new SoftAssert();
        Issue issue = new Issue(new Random());

        removeAuthorizationToken();
        checkRequestsHave404StatusCode(softAssert, getListOfGetRequests(issue));
        addAuthorizationToken(getPrivateTokenValue());

        softAssert.assertAll();
    }

    @Description("" +
            " 1. Initialize the Random expected issue with Iid, Title, Description and Type\n" +
            " 2. Remove the header related to Private Token\n" +
            " 3. Add header with invalid token\n" +
            " 4. Do project GET requests\n" +
            " 5. Add all this responses to new ArrayList" +
            " 6. Check that response has status code 404\n" +
            " 2. Remove the header related to Invalid Private Token\n" +
            " 6. Return back the header related to Real Private Token")
    @Severity(SeverityLevel.NORMAL)
    @Test(groups = {IMPLEMENTED, SECURITY})
    public static void checkImpossibilityToCompleteGETRequestsWithInvalidToken() {
        SoftAssert softAssert = new SoftAssert();
        Issue issue = new Issue(new Random());

        removeAuthorizationToken();
        addAuthorizationToken(getInvalidPrivateTokenValue());
        checkAuthorizationError(softAssert, getListOfDeletePostAndPutRequests(issue));
        removeAuthorizationToken();
        addAuthorizationToken(getPrivateTokenValue());

        softAssert.assertAll();
    }

    @Description("" +
            " 1. Initialize the Random expected issue with Iid, Title, Description and Type\n" +
            " 2. Remove the header related to Private Token\n" +
            " 3. Add header with invalid token\n" +
            " 3. Do project CREATE, UPDATE, DELETE requests\n" +
            " 5. Add all this responses to new ArrayList" +
            " 6. Check that response has status code 404\n" +
            " 2. Remove the header related to Invalid Private Token\n" +
            " 6. Return back the header related to Real Private Token")
    @Test(groups = {IMPLEMENTED, SECURITY})
    public static void checkImpossibilityToCompletePostPutAndDeleteRequestsWithInvalidToken() {
        SoftAssert softAssert = new SoftAssert();
        Issue issue = new Issue(new Random());

        removeAuthorizationToken();
        addAuthorizationToken(getInvalidPrivateTokenValue());
        checkAuthorizationError(softAssert, getListOfDeletePostAndPutRequests(issue));
        removeAuthorizationToken();
        addAuthorizationToken(getPrivateTokenValue());

        softAssert.assertAll();
    }

    /**
     * This test was added to show that it should not be added to test run because of group = IN_DEVELOPMENT
     */
    @Test(groups = IN_DEVELOPMENT)
    public static void unfinishedTest() {
        fail();
    }
}