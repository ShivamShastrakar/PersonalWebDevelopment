--liquibase formatted sql
--changeset {narendra}:{id}

-- ============================================================================
-- MSCE CLASS 5 MATHEMATICS – 100 MCQ QUESTIONS
-- Board: MSCE - PRE UPPER PRIMARY SCHOLARSHIP EXAMINATION
-- Subject: Math – English (subject_id: 37)
-- Class: 4
-- Medium: English
-- ============================================================================

-- Variable Declarations
SET @board_id = (SELECT id FROM board WHERE board_name = 'MSCE' LIMIT 1);
SET @subject_id = (SELECT subject_id FROM subject WHERE subject_name = 'Math – English' LIMIT 1);
SET @class_id = (SELECT id FROM class WHERE class_name = '4' LIMIT 1);
SET @medium = 'English';
SET @created_by = 101;

-- Chapter Variables (7 chapters for Class 5 Math)
-- Dynamically fetch chapter IDs based on subject_id and board_id
SET @chapter_knowledge_numbers = (SELECT c.id FROM chapters c
    INNER JOIN chapter_board_class_mapping cbcm ON c.id = cbcm.chapter_id
    WHERE c.subject_id = @subject_id AND cbcm.board_id = @board_id AND cbcm.class_id = @class_id
    AND c.chapter_name = 'Knowledge of Numbers' LIMIT 1);

SET @chapter_operations = (SELECT c.id FROM chapters c
    INNER JOIN chapter_board_class_mapping cbcm ON c.id = cbcm.chapter_id
    WHERE c.subject_id = @subject_id AND cbcm.board_id = @board_id AND cbcm.class_id = @class_id
    AND c.chapter_name = 'Operations on Numbers' LIMIT 1);

SET @chapter_fractions = (SELECT c.id FROM chapters c
    INNER JOIN chapter_board_class_mapping cbcm ON c.id = cbcm.chapter_id
    WHERE c.subject_id = @subject_id AND cbcm.board_id = @board_id AND cbcm.class_id = @class_id
    AND c.chapter_name = 'Fractions' LIMIT 1);

SET @chapter_measurement = (SELECT c.id FROM chapters c
    INNER JOIN chapter_board_class_mapping cbcm ON c.id = cbcm.chapter_id
    WHERE c.subject_id = @subject_id AND cbcm.board_id = @board_id AND cbcm.class_id = @class_id
    AND c.chapter_name = 'Measurement / Mensuration' LIMIT 1);

SET @chapter_patterns = (SELECT c.id FROM chapters c
    INNER JOIN chapter_board_class_mapping cbcm ON c.id = cbcm.chapter_id
    WHERE c.subject_id = @subject_id AND cbcm.board_id = @board_id AND cbcm.class_id = @class_id
    AND c.chapter_name = 'Patterns' LIMIT 1);

SET @chapter_geometry = (SELECT c.id FROM chapters c
    INNER JOIN chapter_board_class_mapping cbcm ON c.id = cbcm.chapter_id
    WHERE c.subject_id = @subject_id AND cbcm.board_id = @board_id AND cbcm.class_id = @class_id
    AND c.chapter_name = 'Geometry' LIMIT 1);

SET @chapter_pictographs = (SELECT c.id FROM chapters c
    INNER JOIN chapter_board_class_mapping cbcm ON c.id = cbcm.chapter_id
    WHERE c.subject_id = @subject_id AND cbcm.board_id = @board_id AND cbcm.class_id = @class_id
    AND c.chapter_name = 'Pictographs' LIMIT 1);

-- Topic Variables (37 topics)
-- Dynamically fetch topic IDs based on chapter_id and subject_id
SET @topic_reading_writing_numerals = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_knowledge_numbers AND subject_id = @subject_id
    AND topic_name = 'Reading and writing of International numerals' LIMIT 1);

SET @topic_numbers_five_digits = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_knowledge_numbers AND subject_id = @subject_id
    AND topic_name = 'Reading and writing numbers up to five digits' LIMIT 1);

SET @topic_face_place_value = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_knowledge_numbers AND subject_id = @subject_id
    AND topic_name = 'Face value, place value of a digit and expanded form of a number' LIMIT 1);

SET @topic_smallest_greatest = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_knowledge_numbers AND subject_id = @subject_id
    AND topic_name = 'The smallest and greatest numbers from given digits' LIMIT 1);

SET @topic_ascending_descending = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_knowledge_numbers AND subject_id = @subject_id
    AND topic_name = 'Ascending and descending order of numbers and comparison' LIMIT 1);

SET @topic_numbers_1_to_100 = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_knowledge_numbers AND subject_id = @subject_id
    AND topic_name = 'Questions based on numbers from 1 to 100' LIMIT 1);

SET @topic_even_odd = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_knowledge_numbers AND subject_id = @subject_id
    AND topic_name = 'Even and odd numbers' LIMIT 1);

SET @topic_prime_composite = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_knowledge_numbers AND subject_id = @subject_id
    AND topic_name = '1 to 100, prime and composite numbers, triangular and square numbers' LIMIT 1);

SET @topic_addition = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_operations AND subject_id = @subject_id
    AND topic_name = 'Addition (up to five digit numbers) with carrying, word problems' LIMIT 1);

SET @topic_subtraction = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_operations AND subject_id = @subject_id
    AND topic_name = 'Subtraction (up to five digit numbers) by borrowing, word problems' LIMIT 1);

SET @topic_multiplication = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_operations AND subject_id = @subject_id
    AND topic_name = 'Multiplication (up to three digit number by two digit number) word problems' LIMIT 1);

SET @topic_division = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_operations AND subject_id = @subject_id
    AND topic_name = 'Division (up to three digit number by two digit number) word problems' LIMIT 1);

SET @topic_fraction_meaning = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_fractions AND subject_id = @subject_id
    AND topic_name = 'Reading, writing meaning of Fraction' LIMIT 1);

SET @topic_like_fractions = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_fractions AND subject_id = @subject_id
    AND topic_name = 'Fractions with equal denominator (like fractions)' LIMIT 1);

SET @topic_unlike_fractions = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_fractions AND subject_id = @subject_id
    AND topic_name = 'Fraction with unequal denominator (unlike fractions)' LIMIT 1);

