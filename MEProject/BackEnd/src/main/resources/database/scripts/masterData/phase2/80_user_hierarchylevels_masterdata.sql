--liquibase formatted sql
--changeset {narendra}:79_menu_rename

INSERT INTO mahaexam.user_hierarchy_level (level_name,description,level_order,created_at,updated_at,tenant_id) VALUES
	 ('Level 1','Level 1',1,'2026-03-17 07:26:49','2026-04-04 14:40:55',(SELECT tenant_id FROM tenant ORDER BY id ASC LIMIT 1)),
	 ('Level 2','Level 2',2,'2026-03-19 07:45:01','2026-04-03 17:48:31',(SELECT tenant_id FROM tenant ORDER BY id ASC LIMIT 1)),
	 ('Level 3','Level 3',3,'2026-03-19 07:51:18','2026-03-19 07:51:17',(SELECT tenant_id FROM tenant ORDER BY id ASC LIMIT 1)),
	 ('Level 4','Level 4',4,'2026-03-19 07:51:34','2026-04-04 14:40:55',(SELECT tenant_id FROM tenant ORDER BY id ASC LIMIT 1)),
	 ('Level 5','Level 5',5,'2026-03-19 13:44:38','2026-03-19 13:44:38',(SELECT tenant_id FROM tenant ORDER BY id ASC LIMIT 1));
