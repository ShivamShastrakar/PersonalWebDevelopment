--liquibase formatted sql
--changeset {narendra}:{id}

-- =============================================
-- Class 4 Marathi - Third Language Questions
-- Board: MSCE - PRE UPPER PRIMARY SCHOLARSHIP EXAMINATION
-- Class: 4 (class_id = 2)
-- Subject: Marathi – Third Language (subject_id = 38)
-- Medium: English
-- Total Questions: 110 (5 questions per topic across all skill levels and difficulties)
-- =============================================

-- Set variables for board, class, subject, and medium
SET @board_id = (SELECT id FROM board WHERE board_name = 'MSCE' LIMIT 1);
SET @subject_id = (SELECT subject_id FROM subject WHERE subject_name = 'Marathi – Third Language' LIMIT 1);
SET @class_id = (SELECT id FROM class WHERE class_name = '4' LIMIT 1);
SET @medium = 'English';
SET @created_by = 101;

-- Chapter Variables
SET @chapter_aakalan = (SELECT c.id FROM chapters c
    INNER JOIN chapter_board_class_mapping cbcm ON c.id = cbcm.chapter_id
    WHERE c.subject_id = @subject_id AND cbcm.board_id = @board_id AND cbcm.class_id = @class_id
    AND c.chapter_name = 'आकलन' LIMIT 1);

SET @chapter_shabdasampatti = (SELECT c.id FROM chapters c
    INNER JOIN chapter_board_class_mapping cbcm ON c.id = cbcm.chapter_id
    WHERE c.subject_id = @subject_id AND cbcm.board_id = @board_id AND cbcm.class_id = @class_id
    AND c.chapter_name = 'शब्दसंपत्ती' LIMIT 1);

SET @chapter_vyakaran = (SELECT c.id FROM chapters c
    INNER JOIN chapter_board_class_mapping cbcm ON c.id = cbcm.chapter_id
    WHERE c.subject_id = @subject_id AND cbcm.board_id = @board_id AND cbcm.class_id = @class_id
    AND c.chapter_name = 'कार्यात्मक व्याकरण' LIMIT 1);

SET @chapter_sahitya = (SELECT c.id FROM chapters c
    INNER JOIN chapter_board_class_mapping cbcm ON c.id = cbcm.chapter_id
    WHERE c.subject_id = @subject_id AND cbcm.board_id = @board_id AND cbcm.class_id = @class_id
    AND c.chapter_name = '1 ली ते 4 वी मराठी (सुलभभारती) विषयाशी संबंधित सामान्यज्ञान' LIMIT 1);

-- Topic Variables for आकलन (Comprehension) Chapter
SET @topic_passage = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_aakalan AND subject_id = @subject_id
    AND topic_name = 'उतारा व त्यावरील प्रश्न' LIMIT 1);

SET @topic_poem = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_aakalan AND subject_id = @subject_id
    AND topic_name = 'कविता व त्यावरील प्रश्न' LIMIT 1);

SET @topic_dialogue = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_aakalan AND subject_id = @subject_id
    AND topic_name = 'संवाद व त्यावरील प्रश्न' LIMIT 1);

SET @topic_paragraph = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_aakalan AND subject_id = @subject_id
    AND topic_name = 'सुसंगत वाक्यांचा परिच्छेद' LIMIT 1);

-- Topic Variables for शब्दसंपत्ती (Vocabulary) Chapter
SET @topic_synonyms = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_shabdasampatti AND subject_id = @subject_id
    AND topic_name = 'समानार्थी शब्द' LIMIT 1);

SET @topic_antonyms = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_shabdasampatti AND subject_id = @subject_id
    AND topic_name = 'विरुध्द अर्थाचे शब्द' LIMIT 1);

SET @topic_one_word = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_shabdasampatti AND subject_id = @subject_id
    AND topic_name = 'शब्द समूहाबद्दल एक शब्द' LIMIT 1);

SET @topic_sound_words = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_shabdasampatti AND subject_id = @subject_id
    AND topic_name = 'ध्वनिदर्शक शब्द' LIMIT 1);

SET @topic_collective_nouns = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_shabdasampatti AND subject_id = @subject_id
    AND topic_name = 'समूहदर्शक शब्द' LIMIT 1);

SET @topic_dwelling_words = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_shabdasampatti AND subject_id = @subject_id
    AND topic_name = 'घरदर्शक शब्द' LIMIT 1);

SET @topic_idioms = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_shabdasampatti AND subject_id = @subject_id
    AND topic_name = 'वाक्प्रचार व त्यांचे अर्थ' LIMIT 1);

SET @topic_proverbs = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_shabdasampatti AND subject_id = @subject_id
    AND topic_name = 'म्हणी व त्यांचे अर्थ' LIMIT 1);

SET @topic_multiple_meanings = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_shabdasampatti AND subject_id = @subject_id
    AND topic_name = 'एकाच शब्दाचे भिन्न अर्थ' LIMIT 1);

SET @topic_compound_words = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_shabdasampatti AND subject_id = @subject_id
    AND topic_name = 'जोडशब्द' LIMIT 1);

SET @topic_word_formation = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_shabdasampatti AND subject_id = @subject_id
    AND topic_name = 'दिलेल्या अक्षरांपासून अर्थपूर्ण शब्द तयार करणे' LIMIT 1);

-- Topic Variables for कार्यात्मक व्याकरण (Grammar) Chapter
SET @topic_word_types = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_vyakaran AND subject_id = @subject_id
    AND topic_name = 'शब्दांच्या जाती - नाम व क्रियापद' LIMIT 1);

SET @topic_gender = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_vyakaran AND subject_id = @subject_id
    AND topic_name = 'लिंग' LIMIT 1);

SET @topic_number = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_vyakaran AND subject_id = @subject_id
    AND topic_name = 'वचन' LIMIT 1);

SET @topic_punctuation = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_vyakaran AND subject_id = @subject_id
    AND topic_name = 'विरामचिन्हे (पूर्णविराम, स्वल्पविराम, प्रश्नचिन्ह)' LIMIT 1);

SET @topic_spelling = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_vyakaran AND subject_id = @subject_id
    AND topic_name = 'शुध्द व अशुध्द शब्द' LIMIT 1);

-- Topic Variables for साहित्य (Literature) Chapter
SET @topic_authors = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_sahitya AND subject_id = @subject_id
    AND topic_name = 'साहित्यिकांचे साहित्य व त्यांची टोपण नावे' LIMIT 1);

SET @topic_literature_gk = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_sahitya AND subject_id = @subject_id
    AND topic_name = 'साहित्य विषयक सामान्यज्ञान' LIMIT 1);

-- =============================================
-- Insert Questions
-- =============================================

INSERT INTO questions (board_id, subject_id, class_id, medium, chapter_id, topic_id, question_type,
                       question_text, options, correct_answer, answer_explanation,
                       skill_level, difficulty_level, created_by)
VALUES

-- =============================================
-- Chapter 1: आकलन (Comprehension) (Chapter ID: 983)
-- =============================================

