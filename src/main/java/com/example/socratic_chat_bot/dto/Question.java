package com.example.socratic_chat_bot.dto;

public record Question(
    String questionId,
    String text,
    String opA,
    String opB,
    String opC,
    String opD,
    String ans,
    String explanation
) {
}
