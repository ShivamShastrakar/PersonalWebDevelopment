--liquibase formatted sql
--changeset {narendra}:{id}

-- packages definition
CREATE TABLE `packages` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `package_name` varchar(245) DEFAULT NULL,
  `package_details` text,
  `amount` decimal(10,2) DEFAULT NULL,
  `package_for` varchar(500) DEFAULT NULL,
  `package_type` varchar(15) DEFAULT 'Regular',
  `package_target_students` enum('New','Existing') NOT NULL DEFAULT 'Existing',
  `package_mode` enum('ONLINE','OFFLINE','CBT','with_course') NOT NULL DEFAULT 'ONLINE',
  `flag` tinyint(1) DEFAULT '1',
  `package_type_name` varchar(45) DEFAULT NULL,
  `pkg_exam_group` varchar(40) DEFAULT NULL,
  `is_archived` int(11) NOT NULL DEFAULT '0',
  `archived_by` int(11) DEFAULT NULL,
  `start_date` date DEFAULT NULL,
  `end_date` date DEFAULT NULL,
  `target_year` varchar(40) DEFAULT NULL,
  `show_strike_price` tinyint(4) DEFAULT '0',
  `strike_price` decimal(10,2) DEFAULT NULL,
  `is_testing_package` int(11) NOT NULL DEFAULT '0',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime DEFAULT NULL,
  `deleted_at` datetime DEFAULT NULL,
  `deleted` enum('1','0') NOT NULL DEFAULT '0',
  `updated_by` int(11) DEFAULT NULL,
  `package_img_url` varchar(500) DEFAULT NULL,
   tenant_id bigint(20) unsigned NULL,
   PRIMARY KEY (`id`),
   FOREIGN KEY (tenant_id) REFERENCES tenant(tenant_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


 -- services definition

CREATE TABLE `services` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `service_name` varchar(245) DEFAULT NULL,
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime DEFAULT NULL,
  `deleted_at` datetime DEFAULT NULL,
  `deleted` enum('1','0') NOT NULL DEFAULT '0',
  `updated_by` int(11) DEFAULT NULL,
  `service_details` text,
  `service_type` varchar(15) DEFAULT NULL,
  `options` varchar(45) DEFAULT NULL,
   tenant_id bigint(20) unsigned NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (tenant_id) REFERENCES tenant(tenant_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- sub_packages_mapping definition

  CREATE TABLE `sub_packages_mapping` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `parent_package_id` int(11) DEFAULT NULL,
  `child_packge_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `parent_package_id` (`parent_package_id`),
  KEY `child_packge_id` (`child_packge_id`),
  CONSTRAINT `pkg_map_child_fk` FOREIGN KEY (`child_packge_id`) REFERENCES `packages` (`id`),
  CONSTRAINT `pkg_map_parent_fk` FOREIGN KEY (`parent_package_id`) REFERENCES `packages` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


-- package_classes definition

CREATE TABLE `package_classes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `package_id` int(11) DEFAULT NULL,
  `class_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `package_id` (`package_id`),
  KEY `class_id` (`class_id`),
  CONSTRAINT `pkg_cls_class_fk` FOREIGN KEY (`class_id`) REFERENCES `class` (`id`),
  CONSTRAINT `pkg_cls_package_fk` FOREIGN KEY (`package_id`) REFERENCES `packages` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--  package_courses definition

CREATE TABLE `package_courses` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `package_id` int(11) DEFAULT NULL,
  `course_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `package_course_pkg_idx` (`package_id`),
  KEY `package_course__srv_idx` (`course_id`),
  CONSTRAINT `package_course_pkg` FOREIGN KEY (`package_id`) REFERENCES `packages` (`id`),
  CONSTRAINT `package_course_srv` FOREIGN KEY (`course_id`) REFERENCES `course` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--  package_services definition

CREATE TABLE `package_services` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `package_id` int(11) DEFAULT NULL,
  `service_id` int(11) DEFAULT NULL,
  `created_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `created_by` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `package_service_pkg_idx` (`package_id`),
  KEY `package_service_srv_idx` (`service_id`),
  CONSTRAINT `package_service_pkg` FOREIGN KEY (`package_id`) REFERENCES `packages` (`id`),
  CONSTRAINT `package_service_srv` FOREIGN KEY (`service_id`) REFERENCES `services` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

  --  rule_types definition

CREATE TABLE `rule_types` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `rule_type` varchar(45) NOT NULL,
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `created_by` int(11) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `deleted_at` datetime DEFAULT NULL,
  `deleted` enum('1','0') NOT NULL DEFAULT '0',
  `updated_by` int(11) DEFAULT NULL,
   tenant_id bigint(20) unsigned NULL,
   PRIMARY KEY (`id`),
   FOREIGN KEY (tenant_id) REFERENCES tenant(tenant_id),
  UNIQUE KEY `rule_type` (`rule_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


--  fee_rules definition

CREATE TABLE `fee_rules` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `rule_name` varchar(245)  NOT NULL,
  `rule_type_id` int(11) DEFAULT NULL,
  `start_date` datetime DEFAULT NULL,
  `end_date` datetime DEFAULT NULL,
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime DEFAULT NULL,
  `deleted_at` datetime DEFAULT NULL,
  `deleted` enum('1','0') NOT NULL DEFAULT '0',
  `amount` decimal(10,2) DEFAULT NULL,
  `due_date` datetime DEFAULT NULL,
  `package_id` int(11) DEFAULT NULL,
  `institute_id` int(11) DEFAULT NULL,
  `taluka_id` int(11) DEFAULT NULL,
  `district_id` int(11) DEFAULT NULL,
  `state_id` int(11) DEFAULT NULL,
  `division_id` int(11) DEFAULT NULL,
  `amount_type` varchar(10) DEFAULT NULL,
  `rule_code` varchar(50) DEFAULT NULL,
  role_id BIGINT(20) UNSIGNED DEFAULT NULL,
  `incentive_cap` int(11) DEFAULT NULL,
  `rules_amount` varchar(255) DEFAULT NULL,
  `package_type` varchar(40) DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL,
  `parent_package_ids` varchar(255) DEFAULT NULL,
   tenant_id bigint(20) unsigned NULL,
  PRIMARY KEY (`id`),
  KEY `rule_institute_idx` (`institute_id`),
  KEY `rule_taluka_idx` (`taluka_id`),
  KEY `rule_district_idx` (`district_id`),
  KEY `rule_state_idx` (`state_id`),
  KEY `package_id` (`package_id`),
  KEY `rule_type_id` (`rule_type_id`),
  KEY `role_id` (`role_id`),
  KEY `division_id` (`division_id`),
  CONSTRAINT `rule_district` FOREIGN KEY (`district_id`) REFERENCES `district` (`id`),
  CONSTRAINT `rule_institute` FOREIGN KEY (`institute_id`) REFERENCES `institutes` (`id`),
  CONSTRAINT `rule_package` FOREIGN KEY (`package_id`) REFERENCES `packages` (`id`),
  CONSTRAINT `rule_division` FOREIGN KEY (`division_id`) REFERENCES `division` (`id`),
  CONSTRAINT `rule_role_fk` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`),
  CONSTRAINT `rule_state` FOREIGN KEY (`state_id`) REFERENCES `state` (`id`),
  CONSTRAINT `rule_taluka` FOREIGN KEY (`taluka_id`) REFERENCES `taluka` (`id`),
  CONSTRAINT `rule_type_id_fk` FOREIGN KEY (`rule_type_id`) REFERENCES `rule_types` (`id`),
    FOREIGN KEY (tenant_id) REFERENCES tenant(tenant_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--    `commission_level` int(11) DEFAULT NULL,
--   KEY `commission_level` (`commission_level`),
--  CONSTRAINT `rule_commission_level_fk` FOREIGN KEY (`commission_level`) REFERENCES `access_level` (`id`),

