--liquibase formatted sql
--changeset {narendra}:{id}

ALTER TABLE `questions`
ADD COLUMN paragraphId varchar(150);
