--liquibase formatted sql
--changeset {narendra}:{id}

INSERT INTO config
(name, value, created_at, deleted)
VALUES('PAYU_INVOICE_PREFIX', 'INV', CURRENT_TIMESTAMP, '0');

