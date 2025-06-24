package com.example.socratic_chat_bot.repositories;

import com.example.socratic_chat_bot.entities.ChatHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatHistoryRepository extends JpaRepository<ChatHistory, Long> {
}
