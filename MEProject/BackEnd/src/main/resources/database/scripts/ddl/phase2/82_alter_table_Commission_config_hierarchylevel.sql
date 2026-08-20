--liquibase formatted sql
--changeset admin:82_alter_table_Commission_config_hierarchylevel

-- Drop the existing foreign key constraint first (if it exists)
-- Check if constraint exists before dropping
SET @constraint_exists = 0;
SELECT COUNT(*) INTO @constraint_exists FROM INFORMATION_SCHEMA.KEY_COLUMN_USAGE 
WHERE TABLE_NAME='commission_config' AND COLUMN_NAME='role_id' AND REFERENCED_TABLE_NAME='role';

-- Only drop if the constraint exists
SET @sql = IF(@constraint_exists > 0, 'ALTER TABLE commission_config DROP CONSTRAINT comission_config_role_fk', 'SELECT 1');
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

-- Drop the existing unique key constraint (can be dropped now that FK is gone if it exists)
SET @unique_exists = 0;
SELECT COUNT(*) INTO @unique_exists FROM INFORMATION_SCHEMA.STATISTICS 
WHERE TABLE_NAME='commission_config' AND INDEX_NAME='uk_commission_role_pkg';

SET @sql = IF(@unique_exists > 0, 'ALTER TABLE commission_config DROP INDEX uk_commission_role_pkg', 'SELECT 1');
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

-- Drop the role_id column if it exists
SET @column_exists = 0;
SELECT COUNT(*) INTO @column_exists FROM INFORMATION_SCHEMA.COLUMNS 
WHERE TABLE_NAME='commission_config' AND COLUMN_NAME='role_id';

SET @sql = IF(@column_exists > 0, 'ALTER TABLE commission_config DROP COLUMN role_id', 'SELECT 1');
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

-- Add new hierarchy_level_id column if it doesn't exist
SET @hierarchy_exists = 0;
SELECT COUNT(*) INTO @hierarchy_exists FROM INFORMATION_SCHEMA.COLUMNS 
WHERE TABLE_NAME='commission_config' AND COLUMN_NAME='hierarchy_level_id';

SET @sql = IF(@hierarchy_exists = 0, 'ALTER TABLE commission_config ADD COLUMN hierarchy_level_id INT DEFAULT NULL AFTER id', 'SELECT 1');
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

-- Add foreign key constraint for hierarchy_level_id if it doesn't exist
SET @fk_exists = 0;
SELECT COUNT(*) INTO @fk_exists FROM INFORMATION_SCHEMA.KEY_COLUMN_USAGE 
WHERE TABLE_NAME='commission_config' AND COLUMN_NAME='hierarchy_level_id' AND REFERENCED_TABLE_NAME='user_hierarchy_level';

SET @sql = IF(@fk_exists = 0, 'ALTER TABLE commission_config ADD CONSTRAINT fk_commission_config_hierarchy_level FOREIGN KEY (hierarchy_level_id) REFERENCES user_hierarchy_level(id) ON DELETE SET NULL ON UPDATE CASCADE', 'SELECT 1');
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

-- Add new unique key constraint if it doesn't exist
SET @uk_exists = 0;
SELECT COUNT(*) INTO @uk_exists FROM INFORMATION_SCHEMA.STATISTICS 
WHERE TABLE_NAME='commission_config' AND INDEX_NAME='uk_commission_hierarchy_pkg';

SET @sql = IF(@uk_exists = 0, 'ALTER TABLE commission_config ADD CONSTRAINT uk_commission_hierarchy_pkg UNIQUE (hierarchy_level_id, package_type, is_active)', 'SELECT 1');
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

-- Create index on hierarchy_level_id if it doesn't exist
SET @idx_exists = 0;
SELECT COUNT(*) INTO @idx_exists FROM INFORMATION_SCHEMA.STATISTICS 
WHERE TABLE_NAME='commission_config' AND INDEX_NAME='idx_commission_config_hierarchy_level_id';

SET @sql = IF(@idx_exists = 0, 'CREATE INDEX idx_commission_config_hierarchy_level_id ON commission_config(hierarchy_level_id)', 'SELECT 1');
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;
