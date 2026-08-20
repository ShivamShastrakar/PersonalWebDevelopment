--liquibase formatted sql
--changeset {narendra}:{id}

-- MSCE PRE UPPER PRIMARY SCHOLARSHIP EXAMINATION - CLASS 7 Syllabus (English Medium)
-- Complete Ready-to-Run Script with Class Name = '7'

SET NAMES utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Here is the SQL script to insert the syllabus data for "English – First Language" for the 7th Standard, English Medium, based on the provided PDF document.


SET FOREIGN_KEY_CHECKS = 0;


START TRANSACTION;

-- 1. Insert Board (MSCE) if not exists and get its ID
INSERT IGNORE INTO board (tenant_id, board_name) VALUES (NULL, 'MSCE');
SET @board_msce_id = (SELECT id FROM board WHERE board_name = 'MSCE' LIMIT 1);

-- 2. Insert Class 7 if not exists and get its ID
INSERT IGNORE INTO class (class_name) VALUES ('7');
SET @class_7_id = (SELECT id FROM class WHERE class_name = '7' LIMIT 1);

-- 3. Insert Subject 'English – First Language' if not exists and get its ID
INSERT IGNORE INTO subject (subject_name) VALUES ('English – First Language');
SET @subject_english_fl_id = (SELECT subject_id FROM subject WHERE subject_name = 'English – First Language' LIMIT 1);

-- 4. Map Subject to Board, Class, and Medium
INSERT IGNORE INTO subject_board_class_mapping (subject_id, class_id, board_id, medium) VALUES
(@subject_english_fl_id, @class_7_id, @board_msce_id, 'English');


-- 5. Insert Chapters and Topics for "English – First Language" (7th Std, English Medium)

