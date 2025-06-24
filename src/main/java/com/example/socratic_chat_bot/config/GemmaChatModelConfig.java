package com.example.socratic_chat_bot.config;

import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.ollama.api.OllamaApi;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GemmaChatModelConfig {

    @Bean
    public OllamaChatModel gemmaChatModel() {
        return OllamaChatModel.builder()
            .defaultOptions(OllamaOptions.builder().model("gemma3:4b").build())
            .ollamaApi(OllamaApi.builder().build())
            .build();
    }
}

