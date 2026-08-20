--liquibase formatted sql
--changeset narendra:99_addtableexamgroupdtl

CREATE TABLE exam_group_dtls (
  id INT(2) NOT NULL AUTO_INCREMENT,
  name VARCHAR(75) NOT NULL,
  description VARCHAR(200) DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
