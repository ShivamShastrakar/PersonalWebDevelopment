--liquibase formatted sql
--changeset {narendra}:{id}

INSERT INTO config
(name, value, created_at, deleted)
VALUES('AZ_S3_BUCKET_BULK_UPLOAD_FOLDER', 'bulk-upload', CURRENT_TIMESTAMP, '0');

