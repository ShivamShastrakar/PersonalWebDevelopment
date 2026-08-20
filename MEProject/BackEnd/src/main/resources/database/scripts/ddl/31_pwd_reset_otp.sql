--liquibase formatted sql
--changeset {narendra}:{id}

CREATE TABLE pwd_reset_otp (
  id BIGINT NOT NULL AUTO_INCREMENT,
  user_id BIGINT UNSIGNED DEFAULT NULL,
  otp varchar(10) DEFAULT NULL,
  created_at datetime DEFAULT NULL,
  expires_at datetime DEFAULT NULL,
  PRIMARY KEY (id),
  KEY fk_user_idx (user_id),
  CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users (user_id) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB;