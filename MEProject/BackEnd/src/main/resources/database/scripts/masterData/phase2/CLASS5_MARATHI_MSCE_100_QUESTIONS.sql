--liquibase formatted sql
--changeset {narendra}:{id}

-- ============================================================================
-- MSCE CLASS 5 MARATHI – 100 MCQ QUESTIONS
-- Board: MSCE - PRE UPPER PRIMARY SCHOLARSHIP EXAMINATION
-- Subject: Marathi – Third Language (subject_id: 38)
-- Class: 4
-- Medium: English
-- ============================================================================

-- Variable Declarations
SET @board_id = (SELECT id FROM board WHERE board_name = 'MSCE' LIMIT 1);
SET @subject_id = (SELECT subject_id FROM subject WHERE subject_name = 'Marathi – Third Language' LIMIT 1);
SET @class_id = (SELECT id FROM class WHERE class_name = '4' LIMIT 1);
SET @medium = 'English';
SET @created_by = 101;

-- Chapter Variables (4 chapters for Class 5 Marathi)
-- Dynamically fetch chapter IDs based on subject_id and board_id
SET @chapter_comprehension = (SELECT c.id FROM chapters c
    INNER JOIN chapter_board_class_mapping cbcm ON c.id = cbcm.chapter_id
    WHERE c.subject_id = @subject_id AND cbcm.board_id = @board_id AND cbcm.class_id = @class_id
    AND c.chapter_name = 'आकलन' LIMIT 1);

SET @chapter_vocabulary = (SELECT c.id FROM chapters c
    INNER JOIN chapter_board_class_mapping cbcm ON c.id = cbcm.chapter_id
    WHERE c.subject_id = @subject_id AND cbcm.board_id = @board_id AND cbcm.class_id = @class_id
    AND c.chapter_name = 'शब्दसंपत्ती' LIMIT 1);

SET @chapter_grammar = (SELECT c.id FROM chapters c
    INNER JOIN chapter_board_class_mapping cbcm ON c.id = cbcm.chapter_id
    WHERE c.subject_id = @subject_id AND cbcm.board_id = @board_id AND cbcm.class_id = @class_id
    AND c.chapter_name = 'कार्यात्मक व्याकरण' LIMIT 1);

SET @chapter_literature = (SELECT c.id FROM chapters c
    INNER JOIN chapter_board_class_mapping cbcm ON c.id = cbcm.chapter_id
    WHERE c.subject_id = @subject_id AND cbcm.board_id = @board_id AND cbcm.class_id = @class_id
    AND c.chapter_name = '1 ली ते 4 वी मराठी (सुलभभारती) विषयाशी संबंधित सामान्यज्ञान' LIMIT 1);

-- Topic Variables (22 topics)
-- Chapter 1: आकलन (Comprehension) - 4 topics
SET @topic_passage = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_comprehension AND subject_id = @subject_id
    AND topic_name = 'उतारा व त्यावरील प्रश्न' LIMIT 1);

SET @topic_poem = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_comprehension AND subject_id = @subject_id
    AND topic_name = 'कविता व त्यावरील प्रश्न' LIMIT 1);

SET @topic_dialogue = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_comprehension AND subject_id = @subject_id
    AND topic_name = 'संवाद व त्यावरील प्रश्न' LIMIT 1);

SET @topic_paragraph = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_comprehension AND subject_id = @subject_id
    AND topic_name = 'सुसंगत वाक्यांचा परिच्छेद' LIMIT 1);

-- Chapter 2: शब्दसंपत्ती (Vocabulary) - 11 topics
SET @topic_synonyms = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_vocabulary AND subject_id = @subject_id
    AND topic_name = 'समानार्थी शब्द' LIMIT 1);

SET @topic_antonyms = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_vocabulary AND subject_id = @subject_id
    AND topic_name = 'विरुध्द अर्थाचे शब्द' LIMIT 1);

SET @topic_one_word = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_vocabulary AND subject_id = @subject_id
    AND topic_name = 'शब्द समूहाबद्दल एक शब्द' LIMIT 1);

SET @topic_sound_words = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_vocabulary AND subject_id = @subject_id
    AND topic_name = 'ध्वनिदर्शक शब्द' LIMIT 1);

SET @topic_collective_words = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_vocabulary AND subject_id = @subject_id
    AND topic_name = 'समूहदर्शक शब्द' LIMIT 1);

SET @topic_dwelling_words = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_vocabulary AND subject_id = @subject_id
    AND topic_name = 'घरदर्शक शब्द' LIMIT 1);

SET @topic_phrases = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_vocabulary AND subject_id = @subject_id
    AND topic_name = 'वाक्प्रचार व त्यांचे अर्थ' LIMIT 1);

SET @topic_proverbs = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_vocabulary AND subject_id = @subject_id
    AND topic_name = 'म्हणी व त्यांचे अर्थ' LIMIT 1);

SET @topic_multiple_meanings = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_vocabulary AND subject_id = @subject_id
    AND topic_name = 'एकाच शब्दाचे भिन्न अर्थ' LIMIT 1);

SET @topic_compound_words = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_vocabulary AND subject_id = @subject_id
    AND topic_name = 'जोडशब्द' LIMIT 1);

SET @topic_word_formation = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_vocabulary AND subject_id = @subject_id
    AND topic_name = 'दिलेल्या अक्षरांपासून अर्थपूर्ण शब्द तयार करणे' LIMIT 1);

-- Chapter 3: कार्यात्मक व्याकरण (Functional Grammar) - 5 topics
SET @topic_parts_of_speech = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_grammar AND subject_id = @subject_id
    AND topic_name = 'शब्दांच्या जाती - नाम व क्रियापद' LIMIT 1);

SET @topic_gender = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_grammar AND subject_id = @subject_id
    AND topic_name = 'लिंग' LIMIT 1);

SET @topic_number = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_grammar AND subject_id = @subject_id
    AND topic_name = 'वचन' LIMIT 1);

SET @topic_punctuation = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_grammar AND subject_id = @subject_id
    AND topic_name = 'विरामचिन्हे (पूर्णविराम, स्वल्पविराम, प्रश्नचिन्ह)' LIMIT 1);

SET @topic_spelling = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_grammar AND subject_id = @subject_id
    AND topic_name = 'शुध्द व अशुध्द शब्द' LIMIT 1);

-- Chapter 4: सामान्यज्ञान (General Knowledge) - 2 topics
SET @topic_authors = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_literature AND subject_id = @subject_id
    AND topic_name = 'साहित्यिकांचे साहित्य व त्यांची टोपण नावे' LIMIT 1);

