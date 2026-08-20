--liquibase formatted sql
--changeset {narendra}:{id}

CREATE TABLE questions (
    id SERIAL PRIMARY KEY,
    board_id INT REFERENCES board(id),
    subject_id INT,
    class_id INT,
    medium VARCHAR(20),  -- "English", "Marathi"
    chapter_id INT,
    topic_id INT,
    question_type VARCHAR(20) NOT NULL,  -- "MCQ", "FillUp", "OneWord", etc.
    question_text TEXT NOT NULL,
    options JSON,
    correct_answer JSON NOT NULL,
    answer_explanation TEXT,
    skill_level VARCHAR(20),
    difficulty_level VARCHAR(10),  -- "Easy", "Medium", "Hard"
    ai_prompt_hash VARCHAR(64),  -- Deduplication
    created_at TIMESTAMP DEFAULT NOW(),
    created_by BIGINT UNSIGNED,
    FOREIGN KEY (board_id) REFERENCES board(id),
    FOREIGN KEY (subject_id) REFERENCES subject(subject_id),
    FOREIGN KEY (class_id) REFERENCES class(id),
    FOREIGN KEY (chapter_id) REFERENCES chapters(id),
    FOREIGN KEY (topic_id) REFERENCES topics(topic_id),
    FOREIGN KEY (created_by) REFERENCES users(user_id),
    INDEX idx_q_metadata (board_id,class_id, subject_id, topic_id, question_type, skill_level, difficulty_level)
);