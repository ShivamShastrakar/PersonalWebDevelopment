--liquibase formatted sql
--changeset narendra:class4-english-language-study-questions

-- =============================================
-- Class 4 English - Language Study Chapter Questions
-- Board: MSCE - PRE UPPER PRIMARY SCHOLARSHIP EXAMINATION
-- Class: 4 (class_id = 2)
-- Subject: English – First Language (subject_id = 36)
-- Chapter: Language Study (chapter_id = 972)
-- Medium: English
-- Total Questions: 50 (5 questions per topic across SUKA and difficulty levels)
-- =============================================

-- Set variables for board, class, subject, and medium
SET @board_id = (SELECT id FROM board WHERE board_name = 'MSCE' LIMIT 1);
SET @subject_id = (SELECT subject_id FROM subject WHERE subject_name = 'English – First Language' LIMIT 1);
SET @class_id = (SELECT id FROM class WHERE class_name = '4' LIMIT 1);
SET @medium = 'English';
SET @created_by = 101;

-- Chapter Variable
SET @chapter_language_study = (SELECT c.id FROM chapters c
    INNER JOIN chapter_board_class_mapping cbcm ON c.id = cbcm.chapter_id
    WHERE c.subject_id = @subject_id AND cbcm.board_id = @board_id AND cbcm.class_id = @class_id
    AND c.chapter_name = 'Language Study' LIMIT 1);

-- Topic Variables for Language Study Chapter
SET @topic_punctuation = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_language_study AND subject_id = @subject_id
    AND topic_name = 'Punctuation marks' LIMIT 1);

SET @topic_contracted_forms = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_language_study AND subject_id = @subject_id
    AND topic_name = 'Contracted forms' LIMIT 1);

SET @topic_expanded_forms = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_language_study AND subject_id = @subject_id
    AND topic_name = 'Expanded forms' LIMIT 1);

SET @topic_idioms_phrases = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_language_study AND subject_id = @subject_id
    AND topic_name = 'Idioms and Phrases' LIMIT 1);

SET @topic_proverbs = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_language_study AND subject_id = @subject_id
    AND topic_name = 'Proverbs' LIMIT 1);

SET @topic_road_signs = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_language_study AND subject_id = @subject_id
    AND topic_name = 'Follow instructions/ Road Signs' LIMIT 1);

SET @topic_phrases = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_language_study AND subject_id = @subject_id
    AND topic_name = 'Phrases' LIMIT 1);

SET @topic_story_elements = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_language_study AND subject_id = @subject_id
    AND topic_name = 'Elements in story' LIMIT 1);

SET @topic_tenses = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_language_study AND subject_id = @subject_id
    AND topic_name = 'Tenses :- Present, Past, Future' LIMIT 1);

SET @topic_meaningful_sentences = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_language_study AND subject_id = @subject_id
    AND topic_name = 'Make meaningful sentences' LIMIT 1);

-- =============================================
-- Insert Questions
-- =============================================

-- =============================================
-- Topic: Punctuation marks (Topic ID: 1629) - 5 questions
-- =============================================

INSERT INTO questions (board_id, subject_id, class_id, medium, chapter_id, topic_id, question_type,
                       question_text, options, correct_answer, answer_explanation,
                       skill_level, difficulty_level, created_by)
VALUES