-- Topic: उतारा व त्यावरील प्रश्न (Passage and questions) (Topic ID: 1687) - 5 questions
(@board_id, @subject_id, @class_id, @medium, @chapter_aakalan, @topic_passage, 'MCQ',
'वाचा: "राम शाळेत जातो. तो खूप अभ्यास करतो." राम कुठे जातो?',
'{"option1":"शाळेत","option2":"घरी","option3":"बागेत","option4":"बाजारात"}',
'{"correctOption":1}',
'उताऱ्यात "राम शाळेत जातो" असे स्पष्टपणे सांगितले आहे.',
'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_aakalan, @topic_passage, 'MCQ',
'वाचा: "सीता बागेत फुले पाहते. ती खूश होते." सीता का खूश होते?',
'{"option1":"फुले पाहून","option2":"खेळ खेळून","option3":"गाणी ऐकून","option4":"मित्रांना भेटून"}',
'{"correctOption":1}',
'सीता बागेत फुले पाहून खूश होते.',
'UNDERSTANDING', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_aakalan, @topic_passage, 'MCQ',
'वाचा: "आई स्वयंपाक करते. ती चहा बनवते." आई काय करते?',
'{"option1":"स्वयंपाक करते","option2":"वाचन करते","option3":"गाणी गाते","option4":"नाचते"}',
'{"correctOption":1}',
'उताऱ्यातील पहिल्या वाक्यात "आई स्वयंपाक करते" असे लिहिले आहे.',
'APPLICATION', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_aakalan, @topic_passage, 'MCQ',
'वाचा: "मोहन सकाळी उठतो. तो व्यायाम करतो आणि शाळेला जातो." मोहन सकाळी काय करतो?',
'{"option1":"व्यायाम करतो","option2":"झोपतो","option3":"खेळतो","option4":"टीव्ही पाहतो"}',
'{"correctOption":1}',
'उताऱ्यानुसार मोहन सकाळी उठून व्यायाम करतो.',
'SKILL', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_aakalan, @topic_passage, 'MCQ',
'वाचा: "गाय दूध देते. शेतकरी गायीची काळजी घेतो." या उताऱ्यावरून काय समजते?',
'{"option1":"गाय उपयोगी प्राणी आहे","option2":"गाय धावते","option3":"गाय घास खाते","option4":"गाय रानात राहते"}',
'{"correctOption":1}',
'गाय दूध देते आणि शेतकरी तिची काळजी घेतो म्हणून गाय उपयोगी प्राणी आहे.',
'UNDERSTANDING', 'HARD', @created_by),

-- Topic: कविता व त्यावरील प्रश्न (Poem and questions) (Topic ID: 1688) - 5 questions
(@board_id, @subject_id, @class_id, @medium, @chapter_aakalan, @topic_poem, 'MCQ',
'कवितेत "चंद्र निजतो रे" ही ओळ आहे. "चंद्र" या शब्दाचा अर्थ काय?',
'{"option1":"चंद्र/चांदणी","option2":"सूर्य","option3":"तारा","option4":"पाणी"}',
'{"correctOption":1}',
'"चंद्र" म्हणजे चंद्र किंवा चांदणी.',
'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_aakalan, @topic_poem, 'MCQ',
'"फुलं फुलली बागेत, रंग रंगीत" या ओळीत काय वर्णन केले आहे?',
'{"option1":"बागेतील रंगीत फुले","option2":"रंग","option3":"बाग","option4":"झाड"}',
'{"correctOption":1}',
'या ओळीत बागेत फुललेली रंगरंगोटी फुले यांचे वर्णन आहे.',
'UNDERSTANDING', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_aakalan, @topic_poem, 'MCQ',
'"पाऊस पडतो सळसळ, झाडं होती हिरवीगार" या ओळीतून काय कळते?',
'{"option1":"पाऊस पडल्यामुळे झाडे हिरवी होतात","option2":"पाऊस थांबला","option3":"झाडे वाळली","option4":"उन्हाळा आला"}',
'{"correctOption":1}',
'पाऊस पडल्यावर झाडे हिरवीगार होतात हे या ओळीतून कळते.',
'APPLICATION', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_aakalan, @topic_poem, 'MCQ',
'कवितेत "पक्षी गातात किलबिल" असे का म्हटले असेल?',
'{"option1":"पक्षी आनंदाने किलबिल करत असतात","option2":"पक्षी उडतात","option3":"पक्षी झोपतात","option4":"पक्षी खातात"}',
'{"correctOption":1}',
'पक्षी आनंदी असताना किलबिल करत गातात हे दर्शवण्यासाठी असे म्हटले आहे.',
'SKILL', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_aakalan, @topic_poem, 'MCQ',
'"आई माझी कितीही प्रेमळ, स्नेहाने भरलेली" या ओळीचा भाव काय आहे?',
'{"option1":"आईचे प्रेम अपार आहे","option2":"आई रागावते","option3":"आई काम करते","option4":"आई शाळेत जाते"}',
'{"correctOption":1}',
'या ओळीत आईच्या अपार प्रेमाचा भाव व्यक्त केला आहे.',
'UNDERSTANDING', 'HARD', @created_by),

-- Topic: संवाद व त्यावरील प्रश्न (Dialogue and questions) (Topic ID: 1689) - 5 questions
(@board_id, @subject_id, @class_id, @medium, @chapter_aakalan, @topic_dialogue, 'MCQ',
'राम: "तू कुठे जातोस?" श्याम: "मी शाळेत जातो." श्याम कुठे जातो?',
'{"option1":"शाळेत","option2":"घरी","option3":"बागेत","option4":"दुकानात"}',
'{"correctOption":1}',
'संवादात श्याम म्हणतो "मी शाळेत जातो".',
'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_aakalan, @topic_dialogue, 'MCQ',
'सीता: "तुला काय आवडते?" गीता: "मला वाचन आवडते." गीताला काय आवडते?',
'{"option1":"वाचन","option2":"खेळ","option3":"गाणे","option4":"नृत्य"}',
'{"correctOption":1}',
'गीता म्हणते "मला वाचन आवडते".',
'UNDERSTANDING', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_aakalan, @topic_dialogue, 'MCQ',
'मास्तर: "तुम्ही अभ्यास केला का?" विद्यार्थी: "होय, मी अभ्यास केला." विद्यार्थ्याने काय केले?',
'{"option1":"अभ्यास केला","option2":"खेळला","option3":"झोपला","option4":"धावला"}',
'{"correctOption":1}',
'विद्यार्थी म्हणतो की त्याने अभ्यास केला.',
'APPLICATION', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_aakalan, @topic_dialogue, 'MCQ',
'आई: "तू जेवलास का?" मुलगा: "नाही, मला भूक नाही." मुलगा का जेवला नाही?',
'{"option1":"त्याला भूक नव्हती","option2":"त्याला जेवण आवडत नाही","option3":"त्याला रागावले होते","option4":"तो आजारी होता"}',
'{"correctOption":1}',
'मुलगा म्हणतो "मला भूक नाही" म्हणून तो जेवला नाही.',
'SKILL', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_aakalan, @topic_dialogue, 'MCQ',
'डॉक्टर: "तुला काय त्रास आहे?" रुग्ण: "मला डोकेदुखी आहे." या संवादावरून काय समजते?',
'{"option1":"रुग्णाला डोकेदुखी आहे","option2":"रुग्ण बरा आहे","option3":"डॉक्टर आजारी आहे","option4":"दोघांनाही बरे आहे"}',
'{"correctOption":1}',
'रुग्णाने सांगितले की त्याला डोकेदुखी आहे.',
'UNDERSTANDING', 'HARD', @created_by),

