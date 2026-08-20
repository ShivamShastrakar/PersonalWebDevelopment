--liquibase formatted sql
--changeset Narendra :update-uniq-subject-class-board-medium
ALTER TABLE subject_board_class_mapping DROP INDEX uniq_subject_class_board;
ALTER TABLE subject_board_class_mapping ADD UNIQUE INDEX uniq_subject_class_board_medium (subject_id, board_id, class_id, medium);
