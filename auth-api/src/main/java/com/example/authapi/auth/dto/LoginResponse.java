package com.example.authapi.auth.dto;

// Response body returned after successful login.
public record LoginResponse(String token) {
}