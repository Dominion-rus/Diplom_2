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
public class CreateUserTestCase {

    private String userToken;

    private UserApi userApi;

   private final String EMAIL = TestDataGenerator.randomEmail();

   private final String NAME = TestDataGenerator.randomName();

   private final String PASSWORD = "GNC{9MDdHr2vt";


    @Before
    public void setUp() {
        RequestSpecification defaultSpec = RequestSpec.getRequestSpec();
        ResponseSpecification defaultResponseSpec = ResponseSpec.getDefaultResponseSpec();
        ResponseSpecification resp202ResponseSpec = ResponseSpec.getResp202ResponseSpec();
        ResponseSpecification error401ResponseSpec = ResponseSpec.getError401ResponseSpec();
        ResponseSpecification error403ResponseSpec = ResponseSpec.getError403ResponseSpec();
        userApi = new UserApi(defaultSpec, defaultResponseSpec, resp202ResponseSpec,
                error401ResponseSpec, error403ResponseSpec);
    }

    @Test
    @DisplayName("Создание user в системе  и проверка повторнгого создания ")
    @Description("Тест успешного Создание user в системе и проверка повторнгого создания")
    public void checkRequestPostCorrectCreateUser(){
        checkRequestPostCreateUser();
        checkRequestPostCannotCreateTwoIdenticalCourier();
    }
    @Step("Создание user в системе")
    public void checkRequestPostCreateUser(){
        User user = User.builder()
                .email(EMAIL)
                .password(PASSWORD)
                .name(NAME)
                .build();
        userToken = userApi.createUser(user.getEmail(),user.getPassword(),user.getName());
        System.out.println(userToken);
    }
    @Step(("Нельзя создать двух одинаковых user в системе"))
    public void checkRequestPostCannotCreateTwoIdenticalCourier(){
        User user = User.builder()
                .email(EMAIL)
                .password(PASSWORD)
                .name(NAME)
                .build();
        userApi.creatingUserTwice(user.getEmail(),user.getPassword(),user.getName());

    }


    @Test
    @DisplayName("Запрос без имени пользователя")
    @Description("Тест проверки того,что если в запросе нет имени пользователя, запрос возвращает ошибку 403")
    public void checkRequestPostСreatingAnUnnamedUser() {
        User user = User.builder()
                .email(EMAIL)
                .password(PASSWORD)
                .build();
        userApi.creatingUserWithoutAName(user.getEmail(),user.getPassword());
    }

    @Test
    @DisplayName("Запрос без пароля")
    @Description("Тест проверки того,что если в запросе нет пароля, запрос возвращает ошибку 403")
    public void checkRequestPostСreatingUserWithoutAPassword() {
        User user = User.builder()
                .email(EMAIL)
                .name(NAME)
                .build();
        userApi.creatingUserWithoutAPassword(user.getEmail(),user.getName());
    }

    @Test
    @DisplayName("Запрос без почты")
    @Description("Тест проверки того,что если в запросе нет почты, запрос возвращает ошибку 403")
    public void checkRequestPostСreatingUserWithoutAEmail() {
        User user = User.builder()
                .name(NAME)
                .password(PASSWORD)
                .build();
        userApi.creatingUserWithoutAEmail(user.getPassword(), user.getName());
    }



    @After
    public void tearDown(){
        if (userToken != null){
            userApi.deleteUser(userToken);
        }
    }
}
