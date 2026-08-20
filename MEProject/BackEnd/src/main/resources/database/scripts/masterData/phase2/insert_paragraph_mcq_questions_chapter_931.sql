--liquibase formatted sql
--changeset dishika:add-paragraph-mcq-questions-chapter-931-iq-english-20260411

-- =====================================================================
-- PARAGRAPH-BASED MCQ QUESTIONS FOR CHAPTER 931 (IQ – ENGLISH)
-- Subject: IQ – English
-- Board: MSCE - PRE UPPER PRIMARY SCHOLARSHIP EXAMINATION
-- Medium: English
-- Question Type: paragraph-based-mcq
-- SUKA Distribution: SKILL, UNDERSTANDING, KNOWLEDGE
-- Difficulty Distribution: HARD, MEDIUM
-- Structure: 2 paragraphs × 5 questions each = 10 questions
--   Paragraph 1 (SKILL/HARD × SKILL/MEDIUM × UNDERSTANDING/HARD × UNDERSTANDING/MEDIUM × KNOWLEDGE/HARD)
--   Paragraph 2 (KNOWLEDGE/MEDIUM × SKILL/HARD × SKILL/MEDIUM × UNDERSTANDING/HARD × UNDERSTANDING/MEDIUM)
-- =====================================================================

SET @board_id   = (SELECT id FROM board WHERE board_name = 'MSCE' LIMIT 1);
SET @subject_id = (SELECT subject_id FROM subject WHERE subject_name = 'IQ – English' LIMIT 1);
SET @class_id   = (SELECT id FROM class WHERE class_name = '4' LIMIT 1);
SET @medium     = 'English';
SET @chapter_id = 931;
SET @topic_id   = (SELECT topic_id FROM topics WHERE chapter_id = @chapter_id LIMIT 1);
SET @created_by = 101;
SET @qtype      = 'paragraph-based-mcq';

-- =====================================================================
-- PARAGRAPH 1: Alphabet Series / Direction Puzzle
-- paragraphId: a1b2c3d4-e5f6-7890-abcd-ef1234567890
-- =====================================================================

-- Q1: SKILL × HARD
INSERT INTO questions (board_id, subject_id, class_id, medium, chapter_id, topic_id,
    question_type, question_text, options, correct_answer, answer_explanation,
    skill_level, difficulty_level, created_by, paragraph_id, paragraph_text)
VALUES
(@board_id, @subject_id, @class_id, @medium, @chapter_id, @topic_id,
 @qtype,
 'Ravi starts from his house and walks 5 km towards the North. He then turns right and walks 3 km. After that, he turns right again and walks 5 km. He finally turns left and walks 2 km to reach the market. In which direction is the market from Ravi''s house?',
 '{"option1":"East","option2":"West","option3":"North","option4":"South"}',
 '{"correctOption":1}',
 'Ravi walks North 5 km, then East 3 km, then South 5 km (back to original latitude), then East 2 km more. Total: 5 km East of start. Market is East of his house.',
 'SKILL', 'HARD', @created_by,
 'a1b2c3d4-e5f6-7890-abcd-ef1234567890',
 'Ravi starts from his house and walks 5 km towards the North. He then turns right and walks 3 km towards the East. After that, he turns right again and walks 5 km towards the South, returning to the same latitude as his starting point. Finally, he turns left and walks 2 km towards the East to reach the market. The neighbourhood has several landmarks along the way, including a school to the North and a park to the East of his house.');

-- Q2: SKILL × MEDIUM
INSERT INTO questions (board_id, subject_id, class_id, medium, chapter_id, topic_id,
    question_type, question_text, options, correct_answer, answer_explanation,
    skill_level, difficulty_level, created_by, paragraph_id, paragraph_text)
VALUES
(@board_id, @subject_id, @class_id, @medium, @chapter_id, @topic_id,
 @qtype,
 'How far is the market from Ravi''s house in a straight line (as the crow flies)?',
 '{"option1":"5 km","option2":"8 km","option3":"3 km","option4":"2 km"}',
 '{"correctOption":1}',
 'Ravi ends up 5 km East (3+2=5 km East) and 0 km North/South from his house. Straight-line distance = 5 km.',
 'SKILL', 'MEDIUM', @created_by,
 'a1b2c3d4-e5f6-7890-abcd-ef1234567890',
 NULL);

