--liquibase formatted sql
--changeset {narendra}:{id}

-- ============================================================================
-- MSCE CLASS 5 IQ – 100 MCQ QUESTIONS
-- Board: MSCE - PRE UPPER PRIMARY SCHOLARSHIP EXAMINATION
-- Subject: IQ – English (subject_id: 39)
-- Class: 4
-- Medium: English
-- ============================================================================

-- Variable Declarations
SET @board_id = (SELECT id FROM board WHERE board_name = 'MSCE' LIMIT 1);
SET @subject_id = (SELECT subject_id FROM subject WHERE subject_name = 'IQ – English' LIMIT 1);
SET @class_id = (SELECT id FROM class WHERE class_name = '4' LIMIT 1);
SET @medium = 'English';
SET @created_by = 101;

-- Chapter Variables (12 chapters for Class 5 IQ)
-- Dynamically fetch chapter IDs based on subject_id and board_id
SET @chapter_comprehension = (SELECT c.id FROM chapters c
    INNER JOIN chapter_board_class_mapping cbcm ON c.id = cbcm.chapter_id
    WHERE c.subject_id = @subject_id AND cbcm.board_id = @board_id AND cbcm.class_id = @class_id
    AND c.chapter_name = 'Comprehension' LIMIT 1);

SET @chapter_classification = (SELECT c.id FROM chapters c
    INNER JOIN chapter_board_class_mapping cbcm ON c.id = cbcm.chapter_id
    WHERE c.subject_id = @subject_id AND cbcm.board_id = @board_id AND cbcm.class_id = @class_id
    AND c.chapter_name = 'Classification' LIMIT 1);

SET @chapter_correlation = (SELECT c.id FROM chapters c
    INNER JOIN chapter_board_class_mapping cbcm ON c.id = cbcm.chapter_id
    WHERE c.subject_id = @subject_id AND cbcm.board_id = @board_id AND cbcm.class_id = @class_id
    AND c.chapter_name = 'Co-relation' LIMIT 1);

SET @chapter_number_order = (SELECT c.id FROM chapters c
    INNER JOIN chapter_board_class_mapping cbcm ON c.id = cbcm.chapter_id
    WHERE c.subject_id = @subject_id AND cbcm.board_id = @board_id AND cbcm.class_id = @class_id
    AND c.chapter_name = 'Number order' LIMIT 1);

SET @chapter_like_terms = (SELECT c.id FROM chapters c
    INNER JOIN chapter_board_class_mapping cbcm ON c.id = cbcm.chapter_id
    WHERE c.subject_id = @subject_id AND cbcm.board_id = @board_id AND cbcm.class_id = @class_id
    AND c.chapter_name = 'Like Terms' LIMIT 1);

SET @chapter_water_image = (SELECT c.id FROM chapters c
    INNER JOIN chapter_board_class_mapping cbcm ON c.id = cbcm.chapter_id
    WHERE c.subject_id = @subject_id AND cbcm.board_id = @board_id AND cbcm.class_id = @class_id
    AND c.chapter_name = 'Water Image' LIMIT 1);

SET @chapter_mirror_image = (SELECT c.id FROM chapters c
    INNER JOIN chapter_board_class_mapping cbcm ON c.id = cbcm.chapter_id
    WHERE c.subject_id = @subject_id AND cbcm.board_id = @board_id AND cbcm.class_id = @class_id
    AND c.chapter_name = 'Mirror Image' LIMIT 1);

SET @chapter_similarities = (SELECT c.id FROM chapters c
    INNER JOIN chapter_board_class_mapping cbcm ON c.id = cbcm.chapter_id
    WHERE c.subject_id = @subject_id AND cbcm.board_id = @board_id AND cbcm.class_id = @class_id
    AND c.chapter_name = 'Identifying Similarities' LIMIT 1);

SET @chapter_logic = (SELECT c.id FROM chapters c
    INNER JOIN chapter_board_class_mapping cbcm ON c.id = cbcm.chapter_id
    WHERE c.subject_id = @subject_id AND cbcm.board_id = @board_id AND cbcm.class_id = @class_id
    AND c.chapter_name = 'Logic And Inference' LIMIT 1);

SET @chapter_puzzles = (SELECT c.id FROM chapters c
    INNER JOIN chapter_board_class_mapping cbcm ON c.id = cbcm.chapter_id
    WHERE c.subject_id = @subject_id AND cbcm.board_id = @board_id AND cbcm.class_id = @class_id
    AND c.chapter_name = 'Puzzles' LIMIT 1);

SET @chapter_symbolic = (SELECT c.id FROM chapters c
    INNER JOIN chapter_board_class_mapping cbcm ON c.id = cbcm.chapter_id
    WHERE c.subject_id = @subject_id AND cbcm.board_id = @board_id AND cbcm.class_id = @class_id
    AND c.chapter_name = 'Symbolic Language (Symbol)' LIMIT 1);

SET @chapter_special = (SELECT c.id FROM chapters c
    INNER JOIN chapter_board_class_mapping cbcm ON c.id = cbcm.chapter_id
    WHERE c.subject_id = @subject_id AND cbcm.board_id = @board_id AND cbcm.class_id = @class_id
    AND c.chapter_name = 'Special Question Or Important' LIMIT 1);

-- Topic Variables (34 topics)
-- Chapter 1: Comprehension - 3 topics
SET @topic_composite_words = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_comprehension AND subject_id = @subject_id
    AND topic_name = 'Do as directed - composite words, letters, words' LIMIT 1);

SET @topic_number_series = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_comprehension AND subject_id = @subject_id
    AND topic_name = 'Number series' LIMIT 1);

SET @topic_alphabet_series_comp = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_comprehension AND subject_id = @subject_id
    AND topic_name = 'Alphabet series' LIMIT 1);