-- Topic: सुसंगत वाक्यांचा परिच्छेद (Coherent paragraph) (Topic ID: 1690) - 5 questions
(@board_id, @subject_id, @class_id, @medium, @chapter_aakalan, @topic_paragraph, 'MCQ',
'कोणते वाक्य योग्य क्रमाने आहे? 1. मी शाळेत गेलो. 2. मी उठलो. 3. मी नाश्ता केला.',
'{"option1":"2, 3, 1","option2":"1, 2, 3","option3":"3, 2, 1","option4":"2, 1, 3"}',
'{"correctOption":1}',
'योग्य क्रम: उठलो, नाश्ता केला, शाळेत गेलो (2, 3, 1).',
'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_aakalan, @topic_paragraph, 'MCQ',
'खालील वाक्यांचा योग्य क्रम लावा: A. त्याने गोल केला. B. सामना सुरू झाला. C. संघ जिंकला.',
'{"option1":"B, A, C","option2":"A, B, C","option3":"C, B, A","option4":"B, C, A"}',
'{"correctOption":1}',
'योग्य क्रम: सामना सुरू झाला, गोल केला, संघ जिंकला (B, A, C).',
'UNDERSTANDING', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_aakalan, @topic_paragraph, 'MCQ',
'कोणत्या वाक्याने परिच्छेद सुरू करावा? A. मग आम्ही खेळलो. B. आम्ही बागेत गेलो. C. खूप मजा आली.',
'{"option1":"B","option2":"A","option3":"C","option4":"कोणतेही"}',
'{"correctOption":1}',
'परिच्छेद "आम्ही बागेत गेलो" या वाक्याने सुरू व्हावा.',
'APPLICATION', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_aakalan, @topic_paragraph, 'MCQ',
'कोणते वाक्य या परिच्छेदात बसत नाही? 1. मी पुस्तक वाचले. 2. ते मनोरंजक होते. 3. मी फुटबॉल खेळलो. 4. मला आनंद झाला.',
'{"option1":"3","option2":"1","option3":"2","option4":"4"}',
'{"correctOption":1}',
'वाक्य 3 ("मी फुटबॉल खेळलो") पुस्तक वाचण्याच्या विषयात बसत नाही.',
'SKILL', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_aakalan, @topic_paragraph, 'MCQ',
'योग्य शेवटचे वाक्य निवडा: "मी बाजारात गेलो. मी भाज्या विकत घेतल्या. _____"',
'{"option1":"मग मी घरी आलो","option2":"मी शाळेत गेलो","option3":"मी खेळलो","option4":"मी झोपलो"}',
'{"correctOption":1}',
'बाजारातून परत येणे हे नैसर्गिक शेवटचे वाक्य आहे.',
'UNDERSTANDING', 'HARD', @created_by),

-- =============================================
-- Chapter 2: शब्दसंपत्ती (Vocabulary) (Chapter ID: 984)
-- =============================================

-- Topic: समानार्थी शब्द (Synonyms) (Topic ID: 1691) - 5 questions
(@board_id, @subject_id, @class_id, @medium, @chapter_shabdasampatti, @topic_synonyms, 'MCQ',
'"सुंदर" या शब्दाचा समानार्थी शब्द कोणता?',
'{"option1":"सुरेख","option2":"कुरूप","option3":"मोठा","option4":"लहान"}',
'{"correctOption":1}',
'"सुंदर" आणि "सुरेख" हे समानार्थी शब्द आहेत.',
'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_shabdasampatti, @topic_synonyms, 'MCQ',
'"पाणी" चा समानार्थी शब्द कोणता?',
'{"option1":"जल","option2":"दूध","option3":"तेल","option4":"रस"}',
'{"correctOption":1}',
'"पाणी" आणि "जल" हे समानार्थी शब्द आहेत.',
'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_shabdasampatti, @topic_synonyms, 'MCQ',
'"मित्र" चा समानार्थी शब्द ओळखा:',
'{"option1":"सखा","option2":"शत्रू","option3":"गुरू","option4":"भाऊ"}',
'{"correctOption":1}',
'"मित्र" आणि "सखा" हे समानार्थी शब्द आहेत.',
'UNDERSTANDING', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_shabdasampatti, @topic_synonyms, 'MCQ',
'"झाड" चा समानार्थी शब्द कोणता?',
'{"option1":"वृक्ष","option2":"फूल","option3":"पान","option4":"फळ"}',
'{"correctOption":1}',
'"झाड" आणि "वृक्ष" हे समानार्थी शब्द आहेत.',
'SKILL', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_shabdasampatti, @topic_synonyms, 'MCQ',
'"आकाश" चा समानार्थी शब्द कोणता?',
'{"option1":"नभ","option2":"पृथ्वी","option3":"समुद्र","option4":"पर्वत"}',
'{"correctOption":1}',
'"आकाश" आणि "नभ" हे समानार्थी शब्द आहेत.',
'UNDERSTANDING', 'HARD', @created_by),

-- Topic: विरुध्द अर्थाचे शब्द (Antonyms) (Topic ID: 1692) - 5 questions
(@board_id, @subject_id, @class_id, @medium, @chapter_shabdasampatti, @topic_antonyms, 'MCQ',
'"मोठा" या शब्दाचा विरुध्दार्थी शब्द कोणता?',
'{"option1":"लहान","option2":"उंच","option3":"जाड","option4":"पातळ"}',
'{"correctOption":1}',
'"मोठा" चा विरुध्दार्थी शब्द "लहान" आहे.',
'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_shabdasampatti, @topic_antonyms, 'MCQ',
'"दिवस" चा विरुध्दार्थी शब्द कोणता?',
'{"option1":"रात्र","option2":"सकाळ","option3":"संध्याकाळ","option4":"दुपार"}',
'{"correctOption":1}',
'"दिवस" चा विरुध्दार्थी शब्द "रात्र" आहे.',
'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_shabdasampatti, @topic_antonyms, 'MCQ',
'"गरम" चा विरुध्दार्थी शब्द ओळखा:',
'{"option1":"थंड","option2":"कोमट","option3":"उष्ण","option4":"तप्त"}',
'{"correctOption":1}',
'"गरम" चा विरुध्दार्थी शब्द "थंड" आहे.',
'UNDERSTANDING', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_shabdasampatti, @topic_antonyms, 'MCQ',
'"सुख" चा विरुध्दार्थी शब्द कोणता?',
'{"option1":"दुःख","option2":"आनंद","option3":"हर्ष","option4":"उल्हास"}',
'{"correctOption":1}',
'"सुख" चा विरुध्दार्थी शब्द "दुःख" आहे.',
'SKILL', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_shabdasampatti, @topic_antonyms, 'MCQ',
'"प्रकाश" चा विरुध्दार्थी शब्द कोणता?',
'{"option1":"अंधार","option2":"उजेड","option3":"सूर्य","option4":"दिवा"}',
'{"correctOption":1}',
'"प्रकाश" चा विरुध्दार्थी शब्द "अंधार" आहे.',
'UNDERSTANDING', 'HARD', @created_by),