SET @topic_literary_knowledge = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_literature AND subject_id = @subject_id
    AND topic_name = 'साहित्य विषयक सामान्यज्ञान' LIMIT 1);

-- ============================================================================
-- 100 MCQ QUESTIONS
-- Distribution: Comprehension(20), Vocabulary(40), Grammar(30), Literature(10)
-- ============================================================================

INSERT INTO questions (
    board_id, subject_id, class_id, medium, chapter_id, topic_id,
    question_type, question_text, options, correct_answer, answer_explanation,
    skill_level, difficulty_level, created_by
) VALUES

-- ============================================================================
-- CHAPTER 1: आकलन (Comprehension) - 20 questions
-- ============================================================================

-- Topic: उतारा व त्यावरील प्रश्न (Passage and Questions) - 8 questions
(@board_id, @subject_id, @class_id, @medium, @chapter_comprehension, @topic_passage, 'MCQ',
'खालील वाक्यातील मुख्य कर्ता कोण आहे? "रामू रोज शाळेत जातो."',
'{"option1":"रामू","option2":"शाळेत","option3":"रोज","option4":"जातो"}',
'{"correctOption":1}',
'"रामू" हा मुख्य कर्ता आहे जो क्रिया करतो.',
'UNDERSTANDING', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_comprehension, @topic_passage, 'MCQ',
'"सीता बागेत फुले पाहते." या वाक्यात सीता काय करते?',
'{"option1":"खेळते","option2":"फुले पाहते","option3":"धावते","option4":"गाते"}',
'{"correctOption":2}',
'वाक्यात स्पष्टपणे "फुले पाहते" असे सांगितले आहे.',
'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_comprehension, @topic_passage, 'MCQ',
'"आकाश निळे आहे आणि ढग पांढरे आहेत." या वाक्यातून काय कळते?',
'{"option1":"पाऊस पडतो आहे","option2":"रात्र झाली आहे","option3":"हवामान चांगले आहे","option4":"वारा वाहतो आहे"}',
'{"correctOption":3}',
'निळे आकाश आणि पांढरे ढग चांगले हवामान दर्शवतात.',
'UNDERSTANDING', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_comprehension, @topic_passage, 'MCQ',
'"मुले खेळाच्या मैदानात धावत आहेत." या वाक्यात मुले कुठे आहेत?',
'{"option1":"घरात","option2":"शाळेत","option3":"खेळाच्या मैदानात","option4":"बागेत"}',
'{"correctOption":3}',
'वाक्यात "खेळाच्या मैदानात" असे स्पष्ट सांगितले आहे.',
'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_comprehension, @topic_passage, 'MCQ',
'"आई स्वयंपाक करते आणि बाबा वर्तमानपत्र वाचतात." या वाक्यातून काय समजते?',
'{"option1":"दोघेही काम करत आहेत","option2":"दोघेही झोपले आहेत","option3":"दोघेही खेळत आहेत","option4":"दोघेही बाहेर आहेत"}',
'{"correctOption":1}',
'आई आणि बाबा दोघेही आपापले काम करत आहेत.',
'UNDERSTANDING', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_comprehension, @topic_passage, 'MCQ',
'"पक्षी झाडावर बसून गात आहेत." या वाक्यात पक्षी काय करत आहेत?',
'{"option1":"उडत आहेत","option2":"खात आहेत","option3":"गात आहेत","option4":"झोपत आहेत"}',
'{"correctOption":3}',
'वाक्यात "गात आहेत" असे स्पष्ट सांगितले आहे.',
'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_comprehension, @topic_passage, 'MCQ',
'"शिक्षक वर्गात विद्यार्थ्यांना शिकवत आहेत." या वाक्यातील क्रिया कोणती?',
'{"option1":"शिक्षक","option2":"विद्यार्थी","option3":"शिकवत आहेत","option4":"वर्ग"}',
'{"correctOption":3}',
'"शिकवत आहेत" ही क्रिया आहे.',
'UNDERSTANDING', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_comprehension, @topic_passage, 'MCQ',
'"उन्हाळ्यात दिवस मोठे असतात आणि रात्र लहान असतात." या वाक्यातून काय समजते?',
'{"option1":"हिवाळ्याबद्दल माहिती","option2":"पावसाळ्याबद्दल माहिती","option3":"उन्हाळ्याची वैशिष्ट्ये","option4":"वसंत ऋतूबद्दल"}',
'{"correctOption":3}',
'हे वाक्य उन्हाळ्याच्या वैशिष्ट्यांबद्दल सांगते.',
'UNDERSTANDING', 'MEDIUM', @created_by),

-- Topic: कविता व त्यावरील प्रश्न (Poem and Questions) - 5 questions
(@board_id, @subject_id, @class_id, @medium, @chapter_comprehension, @topic_poem, 'MCQ',
'"फुलं फुलली बागेत, रंग रंगीत सुवासिक" या पंक्तीत काय फुलले आहे?',
'{"option1":"झाडे","option2":"फुले","option3":"फळे","option4":"पाने"}',
'{"correctOption":2}',
'पंक्तीत "फुलं फुलली" असे म्हटले आहे.',
'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_comprehension, @topic_poem, 'MCQ',
'"पक्षी गातात झाडावर, किलबिलाट सुरेख" या ओळीत पक्षी काय करतात?',
'{"option1":"झोपतात","option2":"उडतात","option3":"गातात","option4":"खातात"}',
'{"correctOption":3}',
'ओळीत "गातात" असे स्पष्ट सांगितले आहे.',
'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_comprehension, @topic_poem, 'MCQ',
'"चंद्र चमकतो रात्री, तारे टिपटिपतात" या पंक्तीतून कोणता काळ समजतो?',
'{"option1":"सकाळ","option2":"दुपार","option3":"रात्र","option4":"संध्याकाळ"}',
'{"correctOption":3}',
'पंक्तीत "रात्री" असा शब्द वापरला आहे.',
'UNDERSTANDING', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_comprehension, @topic_poem, 'MCQ',
'"नदी वाहते गातगाते, लाटा येती जाती" या ओळीतील मुख्य विषय काय आहे?',
'{"option1":"समुद्र","option2":"नदी","option3":"तलाव","option4":"विहीर"}',
'{"correctOption":2}',
'ओळीत "नदी" हा मुख्य विषय आहे.',
'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_comprehension, @topic_poem, 'MCQ',
'"सूर्य उगवतो पूर्वेला, अंधार पळून जातो" या पंक्तीचा अर्थ काय?',
'{"option1":"रात्र सुरू होते","option2":"सकाळ होते","option3":"दुपार होते","option4":"संध्याकाळ होते"}',
'{"correctOption":2}',
'सूर्य उगवणे म्हणजे सकाळ होणे.',
'UNDERSTANDING', 'MEDIUM', @created_by),

