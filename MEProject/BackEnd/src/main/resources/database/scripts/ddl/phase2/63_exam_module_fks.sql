--liquibase formatted sql
--changeset {narendra}:{id}


SET @fk_name = 'fk_exam_template_exam';

SET @sql = (
    SELECT IF(
        EXISTS (
            SELECT 1
            FROM information_schema.TABLE_CONSTRAINTS
            WHERE CONSTRAINT_SCHEMA = DATABASE()
              AND TABLE_NAME = 'exam_paper_template'
              AND CONSTRAINT_NAME = @fk_name
        ),
        'SELECT ''fk_exam_template_exam already exists'';',
        'ALTER TABLE exam_paper_template
         ADD CONSTRAINT fk_exam_template_exam
         FOREIGN KEY (exam_id)
         REFERENCES exam(id);'
    )
);

PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

SET @fk_name = 'fk_exam_template_paper_template';

SET @sql = (
    SELECT IF(
        EXISTS (
            SELECT 1
            FROM information_schema.TABLE_CONSTRAINTS
            WHERE CONSTRAINT_SCHEMA = DATABASE()
              AND TABLE_NAME = 'exam_paper_template'
              AND CONSTRAINT_NAME = @fk_name
        ),
        'SELECT ''fk_exam_template_paper_template already exists'';',
        'ALTER TABLE exam_paper_template
         ADD CONSTRAINT fk_exam_template_paper_template
         FOREIGN KEY (paper_template_id)
         REFERENCES paper_template(id);'
    )
);

PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;
SET @fk_name = 'fk_pts_paper_template';

SET @sql = (
    SELECT IF(
        EXISTS (
            SELECT 1
            FROM information_schema.TABLE_CONSTRAINTS
            WHERE CONSTRAINT_SCHEMA = DATABASE()
              AND TABLE_NAME = 'paper_template_subject'
              AND CONSTRAINT_NAME = @fk_name
        ),
        'SELECT ''fk_pts_paper_template already exists'';',
        'ALTER TABLE paper_template_subject
         ADD CONSTRAINT fk_pts_paper_template
         FOREIGN KEY (paper_template_id)
         REFERENCES paper_template(id);'
    )
);


SET @fk_name = 'fk_part_paper_template';

SET @sql = (
    SELECT IF(
        EXISTS (
            SELECT 1
            FROM information_schema.TABLE_CONSTRAINTS
            WHERE CONSTRAINT_SCHEMA = DATABASE()
              AND TABLE_NAME = 'part'
              AND CONSTRAINT_NAME = @fk_name
        ),
        'SELECT ''fk_part_paper_template already exists'';',
        'ALTER TABLE part
         ADD CONSTRAINT fk_part_paper_template
         FOREIGN KEY (paper_template_id)
         REFERENCES paper_template(id);'
    )
);

PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;


CREATE INDEX idx_exam_paper_template_id ON exam(paper_template_id);
CREATE INDEX idx_exam_template_exam_id ON exam_paper_template(exam_id);
CREATE INDEX idx_exam_template_pt_id ON exam_paper_template(paper_template_id);
CREATE INDEX idx_part_pt_id ON part(paper_template_id);
CREATE INDEX idx_part_subject_id ON part(subject_id);
