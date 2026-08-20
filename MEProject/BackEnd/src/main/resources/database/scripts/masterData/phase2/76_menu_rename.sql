--liquibase formatted sql
--changeset {narendra}:{id}

update menus set name ='Syllabus' where name='Syllabus Management';

update menus set name ='Paper Template' where name='Paper Template Management';

update menus set name ='Question Papers' where name='Question Papers Management';