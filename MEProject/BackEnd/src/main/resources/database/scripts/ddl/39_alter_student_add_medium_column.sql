--liquibase formatted sql
--changeset {narendra}:{id}

ALTER TABLE  student ADD COLUMN medium varchar(30) default 'English';     