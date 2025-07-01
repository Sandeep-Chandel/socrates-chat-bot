package com.example.socratic_chat_bot.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.IdGeneratorType;
import org.springframework.ai.chat.messages.MessageType;

@Entity
@Data
public class ChatHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String chatId;

    @Column(length = 3000)
    private String message;

    @Enumerated(value = EnumType.STRING)
    private MessageType messageType;

    private Long dateCreated;
}
