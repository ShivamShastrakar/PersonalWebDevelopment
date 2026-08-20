--liquibase formatted sql
--changeset {narendra}:{id}

-- MSCE PRE UPPER PRIMARY SCHOLARSHIP EXAMINATION - CLASS 5 Syllabus (English Medium)
-- Prerequisites: MSCE board exists, SET NAMES utf8mb4;

-- This script is designed to be run in a MySQL environment.
-- Please replace 'your_database_name' with the actual name of your database.
-- Ensure your database and tables are configured to support UTF-8 (e.g., utf8mb4 character set and collation).

-- USE your_database_name;

-- Set the character set for the current connection to support UTF-8, especially for future Marathi content.
SET NAMES 'utf8mb4';

-- Define variables for Board and Class IDs.
-- These assume that 'MSCE' board and '5' class already exist in your 'board' and 'class' tables.
-- If they don't exist, you would need to insert them first or handle their creation.
SET @board_msce_id = (SELECT id FROM board WHERE board_name = 'MSCE' LIMIT 1);
SET @class_5th_id = (SELECT id FROM class WHERE class_name = '5' LIMIT 1);


-- Check if IDs were successfully retrieved
SELECT 'Board ID for MSCE:' AS Info, @board_msce_id AS Value;
SELECT 'Class ID for 5:' AS Info, @class_5th_id AS Value;

-- 1. Insert Subjects as requested for 5th Std - English Medium
-- Using INSERT IGNORE to prevent duplicate entries if the script is run multiple times.
INSERT IGNORE INTO subject (subject_name) VALUES
('English – First Language'),
('Math – English'),
('Marathi – Third Language'),
('IQ – English');

-- Retrieve the subject IDs for further mappings
SET @subject_english_first_lang_id = (SELECT subject_id FROM subject WHERE subject_name = 'English – First Language' LIMIT 1);
SET @subject_math_english_id = (SELECT subject_id FROM subject WHERE subject_name = 'Math – English' LIMIT 1);
SET @subject_marathi_third_lang_id = (SELECT subject_id FROM subject WHERE subject_name = 'Marathi – Third Language' LIMIT 1);
SET @subject_iq_english_id = (SELECT subject_id FROM subject WHERE subject_name = 'IQ – English' LIMIT 1);

-- 2. Insert into subject_board_class_mapping for 5th Std - English Medium
-- Using INSERT IGNORE to prevent duplicate entries.
INSERT IGNORE INTO subject_board_class_mapping (subject_id, class_id, board_id, medium) VALUES
(@subject_english_first_lang_id, @class_5th_id, @board_msce_id, 'English'),
(@subject_math_english_id, @class_5th_id, @board_msce_id, 'English'),
(@subject_marathi_third_lang_id, @class_5th_id, @board_msce_id, 'English'),
(@subject_iq_english_id, @class_5th_id, @board_msce_id, 'English');


-- 3. Insert Chapters and Topics for "English – First Language" based on the attached PDF

-- Chapter: Vocabulary (Unit 1)
INSERT INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES
('Vocabulary', 'Vocabulary', @subject_english_first_lang_id, @class_5th_id, @board_msce_id);
SET @chapter_vocabulary_id = LAST_INSERT_ID();

INSERT INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES
(@chapter_vocabulary_id, @class_5th_id, @board_msce_id);

INSERT INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('Word formation', @chapter_vocabulary_id, @subject_english_first_lang_id, @class_5th_id, @board_msce_id),
('Homophones', @chapter_vocabulary_id, @subject_english_first_lang_id, @class_5th_id, @board_msce_id),
('Prefix- Suffix', @chapter_vocabulary_id, @subject_english_first_lang_id, @class_5th_id, @board_msce_id),
('Antonyms, Synonyms', @chapter_vocabulary_id, @subject_english_first_lang_id, @class_5th_id, @board_msce_id),
('Compound words', @chapter_vocabulary_id, @subject_english_first_lang_id, @class_5th_id, @board_msce_id),
('One word for many', @chapter_vocabulary_id, @subject_english_first_lang_id, @class_5th_id, @board_msce_id),
('Figurative words', @chapter_vocabulary_id, @subject_english_first_lang_id, @class_5th_id, @board_msce_id),
('Names of young ones', @chapter_vocabulary_id, @subject_english_first_lang_id, @class_5th_id, @board_msce_id),
('Professions', @chapter_vocabulary_id, @subject_english_first_lang_id, @class_5th_id, @board_msce_id),
('Jumbled spellings', @chapter_vocabulary_id, @subject_english_first_lang_id, @class_5th_id, @board_msce_id),
('Word puzzles', @chapter_vocabulary_id, @subject_english_first_lang_id, @class_5th_id, @board_msce_id),
('Arrange in alphabetical order', @chapter_vocabulary_id, @subject_english_first_lang_id, @class_5th_id, @board_msce_id),
('Words denoting different sounds', @chapter_vocabulary_id, @subject_english_first_lang_id, @class_5th_id, @board_msce_id),
('Singular and plurals', @chapter_vocabulary_id, @subject_english_first_lang_id, @class_5th_id, @board_msce_id),
('Prepare- Short words from long words', @chapter_vocabulary_id, @subject_english_first_lang_id, @class_5th_id, @board_msce_id),
('Correctly spelt word', @chapter_vocabulary_id, @subject_english_first_lang_id, @class_5th_id, @board_msce_id);

