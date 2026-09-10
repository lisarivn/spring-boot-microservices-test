package com.example.dataapi.transform.dto;

import jakarta.validation.constraints.NotBlank;

// Request body containing text to transform.
public record TransformRequest(

        @NotBlank
        String text
) {
}