-- Topic: संवाद व त्यावरील प्रश्न (Dialogue and Questions) - 4 questions
(@board_id, @subject_id, @class_id, @medium, @chapter_comprehension, @topic_dialogue, 'MCQ',
'रमेश: "तू कुठे जातोस?" राज: "मी शाळेत जातो." राज कुठे जातो आहे?',
'{"option1":"घरी","option2":"शाळेत","option3":"बाजारात","option4":"बागेत"}',
'{"correctOption":2}',
'राजने स्पष्टपणे "मी शाळेत जातो" असे सांगितले.',
'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_comprehension, @topic_dialogue, 'MCQ',
'आई: "तू काय खाणार?" मुलगा: "मला भात आणि डाळ हवी." मुलगा काय खाणार?',
'{"option1":"भाकरी आणि भाजी","option2":"भात आणि डाळ","option3":"चपाती आणि भाजी","option4":"पोळी आणि भाजी"}',
'{"correctOption":2}',
'मुलगा "भात आणि डाळ" हवी असे म्हणाला.',
'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_comprehension, @topic_dialogue, 'MCQ',
'शिक्षक: "तुझे गृहपाठ केले का?" विद्यार्थी: "होय, मी केले." विद्यार्थ्याने गृहपाठ केले का?',
'{"option1":"होय","option2":"नाही","option3":"अर्धे केले","option4":"माहीत नाही"}',
'{"correctOption":1}',
'विद्यार्थ्याने "होय, मी केले" असे उत्तर दिले.',
'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_comprehension, @topic_dialogue, 'MCQ',
'मित्र १: "तुला खेळायला येणार का?" मित्र २: "नाही, मला अभ्यास करायचा आहे." मित्र २ काय करणार?',
'{"option1":"खेळणार","option2":"अभ्यास करणार","option3":"झोपणार","option4":"बाहेर जाणार"}',
'{"correctOption":2}',
'मित्र २ ने "अभ्यास करायचा आहे" असे सांगितले.',
'UNDERSTANDING', 'EASY', @created_by),

-- Topic: सुसंगत वाक्यांचा परिच्छेद (Coherent Paragraph) - 3 questions
(@board_id, @subject_id, @class_id, @medium, @chapter_comprehension, @topic_paragraph, 'MCQ',
'योग्य क्रम लावा: (१) मग तो अंघोळ करतो (२) रामू सकाळी उठतो (३) नंतर तो नाश्ता करतो',
'{"option1":"१, २, ३","option2":"२, १, ३","option3":"३, २, १","option4":"२, ३, १"}',
'{"correctOption":2}',
'योग्य क्रम: प्रथम उठणे, मग अंघोळ, नंतर नाश्ता.',
'APPLICATION', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_comprehension, @topic_paragraph, 'MCQ',
'योग्य क्रम लावा: (१) फुले फुलली (२) पाऊस पडला (३) बियाणे पेरले',
'{"option1":"३, २, १","option2":"१, २, ३","option3":"२, ३, १","option4":"३, १, २"}',
'{"correctOption":1}',
'योग्य क्रम: बियाणे पेरणे, पाऊस पडणे, फुले फुलणे.',
'APPLICATION', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_comprehension, @topic_paragraph, 'MCQ',
'खालीलपैकी कोणते वाक्य "मी शाळेत गेलो" नंतर येईल?',
'{"option1":"मी झोपलो","option2":"मी उठलो","option3":"मी अभ्यास केला","option4":"मी अंघोळ केली"}',
'{"correctOption":3}',
'शाळेत गेल्यानंतर अभ्यास करणे हा तार्किक क्रम आहे.',
'APPLICATION', 'MEDIUM', @created_by),

-- ============================================================================
-- CHAPTER 2: शब्दसंपत्ती (Vocabulary) - 40 questions
-- ============================================================================

-- Topic: समानार्थी शब्द (Synonyms) - 5 questions
(@board_id, @subject_id, @class_id, @medium, @chapter_vocabulary, @topic_synonyms, 'MCQ',
'"सुंदर" या शब्दाचा समानार्थी शब्द कोणता?',
'{"option1":"कुरूप","option2":"सुरेख","option3":"मोठा","option4":"लहान"}',
'{"correctOption":2}',
'"सुरेख" हा "सुंदर" चा समानार्थी शब्द आहे.',
'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_vocabulary, @topic_synonyms, 'MCQ',
'"आनंद" या शब्दाचा समानार्थी शब्द कोणता?',
'{"option1":"दुःख","option2":"सुख","option3":"राग","option4":"भीती"}',
'{"correctOption":2}',
'"सुख" हा "आनंद" चा समानार्थी शब्द आहे.',
'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_vocabulary, @topic_synonyms, 'MCQ',
'"प्रकाश" या शब्दाचा समानार्थी शब्द कोणता?',
'{"option1":"अंधार","option2":"उजेड","option3":"रात्र","option4":"काळोख"}',
'{"correctOption":2}',
'"उजेड" हा "प्रकाश" चा समानार्थी शब्द आहे.',
'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_vocabulary, @topic_synonyms, 'MCQ',
'"वेगवान" या शब्दाचा समानार्थी शब्द कोणता?',
'{"option1":"हळू","option2":"जलद","option3":"मंद","option4":"सावकाश"}',
'{"correctOption":2}',
'"जलद" हा "वेगवान" चा समानार्थी शब्द आहे.',
'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_vocabulary, @topic_synonyms, 'MCQ',
'"धाडसी" या शब्दाचा समानार्थी शब्द कोणता?',
'{"option1":"भित्रा","option2":"शूर","option3":"कमकुवत","option4":"घाबरट"}',
'{"correctOption":2}',
'"शूर" हा "धाडसी" चा समानार्थी शब्द आहे.',
'KNOWLEDGE', 'EASY', @created_by),

