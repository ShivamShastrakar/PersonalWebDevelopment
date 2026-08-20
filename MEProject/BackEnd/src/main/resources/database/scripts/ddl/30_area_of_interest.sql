--liquibase formatted sql
--changeset {narendra}:{id}

CREATE TABLE area_of_interest (
  id int(11) NOT NULL AUTO_INCREMENT,
  name varchar(85) DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB;