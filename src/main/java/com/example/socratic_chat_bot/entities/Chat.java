package com.example.socratic_chat_bot.entities;

import com.example.socratic_chat_bot.dto.Grade;
import com.example.socratic_chat_bot.dto.Topic;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String chatId;

    @Enumerated(value = EnumType.STRING)
    private Topic topic;

    @Enumerated(value = EnumType.STRING)
    private Grade grade;
}
