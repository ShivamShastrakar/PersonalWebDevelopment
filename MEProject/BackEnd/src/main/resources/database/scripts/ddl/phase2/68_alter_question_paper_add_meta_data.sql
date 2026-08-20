--liquibase formatted sql
--changeset {dishika}:{68_alter_question_paper_add_meta_data}

-- Add meta_data column to question_paper table to store SUKA and Difficulty Level distributions in JSON format
-- Note: Using existing 'description' column for additional notes (added in migration 67)
ALTER TABLE question_paper
ADD COLUMN meta_data JSON NULL COMMENT 'Metadata containing SUKA (Skill, Understanding, Knowledge, Application) and Difficulty Level distributions';
