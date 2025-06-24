package com.example.socratic_chat_bot.config;

import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.ollama.api.OllamaApi;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GraniteChatModelConfig {

    @Bean
    public OllamaChatModel graniteChatModel() {
        return OllamaChatModel.builder()
            .defaultOptions(OllamaOptions.builder().model("granite3-guardian:2b").build())
            .ollamaApi(OllamaApi.builder().build())
            .build();
    }
}

