--liquibase formatted sql
--changeset copilot:add-student-category-column
ALTER TABLE student ADD COLUMN category VARCHAR(32) DEFAULT NULL;