SET @topic_comparing_fractions = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_fractions AND subject_id = @subject_id
    AND topic_name = 'Order relation (comparing Fractions), Ascending and Descending order' LIMIT 1);

SET @topic_proper_improper = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_fractions AND subject_id = @subject_id
    AND topic_name = 'Proper, Improper and Mixed fraction, their conversion an comparison' LIMIT 1);

SET @topic_length_mass_capacity = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_measurement AND subject_id = @subject_id
    AND topic_name = 'Length, mass, capacity metric measures conversion of units, addition, subtraction and word problems' LIMIT 1);

SET @topic_time_measurement = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_measurement AND subject_id = @subject_id
    AND topic_name = 'Measuring time ante meridiem and post meridiem. Hours, minutes and seconds-conversion' LIMIT 1);

SET @topic_calendar = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_measurement AND subject_id = @subject_id
    AND topic_name = 'The Calendar' LIMIT 1);

SET @topic_paper_measurement = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_measurement AND subject_id = @subject_id
    AND topic_name = 'Rim, Gross (Paper measurement)' LIMIT 1);

SET @topic_money = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_measurement AND subject_id = @subject_id
    AND topic_name = 'Coins and Currency Notes, Rupees-Paise Conversion. Word problems based on basic operations' LIMIT 1);

SET @topic_geometric_shapes_pattern = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_patterns AND subject_id = @subject_id
    AND topic_name = 'Geometric Shapes' LIMIT 1);

SET @topic_numbers_pattern = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_patterns AND subject_id = @subject_id
    AND topic_name = 'Numbers' LIMIT 1);

SET @topic_freehand_shapes = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_patterns AND subject_id = @subject_id
    AND topic_name = 'Freehand shapes' LIMIT 1);

SET @topic_angles = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_geometry AND subject_id = @subject_id
    AND topic_name = 'Angles - Types of Angles (Right angle, acute angles and obtuse angles)' LIMIT 1);

SET @topic_symmetry = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_geometry AND subject_id = @subject_id
    AND topic_name = 'Symmetry' LIMIT 1);

SET @topic_triangle_square_rectangle = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_geometry AND subject_id = @subject_id
    AND topic_name = 'Triangle, Square, Rectangle - sides and Vertices' LIMIT 1);

SET @topic_circle = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_geometry AND subject_id = @subject_id
    AND topic_name = 'Circle-radius, chord, diameter, centre, circumference, the interior, the exterior' LIMIT 1);

SET @topic_perimeter = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_geometry AND subject_id = @subject_id
    AND topic_name = 'Perimeter - Triangle, Rectangle, Square' LIMIT 1);

SET @topic_area = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_geometry AND subject_id = @subject_id
    AND topic_name = 'Area - Rectangle, Square' LIMIT 1);

SET @topic_3d_objects = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_geometry AND subject_id = @subject_id
    AND topic_name = 'Three dimensional objects and Nets' LIMIT 1);

SET @topic_cone_cylinder_sphere = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_geometry AND subject_id = @subject_id
    AND topic_name = 'Cone, Cylinder, Sphere. (edges, corners)' LIMIT 1);

SET @topic_cube_cuboid = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_geometry AND subject_id = @subject_id
    AND topic_name = 'Cube and Cuboid (Edges, Vertices, Faces)' LIMIT 1);

SET @topic_pictograph_comprehension = (SELECT topic_id FROM topics
    WHERE chapter_id = @chapter_pictographs AND subject_id = @subject_id
    AND topic_name = 'Comprehension on pictorial information' LIMIT 1);

-- ============================================================================
-- 100 MCQ QUESTIONS
-- Distribution across 7 chapters and 37 topics
-- ============================================================================

INSERT INTO questions (
    board_id, subject_id, class_id, medium, chapter_id, topic_id,
    question_type, question_text, options, correct_answer, answer_explanation,
    skill_level, difficulty_level, created_by
) VALUES

-- ============================================================================
-- CHAPTER 1: Knowledge of Numbers (15 questions)
-- ============================================================================

-- Topic: Reading and writing of International numerals (2 questions)
(@board_id, @subject_id, @class_id, @medium, @chapter_knowledge_numbers, @topic_reading_writing_numerals, 'MCQ',
'Write 2,345 in words:',
'{"option1":"Two thousand three hundred forty five","option2":"Twenty three thousand forty five","option3":"Two hundred thirty four five","option4":"Two three four five"}',
'{"correctOption":1}',
'2,345 is written as "Two thousand three hundred forty five".',
'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_knowledge_numbers, @topic_reading_writing_numerals, 'MCQ',
'What is the numeral for "Five thousand six hundred twenty-one"?',
'{"option1":"5,621","option2":"5,612","option3":"5,261","option4":"50,621"}',
'{"correctOption":1}',
'Five thousand six hundred twenty-one = 5,621.',
'KNOWLEDGE', 'EASY', @created_by),

-- Topic: Reading and writing numbers up to five digits (2 questions)
(@board_id, @subject_id, @class_id, @medium, @chapter_knowledge_numbers, @topic_numbers_five_digits, 'MCQ',
'Which is the largest five-digit number?',
'{"option1":"10,000","option2":"99,999","option3":"100,000","option4":"9,999"}',
'{"correctOption":2}',
'The largest five-digit number is 99,999.',
'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_knowledge_numbers, @topic_numbers_five_digits, 'MCQ',
'What is the smallest five-digit number?',
'{"option1":"9,999","option2":"1,000","option3":"10,000","option4":"11,111"}',
'{"correctOption":3}',
'The smallest five-digit number is 10,000.',
'KNOWLEDGE', 'EASY', @created_by),

-- Topic: Face value, place value and expanded form (3 questions)
(@board_id, @subject_id, @class_id, @medium, @chapter_knowledge_numbers, @topic_face_place_value, 'MCQ',
'What is the place value of 7 in 67,345?',
'{"option1":"7","option2":"70","option3":"700","option4":"7,000"}',
'{"correctOption":4}',
'The digit 7 is in the thousands place, so its place value is 7,000.',
'UNDERSTANDING', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_knowledge_numbers, @topic_face_place_value, 'MCQ',
'What is the face value of 5 in 45,678?',
'{"option1":"5","option2":"50","option3":"500","option4":"5,000"}',
'{"correctOption":1}',
'Face value is always the digit itself, which is 5.',
'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_knowledge_numbers, @topic_face_place_value, 'MCQ',
'What is the expanded form of 5,432?',
'{"option1":"5000 + 400 + 30 + 2","option2":"5 + 4 + 3 + 2","option3":"5000 + 432","option4":"500 + 43 + 2"}',
'{"correctOption":1}',
'Expanded form: 5,432 = 5000 + 400 + 30 + 2.',
'UNDERSTANDING', 'MEDIUM', @created_by),

