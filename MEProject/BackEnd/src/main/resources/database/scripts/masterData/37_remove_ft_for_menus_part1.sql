--liquibase formatted sql
--changeset {narendra}:{id}

delete from menu_feature_toggle where menu_id =119;