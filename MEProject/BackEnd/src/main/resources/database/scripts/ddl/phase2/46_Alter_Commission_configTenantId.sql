--liquibase formatted sql
--changeset {narendra}:{id}

-- Add the new column to Student table
ALTER TABLE commission_config
ADD COLUMN tenant_id BIGINT UNSIGNED;

-- Add the foreign key constraint on userId referencing users(userId)
ALTER TABLE commission_config
ADD CONSTRAINT fk_tenant_comissionconfig_reference_id
FOREIGN KEY (tenant_id) REFERENCES tenant(tenant_id)


