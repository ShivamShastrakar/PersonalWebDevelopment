--liquibase formatted sql
--changeset dishika:add-tenant-id-to-questions

-- Add tenant_id column to questions table for multi-tenancy support
ALTER TABLE questions
ADD COLUMN tenant_id BIGINT UNSIGNED NULL;

-- Add foreign key constraint to tenant table
ALTER TABLE questions
ADD CONSTRAINT fk_questions_tenant
FOREIGN KEY (tenant_id) REFERENCES tenant(tenant_id);

-- Add index for better query performance on tenant-scoped queries
CREATE INDEX idx_questions_tenant_id ON questions(tenant_id);

