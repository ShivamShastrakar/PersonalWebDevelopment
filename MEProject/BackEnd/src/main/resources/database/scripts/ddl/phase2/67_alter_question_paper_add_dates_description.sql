--liquibase formatted sql
--changeset {narendra}:{67_alter_question_paper_add_dates_description}

-- Add start_date, end_date, and description columns to question_paper table
ALTER TABLE question_paper
ADD COLUMN start_date DATETIME NULL COMMENT 'Start date and time for the question paper',
ADD COLUMN end_date DATETIME NULL COMMENT 'End date and time for the question paper',
ADD COLUMN description TEXT NULL COMMENT 'Description or notes for the question paper';
