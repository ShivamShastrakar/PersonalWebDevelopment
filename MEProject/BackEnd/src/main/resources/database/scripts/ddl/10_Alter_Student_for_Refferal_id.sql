--liquibase formatted sql
--changeset {narendra}:{id}

-- Add the new column to Student table
ALTER TABLE student
ADD COLUMN student_reference_id BIGINT UNSIGNED;

-- Add the foreign key constraint on userId referencing users(userId)
ALTER TABLE student
ADD CONSTRAINT fk_student_user_reference_id
FOREIGN KEY (student_reference_id) REFERENCES users(user_id)


