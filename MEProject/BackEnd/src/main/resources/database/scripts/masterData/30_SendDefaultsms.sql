--liquibase formatted sql
--changeset {narendra}:{id}

INSERT INTO config
(name, value, created_at,  deleted)
VALUES('DEFAULT_SMS', '1', CURRENT_TIMESTAMP, '0');
