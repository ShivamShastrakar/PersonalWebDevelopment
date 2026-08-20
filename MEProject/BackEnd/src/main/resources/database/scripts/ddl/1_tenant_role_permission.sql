--liquibase formatted sql
--changeset {narendra}:{id}
-- 1. tenant
CREATE TABLE tenant (
    tenant_id bigint(20) unsigned NOT NULL AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL UNIQUE,
    is_active BOOLEAN DEFAULT TRUE,
    created_at datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
   PRIMARY KEY (`tenant_id`)
);


-- 2. role (Global or Tenant-Specific)
CREATE TABLE role (
    role_id BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
    tenant_id BIGINT(20) UNSIGNED, -- NULL = global/system-wide role
    name VARCHAR(50) NOT NULL,
    description TEXT,
    is_active BOOLEAN DEFAULT TRUE,
	is_assignable BOOLEAN DEFAULT TRUE,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (role_id),  -- ✅ REQUIRED for AUTO_INCREMENT
    UNIQUE (tenant_id, name),
    FOREIGN KEY (tenant_id) REFERENCES tenant(tenant_id) ON DELETE CASCADE
);


-- 3. permission (Global or Tenant-Specific)
CREATE TABLE permission (
    permission_id BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL, -- e.g., read:user, write:ticket
    description TEXT,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (permission_id), -- ✅ Required for AUTO_INCREMENT
    UNIQUE (name)
);

-- 4. Role permission (Many-to-Many)
CREATE TABLE role_permission (
    role_id BIGINT(20) UNSIGNED NOT NULL,
    permission_id BIGINT(20) UNSIGNED NOT NULL,
    PRIMARY KEY (role_id, permission_id),
    FOREIGN KEY (role_id) REFERENCES role(role_id) ON DELETE CASCADE,
    FOREIGN KEY (permission_id) REFERENCES permission(permission_id) ON DELETE CASCADE
);

-- 5. Users
CREATE TABLE users (
    user_id BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
    tenant_id BIGINT(20) UNSIGNED NOT NULL,
    username VARCHAR(100) NOT NULL UNIQUE,  -- globally unique username
    password_hash VARCHAR(255) NOT NULL,
    is_active BOOLEAN DEFAULT TRUE,
    is_salt BOOLEAN DEFAULT FALSE,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (user_id),
    FOREIGN KEY (tenant_id) REFERENCES tenant(tenant_id) ON DELETE CASCADE
);


-- 6. User role (Many-to-Many)
CREATE TABLE user_role (
    user_id BIGINT(20) UNSIGNED NOT NULL,
    role_id BIGINT(20) UNSIGNED NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES role(role_id) ON DELETE CASCADE
);