(@board_id, @subject_id, @class_id, @medium, @chapter_language_study, @topic_punctuation, 'MCQ',
'Which punctuation mark is used at the end of a question?',
'{"option1":"Full stop (.)","option2":"Question mark (?)","option3":"Comma (,)","option4":"Exclamation mark (!)"}',
'{"correctOption":2}',
'A question mark (?) is always used at the end of a question.',
'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_language_study, @topic_punctuation, 'MCQ',
'What punctuation is missing? "What a beautiful day"',
'{"option1":".","option2":"?","option3":"!","option4":","}',
'{"correctOption":3}',
'An exclamation mark (!) shows strong feeling or excitement.',
'SKILL', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_language_study, @topic_punctuation, 'MCQ',
'Which sentence uses commas correctly?',
'{"option1":"I like apples bananas and oranges.","option2":"I like apples, bananas, and oranges.","option3":"I like, apples bananas and oranges.","option4":"I like apples bananas, and oranges."}',
'{"correctOption":2}',
'Commas separate items in a list: "apples, bananas, and oranges".',
'UNDERSTANDING', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_language_study, @topic_punctuation, 'MCQ',
'Identify the correctly punctuated sentence:',
'{"option1":"John said I am going home","option2":"John said, I am going home.","option3":"John said I am going home?","option4":"John said I am going home!"}',
'{"correctOption":2}',
'Direct speech needs proper punctuation with a comma before the statement.',
'APPLICATION', 'HARD', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_language_study, @topic_punctuation, 'MCQ',
'What is the purpose of a full stop?',
'{"option1":"To show excitement","option2":"To ask a question","option3":"To end a statement","option4":"To separate items"}',
'{"correctOption":3}',
'A full stop (.) is used to end a statement or declarative sentence.',
'KNOWLEDGE', 'EASY', @created_by),

-- =============================================
-- Topic: Contracted forms (Topic ID: 1630) - 5 questions
-- =============================================

(@board_id, @subject_id, @class_id, @medium, @chapter_language_study, @topic_contracted_forms, 'MCQ',
'What is the contracted form of "I am"?',
'{"option1":"I''m","option2":"Im","option3":"I''ve","option4":"I''ll"}',
'{"correctOption":1}',
'The contracted form of "I am" is "I''m".',
'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_language_study, @topic_contracted_forms, 'MCQ',
'What does "won''t" stand for?',
'{"option1":"Will not","option2":"Want not","option3":"Would not","option4":"Was not"}',
'{"correctOption":1}',
'"Won''t" is the contracted form of "will not".',
'UNDERSTANDING', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_language_study, @topic_contracted_forms, 'MCQ',
'Choose the correct contraction for "they have":',
'{"option1":"They''re","option2":"They''ve","option3":"They''ll","option4":"They''d"}',
'{"correctOption":2}',
'"They''ve" is the contracted form of "they have".',
'SKILL', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_language_study, @topic_contracted_forms, 'MCQ',
'Which sentence uses the correct contracted form?',
'{"option1":"She don''t like ice cream.","option2":"She doesn''t like ice cream.","option3":"She dont like ice cream.","option4":"She does''nt like ice cream."}',
'{"correctOption":2}',
'The correct contraction is "doesn''t" (does not).',
'APPLICATION', 'HARD', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_language_study, @topic_contracted_forms, 'MCQ',
'What is the correct spelling of the contraction for "could not"?',
'{"option1":"couldn''t","option2":"could''t","option3":"couldnt","option4":"coudn''t"}',
'{"correctOption":1}',
'The contracted form of "could not" is "couldn''t".',
'UNDERSTANDING', 'MEDIUM', @created_by),

-- =============================================
-- Topic: Expanded forms (Topic ID: 1631) - 5 questions
-- =============================================

(@board_id, @subject_id, @class_id, @medium, @chapter_language_study, @topic_expanded_forms, 'MCQ',
'What is the expanded form of "can''t"?',
'{"option1":"Can not","option2":"Cannot","option3":"Could not","option4":"Can''t"}',
'{"correctOption":2}',
'The expanded form of "can''t" is "cannot" (written as one word).',
'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_language_study, @topic_expanded_forms, 'MCQ',
'Expand "we''re":',
'{"option1":"We were","option2":"We are","option3":"We will","option4":"We have"}',
'{"correctOption":2}',
'"We''re" is the contraction of "we are".',
'UNDERSTANDING', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_language_study, @topic_expanded_forms, 'MCQ',
'What is the full form of "she''d"?',
'{"option1":"She did","option2":"She would or She had","option3":"She would only","option4":"She had only"}',
'{"correctOption":2}',
'"She''d" can mean "she would" or "she had" depending on context.',
'SKILL', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_language_study, @topic_expanded_forms, 'MCQ',
'Which expanded form is correct for "shouldn''t"?',
'{"option1":"Should not","option2":"Should have not","option3":"Should had not","option4":"Shall not"}',
'{"correctOption":1}',
'"Shouldn''t" expands to "should not".',
'APPLICATION', 'HARD', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_language_study, @topic_expanded_forms, 'MCQ',
'Expand "they''ll":',
'{"option1":"They will","option2":"They shall","option3":"They all","option4":"They hill"}',
'{"correctOption":1}',
'"They''ll" is the contraction of "they will".',
'KNOWLEDGE', 'MEDIUM', @created_by),

