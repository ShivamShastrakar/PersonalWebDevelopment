--liquibase formatted sql
--changeset {narendra}:{id}

SET NAMES utf8mb4 COLLATE utf8mb4_unicode_ci;

--Here is the SQL script to insert the syllabus data for "Marathi – First Language" for the 4th Standard, Marathi Medium, based on the provided PDF document.

SET FOREIGN_KEY_CHECKS = 0;

START TRANSACTION;

-- 1. Insert Board (MSCE) if not exists and get its ID
INSERT IGNORE INTO board (tenant_id, board_name) VALUES (NULL, 'MSCE');
SET @board_msce_id = (SELECT id FROM board WHERE board_name = 'MSCE' LIMIT 1);

-- 2. Insert Class 4 if not exists and get its ID
INSERT IGNORE INTO class (class_name) VALUES ('4');
SET @class_4_id = (SELECT id FROM class WHERE class_name = '4' LIMIT 1);

-- 3. Insert Subject 'Marathi – First Language' if not exists and get its ID
INSERT IGNORE INTO subject (subject_name) VALUES ('Marathi – First Language');
SET @subject_marathi_fl_id = (SELECT subject_id FROM subject WHERE subject_name = 'Marathi – First Language' LIMIT 1);

-- 4. Map Subject to Board, Class, and Medium (Marathi Medium)
INSERT IGNORE INTO subject_board_class_mapping (subject_id, class_id, board_id, medium) VALUES
(@subject_marathi_fl_id, @class_4_id, @board_msce_id, 'Marathi');

-- 5. Insert Chapters and Topics for "Marathi – First Language" (4th Std, Marathi Medium)

