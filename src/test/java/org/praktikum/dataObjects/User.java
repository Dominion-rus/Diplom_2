package org.praktikum.dataObjects;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {
    private final String email;

    private final String password;

    private final String name;

    private final String login;
}
