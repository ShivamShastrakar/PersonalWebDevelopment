--liquibase formatted sql
--changeset {narendra}:{id}

INSERT INTO subject_group
(group_id, tenant_id, group_name, description, created_at, updated_at, deleted_at, deleted)
VALUES(1, null, 'PCM', 'PCM', CURRENT_TIMESTAMP, null, null, '0');

INSERT INTO subject_group
(group_id, tenant_id, group_name, description, created_at, updated_at, deleted_at, deleted)
VALUES(2, null, 'PCB', 'PCB', CURRENT_TIMESTAMP, null, null, '0');

INSERT INTO subject_group
(group_id, tenant_id, group_name, description, created_at, updated_at, deleted_at, deleted)
VALUES(3, null, 'PCMB', 'PCMB', CURRENT_TIMESTAMP, null, null, '0');

