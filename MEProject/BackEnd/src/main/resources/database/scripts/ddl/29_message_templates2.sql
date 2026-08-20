--liquibase formatted sql
--changeset {narendra}:{id}

ALTER TABLE message_templates
    ADD COLUMN  sms_template_id VARCHAR(255) NULL;

