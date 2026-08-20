--liquibase formatted sql
--changeset narendra:class4-english-missing-chapters-questions

-- =============================================
-- Class 4 English - Missing Chapters Questions
-- Board: MSCE - PRE UPPER PRIMARY SCHOLARSHIP EXAMINATION
-- Class: 4 (class_id = 2)
-- Subject: English – First Language (subject_id = 36)
-- Medium: English
-- Chapters: Word Games (970), Grammar (971), Creative writing (973),
--           Reading skills (974), Miscellaneous (975)
-- Total Questions: 150+ (5 questions per topic across SUKA and difficulty levels)
-- =============================================

-- Set variables for board, class, subject, and medium
SET @board_id = (SELECT id FROM board WHERE board_name = 'MSCE' LIMIT 1);
SET @subject_id = (SELECT subject_id FROM subject WHERE subject_name = 'English – First Language' LIMIT 1);
SET @class_id = (SELECT id FROM class WHERE class_name = '4' LIMIT 1);
SET @medium = 'English';
SET @created_by = 101;

-- Chapter Variables
SET @chapter_word_games = (SELECT c.id FROM chapters c
    INNER JOIN chapter_board_class_mapping cbcm ON c.id = cbcm.chapter_id
    WHERE c.subject_id = @subject_id AND cbcm.board_id = @board_id AND cbcm.class_id = @class_id
    AND c.chapter_name = 'Word Games' LIMIT 1);

SET @chapter_grammar = (SELECT c.id FROM chapters c
    INNER JOIN chapter_board_class_mapping cbcm ON c.id = cbcm.chapter_id
    WHERE c.subject_id = @subject_id AND cbcm.board_id = @board_id AND cbcm.class_id = @class_id
    AND c.chapter_name = 'Grammar' LIMIT 1);

SET @chapter_creative_writing = (SELECT c.id FROM chapters c
    INNER JOIN chapter_board_class_mapping cbcm ON c.id = cbcm.chapter_id
    WHERE c.subject_id = @subject_id AND cbcm.board_id = @board_id AND cbcm.class_id = @class_id
    AND c.chapter_name = 'Creative writing' LIMIT 1);

SET @chapter_reading_skills = (SELECT c.id FROM chapters c
    INNER JOIN chapter_board_class_mapping cbcm ON c.id = cbcm.chapter_id
    WHERE c.subject_id = @subject_id AND cbcm.board_id = @board_id AND cbcm.class_id = @class_id
    AND c.chapter_name = 'Reading skills (comprehension)' LIMIT 1);

SET @chapter_miscellaneous = (SELECT c.id FROM chapters c
    INNER JOIN chapter_board_class_mapping cbcm ON c.id = cbcm.chapter_id
    WHERE c.subject_id = @subject_id AND cbcm.board_id = @board_id AND cbcm.class_id = @class_id
    AND c.chapter_name = 'Miscellaneous' LIMIT 1);

-- Topic Variables for Word Games Chapter (970)
SET @topic_puzzles = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_word_games AND subject_id = @subject_id
    AND topic_name = 'Puzzles' LIMIT 1);

SET @topic_word_register = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_word_games AND subject_id = @subject_id
    AND topic_name = 'Word Register' LIMIT 1);

SET @topic_related_words = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_word_games AND subject_id = @subject_id
    AND topic_name = 'Related words' LIMIT 1);

SET @topic_match_words_pictures = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_word_games AND subject_id = @subject_id
    AND topic_name = 'Match the words and pictures' LIMIT 1);

-- Topic Variables for Grammar Chapter (971)
SET @topic_parts_of_speech = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_grammar AND subject_id = @subject_id
    AND topic_name = 'Parts of speech' LIMIT 1);

SET @topic_nouns = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_grammar AND subject_id = @subject_id
    AND topic_name = 'Nouns-types: common, proper, collective, abstract' LIMIT 1);

SET @topic_pronouns = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_grammar AND subject_id = @subject_id
    AND topic_name = 'Pronouns - personal pronouns' LIMIT 1);

SET @topic_adjectives = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_grammar AND subject_id = @subject_id
    AND topic_name = 'Adjectives - degree of comparison' LIMIT 1);

SET @topic_verbs_conjugation = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_grammar AND subject_id = @subject_id
    AND topic_name = 'Verbs - Conjugation' LIMIT 1);

SET @topic_verbs_types = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_grammar AND subject_id = @subject_id
    AND topic_name = 'Verbs - Action (main) verbs and auxiliary verb' LIMIT 1);

SET @topic_adverbs = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_grammar AND subject_id = @subject_id
    AND topic_name = 'Adverbs' LIMIT 1);

SET @topic_prepositions = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_grammar AND subject_id = @subject_id
    AND topic_name = 'Prepositions' LIMIT 1);

SET @topic_conjunction = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_grammar AND subject_id = @subject_id
    AND topic_name = 'Conjunction' LIMIT 1);

SET @topic_articles_vowels = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_grammar AND subject_id = @subject_id
    AND topic_name = 'Articles - Vowels' LIMIT 1);

SET @topic_articles_consonants = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_grammar AND subject_id = @subject_id
    AND topic_name = 'Articles - Consonants' LIMIT 1);

SET @topic_sentence_parts = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_grammar AND subject_id = @subject_id
    AND topic_name = 'Parts of a Sentence: Subject, Predicate' LIMIT 1);

-- Topic Variables for Creative Writing Chapter (973)
SET @topic_titles_captions = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_creative_writing AND subject_id = @subject_id
    AND topic_name = 'Give titles, captions and headlines on news, stories, pictures and leaflet' LIMIT 1);

SET @topic_paragraph_writing = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_creative_writing AND subject_id = @subject_id
    AND topic_name = 'Paragraph writing, Stories, processes, events, experiments, speech, flow chart' LIMIT 1);

SET @topic_autobiography = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_creative_writing AND subject_id = @subject_id
    AND topic_name = 'Auto-biography, short autobiography of a thing or object' LIMIT 1);

SET @topic_letters = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_creative_writing AND subject_id = @subject_id
    AND topic_name = 'Informal letter, formal letter (format or complete the letter)' LIMIT 1);

-- Topic Variables for Reading Skills Chapter (974)
SET @topic_passages = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_reading_skills AND subject_id = @subject_id
    AND topic_name = 'Descriptive / Informative / Narrative / Imaginative Passage' LIMIT 1);

SET @topic_leaflet = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_reading_skills AND subject_id = @subject_id
    AND topic_name = 'Leaflet' LIMIT 1);

SET @topic_skit_conversation = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_reading_skills AND subject_id = @subject_id
    AND topic_name = 'Short skit/ Conversation' LIMIT 1);

SET @topic_poem = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_reading_skills AND subject_id = @subject_id
    AND topic_name = 'Poem' LIMIT 1);

-- Topic Variables for Miscellaneous Chapter (975)
SET @topic_numbers = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_miscellaneous AND subject_id = @subject_id
    AND topic_name = 'Numbers (cardinals and ordinals)' LIMIT 1);

SET @topic_non_english_words = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_miscellaneous AND subject_id = @subject_id
    AND topic_name = 'Non English words' LIMIT 1);

SET @topic_read_maps = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_miscellaneous AND subject_id = @subject_id
    AND topic_name = 'Read maps' LIMIT 1);

SET @topic_charts = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_miscellaneous AND subject_id = @subject_id
    AND topic_name = 'Charts' LIMIT 1);

SET @topic_stock_expressions = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_miscellaneous AND subject_id = @subject_id
    AND topic_name = 'Stock expressions' LIMIT 1);

-- =============================================
-- Insert Questions
-- =============================================

-- =============================================
-- Chapter: Word Games (970)
-- =============================================

-- Topic: Puzzles (Topic ID: 1613) - 5 questions
INSERT INTO questions (board_id, subject_id, class_id, medium, chapter_id, topic_id, question_type,
                       question_text, options, correct_answer, answer_explanation,
                       skill_level, difficulty_level, created_by)
