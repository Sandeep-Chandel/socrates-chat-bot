package com.example.socratic_chat_bot.services;

import com.example.socratic_chat_bot.dto.ChatHistoryDto;
import com.example.socratic_chat_bot.dto.ChatHistorySummaryDto;
import com.example.socratic_chat_bot.dto.Grade;
import com.example.socratic_chat_bot.dto.GradeDto;
import com.example.socratic_chat_bot.dto.Topic;
import com.example.socratic_chat_bot.dto.TopicDto;
import com.example.socratic_chat_bot.entities.Chat;
import com.example.socratic_chat_bot.entities.ChatHistory;
import com.example.socratic_chat_bot.repositories.ChatHistoryRepository;
import com.example.socratic_chat_bot.repositories.ChatRepository;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatHistoryService {
    private final ChatHistoryRepository chatHistoryRepository;

    private final ChatRepository chatRepository;

    public List<ChatHistoryDto> getChatHistory(String chatId) {

        List<ChatHistory> chatHistoryList = chatHistoryRepository.findByChatId(chatId);

        return chatHistoryList.stream()
            .map(e -> new ChatHistoryDto(e.getMessage(), e.getMessageType(), e.getDateCreated()))
            .collect(Collectors.toUnmodifiableList());
    }

    public List<TopicDto> getTopics() {

        return Arrays.stream(Topic.values())
            .map(e -> new TopicDto(e.getDisplayName(), e.name()))
            .collect(Collectors.toUnmodifiableList());
    }

    public List<GradeDto> getGrades() {
        return Arrays.stream(Grade.values())
            .map(e -> new GradeDto(e.name(), e.getText()))
            .collect(Collectors.toUnmodifiableList());
    }

    public List<ChatHistorySummaryDto> getChatSummary() {
        List<Chat> chatList = chatRepository.findAll();

        return chatList.stream()
            .map(e -> ChatHistorySummaryDto.create(e.getChatId(), e.getTitle(), e.getDateCreated()))
            .collect(Collectors.toUnmodifiableList());
    }
}