-- =============================================
-- Topic: Idioms and Phrases (Topic ID: 1632) - 5 questions
-- =============================================

(@board_id, @subject_id, @class_id, @medium, @chapter_language_study, @topic_idioms_phrases, 'MCQ',
'What does "a piece of cake" mean?',
'{"option1":"Something sweet","option2":"Something easy","option3":"Something hard","option4":"A dessert"}',
'{"correctOption":2}',
'"A piece of cake" is an idiom meaning something very easy to do.',
'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_language_study, @topic_idioms_phrases, 'MCQ',
'"Break the ice" means:',
'{"option1":"Break something cold","option2":"Start a conversation","option3":"Stop talking","option4":"Freeze water"}',
'{"correctOption":2}',
'"Break the ice" means to make people feel comfortable and start talking.',
'UNDERSTANDING', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_language_study, @topic_idioms_phrases, 'MCQ',
'What does "once in a blue moon" mean?',
'{"option1":"Every month","option2":"Very rarely","option3":"Every night","option4":"On Mondays"}',
'{"correctOption":2}',
'"Once in a blue moon" means very rarely or almost never.',
'SKILL', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_language_study, @topic_idioms_phrases, 'MCQ',
'"Hit the nail on the head" means:',
'{"option1":"To hurt yourself","option2":"To be exactly right","option3":"To use a hammer","option4":"To make a mistake"}',
'{"correctOption":2}',
'This idiom means to describe exactly what is causing a situation or problem.',
'APPLICATION', 'HARD', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_language_study, @topic_idioms_phrases, 'MCQ',
'What does "under the weather" mean?',
'{"option1":"Standing in rain","option2":"Feeling ill","option3":"Very happy","option4":"Outside"}',
'{"correctOption":2}',
'"Under the weather" is an idiom meaning feeling sick or unwell.',
'UNDERSTANDING', 'MEDIUM', @created_by),

-- =============================================
-- Topic: Proverbs (Topic ID: 1633) - 5 questions
-- =============================================

(@board_id, @subject_id, @class_id, @medium, @chapter_language_study, @topic_proverbs, 'MCQ',
'Complete: "Early to bed and early to rise makes a man ______"',
'{"option1":"healthy, wealthy and wise","option2":"sleepy and tired","option3":"lazy and slow","option4":"hungry and thirsty"}',
'{"correctOption":1}',
'This proverb teaches the benefits of good sleeping habits.',
'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_language_study, @topic_proverbs, 'MCQ',
'What does "Practice makes perfect" mean?',
'{"option1":"Playing is good","option2":"Regular practice leads to improvement","option3":"Perfection is impossible","option4":"Practice is boring"}',
'{"correctOption":2}',
'This proverb means that if you practice regularly, you will improve and become better.',
'UNDERSTANDING', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_language_study, @topic_proverbs, 'MCQ',
'"Actions speak louder than words" means:',
'{"option1":"Shouting is bad","option2":"What you do is more important than what you say","option3":"Words are useless","option4":"Actions make noise"}',
'{"correctOption":2}',
'This proverb emphasizes that actions show more about a person than their words.',
'SKILL', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_language_study, @topic_proverbs, 'MCQ',
'Complete the proverb: "Don''t judge a book by its ______"',
'{"option1":"pages","option2":"cover","option3":"author","option4":"price"}',
'{"correctOption":2}',
'This proverb teaches not to judge things or people based on appearance alone.',
'APPLICATION', 'HARD', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_language_study, @topic_proverbs, 'MCQ',
'"A stitch in time saves nine" teaches us to:',
'{"option1":"Learn sewing","option2":"Fix problems early before they get bigger","option3":"Save money","option4":"Count numbers"}',
'{"correctOption":2}',
'This proverb means dealing with problems immediately prevents them from becoming worse.',
'UNDERSTANDING', 'MEDIUM', @created_by),

