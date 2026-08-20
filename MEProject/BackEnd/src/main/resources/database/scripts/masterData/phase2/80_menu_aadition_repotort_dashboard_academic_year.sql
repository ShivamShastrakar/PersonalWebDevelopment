--liquibase formatted sql
--changeset {narendra}:{id}

--Dashboard
INSERT INTO menus (menu_id, name, parent_id, order_index,`path`)
    values (189, 'Dashboard', 128, 1,'/reporting/dashboard');
      
 -- for MahaAdmin
 INSERT INTO role_menu_permission (role_id, menu_id, permission_id)
 values (2, 189, 1), (2, 189, 4),
        (2, 189, 5), (2, 189, 22);

 -- for TechAdmin
 INSERT INTO role_menu_permission (role_id, menu_id, permission_id)
 values (1, 189, 1), (1, 189, 4),
        (1, 189, 5), (1, 189, 22);

delete from menu_feature_toggle where menu_id=128;

delete from role_menu_permission where menu_id=128 and role_id in(4,5);


--Academic Year
INSERT INTO menus (menu_id, name, parent_id, order_index,`path`)
values (190, 'Academic Year', 15, 0,'/organization/academics/academic-year');

-- for MahaAdmin
INSERT INTO role_menu_permission (role_id, menu_id, permission_id)
values (2, 190, 1), (2, 190, 4),
       (2, 190, 5), (2, 190, 22);

-- for TechAdmin
INSERT INTO role_menu_permission (role_id, menu_id, permission_id)
values (1, 190, 1), (1, 190, 4),
       (1, 190, 5), (1, 190, 22);