-- Q3: UNDERSTANDING × HARD
INSERT INTO questions (board_id, subject_id, class_id, medium, chapter_id, topic_id,
    question_type, question_text, options, correct_answer, answer_explanation,
    skill_level, difficulty_level, created_by, paragraph_id, paragraph_text)
VALUES
(@board_id, @subject_id, @class_id, @medium, @chapter_id, @topic_id,
 @qtype,
 'If Ravi had turned LEFT instead of RIGHT after his first 5 km walk North, in which direction would he have been walking?',
 '{"option1":"East","option2":"West","option3":"North","option4":"South"}',
 '{"correctOption":2}',
 'Facing North and turning LEFT means turning to face West.',
 'UNDERSTANDING', 'HARD', @created_by,
 'a1b2c3d4-e5f6-7890-abcd-ef1234567890',
 NULL);

-- Q4: UNDERSTANDING × MEDIUM
INSERT INTO questions (board_id, subject_id, class_id, medium, chapter_id, topic_id,
    question_type, question_text, options, correct_answer, answer_explanation,
    skill_level, difficulty_level, created_by, paragraph_id, paragraph_text)
VALUES
(@board_id, @subject_id, @class_id, @medium, @chapter_id, @topic_id,
 @qtype,
 'What is the total distance Ravi walked to reach the market?',
 '{"option1":"15 km","option2":"13 km","option3":"10 km","option4":"12 km"}',
 '{"correctOption":1}',
 'Total distance = 5 + 3 + 5 + 2 = 15 km.',
 'UNDERSTANDING', 'MEDIUM', @created_by,
 'a1b2c3d4-e5f6-7890-abcd-ef1234567890',
 NULL);

-- Q5: KNOWLEDGE × HARD
INSERT INTO questions (board_id, subject_id, class_id, medium, chapter_id, topic_id,
    question_type, question_text, options, correct_answer, answer_explanation,
    skill_level, difficulty_level, created_by, paragraph_id, paragraph_text)
VALUES
(@board_id, @subject_id, @class_id, @medium, @chapter_id, @topic_id,
 @qtype,
 'If Ravi faces the market and turns 180 degrees, in which direction is he now facing?',
 '{"option1":"West","option2":"East","option3":"North","option4":"South"}',
 '{"correctOption":1}',
 'The market is East of Ravi''s house. If he faces East (toward market) and turns 180 degrees, he now faces West.',
 'KNOWLEDGE', 'HARD', @created_by,
 'a1b2c3d4-e5f6-7890-abcd-ef1234567890',
 NULL);

-- =====================================================================
-- PARAGRAPH 2: Number Series / Logical Pattern
-- paragraphId: b2c3d4e5-f6a7-8901-bcde-fa2345678901
-- =====================================================================

-- Q6: KNOWLEDGE × MEDIUM
INSERT INTO questions (board_id, subject_id, class_id, medium, chapter_id, topic_id,
    question_type, question_text, options, correct_answer, answer_explanation,
    skill_level, difficulty_level, created_by, paragraph_id, paragraph_text)
VALUES
(@board_id, @subject_id, @class_id, @medium, @chapter_id, @topic_id,
 @qtype,
 'What is the next number in the sequence described: 3, 6, 12, 24, __?',
 '{"option1":"48","option2":"36","option3":"30","option4":"42"}',
 '{"correctOption":1}',
 'Each number is multiplied by 2: 3×2=6, 6×2=12, 12×2=24, 24×2=48.',
 'KNOWLEDGE', 'MEDIUM', @created_by,
 'b2c3d4e5-f6a7-8901-bcde-fa2345678901',
 'In a mathematics club, students were given a number sequence challenge. The sequence began with 3 and each following number was obtained by doubling the previous one. The sequence was: 3, 6, 12, 24, and so on. The students had to find the missing numbers and the rules governing the pattern. They also had to identify which numbers in the sequence are divisible by 4 and which are divisible by 3. The club teacher told them that understanding such patterns is key to IQ and logical reasoning.');

-- Q7: SKILL × HARD (Buffer paragraph 2)
INSERT INTO questions (board_id, subject_id, class_id, medium, chapter_id, topic_id,
    question_type, question_text, options, correct_answer, answer_explanation,
    skill_level, difficulty_level, created_by, paragraph_id, paragraph_text)