-- Chapter: Word Games (Unit 2)
INSERT INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES
('Word Games', 'Word Games', @subject_english_first_lang_id, @class_5th_id, @board_msce_id);
SET @chapter_word_games_id = LAST_INSERT_ID();

INSERT INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES
(@chapter_word_games_id, @class_5th_id, @board_msce_id);

INSERT INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('Puzzles', @chapter_word_games_id, @subject_english_first_lang_id, @class_5th_id, @board_msce_id),
('Word Register', @chapter_word_games_id, @subject_english_first_lang_id, @class_5th_id, @board_msce_id),
('Related words', @chapter_word_games_id, @subject_english_first_lang_id, @class_5th_id, @board_msce_id),
('Match the words and pictures', @chapter_word_games_id, @subject_english_first_lang_id, @class_5th_id, @board_msce_id);

-- Chapter: Grammar (Unit 3)
INSERT INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES
('Grammar', 'Grammar', @subject_english_first_lang_id, @class_5th_id, @board_msce_id);
SET @chapter_grammar_id = LAST_INSERT_ID();

INSERT INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES
(@chapter_grammar_id, @class_5th_id, @board_msce_id);

INSERT INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('Parts of speech: Nouns-types (common, proper, collective, abstract)', @chapter_grammar_id, @subject_english_first_lang_id, @class_5th_id, @board_msce_id),
('Parts of speech: Pronouns-personal pronouns', @chapter_grammar_id, @subject_english_first_lang_id, @class_5th_id, @board_msce_id),
('Parts of speech: Adjectives- degree of comparison', @chapter_grammar_id, @subject_english_first_lang_id, @class_5th_id, @board_msce_id),
('Parts of speech: Verbs- Conjugation', @chapter_grammar_id, @subject_english_first_lang_id, @class_5th_id, @board_msce_id),
('Parts of speech: Verbs- Action (main) verbs and auxiliary verb', @chapter_grammar_id, @subject_english_first_lang_id, @class_5th_id, @board_msce_id),
('Parts of speech: Adverbs', @chapter_grammar_id, @subject_english_first_lang_id, @class_5th_id, @board_msce_id),
('Parts of speech: Prepositions', @chapter_grammar_id, @subject_english_first_lang_id, @class_5th_id, @board_msce_id),
('Parts of speech: Conjunction', @chapter_grammar_id, @subject_english_first_lang_id, @class_5th_id, @board_msce_id),
('Articles: Vowels', @chapter_grammar_id, @subject_english_first_lang_id, @class_5th_id, @board_msce_id),
('Articles: Consonants', @chapter_grammar_id, @subject_english_first_lang_id, @class_5th_id, @board_msce_id),
('Parts of a Sentence Subject, Predicate', @chapter_grammar_id, @subject_english_first_lang_id, @class_5th_id, @board_msce_id),
('Wh Questions', @chapter_grammar_id, @subject_english_first_lang_id, @class_5th_id, @board_msce_id),
('Verbal Questions', @chapter_grammar_id, @subject_english_first_lang_id, @class_5th_id, @board_msce_id);

-- Chapter: Language Study (Unit 4)
INSERT INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES
('Language Study', 'Language Study', @subject_english_first_lang_id, @class_5th_id, @board_msce_id);
SET @chapter_language_study_id = LAST_INSERT_ID();

INSERT INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES
(@chapter_language_study_id, @class_5th_id, @board_msce_id);

