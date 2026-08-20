--liquibase formatted sql
--changeset narendra:100_altertable_packageForExamGroupdtl_step1
-- Clear ALL existing string values before changing column type to INT

UPDATE packages 
SET pkg_exam_group = (
    CASE 
        WHEN EXISTS (SELECT 1 FROM exam_group_dtls WHERE id = 1)
        THEN (SELECT id FROM exam_group_dtls WHERE id = 1 LIMIT 1)
        ELSE NULL
    END
);