VALUES
(@board_id, @subject_id, @class_id, @medium, @chapter_id, @topic_id,
 @qtype,
 'Which number in the sequence 3, 6, 12, 24, 48 is the first to be divisible by 4?',
 '{"option1":"12","option2":"6","option3":"24","option4":"48"}',
 '{"correctOption":1}',
 '12 ÷ 4 = 3. So 12 is the first number in the sequence divisible by 4.',
 'SKILL', 'HARD', @created_by,
 'b2c3d4e5-f6a7-8901-bcde-fa2345678901',
 NULL);

-- Q8: SKILL × MEDIUM (Buffer paragraph 2)
INSERT INTO questions (board_id, subject_id, class_id, medium, chapter_id, topic_id,
    question_type, question_text, options, correct_answer, answer_explanation,
    skill_level, difficulty_level, created_by, paragraph_id, paragraph_text)
VALUES
(@board_id, @subject_id, @class_id, @medium, @chapter_id, @topic_id,
 @qtype,
 'How many numbers in the sequence 3, 6, 12, 24, 48 are divisible by 3?',
 '{"option1":"5","option2":"3","option3":"4","option4":"2"}',
 '{"correctOption":1}',
 'All numbers are divisible by 3: 3÷3=1, 6÷3=2, 12÷3=4, 24÷3=8, 48÷3=16. So all 5 numbers are divisible by 3.',
 'SKILL', 'MEDIUM', @created_by,
 'b2c3d4e5-f6a7-8901-bcde-fa2345678901',
 NULL);

-- Q9: UNDERSTANDING × HARD (Buffer paragraph 2)
INSERT INTO questions (board_id, subject_id, class_id, medium, chapter_id, topic_id,
    question_type, question_text, options, correct_answer, answer_explanation,
    skill_level, difficulty_level, created_by, paragraph_id, paragraph_text)
VALUES
(@board_id, @subject_id, @class_id, @medium, @chapter_id, @topic_id,
 @qtype,
 'If the pattern in the sequence continues, what would the 7th term be?',
 '{"option1":"192","option2":"96","option3":"128","option4":"144"}',
 '{"correctOption":1}',
 'The sequence is 3, 6, 12, 24, 48, 96, 192. Each term doubles. The 7th term = 3 × 2^6 = 3 × 64 = 192.',
 'UNDERSTANDING', 'HARD', @created_by,
 'b2c3d4e5-f6a7-8901-bcde-fa2345678901',
 NULL);

-- Q10: UNDERSTANDING × MEDIUM (Buffer paragraph 2)
INSERT INTO questions (board_id, subject_id, class_id, medium, chapter_id, topic_id,
    question_type, question_text, options, correct_answer, answer_explanation,
    skill_level, difficulty_level, created_by, paragraph_id, paragraph_text)
VALUES
(@board_id, @subject_id, @class_id, @medium, @chapter_id, @topic_id,
 @qtype,
 'What rule connects each number to the next in the sequence 3, 6, 12, 24, 48?',
 '{"option1":"Multiply by 2","option2":"Add 3","option3":"Add 6","option4":"Multiply by 3"}',
 '{"correctOption":1}',
 'Each term is obtained by multiplying the previous term by 2 (doubling).',
 'UNDERSTANDING', 'MEDIUM', @created_by,
 'b2c3d4e5-f6a7-8901-bcde-fa2345678901',
 NULL);

-- =====================================================================
-- PARAGRAPH 3: Coding-Decoding / Alphabet Pattern
-- paragraphId: c3d4e5f6-a7b8-9012-cdef-ab3456789012
-- =====================================================================

-- Q11: KNOWLEDGE × HARD (additional buffer)
INSERT INTO questions (board_id, subject_id, class_id, medium, chapter_id, topic_id,
    question_type, question_text, options, correct_answer, answer_explanation,
    skill_level, difficulty_level, created_by, paragraph_id, paragraph_text)
