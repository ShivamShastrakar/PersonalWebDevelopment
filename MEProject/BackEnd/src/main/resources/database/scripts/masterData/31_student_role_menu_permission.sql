--liquibase formatted sql
--changeset {narendra}:{id}


DELETE from role_menu_permission where role_id=3 and menu_id=117;

INSERT INTO role_menu_permission
(role_id, menu_id, permission_id)
VALUES(3, 118, 1);

INSERT INTO role_menu_permission
(role_id, menu_id, permission_id)
VALUES(3, 118, 4);

INSERT INTO role_menu_permission
(role_id, menu_id, permission_id)
VALUES(3, 118, 5);


INSERT INTO role_menu_permission
(role_id, menu_id, permission_id)
VALUES(3, 119, 1);

INSERT INTO role_menu_permission
(role_id, menu_id, permission_id)
VALUES(3, 119, 4);

INSERT INTO role_menu_permission
(role_id, menu_id, permission_id)
VALUES(3, 119, 5);

INSERT INTO role_menu_permission
(role_id, menu_id, permission_id)
VALUES(3, 85, 1);

UPDATE menus set path='/parents' where menu_id=85;

-- Parent Menu
DELETE from role_menu_permission where menu_id=85 and role_id !=3;