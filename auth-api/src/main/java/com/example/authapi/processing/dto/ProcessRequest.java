package com.example.authapi.processing.dto;

import jakarta.validation.constraints.NotBlank;

// Request body for text processing.
public record ProcessRequest(

        @NotBlank
        String text
) {
}