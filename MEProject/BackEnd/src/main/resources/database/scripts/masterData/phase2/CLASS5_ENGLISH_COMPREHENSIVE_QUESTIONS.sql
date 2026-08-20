--liquibase formatted sql
--changeset {narendra}:{id}

-- ============================================================================
-- MSCE CLASS 5 ENGLISH – 100 QUESTIONS
-- 60 MCQ + 40 paragraph-based questions
-- ============================================================================

-- Variable Declarations
SET @board_id = (SELECT id FROM board WHERE board_name = 'MSCE' LIMIT 1);
SET @subject_id = (SELECT subject_id FROM subject WHERE subject_name LIKE 'English%First%' LIMIT 1);
SET @class_id = (SELECT id FROM class WHERE class_name = '4' LIMIT 1);
SET @medium = 'English';
SET @created_by = 101;

-- Chapter Variables
SET @chapter_grammar = (SELECT id FROM chapters WHERE subject_id = @subject_id AND board_id = @board_id ORDER BY id LIMIT 1);
SET @chapter_reading_skills = (SELECT id FROM chapters WHERE subject_id = @subject_id AND board_id = @board_id ORDER BY id LIMIT 1 OFFSET 1);
SET @chapter_vocabulary = (SELECT id FROM chapters WHERE subject_id = @subject_id AND board_id = @board_id ORDER BY id LIMIT 1 OFFSET 2);
SET @chapter_writing = (SELECT id FROM chapters WHERE subject_id = @subject_id AND board_id = @board_id ORDER BY id LIMIT 1 OFFSET 3);

-- Topic Variables
SET @topic_nouns = (SELECT topic_id FROM topics WHERE subject_id = @subject_id ORDER BY topic_id LIMIT 1);
SET @topic_adjectives = (SELECT topic_id FROM topics WHERE subject_id = @subject_id ORDER BY topic_id LIMIT 1 OFFSET 1);
SET @topic_adverbs = (SELECT topic_id FROM topics WHERE subject_id = @subject_id ORDER BY topic_id LIMIT 1 OFFSET 2);
SET @topic_verbs_conjugation = (SELECT topic_id FROM topics WHERE subject_id = @subject_id ORDER BY topic_id LIMIT 1 OFFSET 3);
SET @topic_conjunction = (SELECT topic_id FROM topics WHERE subject_id = @subject_id ORDER BY topic_id LIMIT 1 OFFSET 4);
SET @topic_singular_plural = (SELECT topic_id FROM topics WHERE subject_id = @subject_id ORDER BY topic_id LIMIT 1 OFFSET 5);
SET @topic_pronouns = (SELECT topic_id FROM topics WHERE subject_id = @subject_id ORDER BY topic_id LIMIT 1 OFFSET 6);
SET @topic_verbs_action = (SELECT topic_id FROM topics WHERE subject_id = @subject_id ORDER BY topic_id LIMIT 1 OFFSET 7);
SET @topic_prepositions = (SELECT topic_id FROM topics WHERE subject_id = @subject_id ORDER BY topic_id LIMIT 1 OFFSET 8);
SET @topic_tenses = (SELECT topic_id FROM topics WHERE subject_id = @subject_id ORDER BY topic_id LIMIT 1 OFFSET 9);
SET @topic_articles_vowels = (SELECT topic_id FROM topics WHERE subject_id = @subject_id ORDER BY topic_id LIMIT 1 OFFSET 10);
SET @topic_sentence_parts = (SELECT topic_id FROM topics WHERE subject_id = @subject_id ORDER BY topic_id LIMIT 1 OFFSET 11);
SET @topic_reading_comprehension = (SELECT topic_id FROM topics WHERE subject_id = @subject_id ORDER BY topic_id LIMIT 1 OFFSET 12);
SET @topic_passages = (SELECT topic_id FROM topics WHERE subject_id = @subject_id ORDER BY topic_id LIMIT 1 OFFSET 13);
SET @topic_word_meaning = (SELECT topic_id FROM topics WHERE subject_id = @subject_id ORDER BY topic_id LIMIT 1 OFFSET 14);
SET @topic_synonyms = (SELECT topic_id FROM topics WHERE subject_id = @subject_id ORDER BY topic_id LIMIT 1 OFFSET 15);
SET @topic_antonyms = (SELECT topic_id FROM topics WHERE subject_id = @subject_id ORDER BY topic_id LIMIT 1 OFFSET 16);
SET @topic_sentence_structure = (SELECT topic_id FROM topics WHERE subject_id = @subject_id ORDER BY topic_id LIMIT 1 OFFSET 17);
SET @topic_paragraph_writing = (SELECT topic_id FROM topics WHERE subject_id = @subject_id ORDER BY topic_id LIMIT 1 OFFSET 18);

-- Paragraph IDs
SET @p1 = 1000001;
SET @p2 = 1000002;
SET @p3 = 1000003;
SET @p4 = 1000004;
SET @p5 = 1000005;
SET @p6 = 1000006;
SET @p7 = 1000007;
SET @p8 = 1000008;

-- ============================================================================
-- 60 MCQ QUESTIONS
-- ============================================================================

INSERT INTO questions (
    board_id, subject_id, class_id, medium, chapter_id, topic_id,
    question_type, question_text, options, correct_answer, answer_explanation,
    skill_level, difficulty_level, created_by
) VALUES

