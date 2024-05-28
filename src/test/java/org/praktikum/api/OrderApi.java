package org.praktikum.api;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.praktikum.dataObjects.Order;
import org.praktikum.dataObjects.User;
import org.praktikum.enums.Endpoint;
import org.praktikum.enums.HttpMethod;
import org.praktikum.utils.JsonUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class OrderApi {
    private RequestSpecification defaultSpec;
    private ResponseSpecification defaultResponseSpec;

    private ResponseSpecification error400ResponseSpec;

    private ResponseSpecification error500ResponseSpec;

    private ResponseSpecification error401ResponseSpec;


    public OrderApi(RequestSpecification defaultSpec, ResponseSpecification defaultResponseSpec,
                   ResponseSpecification error400ResponseSpec,
                   ResponseSpecification error500ResponseSpec, ResponseSpecification error401ResponseSpec ){

        this.defaultSpec = defaultSpec;
        this.defaultResponseSpec = defaultResponseSpec;
        this.error400ResponseSpec = error400ResponseSpec;
        this.error500ResponseSpec = error500ResponseSpec;
        this.error401ResponseSpec = error401ResponseSpec;

    }

    public List<String> getIngredientList(){
        Response response = given()
                .spec(defaultSpec)
                .when()
                .request(HttpMethod.GET.name(), Endpoint.GET_INGREDIENTS.getPath())
                .then()
                .spec(defaultResponseSpec)
                .extract().response();
        response.then()
                .body("success", equalTo(true));

        ObjectMapper objectMapper = new ObjectMapper();
        List<String> idList = new ArrayList<>();

        try {
            String responseBody = response.getBody().asString();
            JsonNode rootNode = objectMapper.readTree(responseBody);
            JsonNode dataNode = rootNode.path("data");

            if (dataNode.isArray()) {
                for (JsonNode node : dataNode) {
                    String id = node.path("_id").asText();
                    idList.add(id);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return idList;
    }

    public void createOrder(List<String> ingredientIds,String accessToken){
        Order variables = Order.builder()
                .ingredients(ingredientIds)
                .build();
        String userData = JsonUtils.objectToJson(variables);
        given()
                .spec(defaultSpec)
                .header("Authorization", accessToken)
                .body(userData)
                .when()
                .request(HttpMethod.POST.name(), Endpoint.ORDERS.getPath())
                .then()
                .spec(defaultResponseSpec)
                .body("success", equalTo(true));

    }

    public void createOrderNoIngredients(List<String> ingredientIds,String accessToken){
        Order variables = Order.builder()
                .ingredients(ingredientIds)
                .build();
        String userData = JsonUtils.objectToJson(variables);
        given()
                .spec(defaultSpec)
                .header("Authorization", accessToken)
                .body(userData)
                .when()
                .request(HttpMethod.POST.name(), Endpoint.ORDERS.getPath())
                .then()
                .spec(error400ResponseSpec)
                .body("success", equalTo(false))
                .body("message", equalTo("Ingredient ids must be provided"));

    }

    public void createOrderWithWrongHash(List<String> ingredientIds,String accessToken){
        Order variables = Order.builder()
                .ingredients(ingredientIds)
                .build();
        String userData = JsonUtils.objectToJson(variables);
        given()
                .spec(defaultSpec)
                .header("Authorization", accessToken)
                .body(userData)
                .when()
                .request(HttpMethod.POST.name(), Endpoint.ORDERS.getPath())
                .then()
                .spec(error500ResponseSpec);

    }


    public void getUserWithTokenOrder(String accessToken){
        given()
                .spec(defaultSpec)
                .header("Authorization", accessToken)
                .when()
                .request(HttpMethod.GET.name(), Endpoint.ORDERS.getPath())
                .then()
                .spec(defaultResponseSpec)
                .body("success", equalTo(true));

    }

    public void getUserWithoutTokenOrder(String accessToken){
        given()
                .spec(defaultSpec)
                .header("Authorization", accessToken)
                .when()
                .request(HttpMethod.GET.name(), Endpoint.ORDERS.getPath())
                .then()
                .spec(error401ResponseSpec)
                .body("success", equalTo(false));

    }




}
