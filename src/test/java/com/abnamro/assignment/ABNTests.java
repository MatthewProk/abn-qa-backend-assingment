package com.abnamro.assignment;


import com.abnamro.BaseTest;
import model.Issue;
import org.testng.annotations.Test;

import java.util.*;

import static checker.Checkers.*;
import static util.RequestUtil.*;

public class ABNTests extends BaseTest {

    /**
     * THIS CASE COVER 2 SCENARIOS - CREATE and READ
     * 1. Initialize the Random issue with Iid, Title, Description and Type and save it to expectedIssue
     * 2. Complete post request 'Create Issue' and save it to createdIssue
     * 3. Do API Get request to get added Issue by expectedIssue and save it to receivedIssue
     * 5. Check expectedIssue, createdIssue and receivedIssue objects are the same
     */
    @Test
    public static void checkIssueIsCreatedSuccessfully() {
        Issue expectedIssue = new Issue(new Random());
        Issue createdIssue = createIssue(expectedIssue);
        Issue receivedIssue = getIssue(expectedIssue);
        checkIssuesAreTheSame(expectedIssue, createdIssue, receivedIssue);
    }

    /**
     * 1. Initialize the Random expected issue with Iid, Title, Description and Type
     * 2. Do CREATE request to create new issue based on initialized issue
     * 2. Complete DELETE request
     * 3. Check deleted issue does not exist anymore
     */
    @Test
    public static void checkIssueIsDeletedSuccessfully() {
        Issue expectedIssue = new Issue(new Random());
        createIssue(expectedIssue);
        deleteIssue(expectedIssue);
        checkIssueDoesNotExist(expectedIssue);
    }

    /**
     * 1. Initialize the Random expected issue with Iid, Title, Description and Type
     * 2. Do CREATE request to create new issue based on initialized issue
     * 4. Do UPDATE request to update current issue using the data from @DataProvider
     * 5. Do GET request to get updated issue by previously randomly created issue
     * 5. Check the updated issue has updated fields
     * 6. Check the updated issue and the received issue are the same
     */
    @Test(dataProvider = "updateIssue")
    public static void checkIssueIsEditedSuccessfully(Map<String, String> data) {
        Issue issue = new Issue(new Random());
        createIssue(issue);
        Issue updatedIssue = updateIssue(issue, data);
        Issue receivedIssue = getIssue(issue);
        checkIssueIsUpdated(data, updatedIssue);
        checkIssueIsUpdated(data, receivedIssue);
        checkIssuesAreTheSame(updatedIssue, receivedIssue);
    }
}
