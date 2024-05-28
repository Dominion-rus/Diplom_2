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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Epic("Praktikum Sprint 8")
public class CreateOrderTestCase {

    private String userToken;

    private UserApi userApi;

    private OrderApi orderApi;

    private final String EMAIL = TestDataGenerator.randomEmail();

    private final String NAME = TestDataGenerator.randomName();

    private List<String> ingredientsList;

    private final List<String> emptyList = Collections.emptyList();

    private final List<String> WRONG_HASH = Arrays.asList("61c0c5a71d1f82001bdaaa6fTEST");


    @Before
    public void setUp() {
        RequestSpecification defaultSpec = RequestSpec.getRequestSpec();
        ResponseSpecification defaultResponseSpec = ResponseSpec.getDefaultResponseSpec();
        ResponseSpecification error401ResponseSpec = ResponseSpec.getError401ResponseSpec();
        ResponseSpecification error400ResponseSpec = ResponseSpec.getError400ResponseSpec();
        ResponseSpecification error403ResponseSpec = ResponseSpec.getError403ResponseSpec();
        ResponseSpecification resp202RespocseSpec = ResponseSpec.getResp202ResponseSpec();
        ResponseSpecification error500ResponseSpec = ResponseSpec.getError500ResponseSpec();
        userApi = new UserApi(defaultSpec, defaultResponseSpec, resp202RespocseSpec,
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
        ingredientsList = orderApi.getIngredientList();
    }


    @Test
    @DisplayName("Создание заказа")
    @Description("Тест создания заказа авторизованным пользователем")
    public void checkCreateOrderAuthUser(){
        orderApi.createOrder(ingredientsList,userToken);
    }

    @Test
    @DisplayName("Создание заказа не авторизованным пользователем")
    @Description("Тест создания заказа не авторизованным пользователем")
    public void checkCreateOrderNotAuthUser(){
        String INCORRECT_TOKEN = "TOKEN";
        orderApi.createOrder(ingredientsList, INCORRECT_TOKEN);
    }

    @Test
    @DisplayName("Создание заказа без ингредиентов")
    @Description("Тест создания заказа без ингредиентов авторизованным пользователем")
    public void checkCreateOrderNoIngredients(){
        orderApi.createOrderNoIngredients(emptyList,userToken);
    }

    @Test
    @DisplayName("Создание заказа с неверным хешем ингредиентов")
    @Description("Тест создания заказа с неверным хешем ингредиентов авторизованным пользователем")
    public void checkCreateOrderWithWrongHash(){
        orderApi.createOrderWithWrongHash(WRONG_HASH,userToken);
    }

    @After
    public void tearDown(){
        if (userToken != null){
            userApi.deleteUser(userToken);
        }
    }
}
