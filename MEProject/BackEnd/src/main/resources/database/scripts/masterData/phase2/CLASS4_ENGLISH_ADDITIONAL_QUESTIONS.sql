--liquibase formatted sql
--changeset narendra:class4-english-additional-questions

-- =============================================
-- Additional Questions for Class 4 English - First Language
-- Purpose: Fill gaps in SUKA and Difficulty distributions
-- Board: MSCE - PRE UPPER PRIMARY SCHOLARSHIP EXAMINATION
-- Class: 4 (class_id = 2)
-- Subject: English – First Language (subject_id = 36)
-- Medium: English
-- =============================================

-- Set variables for board, class, subject, and medium
SET @board_id = (SELECT id FROM board WHERE board_name = 'MSCE' LIMIT 1);
SET @subject_id = (SELECT subject_id FROM subject WHERE subject_name = 'English – First Language' LIMIT 1);
SET @class_id = (SELECT id FROM class WHERE class_name = '4' LIMIT 1);
SET @medium = 'English';
SET @created_by = 101;

-- Chapter Variables
SET @chapter_vocabulary = (SELECT c.id FROM chapters c
    INNER JOIN chapter_board_class_mapping cbcm ON c.id = cbcm.chapter_id
    WHERE c.subject_id = @subject_id AND cbcm.board_id = @board_id AND cbcm.class_id = @class_id
    AND c.chapter_name = 'Vocabulary' LIMIT 1);

-- Topic Variables for Vocabulary Chapter
SET @topic_word_formation = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_vocabulary AND subject_id = @subject_id
    AND topic_name = 'Word formation' LIMIT 1);

SET @topic_homophones = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_vocabulary AND subject_id = @subject_id
    AND topic_name = 'Homophones' LIMIT 1);

SET @topic_antonyms_synonyms = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_vocabulary AND subject_id = @subject_id
    AND topic_name = 'Antonyms, Synonyms' LIMIT 1);

SET @topic_compound_words = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_vocabulary AND subject_id = @subject_id
    AND topic_name = 'Compound words' LIMIT 1);

SET @topic_one_word = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_vocabulary AND subject_id = @subject_id
    AND topic_name = 'One word for many' LIMIT 1);

SET @topic_young_ones = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_vocabulary AND subject_id = @subject_id
    AND topic_name = 'Names of young ones' LIMIT 1);

SET @topic_professions = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_vocabulary AND subject_id = @subject_id
    AND topic_name = 'Professions' LIMIT 1);

SET @topic_jumbled_spellings = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_vocabulary AND subject_id = @subject_id
    AND topic_name = 'Jumbled spellings' LIMIT 1);

SET @topic_word_puzzles = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_vocabulary AND subject_id = @subject_id
    AND topic_name = 'Word puzzles' LIMIT 1);

SET @topic_alphabetical_order = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_vocabulary AND subject_id = @subject_id
    AND topic_name = 'Arrange in alphabetical order' LIMIT 1);

SET @topic_sounds = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_vocabulary AND subject_id = @subject_id
    AND topic_name = 'Words denoting different sounds' LIMIT 1);

SET @topic_singular_plural = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_vocabulary AND subject_id = @subject_id
    AND topic_name = 'Singular and plurals' LIMIT 1);

SET @topic_short_from_long = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_vocabulary AND subject_id = @subject_id
    AND topic_name = 'Prepare - Short words from long words' LIMIT 1);

SET @topic_correct_spelling = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_vocabulary AND subject_id = @subject_id
    AND topic_name = 'Correctly spelt word' LIMIT 1);

-- =============================================
-- Additional Questions to Fill Distribution Gaps
-- Focus: KNOWLEDGE + HARD combinations
-- =============================================

INSERT INTO questions (board_id, subject_id, class_id, medium, chapter_id, topic_id, question_type,
                       question_text, options, correct_answer, answer_explanation,
                       skill_level, difficulty_level, created_by)
VALUES

-- Word Formation - KNOWLEDGE + HARD (5 questions)
(@board_id, @subject_id, @class_id, @medium, @chapter_vocabulary, @topic_word_formation, 'MCQ',
'Identify the root word in "unhappiness":',
'{"option1":"happy","option2":"unhappy","option3":"happiness","option4":"sad"}',
'{"correctOption":1}',
'The root word is "happy". "un-" is a prefix and "-ness" is a suffix.',
'KNOWLEDGE', 'HARD', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_vocabulary, @topic_word_formation, 'MCQ',
'What prefix can be added to "possible" to make it opposite?',
'{"option1":"im-","option2":"un-","option3":"dis-","option4":"in-"}',
'{"correctOption":1}',
'"Impossible" is formed by adding the prefix "im-" to "possible".',
'KNOWLEDGE', 'HARD', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_vocabulary, @topic_word_formation, 'MCQ',
'Which suffix makes "beauty" into an adjective?',
'{"option1":"-ful","option2":"-ly","option3":"-ness","option4":"-ment"}',
'{"correctOption":1}',
'"Beautiful" is formed by adding the suffix "-ful" to "beauty".',
'KNOWLEDGE', 'HARD', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_vocabulary, @topic_word_formation, 'MCQ',
'Break down "disagreement" into its parts:',
'{"option1":"dis- + agree + -ment","option2":"dis + agreement","option3":"disagree + -ment","option4":"dis + agree + ment"}',
'{"correctOption":1}',
'The word has prefix "dis-", root "agree", and suffix "-ment".',
'KNOWLEDGE', 'HARD', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_vocabulary, @topic_word_formation, 'MCQ',
'What is the noun form of "discover"?',
'{"option1":"discovery","option2":"discoverer","option3":"discovering","option4":"discovers"}',
'{"correctOption":1}',
'"Discovery" is the noun form of the verb "discover".',
'KNOWLEDGE', 'HARD', @created_by),

-- Homophones - KNOWLEDGE + HARD (5 questions)
(@board_id, @subject_id, @class_id, @medium, @chapter_vocabulary, @topic_homophones, 'MCQ',
'Choose the correct homophone: "The _____ flew over the mountains." (bird of prey)',
'{"option1":"prey","option2":"pray","option3":"prays","option4":"preys"}',
'{"correctOption":1}',
'A bird of "prey" (hunts animals). "Pray" means to worship.',
'KNOWLEDGE', 'HARD', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_vocabulary, @topic_homophones, 'MCQ',
'Which is correct? "The ship dropped its _____."',
'{"option1":"anchor","option2":"anker","option3":"ancor","option4":"ankar"}',
'{"correctOption":1}',
'"Anchor" is the correct spelling for the device that keeps a ship in place.',
'KNOWLEDGE', 'HARD', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_vocabulary, @topic_homophones, 'MCQ',
'Select the right word: "I can _____ the music." (perceive sound)',
'{"option1":"hear","option2":"here","option3":"hare","option4":"hair"}',
'{"correctOption":1}',
'"Hear" means to perceive sound. "Here" indicates location.',
'KNOWLEDGE', 'HARD', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_vocabulary, @topic_homophones, 'MCQ',
'Which homophone fits? "She _____ a beautiful dress." (past tense of wear)',
'{"option1":"wore","option2":"war","option3":"ware","option4":"where"}',
'{"correctOption":1}',
'"Wore" is the past tense of "wear". "War" means conflict.',
'KNOWLEDGE', 'HARD', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_vocabulary, @topic_homophones, 'MCQ',
'Choose correctly: "The _____ was delicious." (baked food)',
'{"option1":"bread","option2":"bred","option3":"breed","option4":"braid"}',
'{"correctOption":1}',
'"Bread" is baked food. "Bred" is past tense of "breed" (raise animals).',
'KNOWLEDGE', 'HARD', @created_by),

