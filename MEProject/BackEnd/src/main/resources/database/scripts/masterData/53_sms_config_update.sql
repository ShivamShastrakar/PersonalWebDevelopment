--liquibase formatted sql
--changeset {narendra}:{id}

update config set value='MAHEXM' where name='SMS_API_SENDER';
