--liquibase formatted sql
--changeset {narendra}:{id}

RENAME TABLE exam TO question_paper;
RENAME TABLE exam_paper_template TO question_paper_template;

ALTER TABLE question_paper
CHANGE exam_name question_paper_name VARCHAR(150) NOT NULL;


ALTER TABLE question_paper_template
CHANGE exam_id question_paper_id BIGINT NOT NULL;

 -- Disable FK checks temporarily (safe for structure change)
SET FOREIGN_KEY_CHECKS = 0;

-- Drop old unique index
ALTER TABLE question_paper_template
DROP INDEX uk_exam_template;

-- Create new clean unique index
ALTER TABLE question_paper_template
ADD UNIQUE KEY uk_qp_template (question_paper_id, paper_template_id);

-- Recreate foreign key
ALTER TABLE question_paper_template
ADD CONSTRAINT fk_qp_template_qp
FOREIGN KEY (question_paper_id)
REFERENCES question_paper(id);

-- Re-enable FK checks
SET FOREIGN_KEY_CHECKS = 1;
