--liquibase formatted sql
--changeset {dishika}:{id}

CREATE TABLE sections (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    part_id BIGINT NOT NULL,
    name VARCHAR(100) NOT NULL,
    display_name BOOLEAN NOT NULL,
    question_type VARCHAR(50) NOT NULL,
    number_of_questions INT NOT NULL CHECK (number_of_questions >= 1),
    marks_per_question DECIMAL(5,2) NOT NULL CHECK (marks_per_question > 0),
    negative_marks DECIMAL(5,2) NOT NULL DEFAULT 0 CHECK (negative_marks >= 0),
    total_marks DECIMAL(7,2) NOT NULL,
    status VARCHAR(20) DEFAULT 'ACTIVE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT uq_section_name_part UNIQUE (part_id, name),
    CONSTRAINT fk_section_part FOREIGN KEY (part_id) REFERENCES part(id)
);
