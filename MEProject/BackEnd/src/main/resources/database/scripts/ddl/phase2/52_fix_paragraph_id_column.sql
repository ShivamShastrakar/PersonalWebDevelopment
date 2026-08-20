--liquibase formatted sql
--changeset {dishika}:{id}

-- Fix the paragraph_id column name if it was created with camelCase
-- First, try to rename paragraphId to paragraph_id if it exists
ALTER TABLE `questions`
CHANGE COLUMN `paragraphId` `paragraph_id` VARCHAR(150) NULL;

