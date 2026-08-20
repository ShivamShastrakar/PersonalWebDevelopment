--liquibase formatted sql
--changeset {narendra}:{id}

-- MSCE PRE UPPER PRIMARY SCHOLARSHIP EXAMINATION - CLASS 7 Marathi Medium
-- Complete Ready-to-Run Script (Class lookup by name='7')

SET NAMES utf8mb4 COLLATE utf8mb4_unicode_ci;

--Here is the SQL script to insert the syllabus data for "Marathi – First Language" for the 7th Standard, Marathi Medium, based on the provided PDF document. This script ensures all Marathi content is handled correctly with UTF-8 encoding.

SET FOREIGN_KEY_CHECKS = 0;

START TRANSACTION;

-- 1. Insert Board (MSCE) if not exists and get its ID
SET @board_msce_id = (SELECT id FROM board WHERE board_name = 'MSCE' LIMIT 1);

-- 2. Insert Class 7 if not exists and get its ID
SET @class_7_id = (SELECT id FROM class WHERE class_name = '7' LIMIT 1);

-- 3. Insert Subject 'Marathi – First Language' if not exists and get its ID
INSERT IGNORE INTO subject (subject_name) VALUES ('Marathi – First Language');
SET @subject_marathi_fl_id = (SELECT subject_id FROM subject WHERE subject_name = 'Marathi – First Language' LIMIT 1);

-- 4. Map Subject to Board, Class, and Medium (Marathi Medium)
INSERT IGNORE INTO subject_board_class_mapping (subject_id, class_id, board_id, medium) VALUES
(@subject_marathi_fl_id, @class_7_id, @board_msce_id, 'Marathi');

-- 5. Insert Chapters and Topics for "Marathi – First Language" (7th Std, Marathi Medium)

