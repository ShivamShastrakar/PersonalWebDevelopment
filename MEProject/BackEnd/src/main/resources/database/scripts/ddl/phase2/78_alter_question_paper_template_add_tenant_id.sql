--liquibase formatted sql
--changeset dishika:78_alter_question_paper_template_add_tenant_id

-- Add tenant_id column to question_paper_template table for multi-tenancy support
ALTER TABLE question_paper_template
ADD COLUMN tenant_id BIGINT UNSIGNED NULL;

-- Add foreign key constraint to tenant table
ALTER TABLE question_paper_template
ADD CONSTRAINT fk_question_paper_template_tenant
FOREIGN KEY (tenant_id) REFERENCES tenant(tenant_id);

-- Add index for better query performance on tenant-scoped queries
CREATE INDEX idx_question_paper_template_tenant_id ON question_paper_template(tenant_id);

