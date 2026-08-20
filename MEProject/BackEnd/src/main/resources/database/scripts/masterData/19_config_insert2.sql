--liquibase formatted sql
--changeset {narendra}:{id}

INSERT INTO config
(name, value, created_at, deleted)
VALUES('AZ_S3_BUCKET_NAME', 'enrol-me', CURRENT_TIMESTAMP, '0');

INSERT INTO config
(name, value, created_at, deleted)
VALUES('AZ_S3_BUCKET_PACKAGE_IMG_FOLDER', 'package', CURRENT_TIMESTAMP, '0');