-- ───────────────────────────────────────────────
-- Topic 1: Grammar - Parts of Speech (12 questions)
-- Using Grammar chapter + related parts-of-speech topics
-- ───────────────────────────────────────────────
(@board_id, @subject_id, @class_id, @medium, @chapter_grammar, @topic_nouns, 'MCQ',
 'Choose the correct form of the verb: She _____ to school every day.',
 '{"option1":"go","option2":"goes","option3":"going","option4":"gone"}',
 '{"correctOption":2}',
 'With third-person singular subjects (she, he, it) in present tense, we add -s or -es to the verb.',
 'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_grammar, @topic_adjectives, 'MCQ',
 'Identify the adjective: "The beautiful flower bloomed in spring."',
 '{"option1":"flower","option2":"beautiful","option3":"bloomed","option4":"spring"}',
 '{"correctOption":2}',
 'An adjective describes a noun. "Beautiful" describes the flower.',
 'UNDERSTANDING', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_grammar, @topic_adverbs, 'MCQ',
 'What type of word is "quickly" in: "She ran quickly."',
 '{"option1":"Noun","option2":"Verb","option3":"Adjective","option4":"Adverb"}',
 '{"correctOption":4}',
 '"Quickly" is an adverb describing how the action was performed.',
 'UNDERSTANDING', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_grammar, @topic_nouns, 'MCQ',
 'Identify the noun: "The cat drinks milk."',
 '{"option1":"The","option2":"cat","option3":"drinks","option4":"Both cat and milk"}',
 '{"correctOption":4}',
 'Both "cat" and "milk" are nouns.',
 'UNDERSTANDING', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_grammar, @topic_verbs_conjugation, 'MCQ',
 'What is the past tense of "eat"?',
 '{"option1":"eated","option2":"ate","option3":"eaten","option4":"eating"}',
 '{"correctOption":2}',
 '"Ate" is the simple past tense of "eat".',
 'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_grammar, @topic_conjunction, 'MCQ',
 'Choose the correct conjunction: I like tea ___ coffee.',
 '{"option1":"but","option2":"and","option3":"or","option4":"because"}',
 '{"correctOption":2}',
 '"And" connects two items that are both liked.',
 'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_grammar, @topic_singular_plural, 'MCQ',
 'What is the plural of "child"?',
 '{"option1":"Childs","option2":"Children","option3":"Childes","option4":"Childrens"}',
 '{"correctOption":2}',
 '"Child" has an irregular plural form: children.',
 'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_grammar, @topic_pronouns, 'MCQ',
 'Choose the correct pronoun: ___ is my friend.',
 '{"option1":"Me","option2":"I","option3":"He","option4":"Him"}',
 '{"correctOption":3}',
 '"He" is the subject pronoun. "Him" would be used as an object.',
 'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_grammar, @topic_nouns, 'MCQ',
 'Choose the correct possessive form: "This is ___ book."',
 '{"option1":"John","option2":"Johns","option3":"John''s","option4":"Johns''"}',
 '{"correctOption":3}',
 'Use apostrophe + s (''s) to show possession for singular nouns.',
 'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_grammar, @topic_singular_plural, 'MCQ',
 'What is the plural of "mouse"?',
 '{"option1":"mouses","option2":"mice","option3":"mouse","option4":"mices"}',
 '{"correctOption":2}',
 '"Mouse" has an irregular plural: "mice".',
 'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_grammar, @topic_verbs_action, 'MCQ',
 'Identify the verb in: "Birds fly in the sky."',
 '{"option1":"Birds","option2":"fly","option3":"sky","option4":"in"}',
 '{"correctOption":2}',
 '"Fly" is the action word (verb) showing what birds do.',
 'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_grammar, @topic_adjectives, 'MCQ',
 'What is the comparative form of "good"?',
 '{"option1":"gooder","option2":"goodest","option3":"better","option4":"best"}',
 '{"correctOption":3}',
 '"Better" is the comparative form, "best" is superlative.',
 'KNOWLEDGE', 'MEDIUM', @created_by),

-- ───────────────────────────────────────────────
-- Topic 2: Grammar - Tenses & Sentence Structure (12 MCQ)
-- ───────────────────────────────────────────────
(@board_id, @subject_id, @class_id, @medium, @chapter_grammar, @topic_prepositions, 'MCQ',
 'Choose the correct preposition: The book is _____ the table.',
 '{"option1":"in","option2":"on","option3":"at","option4":"by"}',
 '{"correctOption":2}',
 'We use "on" for surfaces.',
 'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_grammar, @topic_tenses, 'MCQ',
 'Which sentence is correct?',
 '{"option1":"She don''t like apples.","option2":"She doesn''t likes apples.","option3":"She doesn''t like apples.","option4":"She don''t likes apples."}',
 '{"correctOption":3}',
 'Third person singular uses "doesn''t" with base form of verb.',
 'UNDERSTANDING', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_grammar, @topic_tenses, 'MCQ',
 'Choose the correct tense: "She ___ her homework yesterday."',
 '{"option1":"does","option2":"did","option3":"doing","option4":"will do"}',
 '{"correctOption":2}',
 '"Yesterday" indicates past tense.',
 'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_grammar, @topic_articles_vowels, 'MCQ',
 'Choose the correct article: I saw ___ elephant in the zoo.',
 '{"option1":"a","option2":"an","option3":"the","option4":"no article"}',
 '{"correctOption":2}',
 '"An" is used before words starting with vowel sounds.',
 'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_grammar, @topic_tenses, 'MCQ',
 'Which sentence is in future tense?',
 '{"option1":"I eat pizza.","option2":"I ate pizza.","option3":"I am eating pizza.","option4":"I will eat pizza."}',
 '{"correctOption":4}',
 '"Will eat" indicates future tense.',
 'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_grammar, @topic_prepositions, 'MCQ',
 'Choose the correct preposition: She lives ___ Mumbai.',
 '{"option1":"at","option2":"in","option3":"on","option4":"by"}',
 '{"correctOption":2}',
 'We use "in" with cities and countries.',
 'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_grammar, @topic_sentence_parts, 'MCQ',
 'What type of sentence is: "Is this your book?"',
 '{"option1":"Declarative","option2":"Interrogative","option3":"Imperative","option4":"Exclamatory"}',
 '{"correctOption":2}',
 'Interrogative sentences ask questions.',
 'UNDERSTANDING', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_grammar, @topic_sentence_parts, 'MCQ',
 'Choose the correct sentence structure:',
 '{"option1":"Runs the boy fast.","option2":"The boy runs fast.","option3":"Fast runs the boy.","option4":"The boy fast runs."}',
 '{"correctOption":2}',
 'Correct order: Subject + Verb + Adverb.',
 'UNDERSTANDING', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_grammar, @topic_tenses, 'MCQ',
 'Select the sentence in present continuous tense:',
 '{"option1":"She reads a book.","option2":"She is reading a book.","option3":"She read a book.","option4":"She will read a book."}',
 '{"correctOption":2}',
 'Present continuous uses "is/am/are + verb-ing".',
 'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_grammar, @topic_prepositions, 'MCQ',
 'Choose the correct preposition: The cat is hiding ___ the table.',
 '{"option1":"in","option2":"on","option3":"under","option4":"at"}',
 '{"correctOption":3}',
 '"Under" indicates below or beneath something.',
 'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_grammar, @topic_sentence_parts, 'MCQ',
 'What type of sentence is: "Close the door!"',
 '{"option1":"Declarative","option2":"Interrogative","option3":"Imperative","option4":"Exclamatory"}',
 '{"correctOption":3}',
 'Imperative sentences give commands or requests.',
 'UNDERSTANDING', 'MEDIUM', @created_by),

