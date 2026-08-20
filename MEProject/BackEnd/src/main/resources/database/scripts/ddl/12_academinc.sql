--liquibase formatted sql
--changeset {narendra}:{id}



CREATE TABLE `chapters` (
  `id` int NOT NULL AUTO_INCREMENT,
  `chapter_name` text,
  `unit` text,
  `created_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `status` varchar(5) DEFAULT NULL,
  `exam_type` varchar(5) DEFAULT NULL,
  `subject_id` int(11) DEFAULT NULL,
  `class` varchar(5) DEFAULT NULL,
  `institute_id` int(11) DEFAULT NULL,
  `deleted_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `chapter_subject_fk_idx` (`subject_id`),
  CONSTRAINT `chapters_subject_id_fk` FOREIGN KEY (`subject_id`) REFERENCES `subject` (`subject_id`)
) ENGINE=InnoDB;


CREATE TABLE acadamic_experience (
    acadamic_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id bigint(20) unsigned NOT NULL,
    class_id INT NOT NULL,
    subject_id INT NOT NULL,
    board_id INT NOT NULL,
    chapter_id INT NOT NULL,
	KEY `class_id` (`class_id`),
	KEY `subject_id` (`subject_id`),
	KEY `board_id` (`board_id`),
	KEY `chapter_id` (`chapter_id`),
   	KEY `user_id` (`user_id`),
   	FOREIGN KEY (user_id) REFERENCES users(user_id),
   	FOREIGN KEY (subject_id) REFERENCES subject(subject_id),
   	FOREIGN KEY (class_id) REFERENCES class(id),
   	FOREIGN KEY (board_id) REFERENCES board(id),
   	FOREIGN KEY (user_id) REFERENCES users(user_id)   
);
