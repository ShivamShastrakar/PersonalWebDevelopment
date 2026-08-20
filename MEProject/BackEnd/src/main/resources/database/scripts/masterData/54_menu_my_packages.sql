--liquibase formatted sql
--changeset {narendra}:{id}

UPDATE menus SET name  ='My Packages'  WHERE name  ='Your Packages';

