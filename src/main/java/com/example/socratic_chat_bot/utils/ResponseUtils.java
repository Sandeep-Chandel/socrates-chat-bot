package com.example.socratic_chat_bot.utils;

import com.example.socratic_chat_bot.dto.Question;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;

public class ResponseUtils {

    private ResponseUtils() {}

    public static List<Question> extractJsonFromLLMResponse(String llmResponse) {

        int jsonStart = llmResponse.indexOf("```json");
        int jsonEnd = llmResponse.indexOf("");

        if (jsonStart == -1 || jsonEnd == -1) {
            throw new IllegalArgumentException("No JSON tags found in LLM response [" + llmResponse + "]");
        }

        String jsonStr = llmResponse.substring(jsonStart + 7).trim();
        jsonStr = jsonStr.substring(0, jsonStr.indexOf("```"));

        // Create ObjectMapper instance
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            // Convert JSON string to Map
            return objectMapper.readValue(jsonStr, new TypeReference<List<Question>>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to parse JSON from LLM response [ " + jsonStr + " ]", e);
        }
    }
}
