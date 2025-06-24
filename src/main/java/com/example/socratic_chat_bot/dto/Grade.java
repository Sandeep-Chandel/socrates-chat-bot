package com.example.socratic_chat_bot.dto;

public enum Grade {

    GRADE_1 ("1st Grade"),
    GRADE_2 ("2nd Grade"),
    GRADE_3 ("3rd Grade"),
    GRADE_4 ("4th Grade"),
    GRADE_5 ("5th Grade"),
    GRADE_6 ("6th Grade"),
    GRADE_7 ("7th Grade"),
    GRADE_8 ("8th Grade"),
    GRADE_9 ("9th Grade"),
    GRADE_10 ("10th Grade"),
    GRADE_11 ("11th Grade"),
    GRADE_12 ("12th Grade");

    private String text;

    Grade(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }
}