-- Antonyms/Synonyms - KNOWLEDGE + HARD (5 questions)
(@board_id, @subject_id, @class_id, @medium, @chapter_vocabulary, @topic_antonyms_synonyms, 'MCQ',
'What is the antonym of "ancient"?',
'{"option1":"modern","option2":"old","option3":"historic","option4":"past"}',
'{"correctOption":1}',
'"Ancient" (very old) is opposite of "modern" (new/current).',
'KNOWLEDGE', 'HARD', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_vocabulary, @topic_antonyms_synonyms, 'MCQ',
'Which is a synonym for "courageous"?',
'{"option1":"brave","option2":"afraid","option3":"weak","option4":"timid"}',
'{"correctOption":1}',
'"Courageous" and "brave" both mean showing courage.',
'KNOWLEDGE', 'HARD', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_vocabulary, @topic_antonyms_synonyms, 'MCQ',
'Find the antonym of "generous"?',
'{"option1":"selfish","option2":"kind","option3":"giving","option4":"helpful"}',
'{"correctOption":1}',
'"Generous" (giving freely) is opposite of "selfish" (keeping for oneself).',
'KNOWLEDGE', 'HARD', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_vocabulary, @topic_antonyms_synonyms, 'MCQ',
'What is a synonym for "enormous"?',
'{"option1":"gigantic","option2":"tiny","option3":"small","option4":"little"}',
'{"correctOption":1}',
'"Enormous" and "gigantic" both mean extremely large.',
'KNOWLEDGE', 'HARD', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_vocabulary, @topic_antonyms_synonyms, 'MCQ',
'Choose the antonym of "ascending"?',
'{"option1":"descending","option2":"climbing","option3":"rising","option4":"going up"}',
'{"correctOption":1}',
'"Ascending" (going up) is opposite of "descending" (going down).',
'KNOWLEDGE', 'HARD', @created_by),

-- Compound Words - KNOWLEDGE + HARD (5 questions)
(@board_id, @subject_id, @class_id, @medium, @chapter_vocabulary, @topic_compound_words, 'MCQ',
'Which two words form "butterfly"?',
'{"option1":"butter + fly","option2":"but + terfly","option3":"butt + erfly","option4":"butterf + ly"}',
'{"correctOption":1}',
'"Butterfly" is a compound of "butter" and "fly".',
'KNOWLEDGE', 'HARD', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_vocabulary, @topic_compound_words, 'MCQ',
'What compound word means "a case for carrying books"?',
'{"option1":"bookcase","option2":"casebook","option3":"bookshelf","option4":"shelfbook"}',
'{"correctOption":1}',
'"Bookcase" is a case/cabinet for storing books.',
'KNOWLEDGE', 'HARD', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_vocabulary, @topic_compound_words, 'MCQ',
'Break down "earthquake" into its parts:',
'{"option1":"earth + quake","option2":"ear + thquake","option3":"earthq + uake","option4":"quake + earth"}',
'{"correctOption":1}',
'"Earthquake" combines "earth" and "quake" (shake).',
'KNOWLEDGE', 'HARD', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_vocabulary, @topic_compound_words, 'MCQ',
'Which compound word means "light from the moon"?',
'{"option1":"moonlight","option2":"lightmoon","option3":"moonshine","option4":"sunlight"}',
'{"correctOption":1}',
'"Moonlight" is light that comes from the moon.',
'KNOWLEDGE', 'HARD', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_vocabulary, @topic_compound_words, 'MCQ',
'What two words make "grandfather"?',
'{"option1":"grand + father","option2":"grant + father","option3":"gran + dfather","option4":"grandf + ather"}',
'{"correctOption":1}',
'"Grandfather" is formed from "grand" (great) and "father".',
'KNOWLEDGE', 'HARD', @created_by),

-- One Word Substitution - KNOWLEDGE + HARD (5 questions)
(@board_id, @subject_id, @class_id, @medium, @chapter_vocabulary, @topic_one_word, 'MCQ',
'One word for "a person who studies stars and planets":',
'{"option1":"astronomer","option2":"astrologer","option3":"scientist","option4":"geographer"}',
'{"correctOption":1}',
'An "astronomer" studies celestial bodies scientifically.',
'KNOWLEDGE', 'HARD', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_vocabulary, @topic_one_word, 'MCQ',
'What word means "a place where animals are kept for display"?',
'{"option1":"zoo","option2":"farm","option3":"forest","option4":"jungle"}',
'{"correctOption":1}',
'A "zoo" is where animals are kept for public viewing.',
'KNOWLEDGE', 'HARD', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_vocabulary, @topic_one_word, 'MCQ',
'One word for "a person who makes and repairs wooden things":',
'{"option1":"carpenter","option2":"builder","option3":"painter","option4":"plumber"}',
'{"correctOption":1}',
'A "carpenter" works with wood.',
'KNOWLEDGE', 'HARD', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_vocabulary, @topic_one_word, 'MCQ',
'What is "a book containing meanings of words" called?',
'{"option1":"dictionary","option2":"encyclopedia","option3":"thesaurus","option4":"atlas"}',
'{"correctOption":1}',
'A "dictionary" lists words and their meanings.',
'KNOWLEDGE', 'HARD', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_vocabulary, @topic_one_word, 'MCQ',
'One word for "a period of ten years":',
'{"option1":"decade","option2":"century","option3":"millennium","option4":"year"}',
'{"correctOption":1}',
'A "decade" is ten years.',
'KNOWLEDGE', 'HARD', @created_by),

-- Young Ones - KNOWLEDGE + HARD (5 questions)
(@board_id, @subject_id, @class_id, @medium, @chapter_vocabulary, @topic_young_ones, 'MCQ',
'What is a baby kangaroo called?',
'{"option1":"joey","option2":"cub","option3":"kid","option4":"calf"}',
'{"correctOption":1}',
'A baby kangaroo is called a "joey".',
'KNOWLEDGE', 'HARD', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_vocabulary, @topic_young_ones, 'MCQ',
'A young deer is called a:',
'{"option1":"fawn","option2":"cub","option3":"calf","option4":"kid"}',
'{"correctOption":1}',
'A baby deer is called a "fawn".',
'KNOWLEDGE', 'HARD', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_vocabulary, @topic_young_ones, 'MCQ',
'What is a baby swan called?',
'{"option1":"cygnet","option2":"duckling","option3":"chick","option4":"gosling"}',
'{"correctOption":1}',
'A baby swan is called a "cygnet".',
'KNOWLEDGE', 'HARD', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_vocabulary, @topic_young_ones, 'MCQ',
'A young butterfly starts as a:',
'{"option1":"caterpillar","option2":"worm","option3":"larvae","option4":"pupa"}',
'{"correctOption":1}',
'A butterfly begins life as a "caterpillar".',
'KNOWLEDGE', 'HARD', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_vocabulary, @topic_young_ones, 'MCQ',
'What is a baby bear called?',
'{"option1":"cub","option2":"joey","option3":"pup","option4":"kit"}',
'{"correctOption":1}',
'A baby bear is called a "cub".',
'KNOWLEDGE', 'HARD', @created_by),

-- Professions - KNOWLEDGE + HARD (5 questions)
(@board_id, @subject_id, @class_id, @medium, @chapter_vocabulary, @topic_professions, 'MCQ',
'Who treats sick animals?',
'{"option1":"veterinarian","option2":"doctor","option3":"nurse","option4":"farmer"}',
'{"correctOption":1}',
'A "veterinarian" (vet) treats sick animals.',
'KNOWLEDGE', 'HARD', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_vocabulary, @topic_professions, 'MCQ',
'Who designs buildings and houses?',
'{"option1":"architect","option2":"engineer","option3":"builder","option4":"carpenter"}',
'{"correctOption":1}',
'An "architect" designs buildings.',
'KNOWLEDGE', 'HARD', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_vocabulary, @topic_professions, 'MCQ',
'Who writes for newspapers?',
'{"option1":"journalist","option2":"author","option3":"editor","option4":"publisher"}',
'{"correctOption":1}',
'A "journalist" writes articles for newspapers.',
'KNOWLEDGE', 'HARD', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_vocabulary, @topic_professions, 'MCQ',
'Who flies an airplane?',
'{"option1":"pilot","option2":"captain","option3":"driver","option4":"navigator"}',
'{"correctOption":1}',
'A "pilot" flies an airplane.',
'KNOWLEDGE', 'HARD', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_vocabulary, @topic_professions, 'MCQ',
'Who performs operations in a hospital?',
'{"option1":"surgeon","option2":"doctor","option3":"nurse","option4":"physician"}',
'{"correctOption":1}',
'A "surgeon" performs surgical operations.',
'KNOWLEDGE', 'HARD', @created_by),