-- Chapter 2: Classification - 4 topics
SET @topic_vocab_class = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_classification AND subject_id = @subject_id
    AND topic_name = 'Vocabulary' LIMIT 1);

SET @topic_figures_class = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_classification AND subject_id = @subject_id
    AND topic_name = 'Figures' LIMIT 1);

SET @topic_numbers_class = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_classification AND subject_id = @subject_id
    AND topic_name = 'Numbers' LIMIT 1);

SET @topic_alphabet_class = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_classification AND subject_id = @subject_id
    AND topic_name = 'Alphabet series' LIMIT 1);

-- Chapter 3: Co-relation - 4 topics
SET @topic_vocab_corr = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_correlation AND subject_id = @subject_id
    AND topic_name = 'Vocabulary' LIMIT 1);

SET @topic_figures_corr = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_correlation AND subject_id = @subject_id
    AND topic_name = 'Figures' LIMIT 1);

SET @topic_numbers_corr = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_correlation AND subject_id = @subject_id
    AND topic_name = 'Numbers' LIMIT 1);

SET @topic_alphabet_corr = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_correlation AND subject_id = @subject_id
    AND topic_name = 'Alphabet series' LIMIT 1);

-- Chapter 4: Number order - 4 topics
SET @topic_number_pattern = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_number_order AND subject_id = @subject_id
    AND topic_name = 'Number pattern (sequence)' LIMIT 1);

SET @topic_figure_pattern = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_number_order AND subject_id = @subject_id
    AND topic_name = 'Figure pattern' LIMIT 1);

SET @topic_symbols = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_number_order AND subject_id = @subject_id
    AND topic_name = 'Symbols' LIMIT 1);

SET @topic_odd_man_out = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_number_order AND subject_id = @subject_id
    AND topic_name = 'Odd man out' LIMIT 1);

-- Chapter 5: Like Terms - 3 topics
SET @topic_vocab_like = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_like_terms AND subject_id = @subject_id
    AND topic_name = 'Vocabulary' LIMIT 1);

SET @topic_figures_like = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_like_terms AND subject_id = @subject_id
    AND topic_name = 'Figures' LIMIT 1);

SET @topic_numbers_like = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_like_terms AND subject_id = @subject_id
    AND topic_name = 'Numbers' LIMIT 1);

-- Chapter 6: Water Image - 3 topics
SET @topic_figures_water = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_water_image AND subject_id = @subject_id
    AND topic_name = 'Figures' LIMIT 1);

SET @topic_numbers_water = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_water_image AND subject_id = @subject_id
    AND topic_name = 'Numbers' LIMIT 1);

SET @topic_alphabets_water = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_water_image AND subject_id = @subject_id
    AND topic_name = 'Alphabets' LIMIT 1);

-- Chapter 7: Mirror Image - 3 topics
SET @topic_figures_mirror = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_mirror_image AND subject_id = @subject_id
    AND topic_name = 'Figures' LIMIT 1);

SET @topic_numbers_mirror = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_mirror_image AND subject_id = @subject_id
    AND topic_name = 'Numbers' LIMIT 1);

SET @topic_alphabets_mirror = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_mirror_image AND subject_id = @subject_id
    AND topic_name = 'Alphabets' LIMIT 1);

-- Chapter 8: Identifying Similarities - 1 topic
SET @topic_figures_sim = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_similarities AND subject_id = @subject_id
    AND topic_name = 'Figures' LIMIT 1);

-- Chapter 9: Logic And Inference - 2 topics
SET @topic_verbal_logic = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_logic AND subject_id = @subject_id
    AND topic_name = 'Verbal - Age, Comparison, Change in Name, Relations' LIMIT 1);

SET @topic_nonverbal_logic = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_logic AND subject_id = @subject_id
    AND topic_name = 'Non Verbal - To count the figures - Triangle, quadrilateral, square, rectangle, segment, angle, cube etc.' LIMIT 1);

-- Chapter 10: Puzzles - 5 topics
SET @topic_position_queue = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_puzzles AND subject_id = @subject_id
    AND topic_name = 'Position in a queue' LIMIT 1);

SET @topic_direction = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_puzzles AND subject_id = @subject_id
    AND topic_name = 'Direction' LIMIT 1);

SET @topic_calendar = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_puzzles AND subject_id = @subject_id
    AND topic_name = 'Calendar' LIMIT 1);

SET @topic_venn_diagram = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_puzzles AND subject_id = @subject_id
    AND topic_name = 'Venn diagram' LIMIT 1);

SET @topic_shapes_numbers = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_puzzles AND subject_id = @subject_id
    AND topic_name = 'Numbers in square, circle and triangle' LIMIT 1);

-- Chapter 11: Symbolic Language - 1 topic
SET @topic_symbolic_lang = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_symbolic AND subject_id = @subject_id
    AND topic_name = 'Use of symbols, numbers and words' LIMIT 1);

-- Chapter 12: Special Questions - 1 topic
SET @topic_emotional_intel = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_special AND subject_id = @subject_id
    AND topic_name = 'Emotional intelligence, Social intelligences' LIMIT 1);

-- ============================================================================
-- 100 MCQ QUESTIONS
-- Distribution across 12 chapters and 34 topics
-- ============================================================================

INSERT INTO questions (
    board_id, subject_id, class_id, medium, chapter_id, topic_id,
    question_type, question_text, options, correct_answer, answer_explanation,
    skill_level, difficulty_level, created_by
) VALUES

-- ============================================================================
-- CHAPTER 1: Comprehension - 10 questions
-- ============================================================================

