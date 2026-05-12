package com.localai.assistant.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String prompt;

    @Column(columnDefinition = "TEXT")
    private String response;

    private LocalDateTime timestamp;

    @ManyToOne
    @JoinColumn(name = "session_id")
    private ChatSession session;

    @PrePersist
    protected void onCreate() {
        timestamp = LocalDateTime.now();
    }

    // getters and setters
    public Long getId() { return id; }

    public String getPrompt() { return prompt; }
    public void setPrompt(String prompt) { this.prompt = prompt; }

    public String getResponse() { return response; }
    public void setResponse(String response) { this.response = response; }

    public LocalDateTime getTimestamp() { return timestamp; }

    public ChatSession getSession() { return session; }
    public void setSession(ChatSession session) { this.session = session; }
}