-- Topic: विरुध्द अर्थाचे शब्द (Antonyms) - 5 questions
(@board_id, @subject_id, @class_id, @medium, @chapter_vocabulary, @topic_antonyms, 'MCQ',
'"मोठा" या शब्दाचा विरुद्धार्थी शब्द कोणता?',
'{"option1":"लहान","option2":"थोर","option3":"विशाल","option4":"उंच"}',
'{"correctOption":1}',
'"लहान" हा "मोठा" चा विरुद्धार्थी शब्द आहे.',
'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_vocabulary, @topic_antonyms, 'MCQ',
'"दिवस" या शब्दाचा विरुद्धार्थी शब्द कोणता?',
'{"option1":"सकाळ","option2":"रात्र","option3":"दुपार","option4":"संध्याकाळ"}',
'{"correctOption":2}',
'"रात्र" हा "दिवस" चा विरुद्धार्थी शब्द आहे.',
'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_vocabulary, @topic_antonyms, 'MCQ',
'"चांगला" या शब्दाचा विरुद्धार्थी शब्द कोणता?',
'{"option1":"सुंदर","option2":"वाईट","option3":"उत्तम","option4":"श्रेष्ठ"}',
'{"correctOption":2}',
'"वाईट" हा "चांगला" चा विरुद्धार्थी शब्द आहे.',
'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_vocabulary, @topic_antonyms, 'MCQ',
'"गरम" या शब्दाचा विरुद्धार्थी शब्द कोणता?',
'{"option1":"थंड","option2":"उष्ण","option3":"कोमट","option4":"तापलेला"}',
'{"correctOption":1}',
'"थंड" हा "गरम" चा विरुद्धार्थी शब्द आहे.',
'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_vocabulary, @topic_antonyms, 'MCQ',
'"जुना" या शब्दाचा विरुद्धार्थी शब्द कोणता?',
'{"option1":"नवीन","option2":"पुराना","option3":"प्राचीन","option4":"फाटका"}',
'{"correctOption":1}',
'"नवीन" हा "जुना" चा विरुद्धार्थी शब्द आहे.',
'KNOWLEDGE', 'EASY', @created_by),

-- Topic: शब्द समूहाबद्दल एक शब्द (One Word Substitution) - 4 questions
(@board_id, @subject_id, @class_id, @medium, @chapter_vocabulary, @topic_one_word, 'MCQ',
'"जे वाचता येते" त्याला काय म्हणतात?',
'{"option1":"वाचनीय","option2":"श्रवणीय","option3":"दृश्य","option4":"स्पर्शनीय"}',
'{"correctOption":1}',
'"वाचनीय" म्हणजे जे वाचता येते.',
'KNOWLEDGE', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_vocabulary, @topic_one_word, 'MCQ',
'"ज्याला भीती नाही" त्याला काय म्हणतात?',
'{"option1":"भित्रा","option2":"निर्भय","option3":"घाबरट","option4":"कमकुवत"}',
'{"correctOption":2}',
'"निर्भय" म्हणजे ज्याला भीती नाही.',
'KNOWLEDGE', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_vocabulary, @topic_one_word, 'MCQ',
'"जो जगात प्रसिद्ध आहे" त्याला काय म्हणतात?',
'{"option1":"अज्ञात","option2":"विख्यात","option3":"गुप्त","option4":"अनोळखी"}',
'{"correctOption":2}',
'"विख्यात" म्हणजे प्रसिद्ध.',
'KNOWLEDGE', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_vocabulary, @topic_one_word, 'MCQ',
'"ज्याचा अर्थ नाही" त्याला काय म्हणतात?',
'{"option1":"अर्थपूर्ण","option2":"अर्थहीन","option3":"सार्थक","option4":"अर्थसंपन्न"}',
'{"correctOption":2}',
'"अर्थहीन" म्हणजे ज्याचा अर्थ नाही.',
'KNOWLEDGE', 'MEDIUM', @created_by),

-- Topic: ध्वनिदर्शक शब्द (Onomatopoeia) - 3 questions
(@board_id, @subject_id, @class_id, @medium, @chapter_vocabulary, @topic_sound_words, 'MCQ',
'कुत्रा ______ करतो.',
'{"option1":"म्याऊं","option2":"भुंकतो","option3":"किलबिलतो","option4":"घुंगरतो"}',
'{"correctOption":2}',
'कुत्रा "भुंकतो" हा त्याचा ध्वनिदर्शक शब्द आहे.',
'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_vocabulary, @topic_sound_words, 'MCQ',
'मांजर ______ करते.',
'{"option1":"म्याऊं","option2":"भुंकते","option3":"घोंगावते","option4":"किलबिलते"}',
'{"correctOption":1}',
'मांजर "म्याऊं" करते हा त्याचा ध्वनिदर्शक शब्द आहे.',
'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_vocabulary, @topic_sound_words, 'MCQ',
'पक्षी ______ करतात.',
'{"option1":"घुंगरतात","option2":"भुंकतात","option3":"किलबिलतात","option4":"म्याऊं करतात"}',
'{"correctOption":3}',
'पक्षी "किलबिलतात" हा त्यांचा ध्वनिदर्शक शब्द आहे.',
'KNOWLEDGE', 'EASY', @created_by),

-- Topic: समूहदर्शक शब्द (Collective Nouns) - 3 questions
(@board_id, @subject_id, @class_id, @medium, @chapter_vocabulary, @topic_collective_words, 'MCQ',
'फुलांच्या समूहाला काय म्हणतात?',
'{"option1":"गुच्छ","option2":"कळप","option3":"झुंड","option4":"पंक्ती"}',
'{"correctOption":1}',
'फुलांच्या समूहाला "गुच्छ" म्हणतात.',
'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_vocabulary, @topic_collective_words, 'MCQ',
'गुरांच्या समूहाला काय म्हणतात?',
'{"option1":"गुच्छ","option2":"कळप","option3":"पंक्ती","option4":"गट"}',
'{"correctOption":2}',
'गुरांच्या समूहाला "कळप" म्हणतात.',
'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_vocabulary, @topic_collective_words, 'MCQ',
'पक्ष्यांच्या समूहाला काय म्हणतात?',
'{"option1":"कळप","option2":"गुच्छ","option3":"झुंड","option4":"पंक्ती"}',
'{"correctOption":3}',
'पक्ष्यांच्या समूहाला "झुंड" म्हणतात.',
'KNOWLEDGE', 'EASY', @created_by),

-- Topic: घरदर्शक शब्द (Dwelling Words) - 3 questions
(@board_id, @subject_id, @class_id, @medium, @chapter_vocabulary, @topic_dwelling_words, 'MCQ',
'सिंहाच्या घराला काय म्हणतात?',
'{"option1":"घरटे","option2":"गुहा","option3":"कोंडवाडा","option4":"विळा"}',
'{"correctOption":2}',
'सिंहाच्या घराला "गुहा" म्हणतात.',
'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_vocabulary, @topic_dwelling_words, 'MCQ',
'पक्ष्याच्या घराला काय म्हणतात?',
'{"option1":"घरटे","option2":"गुहा","option3":"विळा","option4":"कोंडवाडा"}',
'{"correctOption":1}',
'पक्ष्याच्या घराला "घरटे" म्हणतात.',
'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_vocabulary, @topic_dwelling_words, 'MCQ',
'मधमाश्यांच्या घराला काय म्हणतात?',
'{"option1":"घरटे","option2":"गुहा","option3":"पोळे","option4":"कोंडवाडा"}',
'{"correctOption":3}',
'मधमाश्यांच्या घराला "पोळे" म्हणतात.',
'KNOWLEDGE', 'EASY', @created_by),

