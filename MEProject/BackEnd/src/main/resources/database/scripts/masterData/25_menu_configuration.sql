--liquibase formatted sql
--changeset {narendra}:{id}

UPDATE menus set is_active='0'
where name in('Email','SMS','User','Whatsapp');

UPDATE menus  set `path` ='configure/system/configuration', name ='Configuration'
where name ='Overview' and `path` ='configure/system/overview';