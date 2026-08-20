--liquibase formatted sql
--changeset {narendra}:{id}

ALTER TABLE parent
    MODIFY parents_yearly_income VARCHAR(50) NOT NULL;
