--liquibase formatted sql
--changeset {narendra}:{id}
CREATE TABLE upload_batch (
      batch_id BIGINT AUTO_INCREMENT PRIMARY KEY,
      upload_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
      original_file_path VARCHAR(255),
      error_file_path VARCHAR(255),
      status VARCHAR(20)    -- SUCCESS, PARTIAL_FAILURE, FAILURE
);
