package org.praktikum.specs;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.praktikum.utils.AllureRestAssuredFilter;


public class RequestSpec {
    private RequestSpec() {
        throw new AssertionError();
    }

    static {

        RestAssured.requestSpecification = new RequestSpecBuilder()
                .setBaseUri("https://stellarburgers.nomoreparties.site")
                .setContentType(ContentType.JSON)
                .addFilter(new AllureRestAssuredFilter())
                .build();
    }

    public static RequestSpecification getRequestSpec() {
        return RestAssured.given()
                .basePath("/api");
    }


}
