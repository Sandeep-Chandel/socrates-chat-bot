package com.example.socratic_chat_bot.dto;

public record ChatHistorySummaryDto(

    String chatId,
    String title,
    Long dateCreated
) {

    public static ChatHistorySummaryDto create(String chatId, String title, Long dateCreated) {
        return new ChatHistorySummaryDto(chatId, title, dateCreated);
    }
}
