--liquibase formatted sql
--changeset narendra:100_altertable_packageForExamGroupdtl_step1
-- Clear ALL existing string values before changing column type to INT
UPDATE packages SET pkg_exam_group = NULL;

--changeset narendra:100_altertable_packageForExamGroupdtl_step2
-- Modify pkg_exam_group column to Integer with default NULL
ALTER TABLE packages MODIFY COLUMN pkg_exam_group INT(2) DEFAULT NULL;

--changeset narendra:100_altertable_packageForExamGroupdtl_step3
-- Add foreign key constraint referencing exam_group_dtls table
ALTER TABLE packages ADD CONSTRAINT fk_package_examgroup_id FOREIGN KEY (pkg_exam_group) REFERENCES exam_group_dtls(id);
