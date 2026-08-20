--liquibase formatted sql
--changeset {narendra}:{id}

-- mdeeper_dev83.parents definition

CREATE TABLE IF NOT EXISTS parent (
    parent_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    father_name VARCHAR(100) NOT NULL,
    father_mobile_number VARCHAR(20),
    father_occupation VARCHAR(100),
    mother_name VARCHAR(100) NOT NULL,
    mother_mobile_number VARCHAR(20),
    mother_occupation VARCHAR(100),
    number_of_siblings INT NOT NULL,
    first_sibling_name VARCHAR(100) ,
    first_sibling_std VARCHAR(50),
    second_sibling_name VARCHAR(100),
    second_sibling_std VARCHAR(50),
    parents_yearly_income DECIMAL(15, 2) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

ALTER TABLE student
ADD COLUMN parent_id BIGINT;

ALTER TABLE student
ADD CONSTRAINT fk_student_parent
FOREIGN KEY (parent_id) REFERENCES parent(parent_id)
ON DELETE SET NULL ON UPDATE CASCADE;