--liquibase formatted sql
--changeset {narendra}:{id}


CREATE TABLE `student_package_mapping` (
  `id` int NOT NULL AUTO_INCREMENT,
  `package_id` int(11) DEFAULT NULL,
  `student_id` bigint(20) DEFAULT NULL,
  `subscription_type` varchar(100) DEFAULT NULL COMMENT 'either of Monthly,Querterly or Yearly',
  `next_invoice_date` date DEFAULT NULL COMMENT 'To figure out when the next invoice is to be generated',
  `status` enum('Active','Cancelled','Deleted') NOT NULL DEFAULT 'Active',
  `created_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `deleted_at` datetime DEFAULT NULL,
  `created_by` bigint(20) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `package_id` (`package_id`,`student_id`),
  KEY `package_student_pkg_idx` (`package_id`),
  KEY `package_student__srv_idx` (`student_id`),
  KEY `package_student__creator_idx` (`created_by`),
  CONSTRAINT `package_student` FOREIGN KEY (`student_id`) REFERENCES `student` (`student_id`),
  CONSTRAINT `package_student_creator` FOREIGN KEY (`created_by`) REFERENCES `users` (`user_id`),
  CONSTRAINT `package_student_pkg` FOREIGN KEY (`package_id`) REFERENCES `packages` (`id`)
) ENGINE=InnoDB CHARSET=utf8;
