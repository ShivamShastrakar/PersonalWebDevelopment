--liquibase formatted sql

--changeset dishika:103_board_subject_question_type_mapping_data_correction

SET @board_id = (SELECT id FROM board WHERE board_name = 'MSCE' LIMIT 1);
SET @question_type_paragraph_based_mcq_id = (SELECT id FROM question_type WHERE code = 'paragraph-based-mcq' LIMIT 1);

SET @iq_eng_subject_id = (SELECT subject_id FROM subject WHERE subject_name = 'IQ – English' LIMIT 1);
SET @iq_marathi_subject_id = (SELECT subject_id FROM subject WHERE subject_name = 'IQ – Marathi' LIMIT 1);

DELETE FROM board_subject_question_type_mapping
WHERE board_id = @board_id
  AND subject_id IN (@iq_eng_subject_id, @iq_marathi_subject_id)
  AND question_type_id IN (@question_type_paragraph_based_mcq_id);

UPDATE board_subject_question_type_mapping SET tenant_id = 101 WHERE 1 = 1;

UPDATE question_type SET tenant_id = 101 WHERE 1 = 1;