-- =============================================
-- Topic: Follow instructions/ Road Signs (Topic ID: 1634) - 5 questions
-- =============================================

(@board_id, @subject_id, @class_id, @medium, @chapter_language_study, @topic_road_signs, 'MCQ',
'What does a red traffic light mean?',
'{"option1":"Go","option2":"Slow down","option3":"Stop","option4":"Turn right"}',
'{"correctOption":3}',
'A red traffic light means you must stop and wait.',
'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_language_study, @topic_road_signs, 'MCQ',
'A sign showing a crossed-out cigarette means:',
'{"option1":"Smoking area","option2":"No smoking","option3":"Cigarette shop","option4":"Fire hazard"}',
'{"correctOption":2}',
'A crossed-out symbol means something is not allowed - this means no smoking.',
'UNDERSTANDING', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_language_study, @topic_road_signs, 'MCQ',
'What does a green signal at a pedestrian crossing mean?',
'{"option1":"Stop and wait","option2":"Run quickly","option3":"You can cross the road","option4":"Cars are coming"}',
'{"correctOption":3}',
'A green signal indicates it is safe to cross the road.',
'SKILL', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_language_study, @topic_road_signs, 'MCQ',
'If you see "PUSH" written on a door, you should:',
'{"option1":"Pull the door","option2":"Knock on the door","option3":"Push the door to open it","option4":"Wait for someone"}',
'{"correctOption":3}',
'The instruction "PUSH" tells you to push the door to open it.',
'APPLICATION', 'HARD', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_language_study, @topic_road_signs, 'MCQ',
'A sign with a picture of a phone crossed out means:',
'{"option1":"Use phone here","option2":"Phone charging station","option3":"No mobile phones allowed","option4":"Public phone"}',
'{"correctOption":3}',
'A crossed-out phone symbol means mobile phones are not allowed in that area.',
'UNDERSTANDING', 'MEDIUM', @created_by),

-- =============================================
-- Topic: Phrases (Topic ID: 1635) - 5 questions
-- =============================================

(@board_id, @subject_id, @class_id, @medium, @chapter_language_study, @topic_phrases, 'MCQ',
'Which is a noun phrase?',
'{"option1":"A beautiful garden","option2":"Running fast","option3":"Very quickly","option4":"Jumped high"}',
'{"correctOption":1}',
'A noun phrase has a noun as its main word. "A beautiful garden" is a noun phrase.',
'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_language_study, @topic_phrases, 'MCQ',
'Identify the verb phrase:',
'{"option1":"The tall tree","option2":"Is singing","option3":"Very happy","option4":"Blue sky"}',
'{"correctOption":2}',
'"Is singing" is a verb phrase (helping verb + main verb).',
'UNDERSTANDING', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_language_study, @topic_phrases, 'MCQ',
'Which phrase describes HOW something is done?',
'{"option1":"In the morning","option2":"Very carefully","option3":"The red car","option4":"My best friend"}',
'{"correctOption":2}',
'"Very carefully" is an adverb phrase that describes how an action is performed.',
'SKILL', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_language_study, @topic_phrases, 'MCQ',
'Complete the prepositional phrase: "The book is ______ the table."',
'{"option1":"on","option2":"book","option3":"reading","option4":"very"}',
'{"correctOption":1}',
'"On the table" is a prepositional phrase showing where the book is.',
'APPLICATION', 'HARD', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_language_study, @topic_phrases, 'MCQ',
'Which is an adjective phrase?',
'{"option1":"Ran quickly","option2":"Full of joy","option3":"Under the bed","option4":"Will go"}',
'{"correctOption":2}',
'"Full of joy" is an adjective phrase describing a feeling.',
'UNDERSTANDING', 'MEDIUM', @created_by),

