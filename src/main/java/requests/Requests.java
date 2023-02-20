package requests;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;

import static config.Config.getBaseUrl;

public class Requests {

    static {
        RestAssured.baseURI = getBaseUrl();
        RestAssured.useRelaxedHTTPSValidation();
        RestAssured.requestSpecification = new RequestSpecBuilder()
                .build()
                .header();
    }
}
