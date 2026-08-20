--liquibase formatted sql
--changeset {narendra}:{id}

INSERT INTO menus (menu_id, name, parent_id, order_index,`path`) VALUES
(178, 'Student Upload', 12, 1,'configure/data-load');

update menus set name ='Upload History',order_index=2 where name ='Student Excel';

update menus set is_active='0' where name in('Prepare','Practice','Evaluate') and `path` like '%packages%';

-- Packages Menu Path Correction
update menus set `path`='packages/packages/practice' where menu_id=117;

  -- for MahaAdmin
  --Student Upload (178): 1,4,5,22
  INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES
  (2, 178, 1), (2, 178, 4), (2, 178, 5), (2, 178, 22);
  -- For Tech Admin
  --Student Upload (178): 1,4,5,22
  INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES
  (1, 178, 1), (1, 178, 4), (1, 178, 5), (1, 178, 22);

  -- for Channel Partner
  -- Configure (2)
  INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (4, 2, 1);
  -- Data Upload (12): 1
  INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (4, 12, 1);
  --Student Upload (178): 1,4,5,22
  INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES
  (4, 178, 1), (4, 178, 4), (4, 178, 5), (4, 178, 22);


  -- for Institute / Network Partner
  -- Configure (2)
  INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (5, 2, 1);
  -- Data Upload (12): 1
  INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES (5, 12, 1);
  --Student Upload (178): 1,4,5,22
  INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES
  (5, 178, 1), (5, 178, 4), (5, 178, 5), (5, 178, 22);