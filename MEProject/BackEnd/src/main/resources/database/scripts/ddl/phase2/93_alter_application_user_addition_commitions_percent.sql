--liquibase formatted sql
--changeset narendra:93_alter_application_user_addition_commitions_percent

-- Add additional_commission_percent column to application_user table
ALTER TABLE application_user
ADD COLUMN additional_commission_percent DECIMAL(10,2) DEFAULT 0;
