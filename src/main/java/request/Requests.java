package request;

import com.google.gson.Gson;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;

import model.Issue;

import static config.Config.*;
import static io.restassured.RestAssured.expect;
import static org.hamcrest.Matchers.lessThan;

public class Requests {

    protected static final Gson gson = new Gson();

    static {
        RestAssured.baseURI = getBaseUrl();
        RestAssured.useRelaxedHTTPSValidation();
        RestAssured.responseSpecification = expect().time(lessThan(5000L));
        RestAssured.requestSpecification = new RequestSpecBuilder()
                .build()
                .header(getPrivateToken(), getPrivateTokenValue())
                .header(getContentType(), getContentTypeValue());
    }

    /**
     * The method sends a GET request to get a list of issues with for specified projectId.
     *
     * @param projectId The id of project which issues method tries to get.
     * @return The response of the REST API call.
     */
    @Step
    protected static Response getIssuesRequest(int projectId) {
        return RestAssured
                .given()
                .get("projects/" + projectId + "/issues");
    }

    /**
     * The method sends a POST request to create an issue with the specified projectId and Iid.
     *
     * @param issue The issue object to be updated.
     * @return The response of the REST API call.
     */
    @Step
    protected static Response createIssueRequest(Issue issue) {

        return RestAssured
                .given()
                .body(gson.toJson(issue))
                .post("projects/" + issue.getProjectId() + "/issues");
    }

    /**
     * The method sends a DELETE request to delete an issue with the specified projectId and Iid.
     *
     * @param issue The issue object to be updated.
     * @return The response of the REST API call.
     */
    @Step
    protected static Response deleteIssueRequest(Issue issue) {

        return RestAssured
                .given()
                .body(gson.toJson(issue))
                .delete("projects/" + issue.getProjectId() + "/issues/" + issue.getIid());
    }

    /**
     * The method sends a GET request to receive an issue with the specified projectId and Iid.
     *
     * @param issue The issue object to be updated.
     * @return The response of the REST API call.
     */
    @Step
    public static Response getIssueRequest(Issue issue) {
        return RestAssured
                .given()
                .get("projects/" + issue.getProjectId() + "/issues/" + issue.getIid());
    }

    /**
     * The method sends a PUT request to update an issue with the specified projectId and Iid.
     *
     * @param issue The issue object to be updated.
     * @return The response of the REST API call.
     */
    @Step
    protected static Response updateIssueRequest(Issue issue) {
        return RestAssured
                .given()
                .put("projects/" + issue.getProjectId() + "/issues/" + issue.getIid());
    }

}