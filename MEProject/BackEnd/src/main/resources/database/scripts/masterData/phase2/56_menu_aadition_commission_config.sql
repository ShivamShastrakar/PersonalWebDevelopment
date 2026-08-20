--liquibase formatted sql
--changeset {narendra}:{id}

INSERT INTO menus (menu_id, name, parent_id, order_index,`path`) values (185, 'Commission Configuration', 2, 3,'/configure/commission-config');
      
 -- for MahaAdmin
 INSERT INTO role_menu_permission (role_id, menu_id, permission_id) values (2, 185, 1), (2, 185, 4), (2, 185, 5), (2, 185, 22);

 -- 
 INSERT INTO role_menu_permission (role_id, menu_id, permission_id) values (1, 185, 1), (1, 185, 4), (1, 185, 5), (1, 185, 22);
