--liquibase formatted sql
--changeset narendra:104_altertable_commission_config_ExamGroupdtl

ALTER TABLE commission_config
ADD COLUMN `exam_groupId` INT(2) DEFAULT 1 NOT NULL;

ALTER TABLE commission_config
ADD KEY `fk_commission_config_examgroup_id` (`exam_groupId`),
ADD CONSTRAINT `fk_commission_config_examgroup_id` FOREIGN KEY (`exam_groupId`) REFERENCES `exam_group_dtls` (`id`);

-- Step 2: Drop existing unique key
ALTER TABLE commission_config 
DROP INDEX uk_commission_hierarchy_pkg;

-- Step 3: Create new unique key including examgroupId
ALTER TABLE commission_config 
ADD UNIQUE KEY uk_commission_hierarchy_pkg 
(hierarchy_level_id, package_category_id, is_active, exam_groupId);