-- Topic: Smallest and greatest numbers (2 questions)
(@board_id, @subject_id, @class_id, @medium, @chapter_knowledge_numbers, @topic_smallest_greatest, 'MCQ',
'What is the greatest number formed using digits 3, 7, 1, 9?',
'{"option1":"1379","option2":"9731","option3":"9713","option4":"7931"}',
'{"correctOption":2}',
'Arrange digits in descending order: 9731.',
'APPLICATION', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_knowledge_numbers, @topic_smallest_greatest, 'MCQ',
'What is the smallest number formed using digits 5, 2, 8, 0?',
'{"option1":"2058","option2":"258","option3":"2,085","option4":"528"}',
'{"correctOption":1}',
'Place smallest non-zero digit first (2), then 0, then remaining in ascending order: 2058.',
'APPLICATION', 'MEDIUM', @created_by),

-- Topic: Ascending and descending order (2 questions)
(@board_id, @subject_id, @class_id, @medium, @chapter_knowledge_numbers, @topic_ascending_descending, 'MCQ',
'Arrange in ascending order: 4,567, 4,576, 4,657, 4,765',
'{"option1":"4,567, 4,576, 4,657, 4,765","option2":"4,765, 4,657, 4,576, 4,567","option3":"4,576, 4,567, 4,657, 4,765","option4":"4,567, 4,657, 4,576, 4,765"}',
'{"correctOption":1}',
'Ascending order (smallest to largest): 4,567, 4,576, 4,657, 4,765.',
'UNDERSTANDING', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_knowledge_numbers, @topic_ascending_descending, 'MCQ',
'Which number is greater: 23,456 or 23,465?',
'{"option1":"23,456","option2":"23,465","option3":"Both are equal","option4":"Cannot compare"}',
'{"correctOption":2}',
'23,465 > 23,456 (compare digits from left to right).',
'UNDERSTANDING', 'EASY', @created_by),

-- Topic: Even and odd numbers (2 questions)
(@board_id, @subject_id, @class_id, @medium, @chapter_knowledge_numbers, @topic_even_odd, 'MCQ',
'Which of the following is an odd number?',
'{"option1":"24","option2":"36","option3":"47","option4":"58"}',
'{"correctOption":3}',
'47 is odd because it ends in 7 (not divisible by 2).',
'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_knowledge_numbers, @topic_even_odd, 'MCQ',
'What is the sum of two even numbers?',
'{"option1":"Always odd","option2":"Always even","option3":"Can be odd or even","option4":"Always prime"}',
'{"correctOption":2}',
'The sum of two even numbers is always even.',
'UNDERSTANDING', 'MEDIUM', @created_by),

-- Topic: Prime and composite numbers (2 questions)
(@board_id, @subject_id, @class_id, @medium, @chapter_knowledge_numbers, @topic_prime_composite, 'MCQ',
'Which of the following is a prime number?',
'{"option1":"9","option2":"15","option3":"17","option4":"21"}',
'{"correctOption":3}',
'17 is a prime number (divisible only by 1 and 17).',
'KNOWLEDGE', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_knowledge_numbers, @topic_prime_composite, 'MCQ',
'How many prime numbers are there between 1 and 10?',
'{"option1":"3","option2":"4","option3":"5","option4":"6"}',
'{"correctOption":2}',
'Prime numbers between 1 and 10: 2, 3, 5, 7 = 4 numbers.',
'KNOWLEDGE', 'MEDIUM', @created_by),

-- ============================================================================
-- CHAPTER 2: Operations on Numbers (20 questions)
-- ============================================================================

-- Topic: Addition (5 questions)
(@board_id, @subject_id, @class_id, @medium, @chapter_operations, @topic_addition, 'MCQ',
'Add: 23,456 + 12,345 = ?',
'{"option1":"35,801","option2":"35,701","option3":"35,901","option4":"36,801"}',
'{"correctOption":1}',
'23,456 + 12,345 = 35,801.',
'KNOWLEDGE', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_operations, @topic_addition, 'MCQ',
'What is 9,876 + 5,432?',
'{"option1":"15,208","option2":"15,308","option3":"15,408","option4":"15,508"}',
'{"correctOption":2}',
'9,876 + 5,432 = 15,308.',
'KNOWLEDGE', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_operations, @topic_addition, 'MCQ',
'Ram has 1,245 marbles and Shyam has 2,367 marbles. How many marbles do they have together?',
'{"option1":"3,512","option2":"3,612","option3":"3,712","option4":"3,812"}',
'{"correctOption":2}',
'Total = 1,245 + 2,367 = 3,612 marbles.',
'APPLICATION', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_operations, @topic_addition, 'MCQ',
'Find the sum: 45,678 + 23,456 + 12,345',
'{"option1":"81,479","option2":"81,379","option3":"81,579","option4":"81,679"}',
'{"correctOption":1}',
'45,678 + 23,456 + 12,345 = 81,479.',
'APPLICATION', 'HARD', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_operations, @topic_addition, 'MCQ',
'If 3,456 + __ = 10,000, what number goes in the blank?',
'{"option1":"6,544","option2":"6,644","option3":"6,444","option4":"7,544"}',
'{"correctOption":1}',
'10,000 - 3,456 = 6,544.',
'APPLICATION', 'HARD', @created_by),

