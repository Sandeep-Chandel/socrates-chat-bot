package com.example.socratic_chat_bot.controllers;

import com.example.socratic_chat_bot.dto.ChatHistoryDto;
import com.example.socratic_chat_bot.dto.AssistantResponseDto;
import com.example.socratic_chat_bot.dto.ChatHistorySummaryDto;
import com.example.socratic_chat_bot.dto.Grade;
import com.example.socratic_chat_bot.dto.GradeDto;
import com.example.socratic_chat_bot.dto.NewChatResponseDto;
import com.example.socratic_chat_bot.dto.Question;
import com.example.socratic_chat_bot.dto.Topic;
import com.example.socratic_chat_bot.dto.TopicDto;
import com.example.socratic_chat_bot.services.ChatHistoryService;
import com.example.socratic_chat_bot.services.SocraticChatService;
import com.example.socratic_chat_bot.services.TestQuestionsService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    private final ChatHistoryService chatService;

    @GetMapping(path = "genTestQuestions")
    @ResponseBody
    public List<Question> genTestQuestions(
        @RequestParam Topic topic, @RequestParam Grade grade) {

        return testQuestionsService.generateTestQuestions(topic, grade);
    }

    @PutMapping(path = "conv/{chatId}")
    @ResponseBody
    public AssistantResponseDto conv(@PathVariable String chatId, @RequestBody String userResponse) {

        return socraticChatService.chat(chatId, userResponse);
    }

    @PostMapping(path = "createNewChat")
    @ResponseBody
    public NewChatResponseDto createNewChat(@RequestParam Topic topic, @RequestParam Grade grade) {

        return socraticChatService.createNewChat(topic, grade);
    }

    @GetMapping(path = "chatHistory/{chatId}")
    @ResponseBody
    public List<ChatHistoryDto> chatHistory(@PathVariable String chatId) {

        return chatService.getChatHistory(chatId);
    }

    @GetMapping(path = "chatHistory/summary")
    @ResponseBody
    public List<ChatHistorySummaryDto> getShatHistorySummary() {

        return chatService.getChatSummary();
    }

    @GetMapping(path = "topics")
    @ResponseBody
    public List<TopicDto> topics() {

        return chatService.getTopics();
    }

    @GetMapping(path = "grades")
    @ResponseBody
    public List<GradeDto> grades() {

        return chatService.getGrades();
    }

}
