--liquibase formatted sql
--changeset {narendra}:{id}


-- Drop exam if exists
SET @tbl2 := (
  SELECT COUNT(*)
  FROM information_schema.TABLES
  WHERE table_schema = DATABASE()
    AND table_name = 'exam'
);

SET @sql2 := IF(@tbl2 = 1,
  'DROP TABLE exam',
  'SELECT 1');

PREPARE stmt2 FROM @sql2;
EXECUTE stmt2;
DEALLOCATE PREPARE stmt2;

CREATE TABLE exam (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    exam_name VARCHAR(150) NOT NULL,
    paper_template_id BIGINT NOT NULL,
    academic_year VARCHAR(9) NOT NULL,
    status VARCHAR(20) DEFAULT 'ACTIVE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

