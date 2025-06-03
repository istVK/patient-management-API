package com.healthcare.patientmanagementapi.DTO.Auth;

public class AuthResponse {

    private String token;

    // Constructors
    public AuthResponse() {}

    public AuthResponse(String token) {
        this.token = token;
    }


    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
}