-- Topic: Subtraction (5 questions)
(@board_id, @subject_id, @class_id, @medium, @chapter_operations, @topic_subtraction, 'MCQ',
'Subtract: 50,000 - 23,456 = ?',
'{"option1":"26,544","option2":"26,644","option3":"27,544","option4":"26,454"}',
'{"correctOption":1}',
'50,000 - 23,456 = 26,544.',
'KNOWLEDGE', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_operations, @topic_subtraction, 'MCQ',
'What is 78,901 - 45,678?',
'{"option1":"33,223","option2":"33,323","option3":"33,123","option4":"32,223"}',
'{"correctOption":1}',
'78,901 - 45,678 = 33,223.',
'KNOWLEDGE', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_operations, @topic_subtraction, 'MCQ',
'A school has 8,456 students. If 2,678 are girls, how many are boys?',
'{"option1":"5,778","option2":"5,878","option3":"5,678","option4":"5,978"}',
'{"correctOption":1}',
'Boys = 8,456 - 2,678 = 5,778.',
'APPLICATION', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_operations, @topic_subtraction, 'MCQ',
'Find the difference: 90,000 - 67,845',
'{"option1":"22,155","option2":"22,255","option3":"22,055","option4":"23,155"}',
'{"correctOption":1}',
'90,000 - 67,845 = 22,155.',
'KNOWLEDGE', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_operations, @topic_subtraction, 'MCQ',
'If __ - 12,345 = 34,567, what is the missing number?',
'{"option1":"46,912","option2":"46,812","option3":"47,912","option4":"22,222"}',
'{"correctOption":1}',
'Missing number = 34,567 + 12,345 = 46,912.',
'APPLICATION', 'HARD', @created_by),

-- Topic: Multiplication (5 questions)
(@board_id, @subject_id, @class_id, @medium, @chapter_operations, @topic_multiplication, 'MCQ',
'Multiply: 234 × 12 = ?',
'{"option1":"2,708","option2":"2,808","option3":"2,908","option4":"3,008"}',
'{"correctOption":2}',
'234 × 12 = 2,808.',
'KNOWLEDGE', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_operations, @topic_multiplication, 'MCQ',
'What is 456 × 23?',
'{"option1":"10,388","option2":"10,488","option3":"10,588","option4":"10,688"}',
'{"correctOption":3}',
'456 × 23 = 10,488.',
'KNOWLEDGE', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_operations, @topic_multiplication, 'MCQ',
'A box contains 24 pencils. How many pencils are in 35 boxes?',
'{"option1":"740","option2":"840","option3":"940","option4":"1,040"}',
'{"correctOption":2}',
'Total pencils = 24 × 35 = 840.',
'APPLICATION', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_operations, @topic_multiplication, 'MCQ',
'Find the product: 125 × 80',
'{"option1":"10,000","option2":"10,500","option3":"9,500","option4":"11,000"}',
'{"correctOption":1}',
'125 × 80 = 10,000.',
'KNOWLEDGE', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_operations, @topic_multiplication, 'MCQ',
'If 48 × __ = 1,440, what is the missing number?',
'{"option1":"25","option2":"30","option3":"35","option4":"40"}',
'{"correctOption":2}',
'Missing number = 1,440 ÷ 48 = 30.',
'APPLICATION', 'HARD', @created_by),

-- Topic: Division (5 questions)
(@board_id, @subject_id, @class_id, @medium, @chapter_operations, @topic_division, 'MCQ',
'Divide: 648 ÷ 24 = ?',
'{"option1":"25","option2":"26","option3":"27","option4":"28"}',
'{"correctOption":3}',
'648 ÷ 24 = 27.',
'KNOWLEDGE', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_operations, @topic_division, 'MCQ',
'What is 936 ÷ 36?',
'{"option1":"24","option2":"25","option3":"26","option4":"27"}',
'{"correctOption":3}',
'936 ÷ 36 = 26.',
'KNOWLEDGE', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_operations, @topic_division, 'MCQ',
'720 apples are to be packed equally in 45 boxes. How many apples in each box?',
'{"option1":"14","option2":"15","option3":"16","option4":"17"}',
'{"correctOption":3}',
'Apples per box = 720 ÷ 45 = 16.',
'APPLICATION', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_operations, @topic_division, 'MCQ',
'Find the quotient: 875 ÷ 25',
'{"option1":"33","option2":"34","option3":"35","option4":"36"}',
'{"correctOption":3}',
'875 ÷ 25 = 35.',
'KNOWLEDGE', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_operations, @topic_division, 'MCQ',
'If __ ÷ 16 = 48, what is the missing number?',
'{"option1":"768","option2":"748","option3":"788","option4":"728"}',
'{"correctOption":1}',
'Missing number = 48 × 16 = 768.',
'APPLICATION', 'HARD', @created_by),

-- ============================================================================
-- CHAPTER 3: Fractions (15 questions)
-- ============================================================================

-- Topic: Reading, writing meaning of Fraction (3 questions)
(@board_id, @subject_id, @class_id, @medium, @chapter_fractions, @topic_fraction_meaning, 'MCQ',
'What is the numerator in the fraction 3/5?',
'{"option1":"3","option2":"5","option3":"8","option4":"2"}',
'{"correctOption":1}',
'In 3/5, the numerator (top number) is 3.',
'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_fractions, @topic_fraction_meaning, 'MCQ',
'What fraction represents half?',
'{"option1":"1/3","option2":"1/2","option3":"2/3","option4":"1/4"}',
'{"correctOption":2}',
'Half is represented by the fraction 1/2.',
'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_fractions, @topic_fraction_meaning, 'MCQ',
'If a pizza is cut into 8 equal pieces and you eat 3 pieces, what fraction did you eat?',
'{"option1":"3/5","option2":"3/8","option3":"5/8","option4":"8/3"}',
'{"correctOption":2}',
'You ate 3 out of 8 pieces = 3/8.',
'APPLICATION', 'EASY', @created_by),

-- Topic: Like fractions (3 questions)
(@board_id, @subject_id, @class_id, @medium, @chapter_fractions, @topic_like_fractions, 'MCQ',
'Add: 2/7 + 3/7 = ?',
'{"option1":"5/7","option2":"5/14","option3":"6/7","option4":"2/7"}',
'{"correctOption":1}',
'When denominators are same, add numerators: 2 + 3 = 5, so 5/7.',
'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_fractions, @topic_like_fractions, 'MCQ',
'Subtract: 5/9 - 2/9 = ?',
'{"option1":"3/9","option2":"3/18","option3":"7/9","option4":"1/3"}',
'{"correctOption":1}',
'5/9 - 2/9 = 3/9 (which can be simplified to 1/3).',
'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_fractions, @topic_like_fractions, 'MCQ',
'What is 1/6 + 3/6 + 2/6?',
'{"option1":"6/18","option2":"6/6","option3":"1","option4":"Both option2 and option3"}',
'{"correctOption":4}',
'1/6 + 3/6 + 2/6 = 6/6 = 1.',
'UNDERSTANDING', 'MEDIUM', @created_by),

