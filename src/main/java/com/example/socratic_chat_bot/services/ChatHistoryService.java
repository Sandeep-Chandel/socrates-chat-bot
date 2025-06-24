package com.example.socratic_chat_bot.services;

import com.example.socratic_chat_bot.dto.ChatHistoryDto;
import com.example.socratic_chat_bot.entities.ChatHistory;
import com.example.socratic_chat_bot.repositories.ChatHistoryRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatHistoryService {
    private final ChatHistoryRepository chatHistoryRepository;

    public List<ChatHistoryDto> getChatHistory(String chatId) {

        List<ChatHistory> chatHistoryList = chatHistoryRepository.findByChatId(chatId);

        return chatHistoryList.stream()
            .map(e -> new ChatHistoryDto(e.getMessage(), e.getMessageType()))
            .collect(Collectors.toUnmodifiableList());
    }
}
