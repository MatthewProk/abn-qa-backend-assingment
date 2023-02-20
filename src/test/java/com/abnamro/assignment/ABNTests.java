package com.abnamro.assignment;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;


@Tag("UnitTest")
public class ABNTests {

    /**
     * THIS CASE COVER 2 SCENARIOS - CREATE and READ
     * 1. Initialize the Random issue with Iid, Title, Description and Type
     * 2. Complete post request 'Create Issue' and save the Iid of the new Issue
     * 3. Set received Iid for expectedIssue
     * 4. Do API Get request to get added Issue by Iid and save it to actualIssue
     * 5. Check expectedIssue and actualIssue are the same
     */
    @Test
    public static void checkIssueIsCompletedSuccessfully(){
    }


    /**
     * 1. Initialize the Random expected issue with Iid, Title, Description and Type
     * 2. Complete DELETE request
     * 3. Check deleted issue does not exist anymore
     */
    @Test
    public static void checkIssueIsGotSuccessfully(){
    }

    /**
     * 1. Initialize the Random expected issue with Iid, Title, Description and Type
     * 2. Do CREATE request to create new issue based on initialized issue
     * 2. Complete DELETE request
     * 3. Check deleted issue does not exist anymore
     */
    @Test
    public static void checkIssueIsDeletedSuccessfully(){
    }

    /**
     * 1. Initialize the Random expected issue with Iid, Title, Description and Type
     * 2. Do CREATE request to create new issue based on initialized issue
     * 3. Change some parameter of received new issue object
     * 4. Do UPDATE request to update current issue
     * 5. Check the issue is updated successfully
     */
    @Test
    public static void checkIssueIsEditedSuccessfully(){
    }
}
