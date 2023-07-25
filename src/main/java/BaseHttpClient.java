import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.config;
import static io.restassured.RestAssured.given;

public class BaseHttpClient {
    private final String JSON = "application/json";

    public ValidatableResponse doDeleteRequest(String url) {
        return given()
                .header("Content-Type", JSON)
                .delete(url).then();
    }

    public ValidatableResponse getRequest(String endpoint, Object body){
        return given().config(config)
                .header("Content-Type", JSON)
                .body(body)
                .get(endpoint).then().log().all();
    }
    protected ValidatableResponse postRequest(String endpoint, Object body) {
        return given()
                .header("Content-type", JSON)
                .body(body).log().all()
                .when()
                .post(endpoint).then().log().all();
    }
}
