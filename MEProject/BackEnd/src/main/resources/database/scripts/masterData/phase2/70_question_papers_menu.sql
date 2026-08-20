--liquibase formatted sql
--changeset {narendra}:{id}

INSERT INTO menus (menu_id, name, parent_id, order_index,`path`) VALUES
    (186, 'Question Papers Management', 15, 7,'question-papers/list');

-- for MahaAdmin
--Syllabus Management(181): 1,4,5,22
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES
                                                                       (2, 186, 1), (2, 186, 4), (2, 186, 5), (2, 186, 22);
-- For Tech Admin
--Syllabus Management(181): 1,4,5,22
INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES
                                                                       (1, 186, 1), (1, 186, 4), (1, 186, 5), (1, 186, 22);