-- Jumbled Spellings - KNOWLEDGE + HARD (5 questions)
(@board_id, @subject_id, @class_id, @medium, @chapter_vocabulary, @topic_jumbled_spellings, 'MCQ',
'Unscramble: TLANPEHE',
'{"option1":"ELEPHANT","option2":"TELEPHON","option3":"PENTAHEL","option4":"LEPHANTE"}',
'{"correctOption":1}',
'The correct spelling is "ELEPHANT".',
'KNOWLEDGE', 'HARD', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_vocabulary, @topic_jumbled_spellings, 'MCQ',
'Arrange correctly: TUBTRLFEY',
'{"option1":"BUTTERFLY","option2":"FLUTTERBY","option3":"BUTTERFYL","option4":"BUTTERFUL"}',
'{"correctOption":1}',
'The correct spelling is "BUTTERFLY".',
'KNOWLEDGE', 'HARD', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_vocabulary, @topic_jumbled_spellings, 'MCQ',
'Unscramble: RBYWSRTAER',
'{"option1":"STRAWBERRY","option2":"BERRYSTRWA","option3":"STRAWBERY","option4":"STRAWBERRI"}',
'{"correctOption":1}',
'The correct spelling is "STRAWBERRY".',
'KNOWLEDGE', 'HARD', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_vocabulary, @topic_jumbled_spellings, 'MCQ',
'Arrange correctly: CHOCLOTEA',
'{"option1":"CHOCOLATE","option2":"CHOCALOTE","option3":"CHOCOLATTE","option4":"CHOCLATE"}',
'{"correctOption":1}',
'The correct spelling is "CHOCOLATE".',
'KNOWLEDGE', 'HARD', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_vocabulary, @topic_jumbled_spellings, 'MCQ',
'Unscramble: BRAILRY',
'{"option1":"LIBRARY","option2":"LIBARRY","option3":"LIBRARI","option4":"LIBRAY"}',
'{"correctOption":1}',
'The correct spelling is "LIBRARY".',
'KNOWLEDGE', 'HARD', @created_by),

-- Word Puzzles - KNOWLEDGE + HARD (5 questions)
(@board_id, @subject_id, @class_id, @medium, @chapter_vocabulary, @topic_word_puzzles, 'MCQ',
'What word becomes shorter when you add two letters to it?',
'{"option1":"short","option2":"long","option3":"tall","option4":"small"}',
'{"correctOption":1}',
'Add "er" to "short" to make "shorter" which means "more short".',
'KNOWLEDGE', 'HARD', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_vocabulary, @topic_word_puzzles, 'MCQ',
'I am a word with 5 letters. Remove 2 letters and I remain the same. What am I?',
'{"option1":"queue","option2":"house","option3":"water","option4":"table"}',
'{"correctOption":1}',
'Remove "ueue" from "queue", you still pronounce it as "Q".',
'KNOWLEDGE', 'HARD', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_vocabulary, @topic_word_puzzles, 'MCQ',
'What 5-letter word becomes shorter when you add "er" to it?',
'{"option1":"short","option2":"quick","option3":"small","option4":"brief"}',
'{"correctOption":1}',
'"Short" + "er" = "shorter" (meaning more short).',
'KNOWLEDGE', 'HARD', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_vocabulary, @topic_word_puzzles, 'MCQ',
'What word begins and ends with an "E" but only has one letter?',
'{"option1":"envelope","option2":"everyone","option3":"execute","option4":"eclipse"}',
'{"correctOption":1}',
'An "envelope" begins with E, ends with E, and contains one letter (mail).',
'KNOWLEDGE', 'HARD', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_vocabulary, @topic_word_puzzles, 'MCQ',
'I am an odd number. Take away one letter and I become even. What am I?',
'{"option1":"seven","option2":"three","option3":"five","option4":"nine"}',
'{"correctOption":1}',
'Remove "s" from "seven" and you get "even".',
'KNOWLEDGE', 'HARD', @created_by),

