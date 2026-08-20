--liquibase formatted sql
--changeset dishika:add-tenant-id-to-student-subject-summary

-- Add tenant_id column to student_subject_summary table for multi-tenancy support
ALTER TABLE student_subject_summary
ADD COLUMN tenant_id BIGINT UNSIGNED NULL;

-- Add foreign key constraint to tenant table
ALTER TABLE student_subject_summary
ADD CONSTRAINT fk_student_subject_summary_tenant
FOREIGN KEY (tenant_id) REFERENCES tenant(tenant_id);

-- Add index for better query performance on tenant-scoped queries
CREATE INDEX idx_student_subject_summary_tenant_id ON student_subject_summary(tenant_id);