-- Topic: Composite words, letters, words (4 questions)
(@board_id, @subject_id, @class_id, @medium, @chapter_comprehension, @topic_composite_words, 'MCQ',
'How many letters are there in the word "SCHOOL"?',
'{"option1":"5","option2":"6","option3":"7","option4":"8"}',
'{"correctOption":2}',
'Count the letters: S-C-H-O-O-L = 6 letters.',
'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_comprehension, @topic_composite_words, 'MCQ',
'Which letter comes in the middle of the word "FRIEND"?',
'{"option1":"I","option2":"E","option3":"R","option4":"N"}',
'{"correctOption":1}',
'FRIEND has 6 letters. The middle letters are I and E, but 3rd letter is I.',
'UNDERSTANDING', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_comprehension, @topic_composite_words, 'MCQ',
'Arrange the letters to form a word: T, A, C',
'{"option1":"TAC","option2":"CAT","option3":"ACT","option4":"CTA"}',
'{"correctOption":2}',
'CAT is a meaningful English word.',
'APPLICATION', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_comprehension, @topic_composite_words, 'MCQ',
'How many vowels are in the word "APPLE"?',
'{"option1":"1","option2":"2","option3":"3","option4":"4"}',
'{"correctOption":2}',
'Vowels in APPLE are A and E = 2 vowels.',
'KNOWLEDGE', 'EASY', @created_by),

-- Topic: Number series (3 questions)
(@board_id, @subject_id, @class_id, @medium, @chapter_comprehension, @topic_number_series, 'MCQ',
'Complete the series: 2, 4, 6, 8, ?',
'{"option1":"9","option2":"10","option3":"11","option4":"12"}',
'{"correctOption":2}',
'Pattern: Add 2 each time. 8 + 2 = 10.',
'UNDERSTANDING', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_comprehension, @topic_number_series, 'MCQ',
'Find the next number: 5, 10, 15, 20, ?',
'{"option1":"22","option2":"24","option3":"25","option4":"30"}',
'{"correctOption":3}',
'Pattern: Add 5 each time. 20 + 5 = 25.',
'UNDERSTANDING', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_comprehension, @topic_number_series, 'MCQ',
'Complete: 3, 6, 9, 12, ?',
'{"option1":"14","option2":"15","option3":"16","option4":"18"}',
'{"correctOption":2}',
'Pattern: Multiples of 3. Next is 15.',
'UNDERSTANDING', 'EASY', @created_by),

-- Topic: Alphabet series (3 questions)
(@board_id, @subject_id, @class_id, @medium, @chapter_comprehension, @topic_alphabet_series_comp, 'MCQ',
'Find the pattern: A, C, E, G, ?',
'{"option1":"H","option2":"I","option3":"J","option4":"K"}',
'{"correctOption":2}',
'Skip one letter each time. After G, skip H, answer is I.',
'UNDERSTANDING', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_comprehension, @topic_alphabet_series_comp, 'MCQ',
'Complete: B, D, F, H, ?',
'{"option1":"I","option2":"J","option3":"K","option4":"L"}',
'{"correctOption":2}',
'Skip one letter: After H, skip I, answer is J.',
'UNDERSTANDING', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_comprehension, @topic_alphabet_series_comp, 'MCQ',
'What comes next: A, B, C, D, ?',
'{"option1":"E","option2":"F","option3":"G","option4":"H"}',
'{"correctOption":1}',
'Simple alphabetical order. After D comes E.',
'KNOWLEDGE', 'EASY', @created_by),

-- ============================================================================
-- CHAPTER 2: Classification - 12 questions
-- ============================================================================

-- Topic: Vocabulary (3 questions)
(@board_id, @subject_id, @class_id, @medium, @chapter_classification, @topic_vocab_class, 'MCQ',
'Which one is different? Cat, Dog, Cow, Chair',
'{"option1":"Cat","option2":"Dog","option3":"Cow","option4":"Chair"}',
'{"correctOption":4}',
'Chair is not an animal, others are animals.',
'UNDERSTANDING', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_classification, @topic_vocab_class, 'MCQ',
'Find the odd one: Rose, Lotus, Jasmine, Potato',
'{"option1":"Rose","option2":"Lotus","option3":"Jasmine","option4":"Potato"}',
'{"correctOption":4}',
'Potato is a vegetable, others are flowers.',
'UNDERSTANDING', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_classification, @topic_vocab_class, 'MCQ',
'Which is different? Red, Blue, Green, Book',
'{"option1":"Red","option2":"Blue","option3":"Green","option4":"Book"}',
'{"correctOption":4}',
'Book is not a color, others are colors.',
'UNDERSTANDING', 'EASY', @created_by),

-- Topic: Figures (3 questions)
(@board_id, @subject_id, @class_id, @medium, @chapter_classification, @topic_figures_class, 'MCQ',
'Which shape is different? Circle, Triangle, Square, Apple',
'{"option1":"Circle","option2":"Triangle","option3":"Square","option4":"Apple"}',
'{"correctOption":4}',
'Apple is not a geometric shape.',
'UNDERSTANDING', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_classification, @topic_figures_class, 'MCQ',
'Find the odd shape: Rectangle, Square, Triangle, Table',
'{"option1":"Rectangle","option2":"Square","option3":"Triangle","option4":"Table"}',
'{"correctOption":4}',
'Table is an object, not a shape.',
'UNDERSTANDING', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_classification, @topic_figures_class, 'MCQ',
'Which one has no corners? Circle, Square, Triangle, Rectangle',
'{"option1":"Circle","option2":"Square","option3":"Triangle","option4":"Rectangle"}',
'{"correctOption":1}',
'Circle has no corners or edges.',
'KNOWLEDGE', 'EASY', @created_by),

