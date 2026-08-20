--liquibase formatted sql
--changeset {narendra}:{id}

CREATE TABLE user_upline_dtls (
    id BIGINT NOT NULL AUTO_INCREMENT,
    user_level1_id bigint unsigned NOT NULL,
    user_level2_id bigint unsigned NULL,
    user_level3_id bigint unsigned NULL,
    user_level4_id bigint unsigned NULL,
    user_level5_id bigint unsigned NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    KEY `fk_upline_user_id` (`user_level1_id`),
    CONSTRAINT `fk_upline_user_id` FOREIGN KEY (`user_level1_id`) REFERENCES `users` (`user_id`)
);