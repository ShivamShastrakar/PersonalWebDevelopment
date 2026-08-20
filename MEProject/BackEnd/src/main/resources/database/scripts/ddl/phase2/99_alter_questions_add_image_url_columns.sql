--liquibase formatted sql
--changeset alter_questions_add_image_url_columns:99

ALTER TABLE questions
    ADD COLUMN question_image_url VARCHAR(500) NULL,
    ADD COLUMN answer_explanation_image_url VARCHAR(500) NULL;

