--liquibase formatted sql
--changeset copilot:add-student-institute-name-column
ALTER TABLE student ADD COLUMN institute_name VARCHAR(255) DEFAULT NULL;
