package com.example.socratic_chat_bot.controllers;

import com.example.socratic_chat_bot.dto.Grade;
import com.example.socratic_chat_bot.dto.NewChatResponseDto;
import com.example.socratic_chat_bot.dto.Question;
import com.example.socratic_chat_bot.dto.Topic;
import com.example.socratic_chat_bot.services.SocraticChatService;
import com.example.socratic_chat_bot.services.TestQuestionsService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.ollama.api.OllamaApi;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/")
public class ChatController {

    private final TestQuestionsService testQuestionsService;

    private final SocraticChatService socraticChatService;

    @GetMapping(path = "genTestQuestions")
    @ResponseBody
    public List<Question> genTestQuestions() {

        return testQuestionsService.generateTestQuestions();
    }

    @GetMapping(path = "conv/{chatId}")
    @ResponseBody
    public String conv(@PathVariable String chatId, @RequestBody String userResponse) {

        return socraticChatService.chat(chatId, userResponse);
    }

    @PostMapping(path = "createNewChat")
    @ResponseBody
    public NewChatResponseDto createNewChat(@RequestParam Topic topic, @RequestParam Grade grade) {

        return socraticChatService.createNewChat(topic, grade);
    }

}
