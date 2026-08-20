--liquibase formatted sql
--changeset {narendra}:{id}

INSERT INTO menus (menu_id, name, parent_id, order_index,`path`) VALUES
(181, 'Syllabus Management', 15, 5,'organization/academics/syllabus');

  -- for MahaAdmin
  --Syllabus Management(181): 1,4,5,22
  INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES
  (2, 181, 1), (2, 181, 4), (2, 181, 5), (2, 181, 22);
  -- For Tech Admin
   --Syllabus Management(181): 1,4,5,22
  INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES
  (1, 181, 1), (1, 181, 4), (1, 181, 5), (1, 181, 22);


INSERT INTO menus (menu_id, name, parent_id, order_index,`path`) VALUES
(182, 'Paper Template Management', 15,6,'paper-templates');

  -- for MahaAdmin
  --Paper Template Management(182): 1,4,5,22
  INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES
  (2, 182, 1), (2, 182, 4), (2, 182, 5), (2, 182, 22);
  -- For Tech Admin
  --Paper Template Management(182): 1,4,5,22
  INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES
  (1, 182, 1), (1, 182, 4), (1, 182, 5), (1, 182, 22);