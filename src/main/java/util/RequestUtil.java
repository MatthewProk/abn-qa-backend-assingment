package util;

import com.google.gson.reflect.TypeToken;
import io.restassured.response.Response;
import model.Issue;
import requests.Requests;

import java.util.List;

import static util.Util.responseToString;

public class RequestUtil extends Requests {

    public static Issue createIssue(Issue issue) {
        Response response = createIssueRequest(issue);
        return gson.fromJson(responseToString(response), Issue.class);
    }

    public static Issue getIssue(Issue issue) {
        Response response = getIssueRequest(issue);
        return gson.fromJson(responseToString(response), Issue.class);
    }
}
