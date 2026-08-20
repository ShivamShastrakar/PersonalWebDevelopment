--liquibase formatted sql
--changeset {narendra}:{id}

-- This script is designed for MySQL.
-- Ensure your database and tables are configured to use UTF8MB4 character set for full compatibility,
-- especially for Marathi or other non-English characters.

SET NAMES 'utf8mb4';
SET CHARACTER SET utf8mb4;

-- SQL script for PRE UPPER PRIMARY SCHOLARSHIP EXAMINATION syllabus (4th Grade, English Medium, MSCE Board)

-- 1. BOARD: MSCE
-- Inserts the board if it doesn't already exist.
INSERT IGNORE INTO board (board_name) VALUES ('MSCE');
-- Retrieves the ID of the 'MSCE' board for subsequent inserts.
SET @board_msce_id = (SELECT id FROM board WHERE board_name = 'MSCE' LIMIT 1);
SELECT @board_msce_id AS 'MSCE Board ID'; -- For verification

-- 2. CLASS: 4th
-- Inserts the class if it doesn't already exist.
INSERT IGNORE INTO class (class_name) VALUES ('4');
-- Retrieves the ID of the '4th' class.
SET @class_4th_id = (SELECT id FROM class WHERE class_name = '4' LIMIT 1);


-- 3. SUBJECTS
-- Inserts the specified subjects, as requested by the user, if they don't already exist.
INSERT IGNORE INTO subject (subject_name) VALUES
('English – First Language'),
('Math – English'),
('Marathi – Third Language'),
('IQ – English');

-- Retrieves the IDs of the newly inserted or existing subjects.
SET @subject_english_id = (SELECT subject_id FROM subject WHERE subject_name = 'English – First Language' LIMIT 1);
SET @subject_math_id = (SELECT subject_id FROM subject WHERE subject_name = 'Math – English' LIMIT 1);
SET @subject_marathi_id = (SELECT subject_id FROM subject WHERE subject_name = 'Marathi – Third Language' LIMIT 1);
SET @subject_iq_id = (SELECT subject_id FROM subject WHERE subject_name = 'IQ – English' LIMIT 1);


-- 4. SUBJECT_BOARD_CLASS_MAPPING
-- Maps all subjects to the 'MSCE' board and '4th' class with 'English' medium.
INSERT IGNORE INTO subject_board_class_mapping (subject_id, class_id, board_id, medium) VALUES
(@subject_english_id, @class_4th_id, @board_msce_id, 'English'),
(@subject_math_id, @class_4th_id, @board_msce_id, 'English'),
(@subject_marathi_id, @class_4th_id, @board_msce_id, 'English'),
(@subject_iq_id, @class_4th_id, @board_msce_id, 'English');


-- 5. SYLLABUS FOR 'English – First Language' (parsed from the attached PDF)