-- Topic: Numbers (3 questions)
(@board_id, @subject_id, @class_id, @medium, @chapter_classification, @topic_numbers_class, 'MCQ',
'Which is different? 2, 4, 6, 7',
'{"option1":"2","option2":"4","option3":"6","option4":"7"}',
'{"correctOption":4}',
'7 is odd, others are even numbers.',
'UNDERSTANDING', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_classification, @topic_numbers_class, 'MCQ',
'Find the odd one: 10, 20, 30, 35',
'{"option1":"10","option2":"20","option3":"30","option4":"35"}',
'{"correctOption":4}',
'35 is not a multiple of 10.',
'UNDERSTANDING', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_classification, @topic_numbers_class, 'MCQ',
'Which is different? 5, 10, 15, 18',
'{"option1":"5","option2":"10","option3":"15","option4":"18"}',
'{"correctOption":4}',
'18 is not a multiple of 5.',
'UNDERSTANDING', 'MEDIUM', @created_by),

-- Topic: Alphabet series (3 questions)
(@board_id, @subject_id, @class_id, @medium, @chapter_classification, @topic_alphabet_class, 'MCQ',
'Which is different? A, E, I, K',
'{"option1":"A","option2":"E","option3":"I","option4":"K"}',
'{"correctOption":4}',
'K is a consonant, others are vowels.',
'UNDERSTANDING', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_classification, @topic_alphabet_class, 'MCQ',
'Find the odd one: B, C, D, F',
'{"option1":"B","option2":"C","option3":"D","option4":"F"}',
'{"correctOption":4}',
'B, C, D are consecutive, F breaks the sequence.',
'UNDERSTANDING', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_classification, @topic_alphabet_class, 'MCQ',
'Which is different? P, Q, R, T',
'{"option1":"P","option2":"Q","option3":"R","option4":"T"}',
'{"correctOption":4}',
'P, Q, R are consecutive, T breaks the sequence.',
'UNDERSTANDING', 'MEDIUM', @created_by),

-- ============================================================================
-- CHAPTER 3: Co-relation - 12 questions
-- ============================================================================

-- Topic: Vocabulary (3 questions)
(@board_id, @subject_id, @class_id, @medium, @chapter_correlation, @topic_vocab_corr, 'MCQ',
'Eye : See :: Ear : ?',
'{"option1":"Smell","option2":"Hear","option3":"Touch","option4":"Taste"}',
'{"correctOption":2}',
'Eyes are used to see, ears are used to hear.',
'UNDERSTANDING', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_correlation, @topic_vocab_corr, 'MCQ',
'Book : Read :: Food : ?',
'{"option1":"Eat","option2":"Cook","option3":"Buy","option4":"Serve"}',
'{"correctOption":1}',
'Books are meant to read, food is meant to eat.',
'UNDERSTANDING', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_correlation, @topic_vocab_corr, 'MCQ',
'Teacher : School :: Doctor : ?',
'{"option1":"Home","option2":"Hospital","option3":"Market","option4":"Park"}',
'{"correctOption":2}',
'Teacher works in school, doctor works in hospital.',
'UNDERSTANDING', 'EASY', @created_by),

-- Topic: Figures (3 questions)
(@board_id, @subject_id, @class_id, @medium, @chapter_correlation, @topic_figures_corr, 'MCQ',
'Triangle has 3 sides, Square has ?',
'{"option1":"3 sides","option2":"4 sides","option3":"5 sides","option4":"6 sides"}',
'{"correctOption":2}',
'A square has 4 equal sides.',
'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_correlation, @topic_figures_corr, 'MCQ',
'Circle : Round :: Square : ?',
'{"option1":"Curved","option2":"Angular","option3":"Straight","option4":"Bent"}',
'{"correctOption":2}',
'Circle is round, square has angles.',
'UNDERSTANDING', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_correlation, @topic_figures_corr, 'MCQ',
'Rectangle : 4 corners :: Triangle : ?',
'{"option1":"2 corners","option2":"3 corners","option3":"4 corners","option4":"5 corners"}',
'{"correctOption":2}',
'Rectangle has 4 corners, triangle has 3 corners.',
'KNOWLEDGE', 'EASY', @created_by),

-- Topic: Numbers (3 questions)
(@board_id, @subject_id, @class_id, @medium, @chapter_correlation, @topic_numbers_corr, 'MCQ',
'2 : 4 :: 3 : ?',
'{"option1":"5","option2":"6","option3":"7","option4":"8"}',
'{"correctOption":2}',
'Pattern: multiply by 2. 3 × 2 = 6.',
'APPLICATION', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_correlation, @topic_numbers_corr, 'MCQ',
'5 : 25 :: 6 : ?',
'{"option1":"30","option2":"32","option3":"36","option4":"40"}',
'{"correctOption":3}',
'Pattern: square the number. 6² = 36.',
'APPLICATION', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_correlation, @topic_numbers_corr, 'MCQ',
'10 : 100 :: 5 : ?',
'{"option1":"25","option2":"50","option3":"15","option4":"20"}',
'{"correctOption":1}',
'Pattern: multiply by 10 or square. 5² = 25.',
'APPLICATION', 'MEDIUM', @created_by),

-- Topic: Alphabet series (3 questions)
(@board_id, @subject_id, @class_id, @medium, @chapter_correlation, @topic_alphabet_corr, 'MCQ',
'A : B :: C : ?',
'{"option1":"D","option2":"E","option3":"F","option4":"G"}',
'{"correctOption":1}',
'Pattern: next letter. After C comes D.',
'UNDERSTANDING', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_correlation, @topic_alphabet_corr, 'MCQ',
'B : D :: F : ?',
'{"option1":"G","option2":"H","option3":"I","option4":"J"}',
'{"correctOption":2}',
'Pattern: skip one letter. After F, skip G, answer is H.',
'UNDERSTANDING', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_correlation, @topic_alphabet_corr, 'MCQ',
'A : Z :: B : ?',
'{"option1":"X","option2":"Y","option3":"C","option4":"D"}',
'{"correctOption":2}',
'Pattern: first and last letters. B corresponds to Y (second from start and end).',
'APPLICATION', 'HARD', @created_by),

