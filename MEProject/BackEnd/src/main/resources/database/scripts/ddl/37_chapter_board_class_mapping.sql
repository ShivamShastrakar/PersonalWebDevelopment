--liquibase formatted sql
--changeset {narendra}:{id}

CREATE TABLE chapter_board_class_mapping (
  id int(11) NOT NULL AUTO_INCREMENT,
  chapter_id int(11) DEFAULT NULL,
  class_id int(11) DEFAULT NULL,
  board_id int(11) DEFAULT NULL,
  PRIMARY KEY (id),
  KEY chapter_id (chapter_id),
  KEY class_id (class_id),
  KEY board_id (board_id),
  CONSTRAINT board_id_fk FOREIGN KEY (board_id) REFERENCES board (id),
  CONSTRAINT chapter_id_fk FOREIGN KEY (chapter_id) REFERENCES chapters (id),
  CONSTRAINT class_id_fk FOREIGN KEY (class_id) REFERENCES class (id)
) ENGINE=InnoDB CHARSET=utf8mb4;