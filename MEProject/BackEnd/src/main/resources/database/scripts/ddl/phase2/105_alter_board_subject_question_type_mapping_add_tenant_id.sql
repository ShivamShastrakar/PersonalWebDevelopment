--liquibase formatted sql

--changeset dishika:104_alter_board_subject_question_type_mapping_add_tenant_id

-- 1. Add tenant_id column if not exists
SET @col_exists = (
    SELECT COUNT(*)
    FROM INFORMATION_SCHEMA.COLUMNS
    WHERE TABLE_NAME = 'board_subject_question_type_mapping'
      AND COLUMN_NAME = 'tenant_id'
);

SET @sql = IF(@col_exists = 0,
    'ALTER TABLE board_subject_question_type_mapping
     ADD COLUMN tenant_id BIGINT UNSIGNED NULL AFTER id;',
    'SELECT "tenant_id column already exists";'
);
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;


-- 2. Create index if not exists
SET @idx_exists = (
    SELECT COUNT(*)
    FROM INFORMATION_SCHEMA.STATISTICS
    WHERE TABLE_NAME = 'board_subject_question_type_mapping'
      AND INDEX_NAME = 'idx_bsqm_tenant_id'
);

SET @sql = IF(@idx_exists = 0,
    'CREATE INDEX idx_bsqm_tenant_id
     ON board_subject_question_type_mapping(tenant_id);',
    'SELECT "index already exists";'
);
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;


-- 3. Add FK tenant if not exists
SET @fk_exists = (
    SELECT COUNT(*)
    FROM INFORMATION_SCHEMA.REFERENTIAL_CONSTRAINTS
    WHERE CONSTRAINT_NAME = 'fk_bsqm_tenant'
      AND TABLE_NAME = 'board_subject_question_type_mapping'
);

SET @sql = IF(@fk_exists = 0,
    'ALTER TABLE board_subject_question_type_mapping
     ADD CONSTRAINT fk_bsqm_tenant
     FOREIGN KEY (tenant_id) REFERENCES tenant(tenant_id);',
    'SELECT "fk_bsqm_tenant already exists";'
);
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;


-- 4. Drop old foreign keys if exist
SET @fk = (
    SELECT COUNT(*) FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS
    WHERE CONSTRAINT_NAME = 'fk_bsqm_board'
      AND TABLE_NAME = 'board_subject_question_type_mapping'
);
SET @sql = IF(@fk > 0,
    'ALTER TABLE board_subject_question_type_mapping DROP FOREIGN KEY fk_bsqm_board;',
    'SELECT "fk_bsqm_board not exists";'
);
PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;


SET @fk = (
    SELECT COUNT(*) FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS
    WHERE CONSTRAINT_NAME = 'fk_bsqm_subject'
      AND TABLE_NAME = 'board_subject_question_type_mapping'
);
SET @sql = IF(@fk > 0,
    'ALTER TABLE board_subject_question_type_mapping DROP FOREIGN KEY fk_bsqm_subject;',
    'SELECT "fk_bsqm_subject not exists";'
);
PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;


SET @fk = (
    SELECT COUNT(*) FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS
    WHERE CONSTRAINT_NAME = 'fk_bsqm_question_type'
      AND TABLE_NAME = 'board_subject_question_type_mapping'
);
SET @sql = IF(@fk > 0,
    'ALTER TABLE board_subject_question_type_mapping DROP FOREIGN KEY fk_bsqm_question_type;',
    'SELECT "fk_bsqm_question_type not exists";'
);
PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;


-- 5. Drop old unique index if exists
SET @uk_exists = (
    SELECT COUNT(*)
    FROM INFORMATION_SCHEMA.STATISTICS
    WHERE TABLE_NAME = 'board_subject_question_type_mapping'
      AND INDEX_NAME = 'uk_board_subject_qtype'
);

SET @sql = IF(@uk_exists > 0,
    'ALTER TABLE board_subject_question_type_mapping DROP INDEX uk_board_subject_qtype;',
    'SELECT "unique index not exists";'
);
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;


-- 6. Add new unique constraint
SET @uk_exists = (
    SELECT COUNT(*)
    FROM INFORMATION_SCHEMA.STATISTICS
    WHERE TABLE_NAME = 'board_subject_question_type_mapping'
      AND INDEX_NAME = 'uk_bsqm_tenant_board_subject_qtype'
);

SET @sql = IF(@uk_exists = 0,
    'ALTER TABLE board_subject_question_type_mapping
     ADD CONSTRAINT uk_bsqm_tenant_board_subject_qtype
     UNIQUE (tenant_id, board_id, subject_id, question_type_id);',
    'SELECT "new unique constraint already exists";'
);
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;


-- 7. Recreate foreign keys safely

-- board FK
SET @fk_exists = (
    SELECT COUNT(*) FROM INFORMATION_SCHEMA.REFERENTIAL_CONSTRAINTS
    WHERE CONSTRAINT_NAME = 'fk_bsqm_board'
      AND TABLE_NAME = 'board_subject_question_type_mapping'
);

SET @sql = IF(@fk_exists = 0,
    'ALTER TABLE board_subject_question_type_mapping
     ADD CONSTRAINT fk_bsqm_board
     FOREIGN KEY (board_id) REFERENCES board(id) ON DELETE CASCADE;',
    'SELECT "fk_bsqm_board already exists";'
);
PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;


-- subject FK
SET @fk_exists = (
    SELECT COUNT(*) FROM INFORMATION_SCHEMA.REFERENTIAL_CONSTRAINTS
    WHERE CONSTRAINT_NAME = 'fk_bsqm_subject'
      AND TABLE_NAME = 'board_subject_question_type_mapping'
);

SET @sql = IF(@fk_exists = 0,
    'ALTER TABLE board_subject_question_type_mapping
     ADD CONSTRAINT fk_bsqm_subject
     FOREIGN KEY (subject_id) REFERENCES subject(subject_id) ON DELETE CASCADE;',
    'SELECT "fk_bsqm_subject already exists";'
);
PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;


-- question type FK
SET @fk_exists = (
    SELECT COUNT(*) FROM INFORMATION_SCHEMA.REFERENTIAL_CONSTRAINTS
    WHERE CONSTRAINT_NAME = 'fk_bsqm_question_type'
      AND TABLE_NAME = 'board_subject_question_type_mapping'
);

SET @sql = IF(@fk_exists = 0,
    'ALTER TABLE board_subject_question_type_mapping
     ADD CONSTRAINT fk_bsqm_question_type
     FOREIGN KEY (question_type_id) REFERENCES question_type(id) ON DELETE CASCADE;',
    'SELECT "fk_bsqm_question_type already exists";'
);
PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;