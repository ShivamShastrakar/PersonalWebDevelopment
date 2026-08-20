--liquibase formatted sql
--changeset narendra:add-questions-chapter-980-patterns-20260221

-- =====================================================
-- COMPLETE MCQ QUESTIONS FOR CHAPTER 980
-- =====================================================
-- Adds 14 properly formatted MCQ questions with options
-- Current: 6 questions | Target: 20 questions | Adding: 14
-- =====================================================

-- Get IDs
SET @board_id = (SELECT id FROM board WHERE board_name = 'MSCE' LIMIT 1);
SET @subject_id = (SELECT subject_id FROM subject WHERE subject_name = 'Math – English' LIMIT 1);
SET @class_id = (SELECT id FROM class WHERE class_name = '4' LIMIT 1);
SET @medium = 'English';
SET @chapter_id = (SELECT id FROM chapters c WHERE c.chapter_name = 'Patterns' AND c.subject_id = (SELECT subject_id FROM subject WHERE subject_name = 'Math – English' LIMIT 1) LIMIT 1);
SET @topic_id = (SELECT topic_id FROM topics WHERE chapter_id = (SELECT id FROM chapters c WHERE c.chapter_name = 'Patterns' AND c.subject_id = (SELECT subject_id FROM subject WHERE subject_name = 'Math – English' LIMIT 1) LIMIT 1) LIMIT 1);
SET @question_type = 'MCQ';
SET @created_by = 101;

-- =====================================================
-- QUESTIONS 1-2: SKILL × MEDIUM
-- =====================================================

INSERT INTO questions (
    board_id, subject_id, class_id, medium, chapter_id, topic_id,
    question_type, question_text, options, correct_answer, answer_explanation,
    skill_level, difficulty_level, created_by
) VALUES
-- Q1: SKILL × MEDIUM
(@board_id, @subject_id, @class_id, @medium, @chapter_id, @topic_id,
 @question_type, 'Identify the pattern in the sequence: 2, 4, 6, 8, ?',
 '{"option1":"9","option2":"10","option3":"11","option4":"12"}',
 '{"correctOption":2}',
 'The pattern increases by 2 each time, so 8 + 2 = 10.',
 'SKILL', 'MEDIUM', @created_by),

-- Q2: SKILL × MEDIUM
(@board_id, @subject_id, @class_id, @medium, @chapter_id, @topic_id,
 @question_type, 'What comes next in this pattern: 5, 10, 15, 20, ?',
 '{"option1":"22","option2":"24","option3":"25","option4":"30"}',
 '{"correctOption":3}',
 'The pattern is counting by 5s, so 20 + 5 = 25.',
 'SKILL', 'MEDIUM', @created_by);

-- =====================================================
-- QUESTIONS 3-4: SKILL × EASY
-- =====================================================

INSERT INTO questions (
    board_id, subject_id, class_id, medium, chapter_id, topic_id,
    question_type, question_text, options, correct_answer, answer_explanation,
    skill_level, difficulty_level, created_by
) VALUES
-- Q3: SKILL × EASY
(@board_id, @subject_id, @class_id, @medium, @chapter_id, @topic_id,
 @question_type, 'Which shape continues the pattern: circle, square, circle, square, ?',
 '{"option1":"triangle","option2":"circle","option3":"rectangle","option4":"pentagon"}',
 '{"correctOption":2}',
 'The pattern alternates between circle and square.',
 'SKILL', 'EASY', @created_by),

-- Q4: SKILL × EASY
(@board_id, @subject_id, @class_id, @medium, @chapter_id, @topic_id,
 @question_type, 'What is the next number: 1, 3, 5, 7, ?',
 '{"option1":"8","option2":"9","option3":"10","option4":"11"}',
 '{"correctOption":2}',
 'The pattern shows odd numbers, next odd number is 9.',
 'SKILL', 'EASY', @created_by);

-- =====================================================
-- QUESTIONS 5-6: SKILL × HARD
-- =====================================================

INSERT INTO questions (
    board_id, subject_id, class_id, medium, chapter_id, topic_id,
    question_type, question_text, options, correct_answer, answer_explanation,
    skill_level, difficulty_level, created_by
) VALUES
-- Q5: SKILL × HARD
(@board_id, @subject_id, @class_id, @medium, @chapter_id, @topic_id,
 @question_type, 'Complete the complex pattern: 2, 6, 12, 20, ?',
 '{"option1":"28","option2":"30","option3":"32","option4":"34"}',
 '{"correctOption":2}',
 'The differences are 4, 6, 8, so next difference is 10, giving 20 + 10 = 30.',
 'SKILL', 'HARD', @created_by),

-- Q6: SKILL × HARD
(@board_id, @subject_id, @class_id, @medium, @chapter_id, @topic_id,
 @question_type, 'Find the missing number in: 3, 7, 15, 31, ?',
 '{"option1":"47","option2":"55","option3":"63","option4":"71"}',
 '{"correctOption":3}',
 'Each number is double the previous plus 1: (31 × 2) + 1 = 63.',
 'SKILL', 'HARD', @created_by);

