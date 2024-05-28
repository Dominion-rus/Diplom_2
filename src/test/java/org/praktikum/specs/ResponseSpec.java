package org.praktikum.specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.ResponseSpecification;

public class ResponseSpec {

    private static final ResponseSpecification DEFAULT_RESPONSE_SPEC = new ResponseSpecBuilder()
            .expectContentType("application/json;")
            .expectStatusCode(200)
            .build();

    private static final ResponseSpecification RESP_202_RESPONSE_SPEC = new ResponseSpecBuilder()
            .expectContentType("application/json;")
            .expectStatusCode(202)
            .build();

    private static final ResponseSpecification ERROR_401_RESPONSE_SPEC = new ResponseSpecBuilder()
            .expectContentType("application/json;")
            .expectStatusCode(401)
            .build();

    private static final ResponseSpecification ERROR_403_RESPONSE_SPEC = new ResponseSpecBuilder()
            .expectContentType("application/json;")
            .expectStatusCode(403)
            .build();

    private static final ResponseSpecification ERROR_400_RESPONSE_SPEC = new ResponseSpecBuilder()
            .expectContentType("application/json;")
            .expectStatusCode(400)
            .build();

    private static final ResponseSpecification ERROR_500_RESPONSE_SPEC = new ResponseSpecBuilder()
            .expectContentType("text/html; charset=utf-8")
            .expectStatusCode(500)
            .build();

    private ResponseSpec() {
        throw new AssertionError();
    }

    public static ResponseSpecification getDefaultResponseSpec() {
        return DEFAULT_RESPONSE_SPEC;
    }

    public static ResponseSpecification getError401ResponseSpec() {
        return ERROR_401_RESPONSE_SPEC;
    }

    public static ResponseSpecification getError403ResponseSpec() {
        return ERROR_403_RESPONSE_SPEC;
    }

    public static ResponseSpecification getResp202ResponseSpec() {
        return RESP_202_RESPONSE_SPEC;
    }

    public static ResponseSpecification getError400ResponseSpec() {
        return ERROR_400_RESPONSE_SPEC;
    }

    public static ResponseSpecification getError500ResponseSpec() {
        return ERROR_500_RESPONSE_SPEC;
    }






}