-- =============================================
-- Topic: Elements in story (Topic ID: 1636) - 5 questions
-- =============================================

(@board_id, @subject_id, @class_id, @medium, @chapter_language_study, @topic_story_elements, 'MCQ',
'The main character in a story is called the:',
'{"option1":"Narrator","option2":"Protagonist","option3":"Villain","option4":"Author"}',
'{"correctOption":2}',
'The protagonist is the main character or hero of the story.',
'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_language_study, @topic_story_elements, 'MCQ',
'What is the "setting" of a story?',
'{"option1":"The characters","option2":"The ending","option3":"Where and when the story happens","option4":"The author''s name"}',
'{"correctOption":3}',
'Setting refers to the time and place where the story occurs.',
'UNDERSTANDING', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_language_study, @topic_story_elements, 'MCQ',
'The problem or challenge in a story is called the:',
'{"option1":"Plot","option2":"Conflict","option3":"Theme","option4":"Climax"}',
'{"correctOption":2}',
'Conflict is the main problem or challenge the characters face in the story.',
'SKILL', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_language_study, @topic_story_elements, 'MCQ',
'The most exciting part of a story where the problem reaches its peak is called:',
'{"option1":"Introduction","option2":"Rising action","option3":"Climax","option4":"Resolution"}',
'{"correctOption":3}',
'The climax is the turning point or most intense moment in the story.',
'APPLICATION', 'HARD', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_language_study, @topic_story_elements, 'MCQ',
'What is the "theme" of a story?',
'{"option1":"The title","option2":"The main lesson or message","option3":"The first sentence","option4":"The author"}',
'{"correctOption":2}',
'The theme is the central message or lesson that the story teaches.',
'UNDERSTANDING', 'MEDIUM', @created_by),

-- =============================================
-- Topic: Tenses - Present, Past, Future (Topic ID: 1637) - 5 questions
-- =============================================

(@board_id, @subject_id, @class_id, @medium, @chapter_language_study, @topic_tenses, 'MCQ',
'Which sentence is in the present tense?',
'{"option1":"I eat an apple.","option2":"I ate an apple.","option3":"I will eat an apple.","option4":"I have eaten an apple."}',
'{"correctOption":1}',
'"I eat an apple" is in simple present tense.',
'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_language_study, @topic_tenses, 'MCQ',
'Choose the past tense form: "She _____ to school yesterday."',
'{"option1":"go","option2":"goes","option3":"went","option4":"will go"}',
'{"correctOption":3}',
'"Went" is the past tense of "go". The word "yesterday" indicates past tense.',
'UNDERSTANDING', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_language_study, @topic_tenses, 'MCQ',
'Which sentence shows future tense?',
'{"option1":"He plays cricket.","option2":"He played cricket.","option3":"He will play cricket tomorrow.","option4":"He is playing cricket."}',
'{"correctOption":3}',
'"Will play" indicates future tense, and "tomorrow" confirms it.',
'SKILL', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_language_study, @topic_tenses, 'MCQ',
'Convert to past tense: "They are singing a song."',
'{"option1":"They sing a song.","option2":"They sang a song.","option3":"They were singing a song.","option4":"They will sing a song."}',
'{"correctOption":3}',
'"Were singing" is the past continuous tense of "are singing".',
'APPLICATION', 'HARD', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_language_study, @topic_tenses, 'MCQ',
'Identify the tense: "I have finished my homework."',
'{"option1":"Simple present","option2":"Simple past","option3":"Present perfect","option4":"Future tense"}',
'{"correctOption":3}',
'"Have finished" is present perfect tense, showing completed action.',
'UNDERSTANDING', 'MEDIUM', @created_by),

