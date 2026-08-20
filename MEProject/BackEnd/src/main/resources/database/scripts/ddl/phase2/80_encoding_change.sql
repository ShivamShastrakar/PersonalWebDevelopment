--liquibase formatted sql
--changeset {narendra}:{id}
ALTER TABLE student
    CONVERT TO CHARACTER SET utf8mb4
    COLLATE utf8mb4_0900_ai_ci;