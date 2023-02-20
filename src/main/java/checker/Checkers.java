package checker;

import io.restassured.response.Response;
import model.Issue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.stream.IntStream;

import static java.util.Arrays.stream;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

public class Checkers {

    public static final Logger LOGGER = LoggerFactory.getLogger(Checkers.class);

    public static void  checkPositiveStatusCode(Response response){
        int code = response.statusCode();
        if(code == 201 || code == 200 || code == 204){
            LOGGER.info("Request was completed successfully with status code: " + code);
        } else {
            fail("Request was failed. Error message: " + response.getBody().asString());
        }
    }

    public static void checkIssueIsNotDuplicated(Response response) {
        if (response.statusCode() == 409) {
            fail("You created the duplicated Issue. There is some issue that already has this Iid. You need to recheck it.\n" +
                    "Status code: " + response.statusCode() + ".\n" +
                    "Message: " + response.asString());
        }
    }

    public static void checkIssuesAreTheSame(Issue... issues) {
        boolean result = IntStream.range(1, issues.length).allMatch(i -> issues[i - 1].equals(issues[i]));
        if (result){
            LOGGER.info("The issues are the same!");
            assertTrue(true);
        } else {
            stream(issues).forEach(issue -> {
                LOGGER.info("ISSUE: " + issue.getIid() + ", " + issue.getProjectId() + ", " + issue.getTitle() + ", " +
                        issue.getDescription() + ", " + issue.getType());
            });
            fail("The given issues are not the same!!!");
        }
    }
}