-- Topic: Unlike fractions (2 questions)
(@board_id, @subject_id, @class_id, @medium, @chapter_fractions, @topic_unlike_fractions, 'MCQ',
'Which pair has unlike fractions?',
'{"option1":"2/5 and 3/5","option2":"1/4 and 1/8","option3":"3/7 and 4/7","option4":"5/9 and 7/9"}',
'{"correctOption":2}',
'Unlike fractions have different denominators. 1/4 and 1/8 have denominators 4 and 8.',
'UNDERSTANDING', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_fractions, @topic_unlike_fractions, 'MCQ',
'Add: 1/2 + 1/4 = ?',
'{"option1":"2/6","option2":"3/4","option3":"2/4","option4":"1/6"}',
'{"correctOption":2}',
'Convert to common denominator: 2/4 + 1/4 = 3/4.',
'APPLICATION', 'MEDIUM', @created_by),

-- Topic: Comparing fractions (2 questions)
(@board_id, @subject_id, @class_id, @medium, @chapter_fractions, @topic_comparing_fractions, 'MCQ',
'Which fraction is greater: 3/5 or 2/5?',
'{"option1":"3/5","option2":"2/5","option3":"Both are equal","option4":"Cannot compare"}',
'{"correctOption":1}',
'When denominators are same, compare numerators: 3 > 2, so 3/5 > 2/5.',
'UNDERSTANDING', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_fractions, @topic_comparing_fractions, 'MCQ',
'Arrange in ascending order: 1/2, 1/3, 1/4',
'{"option1":"1/2, 1/3, 1/4","option2":"1/4, 1/3, 1/2","option3":"1/3, 1/4, 1/2","option4":"1/4, 1/2, 1/3"}',
'{"correctOption":2}',
'When numerators are same, smaller denominator means larger fraction: 1/4 < 1/3 < 1/2.',
'UNDERSTANDING', 'MEDIUM', @created_by),

-- Topic: Proper, improper and mixed fractions (5 questions)
(@board_id, @subject_id, @class_id, @medium, @chapter_fractions, @topic_proper_improper, 'MCQ',
'Which is a proper fraction?',
'{"option1":"7/5","option2":"3/3","option3":"2/7","option4":"8/8"}',
'{"correctOption":3}',
'A proper fraction has numerator less than denominator. 2/7 is proper.',
'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_fractions, @topic_proper_improper, 'MCQ',
'Which is an improper fraction?',
'{"option1":"3/4","option2":"5/6","option3":"7/8","option4":"9/5"}',
'{"correctOption":4}',
'An improper fraction has numerator greater than or equal to denominator. 9/5 is improper.',
'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_fractions, @topic_proper_improper, 'MCQ',
'Convert 11/4 to a mixed number:',
'{"option1":"2 3/4","option2":"2 2/4","option3":"3 1/4","option4":"2 1/4"}',
'{"correctOption":1}',
'11 ÷ 4 = 2 remainder 3, so 11/4 = 2 3/4.',
'APPLICATION', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_fractions, @topic_proper_improper, 'MCQ',
'Convert 3 2/5 to an improper fraction:',
'{"option1":"15/5","option2":"17/5","option3":"13/5","option4":"16/5"}',
'{"correctOption":2}',
'3 2/5 = (3 × 5 + 2)/5 = 17/5.',
'APPLICATION', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_fractions, @topic_proper_improper, 'MCQ',
'Which is greater: 5/3 or 1 1/2?',
'{"option1":"5/3","option2":"1 1/2","option3":"Both are equal","option4":"Cannot compare"}',
'{"correctOption":1}',
'Convert: 5/3 = 1 2/3 and 1 1/2 = 1 1/2. Since 2/3 > 1/2, 5/3 is greater.',
'APPLICATION', 'HARD', @created_by),

-- ============================================================================
-- CHAPTER 4: Measurement / Mensuration (15 questions)
-- ============================================================================

-- Topic: Length, mass, capacity (5 questions)
(@board_id, @subject_id, @class_id, @medium, @chapter_measurement, @topic_length_mass_capacity, 'MCQ',
'Convert 3 km to meters:',
'{"option1":"300 m","option2":"3,000 m","option3":"30,000 m","option4":"30 m"}',
'{"correctOption":2}',
'1 km = 1,000 m, so 3 km = 3,000 m.',
'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_measurement, @topic_length_mass_capacity, 'MCQ',
'How many grams in 2.5 kg?',
'{"option1":"25 g","option2":"250 g","option3":"2,500 g","option4":"25,000 g"}',
'{"correctOption":3}',
'1 kg = 1,000 g, so 2.5 kg = 2,500 g.',
'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_measurement, @topic_length_mass_capacity, 'MCQ',
'Add: 2 km 350 m + 1 km 750 m = ?',
'{"option1":"3 km 100 m","option2":"4 km 100 m","option3":"4 km","option4":"3 km 1,100 m"}',
'{"correctOption":2}',
'2,350 m + 1,750 m = 4,100 m = 4 km 100 m.',
'APPLICATION', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_measurement, @topic_length_mass_capacity, 'MCQ',
'How many liters in 5,000 ml?',
'{"option1":"5 L","option2":"50 L","option3":"0.5 L","option4":"500 L"}',
'{"correctOption":1}',
'1,000 ml = 1 L, so 5,000 ml = 5 L.',
'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_measurement, @topic_length_mass_capacity, 'MCQ',
'Subtract: 5 kg 200 g - 2 kg 450 g = ?',
'{"option1":"2 kg 750 g","option2":"3 kg 750 g","option3":"2 kg 650 g","option4":"3 kg 650 g"}',
'{"correctOption":1}',
'5,200 g - 2,450 g = 2,750 g = 2 kg 750 g.',
'APPLICATION', 'MEDIUM', @created_by),

