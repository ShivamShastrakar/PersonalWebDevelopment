--liquibase formatted sql
--changeset {narendra}:{id}

--Registered Students
INSERT INTO menus (menu_id, name, parent_id, order_index,`path`)
    values (191, 'Registered Students', 128, 2,'/reporting/registered-students');
      
 -- for MahaAdmin
 INSERT INTO role_menu_permission (role_id, menu_id, permission_id)
 values (2, 191, 1), (2, 191, 4),
        (2, 191, 5), (2, 191, 22);

 -- for TechAdmin
 INSERT INTO role_menu_permission (role_id, menu_id, permission_id)
 values (1, 191, 1), (1, 191, 4),
        (1, 191, 5), (1, 191, 22);


--Subscribed Students
INSERT INTO menus (menu_id, name, parent_id, order_index,`path`)
values (192, 'Subscribed Students', 128, 3,'/reporting/active-with-packages');

-- for MahaAdmin
INSERT INTO role_menu_permission (role_id, menu_id, permission_id)
values (2, 192, 1), (2, 192, 4),
       (2, 192, 5), (2, 192, 22);

-- for TechAdmin
INSERT INTO role_menu_permission (role_id, menu_id, permission_id)
values (1, 192, 1), (1, 192, 4),
       (1, 192, 5), (1, 192, 22);

--Total Exams
INSERT INTO menus (menu_id, name, parent_id, order_index,`path`)
values (193, 'Total Exams', 128, 4,'/reporting/total-exams');

-- for MahaAdmin
INSERT INTO role_menu_permission (role_id, menu_id, permission_id)
values (2, 193, 1), (2, 193, 4),
       (2, 193, 5), (2, 193, 22);

-- for TechAdmin
INSERT INTO role_menu_permission (role_id, menu_id, permission_id)
values (1, 193, 1), (1, 193, 4),
       (1, 193, 5), (1, 193, 22);


--Completed Exams
INSERT INTO menus (menu_id, name, parent_id, order_index,`path`)
values (194, 'Completed Exams', 128, 5,'/reporting/completed-exams');

-- for MahaAdmin
INSERT INTO role_menu_permission (role_id, menu_id, permission_id)
values (2, 194, 1), (2, 194, 4),
       (2, 194, 5), (2, 194, 22);

-- for TechAdmin
INSERT INTO role_menu_permission (role_id, menu_id, permission_id)
values (1, 194, 1), (1, 194, 4),
       (1, 194, 5), (1, 194, 22);

--Upcoming Exams
INSERT INTO menus (menu_id, name, parent_id, order_index,`path`)
values (195, 'Upcoming Exams', 128, 6,'/reporting/upcoming-exams');

-- for MahaAdmin
INSERT INTO role_menu_permission (role_id, menu_id, permission_id)
values (2, 195, 1), (2, 195, 4),
       (2, 195, 5), (2, 195, 22);

-- for TechAdmin
INSERT INTO role_menu_permission (role_id, menu_id, permission_id)
values (1, 195, 1), (1, 195, 4),
       (1, 195, 5), (1, 195, 22);