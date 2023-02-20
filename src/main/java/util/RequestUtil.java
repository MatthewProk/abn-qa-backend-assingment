package util;

import io.restassured.response.Response;
import model.Issue;
import request.Requests;

import static checker.Checkers.checkIssueIsNotDuplicated;
import static checker.Checkers.checkPositiveStatusCode;
import static util.Util.responseToString;

public class RequestUtil extends Requests {

    public static Issue createIssue(Issue issue) {
        Response response = createIssueRequest(issue);
        checkPositiveStatusCode(response);
        return gson.fromJson(responseToString(response), Issue.class);
    }

    public static Issue getIssue(Issue issue) {
        Response response = getIssueRequest(issue);
        checkIssueIsNotDuplicated(response);
        checkPositiveStatusCode(response);
        return gson.fromJson(responseToString(response), Issue.class);
    }
}
