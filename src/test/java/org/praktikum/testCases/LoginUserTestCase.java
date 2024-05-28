package org.praktikum.testCases;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.praktikum.api.UserApi;
import org.praktikum.dataObjects.User;
import org.praktikum.specs.RequestSpec;
import org.praktikum.specs.ResponseSpec;
import org.praktikum.utils.TestDataGenerator;

@Epic("Praktikum Sprint 8")
public class LoginUserTestCase {

    private String userToken;

    private UserApi userApi;

    private final String EMAIL = TestDataGenerator.randomEmail();

    private final String NAME = TestDataGenerator.randomName();

    private final String PASSWORD = "GNC{9MDdHr2vt";

    @Before
    public void setUp() {
        RequestSpecification defaultSpec = RequestSpec.getRequestSpec();
        ResponseSpecification defaultResponseSpec = ResponseSpec.getDefaultResponseSpec();
        ResponseSpecification resp202RespocseSpec = ResponseSpec.getResp202ResponseSpec();
        ResponseSpecification error401ResponseSpec = ResponseSpec.getError401ResponseSpec();
        ResponseSpecification error403ResponseSpec = ResponseSpec.getError403ResponseSpec();
        userApi = new UserApi(defaultSpec, defaultResponseSpec, resp202RespocseSpec,
                error401ResponseSpec, error403ResponseSpec);

        User user = User.builder()
                .email(EMAIL)
                .password(PASSWORD)
                .name(NAME)
                .build();
        userToken = userApi.createUser(user.getEmail(),user.getPassword(),user.getName());
    }

    @Test
    @DisplayName("Авторизацая пользователя")
    @Description("Тест проверки успешной и не успешной авторизации пользователя")
    public void authorizationUser(){
        autorizationWithCorrectUser();
        authorizationWithIncorrectEmail();
        authorizationWithIncorrectPassword();

    }

    @Step
    public void autorizationWithCorrectUser(){
        User user = User.builder()
                .email(EMAIL)
                .password(PASSWORD)
                .name(NAME)
                .build();
        userApi.loginUser(user.getEmail(),user.getPassword(),user.getName());
    }
    @Step
    public void authorizationWithIncorrectEmail(){
        String INCORRECT_EMAIL = "test123@ya.ru";
        User user = User.builder()
                .email(INCORRECT_EMAIL)
                .password(PASSWORD)
                .name(NAME)
                .build();
        userApi.loginUserIncorrectEmail(user.getEmail(),user.getPassword(),user.getName());
    }

   @Step
    public void authorizationWithIncorrectPassword(){
       String INCORRECT_PASSWORD = "GNC{9MDdHr2vt0000000";
       User user = User.builder()
                .email(EMAIL)
                .password(INCORRECT_PASSWORD)
                .name(NAME)
                .build();
        userApi.loginUserIncorrectPassword(user.getEmail(),user.getPassword(),user.getName());
    }



    @After
    public void tearDown(){
        if (userToken != null){
            userApi.deleteUser(userToken);
        }
    }
}
