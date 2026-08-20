--liquibase formatted sql
--changeset narendra:84_alter_table_Commission_config_packageCategory

-- Add package_category_id column to commission_config table
ALTER TABLE commission_config
ADD COLUMN package_category_id INT(2) DEFAULT NULL;

-- Ensure no orphan values before adding FK constraint
UPDATE commission_config SET package_category_id = NULL WHERE package_category_id IS NOT NULL AND package_category_id NOT IN (SELECT id FROM package_category);

-- Add foreign key constraint referencing package_category table
ALTER TABLE commission_config
ADD CONSTRAINT fk_commission_config_package_category
FOREIGN KEY (package_category_id) REFERENCES package_category(id);
