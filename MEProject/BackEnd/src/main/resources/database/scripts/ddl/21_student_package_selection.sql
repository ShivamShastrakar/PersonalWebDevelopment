--liquibase formatted sql
--changeset {narendra}:{id}

-- Creating table for student package selection summary
CREATE TABLE student_package_selection_summary (
    selection_summary_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    student_id bigint(20),
    total_amount DECIMAL(10, 2) NOT NULL,
    selected_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(20) DEFAULT 'PENDING',
    CONSTRAINT fk_summary_student FOREIGN KEY (student_id) REFERENCES student(student_id)
) ENGINE=InnoDB;

-- Creating table for student package selections
CREATE TABLE student_package_selection (
    selection_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    package_id int(11),
    student_id bigint(20),
    selection_summary_id BIGINT,
    amount DECIMAL(10, 2) NOT NULL,
    selected_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_selection_package FOREIGN KEY (package_id) REFERENCES packages(id),
    CONSTRAINT fk_selection_student FOREIGN KEY (student_id) REFERENCES student(student_id),
    CONSTRAINT fk_selection_summary FOREIGN KEY (selection_summary_id) REFERENCES student_package_selection_summary(selection_summary_id)
) ENGINE=InnoDB;

-- Creating table for payment transactions
CREATE TABLE payment_transactions (
    transaction_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    selection_summary_id BIGINT,
    payu_transaction_id VARCHAR(100),
    total_amount DECIMAL(10, 2) NOT NULL,
    payment_status VARCHAR(20) NOT NULL,
    payment_link VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NULL,
    CONSTRAINT fk_transaction_summary FOREIGN KEY (selection_summary_id) REFERENCES student_package_selection_summary(selection_summary_id)
) ENGINE=InnoDB;