-- Topic: शब्द समूहाबद्दल एक शब्द (One word for group of words) (Topic ID: 1693) - 5 questions
(@board_id, @subject_id, @class_id, @medium, @chapter_shabdasampatti, @topic_one_word, 'MCQ',
'"पुस्तके ठेवण्याचे ठिकाण" याला काय म्हणतात?',
'{"option1":"ग्रंथालय","option2":"शाळा","option3":"घर","option4":"दुकान"}',
'{"correctOption":1}',
'पुस्तके ठेवण्याच्या ठिकाणाला "ग्रंथालय" म्हणतात.',
'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_shabdasampatti, @topic_one_word, 'MCQ',
'"आजारी लोकांवर उपचार करणारा" याला काय म्हणतात?',
'{"option1":"डॉक्टर","option2":"शिक्षक","option3":"शेतकरी","option4":"सुतार"}',
'{"correctOption":1}',
'आजारी लोकांवर उपचार करणाऱ्याला "डॉक्टर" म्हणतात.',
'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_shabdasampatti, @topic_one_word, 'MCQ',
'"ज्यामध्ये आपण दररोजच्या गोष्टी लिहितो" याला काय म्हणतात?',
'{"option1":"दैनंदिनी","option2":"वर्तमानपत्र","option3":"पुस्तक","option4":"पत्र"}',
'{"correctOption":1}',
'दररोजच्या गोष्टी लिहिण्याच्या पुस्तकाला "दैनंदिनी" म्हणतात.',
'UNDERSTANDING', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_shabdasampatti, @topic_one_word, 'MCQ',
'"विमाने उतरतात व उडतात ते ठिकाण" याला काय म्हणतात?',
'{"option1":"विमानतळ","option2":"रेल्वेस्थानक","option3":"बंदर","option4":"थांबा"}',
'{"correctOption":1}',
'विमाने उतरतात व उडतात ते ठिकाण "विमानतळ" म्हणतात.',
'SKILL', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_shabdasampatti, @topic_one_word, 'MCQ',
'"जो फुले विकतो" याला काय म्हणतात?',
'{"option1":"फुलविक्रेता","option2":"माळी","option3":"शेतकरी","option4":"दुकानदार"}',
'{"correctOption":1}',
'फुले विकणाऱ्याला "फुलविक्रेता" म्हणतात.',
'UNDERSTANDING', 'HARD', @created_by),

-- Topic: ध्वनिदर्शक शब्द (Onomatopoeia/Sound words) (Topic ID: 1694) - 5 questions
(@board_id, @subject_id, @class_id, @medium, @chapter_shabdasampatti, @topic_sound_words, 'MCQ',
'कुत्रा कसा आवाज करतो?',
'{"option1":"भुंकतो","option2":"घोंघावतो","option3":"म्याऊं करतो","option4":"किलबिलतो"}',
'{"correctOption":1}',
'कुत्रा "भुंकतो" असा आवाज करतो.',
'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_shabdasampatti, @topic_sound_words, 'MCQ',
'मांजर कसा आवाज करते?',
'{"option1":"म्याऊं करते","option2":"भुंकते","option3":"घोंघावते","option4":"किलबिलते"}',
'{"correctOption":1}',
'मांजर "म्याऊं करते" असा आवाज करते.',
'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_shabdasampatti, @topic_sound_words, 'MCQ',
'घंटा कशी वाजते?',
'{"option1":"टणटणते","option2":"भुंकते","option3":"गुणगुणते","option4":"किलबिलते"}',
'{"correctOption":1}',
'घंटा "टणटणते" असा आवाज करते.',
'UNDERSTANDING', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_shabdasampatti, @topic_sound_words, 'MCQ',
'पाऊस पडतो तेव्हा कसा आवाज होतो?',
'{"option1":"सळसळ","option2":"भुंभुं","option3":"टणटण","option4":"म्याऊं"}',
'{"correctOption":1}',
'पाऊस पडतो तेव्हा "सळसळ" असा आवाज होतो.',
'SKILL', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_shabdasampatti, @topic_sound_words, 'MCQ',
'मधमाशी कशी आवाज करते?',
'{"option1":"गुणगुणते","option2":"भुंकते","option3":"म्याऊं करते","option4":"किलबिलते"}',
'{"correctOption":1}',
'मधमाशी "गुणगुणते" असा आवाज करते.',
'UNDERSTANDING', 'HARD', @created_by),

-- Topic: समूहदर्शक शब्द (Collective nouns) (Topic ID: 1695) - 5 questions
(@board_id, @subject_id, @class_id, @medium, @chapter_shabdasampatti, @topic_collective_nouns, 'MCQ',
'फुलांच्या समूहाला काय म्हणतात?',
'{"option1":"गुच्छ","option2":"पाकीट","option3":"जत्था","option4":"कळप"}',
'{"correctOption":1}',
'फुलांच्या समूहाला "गुच्छ" म्हणतात.',
'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_shabdasampatti, @topic_collective_nouns, 'MCQ',
'गायींच्या समूहाला काय म्हणतात?',
'{"option1":"कळप","option2":"गुच्छ","option3":"जत्था","option4":"थवा"}',
'{"correctOption":1}',
'गायींच्या समूहाला "कळप" म्हणतात.',
'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_shabdasampatti, @topic_collective_nouns, 'MCQ',
'मुलांच्या समूहाला काय म्हणतात?',
'{"option1":"टोळी","option2":"कळप","option3":"गुच्छ","option4":"पाकीट"}',
'{"correctOption":1}',
'मुलांच्या समूहाला "टोळी" म्हणतात.',
'UNDERSTANDING', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_shabdasampatti, @topic_collective_nouns, 'MCQ',
'पक्ष्यांच्या समूहाला काय म्हणतात?',
'{"option1":"थवा","option2":"कळप","option3":"जत्था","option4":"गुच्छ"}',
'{"correctOption":1}',
'पक्ष्यांच्या समूहाला "थवा" म्हणतात.',
'SKILL', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_shabdasampatti, @topic_collective_nouns, 'MCQ',
'लोकांच्या समूहाला काय म्हणतात?',
'{"option1":"जत्था/समुदाय","option2":"कळप","option3":"गुच्छ","option4":"थवा"}',
'{"correctOption":1}',
'लोकांच्या समूहाला "जत्था" किंवा "समुदाय" म्हणतात.',
'UNDERSTANDING', 'HARD', @created_by),

