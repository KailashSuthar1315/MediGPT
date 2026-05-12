package com.localai.assistant.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class ChatSession{

    private java.time.LocalDateTime updatedAt;

    public java.time.LocalDateTime getUpdatedAt() {
    return updatedAt;
}

public void setUpdatedAt(java.time.LocalDateTime updatedAt) {
    this.updatedAt = updatedAt;
}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    // getters and setters
    public Long getId() { return id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public LocalDateTime getCreatedAt() { return createdAt; }
}