-- Topic: वाक्प्रचार व त्यांचे अर्थ (Phrases and Meanings) - 4 questions
(@board_id, @subject_id, @class_id, @medium, @chapter_vocabulary, @topic_phrases, 'MCQ',
'"डोळे काढणे" या वाक्प्रचाराचा अर्थ काय?',
'{"option1":"पाहणे","option2":"वाट पाहणे","option3":"आशा बाळगणे","option4":"दुखी होणे"}',
'{"correctOption":2}',
'"डोळे काढणे" म्हणजे वाट पाहणे.',
'UNDERSTANDING', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_vocabulary, @topic_phrases, 'MCQ',
'"हात धुवून घालगे असणे" या वाक्प्रचाराचा अर्थ काय?',
'{"option1":"अंगणी येणे","option2":"मदत करणे","option3":"नाते निर्माण होणे","option4":"मैत्री होणे"}',
'{"correctOption":3}',
'"हात धुवून घालगे असणे" म्हणजे नाते निर्माण होणे.',
'UNDERSTANDING', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_vocabulary, @topic_phrases, 'MCQ',
'"कान भरणे" या वाक्प्रचाराचा अर्थ काय?',
'{"option1":"ऐकणे","option2":"चुगली करणे","option3":"मदत करणे","option4":"बोलणे"}',
'{"correctOption":2}',
'"कान भरणे" म्हणजे चुगली करणे.',
'UNDERSTANDING', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_vocabulary, @topic_phrases, 'MCQ',
'"डोके खाणे" या वाक्प्रचाराचा अर्थ काय?',
'{"option1":"त्रास देणे","option2":"मारणे","option3":"खाणे","option4":"बोलणे"}',
'{"correctOption":1}',
'"डोके खाणे" म्हणजे त्रास देणे.',
'UNDERSTANDING', 'MEDIUM', @created_by),

-- Topic: म्हणी व त्यांचे अर्थ (Proverbs and Meanings) - 4 questions
(@board_id, @subject_id, @class_id, @medium, @chapter_vocabulary, @topic_proverbs, 'MCQ',
'"अपयश हे यशाचे पाऊल" या म्हणीचा अर्थ काय?',
'{"option1":"अपयश मिळणे चांगले","option2":"अपयशातून शिकायला मिळते","option3":"यश मिळणार नाही","option4":"प्रयत्न करू नये"}',
'{"correctOption":2}',
'अपयशातून आपण शिकतो आणि यश मिळवतो.',
'UNDERSTANDING', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_vocabulary, @topic_proverbs, 'MCQ',
'"जसे पेरणी तसे कापणी" या म्हणीचा अर्थ काय?',
'{"option1":"शेती करणे","option2":"जसे कर्म तसे फळ","option3":"पैसे मिळतात","option4":"काम करावे"}',
'{"correctOption":2}',
'आपण जसे कर्म करतो तसे फळ मिळते.',
'UNDERSTANDING', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_vocabulary, @topic_proverbs, 'MCQ',
'"एकाच तळात सर्व बोटे सारखी नाहीत" या म्हणीचा अर्थ काय?',
'{"option1":"सगळे सारखे असतात","option2":"सर्व वेगवेगळे असतात","option3":"बोटे लहान असतात","option4":"तळवार लहान असतो"}',
'{"correctOption":2}',
'सर्व व्यक्ती वेगवेगळ्या स्वभावाच्या असतात.',
'UNDERSTANDING', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_vocabulary, @topic_proverbs, 'MCQ',
'"रानात मोर नाचला तरी कुणी पाहणारा नाही" या म्हणीचा अर्थ काय?',
'{"option1":"मोर नाचतो","option2":"कृती व्यर्थ गेली","option3":"वनात जावे","option4":"पाहणे महत्त्वाचे"}',
'{"correctOption":2}',
'योग्य वेळी आणि ठिकाणी नसल्यास कृती व्यर्थ जाते.',
'UNDERSTANDING', 'HARD', @created_by),

-- Topic: एकाच शब्दाचे भिन्न अर्थ (Multiple Meanings) - 3 questions
(@board_id, @subject_id, @class_id, @medium, @chapter_vocabulary, @topic_multiple_meanings, 'MCQ',
'"फळ" या शब्दाचे दोन अर्थ कोणते?',
'{"option1":"परिणाम आणि खाद्यपदार्थ","option2":"फूल आणि झाड","option3":"पाणी आणि खडक","option4":"दूध आणि पाणी"}',
'{"correctOption":1}',
'"फळ" म्हणजे खाण्याचे फळ किंवा कर्माचे फळ (परिणाम).',
'UNDERSTANDING', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_vocabulary, @topic_multiple_meanings, 'MCQ',
'"पत्र" या शब्दाचे दोन अर्थ कोणते?',
'{"option1":"पान आणि चिठ्ठी","option2":"फूल आणि फळ","option3":"पाणी आणि दूध","option4":"झाड आणि फांदी"}',
'{"correctOption":1}',
'"पत्र" म्हणजे झाडाचे पान किंवा लिहिलेली चिठ्ठी.',
'UNDERSTANDING', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_vocabulary, @topic_multiple_meanings, 'MCQ',
'"कान" या शब्दाचे दोन अर्थ कोणते?',
'{"option1":"ऐकण्याचे अवयव आणि भांड्याचा हातभारा","option2":"डोळा आणि नाक","option3":"हात आणि पाय","option4":"तोंड आणि दात"}',
'{"correctOption":1}',
'"कान" म्हणजे ऐकण्याचा अवयव किंवा भांड्याचा हातभारा.',
'UNDERSTANDING', 'MEDIUM', @created_by),

