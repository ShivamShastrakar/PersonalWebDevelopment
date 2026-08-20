--liquibase formatted sql
--changeset {narendra}:{id}

INSERT INTO menus (menu_id, name, parent_id, order_index,`path`) VALUES
(179, 'Question Generator', 15, 4,'exams/question-generator');

  -- for MahaAdmin
  --Question Generator(179): 1,4,5,22
  INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES
  (2, 179, 1), (2, 179, 4), (2, 179, 5), (2, 179, 22);
  -- For Tech Admin
  --Question Generator (179): 1,4,5,22
  INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES
  (1, 179, 1), (1, 179, 4), (1, 179, 5), (1, 179, 22);


INSERT INTO menus (menu_id, name, parent_id, order_index,`path`) VALUES
(180, 'Learning Content', 92,9,'exams/learning-content');

  -- For Student
  --Learning Content (180): 1,4,5,22
  INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES
  (3, 180, 1), (3, 180, 4), (3, 180, 5), (3, 180, 22);

 delete from menu_feature_toggle where menu_id =92;