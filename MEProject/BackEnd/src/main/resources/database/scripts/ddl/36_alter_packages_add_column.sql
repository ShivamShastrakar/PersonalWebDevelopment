--liquibase formatted sql
--changeset {narendra}:{id}


ALTER TABLE packages ADD COLUMN no_of_mock_exams int(2) default 0;
ALTER TABLE packages ADD COLUMN no_of_pactice_exams int(2) default 0;
ALTER TABLE packages ADD COLUMN no_of_bonus_exams int(2) default 0;
     