-- Topic: घरदर्शक शब्द (Dwelling words) (Topic ID: 1696) - 5 questions
(@board_id, @subject_id, @class_id, @medium, @chapter_shabdasampatti, @topic_dwelling_words, 'MCQ',
'सिंहाचे घर कोणते?',
'{"option1":"गुहा","option2":"घरटे","option3":"पाळी","option4":"खोळ"}',
'{"correctOption":1}',
'सिंहाचे घर "गुहा" असे आहे.',
'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_shabdasampatti, @topic_dwelling_words, 'MCQ',
'पक्ष्याचे घर कोणते?',
'{"option1":"घरटे","option2":"गुहा","option3":"पाळी","option4":"खोळ"}',
'{"correctOption":1}',
'पक्ष्याचे घर "घरटे" असे आहे.',
'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_shabdasampatti, @topic_dwelling_words, 'MCQ',
'कुत्र्याचे घर कोणते?',
'{"option1":"कुत्र्याचे घर/खोळ","option2":"घरटे","option3":"गुहा","option4":"पाळी"}',
'{"correctOption":1}',
'कुत्र्याचे घर "खोळ" म्हणतात.',
'UNDERSTANDING', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_shabdasampatti, @topic_dwelling_words, 'MCQ',
'मधमाश्यांचे घर कोणते?',
'{"option1":"पोळे","option2":"घरटे","option3":"गुहा","option4":"खोळ"}',
'{"correctOption":1}',
'मधमाश्यांचे घर "पोळे" असे आहे.',
'SKILL', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_shabdasampatti, @topic_dwelling_words, 'MCQ',
'मुंग्यांचे घर कोणते?',
'{"option1":"वाळवी","option2":"घरटे","option3":"पोळे","option4":"गुहा"}',
'{"correctOption":1}',
'मुंग्यांचे घर "वाळवी" म्हणतात.',
'UNDERSTANDING', 'HARD', @created_by),

-- Topic: वाक्प्रचार व त्यांचे अर्थ (Idioms and their meanings) (Topic ID: 1697) - 5 questions
(@board_id, @subject_id, @class_id, @medium, @chapter_shabdasampatti, @topic_idioms, 'MCQ',
'"डोळे उघडणे" या वाक्प्रचाराचा अर्थ काय?',
'{"option1":"जागे होणे/जाणीव होणे","option2":"बघणे","option3":"झोपणे","option4":"दिसणे"}',
'{"correctOption":1}',
'"डोळे उघडणे" म्हणजे जागे होणे किंवा जाणीव होणे.',
'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_shabdasampatti, @topic_idioms, 'MCQ',
'"हात धुणे" या वाक्प्रचाराचा अर्थ काय?',
'{"option1":"सोडून देणे/निष्काळजी होणे","option2":"हात स्वच्छ करणे","option3":"काम करणे","option4":"खाणे"}',
'{"correctOption":1}',
'"हात धुणे" म्हणजे सोडून देणे किंवा त्याच्याशी काही संबंध न ठेवणे.',
'UNDERSTANDING', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_shabdasampatti, @topic_idioms, 'MCQ',
'"दात खाणे" या वाक्प्रचाराचा अर्थ काय?',
'{"option1":"राग आवरणे","option2":"खाणे","option3":"रडणे","option4":"हसणे"}',
'{"correctOption":1}',
'"दात खाणे" म्हणजे राग आवरणे किंवा सहन करणे.',
'APPLICATION', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_shabdasampatti, @topic_idioms, 'MCQ',
'"नाक मुरडणे" या वाक्प्रचाराचा अर्थ काय?',
'{"option1":"तिरस्कार करणे","option2":"वास घेणे","option3":"नाक पुसणे","option4":"आजारी असणे"}',
'{"correctOption":1}',
'"नाक मुरडणे" म्हणजे तिरस्कार करणे किंवा आवडत नसणे.',
'SKILL', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_shabdasampatti, @topic_idioms, 'MCQ',
'"कान टवकारणे" या वाक्प्रचाराचा अर्थ काय?',
'{"option1":"चेतावणी देणे","option2":"ऐकणे","option3":"बोलणे","option4":"गाणे"}',
'{"correctOption":1}',
'"कान टवकारणे" म्हणजे चेतावणी देणे किंवा सावध करणे.',
'UNDERSTANDING', 'HARD', @created_by),

-- Topic: म्हणी व त्यांचे अर्थ (Proverbs and meanings) (Topic ID: 1698) - 5 questions
(@board_id, @subject_id, @class_id, @medium, @chapter_shabdasampatti, @topic_proverbs, 'MCQ',
'"जसे पेरणी तसे कापणी" या म्हणीचा अर्थ काय?',
'{"option1":"जसे कर्म तसे फळ","option2":"शेती करणे","option3":"पेरणी करणे","option4":"कापणी करणे"}',
'{"correctOption":1}',
'ज्याप्रमाणे कर्म करतो त्याप्रमाणे फळ मिळते.',
'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_shabdasampatti, @topic_proverbs, 'MCQ',
'"एक म्हैस, तर एक म्हशीला काठी" या म्हणीचा अर्थ काय?',
'{"option1":"सगळ्यांना सारखे वागवणे","option2":"म्हैस ठेवणे","option3":"काठी वापरणे","option4":"शेती करणे"}',
'{"correctOption":1}',
'सर्वांना समान न्याय किंवा वागणूक देणे.',
'UNDERSTANDING', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_shabdasampatti, @topic_proverbs, 'MCQ',
'"मुंगी आली गोड आली" या म्हणीचा अर्थ काय?',
'{"option1":"संकट आले तर मदत येते","option2":"मुंगी येते","option3":"गोड खाणे","option4":"मिठाई बनवणे"}',
'{"correctOption":1}',
'अडचणीत कुणीतरी मदत करायला येते.',
'APPLICATION', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_shabdasampatti, @topic_proverbs, 'MCQ',
'"हातचा मैल तोंडाला लावावा" या म्हणीचा अर्थ काय?',
'{"option1":"स्वतःच्या कामाचे स्वतःला फळ भोगावे लागते","option2":"हात घाण करणे","option3":"तोंड धुणे","option4":"जेवणे"}',
'{"correctOption":1}',
'स्वतःच्या चुकांचे फळ स्वतःलाच भोगावे लागते.',
'SKILL', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_shabdasampatti, @topic_proverbs, 'MCQ',
'"ओवे ओवे साखर" या म्हणीचा अर्थ काय?',
'{"option1":"मुखाने म्हणल्याने गोड होत नाही","option2":"साखर घालणे","option3":"गोड बोलणे","option4":"मिठाई खाणे"}',
'{"correctOption":1}',
'केवळ तोंडाने बोलल्याने काही होत नाही, कृती करावी लागते.',
'UNDERSTANDING', 'HARD', @created_by),

-- Topic: एकाच शब्दाचे भिन्न अर्थ (Multiple meanings) (Topic ID: 1699) - 5 questions
(@board_id, @subject_id, @class_id, @medium, @chapter_shabdasampatti, @topic_multiple_meanings, 'MCQ',
'"आंबा" या शब्दाचे दोन अर्थ कोणते?',
'{"option1":"फळ आणि झाड","option2":"फळ आणि फूल","option3":"झाड आणि पान","option4":"फळ आणि रस"}',
'{"correctOption":1}',
'"आंबा" म्हणजे फळ तसेच आंब्याचे झाड.',
'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_shabdasampatti, @topic_multiple_meanings, 'MCQ',
'"पान" या शब्दाचे अर्थ कोणते असू शकतात?',
'{"option1":"झाडाचे पान आणि वही","option2":"फळ आणि फूल","option3":"झाड आणि वेल","option4":"पाणी आणि दूध"}',
'{"correctOption":1}',
'"पान" म्हणजे झाडाचे पान किंवा वहीचे पान.',
'UNDERSTANDING', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_shabdasampatti, @topic_multiple_meanings, 'MCQ',
'"फळ" या शब्दाचे दोन अर्थ काय आहेत?',
'{"option1":"खाण्याचे फळ आणि परिणाम","option2":"फळ आणि फूल","option3":"झाड आणि पान","option4":"रस आणि बी"}',
'{"correctOption":1}',
'"फळ" म्हणजे खाण्याचे फळ किंवा कामाचे परिणाम.',
'APPLICATION', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_shabdasampatti, @topic_multiple_meanings, 'MCQ',
'"काळ" या शब्दाचे अर्थ कोणते असू शकतात?',
'{"option1":"समय आणि काळा रंग","option2":"दिवस आणि रात्र","option3":"सकाळ आणि संध्याकाळ","option4":"वर्ष आणि महिना"}',
'{"correctOption":1}',
'"काळ" म्हणजे समय/वेळ किंवा काळा रंग.',
'SKILL', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_shabdasampatti, @topic_multiple_meanings, 'MCQ',
'"रस" या शब्दाचे अर्थ कोणते असू शकतात?',
'{"option1":"फळाचा रस आणि आवड","option2":"पाणी आणि दूध","option3":"फळ आणि फूल","option4":"रंग आणि गंध"}',
'{"correctOption":1}',
'"रस" म्हणजे फळाचा रस किंवा आवड/स्वारस्य.',
'UNDERSTANDING', 'HARD', @created_by),

