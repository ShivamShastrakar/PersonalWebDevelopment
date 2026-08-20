--liquibase formatted sql
--changeset narendra:102_addtableexamgroup_packagecategorymapper

CREATE TABLE exam_group_package_category_mappingdtls (
  id INT(2) NOT NULL AUTO_INCREMENT,
  exam_group_id INT(2) NOT NULL DEFAULT 0,
  package_category_id INT(2) DEFAULT NULL,
  PRIMARY KEY (id),
  KEY fk_exam_group_mapping_exam_group_id (exam_group_id),
  KEY fk_exam_group_mapping_package_category_id (package_category_id),
  CONSTRAINT fk_exam_group_mapping_exam_group_id FOREIGN KEY (exam_group_id) REFERENCES exam_group_dtls (id),
  CONSTRAINT fk_exam_group_mapping_package_category_id FOREIGN KEY (package_category_id) REFERENCES package_category (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
