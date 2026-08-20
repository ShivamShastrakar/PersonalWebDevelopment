--liquibase formatted sql
--changeset copilot:add-student-parallel-reservation-column
ALTER TABLE student ADD COLUMN parallel_reservation VARCHAR(64) DEFAULT NULL;