-- Chapter: आकलन (Comprehension)
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES ('आकलन', NULL, @subject_marathi_fl_id, @class_4_id, @board_msce_id);
SET @chapter_aakalan_id = (SELECT id FROM chapters WHERE chapter_name = 'आकलन' AND subject_id = @subject_marathi_fl_id AND class_id = @class_4_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@chapter_aakalan_id, @class_4_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('उतारा व त्यावर आधारित प्रश्न', @chapter_aakalan_id, @subject_marathi_fl_id, @class_4_id, @board_msce_id),
('कविता व त्यावर आधारित प्रश्न', @chapter_aakalan_id, @subject_marathi_fl_id, @class_4_id, @board_msce_id),
('संवादावर आधारित प्रश्न', @chapter_aakalan_id, @subject_marathi_fl_id, @class_4_id, @board_msce_id),
('सुसंगत वाक्यांचा परिच्छेद', @chapter_aakalan_id, @subject_marathi_fl_id, @class_4_id, @board_msce_id),
('जाहिरातीवर आधारित प्रश्न', @chapter_aakalan_id, @subject_marathi_fl_id, @class_4_id, @board_msce_id);

-- Chapter: शब्दसंपत्तीवरील प्रभुत्व (Mastery over Vocabulary)
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES ('शब्दसंपत्तीवरील प्रभुत्व', NULL, @subject_marathi_fl_id, @class_4_id, @board_msce_id);
SET @chapter_shabdasampatti_id = (SELECT id FROM chapters WHERE chapter_name = 'शब्दसंपत्तीवरील प्रभुत्व' AND subject_id = @subject_marathi_fl_id AND class_id = @class_4_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@chapter_shabdasampatti_id, @class_4_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('समानार्थी शब्द', @chapter_shabdasampatti_id, @subject_marathi_fl_id, @class_4_id, @board_msce_id),
('विरूध्दार्थी शब्द', @chapter_shabdasampatti_id, @subject_marathi_fl_id, @class_4_id, @board_msce_id),
('समूहदर्शक शब्द', @chapter_shabdasampatti_id, @subject_marathi_fl_id, @class_4_id, @board_msce_id),
('शब्दसमूहाबद्दल एक शब्द', @chapter_shabdasampatti_id, @subject_marathi_fl_id, @class_4_id, @board_msce_id),
('आलंकारिक शब्द', @chapter_shabdasampatti_id, @subject_marathi_fl_id, @class_4_id, @board_msce_id),
('जोडशब्द', @chapter_shabdasampatti_id, @subject_marathi_fl_id, @class_4_id, @board_msce_id),
('शब्दकोडी', @chapter_shabdasampatti_id, @subject_marathi_fl_id, @class_4_id, @board_msce_id),
('एकाच शब्दाचे भिन्न अर्थ शोधणे', @chapter_shabdasampatti_id, @subject_marathi_fl_id, @class_4_id, @board_msce_id),
('अक्षर जुळवून अर्थपूर्ण शब्द तयार करणे', @chapter_shabdasampatti_id, @subject_marathi_fl_id, @class_4_id, @board_msce_id),
('पिलू दर्शक शब्द, घर दर्शक शब्द, ध्वनी दर्शक शब्द', @chapter_shabdasampatti_id, @subject_marathi_fl_id, @class_4_id, @board_msce_id),
('म्हणी', @chapter_shabdasampatti_id, @subject_marathi_fl_id, @class_4_id, @board_msce_id),
('वाक्प्रचार', @chapter_shabdasampatti_id, @subject_marathi_fl_id, @class_4_id, @board_msce_id);

-- Chapter: कार्यात्मक व्याकरण (Functional Grammar)
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES ('कार्यात्मक व्याकरण', NULL, @subject_marathi_fl_id, @class_4_id, @board_msce_id);
SET @chapter_vyakaran_id = (SELECT id FROM chapters WHERE chapter_name = 'कार्यात्मक व्याकरण' AND subject_id = @subject_marathi_fl_id AND class_id = @class_4_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@chapter_vyakaran_id, @class_4_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('वर्णमाला', @chapter_vyakaran_id, @subject_marathi_fl_id, @class_4_id, @board_msce_id),
('शब्दकोशाप्रमाणे शब्दांचा क्रम लावणे', @chapter_vyakaran_id, @subject_marathi_fl_id, @class_4_id, @board_msce_id),
('शब्दांच्या जाती (नाम, सर्वनाम, विशेषण, क्रियापद)', @chapter_vyakaran_id, @subject_marathi_fl_id, @class_4_id, @board_msce_id), -- Combined sub-items 'अ) नाम', 'ब) सर्वनाम', 'क) विशेषण', 'ड) क्रियापद'
('लिंग', @chapter_vyakaran_id, @subject_marathi_fl_id, @class_4_id, @board_msce_id),
('वचन', @chapter_vyakaran_id, @subject_marathi_fl_id, @class_4_id, @board_msce_id),
('काळ', @chapter_vyakaran_id, @subject_marathi_fl_id, @class_4_id, @board_msce_id),
('विरामचिन्हे', @chapter_vyakaran_id, @subject_marathi_fl_id, @class_4_id, @board_msce_id),
('वाक्यांचे भाग', @chapter_vyakaran_id, @subject_marathi_fl_id, @class_4_id, @board_msce_id),
('शुद्ध - अशुद्ध शब्द', @chapter_vyakaran_id, @subject_marathi_fl_id, @class_4_id, @board_msce_id),
('प्रत्यय घटित व उपसर्ग घटित शब्द', @chapter_vyakaran_id, @subject_marathi_fl_id, @class_4_id, @board_msce_id);

-- Chapter: भाषाविषयक सामान्यज्ञान (Language related General Knowledge)
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES ('भाषाविषयक सामान्यज्ञान', NULL, @subject_marathi_fl_id, @class_4_id, @board_msce_id);
SET @chapter_gk_id = (SELECT id FROM chapters WHERE chapter_name = 'भाषाविषयक सामान्यज्ञान' AND subject_id = @subject_marathi_fl_id AND class_id = @class_4_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@chapter_gk_id, @class_4_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('लेखक, कवी - ग्रंथ व टोपणनावे', @chapter_gk_id, @subject_marathi_fl_id, @class_4_id, @board_msce_id),
('खेळ - खेळाडू', @chapter_gk_id, @subject_marathi_fl_id, @class_4_id, @board_msce_id),
('दिनविशेष', @chapter_gk_id, @subject_marathi_fl_id, @class_4_id, @board_msce_id),
('विविध पुरस्कार, पदवी / किताब', @chapter_gk_id, @subject_marathi_fl_id, @class_4_id, @board_msce_id);


-- Here is the SQL script to insert the syllabus data for "Math – Marathi" for the 4th Standard, Marathi Medium, based on the provided PDF document. This script ensures all Marathi content is handled correctly with UTF-8 encoding.


-- 3. Insert Subject 'Math – Marathi' if not exists and get its ID
INSERT IGNORE INTO subject (subject_name) VALUES ('Math – Marathi');
SET @subject_math_marathi_id = (SELECT subject_id FROM subject WHERE subject_name = 'Math – Marathi' LIMIT 1);

-- 4. Map Subject to Board, Class, and Medium (Marathi Medium)
INSERT IGNORE INTO subject_board_class_mapping (subject_id, class_id, board_id, medium) VALUES
(@subject_math_marathi_id, @class_4_id, @board_msce_id, 'Marathi');

-- 5. Insert Chapters and Topics for "Math – Marathi" (4th Std, Marathi Medium)

-- Chapter: संख्याज्ञान (Number Work)
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES ('संख्याज्ञान', NULL, @subject_math_marathi_id, @class_4_id, @board_msce_id);
SET @chapter_sankhyagnan_id = (SELECT id FROM chapters WHERE chapter_name = 'संख्याज्ञान' AND subject_id = @subject_math_marathi_id AND class_id = @class_4_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@chapter_sankhyagnan_id, @class_4_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('आंतरराष्ट्रीय संख्याचिन्हे वाचन व लेखन', @chapter_sankhyagnan_id, @subject_math_marathi_id, @class_4_id, @board_msce_id),
('पाच अंकापर्यंतच्या संख्यांचे वाचन व लेखन', @chapter_sankhyagnan_id, @subject_math_marathi_id, @class_4_id, @board_msce_id),
('अंकांची दर्शनी किंमत, स्थानिक किंमत, विस्तारित मांडणी', @chapter_sankhyagnan_id, @subject_math_marathi_id, @class_4_id, @board_msce_id),
('मोठ्यात मोठी व लहानात लहान संख्या', @chapter_sankhyagnan_id, @subject_math_marathi_id, @class_4_id, @board_msce_id),
('संख्याचा चढता क्रम व उतरता क्रम, तुलना', @chapter_sankhyagnan_id, @subject_math_marathi_id, @class_4_id, @board_msce_id),
('1 ते 100 संख्यावर आधारित प्रश्न', @chapter_sankhyagnan_id, @subject_math_marathi_id, @class_4_id, @board_msce_id),
('सम, विषम संख्या', @chapter_sankhyagnan_id, @subject_math_marathi_id, @class_4_id, @board_msce_id),
('1 ते 100 मधील मूळ, संयुक्त, त्रिकोणी व चौरस संख्या', @chapter_sankhyagnan_id, @subject_math_marathi_id, @class_4_id, @board_msce_id);

-- Chapter: संख्यांवरील क्रिया (Operations on numbers)
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES ('संख्यांवरील क्रिया', NULL, @subject_math_marathi_id, @class_4_id, @board_msce_id);
SET @chapter_operations_on_numbers_id = (SELECT id FROM chapters WHERE chapter_name = 'संख्यांवरील क्रिया' AND subject_id = @subject_math_marathi_id AND class_id = @class_4_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@chapter_operations_on_numbers_id, @class_4_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('बेरीज (पाच अंकी संख्यापर्यंत) हातच्याची बेरीज, शाब्दिक उदाहरणे', @chapter_operations_on_numbers_id, @subject_math_marathi_id, @class_4_id, @board_msce_id),
('वजाबाकी (पाच अंकी संख्यापर्यंत) हातच्याची वजाबाकी, शाब्दिक उदाहरणे', @chapter_operations_on_numbers_id, @subject_math_marathi_id, @class_4_id, @board_msce_id),
('गुणाकार (तीन अंकी गुणिले दोन अंकी संख्यापर्यंत) शाब्दिक उदाहरणे', @chapter_operations_on_numbers_id, @subject_math_marathi_id, @class_4_id, @board_msce_id),
('भागाकार (तीन अंकी भागिले दोन अंकी संख्यापर्यंत) शाब्दिक उदाहरणे', @chapter_operations_on_numbers_id, @subject_math_marathi_id, @class_4_id, @board_msce_id);

-- Chapter: अपूर्णांक (Fractions)
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES ('अपूर्णांक', NULL, @subject_math_marathi_id, @class_4_id, @board_msce_id);
SET @chapter_fractions_id = (SELECT id FROM chapters WHERE chapter_name = 'अपूर्णांक' AND subject_id = @subject_math_marathi_id AND class_id = @class_4_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@chapter_fractions_id, @class_4_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('अपूर्णांकाचे अर्थ, वाचन व लेखन', @chapter_fractions_id, @subject_math_marathi_id, @class_4_id, @board_msce_id),
('व्यवहारी अपूर्णांक - समच्छेद अपूर्णांक, भिन्नच्छेद अपूर्णांक, अपूर्णांकाचा लहानमोठेपणा, चढता उतरता क्रम', @chapter_fractions_id, @subject_math_marathi_id, @class_4_id, @board_msce_id),
('अंशाधिक, छेदाधिक व पूर्णांकयुक्त अपूर्णांक, तुलना व परस्पर रुपांतर', @chapter_fractions_id, @subject_math_marathi_id, @class_4_id, @board_msce_id);

-- Chapter: मापन / महत्त्वमापन (Measurement / Importance of Measurement)
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES ('मापन / महत्त्वमापन', NULL, @subject_math_marathi_id, @class_4_id, @board_msce_id);
SET @chapter_measurement_id = (SELECT id FROM chapters WHERE chapter_name = 'मापन / महत्त्वमापन' AND subject_id = @subject_math_marathi_id AND class_id = @class_4_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@chapter_measurement_id, @class_4_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('लांबी, वस्तुमान, धारकता एककाचे परस्पर रूपांतर, बेरीज, वजाबाकी व शाब्दिक उदाहरणे', @chapter_measurement_id, @subject_math_marathi_id, @class_4_id, @board_msce_id),
('कालमापन : घड्याळ (मध्यान्हपूर्व, माध्यान्होत्तर) तास, मिनिटे परस्पर रुपांतर', @chapter_measurement_id, @subject_math_marathi_id, @class_4_id, @board_msce_id),
('दिनदर्शिका', @chapter_measurement_id, @subject_math_marathi_id, @class_4_id, @board_msce_id),
('कागदमापन', @chapter_measurement_id, @subject_math_marathi_id, @class_4_id, @board_msce_id),
('नाणी - नोटा (रुपये-पैसे) परस्पर रुपांतर, मूलभूत क्रियांवर आधारित शाब्दिक उदाहरणे', @chapter_measurement_id, @subject_math_marathi_id, @class_4_id, @board_msce_id);

-- Chapter: आकृतिबंध (Patterns)
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES ('आकृतिबंध', NULL, @subject_math_marathi_id, @class_4_id, @board_msce_id);
SET @chapter_patterns_id = (SELECT id FROM chapters WHERE chapter_name = 'आकृतिबंध' AND subject_id = @subject_math_marathi_id AND class_id = @class_4_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@chapter_patterns_id, @class_4_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('भौमितिक आकार', @chapter_patterns_id, @subject_math_marathi_id, @class_4_id, @board_msce_id),
('संख्या', @chapter_patterns_id, @subject_math_marathi_id, @class_4_id, @board_msce_id),
('विविध मुक्तहस्त आकृत्या', @chapter_patterns_id, @subject_math_marathi_id, @class_4_id, @board_msce_id);

-- Chapter: भूमिती (Geometry)
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES ('भूमिती', NULL, @subject_math_marathi_id, @class_4_id, @board_msce_id);
SET @chapter_geometry_id = (SELECT id FROM chapters WHERE chapter_name = 'भूमिती' AND subject_id = @subject_math_marathi_id AND class_id = @class_4_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@chapter_geometry_id, @class_4_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('कोन व त्यांचे प्रकार (काटकोन, लघुकोन, विशालकोन)', @chapter_geometry_id, @subject_math_marathi_id, @class_4_id, @board_msce_id),
('सममिती', @chapter_geometry_id, @subject_math_marathi_id, @class_4_id, @board_msce_id),
('शिरोबिंदू, बाजू : त्रिकोण, चौरस, आयत', @chapter_geometry_id, @subject_math_marathi_id, @class_4_id, @board_msce_id),
('वर्तुळ : त्रिज्या, जीवा, व्यास, केंद्र, कड (परीघ), अंतर्भाग, बाह्यभाग', @chapter_geometry_id, @subject_math_marathi_id, @class_4_id, @board_msce_id),
('परिमिती - त्रिकोण, आयत, चौरस', @chapter_geometry_id, @subject_math_marathi_id, @class_4_id, @board_msce_id),
('क्षेत्रफळ - आयत, चौरस', @chapter_geometry_id, @subject_math_marathi_id, @class_4_id, @board_msce_id),
('त्रिमिती वस्तू व घडणी', @chapter_geometry_id, @subject_math_marathi_id, @class_4_id, @board_msce_id),
('दंडगोल, शंकू व गोल (कडा व कोपरे)', @chapter_geometry_id, @subject_math_marathi_id, @class_4_id, @board_msce_id),
('इष्टिकाचिती व घन (कडा, शिरोबिंदू, पृष्ठे)', @chapter_geometry_id, @subject_math_marathi_id, @class_4_id, @board_msce_id);

-- Chapter: चित्रालेख (Pictograph)
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES ('चित्रालेख', NULL, @subject_math_marathi_id, @class_4_id, @board_msce_id);
SET @chapter_pictograph_id = (SELECT id FROM chapters WHERE chapter_name = 'चित्रालेख' AND subject_id = @subject_math_marathi_id AND class_id = @class_4_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@chapter_pictograph_id, @class_4_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('चित्ररुप माहितीचे आकलन', @chapter_pictograph_id, @subject_math_marathi_id, @class_4_id, @board_msce_id);



-- Here is the SQL script to insert the syllabus data for "English – Third Language" for the 4th Standard, Marathi Medium, based on the provided PDF document.

-- 3. Insert Subject 'English – Third Language' if not exists and get its ID
INSERT IGNORE INTO subject (subject_name) VALUES ('English – Third Language');
SET @subject_english_third_lang_id = (SELECT subject_id FROM subject WHERE subject_name = 'English – Third Language' LIMIT 1);

-- 4. Map Subject to Board, Class, and Medium (Marathi Medium as requested by user)
INSERT IGNORE INTO subject_board_class_mapping (subject_id, class_id, board_id, medium) VALUES
(@subject_english_third_lang_id, @class_4_id, @board_msce_id, 'Marathi');

-- 5. Insert Chapters and Topics for "English – Third Language" (4th Std, Marathi Medium)

-- Chapter: Letters of Alphabets
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES ('Letters of Alphabets', NULL, @subject_english_third_lang_id, @class_4_id, @board_msce_id);
SET @chapter_letters_alphabets_id = (SELECT id FROM chapters WHERE chapter_name = 'Letters of Alphabets' AND subject_id = @subject_english_third_lang_id AND class_id = @class_4_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@chapter_letters_alphabets_id, @class_4_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('Associating the name of a letter with its sound', @chapter_letters_alphabets_id, @subject_english_third_lang_id, @class_4_id, @board_msce_id),
('Formation of words using given alphabets', @chapter_letters_alphabets_id, @subject_english_third_lang_id, @class_4_id, @board_msce_id);

-- Chapter: Vocabulary
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES ('Vocabulary', NULL, @subject_english_third_lang_id, @class_4_id, @board_msce_id);
SET @chapter_vocabulary_id = (SELECT id FROM chapters WHERE chapter_name = 'Vocabulary' AND subject_id = @subject_english_third_lang_id AND class_id = @class_4_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@chapter_vocabulary_id, @class_4_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('Correlating words with pictures (action words, describing words)', @chapter_vocabulary_id, @subject_english_third_lang_id, @class_4_id, @board_msce_id),
('Rhyming words', @chapter_vocabulary_id, @subject_english_third_lang_id, @class_4_id, @board_msce_id),
('Opposite words', @chapter_vocabulary_id, @subject_english_third_lang_id, @class_4_id, @board_msce_id),
('Word register', @chapter_vocabulary_id, @subject_english_third_lang_id, @class_4_id, @board_msce_id),
('Finding small words from the given bigger word', @chapter_vocabulary_id, @subject_english_third_lang_id, @class_4_id, @board_msce_id),
('Contracted Forms', @chapter_vocabulary_id, @subject_english_third_lang_id, @class_4_id, @board_msce_id),
('Dictionary Skills', @chapter_vocabulary_id, @subject_english_third_lang_id, @class_4_id, @board_msce_id),
('Parts of human body, plants, animals', @chapter_vocabulary_id, @subject_english_third_lang_id, @class_4_id, @board_msce_id),
('Names of birds and animals, their living places and sounds', @chapter_vocabulary_id, @subject_english_third_lang_id, @class_4_id, @board_msce_id),
('Comparisons (as.... as....)', @chapter_vocabulary_id, @subject_english_third_lang_id, @class_4_id, @board_msce_id),
('Names of colours, things, shapes, vegetables, fruits, games', @chapter_vocabulary_id, @subject_english_third_lang_id, @class_4_id, @board_msce_id);

-- Chapter: Punctuation Marks
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES ('Punctuation Marks', NULL, @subject_english_third_lang_id, @class_4_id, @board_msce_id);
SET @chapter_punctuation_id = (SELECT id FROM chapters WHERE chapter_name = 'Punctuation Marks' AND subject_id = @subject_english_third_lang_id AND class_id = @class_4_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@chapter_punctuation_id, @class_4_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('Capitalization', @chapter_punctuation_id, @subject_english_third_lang_id, @class_4_id, @board_msce_id),
('Comma', @chapter_punctuation_id, @subject_english_third_lang_id, @class_4_id, @board_msce_id),
('Full stop', @chapter_punctuation_id, @subject_english_third_lang_id, @class_4_id, @board_msce_id),
('Question Mark', @chapter_punctuation_id, @subject_english_third_lang_id, @class_4_id, @board_msce_id),
('Apostrophe', @chapter_punctuation_id, @subject_english_third_lang_id, @class_4_id, @board_msce_id),
('Exclamation mark', @chapter_punctuation_id, @subject_english_third_lang_id, @class_4_id, @board_msce_id);

-- Chapter: Numerical Information
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES ('Numerical Information', NULL, @subject_english_third_lang_id, @class_4_id, @board_msce_id);
SET @chapter_numerical_info_id = (SELECT id FROM chapters WHERE chapter_name = 'Numerical Information' AND subject_id = @subject_english_third_lang_id AND class_id = @class_4_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@chapter_numerical_info_id, @class_4_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('Days of the week', @chapter_numerical_info_id, @subject_english_third_lang_id, @class_4_id, @board_msce_id),
('Months of the year', @chapter_numerical_info_id, @subject_english_third_lang_id, @class_4_id, @board_msce_id),
('Cardinal, Ordinal numbers', @chapter_numerical_info_id, @subject_english_third_lang_id, @class_4_id, @board_msce_id),
('Showing the directions and subdirections, Map reading', @chapter_numerical_info_id, @subject_english_third_lang_id, @class_4_id, @board_msce_id);

-- Chapter: Creative Thinking
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES ('Creative Thinking', NULL, @subject_english_third_lang_id, @class_4_id, @board_msce_id);
SET @chapter_creative_thinking_id = (SELECT id FROM chapters WHERE chapter_name = 'Creative Thinking' AND subject_id = @subject_english_third_lang_id AND class_id = @class_4_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@chapter_creative_thinking_id, @class_4_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('Writing familiar / related words with the given clues or pictures', @chapter_creative_thinking_id, @subject_english_third_lang_id, @class_4_id, @board_msce_id),
('Mottos, Messages', @chapter_creative_thinking_id, @subject_english_third_lang_id, @class_4_id, @board_msce_id),
('Solving puzzles with the given clues', @chapter_creative_thinking_id, @subject_english_third_lang_id, @class_4_id, @board_msce_id),
('Solving riddles with the given clues', @chapter_creative_thinking_id, @subject_english_third_lang_id, @class_4_id, @board_msce_id);

-- Chapter: Stock Expressions
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES ('Stock Expressions', NULL, @subject_english_third_lang_id, @class_4_id, @board_msce_id);
SET @chapter_stock_expressions_id = (SELECT id FROM chapters WHERE chapter_name = 'Stock Expressions' AND subject_id = @subject_english_third_lang_id AND class_id = @class_4_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@chapter_stock_expressions_id, @class_4_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('Greetings', @chapter_stock_expressions_id, @subject_english_third_lang_id, @class_4_id, @board_msce_id),
('Correlation between instructions, expressions and pictures', @chapter_stock_expressions_id, @subject_english_third_lang_id, @class_4_id, @board_msce_id);

-- Chapter: Grammar
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES ('Grammar', NULL, @subject_english_third_lang_id, @class_4_id, @board_msce_id);
SET @chapter_grammar_id = (SELECT id FROM chapters WHERE chapter_name = 'Grammar' AND subject_id = @subject_english_third_lang_id AND class_id = @class_4_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@chapter_grammar_id, @class_4_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('Nouns', @chapter_grammar_id, @subject_english_third_lang_id, @class_4_id, @board_msce_id),
('Pronouns', @chapter_grammar_id, @subject_english_third_lang_id, @class_4_id, @board_msce_id),
('Adverbs', @chapter_grammar_id, @subject_english_third_lang_id, @class_4_id, @board_msce_id),
('Prepositions', @chapter_grammar_id, @subject_english_third_lang_id, @class_4_id, @board_msce_id),
('Articles', @chapter_grammar_id, @subject_english_third_lang_id, @class_4_id, @board_msce_id),
('Tenses (Simple Present, Simple Past, Simple Future)', @chapter_grammar_id, @subject_english_third_lang_id, @class_4_id, @board_msce_id),
('Singular and Plural', @chapter_grammar_id, @subject_english_third_lang_id, @class_4_id, @board_msce_id);

-- Chapter: Comprehension
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES ('Comprehension', NULL, @subject_english_third_lang_id, @class_4_id, @board_msce_id);
SET @chapter_comprehension_id = (SELECT id FROM chapters WHERE chapter_name = 'Comprehension' AND subject_id = @subject_english_third_lang_id AND class_id = @class_4_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@chapter_comprehension_id, @class_4_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('Prose (Upto 20 words)', @chapter_comprehension_id, @subject_english_third_lang_id, @class_4_id, @board_msce_id);


-- Here is the SQL script to insert the syllabus data for "IQ – Marathi" for the 4th Standard, Marathi Medium, based on the provided PDF document. This script ensures all Marathi content is handled correctly with UTF-8 encoding.


-- 3. Insert Subject 'IQ – Marathi' if not exists and get its ID
INSERT IGNORE INTO subject (subject_name) VALUES ('IQ – Marathi');
SET @subject_iq_marathi_id = (SELECT subject_id FROM subject WHERE subject_name = 'IQ – Marathi' LIMIT 1);

-- 4. Map Subject to Board, Class, and Medium (Marathi Medium)
INSERT IGNORE INTO subject_board_class_mapping (subject_id, class_id, board_id, medium) VALUES
(@subject_iq_marathi_id, @class_4_id, @board_msce_id, 'Marathi');

-- 5. Insert Chapters and Topics for "IQ – Marathi" (4th Std, Marathi Medium)

-- Chapter: आकलन (Comprehension)
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES ('आकलन', NULL, @subject_iq_marathi_id, @class_4_id, @board_msce_id);
SET @chapter_aakalan_id = (SELECT id FROM chapters WHERE chapter_name = 'आकलन' AND subject_id = @subject_iq_marathi_id AND class_id = @class_4_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@chapter_aakalan_id, @class_4_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('सूचनापालन : जोडाक्षरे, शब्द, अक्षर', @chapter_aakalan_id, @subject_iq_marathi_id, @class_4_id, @board_msce_id),
('संख्यामालिका', @chapter_aakalan_id, @subject_iq_marathi_id, @class_4_id, @board_msce_id),
('इंग्रजी अक्षरमाला', @chapter_aakalan_id, @subject_iq_marathi_id, @class_4_id, @board_msce_id);

-- Chapter: वर्गीकरण (Classification)
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES ('वर्गीकरण', NULL, @subject_iq_marathi_id, @class_4_id, @board_msce_id);
SET @chapter_vargikaran_id = (SELECT id FROM chapters WHERE chapter_name = 'वर्गीकरण' AND subject_id = @subject_iq_marathi_id AND class_id = @class_4_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@chapter_vargikaran_id, @class_4_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('शब्दसंग्रह', @chapter_vargikaran_id, @subject_iq_marathi_id, @class_4_id, @board_msce_id),
('आकृत्या', @chapter_vargikaran_id, @subject_iq_marathi_id, @class_4_id, @board_msce_id),
('संख्या', @chapter_vargikaran_id, @subject_iq_marathi_id, @class_4_id, @board_msce_id),
('इंग्रजी अक्षरमाला', @chapter_vargikaran_id, @subject_iq_marathi_id, @class_4_id, @board_msce_id);

-- Chapter: समसंबंध (Co-relation)
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES ('समसंबंध', NULL, @subject_iq_marathi_id, @class_4_id, @board_msce_id);
SET @chapter_samasambandh_id = (SELECT id FROM chapters WHERE chapter_name = 'समसंबंध' AND subject_id = @subject_iq_marathi_id AND class_id = @class_4_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@chapter_samasambandh_id, @class_4_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('शब्दसंग्रह', @chapter_samasambandh_id, @subject_iq_marathi_id, @class_4_id, @board_msce_id),
('आकृत्या', @chapter_samasambandh_id, @subject_iq_marathi_id, @class_4_id, @board_msce_id),
('संख्या', @chapter_samasambandh_id, @subject_iq_marathi_id, @class_4_id, @board_msce_id),
('इंग्रजी अक्षरमाला', @chapter_samasambandh_id, @subject_iq_marathi_id, @class_4_id, @board_msce_id);

-- Chapter: क्रम ओळखणे (Series)
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES ('क्रम ओळखणे', NULL, @subject_iq_marathi_id, @class_4_id, @board_msce_id);
SET @chapter_kram_olakne_id = (SELECT id FROM chapters WHERE chapter_name = 'क्रम ओळखणे' AND subject_id = @subject_iq_marathi_id AND class_id = @class_4_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@chapter_kram_olakne_id, @class_4_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('संख्या', @chapter_kram_olakne_id, @subject_iq_marathi_id, @class_4_id, @board_msce_id),
('आकृत्यांची मालिका', @chapter_kram_olakne_id, @subject_iq_marathi_id, @class_4_id, @board_msce_id),
('चिन्हांची मालिका', @chapter_kram_olakne_id, @subject_iq_marathi_id, @class_4_id, @board_msce_id),
('चुकीचे पद ओळखणे (संख्या)', @chapter_kram_olakne_id, @subject_iq_marathi_id, @class_4_id, @board_msce_id);

-- Chapter: गटाशी जुळणारे पद (Matching terms in a group)
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES ('गटाशी जुळणारे पद', NULL, @subject_iq_marathi_id, @class_4_id, @board_msce_id);
SET @chapter_gatashi_julnare_pad_id = (SELECT id FROM chapters WHERE chapter_name = 'गटाशी जुळणारे पद' AND subject_id = @subject_iq_marathi_id AND class_id = @class_4_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@chapter_gatashi_julnare_pad_id, @class_4_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('शब्दसंग्रह', @chapter_gatashi_julnare_pad_id, @subject_iq_marathi_id, @class_4_id, @board_msce_id),
('आकृत्या', @chapter_gatashi_julnare_pad_id, @subject_iq_marathi_id, @class_4_id, @board_msce_id),
('संख्या', @chapter_gatashi_julnare_pad_id, @subject_iq_marathi_id, @class_4_id, @board_msce_id),
('इंग्रजी अक्षरमाला', @chapter_gatashi_julnare_pad_id, @subject_iq_marathi_id, @class_4_id, @board_msce_id);

-- Chapter: जलप्रतिबिंब (Water image)
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES ('जलप्रतिबिंब', NULL, @subject_iq_marathi_id, @class_4_id, @board_msce_id);
SET @chapter_jalpratibimb_id = (SELECT id FROM chapters WHERE chapter_name = 'जलप्रतिबिंब' AND subject_id = @subject_iq_marathi_id AND class_id = @class_4_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@chapter_jalpratibimb_id, @class_4_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('आकृत्या', @chapter_jalpratibimb_id, @subject_iq_marathi_id, @class_4_id, @board_msce_id),
('अंक', @chapter_jalpratibimb_id, @subject_iq_marathi_id, @class_4_id, @board_msce_id),
('अक्षरे', @chapter_jalpratibimb_id, @subject_iq_marathi_id, @class_4_id, @board_msce_id);

-- Chapter: आरशातील प्रतिमा (Mirror image)
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES ('आरशातील प्रतिमा', NULL, @subject_iq_marathi_id, @class_4_id, @board_msce_id);
SET @chapter_arshatil_pratima_id = (SELECT id FROM chapters WHERE chapter_name = 'आरशातील प्रतिमा' AND subject_id = @subject_iq_marathi_id AND class_id = @class_4_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@chapter_arshatil_pratima_id, @class_4_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('आकृत्या', @chapter_arshatil_pratima_id, @subject_iq_marathi_id, @class_4_id, @board_msce_id),
('अंक', @chapter_arshatil_pratima_id, @subject_iq_marathi_id, @class_4_id, @board_msce_id),
('अक्षरे', @chapter_arshatil_pratima_id, @subject_iq_marathi_id, @class_4_id, @board_msce_id);

-- Chapter: समान पद ओळखणे (Identify similar terms)
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES ('समान पद ओळखणे', NULL, @subject_iq_marathi_id, @class_4_id, @board_msce_id);
SET @chapter_saman_pad_olakne_id = (SELECT id FROM chapters WHERE chapter_name = 'समान पद ओळखणे' AND subject_id = @subject_iq_marathi_id AND class_id = @class_4_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@chapter_saman_pad_olakne_id, @class_4_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('आकृत्या', @chapter_saman_pad_olakne_id, @subject_iq_marathi_id, @class_4_id, @board_msce_id);

-- Chapter: तर्कसंगती व अनुमान (Reasoning and Inference)
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES ('तर्कसंगती व अनुमान', NULL, @subject_iq_marathi_id, @class_4_id, @board_msce_id);
SET @chapter_tarkasanti_anuman_id = (SELECT id FROM chapters WHERE chapter_name = 'तर्कसंगती व अनुमान' AND subject_id = @subject_iq_marathi_id AND class_id = @class_4_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@chapter_tarkasanti_anuman_id, @class_4_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('भाषिक : वय, तुलना, नावात बदल, नाती', @chapter_tarkasanti_anuman_id, @subject_iq_marathi_id, @class_4_id, @board_msce_id),
('अभाषिक : आकृत्या मोजणे - त्रिकोण, चौकोन, चौरस, आयत, रेषाखंड, कोन, घनाकृती ठोकळे इत्यादी', @chapter_tarkasanti_anuman_id, @subject_iq_marathi_id, @class_4_id, @board_msce_id);

-- Chapter: कूटप्रश्न (Puzzles)
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES ('कूटप्रश्न', NULL, @subject_iq_marathi_id, @class_4_id, @board_msce_id);
SET @chapter_kutaprashna_id = (SELECT id FROM chapters WHERE chapter_name = 'कूटप्रश्न' AND subject_id = @subject_iq_marathi_id AND class_id = @class_4_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@chapter_kutaprashna_id, @class_4_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('रांगेतील स्थान', @chapter_kutaprashna_id, @subject_iq_marathi_id, @class_4_id, @board_msce_id),
('दिशा', @chapter_kutaprashna_id, @subject_iq_marathi_id, @class_4_id, @board_msce_id),
('दिनदर्शिका', @chapter_kutaprashna_id, @subject_iq_marathi_id, @class_4_id, @board_msce_id),
('वेन आकृती', @chapter_kutaprashna_id, @subject_iq_marathi_id, @class_4_id, @board_msce_id),
('चौरस, वर्तुळ, त्रिकोण इत्यादी आकृत्यांमधील संख्या', @chapter_kutaprashna_id, @subject_iq_marathi_id, @class_4_id, @board_msce_id);

-- Chapter: सांकेतिक भाषा (Symbolic Language)
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES ('सांकेतिक भाषा', NULL, @subject_iq_marathi_id, @class_4_id, @board_msce_id);
SET @chapter_sanketik_bhasha_id = (SELECT id FROM chapters WHERE chapter_name = 'सांकेतिक भाषा' AND subject_id = @subject_iq_marathi_id AND class_id = @class_4_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@chapter_sanketik_bhasha_id, @class_4_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('शब्द, संख्या, चिन्हे यांचा परस्पर वापर', @chapter_sanketik_bhasha_id, @subject_iq_marathi_id, @class_4_id, @board_msce_id);

-- Chapter: विशेष प्रश्न (Special Questions)
INSERT IGNORE INTO chapters (chapter_name, unit, subject_id, class_id, board_id) VALUES ('विशेष प्रश्न', NULL, @subject_iq_marathi_id, @class_4_id, @board_msce_id);
SET @chapter_vishesh_prashna_id = (SELECT id FROM chapters WHERE chapter_name = 'विशेष प्रश्न' AND subject_id = @subject_iq_marathi_id AND class_id = @class_4_id AND board_id = @board_msce_id LIMIT 1);
INSERT IGNORE INTO chapter_board_class_mapping (chapter_id, class_id, board_id) VALUES (@chapter_vishesh_prashna_id, @class_4_id, @board_msce_id);

INSERT IGNORE INTO topics (topic_name, chapter_id, subject_id, class_id, board_id) VALUES
('भावनिक व सामाजिक बुध्दिमत्ता', @chapter_vishesh_prashna_id, @subject_iq_marathi_id, @class_4_id, @board_msce_id);

COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