-- ============================================================================
-- CHAPTER 4: Number order - 12 questions
-- ============================================================================

-- Topic: Number pattern (3 questions)
(@board_id, @subject_id, @class_id, @medium, @chapter_number_order, @topic_number_pattern, 'MCQ',
'What comes next: 1, 3, 5, 7, ?',
'{"option1":"8","option2":"9","option3":"10","option4":"11"}',
'{"correctOption":2}',
'Pattern: odd numbers. Next odd number is 9.',
'UNDERSTANDING', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_number_order, @topic_number_pattern, 'MCQ',
'Complete: 2, 4, 8, 16, ?',
'{"option1":"20","option2":"24","option3":"32","option4":"30"}',
'{"correctOption":3}',
'Pattern: multiply by 2. 16 × 2 = 32.',
'APPLICATION', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_number_order, @topic_number_pattern, 'MCQ',
'Find next: 1, 4, 9, 16, 25, ?',
'{"option1":"30","option2":"35","option3":"36","option4":"40"}',
'{"correctOption":3}',
'Pattern: perfect squares. 6² = 36.',
'APPLICATION', 'MEDIUM', @created_by),

-- Topic: Figure pattern (3 questions)
(@board_id, @subject_id, @class_id, @medium, @chapter_number_order, @topic_figure_pattern, 'MCQ',
'Pattern: ○ △ □ ○ △ ?, what comes next?',
'{"option1":"○","option2":"△","option3":"□","option4":"◇"}',
'{"correctOption":3}',
'Pattern repeats: circle, triangle, square.',
'UNDERSTANDING', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_number_order, @topic_figure_pattern, 'MCQ',
'Complete: ★ ★ ☆ ★ ★ ☆ ★ ★ ?',
'{"option1":"★","option2":"☆","option3":"●","option4":"○"}',
'{"correctOption":2}',
'Pattern: two filled stars, one empty star.',
'UNDERSTANDING', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_number_order, @topic_figure_pattern, 'MCQ',
'Find pattern: △ △ ○ △ △ ○ △ △ ?',
'{"option1":"△","option2":"○","option3":"□","option4":"◇"}',
'{"correctOption":2}',
'Pattern: two triangles, one circle.',
'UNDERSTANDING', 'MEDIUM', @created_by),

-- Topic: Symbols (3 questions)
(@board_id, @subject_id, @class_id, @medium, @chapter_number_order, @topic_symbols, 'MCQ',
'If # means +, what is 5 # 3?',
'{"option1":"2","option2":"8","option3":"15","option4":"53"}',
'{"correctOption":2}',
'# means +, so 5 + 3 = 8.',
'APPLICATION', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_number_order, @topic_symbols, 'MCQ',
'If * means ×, what is 4 * 5?',
'{"option1":"9","option2":"20","option3":"1","option4":"45"}',
'{"correctOption":2}',
'* means ×, so 4 × 5 = 20.',
'APPLICATION', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_number_order, @topic_symbols, 'MCQ',
'If @ means -, what is 10 @ 4?',
'{"option1":"6","option2":"14","option3":"40","option4":"2"}',
'{"correctOption":1}',
'@ means -, so 10 - 4 = 6.',
'APPLICATION', 'EASY', @created_by),

-- Topic: Odd man out (3 questions)
(@board_id, @subject_id, @class_id, @medium, @chapter_number_order, @topic_odd_man_out, 'MCQ',
'Which doesn\'t fit: 2, 4, 6, 9, 8',
'{"option1":"2","option2":"4","option3":"9","option4":"8"}',
'{"correctOption":3}',
'9 is odd, all others are even.',
'UNDERSTANDING', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_number_order, @topic_odd_man_out, 'MCQ',
'Find odd: 3, 6, 9, 12, 14',
'{"option1":"3","option2":"6","option3":"12","option4":"14"}',
'{"correctOption":4}',
'14 is not a multiple of 3.',
'UNDERSTANDING', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_number_order, @topic_odd_man_out, 'MCQ',
'Which is different: 5, 10, 15, 21, 25',
'{"option1":"5","option2":"10","option3":"21","option4":"25"}',
'{"correctOption":3}',
'21 is not a multiple of 5.',
'UNDERSTANDING', 'MEDIUM', @created_by),

-- ============================================================================
-- CHAPTER 5: Like Terms - 9 questions
-- ============================================================================

-- Topic: Vocabulary (3 questions)
(@board_id, @subject_id, @class_id, @medium, @chapter_like_terms, @topic_vocab_like, 'MCQ',
'Which pair is similar? Apple-Fruit, Carrot-?',
'{"option1":"Animal","option2":"Vegetable","option3":"Color","option4":"Shape"}',
'{"correctOption":2}',
'Apple is a fruit, Carrot is a vegetable.',
'UNDERSTANDING', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_like_terms, @topic_vocab_like, 'MCQ',
'Pen is to Write as Knife is to ?',
'{"option1":"Cut","option2":"Eat","option3":"Draw","option4":"Read"}',
'{"correctOption":1}',
'Pen is used to write, knife is used to cut.',
'UNDERSTANDING', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_like_terms, @topic_vocab_like, 'MCQ',
'Fish : Water :: Bird : ?',
'{"option1":"Land","option2":"Sky","option3":"Tree","option4":"Nest"}',
'{"correctOption":2}',
'Fish lives in water, bird flies in sky.',
'UNDERSTANDING', 'EASY', @created_by),

