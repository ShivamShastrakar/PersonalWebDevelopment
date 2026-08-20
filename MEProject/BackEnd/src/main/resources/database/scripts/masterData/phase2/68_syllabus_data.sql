--liquibase formatted sql
--changeset {narendra}:{id}

-- =============================================================================
-- MSCE Pre Upper Primary Scholarship Examination Syllabus Import
-- Syllabus name format: Board%s_Medium%s_Class%s_Subject%s_Year%d
-- Generated 2025 syllabi based on your chapter percent script
-- =============================================================================

START TRANSACTION;

-- Configuration variables - change as needed
SET @tenant_id         := 101;
SET @created_by        := 101;
SET @updated_by        := 101;
SET @academic_year     := 2025;
SET @status            := 'ACTIVE';

-- Board
SET @board_msce_id     := (SELECT id FROM board WHERE board_name = 'MSCE' LIMIT 1);

-- Classes
SET @class_4_id        := (SELECT id FROM class WHERE class_name = '4' LIMIT 1);
SET @class_5_id        := (SELECT id FROM class WHERE class_name = '5' LIMIT 1);
SET @class_7_id        := (SELECT id FROM class WHERE class_name = '7' LIMIT 1);
SET @class_8_id        := (SELECT id FROM class WHERE class_name = '8' LIMIT 1);

-- Subjects - English medium
SET @sub_eng_fl        := (SELECT subject_id FROM subject WHERE subject_name = 'English – First Language' LIMIT 1);
SET @sub_math_eng      := (SELECT subject_id FROM subject WHERE subject_name = 'Math – English'          LIMIT 1);
SET @sub_mar_tl        := (SELECT subject_id FROM subject WHERE subject_name = 'Marathi – Third Language' LIMIT 1);
SET @sub_iq_eng        := (SELECT subject_id FROM subject WHERE subject_name = 'IQ – English'            LIMIT 1);

-- Subjects - Marathi medium
SET @sub_mar_fl        := (SELECT subject_id FROM subject WHERE subject_name = 'Marathi – First Language' LIMIT 1);
SET @sub_math_mar      := (SELECT subject_id FROM subject WHERE subject_name = 'Math – Marathi'          LIMIT 1);
SET @sub_eng_tl        := (SELECT subject_id FROM subject WHERE subject_name = 'English – Third Language' LIMIT 1);
SET @sub_iq_mar        := (SELECT subject_id FROM subject WHERE subject_name = 'IQ – Marathi'            LIMIT 1);

-- Helper table to make insertion cleaner (temporary)
DROP TEMPORARY TABLE IF EXISTS temp_syllabus_input;
CREATE TEMPORARY TABLE temp_syllabus_input (
    class_id      INT,
    subject_id    INT,
    medium        VARCHAR(20),
    subject_code  VARCHAR(50),    -- short/clean name for syllabus name
    board_short   VARCHAR(10)
);

-- English Medium entries
INSERT INTO temp_syllabus_input (class_id, subject_id, medium, subject_code, board_short)
VALUES
    (@class_4_id, @sub_eng_fl,   'English', 'EnglishFirstLanguage', 'MSCE'),
    (@class_4_id, @sub_math_eng, 'English', 'MathEnglish',         'MSCE'),
    (@class_4_id, @sub_mar_tl,   'English', 'MarathiThirdLanguage','MSCE'),
    (@class_4_id, @sub_iq_eng,   'English', 'IQEnglish',           'MSCE'),

    (@class_5_id, @sub_eng_fl,   'English', 'EnglishFirstLanguage', 'MSCE'),
    (@class_5_id, @sub_math_eng, 'English', 'MathEnglish',         'MSCE'),
    (@class_5_id, @sub_mar_tl,   'English', 'MarathiThirdLanguage','MSCE'),
    (@class_5_id, @sub_iq_eng,   'English', 'IQEnglish',           'MSCE'),

    (@class_7_id, @sub_eng_fl,   'English', 'EnglishFirstLanguage', 'MSCE'),
    (@class_7_id, @sub_math_eng, 'English', 'MathEnglish',         'MSCE'),
    (@class_7_id, @sub_mar_tl,   'English', 'MarathiThirdLanguage','MSCE'),

    (@class_8_id, @sub_eng_fl,   'English', 'EnglishFirstLanguage', 'MSCE'),
    (@class_8_id, @sub_math_eng, 'English', 'MathEnglish',         'MSCE'),
    (@class_8_id, @sub_mar_tl,   'English', 'MarathiThirdLanguage','MSCE');

