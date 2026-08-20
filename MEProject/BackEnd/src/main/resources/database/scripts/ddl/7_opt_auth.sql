--liquibase formatted sql
--changeset {narendra}:{id}

CREATE TABLE otp_auth (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    mobile VARCHAR(15),
    email VARCHAR(150) NOT NULL,
    otp VARCHAR(100) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


-- 1. For fast lookup when verifying OTP for login/registration
CREATE INDEX idx_email_mobile ON otp_auth (email, mobile);