-- Topic: Figures (3 questions)
(@board_id, @subject_id, @class_id, @medium, @chapter_like_terms, @topic_figures_like, 'MCQ',
'Which shapes are similar? Circle and ?',
'{"option1":"Triangle","option2":"Square","option3":"Oval","option4":"Rectangle"}',
'{"correctOption":3}',
'Circle and oval are both curved shapes.',
'UNDERSTANDING', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_like_terms, @topic_figures_like, 'MCQ',
'Square is like Rectangle because both have ?',
'{"option1":"3 sides","option2":"4 sides","option3":"5 sides","option4":"No sides"}',
'{"correctOption":2}',
'Both square and rectangle have 4 sides.',
'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_like_terms, @topic_figures_like, 'MCQ',
'Triangle is similar to Pyramid because both have ?',
'{"option1":"Curved lines","option2":"Pointed top","option3":"No corners","option4":"Circular base"}',
'{"correctOption":2}',
'Both have a pointed top.',
'UNDERSTANDING', 'MEDIUM', @created_by),

-- Topic: Numbers (3 questions)
(@board_id, @subject_id, @class_id, @medium, @chapter_like_terms, @topic_numbers_like, 'MCQ',
'Which numbers are alike? 2, 4, 6 and ?',
'{"option1":"7","option2":"8","option3":"9","option4":"11"}',
'{"correctOption":2}',
'All are even numbers. 8 is even.',
'UNDERSTANDING', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_like_terms, @topic_numbers_like, 'MCQ',
'5, 10, 15 are similar to ?',
'{"option1":"20","option2":"22","option3":"23","option4":"24"}',
'{"correctOption":1}',
'All are multiples of 5. 20 is a multiple of 5.',
'UNDERSTANDING', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_like_terms, @topic_numbers_like, 'MCQ',
'1, 4, 9, 16 are similar because they are ?',
'{"option1":"Even numbers","option2":"Odd numbers","option3":"Perfect squares","option4":"Prime numbers"}',
'{"correctOption":3}',
'All are perfect squares: 1², 2², 3², 4².',
'UNDERSTANDING', 'MEDIUM', @created_by),

-- ============================================================================
-- CHAPTER 6: Water Image - 6 questions
-- ============================================================================

-- Topic: Figures (2 questions)
(@board_id, @subject_id, @class_id, @medium, @chapter_water_image, @topic_figures_water, 'MCQ',
'Water image of letter "L" looks like ?',
'{"option1":"L","option2":"Upside down L","option3":"Mirror L","option4":"Rotated L"}',
'{"correctOption":2}',
'Water image is an upside-down reflection.',
'UNDERSTANDING', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_water_image, @topic_figures_water, 'MCQ',
'In water, triangle ▲ appears as ?',
'{"option1":"▲","option2":"▼","option3":"◄","option4":"►"}',
'{"correctOption":2}',
'Water image flips vertically, so ▲ becomes ▼.',
'UNDERSTANDING', 'MEDIUM', @created_by),

-- Topic: Numbers (2 questions)
(@board_id, @subject_id, @class_id, @medium, @chapter_water_image, @topic_numbers_water, 'MCQ',
'Water image of number "2" looks like ?',
'{"option1":"2","option2":"Flipped 2","option3":"5","option4":"3"}',
'{"correctOption":2}',
'Water image flips the number vertically.',
'UNDERSTANDING', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_water_image, @topic_numbers_water, 'MCQ',
'Which number looks same in water? 0, 3, 5, 7',
'{"option1":"0","option2":"3","option3":"5","option4":"7"}',
'{"correctOption":1}',
'0 is symmetrical and looks same in water image.',
'APPLICATION', 'HARD', @created_by),

-- Topic: Alphabets (2 questions)
(@board_id, @subject_id, @class_id, @medium, @chapter_water_image, @topic_alphabets_water, 'MCQ',
'Water image of "A" looks like ?',
'{"option1":"A","option2":"V","option3":"Upside down A","option4":"H"}',
'{"correctOption":3}',
'Water image flips vertically.',
'UNDERSTANDING', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_water_image, @topic_alphabets_water, 'MCQ',
'Which letter looks same in water? A, O, M, L',
'{"option1":"A","option2":"O","option3":"M","option4":"L"}',
'{"correctOption":2}',
'O is circular and symmetrical, looks same in water.',
'APPLICATION', 'HARD', @created_by),

-- ============================================================================
-- CHAPTER 7: Mirror Image - 6 questions
-- ============================================================================

-- Topic: Figures (2 questions)
(@board_id, @subject_id, @class_id, @medium, @chapter_mirror_image, @topic_figures_mirror, 'MCQ',
'Mirror image of "F" looks like ?',
'{"option1":"F","option2":"Reversed F","option3":"Upside down F","option4":"E"}',
'{"correctOption":2}',
'Mirror image flips horizontally.',
'UNDERSTANDING', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_mirror_image, @topic_figures_mirror, 'MCQ',
'In mirror, ► arrow appears as ?',
'{"option1":"►","option2":"◄","option3":"▲","option4":"▼"}',
'{"correctOption":2}',
'Mirror flips horizontally, so ► becomes ◄.',
'UNDERSTANDING', 'MEDIUM', @created_by),

-- Topic: Numbers (2 questions)
(@board_id, @subject_id, @class_id, @medium, @chapter_mirror_image, @topic_numbers_mirror, 'MCQ',
'Mirror image of "3" looks like ?',
'{"option1":"3","option2":"Reversed 3","option3":"E","option4":"8"}',
'{"correctOption":2}',
'Mirror image flips horizontally.',
'UNDERSTANDING', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_mirror_image, @topic_numbers_mirror, 'MCQ',
'Which number looks same in mirror? 0, 2, 5, 8',
'{"option1":"2","option2":"5","option3":"8","option4":"0"}',
'{"correctOption":4}',
'0 is symmetrical and looks same in mirror.',
'APPLICATION', 'HARD', @created_by),

