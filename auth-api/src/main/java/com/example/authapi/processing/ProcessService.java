package com.example.authapi.processing;

import java.time.Instant;

import org.springframework.stereotype.Service;

import com.example.authapi.client.DataApiClient;
import com.example.authapi.processing.dto.ProcessResponse;
import com.example.authapi.user.User;
import com.example.authapi.user.UserRepository;

// Handles protected text processing requests.
@Service
public class ProcessService {

    private final UserRepository userRepository;
    private final ProcessingLogRepository processingLogRepository;
    private final DataApiClient dataApiClient;

    public ProcessService(
            UserRepository userRepository,
            ProcessingLogRepository processingLogRepository,
            DataApiClient dataApiClient) {

        this.userRepository = userRepository;
        this.processingLogRepository = processingLogRepository;
        this.dataApiClient = dataApiClient;
    }

    // Sends input to data-api and stores the processing result in PostgreSQL.
    public ProcessResponse process(String email, String text) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        ProcessResponse response = dataApiClient.transform(text);

        ProcessingLog log = new ProcessingLog(
                user,
                text,
                response.result(),
                Instant.now()
        );

        processingLogRepository.save(log);

        return response;
    }
}