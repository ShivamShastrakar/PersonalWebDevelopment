--liquibase formatted sql
--changeset {narendra}:{id}

CREATE TABLE message_templates (
       template_id INT AUTO_INCREMENT PRIMARY KEY,
       template_name VARCHAR(255) NOT NULL,
       template_type ENUM('email', 'sms') NOT NULL,
       subject VARCHAR(255) DEFAULT NULL,
       content TEXT NOT NULL,
       status ENUM('active', 'inactive') DEFAULT 'active',
       deleted ENUM('1','0') NOT NULL DEFAULT '0',
       created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
       updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
