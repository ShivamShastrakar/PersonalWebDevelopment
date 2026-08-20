--liquibase formatted sql
--changeset {narendra}:{id}

-- MSCE PRE UPPER PRIMARY SCHOLARSHIP EXAMINATION - CLASS 8 Syllabus (English Medium)
-- Complete Ready-to-Run Script with Class ID=6

SET NAMES utf8mb4 COLLATE utf8mb4_unicode_ci;


-- Here is the SQL script to insert the syllabus data for "English – First Language" for the 8th Standard, English Medium, based on the provided PDF document.


SET FOREIGN_KEY_CHECKS = 0;


START TRANSACTION;

-- 1. Insert Board (MSCE) if not exists and get its ID
SET @board_msce_id = (SELECT id FROM board WHERE board_name = 'MSCE' LIMIT 1);

-- 2. Insert Class 8 if not exists and get its ID
SET @class_8_id = (SELECT id FROM class WHERE class_name = '8' LIMIT 1);

-- 3. Insert Subject 'English – First Language' if not exists and get its ID
SET @subject_english_fl_id = (SELECT subject_id FROM subject WHERE subject_name = 'English – First Language' LIMIT 1);
	
-- 4. Map Subject to Board, Class, and Medium
INSERT IGNORE INTO subject_board_class_mapping (subject_id, class_id, board_id, medium) VALUES
(@subject_english_fl_id, @class_8_id, @board_msce_id, 'English');

-- 5. Insert Chapters and Topics for "English – First Language" (8th Std, English Medium)

