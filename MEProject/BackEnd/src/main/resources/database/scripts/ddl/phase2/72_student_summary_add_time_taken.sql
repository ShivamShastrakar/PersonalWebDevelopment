--liquibase formatted sql
--changeset Narendra:72_student_summary_add_time_taken

ALTER TABLE student_subject_summary ADD COLUMN time_taken INT DEFAULT NULL;
