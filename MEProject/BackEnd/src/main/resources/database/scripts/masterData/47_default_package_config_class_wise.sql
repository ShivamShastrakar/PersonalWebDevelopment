--liquibase formatted sql
--changeset {narendra}:{id}

delete from config where name='DEFAULT_PACKAGE';

INSERT INTO config
(name, value, created_at, deleted)
VALUES('DEFAULT_PACKAGE_4', '0', CURRENT_TIMESTAMP, '0');

INSERT INTO config
(name, value, created_at, deleted)
VALUES('DEFAULT_PACKAGE_5', '0', CURRENT_TIMESTAMP, '0');

INSERT INTO config
(name, value, created_at, deleted)
VALUES('DEFAULT_PACKAGE_6', '0', CURRENT_TIMESTAMP, '0');

INSERT INTO config
(name, value, created_at, deleted)
VALUES('DEFAULT_PACKAGE_7', '0', CURRENT_TIMESTAMP, '0');

INSERT INTO config
(name, value, created_at, deleted)
VALUES('DEFAULT_PACKAGE_8', '0', CURRENT_TIMESTAMP, '0');

INSERT INTO config
(name, value, created_at, deleted)
VALUES('DEFAULT_PACKAGE_9', '0', CURRENT_TIMESTAMP, '0');

INSERT INTO config
(name, value, created_at, deleted)
VALUES('DEFAULT_PACKAGE_10', '0', CURRENT_TIMESTAMP, '0');