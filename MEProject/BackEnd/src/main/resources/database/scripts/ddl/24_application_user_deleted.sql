--liquibase formatted sql
--changeset {narendra}:{id}

ALTER TABLE application_user ADD COLUMN `deleted` enum('1','0') NOT NULL DEFAULT '0';
