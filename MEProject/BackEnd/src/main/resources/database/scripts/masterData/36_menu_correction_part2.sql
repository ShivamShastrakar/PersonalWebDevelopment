--liquibase formatted sql
--changeset {narendra}:{id}


  -- for Channel Partner
  --Upload History (13): 1,4,5,22
  INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES
  (4, 13, 1), (4, 13, 4), (4, 13, 5), (4, 13, 22);

  -- for Institute / Network Partner
  --Upload History (13): 1,4,5,22
  INSERT INTO role_menu_permission (role_id, menu_id, permission_id) VALUES
  (5, 13, 1), (5, 13, 4), (5, 13, 5), (5, 13, 22);