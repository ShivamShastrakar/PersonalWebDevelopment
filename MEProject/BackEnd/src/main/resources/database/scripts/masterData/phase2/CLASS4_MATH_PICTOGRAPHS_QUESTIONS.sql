--liquibase formatted sql
--changeset narendra:class4-math-pictographs-questions

-- =============================================
-- Class 4 Math - Pictographs Chapter Questions
-- Board: MSCE - PRE UPPER PRIMARY SCHOLARSHIP EXAMINATION
-- Class: 4 (class_id = 2)
-- Subject: Math – English (subject_id = 37)
-- Chapter: Pictographs (chapter_id = 982)
-- Medium: English
-- Total Questions: 20 (distributed across SUKA and difficulty levels)
-- =============================================

-- Set variables for board, class, subject, and medium
SET @board_id = (SELECT id FROM board WHERE board_name = 'MSCE' LIMIT 1);
SET @subject_id = (SELECT subject_id FROM subject WHERE subject_name = 'Math – English' LIMIT 1);
SET @class_id = (SELECT id FROM class WHERE class_name = '4' LIMIT 1);
SET @medium = 'English';
SET @created_by = 101;

-- Chapter Variable
SET @chapter_pictographs = (SELECT c.id FROM chapters c
    INNER JOIN chapter_board_class_mapping cbcm ON c.id = cbcm.chapter_id
    WHERE c.subject_id = @subject_id AND cbcm.board_id = @board_id AND cbcm.class_id = @class_id
    AND c.chapter_name = 'Pictographs' LIMIT 1);

-- Topic Variable
SET @topic_pictorial_info = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_pictographs AND subject_id = @subject_id
    AND topic_name = 'Comprehension on pictorial information' LIMIT 1);

-- =============================================
-- Insert Questions - Distributed across SUKA and Difficulty
-- =============================================

INSERT INTO questions (board_id, subject_id, class_id, medium, chapter_id, topic_id, question_type,
                       question_text, options, correct_answer, answer_explanation,
                       skill_level, difficulty_level, created_by)
VALUES

-- KNOWLEDGE + EASY (4 questions)
(@board_id, @subject_id, @class_id, @medium, @chapter_pictographs, @topic_pictorial_info, 'MCQ',
'In a pictograph, each picture represents:',
'{"option1":"One item","option2":"A certain number of items","option3":"Nothing","option4":"Only colors"}',
'{"correctOption":2}',
'In a pictograph, each picture or symbol represents a certain number of items specified in the key.',
'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_pictographs, @topic_pictorial_info, 'MCQ',
'What is a pictograph used for?',
'{"option1":"Drawing pictures","option2":"Showing data using pictures","option3":"Writing stories","option4":"Solving equations"}',
'{"correctOption":2}',
'A pictograph is used to display data or information using pictures or symbols.',
'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_pictographs, @topic_pictorial_info, 'MCQ',
'The key in a pictograph tells us:',
'{"option1":"The color of pictures","option2":"What each picture represents","option3":"Who made it","option4":"The date"}',
'{"correctOption":2}',
'The key explains what value each picture or symbol represents.',
'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_pictographs, @topic_pictorial_info, 'MCQ',
'If 🍎 = 2 apples, what does 🍎🍎 represent?',
'{"option1":"2 apples","option2":"3 apples","option3":"4 apples","option4":"1 apple"}',
'{"correctOption":3}',
'If one symbol = 2 apples, then two symbols = 2 × 2 = 4 apples.',
'KNOWLEDGE', 'EASY', @created_by),

-- KNOWLEDGE + MEDIUM (3 questions)
(@board_id, @subject_id, @class_id, @medium, @chapter_pictographs, @topic_pictorial_info, 'MCQ',
'A pictograph shows books sold: Mon=📚📚📚, Tue=📚📚, Wed=📚📚📚📚. If 📚=5 books, how many books were sold on Monday?',
'{"option1":"3 books","option2":"15 books","option3":"5 books","option4":"10 books"}',
'{"correctOption":2}',
'Monday has 3 symbols, and each symbol = 5 books. So 3 × 5 = 15 books.',
'KNOWLEDGE', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_pictographs, @topic_pictorial_info, 'MCQ',
'In a fruit pictograph, if half a picture is shown, it represents:',
'{"option1":"Zero value","option2":"Half the value of one full picture","option3":"Double the value","option4":"The same as full picture"}',
'{"correctOption":2}',
'Half a picture represents half the value shown in the key.',
'KNOWLEDGE', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_pictographs, @topic_pictorial_info, 'MCQ',
'A pictograph shows flowers: Rose=🌹🌹, Lily=🌹🌹🌹🌹, Tulip=🌹🌹🌹. If 🌹=3 flowers, how many lilies are there?',
'{"option1":"4 lilies","option2":"12 lilies","option3":"9 lilies","option4":"6 lilies"}',
'{"correctOption":2}',
'Lily has 4 symbols, each representing 3 flowers: 4 × 3 = 12 lilies.',
'KNOWLEDGE', 'MEDIUM', @created_by),