-- Chapter: आकलन (Comprehension)
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES ('आकलन', NULL, @subject_marathi_fl_id, @class_7_id, @board_msce_id);
SET @chapter_aakalan_id = (SELECT id FROM chapters WHERE chapter_name = 'आकलन' AND subject_id = @subject_marathi_fl_id AND class_id = @class_7_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@chapter_aakalan_id, @class_7_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('उतारा व त्यावर आधारित प्रश्न', @chapter_aakalan_id, @subject_marathi_fl_id, @class_7_id, @board_msce_id),
('कविता व त्यावर आधारित प्रश्न', @chapter_aakalan_id, @subject_marathi_fl_id, @class_7_id, @board_msce_id),
('सुसंगत वाक्यांचा परिच्छेद', @chapter_aakalan_id, @subject_marathi_fl_id, @class_7_id, @board_msce_id),
('संवाद व त्यावर आधारित प्रश्न', @chapter_aakalan_id, @subject_marathi_fl_id, @class_7_id, @board_msce_id);

-- Chapter: शब्दसंपत्ती (Vocabulary)
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES ('शब्दसंपत्ती', NULL, @subject_marathi_fl_id, @class_7_id, @board_msce_id);
SET @chapter_shabdasampatti_id = (SELECT id FROM chapters WHERE chapter_name = 'शब्दसंपत्ती' AND subject_id = @subject_marathi_fl_id AND class_id = @class_7_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@chapter_shabdasampatti_id, @class_7_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('समानार्थी शब्द', @chapter_shabdasampatti_id, @subject_marathi_fl_id, @class_7_id, @board_msce_id),
('विरुद्धार्थी शब्द', @chapter_shabdasampatti_id, @subject_marathi_fl_id, @class_7_id, @board_msce_id),
('शुद्ध - अशुद्ध शब्द', @chapter_shabdasampatti_id, @subject_marathi_fl_id, @class_7_id, @board_msce_id),
('आलंकारिक शब्द', @chapter_shabdasampatti_id, @subject_marathi_fl_id, @class_7_id, @board_msce_id),
('शब्दसमूहाबद्दल एक शब्द', @chapter_shabdasampatti_id, @subject_marathi_fl_id, @class_7_id, @board_msce_id),
('वाक्प्रचार', @chapter_shabdasampatti_id, @subject_marathi_fl_id, @class_7_id, @board_msce_id),
('म्हणी', @chapter_shabdasampatti_id, @subject_marathi_fl_id, @class_7_id, @board_msce_id),
('पारिभाषिक शब्द', @chapter_shabdasampatti_id, @subject_marathi_fl_id, @class_7_id, @board_msce_id);

-- Chapter: कार्यात्मक व्याकरण (Functional Grammar)
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES ('कार्यात्मक व्याकरण', NULL, @subject_marathi_fl_id, @class_7_id, @board_msce_id);
SET @chapter_vyakaran_id = (SELECT id FROM chapters WHERE chapter_name = 'कार्यात्मक व्याकरण' AND subject_id = @subject_marathi_fl_id AND class_id = @class_7_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@chapter_vyakaran_id, @class_7_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('वर्णविचार', @chapter_vyakaran_id, @subject_marathi_fl_id, @class_7_id, @board_msce_id),
('संधी - स्वरसंधी, व्यंजनसंधी, विसर्ग संधी, पूर्वरुप संधी, पररुप संधी', @chapter_vyakaran_id, @subject_marathi_fl_id, @class_7_id, @board_msce_id),
('शब्दांच्या जाती - विकारी व अविकारी', @chapter_vyakaran_id, @subject_marathi_fl_id, @class_7_id, @board_msce_id),
('लिंग', @chapter_vyakaran_id, @subject_marathi_fl_id, @class_7_id, @board_msce_id),
('वचन', @chapter_vyakaran_id, @subject_marathi_fl_id, @class_7_id, @board_msce_id),
('विभक्ती, कारकार्थ व शब्दाचे सामान्यरुप', @chapter_vyakaran_id, @subject_marathi_fl_id, @class_7_id, @board_msce_id),
('प्रयोग - सकर्मक व अकर्मक कर्तरी प्रयोग, कर्मणी प्रयोग, सकर्मक व अकर्मक भावे प्रयोग', @chapter_vyakaran_id, @subject_marathi_fl_id, @class_7_id, @board_msce_id),
('वाक्यांचे प्रकार - केवल, मिश्र, संयुक्त व होकारार्थी - नकारार्थी (अर्थ न बदलता)', @chapter_vyakaran_id, @subject_marathi_fl_id, @class_7_id, @board_msce_id),
('विरामचिन्हे', @chapter_vyakaran_id, @subject_marathi_fl_id, @class_7_id, @board_msce_id),
('काळ, काळांचे प्रकार व क्रियापदाचे अर्थ', @chapter_vyakaran_id, @subject_marathi_fl_id, @class_7_id, @board_msce_id);

-- Chapter: इ. 1 ली ते इ. 7 वी मराठी विषयाशी संबंधित सामान्य ज्ञान (General Knowledge related to Marathi subject for 1st to 7th Std)
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES ('इ. 1 ली ते इ. 7 वी मराठी विषयाशी संबंधित सामान्य ज्ञान', NULL, @subject_marathi_fl_id, @class_7_id, @board_msce_id);
SET @chapter_gk_id = (SELECT id FROM chapters WHERE chapter_name = 'इ. 1 ली ते इ. 7 वी मराठी विषयाशी संबंधित सामान्य ज्ञान' AND subject_id = @subject_marathi_fl_id AND class_id = @class_7_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@chapter_gk_id, @class_7_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('लेखक - कवी यांची टोपण नावे, उपाधी', @chapter_gk_id, @subject_marathi_fl_id, @class_7_id, @board_msce_id),
('साहित्य व साहित्यिक, साहित्य क्षेत्रातील पुरस्कार', @chapter_gk_id, @subject_marathi_fl_id, @class_7_id, @board_msce_id);


--Here is the SQL script to insert the syllabus data for "Math – Marathi" for the 7th Standard, Marathi Medium, based on the provided PDF document. This script ensures all Marathi content is handled correctly with UTF-8 encoding.


-- 3. Insert Subject 'Math – Marathi' if not exists and get its ID
INSERT IGNORE INTO subject (subject_name) VALUES ('Math – Marathi');
SET @subject_math_marathi_id = (SELECT subject_id FROM subject WHERE subject_name = 'Math – Marathi' LIMIT 1);

-- 4. Map Subject to Board, Class, and Medium (Marathi Medium)
INSERT IGNORE INTO subject_board_class_mapping (subject_id, class_id, board_id, medium) VALUES
(@subject_math_marathi_id, @class_7_id, @board_msce_id, 'Marathi');

-- 5. Insert Chapters and Topics for "Math – Marathi" (7th Std, Marathi Medium)

-- Chapter: संख्याज्ञान (Number Knowledge)
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES ('संख्याज्ञान', NULL, @subject_math_marathi_id, @class_7_id, @board_msce_id);
SET @chapter_sankhyagnan_id = (SELECT id FROM chapters WHERE chapter_name = 'संख्याज्ञान' AND subject_id = @subject_math_marathi_id AND class_id = @class_7_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@chapter_sankhyagnan_id, @class_7_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('सम संख्या, विषम संख्या, मूळ संख्या, संयुक्त संख्या, सहमूळ संख्या, जोडमूळ संख्या, त्रिकोणी संख्या, रोमन संख्या चिन्हे', @chapter_sankhyagnan_id, @subject_math_marathi_id, @class_7_id, @board_msce_id),
('नैसर्गिक संख्या, पूर्ण संख्या, पूर्णांक संख्या, परिमेय संख्या', @chapter_sankhyagnan_id, @subject_math_marathi_id, @class_7_id, @board_msce_id),
('परिमेय संख्या व त्यावरील क्रिया', @chapter_sankhyagnan_id, @subject_math_marathi_id, @class_7_id, @board_msce_id),
('संख्या रेषा', @chapter_sankhyagnan_id, @subject_math_marathi_id, @class_7_id, @board_msce_id);

-- Chapter: संख्यांवरील क्रिया (Operations on numbers)
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES ('संख्यांवरील क्रिया', NULL, @subject_math_marathi_id, @class_7_id, @board_msce_id);
SET @chapter_operations_on_numbers_id = (SELECT id FROM chapters WHERE chapter_name = 'संख्यांवरील क्रिया' AND subject_id = @subject_math_marathi_id AND class_id = @class_7_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@chapter_operations_on_numbers_id, @class_7_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('विभाज्य, विभाजक, विभाज्यतेच्या कसोट्या', @chapter_operations_on_numbers_id, @subject_math_marathi_id, @class_7_id, @board_msce_id),
('वर्ग आणि वर्गमूळ, घन आणि घनमूळ', @chapter_operations_on_numbers_id, @subject_math_marathi_id, @class_7_id, @board_msce_id),
('लसावि, मसावि', @chapter_operations_on_numbers_id, @subject_math_marathi_id, @class_7_id, @board_msce_id),
('अपूर्णांक - दशांश अपूर्णांक, व्यावहारिक अपूर्णांक', @chapter_operations_on_numbers_id, @subject_math_marathi_id, @class_7_id, @board_msce_id),
('घातांक', @chapter_operations_on_numbers_id, @subject_math_marathi_id, @class_7_id, @board_msce_id),
('गुणोत्तर व प्रमाण, भागिदारी', @chapter_operations_on_numbers_id, @subject_math_marathi_id, @class_7_id, @board_msce_id),
('चलन', @chapter_operations_on_numbers_id, @subject_math_marathi_id, @class_7_id, @board_msce_id),
('काळ, काम, वेळ', @chapter_operations_on_numbers_id, @subject_math_marathi_id, @class_7_id, @board_msce_id),
('सरासरी', @chapter_operations_on_numbers_id, @subject_math_marathi_id, @class_7_id, @board_msce_id);

-- Chapter: भूमिती (Geometry)
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES ('भूमिती', NULL, @subject_math_marathi_id, @class_7_id, @board_msce_id);
SET @chapter_geometry_id = (SELECT id FROM chapters WHERE chapter_name = 'भूमिती' AND subject_id = @subject_math_marathi_id AND class_id = @class_7_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@chapter_geometry_id, @class_7_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('भूमितीतील मूलभूत संबोध - बिंदू, रेषा, रेषाखंड, किरण, कोन (सरळकोन, शून्य, पूर्ण, प्रविशाल) प्रतल, विरुद्धकोन, संलग्नकोन, पूरककोन, कोटिकोन, कोनदुभाजक व लंबदुभाजक गुणधर्म, एकसंपाती रेषा, संपातबिंदू, बहुभुजाकृतीचे आंतरकोन', @chapter_geometry_id, @subject_math_marathi_id, @class_7_id, @board_msce_id),
('समांतर रेषा गुणधर्म, संगतकोन, आंतरकोन, व्युत्क्रमकोन', @chapter_geometry_id, @subject_math_marathi_id, @class_7_id, @board_msce_id),
('वर्तुळ - वर्तुळकंस, केंद्रियकोन, कंसाचे माप', @chapter_geometry_id, @subject_math_marathi_id, @class_7_id, @board_msce_id),
('त्रिकोण गुणधर्म', @chapter_geometry_id, @subject_math_marathi_id, @class_7_id, @board_msce_id),
('पायथागोरस प्रमेय', @chapter_geometry_id, @subject_math_marathi_id, @class_7_id, @board_msce_id),
('चौकोन गुणधर्म', @chapter_geometry_id, @subject_math_marathi_id, @class_7_id, @board_msce_id),
('त्रिमितीय आकार, मिती, चिती, सूची, गोल. घडणी - पृष्ठे, शिरोबिंदू कडा', @chapter_geometry_id, @subject_math_marathi_id, @class_7_id, @board_msce_id);

-- Chapter: महत्त्वमापन (Mensuration)
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES ('महत्त्वमापन', NULL, @subject_math_marathi_id, @class_7_id, @board_msce_id);
SET @chapter_mensuration_id = (SELECT id FROM chapters WHERE chapter_name = 'महत्त्वमापन' AND subject_id = @subject_math_marathi_id AND class_id = @class_7_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@chapter_mensuration_id, @class_7_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('परिमाणे - लांबी, वस्तुमान, धारकता, कालमापन नाणी व नोटा', @chapter_mensuration_id, @subject_math_marathi_id, @class_7_id, @board_msce_id),
('परिमिती - त्रिकोण, चौकोन, बहुभुजाकृती, वर्तुळ', @chapter_mensuration_id, @subject_math_marathi_id, @class_7_id, @board_msce_id),
('क्षेत्रफळ - त्रिकोण, चौरस, आयत, वर्तुळ', @chapter_mensuration_id, @subject_math_marathi_id, @class_7_id, @board_msce_id),
('घनफळ व पृष्ठफळ - इष्टिकाचिती व घन', @chapter_mensuration_id, @subject_math_marathi_id, @class_7_id, @board_msce_id);

-- Chapter: सांख्यिकी (Statistics)
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES ('सांख्यिकी', NULL, @subject_math_marathi_id, @class_7_id, @board_msce_id);
SET @chapter_statistics_id = (SELECT id FROM chapters WHERE chapter_name = 'सांख्यिकी' AND subject_id = @subject_math_marathi_id AND class_id = @class_7_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@chapter_statistics_id, @class_7_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('मध्यमान, वारंवारता सारणी', @chapter_statistics_id, @subject_math_marathi_id, @class_7_id, @board_msce_id),
('स्तंभालेख, जोडस्तंभालेख', @chapter_statistics_id, @subject_math_marathi_id, @class_7_id, @board_msce_id);

-- Chapter: व्यावहारिक गणित (Commercial Mathematics)
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES ('व्यावहारिक गणित', NULL, @subject_math_marathi_id, @class_7_id, @board_msce_id);
SET @chapter_commercial_math_id = (SELECT id FROM chapters WHERE chapter_name = 'व्यावहारिक गणित' AND subject_id = @subject_math_marathi_id AND class_id = @class_7_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@chapter_commercial_math_id, @class_7_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('शेकडेवारी', @chapter_commercial_math_id, @subject_math_marathi_id, @class_7_id, @board_msce_id),
('बँक व सरळव्याज', @chapter_commercial_math_id, @subject_math_marathi_id, @class_7_id, @board_msce_id),
('नफा - तोटा, सूट (रिबेट), कमिशन (दलाली)', @chapter_commercial_math_id, @subject_math_marathi_id, @class_7_id, @board_msce_id);

-- Chapter: बीजगणित (Algebra)
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES ('बीजगणित', NULL, @subject_math_marathi_id, @class_7_id, @board_msce_id);
SET @chapter_algebra_id = (SELECT id FROM chapters WHERE chapter_name = 'बीजगणित' AND subject_id = @subject_math_marathi_id AND class_id = @class_7_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@chapter_algebra_id, @class_7_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('संख्येसाठी अक्षर, बैजिक राशी व त्यावरील क्रिया, बैजिक राशींची किंमत, बैजिक राशीचे अवयव', @chapter_algebra_id, @subject_math_marathi_id, @class_7_id, @board_msce_id),
('नित्यसमानता - बैजिक सूत्रे वर्गविस्तार', @chapter_algebra_id, @subject_math_marathi_id, @class_7_id, @board_msce_id),
('एकचल समीकरणे व त्यावरील शाब्दिक उदाहरणे', @chapter_algebra_id, @subject_math_marathi_id, @class_7_id, @board_msce_id);


--Here is the SQL script to insert the syllabus data for "English – Third Language" for the 7th Standard, Marathi Medium, based on the provided PDF document.


-- 3. Insert Subject 'English – Third Language' if not exists and get its ID
INSERT IGNORE INTO subject (subject_name) VALUES ('English – Third Language');
SET @subject_english_third_lang_id = (SELECT subject_id FROM subject WHERE subject_name = 'English – Third Language' LIMIT 1);

-- 4. Map Subject to Board, Class, and Medium (Marathi Medium as requested by user)
INSERT IGNORE INTO subject_board_class_mapping (subject_id, class_id, board_id, medium) VALUES
(@subject_english_third_lang_id, @class_7_id, @board_msce_id, 'Marathi');

-- 5. Insert Chapters and Topics for "English – Third Language" (7th Std, Marathi Medium)

-- Chapter: Vocabulary
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES ('Vocabulary', NULL, @subject_english_third_lang_id, @class_7_id, @board_msce_id);
SET @chapter_vocabulary_id = (SELECT id FROM chapters WHERE chapter_name = 'Vocabulary' AND subject_id = @subject_english_third_lang_id AND class_id = @class_7_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@chapter_vocabulary_id, @class_7_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('Writing words using clues and contextual meanings of the words', @chapter_vocabulary_id, @subject_english_third_lang_id, @class_7_id, @board_msce_id),
('Singular and Plural : Opposite and Similar meaning words', @chapter_vocabulary_id, @subject_english_third_lang_id, @class_7_id, @board_msce_id),
('Word Formation', @chapter_vocabulary_id, @subject_english_third_lang_id, @class_7_id, @board_msce_id),
('Phrases', @chapter_vocabulary_id, @subject_english_third_lang_id, @class_7_id, @board_msce_id),
('Writing one word substitute for the clue provided', @chapter_vocabulary_id, @subject_english_third_lang_id, @class_7_id, @board_msce_id),
('Figures of speech (Simile, Metaphor, Personification, hyperbole)', @chapter_vocabulary_id, @subject_english_third_lang_id, @class_7_id, @board_msce_id),
('Homophones', @chapter_vocabulary_id, @subject_english_third_lang_id, @class_7_id, @board_msce_id);

-- Chapter: Word Puzzles
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES ('Word Puzzles', NULL, @subject_english_third_lang_id, @class_7_id, @board_msce_id);
SET @chapter_word_puzzles_id = (SELECT id FROM chapters WHERE chapter_name = 'Word Puzzles' AND subject_id = @subject_english_third_lang_id AND class_id = @class_7_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@chapter_word_puzzles_id, @class_7_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('Crossword puzzles, Riddles', @chapter_word_puzzles_id, @subject_english_third_lang_id, @class_7_id, @board_msce_id),
('Grid', @chapter_word_puzzles_id, @subject_english_third_lang_id, @class_7_id, @board_msce_id);

-- Chapter: Language Study
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES ('Language Study', NULL, @subject_english_third_lang_id, @class_7_id, @board_msce_id);
SET @chapter_language_study_id = (SELECT id FROM chapters WHERE chapter_name = 'Language Study' AND subject_id = @subject_english_third_lang_id AND class_id = @class_7_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@chapter_language_study_id, @class_7_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('Sentence Formation', @chapter_language_study_id, @subject_english_third_lang_id, @class_7_id, @board_msce_id),
('Types of Sentences', @chapter_language_study_id, @subject_english_third_lang_id, @class_7_id, @board_msce_id),
('Parts of speech', @chapter_language_study_id, @subject_english_third_lang_id, @class_7_id, @board_msce_id),
('Modal auxiliaries', @chapter_language_study_id, @subject_english_third_lang_id, @class_7_id, @board_msce_id),
('Tenses - Simple, Progressive, Perfect (Present, Past, Future)', @chapter_language_study_id, @subject_english_third_lang_id, @class_7_id, @board_msce_id);

-- Chapter: Grammar (Transformation of Sentences)
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES ('Grammar (Transformation of Sentences)', NULL, @subject_english_third_lang_id, @class_7_id, @board_msce_id);
SET @chapter_grammar_id = (SELECT id FROM chapters WHERE chapter_name = 'Grammar (Transformation of Sentences)' AND subject_id = @subject_english_third_lang_id AND class_id = @class_7_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@chapter_grammar_id, @class_7_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('Punctuation', @chapter_grammar_id, @subject_english_third_lang_id, @class_7_id, @board_msce_id),
('Direct - Indirect Speech (Only Statements)', @chapter_grammar_id, @subject_english_third_lang_id, @class_7_id, @board_msce_id),
('Degree (Statements)', @chapter_grammar_id, @subject_english_third_lang_id, @class_7_id, @board_msce_id);

-- Chapter: Creative Writing
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES ('Creative Writing', NULL, @subject_english_third_lang_id, @class_7_id, @board_msce_id);
SET @chapter_creative_writing_id = (SELECT id FROM chapters WHERE chapter_name = 'Creative Writing' AND subject_id = @subject_english_third_lang_id AND class_id = @class_7_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@chapter_creative_writing_id, @class_7_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('Short Note', @chapter_creative_writing_id, @subject_english_third_lang_id, @class_7_id, @board_msce_id),
('Dialogue', @chapter_creative_writing_id, @subject_english_third_lang_id, @class_7_id, @board_msce_id),
('Slogans', @chapter_creative_writing_id, @subject_english_third_lang_id, @class_7_id, @board_msce_id),
('Advertisements', @chapter_creative_writing_id, @subject_english_third_lang_id, @class_7_id, @board_msce_id),
('News', @chapter_creative_writing_id, @subject_english_third_lang_id, @class_7_id, @board_msce_id),
('E-mail', @chapter_creative_writing_id, @subject_english_third_lang_id, @class_7_id, @board_msce_id),
('Websites', @chapter_creative_writing_id, @subject_english_third_lang_id, @class_7_id, @board_msce_id),
('Stock Expressions', @chapter_creative_writing_id, @subject_english_third_lang_id, @class_7_id, @board_msce_id);

-- Chapter: Reading Skills
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES ('Reading Skills', NULL, @subject_english_third_lang_id, @class_7_id, @board_msce_id);
SET @chapter_reading_skills_id = (SELECT id FROM chapters WHERE chapter_name = 'Reading Skills' AND subject_id = @subject_english_third_lang_id AND class_id = @class_7_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@chapter_reading_skills_id, @class_7_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('Prose (60 to 70 words)', @chapter_reading_skills_id, @subject_english_third_lang_id, @class_7_id, @board_msce_id);

-- Chapter: Miscellaneous
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES ('Miscellaneous', NULL, @subject_english_third_lang_id, @class_7_id, @board_msce_id);
SET @chapter_miscellaneous_id = (SELECT id FROM chapters WHERE chapter_name = 'Miscellaneous' AND subject_id = @subject_english_third_lang_id AND class_id = @class_7_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@chapter_miscellaneous_id, @class_7_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('Games', @chapter_miscellaneous_id, @subject_english_third_lang_id, @class_7_id, @board_msce_id),
('Clock', @chapter_miscellaneous_id, @subject_english_third_lang_id, @class_7_id, @board_msce_id);


-- ════════════════════════════════════════════════════════════════════════════════
-- PRE UPPER PRIMARY SCHOLARSHIP (7th Std) - IQ / बुद्धिमत्ता चाचणी
-- Marathi Medium - MSCE Board
-- Based on official syllabus dated 04/11/2025
-- ════════════════════════════════════════════════════════════════════════════════


-- 2. Get IDs (recommended to use variables for safety)
SET @board_msce_id     := (SELECT id FROM board     WHERE board_name = 'MSCE' LIMIT 1);
SET @class_7_id        := (SELECT id FROM class     WHERE class_name = '7'    LIMIT 1);
SET @subject_iq_id     := (SELECT subject_id FROM subject WHERE subject_name = 'IQ – Marathi' LIMIT 1);

-- 3. Map subject → board + class + medium  (very important for filtering)
INSERT IGNORE INTO subject_board_class_mapping 
    (subject_id, class_id, board_id, medium)
VALUES 
    (@subject_iq_id, @class_7_id, @board_msce_id, 'Marathi');

-- ────────────────────────────────────────────────────────────────────────────────
--               Now insert all Chapters & Topics (full hierarchy)
-- ────────────────────────────────────────────────────────────────────────────────

-- Chapter 1: आकलन
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) 
VALUES ('आकलन', NULL, @subject_iq_id, @class_7_id, @board_msce_id);

SET @ch1 := LAST_INSERT_ID();   -- or better: query by name + subject + class + board

INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) 
VALUES (@ch1, @class_7_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('सूचनापालन (वर्णन व मजकूर, संख्या)', @ch1, @subject_iq_id, @class_7_id, @board_msce_id),
('इंग्रजी अक्षरमाला',                         @ch1, @subject_iq_id, @class_7_id, @board_msce_id);

-- Chapter 2: वर्गीकरण
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) 
VALUES ('वर्गीकरण', NULL, @subject_iq_id, @class_7_id, @board_msce_id);
SET @ch2 := LAST_INSERT_ID();

INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@ch2, @class_7_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('शब्दसंग्रह',      @ch2, @subject_iq_id, @class_7_id, @board_msce_id),
('आकृत्या',         @ch2, @subject_iq_id, @class_7_id, @board_msce_id),
('संख्या',          @ch2, @subject_iq_id, @class_7_id, @board_msce_id),
('अक्षरमाला (इंग्रजी)', @ch2, @subject_iq_id, @class_7_id, @board_msce_id);

-- Chapter 3: समसंबंध
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) 
VALUES ('समसंबंध', NULL, @subject_iq_id, @class_7_id, @board_msce_id);
SET @ch3 := LAST_INSERT_ID();

INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@ch3, @class_7_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('शब्दसंग्रह',      @ch3, @subject_iq_id, @class_7_id, @board_msce_id),
('आकृत्या',         @ch3, @subject_iq_id, @class_7_id, @board_msce_id),
('संख्या',          @ch3, @subject_iq_id, @class_7_id, @board_msce_id),
('अक्षरमाला (इंग्रजी)', @ch3, @subject_iq_id, @class_7_id, @board_msce_id);

-- Chapter 4: क्रम
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) 
VALUES ('क्रम', NULL, @subject_iq_id, @class_7_id, @board_msce_id);
SET @ch4 := LAST_INSERT_ID();

INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@ch4, @class_7_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('संख्या',                  @ch4, @subject_iq_id, @class_7_id, @board_msce_id),
('आकृत्या',                 @ch4, @subject_iq_id, @class_7_id, @board_msce_id),
('चुकीचे पद ओळखणे (संख्या)', @ch4, @subject_iq_id, @class_7_id, @board_msce_id),
('अक्षरमाला (इंग्रजी)',      @ch4, @subject_iq_id, @class_7_id, @board_msce_id);

-- Chapter 5: गटाशी जुळणारे पद
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) 
VALUES ('गटाशी जुळणारे पद', NULL, @subject_iq_id, @class_7_id, @board_msce_id);
SET @ch5 := LAST_INSERT_ID();

INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@ch5, @class_7_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('संख्या', @ch5, @subject_iq_id, @class_7_id, @board_msce_id),
('शब्द',   @ch5, @subject_iq_id, @class_7_id, @board_msce_id),
('आकृती',  @ch5, @subject_iq_id, @class_7_id, @board_msce_id);

-- Chapter 6: लयबद्ध मांडणी
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) 
VALUES ('लयबद्ध मांडणी', NULL, @subject_iq_id, @class_7_id, @board_msce_id);
SET @ch6 := LAST_INSERT_ID();

INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@ch6, @class_7_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('अंक',    @ch6, @subject_iq_id, @class_7_id, @board_msce_id),
('अक्षरे',  @ch6, @subject_iq_id, @class_7_id, @board_msce_id),
('चिन्हे',  @ch6, @subject_iq_id, @class_7_id, @board_msce_id);

-- Chapter 7: जलप्रतिबिंब
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) 
VALUES ('जलप्रतिबिंब', NULL, @subject_iq_id, @class_7_id, @board_msce_id);
SET @ch7 := LAST_INSERT_ID();

INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@ch7, @class_7_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('अंक, अक्षरे, आकृती', @ch7, @subject_iq_id, @class_7_id, @board_msce_id);

-- Chapter 8: आरशातील प्रतिमा
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) 
VALUES ('आरशातील प्रतिमा', NULL, @subject_iq_id, @class_7_id, @board_msce_id);
SET @ch8 := LAST_INSERT_ID();

INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@ch8, @class_7_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('अंक, अक्षरे, आकृती', @ch8, @subject_iq_id, @class_7_id, @board_msce_id);

-- Chapter 9: तर्क व अनुमान
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) 
VALUES ('तर्क व अनुमान', NULL, @subject_iq_id, @class_7_id, @board_msce_id);
SET @ch9 := LAST_INSERT_ID();

INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@ch9, @class_7_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('भाषिक - वय, वेळ व घड्याळ, नाते, माहितीवरून अनुमान काढणे', @ch9, @subject_iq_id, @class_7_id, @board_msce_id),
('अभाषिक - घनाकृती ठोकळे, त्रिकोण व चौकोन मोजणे',               @ch9, @subject_iq_id, @class_7_id, @board_msce_id);

-- Chapter 10: कूटप्रश्न
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) 
VALUES ('कूटप्रश्न', NULL, @subject_iq_id, @class_7_id, @board_msce_id);
SET @ch10 := LAST_INSERT_ID();

INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@ch10, @class_7_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('रांगेतील स्थान',                     @ch10, @subject_iq_id, @class_7_id, @board_msce_id),
('दिशांवरील प्रश्न',                    @ch10, @subject_iq_id, @class_7_id, @board_msce_id),
('दिनदर्शिका',                         @ch10, @subject_iq_id, @class_7_id, @board_msce_id),
('वेन आकृती',                           @ch10, @subject_iq_id, @class_7_id, @board_msce_id),
('गणिती कोडे',                          @ch10, @subject_iq_id, @class_7_id, @board_msce_id),
('संख्यांच्या मांडणीतील सूत्र ओळखणे',   @ch10, @subject_iq_id, @class_7_id, @board_msce_id);

-- Chapter 11: सांकेतिक भाषा
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) 
VALUES ('सांकेतिक भाषा', NULL, @subject_iq_id, @class_7_id, @board_msce_id);
SET @ch11 := LAST_INSERT_ID();

INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@ch11, @class_7_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('आकृत्या', @ch11, @subject_iq_id, @class_7_id, @board_msce_id),
('अंक',     @ch11, @subject_iq_id, @class_7_id, @board_msce_id),
('अक्षरे',   @ch11, @subject_iq_id, @class_7_id, @board_msce_id);

-- Chapter 12: मनोरे / रचना
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) 
VALUES ('मनोरे / रचना', NULL, @subject_iq_id, @class_7_id, @board_msce_id);
SET @ch12 := LAST_INSERT_ID();

INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@ch12, @class_7_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('अंक',    @ch12, @subject_iq_id, @class_7_id, @board_msce_id),
('अक्षरे',  @ch12, @subject_iq_id, @class_7_id, @board_msce_id),
('चिन्हे',  @ch12, @subject_iq_id, @class_7_id, @board_msce_id);

-- Chapter 13: आकृतीचे पृथक्करण
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) 
VALUES ('आकृतीचे पृथक्करण', NULL, @subject_iq_id, @class_7_id, @board_msce_id);
SET @ch13 := LAST_INSERT_ID();

INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@ch13, @class_7_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('अपूर्ण आकृती पूर्ण करणे',      @ch13, @subject_iq_id, @class_7_id, @board_msce_id),
('तंतोतंत आकृती ओळखणे',           @ch13, @subject_iq_id, @class_7_id, @board_msce_id),
('घडीच्या आकृत्या',                @ch13, @subject_iq_id, @class_7_id, @board_msce_id),
('लपलेली आकृती शोधणे',             @ch13, @subject_iq_id, @class_7_id, @board_msce_id);

-- End of script
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;