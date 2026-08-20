--liquibase formatted sql
--changeset {narendra}:{id}

-- MSCE PRE UPPER PRIMARY SCHOLARSHIP EXAMINATION - CLASS 5 Marathi Medium
-- Complete Ready-to-Run Script (Class ID from class_name='5')

SET NAMES utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Here is the SQL script to insert the syllabus data for "Marathi – First Language" for the 5th Standard, Marathi Medium, based on the provided PDF document.

SET FOREIGN_KEY_CHECKS = 0;

START TRANSACTION;

-- 1. Insert Board (MSCE) if not exists and get its ID
INSERT IGNORE INTO board (tenant_id, board_name) VALUES (NULL, 'MSCE');
SET @board_msce_id = (SELECT id FROM board WHERE board_name = 'MSCE' LIMIT 1);

-- 2. Insert Class 5 if not exists and get its ID
INSERT IGNORE INTO class (class_name) VALUES ('5');
SET @class_5_id = (SELECT id FROM class WHERE class_name = '5' LIMIT 1);

-- 3. Insert Subject 'Marathi – First Language' if not exists and get its ID
INSERT IGNORE INTO subject (subject_name) VALUES ('Marathi – First Language');
SET @subject_marathi_fl_id = (SELECT subject_id FROM subject WHERE subject_name = 'Marathi – First Language' LIMIT 1);

-- 4. Map Subject to Board, Class, and Medium (Marathi Medium)
INSERT IGNORE INTO subject_board_class_mapping (subject_id, class_id, board_id, medium) VALUES
(@subject_marathi_fl_id, @class_5_id, @board_msce_id, 'Marathi');

-- 5. Insert Chapters and Topics for "Marathi – First Language" (5th Std, Marathi Medium)