VALUES
(@board_id, @subject_id, @class_id, @medium, @chapter_word_games, @topic_puzzles, 'MCQ',
'What comes next in the pattern: A, C, E, G, __?',
'{"option1":"H","option2":"I","option3":"J","option4":"K"}',
'{"correctOption":2}',
'The pattern skips one letter each time, so after G comes I.',
'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_word_games, @topic_puzzles, 'MCQ',
'Complete the word: B__K (something you read)',
'{"option1":"OO","option2":"EE","option3":"AA","option4":"UU"}',
'{"correctOption":1}',
'BOOK is something you read.',
'UNDERSTANDING', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_word_games, @topic_puzzles, 'MCQ',
'Find the hidden word: "The cat saw eight mice"',
'{"option1":"CAT","option2":"SAW","option3":"WHEAT","option4":"TEA"}',
'{"correctOption":4}',
'TEA is hidden in "The cat saw eighT micE A..."',
'SKILL', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_word_games, @topic_puzzles, 'MCQ',
'What word can be made from the letters in TRIANGLE?',
'{"option1":"GREAT","option2":"GRAIL","option3":"TRAIL","option4":"All of the above"}',
'{"correctOption":4}',
'GREAT, GRAIL, and TRAIL can all be formed using letters from TRIANGLE.',
'APPLICATION', 'HARD', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_word_games, @topic_puzzles, 'MCQ',
'Which letter replaces the question mark: A=1, B=2, C=3, D=?',
'{"option1":"3","option2":"4","option3":"5","option4":"6"}',
'{"correctOption":2}',
'Following the pattern, D is the 4th letter of the alphabet.',
'UNDERSTANDING', 'MEDIUM', @created_by),

-- Topic: Word Register (Topic ID: 1614) - 5 questions
(@board_id, @subject_id, @class_id, @medium, @chapter_word_games, @topic_word_register, 'MCQ',
'Which word belongs to the "kitchen" word register?',
'{"option1":"Stove","option2":"Bed","option3":"Desk","option4":"Chair"}',
'{"correctOption":1}',
'A stove is used in the kitchen for cooking.',
'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_word_games, @topic_word_register, 'MCQ',
'Which word fits the "school" register?',
'{"option1":"Spoon","option2":"Notebook","option3":"Pillow","option4":"Television"}',
'{"correctOption":2}',
'A notebook is used in school for writing.',
'UNDERSTANDING', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_word_games, @topic_word_register, 'MCQ',
'Select words that belong to "hospital" register:',
'{"option1":"Doctor, nurse, medicine","option2":"Teacher, student, book","option3":"Pilot, plane, airport","option4":"Chef, menu, restaurant"}',
'{"correctOption":1}',
'Doctor, nurse, and medicine are all related to hospitals.',
'SKILL', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_word_games, @topic_word_register, 'MCQ',
'Which word does NOT belong to the "transport" register?',
'{"option1":"Bus","option2":"Train","option3":"Book","option4":"Car"}',
'{"correctOption":3}',
'Book is not a means of transport.',
'APPLICATION', 'HARD', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_word_games, @topic_word_register, 'MCQ',
'Identify the word register: telescope, microscope, laboratory',
'{"option1":"Kitchen","option2":"School","option3":"Science","option4":"Sports"}',
'{"correctOption":3}',
'These words are all related to science and scientific study.',
'UNDERSTANDING', 'MEDIUM', @created_by),

-- Topic: Related words (Topic ID: 1615) - 5 questions
(@board_id, @subject_id, @class_id, @medium, @chapter_word_games, @topic_related_words, 'MCQ',
'Which word is related to "book"?',
'{"option1":"Read","option2":"Run","option3":"Jump","option4":"Swim"}',
'{"correctOption":1}',
'"Read" is related to "book" because we read books.',
'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_word_games, @topic_related_words, 'MCQ',
'Find the word related to "rain":',
'{"option1":"Sun","option2":"Umbrella","option3":"Snow","option4":"Wind"}',
'{"correctOption":2}',
'We use an umbrella when it rains.',
'UNDERSTANDING', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_word_games, @topic_related_words, 'MCQ',
'Which group of words are related?',
'{"option1":"Apple, banana, mango","option2":"Table, run, happy","option3":"Red, quickly, under","option4":"Book, water, sky"}',
'{"correctOption":1}',
'Apple, banana, and mango are all fruits.',
'SKILL', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_word_games, @topic_related_words, 'MCQ',
'Select related words for "hospital":',
'{"option1":"Doctor, patient, medicine","option2":"Teacher, classroom, chalk","option3":"Chef, kitchen, recipe","option4":"Pilot, plane, sky"}',
'{"correctOption":1}',
'Doctor, patient, and medicine are all related to hospitals.',
'APPLICATION', 'HARD', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_word_games, @topic_related_words, 'MCQ',
'Which word is NOT related to "cricket"?',
'{"option1":"Bat","option2":"Ball","option3":"Wicket","option4":"Racket"}',
'{"correctOption":4}',
'Racket is used in tennis or badminton, not cricket.',
'UNDERSTANDING', 'MEDIUM', @created_by),

-- Topic: Match the words and pictures (Topic ID: 1616) - 5 questions
(@board_id, @subject_id, @class_id, @medium, @chapter_word_games, @topic_match_words_pictures, 'MCQ',
'What object is round and used in games?',
'{"option1":"Ball","option2":"Book","option3":"Pencil","option4":"Table"}',
'{"correctOption":1}',
'A ball is round and used in many games.',
'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_word_games, @topic_match_words_pictures, 'MCQ',
'Which word describes a yellow fruit that monkeys love?',
'{"option1":"Apple","option2":"Orange","option3":"Banana","option4":"Grapes"}',
'{"correctOption":3}',
'A banana is yellow and monkeys are known to love it.',
'UNDERSTANDING', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_word_games, @topic_match_words_pictures, 'MCQ',
'What animal has a long neck and lives in Africa?',
'{"option1":"Elephant","option2":"Lion","option3":"Giraffe","option4":"Tiger"}',
'{"correctOption":3}',
'A giraffe has a very long neck and lives in Africa.',
'SKILL', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_word_games, @topic_match_words_pictures, 'MCQ',
'Match: A vehicle with two wheels that you pedal',
'{"option1":"Car","option2":"Bicycle","option3":"Bus","option4":"Train"}',
'{"correctOption":2}',
'A bicycle has two wheels and is powered by pedaling.',
'APPLICATION', 'HARD', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_word_games, @topic_match_words_pictures, 'MCQ',
'What object has keys and is used to type?',
'{"option1":"Piano","option2":"Door","option3":"Keyboard","option4":"Lock"}',
'{"correctOption":3}',
'A keyboard has keys and is used for typing on computers.',
'UNDERSTANDING', 'MEDIUM', @created_by),

-- =============================================
-- Chapter: Grammar (971)
-- =============================================

-- Topic: Parts of speech (Topic ID: 1617) - 5 questions
(@board_id, @subject_id, @class_id, @medium, @chapter_grammar, @topic_parts_of_speech, 'MCQ',
'What part of speech is the word "run"?',
'{"option1":"Noun","option2":"Verb","option3":"Adjective","option4":"Adverb"}',
'{"correctOption":2}',
'"Run" is a verb (action word).',
'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_grammar, @topic_parts_of_speech, 'MCQ',
'Identify the noun in: "The dog barks loudly."',
'{"option1":"The","option2":"dog","option3":"barks","option4":"loudly"}',
'{"correctOption":2}',
'"Dog" is a noun (naming word).',
'UNDERSTANDING', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_grammar, @topic_parts_of_speech, 'MCQ',
'Which word is an adjective?',
'{"option1":"Quickly","option2":"Beautiful","option3":"Run","option4":"And"}',
'{"correctOption":2}',
'"Beautiful" describes a noun, making it an adjective.',
'SKILL', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_grammar, @topic_parts_of_speech, 'MCQ',
'In the sentence "She sings sweetly", what part of speech is "sweetly"?',
'{"option1":"Noun","option2":"Verb","option3":"Adjective","option4":"Adverb"}',
'{"correctOption":4}',
'"Sweetly" describes how she sings, making it an adverb.',
'APPLICATION', 'HARD', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_grammar, @topic_parts_of_speech, 'MCQ',
'What connects two words or sentences?',
'{"option1":"Noun","option2":"Verb","option3":"Conjunction","option4":"Pronoun"}',
'{"correctOption":3}',
'A conjunction connects words, phrases, or sentences (e.g., and, but, or).',
'UNDERSTANDING', 'MEDIUM', @created_by),

