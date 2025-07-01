package com.example.socratic_chat_bot.dto;

public enum Topic {

    NOUN ("Nouns in English Language Grammar"),
    VERB ("Verbs in English Language Grammar"),
    ADJECTIVE ("Adjectives in English Language Grammar"),
    ADVERB ("Adverbs in English Language Grammar"),
    PRONOUN ("Pronouns in English Language Grammar");

    private String text;

    Topic(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }
}
