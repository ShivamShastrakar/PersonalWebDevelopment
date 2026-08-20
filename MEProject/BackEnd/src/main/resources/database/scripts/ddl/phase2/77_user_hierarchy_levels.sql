--liquibase formatted sql
--changeset Admin:77_user_hierarchy_levels

-- Create user_hierarchy_level table
CREATE TABLE user_hierarchy_level (
    id INT NOT NULL AUTO_INCREMENT,
    level_name VARCHAR(30) NOT NULL,
    description VARCHAR(200),
    level_order INT NOT NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    UNIQUE (level_name)
);

-- Create user_role_hierarchy_level_mappings table
CREATE TABLE user_role_hierarchy_level_mappings (
    id INT NOT NULL AUTO_INCREMENT,
    role_id BIGINT(20) UNSIGNED NOT NULL,
    user_hierarchy_level_id INT NOT NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    UNIQUE KEY uk_role_hierarchy (role_id, user_hierarchy_level_id),
    CONSTRAINT fk_user_role_id FOREIGN KEY (role_id) REFERENCES role(role_id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_hierarchy_level_id FOREIGN KEY (user_hierarchy_level_id) REFERENCES user_hierarchy_level(id) ON DELETE CASCADE ON UPDATE CASCADE
);

