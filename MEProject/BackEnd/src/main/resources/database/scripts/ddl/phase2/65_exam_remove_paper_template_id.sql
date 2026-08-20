--liquibase formatted sql
--changeset narendra:remove-paper-template-id-from-exam

-- Drop foreign key constraint first
SET @fk_name = (
  SELECT CONSTRAINT_NAME
  FROM information_schema.TABLE_CONSTRAINTS
  WHERE TABLE_NAME = 'exam'
    AND CONSTRAINT_TYPE = 'FOREIGN KEY'
    AND CONSTRAINT_NAME = 'fk_exam_template'
    AND TABLE_SCHEMA = DATABASE()
);

SET @sql = IF(@fk_name IS NOT NULL,
              'ALTER TABLE exam DROP FOREIGN KEY fk_exam_template;',
              'SELECT "Foreign key does not exist";');

PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;


-- Remove paper_template_id column from exam table
ALTER TABLE exam DROP COLUMN paper_template_id;
