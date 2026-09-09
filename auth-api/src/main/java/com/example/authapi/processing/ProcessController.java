package com.example.authapi.processing;

import java.security.Principal;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.authapi.processing.dto.ProcessRequest;
import com.example.authapi.processing.dto.ProcessResponse;

import jakarta.validation.Valid;

// Exposes the protected processing endpoint.
@RestController
@RequestMapping("/api")
public class ProcessController {

    private final ProcessService processService;

    public ProcessController(ProcessService processService) {
        this.processService = processService;
    }

    // Processes text for the currently authenticated user.
    @PostMapping("/process")
    public ResponseEntity<ProcessResponse> process(
            @Valid @RequestBody ProcessRequest request,
            Principal principal) {

        ProcessResponse response =
                processService.process(principal.getName(), request.text());

        return ResponseEntity.ok(response);
    }
}