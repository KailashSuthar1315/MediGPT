package com.localai.assistant.controller;

import com.localai.assistant.service.OllamaService;
import com.localai.assistant.model.ChatSession;
import com.localai.assistant.model.ChatMessage;
import com.localai.assistant.repository.ChatSessionRepository;
import com.localai.assistant.repository.ChatMessageRepository;
import java.util.Optional;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api") // Base path
public class ChatController {

    private final OllamaService ollamaService;
    private final ChatSessionRepository chatSessionRepository;
    private final ChatMessageRepository chatMessageRepository;

   public ChatController(OllamaService ollamaService,
                      ChatSessionRepository chatSessionRepository,
                      ChatMessageRepository chatMessageRepository) {
    this.ollamaService = ollamaService;
    this.chatSessionRepository = chatSessionRepository;
    this.chatMessageRepository = chatMessageRepository;
}

@PostMapping("/session")
public ChatSession createSession(@RequestParam String title) {

    ChatSession session = new ChatSession();
    session.setTitle(title);
    session.setUpdatedAt(java.time.LocalDateTime.now());

    return chatSessionRepository.save(session);
}

 @PostMapping(value = "/chat/{sessionId}", produces = "text/plain")
public ResponseEntity<org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody> chat(
        @PathVariable Long sessionId,
        @RequestBody ChatRequest request) {

    Optional<ChatSession> optionalSession = chatSessionRepository.findById(sessionId);

    if (optionalSession.isEmpty()) {
        return ResponseEntity.badRequest().build();
    }

    ChatSession session = optionalSession.get();

    session.setUpdatedAt(java.time.LocalDateTime.now());
    chatSessionRepository.save(session);
    

if (session.getTitle().equals("New Chat")) {
    String title = request.getPrompt();

    if (title.length() > 30) {
        title = title.substring(0, 30);
    }

    session.setTitle(title);
    chatSessionRepository.save(session);
}

    StringBuilder fullResponse = new StringBuilder();

    var stream = (org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody) outputStream -> {

        ollamaService.streamResponse(request.getPrompt(), chunk -> {
            try {
                fullResponse.append(chunk); // collect full text
                outputStream.write(chunk.getBytes());
                outputStream.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // After streaming completes, save to DB
        ChatMessage message = new ChatMessage();
        message.setPrompt(request.getPrompt());
        message.setResponse(fullResponse.toString());
        message.setSession(session);

        chatMessageRepository.save(message);
    };

    return ResponseEntity.ok(stream);
}

@GetMapping("/session/{sessionId}")
public List<ChatMessage> getMessages(@PathVariable Long sessionId) {
    return chatMessageRepository.findBySessionIdOrderByTimestampAsc(sessionId);
}

@GetMapping("/sessions")
public List<ChatSession> getAllSessions() {
    return chatSessionRepository.findAllByOrderByUpdatedAtDesc();
}

@DeleteMapping("/session/{id}")
public ResponseEntity<Void> deleteSession(@PathVariable Long id) {
    if (!chatSessionRepository.existsById(id)) {
        return ResponseEntity.notFound().build();
    }

    chatSessionRepository.deleteById(id);
    return ResponseEntity.ok().build();
}




   static class ChatRequest {
    private String prompt;

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }
}

static class ChatResponse {
    private String response;

    public ChatResponse(String response) {
        this.response = response;
    }

    public String getResponse() {
        return response;
    }
}
}


