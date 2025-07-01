package com.example.socratic_chat_bot.init;

import com.example.socratic_chat_bot.dto.Grade;
import com.example.socratic_chat_bot.dto.Topic;
import com.example.socratic_chat_bot.entities.Chat;
import com.example.socratic_chat_bot.entities.ChatHistory;
import com.example.socratic_chat_bot.repositories.ChatHistoryRepository;
import com.example.socratic_chat_bot.repositories.ChatRepository;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.messages.MessageType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DataInitializationService {

    private final ChatRepository chatRepository;
    private final ChatHistoryRepository chatHistoryRepository;

    @Transactional
    public void initializeData() {
        // Create and save Chat entities
        List<Chat> chats = Arrays.asList(
            createChat("chat-001", "Introduction to Algebra", 1672531200000L, Topic.NOUN, Grade.GRADE_8),
            createChat("chat-002", "Understanding Photosynthesis", 1672617600000L, Topic.VERB, Grade.GRADE_7),
            createChat("chat-003", "World War II History", 1672704000000L, Topic.ADJECTIVE, Grade.GRADE_10),
            createChat("chat-004", "Basic Grammar Rules", 1672790400000L, Topic.ADVERB, Grade.GRADE_6),
            createChat("chat-005", "Chemical Reactions", 1672876800000L, Topic.PRONOUN, Grade.GRADE_9)
        );
        chatRepository.saveAll(chats);

        // Create and save ChatHistory entities
        List<ChatHistory> chatHistories = new ArrayList<>();

        // Chat-001 history
        chatHistories.addAll(Arrays.asList(
            createChatHistory("chat-001", "Hello! I'd like to learn about algebra.", MessageType.USER, 1672531260000L),
            createChatHistory("chat-001", "Great! Let's start with the basics. What is a variable in algebra?", MessageType.ASSISTANT, 1672531320000L),
            createChatHistory("chat-001", "A variable is a letter that represents an unknown number.", MessageType.USER, 1672531380000L),
            createChatHistory("chat-001", "Excellent! Now, can you solve this equation: x + 5 = 12?", MessageType.ASSISTANT, 1672531440000L),
            createChatHistory("chat-001", "x = 7", MessageType.USER, 1672531500000L),
            createChatHistory("chat-001", "Perfect! You subtracted 5 from both sides. Let's try a harder one: 2x + 3 = 11", MessageType.ASSISTANT, 1672531560000L),
            createChatHistory("chat-001", "First I subtract 3 from both sides: 2x = 8, then divide by 2: x = 4", MessageType.USER, 1672531620000L),
            createChatHistory("chat-001", "Outstanding work! You're getting the hang of solving linear equations.", MessageType.ASSISTANT, 1672531680000L),
            createChatHistory("chat-001", "Can we try some word problems next?", MessageType.USER, 1672531740000L),
            createChatHistory("chat-001", "Absolutely! Word problems help apply algebra to real situations.", MessageType.ASSISTANT, 1672531800000L)
        ));

        // Chat-002 history
        chatHistories.addAll(Arrays.asList(
            createChatHistory("chat-002", "I need help understanding how plants make food.", MessageType.USER, 1672617660000L),
            createChatHistory("chat-002", "You're asking about photosynthesis! What do you think plants need to make food?", MessageType.ASSISTANT, 1672617720000L),
            createChatHistory("chat-002", "Sunlight and water?", MessageType.USER, 1672617780000L),
            createChatHistory("chat-002", "Good start! They also need carbon dioxide from the air. What gas do plants release?", MessageType.ASSISTANT, 1672617840000L),
            createChatHistory("chat-002", "Oxygen!", MessageType.USER, 1672617900000L),
            createChatHistory("chat-002", "Exactly! The equation is: 6CO2 + 6H2O + light energy → C6H12O6 + 6O2", MessageType.ASSISTANT, 1672617960000L),
            createChatHistory("chat-002", "That looks complicated. What is C6H12O6?", MessageType.USER, 1672618020000L),
            createChatHistory("chat-002", "That's glucose - sugar! It's the food plants make for energy.", MessageType.ASSISTANT, 1672618080000L),
            createChatHistory("chat-002", "So plants basically eat sugar they make themselves?", MessageType.USER, 1672618140000L),
            createChatHistory("chat-002", "Exactly! And this process happens in the chloroplasts, which contain chlorophyll.", MessageType.ASSISTANT, 1672618200000L)
        ));

        // Chat-003 history
        chatHistories.addAll(Arrays.asList(
            createChatHistory("chat-003", "Can you help me understand what caused World War II?", MessageType.USER, 1672704060000L),
            createChatHistory("chat-003", "Certainly! There were multiple causes. What do you know about the Treaty of Versailles?", MessageType.ASSISTANT, 1672704120000L),
            createChatHistory("chat-003", "It ended World War I and was harsh on Germany?", MessageType.USER, 1672704180000L),
            createChatHistory("chat-003", "Right! The harsh terms created resentment. What economic crisis happened in the 1930s?", MessageType.ASSISTANT, 1672704240000L),
            createChatHistory("chat-003", "The Great Depression?", MessageType.USER, 1672704300000L),
            createChatHistory("chat-003", "Yes! This economic crisis helped extremist leaders gain power. Who rose to power in Germany?", MessageType.ASSISTANT, 1672704360000L),
            createChatHistory("chat-003", "Adolf Hitler and the Nazi Party", MessageType.USER, 1672704420000L),
            createChatHistory("chat-003", "Correct. Hitler's aggressive expansion started the war. Which country did Germany invade first?", MessageType.ASSISTANT, 1672704480000L),
            createChatHistory("chat-003", "Poland in 1939?", MessageType.USER, 1672704540000L),
            createChatHistory("chat-003", "Exactly! This invasion prompted Britain and France to declare war on Germany.", MessageType.ASSISTANT, 1672704600000L)
        ));

        // Chat-004 history
        chatHistories.addAll(Arrays.asList(
            createChatHistory("chat-004", "I'm confused about when to use commas.", MessageType.USER, 1672790460000L),
            createChatHistory("chat-004", "Commas can be tricky! Let's start with lists. How would you punctuate: I like apples oranges and bananas?", MessageType.ASSISTANT, 1672790520000L),
            createChatHistory("chat-004", "I like apples, oranges, and bananas?", MessageType.USER, 1672790580000L),
            createChatHistory("chat-004", "Perfect! That's called the Oxford comma. Now, what about joining sentences?", MessageType.ASSISTANT, 1672790640000L),
            createChatHistory("chat-004", "Do you use commas with words like 'and' and 'but'?", MessageType.USER, 1672790700000L),
            createChatHistory("chat-004", "Yes! Use a comma before coordinating conjunctions (and, but, or, so) when joining complete sentences.", MessageType.ASSISTANT, 1672790760000L),
            createChatHistory("chat-004", "So: 'I studied hard, and I passed the test' is correct?", MessageType.USER, 1672790820000L),
            createChatHistory("chat-004", "Exactly right! Both parts could stand alone as sentences.", MessageType.ASSISTANT, 1672790880000L),
            createChatHistory("chat-004", "What about after introductory words?", MessageType.USER, 1672790940000L),
            createChatHistory("chat-004", "Great question! Use commas after introductory elements: 'After the game, we went home.'", MessageType.ASSISTANT, 1672791000000L)
        ));

        // Chat-005 history
        chatHistories.addAll(Arrays.asList(
            createChatHistory("chat-005", "Chemical reactions seem really complicated. Where do I start?", MessageType.USER, 1672876860000L),
            createChatHistory("chat-005", "Let's start simple! What happens when you mix baking soda and vinegar?", MessageType.ASSISTANT, 1672876920000L),
            createChatHistory("chat-005", "It fizzes and bubbles up!", MessageType.USER, 1672876980000L),
            createChatHistory("chat-005", "Exactly! That's a chemical reaction. The bubbles are carbon dioxide gas. What are reactants?", MessageType.ASSISTANT, 1672877040000L),
            createChatHistory("chat-005", "The things you start with before the reaction?", MessageType.USER, 1672877100000L),
            createChatHistory("chat-005", "Perfect! And what do you call what you get after the reaction?", MessageType.ASSISTANT, 1672877160000L),
            createChatHistory("chat-005", "Products?", MessageType.USER, 1672877220000L),
            createChatHistory("chat-005", "Right! So: Reactants → Products. In our example: Baking soda + Vinegar → Salt + Water + CO2", MessageType.ASSISTANT, 1672877280000L),
            createChatHistory("chat-005", "How do you know what products you'll get?", MessageType.USER, 1672877340000L),
            createChatHistory("chat-005", "Great question! It depends on the types of atoms and how they rearrange. We'll explore that next!", MessageType.ASSISTANT, 1672877400000L)
        ));

        chatHistoryRepository.saveAll(chatHistories);
    }


    private Chat createChat(String chatId, String title, Long dateCreated, Topic topic, Grade grade) {
        Chat chat = new Chat();
        chat.setChatId(chatId);
        chat.setTitle(title);
        chat.setDateCreated(dateCreated);
        chat.setTopic(topic);
        chat.setGrade(grade);
        return chat;
    }

    private ChatHistory createChatHistory(String chatId, String message, MessageType messageType, Long dateCreated) {
        ChatHistory chatHistory = new ChatHistory();
        chatHistory.setChatId(chatId);
        chatHistory.setMessage(message);
        chatHistory.setMessageType(messageType);
        chatHistory.setDateCreated(dateCreated);
        return chatHistory;
    }

}
