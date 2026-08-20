--liquibase formatted sql
--changeset {narendra}:{id}

-- Add the new column to Student table
ALTER TABLE temp_students
ADD COLUMN student_id BIGINT;