-- =============================================
-- Topic: Make meaningful sentences (Topic ID: 1638) - 5 questions
-- =============================================

(@board_id, @subject_id, @class_id, @medium, @chapter_language_study, @topic_meaningful_sentences, 'MCQ',
'Which words make a complete sentence?',
'{"option1":"The dog","option2":"Runs fast","option3":"The dog runs fast.","option4":"Fast the dog"}',
'{"correctOption":3}',
'A complete sentence needs a subject and a predicate with proper punctuation.',
'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_language_study, @topic_meaningful_sentences, 'MCQ',
'Arrange in correct order: "plays / She / piano / the"',
'{"option1":"She plays the piano.","option2":"Plays she the piano.","option3":"The piano she plays.","option4":"Piano the she plays."}',
'{"correctOption":1}',
'The correct word order in English is: Subject + Verb + Object.',
'UNDERSTANDING', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_language_study, @topic_meaningful_sentences, 'MCQ',
'Make a meaningful sentence: "garden / beautiful / flowers / The / has"',
'{"option1":"The garden has beautiful flowers.","option2":"Beautiful flowers the garden has.","option3":"Has the garden beautiful flowers.","option4":"Flowers beautiful the garden has."}',
'{"correctOption":1}',
'The correct sentence structure is "The garden has beautiful flowers."',
'SKILL', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_language_study, @topic_meaningful_sentences, 'MCQ',
'Which is grammatically correct?',
'{"option1":"The children was playing.","option2":"The children were playing.","option3":"The children is playing.","option4":"The children am playing."}',
'{"correctOption":2}',
'"Children" is plural, so we use "were" not "was".',
'APPLICATION', 'HARD', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_language_study, @topic_meaningful_sentences, 'MCQ',
'Rearrange: "school / go / I / to / every day"',
'{"option1":"I go to school every day.","option2":"Every day I to school go.","option3":"To school I go every day.","option4":"School I go to every day."}',
'{"correctOption":1}',
'The proper sentence structure is "I go to school every day."',
'UNDERSTANDING', 'MEDIUM', @created_by);

-- =============================================
-- Additional Questions for Better Distribution Coverage
-- =============================================

INSERT INTO questions (board_id, subject_id, class_id, medium, chapter_id, topic_id, question_type,
                       question_text, options, correct_answer, answer_explanation,
                       skill_level, difficulty_level, created_by)
VALUES

-- More EASY + APPLICATION questions for Topic: Punctuation marks
(@board_id, @subject_id, @class_id, @medium, @chapter_language_study, @topic_punctuation, 'MCQ',
'Add the missing punctuation: "Where are you going"',
'{"option1":"Where are you going.","option2":"Where are you going?","option3":"Where are you going!","option4":"Where are you going,"}',
'{"correctOption":2}',
'Questions need a question mark (?) at the end.',
'APPLICATION', 'EASY', @created_by),

-- More HARD + KNOWLEDGE questions
(@board_id, @subject_id, @class_id, @medium, @chapter_language_study, @topic_punctuation, 'MCQ',
'Which punctuation mark shows ownership or possession?',
'{"option1":"Comma (,)","option2":"Apostrophe (\\u0027)","option3":"Colon (:)","option4":"Semicolon (;)"}',
'{"correctOption":2}',
'An apostrophe is used to show possession, like John''s book.',
'KNOWLEDGE', 'HARD', @created_by),

-- More EASY + APPLICATION for Contracted forms
(@board_id, @subject_id, @class_id, @medium, @chapter_language_study, @topic_contracted_forms, 'MCQ',
'Contract these words: "It is"',
'{"option1":"Its","option2":"It''s","option3":"Its''","option4":"Itz"}',
'{"correctOption":2}',
'"It''s" is the correct contraction of "it is".',
'APPLICATION', 'EASY', @created_by),

