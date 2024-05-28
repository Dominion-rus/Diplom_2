package org.praktikum.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

public class JsonUtils {
    private static ObjectMapper objectMapper = new ObjectMapper();

    private JsonUtils() {
        throw new AssertionError();
    }

    @SneakyThrows(JsonProcessingException.class)
    public static String objectToJson(Object object) {
        return objectMapper.writeValueAsString(object);
    }
}
