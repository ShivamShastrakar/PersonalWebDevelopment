--liquibase formatted sql
--changeset {narendra}:{id}

ALTER TABLE packages
    ADD subscriptiontype VARCHAR(50) NULL;
