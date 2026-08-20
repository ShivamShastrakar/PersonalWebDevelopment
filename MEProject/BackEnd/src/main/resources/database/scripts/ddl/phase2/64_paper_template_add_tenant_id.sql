--liquibase formatted sql
--changeset dishika:add-tenant-id-to-paper-template

-- Add tenant_id column to paper_template table (MySQL compatible syntax)
ALTER TABLE paper_template
ADD COLUMN tenant_id BIGINT UNSIGNED;

-- Add foreign key constraint to tenant table
ALTER TABLE paper_template
ADD CONSTRAINT fk_paper_template_tenant
FOREIGN KEY (tenant_id) REFERENCES tenant(tenant_id);

-- Add index for better query performance
CREATE INDEX idx_paper_template_tenant_id ON paper_template(tenant_id);
