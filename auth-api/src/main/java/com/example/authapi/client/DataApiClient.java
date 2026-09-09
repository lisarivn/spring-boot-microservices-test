package com.example.authapi.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import com.example.authapi.processing.dto.ProcessResponse;

// Handles communication with the data-api service.
@Component
public class DataApiClient {

    private final RestClient restClient;
    private final String internalToken;

    public DataApiClient(
            RestClient.Builder restClientBuilder,
            @Value("${INTERNAL_TOKEN}") String internalToken) {

        this.restClient = restClientBuilder
                .baseUrl("http://data-api:8081")
                .build();

        this.internalToken = internalToken;
    }

    // Sends text to data-api and returns the transformed result.
    public ProcessResponse transform(String text) {

        return restClient.post()
                .uri("/api/transform")
                .header("X-Internal-Token", internalToken)
                .body(new TransformRequest(text))
                .retrieve()
                .body(ProcessResponse.class);
    }

    // Internal request DTO used only for communication with data-api.
    private record TransformRequest(String text) {
    }
}