-- Topic: जोडशब्द (Compound Words) - 3 questions
(@board_id, @subject_id, @class_id, @medium, @chapter_vocabulary, @topic_compound_words, 'MCQ',
'"वा + हन = ____" कोणता जोडशब्द तयार होईल?',
'{"option1":"वाहन","option2":"वाहणे","option3":"वाहक","option4":"वाही"}',
'{"correctOption":1}',
'"वा + हन = वाहन" हा योग्य जोडशब्द आहे.',
'KNOWLEDGE', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_vocabulary, @topic_compound_words, 'MCQ',
'"गृह + पाठ = ____" कोणता जोडशब्द तयार होईल?',
'{"option1":"गृहपाठ","option2":"गृहपठ","option3":"घरपाठ","option4":"गृपाठ"}',
'{"correctOption":1}',
'"गृह + पाठ = गृहपाठ" हा योग्य जोडशब्द आहे.',
'KNOWLEDGE', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_vocabulary, @topic_compound_words, 'MCQ',
'"विद्या + आलय = ____" कोणता जोडशब्द तयार होईल?',
'{"option1":"विद्यायलय","option2":"विद्यालय","option3":"विदयालय","option4":"बिद्यालय"}',
'{"correctOption":2}',
'"विद्या + आलय = विद्यालय" (आ + आ = आ नियमानुसार).',
'KNOWLEDGE', 'MEDIUM', @created_by),

-- Topic: दिलेल्या अक्षरांपासून अर्थपूर्ण शब्द तयार करणे (Word Formation) - 3 questions
(@board_id, @subject_id, @class_id, @medium, @chapter_vocabulary, @topic_word_formation, 'MCQ',
'"म, ह, ल, ा" या अक्षरांपासून कोणता शब्द तयार होईल?',
'{"option1":"महाल","option2":"हमाल","option3":"लहाम","option4":"महला"}',
'{"correctOption":1}',
'"म + ह + ा + ल = महाल" हा अर्थपूर्ण शब्द आहे.',
'APPLICATION', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_vocabulary, @topic_word_formation, 'MCQ',
'"श, ळ, ा" या अक्षरांपासून कोणता शब्द तयार होईल?',
'{"option1":"शाळ","option2":"शळा","option3":"शाळा","option4":"ळशा"}',
'{"correctOption":3}',
'"श + ा + ळ + ा = शाळा" हा अर्थपूर्ण शब्द आहे.',
'APPLICATION', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_vocabulary, @topic_word_formation, 'MCQ',
'"प, क, ष, ी" या अक्षरांपासून कोणता शब्द तयार होईल?',
'{"option1":"पषकी","option2":"षीपक","option3":"पक्षी","option4":"कीपष"}',
'{"correctOption":3}',
'"प + क् + ष + ी = पक्षी" हा अर्थपूर्ण शब्द आहे.',
'APPLICATION', 'MEDIUM', @created_by),

-- ============================================================================
-- CHAPTER 3: कार्यात्मक व्याकरण (Functional Grammar) - 30 questions
-- ============================================================================

-- Topic: शब्दांच्या जाती - नाम व क्रियापद (Parts of Speech) - 8 questions
(@board_id, @subject_id, @class_id, @medium, @chapter_grammar, @topic_parts_of_speech, 'MCQ',
'खालीलपैकी कोणता शब्द नाम आहे?',
'{"option1":"धावणे","option2":"मुलगा","option3":"सुंदर","option4":"हळू"}',
'{"correctOption":2}',
'"मुलगा" हे नाम (संज्ञा) आहे.',
'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_grammar, @topic_parts_of_speech, 'MCQ',
'"मुलगी शाळेत जाते" या वाक्यातील क्रियापद कोणते?',
'{"option1":"मुलगी","option2":"शाळेत","option3":"जाते","option4":"मुलगी आणि शाळेत"}',
'{"correctOption":3}',
'"जाते" हे क्रियापद आहे.',
'UNDERSTANDING', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_grammar, @topic_parts_of_speech, 'MCQ',
'खालीलपैकी कोणता शब्द क्रियापद आहे?',
'{"option1":"घर","option2":"खेळणे","option3":"मोठा","option4":"आज"}',
'{"correctOption":2}',
'"खेळणे" हे क्रियापद आहे.',
'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_grammar, @topic_parts_of_speech, 'MCQ',
'"रामू पुस्तक वाचतो" या वाक्यातील नाम कोणते?',
'{"option1":"रामू आणि पुस्तक","option2":"वाचतो","option3":"रामू","option4":"पुस्तक"}',
'{"correctOption":1}',
'"रामू" आणि "पुस्तक" ही दोन्ही नामे आहेत.',
'UNDERSTANDING', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_grammar, @topic_parts_of_speech, 'MCQ',
'खालीलपैकी कोणता शब्द स्थळनाम आहे?',
'{"option1":"मुंबई","option2":"खेळणे","option3":"सुंदर","option4":"मुलगा"}',
'{"correctOption":1}',
'"मुंबई" हे स्थळनाम आहे.',
'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_grammar, @topic_parts_of_speech, 'MCQ',
'"पक्षी आकाशात उडतात" या वाक्यातील क्रिया कोणती?',
'{"option1":"पक्षी","option2":"आकाशात","option3":"उडतात","option4":"पक्षी आणि आकाशात"}',
'{"correctOption":3}',
'"उडतात" ही क्रिया आहे.',
'UNDERSTANDING', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_grammar, @topic_parts_of_speech, 'MCQ',
'खालीलपैकी कोणता शब्द भावनाम आहे?',
'{"option1":"आनंद","option2":"मुलगा","option3":"खेळणे","option4":"मुंबई"}',
'{"correctOption":1}',
'"आनंद" हे भावनाम आहे.',
'KNOWLEDGE', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_grammar, @topic_parts_of_speech, 'MCQ',
'"आई स्वयंपाक करते" या वाक्यात "आई" हा कोणता शब्दप्रकार आहे?',
'{"option1":"नाम","option2":"क्रियापद","option3":"विशेषण","option4":"क्रियाविशेषण"}',
'{"correctOption":1}',
'"आई" हे नाम आहे.',
'UNDERSTANDING', 'EASY', @created_by),

