--liquibase formatted sql
--changeset {narendra}:{id}

-- Create email_requests table
CREATE TABLE email_requests (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    to_addresses VARCHAR(1000) NOT NULL,
    cc_addresses VARCHAR(1000),
    bcc_addresses VARCHAR(1000),
    subject VARCHAR(255) NOT NULL,
    body TEXT NOT NULL,
    is_html BOOLEAN NOT NULL,
    status ENUM('PENDING', 'SENT', 'FAILED') NOT NULL DEFAULT 'PENDING',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    sent_at DATETIME,
    error_message TEXT
);

-- Create email_attachments table
CREATE TABLE email_attachments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email_request_id BIGINT NOT NULL,
    attachment_data LONGBLOB,
    attachment_name VARCHAR(255),
    FOREIGN KEY (email_request_id) REFERENCES email_requests(id) ON DELETE CASCADE
);