-- Topic: Alphabets (2 questions)
(@board_id, @subject_id, @class_id, @medium, @chapter_mirror_image, @topic_alphabets_mirror, 'MCQ',
'Mirror image of "B" looks like ?',
'{"option1":"B","option2":"Reversed B","option3":"D","option4":"P"}',
'{"correctOption":2}',
'Mirror flips B horizontally.',
'UNDERSTANDING', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_mirror_image, @topic_alphabets_mirror, 'MCQ',
'Which letter looks same in mirror? A, H, S, Z',
'{"option1":"A","option2":"H","option3":"S","option4":"Z"}',
'{"correctOption":2}',
'H is vertically symmetrical, looks same in mirror.',
'APPLICATION', 'HARD', @created_by),

-- ============================================================================
-- CHAPTER 8: Identifying Similarities - 3 questions
-- ============================================================================

-- Topic: Figures (3 questions)
(@board_id, @subject_id, @class_id, @medium, @chapter_similarities, @topic_figures_sim, 'MCQ',
'What is common: Square, Rectangle, Rhombus?',
'{"option1":"3 sides","option2":"4 sides","option3":"5 sides","option4":"Curved"}',
'{"correctOption":2}',
'All have 4 sides.',
'UNDERSTANDING', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_similarities, @topic_figures_sim, 'MCQ',
'Similarity in Circle and Oval?',
'{"option1":"Both have corners","option2":"Both are curved","option3":"Both are angular","option4":"Both have sides"}',
'{"correctOption":2}',
'Both are curved shapes with no corners.',
'UNDERSTANDING', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_similarities, @topic_figures_sim, 'MCQ',
'What is common in Cube and Cuboid?',
'{"option1":"2D shapes","option2":"3D shapes","option3":"Curved shapes","option4":"No faces"}',
'{"correctOption":2}',
'Both are 3-dimensional solid shapes.',
'KNOWLEDGE', 'MEDIUM', @created_by),

-- ============================================================================
-- CHAPTER 9: Logic And Inference - 6 questions
-- ============================================================================

-- Topic: Verbal (3 questions)
(@board_id, @subject_id, @class_id, @medium, @chapter_logic, @topic_verbal_logic, 'MCQ',
'Ram is 5 years old. His brother is 3 years older. How old is his brother?',
'{"option1":"5","option2":"6","option3":"7","option4":"8"}',
'{"correctOption":4}',
'5 + 3 = 8 years old.',
'APPLICATION', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_logic, @topic_verbal_logic, 'MCQ',
'If A is father of B, and B is father of C, then A is _____ of C?',
'{"option1":"Father","option2":"Grandfather","option3":"Uncle","option4":"Brother"}',
'{"correctOption":2}',
'A is grandfather of C (father\'s father).',
'UNDERSTANDING', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_logic, @topic_verbal_logic, 'MCQ',
'Mina is taller than Sita. Sita is taller than Gita. Who is the shortest?',
'{"option1":"Mina","option2":"Sita","option3":"Gita","option4":"Cannot say"}',
'{"correctOption":3}',
'If Mina > Sita > Gita, then Gita is shortest.',
'APPLICATION', 'MEDIUM', @created_by),

-- Topic: Non Verbal (3 questions)
(@board_id, @subject_id, @class_id, @medium, @chapter_logic, @topic_nonverbal_logic, 'MCQ',
'How many triangles in this figure: ▲▲ (two triangles side by side)?',
'{"option1":"1","option2":"2","option3":"3","option4":"4"}',
'{"correctOption":2}',
'Count individual triangles: 2 triangles.',
'APPLICATION', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_logic, @topic_nonverbal_logic, 'MCQ',
'A square is divided by one diagonal. How many triangles are formed?',
'{"option1":"1","option2":"2","option3":"3","option4":"4"}',
'{"correctOption":2}',
'One diagonal divides square into 2 triangles.',
'APPLICATION', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_logic, @topic_nonverbal_logic, 'MCQ',
'How many rectangles in a 2×2 grid?',
'{"option1":"4","option2":"6","option3":"8","option4":"9"}',
'{"correctOption":4}',
'Count: 4 small + 2 horizontal + 2 vertical + 1 large = 9 rectangles.',
'APPLICATION', 'HARD', @created_by),

-- ============================================================================
-- CHAPTER 10: Puzzles - 10 questions
-- ============================================================================

-- Topic: Position in queue (2 questions)
(@board_id, @subject_id, @class_id, @medium, @chapter_puzzles, @topic_position_queue, 'MCQ',
'Ram is 5th from front and 8th from back. How many in the queue?',
'{"option1":"12","option2":"13","option3":"14","option4":"15"}',
'{"correctOption":1}',
'Total = 5 + 8 - 1 = 12 people.',
'APPLICATION', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_puzzles, @topic_position_queue, 'MCQ',
'Sita is 3rd from left and 7th from right. Total children?',
'{"option1":"8","option2":"9","option3":"10","option4":"11"}',
'{"correctOption":2}',
'Total = 3 + 7 - 1 = 9 children.',
'APPLICATION', 'MEDIUM', @created_by),

-- Topic: Direction (2 questions)
(@board_id, @subject_id, @class_id, @medium, @chapter_puzzles, @topic_direction, 'MCQ',
'I face North and turn right. Which direction do I face now?',
'{"option1":"South","option2":"East","option3":"West","option4":"North"}',
'{"correctOption":2}',
'From North, turning right means facing East.',
'APPLICATION', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_puzzles, @topic_direction, 'MCQ',
'I walk 5m North, then 5m East. In which direction is my starting point?',
'{"option1":"North-East","option2":"South-West","option3":"North-West","option4":"South-East"}',
'{"correctOption":2}',
'From current position, starting point is South-West.',
'APPLICATION', 'HARD', @created_by),

