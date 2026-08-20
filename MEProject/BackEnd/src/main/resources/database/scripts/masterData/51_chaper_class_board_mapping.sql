--liquibase formatted sql
--changeset {narendra}:{id}


-- File: chapter_mapping_migration_and_triggers.sql
-- Purpose:
-- 1) Clean duplicates and add a uniqueness constraint on chapter_board_class_mapping
-- 2) Populate chapter_board_class_mapping from topics (distinct triples)
SET autocommit = 0;
START TRANSACTION;


-- 1) Remove exact duplicate rows (keep lowest id)
DELETE c1
FROM chapter_board_class_mapping c1
INNER JOIN chapter_board_class_mapping c2
  ON (c1.chapter_id <=> c2.chapter_id)
 AND (c1.class_id   <=> c2.class_id)
 AND (c1.board_id   <=> c2.board_id)
 AND c1.id > c2.id;

-- 2) Conditionally add a UNIQUE index on (chapter_id, class_id, board_id)
-- use a prepared statement to avoid errors if index already exists
SELECT COUNT(*) INTO @idx_exists
FROM INFORMATION_SCHEMA.STATISTICS
WHERE TABLE_SCHEMA = DATABASE()
  AND TABLE_NAME = 'chapter_board_class_mapping'
  AND INDEX_NAME = 'uniq_chapter_class_board';

SET @sql_stmt = IF(@idx_exists = 0,
  'ALTER TABLE chapter_board_class_mapping ADD UNIQUE INDEX uniq_chapter_class_board (chapter_id, class_id, board_id);',
  'SELECT 1;');

PREPARE stmt FROM @sql_stmt;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

-- 3) Populate mapping from topics (distinct non-null triples)
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id)
SELECT DISTINCT chapter_id, class_id, board_id
FROM topics
WHERE chapter_id IS NOT NULL
  AND class_id IS NOT NULL
  AND board_id IS NOT NULL;

COMMIT;
SET autocommit = 1;



