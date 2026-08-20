--liquibase formatted sql
--changeset {dishika}:{paper_template}

-- Drop paper_template if exists
SET @tbl1 := (
  SELECT COUNT(*)
  FROM information_schema.TABLES
  WHERE table_schema = DATABASE()
    AND table_name = 'paper_template'
);

SET @sql1 := IF(@tbl1 = 1,
  'DROP TABLE paper_template',
  'SELECT 1');

PREPARE stmt1 FROM @sql1;
EXECUTE stmt1;
DEALLOCATE PREPARE stmt1;

CREATE TABLE `paper_template` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `class_id` bigint NOT NULL,
  `total_duration` int NOT NULL,
  `medium` varchar(50) NOT NULL,
  `name` varchar(255) NOT NULL,
  `total_marks` int NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `part_display_name` varchar(100) DEFAULT NULL,
  `number_of_parts` int DEFAULT NULL,
  `instructions` json DEFAULT NULL,
  `status` varchar(20) NOT NULL,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `board_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_paper_template_board` (`board_id`),
  CONSTRAINT `fk_paper_template_board` FOREIGN KEY (`board_id`) REFERENCES `board` (`id`),
  CONSTRAINT `paper_template_chk_2` CHECK ((`total_marks` >= 0))
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;