-- Topic: जोडशब्द (Compound words) (Topic ID: 1700) - 5 questions
(@board_id, @subject_id, @class_id, @medium, @chapter_shabdasampatti, @topic_compound_words, 'MCQ',
'खालीलपैकी कोणता जोडशब्द आहे?',
'{"option1":"पाणपुरवठा","option2":"पाणी","option3":"पुरवठा","option4":"नळ"}',
'{"correctOption":1}',
'"पाणी" + "पुरवठा" = "पाणपुरवठा" हा जोडशब्द आहे.',
'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_shabdasampatti, @topic_compound_words, 'MCQ',
'"राजा" आणि "महाराज" ह्यांपासून जोडशब्द कोणता?',
'{"option1":"राजमहाराज","option2":"राज","option3":"महाराज","option4":"राजा"}',
'{"correctOption":1}',
'"राजा" + "महाराज" = "राजमहाराज" हा जोडशब्द आहे.',
'UNDERSTANDING', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_shabdasampatti, @topic_compound_words, 'MCQ',
'"घरबार" हा जोडशब्द कोणत्या शब्दांपासून बनला?',
'{"option1":"घर + बार","option2":"घर + बाग","option3":"घर + द्वार","option4":"घर + खोली"}',
'{"correctOption":1}',
'"घर" + "बार" = "घरबार" (घर आणि संसार).',
'APPLICATION', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_shabdasampatti, @topic_compound_words, 'MCQ',
'"देणघेण" हा जोडशब्द कोणत्या शब्दांपासून बनला?',
'{"option1":"देणे + घेणे","option2":"देवा + घेवा","option3":"दे + घे","option4":"दान + ग्रहण"}',
'{"correctOption":1}',
'"देणे" + "घेणे" = "देणघेण" (व्यवहार).',
'SKILL', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_shabdasampatti, @topic_compound_words, 'MCQ',
'"चहापाणी" या जोडशब्दाचा अर्थ काय?',
'{"option1":"चहा आणि पाणी/जेवणाचा खर्च","option2":"चहा","option3":"पाणी","option4":"पेय"}',
'{"correctOption":1}',
'"चहा" + "पाणी" = "चहापाणी" (जेवणाचा खर्च/पैसे).',
'UNDERSTANDING', 'HARD', @created_by),

-- Topic: दिलेल्या अक्षरांपासून शब्द तयार करणे (Word formation) (Topic ID: 1701) - 5 questions
(@board_id, @subject_id, @class_id, @medium, @chapter_shabdasampatti, @topic_word_formation, 'MCQ',
'"प, ट, ल" या अक्षरांपासून कोणता शब्द बनतो?',
'{"option1":"पटल","option2":"पट","option3":"टप","option4":"लप"}',
'{"correctOption":1}',
'"प" + "ट" + "ल" = "पटल"',
'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_shabdasampatti, @topic_word_formation, 'MCQ',
'"क, म, ल" या अक्षरांपासून कोणता शब्द बनतो?',
'{"option1":"कमळ","option2":"कमल","option3":"मकल","option4":"लमक"}',
'{"correctOption":1}',
'"क" + "म" + "ळ" = "कमळ" (फूल).',
'UNDERSTANDING', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_shabdasampatti, @topic_word_formation, 'MCQ',
'"म, न, स" या अक्षरांपासून कोणता शब्द बनतो?',
'{"option1":"मनस","option2":"नमस","option3":"समन","option4":"सनम"}',
'{"correctOption":1}',
'"म" + "न" + "स" = "मनस" (मन).',
'APPLICATION', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_shabdasampatti, @topic_word_formation, 'MCQ',
'"ग, प, त" या अक्षरांपासून कोणता शब्द बनतो?',
'{"option1":"पगत","option2":"गपत","option3":"तपग","option4":"तगप"}',
'{"correctOption":1}',
'"प" + "ग" + "त" = "पगत" (बूट/चपला).',
'SKILL', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_shabdasampatti, @topic_word_formation, 'MCQ',
'"श, ळ, ब" या अक्षरांपासून कोणता शब्द बनतो?',
'{"option1":"शाळा","option2":"बळ","option3":"ळब","option4":"शब"}',
'{"correctOption":1}',
'"श" + "ळ" + "ा" = "शाळा" (विद्यालय).',
'UNDERSTANDING', 'HARD', @created_by),

-- =============================================
-- Chapter 3: कार्यात्मक व्याकरण (Grammar) (Chapter ID: 985)
-- =============================================

-- Topic: शब्दांच्या जाती (Word types - Nouns and Verbs) (Topic ID: 1702) - 5 questions
(@board_id, @subject_id, @class_id, @medium, @chapter_vyakaran, @topic_word_types, 'MCQ',
'खालीलपैकी नाम कोणते?',
'{"option1":"राम","option2":"धावणे","option3":"खेळणे","option4":"वाचणे"}',
'{"correctOption":1}',
'"राम" हे नाम (Noun) आहे. इतर क्रियापदे (Verbs) आहेत.',
'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_vyakaran, @topic_word_types, 'MCQ',
'खालीलपैकी क्रियापद कोणते?',
'{"option1":"खेळणे","option2":"मुलगा","option3":"पुस्तक","option4":"शाळा"}',
'{"correctOption":1}',
'"खेळणे" हे क्रियापद (Verb) आहे. इतर नामे आहेत.',
'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_vyakaran, @topic_word_types, 'MCQ',
'"मुलगी उडी मारते" या वाक्यातील क्रियापद ओळखा:',
'{"option1":"मारते","option2":"मुलगी","option3":"उडी","option4":"या"}',
'{"correctOption":1}',
'"मारते" हे क्रियापद आहे जे कार्य दर्शवते.',
'UNDERSTANDING', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_vyakaran, @topic_word_types, 'MCQ',
'"राम पुस्तक वाचतो" या वाक्यातील नाम ओळखा:',
'{"option1":"राम, पुस्तक","option2":"वाचतो","option3":"पुस्तक, वाचतो","option4":"राम, वाचतो"}',
'{"correctOption":1}',
'"राम" आणि "पुस्तक" ही दोन नामे आहेत.',
'APPLICATION', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_vyakaran, @topic_word_types, 'MCQ',
'खालील वाक्यात किती नामे आहेत? "सीता शाळेत जाते आणि अभ्यास करते."',
'{"option1":"दोन (सीता, शाळा)","option2":"एक","option3":"तीन","option4":"चार"}',
'{"correctOption":1}',
'"सीता" आणि "शाळा" ही दोन नामे आहेत.',
'SKILL', 'HARD', @created_by),

