--liquibase formatted sql
--changeset {narendra}:{id}

-- MSCE PRE UPPER PRIMARY SCHOLARSHIP EXAMINATION - CLASS 8 Marathi Medium
-- Complete Ready-to-Run Script (Class lookup by name='8')

SET NAMES utf8mb4 COLLATE utf8mb4_unicode_ci;
SET FOREIGN_KEY_CHECKS = 0;

START TRANSACTION;

--PRE UPPER PRIMARY SCHOLARSHIP EXAMINATION – Std 8 – Marathi Medium (MSCE)

SET @board_msce_id = (SELECT id FROM board WHERE board_name='MSCE' LIMIT 1);
SET @class_8th_id  = (SELECT id FROM class WHERE class_name='8' LIMIT 1);

SET @sub_mar  = (SELECT subject_id FROM subject WHERE subject_name='Marathi – First Language');


INSERT IGNORE INTO subject_board_class_mapping
(subject_id, class_id, board_id, medium)
VALUES
(@sub_mar,  @class_8th_id, @board_msce_id, 'Marathi');
--(@sub_math, @class_8th_id, @board_msce_id, 'Marathi'),
--(@sub_eng,  @class_8th_id, @board_msce_id, 'Marathi'),
--(@sub_iq,   @class_8th_id, @board_msce_id, 'Marathi');


INSERT INTO chapters (chapter_name, unit, subject_id, class_id, board_id)
VALUES
('आकलन',            'Unit 1', @sub_mar, @class_8th_id, @board_msce_id),
('शब्दसंपत्ती',      'Unit 2', @sub_mar, @class_8th_id, @board_msce_id),
('कार्यात्मक व्याकरण', 'Unit 3', @sub_mar, @class_8th_id, @board_msce_id),
('सामान्य ज्ञान',    'Unit 4', @sub_mar, @class_8th_id, @board_msce_id);

--Topics – Unit 1: आकलन (24%)
INSERT INTO topics (topic_name, chapter_id, subject_id, class_id, board_id)
SELECT 'उतारा व त्यावर आधारित प्रश्न', id, @sub_mar, @class_8th_id, @board_msce_id
FROM chapters WHERE chapter_name='आकलन';

INSERT INTO topics (topic_name, chapter_id, subject_id, class_id, board_id)
SELECT 'कविता व त्यावर आधारित प्रश्न', id, @sub_mar, @class_8th_id, @board_msce_id
FROM chapters WHERE chapter_name='आकलन';

INSERT INTO topics (topic_name, chapter_id, subject_id, class_id, board_id)
SELECT 'सुसंवादी परिच्छेद', id, @sub_mar, @class_8th_id, @board_msce_id
FROM chapters WHERE chapter_name='आकलन';

INSERT INTO topics (topic_name, chapter_id, subject_id, class_id, board_id)
SELECT 'संवाद व त्यावर आधारित प्रश्न', id, @sub_mar, @class_8th_id, @board_msce_id
FROM chapters WHERE chapter_name='आकलन';

--Topics – Unit 2: शब्दसंपत्ती (24%)
INSERT INTO topics
(topic_name, chapter_id, subject_id, class_id, board_id)
SELECT 'समानार्थी शब्द', id, @sub_mar, @class_8th_id, @board_msce_id
FROM chapters WHERE chapter_name='शब्दसंपत्ती';

INSERT INTO topics (topic_name, chapter_id, subject_id, class_id, board_id)
SELECT 'विरुद्धार्थी शब्द', id, @sub_mar, @class_8th_id, @board_msce_id
FROM chapters WHERE chapter_name='शब्दसंपत्ती';

INSERT INTO topics (topic_name, chapter_id, subject_id, class_id, board_id)
SELECT 'शुद्ध-अशुद्ध शब्द', id, @sub_mar, @class_8th_id, @board_msce_id
FROM chapters WHERE chapter_name='शब्दसंपत्ती';

INSERT INTO topics (topic_name, chapter_id, subject_id, class_id, board_id)
SELECT 'अलंकारिक शब्द', id, @sub_mar, @class_8th_id, @board_msce_id
FROM chapters WHERE chapter_name='शब्दसंपत्ती';

INSERT INTO topics (topic_name, chapter_id, subject_id, class_id, board_id)
SELECT 'वाक्प्रचार', id, @sub_mar, @class_8th_id, @board_msce_id
FROM chapters WHERE chapter_name='शब्दसंपत्ती';

--Topics – Unit 3: कार्यात्मक व्याकरण (44%)
INSERT INTO topics
(topic_name, chapter_id, subject_id, class_id, board_id)
SELECT 'संधी', id, @sub_mar, @class_8th_id, @board_msce_id
FROM chapters WHERE chapter_name='कार्यात्मक व्याकरण';