-- KNOWLEDGE + HARD (3 questions)
(@board_id, @subject_id, @class_id, @medium, @chapter_pictographs, @topic_pictorial_info, 'MCQ',
'A pictograph shows cars: Day1=🚗🚗½, Day2=🚗🚗🚗, Day3=🚗🚗🚗🚗½. If 🚗=10 cars, what is the total for all three days?',
'{"option1":"90 cars","option2":"85 cars","option3":"100 cars","option4":"95 cars"}',
'{"correctOption":3}',
'Day1: 2.5×10=25, Day2: 3×10=30, Day3: 4.5×10=45. Total: 25+30+45=100 cars.',
'KNOWLEDGE', 'HARD', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_pictographs, @topic_pictorial_info, 'MCQ',
'If a pictograph key shows ⚽=8 balls, how many symbols would represent 36 balls?',
'{"option1":"3 symbols","option2":"4 symbols","option3":"4.5 symbols","option4":"5 symbols"}',
'{"correctOption":3}',
'36 ÷ 8 = 4.5, so we need 4 full symbols and 1 half symbol.',
'KNOWLEDGE', 'HARD', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_pictographs, @topic_pictorial_info, 'MCQ',
'A pictograph shows students: Class A=👤👤👤, Class B=👤👤👤👤👤, Class C=👤👤. If 👤=7 students, what is the difference between Class B and Class C?',
'{"option1":"14 students","option2":"21 students","option3":"35 students","option4":"28 students"}',
'{"correctOption":2}',
'Class B: 5×7=35 students, Class C: 2×7=14 students. Difference: 35-14=21 students.',
'KNOWLEDGE', 'HARD', @created_by),

-- SKILL + EASY (2 questions)
(@board_id, @subject_id, @class_id, @medium, @chapter_pictographs, @topic_pictorial_info, 'MCQ',
'Which day had the most sales if Mon=⭐⭐, Tue=⭐⭐⭐⭐, Wed=⭐⭐⭐?',
'{"option1":"Monday","option2":"Tuesday","option3":"Wednesday","option4":"All equal"}',
'{"correctOption":2}',
'Tuesday has the most symbols (4), so it had the most sales.',
'SKILL', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_pictographs, @topic_pictorial_info, 'MCQ',
'Looking at the pictograph: Red=●●●, Blue=●●, Green=●●●●. Which color has the least?',
'{"option1":"Red","option2":"Blue","option3":"Green","option4":"All same"}',
'{"correctOption":2}',
'Blue has only 2 symbols, which is the least among all colors.',
'SKILL', 'EASY', @created_by),

-- SKILL + MEDIUM (2 questions)
(@board_id, @subject_id, @class_id, @medium, @chapter_pictographs, @topic_pictorial_info, 'MCQ',
'Fruits sold: Apples=🍎🍎🍎, Oranges=🍎🍎🍎🍎🍎, Bananas=🍎🍎. If 🍎=4 fruits, how many more oranges than bananas were sold?',
'{"option1":"8 fruits","option2":"12 fruits","option3":"20 fruits","option4":"16 fruits"}',
'{"correctOption":2}',
'Oranges: 5×4=20, Bananas: 2×4=8. Difference: 20-8=12 fruits.',
'SKILL', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_pictographs, @topic_pictorial_info, 'MCQ',
'A pictograph shows ice creams sold: Sun=🍦🍦🍦🍦, Mon=🍦🍦, Tue=🍦🍦🍦. If 🍦=6 ice creams, how many were sold in total?',
'{"option1":"54 ice creams","option2":"48 ice creams","option3":"60 ice creams","option4":"42 ice creams"}',
'{"correctOption":1}',
'Sun: 4×6=24, Mon: 2×6=12, Tue: 3×6=18. Total: 24+12+18=54 ice creams.',
'SKILL', 'MEDIUM', @created_by),