-- Topic: Nouns-types (Topic ID: 1618) - 5 questions
(@board_id, @subject_id, @class_id, @medium, @chapter_grammar, @topic_nouns, 'MCQ',
'Which is a proper noun?',
'{"option1":"city","option2":"Mumbai","option3":"river","option4":"mountain"}',
'{"correctOption":2}',
'"Mumbai" is a proper noun (specific name), others are common nouns.',
'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_grammar, @topic_nouns, 'MCQ',
'What type of noun is "team"?',
'{"option1":"Proper noun","option2":"Common noun","option3":"Collective noun","option4":"Abstract noun"}',
'{"correctOption":3}',
'"Team" is a collective noun (a group of people).',
'UNDERSTANDING', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_grammar, @topic_nouns, 'MCQ',
'Identify the abstract noun:',
'{"option1":"Book","option2":"Happiness","option3":"Table","option4":"Dog"}',
'{"correctOption":2}',
'"Happiness" is an abstract noun (feeling/emotion you cannot touch).',
'SKILL', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_grammar, @topic_nouns, 'MCQ',
'Which sentence has both a proper and common noun?',
'{"option1":"The city is big.","option2":"Delhi is a city.","option3":"Cities are large.","option4":"A place to live."}',
'{"correctOption":2}',
'"Delhi" (proper) and "city" (common) are both nouns.',
'APPLICATION', 'HARD', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_grammar, @topic_nouns, 'MCQ',
'What type of noun is "honesty"?',
'{"option1":"Common","option2":"Proper","option3":"Collective","option4":"Abstract"}',
'{"correctOption":4}',
'"Honesty" is an abstract noun (a quality you cannot see or touch).',
'UNDERSTANDING', 'MEDIUM', @created_by),

-- Topic: Pronouns - personal pronouns (Topic ID: 1619) - 5 questions
(@board_id, @subject_id, @class_id, @medium, @chapter_grammar, @topic_pronouns, 'MCQ',
'Which is a personal pronoun?',
'{"option1":"Book","option2":"He","option3":"Beautiful","option4":"Quickly"}',
'{"correctOption":2}',
'"He" is a personal pronoun used instead of a person\'s name.',
'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_grammar, @topic_pronouns, 'MCQ',
'Choose the correct pronoun: "_____ are going to the park." (for a group)',
'{"option1":"He","option2":"She","option3":"They","option4":"It"}',
'{"correctOption":3}',
'"They" is used for a group of people.',
'UNDERSTANDING', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_grammar, @topic_pronouns, 'MCQ',
'Replace the underlined word with a pronoun: "Ravi plays cricket. Ravi is good."',
'{"option1":"She","option2":"He","option3":"They","option4":"It"}',
'{"correctOption":2}',
'"He" replaces Ravi (a male person).',
'SKILL', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_grammar, @topic_pronouns, 'MCQ',
'Which sentence uses pronouns correctly?',
'{"option1":"Me and my friend went to school.","option2":"My friend and I went to school.","option3":"My friend and me went to school.","option4":"I and my friend went to school."}',
'{"correctOption":2}',
'The correct order is "My friend and I" (others before self).',
'APPLICATION', 'HARD', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_grammar, @topic_pronouns, 'MCQ',
'Select the correct pronoun: "Give the book to _____." (to me)',
'{"option1":"I","option2":"me","option3":"my","option4":"mine"}',
'{"correctOption":2}',
'"Me" is the object pronoun used after "to".',
'UNDERSTANDING', 'MEDIUM', @created_by),

-- Topic: Adjectives - degree of comparison (Topic ID: 1620) - 5 questions
(@board_id, @subject_id, @class_id, @medium, @chapter_grammar, @topic_adjectives, 'MCQ',
'What is the comparative form of "big"?',
'{"option1":"Bigger","option2":"Biggest","option3":"Biger","option4":"Most big"}',
'{"correctOption":1}',
'The comparative form is "bigger" (comparing two things).',
'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_grammar, @topic_adjectives, 'MCQ',
'What is the superlative form of "good"?',
'{"option1":"Gooder","option2":"Goodest","option3":"Better","option4":"Best"}',
'{"correctOption":4}',
'"Best" is the superlative form of "good" (the highest degree).',
'UNDERSTANDING', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_grammar, @topic_adjectives, 'MCQ',
'Choose the correct form: "This is the _____ day of my life." (superlative)',
'{"option1":"happy","option2":"happier","option3":"happiest","option4":"most happy"}',
'{"correctOption":3}',
'Superlative form "happiest" shows the highest degree.',
'SKILL', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_grammar, @topic_adjectives, 'MCQ',
'Which sentence uses the correct comparative?',
'{"option1":"She is more taller than her sister.","option2":"She is taller than her sister.","option3":"She is tallest than her sister.","option4":"She is most tall than her sister."}',
'{"correctOption":2}',
'Use "taller" (not "more taller") for one-syllable adjectives.',
'APPLICATION', 'HARD', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_grammar, @topic_adjectives, 'MCQ',
'What is the comparative of "beautiful"?',
'{"option1":"Beautifuler","option2":"More beautiful","option3":"Most beautiful","option4":"Beautifuller"}',
'{"correctOption":2}',
'For longer adjectives, use "more" for comparative: "more beautiful".',
'UNDERSTANDING', 'MEDIUM', @created_by),

-- Topic: Verbs - Conjugation (Topic ID: 1621) - 5 questions
(@board_id, @subject_id, @class_id, @medium, @chapter_grammar, @topic_verbs_conjugation, 'MCQ',
'What is the past tense of "play"?',
'{"option1":"Plays","option2":"Playing","option3":"Played","option4":"Will play"}',
'{"correctOption":3}',
'The past tense of "play" is "played".',
'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_grammar, @topic_verbs_conjugation, 'MCQ',
'Choose the correct verb form: "She _____ to school every day." (present)',
'{"option1":"go","option2":"goes","option3":"went","option4":"going"}',
'{"correctOption":2}',
'With singular subjects (she), use "goes" in present tense.',
'UNDERSTANDING', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_grammar, @topic_verbs_conjugation, 'MCQ',
'What is the present participle of "swim"?',
'{"option1":"Swims","option2":"Swimming","option3":"Swam","option4":"Swum"}',
'{"correctOption":2}',
'The present participle is formed by adding -ing: "swimming".',
'SKILL', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_grammar, @topic_verbs_conjugation, 'MCQ',
'Identify the correctly conjugated sentence:',
'{"option1":"They was playing.","option2":"They were playing.","option3":"They is playing.","option4":"They am playing."}',
'{"correctOption":2}',
'"They" (plural) takes "were" not "was".',
'APPLICATION', 'HARD', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_grammar, @topic_verbs_conjugation, 'MCQ',
'What are the three forms of "eat"?',
'{"option1":"Eat, ate, eaten","option2":"Eat, eats, eating","option3":"Eat, ate, eating","option4":"Eats, ate, eaten"}',
'{"correctOption":1}',
'The three forms are: present (eat), past (ate), past participle (eaten).',
'UNDERSTANDING', 'MEDIUM', @created_by),

