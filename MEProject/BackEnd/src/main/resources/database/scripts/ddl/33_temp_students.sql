--liquibase formatted sql
--changeset {narendra}:{id}

CREATE TABLE temp_students (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    batch_id BIGINT NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    first_name VARCHAR(100) NOT NULL,
    middle_name VARCHAR(100),
    adhar_no VARCHAR(12) ,
    mobile_number VARCHAR(15) NOT NULL,
    email VARCHAR(255) NOT NULL,
    class_name VARCHAR(100),
    class_id INT,
    exam_group VARCHAR(100),
    courses TEXT,
    course_ids TEXT,  -- Store comma-separated like "1,5,12,23"
    subject_group_id INT,
    target_final_exam_year INT,
    package_id INT,
    reference_id BIGINT UNSIGNED,
    error_message TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    INDEX idx_adhar (adhar_no),
    INDEX idx_email (email),
    INDEX idx_class_id (class_id),
    INDEX idx_subject_group_id (subject_group_id)
) ENGINE=InnoDB;
