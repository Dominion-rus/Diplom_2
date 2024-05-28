package org.praktikum.enums;

import org.praktikum.interfaces.UrlPath;

public enum Endpoint implements UrlPath {
    POST_AUTH_REGISTER("/auth/register"),
    POST_DELETE_USER("/auth/user"),
    POST_LOGIN_USER("/auth/login"),
    PATCH_CHANGE_USER("/auth/user"),
    GET_INGREDIENTS("/ingredients"),
    ORDERS("/orders");

    private final String path;

    Endpoint(String path) {this.path = path;}

    @Override
    public String getPath() {
        return path;
    }
}
