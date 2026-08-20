--liquibase formatted sql
--changeset {narendra}:{id}

update menus set path ="organization/entities/hierarchy-levels" where menu_id=188;