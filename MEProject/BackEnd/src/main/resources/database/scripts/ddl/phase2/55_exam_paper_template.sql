--liquibase formatted sql
--changeset {narendra}:{id}
-- Drop exam_paper_template if exists
SET @tbl1 := (
  SELECT COUNT(*)
  FROM information_schema.TABLES
  WHERE table_schema = DATABASE()
    AND table_name = 'exam_paper_template'
);

SET @sql1 := IF(@tbl1 = 1,
  'DROP TABLE exam_paper_template',
  'SELECT 1');

PREPARE stmt1 FROM @sql1;
EXECUTE stmt1;
DEALLOCATE PREPARE stmt1;

CREATE TABLE exam_paper_template (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    exam_id BIGINT NOT NULL,
    paper_template_id BIGINT NOT NULL,
    sequence INT NOT NULL,   -- order of paper in exam
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uk_exam_template
        UNIQUE (exam_id, paper_template_id),
    CONSTRAINT uk_exam_sequence
        UNIQUE (exam_id, sequence)
);
