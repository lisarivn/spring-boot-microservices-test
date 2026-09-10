package com.example.dataapi.transform;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dataapi.transform.dto.TransformRequest;
import com.example.dataapi.transform.dto.TransformResponse;

import jakarta.validation.Valid;

// Handles internal text transformation requests.
@RestController
@RequestMapping("/api")
public class TransformController {

    private final String internalToken;

    public TransformController(@Value("${INTERNAL_TOKEN}") String internalToken) {
        this.internalToken = internalToken;
    }

    // Transforms text only when the internal token is valid.
    @PostMapping("/transform")
    public ResponseEntity<TransformResponse> transform(
            @RequestHeader(value = "X-Internal-Token", required = false) String token,
            @Valid @RequestBody TransformRequest request) {

        if (token == null || !internalToken.equals(token)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        String result = request.text().toUpperCase();

        return ResponseEntity.ok(new TransformResponse(result));
    }
}