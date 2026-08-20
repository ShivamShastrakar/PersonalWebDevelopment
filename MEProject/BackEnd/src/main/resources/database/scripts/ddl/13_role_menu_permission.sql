--liquibase formatted sql
--changeset {narendra}:{id}

DROP TABLE IF EXISTS role_menus;

CREATE TABLE role_menu_permission (
    role_id BIGINT(20) UNSIGNED NOT NULL,
    menu_id INT NOT NULL,
    permission_id BIGINT(20) UNSIGNED NOT NULL,
    PRIMARY KEY (role_id,menu_id, permission_id),
    FOREIGN KEY (role_id) REFERENCES role(role_id),
    FOREIGN KEY (permission_id) REFERENCES permission(permission_id),
    FOREIGN KEY (menu_id) REFERENCES menus(menu_id)
);


ALTER TABLE permission
ADD type VARCHAR(15);

ALTER TABLE menus
MODIFY COLUMN path VARCHAR(255) NULL;
