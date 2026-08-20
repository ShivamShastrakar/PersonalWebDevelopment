--liquibase formatted sql
--changeset {narendra}:{id}

INSERT INTO config
(name, value, created_at, deleted)
VALUES('ENABLE_SMS', '1', CURRENT_TIMESTAMP, '0');

INSERT INTO config
(name, value, created_at, deleted)
VALUES('ENABLE_EMAIL', '1', CURRENT_TIMESTAMP, '0');

INSERT INTO config
(name, value, created_at, deleted)
VALUES('SMS_API_KEY', 'qaBrKdSwW64-yHFLHJn25L9OYmwnFmgPMnqURHNu5E', CURRENT_TIMESTAMP, '0');

INSERT INTO config
(name, value, created_at, deleted)
VALUES('SMS_API_URL', 'https://api.textlocal.in/send/?', CURRENT_TIMESTAMP, '0');

INSERT INTO config
(name, value, created_at, deleted)
VALUES('SMS_API_SENDER', 'ENRLME', CURRENT_TIMESTAMP, '0');
