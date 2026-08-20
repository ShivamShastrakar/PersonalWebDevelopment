--liquibase formatted sql
--changeset {narendra}:{id}

ALTER TABLE  subject_board_class_mapping ADD COLUMN medium varchar(30) default 'English';
ALTER TABLE  temp_students ADD COLUMN medium varchar(30) default 'English';