VALUES
(@board_id, @subject_id, @class_id, @medium, @chapter_id, @topic_id,
 @qtype,
 'Using the code system described, if MANGO is coded as NBOQP, how is APPLE coded?',
 '{"option1":"BQQMF","option2":"BPQMF","option3":"BQQME","option4":"CQQMF"}',
 '{"correctOption":1}',
 'Each letter shifts by +1: A→B, P→Q, P→Q, L→M, E→F. So APPLE = BQQMF.',
 'KNOWLEDGE', 'HARD', @created_by,
 'c3d4e5f6-a7b8-9012-cdef-ab3456789012',
 'In a secret messaging game, a group of friends used a special code to send messages. In their code, every letter of the alphabet was replaced by the letter that comes immediately after it in the English alphabet. So A became B, B became C, and so on. The last letter Z would become A. For example, using this rule, the word MANGO was written as NBOQP. The friends used this code to hide their messages from others while playing in the school. They practised decoding messages every day to improve their IQ and logical thinking skills.');

-- Q12: KNOWLEDGE × MEDIUM (additional buffer)
INSERT INTO questions (board_id, subject_id, class_id, medium, chapter_id, topic_id,
    question_type, question_text, options, correct_answer, answer_explanation,
    skill_level, difficulty_level, created_by, paragraph_id, paragraph_text)
VALUES
(@board_id, @subject_id, @class_id, @medium, @chapter_id, @topic_id,
 @qtype,
 'Using the same code, what does the coded message DBU mean in plain English?',
 '{"option1":"CAT","option2":"BAT","option3":"DOG","option4":"CUP"}',
 '{"correctOption":1}',
 'Reverse the code by subtracting 1 from each letter: D→C, B→A, U→T. So DBU = CAT.',
 'KNOWLEDGE', 'MEDIUM', @created_by,
 'c3d4e5f6-a7b8-9012-cdef-ab3456789012',
 NULL);

-- Q13: SKILL × HARD (additional buffer)
INSERT INTO questions (board_id, subject_id, class_id, medium, chapter_id, topic_id,
    question_type, question_text, options, correct_answer, answer_explanation,
    skill_level, difficulty_level, created_by, paragraph_id, paragraph_text)
VALUES
(@board_id, @subject_id, @class_id, @medium, @chapter_id, @topic_id,
 @qtype,
 'Using the friends'' code, how would the word SCHOOL be written?',
 '{"option1":"TDIPPM","option2":"TDIPPL","option3":"SDIPPM","option4":"TDIPPN"}',
 '{"correctOption":1}',
 'Each letter +1: S→T, C→D, H→I, O→P, O→P, L→M. So SCHOOL = TDIPPM.',
 'SKILL', 'HARD', @created_by,
 'c3d4e5f6-a7b8-9012-cdef-ab3456789012',
 NULL);

-- Q14: SKILL × MEDIUM (additional buffer)
INSERT INTO questions (board_id, subject_id, class_id, medium, chapter_id, topic_id,
    question_type, question_text, options, correct_answer, answer_explanation,
    skill_level, difficulty_level, created_by, paragraph_id, paragraph_text)
VALUES
(@board_id, @subject_id, @class_id, @medium, @chapter_id, @topic_id,
 @qtype,
 'If the letter Z is coded in the friends'' system, what letter does it become?',
 '{"option1":"A","option2":"Y","option3":"B","option4":"Z"}',
 '{"correctOption":1}',
 'The paragraph states that Z becomes A in this coding system (it wraps around).',
 'SKILL', 'MEDIUM', @created_by,
 'c3d4e5f6-a7b8-9012-cdef-ab3456789012',
 NULL);

-- Q15: UNDERSTANDING × HARD (additional buffer)
INSERT INTO questions (board_id, subject_id, class_id, medium, chapter_id, topic_id,
    question_type, question_text, options, correct_answer, answer_explanation,
    skill_level, difficulty_level, created_by, paragraph_id, paragraph_text)
VALUES
(@board_id, @subject_id, @class_id, @medium, @chapter_id, @topic_id,
 @qtype,
 'What type of coding system do the friends use to send secret messages?',
 '{"option1":"Each letter is replaced by the next letter in the alphabet","option2":"Each letter is replaced by the previous letter in the alphabet","option3":"Each letter is replaced by a number","option4":"Each letter is replaced by a symbol"}',
 '{"correctOption":1}',
 'The paragraph clearly states: every letter is replaced by the letter that comes immediately after it in the English alphabet.',
 'UNDERSTANDING', 'HARD', @created_by,
 'c3d4e5f6-a7b8-9012-cdef-ab3456789012',
 NULL);