INSERT INTO topics (topic_name, chapter_id, subject_id, class_id, board_id)
SELECT 'समास', id, @sub_mar, @class_8th_id, @board_msce_id
FROM chapters WHERE chapter_name='कार्यात्मक व्याकरण';

INSERT INTO topics (topic_name, chapter_id, subject_id, class_id, board_id)
SELECT 'अलंकार', id, @sub_mar, @class_8th_id, @board_msce_id
FROM chapters WHERE chapter_name='कार्यात्मक व्याकरण';

INSERT INTO topics (topic_name, chapter_id, subject_id, class_id, board_id)
SELECT 'वाक्यप्रकार', id, @sub_mar, @class_8th_id, @board_msce_id
FROM chapters WHERE chapter_name='कार्यात्मक व्याकरण';

INSERT INTO topics (topic_name, chapter_id, subject_id, class_id, board_id)
SELECT 'काल व क्रियापद', id, @sub_mar, @class_8th_id, @board_msce_id
FROM chapters WHERE chapter_name='कार्यात्मक व्याकरण';

--Topics – Unit 4: सामान्य ज्ञान (8%)
INSERT INTO topics
(topic_name, chapter_id, subject_id, class_id, board_id)
SELECT 'साहित्य व साहित्यप्रकार', id, @sub_mar, @class_8th_id, @board_msce_id
FROM chapters WHERE chapter_name='सामान्य ज्ञान';

INSERT INTO topics (topic_name, chapter_id, subject_id, class_id, board_id)
SELECT 'लेखक, कवी व त्यांची टोपणनावे', id, @sub_mar, @class_8th_id, @board_msce_id
FROM chapters WHERE chapter_name='सामान्य ज्ञान';


-- =============================================================================
-- Pre Upper Primary Scholarship Exam (Class 8) - Marathi Medium
-- Subject: Math – Marathi (गणित)
-- Syllabus Date: 23 March 2016 (MSCE - Maharashtra State Examination Council)
-- Total weightage: 100%
-- =============================================================================

-- 1. Fetch common IDs (ensure these records already exist in your database)
SET @board_msce_id   := (SELECT id FROM board     WHERE board_name = 'MSCE' LIMIT 1);
SET @class_8_id      := (SELECT id FROM class     WHERE class_name = '8'    LIMIT 1);
SET @subject_math_id := (SELECT subject_id FROM subject WHERE subject_name = 'Math – Marathi' LIMIT 1);

-- 2. Map subject to board + class + medium (if not already done)
INSERT IGNORE INTO subject_board_class_mapping 
    (subject_id, class_id, board_id, medium)
VALUES 
    (@subject_math_id, @class_8_id, @board_msce_id, 'Marathi');

-- =============================================================================
--                           CHAPTERS & TOPICS - गणित
-- =============================================================================

-- Chapter 1: संख्याज्ञान (6%)
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) 
VALUES ('संख्याज्ञान', NULL, @subject_math_id, @class_8_id, @board_msce_id);

SET @ch1 := (SELECT id FROM chapters 
             WHERE chapter_name = 'संख्याज्ञान' 
               AND subject_id = @subject_math_id 
               AND class_id = @class_8_id 
               AND board_id = @board_msce_id LIMIT 1);

INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) 
VALUES (@ch1, @class_8_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('नैसर्गिक संख्या, पूर्ण संख्या, पूर्णांक संख्या, परिमेय संख्या, अपरिमेय व वास्तव संख्या', @ch1, @subject_math_id, @class_8_id, @board_msce_id),
('परिमेय संख्या व त्यावरील क्रिया', @ch1, @subject_math_id, @class_8_id, @board_msce_id),
('सम, विषम, मूळ, जोडमूळ, सहमूळ व संयुक्त संख्या', @ch1, @subject_math_id, @class_8_id, @board_msce_id),
('बेरीज व्यस्त व गुणाकार व्यस्त संख्या', @ch1, @subject_math_id, @class_8_id, @board_msce_id),
('संख्या रेषा', @ch1, @subject_math_id, @class_8_id, @board_msce_id);

-- Chapter 2: संख्यावरील क्रिया (14%)
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) 
VALUES ('संख्यावरील क्रिया', NULL, @subject_math_id, @class_8_id, @board_msce_id);

SET @ch2 := (SELECT id FROM chapters 
             WHERE chapter_name = 'संख्यावरील क्रिया' 
               AND subject_id = @subject_math_id 
               AND class_id = @class_8_id 
               AND board_id = @board_msce_id LIMIT 1);

INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) 
VALUES (@ch2, @class_8_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('विभाज्य, विभाजक व विभाज्यतेच्या कसोट्या', @ch2, @subject_math_id, @class_8_id, @board_msce_id),
('मसावि, लसावि', @ch2, @subject_math_id, @class_8_id, @board_msce_id),
('वर्ग आणि वर्गमूळ, घन आणि घनमूळ', @ch2, @subject_math_id, @class_8_id, @board_msce_id),
('दशांश अपूर्णांक व व्यावहारिक अपूर्णांक', @ch2, @subject_math_id, @class_8_id, @board_msce_id),
('घातांक', @ch2, @subject_math_id, @class_8_id, @board_msce_id),
('गुणोत्तर, प्रमाण व चलन (काळ, काम, वेग इ.)', @ch2, @subject_math_id, @class_8_id, @board_msce_id),
('सरासरी', @ch2, @subject_math_id, @class_8_id, @board_msce_id);

-- Chapter 3: भूमिती (20%)
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) 
VALUES ('भूमिती', NULL, @subject_math_id, @class_8_id, @board_msce_id);

SET @ch3 := (SELECT id FROM chapters 
             WHERE chapter_name = 'भूमिती' 
               AND subject_id = @subject_math_id 
               AND class_id = @class_8_id 
               AND board_id = @board_msce_id LIMIT 1);

INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) 
VALUES (@ch3, @class_8_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('बिंदू, रेषाखंड, रेषा, किरण, कोन (सरळकोन, शून्य अंश कोन, पूर्ण कोन, प्रविष्टाल कोन) प्रतल, विरुध्दकोन, संलग्नकोन, पुरककोन, कोटिकोन', @ch3, @subject_math_id, @class_8_id, @board_msce_id),
('समांतर रेषा व गुणधर्म', @ch3, @subject_math_id, @class_8_id, @board_msce_id),
('वर्तुळ, वर्तुळक्षेत्र, वर्तुळखंड, वर्तुळकंस', @ch3, @subject_math_id, @class_8_id, @board_msce_id),
('त्रिकोण - गुणधर्म, एकरुपता', @ch3, @subject_math_id, @class_8_id, @board_msce_id),
('पायथागोरसचा सिद्धांत', @ch3, @subject_math_id, @class_8_id, @board_msce_id),
('चौकोन - गुणधर्म', @ch3, @subject_math_id, @class_8_id, @board_msce_id);

-- Chapter 4: महत्त्व मापन (20%)
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) 
VALUES ('महत्त्व मापन', NULL, @subject_math_id, @class_8_id, @board_msce_id);

SET @ch4 := (SELECT id FROM chapters 
             WHERE chapter_name = 'महत्त्व मापन' 
               AND subject_id = @subject_math_id 
               AND class_id = @class_8_id 
               AND board_id = @board_msce_id LIMIT 1);

INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) 
VALUES (@ch4, @class_8_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('परिमाणे - लांबी, वस्तूमान, धारकता, कालमापन, नापी आणि नोटा', @ch4, @subject_math_id, @class_8_id, @board_msce_id),
('परिमिती - त्रिकोण, चौकोन व बहुभुजाकृती', @ch4, @subject_math_id, @class_8_id, @board_msce_id),
('क्षेत्रफळ - त्रिकोण, चौरस, आयत, समांतरभुज चौकोन, समभुज चौकोन, समलंब चौकोन, वर्तुळ, अनियमित आकृती व रेखांकित भाग', @ch4, @subject_math_id, @class_8_id, @board_msce_id),
('घनफळ व पृष्ठफळ - इष्टिकाचिती, घन, दंडगोल, शंकू व गोल', @ch4, @subject_math_id, @class_8_id, @board_msce_id);

-- Chapter 5: सांख्यिकी (6%)
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) 
VALUES ('सांख्यिकी', NULL, @subject_math_id, @class_8_id, @board_msce_id);

SET @ch5 := (SELECT id FROM chapters 
             WHERE chapter_name = 'सांख्यिकी' 
               AND subject_id = @subject_math_id 
               AND class_id = @class_8_id 
               AND board_id = @board_msce_id LIMIT 1);

INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) 
VALUES (@ch5, @class_8_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('मध्यमान', @ch5, @subject_math_id, @class_8_id, @board_msce_id),
('स्तंभालेख, जोडस्तंभालेख, वृत्तालेख, विभाजित स्तंभालेख व शतमान आलेख यांची ओळख', @ch5, @subject_math_id, @class_8_id, @board_msce_id);

-- Chapter 6: व्यावहारिक गणित (16%)
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) 
VALUES ('व्यावहारिक गणित', NULL, @subject_math_id, @class_8_id, @board_msce_id);

