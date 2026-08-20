--liquibase formatted sql
--changeset {dishika}:{id}

-- Drop sections if exists
SET @tbl1 := (
  SELECT COUNT(*)
  FROM information_schema.TABLES
  WHERE table_schema = DATABASE()
    AND table_name = 'sections'
);

SET @sql1 := IF(@tbl1 = 1,
  'DROP TABLE sections',
  'SELECT 1');

PREPARE stmt1 FROM @sql1;
EXECUTE stmt1;
DEALLOCATE PREPARE stmt1;

-- Drop part if exists
SET @tbl1 := (
  SELECT COUNT(*)
  FROM information_schema.TABLES
  WHERE table_schema = DATABASE()
    AND table_name = 'part'
);

SET @sql1 := IF(@tbl1 = 1,
  'DROP TABLE part',
  'SELECT 1');

PREPARE stmt1 FROM @sql1;
EXECUTE stmt1;
DEALLOCATE PREPARE stmt1;

CREATE TABLE part (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    paper_template_id BIGINT REFERENCES paper_template(id),
    name VARCHAR(50) NOT NULL,
    display_name BOOLEAN DEFAULT TRUE,
    subject_id BIGINT REFERENCES subject(subject_id),
    display_subject BOOLEAN DEFAULT TRUE,
    number_of_sections INT NOT NULL
);

