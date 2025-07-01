package com.example.socratic_chat_bot.services;

import com.example.socratic_chat_bot.dto.AssistantResponseDto;
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

import static com.example.socratic_chat_bot.constants.SystemPrompts.RETRY_SOCRATIC_METHOD_PROMPT_2;
import static com.example.socratic_chat_bot.constants.SystemPrompts.SOCRATIC_METHOD_PROMPT_2;

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

        saveChat(chatId, topic, grade);

        AssistantResponseDto assistantResponseDto = getSystemResponse(topic, grade, chatId);

        return new NewChatResponseDto(chatId, assistantResponseDto.assistantResponse());
    }

    private void saveChat(String chatId, Topic topic, Grade grade) {

        Chat chat = new Chat();
        chat.setChatId(chatId);
        chat.setTopic(topic);
        chat.setGrade(grade);
        chat.setDateCreated(System.currentTimeMillis());
        chat.setTitle(topic.name() + "_" + grade.name());

        chatRepository.save(chat);
    }

    private AssistantResponseDto getSystemResponse(Topic topic, Grade grade, String chatId) {

        //String prompt = SOCRATIC_METHOD_PROMPT.formatted(
        //    topic.getText(),
        //    grade.getText(),
        //    "",
        //    ""
        //);
        String prompt = SOCRATIC_METHOD_PROMPT_2.formatted(
            grade.getText(),
            topic.getText(),
            grade.getText(),
            topic.getText(),
            "",
            ""
        );
        
        return getLLMResponse(chatId, "", prompt, topic.getText(), grade.getText());
    }

    public AssistantResponseDto chat(String chatId, String userResponse) {

        Chat chat = chatRepository.findByChatId(chatId);
        String topic = chat.getTopic().getText();
        String grade = chat.getGrade().getText();

        //String prompt = SOCRATIC_METHOD_PROMPT.formatted(
        //    topic,
        //    grade,
        //    userResponse,
        //    chatMemory.get(chatId)
        //);

        String prompt = SOCRATIC_METHOD_PROMPT_2.formatted(
            grade,
            topic,
            grade,
            topic,
            userResponse,
            chatMemory.get(chatId)
        );
        return getLLMResponse(chatId, userResponse, prompt, topic, grade);
    }

    private AssistantResponseDto getLLMResponse(
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
                return AssistantResponseDto.create("LLM could not generate question in language suitable for kids");
            }

            return AssistantResponseDto.create(revisedQuestion);

        } else {

            saveInChatMemory(chatId, userResponse, assistantMessage);

            saveInChatHistory(chatId, userResponse, assistantMessage);

            return AssistantResponseDto.create(nextQuestion);
        }
    }

    private String retryGeneratingNewLLMResponse(
        String chatId, String userResponse, String topic, String grade, String nextQuestion) {

        //String retryPrompt = RETRY_SOCRATIC_METHOD_PROMPT.formatted(
        //    topic,
        //    grade,
        //    userResponse,
        //    chatMemory.get(chatId),
        //    nextQuestion
        //);
        String retryPrompt = RETRY_SOCRATIC_METHOD_PROMPT_2.formatted(
            grade,
            grade,
            grade,
            topic,
            userResponse,
            chatMemory.get(chatId),
            nextQuestion
        );

        AssistantMessage assistantMessage = getAssistantMessage(retryPrompt);
        String revisedQuestion = assistantMessage.getText();
        return revisedQuestion;
    }

    private void saveInChatMemory(String chatId, String userResponse, AssistantMessage assistantMessage) {

        // save user message in ChatMemory
        chatMemory.add(chatId, new UserMessage(userResponse));

        // save LLM response in ChatMemory
        chatMemory.add(chatId, assistantMessage);
    }

    private void saveInChatHistory(String chatId, String userResponse, AssistantMessage assistantMessage) {

        long now = System.currentTimeMillis();

        ChatHistory userChatHistory = new ChatHistory();
        userChatHistory.setChatId(chatId);
        userChatHistory.setMessageType(MessageType.USER);
        userChatHistory.setMessage(userResponse);
        userChatHistory.setDateCreated(now);

        chatHistoryRepository.save(userChatHistory);

        ChatHistory assistantChatHistory = new ChatHistory();
        assistantChatHistory.setChatId(chatId);
        assistantChatHistory.setMessageType(MessageType.ASSISTANT);
        assistantChatHistory.setMessage(assistantMessage.getText());
        assistantChatHistory.setDateCreated(now);

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
