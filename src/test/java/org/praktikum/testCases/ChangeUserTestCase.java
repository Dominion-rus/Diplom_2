package org.praktikum.testCases;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
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
public class ChangeUserTestCase {
    private RequestSpecification defaultSpec;

    private ResponseSpecification defaultResponseSpec;

    private ResponseSpecification error401ResponseSpec;

    private ResponseSpecification error403ResponseSpec;

    private ResponseSpecification resp202RespocseSpec;

    private String userToken;

    private UserApi userApi;

    private final String EMAIL = TestDataGenerator.randomEmail();

    private final String NAME = TestDataGenerator.randomName();

    private final String PASSWORD = "GNC{9MDdHr2vt";

    private final String NEW_EMAIL = TestDataGenerator.randomEmail();

    private final String NEW_NAME = TestDataGenerator.randomName();

    private final String INCORRECT_TOKEN = "TOKEN";

    @Before
    public void setUp() {
        defaultSpec = RequestSpec.getRequestSpec();
        defaultResponseSpec = ResponseSpec.getDefaultResponseSpec();
        resp202RespocseSpec = ResponseSpec.getResp202ResponseSpec();
        error401ResponseSpec = ResponseSpec.getError401ResponseSpec();
        error403ResponseSpec = ResponseSpec.getError403ResponseSpec();
        userApi = new UserApi(defaultSpec,defaultResponseSpec,resp202RespocseSpec,
                error401ResponseSpec,error403ResponseSpec);
        User user = User.builder()
                .email(EMAIL)
                .password(PASSWORD)
                .name(NAME)
                .build();
        userToken = userApi.createUser(user.getEmail(),user.getPassword(),user.getName());
    }


    @Test
    @DisplayName("Изменение почты с корректным токеном")
    @Description("Тест изменения почты с корректным токеном")
    public void changeAuthorizedUserEmail(){
        User user = User.builder()
                .email(NEW_EMAIL)
                .password(PASSWORD)
                .name(NAME)
                .build();
        userApi.changeUserWithToken(user.getEmail(),user.getPassword(),user.getName(), userToken);
    }

    @Test
    @DisplayName("Изменение имени с корректным токеном")
    @Description("Тест изменения имени с корректным токеном")
    public void changeAuthorizedUserName(){
        User user = User.builder()
                .email(EMAIL)
                .password(PASSWORD)
                .name(NEW_NAME)
                .build();
        userApi.changeUserWithToken(user.getEmail(),user.getPassword(),user.getName(), userToken);
    }

    @Test
    @DisplayName("Изменение имени с некорректным токеном")
    @Description("Тест изменения имени с некорректным токеном")
    public void changeNotAuthorizedUserName(){
        User user = User.builder()
                .email(EMAIL)
                .password(PASSWORD)
                .name(NEW_NAME)
                .build();
        userApi.changeUserWithoutToken(user.getEmail(),user.getPassword(),user.getName(), INCORRECT_TOKEN);
    }

    @Test
    @DisplayName("Изменение почты с некорректным токеном")
    @Description("Тест изменения почты с некорректным токеном")
    public void changeNotAuthorizedUserEmail(){
        User user = User.builder()
                .email(NEW_EMAIL)
                .password(PASSWORD)
                .name(NAME)
                .build();
        userApi.changeUserWithoutToken(user.getEmail(),user.getPassword(),user.getName(), INCORRECT_TOKEN);
    }




    @After
    public void tearDown(){
        if (userToken != null){
            userApi.deleteUser(userToken);
        }
    }
}