-- =====================================================
-- QUESTIONS 7-8: UNDERSTANDING × MEDIUM
-- =====================================================

INSERT INTO questions (
    board_id, subject_id, class_id, medium, chapter_id, topic_id,
    question_type, question_text, options, correct_answer, answer_explanation,
    skill_level, difficulty_level, created_by
) VALUES
-- Q7: UNDERSTANDING × MEDIUM
(@board_id, @subject_id, @class_id, @medium, @chapter_id, @topic_id,
 @question_type, 'Why do patterns help us in mathematics?',
 '{"option1":"They make problems harder","option2":"They help us predict what comes next","option3":"They are just for fun","option4":"They confuse us"}',
 '{"correctOption":2}',
 'Patterns help us understand sequences and predict future elements.',
 'UNDERSTANDING', 'MEDIUM', @created_by),

-- Q8: UNDERSTANDING × MEDIUM
(@board_id, @subject_id, @class_id, @medium, @chapter_id, @topic_id,
 @question_type, 'What does it mean when we say a pattern repeats?',
 '{"option1":"It never changes","option2":"The same sequence appears again and again","option3":"It gets bigger","option4":"It becomes random"}',
 '{"correctOption":2}',
 'A repeating pattern shows the same sequence multiple times.',
 'UNDERSTANDING', 'MEDIUM', @created_by);

-- =====================================================
-- QUESTIONS 9-10: UNDERSTANDING × HARD
-- =====================================================

INSERT INTO questions (
    board_id, subject_id, class_id, medium, chapter_id, topic_id,
    question_type, question_text, options, correct_answer, answer_explanation,
    skill_level, difficulty_level, created_by
) VALUES
-- Q9: UNDERSTANDING × HARD
(@board_id, @subject_id, @class_id, @medium, @chapter_id, @topic_id,
 @question_type, 'Analyze the relationship between arithmetic and geometric patterns.',
 '{"option1":"They are completely unrelated","option2":"Both follow specific rules but use different operations","option3":"They are exactly the same","option4":"Only arithmetic patterns are real patterns"}',
 '{"correctOption":2}',
 'Both types follow rules, but arithmetic uses addition/subtraction while geometric uses multiplication/division.',
 'UNDERSTANDING', 'HARD', @created_by),

-- Q10: UNDERSTANDING × HARD
(@board_id, @subject_id, @class_id, @medium, @chapter_id, @topic_id,
 @question_type, 'Why is understanding patterns important for solving complex problems?',
 '{"option1":"It helps break problems into predictable steps and identify solutions","option2":"It makes problems more difficult","option3":"It is not important","option4":"It only works with simple problems"}',
 '{"correctOption":1}',
 'Patterns help us recognize structure in complex problems and find systematic solutions.',
 'UNDERSTANDING', 'HARD', @created_by);

-- =====================================================
-- QUESTIONS 11-12: KNOWLEDGE × EASY
-- =====================================================

INSERT INTO questions (
    board_id, subject_id, class_id, medium, chapter_id, topic_id,
    question_type, question_text, options, correct_answer, answer_explanation,
    skill_level, difficulty_level, created_by
) VALUES
-- Q11: KNOWLEDGE × EASY
(@board_id, @subject_id, @class_id, @medium, @chapter_id, @topic_id,
 @question_type, 'What is a pattern in mathematics?',
 '{"option1":"A repeated sequence that follows a rule","option2":"Random numbers","option3":"Any set of numbers","option4":"Only shapes"}',
 '{"correctOption":1}',
 'A pattern is a sequence that repeats or follows a specific rule.',
 'KNOWLEDGE', 'EASY', @created_by),

-- Q12: KNOWLEDGE × EASY
(@board_id, @subject_id, @class_id, @medium, @chapter_id, @topic_id,
 @question_type, 'What do we call the numbers in a pattern?',
 '{"option1":"Pattern pieces","option2":"Terms or elements","option3":"Pattern dots","option4":"Numbers only"}',
 '{"correctOption":2}',
 'The numbers or items in a pattern are called terms or elements.',
 'KNOWLEDGE', 'EASY', @created_by);

-- =====================================================
-- QUESTIONS 13-14: KNOWLEDGE × HARD
-- =====================================================

INSERT INTO questions (
    board_id, subject_id, class_id, medium, chapter_id, topic_id,
    question_type, question_text, options, correct_answer, answer_explanation,
    skill_level, difficulty_level, created_by
) VALUES
-- Q13: KNOWLEDGE × HARD
(@board_id, @subject_id, @class_id, @medium, @chapter_id, @topic_id,
 @question_type, 'What is the relationship between Fibonacci sequence and patterns in nature?',
 '{"option1":"No relationship","option2":"Fibonacci is a mathematical pattern found in natural spirals and growth","option3":"They are opposites","option4":"Only in mathematics"}',
 '{"correctOption":2}',
 'Fibonacci sequence is a pattern that appears frequently in natural phenomena.',
 'KNOWLEDGE', 'HARD', @created_by),

