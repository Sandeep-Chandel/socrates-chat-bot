package com.example.socratic_chat_bot.dto;

public record AssistantResponseDto(
    String assistantResponse
) {

    public static AssistantResponseDto create(String msg) {
        return new AssistantResponseDto(msg);
    }
}
