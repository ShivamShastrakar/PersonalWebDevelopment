--liquibase formatted sql

--changeset dishika:102_board_subject_question_type_mapping

-- =====================================================================
-- Mapping table: board  ×  subject  ×  question_type
-- Controls which question types are allowed for a given
-- board + subject combination.
-- References:
--   board        (id)          → 3_class_courses.sql
--   subject      (subject_id)  → 3_class_courses.sql
--   question_type(id)          → 100_question_type.sql
-- =====================================================================

CREATE TABLE board_subject_question_type_mapping (
    id            INT              NOT NULL AUTO_INCREMENT,
    board_id      INT              NOT NULL,
    subject_id    INT              NOT NULL,
    question_type_id INT           NOT NULL,

    -- audit columns (consistent with rest of the project)
    created_by    INT              DEFAULT NULL,
    created_at    DATETIME         NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_by    INT              DEFAULT NULL,
    updated_at    DATETIME         DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    deleted_at    DATETIME         DEFAULT NULL,
    deleted       ENUM('1','0')    NOT NULL DEFAULT '0',

    PRIMARY KEY (id),

    -- each board + subject + question_type combination must be unique
    UNIQUE KEY uk_board_subject_qtype (board_id, subject_id, question_type_id),

    -- foreign keys
    CONSTRAINT fk_bsqm_board
        FOREIGN KEY (board_id)
        REFERENCES board (id)
        ON DELETE CASCADE,

    CONSTRAINT fk_bsqm_subject
        FOREIGN KEY (subject_id)
        REFERENCES subject (subject_id)
        ON DELETE CASCADE,

    CONSTRAINT fk_bsqm_question_type
        FOREIGN KEY (question_type_id)
        REFERENCES question_type (id)
        ON DELETE CASCADE

) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
