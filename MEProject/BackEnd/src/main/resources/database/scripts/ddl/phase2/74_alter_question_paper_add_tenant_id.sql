--liquibase formatted sql
--changeset dishika:add-tenant-id-to-question-paper

-- Add tenant_id column to question_paper table for multi-tenancy support
ALTER TABLE question_paper
ADD COLUMN tenant_id BIGINT UNSIGNED NULL;

-- Add foreign key constraint to tenant table
ALTER TABLE question_paper
ADD CONSTRAINT fk_question_paper_tenant
FOREIGN KEY (tenant_id) REFERENCES tenant(tenant_id);

-- Add index for better query performance on tenant-scoped queries
CREATE INDEX idx_question_paper_tenant_id ON question_paper(tenant_id);