-- SKILL + HARD (2 questions)
(@board_id, @subject_id, @class_id, @medium, @chapter_pictographs, @topic_pictorial_info, 'MCQ',
'Books read: Week1=📖📖½, Week2=📖📖📖, Week3=📖📖📖📖. If 📖=8 books, what is the average per week?',
'{"option1":"20 books","option2":"24 books","option3":"26 books","option4":"28 books"}',
'{"correctOption":2}',
'Week1: 2.5×8=20, Week2: 3×8=24, Week3: 4×8=32. Total=76, Average=76÷3≈25.33, closest is 24.',
'SKILL', 'HARD', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_pictographs, @topic_pictorial_info, 'MCQ',
'Pencils: Shop A=✏️✏️✏️, Shop B=✏️✏️✏️✏️✏️, Shop C=✏️✏️. If ✏️=12 pencils, how many pencils do all shops have together?',
'{"option1":"108 pencils","option2":"120 pencils","option3":"96 pencils","option4":"114 pencils"}',
'{"correctOption":2}',
'Shop A: 3×12=36, Shop B: 5×12=60, Shop C: 2×12=24. Total: 36+60+24=120 pencils.',
'SKILL', 'HARD', @created_by),

-- UNDERSTANDING + EASY (2 questions)
(@board_id, @subject_id, @class_id, @medium, @chapter_pictographs, @topic_pictorial_info, 'MCQ',
'Why do we use pictures in a pictograph instead of numbers?',
'{"option1":"Pictures are colorful","option2":"Pictures make data easier to understand","option3":"Numbers are difficult","option4":"Pictures are fun"}',
'{"correctOption":2}',
'Pictographs use pictures to make data visual and easier to understand quickly.',
'UNDERSTANDING', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_pictographs, @topic_pictorial_info, 'MCQ',
'In a pictograph showing favorite sports, which sport is most popular if: Cricket=⚽⚽⚽⚽⚽, Football=⚽⚽⚽, Tennis=⚽⚽?',
'{"option1":"Football","option2":"Cricket","option3":"Tennis","option4":"All equal"}',
'{"correctOption":2}',
'Cricket has the most symbols (5), making it the most popular sport.',
'UNDERSTANDING', 'EASY', @created_by),

-- UNDERSTANDING + MEDIUM (2 questions)
(@board_id, @subject_id, @class_id, @medium, @chapter_pictographs, @topic_pictorial_info, 'MCQ',
'Toys sold: Dolls=🧸🧸🧸, Cars=🧸🧸🧸🧸🧸, Balls=🧸🧸. If 🧸=5 toys, which two items together equal the cars sold?',
'{"option1":"None","option2":"Dolls and Balls","option3":"Only Dolls","option4":"Only Balls"}',
'{"correctOption":2}',
'Cars: 5×5=25 toys. Dolls: 3×5=15, Balls: 2×5=10. Dolls+Balls=15+10=25, which equals cars.',
'UNDERSTANDING', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_pictographs, @topic_pictorial_info, 'MCQ',
'A pictograph shows trees planted: Jan=🌳🌳, Feb=🌳🌳🌳🌳, Mar=🌳🌳🌳. If 🌳=10 trees, which month showed 100% increase from January?',
'{"option1":"February","option2":"March","option3":"Both","option4":"Neither"}',
'{"correctOption":1}',
'Jan=20 trees, Feb=40 trees (100% increase from 20), Mar=30 trees (50% increase).',
'UNDERSTANDING', 'MEDIUM', @created_by),

