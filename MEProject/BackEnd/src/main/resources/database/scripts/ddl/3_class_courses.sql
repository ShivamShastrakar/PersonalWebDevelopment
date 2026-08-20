--liquibase formatted sql
--changeset {narendra}:{id}

CREATE TABLE subject_group (
  group_id INT AUTO_INCREMENT PRIMARY KEY,
  tenant_id BIGINT(20) UNSIGNED DEFAULT NULL,
  group_name VARCHAR(50) NOT NULL, -- example: PCM, PCB, etc.
  description TEXT,

  created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_at DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  deleted_at DATETIME DEFAULT NULL,
  deleted ENUM('1','0') NOT NULL DEFAULT '0',

  UNIQUE KEY uk_group_name_tenant (tenant_id, group_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE subject (
  subject_id INT AUTO_INCREMENT PRIMARY KEY,
  tenant_id BIGINT(20) UNSIGNED DEFAULT NULL,
  subject_name VARCHAR(100) NOT NULL,

  created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_at DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  deleted_at DATETIME DEFAULT NULL,
  deleted ENUM('1','0') NOT NULL DEFAULT '0',

  UNIQUE KEY uk_subject_name_tenant (tenant_id, subject_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE subject_group_mapping (
  mapping_id INT AUTO_INCREMENT PRIMARY KEY,
  group_id INT NOT NULL,
  subject_id INT NOT NULL,

  created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_at DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  deleted_at DATETIME DEFAULT NULL,
  deleted ENUM('1','0') NOT NULL DEFAULT '0',

  UNIQUE KEY uk_group_subject (group_id, subject_id),
  FOREIGN KEY (group_id) REFERENCES subject_group(group_id),
  FOREIGN KEY (subject_id) REFERENCES subject(subject_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


CREATE TABLE board (
  id INT AUTO_INCREMENT PRIMARY KEY,
  tenant_id BIGINT(20) UNSIGNED DEFAULT NULL,
  board_name VARCHAR(145) NOT NULL,

  created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_at DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  deleted_at DATETIME DEFAULT NULL,
  deleted ENUM('1','0') NOT NULL DEFAULT '0',

  UNIQUE KEY uk_board_name_tenant (tenant_id, board_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE class (
  id INT AUTO_INCREMENT PRIMARY KEY,
  tenant_id BIGINT(20) UNSIGNED DEFAULT NULL,
  class_name VARCHAR(50) NOT NULL,

  created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_at DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  deleted_at DATETIME DEFAULT NULL,
  deleted ENUM('1','0') NOT NULL DEFAULT '0',

  UNIQUE KEY uk_class_name_tenant (tenant_id, class_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE subject_board_class_mapping (
  id INT AUTO_INCREMENT PRIMARY KEY,
  subject_id INT DEFAULT NULL,
  class_id INT DEFAULT NULL,
  board_id INT DEFAULT NULL,

  created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_at DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  deleted_at DATETIME DEFAULT NULL,
  deleted ENUM('1','0') NOT NULL DEFAULT '0',

  KEY idx_subject_id (subject_id),
  KEY idx_class_id (class_id),
  KEY idx_board_id (board_id),

  CONSTRAINT scm_subject_fk FOREIGN KEY (subject_id) REFERENCES subject(subject_id),
  CONSTRAINT scm_class_fk FOREIGN KEY (class_id) REFERENCES class(id),
  CONSTRAINT scm_board_fk FOREIGN KEY (board_id) REFERENCES board(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


CREATE TABLE course (
  id INT AUTO_INCREMENT PRIMARY KEY,
  course_name VARCHAR(245) NOT NULL,
  course_details TEXT,

  tenant_id BIGINT(20) UNSIGNED DEFAULT NULL,
  updated_by BIGINT(20) UNSIGNED DEFAULT NULL,

  created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  deleted_at DATETIME DEFAULT NULL,
  deleted ENUM('1','0') NOT NULL DEFAULT '0',

  UNIQUE KEY uk_course_name_tenant (tenant_id, course_name),
  KEY course_updated_by_idx (updated_by),

  CONSTRAINT fk_course_updated_by FOREIGN KEY (updated_by) REFERENCES users(user_id),
  CONSTRAINT fk_course_tenant FOREIGN KEY (tenant_id) REFERENCES tenant(tenant_id) 
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE course_subject_group_mapping (
  id INT AUTO_INCREMENT PRIMARY KEY,
  subject_group_id INT DEFAULT NULL,
  course_id INT DEFAULT NULL,

  created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_at DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  deleted_at DATETIME DEFAULT NULL,
  deleted ENUM('1','0') NOT NULL DEFAULT '0',

  KEY subject_group_id_idx (subject_group_id),
  KEY course_id_idx (course_id),

  CONSTRAINT course_subject_group_fk FOREIGN KEY (subject_group_id) REFERENCES subject_group(group_id),
  CONSTRAINT course_course_fk FOREIGN KEY (course_id) REFERENCES course(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