-- Topic: Verbs - Action and auxiliary (Topic ID: 1622) - 5 questions
(@board_id, @subject_id, @class_id, @medium, @chapter_grammar, @topic_verbs_types, 'MCQ',
'Which is an action verb?',
'{"option1":"is","option2":"jump","option3":"was","option4":"have"}',
'{"correctOption":2}',
'"Jump" shows action. "Is", "was", and "have" are helping verbs.',
'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_grammar, @topic_verbs_types, 'MCQ',
'Identify the auxiliary verb: "She is reading a book."',
'{"option1":"She","option2":"is","option3":"reading","option4":"book"}',
'{"correctOption":2}',
'"Is" is the auxiliary (helping) verb; "reading" is the main verb.',
'UNDERSTANDING', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_grammar, @topic_verbs_types, 'MCQ',
'Which sentence has both main and auxiliary verbs?',
'{"option1":"Birds fly.","option2":"She is dancing.","option3":"They jumped.","option4":"I sleep."}',
'{"correctOption":2}',
'"Is" (auxiliary) + "dancing" (main) form the complete verb.',
'SKILL', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_grammar, @topic_verbs_types, 'MCQ',
'What is the main verb in: "They have finished their work."?',
'{"option1":"They","option2":"have","option3":"finished","option4":"work"}',
'{"correctOption":3}',
'"Finished" is the main verb; "have" is the auxiliary verb.',
'APPLICATION', 'HARD', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_grammar, @topic_verbs_types, 'MCQ',
'Which word is NOT an auxiliary verb?',
'{"option1":"is","option2":"have","option3":"run","option4":"will"}',
'{"correctOption":3}',
'"Run" is an action verb, not a helping verb.',
'UNDERSTANDING', 'MEDIUM', @created_by),

-- Topic: Adverbs (Topic ID: 1623) - 5 questions
(@board_id, @subject_id, @class_id, @medium, @chapter_grammar, @topic_adverbs, 'MCQ',
'Which word is an adverb?',
'{"option1":"Quick","option2":"Quickly","option3":"Quickness","option4":"Quicker"}',
'{"correctOption":2}',
'"Quickly" is an adverb (describes how an action is done).',
'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_grammar, @topic_adverbs, 'MCQ',
'Find the adverb: "The cat ran swiftly."',
'{"option1":"cat","option2":"ran","option3":"swiftly","option4":"The"}',
'{"correctOption":3}',
'"Swiftly" tells how the cat ran, making it an adverb.',
'UNDERSTANDING', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_grammar, @topic_adverbs, 'MCQ',
'What does the adverb describe in: "She sings beautifully."?',
'{"option1":"Who sings","option2":"What she sings","option3":"How she sings","option4":"Where she sings"}',
'{"correctOption":3}',
'"Beautifully" describes HOW she sings.',
'SKILL', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_grammar, @topic_adverbs, 'MCQ',
'Choose the sentence with the adverb in the correct position:',
'{"option1":"He always is late.","option2":"He is always late.","option3":"He is late always.","option4":"Always he is late."}',
'{"correctOption":2}',
'Adverbs of frequency (always) usually come after "is/am/are".',
'APPLICATION', 'HARD', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_grammar, @topic_adverbs, 'MCQ',
'Which word is an adverb of place?',
'{"option1":"Yesterday","option2":"Quickly","option3":"Here","option4":"Always"}',
'{"correctOption":3}',
'"Here" is an adverb of place (tells where).',
'UNDERSTANDING', 'MEDIUM', @created_by),

-- Topic: Prepositions (Topic ID: 1624) - 5 questions
(@board_id, @subject_id, @class_id, @medium, @chapter_grammar, @topic_prepositions, 'MCQ',
'Which is a preposition?',
'{"option1":"on","option2":"and","option3":"run","option4":"happy"}',
'{"correctOption":1}',
'"On" is a preposition showing position.',
'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_grammar, @topic_prepositions, 'MCQ',
'Fill in: "The book is _____ the table."',
'{"option1":"in","option2":"on","option3":"under","option4":"All can be correct"}',
'{"correctOption":4}',
'All three prepositions can be correct depending on the book\'s position.',
'UNDERSTANDING', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_grammar, @topic_prepositions, 'MCQ',
'Which preposition shows time?',
'{"option1":"at 5 o clock","option2":"on the table","option3":"under the bed","option4":"with my friend"}',
'{"correctOption":1}',
'"At" with a time (5 o clock) shows when something happens.',
'SKILL', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_grammar, @topic_prepositions, 'MCQ',
'Choose the correct preposition: "She is afraid _____ dogs."',
'{"option1":"of","option2":"in","option3":"on","option4":"at"}',
'{"correctOption":1}',
'"Afraid of" is the correct prepositional phrase.',
'APPLICATION', 'HARD', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_grammar, @topic_prepositions, 'MCQ',
'Which preposition indicates direction?',
'{"option1":"The cat is on the mat.","option2":"She walked to the park.","option3":"The book is in the bag.","option4":"He sits at his desk."}',
'{"correctOption":2}',
'"To" indicates direction of movement towards the park.',
'UNDERSTANDING', 'MEDIUM', @created_by),

-- Topic: Conjunction (Topic ID: 1625) - 5 questions
(@board_id, @subject_id, @class_id, @medium, @chapter_grammar, @topic_conjunction, 'MCQ',
'Which word is a conjunction?',
'{"option1":"and","option2":"run","option3":"the","option4":"quickly"}',
'{"correctOption":1}',
'"And" is a conjunction that connects words or sentences.',
'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_grammar, @topic_conjunction, 'MCQ',
'Choose the correct conjunction: "I like tea _____ coffee."',
'{"option1":"and","option2":"but","option3":"or","option4":"because"}',
'{"correctOption":1}',
'"And" connects two things you like.',
'UNDERSTANDING', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_grammar, @topic_conjunction, 'MCQ',
'Which conjunction shows contrast?',
'{"option1":"and","option2":"but","option3":"or","option4":"so"}',
'{"correctOption":2}',
'"But" shows contrast or opposition between two ideas.',
'SKILL', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_grammar, @topic_conjunction, 'MCQ',
'Complete: "Study hard _____ you will pass."',
'{"option1":"but","option2":"or","option3":"and","option4":"because"}',
'{"correctOption":3}',
'"And" shows the result of studying hard.',
'APPLICATION', 'HARD', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_grammar, @topic_conjunction, 'MCQ',
'Which conjunction shows cause?',
'{"option1":"and","option2":"but","option3":"because","option4":"or"}',
'{"correctOption":3}',
'"Because" shows the reason or cause for something.',
'UNDERSTANDING', 'MEDIUM', @created_by),

-- Topic: Articles - Vowels (Topic ID: 1626) - 5 questions
(@board_id, @subject_id, @class_id, @medium, @chapter_grammar, @topic_articles_vowels, 'MCQ',
'Which article is used before words starting with vowels?',
'{"option1":"a","option2":"an","option3":"the","option4":"no article"}',
'{"correctOption":2}',
'"An" is used before words starting with vowel sounds.',
'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_grammar, @topic_articles_vowels, 'MCQ',
'Choose the correct article: "_____ elephant"',
'{"option1":"a","option2":"an","option3":"the","option4":"no article needed"}',
'{"correctOption":2}',
'"An" is used before "elephant" because it starts with a vowel.',
'UNDERSTANDING', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_grammar, @topic_articles_vowels, 'MCQ',
'Which is correct?',
'{"option1":"a umbrella","option2":"an umbrella","option3":"the umbrella only","option4":"umbrella"}',
'{"correctOption":2}',
'"An umbrella" is correct because umbrella starts with a vowel sound.',
'SKILL', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_grammar, @topic_articles_vowels, 'MCQ',
'Fill in correctly: "She ate _____ apple and _____ orange."',
'{"option1":"a, a","option2":"an, an","option3":"a, an","option4":"the, the"}',
'{"correctOption":2}',
'Both "apple" and "orange" start with vowels, so use "an".',
'APPLICATION', 'HARD', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_grammar, @topic_articles_vowels, 'MCQ',
'Which requires "an" not "a"?',
'{"option1":"___ book","option2":"___ hour","option3":"___ cat","option4":"___ table"}',
'{"correctOption":2}',
'"Hour" starts with a vowel sound (the h is silent), so we use "an".',
'UNDERSTANDING', 'MEDIUM', @created_by),