-- More HARD + KNOWLEDGE for Expanded forms
(@board_id, @subject_id, @class_id, @medium, @chapter_language_study, @topic_expanded_forms, 'MCQ',
'What is the expanded form of "I''d''ve"?',
'{"option1":"I would have","option2":"I did have","option3":"I would of","option4":"I had have"}',
'{"correctOption":1}',
'"I''d''ve" is a double contraction that expands to "I would have".',
'KNOWLEDGE', 'HARD', @created_by),

-- More EASY + APPLICATION for Idioms
(@board_id, @subject_id, @class_id, @medium, @chapter_language_study, @topic_idioms_phrases, 'MCQ',
'What does "it''s raining cats and dogs" mean?',
'{"option1":"Animals are falling","option2":"It''s raining very heavily","option3":"It''s sunny","option4":"There are many pets"}',
'{"correctOption":2}',
'This idiom means it is raining very heavily.',
'APPLICATION', 'EASY', @created_by),

-- More HARD + KNOWLEDGE for Proverbs
(@board_id, @subject_id, @class_id, @medium, @chapter_language_study, @topic_proverbs, 'MCQ',
'"All that glitters is not gold" means:',
'{"option1":"Gold does not shine","option2":"Everything shiny is valuable","option3":"Appearances can be deceiving","option4":"Gold is better than silver"}',
'{"correctOption":3}',
'This proverb teaches that not everything that looks good is truly valuable.',
'KNOWLEDGE', 'HARD', @created_by),

-- More EASY + APPLICATION for Road Signs
(@board_id, @subject_id, @class_id, @medium, @chapter_language_study, @topic_road_signs, 'MCQ',
'What should you do when you see "EXIT" sign?',
'{"option1":"Enter from there","option2":"Stop there","option3":"Use it to leave","option4":"Ignore it"}',
'{"correctOption":3}',
'An "EXIT" sign shows the way to leave a building.',
'APPLICATION', 'EASY', @created_by),

-- More HARD + KNOWLEDGE for Phrases
(@board_id, @subject_id, @class_id, @medium, @chapter_language_study, @topic_phrases, 'MCQ',
'Which type of phrase is "with great enthusiasm"?',
'{"option1":"Noun phrase","option2":"Verb phrase","option3":"Adjective phrase","option4":"Adverb phrase"}',
'{"correctOption":4}',
'"With great enthusiasm" describes how an action is done, making it an adverb phrase.',
'KNOWLEDGE', 'HARD', @created_by),

-- More EASY + APPLICATION for Story Elements
(@board_id, @subject_id, @class_id, @medium, @chapter_language_study, @topic_story_elements, 'MCQ',
'In "The Tortoise and the Hare", who is the protagonist?',
'{"option1":"The author","option2":"The tortoise","option3":"The forest","option4":"The race"}',
'{"correctOption":2}',
'The tortoise is the main character (protagonist) who wins the race.',
'APPLICATION', 'EASY', @created_by),

-- More HARD + KNOWLEDGE for Tenses
(@board_id, @subject_id, @class_id, @medium, @chapter_language_study, @topic_tenses, 'MCQ',
'Which tense is "She had been studying"?',
'{"option1":"Past perfect","option2":"Past continuous","option3":"Past perfect continuous","option4":"Simple past"}',
'{"correctOption":3}',
'"Had been studying" is past perfect continuous tense.',
'KNOWLEDGE', 'HARD', @created_by),

-- More EASY + APPLICATION for Meaningful Sentences
(@board_id, @subject_id, @class_id, @medium, @chapter_language_study, @topic_meaningful_sentences, 'MCQ',
'Which sentence is correct?',
'{"option1":"He go to market.","option2":"He goes to market.","option3":"He going to market.","option4":"He gone to market."}',
'{"correctOption":2}',
'With "he" (singular third person), we use "goes" not "go".',
'APPLICATION', 'EASY', @created_by);

COMMIT;

