--liquibase formatted sql
--changeset {narendra}:{id}


UPDATE `role`
SET  name='Student', description='Student' 
WHERE role_id=3;

UPDATE `role`
SET  name='Channel Partner', description='Channel Partner' 
WHERE role_id=4;

UPDATE `role`
SET  name='Institute Representative', description='Institute Representative'
WHERE role_id=5;


UPDATE user_role
SET role_id=4
WHERE role_id=6;

UPDATE user_role
SET role_id=3
WHERE role_id=9;


UPDATE user_role
SET role_id=5
WHERE role_id=7;


delete from `role` where role_id>5;