-- Topic: Articles - Consonants (Topic ID: 1627) - 5 questions
(@board_id, @subject_id, @class_id, @medium, @chapter_grammar, @topic_articles_consonants, 'MCQ',
'Which article is used before "cat"?',
'{"option1":"a","option2":"an","option3":"either a or an","option4":"no article"}',
'{"correctOption":1}',
'"A" is used before words starting with consonant sounds.',
'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_grammar, @topic_articles_consonants, 'MCQ',
'Choose the correct: "_____ dog"',
'{"option1":"a","option2":"an","option3":"dog","option4":"the only"}',
'{"correctOption":1}',
'"A dog" is correct because dog starts with a consonant.',
'UNDERSTANDING', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_grammar, @topic_articles_consonants, 'MCQ',
'Which is correct?',
'{"option1":"an ball","option2":"a ball","option3":"ball a","option4":"the ball only"}',
'{"correctOption":2}',
'"A ball" is correct because ball starts with a consonant sound.',
'SKILL', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_grammar, @topic_articles_consonants, 'MCQ',
'Complete: "I saw _____ beautiful bird in _____ garden."',
'{"option1":"a, the","option2":"an, a","option3":"a, a","option4":"the, a"}',
'{"correctOption":1}',
'"A" before beautiful (consonant), "the" for specific garden.',
'APPLICATION', 'HARD', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_grammar, @topic_articles_consonants, 'MCQ',
'Which requires "a" not "an"?',
'{"option1":"___ apple","option2":"___ boy","option3":"___ orange","option4":"___ egg"}',
'{"correctOption":2}',
'"Boy" starts with a consonant, so we use "a".',
'UNDERSTANDING', 'MEDIUM', @created_by),

-- Topic: Parts of a Sentence (Topic ID: 1628) - 5 questions
(@board_id, @subject_id, @class_id, @medium, @chapter_grammar, @topic_sentence_parts, 'MCQ',
'What is the subject in: "The cat sleeps."?',
'{"option1":"The","option2":"cat","option3":"sleeps","option4":"The cat"}',
'{"correctOption":4}',
'The subject is "The cat" (who or what the sentence is about).',
'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_grammar, @topic_sentence_parts, 'MCQ',
'What is the predicate in: "Birds fly in the sky."?',
'{"option1":"Birds","option2":"fly","option3":"fly in the sky","option4":"in the sky"}',
'{"correctOption":3}',
'The predicate is "fly in the sky" (tells what the subject does).',
'UNDERSTANDING', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_grammar, @topic_sentence_parts, 'MCQ',
'Identify the subject: "My little brother plays cricket."',
'{"option1":"My","option2":"brother","option3":"My little brother","option4":"plays cricket"}',
'{"correctOption":3}',
'The complete subject is "My little brother".',
'SKILL', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_grammar, @topic_sentence_parts, 'MCQ',
'Which sentence has a complete subject and predicate?',
'{"option1":"The beautiful garden","option2":"Runs very fast","option3":"The children play happily.","option4":"In the morning"}',
'{"correctOption":3}',
'Subject: "The children", Predicate: "play happily".',
'APPLICATION', 'HARD', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_grammar, @topic_sentence_parts, 'MCQ',
'What is the predicate in: "The teacher teaches well."?',
'{"option1":"The teacher","option2":"teacher","option3":"teaches","option4":"teaches well"}',
'{"correctOption":4}',
'The predicate is "teaches well" (what the subject does and how).',
'UNDERSTANDING', 'MEDIUM', @created_by),

-- =============================================
-- Chapter: Creative writing (973)
-- =============================================

-- Topic: Give titles, captions and headlines (Topic ID: 1639) - 5 questions
(@board_id, @subject_id, @class_id, @medium, @chapter_creative_writing, @topic_titles_captions, 'MCQ',
'What is a good title for a story about a brave boy?',
'{"option1":"The Coward","option2":"The Brave Boy","option3":"A Lazy Day","option4":"The Angry Man"}',
'{"correctOption":2}',
'The title should match the story content - "The Brave Boy".',
'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_creative_writing, @topic_titles_captions, 'MCQ',
'Choose the best caption for a picture of children playing:',
'{"option1":"Sleeping time","option2":"Study hard","option3":"Fun and games","option4":"Quiet please"}',
'{"correctOption":3}',
'"Fun and games" describes children playing.',
'UNDERSTANDING', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_creative_writing, @topic_titles_captions, 'MCQ',
'What headline fits: "Team wins the match"?',
'{"option1":"Victory for Our Team","option2":"Team Loses","option3":"Rain Stops Play","option4":"Practice Session"}',
'{"correctOption":1}',
'"Victory for Our Team" matches a win.',
'SKILL', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_creative_writing, @topic_titles_captions, 'MCQ',
'Select the best title for a story about friendship:',
'{"option1":"The Enemy","option2":"True Friends","option3":"Alone in the World","option4":"The Competition"}',
'{"correctOption":2}',
'"True Friends" is the best title for a friendship story.',
'APPLICATION', 'HARD', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_creative_writing, @topic_titles_captions, 'MCQ',
'What caption suits a picture of a sunrise?',
'{"option1":"Good Night","option2":"A New Dawn","option3":"Darkness Falls","option4":"Moon Rising"}',
'{"correctOption":2}',
'"A New Dawn" describes a sunrise.',
'UNDERSTANDING', 'MEDIUM', @created_by),

-- Topic: Paragraph writing (Topic ID: 1640) - 5 questions
(@board_id, @subject_id, @class_id, @medium, @chapter_creative_writing, @topic_paragraph_writing, 'MCQ',
'What should a paragraph have?',
'{"option1":"Only one sentence","option2":"A topic sentence and supporting details","option3":"Only questions","option4":"No punctuation"}',
'{"correctOption":2}',
'A good paragraph has a main idea (topic sentence) and supporting details.',
'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_creative_writing, @topic_paragraph_writing, 'MCQ',
'Which is the best opening sentence for a paragraph about "My School"?',
'{"option1":"I woke up early.","option2":"My school is a wonderful place.","option3":"I like ice cream.","option4":"The weather is nice."}',
'{"correctOption":2}',
'"My school is a wonderful place" introduces the topic clearly.',
'UNDERSTANDING', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_creative_writing, @topic_paragraph_writing, 'MCQ',
'What makes a good story beginning?',
'{"option1":"It grabs the reader attention","option2":"It ends the story","option3":"It has no details","option4":"It is very long"}',
'{"correctOption":1}',
'A good beginning captures the reader\'s interest.',
'SKILL', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_creative_writing, @topic_paragraph_writing, 'MCQ',
'Arrange the story sequence: 1.Climax 2.Beginning 3.Middle 4.End',
'{"option1":"1,2,3,4","option2":"2,3,1,4","option3":"4,3,2,1","option4":"2,1,3,4"}',
'{"correctOption":2}',
'Correct order: Beginning, Middle (rising action), Climax, End.',
'APPLICATION', 'HARD', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_creative_writing, @topic_paragraph_writing, 'MCQ',
'What should the concluding sentence of a paragraph do?',
'{"option1":"Start a new topic","option2":"Repeat the first sentence exactly","option3":"Summarize the main idea","option4":"Ask many questions"}',
'{"correctOption":3}',
'A concluding sentence wraps up and summarizes the main idea.',
'UNDERSTANDING', 'MEDIUM', @created_by),

