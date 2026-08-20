--liquibase formatted sql
--changeset {narendra}:{id}

ALTER TABLE subject DROP INDEX uk_subject_name_tenant;

ALTER TABLE board DROP INDEX uk_board_name_tenant;