-- Topic: लिंग (Gender) (Topic ID: 1703) - 5 questions
(@board_id, @subject_id, @class_id, @medium, @chapter_vyakaran, @topic_gender, 'MCQ',
'"मुलगा" या शब्दाचे स्त्रीलिंग कोणते?',
'{"option1":"मुलगी","option2":"मुला","option3":"मुलं","option4":"बाळ"}',
'{"correctOption":1}',
'"मुलगा" (पुल्लिंग) चे स्त्रीलिंग "मुलगी" आहे.',
'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_vyakaran, @topic_gender, 'MCQ',
'"वडील" या शब्दाचे स्त्रीलिंग कोणते?',
'{"option1":"आई","option2":"आजी","option3":"बहीण","option4":"मावशी"}',
'{"correctOption":1}',
'"वडील" (पुल्लिंग) चे स्त्रीलिंग "आई" आहे.',
'UNDERSTANDING', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_vyakaran, @topic_gender, 'MCQ',
'"गाय" या शब्दाचे पुल्लिंग कोणते?',
'{"option1":"बैल","option2":"म्हैस","option3":"घोडा","option4":"शेळी"}',
'{"correctOption":1}',
'"गाय" (स्त्रीलिंग) चे पुल्लिंग "बैल" आहे.',
'APPLICATION', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_vyakaran, @topic_gender, 'MCQ',
'"राजा" या शब्दाचे स्त्रीलिंग कोणते?',
'{"option1":"राणी","option2":"राजकुमारी","option3":"राजमाता","option4":"सती"}',
'{"correctOption":1}',
'"राजा" चे स्त्रीलिंग "राणी" आहे.',
'SKILL', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_vyakaran, @topic_gender, 'MCQ',
'"सिंह" या शब्दाचे स्त्रीलिंग कोणते?',
'{"option1":"सिंहीण","option2":"सिंहिणी","option3":"वाघीण","option4":"मादी सिंह"}',
'{"correctOption":2}',
'"सिंह" चे स्त्रीलिंग "सिंहिणी" आहे.',
'UNDERSTANDING', 'HARD', @created_by),

-- Topic: वचन (Number - Singular/Plural) (Topic ID: 1704) - 5 questions
(@board_id, @subject_id, @class_id, @medium, @chapter_vyakaran, @topic_number, 'MCQ',
'"मुलगा" या शब्दाचे अनेकवचन कोणते?',
'{"option1":"मुले","option2":"मुलगे","option3":"मुलं","option4":"मुला"}',
'{"correctOption":1}',
'"मुलगा" (एकवचन) चे अनेकवचन "मुले" आहे.',
'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_vyakaran, @topic_number, 'MCQ',
'"पुस्तक" या शब्दाचे अनेकवचन कोणते?',
'{"option1":"पुस्तके","option2":"पुस्तकं","option3":"पुस्तका","option4":"पुस्तकी"}',
'{"correctOption":1}',
'"पुस्तक" चे अनेकवचन "पुस्तके" आहे.',
'UNDERSTANDING', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_vyakaran, @topic_number, 'MCQ',
'"फूल" या शब्दाचे अनेकवचन कोणते?',
'{"option1":"फुले","option2":"फुलं","option3":"फूला","option4":"फुली"}',
'{"correctOption":1}',
'"फूल" चे अनेकवचन "फुले" आहे.',
'APPLICATION', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_vyakaran, @topic_number, 'MCQ',
'"झाड" या शब्दाचे अनेकवचन कोणते?',
'{"option1":"झाडे","option2":"झाडं","option3":"झाडा","option4":"झाडी"}',
'{"correctOption":1}',
'"झाड" चे अनेकवचन "झाडे" आहे.',
'SKILL', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_vyakaran, @topic_number, 'MCQ',
'"घर" या शब्दाचे अनेकवचन कोणते?',
'{"option1":"घरे","option2":"घरं","option3":"घरा","option4":"घरी"}',
'{"correctOption":1}',
'"घर" चे अनेकवचन "घरे" आहे.',
'UNDERSTANDING', 'HARD', @created_by),

-- Topic: विरामचिन्हे (Punctuation) (Topic ID: 1705) - 5 questions
(@board_id, @subject_id, @class_id, @medium, @chapter_vyakaran, @topic_punctuation, 'MCQ',
'वाक्याच्या शेवटी कोणते चिन्ह लावतात?',
'{"option1":"पूर्णविराम (.)","option2":"स्वल्पविराम (,)","option3":"प्रश्नचिन्ह (?)","option4":"उद्गारचिन्ह (!)"}',
'{"correctOption":1}',
'वाक्याच्या शेवटी पूर्णविराम (.) लावतात.',
'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_vyakaran, @topic_punctuation, 'MCQ',
'प्रश्न विचारताना कोणते चिन्ह वापरतात?',
'{"option1":"प्रश्नचिन्ह (?)","option2":"पूर्णविराम (.)","option3":"स्वल्पविराम (,)","option4":"उद्गारचिन्ह (!)"}',
'{"correctOption":1}',
'प्रश्न विचारताना प्रश्नचिन्ह (?) वापरतात.',
'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_vyakaran, @topic_punctuation, 'MCQ',
'वाक्यात थोडा विराम द्यायचा असेल तर कोणते चिन्ह वापरतात?',
'{"option1":"स्वल्पविराम (,)","option2":"पूर्णविराम (.)","option3":"प्रश्नचिन्ह (?)","option4":"उद्गारचिन्ह (!)"}',
'{"correctOption":1}',
'थोडा विराम द्यायला स्वल्पविराम (,) वापरतात.',
'UNDERSTANDING', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_vyakaran, @topic_punctuation, 'MCQ',
'कोणत्या वाक्यात योग्य विरामचिन्हे आहेत?',
'{"option1":"तू कुठे जातोस?","option2":"तू कुठे जातोस","option3":"तू कुठे जातोस,","option4":"तू कुठे जातोस!"}',
'{"correctOption":1}',
'प्रश्न असल्यामुळे प्रश्नचिन्ह (?) योग्य आहे.',
'APPLICATION', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_vyakaran, @topic_punctuation, 'MCQ',
'योग्य वाक्य ओळखा:',
'{"option1":"मी शाळेत जातो.","option2":"मी शाळेत जातो","option3":"मी शाळेत जातो?","option4":"मी शाळेत जातो,"}',
'{"correctOption":1}',
'विधानार्थी वाक्याच्या शेवटी पूर्णविराम (.) लावतात.',
'SKILL', 'HARD', @created_by),