-- Topic: Auto-biography (Topic ID: 1641) - 5 questions
(@board_id, @subject_id, @class_id, @medium, @chapter_creative_writing, @topic_autobiography, 'MCQ',
'An autobiography is written by:',
'{"option1":"Someone else","option2":"The person about themselves","option3":"A teacher","option4":"A parent"}',
'{"correctOption":2}',
'Auto-biography is a life story written by the person themselves.',
'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_creative_writing, @topic_autobiography, 'MCQ',
'If a pencil writes its own story, it would start with:',
'{"option1":"You are a pencil","option2":"He is a pencil","option3":"I am a pencil","option4":"They are pencils"}',
'{"correctOption":3}',
'In autobiography, the object speaks as "I" (first person).',
'UNDERSTANDING', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_creative_writing, @topic_autobiography, 'MCQ',
'What point of view is used in autobiography?',
'{"option1":"First person (I, me)","option2":"Second person (you)","option3":"Third person (he, she)","option4":"No person"}',
'{"correctOption":1}',
'Autobiography uses first person perspective.',
'SKILL', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_creative_writing, @topic_autobiography, 'MCQ',
'In "Autobiography of a Coin", the coin might say:',
'{"option1":"The coin was minted","option2":"I was minted in a factory","option3":"You were minted","option4":"Coins are minted"}',
'{"correctOption":2}',
'The coin speaks as "I" in first person.',
'APPLICATION', 'HARD', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_creative_writing, @topic_autobiography, 'MCQ',
'What should an autobiography include?',
'{"option1":"Only happy memories","option2":"Important events from the subject life","option3":"Other people stories","option4":"Only one event"}',
'{"correctOption":2}',
'An autobiography includes important life events and experiences.',
'UNDERSTANDING', 'MEDIUM', @created_by),

-- Topic: Letters (Topic ID: 1642) - 5 questions
(@board_id, @subject_id, @class_id, @medium, @chapter_creative_writing, @topic_letters, 'MCQ',
'How do you start a letter to your friend?',
'{"option1":"Dear Sir","option2":"Dear Friend","option3":"To whom it may concern","option4":"Dear Madam"}',
'{"correctOption":2}',
'Informal letters to friends start with "Dear Friend" or their name.',
'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_creative_writing, @topic_letters, 'MCQ',
'What is the difference between formal and informal letters?',
'{"option1":"Formal uses casual language, informal uses formal language","option2":"Formal is for official purposes, informal is for friends/family","option3":"They are the same","option4":"Formal is shorter"}',
'{"correctOption":2}',
'Formal letters are for official purposes, informal for personal communication.',
'UNDERSTANDING', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_creative_writing, @topic_letters, 'MCQ',
'How should you end a formal letter?',
'{"option1":"Love,","option2":"Your friend,","option3":"Yours sincerely,","option4":"See you soon,"}',
'{"correctOption":3}',
'"Yours sincerely" or "Yours faithfully" are formal closings.',
'SKILL', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_creative_writing, @topic_letters, 'MCQ',
'In a formal letter to a principal, the salutation should be:',
'{"option1":"Hi Principal","option2":"Dear Principal","option3":"Respected Sir/Madam","option4":"Hello"}',
'{"correctOption":3}',
'"Respected Sir/Madam" is the appropriate formal salutation.',
'APPLICATION', 'HARD', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_creative_writing, @topic_letters, 'MCQ',
'What goes in the top right corner of a letter?',
'{"option1":"Your name","option2":"Date","option3":"Your address and date","option4":"Nothing"}',
'{"correctOption":3}',
'The sender\'s address and date go in the top right corner.',
'UNDERSTANDING', 'MEDIUM', @created_by),

-- =============================================
-- Chapter: Reading skills (comprehension) (974)
-- =============================================

-- Topic: Passages (Topic ID: 1643) - 5 questions
(@board_id, @subject_id, @class_id, @medium, @chapter_reading_skills, @topic_passages, 'MCQ',
'What is a passage?',
'{"option1":"A single word","option2":"A group of sentences on one topic","option3":"A question","option4":"A picture"}',
'{"correctOption":2}',
'A passage is a group of sentences written about one topic.',
'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_reading_skills, @topic_passages, 'MCQ',
'What type of passage tells a story?',
'{"option1":"Descriptive","option2":"Informative","option3":"Narrative","option4":"Imaginative"}',
'{"correctOption":3}',
'A narrative passage tells a story with characters and events.',
'UNDERSTANDING', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_reading_skills, @topic_passages, 'MCQ',
'What should you do first when reading a passage?',
'{"option1":"Answer questions immediately","option2":"Read the title and skim through","option3":"Count the words","option4":"Close your eyes"}',
'{"correctOption":2}',
'Reading the title and skimming helps understand the main idea.',
'SKILL', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_reading_skills, @topic_passages, 'MCQ',
'In a descriptive passage, the author mainly:',
'{"option1":"Tells a story","option2":"Describes using sensory details","option3":"Gives instructions","option4":"Asks questions"}',
'{"correctOption":2}',
'Descriptive passages use details to paint a picture with words.',
'APPLICATION', 'HARD', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_reading_skills, @topic_passages, 'MCQ',
'An informative passage aims to:',
'{"option1":"Entertain","option2":"Provide facts and information","option3":"Tell a fictional story","option4":"Express feelings"}',
'{"correctOption":2}',
'Informative passages provide factual information about a topic.',
'UNDERSTANDING', 'MEDIUM', @created_by),

-- Topic: Leaflet (Topic ID: 1644) - 5 questions
(@board_id, @subject_id, @class_id, @medium, @chapter_reading_skills, @topic_leaflet, 'MCQ',
'What is a leaflet?',
'{"option1":"A type of leaf","option2":"A small printed sheet with information","option3":"A book","option4":"A newspaper"}',
'{"correctOption":2}',
'A leaflet is a small printed paper giving information.',
'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_reading_skills, @topic_leaflet, 'MCQ',
'What information would you find in a school event leaflet?',
'{"option1":"Cooking recipes","option2":"Event date, time, and venue","option3":"Math problems","option4":"Story tales"}',
'{"correctOption":2}',
'Event leaflets contain date, time, venue, and other relevant details.',
'UNDERSTANDING', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_reading_skills, @topic_leaflet, 'MCQ',
'A leaflet about a zoo would most likely include:',
'{"option1":"Animals found, visiting hours, ticket price","option2":"Cooking recipes","option3":"Math formulas","option4":"Historical dates"}',
'{"correctOption":1}',
'A zoo leaflet provides information about animals, timings, and tickets.',
'SKILL', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_reading_skills, @topic_leaflet, 'MCQ',
'What makes a leaflet effective?',
'{"option1":"Very long paragraphs","option2":"No pictures","option3":"Clear, concise information with visuals","option4":"Difficult words only"}',
'{"correctOption":3}',
'Effective leaflets have clear, brief information and helpful visuals.',
'APPLICATION', 'HARD', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_reading_skills, @topic_leaflet, 'MCQ',
'Why are leaflets useful?',
'{"option1":"To waste paper","option2":"To quickly inform people about something","option3":"To make airplanes","option4":"To replace books"}',
'{"correctOption":2}',
'Leaflets quickly provide important information to many people.',
'UNDERSTANDING', 'MEDIUM', @created_by),

-- Topic: Short skit/ Conversation (Topic ID: 1645) - 5 questions
(@board_id, @subject_id, @class_id, @medium, @chapter_reading_skills, @topic_skit_conversation, 'MCQ',
'What is a skit?',
'{"option1":"A type of food","option2":"A short play or performance","option3":"A long book","option4":"A type of game"}',
'{"correctOption":2}',
'A skit is a short dramatic performance or play.',
'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_reading_skills, @topic_skit_conversation, 'MCQ',
'In a written conversation, who is speaking is shown by:',
'{"option1":"Colors","option2":"Names before the dialogue","option3":"Numbers","option4":"No indication needed"}',
'{"correctOption":2}',
'In scripts, the character\'s name appears before their dialogue.',
'UNDERSTANDING', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_reading_skills, @topic_skit_conversation, 'MCQ',
'What makes a good conversation in a skit?',
'{"option1":"Very long speeches","option2":"Natural, realistic dialogue","option3":"No emotions","option4":"Only one person talking"}',
'{"correctOption":2}',
'Good dialogue sounds natural and realistic.',
'SKILL', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_reading_skills, @topic_skit_conversation, 'MCQ',
'How should you read a conversation to understand it better?',
'{"option1":"Very fast","option2":"Without paying attention","option3":"With expression, imagining the scene","option4":"Backwards"}',
'{"correctOption":3}',
'Reading with expression and visualizing helps understand conversations.',
'APPLICATION', 'HARD', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_reading_skills, @topic_skit_conversation, 'MCQ',
'What are stage directions in a skit?',
'{"option1":"Directions to the stage","option2":"Instructions about how actors should move or speak","option3":"The title of the play","option4":"The audience seats"}',
'{"correctOption":2}',
'Stage directions tell actors how to perform (movements, expressions, tone).',
'UNDERSTANDING', 'MEDIUM', @created_by),

