--liquibase formatted sql
--changeset {narendra}:{id}


CREATE TABLE `state` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `state_name` varchar(45) DEFAULT NULL,
  `state_alias_name` varchar(15) DEFAULT NULL,
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime DEFAULT NULL,
  `deleted_at` datetime DEFAULT NULL,
  `deleted` enum('1','0') NOT NULL DEFAULT '0',
  `tenant_id` BIGINT(20) UNSIGNED, -- NULL = global/system-wide role
  PRIMARY KEY (`id`),
  UNIQUE KEY `state_name` (`state_name`)
);


CREATE TABLE `division` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `division_name` varchar(245) DEFAULT NULL,
  `division_code` int(11) NOT NULL,
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime DEFAULT NULL,
  `deleted_at` datetime DEFAULT NULL,
  `deleted` enum('1','0') NOT NULL DEFAULT '0',
  `tenant_id` BIGINT(20) UNSIGNED, -- NULL = global/system-wide role
  `state_id` int(11),
  PRIMARY KEY (`id`),
  UNIQUE KEY `division_name` (`division_name`,`state_id`),
  CONSTRAINT `division_state_id_fk` FOREIGN KEY (`state_id`) REFERENCES `state` (`id`)
) ENGINE=InnoDB ;


CREATE TABLE `zone` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `zone_name` varchar(100) DEFAULT NULL,
  `zone_code` varchar(100) DEFAULT NULL,
  `deleted` enum('1','0') NOT NULL DEFAULT '0',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime DEFAULT NULL,
  `tenant_id` BIGINT(20) UNSIGNED, -- NULL = global/system-wide role
  `state_id` int(11),
  PRIMARY KEY (`id`),
   FOREIGN KEY (tenant_id) REFERENCES tenant(tenant_id) ,
   CONSTRAINT `zone_state_id_fk` FOREIGN KEY (`state_id`) REFERENCES `state` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;



CREATE TABLE `district` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `district_name` varchar(245) DEFAULT NULL,
  `district_code` int(11),
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime DEFAULT NULL,
  `deleted_at` datetime DEFAULT NULL,
  `deleted` enum('1','0') NOT NULL DEFAULT '0',
  `tenant_id` BIGINT(20) UNSIGNED, -- NULL = global/system-wide role
  `state_id` int(11),
  `zone_id`int(11),
  `division_id` int (11),
  PRIMARY KEY (`id`),
  CONSTRAINT `district_state_fk_idx` FOREIGN KEY (`state_id`) REFERENCES `state` (`id`),
  CONSTRAINT `district_division_fk_idx` FOREIGN KEY (`division_id`) REFERENCES `division` (`id`),
  CONSTRAINT `district_zone_fk_idx` FOREIGN KEY (`zone_id`) REFERENCES `zone` (`id`)
) ;

  
CREATE TABLE `taluka` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `taluka_name` varchar(245) DEFAULT NULL,
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime DEFAULT NULL,
  `deleted_at` datetime DEFAULT NULL,
  `deleted` enum('1','0') NOT NULL DEFAULT '0',
  `district_id` int(11) DEFAULT NULL,
  `tenant_id` BIGINT(20) UNSIGNED, -- NULL = global/system-wide role
  PRIMARY KEY (`id`),
  KEY `taluka_district_fk` (`district_id`),
  CONSTRAINT `taluka_district_fk` FOREIGN KEY (`district_id`) REFERENCES `district` (`id`)
) ;