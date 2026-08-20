--liquibase formatted sql
--changeset {narendra}:{id}

delete from role_menu_permission where menu_id =92 and role_id =5;