-- Topic: Poem (Topic ID: 1646) - 5 questions
(@board_id, @subject_id, @class_id, @medium, @chapter_reading_skills, @topic_poem, 'MCQ',
'What is rhyme in a poem?',
'{"option1":"The title","option2":"When words end with similar sounds","option3":"The first line","option4":"The poet name"}',
'{"correctOption":2}',
'Rhyme is when words have the same ending sound (e.g., cat/hat).',
'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_reading_skills, @topic_poem, 'MCQ',
'Which words rhyme?',
'{"option1":"Sun and moon","option2":"Cat and mat","option3":"Book and pencil","option4":"Red and blue"}',
'{"correctOption":2}',
'"Cat" and "mat" have the same ending sound.',
'UNDERSTANDING', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_reading_skills, @topic_poem, 'MCQ',
'What is a stanza in a poem?',
'{"option1":"A single word","option2":"A group of lines","option3":"The title","option4":"The rhyme"}',
'{"correctOption":2}',
'A stanza is a group of lines in a poem (like a paragraph).',
'SKILL', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_reading_skills, @topic_poem, 'MCQ',
'What makes poetry different from prose?',
'{"option1":"It has no meaning","option2":"It uses rhythm, rhyme, and line breaks","option3":"It is always sad","option4":"It has no punctuation"}',
'{"correctOption":2}',
'Poetry uses rhythm, rhyme, metaphor, and structured line breaks.',
'APPLICATION', 'HARD', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_reading_skills, @topic_poem, 'MCQ',
'What is the mood of a poem about a sunny day?',
'{"option1":"Sad","option2":"Angry","option3":"Cheerful and bright","option4":"Scary"}',
'{"correctOption":3}',
'A poem about a sunny day would likely have a cheerful, positive mood.',
'UNDERSTANDING', 'MEDIUM', @created_by),

-- =============================================
-- Chapter: Miscellaneous (975)
-- =============================================

-- Topic: Numbers (cardinals and ordinals) (Topic ID: 1647) - 5 questions
(@board_id, @subject_id, @class_id, @medium, @chapter_miscellaneous, @topic_numbers, 'MCQ',
'Which is a cardinal number?',
'{"option1":"First","option2":"Second","option3":"Three","option4":"Fifth"}',
'{"correctOption":3}',
'Cardinal numbers (1, 2, 3) show quantity. Ordinals (1st, 2nd, 3rd) show order.',
'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_miscellaneous, @topic_numbers, 'MCQ',
'What is the ordinal form of 4?',
'{"option1":"Four","option2":"Forty","option3":"Fourth","option4":"Fourteen"}',
'{"correctOption":3}',
'The ordinal form of 4 is "fourth" (4th).',
'UNDERSTANDING', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_miscellaneous, @topic_numbers, 'MCQ',
'Complete: "She came _____ in the race." (position)',
'{"option1":"three","option2":"third","option3":"thirty","option4":"thirteen"}',
'{"correctOption":2}',
'Use ordinal "third" to show position in a race.',
'SKILL', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_miscellaneous, @topic_numbers, 'MCQ',
'Which sentence uses cardinal numbers correctly?',
'{"option1":"I have third pencils.","option2":"I have three pencils.","option3":"I have third pencil.","option4":"I have threeth pencils."}',
'{"correctOption":2}',
'Use cardinal "three" to show quantity.',
'APPLICATION', 'HARD', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_miscellaneous, @topic_numbers, 'MCQ',
'What is the cardinal number for "tenth"?',
'{"option1":"Ten","option2":"Hundred","option3":"Twenty","option4":"One"}',
'{"correctOption":1}',
'Tenth (ordinal) → Ten (cardinal).',
'UNDERSTANDING', 'MEDIUM', @created_by),

-- Topic: Non English words (Topic ID: 1648) - 5 questions
(@board_id, @subject_id, @class_id, @medium, @chapter_miscellaneous, @topic_non_english_words, 'MCQ',
'Which word originally comes from Hindi but is used in English?',
'{"option1":"Cat","option2":"Bungalow","option3":"Book","option4":"Water"}',
'{"correctOption":2}',
'"Bungalow" comes from Hindi "bangla" and is now used in English.',
'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_miscellaneous, @topic_non_english_words, 'MCQ',
'Which food word came from another language?',
'{"option1":"Bread","option2":"Pizza","option3":"Butter","option4":"Milk"}',
'{"correctOption":2}',
'"Pizza" is originally an Italian word.',
'UNDERSTANDING', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_miscellaneous, @topic_non_english_words, 'MCQ',
'What does "guru" mean (from Sanskrit)?',
'{"option1":"Student","option2":"Teacher or guide","option3":"Book","option4":"School"}',
'{"correctOption":2}',
'"Guru" means teacher or spiritual guide in Sanskrit.',
'SKILL', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_miscellaneous, @topic_non_english_words, 'MCQ',
'Which word borrowed from French means "goodbye"?',
'{"option1":"Hello","option2":"Adieu","option3":"Thank you","option4":"Please"}',
'{"correctOption":2}',
'"Adieu" is French for goodbye.',
'APPLICATION', 'HARD', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_miscellaneous, @topic_non_english_words, 'MCQ',
'The word "jungle" comes from:',
'{"option1":"English","option2":"French","option3":"Hindi/Sanskrit","option4":"Spanish"}',
'{"correctOption":3}',
'"Jungle" comes from Hindi "jangal".',
'UNDERSTANDING', 'MEDIUM', @created_by),

-- Topic: Read maps (Topic ID: 1649) - 5 questions
(@board_id, @subject_id, @class_id, @medium, @chapter_miscellaneous, @topic_read_maps, 'MCQ',
'What does a map show?',
'{"option1":"Only pictures","option2":"Locations and directions","option3":"Stories","option4":"Numbers only"}',
'{"correctOption":2}',
'Maps show places, locations, and how to get from one place to another.',
'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_miscellaneous, @topic_read_maps, 'MCQ',
'On a map, what does "N" usually represent?',
'{"option1":"Name","option2":"Number","option3":"North","option4":"New"}',
'{"correctOption":3}',
'"N" on a map indicates North direction.',
'UNDERSTANDING', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_miscellaneous, @topic_read_maps, 'MCQ',
'If the library is west of school on a map, and you\'re at school, which way do you go?',
'{"option1":"Left","option2":"Right","option3":"It depends on which way you are facing","option4":"Up"}',
'{"correctOption":3}',
'Direction depends on your orientation. West is left if facing north.',
'SKILL', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_miscellaneous, @topic_read_maps, 'MCQ',
'What is a map legend or key?',
'{"option1":"The map title","option2":"Explanation of symbols used on the map","option3":"The border of the map","option4":"The map maker name"}',
'{"correctOption":2}',
'The legend explains what symbols and colors mean on the map.',
'APPLICATION', 'HARD', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_miscellaneous, @topic_read_maps, 'MCQ',
'Why do we need maps?',
'{"option1":"For decoration only","option2":"To find locations and plan routes","option3":"To draw pictures","option4":"To write stories"}',
'{"correctOption":2}',
'Maps help us locate places and plan how to reach them.',
'UNDERSTANDING', 'MEDIUM', @created_by),