-- CHAPTER 1: Vocabulary (Unit 1 from PDF)
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES
('Vocabulary', '1', @subject_english_id, @class_4th_id, @board_msce_id);
SET @chapter_vocabulary_id = (SELECT id FROM chapters WHERE chapter_name = 'Vocabulary' AND subject_id = @subject_english_id AND class_id = @class_4th_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES
(@chapter_vocabulary_id, @class_4th_id, @board_msce_id);
-- TOPICS for Vocabulary
INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('Word formation', @chapter_vocabulary_id, @subject_english_id, @class_4th_id, @board_msce_id),
('Homophones', @chapter_vocabulary_id, @subject_english_id, @class_4th_id, @board_msce_id),
('Antonyms, Synonyms', @chapter_vocabulary_id, @subject_english_id, @class_4th_id, @board_msce_id),
('Compound words', @chapter_vocabulary_id, @subject_english_id, @class_4th_id, @board_msce_id),
('One word for many', @chapter_vocabulary_id, @subject_english_id, @class_4th_id, @board_msce_id),
('Names of young ones', @chapter_vocabulary_id, @subject_english_id, @class_4th_id, @board_msce_id),
('Professions', @chapter_vocabulary_id, @subject_english_id, @class_4th_id, @board_msce_id),
('Jumbled spellings', @chapter_vocabulary_id, @subject_english_id, @class_4th_id, @board_msce_id),
('Word puzzles', @chapter_vocabulary_id, @subject_english_id, @class_4th_id, @board_msce_id),
('Arrange in alphabetical order', @chapter_vocabulary_id, @subject_english_id, @class_4th_id, @board_msce_id),
('Words denoting different sounds', @chapter_vocabulary_id, @subject_english_id, @class_4th_id, @board_msce_id),
('Singular and plurals', @chapter_vocabulary_id, @subject_english_id, @class_4th_id, @board_msce_id),
('Prepare - Short words from long words', @chapter_vocabulary_id, @subject_english_id, @class_4th_id, @board_msce_id),
('Correctly spelt word', @chapter_vocabulary_id, @subject_english_id, @class_4th_id, @board_msce_id);


-- CHAPTER 2: Word Games (Unit 2 from PDF)
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES
('Word Games', '2', @subject_english_id, @class_4th_id, @board_msce_id);
SET @chapter_word_games_id = (SELECT id FROM chapters WHERE chapter_name = 'Word Games' AND subject_id = @subject_english_id AND class_id = @class_4th_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES
(@chapter_word_games_id, @class_4th_id, @board_msce_id);
-- TOPICS for Word Games
INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('Puzzles', @chapter_word_games_id, @subject_english_id, @class_4th_id, @board_msce_id),
('Word Register', @chapter_word_games_id, @subject_english_id, @class_4th_id, @board_msce_id),
('Related words', @chapter_word_games_id, @subject_english_id, @class_4th_id, @board_msce_id),
('Match the words and pictures', @chapter_word_games_id, @subject_english_id, @class_4th_id, @board_msce_id);

-- CHAPTER 3: Grammar (Unit 3 from PDF)
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES
('Grammar', '3', @subject_english_id, @class_4th_id, @board_msce_id);
SET @chapter_grammar_id = (SELECT id FROM chapters WHERE chapter_name = 'Grammar' AND subject_id = @subject_english_id AND class_id = @class_4th_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES
(@chapter_grammar_id, @class_4th_id, @board_msce_id);
-- TOPICS for Grammar
INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('Parts of speech', @chapter_grammar_id, @subject_english_id, @class_4th_id, @board_msce_id),
('Nouns-types: common, proper, collective, abstract', @chapter_grammar_id, @subject_english_id, @class_4th_id, @board_msce_id),
('Pronouns - personal pronouns', @chapter_grammar_id, @subject_english_id, @class_4th_id, @board_msce_id),
('Adjectives - degree of comparison', @chapter_grammar_id, @subject_english_id, @class_4th_id, @board_msce_id),
('Verbs - Conjugation', @chapter_grammar_id, @subject_english_id, @class_4th_id, @board_msce_id),
('Verbs - Action (main) verbs and auxiliary verb', @chapter_grammar_id, @subject_english_id, @class_4th_id, @board_msce_id),
('Adverbs', @chapter_grammar_id, @subject_english_id, @class_4th_id, @board_msce_id),
('Prepositions', @chapter_grammar_id, @subject_english_id, @class_4th_id, @board_msce_id),
('Conjunction', @chapter_grammar_id, @subject_english_id, @class_4th_id, @board_msce_id),
('Articles - Vowels', @chapter_grammar_id, @subject_english_id, @class_4th_id, @board_msce_id),
('Articles - Consonants', @chapter_grammar_id, @subject_english_id, @class_4th_id, @board_msce_id),
('Parts of a Sentence: Subject, Predicate', @chapter_grammar_id, @subject_english_id, @class_4th_id, @board_msce_id);

-- CHAPTER 4: Language Study (Unit 4 from PDF)
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES
('Language Study', '4', @subject_english_id, @class_4th_id, @board_msce_id);
SET @chapter_language_study_id = (SELECT id FROM chapters WHERE chapter_name = 'Language Study' AND subject_id = @subject_english_id AND class_id = @class_4th_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES
(@chapter_language_study_id, @class_4th_id, @board_msce_id);
-- TOPICS for Language Study
INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('Punctuation marks', @chapter_language_study_id, @subject_english_id, @class_4th_id, @board_msce_id),
('Contracted forms', @chapter_language_study_id, @subject_english_id, @class_4th_id, @board_msce_id),
('Expanded forms', @chapter_language_study_id, @subject_english_id, @class_4th_id, @board_msce_id),
('Idioms and Phrases', @chapter_language_study_id, @subject_english_id, @class_4th_id, @board_msce_id),
('Proverbs', @chapter_language_study_id, @subject_english_id, @class_4th_id, @board_msce_id),
('Follow instructions/ Road Signs', @chapter_language_study_id, @subject_english_id, @class_4th_id, @board_msce_id),
('Phrases', @chapter_language_study_id, @subject_english_id, @class_4th_id, @board_msce_id),
('Elements in story', @chapter_language_study_id, @subject_english_id, @class_4th_id, @board_msce_id),
('Tenses :- Present, Past, Future', @chapter_language_study_id, @subject_english_id, @class_4th_id, @board_msce_id),
('Make meaningful sentences', @chapter_language_study_id, @subject_english_id, @class_4th_id, @board_msce_id);

-- CHAPTER 5: Creative writing (Unit 5 from PDF)
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES
('Creative writing', '5', @subject_english_id, @class_4th_id, @board_msce_id);
SET @chapter_creative_writing_id = (SELECT id FROM chapters WHERE chapter_name = 'Creative writing' AND subject_id = @subject_english_id AND class_id = @class_4th_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES
(@chapter_creative_writing_id, @class_4th_id, @board_msce_id);
-- TOPICS for Creative writing
INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('Give titles, captions and headlines on news, stories, pictures and leaflet', @chapter_creative_writing_id, @subject_english_id, @class_4th_id, @board_msce_id),
('Paragraph writing, Stories, processes, events, experiments, speech, flow chart', @chapter_creative_writing_id, @subject_english_id, @class_4th_id, @board_msce_id),
('Auto-biography, short autobiography of a thing or object', @chapter_creative_writing_id, @subject_english_id, @class_4th_id, @board_msce_id),
('Informal letter, formal letter (format or complete the letter)', @chapter_creative_writing_id, @subject_english_id, @class_4th_id, @board_msce_id);

-- CHAPTER 6: Reading skills (comprehension) (Unit 6 from PDF)
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES
('Reading skills (comprehension)', '6', @subject_english_id, @class_4th_id, @board_msce_id);
SET @chapter_reading_skills_id = (SELECT id FROM chapters WHERE chapter_name = 'Reading skills (comprehension)' AND subject_id = @subject_english_id AND class_id = @class_4th_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES
(@chapter_reading_skills_id, @class_4th_id, @board_msce_id);
-- TOPICS for Reading skills (comprehension)
INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('Descriptive / Informative / Narrative / Imaginative Passage', @chapter_reading_skills_id, @subject_english_id, @class_4th_id, @board_msce_id),
('Leaflet', @chapter_reading_skills_id, @subject_english_id, @class_4th_id, @board_msce_id),
('Short skit/ Conversation', @chapter_reading_skills_id, @subject_english_id, @class_4th_id, @board_msce_id),
('Poem', @chapter_reading_skills_id, @subject_english_id, @class_4th_id, @board_msce_id);

-- CHAPTER 7: Miscellaneous (Unit 7 from PDF)
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES
('Miscellaneous', '7', @subject_english_id, @class_4th_id, @board_msce_id);
SET @chapter_miscellaneous_id = (SELECT id FROM chapters WHERE chapter_name = 'Miscellaneous' AND subject_id = @subject_english_id AND class_id = @class_4th_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES
(@chapter_miscellaneous_id, @class_4th_id, @board_msce_id);
-- TOPICS for Miscellaneous
INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('Numbers (cardinals and ordinals)', @chapter_miscellaneous_id, @subject_english_id, @class_4th_id, @board_msce_id),
('Non English words', @chapter_miscellaneous_id, @subject_english_id, @class_4th_id, @board_msce_id),
('Read maps', @chapter_miscellaneous_id, @subject_english_id, @class_4th_id, @board_msce_id),
('Charts', @chapter_miscellaneous_id, @subject_english_id, @class_4th_id, @board_msce_id),
('Stock expressions', @chapter_miscellaneous_id, @subject_english_id, @class_4th_id, @board_msce_id);




-- This script is designed for MySQL.
-- Ensure your database and tables are configured to use UTF8MB4 character set for full compatibility.

-- SQL script for PRE UPPER PRIMARY SCHOLARSHIP EXAMINATION syllabus (4th Grade, English Medium, MSCE Board)
-- This script focuses on the 'Math – English' subject based on the provided PDF.

-- 1. BOARD: MSCE
-- Inserts the board if it doesn't already exist.
INSERT IGNORE INTO board (board_name) VALUES ('MSCE');
-- Retrieves the ID of the 'MSCE' board for subsequent inserts.
SET @board_msce_id = (SELECT id FROM board WHERE board_name = 'MSCE' LIMIT 1);
SELECT @board_msce_id AS 'MSCE Board ID'; -- For verification

-- 2. CLASS: 4th
-- Inserts the class if it doesn't already exist.
INSERT IGNORE INTO class (class_name) VALUES ('4');
-- Retrieves the ID of the '4th' class.
SET @class_4th_id = (SELECT id FROM class WHERE class_name = '4' LIMIT 1);


-- 3. SUBJECTS
-- Inserts the specified subjects, as requested by the user, if they don't already exist.
INSERT IGNORE INTO subject (subject_name) VALUES
('English – First Language'),
('Math – English'),
('Marathi – Third Language'),
('IQ – English');

-- Retrieves the IDs of the newly inserted or existing subjects.
SET @subject_english_id = (SELECT subject_id FROM subject WHERE subject_name = 'English – First Language' LIMIT 1);
SET @subject_math_id = (SELECT subject_id FROM subject WHERE subject_name = 'Math – English' LIMIT 1);
SET @subject_marathi_id = (SELECT subject_id FROM subject WHERE subject_name = 'Marathi – Third Language' LIMIT 1);
SET @subject_iq_id = (SELECT subject_id FROM subject WHERE subject_name = 'IQ – English' LIMIT 1);

SELECT @subject_english_id AS 'English Subject ID',
       @subject_math_id AS 'Math Subject ID',
       @subject_marathi_id AS 'Marathi Subject ID',
       @subject_iq_id AS 'IQ Subject ID'; -- For verification

-- 4. SUBJECT_BOARD_CLASS_MAPPING
-- Maps all subjects to the 'MSCE' board and '4th' class with 'English' medium.
INSERT IGNORE INTO subject_board_class_mapping (subject_id, class_id, board_id, medium) VALUES
(@subject_english_id, @class_4th_id, @board_msce_id, 'English'),
(@subject_math_id, @class_4th_id, @board_msce_id, 'English'),
(@subject_marathi_id, @class_4th_id, @board_msce_id, 'English'),
(@subject_iq_id, @class_4th_id, @board_msce_id, 'English');


-- 5. SYLLABUS FOR 'Math – English' (parsed from the attached PDF)

-- CHAPTER 1: Knowledge of Numbers (Unit 1 from PDF)
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES
('Knowledge of Numbers', '1', @subject_math_id, @class_4th_id, @board_msce_id);
SET @chapter_knowledge_of_numbers_id = (SELECT id FROM chapters WHERE chapter_name = 'Knowledge of Numbers' AND subject_id = @subject_math_id AND class_id = @class_4th_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES
(@chapter_knowledge_of_numbers_id, @class_4th_id, @board_msce_id);
-- TOPICS for Knowledge of Numbers
INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('Reading and writing of International numerals', @chapter_knowledge_of_numbers_id, @subject_math_id, @class_4th_id, @board_msce_id),
('Reading and writing numbers up to five digits', @chapter_knowledge_of_numbers_id, @subject_math_id, @class_4th_id, @board_msce_id),
('Face value, place value of a digit and expanded form of a number', @chapter_knowledge_of_numbers_id, @subject_math_id, @class_4th_id, @board_msce_id),
('The smallest and greatest numbers from given digits', @chapter_knowledge_of_numbers_id, @subject_math_id, @class_4th_id, @board_msce_id),
('Ascending and descending order of numbers and comparison', @chapter_knowledge_of_numbers_id, @subject_math_id, @class_4th_id, @board_msce_id),
('Questions based on numbers from 1 to 100', @chapter_knowledge_of_numbers_id, @subject_math_id, @class_4th_id, @board_msce_id),
('Even and odd numbers', @chapter_knowledge_of_numbers_id, @subject_math_id, @class_4th_id, @board_msce_id),
('1 to 100, prime and composite numbers, triangular and square numbers', @chapter_knowledge_of_numbers_id, @subject_math_id, @class_4th_id, @board_msce_id);


-- CHAPTER 2: Operations on Numbers (Unit 2 from PDF)
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES
('Operations on Numbers', '2', @subject_math_id, @class_4th_id, @board_msce_id);
SET @chapter_operations_on_numbers_id = (SELECT id FROM chapters WHERE chapter_name = 'Operations on Numbers' AND subject_id = @subject_math_id AND class_id = @class_4th_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES
(@chapter_operations_on_numbers_id, @class_4th_id, @board_msce_id);
-- TOPICS for Operations on Numbers
INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('Addition (up to five digit numbers) with carrying, word problems', @chapter_operations_on_numbers_id, @subject_math_id, @class_4th_id, @board_msce_id),
('Subtraction (up to five digit numbers) by borrowing, word problems', @chapter_operations_on_numbers_id, @subject_math_id, @class_4th_id, @board_msce_id),
('Multiplication (up to three digit number by two digit number) word problems', @chapter_operations_on_numbers_id, @subject_math_id, @class_4th_id, @board_msce_id),
('Division (up to three digit number by two digit number) word problems', @chapter_operations_on_numbers_id, @subject_math_id, @class_4th_id, @board_msce_id);

-- CHAPTER 3: Fractions (Unit 3 from PDF)
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES
('Fractions', '3', @subject_math_id, @class_4th_id, @board_msce_id);
SET @chapter_fractions_id = (SELECT id FROM chapters WHERE chapter_name = 'Fractions' AND subject_id = @subject_math_id AND class_id = @class_4th_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES
(@chapter_fractions_id, @class_4th_id, @board_msce_id);
-- TOPICS for Fractions
INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('Reading, writing meaning of Fraction', @chapter_fractions_id, @subject_math_id, @class_4th_id, @board_msce_id),
('Fractions with equal denominator (like fractions)', @chapter_fractions_id, @subject_math_id, @class_4th_id, @board_msce_id),
('Fraction with unequal denominator (unlike fractions)', @chapter_fractions_id, @subject_math_id, @class_4th_id, @board_msce_id),
('Order relation (comparing Fractions), Ascending and Descending order', @chapter_fractions_id, @subject_math_id, @class_4th_id, @board_msce_id),
('Proper, Improper and Mixed fraction, their conversion an comparison', @chapter_fractions_id, @subject_math_id, @class_4th_id, @board_msce_id);

-- CHAPTER 4: Measurement / Mensuration (Unit 4 from PDF)
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES
('Measurement / Mensuration', '4', @subject_math_id, @class_4th_id, @board_msce_id);
SET @chapter_measurement_mensuration_id = (SELECT id FROM chapters WHERE chapter_name = 'Measurement / Mensuration' AND subject_id = @subject_math_id AND class_id = @class_4th_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES
(@chapter_measurement_mensuration_id, @class_4th_id, @board_msce_id);
-- TOPICS for Measurement / Mensuration
INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('Length, mass, capacity metric measures conversion of units, addition, subtraction and word problems', @chapter_measurement_mensuration_id, @subject_math_id, @class_4th_id, @board_msce_id),
('Measuring time ante meridiem and post meridiem. Hours, minutes and seconds-conversion', @chapter_measurement_mensuration_id, @subject_math_id, @class_4th_id, @board_msce_id),
('The Calendar', @chapter_measurement_mensuration_id, @subject_math_id, @class_4th_id, @board_msce_id),
('Rim, Gross (Paper measurement)', @chapter_measurement_mensuration_id, @subject_math_id, @class_4th_id, @board_msce_id),
('Coins and Currency Notes, Rupees-Paise Conversion. Word problems based on basic operations', @chapter_measurement_mensuration_id, @subject_math_id, @class_4th_id, @board_msce_id);

-- CHAPTER 5: Patterns (Unit 5 from PDF)
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES
('Patterns', '5', @subject_math_id, @class_4th_id, @board_msce_id);
SET @chapter_patterns_id = (SELECT id FROM chapters WHERE chapter_name = 'Patterns' AND subject_id = @subject_math_id AND class_id = @class_4th_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES
(@chapter_patterns_id, @class_4th_id, @board_msce_id);
-- TOPICS for Patterns
INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('Geometric Shapes', @chapter_patterns_id, @subject_math_id, @class_4th_id, @board_msce_id),
('Numbers', @chapter_patterns_id, @subject_math_id, @class_4th_id, @board_msce_id),
('Freehand shapes', @chapter_patterns_id, @subject_math_id, @class_4th_id, @board_msce_id);

-- CHAPTER 6: Geometry (Unit 6 from PDF)
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES
('Geometry', '6', @subject_math_id, @class_4th_id, @board_msce_id);
SET @chapter_geometry_id = (SELECT id FROM chapters WHERE chapter_name = 'Geometry' AND subject_id = @subject_math_id AND class_id = @class_4th_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES
(@chapter_geometry_id, @class_4th_id, @board_msce_id);
-- TOPICS for Geometry
INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('Angles - Types of Angles (Right angle, acute angles and obtuse angles)', @chapter_geometry_id, @subject_math_id, @class_4th_id, @board_msce_id),
('Symmetry', @chapter_geometry_id, @subject_math_id, @class_4th_id, @board_msce_id),
('Triangle, Square, Rectangle - sides and Vertices', @chapter_geometry_id, @subject_math_id, @class_4th_id, @board_msce_id),
('Circle-radius, chord, diameter, centre, circumference, the interior, the exterior', @chapter_geometry_id, @subject_math_id, @class_4th_id, @board_msce_id),
('Perimeter - Triangle, Rectangle, Square', @chapter_geometry_id, @subject_math_id, @class_4th_id, @board_msce_id),
('Area - Rectangle, Square', @chapter_geometry_id, @subject_math_id, @class_4th_id, @board_msce_id),
('Three dimensional objects and Nets', @chapter_geometry_id, @subject_math_id, @class_4th_id, @board_msce_id),
('Cone, Cylinder, Sphere. (edges, corners)', @chapter_geometry_id, @subject_math_id, @class_4th_id, @board_msce_id),
('Cube and Cuboid (Edges, Vertices, Faces)', @chapter_geometry_id, @subject_math_id, @class_4th_id, @board_msce_id);

-- CHAPTER 7: Pictographs (Unit 7 from PDF)
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES
('Pictographs', '7', @subject_math_id, @class_4th_id, @board_msce_id);
SET @chapter_pictographs_id = (SELECT id FROM chapters WHERE chapter_name = 'Pictographs' AND subject_id = @subject_math_id AND class_id = @class_4th_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES
(@chapter_pictographs_id, @class_4th_id, @board_msce_id);
-- TOPICS for Pictographs
INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('Comprehension on pictorial information', @chapter_pictographs_id, @subject_math_id, @class_4th_id, @board_msce_id);


-- This script is designed for MySQL.
-- Ensure your database and tables are configured to use UTF8MB4 character set for full compatibility,
-- especially for Marathi or other non-English characters.
SET NAMES 'utf8mb4';
SET CHARACTER SET utf8mb4;

-- SQL script for PRE UPPER PRIMARY SCHOLARSHIP EXAMINATION syllabus (4th Grade, English Medium, MSCE Board)
-- This script focuses on the 'Marathi – Third Language' subject based on the attached PDF.

-- 1. BOARD: MSCE
-- Inserts the board if it doesn't already exist.
INSERT IGNORE INTO board (board_name) VALUES ('MSCE');
-- Retrieves the ID of the 'MSCE' board for subsequent inserts.
SET @board_msce_id = (SELECT id FROM board WHERE board_name = 'MSCE' LIMIT 1);
SELECT @board_msce_id AS 'MSCE Board ID'; -- For verification

-- 2. CLASS: 4th
-- Inserts the class if it doesn't already exist.
INSERT IGNORE INTO class (class_name) VALUES ('4');
-- Retrieves the ID of the '4th' class.
SET @class_4th_id = (SELECT id FROM class WHERE class_name = '4' LIMIT 1);
SELECT @class_4th_id AS '4th Class ID'; -- For verification

-- 3. SUBJECTS
-- Inserts the specified subjects, as requested by the user, if they don't already exist.
INSERT IGNORE INTO subject (subject_name) VALUES
('English – First Language'),
('Math – English'),
('Marathi – Third Language'),
('IQ – English');

-- Retrieves the IDs of the newly inserted or existing subjects.
SET @subject_english_id = (SELECT subject_id FROM subject WHERE subject_name = 'English – First Language' LIMIT 1);
SET @subject_math_id = (SELECT subject_id FROM subject WHERE subject_name = 'Math – English' LIMIT 1);
SET @subject_marathi_id = (SELECT subject_id FROM subject WHERE subject_name = 'Marathi – Third Language' LIMIT 1);
SET @subject_iq_id = (SELECT subject_id FROM subject WHERE subject_name = 'IQ – English' LIMIT 1);

SELECT @subject_english_id AS 'English Subject ID',
       @subject_math_id AS 'Math Subject ID',
       @subject_marathi_id AS 'Marathi Subject ID',
       @subject_iq_id AS 'IQ Subject ID'; -- For verification

-- 4. SUBJECT_BOARD_CLASS_MAPPING
-- Maps all subjects to the 'MSCE' board and '4th' class with 'English' medium.
INSERT IGNORE INTO subject_board_class_mapping (subject_id, class_id, board_id, medium) VALUES
(@subject_english_id, @class_4th_id, @board_msce_id, 'English'),
(@subject_math_id, @class_4th_id, @board_msce_id, 'English'),
(@subject_marathi_id, @class_4th_id, @board_msce_id, 'English'),
(@subject_iq_id, @class_4th_id, @board_msce_id, 'English');


-- 5. SYLLABUS FOR 'Marathi – Third Language' (parsed from the attached PDF)

-- CHAPTER 1: आकलन (Comprehension) (Unit 1 from PDF)
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES
('आकलन', '1', @subject_marathi_id, @class_4th_id, @board_msce_id);
SET @chapter_akalan_id = (SELECT id FROM chapters WHERE chapter_name = 'आकलन' AND subject_id = @subject_marathi_id AND class_id = @class_4th_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES
(@chapter_akalan_id, @class_4th_id, @board_msce_id);
-- TOPICS for आकलन (Comprehension)
INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('उतारा व त्यावरील प्रश्न', @chapter_akalan_id, @subject_marathi_id, @class_4th_id, @board_msce_id), -- Passage and questions on it
('कविता व त्यावरील प्रश्न', @chapter_akalan_id, @subject_marathi_id, @class_4th_id, @board_msce_id), -- Poem and questions on it
('संवाद व त्यावरील प्रश्न', @chapter_akalan_id, @subject_marathi_id, @class_4th_id, @board_msce_id), -- Dialogue and questions on it
('सुसंगत वाक्यांचा परिच्छेद', @chapter_akalan_id, @subject_marathi_id, @class_4th_id, @board_msce_id); -- Paragraph of coherent sentences


-- CHAPTER 2: शब्दसंपत्ती (Vocabulary) (Unit 2 from PDF)
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES
('शब्दसंपत्ती', '2', @subject_marathi_id, @class_4th_id, @board_msce_id);
SET @chapter_shabdsampatti_id = (SELECT id FROM chapters WHERE chapter_name = 'शब्दसंपत्ती' AND subject_id = @subject_marathi_id AND class_id = @class_4th_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES
(@chapter_shabdsampatti_id, @class_4th_id, @board_msce_id);
-- TOPICS for शब्दसंपत्ती (Vocabulary)
INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('समानार्थी शब्द', @chapter_shabdsampatti_id, @subject_marathi_id, @class_4th_id, @board_msce_id), -- Synonyms
('विरुध्द अर्थाचे शब्द', @chapter_shabdsampatti_id, @subject_marathi_id, @class_4th_id, @board_msce_id), -- Antonyms
('शब्द समूहाबद्दल एक शब्द', @chapter_shabdsampatti_id, @subject_marathi_id, @class_4th_id, @board_msce_id), -- One word for a group of words
('ध्वनिदर्शक शब्द', @chapter_shabdsampatti_id, @subject_marathi_id, @class_4th_id, @board_msce_id), -- Onomatopoeic words (sound-indicating words)
('समूहदर्शक शब्द', @chapter_shabdsampatti_id, @subject_marathi_id, @class_4th_id, @board_msce_id), -- Collective nouns
('घरदर्शक शब्द', @chapter_shabdsampatti_id, @subject_marathi_id, @class_4th_id, @board_msce_id), -- Words indicating dwelling/home
('वाक्प्रचार व त्यांचे अर्थ', @chapter_shabdsampatti_id, @subject_marathi_id, @class_4th_id, @board_msce_id), -- Idioms and their meanings
('म्हणी व त्यांचे अर्थ', @chapter_shabdsampatti_id, @subject_marathi_id, @class_4th_id, @board_msce_id), -- Proverbs and their meanings
('एकाच शब्दाचे भिन्न अर्थ', @chapter_shabdsampatti_id, @subject_marathi_id, @class_4th_id, @board_msce_id), -- Different meanings of the same word
('जोडशब्द', @chapter_shabdsampatti_id, @subject_marathi_id, @class_4th_id, @board_msce_id), -- Compound words
('दिलेल्या अक्षरांपासून अर्थपूर्ण शब्द तयार करणे', @chapter_shabdsampatti_id, @subject_marathi_id, @class_4th_id, @board_msce_id); -- Forming meaningful words from given letters


-- CHAPTER 3: कार्यात्मक व्याकरण (Functional Grammar) (Unit 3 from PDF)
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES
('कार्यात्मक व्याकरण', '3', @subject_marathi_id, @class_4th_id, @board_msce_id);
SET @chapter_vyakaran_id = (SELECT id FROM chapters WHERE chapter_name = 'कार्यात्मक व्याकरण' AND subject_id = @subject_marathi_id AND class_id = @class_4th_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES
(@chapter_vyakaran_id, @class_4th_id, @board_msce_id);
-- TOPICS for कार्यात्मक व्याकरण (Functional Grammar)
INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('शब्दांच्या जाती - नाम व क्रियापद', @chapter_vyakaran_id, @subject_marathi_id, @class_4th_id, @board_msce_id), -- Parts of speech - Noun and Verb
('लिंग', @chapter_vyakaran_id, @subject_marathi_id, @class_4th_id, @board_msce_id), -- Gender
('वचन', @chapter_vyakaran_id, @subject_marathi_id, @class_4th_id, @board_msce_id), -- Number (singular/plural)
('विरामचिन्हे (पूर्णविराम, स्वल्पविराम, प्रश्नचिन्ह)', @chapter_vyakaran_id, @subject_marathi_id, @class_4th_id, @board_msce_id), -- Punctuation marks (Full stop, Comma, Question mark)
('शुध्द व अशुध्द शब्द', @chapter_vyakaran_id, @subject_marathi_id, @class_4th_id, @board_msce_id); -- Correct and incorrect words


-- CHAPTER 4: 1 ली ते 4 वी मराठी (सुलभभारती) विषयाशी संबंधित सामान्यज्ञान (General Knowledge related to 1st to 4th Marathi (Sulabhbharti) subject) (Unit 4 from PDF)
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES
('1 ली ते 4 वी मराठी (सुलभभारती) विषयाशी संबंधित सामान्यज्ञान', '4', @subject_marathi_id, @class_4th_id, @board_msce_id);
SET @chapter_samanyagnan_id = (SELECT id FROM chapters WHERE chapter_name = '1 ली ते 4 वी मराठी (सुलभभारती) विषयाशी संबंधित सामान्यज्ञान' AND subject_id = @subject_marathi_id AND class_id = @class_4th_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES
(@chapter_samanyagnan_id, @class_4th_id, @board_msce_id);
-- TOPICS for 1 ली ते 4 वी मराठी (सुलभभारती) विषयाशी संबंधित सामान्यज्ञान
INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('साहित्यिकांचे साहित्य व त्यांची टोपण नावे', @chapter_samanyagnan_id, @subject_marathi_id, @class_4th_id, @board_msce_id), -- Literature of authors and their pen names
('साहित्य विषयक सामान्यज्ञान', @chapter_samanyagnan_id, @subject_marathi_id, @class_4th_id, @board_msce_id); -- General knowledge about literature



-- This script is designed for MySQL.
-- Ensure your database and tables are configured to use UTF8MB4 character set for full compatibility.
SET NAMES 'utf8mb4';
SET CHARACTER SET utf8mb4;

-- SQL script for PRE UPPER PRIMARY SCHOLARSHIP EXAMINATION syllabus (4th Grade, English Medium, MSCE Board)
-- This script focuses on the 'IQ – English' subject based on the attached PDF.

-- 1. BOARD: MSCE
-- Inserts the board if it doesn't already exist.
INSERT IGNORE INTO board (board_name) VALUES ('MSCE');
-- Retrieves the ID of the 'MSCE' board for subsequent inserts.
SET @board_msce_id = (SELECT id FROM board WHERE board_name = 'MSCE' LIMIT 1);
SELECT @board_msce_id AS 'MSCE Board ID'; -- For verification

-- 2. CLASS: 4th
-- Inserts the class if it doesn't already exist.
INSERT IGNORE INTO class (class_name) VALUES ('4');
-- Retrieves the ID of the '4th' class.
SET @class_4th_id = (SELECT id FROM class WHERE class_name = '4' LIMIT 1);


-- 3. SUBJECTS
-- Inserts the specified subjects, as requested by the user, if they don't already exist.
INSERT IGNORE INTO subject (subject_name) VALUES
('English – First Language'),
('Math – English'),
('Marathi – Third Language'),
('IQ – English');

-- Retrieves the IDs of the newly inserted or existing subjects.
SET @subject_english_id = (SELECT subject_id FROM subject WHERE subject_name = 'English – First Language' LIMIT 1);
SET @subject_math_id = (SELECT subject_id FROM subject WHERE subject_name = 'Math – English' LIMIT 1);
SET @subject_marathi_id = (SELECT subject_id FROM subject WHERE subject_name = 'Marathi – Third Language' LIMIT 1);
SET @subject_iq_id = (SELECT subject_id FROM subject WHERE subject_name = 'IQ – English' LIMIT 1);

SELECT @subject_english_id AS 'English Subject ID',
       @subject_math_id AS 'Math Subject ID',
       @subject_marathi_id AS 'Marathi Subject ID',
       @subject_iq_id AS 'IQ Subject ID'; -- For verification

-- 4. SUBJECT_BOARD_CLASS_MAPPING
-- Maps all subjects to the 'MSCE' board and '4th' class with 'English' medium.
INSERT IGNORE INTO subject_board_class_mapping (subject_id, class_id, board_id, medium) VALUES
(@subject_english_id, @class_4th_id, @board_msce_id, 'English'),
(@subject_math_id, @class_4th_id, @board_msce_id, 'English'),
(@subject_marathi_id, @class_4th_id, @board_msce_id, 'English'),
(@subject_iq_id, @class_4th_id, @board_msce_id, 'English');


-- 5. SYLLABUS FOR 'IQ – English' (parsed from the attached PDF - Subject: बुध्दिमत्ता चाचणी)

-- CHAPTER 1: Comprehension (Unit 1 from PDF)
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES
('Comprehension', '1', @subject_iq_id, @class_4th_id, @board_msce_id);
SET @chapter_iq_comprehension_id = (SELECT id FROM chapters WHERE chapter_name = 'Comprehension' AND subject_id = @subject_iq_id AND class_id = @class_4th_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES
(@chapter_iq_comprehension_id, @class_4th_id, @board_msce_id);
-- TOPICS for Comprehension
INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('Do as directed - composite words, letters, words', @chapter_iq_comprehension_id, @subject_iq_id, @class_4th_id, @board_msce_id),
('Number series', @chapter_iq_comprehension_id, @subject_iq_id, @class_4th_id, @board_msce_id),
('Alphabet series', @chapter_iq_comprehension_id, @subject_iq_id, @class_4th_id, @board_msce_id);


-- CHAPTER 2: Classification (Unit 2 from PDF)
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES
('Classification', '2', @subject_iq_id, @class_4th_id, @board_msce_id);
SET @chapter_iq_classification_id = (SELECT id FROM chapters WHERE chapter_name = 'Classification' AND subject_id = @subject_iq_id AND class_id = @class_4th_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES
(@chapter_iq_classification_id, @class_4th_id, @board_msce_id);
-- TOPICS for Classification
INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('Vocabulary', @chapter_iq_classification_id, @subject_iq_id, @class_4th_id, @board_msce_id),
('Figures', @chapter_iq_classification_id, @subject_iq_id, @class_4th_id, @board_msce_id),
('Numbers', @chapter_iq_classification_id, @subject_iq_id, @class_4th_id, @board_msce_id),
('Alphabet series', @chapter_iq_classification_id, @subject_iq_id, @class_4th_id, @board_msce_id);


-- CHAPTER 3: Co-relation (Unit 3 from PDF)
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES
('Co-relation', '3', @subject_iq_id, @class_4th_id, @board_msce_id);
SET @chapter_iq_correlation_id = (SELECT id FROM chapters WHERE chapter_name = 'Co-relation' AND subject_id = @subject_iq_id AND class_id = @class_4th_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES
(@chapter_iq_correlation_id, @class_4th_id, @board_msce_id);
-- TOPICS for Co-relation
INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('Vocabulary', @chapter_iq_correlation_id, @subject_iq_id, @class_4th_id, @board_msce_id),
('Figures', @chapter_iq_correlation_id, @subject_iq_id, @class_4th_id, @board_msce_id),
('Numbers', @chapter_iq_correlation_id, @subject_iq_id, @class_4th_id, @board_msce_id),
('Alphabet series', @chapter_iq_correlation_id, @subject_iq_id, @class_4th_id, @board_msce_id);


-- CHAPTER 4: Number order (Unit 4 from PDF)
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES
('Number order', '4', @subject_iq_id, @class_4th_id, @board_msce_id);
SET @chapter_iq_number_order_id = (SELECT id FROM chapters WHERE chapter_name = 'Number order' AND subject_id = @subject_iq_id AND class_id = @class_4th_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES
(@chapter_iq_number_order_id, @class_4th_id, @board_msce_id);
-- TOPICS for Number order
INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('Number pattern (sequence)', @chapter_iq_number_order_id, @subject_iq_id, @class_4th_id, @board_msce_id),
('Figure pattern', @chapter_iq_number_order_id, @subject_iq_id, @class_4th_id, @board_msce_id),
('Symbols', @chapter_iq_number_order_id, @subject_iq_id, @class_4th_id, @board_msce_id),
('Odd man out', @chapter_iq_number_order_id, @subject_iq_id, @class_4th_id, @board_msce_id);


-- CHAPTER 5: Like Terms (Unit 5 from PDF)
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES
('Like Terms', '5', @subject_iq_id, @class_4th_id, @board_msce_id);
SET @chapter_iq_like_terms_id = (SELECT id FROM chapters WHERE chapter_name = 'Like Terms' AND subject_id = @subject_iq_id AND class_id = @class_4th_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES
(@chapter_iq_like_terms_id, @class_4th_id, @board_msce_id);
-- TOPICS for Like Terms
INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('Vocabulary', @chapter_iq_like_terms_id, @subject_iq_id, @class_4th_id, @board_msce_id),
('Figures', @chapter_iq_like_terms_id, @subject_iq_id, @class_4th_id, @board_msce_id),
('Numbers', @chapter_iq_like_terms_id, @subject_iq_id, @class_4th_id, @board_msce_id);


-- CHAPTER 6: Water Image (Unit 6 from PDF)
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES
('Water Image', '6', @subject_iq_id, @class_4th_id, @board_msce_id);
SET @chapter_iq_water_image_id = (SELECT id FROM chapters WHERE chapter_name = 'Water Image' AND subject_id = @subject_iq_id AND class_id = @class_4th_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES
(@chapter_iq_water_image_id, @class_4th_id, @board_msce_id);
-- TOPICS for Water Image
INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('Figures', @chapter_iq_water_image_id, @subject_iq_id, @class_4th_id, @board_msce_id),
('Numbers', @chapter_iq_water_image_id, @subject_iq_id, @class_4th_id, @board_msce_id),
('Alphabets', @chapter_iq_water_image_id, @subject_iq_id, @class_4th_id, @board_msce_id);


-- CHAPTER 7: Mirror Image (Unit 7 from PDF)
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES
('Mirror Image', '7', @subject_iq_id, @class_4th_id, @board_msce_id);
SET @chapter_iq_mirror_image_id = (SELECT id FROM chapters WHERE chapter_name = 'Mirror Image' AND subject_id = @subject_iq_id AND class_id = @class_4th_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES
(@chapter_iq_mirror_image_id, @class_4th_id, @board_msce_id);
-- TOPICS for Mirror Image
INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('Figures', @chapter_iq_mirror_image_id, @subject_iq_id, @class_4th_id, @board_msce_id),
('Numbers', @chapter_iq_mirror_image_id, @subject_iq_id, @class_4th_id, @board_msce_id),
('Alphabets', @chapter_iq_mirror_image_id, @subject_iq_id, @class_4th_id, @board_msce_id);


-- CHAPTER 8: Identifying Similarities (Unit 8 from PDF)
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES
('Identifying Similarities', '8', @subject_iq_id, @class_4th_id, @board_msce_id);
SET @chapter_iq_identifying_similarities_id = (SELECT id FROM chapters WHERE chapter_name = 'Identifying Similarities' AND subject_id = @subject_iq_id AND class_id = @class_4th_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES
(@chapter_iq_identifying_similarities_id, @class_4th_id, @board_msce_id);
-- TOPICS for Identifying Similarities
INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('Figures', @chapter_iq_identifying_similarities_id, @subject_iq_id, @class_4th_id, @board_msce_id);


-- CHAPTER 9: Logic And Inference (Unit 9 from PDF)
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES
('Logic And Inference', '9', @subject_iq_id, @class_4th_id, @board_msce_id);
SET @chapter_iq_logic_inference_id = (SELECT id FROM chapters WHERE chapter_name = 'Logic And Inference' AND subject_id = @subject_iq_id AND class_id = @class_4th_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES
(@chapter_iq_logic_inference_id, @class_4th_id, @board_msce_id);
-- TOPICS for Logic And Inference
INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('Verbal - Age, Comparison, Change in Name, Relations', @chapter_iq_logic_inference_id, @subject_iq_id, @class_4th_id, @board_msce_id),
('Non Verbal - To count the figures - Triangle, quadrilateral, square, rectangle, segment, angle, cube etc.', @chapter_iq_logic_inference_id, @subject_iq_id, @class_4th_id, @board_msce_id);


-- CHAPTER 10: Puzzles (Unit 10 from PDF)
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES
('Puzzles', '10', @subject_iq_id, @class_4th_id, @board_msce_id);
SET @chapter_iq_puzzles_id = (SELECT id FROM chapters WHERE chapter_name = 'Puzzles' AND subject_id = @subject_iq_id AND class_id = @class_4th_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES
(@chapter_iq_puzzles_id, @class_4th_id, @board_msce_id);
-- TOPICS for Puzzles
INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('Position in a queue', @chapter_iq_puzzles_id, @subject_iq_id, @class_4th_id, @board_msce_id),
('Direction', @chapter_iq_puzzles_id, @subject_iq_id, @class_4th_id, @board_msce_id),
('Calendar', @chapter_iq_puzzles_id, @subject_iq_id, @class_4th_id, @board_msce_id),
('Venn diagram', @chapter_iq_puzzles_id, @subject_iq_id, @class_4th_id, @board_msce_id),
('Numbers in square, circle and triangle', @chapter_iq_puzzles_id, @subject_iq_id, @class_4th_id, @board_msce_id);


-- CHAPTER 11: Symbolic Language (Symbol) (Unit 11 from PDF)
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES
('Symbolic Language (Symbol)', '11', @subject_iq_id, @class_4th_id, @board_msce_id);
SET @chapter_iq_symbolic_language_id = (SELECT id FROM chapters WHERE chapter_name = 'Symbolic Language (Symbol)' AND subject_id = @subject_iq_id AND class_id = @class_4th_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES
(@chapter_iq_symbolic_language_id, @class_4th_id, @board_msce_id);
-- TOPICS for Symbolic Language (Symbol)
INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('Use of symbols, numbers and words', @chapter_iq_symbolic_language_id, @subject_iq_id, @class_4th_id, @board_msce_id);


-- CHAPTER 12: Special Question Or Important (Unit 12 from PDF)
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES
('Special Question Or Important', '12', @subject_iq_id, @class_4th_id, @board_msce_id);
SET @chapter_iq_special_question_id = (SELECT id FROM chapters WHERE chapter_name = 'Special Question Or Important' AND subject_id = @subject_iq_id AND class_id = @class_4th_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES
(@chapter_iq_special_question_id, @class_4th_id, @board_msce_id);
-- TOPICS for Special Question Or Important
INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('Emotional intelligence, Social intelligences', @chapter_iq_special_question_id, @subject_iq_id, @class_4th_id, @board_msce_id);