-- ───────────────────────────────────────────────
-- Topic 3: Vocabulary - Synonyms & Antonyms (12 MCQ)
-- ───────────────────────────────────────────────
(@board_id, @subject_id, @class_id, @medium, @chapter_word_formation, @topic_antonyms_synonyms, 'MCQ',
 'What is the antonym of "brave"?',
 '{"option1":"courageous","option2":"fearless","option3":"cowardly","option4":"bold"}',
 '{"correctOption":3}',
 'Cowardly means lacking courage, opposite of brave.',
 'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_word_formation, @topic_antonyms_synonyms, 'MCQ',
 'What is a synonym for "happy"?',
 '{"option1":"Sad","option2":"Angry","option3":"Joyful","option4":"Tired"}',
 '{"correctOption":3}',
 '"Joyful" means feeling great happiness.',
 'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_word_formation, @topic_antonyms_synonyms, 'MCQ',
 'What is the antonym of "big"?',
 '{"option1":"Large","option2":"Small","option3":"Huge","option4":"Giant"}',
 '{"correctOption":2}',
 '"Small" is the opposite of "big".',
 'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_word_formation, @topic_antonyms_synonyms, 'MCQ',
 'What is a synonym for "smart"?',
 '{"option1":"Intelligent","option2":"Foolish","option3":"Weak","option4":"Slow"}',
 '{"correctOption":1}',
 '"Intelligent" means having good mental capacity, similar to smart.',
 'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_word_formation, @topic_antonyms_synonyms, 'MCQ',
 'What is the antonym of "hot"?',
 '{"option1":"Warm","option2":"Boiling","option3":"Cold","option4":"Heated"}',
 '{"correctOption":3}',
 '"Cold" is the opposite of "hot".',
 'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_word_formation, @topic_antonyms_synonyms, 'MCQ',
 'What is a synonym for "beautiful"?',
 '{"option1":"Ugly","option2":"Lovely","option3":"Bad","option4":"Dirty"}',
 '{"correctOption":2}',
 '"Lovely" means attractive or beautiful.',
 'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_word_formation, @topic_antonyms_synonyms, 'MCQ',
 'What is the antonym of "fast"?',
 '{"option1":"Quick","option2":"Rapid","option3":"Slow","option4":"Swift"}',
 '{"correctOption":3}',
 '"Slow" means moving at low speed, opposite of fast.',
 'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_word_formation, @topic_antonyms_synonyms, 'MCQ',
 'What is a synonym for "difficult"?',
 '{"option1":"Easy","option2":"Simple","option3":"Hard","option4":"Clear"}',
 '{"correctOption":3}',
 '"Hard" means requiring effort, similar to difficult.',
 'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_word_formation, @topic_antonyms_synonyms, 'MCQ',
 'What is the antonym of "clean"?',
 '{"option1":"Pure","option2":"Spotless","option3":"Dirty","option4":"Fresh"}',
 '{"correctOption":3}',
 '"Dirty" means not clean.',
 'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_word_formation, @topic_antonyms_synonyms, 'MCQ',
 'What is a synonym for "start"?',
 '{"option1":"End","option2":"Begin","option3":"Stop","option4":"Finish"}',
 '{"correctOption":2}',
 '"Begin" means to start something.',
 'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_word_formation, @topic_antonyms_synonyms, 'MCQ',
 'What is the antonym of "strong"?',
 '{"option1":"Powerful","option2":"Weak","option3":"Mighty","option4":"Tough"}',
 '{"correctOption":2}',
 '"Weak" means lacking strength, opposite of strong.',
 'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_word_formation, @topic_antonyms_synonyms, 'MCQ',
 'What is a synonym for "angry"?',
 '{"option1":"Calm","option2":"Furious","option3":"Happy","option4":"Peaceful"}',
 '{"correctOption":2}',
 '"Furious" is a strong synonym for very angry.',
 'KNOWLEDGE', 'EASY', @created_by),

