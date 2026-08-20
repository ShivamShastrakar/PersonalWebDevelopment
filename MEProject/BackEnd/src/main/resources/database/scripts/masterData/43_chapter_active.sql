--liquibase formatted sql
--changeset {narendra}:{id}

update chapters set status = '0' where 1=1;