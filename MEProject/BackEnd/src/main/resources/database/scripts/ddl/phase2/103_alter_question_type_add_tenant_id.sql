--liquibase formatted sql

--changeset dishika:103_alter_question_type_add_tenant_id

-- Add tenant_id to question_type for multi-tenancy support
ALTER TABLE question_type
ADD COLUMN tenant_id BIGINT UNSIGNED NULL AFTER id;

-- Add foreign key to tenant table
ALTER TABLE question_type
ADD CONSTRAINT fk_question_type_tenant
FOREIGN KEY (tenant_id) REFERENCES tenant(tenant_id);

-- Index for tenant-scoped queries
CREATE INDEX idx_question_type_tenant_id ON question_type(tenant_id);

