--liquibase formatted sql
--changeset {narendra}:{id}

CREATE TABLE package_question_paper_mapping (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    tenant_id BIGINT(20) UNSIGNED DEFAULT NULL,
    package_id INT NOT NULL,
    question_paper_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_package FOREIGN KEY (package_id) REFERENCES packages (id),
    CONSTRAINT fk_question_paper FOREIGN KEY (question_paper_id) REFERENCES question_paper (id),
    UNIQUE (package_id, question_paper_id)
);