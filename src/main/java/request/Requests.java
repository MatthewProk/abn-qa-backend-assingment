package request;

import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import model.Issue;

import static config.Config.*;

public class Requests {

    protected static final Gson gson = new Gson();

    static {
        RestAssured.baseURI = getBaseUrl();
        RestAssured.useRelaxedHTTPSValidation();
        RestAssured.requestSpecification = new RequestSpecBuilder()
                .build()
                .header(getPrivateToken(), getPrivateTokenValue())
                .header(getContentType(), getContentTypeValue());
    }

    protected static Response getIssuesRequest() {
        return RestAssured
                .given()
                .get("issues");
    }


    protected static Response createIssueRequest(Issue issue) {

        return RestAssured
                .given()
                .body(gson.toJson(issue))
                .post("projects/" + issue.getProjectId() + "/issues");
    }


    protected static Response deleteIssueRequest(Issue issue) {

        return RestAssured
                .given()
                .body(gson.toJson(issue))
                .delete("projects/" + issue.getProjectId() + "/issues/" + issue.getIid());
    }


    public static Response getIssueRequest(Issue issue) {
        return RestAssured
                .given()
                .get("projects/" + issue.getProjectId() + "/issues/" + issue.getIid());
    }
}
