--liquibase formatted sql
--changeset {narendra}:{id}

INSERT INTO menus (menu_id, name, parent_id, order_index,`path`) VALUES (188, 'Hierarchy Levels', 14, 9,'organization/user-hierarchy-levels');

-- for MahaAdmin

INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES
                                                                       (2, 188, 1), (2, 188, 4), (2, 188, 5), (2, 188, 22);
-- For Tech Admin

INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES
                                                                       (1, 188, 1), (1, 188, 4), (1, 188, 5), (1, 188, 22);