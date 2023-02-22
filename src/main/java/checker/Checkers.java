package checker;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import model.Issue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.asserts.SoftAssert;

import java.util.List;
import java.util.Map;

import static java.util.Arrays.stream;
import static java.util.stream.IntStream.range;
import static org.testng.Assert.*;
import static request.Requests.*;

public class Checkers {

    public static final Logger LOGGER = LoggerFactory.getLogger(Checkers.class);

    /**
     * Checks if the response status code indicates a successful request, which could be 201, 200, or 204.
     * If the status code is positive, it logs a message with the code, indicating the request was completed successfully.
     * Otherwise, it fails the test and logs an error message with the response body as a string.
     *
     * @param response The Response object from the request.
     */
    @Step
    public static void checkPositiveStatusCode(Response response) {
        int code = response.statusCode();
        if (code == 201 || code == 200 || code == 204) {
            LOGGER.info("Request was completed successfully with status code: " + code);
        } else {
            fail("Request was failed. Error message: " + response.getBody().asString());
        }
    }

    /**
     * This method checks if the given response indicates that the created issue is a duplicate. If the status code of the
     * response is 409, the method logs an error message and fails the test.
     *
     * @param response the response to be checked for a duplicated issue
     */
    @Step
    public static void checkIssueIsNotDuplicated(Response response) {
        if (response.statusCode() == 409) {
            fail("You created the duplicated Issue. There is some issue that already has this Iid. You need to recheck it.\n" +
                    "Status code: " + response.statusCode() + ".\n" +
                    "Message: " + response.asString());
        }
    }

    /**
     * This method checks if the objects inside given array of issues are the same by comparing the fields
     * iid, projectId, title, description, and type of each issue=
     * If the issues are the same, the method logs an information message and
     * asserts that the test is passed. If the issues are not the same, the method logs
     * the information of each issue and fails the test.
     *
     * @param issues an array of Issue objects to be compared
     */
    @Step
    public static void checkIssuesAreTheSame(Issue... issues) {
        boolean result = range(1, issues.length).allMatch(i -> issues[i - 1].equals(issues[i]));
        if (result) {
            LOGGER.info("The issues are the same!");
            assertTrue(true);
        } else {
            stream(issues).forEach(issue -> LOGGER.info("ISSUE: " + issue.getIid() + ", " + issue.getProjectId() + ", " + issue.getTitle() + ", " +
                    issue.getDescription() + ", " + issue.getType()));
            fail("The given issues are not the same!!!");
        }
    }

    /**
     * This method checks that updated Issue fields match with values for update provided by map.
     * If a specific key in the Map does not exist in the switch statement, it logs a message for further inspection.
     *
     * @param data  the data for issue update
     * @param issue updated issue
     */
    @Step
    public static void checkIssueIsUpdated(Map<String, String> data, Issue issue) {
        data.forEach((key, value) -> {
            switch (key) {
                case "title":
                    assertEquals(issue.getTitle(), value, "Error: " + issue.getTitle() +
                            "is not equal to " + value);
                    break;
                case "issue_type":
                    assertEquals(issue.getIssueType(), value, "Error: " + issue.getIssueType() +
                            "is not equal to " + value);
                    break;
                case "description":
                    assertEquals(issue.getDescription(), value, "Error: " + issue.getDescription() +
                            "is not equal to " + value);
                    break;
                default:
                    LOGGER.info("There is not checks for this case:" + key + ". Do we need to add it?");
                    break;
            }
        });
    }

    /**
     * Checks if an object does not exist by verifying the status code in the given response.
     * If the status code is 404, the method logs an info message and asserts that the object does not exist.
     * If the status code is 200 or 201, the method fails the test and returns an error message indicating that the object still exists.
     *
     * @param response the response to check for object existence
     */
    @Step
    public static void checkObjectDoesNotExist(Response response) {
        String message = response.getBody().asString();
        int statusCode = response.statusCode();
        if (statusCode == 404) {
            LOGGER.info("Object does not exist! Status code: " + statusCode);
            assertTrue(true);
        } else if (statusCode == 200 || statusCode == 201) {
            fail("Object still exists! Message: " + message);
        } else {
            fail("Something was wrong! It not clear if the object still exist. Make sense to recheck it!\n " +
                    "Status code: " + statusCode + "\nMessage: " + message);
        }
    }


    /**
     * This method verifies that an attempt to update an issue with a null title results in a 400 response, as expected.
     * If the response is not 400, the test fails with an appropriate message.
     * If the response is 400, it also checks that the response body contains the expected error message.
     *
     * @param response the response of the request to update an issue with a null title
     */
    @Step
    public static void checkIssueTitleCannotBeNull(Response response) {
        String message = response.getBody().asString();
        int statusCode = response.statusCode();
        if (statusCode == 400) {
            LOGGER.info("Issue title couldn't be updated with Null! It is expected result! " +
                    "Status code: " + response.statusCode());
            assertEquals(response.getBody().asString(), "{\"message\":{\"title\":[\"can't be blank\"]}}");
        } else if (statusCode == 200 || statusCode == 201) {
            fail("Issue title was update successfully! It is wrong result! Message: " + message);
        } else {
            fail("Unexpected Response! Issue title was not updated, but better clarify why this response has this: \n" +
                    "Status code: " + statusCode + "\nMessage: " + message);
        }
    }

    /**
     * This method sends a GET request to try to get an issue with the provided parameters
     * and checks that the response status code is 404 Not Found.
     *
     * @param issue an Issue object with the parameters of the issue to check
     */
    @Step
    public static void checkIssueDoesNotExist(Issue issue) {
        Response response = getIssueRequest(issue);
        checkObjectDoesNotExist(response);
    }

    /**
     * Checks that each response in the given list contains an authorization error (401 status code).
     *
     * @param softAssert a SoftAssert object to collect multiple assertion failures
     * @param responses  a list of Rest-Assured responses to check for authorization errors
     */
    public static void checkAuthorizationError(SoftAssert softAssert, List<Response> responses) {
        responses.forEach(response -> {
            int code = response.statusCode();
            String message = response.getBody().asString();
            if (code == 401) {
                LOGGER.info("Authorization error was received!!! It is expected result!!! Response message: " + message);
                softAssert.assertTrue(true);
            } else {
                softAssert.fail("Authorization was completed successfully or error is not related to authorization! Status Code: "
                        + code + ". Message: " + message);
            }
        });
    }

    /**
     * Checks that each response in the given list has a 404 status code.
     *
     * @param softAssert a SoftAssert object to collect multiple assertion failures
     * @param responses  a list of Rest-Assured responses to check for 404 status codes
     */
    public static void checkRequestsHave404StatusCode(SoftAssert softAssert, List<Response> responses) {
        responses.forEach(response -> {
            int code = response.statusCode();
            String message = response.getBody().asString();
            if (code == 404) {
                LOGGER.info("Request could not be completed and failed with 404! It is expected result!!! Response message: " + message);
                softAssert.assertTrue(true);
            } else {
                softAssert.fail("The request was completed or was failed not with 404! It is wrong result! " +
                        "There is point to recheck this request! Status Code: " + code + ". Message: " + message);
            }
        });
    }

}