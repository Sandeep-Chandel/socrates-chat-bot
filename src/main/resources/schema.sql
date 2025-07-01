
-- Create Chat table
CREATE TABLE chat (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    chat_id VARCHAR(255) NOT NULL,
    title VARCHAR(1000),
    date_created BIGINT,
    topic VARCHAR(50),
    grade VARCHAR(50)
);

-- Create ChatHistory table
CREATE TABLE chat_history (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    chat_id VARCHAR(255) NOT NULL,
    message VARCHAR(3000),
    message_type VARCHAR(50),
    date_created BIGINT
);

-- Create indexes for better performance
CREATE INDEX idx_chat_chat_id ON chat(chat_id);

CREATE INDEX idx_chat_history_chat_id ON chat_history(chat_id);


