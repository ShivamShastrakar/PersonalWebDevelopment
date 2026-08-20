--liquibase formatted sql
--changeset dishika:70_question_paper_questions_alter

ALTER TABLE question_paper_questions
ADD COLUMN subject_id int NULL AFTER question_paper_id,
ADD COLUMN part_id BIGINT  NULL AFTER subject_id,
ADD COLUMN section_id BIGINT  NULL AFTER part_id;

-- Add foreign key for subject_id (assuming subject table is named 'subject' and PK is 'id')
ALTER TABLE question_paper_questions
ADD CONSTRAINT fk_qpq_subject_id FOREIGN KEY (subject_id) REFERENCES subject(subject_id);

-- Add foreign key for part_id (assuming part table is named 'part' and PK is 'id')
ALTER TABLE question_paper_questions
ADD CONSTRAINT fk_qpq_part_id FOREIGN KEY (part_id) REFERENCES part(id);

-- Add foreign key for section_id (assuming section table is named 'section' and PK is 'id')
ALTER TABLE question_paper_questions
ADD CONSTRAINT fk_qpq_section_id FOREIGN KEY (section_id) REFERENCES sections(id);
