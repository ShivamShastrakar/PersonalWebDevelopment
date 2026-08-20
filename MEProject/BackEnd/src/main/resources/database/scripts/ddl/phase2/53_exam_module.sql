--liquibase formatted sql
--changeset {narendra}:{id}

DROP TABLE IF EXISTS syllabus_chapter;
DROP TABLE IF EXISTS syllabus;

CREATE TABLE syllabus (
    id INT AUTO_INCREMENT PRIMARY KEY,

    tenant_id BIGINT UNSIGNED DEFAULT NULL,
    created_by BIGINT UNSIGNED DEFAULT NULL,
    updated_by BIGINT UNSIGNED DEFAULT NULL,

    class_id INT NOT NULL,
    subject_id INT NOT NULL,
    board_id INT DEFAULT NULL,

    name VARCHAR(100),
    medium VARCHAR(20) NOT NULL,
    academic_year INT NOT NULL,
    status VARCHAR(20) NOT NULL,

    UNIQUE KEY uk_syllabus (class_id, subject_id, medium, academic_year),

    CONSTRAINT fk_syllabus_tenant
        FOREIGN KEY (tenant_id) REFERENCES tenant(tenant_id),

    CONSTRAINT fk_syllabus_board
        FOREIGN KEY (board_id) REFERENCES board(id),

    CONSTRAINT fk_syllabus_class
        FOREIGN KEY (class_id) REFERENCES class(id),

    CONSTRAINT fk_syllabus_subject
        FOREIGN KEY (subject_id) REFERENCES subject(subject_id)
);


CREATE TABLE syllabus_chapter (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    syllabus_id INT NOT NULL,
    chapter_id BIGINT NOT NULL,
    number_of_questions INT,
    coverage_percentage DECIMAL(5,2) NOT NULL,
    marks INT,
    FOREIGN KEY (syllabus_id) REFERENCES syllabus(id) ON DELETE CASCADE
);