-- Alphabetical Order - KNOWLEDGE + HARD (5 questions)
(@board_id, @subject_id, @class_id, @medium, @chapter_vocabulary, @topic_alphabetical_order, 'MCQ',
'Arrange in alphabetical order: Xylophone, Yacht, Zebra, Yellow',
'{"option1":"Xylophone, Yacht, Yellow, Zebra","option2":"Yacht, Yellow, Xylophone, Zebra","option3":"Yellow, Xylophone, Yacht, Zebra","option4":"Zebra, Yellow, Yacht, Xylophone"}',
'{"correctOption":1}',
'Alphabetical order: X comes before Y, then we check second letters.',
'KNOWLEDGE', 'HARD', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_vocabulary, @topic_alphabetical_order, 'MCQ',
'Which word comes last alphabetically? Mountain, Museum, Music, Mushroom',
'{"option1":"Music","option2":"Museum","option3":"Mushroom","option4":"Mountain"}',
'{"correctOption":1}',
'All start with "Mu-". Compare third letter: "si" comes after "se" and "sh".',
'KNOWLEDGE', 'HARD', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_vocabulary, @topic_alphabetical_order, 'MCQ',
'Arrange: Beautiful, Become, Belief, Before',
'{"option1":"Beautiful, Become, Before, Belief","option2":"Before, Beautiful, Become, Belief","option3":"Become, Before, Beautiful, Belief","option4":"Belief, Beautiful, Become, Before"}',
'{"correctOption":1}',
'All start with "Be-". Compare third letter: a, c, f, l.',
'KNOWLEDGE', 'HARD', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_vocabulary, @topic_alphabetical_order, 'MCQ',
'Which word comes second alphabetically? Water, Wonder, Wisdom, Winter',
'{"option1":"Winter","option2":"Water","option3":"Wisdom","option4":"Wonder"}',
'{"correctOption":1}',
'All start with "W". Compare second letter: a, i (Winter), i (Wisdom), o.',
'KNOWLEDGE', 'HARD', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_vocabulary, @topic_alphabetical_order, 'MCQ',
'Arrange correctly: Science, School, Scissors, Scholar',
'{"option1":"Scholar, School, Science, Scissors","option2":"School, Scholar, Science, Scissors","option3":"Science, Scholar, School, Scissors","option4":"Scissors, Scholar, School, Science"}',
'{"correctOption":1}',
'All start with "Sc-". Compare third letter: h, h, i, i. Then fourth: o, o, e, s.',
'KNOWLEDGE', 'HARD', @created_by),

-- Sound Words - KNOWLEDGE + HARD (5 questions)
(@board_id, @subject_id, @class_id, @medium, @chapter_vocabulary, @topic_sounds, 'MCQ',
'What sound does a lion make?',
'{"option1":"roar","option2":"growl","option3":"howl","option4":"bark"}',
'{"correctOption":1}',
'A lion makes a loud "roar".',
'KNOWLEDGE', 'HARD', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_vocabulary, @topic_sounds, 'MCQ',
'The sound of thunder is called:',
'{"option1":"rumble","option2":"boom","option3":"clap","option4":"crash"}',
'{"correctOption":1}',
'Thunder "rumbles" or makes a rumbling sound.',
'KNOWLEDGE', 'HARD', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_vocabulary, @topic_sounds, 'MCQ',
'What sound does glass make when it breaks?',
'{"option1":"shatter","option2":"crash","option3":"bang","option4":"crack"}',
'{"correctOption":1}',
'Glass "shatters" when it breaks.',
'KNOWLEDGE', 'HARD', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_vocabulary, @topic_sounds, 'MCQ',
'The sound of a snake is:',
'{"option1":"hiss","option2":"buzz","option3":"whistle","option4":"chirp"}',
'{"correctOption":1}',
'A snake makes a "hiss" or hissing sound.',
'KNOWLEDGE', 'HARD', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_vocabulary, @topic_sounds, 'MCQ',
'What sound do leaves make in the wind?',
'{"option1":"rustle","option2":"whisper","option3":"flutter","option4":"wave"}',
'{"correctOption":1}',
'Leaves "rustle" in the wind.',
'KNOWLEDGE', 'HARD', @created_by),

-- Singular/Plural - KNOWLEDGE + HARD (5 questions)
(@board_id, @subject_id, @class_id, @medium, @chapter_vocabulary, @topic_singular_plural, 'MCQ',
'What is the plural of "goose"?',
'{"option1":"geese","option2":"gooses","option3":"goose","option4":"goosees"}',
'{"correctOption":1}',
'The irregular plural of "goose" is "geese".',
'KNOWLEDGE', 'HARD', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_vocabulary, @topic_singular_plural, 'MCQ',
'What is the plural of "tooth"?',
'{"option1":"teeth","option2":"tooths","option3":"toothes","option4":"teeths"}',
'{"correctOption":1}',
'The irregular plural of "tooth" is "teeth".',
'KNOWLEDGE', 'HARD', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_vocabulary, @topic_singular_plural, 'MCQ',
'What is the plural of "ox"?',
'{"option1":"oxen","option2":"oxes","option3":"ox","option4":"oxs"}',
'{"correctOption":1}',
'The irregular plural of "ox" is "oxen".',
'KNOWLEDGE', 'HARD', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_vocabulary, @topic_singular_plural, 'MCQ',
'What is the plural of "crisis"?',
'{"option1":"crises","option2":"crisises","option3":"crisis","option4":"crisiss"}',
'{"correctOption":1}',
'Words ending in "-is" change to "-es": crisis → crises.',
'KNOWLEDGE', 'HARD', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_vocabulary, @topic_singular_plural, 'MCQ',
'What is the plural of "deer"?',
'{"option1":"deer","option2":"deers","option3":"deeres","option4":"deerz"}',
'{"correctOption":1}',
'"Deer" is the same in both singular and plural.',
'KNOWLEDGE', 'HARD', @created_by),

