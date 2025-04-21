package com.mirza.simplysocial.model;

public class LoginResponse {
    private boolean success;
    private String message;
    private String sessionToken;

    public LoginResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
    public LoginResponse(boolean success, String message, String sessionToken) {
        this.success = success;
        this.message = message;
        this.sessionToken = sessionToken;
    }
    public boolean isSuccess() {
        return success;
    }
    public String getMessage() {
        return message;
    }
    public String getSessionToken() {
        return sessionToken;
    }
}
