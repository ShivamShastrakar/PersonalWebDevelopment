--liquibase formatted sql
--changeset {narendra}:{id}

-- Create the offline_payment table to store offline payment details
CREATE TABLE offline_payment (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,

    amount DECIMAL(12,2) NOT NULL,

    payment_mode VARCHAR(50) NOT NULL,
    payment_date DATE,
    remarks VARCHAR(255),

    -- Cheque fields
    cheque_number VARCHAR(50),
    bank_name VARCHAR(100),
    cheque_date DATE,

    -- Cash fields
    received_by VARCHAR(100),

    batch_id BIGINT,

    transaction_id BIGINT,
    status VARCHAR(50) NOT NULL,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (batch_id) REFERENCES upload_batch(batch_id),
    FOREIGN KEY (transaction_id) REFERENCES payment_transactions(transaction_id)
);

