--liquibase formatted sql
--changeset copilot:92_academic_year_add_board_id

-- Add board_id column to academic_year table
SET @col := (
  SELECT COUNT(*)
  FROM information_schema.COLUMNS
  WHERE table_schema = DATABASE()
    AND table_name = 'academic_year'
    AND column_name = 'board_id'
);

SET @sql := IF(@col = 0,
  'ALTER TABLE academic_year ADD COLUMN board_id INT DEFAULT NULL',
  'SELECT 1');

PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

-- Add foreign key constraint to board table
SET @fk := (
    SELECT COUNT(*)
    FROM information_schema.TABLE_CONSTRAINTS
    WHERE CONSTRAINT_SCHEMA = DATABASE()
      AND TABLE_NAME = 'academic_year'
      AND CONSTRAINT_NAME = 'fk_academic_year_board'
      AND CONSTRAINT_TYPE = 'FOREIGN KEY'
);

SET @sql := IF(
    @fk = 0,
    'ALTER TABLE academic_year
        ADD CONSTRAINT fk_academic_year_board
        FOREIGN KEY (board_id) REFERENCES board(id)',
    'SELECT 1'
);

PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;