-- Topic: Charts (Topic ID: 1650) - 5 questions
(@board_id, @subject_id, @class_id, @medium, @chapter_miscellaneous, @topic_charts, 'MCQ',
'What does a chart show?',
'{"option1":"Information in a visual form","option2":"Only words","option3":"Stories","option4":"Nothing"}',
'{"correctOption":1}',
'Charts present information visually using graphs, bars, or pictures.',
'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_miscellaneous, @topic_charts, 'MCQ',
'What type of chart uses rectangular bars?',
'{"option1":"Pie chart","option2":"Bar chart","option3":"Line chart","option4":"Scatter chart"}',
'{"correctOption":2}',
'A bar chart uses bars of different heights to show data.',
'UNDERSTANDING', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_miscellaneous, @topic_charts, 'MCQ',
'In a pie chart, what does a larger slice mean?',
'{"option1":"Less quantity","option2":"More quantity or percentage","option3":"Equal quantity","option4":"No quantity"}',
'{"correctOption":2}',
'Larger slices represent bigger quantities or percentages.',
'SKILL', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_miscellaneous, @topic_charts, 'MCQ',
'If a bar chart shows "Favorite Fruits", what does the tallest bar represent?',
'{"option1":"Least favorite fruit","option2":"Most popular fruit","option3":"Most expensive fruit","option4":"Largest fruit"}',
'{"correctOption":2}',
'The tallest bar shows the most popular or frequently chosen fruit.',
'APPLICATION', 'HARD', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_miscellaneous, @topic_charts, 'MCQ',
'Why are charts useful in presenting information?',
'{"option1":"They make data harder to understand","option2":"They make information easy to see and compare","option3":"They replace all text","option4":"They are just decorations"}',
'{"correctOption":2}',
'Charts make data visual and easier to understand and compare.',
'UNDERSTANDING', 'MEDIUM', @created_by),

-- Topic: Stock expressions (Topic ID: 1651) - 5 questions
(@board_id, @subject_id, @class_id, @medium, @chapter_miscellaneous, @topic_stock_expressions, 'MCQ',
'What do you say when someone sneezes?',
'{"option1":"Good morning","option2":"Bless you","option3":"Goodbye","option4":"Thank you"}',
'{"correctOption":2}',
'"Bless you" is the common response when someone sneezes.',
'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_miscellaneous, @topic_stock_expressions, 'MCQ',
'What is the polite response to "Thank you"?',
'{"option1":"Goodbye","option2":"Hello","option3":"You''re welcome","option4":"Sorry"}',
'{"correctOption":3}',
'"You''re welcome" is the polite response to thank you.',
'UNDERSTANDING', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_miscellaneous, @topic_stock_expressions, 'MCQ',
'When should you say "Excuse me"?',
'{"option1":"When greeting someone","option2":"When you need to get someone\'s attention or pass by","option3":"When saying goodbye","option4":"When eating food"}',
'{"correctOption":2}',
'"Excuse me" is used to politely get attention or ask to pass.',
'SKILL', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_miscellaneous, @topic_stock_expressions, 'MCQ',
'What is appropriate to say when leaving someone?',
'{"option1":"Hello","option2":"Good morning","option3":"See you later","option4":"How are you"}',
'{"correctOption":3}',
'"See you later" or "Goodbye" are used when leaving.',
'APPLICATION', 'HARD', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_miscellaneous, @topic_stock_expressions, 'MCQ',
'If someone asks "How are you?", you can respond:',
'{"option1":"I am 10 years old","option2":"I am fine, thank you","option3":"My name is Raj","option4":"I live in Mumbai"}',
'{"correctOption":2}',
'"I am fine, thank you" is the appropriate response to "How are you?"',
'UNDERSTANDING', 'MEDIUM', @created_by);

-- =============================================
-- Additional questions to ensure distribution coverage
-- =============================================

-- Additional EASY + APPLICATION combinations
INSERT INTO questions (board_id, subject_id, class_id, medium, chapter_id, topic_id, question_type,
                       question_text, options, correct_answer, answer_explanation,
                       skill_level, difficulty_level, created_by)
VALUES
(@board_id, @subject_id, @class_id, @medium, @chapter_word_games, @topic_puzzles, 'MCQ',
'Complete the pattern: 2, 4, 6, 8, ___',
'{"option1":"9","option2":"10","option3":"11","option4":"12"}',
'{"correctOption":2}',
'The pattern adds 2 each time: 8 + 2 = 10.',
'APPLICATION', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_grammar, @topic_parts_of_speech, 'MCQ',
'Use the noun "play" as a verb in a sentence:',
'{"option1":"I watch a play.","option2":"The play was good.","option3":"I play games.","option4":"A play has actors."}',
'{"correctOption":3}',
'In "I play games", "play" is used as a verb (action).',
'APPLICATION', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_creative_writing, @topic_titles_captions, 'MCQ',
'Give a title to a picture of a cat sleeping:',
'{"option1":"The Running Cat","option2":"Cat\'s Nap Time","option3":"Hungry Cat","option4":"Cat Playing"}',
'{"correctOption":2}',
'"Cat\'s Nap Time" fits a sleeping cat.',
'APPLICATION', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_reading_skills, @topic_passages, 'MCQ',
'Read: "The sun was bright. Birds sang. Flowers bloomed." What type of passage?',
'{"option1":"Narrative","option2":"Descriptive","option3":"Argumentative","option4":"Technical"}',
'{"correctOption":2}',
'It describes a scene, making it descriptive.',
'APPLICATION', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_miscellaneous, @topic_stock_expressions, 'MCQ',
'What should you say before eating?',
'{"option1":"Goodbye","option2":"Good night","option3":"Bon appétit or Enjoy your meal","option4":"See you"}',
'{"correctOption":3}',
'"Bon appétit" or "Enjoy your meal" are said before eating.',
'APPLICATION', 'EASY', @created_by),

-- Additional HARD + KNOWLEDGE combinations
(@board_id, @subject_id, @class_id, @medium, @chapter_word_games, @topic_word_register, 'MCQ',
'Which specialized word belongs to "maritime" register?',
'{"option1":"Telescope","option2":"Anchor","option3":"Microscope","option4":"Stethoscope"}',
'{"correctOption":2}',
'"Anchor" is a maritime (sea/ship-related) term.',
'KNOWLEDGE', 'HARD', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_grammar, @topic_nouns, 'MCQ',
'Which noun is both countable and uncountable depending on context?',
'{"option1":"Water","option2":"Paper","option3":"Book","option4":"Chair"}',
'{"correctOption":2}',
'"Paper" can be uncountable (material) or countable (newspaper, document).',
'KNOWLEDGE', 'HARD', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_creative_writing, @topic_paragraph_writing, 'MCQ',
'What is a topic sentence?',
'{"option1":"Any sentence in the paragraph","option2":"The sentence that states the main idea","option3":"The last sentence","option4":"A question"}',
'{"correctOption":2}',
'A topic sentence states the main idea of the paragraph.',
'KNOWLEDGE', 'HARD', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_reading_skills, @topic_poem, 'MCQ',
'What is alliteration in poetry?',
'{"option1":"Rhyming words","option2":"Repetition of same starting sound","option3":"Long sentences","option4":"Sad poems"}',
'{"correctOption":2}',
'Alliteration is repetition of initial consonant sounds (e.g., "Peter Piper picked").',
'KNOWLEDGE', 'HARD', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_miscellaneous, @topic_charts, 'MCQ',
'What is the advantage of a line graph over a bar chart?',
'{"option1":"It looks prettier","option2":"It shows trends and changes over time better","option3":"It uses less space","option4":"It has no advantage"}',
'{"correctOption":2}',
'Line graphs are better for showing how data changes over time.',
'KNOWLEDGE', 'HARD', @created_by);

COMMIT;
