--liquibase formatted sql
--changeset Narendra:73_student_attempt_add_summary_id

ALTER TABLE student_question_attempt ADD COLUMN summary_id BIGINT;

ALTER TABLE student_question_attempt
    ADD CONSTRAINT fk_attempt_summary
        FOREIGN KEY (summary_id) REFERENCES student_subject_summary(id)
            ON DELETE CASCADE ON UPDATE CASCADE;
