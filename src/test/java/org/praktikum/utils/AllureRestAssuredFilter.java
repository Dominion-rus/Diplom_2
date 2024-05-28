package org.praktikum.utils;

import io.qameta.allure.Allure;
import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;


public class AllureRestAssuredFilter implements Filter {
    @Override
    public Response filter(FilterableRequestSpecification requestSpec,
                           FilterableResponseSpecification responseSpec,
                           FilterContext filterContext) {

        String dateTimePattern = "yyyy-MM-dd HH:mm:ss";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateTimePattern);
        String currentDateTime = LocalDateTime.now(ZoneId.of("Europe/Moscow")).format(formatter);


        Response response = filterContext.next(requestSpec, responseSpec);

        if (requestSpec.getBody() != null) {
            String body = requestSpec.getBody().toString();
            if (!body.trim().isEmpty()) {
                Allure.addAttachment("Timestamp: " + currentDateTime + " | Request", body);
            }
        } else {
            Allure.addAttachment("Timestamp: " + currentDateTime + " | Request", "No request body.");
        }

        Allure.addAttachment("Timestamp: " + currentDateTime + " | Response", response.getBody().asString());

        return response;
    }


}
