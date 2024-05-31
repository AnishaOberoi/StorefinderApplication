package com.example.storefinderbackend.response;

public class AuthResponse {
    private String token;
    private String message;

    private String role;

    private String email;

    private String username;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public AuthResponse(String token, String message, String role, String email, String username){
        super();
        this.token=token;
        this.message=message;
        this.role = role;
        this.email = email;
        this.username=username;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
