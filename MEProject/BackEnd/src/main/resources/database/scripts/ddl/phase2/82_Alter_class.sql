--liquibase formatted sql
--changeset {narendra}:{id}
ALTER TABLE class ADD COLUMN is_exam_group_required TINYINT(1) DEFAULT 0;