-- Topic: Time measurement (4 questions)
(@board_id, @subject_id, @class_id, @medium, @chapter_measurement, @topic_time_measurement, 'MCQ',
'How many minutes are in 3 hours?',
'{"option1":"60 minutes","option2":"120 minutes","option3":"180 minutes","option4":"240 minutes"}',
'{"correctOption":3}',
'1 hour = 60 minutes, so 3 hours = 180 minutes.',
'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_measurement, @topic_time_measurement, 'MCQ',
'Convert 150 seconds to minutes and seconds:',
'{"option1":"2 min 30 sec","option2":"2 min 20 sec","option3":"1 min 50 sec","option4":"3 min"}',
'{"correctOption":1}',
'150 seconds = 2 minutes 30 seconds (150 ÷ 60 = 2 remainder 30).',
'APPLICATION', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_measurement, @topic_time_measurement, 'MCQ',
'If it is 9:45 AM now, what time will it be after 2 hours 30 minutes?',
'{"option1":"11:15 AM","option2":"12:15 PM","option3":"11:45 AM","option4":"12:45 PM"}',
'{"correctOption":2}',
'9:45 AM + 2 hours 30 minutes = 12:15 PM.',
'APPLICATION', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_measurement, @topic_time_measurement, 'MCQ',
'What does PM stand for?',
'{"option1":"Pre Meridiem","option2":"Post Meridiem","option3":"Past Morning","option4":"Present Moment"}',
'{"correctOption":2}',
'PM stands for Post Meridiem (after noon).',
'KNOWLEDGE', 'EASY', @created_by),

-- Topic: Calendar (2 questions)
(@board_id, @subject_id, @class_id, @medium, @chapter_measurement, @topic_calendar, 'MCQ',
'How many days are in the month of February in a leap year?',
'{"option1":"28","option2":"29","option3":"30","option4":"31"}',
'{"correctOption":2}',
'February has 29 days in a leap year.',
'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_measurement, @topic_calendar, 'MCQ',
'If today is Wednesday, what day will it be after 10 days?',
'{"option1":"Thursday","option2":"Friday","option3":"Saturday","option4":"Sunday"}',
'{"correctOption":3}',
'10 days = 1 week + 3 days. Wednesday + 3 days = Saturday.',
'APPLICATION', 'MEDIUM', @created_by),

-- Topic: Paper measurement (2 questions)
(@board_id, @subject_id, @class_id, @medium, @chapter_measurement, @topic_paper_measurement, 'MCQ',
'How many sheets are in 1 Gross?',
'{"option1":"12","option2":"20","option3":"144","option4":"500"}',
'{"correctOption":3}',
'1 Gross = 144 sheets (12 × 12).',
'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_measurement, @topic_paper_measurement, 'MCQ',
'How many sheets are in 1 Rim?',
'{"option1":"100","option2":"144","option3":"480","option4":"500"}',
'{"correctOption":4}',
'1 Rim = 500 sheets of paper.',
'KNOWLEDGE', 'EASY', @created_by),

-- Topic: Money (2 questions)
(@board_id, @subject_id, @class_id, @medium, @chapter_measurement, @topic_money, 'MCQ',
'Convert ₹5.75 to paise:',
'{"option1":"57 paise","option2":"575 paise","option3":"5,750 paise","option4":"5.75 paise"}',
'{"correctOption":2}',
'₹1 = 100 paise, so ₹5.75 = 575 paise.',
'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_measurement, @topic_money, 'MCQ',
'Ravi bought 3 notebooks at ₹25 each and 2 pens at ₹12 each. What is the total cost?',
'{"option1":"₹99","option2":"₹101","option3":"₹95","option4":"₹97"}',
'{"correctOption":1}',
'Total = (3 × 25) + (2 × 12) = 75 + 24 = ₹99.',
'APPLICATION', 'MEDIUM', @created_by),

-- ============================================================================
-- CHAPTER 5: Patterns (6 questions)
-- ============================================================================

-- Topic: Geometric shapes patterns (2 questions)
(@board_id, @subject_id, @class_id, @medium, @chapter_patterns, @topic_geometric_shapes_pattern, 'MCQ',
'What comes next in the pattern: Circle, Square, Triangle, Circle, Square, ___?',
'{"option1":"Circle","option2":"Square","option3":"Triangle","option4":"Rectangle"}',
'{"correctOption":3}',
'The pattern repeats: Circle, Square, Triangle. Next is Triangle.',
'UNDERSTANDING', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_patterns, @topic_geometric_shapes_pattern, 'MCQ',
'Complete the pattern: ■ ■ □ ■ ■ □ ■ ■ ___',
'{"option1":"■","option2":"□","option3":"■ ■","option4":"□ □"}',
'{"correctOption":2}',
'Pattern: two filled, one empty. Next is empty (□).',
'UNDERSTANDING', 'EASY', @created_by),

-- Topic: Number patterns (2 questions)
(@board_id, @subject_id, @class_id, @medium, @chapter_patterns, @topic_numbers_pattern, 'MCQ',
'What comes next: 2, 4, 6, 8, 10, ___?',
'{"option1":"11","option2":"12","option3":"13","option4":"14"}',
'{"correctOption":2}',
'Pattern: even numbers, add 2 each time. 10 + 2 = 12.',
'UNDERSTANDING', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_patterns, @topic_numbers_pattern, 'MCQ',
'Find the missing number: 5, 10, 15, ___, 25, 30',
'{"option1":"17","option2":"18","option3":"20","option4":"22"}',
'{"correctOption":3}',
'Pattern: multiples of 5. Missing number is 20.',
'UNDERSTANDING', 'EASY', @created_by),

-- Topic: Freehand shapes (2 questions)
(@board_id, @subject_id, @class_id, @medium, @chapter_patterns, @topic_freehand_shapes, 'MCQ',
'Which letter has a line of symmetry?',
'{"option1":"Z","option2":"N","option3":"A","option4":"S"}',
'{"correctOption":3}',
'Letter A has a vertical line of symmetry.',
'UNDERSTANDING', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_patterns, @topic_freehand_shapes, 'MCQ',
'How many lines of symmetry does a circle have?',
'{"option1":"0","option2":"1","option3":"4","option4":"Infinite"}',
'{"correctOption":4}',
'A circle has infinite lines of symmetry through its center.',
'KNOWLEDGE', 'MEDIUM', @created_by),

-- ============================================================================
-- CHAPTER 6: Geometry (20 questions)
-- ============================================================================