SET @ch6 := (SELECT id FROM chapters 
             WHERE chapter_name = 'व्यावहारिक गणित' 
               AND subject_id = @subject_math_id 
               AND class_id = @class_8_id 
               AND board_id = @board_msce_id LIMIT 1);

INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) 
VALUES (@ch6, @class_8_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('शेकडेवारी', @ch6, @subject_math_id, @class_8_id, @board_msce_id),
('सरळव्याज व चक्रवाढ व्याज', @ch6, @subject_math_id, @class_8_id, @board_msce_id),
('नफा-तोटा, सूट (रिबेट), कमिशन (दलाली)', @ch6, @subject_math_id, @class_8_id, @board_msce_id);

-- Chapter 7: बीजगणित (18%)
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) 
VALUES ('बीजगणित', NULL, @subject_math_id, @class_8_id, @board_msce_id);

SET @ch7 := (SELECT id FROM chapters 
             WHERE chapter_name = 'बीजगणित' 
               AND subject_id = @subject_math_id 
               AND class_id = @class_8_id 
               AND board_id = @board_msce_id LIMIT 1);

INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) 
VALUES (@ch7, @class_8_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('संख्येसाठी अक्षर, बीजिक राशींचे अवयव, किंमत व त्यावरील क्रिया', @ch7, @subject_math_id, @class_8_id, @board_msce_id),
('नित्य समानता', @ch7, @subject_math_id, @class_8_id, @board_msce_id),
('एकचल समीकरणे व त्यावरील शाब्दिक उदाहरणे', @ch7, @subject_math_id, @class_8_id, @board_msce_id),
('बहुपदी, त्यावरील क्रिया व अवयव', @ch7, @subject_math_id, @class_8_id, @board_msce_id);

-- =============================================================================
-- End of Math syllabus insertion for Class 8
-- All entries use safe SELECT for chapter IDs and INSERT IGNORE to avoid duplicates
-- =============================================================================


--Std 8 – Marathi Medium – ENGLISH (Third Language)
SET @board_msce_id = (SELECT id FROM board WHERE board_name='MSCE' LIMIT 1);
SET @class_8th_id  = (SELECT id FROM class WHERE class_name='8' LIMIT 1);
SET @sub_eng       = (SELECT subject_id FROM subject WHERE subject_name='English' LIMIT 1);

INSERT IGNORE INTO subject_board_class_mapping
(subject_id, class_id, board_id, medium)
VALUES
(@sub_eng, @class_8th_id, @board_msce_id, 'Marathi');

INSERT INTO chapters
(chapter_name, unit, subject_id, class_id, board_id)
VALUES
('Vocabulary',        'Unit 1', @sub_eng, @class_8th_id, @board_msce_id),
('Word Puzzles',      'Unit 2', @sub_eng, @class_8th_id, @board_msce_id),
('Language Study',    'Unit 3', @sub_eng, @class_8th_id, @board_msce_id),
('Grammar',           'Unit 4', @sub_eng, @class_8th_id, @board_msce_id),
('Creative Writing',  'Unit 5', @sub_eng, @class_8th_id, @board_msce_id),
('Reading Skills',    'Unit 6', @sub_eng, @class_8th_id, @board_msce_id),
('Miscellaneous',     'Unit 7', @sub_eng, @class_8th_id, @board_msce_id);

--Unit 1 – Vocabulary (16%)
INSERT INTO topics (topic_name, chapter_id, subject_id, class_id, board_id)
SELECT 'Writing words using given clues', id, @sub_eng, @class_8th_id, @board_msce_id
FROM chapters WHERE chapter_name='Vocabulary';

INSERT INTO topics (topic_name, chapter_id, subject_id, class_id, board_id)
SELECT 'Contextual meanings of words', id, @sub_eng, @class_8th_id, @board_msce_id
FROM chapters WHERE chapter_name='Vocabulary';

INSERT INTO topics (topic_name, chapter_id, subject_id, class_id, board_id)
SELECT 'Opposite and similar meaning words', id, @sub_eng, @class_8th_id, @board_msce_id
FROM chapters WHERE chapter_name='Vocabulary';

INSERT INTO topics (topic_name, chapter_id, subject_id, class_id, board_id)
SELECT 'Word formation', id, @sub_eng, @class_8th_id, @board_msce_id
FROM chapters WHERE chapter_name='Vocabulary';

INSERT INTO topics (topic_name, chapter_id, subject_id, class_id, board_id)
SELECT 'Homophones', id, @sub_eng, @class_8th_id, @board_msce_id
FROM chapters WHERE chapter_name='Vocabulary';

INSERT INTO topics (topic_name, chapter_id, subject_id, class_id, board_id)
SELECT 'Phrases', id, @sub_eng, @class_8th_id, @board_msce_id
FROM chapters WHERE chapter_name='Vocabulary';

INSERT INTO topics (topic_name, chapter_id, subject_id, class_id, board_id)
SELECT 'One word substitution', id, @sub_eng, @class_8th_id, @board_msce_id
FROM chapters WHERE chapter_name='Vocabulary';

INSERT INTO topics (topic_name, chapter_id, subject_id, class_id, board_id)
SELECT 'Guessing the meaning of words', id, @sub_eng, @class_8th_id, @board_msce_id
FROM chapters WHERE chapter_name='Vocabulary';

-- Unit 2 – Word Puzzles (8%)
INSERT INTO topics
(topic_name, chapter_id, subject_id, class_id, board_id)
SELECT 'Crossword puzzles and riddles', id, @sub_eng, @class_8th_id, @board_msce_id
FROM chapters WHERE chapter_name='Word Puzzles';

INSERT INTO topics (topic_name, chapter_id, subject_id, class_id, board_id)
SELECT 'Grid puzzles', id, @sub_eng, @class_8th_id, @board_msce_id
FROM chapters WHERE chapter_name='Word Puzzles';

-- Unit 3 – Language Study (24%)
INSERT INTO topics
(topic_name, chapter_id, subject_id, class_id, board_id)
SELECT 'Sentence formation', id, @sub_eng, @class_8th_id, @board_msce_id
FROM chapters WHERE chapter_name='Language Study';

INSERT INTO topics (topic_name, chapter_id, subject_id, class_id, board_id)
SELECT 'Types of sentences', id, @sub_eng, @class_8th_id, @board_msce_id
FROM chapters WHERE chapter_name='Language Study';

INSERT INTO topics (topic_name, chapter_id, subject_id, class_id, board_id)
SELECT 'Parts of speech', id, @sub_eng, @class_8th_id, @board_msce_id
FROM chapters WHERE chapter_name='Language Study';

INSERT INTO topics (topic_name, chapter_id, subject_id, class_id, board_id)
SELECT 'Modal auxiliaries', id, @sub_eng, @class_8th_id, @board_msce_id
FROM chapters WHERE chapter_name='Language Study';

INSERT INTO topics (topic_name, chapter_id, subject_id, class_id, board_id)
SELECT 'Tenses', id, @sub_eng, @class_8th_id, @board_msce_id
FROM chapters WHERE chapter_name='Language Study';

-- Unit 4 – Grammar (12%)
INSERT INTO topics
(topic_name, chapter_id, subject_id, class_id, board_id)
SELECT 'Active and Passive Voice', id, @sub_eng, @class_8th_id, @board_msce_id
FROM chapters WHERE chapter_name='Grammar';

INSERT INTO topics (topic_name, chapter_id, subject_id, class_id, board_id)
SELECT 'Direct and Indirect Speech', id, @sub_eng, @class_8th_id, @board_msce_id
FROM chapters WHERE chapter_name='Grammar';

INSERT INTO topics (topic_name, chapter_id, subject_id, class_id, board_id)
SELECT 'Degrees of comparison', id, @sub_eng, @class_8th_id, @board_msce_id
FROM chapters WHERE chapter_name='Grammar';

-- Unit 5 – Creative Writing (16%)
INSERT INTO topics
(topic_name, chapter_id, subject_id, class_id, board_id)
SELECT 'Short note', id, @sub_eng, @class_8th_id, @board_msce_id
FROM chapters WHERE chapter_name='Creative Writing';

INSERT INTO topics (topic_name, chapter_id, subject_id, class_id, board_id)
SELECT 'Dialogue writing', id, @sub_eng, @class_8th_id, @board_msce_id
FROM chapters WHERE chapter_name='Creative Writing';

INSERT INTO topics (topic_name, chapter_id, subject_id, class_id, board_id)
SELECT 'Slogans', id, @sub_eng, @class_8th_id, @board_msce_id
FROM chapters WHERE chapter_name='Creative Writing';

INSERT INTO topics (topic_name, chapter_id, subject_id, class_id, board_id)
SELECT 'Advertisements', id, @sub_eng, @class_8th_id, @board_msce_id
FROM chapters WHERE chapter_name='Creative Writing';

INSERT INTO topics (topic_name, chapter_id, subject_id, class_id, board_id)
SELECT 'News writing', id, @sub_eng, @class_8th_id, @board_msce_id
FROM chapters WHERE chapter_name='Creative Writing';

INSERT INTO topics (topic_name, chapter_id, subject_id, class_id, board_id)
SELECT 'E-mail writing', id, @sub_eng, @class_8th_id, @board_msce_id
FROM chapters WHERE chapter_name='Creative Writing';

INSERT INTO topics (topic_name, chapter_id, subject_id, class_id, board_id)
SELECT 'Websites', id, @sub_eng, @class_8th_id, @board_msce_id
FROM chapters WHERE chapter_name='Creative Writing';

INSERT INTO topics (topic_name, chapter_id, subject_id, class_id, board_id)
SELECT 'SMS language', id, @sub_eng, @class_8th_id, @board_msce_id
FROM chapters WHERE chapter_name='Creative Writing';

-- Unit 6 – Reading Skills (20%)
INSERT INTO topics
(topic_name, chapter_id, subject_id, class_id, board_id)
SELECT 'Prose (70 to 80 words)', id, @sub_eng, @class_8th_id, @board_msce_id
FROM chapters WHERE chapter_name='Reading Skills';

-- Unit 7 – Miscellaneous (4%)
INSERT INTO topics
(topic_name, chapter_id, subject_id, class_id, board_id)
SELECT 'Games', id, @sub_eng, @class_8th_id, @board_msce_id
FROM chapters WHERE chapter_name='Miscellaneous';

INSERT INTO topics (topic_name, chapter_id, subject_id, class_id, board_id)
SELECT 'Clock', id, @sub_eng, @class_8th_id, @board_msce_id
FROM chapters WHERE chapter_name='Miscellaneous';

--Std 8 – Marathi Medium – IQ – Marathi (बुद्धिमत्ता चाचणी)

--PRE UPPER PRIMARY SCHOLARSHIP EXAMINATION – MSCE

SET @sub_iq        = (SELECT subject_id FROM subject WHERE subject_name='IQ – Marathi' LIMIT 1);

INSERT IGNORE INTO subject_board_class_mapping
(subject_id, class_id, board_id, medium)
VALUES
(@sub_iq, @class_8th_id, @board_msce_id, 'Marathi');

INSERT INTO chapters
(chapter_name, unit, subject_id, class_id, board_id)
VALUES
('आकलन',                 'Unit 1', @sub_iq, @class_8th_id, @board_msce_id),
('वर्गीकरण',              'Unit 2', @sub_iq, @class_8th_id, @board_msce_id),
('संबंध',                'Unit 3', @sub_iq, @class_8th_id, @board_msce_id),
('क्रम',                  'Unit 4', @sub_iq, @class_8th_id, @board_msce_id),
('सांकेतिक भाषा',          'Unit 5', @sub_iq, @class_8th_id, @board_msce_id),
('लयबद्ध मांडणी',          'Unit 6', @sub_iq, @class_8th_id, @board_msce_id),
('मनोगत',                'Unit 7', @sub_iq, @class_8th_id, @board_msce_id),
('प्रतिबिंब / प्रतिमा',    'Unit 8', @sub_iq, @class_8th_id, @board_msce_id),
('तर्क व अनुमान',         'Unit 9', @sub_iq, @class_8th_id, @board_msce_id),
('कूट प्रश्न',            'Unit 10',@sub_iq, @class_8th_id, @board_msce_id),
('आकृतीचे पृथक्करण',      'Unit 11',@sub_iq, @class_8th_id, @board_msce_id);

--Unit 1 – आकलन (10%)
INSERT INTO topics (topic_name, chapter_id, subject_id, class_id, board_id)
SELECT 'सूचना पालन, वर्णन व मजकूर', id, @sub_iq, @class_8th_id, @board_msce_id
FROM chapters WHERE chapter_name='आकलन';

INSERT INTO topics (topic_name, chapter_id, subject_id, class_id, board_id)
SELECT 'भाषा-ज्ञान', id, @sub_iq, @class_8th_id, @board_msce_id
FROM chapters WHERE chapter_name='आकलन';

INSERT INTO topics (topic_name, chapter_id, subject_id, class_id, board_id)
SELECT 'इंग्रजी अक्षरमाला', id, @sub_iq, @class_8th_id, @board_msce_id
FROM chapters WHERE chapter_name='आकलन';

-- Unit 2 – वर्गीकरण (10%)
INSERT INTO topics
(topic_name, chapter_id, subject_id, class_id, board_id)
SELECT 'शब्दसंग्रह', id, @sub_iq, @class_8th_id, @board_msce_id
FROM chapters WHERE chapter_name='वर्गीकरण';

INSERT INTO topics (topic_name, chapter_id, subject_id, class_id, board_id)
SELECT 'आकृत्या', id, @sub_iq, @class_8th_id, @board_msce_id
FROM chapters WHERE chapter_name='वर्गीकरण';

INSERT INTO topics (topic_name, chapter_id, subject_id, class_id, board_id)
SELECT 'संख्या', id, @sub_iq, @class_8th_id, @board_msce_id
FROM chapters WHERE chapter_name='वर्गीकरण';

INSERT INTO topics (topic_name, chapter_id, subject_id, class_id, board_id)
SELECT 'इंग्रजी वर्णमाला', id, @sub_iq, @class_8th_id, @board_msce_id
FROM chapters WHERE chapter_name='वर्गीकरण';

-- Unit 3 – संबंध (10%)
INSERT INTO topics (topic_name, chapter_id, subject_id, class_id, board_id)
SELECT 'शब्दसंग्रह', id, @sub_iq, @class_8th_id, @board_msce_id
FROM chapters WHERE chapter_name='संबंध';

INSERT INTO topics (topic_name, chapter_id, subject_id, class_id, board_id)
SELECT 'आकृत्या', id, @sub_iq, @class_8th_id, @board_msce_id
FROM chapters WHERE chapter_name='संबंध';

INSERT INTO topics (topic_name, chapter_id, subject_id, class_id, board_id)
SELECT 'संख्या', id, @sub_iq, @class_8th_id, @board_msce_id
FROM chapters WHERE chapter_name='संबंध';

INSERT INTO topics (topic_name, chapter_id, subject_id, class_id, board_id)
SELECT 'इंग्रजी वर्णमाला', id, @sub_iq, @class_8th_id, @board_msce_id
FROM chapters WHERE chapter_name='संबंध';

-- Unit 4 – क्रम (12%)
INSERT INTO topics (topic_name, chapter_id, subject_id, class_id, board_id)
SELECT 'संख्या', id, @sub_iq, @class_8th_id, @board_msce_id
FROM chapters WHERE chapter_name='क्रम';

INSERT INTO topics (topic_name, chapter_id, subject_id, class_id, board_id)
SELECT 'आकृत्या', id, @sub_iq, @class_8th_id, @board_msce_id
FROM chapters WHERE chapter_name='क्रम';

INSERT INTO topics (topic_name, chapter_id, subject_id, class_id, board_id)
SELECT 'चिन्हे', id, @sub_iq, @class_8th_id, @board_msce_id
FROM chapters WHERE chapter_name='क्रम';

INSERT INTO topics (topic_name, chapter_id, subject_id, class_id, board_id)
SELECT 'चुकीचे पद ओळखणे', id, @sub_iq, @class_8th_id, @board_msce_id
FROM chapters WHERE chapter_name='क्रम';

INSERT INTO topics (topic_name, chapter_id, subject_id, class_id, board_id)
SELECT 'इंग्रजी वर्णमाला', id, @sub_iq, @class_8th_id, @board_msce_id
FROM chapters WHERE chapter_name='क्रम';

-- Unit 5 – सांकेतिक भाषा (6%)
INSERT INTO topics (topic_name, chapter_id, subject_id, class_id, board_id)
SELECT 'आकृत्या, अंक, अक्षरे', id, @sub_iq, @class_8th_id, @board_msce_id
FROM chapters WHERE chapter_name='सांकेतिक भाषा';

-- Unit 6 – लयबद्ध मांडणी (6%)
INSERT INTO topics (topic_name, chapter_id, subject_id, class_id, board_id)
SELECT 'अक्षरांचा वापर', id, @sub_iq, @class_8th_id, @board_msce_id
FROM chapters WHERE chapter_name='लयबद्ध मांडणी';

INSERT INTO topics (topic_name, chapter_id, subject_id, class_id, board_id)
SELECT 'चिन्हांचा वापर', id, @sub_iq, @class_8th_id, @board_msce_id
FROM chapters WHERE chapter_name='लयबद्ध मांडणी';

INSERT INTO topics (topic_name, chapter_id, subject_id, class_id, board_id)
SELECT 'अंकांचा वापर', id, @sub_iq, @class_8th_id, @board_msce_id
FROM chapters WHERE chapter_name='लयबद्ध मांडणी';

-- Unit 7 – मनोगत (6%)
INSERT INTO topics (topic_name, chapter_id, subject_id, class_id, board_id)
SELECT 'अंकांचा वापर', id, @sub_iq, @class_8th_id, @board_msce_id
FROM chapters WHERE chapter_name='मनोगत';

INSERT INTO topics (topic_name, chapter_id, subject_id, class_id, board_id)
SELECT 'अक्षरांचा वापर', id, @sub_iq, @class_8th_id, @board_msce_id
FROM chapters WHERE chapter_name='मनोगत';

-- Unit 8 – प्रतिबिंब / प्रतिमा (6%)
INSERT INTO topics (topic_name, chapter_id, subject_id, class_id, board_id)
SELECT 'अंक', id, @sub_iq, @class_8th_id, @board_msce_id
FROM chapters WHERE chapter_name='प्रतिबिंब / प्रतिमा';

INSERT INTO topics (topic_name, chapter_id, subject_id, class_id, board_id)
SELECT 'अक्षरे', id, @sub_iq, @class_8th_id, @board_msce_id
FROM chapters WHERE chapter_name='प्रतिबिंब / प्रतिमा';

INSERT INTO topics (topic_name, chapter_id, subject_id, class_id, board_id)
SELECT 'आकृती', id, @sub_iq, @class_8th_id, @board_msce_id
FROM chapters WHERE chapter_name='प्रतिबिंब / प्रतिमा';

-- Unit 9 – तर्क व अनुमान (12%)
INSERT INTO topics (topic_name, chapter_id, subject_id, class_id, board_id)
SELECT 'भाषिक तर्क व अनुमान', id, @sub_iq, @class_8th_id, @board_msce_id
FROM chapters WHERE chapter_name='तर्क व अनुमान';

INSERT INTO topics (topic_name, chapter_id, subject_id, class_id, board_id)
SELECT 'अभाषिक तर्क', id, @sub_iq, @class_8th_id, @board_msce_id
FROM chapters WHERE chapter_name='तर्क व अनुमान';

INSERT INTO topics (topic_name, chapter_id, subject_id, class_id, board_id)
SELECT 'संख्यांवरील अनुमान', id, @sub_iq, @class_8th_id, @board_msce_id
FROM chapters WHERE chapter_name='तर्क व अनुमान';

-- Unit 10 – कूट प्रश्न (12%)
INSERT INTO topics (topic_name, chapter_id, subject_id, class_id, board_id)
SELECT 'रांगेतील स्थान', id, @sub_iq, @class_8th_id, @board_msce_id
FROM chapters WHERE chapter_name='कूट प्रश्न';

INSERT INTO topics (topic_name, chapter_id, subject_id, class_id, board_id)
SELECT 'दिशावरील प्रश्न', id, @sub_iq, @class_8th_id, @board_msce_id
FROM chapters WHERE chapter_name='कूट प्रश्न';

INSERT INTO topics (topic_name, chapter_id, subject_id, class_id, board_id)
SELECT 'दिनदर्शिका', id, @sub_iq, @class_8th_id, @board_msce_id
FROM chapters WHERE chapter_name='कूट प्रश्न';

INSERT INTO topics (topic_name, chapter_id, subject_id, class_id, board_id)
SELECT 'वेळ आकृती', id, @sub_iq, @class_8th_id, @board_msce_id
FROM chapters WHERE chapter_name='कूट प्रश्न';

INSERT INTO topics (topic_name, chapter_id, subject_id, class_id, board_id)
SELECT 'घड्याळ कोडे', id, @sub_iq, @class_8th_id, @board_msce_id
FROM chapters WHERE chapter_name='कूट प्रश्न';

-- Unit 11 – आकृतीचे पृथक्करण (10%)
INSERT INTO topics (topic_name, chapter_id, subject_id, class_id, board_id)
SELECT 'अपूर्ण आकृती पूर्ण करणे', id, @sub_iq, @class_8th_id, @board_msce_id
FROM chapters WHERE chapter_name='आकृतीचे पृथक्करण';

INSERT INTO topics (topic_name, chapter_id, subject_id, class_id, board_id)
SELECT 'तंतोतंत आकृती ओळखणे', id, @sub_iq, @class_8th_id, @board_msce_id
FROM chapters WHERE chapter_name='आकृतीचे पृथक्करण';

INSERT INTO topics (topic_name, chapter_id, subject_id, class_id, board_id)
SELECT 'घडीची आकृती', id, @sub_iq, @class_8th_id, @board_msce_id
FROM chapters WHERE chapter_name='आकृतीचे पृथक्करण';

INSERT INTO topics (topic_name, chapter_id, subject_id, class_id, board_id)
SELECT 'लपलेली आकृती शोधणे', id, @sub_iq, @class_8th_id, @board_msce_id
FROM chapters WHERE chapter_name='आकृतीचे पृथक्करण';


COMMIT;

SET FOREIGN_KEY_CHECKS = 1;