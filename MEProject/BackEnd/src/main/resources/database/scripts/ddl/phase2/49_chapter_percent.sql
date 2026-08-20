--liquibase formatted sql
--changeset {narendra}:{id}

ALTER TABLE `chapters`
ADD COLUMN percent INT;