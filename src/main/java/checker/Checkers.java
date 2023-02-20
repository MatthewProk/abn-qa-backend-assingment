package checker;

import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
}
