package com.bitbyte.cargaraag.auth.models;

public class AuthorizationResponse {
    private String message;
    private String token;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public AuthorizationResponse(String message, String token) {
        this.message = message;
        this.token = token;
    }
}