-- Marathi Medium entries
INSERT INTO temp_syllabus_input (class_id, subject_id, medium, subject_code, board_short)
VALUES
    (@class_4_id, @sub_mar_fl,   'Marathi', 'MarathiFirstLanguage', 'MSCE'),
    (@class_4_id, @sub_math_mar, 'Marathi', 'MathMarathi',          'MSCE'),
    (@class_4_id, @sub_eng_tl,   'Marathi', 'EnglishThirdLanguage', 'MSCE'),
    (@class_4_id, @sub_iq_mar,   'Marathi', 'IQMarathi',            'MSCE'),

    (@class_5_id, @sub_mar_fl,   'Marathi', 'MarathiFirstLanguage', 'MSCE'),
    (@class_5_id, @sub_math_mar, 'Marathi', 'MathMarathi',          'MSCE'),
    (@class_5_id, @sub_eng_tl,   'Marathi', 'EnglishThirdLanguage', 'MSCE'),
    (@class_5_id, @sub_iq_mar,   'Marathi', 'IQMarathi',            'MSCE'),

    (@class_7_id, @sub_mar_fl,   'Marathi', 'MarathiFirstLanguage', 'MSCE'),
    (@class_7_id, @sub_math_mar, 'Marathi', 'MathMarathi',          'MSCE'),
    (@class_7_id, @sub_eng_tl,   'Marathi', 'EnglishThirdLanguage', 'MSCE'),
    (@class_7_id, @sub_iq_mar,   'Marathi', 'IQMarathi',            'MSCE'),

    (@class_8_id, @sub_mar_fl,   'Marathi', 'MarathiFirstLanguage', 'MSCE'),
    (@class_8_id, @sub_math_mar, 'Marathi', 'MathMarathi',          'MSCE'),
    (@class_8_id, @sub_eng_tl,   'Marathi', 'EnglishThirdLanguage', 'MSCE'),
    (@class_8_id, @sub_iq_mar,   'Marathi', 'IQMarathi',            'MSCE');

-- Final insert into syllabus with formatted name
INSERT INTO syllabus (
    tenant_id, created_by, updated_by,
    class_id, subject_id, board_id,
    name,
    medium, academic_year, status
)
SELECT
    @tenant_id, @created_by, @updated_by,
    t.class_id, t.subject_id, @board_msce_id,
    CONCAT(
        'Board', t.board_short,
        '_Medium', t.medium,
        '_Class', c.class_name,
        '_Subject', REPLACE(t.subject_code, ' ', ''),   -- remove spaces
        '_Year', @academic_year
    ) AS name,
    t.medium,
    @academic_year,
    @status
FROM temp_syllabus_input t
JOIN class c ON c.id = t.class_id;

-- Drop helper table
DROP TEMPORARY TABLE IF EXISTS temp_syllabus_input;

-- =============================================================================
-- Now link chapters with their percentage weights
-- (assuming chapters table already has percent column filled)
-- =============================================================================

INSERT INTO syllabus_chapter (
    syllabus_id,
    chapter_id,
    coverage_percentage,
    number_of_questions,
    marks
)
SELECT
    s.id                          AS syllabus_id,
    ch.id                         AS chapter_id,
    ch.percent                    AS coverage_percentage,
    NULL                          AS number_of_questions,
    NULL                          AS marks
FROM syllabus s
JOIN chapters ch
    ON ch.class_id    = s.class_id
   AND ch.subject_id  = s.subject_id
   AND ch.board_id    = s.board_id
WHERE s.academic_year = @academic_year
  AND s.status        = @status
  AND ch.percent      IS NOT NULL
  AND ch.percent      > 0;

COMMIT;