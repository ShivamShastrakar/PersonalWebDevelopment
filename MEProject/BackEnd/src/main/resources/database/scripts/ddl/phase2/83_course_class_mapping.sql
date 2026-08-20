--liquibase formatted sql
--changeset {narendra}:{id}

-- SQL for course_class_mapping join table
CREATE TABLE course_class_mapping (
    id SERIAL PRIMARY KEY,
    course_id INTEGER NOT NULL,
    class_id INTEGER NOT NULL,
    CONSTRAINT fk_course FOREIGN KEY (course_id) REFERENCES course(id),
    CONSTRAINT fk_class FOREIGN KEY (class_id) REFERENCES class(id)
);
-- Add indexes for performance
CREATE INDEX idx_course_class_course_id ON course_class_mapping(course_id);
CREATE INDEX idx_course_class_class_id ON course_class_mapping(class_id);

