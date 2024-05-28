package org.praktikum.testCases;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.praktikum.api.OrderApi;
import org.praktikum.api.UserApi;
import org.praktikum.dataObjects.User;
import org.praktikum.specs.RequestSpec;
import org.praktikum.specs.ResponseSpec;
import org.praktikum.utils.TestDataGenerator;

import java.util.List;

@Epic("Praktikum Sprint 8")
public class GetOrderTestCase {

    private String userToken;

    private UserApi userApi;

    private OrderApi orderApi;

    private final String EMAIL = TestDataGenerator.randomEmail();

    private final String NAME = TestDataGenerator.randomName();


    @Before
    public void setUp() {
        RequestSpecification defaultSpec = RequestSpec.getRequestSpec();
        ResponseSpecification defaultResponseSpec = ResponseSpec.getDefaultResponseSpec();
        ResponseSpecification error401ResponseSpec = ResponseSpec.getError401ResponseSpec();
        ResponseSpecification error400ResponseSpec = ResponseSpec.getError400ResponseSpec();
        ResponseSpecification error403ResponseSpec = ResponseSpec.getError403ResponseSpec();
        ResponseSpecification resp202ResponseSpec = ResponseSpec.getResp202ResponseSpec();
        ResponseSpecification error500ResponseSpec = ResponseSpec.getError500ResponseSpec();
        userApi = new UserApi(defaultSpec, defaultResponseSpec, resp202ResponseSpec,
                error401ResponseSpec, error403ResponseSpec);
        String PASSWORD = "GNC{9MDdHr2vt";
        User user = User.builder()
                .email(EMAIL)
                .password(PASSWORD)
                .name(NAME)
                .build();
        userToken = userApi.createUser(user.getEmail(),user.getPassword(),user.getName());
        orderApi = new OrderApi(defaultSpec, defaultResponseSpec, error400ResponseSpec,
                error500ResponseSpec, error401ResponseSpec);
        List<String> ingredientsList = orderApi.getIngredientList();
        orderApi.createOrder(ingredientsList,userToken);
    }


    @Test
    @DisplayName("Создание заказа")
    @Description("Тест создания заказа авторизованным пользователем")
    public void checkGetOrderAuthUser(){
        orderApi.getUserWithTokenOrder(userToken);
    }

    @Test
    @DisplayName("Создание заказа")
    @Description("Тест создания заказа авторизованным пользователем")
    public void checkGetOrderNotAuthUser(){
        String INCORRECT_TOKEN = "TOKEN";
        orderApi.getUserWithoutTokenOrder(INCORRECT_TOKEN);
    }



    @After
    public void tearDown(){
        if (userToken != null){
            userApi.deleteUser(userToken);
        }
    }
}
