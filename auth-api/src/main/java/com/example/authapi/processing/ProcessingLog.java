package com.example.authapi.processing;

import java.time.Instant;
import java.util.UUID;

import com.example.authapi.user.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

// Represents a record of a successfully processed request.
@Entity
@Table(name = "processing_log")
public class ProcessingLog {

    // Primary key for the processing log entry.
    @Id
    @GeneratedValue
    private UUID id;

    // Links the processing record to the user who made the request.
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Stores the original text received from the client.
    @Column(name = "input_text", nullable = false)
    private String inputText;

    // Stores the transformed result returned by data-api.
    @Column(name = "output_text", nullable = false)
    private String outputText;

    // Stores the time when the processing record was created.
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    // Required by JPA for entity creation.
    public ProcessingLog() {
    }

    public ProcessingLog(User user, String inputText, String outputText, Instant createdAt) {
        this.user = user;
        this.inputText = inputText;
        this.outputText = outputText;
        this.createdAt = createdAt;
    }

    public UUID getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public String getInputText() {
        return inputText;
    }

    public String getOutputText() {
        return outputText;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}