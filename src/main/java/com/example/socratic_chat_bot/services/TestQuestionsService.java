package com.example.socratic_chat_bot.services;

import com.example.socratic_chat_bot.config.GemmaChatModelConfig;
import com.example.socratic_chat_bot.dto.Grade;
import com.example.socratic_chat_bot.dto.Question;
import com.example.socratic_chat_bot.dto.Topic;
import com.example.socratic_chat_bot.utils.ResponseUtils;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.stereotype.Service;

import static com.example.socratic_chat_bot.constants.SystemPrompts.TEST_QUIZ_PROMPT;

@Service
@RequiredArgsConstructor
public class TestQuestionsService {

    private final OllamaChatModel gemmaChatModel;

    public List<Question> generateTestQuestions(Topic topic, Grade grade) {

        String prompt = TEST_QUIZ_PROMPT.formatted(
            topic.getText(),
            grade.getText());
        ChatResponse chatResponse = gemmaChatModel.call(new Prompt(prompt));
        String nextQuestion = chatResponse.getResult().getOutput().getText();
        List<Question> questions = ResponseUtils.extractJsonFromLLMResponse(nextQuestion);
        return questions;
    }
}
