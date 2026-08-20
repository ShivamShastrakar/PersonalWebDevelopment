--liquibase formatted sql
--changeset {narendra}:{id}

update menus set  parent_id =115 where menu_id in(118,119);