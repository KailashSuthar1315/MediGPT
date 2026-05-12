package com.localai.assistant.repository;
import java.util.List;
import com.localai.assistant.model.ChatSession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatSessionRepository extends JpaRepository<ChatSession, Long> {
    List<ChatSession> findAllByOrderByUpdatedAtDesc();
}