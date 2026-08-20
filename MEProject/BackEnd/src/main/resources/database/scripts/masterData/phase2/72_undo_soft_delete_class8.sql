--liquibase formatted sql
--changeset {narendra}:{id}

UPDATE `class`
SET deleted = '0',
    deleted_at = CURRENT_TIMESTAMP()
WHERE id IN (
    SELECT id
    FROM (
             SELECT id FROM `class` WHERE class_name IN ('8')
         ) AS tmp
);