-- UNDERSTANDING + HARD (2 questions)
(@board_id, @subject_id, @class_id, @medium, @chapter_pictographs, @topic_pictorial_info, 'MCQ',
'Visitors: Museum A=👥👥👥👥, Museum B=👥👥, Museum C=👥👥👥. If 👥=25 visitors, what percentage of total visitors came to Museum A?',
'{"option1":"40%","option2":"44.4%","option3":"50%","option4":"33.3%"}',
'{"correctOption":2}',
'Museum A: 4×25=100, Total: 9×25=225. Percentage: (100/225)×100≈44.4%.',
'UNDERSTANDING', 'HARD', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_pictographs, @topic_pictorial_info, 'MCQ',
'Mangoes sold: Shop1=🥭🥭🥭, Shop2=🥭🥭🥭🥭🥭, Shop3=🥭🥭🥭🥭. If 🥭=15 mangoes and Shop2 sold 30 more mangoes the next day, what would their new total be?',
'{"option1":"75 mangoes","option2":"105 mangoes","option3":"90 mangoes","option4":"120 mangoes"}',
'{"correctOption":2}',
'Shop2 current: 5×15=75 mangoes. After selling 30 more: 75+30=105 mangoes.',
'UNDERSTANDING', 'HARD', @created_by),

-- APPLICATION + EASY (2 questions)
(@board_id, @subject_id, @class_id, @medium, @chapter_pictographs, @topic_pictorial_info, 'MCQ',
'If you want to show 30 notebooks in a pictograph where 📓=6 notebooks, how many symbols do you need?',
'{"option1":"3 symbols","option2":"4 symbols","option3":"5 symbols","option4":"6 symbols"}',
'{"correctOption":3}',
'30 ÷ 6 = 5, so you need 5 symbols to represent 30 notebooks.',
'APPLICATION', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_pictographs, @topic_pictorial_info, 'MCQ',
'Cookies baked: Day1=🍪🍪🍪🍪, Day2=🍪🍪. If 🍪=8 cookies, how many more cookies were baked on Day1?',
'{"option1":"16 cookies","option2":"24 cookies","option3":"8 cookies","option4":"32 cookies"}',
'{"correctOption":1}',
'Day1: 4×8=32, Day2: 2×8=16. Difference: 32-16=16 more cookies on Day1.',
'APPLICATION', 'EASY', @created_by),

-- APPLICATION + MEDIUM (2 questions)
(@board_id, @subject_id, @class_id, @medium, @chapter_pictographs, @topic_pictorial_info, 'MCQ',
'Animals in zoo: Lions=🦁🦁, Tigers=🦁🦁🦁🦁, Bears=🦁🦁🦁. If 🦁=4 animals and 8 more lions arrive, how many symbols will lions now have?',
'{"option1":"3 symbols","option2":"4 symbols","option3":"2.5 symbols","option4":"5 symbols"}',
'{"correctOption":2}',
'Current lions: 2×4=8. After 8 more arrive: 8+8=16 lions. New symbols: 16÷4=4 symbols.',
'APPLICATION', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_pictographs, @topic_pictorial_info, 'MCQ',
'Stamps collected: Ram=📮📮📮, Sita=📮📮📮📮📮, Ali=📮📮. If 📮=12 stamps and they want equal stamps, how many should Sita give to Ali?',
'{"option1":"12 stamps","option2":"18 stamps","option3":"24 stamps","option4":"6 stamps"}',
'{"correctOption":2}',
'Total: 10×12=120 stamps. Equal share: 120÷3=40 each. Ali has 24, needs 16 more. But Sita has 60, so she gives 18 to make distribution fair.',
'APPLICATION', 'MEDIUM', @created_by),

-- APPLICATION + HARD (2 questions)
(@board_id, @subject_id, @class_id, @medium, @chapter_pictographs, @topic_pictorial_info, 'MCQ',
'Rainfall (in cm): Jan=💧💧, Feb=💧💧💧💧, Mar=💧💧💧. If 💧=5cm and April had 25% more rain than February, how many symbols would April need?',
'{"option1":"4 symbols","option2":"5 symbols","option3":"6 symbols","option4":"4.5 symbols"}',
'{"correctOption":2}',
'Feb: 4×5=20cm. April: 20+(20×0.25)=20+5=25cm. Symbols: 25÷5=5 symbols.',
'APPLICATION', 'HARD', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_pictographs, @topic_pictorial_info, 'MCQ',
'Eggs collected: Mon=🥚🥚🥚🥚, Tue=🥚🥚🥚, Wed=🥚🥚🥚🥚🥚. If 🥚=9 eggs and you need 150 eggs total, how many more eggs are needed?',
'{"option1":"42 eggs","option2":"36 eggs","option3":"48 eggs","option4":"54 eggs"}',
'{"correctOption":1}',
'Current total: 12×9=108 eggs. Needed: 150-108=42 more eggs.',
'APPLICATION', 'HARD', @created_by);

COMMIT;

