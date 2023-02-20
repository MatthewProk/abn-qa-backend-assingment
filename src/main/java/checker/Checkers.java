package checker;

import io.restassured.response.Response;
import model.Issue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.util.Arrays.stream;
import static java.util.stream.IntStream.range;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

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
}
