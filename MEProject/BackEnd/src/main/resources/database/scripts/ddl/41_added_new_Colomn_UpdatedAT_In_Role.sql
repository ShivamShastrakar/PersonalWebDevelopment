--liquibase formatted sql
--changeset {narendra}:{id}

ALTER TABLE role ADD COLUMN  updated_at datetime NULL DEFAULT NULL;
