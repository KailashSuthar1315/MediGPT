



package com.localai.assistant.service;

import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class OllamaService {

    
    private final RestTemplate restTemplate;

    private final String OLLAMA_API_URL = "http://localhost:11434/api/generate";

    public OllamaService(RestTemplateBuilder restTemplateBuilder) {
    this.restTemplate = restTemplateBuilder.build();
}

    // public String generateResponse(String prompt) {

    //     Map<String, Object> request = new HashMap<>();
    //     request.put("model", "phi3");

    public String generateResponse(String prompt) {

    
    if (!isMedicalQuestion(prompt)) {
        return "Sorry, I can answer only medical and health-related questions.";
    }

    Map<String, Object> request = new HashMap<>();
    request.put("model", "phi3");
       




String systemPrompt = """
You are MediGPT, a STRICT medical assistant.

You MUST follow these rules:

1. Check the user question:
   - If it is NOT related to medical or health → DO NOT ANSWER
   - Instead reply EXACTLY:
     Sorry, I can answer only medical and health-related questions.

2. Medical topics include ONLY:
   - diseases
   - symptoms
   - medicines
   - treatments
   - first aid
   - lab reports
   - body organs
   - diet and nutrition for health
   - mental health
   - prevention

3. If the question is about cooking, coding, business, general knowledge, etc → REJECT IT.

--------------------------------------

FOR MEDICAL QUESTIONS ONLY:

Follow this STRICT format:

[One line definition]

## Key Points
- Point 1
- Point 2
- Point 3

## Symptoms / Causes / Treatment
- Point 1
- Point 2

## When to Seek Help
- Point 1

--------------------------------------

STRICT RULES:
- NEVER answer non-medical questions
- NEVER give recipe, coding, or general answers
- NEVER write a paragraph
- ALWAYS use bullet points
- MAX 3 sections only
- Keep answers short
""";

        String finalPrompt = systemPrompt + """

IMPORTANT:
Answer only in this format:

One-line definition.

## Key Points
- Point 1
- Point 2
- Point 3

## Extra Info
- Point 1
- Point 2

Do not write long paragraphs.
Do not exceed 6 bullet points.
Do not use phrases like "As an AI".

User Question:
""" + prompt;

        request.put("prompt", finalPrompt);
        request.put("stream", false);

        ResponseEntity<String> response = restTemplate.postForEntity(
                OLLAMA_API_URL,
                request,
                String.class
        );

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(response.getBody());
            System.out.println(jsonNode);
            String aiResponse = jsonNode.get("response").asText();

            System.out.println(aiResponse);

            

            return aiResponse;

        } catch (Exception e) {
            System.out.println(e);
            throw new RuntimeException("Failed to respond", e);
        }
    }
    public void streamResponse(String prompt, java.util.function.Consumer<String> onChunk) {
        if (!isMedicalQuestion(prompt)) {
    onChunk.accept("Sorry, I can answer only medical and health-related questions.");
    return;
}

    Map<String, Object> request = new HashMap<>();
    request.put("model", "phi3");




String systemPrompt = """
You are MediGPT, a STRICT medical assistant.

You MUST follow these rules:

1. Check the user question:
   - If it is NOT related to medical or health → DO NOT ANSWER
   - Instead reply EXACTLY:
     Sorry, I can answer only medical and health-related questions.

2. Medical topics include ONLY:
   - diseases
   - symptoms
   - medicines
   - treatments
   - first aid
   - lab reports
   - body organs
   - diet and nutrition for health
   - mental health
   - prevention

3. If the question is about cooking, coding, business, general knowledge, etc → REJECT IT.

--------------------------------------

FOR MEDICAL QUESTIONS ONLY:

Follow this STRICT format:

[One line definition]

## Key Points
- Point 1
- Point 2
- Point 3

## Symptoms / Causes / Treatment
- Point 1
- Point 2

## When to Seek Help
- Point 1

--------------------------------------

STRICT RULES:
- NEVER answer non-medical questions
- NEVER give recipe, coding, or general answers
- NEVER write a paragraph
- ALWAYS use bullet points
- MAX 3 sections only
- Keep answers short
""";
    String finalPrompt = systemPrompt + """

IMPORTANT:
Answer only in this format:

One-line definition.

## Key Points
- Point 1
- Point 2
- Point 3

## Extra Info
- Point 1
- Point 2

Do not write long paragraphs.
Do not exceed 6 bullet points.
Do not use phrases like "As an AI".

User Question:
""" + prompt;

    request.put("prompt", finalPrompt);
    request.put("stream", true);

    try {
        ObjectMapper mapper = new ObjectMapper();

        restTemplate.execute(
                OLLAMA_API_URL,
                org.springframework.http.HttpMethod.POST,
                clientHttpRequest -> {
                    clientHttpRequest.getHeaders().setContentType(
                            org.springframework.http.MediaType.APPLICATION_JSON
                    );
                    mapper.writeValue(clientHttpRequest.getBody(), request);
                },
                clientHttpResponse -> {

                    BufferedReader reader = new BufferedReader(
                            new java.io.InputStreamReader(clientHttpResponse.getBody())
                    );

                    String line;

                    while ((line = reader.readLine()) != null) {

                        JsonNode node = mapper.readTree(line);

                        if (node.has("response")) {
                            String chunk = node.get("response").asText();
                            onChunk.accept(chunk);
                        }
                    }

                    return null;
                }
        );

    } catch (Exception e) {
        throw new RuntimeException("Streaming failed", e);
    }
}
private boolean isMedicalQuestion(String prompt) {
    String text = prompt.toLowerCase().trim();

    // ❌ Block obvious non-medical topics
    if (text.matches(".*(travel|train|flight|recipe|cook|biryani|politics|government|history|geography|code|programming|java|python|movie|actor|cricket|football).*")) {
        return false;
    }
     return true;
    // ✅ Allow medical topics
    // return text.matches(".*(fever|pain|disease|symptom|symptoms|medicine|treatment|doctor|health|infection|virus|cancer|diabetes|bp|blood|injury|headache|cold|cough|body|mental|stress|anxiety|nutrition|diet|exercise|polio|cholesterol|heart|attack|thyroid|vomiting|nausea|rash|allergy|hospital|tablet|paracetamol|dolo|bmi|weight).*");
}
  private String rejectionMessage() {
        return "Sorry, I can answer only medical and health-related questions.";
    }
}