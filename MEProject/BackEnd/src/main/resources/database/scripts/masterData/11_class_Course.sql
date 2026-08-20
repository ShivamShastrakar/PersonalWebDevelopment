--liquibase formatted sql
--changeset {narendra}:{id}

INSERT INTO class
(id,  class_name, created_at,  deleted)
VALUES(1,  '5th', CURRENT_TIMESTAMP, '0');
INSERT INTO class
(id,  class_name, created_at,  deleted)
VALUES(2,  '6th', CURRENT_TIMESTAMP, '0');
INSERT INTO class
(id,  class_name, created_at,  deleted)
VALUES(3,  '7th', CURRENT_TIMESTAMP, '0');
INSERT INTO class
(id,  class_name, created_at,  deleted)
VALUES(4,  '8th', CURRENT_TIMESTAMP, '0');
INSERT INTO class
(id,  class_name, created_at,  deleted)
VALUES(5,  '9th', CURRENT_TIMESTAMP, '0');
INSERT INTO class
(id,  class_name, created_at,  deleted)
VALUES(6,  '10th', CURRENT_TIMESTAMP, '0');

INSERT INTO course
(course_name, course_details, deleted)
VALUES('5th Scholarship Exam', '5th Scholarship Exam', '0');
INSERT INTO course
(course_name, course_details, deleted)
VALUES('6th Scholarship Exam', '6th Scholarship Exam', '0');