-- Topic: शुध्द व अशुध्द शब्द (Correct and incorrect spelling) (Topic ID: 1706) - 5 questions
(@board_id, @subject_id, @class_id, @medium, @chapter_vyakaran, @topic_spelling, 'MCQ',
'कोणती शुद्ध शब्दलेखन आहे?',
'{"option1":"शाळा","option2":"साळा","option3":"शाला","option4":"शळा"}',
'{"correctOption":1}',
'"शाळा" हे शुद्ध शब्दलेखन आहे.',
'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_vyakaran, @topic_spelling, 'MCQ',
'कोणते शब्दलेखन अशुद्ध आहे?',
'{"option1":"पुस्तक","option2":"पूस्तक","option3":"वाचन","option4":"शिक्षण"}',
'{"correctOption":2}',
'"पूस्तक" हे अशुद्ध आहे. शुद्ध लेखन "पुस्तक" आहे.',
'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_vyakaran, @topic_spelling, 'MCQ',
'"विद्यार्थी" या शब्दाचे शुद्ध लेखन कोणते?',
'{"option1":"विद्यार्थी","option2":"विधार्थी","option3":"विद्यार्थि","option4":"विद्यर्थी"}',
'{"correctOption":1}',
'"विद्यार्थी" हे शुद्ध लेखन आहे.',
'UNDERSTANDING', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_vyakaran, @topic_spelling, 'MCQ',
'खालीलपैकी कोणते शब्दलेखन शुद्ध आहे?',
'{"option1":"शिक्षक","option2":"शिक्षक","option3":"सिक्षक","option4":"शिकशक"}',
'{"correctOption":1}',
'"शिक्षक" हे शुद्ध लेखन आहे.',
'APPLICATION', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_vyakaran, @topic_spelling, 'MCQ',
'खालीलपैकी अशुद्ध शब्द कोणता?',
'{"option1":"गणित","option2":"गनित","option3":"विज्ञान","option4":"भूगोल"}',
'{"correctOption":2}',
'"गनित" हे अशुद्ध आहे. शुद्ध लेखन "गणित" आहे.',
'SKILL', 'HARD', @created_by),

-- =============================================
-- Chapter 4: साहित्य (Literature) (Chapter ID: 986)
-- =============================================

-- Topic: साहित्यिकांचे साहित्य व त्यांची टोपण नावे (Authors and pen names) (Topic ID: 1707) - 5 questions
(@board_id, @subject_id, @class_id, @medium, @chapter_sahitya, @topic_authors, 'MCQ',
'पु. ल. देशपांडे यांचे पूर्ण नाव काय?',
'{"option1":"पुरुषोत्तम लक्ष्मण देशपांडे","option2":"पांडुरंग लक्ष्मण देशपांडे","option3":"प्रभाकर लक्ष्मण देशपांडे","option4":"परशुराम लक्ष्मण देशपांडे"}',
'{"correctOption":1}',
'पु. ल. देशपांडे म्हणजे पुरुषोत्तम लक्ष्मण देशपांडे.',
'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_sahitya, @topic_authors, 'MCQ',
'"मराठीचा सुवर्णकाळ" कोणत्या कालात होता?',
'{"option1":"यादव काळ","option2":"पेशवे काळ","option3":"मुघल काळ","option4":"ब्रिटिश काळ"}',
'{"correctOption":1}',
'यादव काळात मराठी साहित्याचा सुवर्णकाळ होता.',
'UNDERSTANDING', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_sahitya, @topic_authors, 'MCQ',
'संत तुकाराम यांच्या रचना कोणत्या नावाने ओळखल्या जातात?',
'{"option1":"अभंग","option2":"कविता","option3":"नाटक","option4":"कथा"}',
'{"correctOption":1}',
'संत तुकाराम यांच्या रचना "अभंग" म्हणून ओळखल्या जातात.',
'APPLICATION', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_sahitya, @topic_authors, 'MCQ',
'"ज्ञानेश्वरी" हे ग्रंथ कोणी लिहिले?',
'{"option1":"संत ज्ञानेश्वर","option2":"संत तुकाराम","option3":"संत एकनाथ","option4":"संत नामदेव"}',
'{"correctOption":1}',
'"ज्ञानेश्वरी" हे ग्रंथ संत ज्ञानेश्वर महाराजांनी लिहिले.',
'SKILL', 'HARD', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_sahitya, @topic_authors, 'MCQ',
'मराठी भाषेची लिपी कोणती?',
'{"option1":"देवनागरी","option2":"रोमन","option3":"उर्दू","option4":"गुजराती"}',
'{"correctOption":1}',
'मराठी भाषा देवनागरी लिपीत लिहिली जाते.',
'UNDERSTANDING', 'EASY', @created_by),

-- Topic: साहित्य विषयक सामान्यज्ञान (Literature general knowledge) (Topic ID: 1708) - 5 questions
(@board_id, @subject_id, @class_id, @medium, @chapter_sahitya, @topic_literature_gk, 'MCQ',
'मराठीतील पहिली छापखान्यातून छापलेली पुस्तक कोणते?',
'{"option1":"मुंबईचे वर्णन","option2":"शेतकऱ्याचा आसूड","option3":"बोधकथा","option4":"ज्ञानेश्वरी"}',
'{"correctOption":3}',
'मराठीतील पहिले छापखान्यातून छापलेले पुस्तक "बोधकथा" होते.',
'KNOWLEDGE', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_sahitya, @topic_literature_gk, 'MCQ',
'मराठी भाषा दिन कधी साजरा केला जातो?',
'{"option1":"27 फेब्रुवारी","option2":"14 नोव्हेंबर","option3":"26 जानेवारी","option4":"15 ऑगस्ट"}',
'{"correctOption":1}',
'मराठी भाषा दिन 27 फेब्रुवारी रोजी साजरा केला जातो.',
'UNDERSTANDING', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_sahitya, @topic_literature_gk, 'MCQ',
'"पंचतंत्र" या कथांचे मराठीत भाषांतर कोणी केले?',
'{"option1":"केशवसुत","option2":"तुकाराम","option3":"ज्ञानेश्वर","option4":"रामदास"}',
'{"correctOption":1}',
'"पंचतंत्र" चे मराठीत भाषांतर केशवसुत यांनी केले.',
'APPLICATION', 'HARD', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_sahitya, @topic_literature_gk, 'MCQ',
'मराठीतील साहित्य प्रकारात कोणते समाविष्ट नाही?',
'{"option1":"चित्रपट","option2":"कविता","option3":"कथा","option4":"नाटक"}',
'{"correctOption":1}',
'चित्रपट हा साहित्य प्रकार नसून कला प्रकार आहे.',
'SKILL', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_sahitya, @topic_literature_gk, 'MCQ',
'मराठी वर्णमाला मध्ये किती अक्षरे आहेत?',
'{"option1":"46 (13 स्वर + 33 व्यंजने)","option2":"26","option3":"36","option4":"52"}',
'{"correctOption":1}',
'मराठी वर्णमालेत 13 स्वर आणि 33 व्यंजने म्हणजे एकूण 46 अक्षरे आहेत.',
'UNDERSTANDING', 'HARD', @created_by);

-- Note: This SQL file contains 110 questions (5 questions per topic × 22 topics) for Class 4 Marathi – Third Language.
-- Each question covers different skill levels (KNOWLEDGE, UNDERSTANDING, SKILL, APPLICATION) and
-- difficulty levels (EASY, MEDIUM, HARD) to ensure comprehensive assessment.

COMMIT;

