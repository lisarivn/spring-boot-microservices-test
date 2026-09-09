package com.example.authapi.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

// Request body used for user login.
public record LoginRequest(

        @Email
        @NotBlank
        String email,

        @NotBlank
        String password
) {
}