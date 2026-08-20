--liquibase formatted sql
--changeset {narendra}:{id}

CREATE TABLE user_tenant (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id bigint(20) unsigned NOT NULL,
    tenant_id bigint(20) unsigned NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id),
   FOREIGN KEY (tenant_id) REFERENCES tenant(tenant_id)
);
