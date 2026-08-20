--liquibase formatted sql
--changeset {narendra}:{id}
-- For chapters table (assuming it exists with at least an 'id' column)
ALTER TABLE chapters ADD COLUMN tenant_id BIGINT(20) UNSIGNED NULL;
ALTER TABLE chapters ADD FOREIGN KEY (tenant_id) REFERENCES tenant(tenant_id);

-- For topics table
ALTER TABLE topics ADD COLUMN tenant_id BIGINT(20) UNSIGNED NULL;
ALTER TABLE topics ADD FOREIGN KEY (tenant_id) REFERENCES tenant(tenant_id);
