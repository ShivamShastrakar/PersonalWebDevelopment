--liquibase formatted sql
--changeset {narendra}:{id}

INSERT INTO config
(name, value, created_at, deleted)
VALUES('AZ_S3_BUCKET_PHOTO_IMG_FOLDER', 'photo', CURRENT_TIMESTAMP, '0');

