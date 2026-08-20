--liquibase formatted sql
--changeset mahaexam:add-board-state-mapping-table

ALTER TABLE student ADD COLUMN school_name VARCHAR(255);
ALTER TABLE student ADD COLUMN school_address TEXT;
