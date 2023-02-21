package util;

import com.google.gson.reflect.TypeToken;
import io.restassured.response.Response;
import model.Issue;
import request.Requests;

import java.util.*;

import static checker.Checkers.*;
import static config.Config.getProject;
import static io.restassured.RestAssured.requestSpecification;
import static util.Util.responseToString;

public class RequestUtil extends Requests {

    /**
     * This method sends a POST request to create a new issue using the provided Issue object,
     * checks if the response status code is positive,
     * and returns the Issue object.
     *
     * @param issue the Issue object that will be used to create the issue via the API
     * @return the Issue object
     */
    public static Issue createIssue(Issue issue) {
        Response response = createIssueRequest(issue);
        checkPositiveStatusCode(response);
        return gson.fromJson(responseToString(response), Issue.class);
    }

    /**
     * This method sends a GET request to the server to retrieve an Issue that matches the Iid of parameter Issue object.
     * Ensures that the retrieved Issue is not a duplicate by checking the response status code for 409 Conflict.
     * If the Issue is not a duplicate, checks that the response has a positive status code
     * using the `checkPositiveStatusCode` method.
     * If the response is positive, returns the retrieved Issue object.
     *
     * @param issue The Issue object used to retrieve the Issue from the server
     * @return The retrieved Issue object
     */
    public static Issue getIssue(Issue issue) {
        Response response = getIssueRequest(issue);
        checkIssueIsNotDuplicated(response);
        checkPositiveStatusCode(response);
        return gson.fromJson(responseToString(response), Issue.class);
    }

    /**
     * This method sends a GET request to the server to retrieve a list of all issues.
     * Checks the response status code and returns the list of issues as Java objects.
     *
     * @return a list of issues
     */
    public static List<Issue> getIssues(int projectId) {
        Response response = getIssuesRequest(projectId);
        checkPositiveStatusCode(response);
        return gson.fromJson(responseToString(response), new TypeToken<List<Issue>>() {
        }.getType());
    }

    /**
     * This method sends a DELETE request to the server to delete the given issue and checks the response status code.
     * If the status code is positive, it means the issue was successfully deleted from the server.
     *
     * @param issue The issue to be deleted
     */
    public static void deleteIssue(Issue issue) {
        Response response = deleteIssueRequest(issue);
        checkPositiveStatusCode(response);
    }

    /**
     * The method adds the parameters for issue update retrieved from Map into queryParam.
     * Updates an issue with the given parameters.
     * Checks the request is completed successfully.
     * Returns the updated issue
     *
     * @param issue   the issue to update
     * @param updates a Map of the updates to be made
     * @return the updated Issue object
     */
    public static Issue updateIssue(Issue issue, Map<String, String> updates) {
        updates.forEach(requestSpecification::queryParam);
        Response response = updateIssueRequest(issue);
        checkPositiveStatusCode(response);
        return gson.fromJson(responseToString(response), Issue.class);
    }

    /**
     * This method is responsible for cleaning up the test issues by deleting them.
     * It retrieves a list of issues by calling getIssues() method with the current project.
     * It then enters into a loop and checks if the list of issues is empty or not.
     * If not empty, it deletes the issues using RequestUtil.deleteIssue() method.
     * The loop continues until all issues are deleted from the project.
     */
    public static void cleanupTestIssues() {
        List<Issue> issues = getIssues(getProject());
        while (!getIssues(getProject()).isEmpty()) {
            issues.forEach(RequestUtil::deleteIssue);
        }
    }
}
