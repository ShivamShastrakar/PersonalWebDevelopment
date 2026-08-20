--liquibase formatted sql

--changeset dishika:104_alter_board_subject_question_type_mapping_add_tenant_id

--preconditions onFail:MARK_RAN
--precondition-sql-check expectedResult:0 SELECT COUNT(*)
FROM information_schema.COLUMNS
WHERE TABLE_NAME = 'board_subject_question_type_mapping'
AND COLUMN_NAME = 'tenant_id';

ALTER TABLE board_subject_question_type_mapping
    ADD COLUMN tenant_id BIGINT UNSIGNED NULL AFTER id;

CREATE INDEX idx_bsqm_tenant_id
    ON board_subject_question_type_mapping(tenant_id);

ALTER TABLE board_subject_question_type_mapping
    ADD CONSTRAINT fk_bsqm_tenant
        FOREIGN KEY (tenant_id) REFERENCES tenant(tenant_id);

ALTER TABLE board_subject_question_type_mapping
DROP FOREIGN KEY fk_bsqm_board;

ALTER TABLE board_subject_question_type_mapping
DROP FOREIGN KEY fk_bsqm_subject;

ALTER TABLE board_subject_question_type_mapping
DROP FOREIGN KEY fk_bsqm_question_type;

ALTER TABLE board_subject_question_type_mapping
DROP INDEX uk_board_subject_qtype;

ALTER TABLE board_subject_question_type_mapping
    ADD CONSTRAINT uk_bsqm_tenant_board_subject_qtype
        UNIQUE (tenant_id, board_id, subject_id, question_type_id);

ALTER TABLE board_subject_question_type_mapping
    ADD CONSTRAINT fk_bsqm_board
        FOREIGN KEY (board_id) REFERENCES board(id) ON DELETE CASCADE;

ALTER TABLE board_subject_question_type_mapping
    ADD CONSTRAINT fk_bsqm_subject
        FOREIGN KEY (subject_id) REFERENCES subject(subject_id) ON DELETE CASCADE;

ALTER TABLE board_subject_question_type_mapping
    ADD CONSTRAINT fk_bsqm_question_type
        FOREIGN KEY (question_type_id) REFERENCES question_type(id) ON DELETE CASCADE;