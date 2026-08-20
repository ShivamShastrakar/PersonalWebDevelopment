--liquibase formatted sql
--changeset {narendra}:{id}

use mahaexam;


INSERT INTO tenant
(tenant_id, name, is_active, created_at)
VALUES(101, 'Maha Exam', 1, CURRENT_TIMESTAMP);

INSERT INTO users
(user_id,tenant_id,username, password_hash, is_active, is_salt, created_at)
VALUES(100,101,'TechAdmin', '9af15b336e6a9619928537df30b2e6a2376569fcf9d7e773eccede65606529a0', 1, 0, CURRENT_TIMESTAMP);

INSERT INTO users
(user_id,tenant_id, username, password_hash, is_active, is_salt, created_at)
VALUES(101,101, 'MahaAdmin', '9af15b336e6a9619928537df30b2e6a2376569fcf9d7e773eccede65606529a0', 1, 0, CURRENT_TIMESTAMP);



INSERT INTO `role`
(role_id,  name, description, is_active, is_assignable, created_at)
VALUES(1,'Tech-Admin', 'Tech-Admin', 1, 1, CURRENT_TIMESTAMP);
INSERT INTO `role`
(role_id,  name, description, is_active, is_assignable, created_at)
VALUES(2,'ME-Admin', 'ME-Admin', 1, 1, CURRENT_TIMESTAMP);

INSERT INTO user_role
(user_id, role_id)
VALUES(100, 1);

INSERT INTO user_role
(user_id, role_id)
VALUES(101, 2);