-- Topic: लिंग (Gender) - 6 questions
(@board_id, @subject_id, @class_id, @medium, @chapter_grammar, @topic_gender, 'MCQ',
'"मुलगा" या शब्दाचे स्त्रीलिंग काय?',
'{"option1":"मुलगी","option2":"बाई","option3":"बहीण","option4":"आई"}',
'{"correctOption":1}',
'"मुलगा" चे स्त्रीलिंग "मुलगी" आहे.',
'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_grammar, @topic_gender, 'MCQ',
'"बाबा" या शब्दाचे स्त्रीलिंग काय?',
'{"option1":"बहीण","option2":"आई","option3":"मुलगी","option4":"आजी"}',
'{"correctOption":2}',
'"बाबा" चे स्त्रीलिंग "आई" आहे.',
'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_grammar, @topic_gender, 'MCQ',
'"सिंह" या शब्दाचे स्त्रीलिंग काय?',
'{"option1":"सिंहीण","option2":"वाघीण","option3":"मांजर","option4":"कुत्री"}',
'{"correctOption":1}',
'"सिंह" चे स्त्रीलिंग "सिंहीण" आहे.',
'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_grammar, @topic_gender, 'MCQ',
'खालीलपैकी कोणता शब्द पुल्लिंगी आहे?',
'{"option1":"मुलगी","option2":"मुलगा","option3":"बहीण","option4":"आई"}',
'{"correctOption":2}',
'"मुलगा" हा पुल्लिंगी शब्द आहे.',
'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_grammar, @topic_gender, 'MCQ',
'"आजोबा" या शब्दाचे स्त्रीलिंग काय?',
'{"option1":"आजी","option2":"आई","option3":"मावशी","option4":"काकू"}',
'{"correctOption":1}',
'"आजोबा" चे स्त्रीलिंग "आजी" आहे.',
'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_grammar, @topic_gender, 'MCQ',
'खालीलपैकी कोणता शब्द स्त्रीलिंगी आहे?',
'{"option1":"मुलगा","option2":"बाबा","option3":"सीता","option4":"रामू"}',
'{"correctOption":3}',
'"सीता" हा स्त्रीलिंगी शब्द आहे.',
'KNOWLEDGE', 'EASY', @created_by),

-- Topic: वचन (Number) - 6 questions
(@board_id, @subject_id, @class_id, @medium, @chapter_grammar, @topic_number, 'MCQ',
'"मुलगा" या शब्दाचे अनेकवचन काय?',
'{"option1":"मुलगे","option2":"मुले","option3":"मुलगी","option4":"मुलगो"}',
'{"correctOption":2}',
'"मुलगा" चे अनेकवचन "मुले" आहे.',
'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_grammar, @topic_number, 'MCQ',
'"पुस्तक" या शब्दाचे अनेकवचन काय?',
'{"option1":"पुस्तके","option2":"पुस्तक","option3":"पुस्तकं","option4":"पुस्तकां"}',
'{"correctOption":1}',
'"पुस्तक" चे अनेकवचन "पुस्तके" आहे.',
'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_grammar, @topic_number, 'MCQ',
'"मुली खेळतात" या वाक्यात कोणते वचन आहे?',
'{"option1":"एकवचन","option2":"अनेकवचन","option3":"द्विवचन","option4":"कोणतेही नाही"}',
'{"correctOption":2}',
'"मुली" हा अनेकवचनी शब्द आहे.',
'UNDERSTANDING', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_grammar, @topic_number, 'MCQ',
'खालीलपैकी कोणता शब्द एकवचनी आहे?',
'{"option1":"मुले","option2":"मुलगा","option3":"पुस्तके","option4":"फुले"}',
'{"correctOption":2}',
'"मुलगा" हा एकवचनी शब्द आहे.',
'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_grammar, @topic_number, 'MCQ',
'"फूल" या शब्दाचे अनेकवचन काय?',
'{"option1":"फुले","option2":"फुलं","option3":"फुलां","option4":"फुला"}',
'{"correctOption":1}',
'"फूल" चे अनेकवचन "फुले" आहे.',
'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_grammar, @topic_number, 'MCQ',
'"पक्षी उडतो" या वाक्यात कोणते वचन आहे?',
'{"option1":"एकवचन","option2":"अनेकवचन","option3":"द्विवचन","option4":"कोणतेही नाही"}',
'{"correctOption":1}',
'"पक्षी उडतो" हे एकवचन आहे.',
'UNDERSTANDING', 'EASY', @created_by),

-- Topic: विरामचिन्हे (Punctuation) - 5 questions
(@board_id, @subject_id, @class_id, @medium, @chapter_grammar, @topic_punctuation, 'MCQ',
'वाक्याच्या शेवटी कोणते विरामचिन्ह येते?',
'{"option1":"स्वल्पविराम (,)","option2":"पूर्णविराम (.)","option3":"प्रश्नचिन्ह (?)","option4":"उद्गारवाचक (!)"}',
'{"correctOption":2}',
'वाक्याच्या शेवटी पूर्णविराम (.) येतो.',
'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_grammar, @topic_punctuation, 'MCQ',
'प्रश्न विचारताना कोणते चिन्ह वापरतात?',
'{"option1":"पूर्णविराम (.)","option2":"स्वल्पविराम (,)","option3":"प्रश्नचिन्ह (?)","option4":"अर्धविराम (;)"}',
'{"correctOption":3}',
'प्रश्न विचारताना प्रश्नचिन्ह (?) वापरतात.',
'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_grammar, @topic_punctuation, 'MCQ',
'"मला सफरचंद, केळी, आणि द्राक्षे आवडतात" या वाक्यात कोणते विरामचिन्ह वापरले आहे?',
'{"option1":"पूर्णविराम","option2":"स्वल्पविराम","option3":"प्रश्नचिन्ह","option4":"उद्गारवाचक"}',
'{"correctOption":2}',
'यादी देताना स्वल्पविराम (,) वापरतात.',
'UNDERSTANDING', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_grammar, @topic_punctuation, 'MCQ',
'"तू कुठे जातोस____" या वाक्याच्या शेवटी कोणते चिन्ह येईल?',
'{"option1":"पूर्णविराम (.)","option2":"प्रश्नचिन्ह (?)","option3":"स्वल्पविराम (,)","option4":"उद्गारवाचक (!)"}',
'{"correctOption":2}',
'हा प्रश्न असल्याने प्रश्नचिन्ह (?) येईल.',
'APPLICATION', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_grammar, @topic_punctuation, 'MCQ',
'खालीलपैकी कोणते वाक्य योग्य आहे?',
'{"option1":"मी शाळेत जातो","option2":"मी शाळेत जातो.","option3":"मी, शाळेत जातो","option4":"मी शाळेत? जातो"}',
'{"correctOption":2}',
'वाक्याच्या शेवटी पूर्णविराम असावा.',
'UNDERSTANDING', 'MEDIUM', @created_by),