-- ───────────────────────────────────────────────
-- Topic 4: Vocabulary - Spelling & Meanings (12 MCQ)
-- ───────────────────────────────────────────────
(@board_id, @subject_id, @class_id, @medium, @chapter_word_formation, @topic_spelling, 'MCQ',
 'Choose the correctly spelled word:',
 '{"option1":"Recieve","option2":"Receive","option3":"Receve","option4":"Receeve"}',
 '{"correctOption":2}',
 'The correct spelling is "Receive" (i before e, except after c).',
 'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_word_formation, @topic_spelling, 'MCQ',
 'What does "enormous" mean?',
 '{"option1":"Very small","option2":"Very large","option3":"Very fast","option4":"Very slow"}',
 '{"correctOption":2}',
 '"Enormous" means extremely large in size.',
 'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_word_formation, @topic_spelling, 'MCQ',
 'Choose the correctly spelled word:',
 '{"option1":"Seperate","option2":"Separate","option3":"Seperete","option4":"Separete"}',
 '{"correctOption":2}',
 '"Separate" is the correct spelling.',
 'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_word_formation, @topic_spelling, 'MCQ',
 'What does "ancient" mean?',
 '{"option1":"New","option2":"Modern","option3":"Very old","option4":"Beautiful"}',
 '{"correctOption":3}',
 '"Ancient" means belonging to a very distant past.',
 'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_word_formation, @topic_spelling, 'MCQ',
 'Choose the correct word: "The weather is ___ today."',
 '{"option1":"whether","option2":"weather","option3":"wether","option4":"wheather"}',
 '{"correctOption":2}',
 '"Weather" refers to atmospheric conditions. "Whether" means "if".',
 'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_word_formation, @topic_spelling, 'MCQ',
 'What does "fragile" mean?',
 '{"option1":"Strong","option2":"Heavy","option3":"Easily broken","option4":"Valuable"}',
 '{"correctOption":3}',
 '"Fragile" means easily broken or damaged.',
 'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_word_formation, @topic_spelling, 'MCQ',
 'Choose the correctly spelled word:',
 '{"option1":"Occassion","option2":"Occasion","option3":"Ocasion","option4":"Occacion"}',
 '{"correctOption":2}',
 '"Occasion" is the correct spelling.',
 'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_word_formation, @topic_spelling, 'MCQ',
 'What does "curious" mean?',
 '{"option1":"Bored","option2":"Eager to learn","option3":"Sleepy","option4":"Angry"}',
 '{"correctOption":2}',
 '"Curious" means eager to know or learn something.',
 'UNDERSTANDING', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_word_formation, @topic_spelling, 'MCQ',
 'What does "whisper" mean?',
 '{"option1":"To shout loudly","option2":"To speak very softly","option3":"To sing","option4":"To cry"}',
 '{"correctOption":2}',
 '"Whisper" means to speak very softly or quietly.',
 'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_word_formation, @topic_spelling, 'MCQ',
 'What does "furious" mean?',
 '{"option1":"Very angry","option2":"Very happy","option3":"Very sad","option4":"Very tired"}',
 '{"correctOption":1}',
 '"Furious" means extremely angry.',
 'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_word_formation, @topic_spelling, 'MCQ',
 'Choose the correctly spelled word:',
 '{"option1":"Beleive","option2":"Believe","option3":"Beleeve","option4":"Belive"}',
 '{"correctOption":2}',
 '"Believe" is the correct spelling.',
 'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_word_formation, @topic_spelling, 'MCQ',
 'What does "diligent" mean?',
 '{"option1":"Lazy","option2":"Hardworking","option3":"Careless","option4":"Sleepy"}',
 '{"correctOption":2}',
 '"Diligent" means showing careful and persistent work.',
 'UNDERSTANDING', 'MEDIUM', @created_by),

-- ───────────────────────────────────────────────
-- Topic 5: Literature & Comprehension (12 MCQ)
-- ───────────────────────────────────────────────
(@board_id, @subject_id, @class_id, @medium, @chapter_reading_skills, @topic_passages, 'MCQ',
 'What is a fable?',
 '{"option1":"A true story","option2":"A story with a moral lesson","option3":"A poem","option4":"A play"}',
 '{"correctOption":2}',
 'A fable is a short story that teaches a moral lesson, often with animals.',
 'UNDERSTANDING', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_reading_skills, @topic_passages, 'MCQ',
 'Who is the author of a book?',
 '{"option1":"The person who illustrates it","option2":"The person who writes it","option3":"The person who publishes it","option4":"The person who reads it"}',
 '{"correctOption":2}',
 'An author is the person who writes a book or story.',
 'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_reading_skills, @topic_passages, 'MCQ',
 'What is a rhyme?',
 '{"option1":"Words that mean the same","option2":"Words that sound the same at the end","option3":"Words that look the same","option4":"Long words"}',
 '{"correctOption":2}',
 'Rhyme refers to words that have the same ending sound.',
 'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_reading_skills, @topic_passages, 'MCQ',
 'What is the main character in a story called?',
 '{"option1":"Villain","option2":"Protagonist","option3":"Author","option4":"Reader"}',
 '{"correctOption":2}',
 'The protagonist is the main character in a story.',
 'KNOWLEDGE', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_reading_skills, @topic_passages, 'MCQ',
 'What does "setting" mean in a story?',
 '{"option1":"The characters","option2":"The problem","option3":"The time and place","option4":"The ending"}',
 '{"correctOption":3}',
 'Setting refers to when and where a story takes place.',
 'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_reading_skills, @topic_passages, 'MCQ',
 'What is dialogue in a story?',
 '{"option1":"The description of places","option2":"The conversation between characters","option3":"The pictures","option4":"The title"}',
 '{"correctOption":2}',
 'Dialogue is the conversation between characters in a story.',
 'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_reading_skills, @topic_passages, 'MCQ',
 'What is the "moral" of a story?',
 '{"option1":"The funny part","option2":"The lesson or message","option3":"The main character","option4":"The setting"}',
 '{"correctOption":2}',
 'The moral is the lesson or message that a story teaches.',
 'UNDERSTANDING', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_reading_skills, @topic_passages, 'MCQ',
 'What is a poem?',
 '{"option1":"A long story","option2":"Writing with rhythm and often rhyme","option3":"A science report","option4":"A letter"}',
 '{"correctOption":2}',
 'A poem is writing with rhythm and often rhyme, expressing feelings or ideas.',
 'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_reading_skills, @topic_passages, 'MCQ',
 'What does "fiction" mean?',
 '{"option1":"True stories","option2":"Made-up stories","option3":"Poetry","option4":"Biography"}',
 '{"correctOption":2}',
 'Fiction refers to literature created from imagination, not real events.',
 'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_reading_skills, @topic_passages, 'MCQ',
 'What is "non-fiction"?',
 '{"option1":"Made-up stories","option2":"True or factual information","option3":"Poetry only","option4":"Fairy tales"}',
 '{"correctOption":2}',
 'Non-fiction is writing based on facts and real events.',
 'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_reading_skills, @topic_passages, 'MCQ',
 'What is the "climax" of a story?',
 '{"option1":"The beginning","option2":"The most exciting part","option3":"The ending","option4":"The characters"}',
 '{"correctOption":2}',
 'The climax is the turning point or most exciting moment in a story.',
 'UNDERSTANDING', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_reading_skills, @topic_passages, 'MCQ',
 'What is the "conclusion" of a story?',
 '{"option1":"The beginning","option2":"The middle","option3":"The ending","option4":"The characters"}',
 '{"correctOption":3}',
 'The conclusion is the end of a story where issues are resolved.',
 'KNOWLEDGE', 'EASY', @created_by);

