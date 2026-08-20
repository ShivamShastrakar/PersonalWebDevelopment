--liquibase formatted sql
--changeset {narendra}:{id}

ALTER DATABASE mahaexam
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_0900_ai_ci;

ALTER TABLE syllabus
CONVERT TO CHARACTER SET utf8mb4
COLLATE utf8mb4_0900_ai_ci;

ALTER TABLE chapters
CONVERT TO CHARACTER SET utf8mb4
COLLATE utf8mb4_0900_ai_ci;