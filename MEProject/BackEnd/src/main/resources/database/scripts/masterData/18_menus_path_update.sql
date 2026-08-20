--liquibase formatted sql
--changeset {narendra}:{id}


UPDATE menus m
LEFT JOIN menus m2 ON m.parent_id = m2.menu_id
LEFT JOIN menus m3 ON m2.parent_id = m3.menu_id
LEFT JOIN (
    SELECT parent_id, COUNT(*) AS child_count
    FROM menus
    GROUP BY parent_id
) children ON m.menu_id = children.parent_id
SET m.path = 
    CASE
        -- Set path to NULL if the menu has children (child_count > 0) or is top-level (parent_id IS NULL)
        WHEN children.child_count > 0 OR m.parent_id IS NULL THEN NULL
        -- Sub-menu without children (parent_id is not null, m2.parent_id is null)
        WHEN m2.parent_id IS NULL AND m.parent_id IS NOT NULL THEN
            CONCAT(
                LOWER(REPLACE(m2.name, ' ', '-')), '/',
                LOWER(REPLACE(m.name, ' ', '-'))
            )
        -- Sub-sub-menu without children (m2.parent_id is not null)
        ELSE
            CONCAT(
                LOWER(REPLACE(m3.name, ' ', '-')), '/',
                LOWER(REPLACE(m2.name, ' ', '-')), '/',
                LOWER(REPLACE(m.name, ' ', '-'))
            )
    end
   where 1=1;