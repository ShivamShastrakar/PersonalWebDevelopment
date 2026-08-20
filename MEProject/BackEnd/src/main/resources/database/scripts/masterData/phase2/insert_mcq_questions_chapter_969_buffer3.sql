--liquibase formatted sql
--changeset dishika:add-questions-chapter-969-vocabulary-buffer3-20260224

-- ADDITIONAL BUFFER QUESTIONS FOR CHAPTER 969 (Vocabulary) - BUFFER 3
-- These are extra questions for each SUKA and difficulty combination for redundancy

-- SKILL × HARD (Buffer 3)
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
 'Which word is a synonym for "difficult"?',
 '{"option1":"hard","option2":"challenging","option3":"easy","option4":"simple"}',
 '{"correctOption":2}',
 '"Challenging" is a synonym for "difficult".',
 'SKILL', 'HARD', 101);

-- SKILL × MEDIUM (Buffer 3)
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
 'What is the meaning of the word "generous"?',
 '{"option1":"kind","option2":"selfish","option3":"mean","option4":"angry"}',
 '{"correctOption":1}',
 '"Generous" means kind and giving.',
 'SKILL', 'MEDIUM', 101);

-- SKILL × EASY (Buffer 3)
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
 'Which word best completes the sentence: The sun is very _____.',
 '{"option1":"hot","option2":"cold","option3":"wet","option4":"dark"}',
 '{"correctOption":1}',
 '"Hot" is the correct adjective for the sun.',
 'SKILL', 'EASY', 101);