-- Topic: Angles (3 questions)
(@board_id, @subject_id, @class_id, @medium, @chapter_geometry, @topic_angles, 'MCQ',
'How many degrees are in a right angle?',
'{"option1":"45°","option2":"60°","option3":"90°","option4":"180°"}',
'{"correctOption":3}',
'A right angle measures 90 degrees.',
'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_geometry, @topic_angles, 'MCQ',
'Which angle is less than 90 degrees?',
'{"option1":"Right angle","option2":"Acute angle","option3":"Obtuse angle","option4":"Straight angle"}',
'{"correctOption":2}',
'An acute angle is less than 90 degrees.',
'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_geometry, @topic_angles, 'MCQ',
'An angle measuring 120° is called:',
'{"option1":"Acute angle","option2":"Right angle","option3":"Obtuse angle","option4":"Straight angle"}',
'{"correctOption":3}',
'An angle between 90° and 180° is an obtuse angle.',
'KNOWLEDGE', 'EASY', @created_by),

-- Topic: Symmetry (2 questions)
(@board_id, @subject_id, @class_id, @medium, @chapter_geometry, @topic_symmetry, 'MCQ',
'How many lines of symmetry does a square have?',
'{"option1":"2","option2":"3","option3":"4","option4":"5"}',
'{"correctOption":3}',
'A square has 4 lines of symmetry (2 diagonal and 2 through midpoints).',
'KNOWLEDGE', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_geometry, @topic_symmetry, 'MCQ',
'Which shape has only 1 line of symmetry?',
'{"option1":"Circle","option2":"Rectangle","option3":"Isosceles triangle","option4":"Square"}',
'{"correctOption":3}',
'An isosceles triangle has 1 line of symmetry (through the vertex and midpoint of base).',
'UNDERSTANDING', 'MEDIUM', @created_by),

-- Topic: Triangle, Square, Rectangle (3 questions)
(@board_id, @subject_id, @class_id, @medium, @chapter_geometry, @topic_triangle_square_rectangle, 'MCQ',
'How many vertices does a triangle have?',
'{"option1":"2","option2":"3","option3":"4","option4":"5"}',
'{"correctOption":2}',
'A triangle has 3 vertices (corners).',
'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_geometry, @topic_triangle_square_rectangle, 'MCQ',
'How many sides does a rectangle have?',
'{"option1":"3","option2":"4","option3":"5","option4":"6"}',
'{"correctOption":2}',
'A rectangle has 4 sides.',
'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_geometry, @topic_triangle_square_rectangle, 'MCQ',
'All sides of which shape are equal?',
'{"option1":"Rectangle","option2":"Triangle","option3":"Square","option4":"Circle"}',
'{"correctOption":3}',
'All four sides of a square are equal.',
'KNOWLEDGE', 'EASY', @created_by),

-- Topic: Circle (3 questions)
(@board_id, @subject_id, @class_id, @medium, @chapter_geometry, @topic_circle, 'MCQ',
'The distance from the center to any point on the circle is called:',
'{"option1":"Diameter","option2":"Radius","option3":"Chord","option4":"Circumference"}',
'{"correctOption":2}',
'The radius is the distance from center to any point on the circle.',
'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_geometry, @topic_circle, 'MCQ',
'The diameter of a circle is:',
'{"option1":"Half the radius","option2":"Equal to radius","option3":"Twice the radius","option4":"Three times the radius"}',
'{"correctOption":3}',
'Diameter = 2 × radius.',
'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_geometry, @topic_circle, 'MCQ',
'If the radius of a circle is 5 cm, what is its diameter?',
'{"option1":"2.5 cm","option2":"5 cm","option3":"10 cm","option4":"15 cm"}',
'{"correctOption":3}',
'Diameter = 2 × radius = 2 × 5 = 10 cm.',
'APPLICATION', 'EASY', @created_by),

-- Topic: Perimeter (3 questions)
(@board_id, @subject_id, @class_id, @medium, @chapter_geometry, @topic_perimeter, 'MCQ',
'What is the perimeter of a rectangle with length 8 cm and width 5 cm?',
'{"option1":"13 cm","option2":"26 cm","option3":"40 cm","option4":"20 cm"}',
'{"correctOption":2}',
'Perimeter = 2(length + width) = 2(8 + 5) = 2 × 13 = 26 cm.',
'APPLICATION', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_geometry, @topic_perimeter, 'MCQ',
'A square has a perimeter of 36 cm. What is the length of each side?',
'{"option1":"6 cm","option2":"9 cm","option3":"12 cm","option4":"18 cm"}',
'{"correctOption":2}',
'Side = Perimeter ÷ 4 = 36 ÷ 4 = 9 cm.',
'APPLICATION', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_geometry, @topic_perimeter, 'MCQ',
'What is the perimeter of a triangle with sides 5 cm, 6 cm, and 7 cm?',
'{"option1":"15 cm","option2":"16 cm","option3":"17 cm","option4":"18 cm"}',
'{"correctOption":4}',
'Perimeter = 5 + 6 + 7 = 18 cm.',
'APPLICATION', 'EASY', @created_by),

-- Topic: Area (3 questions)
(@board_id, @subject_id, @class_id, @medium, @chapter_geometry, @topic_area, 'MCQ',
'What is the area of a square with side 6 cm?',
'{"option1":"12 cm²","option2":"24 cm²","option3":"36 cm²","option4":"42 cm²"}',
'{"correctOption":3}',
'Area of square = side × side = 6 × 6 = 36 cm².',
'APPLICATION', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_geometry, @topic_area, 'MCQ',
'Find the area of a rectangle with length 10 cm and width 4 cm:',
'{"option1":"14 cm²","option2":"28 cm²","option3":"40 cm²","option4":"44 cm²"}',
'{"correctOption":3}',
'Area = length × width = 10 × 4 = 40 cm².',
'APPLICATION', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_geometry, @topic_area, 'MCQ',
'A rectangular field is 15 m long and 8 m wide. What is its area?',
'{"option1":"23 m²","option2":"46 m²","option3":"120 m²","option4":"240 m²"}',
'{"correctOption":3}',
'Area = 15 × 8 = 120 m².',
'APPLICATION', 'MEDIUM', @created_by),

