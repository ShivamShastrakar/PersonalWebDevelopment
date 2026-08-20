--liquibase formatted sql
--changeset {narendra}:{id}

-- Add tenant_id column to user_hierarchy_level table
ALTER TABLE user_hierarchy_level
ADD COLUMN tenant_id BIGINT(20) UNSIGNED NOT NULL;

-- Add foreign key constraint to tenant table
ALTER TABLE user_hierarchy_level
ADD CONSTRAINT fk_user_hierarchy_level_tenant
FOREIGN KEY (tenant_id) REFERENCES tenant(tenant_id);

-- Add index for better query performance
CREATE INDEX idx_user_hierarchy_level_tenant_id ON user_hierarchy_level(tenant_id);