-- ============================================================================
-- 40 PARAGRAPH-BASED QUESTIONS
-- Using Reading Skills chapter + appropriate comprehension/story topics
-- ============================================================================

-- Paragraph 1: The Sun
INSERT INTO questions (
    board_id, subject_id, class_id, medium, chapter_id, topic_id,
    question_type, question_text, paragraph_text, paragraph_id,
    options, correct_answer, answer_explanation,
    skill_level, difficulty_level, created_by
) VALUES
      (@board_id, @subject_id, @class_id, @medium, @chapter_reading_skills, @topic_passages, 'paragraph-based-mcq',
       'What is the main idea of the passage?',
       'The sun is the center of our solar system. It provides light and heat to all the planets. Without the sun, there would be no life on Earth. Plants need sunlight to make food, and animals depend on plants for their survival. The sun is approximately 93 million miles away from Earth.',
       @p1,
       '{"option1":"The sun is very far away","option2":"The sun is essential for life on Earth","option3":"Plants need water","option4":"Animals eat plants"}',
       '{"correctOption":2}',
       'The passage discusses how the sun is essential for life on Earth.',
       'UNDERSTANDING','MEDIUM',@created_by),

      (@board_id, @subject_id, @class_id, @medium, @chapter_reading_skills, @topic_passages, 'paragraph-based-mcq',
       'According to the passage, why do plants need sunlight?',
       NULL, @p1,
       '{"option1":"To grow tall","option2":"To make food","option3":"To produce oxygen","option4":"To stay green"}',
       '{"correctOption":2}',
       'The passage states plants need sunlight to make food.',
       'KNOWLEDGE','EASY',@created_by),

      (@board_id, @subject_id, @class_id, @medium, @chapter_reading_skills, @topic_passages, 'paragraph-based-mcq',
       'What does the sun provide to the planets?',
       NULL, @p1,
       '{"option1":"Water and minerals","option2":"Light and heat","option3":"Oxygen and nitrogen","option4":"Gravity"}',
       '{"correctOption":2}',
       'The passage states the sun provides light and heat.',
       'KNOWLEDGE','EASY',@created_by),

      (@board_id, @subject_id, @class_id, @medium, @chapter_reading_skills, @topic_passages, 'paragraph-based-mcq',
       'How far is the sun from Earth?',
       NULL, @p1,
       '{"option1":"90 million miles","option2":"93 million miles","option3":"100 million miles","option4":"110 million miles"}',
       '{"correctOption":2}',
       'The passage states the sun is approximately 93 million miles away.',
       'KNOWLEDGE','EASY',@created_by),

      (@board_id, @subject_id, @class_id, @medium, @chapter_reading_skills, @topic_passages, 'paragraph-based-mcq',
       'Why is the sun important for animals?',
       NULL, @p1,
       '{"option1":"It keeps them warm","option2":"Animals depend on plants which need sunlight","option3":"It provides oxygen","option4":"It helps them sleep"}',
       '{"correctOption":2}',
       'Animals depend on plants for survival, and plants need sunlight.',
       'UNDERSTANDING','MEDIUM',@created_by),

-- Paragraph 2: The Crow and the Pitcher
      (@board_id, @subject_id, @class_id, @medium, @chapter_reading_skills, @topic_story_elements, 'paragraph-based-mcq',
       'What lesson does the story teach?',
       'Once there was a thirsty crow. It searched for water everywhere but could not find any. Finally, it saw a pot with little water at the bottom. The crow could not reach the water. It thought of a clever plan. It dropped pebbles into the pot one by one. Slowly, the water level rose, and the crow drank the water happily.',
       @p2,
       '{"option1":"Crows are intelligent birds","option2":"Where there is a will, there is a way","option3":"Water is important","option4":"Pebbles are useful"}',
       '{"correctOption":2}',
       'The story teaches us that with determination and clever thinking, we can solve problems.',
       'APPLICATION','MEDIUM',@created_by),

      (@board_id, @subject_id, @class_id, @medium, @chapter_reading_skills, @topic_story_elements, 'paragraph-based-mcq',
       'Why couldn''t the crow drink the water initially?',
       NULL, @p2,
       '{"option1":"The pot was too heavy","option2":"The water was dirty","option3":"The water level was too low","option4":"The pot was covered"}',
       '{"correctOption":3}',
       'The passage states there was little water at the bottom and the crow could not reach it.',
       'KNOWLEDGE','EASY',@created_by),

      (@board_id, @subject_id, @class_id, @medium, @chapter_reading_skills, @topic_story_elements, 'paragraph-based-mcq',
       'What clever plan did the crow use?',
       NULL, @p2,
       '{"option1":"Breaking the pot","option2":"Dropping pebbles into the pot","option3":"Finding another water source","option4":"Asking for help"}',
       '{"correctOption":2}',
       'The story states that the crow dropped pebbles into the pot one by one.',
       'UNDERSTANDING','MEDIUM',@created_by),

      (@board_id, @subject_id, @class_id, @medium, @chapter_reading_skills, @topic_story_elements, 'paragraph-based-mcq',
       'How did the water level rise?',
       NULL, @p2,
       '{"option1":"More water was added","option2":"The pot was tilted","option3":"Pebbles were dropped one by one","option4":"Rain fell into the pot"}',
       '{"correctOption":3}',
       'The passage explains that as the crow dropped pebbles, the water level slowly rose.',
       'KNOWLEDGE','EASY',@created_by),

      (@board_id, @subject_id, @class_id, @medium, @chapter_reading_skills, @topic_story_elements, 'paragraph-based-mcq',
       'How did the crow feel after drinking the water?',
       NULL, @p2,
       '{"option1":"Tired","option2":"Sad","option3":"Happy","option4":"Confused"}',
       '{"correctOption":3}',
       'The passage states that the crow drank the water happily.',
       'KNOWLEDGE','EASY',@created_by);

