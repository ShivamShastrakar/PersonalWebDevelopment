--liquibase formatted sql
--changeset {narendra}:{id}

INSERT INTO menu_feature_toggle
(menu_id, feature_id)
VALUES(49, 1);