-- Topic: शुध्द व अशुध्द शब्द (Correct and Incorrect Spelling) - 5 questions
(@board_id, @subject_id, @class_id, @medium, @chapter_grammar, @topic_spelling, 'MCQ',
'योग्य शब्दलेखन निवडा:',
'{"option1":"विद्यालय","option2":"बिद्यालय","option3":"विदयालय","option4":"बिदयालय"}',
'{"correctOption":1}',
'योग्य शब्दलेखन "विद्यालय" आहे.',
'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_grammar, @topic_spelling, 'MCQ',
'योग्य शब्दलेखन निवडा:',
'{"option1":"स्वतंत्र","option2":"सवतंत्र","option3":"सवातंत्र","option4":"सवतनत्र"}',
'{"correctOption":1}',
'योग्य शब्दलेखन "स्वतंत्र" आहे.',
'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_grammar, @topic_spelling, 'MCQ',
'योग्य शब्दलेखन निवडा:',
'{"option1":"शिक्षक","option2":"षिक्षक","option3":"शीक्षक","option4":"शिकशक"}',
'{"correctOption":1}',
'योग्य शब्दलेखन "शिक्षक" आहे.',
'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_grammar, @topic_spelling, 'MCQ',
'योग्य शब्दलेखन निवडा:',
'{"option1":"प्रकाष","option2":"परकाश","option3":"प्रकाश","option4":"परकाष"}',
'{"correctOption":3}',
'योग्य शब्दलेखन "प्रकाश" आहे.',
'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_grammar, @topic_spelling, 'MCQ',
'खालीलपैकी कोणता शब्द चुकीचा आहे?',
'{"option1":"शाळा","option2":"पुस्तक","option3":"बिद्यार्थी","option4":"शिक्षक"}',
'{"correctOption":3}',
'योग्य शब्दलेखन "विद्यार्थी" आहे, "बिद्यार्थी" चुकीचे आहे.',
'UNDERSTANDING', 'MEDIUM', @created_by),

-- ============================================================================
-- CHAPTER 4: सामान्यज्ञान (General Knowledge) - 10 questions
-- ============================================================================

-- Topic: साहित्यिकांचे साहित्य व त्यांची टोपण नावे (Authors and Pen Names) - 5 questions
(@board_id, @subject_id, @class_id, @medium, @chapter_literature, @topic_authors, 'MCQ',
'पु. ल. देशपांडे यांचे पूर्ण नाव काय?',
'{"option1":"पुरुषोत्तम लक्ष्मण देशपांडे","option2":"प्रभाकर लक्ष्मण देशपांडे","option3":"पांडुरंग लक्ष्मण देशपांडे","option4":"परशुराम लक्ष्मण देशपांडे"}',
'{"correctOption":1}',
'पु. ल. देशपांडे यांचे पूर्ण नाव पुरुषोत्तम लक्ष्मण देशपांडे आहे.',
'KNOWLEDGE', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_literature, @topic_authors, 'MCQ',
'"मराठीच्या शाळा" हे पुस्तक कोणी लिहिले?',
'{"option1":"महात्मा गांधी","option2":"महात्मा ज्योतिबा फुले","option3":"लोकमान्य टिळक","option4":"गोपाळ गणेश आगरकर"}',
'{"correctOption":2}',
'महात्मा ज्योतिबा फुले यांनी "मराठीच्या शाळा" लिहिली.',
'KNOWLEDGE', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_literature, @topic_authors, 'MCQ',
'संत तुकाराम यांनी कोणत्या प्रकारचे साहित्य लिहिले?',
'{"option1":"कादंबरी","option2":"नाटक","option3":"अभंग","option4":"कथा"}',
'{"correctOption":3}',
'संत तुकाराम यांनी अभंग लिहिले.',
'KNOWLEDGE', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_literature, @topic_authors, 'MCQ',
'"माझी जन्मठेप" आत्मचरित्र कोणाचे आहे?',
'{"option1":"महात्मा गांधी","option2":"छत्रपती शिवाजी महाराज","option3":"बाबासाहेब आंबेडकर","option4":"लोकमान्य टिळक"}',
'{"correctOption":3}',
'"माझी जन्मठेप" हे बाबासाहेब आंबेडकर यांचे आत्मचरित्र आहे.',
'KNOWLEDGE', 'HARD', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_literature, @topic_authors, 'MCQ',
'कोणत्या कवीला "कविसम्राट" म्हणतात?',
'{"option1":"संत तुकाराम","option2":"कुसुमाग्रज","option3":"संत ज्ञानेश्वर","option4":"सुरेश भट"}',
'{"correctOption":2}',
'कुसुमाग्रज (वि. वा. शिरवाडकर) यांना "कविसम्राट" म्हणतात.',
'KNOWLEDGE', 'HARD', @created_by),

-- Topic: साहित्य विषयक सामान्यज्ञान (Literary General Knowledge) - 5 questions
(@board_id, @subject_id, @class_id, @medium, @chapter_literature, @topic_literary_knowledge, 'MCQ',
'मराठी भाषा कोणत्या लिपीत लिहितात?',
'{"option1":"रोमन","option2":"देवनागरी","option3":"गुरुमुखी","option4":"उर्दू"}',
'{"correctOption":2}',
'मराठी भाषा देवनागरी लिपीत लिहितात.',
'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_literature, @topic_literary_knowledge, 'MCQ',
'महाराष्ट्राची राजधानी कोणती?',
'{"option1":"पुणे","option2":"नागपूर","option3":"मुंबई","option4":"औरंगाबाद"}',
'{"correctOption":3}',
'मुंबई ही महाराष्ट्राची राजधानी आहे.',
'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_literature, @topic_literary_knowledge, 'MCQ',
'"ज्ञानेश्वरी" ग्रंथ कोणी लिहिला?',
'{"option1":"संत तुकाराम","option2":"संत ज्ञानेश्वर","option3":"संत नामदेव","option4":"संत एकनाथ"}',
'{"correctOption":2}',
'"ज्ञानेश्वरी" संत ज्ञानेश्वर यांनी लिहिली.',
'KNOWLEDGE', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_literature, @topic_literary_knowledge, 'MCQ',
'मराठी वर्णमालेत किती स्वर आहेत?',
'{"option1":"10","option2":"12","option3":"13","option4":"14"}',
'{"correctOption":2}',
'मराठी वर्णमालेत 12 स्वर आहेत.',
'KNOWLEDGE', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_literature, @topic_literary_knowledge, 'MCQ',
'कोणत्या ग्रंथाला "मराठी साहित्याचे पहिले महाकाव्य" म्हणतात?',
'{"option1":"ज्ञानेश्वरी","option2":"रामायण","option3":"महाभारत","option4":"भागवत"}',
'{"correctOption":1}',
'"ज्ञानेश्वरी" ला मराठी साहित्याचे पहिले महाकाव्य म्हणतात.',
'KNOWLEDGE', 'HARD', @created_by);

-- ============================================================================
-- COMPLETION MESSAGE
-- ============================================================================

SELECT 'MSCE Class 5 Marathi questions insertion completed!' as status;
SELECT 'Total: 100 MCQ questions' as summary;
SELECT 'Distribution: Comprehension(20), Vocabulary(40), Grammar(30), Literature(10)' as breakdown;

