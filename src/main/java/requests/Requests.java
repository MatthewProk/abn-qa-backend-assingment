package requests;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;

import static config.Config.*;

public class Requests {

    static {
        RestAssured.baseURI = getBaseUrl();
        RestAssured.useRelaxedHTTPSValidation();
        RestAssured.requestSpecification = new RequestSpecBuilder()
                .build()
                .header(getPrivateToken(), getPrivateTokenValue())
                .header(getContentType(), getContentTypeValue());
    }

}
