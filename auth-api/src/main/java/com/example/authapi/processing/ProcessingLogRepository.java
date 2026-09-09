package com.example.authapi.processing;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

// Provides database operations for processing log records.
public interface ProcessingLogRepository extends JpaRepository<ProcessingLog, UUID> {
}