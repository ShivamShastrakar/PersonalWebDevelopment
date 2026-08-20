--liquibase formatted sql
--changeset {narendra}:{id}

CREATE TABLE `categories` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `category_name` varchar(145) DEFAULT NULL,
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime DEFAULT NULL,
  `deleted_at` datetime DEFAULT NULL,
  `deleted` enum('1','0') NOT NULL DEFAULT '0',
  `is_disabled` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `category_name` (`category_name`)
) ENGINE=InnoDB;

-- mdeeper_dev83.topics definition

CREATE TABLE `topics` (
  `topic_id` int(11) NOT NULL AUTO_INCREMENT,
  `topic_name` varchar(250) DEFAULT NULL,
  `chapter_id` int(11) DEFAULT NULL,
  `subject_id` int(11) DEFAULT NULL,
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `created_by` int(11) DEFAULT NULL,
  `updated_by` int(11) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `deleted_at` datetime DEFAULT NULL,
  `class_id` int(11) DEFAULT NULL,
  `board_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`topic_id`),
  KEY `chapter_id` (`chapter_id`),
  KEY `subject_id` (`subject_id`),
  KEY `class_id` (`class_id`),
  KEY `board_id` (`board_id`),
  CONSTRAINT fk_topics_chapter FOREIGN KEY (chapter_id) REFERENCES chapters(id),
  CONSTRAINT fk_topics_subject FOREIGN KEY (subject_id) REFERENCES subject(subject_id),
  CONSTRAINT fk_topics_class FOREIGN KEY (class_id) REFERENCES class(id),
   CONSTRAINT fk_topics_board FOREIGN KEY (board_id) REFERENCES board(id)
) ENGINE=InnoDB;