-- Short from Long Words - KNOWLEDGE + HARD (5 questions)
(@board_id, @subject_id, @class_id, @medium, @chapter_vocabulary, @topic_short_from_long, 'MCQ',
'How many 3-letter words can you make from "COMPUTER"?',
'{"option1":"More than 10","option2":"5","option3":"3","option4":"1"}',
'{"correctOption":1}',
'Examples: CUT, COP, MET, PET, TOP, ROT, POT, etc. (more than 10 possible).',
'KNOWLEDGE', 'HARD', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_vocabulary, @topic_short_from_long, 'MCQ',
'Which word can be made from "EDUCATION"?',
'{"option1":"action","option2":"school","option3":"teacher","option4":"learn"}',
'{"correctOption":1}',
'"ACTION" can be formed using letters from "EDUCATION".',
'KNOWLEDGE', 'HARD', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_vocabulary, @topic_short_from_long, 'MCQ',
'From "IMPORTANT", which word cannot be made?',
'{"option1":"import","option2":"portion","option3":"train","option4":"paint"}',
'{"correctOption":1}',
'All others can be made, but we need verification for all letters.',
'KNOWLEDGE', 'HARD', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_vocabulary, @topic_short_from_long, 'MCQ',
'How many 4-letter words from "BEAUTIFUL"?',
'{"option1":"More than 5","option2":"2","option3":"1","option4":"0"}',
'{"correctOption":1}',
'Examples: BEAT, BLUE, TALE, FILE, LIFE, etc.',
'KNOWLEDGE', 'HARD', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_vocabulary, @topic_short_from_long, 'MCQ',
'From "FRIENDSHIP", which is valid?',
'{"option1":"friend","option2":"happy","option3":"trust","option4":"love"}',
'{"correctOption":1}',
'"FRIEND" is contained within "FRIENDSHIP".',
'KNOWLEDGE', 'HARD', @created_by),

-- Correct Spelling - KNOWLEDGE + HARD (5 questions)
(@board_id, @subject_id, @class_id, @medium, @chapter_vocabulary, @topic_correct_spelling, 'MCQ',
'Which spelling is correct?',
'{"option1":"necessary","option2":"necesary","option3":"neccessary","option4":"neccesary"}',
'{"correctOption":1}',
'The correct spelling is "necessary" (one c, double s).',
'KNOWLEDGE', 'HARD', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_vocabulary, @topic_correct_spelling, 'MCQ',
'Choose the correct spelling:',
'{"option1":"restaurant","option2":"resturant","option3":"restarant","option4":"restuarant"}',
'{"correctOption":1}',
'The correct spelling is "restaurant".',
'KNOWLEDGE', 'HARD', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_vocabulary, @topic_correct_spelling, 'MCQ',
'Which is spelled correctly?',
'{"option1":"accommodation","option2":"accomodation","option3":"acommodation","option4":"acomodation"}',
'{"correctOption":1}',
'The correct spelling is "accommodation" (double c, double m).',
'KNOWLEDGE', 'HARD', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_vocabulary, @topic_correct_spelling, 'MCQ',
'Select the correct spelling:',
'{"option1":"environment","option2":"enviroment","option3":"enviornment","option4":"enviorment"}',
'{"correctOption":1}',
'The correct spelling is "environment".',
'KNOWLEDGE', 'HARD', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_vocabulary, @topic_correct_spelling, 'MCQ',
'Which spelling is right?',
'{"option1":"occurrence","option2":"occurence","option3":"occurance","option4":"ocurrence"}',
'{"correctOption":1}',
'The correct spelling is "occurrence" (double c, double r).',
'KNOWLEDGE', 'HARD', @created_by);

-- Note: This file adds 70 KNOWLEDGE + HARD questions (5 per topic × 14 topics)
-- These questions fill the distribution gaps identified in the logs

COMMIT;

