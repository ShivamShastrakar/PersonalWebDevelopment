--liquibase formatted sql
--changeset {narendra}:{id}

ALTER TABLE upload_batch
    ADD COLUMN `created_by` BIGINT(20) UNSIGNED NULL,
    ADD COLUMN `tenant_id` BIGINT(20) UNSIGNED NULL,
    ADD COLUMN `entity_type` VARCHAR(25) NULL,
    ADD COLUMN `updated_at` DATETIME,
    ADD COLUMN `total_count` int  NULL,
    ADD COLUMN `success_count` int  NULL,
    ADD CONSTRAINT fk_upload_batch_created_by FOREIGN KEY (`created_by`) REFERENCES users(`user_id`),
    ADD CONSTRAINT fk_upload_batch_tenant_id FOREIGN KEY (`tenant_id`) REFERENCES tenant(`tenant_id`);

