--liquibase formatted sql
--changeset {narendra}:{id}

-- Add a default category if it doesn't exist
INSERT INTO `package_category` (`id`, `name`, `description`, `created_date`)
SELECT 1, 'Premium', 'Premium', CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM `package_category` WHERE `id` = 1);

-- Add the column with NULL default initially
ALTER TABLE `packages` ADD COLUMN `package_category_id` INT(2) DEFAULT NULL;

-- Update existing rows to reference the default category (ID 1)
UPDATE `packages` SET `package_category_id` = 1 WHERE `package_category_id` IS NULL;

-- Now add the foreign key constraint
ALTER TABLE `packages` ADD CONSTRAINT `fk_package_category_id` FOREIGN KEY (`package_category_id`) REFERENCES `package_category` (`id`);
