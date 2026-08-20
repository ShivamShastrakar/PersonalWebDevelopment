--liquibase formatted sql
--changeset {narendra}:79_menu_rename

update menus set name ='Talukas' where name='Taluka';

update menus set name ='Education Societies' where name='Education Society';
