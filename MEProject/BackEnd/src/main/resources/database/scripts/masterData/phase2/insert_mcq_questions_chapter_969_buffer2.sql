--liquibase formatted sql
--changeset dishika:add-questions-chapter-969-vocabulary-buffer2-20260224

-- ADDITIONAL BUFFER QUESTIONS FOR CHAPTER 969 (Vocabulary)
-- These are extra questions for each SUKA and difficulty combination for redundancy

-- SKILL × HARD (Buffer 2)
INSERT INTO questions (
    board_id, subject_id, class_id, medium, chapter_id, topic_id,
    question_type, question_text, options, correct_answer, answer_explanation,
    skill_level, difficulty_level, created_by
) VALUES
((SELECT id FROM board WHERE board_name = 'MSCE' LIMIT 1),
 (SELECT subject_id FROM subject WHERE subject_name = 'English' LIMIT 1),
 (SELECT id FROM class WHERE class_name = '4' LIMIT 1),
 'English',
 (SELECT id FROM chapters WHERE chapter_name = 'Vocabulary' AND subject_id = (SELECT subject_id FROM subject WHERE subject_name = 'English' LIMIT 1) LIMIT 1),
 (SELECT topic_id FROM topics WHERE chapter_id = (SELECT id FROM chapters WHERE chapter_name = 'Vocabulary' AND subject_id = (SELECT subject_id FROM subject WHERE subject_name = 'English' LIMIT 1) LIMIT 1) LIMIT 1),
 'MCQ',
 'Which word is an antonym for "difficult"?',
 '{"option1":"hard","option2":"easy","option3":"tough","option4":"rough"}',
 '{"correctOption":2}',
 '"Easy" is the opposite of "difficult".',
 'SKILL', 'HARD', 101);

-- SKILL × MEDIUM (Buffer 2)
INSERT INTO questions (
    board_id, subject_id, class_id, medium, chapter_id, topic_id,
    question_type, question_text, options, correct_answer, answer_explanation,
    skill_level, difficulty_level, created_by
) VALUES
((SELECT id FROM board WHERE board_name = 'MSCE' LIMIT 1),
 (SELECT subject_id FROM subject WHERE subject_name = 'English' LIMIT 1),
 (SELECT id FROM class WHERE class_name = '4' LIMIT 1),
 'English',
 (SELECT id FROM chapters WHERE chapter_name = 'Vocabulary' AND subject_id = (SELECT subject_id FROM subject WHERE subject_name = 'English' LIMIT 1) LIMIT 1),
 (SELECT topic_id FROM topics WHERE chapter_id = (SELECT id FROM chapters WHERE chapter_name = 'Vocabulary' AND subject_id = (SELECT subject_id FROM subject WHERE subject_name = 'English' LIMIT 1) LIMIT 1) LIMIT 1),
 'MCQ',
 'What is the meaning of the word "fragile"?',
 '{"option1":"strong","option2":"breakable","option3":"fast","option4":"old"}',
 '{"correctOption":2}',
 '"Fragile" means breakable.',
 'SKILL', 'MEDIUM', 101);

-- SKILL × EASY (Buffer 2)
INSERT INTO questions (
    board_id, subject_id, class_id, medium, chapter_id, topic_id,
    question_type, question_text, options, correct_answer, answer_explanation,
    skill_level, difficulty_level, created_by
) VALUES
((SELECT id FROM board WHERE board_name = 'MSCE' LIMIT 1),
 (SELECT subject_id FROM subject WHERE subject_name = 'English' LIMIT 1),
 (SELECT id FROM class WHERE class_name = '4' LIMIT 1),
 'English',
 (SELECT id FROM chapters WHERE chapter_name = 'Vocabulary' AND subject_id = (SELECT subject_id FROM subject WHERE subject_name = 'English' LIMIT 1) LIMIT 1),
 (SELECT topic_id FROM topics WHERE chapter_id = (SELECT id FROM chapters WHERE chapter_name = 'Vocabulary' AND subject_id = (SELECT subject_id FROM subject WHERE subject_name = 'English' LIMIT 1) LIMIT 1) LIMIT 1),
 'MCQ',
 'Which word best completes the sentence: The bird can _____.',
 '{"option1":"swim","option2":"fly","option3":"run","option4":"bark"}',
 '{"correctOption":2}',
 '"Fly" is the correct verb for a bird.',
 'SKILL', 'EASY', 101);

