--liquibase formatted sql
--changeset {narendra}:{id}


ALTER TABLE board
ADD UNIQUE KEY uk_board_name (board_name);

ALTER TABLE class
ADD UNIQUE KEY uk_class_name (class_name);

ALTER TABLE subject
ADD UNIQUE KEY uk_subject_name (subject_name);