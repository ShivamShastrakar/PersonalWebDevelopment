--liquibase formatted sql
--changeset {narendra}:{id}

-- Create my_earning_stats table
CREATE TABLE my_earning_stats (
    id BIGINT NOT NULL AUTO_INCREMENT,
    user_id bigint unsigned NOT NULL,
    level_order_id BIGINT NULL,
    earning_period_end_dt DATE NULL,
    total_direct_student_count INT NULL,
    total_direct_earning_amt DECIMAL(10,2) NULL,
    total_indirect_student_count INT NULL,
    total_indirect_earning_amt DECIMAL(10,2) NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    KEY `fk_myearning_stats_user_id` (`user_id`),
    CONSTRAINT `fk_myearning_stats_user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
);
