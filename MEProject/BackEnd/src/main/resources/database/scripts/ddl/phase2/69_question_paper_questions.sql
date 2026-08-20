-- Create question_paper_questions table to store actual question IDs for each question paper
-- This table maintains the relationship between question papers and the actual questions selected

CREATE TABLE IF NOT EXISTS question_paper_questions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    question_paper_id BIGINT NOT NULL,
    question_id BIGINT UNSIGNED NOT NULL,
    sequence_number INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    created_by BIGINT NULL,
    updated_by BIGINT NULL,

    -- Foreign key constraints
    CONSTRAINT fk_qpq_question_paper FOREIGN KEY (question_paper_id)
        REFERENCES question_paper(id) ON DELETE CASCADE,

    CONSTRAINT fk_qpq_question FOREIGN KEY (question_id)
        REFERENCES questions(id) ON DELETE CASCADE,

    -- Unique constraint to prevent duplicate questions in same question paper
    CONSTRAINT uk_qpq_paper_question UNIQUE (question_paper_id, question_id),

    -- Indexes for better query performance
    INDEX idx_qpq_question_paper_id (question_paper_id),
    INDEX idx_qpq_question_id (question_id),
    INDEX idx_qpq_sequence_number (question_paper_id, sequence_number)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

