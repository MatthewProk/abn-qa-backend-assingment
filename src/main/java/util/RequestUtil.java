package util;

import com.google.gson.reflect.TypeToken;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.internal.RequestSpecificationImpl;
import io.restassured.response.Response;
import model.Issue;
import request.Requests;

import java.util.*;

import static checker.Checkers.*;
import static config.Config.*;
import static io.restassured.RestAssured.requestSpecification;
import static java.util.Arrays.asList;
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
    @Step
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
    @Step
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
    @Step
    public static List<Issue> getIssues(int projectId) {
        Response response = getIssuesRequest(projectId);
        checkPositiveStatusCode(response);
        return gson.fromJson(responseToString(response), new TypeToken<List<Issue>>() {}.getType());
    }

    /**
     * This method sends a DELETE request to the server to delete the given issue and checks the response status code.
     * If the status code is positive, it means the issue was successfully deleted from the server.
     *
     * @param issue The issue to be deleted
     */
    @Step
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
    @Step
    public static Issue updateIssue(Issue issue, Map<String, String> updates) {
        updates.forEach(requestSpecification::queryParam);
        Response response = updateIssueRequest(issue);
        checkPositiveStatusCode(response);
        return gson.fromJson(responseToString(response), Issue.class);
    }

    @Step
    public static Response updateIssueTitleWithNull(Issue issue) {
        requestSpecification.queryParam("title", (Object) null);
        return updateIssueRequest(issue);
    }

    /**
     * This method is responsible for cleaning up the test issues by deleting them.
     * It retrieves a list of issues by calling getIssues() method with the current project.
     * It then enters into a loop and checks if the list of issues is empty or not.
     * If not empty, it deletes the issues using RequestUtil.deleteIssue() method.
     * The loop continues until all issues are deleted from the project.
     */
    @Step
    public static void cleanupTestIssues() {
        List<Issue> issues = getIssues(getProject());
        while (!getIssues(getProject()).isEmpty()) {
            issues.forEach(RequestUtil::deleteIssue);
        }
    }

    /**
     * This method adds the private token header to the request specification with its value.
     * The private token is obtained from the configuration file.
     */
    @Step
    public static void addAuthorizationToken(String token) {
        RestAssured.requestSpecification.header(getPrivateToken(), token);
    }

    /**
     * This method removes the private token header from the request specification with its value.
     */
    @Step
    public static void removeAuthorizationToken() {
        ((RequestSpecificationImpl) RestAssured.requestSpecification).removeHeader(getPrivateToken());
    }

    /**
     * Returns a list of Rest-Assured requests to get all issues and a single issue by ID.
     *
     * @param issue the issue to retrieve
     * @return a list of Rest-Assured requests
     */
    public static List<Response> getListOfGetRequests(Issue issue) {
        return asList(getIssuesRequest(getProject()),
                getIssueRequest(issue));
    }

    /**
     * Returns a list of Rest-Assured requests to create, update, and delete an issue.
     *
     * @param issue the issue to perform the requests on
     * @return a list of Rest-Assured requests
     */
    public static List<Response> getListOfDeletePostAndPutRequests(Issue issue) {
        return asList(createIssueRequest(issue),
                updateIssueRequest(issue),
                deleteIssueRequest(issue));
    }

}