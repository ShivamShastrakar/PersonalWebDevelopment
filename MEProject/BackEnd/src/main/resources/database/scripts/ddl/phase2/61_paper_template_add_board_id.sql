--liquibase formatted sql
--changeset {dishika}:{add-board-id-to-paper-template}

-- Add board_id column to paper_template table
SET @col := (
  SELECT COUNT(*)
  FROM information_schema.COLUMNS
  WHERE table_schema = DATABASE()
    AND table_name = 'paper_template'
    AND column_name = 'board_id'
);

SET @sql := IF(@col = 0,
  'ALTER TABLE paper_template ADD COLUMN board_id INT',
  'SELECT 1');

PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

-- Add foreign key constraint to board table
SET @fk := (
    SELECT COUNT(*)
    FROM information_schema.TABLE_CONSTRAINTS
    WHERE CONSTRAINT_SCHEMA = DATABASE()
      AND TABLE_NAME = 'paper_template'
      AND CONSTRAINT_NAME = 'fk_paper_template_board'
      AND CONSTRAINT_TYPE = 'FOREIGN KEY'
);

SET @sql := IF(
    @fk = 0,
    'ALTER TABLE paper_template
        ADD CONSTRAINT fk_paper_template_board_1
        FOREIGN KEY (board_id) REFERENCES board(id)',
    'SELECT 1'
);

PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;