-- Chapter: वाचून कल्पना व संकल्पना स्पष्ट करणे (Read and clarify ideas and concepts)
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES ('वाचून कल्पना व संकल्पना स्पष्ट करणे', NULL, @subject_marathi_fl_id, @class_5_id, @board_msce_id);
SET @chapter_reading_comprehension_id = (SELECT id FROM chapters WHERE chapter_name = 'वाचून कल्पना व संकल्पना स्पष्ट करणे' AND subject_id = @subject_marathi_fl_id AND class_id = @class_5_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@chapter_reading_comprehension_id, @class_5_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('उतारा व त्यावर आधारित प्रश्न', @chapter_reading_comprehension_id, @subject_marathi_fl_id, @class_5_id, @board_msce_id),
('वृत्तपत्रातील जाहिराती व बातम्यांवर आधारित प्रश्न', @chapter_reading_comprehension_id, @subject_marathi_fl_id, @class_5_id, @board_msce_id),
('कविता व त्यावर आधारित प्रश्न', @chapter_reading_comprehension_id, @subject_marathi_fl_id, @class_5_id, @board_msce_id);

-- Chapter: कार्यात्मक व्याकरण (Functional Grammar)
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES ('कार्यात्मक व्याकरण', NULL, @subject_marathi_fl_id, @class_5_id, @board_msce_id);
SET @chapter_functional_grammar_id = (SELECT id FROM chapters WHERE chapter_name = 'कार्यात्मक व्याकरण' AND subject_id = @subject_marathi_fl_id AND class_id = @class_5_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@chapter_functional_grammar_id, @class_5_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('शब्दांच्या जाती- नाम, सर्वनाम, विशेषण, क्रियापद', @chapter_functional_grammar_id, @subject_marathi_fl_id, @class_5_id, @board_msce_id),
('लिंग', @chapter_functional_grammar_id, @subject_marathi_fl_id, @class_5_id, @board_msce_id),
('वचन', @chapter_functional_grammar_id, @subject_marathi_fl_id, @class_5_id, @board_msce_id),
('काळ', @chapter_functional_grammar_id, @subject_marathi_fl_id, @class_5_id, @board_msce_id),
('विरामचिन्हे', @chapter_functional_grammar_id, @subject_marathi_fl_id, @class_5_id, @board_msce_id),
('वाक्यांचे भाग', @chapter_functional_grammar_id, @subject_marathi_fl_id, @class_5_id, @board_msce_id),
('शुध्द/अशुध्द शब्द', @chapter_functional_grammar_id, @subject_marathi_fl_id, @class_5_id, @board_msce_id);

-- Chapter: भाषेचा व्यवहारात उपयोग (Practical Use of Language)
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES ('भाषेचा व्यवहारात उपयोग', NULL, @subject_marathi_fl_id, @class_5_id, @board_msce_id);
SET @chapter_practical_language_id = (SELECT id FROM chapters WHERE chapter_name = 'भाषेचा व्यवहारात उपयोग' AND subject_id = @subject_marathi_fl_id AND class_id = @class_5_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@chapter_practical_language_id, @class_5_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('वाक्प्रचार', @chapter_practical_language_id, @subject_marathi_fl_id, @class_5_id, @board_msce_id),
('म्हणी', @chapter_practical_language_id, @subject_marathi_fl_id, @class_5_id, @board_msce_id),
('संवादावर आधारित प्रश्न', @chapter_practical_language_id, @subject_marathi_fl_id, @class_5_id, @board_msce_id),
('निर्देश - अ) योग्य शब्दांचा वापर करून वाक्य पूर्ण करणे', @chapter_practical_language_id, @subject_marathi_fl_id, @class_5_id, @board_msce_id),
('आ) सुसंगत वाक्यांचा परिच्छेद', @chapter_practical_language_id, @subject_marathi_fl_id, @class_5_id, @board_msce_id),
('इ) वाक्यांच्या वेगवेगळ्या भागातील चुकीचा भाग ओळखणे', @chapter_practical_language_id, @subject_marathi_fl_id, @class_5_id, @board_msce_id),
('ई) सर्वोत्कृष्ट विकल्प निवडून वाक्य पूर्ण करणे', @chapter_practical_language_id, @subject_marathi_fl_id, @class_5_id, @board_msce_id),
('माहिती तंत्रज्ञान विषयक व मराठी भाषेत वापरल्या जाणाऱ्या इंग्रजी शब्दांना पर्यायी मराठी शब्द शोधणे', @chapter_practical_language_id, @subject_marathi_fl_id, @class_5_id, @board_msce_id);

-- Chapter: शब्दसंपत्तीवरील प्रभुत्व (Mastery over Vocabulary)
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES ('शब्दसंपत्तीवरील प्रभुत्व', NULL, @subject_marathi_fl_id, @class_5_id, @board_msce_id);
SET @chapter_vocabulary_mastery_id = (SELECT id FROM chapters WHERE chapter_name = 'शब्दसंपत्तीवरील प्रभुत्व' AND subject_id = @subject_marathi_fl_id AND class_id = @class_5_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@chapter_vocabulary_mastery_id, @class_5_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('समानार्थी शब्द', @chapter_vocabulary_mastery_id, @subject_marathi_fl_id, @class_5_id, @board_msce_id),
('विरूध्दार्थी शब्द', @chapter_vocabulary_mastery_id, @subject_marathi_fl_id, @class_5_id, @board_msce_id),
('शब्दकोडी', @chapter_vocabulary_mastery_id, @subject_marathi_fl_id, @class_5_id, @board_msce_id),
('जोडशब्द', @chapter_vocabulary_mastery_id, @subject_marathi_fl_id, @class_5_id, @board_msce_id),
('आलंकारिक शब्द', @chapter_vocabulary_mastery_id, @subject_marathi_fl_id, @class_5_id, @board_msce_id),
('शब्दसमूहाबद्दल एक शब्द', @chapter_vocabulary_mastery_id, @subject_marathi_fl_id, @class_5_id, @board_msce_id),
('समूहदर्शक शब्द', @chapter_vocabulary_mastery_id, @subject_marathi_fl_id, @class_5_id, @board_msce_id),
('पिलुदर्शक शब्द', @chapter_vocabulary_mastery_id, @subject_marathi_fl_id, @class_5_id, @board_msce_id),
('घरदर्शक शब्द', @chapter_vocabulary_mastery_id, @subject_marathi_fl_id, @class_5_id, @board_msce_id),
('अक्षरे जुळवून अर्थपूर्ण शब्द तयार करणे', @chapter_vocabulary_mastery_id, @subject_marathi_fl_id, @class_5_id, @board_msce_id),
('वर्णानुक्रमे शब्दक्रम लावणे', @chapter_vocabulary_mastery_id, @subject_marathi_fl_id, @class_5_id, @board_msce_id),
('एकाच शब्दाचे भिन्न अर्थ शोधणे', @chapter_vocabulary_mastery_id, @subject_marathi_fl_id, @class_5_id, @board_msce_id),
('भाषा विषयक सामान्यज्ञान - लेखक, कवी, संत, शास्त्रज्ञ, खेळाडू, राष्ट्रीय प्रतिके, दिनविशेष, शौर्य पदके, पुरस्कार, ग्रंथ, खेळ, रंगछटा, चव आणि प्रसिध्द् संबोधने', @chapter_vocabulary_mastery_id, @subject_marathi_fl_id, @class_5_id, @board_msce_id);


-- Here is the SQL script to insert the syllabus data for "Math – Marathi" for the 5th Standard, Marathi Medium, based on the provided PDF document. This script ensures all Marathi content is handled correctly with UTF-8 encoding.

-- 3. Insert Subject 'Math – Marathi' if not exists and get its ID
INSERT IGNORE INTO subject (subject_name) VALUES ('Math – Marathi');
SET @subject_math_marathi_id = (SELECT subject_id FROM subject WHERE subject_name = 'Math – Marathi' LIMIT 1);

-- 4. Map Subject to Board, Class, and Medium (Marathi Medium)
INSERT IGNORE INTO subject_board_class_mapping (subject_id, class_id, board_id, medium) VALUES
(@subject_math_marathi_id, @class_5_id, @board_msce_id, 'Marathi');

-- 5. Insert Chapters and Topics for "Math – Marathi" (5th Std, Marathi Medium)

-- Chapter: संख्याज्ञान (Number Knowledge)
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES ('संख्याज्ञान', NULL, @subject_math_marathi_id, @class_5_id, @board_msce_id);
SET @chapter_sankhyagnan_id = (SELECT id FROM chapters WHERE chapter_name = 'संख्याज्ञान' AND subject_id = @subject_math_marathi_id AND class_id = @class_5_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@chapter_sankhyagnan_id, @class_5_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('आंतरराष्ट्रीय व रोमन संख्याचिन्हे', @chapter_sankhyagnan_id, @subject_math_marathi_id, @class_5_id, @board_msce_id),
('दहा अंकांपर्यंतच्या संख्यांचे वाचन व लेखन', @chapter_sankhyagnan_id, @subject_math_marathi_id, @class_5_id, @board_msce_id),
('अंकांची दर्शनी किंमत, स्थानिक किंमत व विस्तारित मांडणी', @chapter_sankhyagnan_id, @subject_math_marathi_id, @class_5_id, @board_msce_id),
('मोठ्यात मोठी व लहानात लहान संख्या तयार करणे', @chapter_sankhyagnan_id, @subject_math_marathi_id, @class_5_id, @board_msce_id),
('संख्यांचा चढता-उतरता क्रम व तुलना', @chapter_sankhyagnan_id, @subject_math_marathi_id, @class_5_id, @board_msce_id),
('१ ते १०० संख्यांवर आधारित प्रश्न', @chapter_sankhyagnan_id, @subject_math_marathi_id, @class_5_id, @board_msce_id),
('सम, विषम, मूळ, जोडमूळ, सहमूळ, संयुक्त, त्रिकोणी व चौरस संख्या', @chapter_sankhyagnan_id, @subject_math_marathi_id, @class_5_id, @board_msce_id);

-- Chapter: संख्यांवरील क्रिया (Operations on Numbers)
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES ('संख्यांवरील क्रिया', NULL, @subject_math_marathi_id, @class_5_id, @board_msce_id);
SET @chapter_operations_on_numbers_id = (SELECT id FROM chapters WHERE chapter_name = 'संख्यांवरील क्रिया' AND subject_id = @subject_math_marathi_id AND class_id = @class_5_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@chapter_operations_on_numbers_id, @class_5_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('बेरीज (सात अंकी संख्यांपर्यंत) हातच्याची बेरीज, शाब्दिक उदाहरणे', @chapter_operations_on_numbers_id, @subject_math_marathi_id, @class_5_id, @board_msce_id),
('वजाबाकी (सात अंकी संख्यांपर्यंत) हातच्याची वजाबाकी, शाब्दिक उदाहरणे', @chapter_operations_on_numbers_id, @subject_math_marathi_id, @class_5_id, @board_msce_id),
('गुणाकार (पाच अंकी गुणिले तीन अंकी संख्येपर्यंत)', @chapter_operations_on_numbers_id, @subject_math_marathi_id, @class_5_id, @board_msce_id),
('भागाकार (पाच अंकी भागिले दोन अंकी संख्येपर्यंत)', @chapter_operations_on_numbers_id, @subject_math_marathi_id, @class_5_id, @board_msce_id),
('पदावली व अक्षरांचा उपयोग', @chapter_operations_on_numbers_id, @subject_math_marathi_id, @class_5_id, @board_msce_id),
('संख्यांचे विभाजक (अवयव) व विभाज्य, एक ते दहा पर्यंतच्या विभाज्यतेच्या कसोटया', @chapter_operations_on_numbers_id, @subject_math_marathi_id, @class_5_id, @board_msce_id);

-- Chapter: अपूर्णांक (Fractions)
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES ('अपूर्णांक', NULL, @subject_math_marathi_id, @class_5_id, @board_msce_id);
SET @chapter_fractions_id = (SELECT id FROM chapters WHERE chapter_name = 'अपूर्णांक' AND subject_id = @subject_math_marathi_id AND class_id = @class_5_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@chapter_fractions_id, @class_5_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('व्यवहारी अपूर्णांक (समच्छेद व भिन्नच्छेद अपूर्णांकाचा लहानमोठेपणा, चढता-उतरता क्रम, बेरीज, वजाबाकी, गुणाकार)', @chapter_fractions_id, @subject_math_marathi_id, @class_5_id, @board_msce_id),
('व्यवहारी अपूर्णांक (अंशाधिक, छेदाधिक व पूर्णांकयुक्त अपूर्णांक- परस्पर रूपांतर)', @chapter_fractions_id, @subject_math_marathi_id, @class_5_id, @board_msce_id),
('व्यवहारी अपूर्णांक (सममूल्य अपूर्णांक)', @chapter_fractions_id, @subject_math_marathi_id, @class_5_id, @board_msce_id),
('दशांश अपूर्णांक (वाचन, लेखन)', @chapter_fractions_id, @subject_math_marathi_id, @class_5_id, @board_msce_id),
('दशांश अपूर्णांक (स्थानिक किंमत, दशांश अपूर्णांक उपयोग)', @chapter_fractions_id, @subject_math_marathi_id, @class_5_id, @board_msce_id),
('दशांश अपूर्णांक (बेरीज, वजाबाकी)', @chapter_fractions_id, @subject_math_marathi_id, @class_5_id, @board_msce_id);

-- Chapter: मापन/महत्त्वमापन (Measurement/Metrology)
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES ('मापन/महत्त्वमापन', NULL, @subject_math_marathi_id, @class_5_id, @board_msce_id);
SET @chapter_measurement_id = (SELECT id FROM chapters WHERE chapter_name = 'मापन/महत्त्वमापन' AND subject_id = @subject_math_marathi_id AND class_id = @class_5_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@chapter_measurement_id, @class_5_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('लांबी, वस्तुमान, धारकता (दशमान परिमाण)- परस्पर रूपांतर, बेरीज, वजाबाकी व शाब्दिक उदाहरणे', @chapter_measurement_id, @subject_math_marathi_id, @class_5_id, @board_msce_id),
('कालमापन : घडयाळ - (मध्यान्हपूर्व, माध्यान्होत्तर) तास, मिनिटे, सेकंद - परस्पर रूपांतर, बेरीज, वजाबाकी व शाब्दिक उदाहरणे', @chapter_measurement_id, @subject_math_marathi_id, @class_5_id, @board_msce_id),
('दिनदर्शिका', @chapter_measurement_id, @subject_math_marathi_id, @class_5_id, @board_msce_id),
('कागदमापन (रीम, दस्ता)', @chapter_measurement_id, @subject_math_marathi_id, @class_5_id, @board_msce_id),
('नाणी-नोटा (रूपये-पैसे)- परस्पर रूपांतर, मूलभूत क्रियांवर आधारित खरेदी व विक्रीसंबंधी उदाहरणे', @chapter_measurement_id, @subject_math_marathi_id, @class_5_id, @board_msce_id);

-- Chapter: व्यावहारिक गणित (Practical Mathematics)
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES ('व्यावहारिक गणित', NULL, @subject_math_marathi_id, @class_5_id, @board_msce_id);
SET @chapter_practical_math_id = (SELECT id FROM chapters WHERE chapter_name = 'व्यावहारिक गणित' AND subject_id = @subject_math_marathi_id AND class_id = @class_5_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@chapter_practical_math_id, @class_5_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('नफा-तोटा, शेकडेवारी, सरळव्याज (प्राथमिक माहितीवर आधारित उदाहरणे)', @chapter_practical_math_id, @subject_math_marathi_id, @class_5_id, @board_msce_id);

-- Chapter: भूमिती (Geometry)
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES ('भूमिती', NULL, @subject_math_marathi_id, @class_5_id, @board_msce_id);
SET @chapter_geometry_id = (SELECT id FROM chapters WHERE chapter_name = 'भूमिती' AND subject_id = @subject_math_marathi_id AND class_id = @class_5_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@chapter_geometry_id, @class_5_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('कोन व त्यांचे प्रकार', @chapter_geometry_id, @subject_math_marathi_id, @class_5_id, @board_msce_id),
('समांतर व लंब रेषा', @chapter_geometry_id, @subject_math_marathi_id, @class_5_id, @board_msce_id),
('त्रिकोण, चौरस, बाजू, शिरोबिंदू', @chapter_geometry_id, @subject_math_marathi_id, @class_5_id, @board_msce_id),
('वर्तुळ-त्रिज्या, जीवा, व्यास, केंद्र, परिघ, अंतर्भाग, बाहयभाग, वर्तुळकंस', @chapter_geometry_id, @subject_math_marathi_id, @class_5_id, @board_msce_id),
('परिमिती-त्रिकोण, आयत, चौरस, बहुभुजाकृती', @chapter_geometry_id, @subject_math_marathi_id, @class_5_id, @board_msce_id),
('क्षेत्रफळ-आयत, चौरस', @chapter_geometry_id, @subject_math_marathi_id, @class_5_id, @board_msce_id),
('त्रिमिती वस्तू व घडणी', @chapter_geometry_id, @subject_math_marathi_id, @class_5_id, @board_msce_id),
('आकृतिबंध', @chapter_geometry_id, @subject_math_marathi_id, @class_5_id, @board_msce_id),
('इष्टिकाचिती व घन (कडा, शिरोबिंदू, पृष्ठे)', @chapter_geometry_id, @subject_math_marathi_id, @class_5_id, @board_msce_id);

-- Chapter: चित्रालेख (Pictograph)
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES ('चित्रालेख', NULL, @subject_math_marathi_id, @class_5_id, @board_msce_id);
SET @chapter_pictograph_id = (SELECT id FROM chapters WHERE chapter_name = 'चित्रालेख' AND subject_id = @subject_math_marathi_id AND class_id = @class_5_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@chapter_pictograph_id, @class_5_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('चित्ररूप माहितीचे आकलन', @chapter_pictograph_id, @subject_math_marathi_id, @class_5_id, @board_msce_id);



-- Here is the SQL script to insert the syllabus data for "English – Third Language" for the 5th Standard, Marathi Medium, based on the provided PDF document.


-- 3. Insert Subject 'English – Third Language' if not exists and get its ID
INSERT IGNORE INTO subject (subject_name) VALUES ('English – Third Language');
SET @subject_english_third_lang_id = (SELECT subject_id FROM subject WHERE subject_name = 'English – Third Language' LIMIT 1);

-- 4. Map Subject to Board, Class, and Medium (Marathi Medium as requested by user)
INSERT IGNORE INTO subject_board_class_mapping (subject_id, class_id, board_id, medium) VALUES
(@subject_english_third_lang_id, @class_5_id, @board_msce_id, 'Marathi');

-- 5. Insert Chapters and Topics for "English – Third Language" (5th Std, Marathi Medium)

-- Chapter: Letters of Alphabet
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES ('Letters of Alphabet', NULL, @subject_english_third_lang_id, @class_5_id, @board_msce_id);
SET @chapter_letters_alphabet_id = (SELECT id FROM chapters WHERE chapter_name = 'Letters of Alphabet' AND subject_id = @subject_english_third_lang_id AND class_id = @class_5_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@chapter_letters_alphabet_id, @class_5_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('Associating the name of a letter with its sound', @chapter_letters_alphabet_id, @subject_english_third_lang_id, @class_5_id, @board_msce_id),
('Formation of words using given alphabets', @chapter_letters_alphabet_id, @subject_english_third_lang_id, @class_5_id, @board_msce_id);

-- Chapter: Vocabulary
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES ('Vocabulary', NULL, @subject_english_third_lang_id, @class_5_id, @board_msce_id);
SET @chapter_vocabulary_id = (SELECT id FROM chapters WHERE chapter_name = 'Vocabulary' AND subject_id = @subject_english_third_lang_id AND class_id = @class_5_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@chapter_vocabulary_id, @class_5_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('Writing familiar/related words with the given clues/pictures', @chapter_vocabulary_id, @subject_english_third_lang_id, @class_5_id, @board_msce_id),
('Correlating words with pictures (action words, describing words)', @chapter_vocabulary_id, @subject_english_third_lang_id, @class_5_id, @board_msce_id),
('Rhyming words', @chapter_vocabulary_id, @subject_english_third_lang_id, @class_5_id, @board_msce_id),
('Writing opposite words', @chapter_vocabulary_id, @subject_english_third_lang_id, @class_5_id, @board_msce_id),
('Writing word register', @chapter_vocabulary_id, @subject_english_third_lang_id, @class_5_id, @board_msce_id),
('Finding small words from the bigger ones', @chapter_vocabulary_id, @subject_english_third_lang_id, @class_5_id, @board_msce_id),
('Using/writing contracted forms', @chapter_vocabulary_id, @subject_english_third_lang_id, @class_5_id, @board_msce_id),
('Dictionary skills', @chapter_vocabulary_id, @subject_english_third_lang_id, @class_5_id, @board_msce_id),
('Parts of body', @chapter_vocabulary_id, @subject_english_third_lang_id, @class_5_id, @board_msce_id),
('Names of birds and animals, their living places and sounds', @chapter_vocabulary_id, @subject_english_third_lang_id, @class_5_id, @board_msce_id),
('Comparisons (as.... as....)', @chapter_vocabulary_id, @subject_english_third_lang_id, @class_5_id, @board_msce_id),
('Homophones', @chapter_vocabulary_id, @subject_english_third_lang_id, @class_5_id, @board_msce_id),
('Names of colours, things, shapes, vegetables, fruits, games', @chapter_vocabulary_id, @subject_english_third_lang_id, @class_5_id, @board_msce_id);

-- Chapter: Punctuation marks
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES ('Punctuation marks', NULL, @subject_english_third_lang_id, @class_5_id, @board_msce_id);
SET @chapter_punctuation_marks_id = (SELECT id FROM chapters WHERE chapter_name = 'Punctuation marks' AND subject_id = @subject_english_third_lang_id AND class_id = @class_5_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@chapter_punctuation_marks_id, @class_5_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('Capitalisation', @chapter_punctuation_marks_id, @subject_english_third_lang_id, @class_5_id, @board_msce_id),
('Comma', @chapter_punctuation_marks_id, @subject_english_third_lang_id, @class_5_id, @board_msce_id),
('Full Stop', @chapter_punctuation_marks_id, @subject_english_third_lang_id, @class_5_id, @board_msce_id),
('Question Mark', @chapter_punctuation_marks_id, @subject_english_third_lang_id, @class_5_id, @board_msce_id),
('Apostrophe', @chapter_punctuation_marks_id, @subject_english_third_lang_id, @class_5_id, @board_msce_id),
('Exclamation Mark', @chapter_punctuation_marks_id, @subject_english_third_lang_id, @class_5_id, @board_msce_id);

-- Chapter: Numerical Information
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES ('Numerical Information', NULL, @subject_english_third_lang_id, @class_5_id, @board_msce_id);
SET @chapter_numerical_information_id = (SELECT id FROM chapters WHERE chapter_name = 'Numerical Information' AND subject_id = @subject_english_third_lang_id AND class_id = @class_5_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@chapter_numerical_information_id, @class_5_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('Days of the week/ months of the year', @chapter_numerical_information_id, @subject_english_third_lang_id, @class_5_id, @board_msce_id),
('Cardinal, ordinal numbers', @chapter_numerical_information_id, @subject_english_third_lang_id, @class_5_id, @board_msce_id),
('Showing the directions and subdirections, map reading', @chapter_numerical_information_id, @subject_english_third_lang_id, @class_5_id, @board_msce_id),
('Telling time', @chapter_numerical_information_id, @subject_english_third_lang_id, @class_5_id, @board_msce_id);

-- Chapter: Creative Thinking
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES ('Creative Thinking', NULL, @subject_english_third_lang_id, @class_5_id, @board_msce_id);
SET @chapter_creative_thinking_id = (SELECT id FROM chapters WHERE chapter_name = 'Creative Thinking' AND subject_id = @subject_english_third_lang_id AND class_id = @class_5_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@chapter_creative_thinking_id, @class_5_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('Advertisements, mottos, messages', @chapter_creative_thinking_id, @subject_english_third_lang_id, @class_5_id, @board_msce_id),
('Solving puzzles with the given clues', @chapter_creative_thinking_id, @subject_english_third_lang_id, @class_5_id, @board_msce_id),
('Solving riddles with the given clues', @chapter_creative_thinking_id, @subject_english_third_lang_id, @class_5_id, @board_msce_id);

-- Chapter: Stock expressions
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES ('Stock expressions', NULL, @subject_english_third_lang_id, @class_5_id, @board_msce_id);
SET @chapter_stock_expressions_id = (SELECT id FROM chapters WHERE chapter_name = 'Stock expressions' AND subject_id = @subject_english_third_lang_id AND class_id = @class_5_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@chapter_stock_expressions_id, @class_5_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('Greetings', @chapter_stock_expressions_id, @subject_english_third_lang_id, @class_5_id, @board_msce_id),
('Seeking permission', @chapter_stock_expressions_id, @subject_english_third_lang_id, @class_5_id, @board_msce_id),
('Making request', @chapter_stock_expressions_id, @subject_english_third_lang_id, @class_5_id, @board_msce_id),
('Corelation between instructions, expressions and pictures', @chapter_stock_expressions_id, @subject_english_third_lang_id, @class_5_id, @board_msce_id);

-- Chapter: Miscellaneous
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES ('Miscellaneous', NULL, @subject_english_third_lang_id, @class_5_id, @board_msce_id);
SET @chapter_miscellaneous_id = (SELECT id FROM chapters WHERE chapter_name = 'Miscellaneous' AND subject_id = @subject_english_third_lang_id AND class_id = @class_5_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@chapter_miscellaneous_id, @class_5_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('Articles', @chapter_miscellaneous_id, @subject_english_third_lang_id, @class_5_id, @board_msce_id),
('Prepositions', @chapter_miscellaneous_id, @subject_english_third_lang_id, @class_5_id, @board_msce_id),
('Adjectives', @chapter_miscellaneous_id, @subject_english_third_lang_id, @class_5_id, @board_msce_id),
('Adverbs', @chapter_miscellaneous_id, @subject_english_third_lang_id, @class_5_id, @board_msce_id),
('Sentence formation (tenses)', @chapter_miscellaneous_id, @subject_english_third_lang_id, @class_5_id, @board_msce_id),
('Singular and plural', @chapter_miscellaneous_id, @subject_english_third_lang_id, @class_5_id, @board_msce_id);

-- Chapter: Comprehension (Reading Skill)
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES ('Comprehension (Reading Skill)', NULL, @subject_english_third_lang_id, @class_5_id, @board_msce_id);
SET @chapter_comprehension_reading_skill_id = (SELECT id FROM chapters WHERE chapter_name = 'Comprehension (Reading Skill)' AND subject_id = @subject_english_third_lang_id AND class_id = @class_5_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@chapter_comprehension_reading_skill_id, @class_5_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('Prose (Upto 30 words)', @chapter_comprehension_reading_skill_id, @subject_english_third_lang_id, @class_5_id, @board_msce_id);


-- Here is the SQL script to insert the syllabus data for "IQ – Marathi" for the 5th Standard, Marathi Medium, based on the provided PDF document. This script ensures all Marathi content is handled correctly with UTF-8 encoding.

-- 3. Insert Subject 'IQ – Marathi' if not exists and get its ID
-- Use the provided board and class IDs
SET @board_msce_id = (SELECT id FROM board WHERE board_name = 'MSCE' LIMIT 1);
SET @class_5th_id = (SELECT id FROM class WHERE class_name = '5' LIMIT 1);

-- Define a variable for Marathi medium
SET @medium_marathi = 'Marathi';

-- 1. Insert Subjects if they don't exist and get their IDs
-- For 5th Std-Marathi Medium
-- Subject Display
-- Marathi – First Language
-- Math – Marathi
-- English – Third Language
-- IQ – Marathi (This will correspond to 'बुध्दिमत्ता चाचणी')

SET @subject_marathi_first_id = (SELECT subject_id FROM subject WHERE subject_name = 'Marathi – First Language' LIMIT 1);
SET @subject_math_marathi_id = (SELECT subject_id FROM subject WHERE subject_name = 'Math – Marathi' LIMIT 1);
SET @subject_english_third_id = (SELECT subject_id FROM subject WHERE subject_name = 'English – Third Language' LIMIT 1);
SET @subject_iq_marathi_id = (SELECT subject_id FROM subject WHERE subject_name = 'IQ – Marathi' LIMIT 1);

-- 2. Insert into subject_board_class_mapping
INSERT IGNORE INTO subject_board_class_mapping (subject_id, class_id, board_id, medium) VALUES
(@subject_marathi_first_id, @class_5th_id, @board_msce_id, @medium_marathi),
(@subject_math_marathi_id, @class_5th_id, @board_msce_id, @medium_marathi),
(@subject_english_third_id, @class_5th_id, @board_msce_id, @medium_marathi),
(@subject_iq_marathi_id, @class_5th_id, @board_msce_id, @medium_marathi);


-- 3. Insert Chapters (घटक) and Topics (उपघटक) for 'IQ – Marathi' subject
-- Using IFNULL and nested SELECTs to ensure IDs are correctly retrieved for each step

-- Chapter 1: आकलन (Comprehension)
INSERT IGNORE INTO chapters (chapter_name, subject_id, class_id, board_id) VALUES
('आकलन', @subject_iq_marathi_id, @class_5th_id, @board_msce_id);
SET @chapter_aakalan_id = (SELECT id FROM chapters WHERE chapter_name = 'आकलन' AND subject_id = @subject_iq_marathi_id AND class_id = @class_5th_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@chapter_aakalan_id, @class_5th_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('सूचनापालन : जोडाक्षरे, अक्षर, शब्द', @chapter_aakalan_id, @subject_iq_marathi_id, @class_5th_id, @board_msce_id),
('संख्यामालिका', @chapter_aakalan_id, @subject_iq_marathi_id, @class_5th_id, @board_msce_id),
('इंग्रजी अक्षरमाला', @chapter_aakalan_id, @subject_iq_marathi_id, @class_5th_id, @board_msce_id);


-- Chapter 2: वर्गीकरण (Classification)
INSERT IGNORE INTO chapters (chapter_name, subject_id, class_id, board_id) VALUES
('वर्गीकरण', @subject_iq_marathi_id, @class_5th_id, @board_msce_id);
SET @chapter_vargikaran_id = (SELECT id FROM chapters WHERE chapter_name = 'वर्गीकरण' AND subject_id = @subject_iq_marathi_id AND class_id = @class_5th_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@chapter_vargikaran_id, @class_5th_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('शब्दसंग्रह', @chapter_vargikaran_id, @subject_iq_marathi_id, @class_5th_id, @board_msce_id),
('आकृत्या', @chapter_vargikaran_id, @subject_iq_marathi_id, @class_5th_id, @board_msce_id),
('संख्या', @chapter_vargikaran_id, @subject_iq_marathi_id, @class_5th_id, @board_msce_id);


-- Chapter 3: क्रम ओळखणे (Identify the order)
INSERT IGNORE INTO chapters (chapter_name, subject_id, class_id, board_id) VALUES
('क्रम ओळखणे', @subject_iq_marathi_id, @class_5th_id, @board_msce_id);
SET @chapter_kram_olakne_id = (SELECT id FROM chapters WHERE chapter_name = 'क्रम ओळखणे' AND subject_id = @subject_iq_marathi_id AND class_id = @class_5th_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@chapter_kram_olakne_id, @class_5th_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('संख्यामालिका', @chapter_kram_olakne_id, @subject_iq_marathi_id, @class_5th_id, @board_msce_id),
('आकृत्यांची मालिका', @chapter_kram_olakne_id, @subject_iq_marathi_id, @class_5th_id, @board_msce_id),
('चिन्हांची मालिका', @chapter_kram_olakne_id, @subject_iq_marathi_id, @class_5th_id, @board_msce_id),
('चुकीचे पद ओळखणे', @chapter_kram_olakne_id, @subject_iq_marathi_id, @class_5th_id, @board_msce_id);


-- Chapter 4: तर्क संगती व अनुमान (Logical Reasoning and Deduction)
INSERT IGNORE INTO chapters (chapter_name, subject_id, class_id, board_id) VALUES
('तर्क संगती व अनुमान', @subject_iq_marathi_id, @class_5th_id, @board_msce_id);
SET @chapter_tark_sangat_anuman_id = (SELECT id FROM chapters WHERE chapter_name = 'तर्क संगती व अनुमान' AND subject_id = @subject_iq_marathi_id AND class_id = @class_5th_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@chapter_tark_sangat_anuman_id, @class_5th_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('भाषिक : वय, तुलना, नावात बदल, नाती', @chapter_tark_sangat_anuman_id, @subject_iq_marathi_id, @class_5th_id, @board_msce_id),
('अभाषिक : आकृत्या मोजणे - त्रिकोण, चौकोन, घनाकृती ठोकळे इ.', @chapter_tark_sangat_anuman_id, @subject_iq_marathi_id, @class_5th_id, @board_msce_id);


-- Chapter 5: प्रतिबिंब/प्रतिमा (Reflection/Image)
INSERT IGNORE INTO chapters (chapter_name, subject_id, class_id, board_id) VALUES
('प्रतिबिंब/प्रतिमा', @subject_iq_marathi_id, @class_5th_id, @board_msce_id);
SET @chapter_pratibimb_id = (SELECT id FROM chapters WHERE chapter_name = 'प्रतिबिंब/प्रतिमा' AND subject_id = @subject_iq_marathi_id AND class_id = @class_5th_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@chapter_pratibimb_id, @class_5th_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('आरशातील प्रतिमा (आकृत्या, अंक, अक्षरे )', @chapter_pratibimb_id, @subject_iq_marathi_id, @class_5th_id, @board_msce_id),
('जलप्रतिबिंब (आकृत्या, अंक, अक्षरे )', @chapter_pratibimb_id, @subject_iq_marathi_id, @class_5th_id, @board_msce_id);


-- Chapter 6: समसंबंध (Analogy)
INSERT IGNORE INTO chapters (chapter_name, subject_id, class_id, board_id) VALUES
('समसंबंध', @subject_iq_marathi_id, @class_5th_id, @board_msce_id);
SET @chapter_samsambandh_id = (SELECT id FROM chapters WHERE chapter_name = 'समसंबंध' AND subject_id = @subject_iq_marathi_id AND class_id = @class_5th_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@chapter_samsambandh_id, @class_5th_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('शब्दसंग्रह, आकृती, संख्या', @chapter_samsambandh_id, @subject_iq_marathi_id, @class_5th_id, @board_msce_id);


-- Chapter 7: समानपद ओळखणे (Identify the common term)
INSERT IGNORE INTO chapters (chapter_name, subject_id, class_id, board_id) VALUES
('समानपद ओळखणे', @subject_iq_marathi_id, @class_5th_id, @board_msce_id);
SET @chapter_samanpad_olakne_id = (SELECT id FROM chapters WHERE chapter_name = 'समानपद ओळखणे' AND subject_id = @subject_iq_marathi_id AND class_id = @class_5th_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@chapter_samanpad_olakne_id, @class_5th_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('आकृत्या', @chapter_samanpad_olakne_id, @subject_iq_marathi_id, @class_5th_id, @board_msce_id);


-- Chapter 8: कूटप्रश्न (Puzzles/Riddles)
INSERT IGNORE INTO chapters (chapter_name, subject_id, class_id, board_id) VALUES
('कूटप्रश्न', @subject_iq_marathi_id, @class_5th_id, @board_msce_id);
SET @chapter_kutprashna_id = (SELECT id FROM chapters WHERE chapter_name = 'कूटप्रश्न' AND subject_id = @subject_iq_marathi_id AND class_id = @class_5th_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@chapter_kutprashna_id, @class_5th_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('रांगेतील स्थान', @chapter_kutprashna_id, @subject_iq_marathi_id, @class_5th_id, @board_msce_id),
('दिशा', @chapter_kutprashna_id, @subject_iq_marathi_id, @class_5th_id, @board_msce_id),
('दिनदर्शिका', @chapter_kutprashna_id, @subject_iq_marathi_id, @class_5th_id, @board_msce_id),
('वेनआकृती', @chapter_kutprashna_id, @subject_iq_marathi_id, @class_5th_id, @board_msce_id),
('चौरस/वर्तुळातील संख्या', @chapter_kutprashna_id, @subject_iq_marathi_id, @class_5th_id, @board_msce_id);


-- Chapter 9: गटाशी जुळणारे पद (Matching the Group) - User specifically asked for this and onwards
INSERT IGNORE INTO chapters (chapter_name, subject_id, class_id, board_id) VALUES
('गटाशी जुळणारे पद', @subject_iq_marathi_id, @class_5th_id, @board_msce_id);
SET @chapter_gatashi_julnare_pad_id = (SELECT id FROM chapters WHERE chapter_name = 'गटाशी जुळणारे पद' AND subject_id = @subject_iq_marathi_id AND class_id = @class_5th_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@chapter_gatashi_julnare_pad_id, @class_5th_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('शब्दसंग्रह, आकृती, संख्या', @chapter_gatashi_julnare_pad_id, @subject_iq_marathi_id, @class_5th_id, @board_msce_id);


-- Chapter 10: सांकेतिक भाषा (Coded Language)
INSERT IGNORE INTO chapters (chapter_name, subject_id, class_id, board_id) VALUES
('सांकेतिक भाषा', @subject_iq_marathi_id, @class_5th_id, @board_msce_id);
SET @chapter_sanketik_bhasha_id = (SELECT id FROM chapters WHERE chapter_name = 'सांकेतिक भाषा' AND subject_id = @subject_iq_marathi_id AND class_id = @class_5th_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@chapter_sanketik_bhasha_id, @class_5th_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('शब्द, संख्या, चिन्हे यांचा परस्पर वापर', @chapter_sanketik_bhasha_id, @subject_iq_marathi_id, @class_5th_id, @board_msce_id);


-- Chapter 11: भावनिक व सामाजिक बुद्धिमत्ता (Emotional and Social Intelligence)
INSERT IGNORE INTO chapters (chapter_name, subject_id, class_id, board_id) VALUES
('भावनिक व सामाजिक बुद्धिमत्ता', @subject_iq_marathi_id, @class_5th_id, @board_msce_id);
SET @chapter_bhavanik_samajik_buddhimatta_id = (SELECT id FROM chapters WHERE chapter_name = 'भावनिक व सामाजिक बुद्धिमत्ता' AND subject_id = @subject_iq_marathi_id AND class_id = @class_5th_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@chapter_bhavanik_samajik_buddhimatta_id, @class_5th_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('भावनिक बुद्धिमत्ता', @chapter_bhavanik_samajik_buddhimatta_id, @subject_iq_marathi_id, @class_5th_id, @board_msce_id),
('सामाजिक बुद्धिमत्ता', @chapter_bhavanik_samajik_buddhimatta_id, @subject_iq_marathi_id, @class_5th_id, @board_msce_id);



COMMIT;

SET FOREIGN_KEY_CHECKS = 1;