-- ============================================================================
-- Paragraph 3: Healthy Habits (5 questions)
-- ============================================================================
INSERT INTO questions (
    board_id, subject_id, class_id, medium, chapter_id, topic_id,
    question_type, question_text, paragraph_text, paragraph_id,
    options, correct_answer, answer_explanation,
    skill_level, difficulty_level, created_by
) VALUES
      (@board_id, @subject_id, @class_id, @medium, @chapter_reading_skills, @topic_passages, 'paragraph-based-mcq',
       'What is the main topic of this passage?',
       'Eating healthy food is very important for our body. Fresh fruits and vegetables give us vitamins and minerals. Drinking plenty of water keeps us hydrated. We should avoid eating too much junk food like chips and candy. Exercise daily and get enough sleep to stay fit and strong.',
       @p3,
       '{"option1":"Sports activities","option2":"Healthy living habits","option3":"School subjects","option4":"Cooking recipes"}',
       '{"correctOption":2}',
       'The passage discusses various healthy habits like eating well, drinking water, and exercising.',
       'UNDERSTANDING', 'MEDIUM', @created_by),

      (@board_id, @subject_id, @class_id, @medium, @chapter_reading_skills, @topic_passages, 'paragraph-based-mcq',
       'What do fruits and vegetables provide?',
       NULL, @p3,
       '{"option1":"Sugar and fat","option2":"Vitamins and minerals","option3":"Salt and pepper","option4":"Oil and butter"}',
       '{"correctOption":2}',
       'The passage states that fresh fruits and vegetables give us vitamins and minerals.',
       'KNOWLEDGE', 'EASY', @created_by),

      (@board_id, @subject_id, @class_id, @medium, @chapter_reading_skills, @topic_passages, 'paragraph-based-mcq',
       'Why should we drink plenty of water?',
       NULL, @p3,
       '{"option1":"To feel full","option2":"To stay hydrated","option3":"To lose weight","option4":"To sleep better"}',
       '{"correctOption":2}',
       'The passage mentions that drinking plenty of water keeps us hydrated.',
       'KNOWLEDGE', 'EASY', @created_by),

      (@board_id, @subject_id, @class_id, @medium, @chapter_reading_skills, @topic_passages, 'paragraph-based-mcq',
       'What should we avoid eating too much of?',
       NULL, @p3,
       '{"option1":"Fruits","option2":"Vegetables","option3":"Junk food","option4":"Water"}',
       '{"correctOption":3}',
       'The passage advises avoiding too much junk food like chips and candy.',
       'KNOWLEDGE', 'EASY', @created_by),

      (@board_id, @subject_id, @class_id, @medium, @chapter_reading_skills, @topic_passages, 'paragraph-based-mcq',
       'According to the passage, what helps us stay fit and strong?',
       NULL, @p3,
       '{"option1":"Watching TV all day","option2":"Eating candy","option3":"Exercise and sleep","option4":"Playing video games"}',
       '{"correctOption":3}',
       'The passage states we should exercise daily and get enough sleep to stay fit and strong.',
       'UNDERSTANDING', 'MEDIUM', @created_by);

-- ============================================================================
-- Paragraph 4: A Visit to the Library (5 questions)
-- ============================================================================
INSERT INTO questions (
    board_id, subject_id, class_id, medium, chapter_id, topic_id,
    question_type, question_text, paragraph_text, paragraph_id,
    options, correct_answer, answer_explanation,
    skill_level, difficulty_level, created_by
) VALUES
      (@board_id, @subject_id, @class_id, @medium, @chapter_reading_skills, @topic_passages, 'paragraph-based-mcq',
       'What is the main idea of the passage?',
       'Yesterday, my class visited the school library. The librarian welcomed us with a smile. She showed us different sections - story books, science books, and reference books. She explained that we must keep our voices low in the library. We learned how to borrow books and return them on time. Reading books helps us learn new things.',
       @p4,
       '{"option1":"Going on a picnic","option2":"Visiting the library","option3":"Playing sports","option4":"Doing homework"}',
       '{"correctOption":2}',
       'The passage describes a visit to the school library.',
       'UNDERSTANDING', 'EASY', @created_by),

      (@board_id, @subject_id, @class_id, @medium, @chapter_reading_skills, @topic_passages, 'paragraph-based-mcq',
       'Who welcomed the class?',
       NULL, @p4,
       '{"option1":"The principal","option2":"The librarian","option3":"The teacher","option4":"A friend"}',
       '{"correctOption":2}',
       'The passage states that the librarian welcomed them with a smile.',
       'KNOWLEDGE', 'EASY', @created_by),

      (@board_id, @subject_id, @class_id, @medium, @chapter_reading_skills, @topic_passages, 'paragraph-based-mcq',
       'What sections were shown in the library?',
       NULL, @p4,
       '{"option1":"Only story books","option2":"Story books, science books, and reference books","option3":"Only science books","option4":"Only magazines"}',
       '{"correctOption":2}',
       'The passage mentions different sections including story books, science books, and reference books.',
       'KNOWLEDGE', 'EASY', @created_by),

      (@board_id, @subject_id, @class_id, @medium, @chapter_reading_skills, @topic_passages, 'paragraph-based-mcq',
       'What must we do in the library?',
       NULL, @p4,
       '{"option1":"Run around","option2":"Keep voices low","option3":"Eat snacks","option4":"Play games"}',
       '{"correctOption":2}',
       'The librarian explained that we must keep our voices low in the library.',
       'KNOWLEDGE', 'EASY', @created_by),

      (@board_id, @subject_id, @class_id, @medium, @chapter_reading_skills, @topic_passages, 'paragraph-based-mcq',
       'According to the passage, why is reading books helpful?',
       NULL, @p4,
       '{"option1":"It makes us sleepy","option2":"It helps us learn new things","option3":"It wastes time","option4":"It is boring"}',
       '{"correctOption":2}',
       'The passage concludes that reading books helps us learn new things.',
       'UNDERSTANDING', 'MEDIUM', @created_by);

