--liquibase formatted sql

--changeset 100_question_type:100

DROP TABLE IF EXISTS question_type;

CREATE TABLE question_type (
       id INT PRIMARY KEY AUTO_INCREMENT,
       code VARCHAR(50) UNIQUE,
       name VARCHAR(100),
       description VARCHAR(255),
       created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);