--liquibase formatted sql
--changeset {narendra}:{id}

CREATE TABLE paper_templates (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    subject_id INT REFERENCES subjects(id),
    board_id INT REFERENCES board(id),
    class_id INT REFERENCES classes(id),
    medium VARCHAR(20),
    name VARCHAR(150) NOT NULL,
    question_config_json JSON NOT NULL,
    -- Stores section, language (EN/MR), question type, count,
    -- positive marks, negative marks
    duration_minutes INT NOT NULL CHECK (duration_minutes > 0),
    total_marks INT NOT NULL CHECK (total_marks >= 0),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
