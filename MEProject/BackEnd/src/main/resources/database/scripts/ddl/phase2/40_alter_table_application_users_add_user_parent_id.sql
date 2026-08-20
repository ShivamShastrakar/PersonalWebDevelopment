--liquibase formatted sql
--changeset narendra:40_alter_table_application_users_add_user_parent_id

-- Add user_parent_id column to application_user table
ALTER TABLE application_user
ADD COLUMN user_parent_id BIGINT DEFAULT NULL;

-- Add index for better query performance
CREATE INDEX idx_application_user_parent_id ON application_user(user_parent_id);

-- Add foreign key constraint to application_user table (self-referencing)