-- Chapter: Vocabulary
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES ('Vocabulary', NULL, @subject_english_fl_id, @class_8_id, @board_msce_id);
SET @chapter_vocabulary_id = (SELECT id FROM chapters WHERE chapter_name = 'Vocabulary' AND subject_id = @subject_english_fl_id AND class_id = @class_8_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@chapter_vocabulary_id, @class_8_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('Similar meanings', @chapter_vocabulary_id, @subject_english_fl_id, @class_8_id, @board_msce_id),
('Find out the words which means', @chapter_vocabulary_id, @subject_english_fl_id, @class_8_id, @board_msce_id),
('Find out Opposite words (Antonyms)', @chapter_vocabulary_id, @subject_english_fl_id, @class_8_id, @board_msce_id),
('Find the words Synonyms of', @chapter_vocabulary_id, @subject_english_fl_id, @class_8_id, @board_msce_id),
('Formation Of Words (Word Building – Adjective, Adverbs, Nouns)', @chapter_vocabulary_id, @subject_english_fl_id, @class_8_id, @board_msce_id),
('Phrases-Phrasal verbs, Noun phrases', @chapter_vocabulary_id, @subject_english_fl_id, @class_8_id, @board_msce_id),
('Write contextual meaning of words', @chapter_vocabulary_id, @subject_english_fl_id, @class_8_id, @board_msce_id),
('Writing words using given clues', @chapter_vocabulary_id, @subject_english_fl_id, @class_8_id, @board_msce_id),
('Find out what the following abbreviations stand for', @chapter_vocabulary_id, @subject_english_fl_id, @class_8_id, @board_msce_id);

-- Chapter: Word Puzzles Riddles
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES ('Word Puzzles Riddles', NULL, @subject_english_fl_id, @class_8_id, @board_msce_id);
SET @chapter_word_puzzles_id = (SELECT id FROM chapters WHERE chapter_name = 'Word Puzzles Riddles' AND subject_id = @subject_english_fl_id AND class_id = @class_8_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@chapter_word_puzzles_id, @class_8_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('Crossword Puzzles', @chapter_word_puzzles_id, @subject_english_fl_id, @class_8_id, @board_msce_id),
('Riddles', @chapter_word_puzzles_id, @subject_english_fl_id, @class_8_id, @board_msce_id),
('Word Ladders', @chapter_word_puzzles_id, @subject_english_fl_id, @class_8_id, @board_msce_id),
('Word Web', @chapter_word_puzzles_id, @subject_english_fl_id, @class_8_id, @board_msce_id),
('Word Register', @chapter_word_puzzles_id, @subject_english_fl_id, @class_8_id, @board_msce_id),
('Grid', @chapter_word_puzzles_id, @subject_english_fl_id, @class_8_id, @board_msce_id);

-- Chapter: Language Study
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES ('Language Study', NULL, @subject_english_fl_id, @class_8_id, @board_msce_id);
SET @chapter_language_study_id = (SELECT id FROM chapters WHERE chapter_name = 'Language Study' AND subject_id = @subject_english_fl_id AND class_id = @class_8_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@chapter_language_study_id, @class_8_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('Parts of Speech Tenses', @chapter_language_study_id, @subject_english_fl_id, @class_8_id, @board_msce_id),
('Modal auxiliaries', @chapter_language_study_id, @subject_english_fl_id, @class_8_id, @board_msce_id),
('Clauses', @chapter_language_study_id, @subject_english_fl_id, @class_8_id, @board_msce_id),
('Types of Sentences (Simple, Compound, Complex)', @chapter_language_study_id, @subject_english_fl_id, @class_8_id, @board_msce_id),
('Figures of Speech (Simile, Metaphor, Personification, Hyperbole, Antithesis, Exclamation, Climax, Anticlimax)', @chapter_language_study_id, @subject_english_fl_id, @class_8_id, @board_msce_id),
('Synthesis', @chapter_language_study_id, @subject_english_fl_id, @class_8_id, @board_msce_id);

-- Chapter: Grammar
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES ('Grammar', NULL, @subject_english_fl_id, @class_8_id, @board_msce_id);
SET @chapter_grammar_id = (SELECT id FROM chapters WHERE chapter_name = 'Grammar' AND subject_id = @subject_english_fl_id AND class_id = @class_8_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@chapter_grammar_id, @class_8_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('Active, Passive Voice', @chapter_grammar_id, @subject_english_fl_id, @class_8_id, @board_msce_id),
('Direct, Indirect Speech', @chapter_grammar_id, @subject_english_fl_id, @class_8_id, @board_msce_id),
('Degree', @chapter_grammar_id, @subject_english_fl_id, @class_8_id, @board_msce_id),
('Transformation (Affirmative, Negative, Add a Question Tag, Exclamatory, Assertive.)', @chapter_grammar_id, @subject_english_fl_id, @class_8_id, @board_msce_id),
('Co-relative Conjunctions', @chapter_grammar_id, @subject_english_fl_id, @class_8_id, @board_msce_id),
('Compound Conjunctions', @chapter_grammar_id, @subject_english_fl_id, @class_8_id, @board_msce_id),
('Wh Questions', @chapter_grammar_id, @subject_english_fl_id, @class_8_id, @board_msce_id);

-- Chapter: Creative Writing
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES ('Creative Writing', NULL, @subject_english_fl_id, @class_8_id, @board_msce_id);
SET @chapter_creative_writing_id = (SELECT id FROM chapters WHERE chapter_name = 'Creative Writing' AND subject_id = @subject_english_fl_id AND class_id = @class_8_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@chapter_creative_writing_id, @class_8_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('Responding', @chapter_creative_writing_id, @subject_english_fl_id, @class_8_id, @board_msce_id),
('News', @chapter_creative_writing_id, @subject_english_fl_id, @class_8_id, @board_msce_id),
('Advertisement', @chapter_creative_writing_id, @subject_english_fl_id, @class_8_id, @board_msce_id),
('Emails', @chapter_creative_writing_id, @subject_english_fl_id, @class_8_id, @board_msce_id),
('Websites', @chapter_creative_writing_id, @subject_english_fl_id, @class_8_id, @board_msce_id),
('Sms', @chapter_creative_writing_id, @subject_english_fl_id, @class_8_id, @board_msce_id),
('Complete slogans', @chapter_creative_writing_id, @subject_english_fl_id, @class_8_id, @board_msce_id),
('Dialogue writing', @chapter_creative_writing_id, @subject_english_fl_id, @class_8_id, @board_msce_id),
('Letter writing', @chapter_creative_writing_id, @subject_english_fl_id, @class_8_id, @board_msce_id),
('Interview', @chapter_creative_writing_id, @subject_english_fl_id, @class_8_id, @board_msce_id),
('Report writing', @chapter_creative_writing_id, @subject_english_fl_id, @class_8_id, @board_msce_id),
('Write-up', @chapter_creative_writing_id, @subject_english_fl_id, @class_8_id, @board_msce_id),
('Quotations', @chapter_creative_writing_id, @subject_english_fl_id, @class_8_id, @board_msce_id);

-- Chapter: Reading Skills (Comprehension)
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES ('Reading Skills (Comprehension)', NULL, @subject_english_fl_id, @class_8_id, @board_msce_id);
SET @chapter_reading_skills_id = (SELECT id FROM chapters WHERE chapter_name = 'Reading Skills (Comprehension)' AND subject_id = @subject_english_fl_id AND class_id = @class_8_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@chapter_reading_skills_id, @class_8_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('Extracts', @chapter_reading_skills_id, @subject_english_fl_id, @class_8_id, @board_msce_id),
('Poem (two to three stanzas)', @chapter_reading_skills_id, @subject_english_fl_id, @class_8_id, @board_msce_id),
('Prose (70 to 80 words)', @chapter_reading_skills_id, @subject_english_fl_id, @class_8_id, @board_msce_id),
('News', @chapter_reading_skills_id, @subject_english_fl_id, @class_8_id, @board_msce_id),
('Dialogues', @chapter_reading_skills_id, @subject_english_fl_id, @class_8_id, @board_msce_id),
('Travelogue', @chapter_reading_skills_id, @subject_english_fl_id, @class_8_id, @board_msce_id),
('Leaflet', @chapter_reading_skills_id, @subject_english_fl_id, @class_8_id, @board_msce_id),
('Write-up', @chapter_reading_skills_id, @subject_english_fl_id, @class_8_id, @board_msce_id),
('Notice', @chapter_reading_skills_id, @subject_english_fl_id, @class_8_id, @board_msce_id),
('Interview', @chapter_reading_skills_id, @subject_english_fl_id, @class_8_id, @board_msce_id),
('Tour itinerary', @chapter_reading_skills_id, @subject_english_fl_id, @class_8_id, @board_msce_id);


-- Chapter: Miscellaneous (Loan Words)
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES ('Miscellaneous (Loan Words)', NULL, @subject_english_fl_id, @class_8_id, @board_msce_id);
SET @chapter_misc_loan_words_id = (SELECT id FROM chapters WHERE chapter_name = 'Miscellaneous (Loan Words)' AND subject_id = @subject_english_fl_id AND class_id = @class_8_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@chapter_misc_loan_words_id, @class_8_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('Loan Words', @chapter_misc_loan_words_id, @subject_english_fl_id, @class_8_id, @board_msce_id),
('Indian Words - Used in Text Books. (Code Mixing - Non-English words)', @chapter_misc_loan_words_id, @subject_english_fl_id, @class_8_id, @board_msce_id);


-- Here is the SQL script to insert the syllabus data for "Math – English" for the 8th Standard, English Medium, based on the provided PDF document.

-- 3. Insert Subject 'Math – English' if not exists and get its ID
SET @subject_math_english_id = (SELECT subject_id FROM subject WHERE subject_name = 'Math – English' LIMIT 1);

-- 4. Map Subject to Board, Class, and Medium
INSERT IGNORE INTO subject_board_class_mapping (subject_id, class_id, board_id, medium) VALUES
(@subject_math_english_id, @class_8_id, @board_msce_id, 'English');

-- 5. Insert Chapters and Topics for "Math – English" (8th Std, English Medium)

-- Chapter: Number Work
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES ('Number Work', NULL, @subject_math_english_id, @class_8_id, @board_msce_id);
SET @chapter_number_work_id = (SELECT id FROM chapters WHERE chapter_name = 'Number Work' AND subject_id = @subject_math_english_id AND class_id = @class_8_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@chapter_number_work_id, @class_8_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('Natural numbers, whole numbers, integer rational numbers, irrational numbers, real numbers', @chapter_number_work_id, @subject_math_english_id, @class_8_id, @board_msce_id),
('Rational numbers and operations on rational numbers', @chapter_number_work_id, @subject_math_english_id, @class_8_id, @board_msce_id),
('Even, Odd numbers, prime, twin prime numbers, co-prime numbers, composite numbers', @chapter_number_work_id, @subject_math_english_id, @class_8_id, @board_msce_id),
('Additive inverse and multiplicative inverse of the number', @chapter_number_work_id, @subject_math_english_id, @class_8_id, @board_msce_id),
('Number line', @chapter_number_work_id, @subject_math_english_id, @class_8_id, @board_msce_id);

-- Chapter: Operations on numbers
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES ('Operations on numbers', NULL, @subject_math_english_id, @class_8_id, @board_msce_id);
SET @chapter_operations_id = (SELECT id FROM chapters WHERE chapter_name = 'Operations on numbers' AND subject_id = @subject_math_english_id AND class_id = @class_8_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@chapter_operations_id, @class_8_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('Dividend, Divisor, Test of Divisibility, L.C.M., G.C.D.', @chapter_operations_id, @subject_math_english_id, @class_8_id, @board_msce_id),
('Square and Square-Root, Cube and Cube-Root', @chapter_operations_id, @subject_math_english_id, @class_8_id, @board_msce_id),
('Vulgar fraction and Decimal fractions', @chapter_operations_id, @subject_math_english_id, @class_8_id, @board_msce_id),
('Indices', @chapter_operations_id, @subject_math_english_id, @class_8_id, @board_msce_id),
('Ratio, proportion and variation. (time-work-speed)', @chapter_operations_id, @subject_math_english_id, @class_8_id, @board_msce_id),
('Average', @chapter_operations_id, @subject_math_english_id, @class_8_id, @board_msce_id);

-- Chapter: Geometry
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES ('Geometry', NULL, @subject_math_english_id, @class_8_id, @board_msce_id);
SET @chapter_geometry_id = (SELECT id FROM chapters WHERE chapter_name = 'Geometry' AND subject_id = @subject_math_english_id AND class_id = @class_8_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@chapter_geometry_id, @class_8_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('Point, segment, line, ray, angle (Straight angle, Zero angle, Whole angle, Reflex angle)', @chapter_geometry_id, @subject_math_english_id, @class_8_id, @board_msce_id),
('Properties of parallel lines', @chapter_geometry_id, @subject_math_english_id, @class_8_id, @board_msce_id),
('Circle, Circular region, Segment, Area of a circle', @chapter_geometry_id, @subject_math_english_id, @class_8_id, @board_msce_id),
('Properties of triangle and congruence of the triangles', @chapter_geometry_id, @subject_math_english_id, @class_8_id, @board_msce_id),
('Properties of quadrilateral', @chapter_geometry_id, @subject_math_english_id, @class_8_id, @board_msce_id),
('Pythagoras Theorem', @chapter_geometry_id, @subject_math_english_id, @class_8_id, @board_msce_id);

-- Chapter: Mensuration
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES ('Mensuration', NULL, @subject_math_english_id, @class_8_id, @board_msce_id);
SET @chapter_mensuration_id = (SELECT id FROM chapters WHERE chapter_name = 'Mensuration' AND subject_id = @subject_math_english_id AND class_id = @class_8_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@chapter_mensuration_id, @class_8_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('Measures-Length, Mass, Capacity, Measurement of Coins and Currency Notes', @chapter_mensuration_id, @subject_math_english_id, @class_8_id, @board_msce_id),
('Perimeter- Triangle, Quadrilateral Polygon', @chapter_mensuration_id, @subject_math_english_id, @class_8_id, @board_msce_id),
('Area- Triangle, Square, Rectangle, Parallelogram Rhombus Quadrilateral, Circle, Irregular Figure, Shaded Portion Trapezium', @chapter_mensuration_id, @subject_math_english_id, @class_8_id, @board_msce_id),
('Volume and Surface area, Cuboid, Cube, Cylinder, Cone, Sphere', @chapter_mensuration_id, @subject_math_english_id, @class_8_id, @board_msce_id);

-- Chapter: Statistics
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES ('Statistics', NULL, @subject_math_english_id, @class_8_id, @board_msce_id);
SET @chapter_statistics_id = (SELECT id FROM chapters WHERE chapter_name = 'Statistics' AND subject_id = @subject_math_english_id AND class_id = @class_8_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@chapter_statistics_id, @class_8_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('Mean, Pictographs, Bar Graph, Joint Bar Graph, Pie Chart', @chapter_statistics_id, @subject_math_english_id, @class_8_id, @board_msce_id),
('Sub divided bar graph, Percentage bar graph (Only introduction)', @chapter_statistics_id, @subject_math_english_id, @class_8_id, @board_msce_id);

-- Chapter: Applied mathematics
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES ('Applied mathematics', NULL, @subject_math_english_id, @class_8_id, @board_msce_id);
SET @chapter_applied_math_id = (SELECT id FROM chapters WHERE chapter_name = 'Applied mathematics' AND subject_id = @subject_math_english_id AND class_id = @class_8_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@chapter_applied_math_id, @class_8_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('Percentage', @chapter_applied_math_id, @subject_math_english_id, @class_8_id, @board_msce_id),
('Simple interest, Compound interest', @chapter_applied_math_id, @subject_math_english_id, @class_8_id, @board_msce_id),
('Profit-Loss, Discount, Rebate, Commission', @chapter_applied_math_id, @subject_math_english_id, @class_8_id, @board_msce_id);

-- Chapter: Algebra
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES ('Algebra', NULL, @subject_math_english_id, @class_8_id, @board_msce_id);
SET @chapter_algebra_id = (SELECT id FROM chapters WHERE chapter_name = 'Algebra' AND subject_id = @subject_math_english_id AND class_id = @class_8_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@chapter_algebra_id, @class_8_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('Use of letters for numbers, factors of algebraic expressions. Value of the polynomial and operations on them', @chapter_algebra_id, @subject_math_english_id, @class_8_id, @board_msce_id),
('Identity', @chapter_algebra_id, @subject_math_english_id, @class_8_id, @board_msce_id),
('Equations in one variable and word problems based on it', @chapter_algebra_id, @subject_math_english_id, @class_8_id, @board_msce_id),
('Polynomial, their operations and factors of polynomial', @chapter_algebra_id, @subject_math_english_id, @class_8_id, @board_msce_id);


---PRE UPPER PRIMARY SCHOLARSHIP EXAMINATION – Std 8 – English Medium, including Marathi (Third Language)

-- CLASS: 8th
SET @class_8th_id = (
  SELECT id FROM class WHERE class_name = '8' LIMIT 1
);


SET @sub_mar  = (SELECT subject_id FROM subject WHERE subject_name='Marathi – Third Language');

INSERT IGNORE INTO subject_board_class_mapping
(subject_id, class_id, board_id, medium)
VALUES
(@sub_mar,  @class_8th_id, @board_msce_id, 'ENGLISH');


--Marathi Chapters (Units)
INSERT IGNORE INTO chapters
(chapter_name, unit, subject_id, class_id, board_id)
VALUES
('आकलन', 'Unit 1', @sub_mar, @class_8th_id, @board_msce_id),
('शब्दसंपत्ती', 'Unit 2', @sub_mar, @class_8th_id, @board_msce_id),
('कार्यात्मक व्याकरण', 'Unit 3', @sub_mar, @class_8th_id, @board_msce_id),
('सामान्य ज्ञान (इ.1 ते इ.8 मराठी)', 'Unit 4', @sub_mar, @class_8th_id, @board_msce_id);



SET @ch_akal   = (SELECT id FROM chapters WHERE chapter_name='आकलन' AND subject_id=@sub_mar and class_id=@class_8th_id AND board_id = @board_msce_id);
SET @ch_shabd  = (SELECT id FROM chapters WHERE chapter_name='शब्दसंपत्ती' AND subject_id=@sub_mar and class_id=@class_8th_id AND board_id = @board_msce_id);
SET @ch_vyak   = (SELECT id FROM chapters WHERE chapter_name='कार्यात्मक व्याकरण' AND subject_id=@sub_mar and class_id=@class_8th_id AND board_id = @board_msce_id);
SET @ch_gk     = (SELECT id FROM chapters WHERE chapter_name LIKE 'सामान्य ज्ञान%' AND subject_id=@sub_mar and class_id=@class_8th_id AND board_id = @board_msce_id);


--Unit 1: आकलन
INSERT IGNORE INTO topics
(topic_name, chapter_id, subject_id, class_id, board_id)
VALUES
('उताऱ्यावर आधारित प्रश्न', @ch_akal, @sub_mar, @class_8th_id, @board_msce_id),
('कवितेवर आधारित प्रश्न', @ch_akal, @sub_mar, @class_8th_id, @board_msce_id),
('सुसंगत वाक्यांचा परिच्छेद', @ch_akal, @sub_mar, @class_8th_id, @board_msce_id),
('संवादावर आधारित प्रश्न', @ch_akal, @sub_mar, @class_8th_id, @board_msce_id);


--Unit 2: शब्दसंपत्ती
INSERT IGNORE INTO topics
(topic_name, chapter_id, subject_id, class_id, board_id)
VALUES
('समानार्थी शब्द',@ch_shabd,@sub_mar,@class_8th_id,@board_msce_id),
('विरुद्धार्थी शब्द',@ch_shabd,@sub_mar,@class_8th_id,@board_msce_id),
('शुद्ध व अशुद्ध शब्द',@ch_shabd,@sub_mar,@class_8th_id,@board_msce_id),
('शब्दसमूहाबद्दल एक शब्द',@ch_shabd,@sub_mar,@class_8th_id,@board_msce_id),
('वाक्प्रचार',@ch_shabd,@sub_mar,@class_8th_id,@board_msce_id),
('म्हणी',@ch_shabd,@sub_mar,@class_8th_id,@board_msce_id),
('जोडशब्द',@ch_shabd,@sub_mar,@class_8th_id,@board_msce_id),
('एकाच शब्दाचे भिन्न अर्थ असणारे शब्द',@ch_shabd,@sub_mar,@class_8th_id,@board_msce_id);


--Unit 3: कार्यात्मक व्याकरण
INSERT IGNORE INTO topics 
(topic_name, chapter_id, subject_id, class_id, board_id)
VALUES
('वर्णविचार (स्वर, व्यंजन, स्वरादी)',@ch_vyak,@sub_mar,@class_8th_id,@board_msce_id),
('शब्दांच्या जाती',@ch_vyak,@sub_mar,@class_8th_id,@board_msce_id),
('लिंग',@ch_vyak,@sub_mar,@class_8th_id,@board_msce_id),
('वचन',@ch_vyak,@sub_mar,@class_8th_id,@board_msce_id),
('विरामचिन्हे',@ch_vyak,@sub_mar,@class_8th_id,@board_msce_id),
('काळ',@ch_vyak,@sub_mar,@class_8th_id,@board_msce_id);

--Unit 4: सामान्य ज्ञान (इ.1 ते इ.8 मराठी)
INSERT IGNORE INTO topics 
(topic_name, chapter_id, subject_id, class_id, board_id)
VALUES
('साहित्य व साहित्यप्रकार',@ch_gk,@sub_mar,@class_8th_id,@board_msce_id),
('सामान्य ज्ञान',@ch_gk,@sub_mar,@class_8th_id,@board_msce_id);


-- SUBJECT: IQ – English
SET @sub_iq = (
  SELECT subject_id FROM subject WHERE subject_name = 'IQ – English' LIMIT 1
);

INSERT IGNORE INTO subject_board_class_mapping
(subject_id, class_id, board_id, medium)
VALUES
(@sub_iq,  @class_8th_id, @board_msce_id, 'ENGLISH');

-- IQ Chapters (Units)
INSERT IGNORE INTO chapters
(chapter_name, unit, subject_id, class_id, board_id)
VALUES
('Comprehension', 'Unit 1', @sub_iq, @class_8th_id, @board_msce_id),
('Classification', 'Unit 2', @sub_iq, @class_8th_id, @board_msce_id),
('Correlation', 'Unit 3', @sub_iq, @class_8th_id, @board_msce_id),
('Series (Order)', 'Unit 4', @sub_iq, @class_8th_id, @board_msce_id),
('Code Language', 'Unit 5', @sub_iq, @class_8th_id, @board_msce_id),
('Rhythm and Sequence', 'Unit 6', @sub_iq, @class_8th_id, @board_msce_id),
('Pyramids', 'Unit 7', @sub_iq, @class_8th_id, @board_msce_id),
('Reflection / Image', 'Unit 8', @sub_iq, @class_8th_id, @board_msce_id),
('Logic and Conclusion', 'Unit 9', @sub_iq, @class_8th_id, @board_msce_id),
('Puzzles and Brain Teasers', 'Unit 10', @sub_iq, @class_8th_id, @board_msce_id),
('Analysis of Figure', 'Unit 11', @sub_iq, @class_8th_id, @board_msce_id);


SET @ch_comp  = (SELECT id FROM chapters WHERE chapter_name='Comprehension' AND subject_id=@sub_iq and class_id=@class_8th_id AND board_id = @board_msce_id);
SET @ch_class = (SELECT id FROM chapters WHERE chapter_name='Classification' AND subject_id=@sub_iq and class_id=@class_8th_id AND board_id = @board_msce_id);
SET @ch_corr  = (SELECT id FROM chapters WHERE chapter_name='Correlation' AND subject_id=@sub_iq and class_id=@class_8th_id AND board_id = @board_msce_id);
SET @ch_series= (SELECT id FROM chapters WHERE chapter_name='Series (Order)' AND subject_id=@sub_iq and class_id=@class_8th_id AND board_id = @board_msce_id);
SET @ch_code  = (SELECT id FROM chapters WHERE chapter_name='Code Language' AND subject_id=@sub_iq and class_id=@class_8th_id AND board_id = @board_msce_id);
SET @ch_rhythm= (SELECT id FROM chapters WHERE chapter_name='Rhythm and Sequence' AND subject_id=@sub_iq and class_id=@class_8th_id AND board_id = @board_msce_id);
SET @ch_pyr   = (SELECT id FROM chapters WHERE chapter_name='Pyramids' AND subject_id=@sub_iq and class_id=@class_8th_id AND board_id = @board_msce_id);
SET @ch_ref   = (SELECT id FROM chapters WHERE chapter_name LIKE 'Reflection%' AND subject_id=@sub_iq and class_id=@class_8th_id AND board_id = @board_msce_id);
SET @ch_logic = (SELECT id FROM chapters WHERE chapter_name='Logic and Conclusion' AND subject_id=@sub_iq and class_id=@class_8th_id AND board_id = @board_msce_id);
SET @ch_puzz  = (SELECT id FROM chapters WHERE chapter_name='Puzzles and Brain Teasers' AND subject_id=@sub_iq and class_id=@class_8th_id AND board_id = @board_msce_id);
SET @ch_fig   = (SELECT id FROM chapters WHERE chapter_name='Analysis of Figure' AND subject_id=@sub_iq and class_id=@class_8th_id AND board_id = @board_msce_id);


--Unit 1: Comprehension
INSERT IGNORE INTO topics 
(topic_name, chapter_id, subject_id, class_id, board_id)
VALUES
('Follow the given instructions and analyse the content',@ch_comp,@sub_iq,@class_8th_id,@board_msce_id),
('Knowledge of language',@ch_comp,@sub_iq,@class_8th_id,@board_msce_id),
('English alphabet',@ch_comp,@sub_iq,@class_8th_id,@board_msce_id);

--Unit 2: Classification
INSERT IGNORE INTO topics 
(topic_name, chapter_id, subject_id, class_id, board_id)
VALUES
('Vocabulary',@ch_class,@sub_iq,@class_8th_id,@board_msce_id),
('Figures',@ch_class,@sub_iq,@class_8th_id,@board_msce_id),
('Numbers',@ch_class,@sub_iq,@class_8th_id,@board_msce_id),
('English alphabet',@ch_class,@sub_iq,@class_8th_id,@board_msce_id);

-- Unit 3: Correlation
INSERT IGNORE INTO topics 
(topic_name, chapter_id, subject_id, class_id, board_id)
VALUES
('Vocabulary',@ch_corr,@sub_iq,@class_8th_id,@board_msce_id),
('Figures',@ch_corr,@sub_iq,@class_8th_id,@board_msce_id),
('Numbers',@ch_corr,@sub_iq,@class_8th_id,@board_msce_id),
('English alphabet',@ch_corr,@sub_iq,@class_8th_id,@board_msce_id);


--Unit 4: Series (Order)
INSERT IGNORE INTO topics 
(topic_name, chapter_id, subject_id, class_id, board_id)
VALUES
('Number series',@ch_series,@sub_iq,@class_8th_id,@board_msce_id),
('Figure series',@ch_series,@sub_iq,@class_8th_id,@board_msce_id),
('Sign series',@ch_series,@sub_iq,@class_8th_id,@board_msce_id),
('To find the odd term',@ch_series,@sub_iq,@class_8th_id,@board_msce_id),
('Alphabet series',@ch_series,@sub_iq,@class_8th_id,@board_msce_id);

-- Unit 5: Code Language
INSERT IGNORE INTO topics 
(topic_name, chapter_id, subject_id, class_id, board_id)
VALUES
('Figures',@ch_code,@sub_iq,@class_8th_id,@board_msce_id),
('Numbers',@ch_code,@sub_iq,@class_8th_id,@board_msce_id),
('Letters',@ch_code,@sub_iq,@class_8th_id,@board_msce_id);

-- Unit 6: Rhythm and Sequence
INSERT IGNORE INTO topics 
(topic_name, chapter_id, subject_id, class_id, board_id)
VALUES
('Use of letters',@ch_rhythm,@sub_iq,@class_8th_id,@board_msce_id),
('Use of signs',@ch_rhythm,@sub_iq,@class_8th_id,@board_msce_id),
('Use of numbers',@ch_rhythm,@sub_iq,@class_8th_id,@board_msce_id);


-- Unit 7: Pyramids

INSERT IGNORE INTO topics 
(topic_name, chapter_id, subject_id, class_id, board_id)
VALUES
('Use of numbers',@ch_pyr,@sub_iq,@class_8th_id,@board_msce_id),
('Use of letters',@ch_pyr,@sub_iq,@class_8th_id,@board_msce_id);


-- Unit 8: Reflection / Image
INSERT IGNORE INTO topics 
(topic_name, chapter_id, subject_id, class_id, board_id)
VALUES
('Numbers',@ch_ref,@sub_iq,@class_8th_id,@board_msce_id),
('Letters',@ch_ref,@sub_iq,@class_8th_id,@board_msce_id),
('Figures',@ch_ref,@sub_iq,@class_8th_id,@board_msce_id);


-- Unit 9: Logic and Conclusion
INSERT IGNORE INTO topics 
(topic_name, chapter_id, subject_id, class_id, board_id)
VALUES
('Verbal reasoning: age, time, clock and logical relation',@ch_logic,@sub_iq,@class_8th_id,@board_msce_id),
('Non-verbal reasoning: cube, cuboid, triangle and square',@ch_logic,@sub_iq,@class_8th_id,@board_msce_id),
('Identifying formulae in number series',@ch_logic,@sub_iq,@class_8th_id,@board_msce_id);

-- Unit 10: Puzzles and Brain Teasers
INSERT IGNORE INTO topics 
(topic_name, chapter_id, subject_id, class_id, board_id)
VALUES
('Position in a queue',@ch_puzz,@sub_iq,@class_8th_id,@board_msce_id),
('Problems based on direction',@ch_puzz,@sub_iq,@class_8th_id,@board_msce_id),
('Calendar problems',@ch_puzz,@sub_iq,@class_8th_id,@board_msce_id),
('Venn diagram',@ch_puzz,@sub_iq,@class_8th_id,@board_msce_id),
('Mathematical puzzles',@ch_puzz,@sub_iq,@class_8th_id,@board_msce_id);


-- Unit 11: Analysis of Figure
INSERT IGNORE INTO topics 
(topic_name, chapter_id, subject_id, class_id, board_id)
VALUES
('To complete the figure',@ch_fig,@sub_iq,@class_8th_id,@board_msce_id),
('Exact replica / identical figures',@ch_fig,@sub_iq,@class_8th_id,@board_msce_id),
('Figures by folding and unfolding paper',@ch_fig,@sub_iq,@class_8th_id,@board_msce_id),
('Finding the hidden figure',@ch_fig,@sub_iq,@class_8th_id,@board_msce_id);


COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
