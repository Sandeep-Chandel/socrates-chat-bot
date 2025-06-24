package com.example.socratic_chat_bot.dto;

import org.springframework.ai.chat.messages.MessageType;

public record ChatHistoryDto(
    String message,
    MessageType messageType
) {
}