INSERT INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('Punctuation marks', @chapter_language_study_id, @subject_english_first_lang_id, @class_5th_id, @board_msce_id),
('Contracted forms', @chapter_language_study_id, @subject_english_first_lang_id, @class_5th_id, @board_msce_id),
('Expanded forms', @chapter_language_study_id, @subject_english_first_lang_id, @class_5th_id, @board_msce_id),
('Idioms and Phrases', @chapter_language_study_id, @subject_english_first_lang_id, @class_5th_id, @board_msce_id),
('Proverbs, Slogans, Axioms', @chapter_language_study_id, @subject_english_first_lang_id, @class_5th_id, @board_msce_id),
('Follow instructions/ Road Signs', @chapter_language_study_id, @subject_english_first_lang_id, @class_5th_id, @board_msce_id),
('Phrases', @chapter_language_study_id, @subject_english_first_lang_id, @class_5th_id, @board_msce_id),
('Elements in story', @chapter_language_study_id, @subject_english_first_lang_id, @class_5th_id, @board_msce_id),
('Tenses: Present, Past, Future', @chapter_language_study_id, @subject_english_first_lang_id, @class_5th_id, @board_msce_id),
('Make meaningful sentences', @chapter_language_study_id, @subject_english_first_lang_id, @class_5th_id, @board_msce_id);

-- Chapter: Creative writing (Unit 5)
INSERT INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES
('Creative writing', 'Creative writing', @subject_english_first_lang_id, @class_5th_id, @board_msce_id);
SET @chapter_creative_writing_id = LAST_INSERT_ID();

INSERT INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES
(@chapter_creative_writing_id, @class_5th_id, @board_msce_id);

INSERT INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('Give titles, captions and headlines on news, stories, pictures and cartoons, leaflet', @chapter_creative_writing_id, @subject_english_first_lang_id, @class_5th_id, @board_msce_id),
('Paragraph writing, Stories, processes, events, experiments', @chapter_creative_writing_id, @subject_english_first_lang_id, @class_5th_id, @board_msce_id),
('Auto-biography, short autobiography of a thing or object', @chapter_creative_writing_id, @subject_english_first_lang_id, @class_5th_id, @board_msce_id),
('Informal letter (format or complete the letter)', @chapter_creative_writing_id, @subject_english_first_lang_id, @class_5th_id, @board_msce_id);

-- Chapter: Reading skills (comprehension) (Unit 6)
INSERT INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES
('Reading skills (comprehension)', 'Reading skills (comprehension)', @subject_english_first_lang_id, @class_5th_id, @board_msce_id);
SET @chapter_reading_skills_id = LAST_INSERT_ID();

INSERT INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES
(@chapter_reading_skills_id, @class_5th_id, @board_msce_id);

INSERT INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('Descriptive/ Informative/Narrative/ Imaginative Passage', @chapter_reading_skills_id, @subject_english_first_lang_id, @class_5th_id, @board_msce_id),
('News items/ Advertisement/ Leaflet', @chapter_reading_skills_id, @subject_english_first_lang_id, @class_5th_id, @board_msce_id),
('Short skit/ Interview', @chapter_reading_skills_id, @subject_english_first_lang_id, @class_5th_id, @board_msce_id),
('Poem', @chapter_reading_skills_id, @subject_english_first_lang_id, @class_5th_id, @board_msce_id);

-- Chapter: Miscellaneous (Unit 7)
INSERT INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES
('Miscellaneous', 'Miscellaneous', @subject_english_first_lang_id, @class_5th_id, @board_msce_id);
SET @chapter_miscellaneous_id = LAST_INSERT_ID();

INSERT INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES
(@chapter_miscellaneous_id, @class_5th_id, @board_msce_id);

INSERT INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('Numbers (cardinals and ordinals)', @chapter_miscellaneous_id, @subject_english_first_lang_id, @class_5th_id, @board_msce_id),
('Non English words', @chapter_miscellaneous_id, @subject_english_first_lang_id, @class_5th_id, @board_msce_id),
('Read maps', @chapter_miscellaneous_id, @subject_english_first_lang_id, @class_5th_id, @board_msce_id),
('Charts', @chapter_miscellaneous_id, @subject_english_first_lang_id, @class_5th_id, @board_msce_id),
('Stock expressions', @chapter_miscellaneous_id, @subject_english_first_lang_id, @class_5th_id, @board_msce_id),
('Abbreviations', @chapter_miscellaneous_id, @subject_english_first_lang_id, @class_5th_id, @board_msce_id);



SET FOREIGN_KEY_CHECKS = 0;



START TRANSACTION;


SET @subject_english_id = (SELECT subject_id FROM subject WHERE subject_name = 'English – First Language' LIMIT 1);