-- Topic: 3D objects and nets (1 question)
(@board_id, @subject_id, @class_id, @medium, @chapter_geometry, @topic_3d_objects, 'MCQ',
'Which is a 3-dimensional object?',
'{"option1":"Square","option2":"Circle","option3":"Triangle","option4":"Cube"}',
'{"correctOption":4}',
'A cube is a 3-dimensional object with length, width, and height.',
'KNOWLEDGE', 'EASY', @created_by),

-- Topic: Cone, Cylinder, Sphere (2 questions)
(@board_id, @subject_id, @class_id, @medium, @chapter_geometry, @topic_cone_cylinder_sphere, 'MCQ',
'Which 3D shape has no corners or edges?',
'{"option1":"Cube","option2":"Cone","option3":"Cylinder","option4":"Sphere"}',
'{"correctOption":4}',
'A sphere has no corners or edges; it is perfectly round.',
'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_geometry, @topic_cone_cylinder_sphere, 'MCQ',
'How many circular faces does a cylinder have?',
'{"option1":"0","option2":"1","option3":"2","option4":"3"}',
'{"correctOption":3}',
'A cylinder has 2 circular faces (top and bottom).',
'KNOWLEDGE', 'EASY', @created_by),

-- Topic: Cube and Cuboid (3 questions)
(@board_id, @subject_id, @class_id, @medium, @chapter_geometry, @topic_cube_cuboid, 'MCQ',
'How many faces does a cube have?',
'{"option1":"4","option2":"6","option3":"8","option4":"12"}',
'{"correctOption":2}',
'A cube has 6 square faces.',
'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_geometry, @topic_cube_cuboid, 'MCQ',
'How many edges does a cuboid have?',
'{"option1":"6","option2":"8","option3":"10","option4":"12"}',
'{"correctOption":4}',
'A cuboid has 12 edges.',
'KNOWLEDGE', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_geometry, @topic_cube_cuboid, 'MCQ',
'How many vertices does a cube have?',
'{"option1":"4","option2":"6","option3":"8","option4":"12"}',
'{"correctOption":3}',
'A cube has 8 vertices (corners).',
'KNOWLEDGE', 'EASY', @created_by),

-- ============================================================================
-- CHAPTER 7: Pictographs (9 questions)
-- ============================================================================

-- Topic: Comprehension on pictorial information (9 questions)
(@board_id, @subject_id, @class_id, @medium, @chapter_pictographs, @topic_pictograph_comprehension, 'MCQ',
'A pictograph shows 4 book symbols for Class A and 6 for Class B. Each symbol = 10 books. How many more books does Class B have?',
'{"option1":"2","option2":"10","option3":"20","option4":"60"}',
'{"correctOption":3}',
'Class A: 4 × 10 = 40 books. Class B: 6 × 10 = 60 books. Difference: 60 - 40 = 20.',
'APPLICATION', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_pictographs, @topic_pictograph_comprehension, 'MCQ',
'In a pictograph, 1 apple symbol = 5 apples. If there are 7 symbols, how many apples in total?',
'{"option1":"7","option2":"12","option3":"35","option4":"70"}',
'{"correctOption":3}',
'Total apples = 7 × 5 = 35.',
'APPLICATION', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_pictographs, @topic_pictograph_comprehension, 'MCQ',
'A bar graph shows: Monday-20, Tuesday-30, Wednesday-25. What is the total for all three days?',
'{"option1":"65","option2":"70","option3":"75","option4":"80"}',
'{"correctOption":3}',
'Total = 20 + 30 + 25 = 75.',
'APPLICATION', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_pictographs, @topic_pictograph_comprehension, 'MCQ',
'In a survey, 15 students like cricket, 12 like football, 8 like both. How many like at least one sport?',
'{"option1":"19","option2":"27","option3":"35","option4":"25"}',
'{"correctOption":1}',
'Students liking at least one = 15 + 12 - 8 = 19 (subtract those counted twice).',
'APPLICATION', 'HARD', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_pictographs, @topic_pictograph_comprehension, 'MCQ',
'A tally chart shows |||| for Monday and |||| |||| for Tuesday. How many more on Tuesday?',
'{"option1":"4","option2":"5","option3":"9","option4":"14"}',
'{"correctOption":2}',
'Monday: 4 marks, Tuesday: 9 marks. Difference: 9 - 4 = 5.',
'APPLICATION', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_pictographs, @topic_pictograph_comprehension, 'MCQ',
'In a pictograph, if half a symbol represents 3 items, what does 1 full symbol represent?',
'{"option1":"3","option2":"6","option3":"9","option4":"12"}',
'{"correctOption":2}',
'If half symbol = 3, then full symbol = 3 × 2 = 6.',
'APPLICATION', 'MEDIUM', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_pictographs, @topic_pictograph_comprehension, 'MCQ',
'A bar graph shows sales: Jan-40, Feb-50, Mar-45. Which month had the highest sales?',
'{"option1":"January","option2":"February","option3":"March","option4":"All equal"}',
'{"correctOption":2}',
'February had the highest sales with 50 units.',
'UNDERSTANDING', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_pictographs, @topic_pictograph_comprehension, 'MCQ',
'In a pictograph showing favorite colors, Red has 8 faces, Blue has 6, Green has 5. How many students voted?',
'{"option1":"13","option2":"14","option3":"19","option4":"24"}',
'{"correctOption":3}',
'Total students = 8 + 6 + 5 = 19.',
'APPLICATION', 'EASY', @created_by),

(@board_id, @subject_id, @class_id, @medium, @chapter_pictographs, @topic_pictograph_comprehension, 'MCQ',
'A graph shows temperatures: Mon-25°C, Tue-28°C, Wed-22°C. What is the average temperature?',
'{"option1":"23°C","option2":"24°C","option3":"25°C","option4":"26°C"}',
'{"correctOption":3}',
'Average = (25 + 28 + 22) ÷ 3 = 75 ÷ 3 = 25°C.',
'APPLICATION', 'MEDIUM', @created_by);

-- ============================================================================
-- COMPLETION MESSAGE
-- ============================================================================

SELECT 'MSCE Class 5 Mathematics questions insertion completed!' as status;
SELECT 'Total: 100 MCQ questions' as summary;
SELECT 'Distribution: Knowledge of Numbers(15), Operations(20), Fractions(15), Measurement(15), Patterns(6), Geometry(20), Pictographs(9)' as breakdown;

