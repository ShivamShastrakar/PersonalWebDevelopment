--liquibase formatted sql
--changeset {narendra}:{id}

ALTER TABLE commission_config
DROP INDEX uk_commission_hierarchy_pkg;

ALTER TABLE commission_config
ADD UNIQUE KEY uk_commission_hierarchy_pkg 
(hierarchy_level_id, package_category_id, is_active);
