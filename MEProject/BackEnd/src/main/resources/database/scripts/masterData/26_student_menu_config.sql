--liquibase formatted sql
--changeset {narendra}:{id}

update menus set path ='students/all-students' where path = 'organization/application-users/students';