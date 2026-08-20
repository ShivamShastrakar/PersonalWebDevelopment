--liquibase formatted sql

--changeset dishika:102_board_subject_question_type_mapping_data

-- =====================================================================
-- MASTER DATA: board_subject_question_type_mapping
-- Seeds all active board × subject combinations with the two question
-- types currently enabled in the UI (mcq, paragraph-based-mcq).
-- Run AFTER:
--   100_question_type.sql    (creates question_type table)
--   101_question_type_data.sql (inserts question_type rows)
--   102_question_type_mapping.sql (creates mapping table)
-- =====================================================================

SET @board_id = (SELECT id FROM board WHERE board_name = 'MSCE' LIMIT 1);
SET @question_type_mcq_id = (SELECT id FROM question_type WHERE code = 'mcq' LIMIT 1);
SET @question_type_paragraph_based_mcq_id = (SELECT id FROM question_type WHERE code = 'paragraph-based-mcq' LIMIT 1);

SET @eng_fl_subject_id = (SELECT subject_id FROM subject WHERE subject_name = 'English – First Language' LIMIT 1);
SET @math_eng_subject_id = (SELECT subject_id FROM subject WHERE subject_name = 'Math – English' LIMIT 1);
SET @marathi_third_lang_subject_id = (SELECT subject_id FROM subject WHERE subject_name = 'Marathi – Third Language' LIMIT 1);
SET @iq_eng_subject_id = (SELECT subject_id FROM subject WHERE subject_name = 'IQ – English' LIMIT 1);
SET @marathi_first_lang_subject_id = (SELECT subject_id FROM subject WHERE subject_name = 'Marathi – First Language' LIMIT 1);
SET @math_marathi_subject_id = (SELECT subject_id FROM subject WHERE subject_name = 'Math – Marathi' LIMIT 1);
SET @english_third_lang_subject_id = (SELECT subject_id FROM subject WHERE subject_name = 'English – Third Language' LIMIT 1);
SET @iq_marathi_subject_id = (SELECT subject_id FROM subject WHERE subject_name = 'IQ – Marathi' LIMIT 1);

INSERT INTO board_subject_question_type_mapping
    (board_id, subject_id, question_type_id, created_by)
VALUES
    (@board_id, @eng_fl_subject_id,           @question_type_mcq_id,                    101),
    (@board_id, @eng_fl_subject_id,           @question_type_paragraph_based_mcq_id,    101),
    (@board_id, @math_eng_subject_id,         @question_type_mcq_id,                    101),
    (@board_id, @marathi_third_lang_subject_id, @question_type_mcq_id,                  101),
    (@board_id, @marathi_third_lang_subject_id, @question_type_paragraph_based_mcq_id,  101),
    (@board_id, @iq_eng_subject_id,           @question_type_mcq_id,                    101),
    (@board_id, @iq_eng_subject_id,           @question_type_paragraph_based_mcq_id,    101),
    (@board_id, @marathi_first_lang_subject_id, @question_type_mcq_id,                  101),
    (@board_id, @marathi_first_lang_subject_id, @question_type_paragraph_based_mcq_id,  101),
    (@board_id, @math_marathi_subject_id,     @question_type_mcq_id,                    101),
    (@board_id, @english_third_lang_subject_id, @question_type_mcq_id,                  101),
    (@board_id, @english_third_lang_subject_id, @question_type_paragraph_based_mcq_id,  101),
    (@board_id, @iq_marathi_subject_id,       @question_type_mcq_id,                    101),
    (@board_id, @iq_marathi_subject_id,       @question_type_paragraph_based_mcq_id,    101)
ON DUPLICATE KEY UPDATE updated_at = CURRENT_TIMESTAMP;
