--liquibase formatted sql
--changeset mahaexam:add-board-state-mapping-table

ALTER TABLE teacher ADD COLUMN pan_number VARCHAR(20);