-- ============================================================================
-- Paragraph 5: The Seasons (5 questions)
-- ============================================================================
INSERT INTO questions (
    board_id, subject_id, class_id, medium, chapter_id, topic_id,
    question_type, question_text, paragraph_text, paragraph_id,
    options, correct_answer, answer_explanation,
    skill_level, difficulty_level, created_by
) VALUES
      (@board_id, @subject_id, @class_id, @medium, @chapter_reading_skills, @topic_passages, 'paragraph-based-mcq',
       'What is this passage about?',
       'There are four seasons in a year - summer, winter, spring, and autumn. Each season has its own beauty. Summer is hot and sunny. Winter is cold with short days. Spring brings new flowers and fresh leaves. Autumn is when leaves fall and the weather becomes cooler. Nature changes with every season.',
       @p5,
       '{"option1":"Different months","option2":"The four seasons","option3":"Weather forecasts","option4":"School holidays"}',
       '{"correctOption":2}',
       'The passage describes the four seasons of the year.',
       'UNDERSTANDING', 'EASY', @created_by),

      (@board_id, @subject_id, @class_id, @medium, @chapter_reading_skills, @topic_passages, 'paragraph-based-mcq',
       'How many seasons are there in a year?',
       NULL, @p5,
       '{"option1":"Two","option2":"Three","option3":"Four","option4":"Five"}',
       '{"correctOption":3}',
       'The passage states there are four seasons in a year.',
       'KNOWLEDGE', 'EASY', @created_by),

      (@board_id, @subject_id, @class_id, @medium, @chapter_reading_skills, @topic_passages, 'paragraph-based-mcq',
       'Which season is hot and sunny?',
       NULL, @p5,
       '{"option1":"Winter","option2":"Summer","option3":"Spring","option4":"Autumn"}',
       '{"correctOption":2}',
       'The passage states that summer is hot and sunny.',
       'KNOWLEDGE', 'EASY', @created_by),

      (@board_id, @subject_id, @class_id, @medium, @chapter_reading_skills, @topic_passages, 'paragraph-based-mcq',
       'What happens in spring?',
       NULL, @p5,
       '{"option1":"Leaves fall","option2":"It becomes very cold","option3":"New flowers and fresh leaves appear","option4":"It becomes very hot"}',
       '{"correctOption":3}',
       'The passage states that spring brings new flowers and fresh leaves.',
       'KNOWLEDGE', 'EASY', @created_by),

      (@board_id, @subject_id, @class_id, @medium, @chapter_reading_skills, @topic_passages, 'paragraph-based-mcq',
       'What changes with every season?',
       NULL, @p5,
       '{"option1":"School timings","option2":"Nature","option3":"People","option4":"Buildings"}',
       '{"correctOption":2}',
       'The passage concludes that nature changes with every season.',
       'UNDERSTANDING', 'MEDIUM', @created_by);

-- ============================================================================
-- Paragraph 6: Helping Others (5 questions)
-- ============================================================================
INSERT INTO questions (
    board_id, subject_id, class_id, medium, chapter_id, topic_id,
    question_type, question_text, paragraph_text, paragraph_id,
    options, correct_answer, answer_explanation,
    skill_level, difficulty_level, created_by
) VALUES
      (@board_id, @subject_id, @class_id, @medium, @chapter_reading_skills, @topic_passages, 'paragraph-based-mcq',
       'What is the main message of the passage?',
       'Helping others makes us feel good. When we see someone in need, we should try to help them. We can help our parents with household chores. We can help our friends with their studies. Even small acts of kindness like sharing food or holding the door open for someone makes a difference. Being helpful is a good quality.',
       @p6,
       '{"option1":"Studying is important","option2":"Helping others is good","option3":"Playing is fun","option4":"Food is necessary"}',
       '{"correctOption":2}',
       'The passage discusses the importance of helping others.',
       'UNDERSTANDING', 'MEDIUM', @created_by),

      (@board_id, @subject_id, @class_id, @medium, @chapter_reading_skills, @topic_passages, 'paragraph-based-mcq',
       'How does helping others make us feel?',
       NULL, @p6,
       '{"option1":"Sad","option2":"Angry","option3":"Good","option4":"Tired"}',
       '{"correctOption":3}',
       'The passage states that helping others makes us feel good.',
       'KNOWLEDGE', 'EASY', @created_by),

      (@board_id, @subject_id, @class_id, @medium, @chapter_reading_skills, @topic_passages, 'paragraph-based-mcq',
       'What can we help our parents with?',
       NULL, @p6,
       '{"option1":"Homework","option2":"Household chores","option3":"Office work","option4":"Shopping only"}',
       '{"correctOption":2}',
       'The passage mentions we can help our parents with household chores.',
       'KNOWLEDGE', 'EASY', @created_by),

      (@board_id, @subject_id, @class_id, @medium, @chapter_reading_skills, @topic_passages, 'paragraph-based-mcq',
       'According to the passage, what are examples of small acts of kindness?',
       NULL, @p6,
       '{"option1":"Shouting at people","option2":"Sharing food or holding doors","option3":"Ignoring others","option4":"Being rude"}',
       '{"correctOption":2}',
       'The passage gives examples like sharing food or holding the door open for someone.',
       'KNOWLEDGE', 'EASY', @created_by),

      (@board_id, @subject_id, @class_id, @medium, @chapter_reading_skills, @topic_passages, 'paragraph-based-mcq',
       'What quality does being helpful represent?',
       NULL, @p6,
       '{"option1":"A bad quality","option2":"A good quality","option3":"A useless quality","option4":"A harmful quality"}',
       '{"correctOption":2}',
       'The passage concludes that being helpful is a good quality.',
       'UNDERSTANDING', 'EASY', @created_by);

