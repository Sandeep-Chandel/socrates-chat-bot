package com.example.socratic_chat_bot.services;

import com.example.socratic_chat_bot.dto.Grade;
import com.example.socratic_chat_bot.dto.NewChatResponseDto;
import com.example.socratic_chat_bot.dto.Topic;
import com.example.socratic_chat_bot.entities.Chat;
import com.example.socratic_chat_bot.entities.ChatHistory;
import com.example.socratic_chat_bot.repositories.ChatHistoryRepository;
import com.example.socratic_chat_bot.repositories.ChatRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.MessageType;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.stereotype.Service;

import static com.example.socratic_chat_bot.constants.SystemPrompts.RETRY_SOCRATIC_METHOD_PROMPT;
import static com.example.socratic_chat_bot.constants.SystemPrompts.SOCRATIC_METHOD_PROMPT;

@Service
@RequiredArgsConstructor
public class SocraticChatService {

    private final OllamaChatModel gemmaChatModel;

    private final OllamaChatModel graniteChatModel;

    private final ChatMemory chatMemory;

    private final ChatRepository chatRepository;

    private final ChatHistoryRepository chatHistoryRepository;

    private ChatClient graniteChatClient;

    @PostConstruct
    public void init() {
        graniteChatClient = ChatClient.builder(graniteChatModel)
            .defaultAdvisors(new SimpleLoggerAdvisor())
            .build();
    }
    
    public NewChatResponseDto createNewChat(Topic topic, Grade grade) {

        String chatId = String.valueOf(System.currentTimeMillis());

        String systemResponse = getSystemResponse(topic, grade, chatId);

        saveChat(chatId, topic, grade);

        return new NewChatResponseDto(chatId, systemResponse);
    }

    private void saveChat(String chatId, Topic topic, Grade grade) {

        Chat chat = new Chat();
        chat.setChatId(chatId);
        chat.setTopic(topic);
        chat.setGrade(grade);

        chatRepository.save(chat);
    }

    private String getSystemResponse(Topic topic, Grade grade, String chatId) {

        String prompt = SOCRATIC_METHOD_PROMPT.formatted(
            topic.getText(),
            grade.getText(),
            "",
            ""
        );
        
        return getLLMResponse(chatId, "", prompt, topic.getText(), grade.getText());
    }

    public String chat(String chatId, String userResponse) {

        Chat chat = chatRepository.findByChatId(chatId);
        String topic = chat.getTopic().getText();
        String grade = chat.getGrade().getText();

        String prompt = SOCRATIC_METHOD_PROMPT.formatted(
            topic,
            grade,
            userResponse,
            chatMemory.get(chatId)
        );
        return getLLMResponse(chatId, userResponse, prompt, topic, grade);
    }

    private String getLLMResponse(
        String chatId, String userResponse, String prompt, String topic, String grade) {
        
        // get first response from LLM
        AssistantMessage assistantMessage = getAssistantMessage(prompt);

        String nextQuestion = assistantMessage.getText();

        String moderationLLMResponse = runByTheModeationLLM(nextQuestion);

        if (!passesModerationTest(moderationLLMResponse)) {

            String revisedQuestion = retryGeneratingNewLLMResponse(chatId, userResponse, topic, grade, nextQuestion);

            String secondModerationResponse = runByTheModeationLLM(revisedQuestion);

            saveInChatMemory(chatId, userResponse, assistantMessage);

            saveInChatHistory(chatId, userResponse, assistantMessage);

            if (!passesModerationTest(secondModerationResponse)) {
                return "LLM could not generate question in language suitable for kids";
            }

            return revisedQuestion;

        } else {

            saveInChatMemory(chatId, userResponse, assistantMessage);

            saveInChatHistory(chatId, userResponse, assistantMessage);

            return nextQuestion;
        }
    }

    private String retryGeneratingNewLLMResponse(
        String chatId, String userResponse, String topic, String grade, String nextQuestion) {

        String retryPrompt = RETRY_SOCRATIC_METHOD_PROMPT.formatted(
            topic,
            grade,
            userResponse,
            chatMemory.get(chatId),
            nextQuestion
        );

        AssistantMessage assistantMessage1 = getAssistantMessage(retryPrompt);
        String revisedQuestion = assistantMessage1.getText();
        return revisedQuestion;
    }

    private void saveInChatMemory(String chatId, String userResponse, AssistantMessage assistantMessage) {

        // save user message in ChatMemory
        chatMemory.add(chatId, new UserMessage(userResponse));

        // save LLM response in ChatMemory
        chatMemory.add(chatId, assistantMessage);
    }

    private void saveInChatHistory(String chatId, String userResponse, AssistantMessage assistantMessage) {

        ChatHistory userChatHistory = new ChatHistory();
        userChatHistory.setChatId(chatId);
        userChatHistory.setMessageType(MessageType.USER);
        userChatHistory.setMessage(userResponse);

        chatHistoryRepository.save(userChatHistory);

        ChatHistory assistantChatHistory = new ChatHistory();
        assistantChatHistory.setChatId(chatId);
        assistantChatHistory.setMessageType(MessageType.ASSISTANT);
        assistantChatHistory.setMessage(assistantMessage.getText());

        chatHistoryRepository.save(assistantChatHistory);
    }

    private static boolean passesModerationTest(String moderationLLMResponse) {
        return "No".equals(moderationLLMResponse);
    }

    private AssistantMessage getAssistantMessage(String prompt) {
        ChatResponse chatResponse = gemmaChatModel.call(new Prompt(prompt));
        AssistantMessage assistantMessage = chatResponse.getResult().getOutput();
        return assistantMessage;
    }

    private String runByTheModeationLLM(String nextQuestion) {
        String moderationLLMResponse = graniteChatClient.prompt()
            .user(nextQuestion)
            .call().content();
        return moderationLLMResponse;
    }
}