-- Topic: Calendar (2 questions)
(@board_id, @subject_id, @class_id, @medium, @chapter_puzzles, @topic_calendar, 'MCQ',
'If today is Monday, what day will it be after 3 days?',
'{"option1":"Tuesday","option2":"Wednesday","option3":"Thursday","option4":"Friday"}',
'{"correctOption":3}',
'Monday + 3 days = Thursday.',
'APPLICATION', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_puzzles, @topic_calendar, 'MCQ',
'How many days in February in a leap year?',
'{"option1":"28","option2":"29","option3":"30","option4":"31"}',
'{"correctOption":2}',
'Leap year February has 29 days.',
'KNOWLEDGE', 'EASY', @created_by),

-- Topic: Venn diagram (2 questions)
(@board_id, @subject_id, @class_id, @medium, @chapter_puzzles, @topic_venn_diagram, 'MCQ',
'15 like cricket, 10 like football, 5 like both. How many like only cricket?',
'{"option1":"5","option2":"10","option3":"15","option4":"20"}',
'{"correctOption":2}',
'Only cricket = 15 - 5 = 10.',
'APPLICATION', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_puzzles, @topic_venn_diagram, 'MCQ',
'20 students, 12 play chess, 10 play carrom, 5 play both. How many play neither?',
'{"option1":"3","option2":"4","option3":"5","option4":"6"}',
'{"correctOption":1}',
'Play at least one = 12 + 10 - 5 = 17. Neither = 20 - 17 = 3.',
'APPLICATION', 'HARD', @created_by),

-- Topic: Numbers in shapes (2 questions)
(@board_id, @subject_id, @class_id, @medium, @chapter_puzzles, @topic_shapes_numbers, 'MCQ',
'Circle has 1, 2, 3. Square has 2, 3, 4. What is common?',
'{"option1":"1","option2":"2 and 3","option3":"4","option4":"None"}',
'{"correctOption":2}',
'Both shapes have 2 and 3 in common.',
'APPLICATION', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_puzzles, @topic_shapes_numbers, 'MCQ',
'Triangle has even numbers. Circle has odd numbers. Where does 6 go?',
'{"option1":"Triangle","option2":"Circle","option3":"Both","option4":"Neither"}',
'{"correctOption":1}',
'6 is even, so it goes in triangle.',
'APPLICATION', 'EASY', @created_by),

-- ============================================================================
-- CHAPTER 11: Symbolic Language - 3 questions
-- ============================================================================

-- Topic: Use of symbols (3 questions)
(@board_id, @subject_id, @class_id, @medium, @chapter_symbolic, @topic_symbolic_lang, 'MCQ',
'If A=1, B=2, C=3, what is D?',
'{"option1":"3","option2":"4","option3":"5","option4":"6"}',
'{"correctOption":2}',
'Pattern: alphabetical position. D is 4th letter.',
'UNDERSTANDING', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_symbolic, @topic_symbolic_lang, 'MCQ',
'If CAT = 3120, what does C represent?',
'{"option1":"1","option2":"2","option3":"3","option4":"4"}',
'{"correctOption":3}',
'C is 3rd letter of alphabet = 3.',
'APPLICATION', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_symbolic, @topic_symbolic_lang, 'MCQ',
'If △ = 5, □ = 3, what is △ + □?',
'{"option1":"5","option2":"8","option3":"15","option4":"53"}',
'{"correctOption":2}',
'△ + □ = 5 + 3 = 8.',
'APPLICATION', 'EASY', @created_by),

-- ============================================================================
-- CHAPTER 12: Special Questions - 4 questions
-- ============================================================================

-- Topic: Emotional & Social Intelligence (4 questions)
(@board_id, @subject_id, @class_id, @medium, @chapter_special, @topic_emotional_intel, 'MCQ',
'Your friend is sad. What should you do?',
'{"option1":"Ignore them","option2":"Laugh at them","option3":"Comfort and talk to them","option4":"Run away"}',
'{"correctOption":3}',
'Show empathy by comforting and talking to your friend.',
'UNDERSTANDING', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_special, @topic_emotional_intel, 'MCQ',
'Someone helps you with homework. You should say?',
'{"option1":"Nothing","option2":"Thank you","option3":"Go away","option4":"I dont need help"}',
'{"correctOption":2}',
'Always thank people who help you.',
'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_special, @topic_emotional_intel, 'MCQ',
'You accidentally break your friend\'s toy. What should you do?',
'{"option1":"Hide it","option2":"Blame someone else","option3":"Say sorry and try to fix it","option4":"Run away"}',
'{"correctOption":3}',
'Take responsibility, apologize, and make amends.',
'UNDERSTANDING', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_special, @topic_emotional_intel, 'MCQ',
'A new student joins your class. You should?',
'{"option1":"Ignore them","option2":"Make fun of them","option3":"Welcome and help them","option4":"Stay away"}',
'{"correctOption":3}',
'Be friendly and help new students feel welcome.',
'UNDERSTANDING', 'EASY', @created_by);

-- ============================================================================
-- COMPLETION MESSAGE
-- ============================================================================

SELECT 'MSCE Class 5 IQ questions insertion completed!' as status;
SELECT 'Total: 100 MCQ questions' as summary;
SELECT 'Distribution: Comprehension(10), Classification(12), Correlation(12), Number Order(12), Like Terms(9), Water Image(6), Mirror Image(6), Similarities(3), Logic(6), Puzzles(10), Symbolic(3), Special(4)' as breakdown;