-- ============================================================================
-- Paragraph 7: The Ant and the Grasshopper (5 questions)
-- ============================================================================
INSERT INTO questions (
    board_id, subject_id, class_id, medium, chapter_id, topic_id,
    question_type, question_text, paragraph_text, paragraph_id,
    options, correct_answer, answer_explanation,
    skill_level, difficulty_level, created_by
) VALUES
      (@board_id, @subject_id, @class_id, @medium, @chapter_reading_skills, @topic_story_elements, 'paragraph-based-mcq',
       'What is the moral of this story?',
       'In summer, an ant worked hard collecting food for winter. A grasshopper saw the ant and laughed. The grasshopper sang and danced all summer instead of working. When winter came, the ant had plenty of food. The grasshopper had nothing to eat and was hungry. He learned that hard work and planning are important.',
       @p7,
       '{"option1":"Singing is better than working","option2":"Hard work and planning are important","option3":"Summer is the best season","option4":"Ants are better than grasshoppers"}',
       '{"correctOption":2}',
       'The story teaches that hard work and planning for the future are important.',
       'APPLICATION', 'MEDIUM', @created_by),

      (@board_id, @subject_id, @class_id, @medium, @chapter_reading_skills, @topic_story_elements, 'paragraph-based-mcq',
       'What did the ant do in summer?',
       NULL, @p7,
       '{"option1":"Sang and danced","option2":"Worked hard collecting food","option3":"Slept all day","option4":"Played with friends"}',
       '{"correctOption":2}',
       'The passage states that the ant worked hard collecting food for winter.',
       'KNOWLEDGE', 'EASY', @created_by),

      (@board_id, @subject_id, @class_id, @medium, @chapter_reading_skills, @topic_story_elements, 'paragraph-based-mcq',
       'What did the grasshopper do instead of working?',
       NULL, @p7,
       '{"option1":"Collected food","option2":"Helped the ant","option3":"Sang and danced","option4":"Built a house"}',
       '{"correctOption":3}',
       'The passage says the grasshopper sang and danced all summer instead of working.',
       'KNOWLEDGE', 'EASY', @created_by),

      (@board_id, @subject_id, @class_id, @medium, @chapter_reading_skills, @topic_story_elements, 'paragraph-based-mcq',
       'What happened to the grasshopper in winter?',
       NULL, @p7,
       '{"option1":"He had plenty of food","option2":"He had nothing to eat and was hungry","option3":"He went on vacation","option4":"He helped the ant"}',
       '{"correctOption":2}',
       'The passage states that the grasshopper had nothing to eat and was hungry in winter.',
       'KNOWLEDGE', 'EASY', @created_by),

      (@board_id, @subject_id, @class_id, @medium, @chapter_reading_skills, @topic_story_elements, 'paragraph-based-mcq',
       'What did the grasshopper learn?',
       NULL, @p7,
       '{"option1":"Dancing is important","option2":"Hard work and planning are important","option3":"Winter is bad","option4":"Ants are his enemies"}',
       '{"correctOption":2}',
       'The grasshopper learned that hard work and planning are important.',
       'UNDERSTANDING', 'MEDIUM', @created_by);

-- ============================================================================
-- Paragraph 8: Our Environment (5 questions)
-- ============================================================================
INSERT INTO questions (
    board_id, subject_id, class_id, medium, chapter_id, topic_id,
    question_type, question_text, paragraph_text, paragraph_id,
    options, correct_answer, answer_explanation,
    skill_level, difficulty_level, created_by
) VALUES
      (@board_id, @subject_id, @class_id, @medium, @chapter_reading_skills, @topic_passages, 'paragraph-based-mcq',
       'What is the main topic of this passage?',
       'Our environment includes everything around us - air, water, plants, and animals. It is our duty to keep our environment clean. We should not throw garbage on the streets. Planting trees helps make the air fresh. Using less plastic protects our oceans. Every small action counts in protecting our planet.',
       @p8,
       '{"option1":"School rules","option2":"Protecting our environment","option3":"Playing games","option4":"Making friends"}',
       '{"correctOption":2}',
       'The passage discusses protecting our environment.',
       'UNDERSTANDING', 'MEDIUM', @created_by),

      (@board_id, @subject_id, @class_id, @medium, @chapter_reading_skills, @topic_passages, 'paragraph-based-mcq',
       'What does our environment include?',
       NULL, @p8,
       '{"option1":"Only water","option2":"Only plants","option3":"Air, water, plants, and animals","option4":"Only buildings"}',
       '{"correctOption":3}',
       'The passage states environment includes air, water, plants, and animals.',
       'KNOWLEDGE', 'EASY', @created_by),

      (@board_id, @subject_id, @class_id, @medium, @chapter_reading_skills, @topic_passages, 'paragraph-based-mcq',
       'What should we not do on the streets?',
       NULL, @p8,
       '{"option1":"Walk","option2":"Throw garbage","option3":"Talk to friends","option4":"Play games"}',
       '{"correctOption":2}',
       'The passage says we should not throw garbage on the streets.',
       'KNOWLEDGE', 'EASY', @created_by),

      (@board_id, @subject_id, @class_id, @medium, @chapter_reading_skills, @topic_passages, 'paragraph-based-mcq',
       'How does planting trees help?',
       NULL, @p8,
       '{"option1":"It makes noise","option2":"It makes the air fresh","option3":"It blocks roads","option4":"It wastes space"}',
       '{"correctOption":2}',
       'The passage states that planting trees helps make the air fresh.',
       'KNOWLEDGE', 'EASY', @created_by),

      (@board_id, @subject_id, @class_id, @medium, @chapter_reading_skills, @topic_passages, 'paragraph-based-mcq',
       'What protects our oceans?',
       NULL, @p8,
       '{"option1":"Using more plastic","option2":"Using less plastic","option3":"Throwing garbage in water","option4":"Cutting trees"}',
       '{"correctOption":2}',
       'The passage mentions that using less plastic protects our oceans.',
       'UNDERSTANDING', 'MEDIUM', @created_by);

-- ============================================================================
-- Final message
-- ============================================================================
SELECT 'All 40 paragraph-based questions (p3 to p8) inserted successfully' AS status;