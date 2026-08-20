--liquibase formatted sql
--changeset {narendra}:{id}

ALTER TABLE payment_transactions
ADD COLUMN payment_link_id VARCHAR(255) NULL,
ADD COLUMN remark VARCHAR(500) NULL;
