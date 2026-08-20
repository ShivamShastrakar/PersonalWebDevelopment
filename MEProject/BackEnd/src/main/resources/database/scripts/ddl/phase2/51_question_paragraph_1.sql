--liquibase formatted sql
--changeset {narendra}:{id}

ALTER TABLE `questions`
ADD COLUMN `paragraph_text` TEXT NULL;
