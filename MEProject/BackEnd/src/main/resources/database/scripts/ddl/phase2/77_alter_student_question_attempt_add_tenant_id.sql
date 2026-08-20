--liquibase formatted sql
--changeset dishika:add-tenant-id-to-student-question-attempt

-- Add tenant_id column to student_question_attempt table for multi-tenancy support
ALTER TABLE student_question_attempt
ADD COLUMN tenant_id BIGINT UNSIGNED NULL;

-- Add foreign key constraint to tenant table
ALTER TABLE student_question_attempt
ADD CONSTRAINT fk_student_question_attempt_tenant
FOREIGN KEY (tenant_id) REFERENCES tenant(tenant_id);

-- Add index for better query performance on tenant-scoped queries
CREATE INDEX idx_student_question_attempt_tenant_id ON student_question_attempt(tenant_id);

