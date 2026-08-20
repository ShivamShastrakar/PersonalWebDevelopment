--liquibase formatted sql
--changeset {narendra}:{id}

-- packages definition
CREATE TABLE commission_config (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `role_id` BIGINT(20) UNSIGNED DEFAULT NULL,
	`package_type` VARCHAR(30) NOT NULL,        -- PREMIUM, SUPER, SUPREME
    `commission_type` VARCHAR(20) NOT NULL,     -- PERCENTAGE, FIXED
    `is_active` TINYINT(1) NOT NULL DEFAULT 1,
	`created_date` datetime DEFAULT CURRENT_TIMESTAMP,
    `created_by` int(11) DEFAULT NULL,
    `updated_by` int(11) DEFAULT NULL,
    `updated_at` datetime DEFAULT CURRENT_TIMESTAMP,
    UNIQUE KEY uk_commission_role_pkg (role_id, package_type, is_active),
	CONSTRAINT `comission_config_role_fk` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE commission_slab (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `commission_config_id` BIGINT NOT NULL,
    `from_student_count` INT NOT NULL,
    `to_student_count` INT NOT NULL,
    `percentage` DECIMAL(5,2) NULL,
    `amount` DECIMAL(12,2) NULL,
    CONSTRAINT fk_commission_slab_config
        FOREIGN KEY (commission_config_id)
        REFERENCES commission_config(id),
    CONSTRAINT chk_commission_value
        CHECK (
            (percentage IS NOT NULL AND amount IS NULL) OR
            (percentage IS NULL AND amount IS NOT NULL)
        ),
    CONSTRAINT chk_student_range
        CHECK (from_student_count <= to_student_count)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;