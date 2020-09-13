package org.code.model;

public class LoginResponse {
    private final String token;

    public String getToken() {
        return token;
    }

    public LoginResponse(String token) {
        this.token = token;
    }
}
