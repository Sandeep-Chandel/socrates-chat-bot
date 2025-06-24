package com.example.socratic_chat_bot.repositories;

import com.example.socratic_chat_bot.entities.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<Chat, Long> {

    Chat findByChatId(String chatId);
}