INSERT IGNORE INTO subject (subject_name) VALUES ('Math – English');
SET @subject_math_english_id = (SELECT subject_id FROM subject WHERE subject_name = 'Math – English' LIMIT 1);

INSERT IGNORE INTO subject (subject_name) VALUES ('Marathi – Third Language');
SET @subject_marathi_id = (SELECT subject_id FROM subject WHERE subject_name = 'Marathi – Third Language' LIMIT 1);

INSERT IGNORE INTO subject (subject_name) VALUES ('IQ – English');
SET @subject_iq_id = (SELECT subject_id FROM subject WHERE subject_name = 'IQ – English' LIMIT 1);



-- Insert Chapters and Topics for "Math – English" based on the PDF

-- Chapter: Number Work
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES ('Number Work', NULL, @subject_math_english_id, @class_5_id, @board_msce_id);
SET @chapter_number_work_id = (SELECT id FROM chapters WHERE chapter_name = 'Number Work' AND subject_id = @subject_math_english_id AND class_id = @class_5_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@chapter_number_work_id, @class_5_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('International numerals and roman numerals', @chapter_number_work_id, @subject_math_english_id, @class_5_id, @board_msce_id),
('Reading and writing numbers up to ten digits', @chapter_number_work_id, @subject_math_english_id, @class_5_id, @board_msce_id),
('Face value, place value of a digit and expanded form of a number', @chapter_number_work_id, @subject_math_english_id, @class_5_id, @board_msce_id),
('Making the smallest and greatest numbers from given digits', @chapter_number_work_id, @subject_math_english_id, @class_5_id, @board_msce_id),
('Ascending and descending order of numbers and comparison', @chapter_number_work_id, @subject_math_english_id, @class_5_id, @board_msce_id),
('Questions based on numbers from 1 to 100', @chapter_number_work_id, @subject_math_english_id, @class_5_id, @board_msce_id),
('Even and odd numbers, prime and composite numbers, twin prime, co-prime numbers, triangular and square numbers', @chapter_number_work_id, @subject_math_english_id, @class_5_id, @board_msce_id);

-- Chapter: Operations On Numbers
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES ('Operations On Numbers', NULL, @subject_math_english_id, @class_5_id, @board_msce_id);
SET @chapter_operations_id = (SELECT id FROM chapters WHERE chapter_name = 'Operations On Numbers' AND subject_id = @subject_math_english_id AND class_id = @class_5_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@chapter_operations_id, @class_5_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('Addition (up to seven digit numbers) with carrying, word problems', @chapter_operations_id, @subject_math_english_id, @class_5_id, @board_msce_id),
('Subtraction (up to seven digit numbers) by borrowing, word problems', @chapter_operations_id, @subject_math_english_id, @class_5_id, @board_msce_id),
('Multiplication (up to five digit number by three digit number)', @chapter_operations_id, @subject_math_english_id, @class_5_id, @board_msce_id),
('Division (up to five digit number by two digit number)', @chapter_operations_id, @subject_math_english_id, @class_5_id, @board_msce_id),
('Word problems on multiplication and division', @chapter_operations_id, @subject_math_english_id, @class_5_id, @board_msce_id),
('Expression and the use of letters in place of numbers', @chapter_operations_id, @subject_math_english_id, @class_5_id, @board_msce_id),
('Test of divisibility (1 to 10) and factors and multiples of numbers', @chapter_operations_id, @subject_math_english_id, @class_5_id, @board_msce_id);

-- Chapter: Fractions
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES ('Fractions', NULL, @subject_math_english_id, @class_5_id, @board_msce_id);
SET @chapter_fractions_id = (SELECT id FROM chapters WHERE chapter_name = 'Fractions' AND subject_id = @subject_math_english_id AND class_id = @class_5_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@chapter_fractions_id, @class_5_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('Vulgar fractions', @chapter_fractions_id, @subject_math_english_id, @class_5_id, @board_msce_id),
('Fractions with equal denominator (like fractions)', @chapter_fractions_id, @subject_math_english_id, @class_5_id, @board_msce_id),
('Fractions with unequal denominator (unlike fractions)', @chapter_fractions_id, @subject_math_english_id, @class_5_id, @board_msce_id),
('Order relation (comparing Fractions)', @chapter_fractions_id, @subject_math_english_id, @class_5_id, @board_msce_id),
('Ascending and Descending order of fractions', @chapter_fractions_id, @subject_math_english_id, @class_5_id, @board_msce_id),
('Addition, Subtraction and Multiplication of fractions', @chapter_fractions_id, @subject_math_english_id, @class_5_id, @board_msce_id),
('Proper, Improper and Mixed fraction, their conversion', @chapter_fractions_id, @subject_math_english_id, @class_5_id, @board_msce_id),
('Equivalent fractions', @chapter_fractions_id, @subject_math_english_id, @class_5_id, @board_msce_id),
('Decimal fractions', @chapter_fractions_id, @subject_math_english_id, @class_5_id, @board_msce_id),
('Decimal fractions: Reading and writing', @chapter_fractions_id, @subject_math_english_id, @class_5_id, @board_msce_id),
('Decimal fractions: Place value of digits in decimal fractions, use of decimal fractions', @chapter_fractions_id, @subject_math_english_id, @class_5_id, @board_msce_id),
('Decimal fractions: Addition and Subtraction of decimal fractions', @chapter_fractions_id, @subject_math_english_id, @class_5_id, @board_msce_id);

-- Chapter: Measurement / Mensuration
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES ('Measurement / Mensuration', NULL, @subject_math_english_id, @class_5_id, @board_msce_id);
SET @chapter_measurement_id = (SELECT id FROM chapters WHERE chapter_name = 'Measurement / Mensuration' AND subject_id = @subject_math_english_id AND class_id = @class_5_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@chapter_measurement_id, @class_5_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('Length, mass, capacity metric measures conversion of units, addition, subtraction and word problems', @chapter_measurement_id, @subject_math_english_id, @class_5_id, @board_msce_id),
('Measuring time ante meridiem and post meridiem. Hours, minutes and seconds- conversion. Addition, Subtraction and Word Problems', @chapter_measurement_id, @subject_math_english_id, @class_5_id, @board_msce_id);

-- Chapter: Applied Mathematics
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES ('Applied Mathematics', NULL, @subject_math_english_id, @class_5_id, @board_msce_id);
SET @chapter_applied_math_id = (SELECT id FROM chapters WHERE chapter_name = 'Applied Mathematics' AND subject_id = @subject_math_english_id AND class_id = @class_5_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@chapter_applied_math_id, @class_5_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('The Calendar', @chapter_applied_math_id, @subject_math_english_id, @class_5_id, @board_msce_id),
('Rim, Gross (Paper measurement)', @chapter_applied_math_id, @subject_math_english_id, @class_5_id, @board_msce_id),
('Coins and Currency Notes, Rupees-Paise Conversion', @chapter_applied_math_id, @subject_math_english_id, @class_5_id, @board_msce_id),
('Basic concepts of selling and purchasing of articles', @chapter_applied_math_id, @subject_math_english_id, @class_5_id, @board_msce_id),
('Profit - loss percentage', @chapter_applied_math_id, @subject_math_english_id, @class_5_id, @board_msce_id),
('Simple interest (problems based on basic information)', @chapter_applied_math_id, @subject_math_english_id, @class_5_id, @board_msce_id);

-- Chapter: Geometry
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES ('Geometry', NULL, @subject_math_english_id, @class_5_id, @board_msce_id);
SET @chapter_geometry_id = (SELECT id FROM chapters WHERE chapter_name = 'Geometry' AND subject_id = @subject_math_english_id AND class_id = @class_5_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@chapter_geometry_id, @class_5_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('Angles (Types of Angles)', @chapter_geometry_id, @subject_math_english_id, @class_5_id, @board_msce_id),
('Parallel and Perpendicular lines', @chapter_geometry_id, @subject_math_english_id, @class_5_id, @board_msce_id),
('Triangle, Square-sides and Vertices', @chapter_geometry_id, @subject_math_english_id, @class_5_id, @board_msce_id),
('Circle-radius, chord, diameter, centre, circumference, the interior, the exterior, arc of circle', @chapter_geometry_id, @subject_math_english_id, @class_5_id, @board_msce_id),
('Perimeter- Triangle, Rectangle, Square, Polygon', @chapter_geometry_id, @subject_math_english_id, @class_5_id, @board_msce_id),
('Area- Rectangle, Square', @chapter_geometry_id, @subject_math_english_id, @class_5_id, @board_msce_id),
('Three dimensional objects and Nets', @chapter_geometry_id, @subject_math_english_id, @class_5_id, @board_msce_id),
('Patterns', @chapter_geometry_id, @subject_math_english_id, @class_5_id, @board_msce_id),
('Cube and Cuboid (Edges, Vertices, Faces)', @chapter_geometry_id, @subject_math_english_id, @class_5_id, @board_msce_id);

-- Chapter: Pictographs
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES ('Pictographs', NULL, @subject_math_english_id, @class_5_id, @board_msce_id);
SET @chapter_pictographs_id = (SELECT id FROM chapters WHERE chapter_name = 'Pictographs' AND subject_id = @subject_math_english_id AND class_id = @class_5_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@chapter_pictographs_id, @class_5_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('Comprehension on pictorial information', @chapter_pictographs_id, @subject_math_english_id, @class_5_id, @board_msce_id);




-- Insert Chapters and Topics for "Marathi – Third Language" based on the PDF

-- Chapter: आकलन (Aakalan - Comprehension)
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES ('आकलन', 'उद्दिष्टानुरुप घटक', @subject_marathi_id, @class_5_id, @board_msce_id);
SET @chapter_aakalan_id = (SELECT id FROM chapters WHERE chapter_name = 'आकलन' AND subject_id = @subject_marathi_id AND class_id = @class_5_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@chapter_aakalan_id, @class_5_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('उतारा व त्यावरील प्रश्न', @chapter_aakalan_id, @subject_marathi_id, @class_5_id, @board_msce_id),
('कविता व त्यावरील प्रश्न', @chapter_aakalan_id, @subject_marathi_id, @class_5_id, @board_msce_id),
('संवाद व त्यावरील प्रश्न', @chapter_aakalan_id, @subject_marathi_id, @class_5_id, @board_msce_id),
('सुसंगत वाक्यांचा परिच्छेद', @chapter_aakalan_id, @subject_marathi_id, @class_5_id, @board_msce_id);

-- Chapter: शब्दसंपत्ती (Shabdasampatti - Vocabulary)
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES ('शब्दसंपत्ती', 'उद्दिष्टानुरुप घटक', @subject_marathi_id, @class_5_id, @board_msce_id);
SET @chapter_shabdasampatti_id = (SELECT id FROM chapters WHERE chapter_name = 'शब्दसंपत्ती' AND subject_id = @subject_marathi_id AND class_id = @class_5_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@chapter_shabdasampatti_id, @class_5_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('समानार्थी शब्द', @chapter_shabdasampatti_id, @subject_marathi_id, @class_5_id, @board_msce_id),
('विरुध्द अर्थाचे शब्द', @chapter_shabdasampatti_id, @subject_marathi_id, @class_5_id, @board_msce_id),
('शब्द समुहाबद्दल एक शब्द', @chapter_shabdasampatti_id, @subject_marathi_id, @class_5_id, @board_msce_id),
('ध्वनीदर्शक शब्द', @chapter_shabdasampatti_id, @subject_marathi_id, @class_5_id, @board_msce_id),
('समुहदर्शक शब्द', @chapter_shabdasampatti_id, @subject_marathi_id, @class_5_id, @board_msce_id),
('घरदर्शक शब्द', @chapter_shabdasampatti_id, @subject_marathi_id, @class_5_id, @board_msce_id),
('वाक्प्रचार व त्यांचे अर्थ', @chapter_shabdasampatti_id, @subject_marathi_id, @class_5_id, @board_msce_id),
('म्हणी व त्यांचे अर्थ', @chapter_shabdasampatti_id, @subject_marathi_id, @class_5_id, @board_msce_id),
('एकाच शब्दाचे भिन्न अर्थ', @chapter_shabdasampatti_id, @subject_marathi_id, @class_5_id, @board_msce_id),
('जोडशब्द', @chapter_shabdasampatti_id, @subject_marathi_id, @class_5_id, @board_msce_id),
('दिलेल्या अक्षरापासून अर्थपूर्ण शब्द तयार करणे', @chapter_shabdasampatti_id, @subject_marathi_id, @class_5_id, @board_msce_id);

-- Chapter: कार्यात्मक व्याकरण (Karyatmak Vyakaran - Functional Grammar)
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES ('कार्यात्मक व्याकरण', 'उद्दिष्टानुरुप घटक', @subject_marathi_id, @class_5_id, @board_msce_id);
SET @chapter_vyakaran_id = (SELECT id FROM chapters WHERE chapter_name = 'कार्यात्मक व्याकरण' AND subject_id = @subject_marathi_id AND class_id = @class_5_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@chapter_vyakaran_id, @class_5_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('शब्दांच्या जाती- नाम व क्रियापद', @chapter_vyakaran_id, @subject_marathi_id, @class_5_id, @board_msce_id),
('लिंग', @chapter_vyakaran_id, @subject_marathi_id, @class_5_id, @board_msce_id),
('वचन', @chapter_vyakaran_id, @subject_marathi_id, @class_5_id, @board_msce_id),
('विरामचिन्हे (पूर्णविराम, स्वल्पविराम, प्रश्नचिन्ह )', @chapter_vyakaran_id, @subject_marathi_id, @class_5_id, @board_msce_id),
('शुध्द व अशुध्द शब्द', @chapter_vyakaran_id, @subject_marathi_id, @class_5_id, @board_msce_id);

-- Chapter: १ ली ते ५ वी मराठी (सुलभभारती) विषयांशी संबंधित सामान्यज्ञान (1st to 5th Std Marathi (Sulabhbharati) related General Knowledge)
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES ('१ ली ते ५ वी मराठी (सुलभभारती) विषयांशी संबंधित सामान्यज्ञान', 'उद्दिष्टानुरुप घटक', @subject_marathi_id, @class_5_id, @board_msce_id);
SET @chapter_gk_id = (SELECT id FROM chapters WHERE chapter_name = '१ ली ते ५ वी मराठी (सुलभभारती) विषयांशी संबंधित सामान्यज्ञान' AND subject_id = @subject_marathi_id AND class_id = @class_5_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@chapter_gk_id, @class_5_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('साहित्यिक, साहित्य', @chapter_gk_id, @subject_marathi_id, @class_5_id, @board_msce_id),
('सामान्यज्ञान', @chapter_gk_id, @subject_marathi_id, @class_5_id, @board_msce_id);




--Insert Chapters and Topics for "IQ – English" based on the PDF

-- Chapter: Comprehension
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES ('Comprehension', NULL, @subject_iq_id, @class_5_id, @board_msce_id);
SET @chapter_comprehension_id = (SELECT id FROM chapters WHERE chapter_name = 'Comprehension' AND subject_id = @subject_iq_id AND class_id = @class_5_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@chapter_comprehension_id, @class_5_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('Do as directed - composite words, letters, words', @chapter_comprehension_id, @subject_iq_id, @class_5_id, @board_msce_id),
('Numbers', @chapter_comprehension_id, @subject_iq_id, @class_5_id, @board_msce_id),
('Series (Sequence)', @chapter_comprehension_id, @subject_iq_id, @class_5_id, @board_msce_id),
('Alphabets', @chapter_comprehension_id, @subject_iq_id, @class_5_id, @board_msce_id);

-- Chapter: Classification
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES ('Classification', NULL, @subject_iq_id, @class_5_id, @board_msce_id);
SET @chapter_classification_id = (SELECT id FROM chapters WHERE chapter_name = 'Classification' AND subject_id = @subject_iq_id AND class_id = @class_5_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@chapter_classification_id, @class_5_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('Vocabulary', @chapter_classification_id, @subject_iq_id, @class_5_id, @board_msce_id),
('Figures', @chapter_classification_id, @subject_iq_id, @class_5_id, @board_msce_id),
('Numbers', @chapter_classification_id, @subject_iq_id, @class_5_id, @board_msce_id);

-- Chapter: Number order
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES ('Number order', NULL, @subject_iq_id, @class_5_id, @board_msce_id);
SET @chapter_number_order_id = (SELECT id FROM chapters WHERE chapter_name = 'Number order' AND subject_id = @subject_iq_id AND class_id = @class_5_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@chapter_number_order_id, @class_5_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('Number pattern (sequence)', @chapter_number_order_id, @subject_iq_id, @class_5_id, @board_msce_id),
('Figure pattern', @chapter_number_order_id, @subject_iq_id, @class_5_id, @board_msce_id),
('Symbols', @chapter_number_order_id, @subject_iq_id, @class_5_id, @board_msce_id),
('Odd man out', @chapter_number_order_id, @subject_iq_id, @class_5_id, @board_msce_id);

-- Chapter: Logic And Inference
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES ('Logic And Inference', NULL, @subject_iq_id, @class_5_id, @board_msce_id);
SET @chapter_logic_inference_id = (SELECT id FROM chapters WHERE chapter_name = 'Logic And Inference' AND subject_id = @subject_iq_id AND class_id = @class_5_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@chapter_logic_inference_id, @class_5_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('Verbal – Age, Comparison, Change in Name, Relations', @chapter_logic_inference_id, @subject_iq_id, @class_5_id, @board_msce_id),
('Non verbal - Triangle', @chapter_logic_inference_id, @subject_iq_id, @class_5_id, @board_msce_id);

-- Chapter: Image (Water Image, Mirror Image)
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES ('Image (Water Image, Mirror Image)', NULL, @subject_iq_id, @class_5_id, @board_msce_id);
SET @chapter_image_id = (SELECT id FROM chapters WHERE chapter_name = 'Image (Water Image, Mirror Image)' AND subject_id = @subject_iq_id AND class_id = @class_5_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@chapter_image_id, @class_5_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('Figures', @chapter_image_id, @subject_iq_id, @class_5_id, @board_msce_id),
('Numbers', @chapter_image_id, @subject_iq_id, @class_5_id, @board_msce_id),
('Alphabets', @chapter_image_id, @subject_iq_id, @class_5_id, @board_msce_id);

-- Chapter: Co-relation
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES ('Co-relation', NULL, @subject_iq_id, @class_5_id, @board_msce_id);
SET @chapter_corelation_id = (SELECT id FROM chapters WHERE chapter_name = 'Co-relation' AND subject_id = @subject_iq_id AND class_id = @class_5_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@chapter_corelation_id, @class_5_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('Vocabulary', @chapter_corelation_id, @subject_iq_id, @class_5_id, @board_msce_id),
('Figures', @chapter_corelation_id, @subject_iq_id, @class_5_id, @board_msce_id),
('Numbers', @chapter_corelation_id, @subject_iq_id, @class_5_id, @board_msce_id);

-- Chapter: Identifying Similarities
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES ('Identifying Similarities', NULL, @subject_iq_id, @class_5_id, @board_msce_id);
SET @chapter_similarities_id = (SELECT id FROM chapters WHERE chapter_name = 'Identifying Similarities' AND subject_id = @subject_iq_id AND class_id = @class_5_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@chapter_similarities_id, @class_5_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('Figures', @chapter_similarities_id, @subject_iq_id, @class_5_id, @board_msce_id);

-- Chapter: Puzzles
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES ('Puzzles', NULL, @subject_iq_id, @class_5_id, @board_msce_id);
SET @chapter_puzzles_id = (SELECT id FROM chapters WHERE chapter_name = 'Puzzles' AND subject_id = @subject_iq_id AND class_id = @class_5_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@chapter_puzzles_id, @class_5_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('Position in a queue', @chapter_puzzles_id, @subject_iq_id, @class_5_id, @board_msce_id),
('Direction', @chapter_puzzles_id, @subject_iq_id, @class_5_id, @board_msce_id),
('Calendar', @chapter_puzzles_id, @subject_iq_id, @class_5_id, @board_msce_id),
('Venn diagram', @chapter_puzzles_id, @subject_iq_id, @class_5_id, @board_msce_id),
('Numbers in square and circle', @chapter_puzzles_id, @subject_iq_id, @class_5_id, @board_msce_id);

-- Chapter: Like Terms
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES ('Like Terms', NULL, @subject_iq_id, @class_5_id, @board_msce_id);
SET @chapter_like_terms_id = (SELECT id FROM chapters WHERE chapter_name = 'Like Terms' AND subject_id = @subject_iq_id AND class_id = @class_5_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@chapter_like_terms_id, @class_5_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('Vocabulary', @chapter_like_terms_id, @subject_iq_id, @class_5_id, @board_msce_id),
('Figures', @chapter_like_terms_id, @subject_iq_id, @class_5_id, @board_msce_id),
('Numbers', @chapter_like_terms_id, @subject_iq_id, @class_5_id, @board_msce_id);

-- Chapter: Symbolic Language (Symbol)
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES ('Symbolic Language (Symbol)', NULL, @subject_iq_id, @class_5_id, @board_msce_id);
SET @chapter_symbolic_language_id = (SELECT id FROM chapters WHERE chapter_name = 'Symbolic Language (Symbol)' AND subject_id = @subject_iq_id AND class_id = @class_5_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@chapter_symbolic_language_id, @class_5_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('Use of symbols for numbers and words', @chapter_symbolic_language_id, @subject_iq_id, @class_5_id, @board_msce_id);

-- Chapter: Special Question Or Important
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES ('Special Question Or Important', NULL, @subject_iq_id, @class_5_id, @board_msce_id);
SET @chapter_special_question_id = (SELECT id FROM chapters WHERE chapter_name = 'Special Question Or Important' AND subject_id = @subject_iq_id AND class_id = @class_5_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@chapter_special_question_id, @class_5_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('Emotional intelligence, Social intelligence', @chapter_special_question_id, @subject_iq_id, @class_5_id, @board_msce_id);

COMMIT;

SET FOREIGN_KEY_CHECKS = 1;