-- Q14: KNOWLEDGE × HARD
(@board_id, @subject_id, @class_id, @medium, @chapter_id, @topic_id,
 @question_type, 'Which type of pattern uses multiplication or division to find the next term?',
 '{"option1":"Arithmetic pattern","option2":"Random pattern","option3":"Geometric pattern","option4":"Linear pattern"}',
 '{"correctOption":3}',
 'Geometric patterns use multiplication or division, while arithmetic patterns use addition or subtraction.',
 'KNOWLEDGE', 'HARD', @created_by);

-- =====================================================
-- QUESTIONS 15-16: APPLICATION × MEDIUM
-- =====================================================

INSERT INTO questions (
    board_id, subject_id, class_id, medium, chapter_id, topic_id,
    question_type, question_text, options, correct_answer, answer_explanation,
    skill_level, difficulty_level, created_by
) VALUES
-- Q15: APPLICATION × MEDIUM
(@board_id, @subject_id, @class_id, @medium, @chapter_id, @topic_id,
 @question_type, 'A store arranges products in rows: Row 1 has 5, Row 2 has 10, Row 3 has 15. How many in Row 5?',
 '{"option1":"20","option2":"25","option3":"30","option4":"35"}',
 '{"correctOption":2}',
 'The pattern increases by 5: Row 5 = 5 × 5 = 25 products.',
 'APPLICATION', 'MEDIUM', @created_by),

-- Q16: APPLICATION × MEDIUM
(@board_id, @subject_id, @class_id, @medium, @chapter_id, @topic_id,
 @question_type, 'If a plant grows 3 cm in Week 1, 6 cm in Week 2, 9 cm in Week 3, how much in Week 4?',
 '{"option1":"10 cm","option2":"11 cm","option3":"12 cm","option4":"15 cm"}',
 '{"correctOption":3}',
 'The growth pattern increases by 3 cm each week: Week 4 = 12 cm.',
 'APPLICATION', 'MEDIUM', @created_by);

-- =====================================================
-- QUESTIONS 17-18: APPLICATION × EASY
-- =====================================================

INSERT INTO questions (
    board_id, subject_id, class_id, medium, chapter_id, topic_id,
    question_type, question_text, options, correct_answer, answer_explanation,
    skill_level, difficulty_level, created_by
) VALUES
-- Q17: APPLICATION × EASY
(@board_id, @subject_id, @class_id, @medium, @chapter_id, @topic_id,
 @question_type, 'You see buttons arranged: red, blue, red, blue. What color comes next?',
 '{"option1":"green","option2":"red","option3":"yellow","option4":"purple"}',
 '{"correctOption":2}',
 'The pattern alternates red and blue, so red comes next.',
 'APPLICATION', 'EASY', @created_by),

-- Q18: APPLICATION × EASY
(@board_id, @subject_id, @class_id, @medium, @chapter_id, @topic_id,
 @question_type, 'If Monday is Day 1, Tuesday is Day 2, what is Day 4?',
 '{"option1":"Wednesday","option2":"Thursday","option3":"Friday","option4":"Saturday"}',
 '{"correctOption":2}',
 'Following the pattern: Day 1=Monday, Day 2=Tuesday, Day 3=Wednesday, Day 4=Thursday.',
 'APPLICATION', 'EASY', @created_by);

-- =====================================================
-- QUESTIONS 19-20: APPLICATION × HARD
-- =====================================================

INSERT INTO questions (
    board_id, subject_id, class_id, medium, chapter_id, topic_id,
    question_type, question_text, options, correct_answer, answer_explanation,
    skill_level, difficulty_level, created_by
) VALUES
-- Q19: APPLICATION × HARD
(@board_id, @subject_id, @class_id, @medium, @chapter_id, @topic_id,
 @question_type, 'A sequence follows rule: n² + n. For n=1 result is 2, n=2 is 6, n=3 is 12. What is n=5?',
 '{"option1":"25","option2":"30","option3":"35","option4":"40"}',
 '{"correctOption":2}',
 'For n=5: (5²) + 5 = 25 + 5 = 30.',
 'APPLICATION', 'HARD', @created_by),

-- Q20: APPLICATION × HARD
(@board_id, @subject_id, @class_id, @medium, @chapter_id, @topic_id,
 @question_type, 'If pattern A adds 2 and pattern B multiplies by 2, what strategy combines both starting with 3?',
 '{"option1":"3,5,10,12,24","option2":"3,6,8,16,18","option3":"3,5,7,9,11","option4":"3,6,12,24,48"}',
 '{"correctOption":1}',
 'Start 3, add 2=5, multiply 2=10, add 2=12, multiply 2=24. Alternating operations.',
 'APPLICATION', 'HARD', @created_by);
