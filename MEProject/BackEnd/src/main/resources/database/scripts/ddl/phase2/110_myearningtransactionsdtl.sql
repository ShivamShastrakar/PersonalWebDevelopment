--liquibase formatted sql
--changeset {narendra}:{id}

CREATE TABLE user_earning_transactions_dtls (
    id BIGINT NOT NULL AUTO_INCREMENT,
    referral_user_id bigint unsigned,
    student_id BIGINT NOT NULL,
    student_package_id INT NOT NULL,
    commision_config_id BIGINT NULL,
    commision_type TINYINT DEFAULT 0,
    level_order_id BIGINT NULL,
    earned_amount DECIMAL(10,2) DEFAULT 0,
    earned_date DATE NULL,
    eligible_commision_slab BIGINT DEFAULT 0,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    KEY `fk_usertransaction_referral_user_id` (`referral_user_id`),
    CONSTRAINT `fk_usertransaction_referral_user_id` FOREIGN KEY (`referral_user_id`) REFERENCES `users` (`user_id`),
    KEY `fk_usertransaction_Student_id` (`student_id`),
    CONSTRAINT `fk_usertransaction_Student_id` FOREIGN KEY (`student_id`) REFERENCES `student` (`student_id`),
    KEY `fk_usertransaction_package_id` (`student_package_id`),
    CONSTRAINT `fk_usertransaction_package_id` FOREIGN KEY (`student_package_id`) REFERENCES `packages` (`id`)
);
