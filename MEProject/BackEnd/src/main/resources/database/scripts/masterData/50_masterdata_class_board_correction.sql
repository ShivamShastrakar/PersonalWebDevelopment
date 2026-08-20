--liquibase formatted sql
--changeset {narendra}:{id}

-- `chapters`- `subject_id` ,`class`

 -- INSERT INTO topics (topic_id,	 topic_name,	 chapter_id,	 subject_id,	 class_id,	 board_id)

 -- Script: update_chapters_from_topics_mysql.sql
 -- Purpose:
 -- 1) Add class_id and board_id columns to chapters if they don't exist
 -- 2) Populate chapters.subject_id, chapters.class_id, chapters.board_id from topics (one value per chapter)
 -- 3) Ensure subject_board_class_mapping contains the distinct (subject_id, class_id, board_id) triples
 --
 -- Notes:
 -- - This is written to be MySQL-compatible (uses INFORMATION_SCHEMA + PREPARE for conditional DDL).
 -- - It uses MAX() to pick a non-NULL value per chapter from topics. If you want a different tie-breaker
 --   (most frequent, earliest, etc.) tell me and I can change the aggregation logic.
 -- - Test on a staging DB before running in production.
 -- - If you have a very large dataset, consider running in smaller batches or during a maintenance window.

 SET autocommit = 0;
 START TRANSACTION;

 -- 0) Quick backup of chapters (remove if you already have backups)
 DROP TABLE IF EXISTS chapters_backup;
 CREATE TABLE chapters_backup AS SELECT * FROM chapters;

 -- 1) Add columns class_id and board_id to chapters if they do not exist
 SELECT IF(
   (SELECT COUNT(*) FROM INFORMATION_SCHEMA.COLUMNS
      WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'chapters' AND COLUMN_NAME = 'class_id') = 0,
   'ALTER TABLE chapters ADD COLUMN class_id INT DEFAULT NULL;',
   'SELECT 1;'
 ) INTO @sql_stmt;
 PREPARE stmt FROM @sql_stmt;
 EXECUTE stmt;
 DEALLOCATE PREPARE stmt;

 SELECT IF(
   (SELECT COUNT(*) FROM INFORMATION_SCHEMA.COLUMNS
      WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'chapters' AND COLUMN_NAME = 'board_id') = 0,
   'ALTER TABLE chapters ADD COLUMN board_id INT DEFAULT NULL;',
   'SELECT 1;'
 ) INTO @sql_stmt;
 PREPARE stmt FROM @sql_stmt;
 EXECUTE stmt;
 DEALLOCATE PREPARE stmt;

 -- 2) Aggregate values from topics for each chapter (pick MAX non-NULL value per field)
 DROP TEMPORARY TABLE IF EXISTS tmp_chapter_topic;
 CREATE TEMPORARY TABLE tmp_chapter_topic AS
 SELECT
   chapter_id,
   MAX(subject_id) AS subject_id,
   MAX(class_id)   AS class_id,
   MAX(board_id)   AS board_id
 FROM topics
 GROUP BY chapter_id;

 -- 3) Update chapters from aggregated topic values.
 --    Use the topic values to overwrite chapters (you can change to only set when chapters.* IS NULL if desired).
 UPDATE chapters c
 JOIN tmp_chapter_topic t ON c.id = t.chapter_id
 SET
   c.subject_id = COALESCE(t.subject_id, c.subject_id),
   c.class_id   = COALESCE(t.class_id, c.class_id),
   c.board_id   = COALESCE(t.board_id, c.board_id)
 WHERE (t.subject_id IS NOT NULL OR t.class_id IS NOT NULL OR t.board_id IS NOT NULL);

 -- 4) Remove duplicate rows in subject_board_class_mapping (keep lowest id for each triple)
 DELETE s1
 FROM subject_board_class_mapping s1
 INNER JOIN subject_board_class_mapping s2
   ON s1.subject_id = s2.subject_id
  AND (s1.class_id <=> s2.class_id) -- <=> handles NULL-safe equality
  AND (s1.board_id <=> s2.board_id)
  AND s1.id > s2.id;

 -- 5) Add a UNIQUE index on (subject_id, class_id, board_id) if it doesn't already exist.
 SELECT IF(
   (SELECT COUNT(*) FROM INFORMATION_SCHEMA.STATISTICS
      WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'subject_board_class_mapping' AND INDEX_NAME = 'uniq_subject_class_board') = 0,
   'ALTER TABLE subject_board_class_mapping ADD CONSTRAINT uniq_subject_class_board UNIQUE (subject_id, class_id, board_id);',
   'SELECT 1;'
 ) INTO @sql_stmt;
 PREPARE stmt FROM @sql_stmt;
 EXECUTE stmt;
 DEALLOCATE PREPARE stmt;

 -- 6) Insert distinct triples from chapters (only fully non-NULL triples) into mapping.
 --    Use INSERT IGNORE so duplicates are skipped (unique constraint prevents dupes).
 INSERT IGNORE INTO subject_board_class_mapping (subject_id, class_id, board_id)
 SELECT DISTINCT subject_id, class_id, board_id
 FROM chapters
 WHERE subject_id IS NOT NULL AND class_id IS NOT NULL AND board_id IS NOT NULL;

 -- 7) Also ensure triples that exist in topics but not yet in chapters are present in mapping
 INSERT IGNORE INTO subject_board_class_mapping (subject_id, class_id, board_id)
 SELECT DISTINCT subject_id, class_id, board_id
 FROM topics
 WHERE subject_id IS NOT NULL AND class_id IS NOT NULL AND board_id IS NOT NULL;

 COMMIT;
 SET autocommit = 1;

 -- Verification queries (run manually after script):
 -- 1) Count chapters updated from topics:
 --    SELECT COUNT(*) FROM chapters c JOIN (SELECT DISTINCT chapter_id FROM topics) t ON c.id = t.chapter_id;
 --
 -- 2) Sample chapters with new columns:
 --    SELECT id, chapter_name, subject_id, class_id, board_id FROM chapters ORDER BY id LIMIT 50;
 --
 -- 3) See new mappings:
 --    SELECT id, subject_id, class_id, board_id FROM subject_board_class_mapping ORDER BY id LIMIT 200;
 --
 -- 4) If you want only to set chapters.class_id/board_id when they are NULL (not overwrite existing), change the UPDATE to:
 --    SET c.subject_id = COALESCE(t.subject_id, c.subject_id),
 --        c.class_id   = CASE WHEN c.class_id IS NULL THEN t.class_id ELSE c.class_id END,
 --        c.board_id   = CASE WHEN c.board_id IS NULL THEN t.board_id ELSE c.board_id END;