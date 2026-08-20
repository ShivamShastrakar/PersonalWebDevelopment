--liquibase formatted sql
--changeset {narendra}:{id}

-- Alter status column in upload_batch table
ALTER TABLE upload_batch
MODIFY COLUMN status VARCHAR(50);
