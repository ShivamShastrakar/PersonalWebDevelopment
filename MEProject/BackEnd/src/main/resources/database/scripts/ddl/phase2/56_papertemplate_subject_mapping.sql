--liquibase formatted sql
--changeset {narendra}:{id}
-- Drop paper_template_subject if exists
SET @tbl1 := (
  SELECT COUNT(*)
  FROM information_schema.TABLES
  WHERE table_schema = DATABASE()
    AND table_name = 'paper_template_subject'
);

SET @sql1 := IF(@tbl1 = 1,
  'DROP TABLE paper_template_subject',
  'SELECT 1');

PREPARE stmt1 FROM @sql1;
EXECUTE stmt1;
DEALLOCATE PREPARE stmt1;

CREATE TABLE paper_template_subject (
    paper_template_id BIGINT REFERENCES paper_template(id),
    subject_id BIGINT REFERENCES subject(subject_id),
    PRIMARY KEY (paper_template_id, subject_id)
);

