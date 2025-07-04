# Socratic Chat Bot

A chat application that enables users to learn predefined topics using the Socratic method of teaching. The application leverages AI models for interactive learning experiences with content moderation capabilities.

## Overview

The Socratic Chat Bot is designed to facilitate learning through guided questioning and discussion, following the principles of the Socratic method. Users can engage with various topics through conversational AI, with built-in content moderation to ensure safe and appropriate interactions.

After completing a learning session user can test themselves on the same topic using a quiz generated by the system.

## Features

- **Interactive Learning**: Engage with predefined topics through AI-powered conversations
- **Socratic Method**: Learn through guided questioning and critical thinking
- **Content Moderation**: Built-in moderation using Granite3-guardian model
- **Chat History**: Track and review previous learning sessions
- **Test Quiz**: Take quiz to test what you learned
- **Multiple Topics & Grades**: Support for various educational topics and grade levels
- **RESTful API**: Clean REST endpoints for easy integration

## Technology Stack

- **Java Version**: 21
- **Primary LLM**: Gemma3:4b for chat interactions
- **Moderation Model**: Granite3-guardian for content filtering
- **Database**: H2 (in-memory for development)

## Prerequisites

Before running the application, ensure you have the following installed:

- Java 21 or higher
- Ollama (for running local AI models)

### AI Models Setup

1. **Install Ollama**: Follow the installation guide at [ollama.ai](https://ollama.ai)

2. **Download Required Models**:
   ```bash
   # Download Gemma3 model for chat
   ollama pull gemma3:4b
   
   # Download Granite3-guardian model for content moderation
   ollama pull granite3-guardian:2b
   ```

## Installation & Setup

1. **Clone the repository**:
   ```bash
   git clone <repository-url>
   cd socratic-chat-bot
   ```

2. **Build the project**:
   ```bash
   ./mvnw clean install
   ```

3. **Run the application**:
   ```bash
   ./mvnw spring-boot:run
   ```

The application will start on `http://localhost:8080`

## API Endpoints

### Chat Management

- **POST** `/createNewChat?topic=VERB&grade=GRADE_1`
  - Create a new chat session
  - Returns: New chat session details

- **GET** `/conv/{chatId}`
  - Continue conversation in existing chat
  - Parameters: `chatId` (path)
  - Body: user response
  - Returns: AI response string

- **GET** `/chatHistory/{chatId}`
  - Retrieve chat history for a session
  - Parameters: `chatId` (path)
  - Returns: List of chat history entries

- **GET** `/genTestQuestions?topic=VERB&grade=GRADE_1`
  - Generate test questions for learning topics
  - Returns: List of questions
