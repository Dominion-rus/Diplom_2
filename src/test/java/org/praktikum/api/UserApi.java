package org.praktikum.api;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.praktikum.dataObjects.User;
import org.praktikum.enums.Endpoint;
import org.praktikum.enums.HttpMethod;
import org.praktikum.utils.JsonUtils;

import static io.restassured.RestAssured.given;


import static org.hamcrest.Matchers.equalTo;

public class UserApi {

    private RequestSpecification defaultSpec;
    private ResponseSpecification defaultResponseSpec;

    private ResponseSpecification error401ResponseSpec;

    private ResponseSpecification error403ResponseSpec;

    private ResponseSpecification resp202ResponseSpec;


public UserApi(RequestSpecification defaultSpec, ResponseSpecification defaultResponseSpec,
               ResponseSpecification resp202ResponseSpec,
               ResponseSpecification error401ResponseSpec, ResponseSpecification error403ResponseSpec ){

    this.defaultSpec = defaultSpec;
    this.defaultResponseSpec = defaultResponseSpec;
    this.error401ResponseSpec = error401ResponseSpec;
    this.error403ResponseSpec = error403ResponseSpec;
    this.resp202ResponseSpec = resp202ResponseSpec;
}



    public  String createUser (String email, String password, String name){
        User variables = User.builder()
                .email(email)
                .password(password)
                .name(name)
                .build();
        String userData = JsonUtils.objectToJson(variables);
        Response response = given()
                .spec(defaultSpec)
                .body(userData)
                .when()
                .request(HttpMethod.POST.name(), Endpoint.POST_AUTH_REGISTER.getPath())
                .then()
                .spec(defaultResponseSpec)
                .extract().response();
        response.then()
                .body("success", equalTo(true));

        return response
                .then()
                .extract()
                .jsonPath()
                .getString("accessToken");

    }

    public void creatingUserTwice(String email, String password, String name) {
        User variables = User.builder()
                .email(email)
                .password(password)
                .name(name)
                .build();
        String userData = JsonUtils.objectToJson(variables);
        given()
                .spec(defaultSpec)
                .body(userData)
                .when()
                .request(HttpMethod.POST.name(), Endpoint.POST_AUTH_REGISTER.getPath())
                .then()
                .spec(error403ResponseSpec)
                .body("success", equalTo(false));
    }

    public void creatingUserWithoutAPassword(String email, String name){
        User variables = User.builder()
                .email(email)
                .name(name)
                .build();
        String userData = JsonUtils.objectToJson(variables);
        given()
                .spec(defaultSpec)
                .body(userData)
                .when()
                .request(HttpMethod.POST.name(), Endpoint.POST_AUTH_REGISTER.getPath())
                .then()
                .spec(error403ResponseSpec)
                .body("success", equalTo(false));
    }

    public void creatingUserWithoutAEmail(String password, String name){
        User variables = User.builder()
                .password(password)
                .name(name)
                .build();
        String userData = JsonUtils.objectToJson(variables);
        given()
                .spec(defaultSpec)
                .body(userData)
                .when()
                .request(HttpMethod.POST.name(), Endpoint.POST_AUTH_REGISTER.getPath())
                .then()
                .spec(error403ResponseSpec)
                .body("success", equalTo(false));
    }

    public void creatingUserWithoutAName(String email, String password){
        User variables = User.builder()
                .password(password)
                .email(email)
                .build();
        String userData = JsonUtils.objectToJson(variables);
        given()
                .spec(defaultSpec)
                .body(userData)
                .when()
                .request(HttpMethod.POST.name(), Endpoint.POST_AUTH_REGISTER.getPath())
                .then()
                .spec(error403ResponseSpec)
                .body("success", equalTo(false));
    }

    public void loginUser(String email, String password, String name) {
        User variables = User.builder()
                .email(email)
                .password(password)
                .name(name)
                .build();
        String userData = JsonUtils.objectToJson(variables);
        given()
                .spec(defaultSpec)
                .body(userData)
                .when()
                .request(HttpMethod.POST.name(), Endpoint.POST_LOGIN_USER.getPath())
                .then()
                .spec(defaultResponseSpec)
                .body("success", equalTo(true));
    }

    public void loginUserIncorrectEmail(String email, String password, String name) {
        User variables = User.builder()
                .email(email)
                .password(password)
                .name(name)
                .build();
        String userData = JsonUtils.objectToJson(variables);
        given()
                .spec(defaultSpec)
                .body(userData)
                .when()
                .request(HttpMethod.POST.name(), Endpoint.POST_LOGIN_USER.getPath())
                .then()
                .spec(error401ResponseSpec)
                .body("success", equalTo(false));
    }

    public void loginUserIncorrectPassword(String email, String password, String name) {
        User variables = User.builder()
                .email(email)
                .password(password)
                .name(name)
                .build();
        String userData = JsonUtils.objectToJson(variables);
        given()
                .spec(defaultSpec)
                .body(userData)
                .when()
                .request(HttpMethod.POST.name(), Endpoint.POST_LOGIN_USER.getPath())
                .then()
                .spec(error401ResponseSpec)
                .body("success", equalTo(false));
    }
    public void changeUserWithToken (String email, String password, String name, String accessToken) {
        User variables = User.builder()
                .email(email)
                .password(password)
                .name(name)
                .build();
        String userData = JsonUtils.objectToJson(variables);
        given()
                .spec(defaultSpec)
                .header("Authorization", accessToken)
                .body(userData)
                .when()
                .request(HttpMethod.PATCH.name(), Endpoint.PATCH_CHANGE_USER.getPath())
                .then()
                .spec(defaultResponseSpec)
                .body("success", equalTo(true));
    }

    public void changeUserWithoutToken (String email, String password, String name, String accessToken) {
        User variables = User.builder()
                .email(email)
                .password(password)
                .name(name)
                .build();
        String userData = JsonUtils.objectToJson(variables);
        given()
                .spec(defaultSpec)
                .header("Authorization", accessToken)
                .body(userData)
                .when()
                .request(HttpMethod.PATCH.name(), Endpoint.PATCH_CHANGE_USER.getPath())
                .then()
                .spec(error401ResponseSpec)
                .body("success", equalTo(false));
    }

    public void deleteUser (String accessToken) {
        given()
                .spec(defaultSpec)
                .header("Authorization", accessToken)
                .when()
                .request(HttpMethod.DELETE.name(), Endpoint.POST_DELETE_USER.getPath())
                .then()
                .spec(resp202ResponseSpec);
    }

}
