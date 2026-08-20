--liquibase formatted sql
--changeset {narendra}:{id}

ALTER TABLE paper_templates RENAME TO paper_template;

ALTER TABLE paper_template
DROP COLUMN subject_id,
DROP COLUMN board_id,
DROP COLUMN question_config_json,
DROP COLUMN duration_minutes;

ALTER TABLE paper_template
MODIFY COLUMN name VARCHAR(255) NOT NULL,
MODIFY COLUMN medium VARCHAR(50) NOT NULL,
MODIFY COLUMN class_id BIGINT NOT NULL,
MODIFY COLUMN total_marks INTEGER NOT NULL;

ALTER TABLE paper_template
ADD COLUMN total_duration INTEGER NOT NULL AFTER class_id,
ADD COLUMN part_display_name VARCHAR(100),
ADD COLUMN number_of_parts INTEGER,
ADD COLUMN instructions JSON,
ADD COLUMN status VARCHAR(20) NOT NULL,
ADD COLUMN updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    ON UPDATE CURRENT_TIMESTAMP;