-- Chapter: Vocabulary
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES ('Vocabulary', NULL, @subject_english_fl_id, @class_7_id, @board_msce_id);
SET @chapter_vocabulary_id = (SELECT id FROM chapters WHERE chapter_name = 'Vocabulary' AND subject_id = @subject_english_fl_id AND class_id = @class_7_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@chapter_vocabulary_id, @class_7_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('Similar meanings', @chapter_vocabulary_id, @subject_english_fl_id, @class_7_id, @board_msce_id),
('Homonyms', @chapter_vocabulary_id, @subject_english_fl_id, @class_7_id, @board_msce_id),
('Find out the words which means', @chapter_vocabulary_id, @subject_english_fl_id, @class_7_id, @board_msce_id),
('Find out Opposite words (Antonyms)', @chapter_vocabulary_id, @subject_english_fl_id, @class_7_id, @board_msce_id),
('Find the words Synonyms of', @chapter_vocabulary_id, @subject_english_fl_id, @class_7_id, @board_msce_id),
('Formation Of Words (Word Building – Adjective, Adverbs, Nouns)', @chapter_vocabulary_id, @subject_english_fl_id, @class_7_id, @board_msce_id),
('Phrases-Phrasal verbs, Noun phrases', @chapter_vocabulary_id, @subject_english_fl_id, @class_7_id, @board_msce_id),
('Write contextual meaning of words', @chapter_vocabulary_id, @subject_english_fl_id, @class_7_id, @board_msce_id),
('Writing words using given clues', @chapter_vocabulary_id, @subject_english_fl_id, @class_7_id, @board_msce_id),
('Find out what the following abbreviations stand for', @chapter_vocabulary_id, @subject_english_fl_id, @class_7_id, @board_msce_id),
('Prefix, Suffix', @chapter_vocabulary_id, @subject_english_fl_id, @class_7_id, @board_msce_id);

-- Chapter: Word Puzzles Riddles
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES ('Word Puzzles Riddles', NULL, @subject_english_fl_id, @class_7_id, @board_msce_id);
SET @chapter_word_puzzles_id = (SELECT id FROM chapters WHERE chapter_name = 'Word Puzzles Riddles' AND subject_id = @subject_english_fl_id AND class_id = @class_7_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@chapter_word_puzzles_id, @class_7_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('Crossword Puzzles', @chapter_word_puzzles_id, @subject_english_fl_id, @class_7_id, @board_msce_id),
('Riddles', @chapter_word_puzzles_id, @subject_english_fl_id, @class_7_id, @board_msce_id),
('Word Ladders', @chapter_word_puzzles_id, @subject_english_fl_id, @class_7_id, @board_msce_id),
('Word Web', @chapter_word_puzzles_id, @subject_english_fl_id, @class_7_id, @board_msce_id),
('Word Register', @chapter_word_puzzles_id, @subject_english_fl_id, @class_7_id, @board_msce_id),
('Grid', @chapter_word_puzzles_id, @subject_english_fl_id, @class_7_id, @board_msce_id);

-- Chapter: Language Study
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES ('Language Study', NULL, @subject_english_fl_id, @class_7_id, @board_msce_id);
SET @chapter_language_study_id = (SELECT id FROM chapters WHERE chapter_name = 'Language Study' AND subject_id = @subject_english_fl_id AND class_id = @class_7_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@chapter_language_study_id, @class_7_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('Parts of Speech Tenses', @chapter_language_study_id, @subject_english_fl_id, @class_7_id, @board_msce_id),
('Modal auxiliaries', @chapter_language_study_id, @subject_english_fl_id, @class_7_id, @board_msce_id),
('Clauses (Identify the main clause and subordinate clause)', @chapter_language_study_id, @subject_english_fl_id, @class_7_id, @board_msce_id),
('Types of Sentences (Simple, Compound, Complex)', @chapter_language_study_id, @subject_english_fl_id, @class_7_id, @board_msce_id),
('Kinds of Sentences (Assertive, Interrogative, Exclamatory, Imperative)', @chapter_language_study_id, @subject_english_fl_id, @class_7_id, @board_msce_id),
('Figures of Speech (Simile, Metaphor, Personification, Antithesis, Alliteration, Repetition, Exclamation)', @chapter_language_study_id, @subject_english_fl_id, @class_7_id, @board_msce_id);

-- Chapter: Grammar
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES ('Grammar', NULL, @subject_english_fl_id, @class_7_id, @board_msce_id);
SET @chapter_grammar_id = (SELECT id FROM chapters WHERE chapter_name = 'Grammar' AND subject_id = @subject_english_fl_id AND class_id = @class_7_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@chapter_grammar_id, @class_7_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('Active, Passive Voice', @chapter_grammar_id, @subject_english_fl_id, @class_7_id, @board_msce_id),
('Direct, Indirect Speech', @chapter_grammar_id, @subject_english_fl_id, @class_7_id, @board_msce_id),
('Degree', @chapter_grammar_id, @subject_english_fl_id, @class_7_id, @board_msce_id),
('Transformation (Affirmative, Negative, Add a Question Tag, Exclamatory to Assertive, Assertive to Exclamatory, Interrogative to assertive and Assertive to Interrogative)', @chapter_grammar_id, @subject_english_fl_id, @class_7_id, @board_msce_id),
('Co-relative Conjunctions', @chapter_grammar_id, @subject_english_fl_id, @class_7_id, @board_msce_id),
('Compound Conjunctions', @chapter_grammar_id, @subject_english_fl_id, @class_7_id, @board_msce_id),
('Wh Questions', @chapter_grammar_id, @subject_english_fl_id, @class_7_id, @board_msce_id);

-- Chapter: Creative Writing
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES ('Creative Writing', NULL, @subject_english_fl_id, @class_7_id, @board_msce_id);
SET @chapter_creative_writing_id = (SELECT id FROM chapters WHERE chapter_name = 'Creative Writing' AND subject_id = @subject_english_fl_id AND class_id = @class_7_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@chapter_creative_writing_id, @class_7_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('Responding', @chapter_creative_writing_id, @subject_english_fl_id, @class_7_id, @board_msce_id),
('News', @chapter_creative_writing_id, @subject_english_fl_id, @class_7_id, @board_msce_id),
('Advertisement', @chapter_creative_writing_id, @subject_english_fl_id, @class_7_id, @board_msce_id),
('Emails', @chapter_creative_writing_id, @subject_english_fl_id, @class_7_id, @board_msce_id),
('Websites', @chapter_creative_writing_id, @subject_english_fl_id, @class_7_id, @board_msce_id),
('SMS', @chapter_creative_writing_id, @subject_english_fl_id, @class_7_id, @board_msce_id),
('Complete slogans', @chapter_creative_writing_id, @subject_english_fl_id, @class_7_id, @board_msce_id),
('Dialogue writing / Conversation', @chapter_creative_writing_id, @subject_english_fl_id, @class_7_id, @board_msce_id),
('Letter writing', @chapter_creative_writing_id, @subject_english_fl_id, @class_7_id, @board_msce_id),
('Diary / Play / Debate / Short Story Writing', @chapter_creative_writing_id, @subject_english_fl_id, @class_7_id, @board_msce_id),
('Report writing', @chapter_creative_writing_id, @subject_english_fl_id, @class_7_id, @board_msce_id),
('Write-up', @chapter_creative_writing_id, @subject_english_fl_id, @class_7_id, @board_msce_id),
('Quotations', @chapter_creative_writing_id, @subject_english_fl_id, @class_7_id, @board_msce_id);

-- Chapter: Reading Skills (Comprehension)
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES ('Reading Skills (Comprehension)', NULL, @subject_english_fl_id, @class_7_id, @board_msce_id);
SET @chapter_reading_skills_id = (SELECT id FROM chapters WHERE chapter_name = 'Reading Skills (Comprehension)' AND subject_id = @subject_english_fl_id AND class_id = @class_7_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@chapter_reading_skills_id, @class_7_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('Extracts', @chapter_reading_skills_id, @subject_english_fl_id, @class_7_id, @board_msce_id),
('Poem (two to three stanzas)', @chapter_reading_skills_id, @subject_english_fl_id, @class_7_id, @board_msce_id),
('Prose (70 to 80 words)', @chapter_reading_skills_id, @subject_english_fl_id, @class_7_id, @board_msce_id),
('News', @chapter_reading_skills_id, @subject_english_fl_id, @class_7_id, @board_msce_id),
('Dialogues', @chapter_reading_skills_id, @subject_english_fl_id, @class_7_id, @board_msce_id),
('Invitation', @chapter_reading_skills_id, @subject_english_fl_id, @class_7_id, @board_msce_id),
('Leaflet', @chapter_reading_skills_id, @subject_english_fl_id, @class_7_id, @board_msce_id),
('Write-up', @chapter_reading_skills_id, @subject_english_fl_id, @class_7_id, @board_msce_id),
('Notice', @chapter_reading_skills_id, @subject_english_fl_id, @class_7_id, @board_msce_id),
('Compering', @chapter_reading_skills_id, @subject_english_fl_id, @class_7_id, @board_msce_id);

-- Chapter: Miscellaneous (Loan Words)
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES ('Miscellaneous (Loan Words)', NULL, @subject_english_fl_id, @class_7_id, @board_msce_id);
SET @chapter_misc_loan_words_id = (SELECT id FROM chapters WHERE chapter_name = 'Miscellaneous (Loan Words)' AND subject_id = @subject_english_fl_id AND class_id = @class_7_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@chapter_misc_loan_words_id, @class_7_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('Loan Words', @chapter_misc_loan_words_id, @subject_english_fl_id, @class_7_id, @board_msce_id),
('Indian Words - Used in Text Books', @chapter_misc_loan_words_id, @subject_english_fl_id, @class_7_id, @board_msce_id),
('(Code Mixing - Non-English words)', @chapter_misc_loan_words_id, @subject_english_fl_id, @class_7_id, @board_msce_id);


-- Here is the SQL script to insert the syllabus data for "Math – English" for the 7th Standard, English Medium, based on the provided PDF document.




-- 3. Insert Subject 'Math – English' if not exists and get its ID
INSERT IGNORE INTO subject (subject_name) VALUES ('Math – English');
SET @subject_math_english_id = (SELECT subject_id FROM subject WHERE subject_name = 'Math – English' LIMIT 1);

-- 4. Map Subject to Board, Class, and Medium
INSERT IGNORE INTO subject_board_class_mapping (subject_id, class_id, board_id, medium) VALUES
(@subject_math_english_id, @class_7_id, @board_msce_id, 'English');

-- 5. Insert Chapters and Topics for "Math – English" (7th Std, English Medium)

-- Chapter: Number Work
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES ('Number Work', NULL, @subject_math_english_id, @class_7_id, @board_msce_id);
SET @chapter_number_work_id = (SELECT id FROM chapters WHERE chapter_name = 'Number Work' AND subject_id = @subject_math_english_id AND class_id = @class_7_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@chapter_number_work_id, @class_7_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('Even Numbers, Odd numbers, prime Numbers, twin prime numbers, co-prime numbers, composite numbers. Triangular numbers and Roman numerals', @chapter_number_work_id, @subject_math_english_id, @class_7_id, @board_msce_id),
('Natural numbers, whole numbers, Integers, Rational numbers', @chapter_number_work_id, @subject_math_english_id, @class_7_id, @board_msce_id),
('Rational numbers and operations on rational numbers', @chapter_number_work_id, @subject_math_english_id, @class_7_id, @board_msce_id),
('Number line', @chapter_number_work_id, @subject_math_english_id, @class_7_id, @board_msce_id);

-- Chapter: Operations on numbers
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES ('Operations on numbers', NULL, @subject_math_english_id, @class_7_id, @board_msce_id);
SET @chapter_operations_id = (SELECT id FROM chapters WHERE chapter_name = 'Operations on numbers' AND subject_id = @subject_math_english_id AND class_id = @class_7_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@chapter_operations_id, @class_7_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('Dividend, Divisor, Test of Divisibility', @chapter_operations_id, @subject_math_english_id, @class_7_id, @board_msce_id),
('Square and Square-Root, Cube and Cube-Root', @chapter_operations_id, @subject_math_english_id, @class_7_id, @board_msce_id),
('H.C.F. and L.C.M.', @chapter_operations_id, @subject_math_english_id, @class_7_id, @board_msce_id),
('Fractions – Vulgar fraction and Decimal fractions', @chapter_operations_id, @subject_math_english_id, @class_7_id, @board_msce_id),
('Indices', @chapter_operations_id, @subject_math_english_id, @class_7_id, @board_msce_id),
('Ratio, proportion and partnership (time-work-speed)', @chapter_operations_id, @subject_math_english_id, @class_7_id, @board_msce_id),
('Variation', @chapter_operations_id, @subject_math_english_id, @class_7_id, @board_msce_id),
('Time - Speed – Work', @chapter_operations_id, @subject_math_english_id, @class_7_id, @board_msce_id),
('Average', @chapter_operations_id, @subject_math_english_id, @class_7_id, @board_msce_id);

-- Chapter: Geometry
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES ('Geometry', NULL, @subject_math_english_id, @class_7_id, @board_msce_id);
SET @chapter_geometry_id = (SELECT id FROM chapters WHERE chapter_name = 'Geometry' AND subject_id = @subject_math_english_id AND class_id = @class_7_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@chapter_geometry_id, @class_7_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('Basic concept in Geometry - Point, segment, line, ray, angle (Straight angle, Zero angle, Complete angle, Reflex angle) plane, opposite angles, adjacent angles, supplementary angles, complimentary angles, angle bisector and perpendicular bisector and its properties. Concurrent lines, point of concurrent, interior angles of polygon', @chapter_geometry_id, @subject_math_english_id, @class_7_id, @board_msce_id),
('Properties of parallel lines. Adjacent angles, interior angels and alternate angles', @chapter_geometry_id, @subject_math_english_id, @class_7_id, @board_msce_id),
('Circle – Arc of a circle, Central angle, Measure of an arc', @chapter_geometry_id, @subject_math_english_id, @class_7_id, @board_msce_id),
('Properties of triangle', @chapter_geometry_id, @subject_math_english_id, @class_7_id, @board_msce_id),
('Properties of quadrilateral', @chapter_geometry_id, @subject_math_english_id, @class_7_id, @board_msce_id),
('Pythagoras Theorem', @chapter_geometry_id, @subject_math_english_id, @class_7_id, @board_msce_id),
('Three dimensional objects, triangular prism, rectangular prism, sphere. Net using surfaces, vertices and edges', @chapter_geometry_id, @subject_math_english_id, @class_7_id, @board_msce_id);

-- Chapter: Mensuration
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES ('Mensuration', NULL, @subject_math_english_id, @class_7_id, @board_msce_id);
SET @chapter_mensuration_id = (SELECT id FROM chapters WHERE chapter_name = 'Mensuration' AND subject_id = @subject_math_english_id AND class_id = @class_7_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@chapter_mensuration_id, @class_7_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('Dimensions - Length, Mass, Capacity, Measuring time, Coins and Currency Notes', @chapter_mensuration_id, @subject_math_english_id, @class_7_id, @board_msce_id),
('Perimeter - Triangle, Quadrilateral Polygon, Circle', @chapter_mensuration_id, @subject_math_english_id, @class_7_id, @board_msce_id),
('Area - Triangle, Square, Rectangle', @chapter_mensuration_id, @subject_math_english_id, @class_7_id, @board_msce_id),
('Surface area of Cuboid, Cube', @chapter_mensuration_id, @subject_math_english_id, @class_7_id, @board_msce_id);

-- Chapter: Statistics
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES ('Statistics', NULL, @subject_math_english_id, @class_7_id, @board_msce_id);
SET @chapter_statistics_id = (SELECT id FROM chapters WHERE chapter_name = 'Statistics' AND subject_id = @subject_math_english_id AND class_id = @class_7_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@chapter_statistics_id, @class_7_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('Mean, frequency distribution table', @chapter_statistics_id, @subject_math_english_id, @class_7_id, @board_msce_id),
('Bar graph, Joint bar graph', @chapter_statistics_id, @subject_math_english_id, @class_7_id, @board_msce_id);

-- Chapter: Applied Mathematics
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES ('Applied Mathematics', NULL, @subject_math_english_id, @class_7_id, @board_msce_id);
SET @chapter_applied_math_id = (SELECT id FROM chapters WHERE chapter_name = 'Applied Mathematics' AND subject_id = @subject_math_english_id AND class_id = @class_7_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@chapter_applied_math_id, @class_7_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('Percentage', @chapter_applied_math_id, @subject_math_english_id, @class_7_id, @board_msce_id),
('Simple interest and bank', @chapter_applied_math_id, @subject_math_english_id, @class_7_id, @board_msce_id),
('Profit - Loss', @chapter_applied_math_id, @subject_math_english_id, @class_7_id, @board_msce_id);

-- Chapter: Algebra
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES ('Algebra', NULL, @subject_math_english_id, @class_7_id, @board_msce_id);
SET @chapter_algebra_id = (SELECT id FROM chapters WHERE chapter_name = 'Algebra' AND subject_id = @subject_math_english_id AND class_id = @class_7_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@chapter_algebra_id, @class_7_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('Use of alphabet for numbers, factors of algebraic expressions. Value of the polynomial and operations on them', @chapter_algebra_id, @subject_math_english_id, @class_7_id, @board_msce_id),
('Identity, Algebraic formulae and expansion of squares', @chapter_algebra_id, @subject_math_english_id, @class_7_id, @board_msce_id),
('Equations in one variable and word problems based on it', @chapter_algebra_id, @subject_math_english_id, @class_7_id, @board_msce_id);



-- 3. Insert Subject 'Marathi – Third Language' if not exists and get its ID
INSERT IGNORE INTO subject (subject_name) VALUES ('Marathi – Third Language');
SET @subject_marathi_id = (SELECT subject_id FROM subject WHERE subject_name = 'Marathi – Third Language' LIMIT 1);

-- 4. Map Subject to Board, Class, and Medium (English Medium Context as requested)
INSERT IGNORE INTO subject_board_class_mapping (subject_id, class_id, board_id, medium) VALUES
(@subject_marathi_id, @class_7_id, @board_msce_id, 'English');

-- 5. Insert Chapters and Topics for "Marathi – Third Language" (7th Std, English Medium)

-- Chapter: आकलन (Comprehension)
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES ('आकलन', NULL, @subject_marathi_id, @class_7_id, @board_msce_id);
SET @chapter_aakalan_id = (SELECT id FROM chapters WHERE chapter_name = 'आकलन' AND subject_id = @subject_marathi_id AND class_id = @class_7_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@chapter_aakalan_id, @class_7_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('उतारा व त्यावर आधारित प्रश्न', @chapter_aakalan_id, @subject_marathi_id, @class_7_id, @board_msce_id),
('कविता व त्यावर आधारित प्रश्न', @chapter_aakalan_id, @subject_marathi_id, @class_7_id, @board_msce_id),
('सुसंगत वाक्यांचा परिच्छेद', @chapter_aakalan_id, @subject_marathi_id, @class_7_id, @board_msce_id),
('संवाद व त्यावर आधारित प्रश्न', @chapter_aakalan_id, @subject_marathi_id, @class_7_id, @board_msce_id);

-- Chapter: शब्दसंपत्ती (Vocabulary)
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES ('शब्दसंपत्ती', NULL, @subject_marathi_id, @class_7_id, @board_msce_id);
SET @chapter_shabdasampatti_id = (SELECT id FROM chapters WHERE chapter_name = 'शब्दसंपत्ती' AND subject_id = @subject_marathi_id AND class_id = @class_7_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@chapter_shabdasampatti_id, @class_7_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('समानार्थी शब्द', @chapter_shabdasampatti_id, @subject_marathi_id, @class_7_id, @board_msce_id),
('विरूध्दार्थी शब्द', @chapter_shabdasampatti_id, @subject_marathi_id, @class_7_id, @board_msce_id),
('शुध्द व अशुध्द शब्द', @chapter_shabdasampatti_id, @subject_marathi_id, @class_7_id, @board_msce_id),
('शब्दसमुहाबद्दल एक शब्द', @chapter_shabdasampatti_id, @subject_marathi_id, @class_7_id, @board_msce_id),
('वाक्प्रचार', @chapter_shabdasampatti_id, @subject_marathi_id, @class_7_id, @board_msce_id),
('म्हणी', @chapter_shabdasampatti_id, @subject_marathi_id, @class_7_id, @board_msce_id),
('जोडशब्द', @chapter_shabdasampatti_id, @subject_marathi_id, @class_7_id, @board_msce_id),
('एकाच शब्दाचे भिन्न अर्थ असणारे शब्द', @chapter_shabdasampatti_id, @subject_marathi_id, @class_7_id, @board_msce_id);

-- Chapter: कार्यात्मक व्याकरण (Functional Grammar)
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES ('कार्यात्मक व्याकरण', NULL, @subject_marathi_id, @class_7_id, @board_msce_id);
SET @chapter_vyakaran_id = (SELECT id FROM chapters WHERE chapter_name = 'कार्यात्मक व्याकरण' AND subject_id = @subject_marathi_id AND class_id = @class_7_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@chapter_vyakaran_id, @class_7_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('वर्ण विचार (स्वर, व्यंजने, स्वरादी)', @chapter_vyakaran_id, @subject_marathi_id, @class_7_id, @board_msce_id),
('शब्दांच्या जाती (विकारी व अविकारी)', @chapter_vyakaran_id, @subject_marathi_id, @class_7_id, @board_msce_id),
('लिंग', @chapter_vyakaran_id, @subject_marathi_id, @class_7_id, @board_msce_id),
('वचन', @chapter_vyakaran_id, @subject_marathi_id, @class_7_id, @board_msce_id),
('विरामचिन्हे', @chapter_vyakaran_id, @subject_marathi_id, @class_7_id, @board_msce_id),
('काळ', @chapter_vyakaran_id, @subject_marathi_id, @class_7_id, @board_msce_id);

-- Chapter: इ. 1 ली ते 7 वी मराठी विषयाशी संबंधित सामान्यज्ञान (General Knowledge related to Marathi subject for 1st to 7th Std)
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES ('इ. 1 ली ते 7 वी मराठी विषयाशी संबंधित सामान्यज्ञान', NULL, @subject_marathi_id, @class_7_id, @board_msce_id);
SET @chapter_gk_id = (SELECT id FROM chapters WHERE chapter_name = 'इ. 1 ली ते 7 वी मराठी विषयाशी संबंधित सामान्यज्ञान' AND subject_id = @subject_marathi_id AND class_id = @class_7_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@chapter_gk_id, @class_7_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('साहित्यिकांचे साहित्य व टोपणनावे', @chapter_gk_id, @subject_marathi_id, @class_7_id, @board_msce_id),
('साहित्यविषयक सामान्यज्ञान', @chapter_gk_id, @subject_marathi_id, @class_7_id, @board_msce_id);


-- 3. Insert Subject 'IQ – English' if not exists and get its ID
INSERT IGNORE INTO subject (subject_name) VALUES ('IQ – English');
SET @subject_iq_id = (SELECT subject_id FROM subject WHERE subject_name = 'IQ – English' LIMIT 1);

-- 4. Map Subject to Board, Class, and Medium (English Medium)
INSERT IGNORE INTO subject_board_class_mapping (subject_id, class_id, board_id, medium) VALUES
(@subject_iq_id, @class_7_id, @board_msce_id, 'English');

-- 5. Insert Chapters and Topics for "IQ – English" (7th Std, English Medium)

-- Chapter: Comprehension
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES ('Comprehension', NULL, @subject_iq_id, @class_7_id, @board_msce_id);
SET @chapter_comprehension_id = (SELECT id FROM chapters WHERE chapter_name = 'Comprehension' AND subject_id = @subject_iq_id AND class_id = @class_7_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@chapter_comprehension_id, @class_7_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('Follow the given instructions (Analyse the content, number)', @chapter_comprehension_id, @subject_iq_id, @class_7_id, @board_msce_id),
('English alphabet', @chapter_comprehension_id, @subject_iq_id, @class_7_id, @board_msce_id);

-- Chapter: Classification
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES ('Classification', NULL, @subject_iq_id, @class_7_id, @board_msce_id);
SET @chapter_classification_id = (SELECT id FROM chapters WHERE chapter_name = 'Classification' AND subject_id = @subject_iq_id AND class_id = @class_7_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@chapter_classification_id, @class_7_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('Vocabulary', @chapter_classification_id, @subject_iq_id, @class_7_id, @board_msce_id),
('Figures', @chapter_classification_id, @subject_iq_id, @class_7_id, @board_msce_id),
('Numbers', @chapter_classification_id, @subject_iq_id, @class_7_id, @board_msce_id),
('English alphabet', @chapter_classification_id, @subject_iq_id, @class_7_id, @board_msce_id);

-- Chapter: Co-relation
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES ('Co-relation', NULL, @subject_iq_id, @class_7_id, @board_msce_id);
SET @chapter_corelation_id = (SELECT id FROM chapters WHERE chapter_name = 'Co-relation' AND subject_id = @subject_iq_id AND class_id = @class_7_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@chapter_corelation_id, @class_7_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('Vocabulary', @chapter_corelation_id, @subject_iq_id, @class_7_id, @board_msce_id),
('Figures', @chapter_corelation_id, @subject_iq_id, @class_7_id, @board_msce_id),
('Numbers', @chapter_corelation_id, @subject_iq_id, @class_7_id, @board_msce_id),
('English alphabet', @chapter_corelation_id, @subject_iq_id, @class_7_id, @board_msce_id);

-- Chapter: Series (Order)
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES ('Series (Order)', NULL, @subject_iq_id, @class_7_id, @board_msce_id);
SET @chapter_series_order_id = (SELECT id FROM chapters WHERE chapter_name = 'Series (Order)' AND subject_id = @subject_iq_id AND class_id = @class_7_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@chapter_series_order_id, @class_7_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('Numbers', @chapter_series_order_id, @subject_iq_id, @class_7_id, @board_msce_id),
('Figures', @chapter_series_order_id, @subject_iq_id, @class_7_id, @board_msce_id),
('To find the odd term', @chapter_series_order_id, @subject_iq_id, @class_7_id, @board_msce_id),
('English alphabet', @chapter_series_order_id, @subject_iq_id, @class_7_id, @board_msce_id);

-- Chapter: Like terms
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES ('Like terms', NULL, @subject_iq_id, @class_7_id, @board_msce_id);
SET @chapter_like_terms_id = (SELECT id FROM chapters WHERE chapter_name = 'Like terms' AND subject_id = @subject_iq_id AND class_id = @class_7_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@chapter_like_terms_id, @class_7_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('Numbers', @chapter_like_terms_id, @subject_iq_id, @class_7_id, @board_msce_id),
('Words', @chapter_like_terms_id, @subject_iq_id, @class_7_id, @board_msce_id),
('Figures', @chapter_like_terms_id, @subject_iq_id, @class_7_id, @board_msce_id);

-- Chapter: Rhythm and Sequence
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES ('Rhythm and Sequence', NULL, @subject_iq_id, @class_7_id, @board_msce_id);
SET @chapter_rhythm_sequence_id = (SELECT id FROM chapters WHERE chapter_name = 'Rhythm and Sequence' AND subject_id = @subject_iq_id AND class_id = @class_7_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@chapter_rhythm_sequence_id, @class_7_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('Use of Numbers', @chapter_rhythm_sequence_id, @subject_iq_id, @class_7_id, @board_msce_id),
('Use of Letters', @chapter_rhythm_sequence_id, @subject_iq_id, @class_7_id, @board_msce_id),
('Use of Signs', @chapter_rhythm_sequence_id, @subject_iq_id, @class_7_id, @board_msce_id);

-- Chapter: Water image
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES ('Water image', NULL, @subject_iq_id, @class_7_id, @board_msce_id);
SET @chapter_water_image_id = (SELECT id FROM chapters WHERE chapter_name = 'Water image' AND subject_id = @subject_iq_id AND class_id = @class_7_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@chapter_water_image_id, @class_7_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('Numbers', @chapter_water_image_id, @subject_iq_id, @class_7_id, @board_msce_id),
('Letters', @chapter_water_image_id, @subject_iq_id, @class_7_id, @board_msce_id),
('Figures', @chapter_water_image_id, @subject_iq_id, @class_7_id, @board_msce_id);

-- Chapter: Mirror Image
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES ('Mirror Image', NULL, @subject_iq_id, @class_7_id, @board_msce_id);
SET @chapter_mirror_image_id = (SELECT id FROM chapters WHERE chapter_name = 'Mirror Image' AND subject_id = @subject_iq_id AND class_id = @class_7_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@chapter_mirror_image_id, @class_7_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('Numbers', @chapter_mirror_image_id, @subject_iq_id, @class_7_id, @board_msce_id),
('Letters', @chapter_mirror_image_id, @subject_iq_id, @class_7_id, @board_msce_id),
('Figures', @chapter_mirror_image_id, @subject_iq_id, @class_7_id, @board_msce_id);

-- Chapter: Logic and Conclusion
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES ('Logic and Conclusion', NULL, @subject_iq_id, @class_7_id, @board_msce_id);
SET @chapter_logic_conclusion_id = (SELECT id FROM chapters WHERE chapter_name = 'Logic and Conclusion' AND subject_id = @subject_iq_id AND class_id = @class_7_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@chapter_logic_conclusion_id, @class_7_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('Verbal- To draw conclusion using given information of Age, Time, Clock & Logical Relation', @chapter_logic_conclusion_id, @subject_iq_id, @class_7_id, @board_msce_id),
('Non-Verbal - To count Cube, Triangle and Quadrilateral', @chapter_logic_conclusion_id, @subject_iq_id, @class_7_id, @board_msce_id);

-- Chapter: Puzzles and Brain Teasers
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES ('Puzzles and Brain Teasers', NULL, @subject_iq_id, @class_7_id, @board_msce_id);
SET @chapter_puzzles_id = (SELECT id FROM chapters WHERE chapter_name = 'Puzzles and Brain Teasers' AND subject_id = @subject_iq_id AND class_id = @class_7_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@chapter_puzzles_id, @class_7_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('Position in a Queue', @chapter_puzzles_id, @subject_iq_id, @class_7_id, @board_msce_id),
('Problems based on Direction', @chapter_puzzles_id, @subject_iq_id, @class_7_id, @board_msce_id),
('Calendar', @chapter_puzzles_id, @subject_iq_id, @class_7_id, @board_msce_id),
('Venn Diagram', @chapter_puzzles_id, @subject_iq_id, @class_7_id, @board_msce_id),
('Mathematical Puzzles', @chapter_puzzles_id, @subject_iq_id, @class_7_id, @board_msce_id),
('To identify formulae in number series', @chapter_puzzles_id, @subject_iq_id, @class_7_id, @board_msce_id);

-- Chapter: Code Language
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES ('Code Language', NULL, @subject_iq_id, @class_7_id, @board_msce_id);
SET @chapter_code_language_id = (SELECT id FROM chapters WHERE chapter_name = 'Code Language' AND subject_id = @subject_iq_id AND class_id = @class_7_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@chapter_code_language_id, @class_7_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('Figures', @chapter_code_language_id, @subject_iq_id, @class_7_id, @board_msce_id),
('Numbers', @chapter_code_language_id, @subject_iq_id, @class_7_id, @board_msce_id),
('Letters', @chapter_code_language_id, @subject_iq_id, @class_7_id, @board_msce_id);

-- Chapter: Pyramids / Structures
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES ('Pyramids / Structures', NULL, @subject_iq_id, @class_7_id, @board_msce_id);
SET @chapter_pyramids_id = (SELECT id FROM chapters WHERE chapter_name = 'Pyramids / Structures' AND subject_id = @subject_iq_id AND class_id = @class_7_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@chapter_pyramids_id, @class_7_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('Numbers', @chapter_pyramids_id, @subject_iq_id, @class_7_id, @board_msce_id),
('Letters', @chapter_pyramids_id, @subject_iq_id, @class_7_id, @board_msce_id),
('Symbols', @chapter_pyramids_id, @subject_iq_id, @class_7_id, @board_msce_id);

-- Chapter: Analysis of Figure
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES ('Analysis of Figure', NULL, @subject_iq_id, @class_7_id, @board_msce_id);
SET @chapter_analysis_figure_id = (SELECT id FROM chapters WHERE chapter_name = 'Analysis of Figure' AND subject_id = @subject_iq_id AND class_id = @class_7_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@chapter_analysis_figure_id, @class_7_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('To complete the figure', @chapter_analysis_figure_id, @subject_iq_id, @class_7_id, @board_msce_id),
('Exact replica of the figure / identical figures', @chapter_analysis_figure_id, @subject_iq_id, @class_7_id, @board_msce_id),
('Figures - By folding and unfolding the paper', @chapter_analysis_figure_id, @subject_iq_id, @class_7_id, @board_msce_id),
('Find the hidden figure', @chapter_analysis_figure_id, @subject_iq_id, @class_7_id, @board_msce_id);

COMMIT;

SET FOREIGN_KEY_CHECKS = 1;

