package checker;

import io.restassured.response.Response;
import model.Issue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

import static java.util.Arrays.stream;
import static java.util.stream.IntStream.range;
import static org.testng.Assert.*;
import static request.Requests.getIssueRequest;

public class Checkers {

    public static final Logger LOGGER = LoggerFactory.getLogger(Checkers.class);

    /**
     * Checks if the response status code indicates a successful request, which could be 201, 200, or 204.
     * If the status code is positive, it logs a message with the code, indicating the request was completed successfully.
     * Otherwise, it fails the test and logs an error message with the response body as a string.
     *
     * @param response The Response object from the request.
     */
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
     * This method checks that updated Issue fields match with values for update contained by map.
     *
     * @param data  the data for issue update
     * @param issue updated issue
     */
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
    public static void checkObjectDoesNotExist(Response response) {
        if (response.statusCode() == 404) {
            LOGGER.info("Object does not exist! Status code: " + response.statusCode());
            assertTrue(true);
        } else if (response.statusCode() == 200 || response.statusCode() == 201) {
            fail("Object still exists! Message: " + response.getBody().asString());
        }
    }

    /**
     * This method sends a GET request to try to get an issue with the provided parameters
     * and checks that the response status code is 404 Not Found.
     *
     * @param issue an Issue object with the parameters of the issue to check
     */
    public static void checkIssueDoesNotExist(Issue issue) {
        Response response = getIssueRequest(issue);
        checkObjectDoesNotExist(response);
    }

}
