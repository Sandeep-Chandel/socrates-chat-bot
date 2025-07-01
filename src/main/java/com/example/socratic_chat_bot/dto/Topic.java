package com.example.socratic_chat_bot.dto;

public enum Topic {

    NOUN ("Nouns in English Language Grammar", "Noun"),
    VERB ("Verbs in English Language Grammar", "Verb"),
    ADJECTIVE ("Adjectives in English Language Grammar", "Adjective"),
    ADVERB ("Adverbs in English Language Grammar", "Adverb"),
    PRONOUN ("Pronouns in English Language Grammar", "Pronoun");

    private String displayName;
    private String text;

    Topic(String text, String displayName) {

        this.text = text;
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public String getText() {
        return this.text;
    }
}
