--liquibase formatted sql
--changeset {narendra}:{id}

CREATE TABLE menus (
    menu_id INT AUTO_INCREMENT PRIMARY KEY,
    parent_id INT DEFAULT NULL, -- for nesting
    name VARCHAR(100) NOT NULL,
    path VARCHAR(255) NOT NULL, -- URL or route
    icon VARCHAR(100),
    order_index INT DEFAULT 0,
    is_active BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (parent_id) REFERENCES menus(menu_id) ON DELETE CASCADE
);


CREATE TABLE role_menus (
    role_id BIGINT(20) UNSIGNED,
    menu_id INT,
    PRIMARY KEY (role_id, menu_id),
    FOREIGN KEY (role_id) REFERENCES role(role_id),
    FOREIGN KEY (menu_id) REFERENCES menus(menu_id)
);

CREATE TABLE feature_toggles (
    feature_id INT AUTO_INCREMENT PRIMARY KEY,
    feature_key VARCHAR(100) UNIQUE,
    description TEXT,
    enabled BOOLEAN DEFAULT TRUE,
    rollout_percentage INT DEFAULT 100, -- optional for A/B testing
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    activation_date TIMESTAMP,
    expiration_date TIMESTAMP
);

CREATE TABLE menu_feature_toggle (
    menu_id INT,
    feature_id INT,
    PRIMARY KEY (menu_id, feature_id),
    FOREIGN KEY (menu_id) REFERENCES menus(menu_id) ON DELETE CASCADE,
    FOREIGN KEY (feature_id) REFERENCES feature_toggles(feature_id) ON DELETE CASCADE
);