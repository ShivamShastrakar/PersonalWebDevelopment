--liquibase formatted sql
--changeset {narendra}:{id}

ALTER TABLE payment_transactions
    ADD batch_id BIGINT NULL;


ALTER TABLE upload_batch
    ADD COLUMN payment_transaction_id BIGINT NULL;
