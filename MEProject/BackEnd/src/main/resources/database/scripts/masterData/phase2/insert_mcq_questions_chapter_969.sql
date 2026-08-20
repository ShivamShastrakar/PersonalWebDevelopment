--liquibase formatted sql
--changeset dishika:add-questions-chapter-969-vocabulary-20260224

-- =====================================================
-- COMPLETE MCQ QUESTIONS FOR CHAPTER 969 (Vocabulary)
-- =====================================================
-- Adds 3 MCQ questions for SKILL, UNDERSTANDING, KNOWLEDGE × HARD, MEDIUM, EASY
-- SUKA Distribution: {SKILL=1, UNDERSTANDING=1, KNOWLEDGE=1, APPLICATION=0}
-- Difficulty Distribution: {HARD=1, MEDIUM=1, EASY=1}
-- =====================================================

-- Get IDs
SET @board_id = (SELECT id FROM board WHERE board_name = 'MSCE' LIMIT 1);
SET @subject_id = (SELECT subject_id FROM subject WHERE subject_name = 'English' LIMIT 1);
SET @class_id = (SELECT id FROM class WHERE class_name = '4' LIMIT 1);
SET @medium = 'English';
SET @chapter_id = (SELECT id FROM chapters c WHERE c.chapter_name = 'Vocabulary' AND c.subject_id = (SELECT subject_id FROM subject WHERE subject_name = 'English' LIMIT 1) LIMIT 1);
SET @topic_id = (SELECT topic_id FROM topics WHERE chapter_id = @chapter_id LIMIT 1);
SET @question_type = 'MCQ';
SET @created_by = 101;

-- SKILL × EASY
INSERT INTO questions (
    board_id, subject_id, class_id, medium, chapter_id, topic_id,
    question_type, question_text, options, correct_answer, answer_explanation,
    skill_level, difficulty_level, created_by
) VALUES
(@board_id, @subject_id, @class_id, @medium, @chapter_id, @topic_id,
 @question_type, 'Which word best completes the sentence: The cat is very _____.',
 '{"option1":"happy","option2":"quick","option3":"blue","option4":"run"}',
 '{"correctOption":1}',
 '"Happy" is an adjective that describes the cat.',
 'SKILL', 'EASY', @created_by);

-- UNDERSTANDING × MEDIUM
INSERT INTO questions (
    board_id, subject_id, class_id, medium, chapter_id, topic_id,
    question_type, question_text, options, correct_answer, answer_explanation,
    skill_level, difficulty_level, created_by
) VALUES
(@board_id, @subject_id, @class_id, @medium, @chapter_id, @topic_id,
 @question_type, 'What does the word "enormous" mean?',
 '{"option1":"very small","option2":"very big","option3":"very fast","option4":"very old"}',
 '{"correctOption":2}',
 '"Enormous" means very big.',
 'UNDERSTANDING', 'MEDIUM', @created_by);

-- KNOWLEDGE × HARD
INSERT INTO questions (
    board_id, subject_id, class_id, medium, chapter_id, topic_id,
    question_type, question_text, options, correct_answer, answer_explanation,
    skill_level, difficulty_level, created_by
) VALUES
(@board_id, @subject_id, @class_id, @medium, @chapter_id, @topic_id,
 @question_type, 'Which of the following is a synonym for "rapid"?',
 '{"option1":"slow","option2":"quick","option3":"tall","option4":"loud"}',
 '{"correctOption":2}',
 '"Quick" is a synonym for "rapid".',
 'KNOWLEDGE', 'HARD', @created_by);

-- BUFFER QUESTIONS FOR CHAPTER 969 (Vocabulary)
-- These are extra questions for each SUKA and difficulty combination for redundancy

-- SKILL × EASY (Buffer)
INSERT INTO questions (
    board_id, subject_id, class_id, medium, chapter_id, topic_id,
    question_type, question_text, options, correct_answer, answer_explanation,
    skill_level, difficulty_level, created_by
) VALUES
(@board_id, @subject_id, @class_id, @medium, @chapter_id, @topic_id,
 @question_type, 'Choose the correct word: The dog is very _____.',
 '{"option1":"angry","option2":"playful","option3":"green","option4":"jump"}',
 '{"correctOption":2}',
 '"Playful" is an adjective that describes the dog.',
 'SKILL', 'EASY', @created_by);

-- UNDERSTANDING × MEDIUM (Buffer)
INSERT INTO questions (
    board_id, subject_id, class_id, medium, chapter_id, topic_id,
    question_type, question_text, options, correct_answer, answer_explanation,
    skill_level, difficulty_level, created_by
) VALUES
(@board_id, @subject_id, @class_id, @medium, @chapter_id, @topic_id,
 @question_type, 'What does the word "ancient" mean?',
 '{"option1":"very new","option2":"very old","option3":"very fast","option4":"very big"}',
 '{"correctOption":2}',
 '"Ancient" means very old.',
 'UNDERSTANDING', 'MEDIUM', @created_by);

-- KNOWLEDGE × HARD (Buffer)
INSERT INTO questions (
    board_id, subject_id, class_id, medium, chapter_id, topic_id,
    question_type, question_text, options, correct_answer, answer_explanation,
    skill_level, difficulty_level, created_by
) VALUES
(@board_id, @subject_id, @class_id, @medium, @chapter_id, @topic_id,
 @question_type, 'Which of the following is an antonym for "brave"?',
 '{"option1":"cowardly","option2":"strong","option3":"quick","option4":"happy"}',
 '{"correctOption":1}',
 '"Cowardly" is an antonym for "brave".',
 'KNOWLEDGE', 'HARD', @created_by);
