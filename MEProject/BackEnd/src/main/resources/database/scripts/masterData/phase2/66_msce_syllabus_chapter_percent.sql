--liquibase formatted sql
--changeset {narendra}:{id}

START TRANSACTION;

SET @board_msce_id = (SELECT id FROM board WHERE board_name = 'MSCE' LIMIT 1);

SET @subject_english_id = (SELECT subject_id FROM subject WHERE subject_name = 'English – First Language' LIMIT 1);
SET @subject_math_id = (SELECT subject_id FROM subject WHERE subject_name = 'Math – English' LIMIT 1);
SET @subject_marathi_id = (SELECT subject_id FROM subject WHERE subject_name = 'Marathi – Third Language' LIMIT 1);
SET @subject_iq_id = (SELECT subject_id FROM subject WHERE subject_name = 'IQ – English' LIMIT 1);


SET @class_4th_id = (SELECT id FROM class WHERE class_name = '4' LIMIT 1);

-- 5. SYLLABUS FOR 'English – First Language' (parsed from the attached PDF)
SET @chapter_vocabulary_id = (SELECT id FROM chapters WHERE chapter_name = 'Vocabulary' AND subject_id = @subject_english_id AND class_id = @class_4th_id AND board_id = @board_msce_id LIMIT 1);
SET @chapter_word_games_id = (SELECT id FROM chapters WHERE chapter_name = 'Word Games' AND subject_id = @subject_english_id AND class_id = @class_4th_id AND board_id = @board_msce_id LIMIT 1);
SET @chapter_grammar_id = (SELECT id FROM chapters WHERE chapter_name = 'Grammar' AND subject_id = @subject_english_id AND class_id = @class_4th_id AND board_id = @board_msce_id LIMIT 1);
SET @chapter_language_study_id = (SELECT id FROM chapters WHERE chapter_name = 'Language Study' AND subject_id = @subject_english_id AND class_id = @class_4th_id AND board_id = @board_msce_id LIMIT 1);
SET @chapter_creative_writing_id = (SELECT id FROM chapters WHERE chapter_name = 'Creative writing' AND subject_id = @subject_english_id AND class_id = @class_4th_id AND board_id = @board_msce_id LIMIT 1);
SET @chapter_reading_skills_id = (SELECT id FROM chapters WHERE chapter_name = 'Reading skills (comprehension)' AND subject_id = @subject_english_id AND class_id = @class_4th_id AND board_id = @board_msce_id LIMIT 1);
SET @chapter_miscellaneous_id = (SELECT id FROM chapters WHERE chapter_name = 'Miscellaneous' AND subject_id = @subject_english_id AND class_id = @class_4th_id AND board_id = @board_msce_id LIMIT 1);


update chapters set percent=24 where id=@chapter_vocabulary_id;
update chapters set percent=8 where id=@chapter_word_games_id;
update chapters set percent=24 where id=@chapter_grammar_id;
update chapters set percent=6 where id=@chapter_language_study_id;
update chapters set percent=8 where id=@chapter_creative_writing_id;
update chapters set percent=24 where id=@chapter_reading_skills_id;
update chapters set percent=6 where id=@chapter_miscellaneous_id;


-- 5. SYLLABUS FOR 'Math – English' (parsed from the attached PDF)
SET @chapter_knowledge_of_numbers_id = (SELECT id FROM chapters WHERE chapter_name = 'Knowledge of Numbers' AND subject_id = @subject_math_id AND class_id = @class_4th_id AND board_id = @board_msce_id LIMIT 1);
SET @chapter_operations_on_numbers_id = (SELECT id FROM chapters WHERE chapter_name = 'Operations on Numbers' AND subject_id = @subject_math_id AND class_id = @class_4th_id AND board_id = @board_msce_id LIMIT 1);
SET @chapter_fractions_id = (SELECT id FROM chapters WHERE chapter_name = 'Fractions' AND subject_id = @subject_math_id AND class_id = @class_4th_id AND board_id = @board_msce_id LIMIT 1);
SET @chapter_measurement_mensuration_id = (SELECT id FROM chapters WHERE chapter_name = 'Measurement / Mensuration' AND subject_id = @subject_math_id AND class_id = @class_4th_id AND board_id = @board_msce_id LIMIT 1);
SET @chapter_patterns_id = (SELECT id FROM chapters WHERE chapter_name = 'Patterns' AND subject_id = @subject_math_id AND class_id = @class_4th_id AND board_id = @board_msce_id LIMIT 1);
SET @chapter_geometry_id = (SELECT id FROM chapters WHERE chapter_name = 'Geometry' AND subject_id = @subject_math_id AND class_id = @class_4th_id AND board_id = @board_msce_id LIMIT 1);
SET @chapter_pictographs_id = (SELECT id FROM chapters WHERE chapter_name = 'Pictographs' AND subject_id = @subject_math_id AND class_id = @class_4th_id AND board_id = @board_msce_id LIMIT 1);


update chapters set percent=16 where id=@chapter_knowledge_of_num;
update chapters set percent=20 where id=@chapter_operations_on_numbers_id;
update chapters set percent=12 where id=@chapter_fractions_id;
update chapters set percent=20 where id=@chapter_measurement_mensuration_id;
update chapters set percent=8 where id=@chapter_patterns_id;
update chapters set percent=18 where id=@chapter_geometry_id;
update chapters set percent=6 where id=@chapter_pictographs_id;


-- 5. SYLLABUS FOR 'Marathi – Third Language' (parsed from the attached PDF)
SET @chapter_akalan_id = (SELECT id FROM chapters WHERE chapter_name = 'आकलन' AND subject_id = @subject_marathi_id AND class_id = @class_4th_id AND board_id = @board_msce_id LIMIT 1);
SET @chapter_shabdsampatti_id = (SELECT id FROM chapters WHERE chapter_name = 'शब्दसंपत्ती' AND subject_id = @subject_marathi_id AND class_id = @class_4th_id AND board_id = @board_msce_id LIMIT 1);
SET @chapter_vyakaran_id = (SELECT id FROM chapters WHERE chapter_name = 'कार्यात्मक व्याकरण' AND subject_id = @subject_marathi_id AND class_id = @class_4th_id AND board_id = @board_msce_id LIMIT 1);
SET @chapter_samanyagnan_id = (SELECT id FROM chapters WHERE chapter_name = '1 ली ते 4 वी मराठी (सुलभभारती) विषयाशी संबंधित सामान्यज्ञान' AND subject_id = @subject_marathi_id AND class_id = @class_4th_id AND board_id = @board_msce_id LIMIT 1);


update chapters set percent=24 where id=@chapter_akalan_id;
update chapters set percent=44 where id=@chapter_shabdsampatti_id;
update chapters set percent=24 where id=@chapter_vyakaran_id;
update chapters set percent=8 where id=@chapter_samanyagnan_id;

-- 5. SYLLABUS FOR 'IQ – English' (parsed from the attached PDF - Subject: बुध्दिमत्ता चाचणी)
SET @chapter_iq_comprehension_id = (SELECT id FROM chapters WHERE chapter_name = 'Comprehension' AND subject_id = @subject_iq_id AND class_id = @class_4th_id AND board_id = @board_msce_id LIMIT 1);
SET @chapter_iq_classification_id = (SELECT id FROM chapters WHERE chapter_name = 'Classification' AND subject_id = @subject_iq_id AND class_id = @class_4th_id AND board_id = @board_msce_id LIMIT 1);
SET @chapter_iq_correlation_id = (SELECT id FROM chapters WHERE chapter_name = 'Co-relation' AND subject_id = @subject_iq_id AND class_id = @class_4th_id AND board_id = @board_msce_id LIMIT 1);
SET @chapter_iq_number_order_id = (SELECT id FROM chapters WHERE chapter_name = 'Number order' AND subject_id = @subject_iq_id AND class_id = @class_4th_id AND board_id = @board_msce_id LIMIT 1);
SET @chapter_iq_like_terms_id = (SELECT id FROM chapters WHERE chapter_name = 'Like Terms' AND subject_id = @subject_iq_id AND class_id = @class_4th_id AND board_id = @board_msce_id LIMIT 1);
SET @chapter_iq_water_image_id = (SELECT id FROM chapters WHERE chapter_name = 'Water Image' AND subject_id = @subject_iq_id AND class_id = @class_4th_id AND board_id = @board_msce_id LIMIT 1);
SET @chapter_iq_mirror_image_id = (SELECT id FROM chapters WHERE chapter_name = 'Mirror Image' AND subject_id = @subject_iq_id AND class_id = @class_4th_id AND board_id = @board_msce_id LIMIT 1);
SET @chapter_iq_identifying_similarities_id = (SELECT id FROM chapters WHERE chapter_name = 'Identifying Similarities' AND subject_id = @subject_iq_id AND class_id = @class_4th_id AND board_id = @board_msce_id LIMIT 1);
SET @chapter_iq_logic_inference_id = (SELECT id FROM chapters WHERE chapter_name = 'Logic And Inference' AND subject_id = @subject_iq_id AND class_id = @class_4th_id AND board_id = @board_msce_id LIMIT 1);
SET @chapter_iq_puzzles_id = (SELECT id FROM chapters WHERE chapter_name = 'Puzzles' AND subject_id = @subject_iq_id AND class_id = @class_4th_id AND board_id = @board_msce_id LIMIT 1);
SET @chapter_iq_symbolic_language_id = (SELECT id FROM chapters WHERE chapter_name = 'Symbolic Language (Symbol)' AND subject_id = @subject_iq_id AND class_id = @class_4th_id AND board_id = @board_msce_id LIMIT 1);
SET @chapter_iq_special_question_id = (SELECT id FROM chapters WHERE chapter_name = 'Special Question Or Important' AND subject_id = @subject_iq_id AND class_id = @class_4th_id AND board_id = @board_msce_id LIMIT 1);


update chapters set percent=8 where id=@chapter_iq_comprehension_id;
update chapters set percent=10 where id=@chapter_iq_classification_id;
update chapters set percent=10 where id=@chapter_iq_correlation_id;
update chapters set percent=10 where id=@chapter_iq_number_order_id;
update chapters set percent=8 where id=@chapter_iq_like_terms_id;
update chapters set percent=4 where id=@chapter_iq_water_image_id;
update chapters set percent=4 where id=@chapter_iq_mirror_image_id;
update chapters set percent=4 where id=@chapter_iq_identifying_similarities_id;
update chapters set percent=14 where id=@chapter_iq_logic_inference_id;
update chapters set percent=18 where id=@chapter_iq_puzzles_id;
update chapters set percent=8 where id=@chapter_iq_symbolic_language_id;
update chapters set percent=2 where id=@chapter_iq_special_question_id;


SET @class_5th_id = (SELECT id FROM class WHERE class_name = '5' LIMIT 1);

--  Chapters and Topics for "English – First Language" based on the PDF
SET @chapter_vocabulary_id = (SELECT id FROM chapters WHERE chapter_name = 'Vocabulary' AND subject_id = @subject_english_id AND class_id = @class_5th_id AND board_id = @board_msce_id LIMIT 1);
-- Chapter: Word Games (Unit 2)
SET @chapter_word_games_id = (SELECT id FROM chapters WHERE chapter_name = 'Word Games' AND subject_id = @subject_english_id AND class_id = @class_5th_id AND board_id = @board_msce_id LIMIT 1);
-- Chapter: Grammar (Unit 3)
SET @chapter_grammar_id = (SELECT id FROM chapters WHERE chapter_name = 'Grammar' AND subject_id = @subject_english_id AND class_id = @class_5th_id AND board_id = @board_msce_id LIMIT 1);
-- Chapter: Language Study (Unit 4)
SET @chapter_language_study_id = (SELECT id FROM chapters WHERE chapter_name = 'Language Study' AND subject_id = @subject_english_id AND class_id = @class_5th_id AND board_id = @board_msce_id LIMIT 1);
-- Chapter: Creative writing (Unit 5)
SET @chapter_creative_writing_id = (SELECT id FROM chapters WHERE chapter_name = 'Creative writing' AND subject_id = @subject_english_id AND class_id = @class_5th_id AND board_id = @board_msce_id LIMIT 1);
-- Chapter: Reading skills (comprehension) (Unit 6)
SET @chapter_reading_skills_id =(SELECT id FROM chapters WHERE chapter_name = 'Reading skills (comprehension)' AND subject_id = @subject_english_id AND class_id = @class_5th_id AND board_id = @board_msce_id LIMIT 1);
-- Chapter: Miscellaneous (Unit 7)
SET @chapter_miscellaneous_id = (SELECT id FROM chapters WHERE chapter_name = 'Miscellaneous' AND subject_id = @subject_english_id AND class_id = @class_5th_id AND board_id = @board_msce_id LIMIT 1);

update chapters set percent=15 where id=@chapter_vocabulary_id;
update chapters set percent=8 where id=@chapter_word_games_id;
update chapters set percent=24 where id=@chapter_grammar_id;
update chapters set percent=4 where id=@chapter_language_study_id;
update chapters set percent=16 where id=@chapter_creative_writing_id;
update chapters set percent=24 where id=@chapter_reading_skills_id;
update chapters set percent=8 where id=@chapter_miscellaneous_id;


--  Chapters and Topics for "Math – English" based on the PDF

-- Chapter: Number Work
SET @chapter_number_work_id = (SELECT id FROM chapters WHERE chapter_name = 'Number Work' AND subject_id = @subject_math_id AND class_id = @class_5_id AND board_id = @board_msce_id LIMIT 1);
-- Chapter: Operations On Numbers
SET @chapter_operations_id = (SELECT id FROM chapters WHERE chapter_name = 'Operations On Numbers' AND subject_id = @subject_math_id AND class_id = @class_5_id AND board_id = @board_msce_id LIMIT 1);
-- Chapter: Fractions
SET @chapter_fractions_id = (SELECT id FROM chapters WHERE chapter_name = 'Fractions' AND subject_id = @subject_math_id AND class_id = @class_5_id AND board_id = @board_msce_id LIMIT 1);
-- Chapter: Measurement / Mensuration
SET @chapter_measurement_id = (SELECT id FROM chapters WHERE chapter_name = 'Measurement / Mensuration' AND subject_id = @subject_math_id AND class_id = @class_5_id AND board_id = @board_msce_id LIMIT 1);
-- Chapter: Applied Mathematics
SET @chapter_applied_math_id = (SELECT id FROM chapters WHERE chapter_name = 'Applied Mathematics' AND subject_id = @subject_math_id AND class_id = @class_5_id AND board_id = @board_msce_id LIMIT 1);
-- Chapter: Geometry
SET @chapter_geometry_id = (SELECT id FROM chapters WHERE chapter_name = 'Geometry' AND subject_id = @subject_math_id AND class_id = @class_5_id AND board_id = @board_msce_id LIMIT 1);
-- Chapter: Pictographs
SET @chapter_pictographs_id = (SELECT id FROM chapters WHERE chapter_name = 'Pictographs' AND subject_id = @subject_math_id AND class_id = @class_5_id AND board_id = @board_msce_id LIMIT 1);

update chapters set percent=12 where id=@chapter_number_work_id;
update chapters set percent=20 where id=@chapter_operations_id;
update chapters set percent=14 where id=@chapter_fractions_id;
update chapters set percent=20 where id=@chapter_measurement_id;
update chapters set percent=16 where id=@chapter_applied_math_id;
update chapters set percent=14 where id=@chapter_geometry_id;
update chapters set percent=4 where id=@chapter_pictographs_id;



-- Insert Chapters and Topics for "Marathi – Third Language" based on the PDF

-- Chapter: आकलन (Aakalan - Comprehension)
SET @chapter_aakalan_id = (SELECT id FROM chapters WHERE chapter_name = 'आकलन' AND subject_id = @subject_marathi_id AND class_id = @class_5_id AND board_id = @board_msce_id LIMIT 1);
-- Chapter: शब्दसंपत्ती (Shabdasampatti - Vocabulary)
SET @chapter_shabdasampatti_id = (SELECT id FROM chapters WHERE chapter_name = 'शब्दसंपत्ती' AND subject_id = @subject_marathi_id AND class_id = @class_5_id AND board_id = @board_msce_id LIMIT 1);
-- Chapter: कार्यात्मक व्याकरण (Karyatmak Vyakaran - Functional Grammar)
SET @chapter_vyakaran_id = (SELECT id FROM chapters WHERE chapter_name = 'कार्यात्मक व्याकरण' AND subject_id = @subject_marathi_id AND class_id = @class_5_id AND board_id = @board_msce_id LIMIT 1);
-- Chapter: १ ली ते ५ वी मराठी (सुलभभारती) विषयांशी संबंधित सामान्यज्ञान (1st to 5th Std Marathi (Sulabhbharati) related General Knowledge)
SET @chapter_gk_id = (SELECT id FROM chapters WHERE chapter_name = '१ ली ते ५ वी मराठी (सुलभभारती) विषयांशी संबंधित सामान्यज्ञान' AND subject_id = @subject_marathi_id AND class_id = @class_5_id AND board_id = @board_msce_id LIMIT 1);

update chapters set percent=24 where id=@chapter_aakalan_id;
update chapters set percent=44 where id=@chapter_shabdasampatti_id;
update chapters set percent=24 where id=@chapter_vyakaran_id;
update chapters set percent=8 where id=@chapter_gk_id;



-- Chapters and Topics for "IQ – English" based on the PDF
-- Chapter: Comprehension
SET @chapter_comprehension_id = (SELECT id FROM chapters WHERE chapter_name = 'Comprehension' AND subject_id = @subject_iq_id AND class_id = @class_5_id AND board_id = @board_msce_id LIMIT 1);
-- Chapter: Classification
SET @chapter_classification_id = (SELECT id FROM chapters WHERE chapter_name = 'Classification' AND subject_id = @subject_iq_id AND class_id = @class_5_id AND board_id = @board_msce_id LIMIT 1);
-- Chapter: Number order
SET @chapter_number_order_id = (SELECT id FROM chapters WHERE chapter_name = 'Number order' AND subject_id = @subject_iq_id AND class_id = @class_5_id AND board_id = @board_msce_id LIMIT 1);
-- Chapter: Logic And Inference
SET @chapter_logic_inference_id = (SELECT id FROM chapters WHERE chapter_name = 'Logic And Inference' AND subject_id = @subject_iq_id AND class_id = @class_5_id AND board_id = @board_msce_id LIMIT 1);
-- Chapter: Image (Water Image, Mirror Image)
SET @chapter_image_id = (SELECT id FROM chapters WHERE chapter_name = 'Image (Water Image, Mirror Image)' AND subject_id = @subject_iq_id AND class_id = @class_5_id AND board_id = @board_msce_id LIMIT 1);
-- Chapter: Co-relation
SET @chapter_corelation_id = (SELECT id FROM chapters WHERE chapter_name = 'Co-relation' AND subject_id = @subject_iq_id AND class_id = @class_5_id AND board_id = @board_msce_id LIMIT 1);
-- Chapter: Identifying Similarities
SET @chapter_similarities_id = (SELECT id FROM chapters WHERE chapter_name = 'Identifying Similarities' AND subject_id = @subject_iq_id AND class_id = @class_5_id AND board_id = @board_msce_id LIMIT 1);
-- Chapter: Puzzles
SET @chapter_puzzles_id = (SELECT id FROM chapters WHERE chapter_name = 'Puzzles' AND subject_id = @subject_iq_id AND class_id = @class_5_id AND board_id = @board_msce_id LIMIT 1);
-- Chapter: Like Terms
SET @chapter_like_terms_id = (SELECT id FROM chapters WHERE chapter_name = 'Like Terms' AND subject_id = @subject_iq_id AND class_id = @class_5_id AND board_id = @board_msce_id LIMIT 1);
-- Chapter: Symbolic Language (Symbol)
SET @chapter_symbolic_language_id = (SELECT id FROM chapters WHERE chapter_name = 'Symbolic Language (Symbol)' AND subject_id = @subject_iq_id AND class_id = @class_5_id AND board_id = @board_msce_id LIMIT 1);
-- Chapter: Special Question Or Important
SET @chapter_special_question_id = (SELECT id FROM chapters WHERE chapter_name = 'Special Question Or Important' AND subject_id = @subject_iq_id AND class_id = @class_5_id AND board_id = @board_msce_id LIMIT 1);

update chapters set percent=8 where id=@chapter_comprehension_id;
update chapters set percent=10 where id=@chapter_classification_id;
update chapters set percent=10 where id=@chapter_number_order_id;
update chapters set percent=12 where id=@chapter_logic_inference_id;
update chapters set percent=8 where id=@chapter_image_id;
update chapters set percent=10 where id=@chapter_corelation_id;
update chapters set percent=4 where id=@chapter_similarities_id;
update chapters set percent=16 where id=@chapter_puzzles_id;
update chapters set percent=10 where id=@chapter_like_terms_id;
update chapters set percent=8 where id=@chapter_symbolic_language_id;
update chapters set percent=4 where id=@chapter_special_question_id;


-- 5. Chapters and Topics for "English – First Language" (7th Std, English Medium)
SET @class_7_id = (SELECT id FROM class WHERE class_name = '7' LIMIT 1);
-- Chapter: Vocabulary
SET @chapter_vocabulary_id = (SELECT id FROM chapters WHERE chapter_name = 'Vocabulary' AND subject_id = @subject_english_id AND class_id = @class_7_id AND board_id = @board_msce_id LIMIT 1);
-- Chapter: Word Puzzles Riddles
SET @chapter_word_puzzles_id = (SELECT id FROM chapters WHERE chapter_name = 'Word Puzzles Riddles' AND subject_id = @subject_english_id AND class_id = @class_7_id AND board_id = @board_msce_id LIMIT 1);
-- Chapter: Language Study
SET @chapter_language_study_id = (SELECT id FROM chapters WHERE chapter_name = 'Language Study' AND subject_id = @subject_english_id AND class_id = @class_7_id AND board_id = @board_msce_id LIMIT 1);
-- Chapter: Grammar
SET @chapter_grammar_id = (SELECT id FROM chapters WHERE chapter_name = 'Grammar' AND subject_id = @subject_english_id AND class_id = @class_7_id AND board_id = @board_msce_id LIMIT 1);
-- Chapter: Creative Writing
SET @chapter_creative_writing_id = (SELECT id FROM chapters WHERE chapter_name = 'Creative Writing' AND subject_id = @subject_english_id AND class_id = @class_7_id AND board_id = @board_msce_id LIMIT 1);
-- Chapter: Reading Skills (Comprehension)
SET @chapter_reading_skills_id = (SELECT id FROM chapters WHERE chapter_name = 'Reading Skills (Comprehension)' AND subject_id = @subject_english_id AND class_id = @class_7_id AND board_id = @board_msce_id LIMIT 1);
-- Chapter: Miscellaneous (Loan Words)
SET @chapter_misc_loan_words_id = (SELECT id FROM chapters WHERE chapter_name = 'Miscellaneous (Loan Words)' AND subject_id = @subject_english_id AND class_id = @class_7_id AND board_id = @board_msce_id LIMIT 1);

update chapters set percent=12 where id=@chapter_vocabulary_id;
update chapters set percent=4 where id=@chapter_word_puzzles_id;
update chapters set percent=16 where id=@chapter_language_study_id;
update chapters set percent=16 where id=@chapter_grammar_id;
update chapters set percent=24 where id=@chapter_creative_writing_id;
update chapters set percent=24 where id=@chapter_reading_skills_id;
update chapters set percent=4 where id=@chapter_misc_loan_words_id;

-- Chapters and Topics for "Math – English" (7th Std, English Medium)
-- Chapter: Number Work
SET @chapter_number_work_id = (SELECT id FROM chapters WHERE chapter_name = 'Number Work' AND subject_id = @subject_math_id AND class_id = @class_7_id AND board_id = @board_msce_id LIMIT 1);
-- Chapter: Operations on numbers
SET @chapter_operations_id = (SELECT id FROM chapters WHERE chapter_name = 'Operations on numbers' AND subject_id = @subject_math_id AND class_id = @class_7_id AND board_id = @board_msce_id LIMIT 1);
-- Chapter: Geometry
SET @chapter_geometry_id = (SELECT id FROM chapters WHERE chapter_name = 'Geometry' AND subject_id = @subject_math_id AND class_id = @class_7_id AND board_id = @board_msce_id LIMIT 1);
-- Chapter: Mensuration
SET @chapter_mensuration_id = (SELECT id FROM chapters WHERE chapter_name = 'Mensuration' AND subject_id = @subject_math_id AND class_id = @class_7_id AND board_id = @board_msce_id LIMIT 1);
-- Chapter: Statistics
SET @chapter_statistics_id = (SELECT id FROM chapters WHERE chapter_name = 'Statistics' AND subject_id = @subject_math_id AND class_id = @class_7_id AND board_id = @board_msce_id LIMIT 1);
-- Chapter: Applied Mathematics
SET @chapter_applied_math_id = (SELECT id FROM chapters WHERE chapter_name = 'Applied Mathematics' AND subject_id = @subject_math_id AND class_id = @class_7_id AND board_id = @board_msce_id LIMIT 1);
-- Chapter: Algebra
SET @chapter_algebra_id = (SELECT id FROM chapters WHERE chapter_name = 'Algebra' AND subject_id = @subject_math_id AND class_id = @class_7_id AND board_id = @board_msce_id LIMIT 1);

update chapters set percent=8 where id=@chapter_number_work_id;
update chapters set percent=18 where id=@chapter_operations_id;
update chapters set percent=22 where id=@chapter_geometry_id;
update chapters set percent=16 where id=@chapter_mensuration_id;
update chapters set percent=6 where id=@chapter_statistics_id;
update chapters set percent=14 where id=@chapter_applied_math_id;
update chapters set percent=16 where id=@chapter_algebra_id;


--  Chapters and Topics for "Marathi – Third Language" (7th Std, English Medium)

-- Chapter: आकलन (Comprehension)
SET @chapter_aakalan_id = (SELECT id FROM chapters WHERE chapter_name = 'आकलन' AND subject_id = @subject_marathi_id AND class_id = @class_7_id AND board_id = @board_msce_id LIMIT 1);
-- Chapter: शब्दसंपत्ती (Vocabulary)
SET @chapter_shabdasampatti_id = (SELECT id FROM chapters WHERE chapter_name = 'शब्दसंपत्ती' AND subject_id = @subject_marathi_id AND class_id = @class_7_id AND board_id = @board_msce_id LIMIT 1);
-- Chapter: कार्यात्मक व्याकरण (Functional Grammar)
SET @chapter_vyakaran_id = (SELECT id FROM chapters WHERE chapter_name = 'कार्यात्मक व्याकरण' AND subject_id = @subject_marathi_id AND class_id = @class_7_id AND board_id = @board_msce_id LIMIT 1);
-- Chapter: इ. 1 ली ते 7 वी मराठी विषयाशी संबंधित सामान्यज्ञान (General Knowledge related to Marathi subject for 1st to 7th Std)
SET @chapter_gk_id = (SELECT id FROM chapters WHERE chapter_name = 'इ. 1 ली ते 7 वी मराठी विषयाशी संबंधित सामान्यज्ञान' AND subject_id = @subject_marathi_id AND class_id = @class_7_id AND board_id = @board_msce_id LIMIT 1);

update chapters set percent=24 where id=@chapter_aakalan_id;
update chapters set percent=36 where id=@chapter_shabdasampatti_id;
update chapters set percent=32 where id=@chapter_vyakaran_id;
update chapters set percent=8 where id=@chapter_gk_id;



-- Subject 'IQ – English' if not exists and get its ID
SET @subject_iq_id = (SELECT subject_id FROM subject WHERE subject_name = 'IQ – English' LIMIT 1);

-- Chapters and Topics for "IQ – English" (7th Std, English Medium)

-- Chapter: Comprehension
SET @chapter_comprehension_id = (SELECT id FROM chapters WHERE chapter_name = 'Comprehension' AND subject_id = @subject_iq_id AND class_id = @class_7_id AND board_id = @board_msce_id LIMIT 1);
-- Chapter: Classification
SET @chapter_classification_id = (SELECT id FROM chapters WHERE chapter_name = 'Classification' AND subject_id = @subject_iq_id AND class_id = @class_7_id AND board_id = @board_msce_id LIMIT 1);
-- Chapter: Co-relation
SET @chapter_corelation_id = (SELECT id FROM chapters WHERE chapter_name = 'Co-relation' AND subject_id = @subject_iq_id AND class_id = @class_7_id AND board_id = @board_msce_id LIMIT 1);
-- Chapter: Series (Order)
SET @chapter_series_order_id = (SELECT id FROM chapters WHERE chapter_name = 'Series (Order)' AND subject_id = @subject_iq_id AND class_id = @class_7_id AND board_id = @board_msce_id LIMIT 1);
-- Chapter: Like terms
SET @chapter_like_terms_id = (SELECT id FROM chapters WHERE chapter_name = 'Like terms' AND subject_id = @subject_iq_id AND class_id = @class_7_id AND board_id = @board_msce_id LIMIT 1);
-- Chapter: Rhythm and Sequence
SET @chapter_rhythm_sequence_id = (SELECT id FROM chapters WHERE chapter_name = 'Rhythm and Sequence' AND subject_id = @subject_iq_id AND class_id = @class_7_id AND board_id = @board_msce_id LIMIT 1);
-- Chapter: Water image
SET @chapter_water_image_id = (SELECT id FROM chapters WHERE chapter_name = 'Water image' AND subject_id = @subject_iq_id AND class_id = @class_7_id AND board_id = @board_msce_id LIMIT 1);
-- Chapter: Mirror Image
SET @chapter_mirror_image_id = (SELECT id FROM chapters WHERE chapter_name = 'Mirror Image' AND subject_id = @subject_iq_id AND class_id = @class_7_id AND board_id = @board_msce_id LIMIT 1);
-- Chapter: Logic and Conclusion
SET @chapter_logic_conclusion_id = (SELECT id FROM chapters WHERE chapter_name = 'Logic and Conclusion' AND subject_id = @subject_iq_id AND class_id = @class_7_id AND board_id = @board_msce_id LIMIT 1);
-- Chapter: Puzzles and Brain Teasers
SET @chapter_puzzles_id = (SELECT id FROM chapters WHERE chapter_name = 'Puzzles and Brain Teasers' AND subject_id = @subject_iq_id AND class_id = @class_7_id AND board_id = @board_msce_id LIMIT 1);
-- Chapter: Code Language
SET @chapter_code_language_id = (SELECT id FROM chapters WHERE chapter_name = 'Code Language' AND subject_id = @subject_iq_id AND class_id = @class_7_id AND board_id = @board_msce_id LIMIT 1);
-- Chapter: Pyramids / Structures
SET @chapter_pyramids_id = (SELECT id FROM chapters WHERE chapter_name = 'Pyramids / Structures' AND subject_id = @subject_iq_id AND class_id = @class_7_id AND board_id = @board_msce_id LIMIT 1);
-- Chapter: Analysis of Figure
SET @chapter_analysis_figure_id = (SELECT id FROM chapters WHERE chapter_name = 'Analysis of Figure' AND subject_id = @subject_iq_id AND class_id = @class_7_id AND board_id = @board_msce_id LIMIT 1);

update chapters set percent=8 where id=@chapter_comprehension_id;
update chapters set percent=10 where id=@chapter_classification_id;
update chapters set percent=10 where id=@chapter_corelation_id;
update chapters set percent=10 where id=@chapter_series_order_id;
update chapters set percent=6 where id=@chapter_like_terms_id;
update chapters set percent=4 where id=@chapter_rhythm_sequence_id;
update chapters set percent=4 where id=@chapter_water_image_id;
update chapters set percent=4 where id=@chapter_mirror_image_id;
update chapters set percent=12 where id=@chapter_logic_conclusion_id;
update chapters set percent=14 where id=@chapter_puzzles_id;
update chapters set percent=6 where id=@chapter_code_language_id;
update chapters set percent=4 where id=@chapter_pyramids_id;
update chapters set percent=8 where id=@chapter_analysis_figure_id;



-- 8th Standard, English Medium 

-- 2. Insert Class 8 if not exists and get its ID
SET @class_8_id = (SELECT id FROM class WHERE class_name = '8' LIMIT 1);

-- 3. Insert Subject 'English – First Language' if not exists and get its ID
SET @subject_english_fl_id = (SELECT subject_id FROM subject WHERE subject_name = 'English – First Language' LIMIT 1);
	
-- Chapters and Topics for "English – First Language" (8th Std, English Medium)

-- Chapter: Vocabulary
SET @chapter_vocabulary_id = (SELECT id FROM chapters WHERE chapter_name = 'Vocabulary' AND subject_id = @subject_english_fl_id AND class_id = @class_8_id AND board_id = @board_msce_id LIMIT 1);
-- Chapter: Word Puzzles Riddles
SET @chapter_word_puzzles_id = (SELECT id FROM chapters WHERE chapter_name = 'Word Puzzles Riddles' AND subject_id = @subject_english_fl_id AND class_id = @class_8_id AND board_id = @board_msce_id LIMIT 1);
-- Chapter: Language Study
SET @chapter_language_study_id = (SELECT id FROM chapters WHERE chapter_name = 'Language Study' AND subject_id = @subject_english_fl_id AND class_id = @class_8_id AND board_id = @board_msce_id LIMIT 1);
-- Chapter: Grammar
SET @chapter_grammar_id = (SELECT id FROM chapters WHERE chapter_name = 'Grammar' AND subject_id = @subject_english_fl_id AND class_id = @class_8_id AND board_id = @board_msce_id LIMIT 1);
-- Chapter: Creative Writing
SET @chapter_creative_writing_id = (SELECT id FROM chapters WHERE chapter_name = 'Creative Writing' AND subject_id = @subject_english_fl_id AND class_id = @class_8_id AND board_id = @board_msce_id LIMIT 1);
-- Chapter: Reading Skills (Comprehension)
SET @chapter_reading_skills_id = (SELECT id FROM chapters WHERE chapter_name = 'Reading Skills (Comprehension)' AND subject_id = @subject_english_fl_id AND class_id = @class_8_id AND board_id = @board_msce_id LIMIT 1);
-- Chapter: Miscellaneous (Loan Words)
SET @chapter_misc_loan_words_id = (SELECT id FROM chapters WHERE chapter_name = 'Miscellaneous (Loan Words)' AND subject_id = @subject_english_fl_id AND class_id = @class_8_id AND board_id = @board_msce_id LIMIT 1);

update chapters set percent=12 where id=@chapter_vocabulary_id;
update chapters set percent=4 where id=@chapter_word_puzzles_id;
update chapters set percent=16 where id=@chapter_language_study_id;
update chapters set percent=16 where id=@chapter_grammar_id;
update chapters set percent=24 where id=@chapter_creative_writing_id;
update chapters set percent=24 where id=@chapter_reading_skills_id;
update chapters set percent=4 where id=@chapter_misc_loan_words_id;

--  Subject 'Math – English' if not exists and get its ID
SET @subject_math_english_id = (SELECT subject_id FROM subject WHERE subject_name = 'Math – English' LIMIT 1);

--  Chapters and Topics for "Math – English" (8th Std, English Medium)

-- Chapter: Number Work
SET @chapter_number_work_id = (SELECT id FROM chapters WHERE chapter_name = 'Number Work' AND subject_id = @subject_math_english_id AND class_id = @class_8_id AND board_id = @board_msce_id LIMIT 1);
-- Chapter: Operations on numbers
SET @chapter_operations_id = (SELECT id FROM chapters WHERE chapter_name = 'Operations on numbers' AND subject_id = @subject_math_english_id AND class_id = @class_8_id AND board_id = @board_msce_id LIMIT 1);
-- Chapter: Geometry
SET @chapter_geometry_id = (SELECT id FROM chapters WHERE chapter_name = 'Geometry' AND subject_id = @subject_math_english_id AND class_id = @class_8_id AND board_id = @board_msce_id LIMIT 1);
-- Chapter: Mensuration
SET @chapter_mensuration_id = (SELECT id FROM chapters WHERE chapter_name = 'Mensuration' AND subject_id = @subject_math_english_id AND class_id = @class_8_id AND board_id = @board_msce_id LIMIT 1);
-- Chapter: Statistics
SET @chapter_statistics_id = (SELECT id FROM chapters WHERE chapter_name = 'Statistics' AND subject_id = @subject_math_english_id AND class_id = @class_8_id AND board_id = @board_msce_id LIMIT 1);
-- Chapter: Applied mathematics
SET @chapter_applied_math_id = (SELECT id FROM chapters WHERE chapter_name = 'Applied mathematics' AND subject_id = @subject_math_english_id AND class_id = @class_8_id AND board_id = @board_msce_id LIMIT 1);
-- Chapter: Algebra
SET @chapter_algebra_id = (SELECT id FROM chapters WHERE chapter_name = 'Algebra' AND subject_id = @subject_math_english_id AND class_id = @class_8_id AND board_id = @board_msce_id LIMIT 1);

update chapters set percent=6 where id=@chapter_number_work_id;
update chapters set percent=14 where id=@chapter_operations_id;
update chapters set percent=20 where id=@chapter_geometry_id;
update chapters set percent=20 where id=@chapter_mensuration_id;
update chapters set percent=6 where id=@chapter_statistics_id;
update chapters set percent=16 where id=@chapter_applied_math_id;
update chapters set percent=18	 where id=@chapter_algebra_id;


---PRE UPPER PRIMARY SCHOLARSHIP EXAMINATION – Std 8 – English Medium, including Marathi (Third Language)
-- CLASS: 8th
SET @class_8th_id = (
  SELECT id FROM class WHERE class_name = '8' LIMIT 1
);


SET @sub_mar  = (SELECT subject_id FROM subject WHERE subject_name='Marathi – Third Language');

--Marathi Chapters (Units)
SET @ch_akal   = (SELECT id FROM chapters WHERE chapter_name='आकलन' AND subject_id=@sub_mar and class_id=@class_8th_id AND board_id = @board_msce_id);
SET @ch_shabd  = (SELECT id FROM chapters WHERE chapter_name='शब्दसंपत्ती' AND subject_id=@sub_mar and class_id=@class_8th_id AND board_id = @board_msce_id);
SET @ch_vyak   = (SELECT id FROM chapters WHERE chapter_name='कार्यात्मक व्याकरण' AND subject_id=@sub_mar and class_id=@class_8th_id AND board_id = @board_msce_id);
SET @ch_gk     = (SELECT id FROM chapters WHERE chapter_name LIKE 'सामान्य ज्ञान%' AND subject_id=@sub_mar and class_id=@class_8th_id AND board_id = @board_msce_id);

update chapters set percent=24 where id=@ch_akal;
update chapters set percent=36 where id=@ch_shabd;
update chapters set percent=32 where id=@ch_vyak;
update chapters set percent=8 where id=@ch_gk;

-- SUBJECT: IQ – English
SET @sub_iq = (
  SELECT subject_id FROM subject WHERE subject_name = 'IQ – English' LIMIT 1
);

-- IQ Chapters (Units)
SET @ch_comp  = (SELECT id FROM chapters WHERE chapter_name='Comprehension' AND subject_id=@sub_iq and class_id=@class_8th_id AND board_id = @board_msce_id);
SET @ch_class = (SELECT id FROM chapters WHERE chapter_name='Classification' AND subject_id=@sub_iq and class_id=@class_8th_id AND board_id = @board_msce_id);
SET @ch_corr  = (SELECT id FROM chapters WHERE chapter_name='Correlation' AND subject_id=@sub_iq and class_id=@class_8th_id AND board_id = @board_msce_id);
SET @ch_series= (SELECT id FROM chapters WHERE chapter_name='Series (Order)' AND subject_id=@sub_iq and class_id=@class_8th_id AND board_id = @board_msce_id);
SET @ch_code  = (SELECT id FROM chapters WHERE chapter_name='Code Language' AND subject_id=@sub_iq and class_id=@class_8th_id AND board_id = @board_msce_id);
SET @ch_rhythm= (SELECT id FROM chapters WHERE chapter_name='Rhythm and Sequence' AND subject_id=@sub_iq and class_id=@class_8th_id AND board_id = @board_msce_id);
SET @ch_pyr   = (SELECT id FROM chapters WHERE chapter_name='Pyramids' AND subject_id=@sub_iq and class_id=@class_8th_id AND board_id = @board_msce_id);
SET @ch_ref   = (SELECT id FROM chapters WHERE chapter_name LIKE 'Reflection%' AND subject_id=@sub_iq and class_id=@class_8th_id AND board_id = @board_msce_id);
SET @ch_logic = (SELECT id FROM chapters WHERE chapter_name='Logic and Conclusion' AND subject_id=@sub_iq and class_id=@class_8th_id AND board_id = @board_msce_id);
SET @ch_puzz  = (SELECT id FROM chapters WHERE chapter_name='Puzzles and Brain Teasers' AND subject_id=@sub_iq and class_id=@class_8th_id AND board_id = @board_msce_id);
SET @ch_fig   = (SELECT id FROM chapters WHERE chapter_name='Analysis of Figure' AND subject_id=@sub_iq and class_id=@class_8th_id AND board_id = @board_msce_id);

update chapters set percent=10 where id=@ch_comp;
update chapters set percent=10 where id=@ch_class;
update chapters set percent=10 where id=@ch_corr;
update chapters set percent=12 where id=@ch_series;
update chapters set percent=6 where id=@ch_code;
update chapters set percent=6 where id=@ch_rhythm;
update chapters set percent=6 where id=@ch_pyr;
update chapters set percent=6 where id=@ch_ref;
update chapters set percent=12 where id=@ch_logic;
update chapters set percent=12 where id=@ch_puzz;
update chapters set percent=10 where id=@ch_fig;


---Starting Marathi Mediaum
-- 1. Insert Board (MSCE) if not exists and get its ID
SET @board_msce_id = (SELECT id FROM board WHERE board_name = 'MSCE' LIMIT 1);

-- 2. Insert Class 4 if not exists and get its ID
SET @class_4_id = (SELECT id FROM class WHERE class_name = '4' LIMIT 1);

-- 3. Insert Subject 'Marathi – First Language' if not exists and get its ID
SET @subject_marathi_fl_id = (SELECT subject_id FROM subject WHERE subject_name = 'Marathi – First Language' LIMIT 1);

-- Chapters and Topics for "Marathi – First Language" (4th Std, Marathi Medium)
-- Chapter: आकलन (Comprehension)
SET @chapter_aakalan_id = (SELECT id FROM chapters WHERE chapter_name = 'आकलन' AND subject_id = @subject_marathi_fl_id AND class_id = @class_4_id AND board_id = @board_msce_id LIMIT 1);
-- Chapter: शब्दसंपत्तीवरील प्रभुत्व (Mastery over Vocabulary)
SET @chapter_shabdasampatti_id = (SELECT id FROM chapters WHERE chapter_name = 'शब्दसंपत्तीवरील प्रभुत्व' AND subject_id = @subject_marathi_fl_id AND class_id = @class_4_id AND board_id = @board_msce_id LIMIT 1);
-- Chapter: कार्यात्मक व्याकरण (Functional Grammar)
SET @chapter_vyakaran_id = (SELECT id FROM chapters WHERE chapter_name = 'कार्यात्मक व्याकरण' AND subject_id = @subject_marathi_fl_id AND class_id = @class_4_id AND board_id = @board_msce_id LIMIT 1);
-- Chapter: भाषाविषयक सामान्यज्ञान (Language related General Knowledge)
SET @chapter_gk_id = (SELECT id FROM chapters WHERE chapter_name = 'भाषाविषयक सामान्यज्ञान' AND subject_id = @subject_marathi_fl_id AND class_id = @class_4_id AND board_id = @board_msce_id LIMIT 1);

update chapters set percent=24 where id=@chapter_aakalan_id;
update chapters set percent=40 where id=@chapter_shabdasampatti_id;
update chapters set percent=32 where id=@chapter_vyakaran_id;
update chapters set percent=4 where id=@chapter_gk_id;

-- Here is the SQL script to insert the syllabus data for "Math – Marathi" for the 4th Standard, Marathi Medium, based on the provided PDF document. This script ensures all Marathi content is handled correctly with UTF-8 encoding.
-- 3. Insert Subject 'Math – Marathi' if not exists and get its ID
SET @subject_math_marathi_id = (SELECT subject_id FROM subject WHERE subject_name = 'Math – Marathi' LIMIT 1);

-- Chapters and Topics for "Math – Marathi" (4th Std, Marathi Medium)
-- Chapter: संख्याज्ञान (Number Work)
SET @chapter_sankhyagnan_id = (SELECT id FROM chapters WHERE chapter_name = 'संख्याज्ञान' AND subject_id = @subject_math_marathi_id AND class_id = @class_4_id AND board_id = @board_msce_id LIMIT 1);
-- Chapter: संख्यांवरील क्रिया (Operations on numbers)
SET @chapter_operations_on_numbers_id = (SELECT id FROM chapters WHERE chapter_name = 'संख्यांवरील क्रिया' AND subject_id = @subject_math_marathi_id AND class_id = @class_4_id AND board_id = @board_msce_id LIMIT 1);
-- Chapter: अपूर्णांक (Fractions)
SET @chapter_fractions_id = (SELECT id FROM chapters WHERE chapter_name = 'अपूर्णांक' AND subject_id = @subject_math_marathi_id AND class_id = @class_4_id AND board_id = @board_msce_id LIMIT 1);
-- Chapter: मापन / महत्त्वमापन (Measurement / Importance of Measurement)
SET @chapter_measurement_id = (SELECT id FROM chapters WHERE chapter_name = 'मापन / महत्त्वमापन' AND subject_id = @subject_math_marathi_id AND class_id = @class_4_id AND board_id = @board_msce_id LIMIT 1);
-- Chapter: आकृतिबंध (Patterns)
SET @chapter_patterns_id = (SELECT id FROM chapters WHERE chapter_name = 'आकृतिबंध' AND subject_id = @subject_math_marathi_id AND class_id = @class_4_id AND board_id = @board_msce_id LIMIT 1);
-- Chapter: भूमिती (Geometry)
SET @chapter_geometry_id = (SELECT id FROM chapters WHERE chapter_name = 'भूमिती' AND subject_id = @subject_math_marathi_id AND class_id = @class_4_id AND board_id = @board_msce_id LIMIT 1);
-- Chapter: चित्रालेख (Pictograph)
SET @chapter_pictograph_id = (SELECT id FROM chapters WHERE chapter_name = 'चित्रालेख' AND subject_id = @subject_math_marathi_id AND class_id = @class_4_id AND board_id = @board_msce_id LIMIT 1);


update chapters set percent=16 where id=@chapter_sankhyagnan_id;
update chapters set percent=20 where id=@chapter_operations_on_numbers_id;
update chapters set percent=12 where id=@chapter_fractions_id;
update chapters set percent=20 where id=@chapter_measurement_id;
update chapters set percent=8 where id=@chapter_patterns_id;
update chapters set percent=18 where id=@chapter_geometry_id;
update chapters set percent=6 where id=@chapter_pictograph_id;

-- Subject 'English – Third Language' if not exists and get its ID
SET @subject_english_third_lang_id = (SELECT subject_id FROM subject WHERE subject_name = 'English – Third Language' LIMIT 1);

-- Chapters and Topics for "English – Third Language" (4th Std, Marathi Medium)

-- Chapter: Letters of Alphabets
SET @chapter_letters_alphabets_id = (SELECT id FROM chapters WHERE chapter_name = 'Letters of Alphabets' AND subject_id = @subject_english_third_lang_id AND class_id = @class_4_id AND board_id = @board_msce_id LIMIT 1);
-- Chapter: Vocabulary
SET @chapter_vocabulary_id = (SELECT id FROM chapters WHERE chapter_name = 'Vocabulary' AND subject_id = @subject_english_third_lang_id AND class_id = @class_4_id AND board_id = @board_msce_id LIMIT 1);
-- Chapter: Punctuation Marks
SET @chapter_punctuation_id = (SELECT id FROM chapters WHERE chapter_name = 'Punctuation Marks' AND subject_id = @subject_english_third_lang_id AND class_id = @class_4_id AND board_id = @board_msce_id LIMIT 1);
-- Chapter: Numerical Information
SET @chapter_numerical_info_id = (SELECT id FROM chapters WHERE chapter_name = 'Numerical Information' AND subject_id = @subject_english_third_lang_id AND class_id = @class_4_id AND board_id = @board_msce_id LIMIT 1);
-- Chapter: Creative Thinking
SET @chapter_creative_thinking_id = (SELECT id FROM chapters WHERE chapter_name = 'Creative Thinking' AND subject_id = @subject_english_third_lang_id AND class_id = @class_4_id AND board_id = @board_msce_id LIMIT 1);
-- Chapter: Stock Expressions
SET @chapter_stock_expressions_id = (SELECT id FROM chapters WHERE chapter_name = 'Stock Expressions' AND subject_id = @subject_english_third_lang_id AND class_id = @class_4_id AND board_id = @board_msce_id LIMIT 1);
-- Chapter: Grammar
SET @chapter_grammar_id = (SELECT id FROM chapters WHERE chapter_name = 'Grammar' AND subject_id = @subject_english_third_lang_id AND class_id = @class_4_id AND board_id = @board_msce_id LIMIT 1);
-- Chapter: Comprehension
SET @chapter_comprehension_id = (SELECT id FROM chapters WHERE chapter_name = 'Comprehension' AND subject_id = @subject_english_third_lang_id AND class_id = @class_4_id AND board_id = @board_msce_id LIMIT 1);


update chapters set percent=4 where id=@chapter_letters_alphabets_id;
update chapters set percent=28 where id=@chapter_vocabulary_id;
update chapters set percent=12 where id=@chapter_punctuation_id;
update chapters set percent=12 where id=@chapter_numerical_info_id;
update chapters set percent=12 where id=@chapter_creative_thinking_id;
update chapters set percent=8 where id=@chapter_stock_expressions_id;
update chapters set percent=16 where id=@chapter_grammar_id;
update chapters set percent=8 where id=@chapter_comprehension_id;

-- Subject 'IQ – Marathi' if not exists and get its ID
SET @subject_iq_marathi_id = (SELECT subject_id FROM subject WHERE subject_name = 'IQ – Marathi' LIMIT 1);

-- Chapters and Topics for "IQ – Marathi" (4th Std, Marathi Medium)

-- Chapter: आकलन (Comprehension)
SET @chapter_aakalan_id = (SELECT id FROM chapters WHERE chapter_name = 'आकलन' AND subject_id = @subject_iq_marathi_id AND class_id = @class_4_id AND board_id = @board_msce_id LIMIT 1);
-- Chapter: वर्गीकरण (Classification)
SET @chapter_vargikaran_id = (SELECT id FROM chapters WHERE chapter_name = 'वर्गीकरण' AND subject_id = @subject_iq_marathi_id AND class_id = @class_4_id AND board_id = @board_msce_id LIMIT 1);
-- Chapter: समसंबंध (Co-relation)
SET @chapter_samasambandh_id = (SELECT id FROM chapters WHERE chapter_name = 'समसंबंध' AND subject_id = @subject_iq_marathi_id AND class_id = @class_4_id AND board_id = @board_msce_id LIMIT 1);
-- Chapter: क्रम ओळखणे (Series)
SET @chapter_kram_olakne_id = (SELECT id FROM chapters WHERE chapter_name = 'क्रम ओळखणे' AND subject_id = @subject_iq_marathi_id AND class_id = @class_4_id AND board_id = @board_msce_id LIMIT 1);
-- Chapter: गटाशी जुळणारे पद (Matching terms in a group)
SET @chapter_gatashi_julnare_pad_id = (SELECT id FROM chapters WHERE chapter_name = 'गटाशी जुळणारे पद' AND subject_id = @subject_iq_marathi_id AND class_id = @class_4_id AND board_id = @board_msce_id LIMIT 1);
-- Chapter: जलप्रतिबिंब (Water image)
SET @chapter_jalpratibimb_id = (SELECT id FROM chapters WHERE chapter_name = 'जलप्रतिबिंब' AND subject_id = @subject_iq_marathi_id AND class_id = @class_4_id AND board_id = @board_msce_id LIMIT 1);
-- Chapter: आरशातील प्रतिमा (Mirror image)
SET @chapter_arshatil_pratima_id = (SELECT id FROM chapters WHERE chapter_name = 'आरशातील प्रतिमा' AND subject_id = @subject_iq_marathi_id AND class_id = @class_4_id AND board_id = @board_msce_id LIMIT 1);
-- Chapter: समान पद ओळखणे (Identify similar terms)
SET @chapter_saman_pad_olakne_id = (SELECT id FROM chapters WHERE chapter_name = 'समान पद ओळखणे' AND subject_id = @subject_iq_marathi_id AND class_id = @class_4_id AND board_id = @board_msce_id LIMIT 1);
-- Chapter: तर्कसंगती व अनुमान (Reasoning and Inference)
SET @chapter_tarkasanti_anuman_id = (SELECT id FROM chapters WHERE chapter_name = 'तर्कसंगती व अनुमान' AND subject_id = @subject_iq_marathi_id AND class_id = @class_4_id AND board_id = @board_msce_id LIMIT 1);
-- Chapter: कूटप्रश्न (Puzzles)
SET @chapter_kutaprashna_id = (SELECT id FROM chapters WHERE chapter_name = 'कूटप्रश्न' AND subject_id = @subject_iq_marathi_id AND class_id = @class_4_id AND board_id = @board_msce_id LIMIT 1);
-- Chapter: सांकेतिक भाषा (Symbolic Language)
SET @chapter_sanketik_bhasha_id = (SELECT id FROM chapters WHERE chapter_name = 'सांकेतिक भाषा' AND subject_id = @subject_iq_marathi_id AND class_id = @class_4_id AND board_id = @board_msce_id LIMIT 1);
-- Chapter: विशेष प्रश्न (Special Questions)
SET @chapter_vishesh_prashna_id = (SELECT id FROM chapters WHERE chapter_name = 'विशेष प्रश्न' AND subject_id = @subject_iq_marathi_id AND class_id = @class_4_id AND board_id = @board_msce_id LIMIT 1);

update chapters set percent=8 where id=@chapter_aakalan_id;
update chapters set percent=10 where id=@chapter_vargikaran_id;
update chapters set percent=10 where id=@chapter_samasambandh_id;
update chapters set percent=10 where id=@chapter_kram_olakne_id;
update chapters set percent=8 where id=@chapter_gatashi_julnare_pad_id;
update chapters set percent=4 where id=@chapter_jalpratibimb_id;
update chapters set percent=4 where id=@chapter_arshatil_pratima_id;
update chapters set percent=4 where id=@chapter_saman_pad_olakne_id;
update chapters set percent=14 where id=@chapter_tarkasanti_anuman_id;
update chapters set percent=18 where id=@chapter_kutaprashna_id;
update chapters set percent=8 where id=@chapter_sanketik_bhasha_id;
update chapters set percent=2 where id=@chapter_vishesh_prashna_id;


-- MSCE PRE UPPER PRIMARY SCHOLARSHIP EXAMINATION - CLASS 5 Marathi Medium
-- Complete Ready-to-Run Script (Class ID from class_name='5')

--  Class 5 if not exists and get its ID
SET @class_5_id = (SELECT id FROM class WHERE class_name = '5' LIMIT 1);

-- Subject 'Marathi – First Language' if not exists and get its ID
SET @subject_marathi_fl_id = (SELECT subject_id FROM subject WHERE subject_name = 'Marathi – First Language' LIMIT 1);


-- Chapters and Topics for "Marathi – First Language" (5th Std, Marathi Medium)

-- Chapter: वाचून कल्पना व संकल्पना स्पष्ट करणे (Read and clarify ideas and concepts)
SET @chapter_reading_comprehension_id = (SELECT id FROM chapters WHERE chapter_name = 'वाचून कल्पना व संकल्पना स्पष्ट करणे' AND subject_id = @subject_marathi_fl_id AND class_id = @class_5_id AND board_id = @board_msce_id LIMIT 1);
-- Chapter: कार्यात्मक व्याकरण (Functional Grammar)
SET @chapter_functional_grammar_id = (SELECT id FROM chapters WHERE chapter_name = 'कार्यात्मक व्याकरण' AND subject_id = @subject_marathi_fl_id AND class_id = @class_5_id AND board_id = @board_msce_id LIMIT 1);
-- Chapter: भाषेचा व्यवहारात उपयोग (Practical Use of Language)
SET @chapter_practical_language_id = (SELECT id FROM chapters WHERE chapter_name = 'भाषेचा व्यवहारात उपयोग' AND subject_id = @subject_marathi_fl_id AND class_id = @class_5_id AND board_id = @board_msce_id LIMIT 1);
-- Chapter: शब्दसंपत्तीवरील प्रभुत्व (Mastery over Vocabulary)
SET @chapter_vocabulary_mastery_id = (SELECT id FROM chapters WHERE chapter_name = 'शब्दसंपत्तीवरील प्रभुत्व' AND subject_id = @subject_marathi_fl_id AND class_id = @class_5_id AND board_id = @board_msce_id LIMIT 1);

update chapters set percent=24 where id=@chapter_reading_comprehension_id;
update chapters set percent=20 where id=@chapter_functional_grammar_id;
update chapters set percent=24 where id=@chapter_practical_language_id;
update chapters set percent=32 where id=@chapter_vocabulary_mastery_id;


-- Subject 'Math – Marathi' if not exists and get its ID
SET @subject_math_marathi_id = (SELECT subject_id FROM subject WHERE subject_name = 'Math – Marathi' LIMIT 1);

-- Chapters and Topics for "Math – Marathi" (5th Std, Marathi Medium)

-- Chapter: संख्याज्ञान (Number Knowledge)
SET @chapter_sankhyagnan_id = (SELECT id FROM chapters WHERE chapter_name = 'संख्याज्ञान' AND subject_id = @subject_math_marathi_id AND class_id = @class_5_id AND board_id = @board_msce_id LIMIT 1);
-- Chapter: संख्यांवरील क्रिया (Operations on Numbers)
SET @chapter_operations_on_numbers_id = (SELECT id FROM chapters WHERE chapter_name = 'संख्यांवरील क्रिया' AND subject_id = @subject_math_marathi_id AND class_id = @class_5_id AND board_id = @board_msce_id LIMIT 1);
-- Chapter: अपूर्णांक (Fractions)
SET @chapter_fractions_id = (SELECT id FROM chapters WHERE chapter_name = 'अपूर्णांक' AND subject_id = @subject_math_marathi_id AND class_id = @class_5_id AND board_id = @board_msce_id LIMIT 1);
-- Chapter: मापन/महत्त्वमापन (Measurement/Metrology)
SET @chapter_measurement_id = (SELECT id FROM chapters WHERE chapter_name = 'मापन/महत्त्वमापन' AND subject_id = @subject_math_marathi_id AND class_id = @class_5_id AND board_id = @board_msce_id LIMIT 1);
-- Chapter: व्यावहारिक गणित (Practical Mathematics)
SET @chapter_practical_math_id = (SELECT id FROM chapters WHERE chapter_name = 'व्यावहारिक गणित' AND subject_id = @subject_math_marathi_id AND class_id = @class_5_id AND board_id = @board_msce_id LIMIT 1);
-- Chapter: भूमिती (Geometry)
SET @chapter_geometry_id = (SELECT id FROM chapters WHERE chapter_name = 'भूमिती' AND subject_id = @subject_math_marathi_id AND class_id = @class_5_id AND board_id = @board_msce_id LIMIT 1);
-- Chapter: चित्रालेख (Pictograph)
SET @chapter_pictograph_id = (SELECT id FROM chapters WHERE chapter_name = 'चित्रालेख' AND subject_id = @subject_math_marathi_id AND class_id = @class_5_id AND board_id = @board_msce_id LIMIT 1);

update chapters set percent=12 where id=@chapter_sankhyagnan_id;
update chapters set percent=20 where id=@chapter_operations_on_numbers_id;
update chapters set percent=14 where id=@chapter_fractions_id;
update chapters set percent=20 where id=@chapter_measurement_id;
update chapters set percent=16 where id=@chapter_practical_math_id;
update chapters set percent=14 where id=@chapter_geometry_id;
update chapters set percent=4 where id=@chapter_pictograph_id;


-- Subject 'English – Third Language' if not exists and get its ID
SET @subject_english_third_lang_id = (SELECT subject_id FROM subject WHERE subject_name = 'English – Third Language' LIMIT 1);

-- Chapters and Topics for "English – Third Language" (5th Std, Marathi Medium)

-- Chapter: Letters of Alphabet
SET @chapter_letters_alphabet_id = (SELECT id FROM chapters WHERE chapter_name = 'Letters of Alphabet' AND subject_id = @subject_english_third_lang_id AND class_id = @class_5_id AND board_id = @board_msce_id LIMIT 1);
-- Chapter: Vocabulary
SET @chapter_vocabulary_id = (SELECT id FROM chapters WHERE chapter_name = 'Vocabulary' AND subject_id = @subject_english_third_lang_id AND class_id = @class_5_id AND board_id = @board_msce_id LIMIT 1);
-- Chapter: Punctuation marks
SET @chapter_punctuation_marks_id = (SELECT id FROM chapters WHERE chapter_name = 'Punctuation marks' AND subject_id = @subject_english_third_lang_id AND class_id = @class_5_id AND board_id = @board_msce_id LIMIT 1);
-- Chapter: Numerical Information
SET @chapter_numerical_information_id = (SELECT id FROM chapters WHERE chapter_name = 'Numerical Information' AND subject_id = @subject_english_third_lang_id AND class_id = @class_5_id AND board_id = @board_msce_id LIMIT 1);
-- Chapter: Creative Thinking
SET @chapter_creative_thinking_id = (SELECT id FROM chapters WHERE chapter_name = 'Creative Thinking' AND subject_id = @subject_english_third_lang_id AND class_id = @class_5_id AND board_id = @board_msce_id LIMIT 1);
-- Chapter: Stock expressions
SET @chapter_stock_expressions_id = (SELECT id FROM chapters WHERE chapter_name = 'Stock expressions' AND subject_id = @subject_english_third_lang_id AND class_id = @class_5_id AND board_id = @board_msce_id LIMIT 1);
-- Chapter: Miscellaneous
SET @chapter_miscellaneous_id = (SELECT id FROM chapters WHERE chapter_name = 'Miscellaneous' AND subject_id = @subject_english_third_lang_id AND class_id = @class_5_id AND board_id = @board_msce_id LIMIT 1);
-- Chapter: Comprehension (Reading Skill)
SET @chapter_comprehension_reading_skill_id = (SELECT id FROM chapters WHERE chapter_name = 'Comprehension (Reading Skill)' AND subject_id = @subject_english_third_lang_id AND class_id = @class_5_id AND board_id = @board_msce_id LIMIT 1);

update chapters set percent=4 where id=@chapter_letters_alphabet_id;
update chapters set percent=24 where id=@chapter_vocabulary_id;
update chapters set percent=12 where id=@chapter_punctuation_marks_id;
update chapters set percent=12 where id=@chapter_numerical_information_id;
update chapters set percent=12 where id=@chapter_creative_thinking_id;
update chapters set percent=12 where id=@chapter_stock_expressions_id;
update chapters set percent=12 where id=@chapter_miscellaneous_id;
update chapters set percent=12 where id=@chapter_comprehension_reading_skill_id;


-- Subject 'IQ – Marathi' if not exists and get its ID
SET @subject_iq_marathi_id = (SELECT subject_id FROM subject WHERE subject_name = 'IQ – Marathi' LIMIT 1);

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

-- 3. Insert Chapters (घटक) and Topics (उपघटक) for 'IQ – Marathi' subject
-- Using IFNULL and nested SELECTs to ensure IDs are correctly retrieved for each step

-- Chapter 1: आकलन (Comprehension)
SET @chapter_aakalan_id = (SELECT id FROM chapters WHERE chapter_name = 'आकलन' AND subject_id = @subject_iq_marathi_id AND class_id = @class_5th_id AND board_id = @board_msce_id LIMIT 1);
-- Chapter 2: वर्गीकरण (Classification)
SET @chapter_vargikaran_id = (SELECT id FROM chapters WHERE chapter_name = 'वर्गीकरण' AND subject_id = @subject_iq_marathi_id AND class_id = @class_5th_id AND board_id = @board_msce_id LIMIT 1);
-- Chapter 3: क्रम ओळखणे (Identify the order)
SET @chapter_kram_olakne_id = (SELECT id FROM chapters WHERE chapter_name = 'क्रम ओळखणे' AND subject_id = @subject_iq_marathi_id AND class_id = @class_5th_id AND board_id = @board_msce_id LIMIT 1);
-- Chapter 4: तर्क संगती व अनुमान (Logical Reasoning and Deduction)
SET @chapter_tark_sangat_anuman_id = (SELECT id FROM chapters WHERE chapter_name = 'तर्क संगती व अनुमान' AND subject_id = @subject_iq_marathi_id AND class_id = @class_5th_id AND board_id = @board_msce_id LIMIT 1);
-- Chapter 5: प्रतिबिंब/प्रतिमा (Reflection/Image)
SET @chapter_pratibimb_id = (SELECT id FROM chapters WHERE chapter_name = 'प्रतिबिंब/प्रतिमा' AND subject_id = @subject_iq_marathi_id AND class_id = @class_5th_id AND board_id = @board_msce_id LIMIT 1);
-- Chapter 6: समसंबंध (Analogy)
SET @chapter_samsambandh_id = (SELECT id FROM chapters WHERE chapter_name = 'समसंबंध' AND subject_id = @subject_iq_marathi_id AND class_id = @class_5th_id AND board_id = @board_msce_id LIMIT 1);
-- Chapter 7: समानपद ओळखणे (Identify the common term)
SET @chapter_samanpad_olakne_id = (SELECT id FROM chapters WHERE chapter_name = 'समानपद ओळखणे' AND subject_id = @subject_iq_marathi_id AND class_id = @class_5th_id AND board_id = @board_msce_id LIMIT 1);
-- Chapter 8: कूटप्रश्न (Puzzles/Riddles)
SET @chapter_kutprashna_id = (SELECT id FROM chapters WHERE chapter_name = 'कूटप्रश्न' AND subject_id = @subject_iq_marathi_id AND class_id = @class_5th_id AND board_id = @board_msce_id LIMIT 1);
-- Chapter 9: गटाशी जुळणारे पद (Matching the Group) - User specifically asked for this and onwards
SET @chapter_gatashi_julnare_pad_id = (SELECT id FROM chapters WHERE chapter_name = 'गटाशी जुळणारे पद' AND subject_id = @subject_iq_marathi_id AND class_id = @class_5th_id AND board_id = @board_msce_id LIMIT 1);
-- Chapter 10: सांकेतिक भाषा (Coded Language)
SET @chapter_sanketik_bhasha_id = (SELECT id FROM chapters WHERE chapter_name = 'सांकेतिक भाषा' AND subject_id = @subject_iq_marathi_id AND class_id = @class_5th_id AND board_id = @board_msce_id LIMIT 1);
-- Chapter 11: भावनिक व सामाजिक बुद्धिमत्ता (Emotional and Social Intelligence)
SET @chapter_bhavanik_samajik_buddhimatta_id = (SELECT id FROM chapters WHERE chapter_name = 'भावनिक व सामाजिक बुद्धिमत्ता' AND subject_id = @subject_iq_marathi_id AND class_id = @class_5th_id AND board_id = @board_msce_id LIMIT 1);

update chapters set percent=8 where id=@chapter_aakalan_id;
update chapters set percent=10 where id=@chapter_vargikaran_id;
update chapters set percent=10 where id=@chapter_kram_olakne_id;
update chapters set percent=12 where id=@chapter_tark_sangat_anuman_id;
update chapters set percent=8 where id=@chapter_pratibimb_id;
update chapters set percent=10 where id=@chapter_samsambandh_id;
update chapters set percent=4 where id=@chapter_samanpad_olakne_id;
update chapters set percent=16 where id=@chapter_kutprashna_id;
update chapters set percent=10 where id=@chapter_gatashi_julnare_pad_id;
update chapters set percent=8 where id=@chapter_sanketik_bhasha_id;
update chapters set percent=4 where id=@chapter_bhavanik_samajik_buddhimatta_id;



-- MSCE PRE UPPER PRIMARY SCHOLARSHIP EXAMINATION - CLASS 7 Marathi Medium
--  Board (MSCE) if not exists and get its ID
SET @board_msce_id = (SELECT id FROM board WHERE board_name = 'MSCE' LIMIT 1);

--  Class 7 if not exists and get its ID
SET @class_7_id = (SELECT id FROM class WHERE class_name = '7' LIMIT 1);

--  Subject 'Marathi – First Language' if not exists and get its ID
SET @subject_marathi_fl_id = (SELECT subject_id FROM subject WHERE subject_name = 'Marathi – First Language' LIMIT 1);

-- Chapters and Topics for "Marathi – First Language" (7th Std, Marathi Medium)

-- Chapter: आकलन (Comprehension)
SET @chapter_aakalan_id = (SELECT id FROM chapters WHERE chapter_name = 'आकलन' AND subject_id = @subject_marathi_fl_id AND class_id = @class_7_id AND board_id = @board_msce_id LIMIT 1);
-- Chapter: शब्दसंपत्ती (Vocabulary)
SET @chapter_shabdasampatti_id = (SELECT id FROM chapters WHERE chapter_name = 'शब्दसंपत्ती' AND subject_id = @subject_marathi_fl_id AND class_id = @class_7_id AND board_id = @board_msce_id LIMIT 1);
-- Chapter: कार्यात्मक व्याकरण (Functional Grammar)
SET @chapter_vyakaran_id = (SELECT id FROM chapters WHERE chapter_name = 'कार्यात्मक व्याकरण' AND subject_id = @subject_marathi_fl_id AND class_id = @class_7_id AND board_id = @board_msce_id LIMIT 1);
-- Chapter: इ. 1 ली ते इ. 7 वी मराठी विषयाशी संबंधित सामान्य ज्ञान (General Knowledge related to Marathi subject for 1st to 7th Std)
SET @chapter_gk_id = (SELECT id FROM chapters WHERE chapter_name = 'इ. 1 ली ते इ. 7 वी मराठी विषयाशी संबंधित सामान्य ज्ञान' AND subject_id = @subject_marathi_fl_id AND class_id = @class_7_id AND board_id = @board_msce_id LIMIT 1);

update chapters set percent=24 where id=@chapter_aakalan_id;
update chapters set percent=24 where id=@chapter_shabdasampatti_id;
update chapters set percent=48 where id=@chapter_vyakaran_id;
update chapters set percent=4 where id=@chapter_gk_id;

--H"Math – Marathi" for the 7th Standard, Marathi Medium, based on the provided PDF document. 
-- Subject 'Math – Marathi' if not exists and get its ID
SET @subject_math_marathi_id = (SELECT subject_id FROM subject WHERE subject_name = 'Math – Marathi' LIMIT 1);

-- Chapters and Topics for "Math – Marathi" (7th Std, Marathi Medium)
-- Chapter: संख्याज्ञान (Number Knowledge)
SET @chapter_sankhyagnan_id = (SELECT id FROM chapters WHERE chapter_name = 'संख्याज्ञान' AND subject_id = @subject_math_marathi_id AND class_id = @class_7_id AND board_id = @board_msce_id LIMIT 1);
-- Chapter: संख्यांवरील क्रिया (Operations on numbers)
SET @chapter_operations_on_numbers_id = (SELECT id FROM chapters WHERE chapter_name = 'संख्यांवरील क्रिया' AND subject_id = @subject_math_marathi_id AND class_id = @class_7_id AND board_id = @board_msce_id LIMIT 1);
-- Chapter: भूमिती (Geometry)
SET @chapter_geometry_id = (SELECT id FROM chapters WHERE chapter_name = 'भूमिती' AND subject_id = @subject_math_marathi_id AND class_id = @class_7_id AND board_id = @board_msce_id LIMIT 1);
-- Chapter: महत्त्वमापन (Mensuration)
SET @chapter_mensuration_id = (SELECT id FROM chapters WHERE chapter_name = 'महत्त्वमापन' AND subject_id = @subject_math_marathi_id AND class_id = @class_7_id AND board_id = @board_msce_id LIMIT 1);
-- Chapter: सांख्यिकी (Statistics)
SET @chapter_statistics_id = (SELECT id FROM chapters WHERE chapter_name = 'सांख्यिकी' AND subject_id = @subject_math_marathi_id AND class_id = @class_7_id AND board_id = @board_msce_id LIMIT 1);
-- Chapter: व्यावहारिक गणित (Commercial Mathematics)
SET @chapter_commercial_math_id = (SELECT id FROM chapters WHERE chapter_name = 'व्यावहारिक गणित' AND subject_id = @subject_math_marathi_id AND class_id = @class_7_id AND board_id = @board_msce_id LIMIT 1);
-- Chapter: बीजगणित (Algebra)
SET @chapter_algebra_id = (SELECT id FROM chapters WHERE chapter_name = 'बीजगणित' AND subject_id = @subject_math_marathi_id AND class_id = @class_7_id AND board_id = @board_msce_id LIMIT 1);

update chapters set percent=8 where id=@chapter_sankhyagnan_id;
update chapters set percent=18 where id=@chapter_operations_on_numbers_id;
update chapters set percent=22 where id=@chapter_geometry_id;
update chapters set percent=16 where id=@chapter_mensuration_id;
update chapters set percent=6 where id=@chapter_statistics_id;
update chapters set percent=14 where id=@chapter_commercial_math_id;
update chapters set percent=16 where id=@chapter_algebra_id;

--"English – Third Language" for the 7th Standard, Marathi Medium
-- Subject 'English – Third Language' if not exists and get its ID
SET @subject_english_third_lang_id = (SELECT subject_id FROM subject WHERE subject_name = 'English – Third Language' LIMIT 1);

-- Chapters and Topics for "English – Third Language" (7th Std, Marathi Medium)

-- Chapter: Vocabulary
SET @chapter_vocabulary_id = (SELECT id FROM chapters WHERE chapter_name = 'Vocabulary' AND subject_id = @subject_english_third_lang_id AND class_id = @class_7_id AND board_id = @board_msce_id LIMIT 1);
-- Chapter: Word Puzzles
SET @chapter_word_puzzles_id = (SELECT id FROM chapters WHERE chapter_name = 'Word Puzzles' AND subject_id = @subject_english_third_lang_id AND class_id = @class_7_id AND board_id = @board_msce_id LIMIT 1);
-- Chapter: Language Study
SET @chapter_language_study_id = (SELECT id FROM chapters WHERE chapter_name = 'Language Study' AND subject_id = @subject_english_third_lang_id AND class_id = @class_7_id AND board_id = @board_msce_id LIMIT 1);
-- Chapter: Grammar (Transformation of Sentences)
SET @chapter_grammar_id = (SELECT id FROM chapters WHERE chapter_name = 'Grammar (Transformation of Sentences)' AND subject_id = @subject_english_third_lang_id AND class_id = @class_7_id AND board_id = @board_msce_id LIMIT 1);
-- Chapter: Creative Writing
SET @chapter_creative_writing_id = (SELECT id FROM chapters WHERE chapter_name = 'Creative Writing' AND subject_id = @subject_english_third_lang_id AND class_id = @class_7_id AND board_id = @board_msce_id LIMIT 1);
-- Chapter: Reading Skills
SET @chapter_reading_skills_id = (SELECT id FROM chapters WHERE chapter_name = 'Reading Skills' AND subject_id = @subject_english_third_lang_id AND class_id = @class_7_id AND board_id = @board_msce_id LIMIT 1);
-- Chapter: Miscellaneous
SET @chapter_miscellaneous_id = (SELECT id FROM chapters WHERE chapter_name = 'Miscellaneous' AND subject_id = @subject_english_third_lang_id AND class_id = @class_7_id AND board_id = @board_msce_id LIMIT 1);

update chapters set percent=16 where id=@chapter_vocabulary_id;
update chapters set percent=8 where id=@chapter_word_puzzles_id;
update chapters set percent=24 where id=@chapter_language_study_id;
update chapters set percent=12 where id=@chapter_grammar_id;
update chapters set percent=16 where id=@chapter_creative_writing_id;
update chapters set percent=20 where id=@chapter_reading_skills_id;
update chapters set percent=4 where id=@chapter_miscellaneous_id;


-- PRE UPPER PRIMARY SCHOLARSHIP (7th Std) - IQ / बुद्धिमत्ता चाचणी
-- Marathi Medium - MSCE Board


-- 2. Get IDs (recommended to use variables for safety)
SET @board_msce_id     := (SELECT id FROM board     WHERE board_name = 'MSCE' LIMIT 1);
SET @class_7_id        := (SELECT id FROM class     WHERE class_name = '7'    LIMIT 1);
SET @subject_iq_id     := (SELECT subject_id FROM subject WHERE subject_name = 'IQ – Marathi' LIMIT 1);


-- Chapter 1: आकलन
SET @ch1 := (SELECT id FROM chapters WHERE chapter_name = 'आकलन' AND subject_id = @subject_iq_id AND class_id = @class_7_id AND board_id = @board_msce_id LIMIT 1);
-- Chapter 2: वर्गीकरण
SET @ch2 := (SELECT id FROM chapters WHERE chapter_name = 'वर्गीकरण' AND subject_id = @subject_iq_id AND class_id = @class_7_id AND board_id = @board_msce_id LIMIT 1);
-- Chapter 3: समसंबंध
SET @ch3 := (SELECT id FROM chapters WHERE chapter_name = 'समसंबंध' AND subject_id = @subject_iq_id AND class_id = @class_7_id AND board_id = @board_msce_id LIMIT 1);
-- Chapter 4: क्रम
SET @ch4 := (SELECT id FROM chapters WHERE chapter_name = 'क्रम' AND subject_id = @subject_iq_id AND class_id = @class_7_id AND board_id = @board_msce_id LIMIT 1);
-- Chapter 5: गटाशी जुळणारे पद
SET @ch5 := (SELECT id FROM chapters WHERE chapter_name = 'गटाशी जुळणारे पद' AND subject_id = @subject_iq_id AND class_id = @class_7_id AND board_id = @board_msce_id LIMIT 1);
-- Chapter 6: लयबद्ध मांडणी
SET @ch6 := (SELECT id FROM chapters WHERE chapter_name = 'लयबद्ध मांडणी' AND subject_id = @subject_iq_id AND class_id = @class_7_id AND board_id = @board_msce_id LIMIT 1);
-- Chapter 7: जलप्रतिबिंब
SET @ch7 := (SELECT id FROM chapters WHERE chapter_name = 'जलप्रतिबिंब' AND subject_id = @subject_iq_id AND class_id = @class_7_id AND board_id = @board_msce_id LIMIT 1);
-- Chapter 8: आरशातील प्रतिमा
SET @ch8 := (SELECT id FROM chapters WHERE chapter_name = 'आरशातील प्रतिमा' AND subject_id = @subject_iq_id AND class_id = @class_7_id AND board_id = @board_msce_id LIMIT 1);
-- Chapter 9: तर्क व अनुमान
SET @ch9 := (SELECT id FROM chapters WHERE chapter_name = 'तर्क व अनुमान' AND subject_id = @subject_iq_id AND class_id = @class_7_id AND board_id = @board_msce_id LIMIT 1);
-- Chapter 10: कूटप्रश्न
SET @ch10 := (SELECT id FROM chapters WHERE chapter_name = 'कूटप्रश्न' AND subject_id = @subject_iq_id AND class_id = @class_7_id AND board_id = @board_msce_id LIMIT 1);
-- Chapter 11: सांकेतिक भाषा
SET @ch11 := (SELECT id FROM chapters WHERE chapter_name = 'सांकेतिक भाषा' AND subject_id = @subject_iq_id AND class_id = @class_7_id AND board_id = @board_msce_id LIMIT 1);
-- Chapter 12: मनोरे / रचना
SET @ch12 := (SELECT id FROM chapters WHERE chapter_name = 'मनोरे / रचना' AND subject_id = @subject_iq_id AND class_id = @class_7_id AND board_id = @board_msce_id LIMIT 1);
-- Chapter 13: आकृतीचे पृथक्करण
SET @ch13 := (SELECT id FROM chapters WHERE chapter_name = 'आकृतीचे पृथक्करण' AND subject_id = @subject_iq_id AND class_id = @class_7_id AND board_id = @board_msce_id LIMIT 1);

update chapters set percent=8 where id=@ch1;
update chapters set percent=10 where id=@ch2;
update chapters set percent=10 where id=@ch3;
update chapters set percent=10 where id=@ch4;
update chapters set percent=6 where id=@ch5;
update chapters set percent=4 where id=@ch6;
update chapters set percent=4 where id=@ch7;
update chapters set percent=4 where id=@ch8;
update chapters set percent=12 where id=@ch9;
update chapters set percent=14 where id=@ch10;
update chapters set percent=6 where id=@ch11;
update chapters set percent=4 where id=@ch12;
update chapters set percent=8 where id=@ch13;


-- MSCE PRE UPPER PRIMARY SCHOLARSHIP EXAMINATION - CLASS 8 Marathi Medium
--PRE UPPER PRIMARY SCHOLARSHIP EXAMINATION – Std 8 – Marathi Medium (MSCE)

SET @board_msce_id = (SELECT id FROM board WHERE board_name='MSCE' LIMIT 1);
SET @class_8th_id  = (SELECT id FROM class WHERE class_name='8' LIMIT 1);

SET @sub_mar  = (SELECT subject_id FROM subject WHERE subject_name='Marathi – First Language');

INSERT INTO chapters (chapter_name, unit, subject_id, class_id, board_id)
VALUES
('आकलन',            'Unit 1', @sub_mar, @class_8th_id, @board_msce_id),
('शब्दसंपत्ती',      'Unit 2', @sub_mar, @class_8th_id, @board_msce_id),
('कार्यात्मक व्याकरण', 'Unit 3', @sub_mar, @class_8th_id, @board_msce_id),
('सामान्य ज्ञान',    'Unit 4', @sub_mar, @class_8th_id, @board_msce_id);

--Topics – Unit 1: आकलन (24%)
SET @ch1 := (SELECT id FROM chapters WHERE chapter_name = 'आकलन' AND subject_id = @sub_mar AND class_id = @class_8th_id AND board_id = @board_msce_id LIMIT 1);
--Topics – Unit 2: शब्दसंपत्ती (24%)
SET @ch2 := (SELECT id FROM chapters WHERE chapter_name = 'शब्दसंपत्ती' AND subject_id = @sub_mar AND class_id = @class_8th_id AND board_id = @board_msce_id LIMIT 1);
--Topics – Unit 3: कार्यात्मक व्याकरण (44%)
SET @ch3 := (SELECT id FROM chapters WHERE chapter_name = 'कार्यात्मक व्याकरण' AND subject_id = @sub_mar AND class_id = @class_8th_id AND board_id = @board_msce_id LIMIT 1);
--Topics – Unit 4: सामान्य ज्ञान (8%)
SET @ch4 := (SELECT id FROM chapters WHERE chapter_name = 'सामान्य ज्ञान' AND subject_id = @sub_mar AND class_id = @class_8th_id AND board_id = @board_msce_id LIMIT 1);

update chapters set percent=24 where id=@ch1;
update chapters set percent=24 where id=@ch2;
update chapters set percent=44 where id=@ch3;
update chapters set percent=8 where id=@ch4;

-- =============================================================================
-- Pre Upper Primary Scholarship Exam (Class 8) - Marathi Medium
-- Subject: Math – Marathi (गणित)
-- Syllabus Date: 23 March 2016 (MSCE - Maharashtra State Examination Council)
-- Total weightage: 100%
-- =============================================================================

-- 1. Fetch common IDs (ensure these records already exist in your database)
SET @board_msce_id   := (SELECT id FROM board     WHERE board_name = 'MSCE' LIMIT 1);
SET @class_8_id      := (SELECT id FROM class     WHERE class_name = '8'    LIMIT 1);
SET @subject_math_id := (SELECT subject_id FROM subject WHERE subject_name = 'Math – Marathi' LIMIT 1);


--                           CHAPTERS & TOPICS - गणित

-- Chapter 1: संख्याज्ञान (6%)
SET @ch1 := (SELECT id FROM chapters 
             WHERE chapter_name = 'संख्याज्ञान' 
               AND subject_id = @subject_math_id 
               AND class_id = @class_8_id 
               AND board_id = @board_msce_id LIMIT 1);

-- Chapter 2: संख्यावरील क्रिया (14%)
SET @ch2 := (SELECT id FROM chapters 
             WHERE chapter_name = 'संख्यावरील क्रिया' 
               AND subject_id = @subject_math_id 
               AND class_id = @class_8_id 
               AND board_id = @board_msce_id LIMIT 1);

-- Chapter 3: भूमिती (20%)
SET @ch3 := (SELECT id FROM chapters 
             WHERE chapter_name = 'भूमिती' 
               AND subject_id = @subject_math_id 
               AND class_id = @class_8_id 
               AND board_id = @board_msce_id LIMIT 1);

-- Chapter 4: महत्त्व मापन (20%)
SET @ch4 := (SELECT id FROM chapters 
             WHERE chapter_name = 'महत्त्व मापन' 
               AND subject_id = @subject_math_id 
               AND class_id = @class_8_id 
               AND board_id = @board_msce_id LIMIT 1);

-- Chapter 5: सांख्यिकी (6%)
SET @ch5 := (SELECT id FROM chapters 
             WHERE chapter_name = 'सांख्यिकी' 
               AND subject_id = @subject_math_id 
               AND class_id = @class_8_id 
               AND board_id = @board_msce_id LIMIT 1);

-- Chapter 6: व्यावहारिक गणित (16%)
SET @ch6 := (SELECT id FROM chapters 
             WHERE chapter_name = 'व्यावहारिक गणित' 
               AND subject_id = @subject_math_id 
               AND class_id = @class_8_id 
               AND board_id = @board_msce_id LIMIT 1);

-- Chapter 7: बीजगणित (18%)
SET @ch7 := (SELECT id FROM chapters 
             WHERE chapter_name = 'बीजगणित' 
               AND subject_id = @subject_math_id 
               AND class_id = @class_8_id 
               AND board_id = @board_msce_id LIMIT 1);


update chapters set percent=6 where id=@ch1;
update chapters set percent=14 where id=@ch2;
update chapters set percent=20 where id=@ch3;
update chapters set percent=20 where id=@ch4;
update chapters set percent=6 where id=@ch5;
update chapters set percent=16 where id=@ch6;
update chapters set percent=18 where id=@ch7;

--Std 8 – Marathi Medium – ENGLISH (Third Language)
SET @board_msce_id = (SELECT id FROM board WHERE board_name='MSCE' LIMIT 1);
SET @class_8th_id  = (SELECT id FROM class WHERE class_name='8' LIMIT 1);
SET @sub_eng       = (SELECT subject_id FROM subject WHERE subject_name='English' LIMIT 1);


--Unit 1 – Vocabulary (16%)
SET @ch1 := (SELECT  id FROM chapters WHERE chapter_name='Vocabulary' 
               AND subject_id = @subject_math_id 
               AND class_id = @class_8_id 
               AND board_id = @board_msce_id LIMIT 1);
-- Unit 2 – Word Puzzles (8%)
SET @ch2 := (SELECT  id FROM chapters WHERE chapter_name='Word Puzzles' 
               AND subject_id = @subject_math_id 
               AND class_id = @class_8_id 
               AND board_id = @board_msce_id LIMIT 1);
               
-- Unit 3 – Language Study (24%)
SET @ch3 := (SELECT  id FROM chapters WHERE chapter_name='Language Study' 
               AND subject_id = @subject_math_id 
               AND class_id = @class_8_id 
               AND board_id = @board_msce_id LIMIT 1);

-- Unit 4 – Grammar (12%)
SET @ch4 := (SELECT  id FROM chapters WHERE chapter_name='Grammar' 
               AND subject_id = @subject_math_id 
               AND class_id = @class_8_id 
               AND board_id = @board_msce_id LIMIT 1);
-- Unit 5 – Creative Writing (16%)
SET @ch5 := (SELECT  id FROM chapters WHERE chapter_name='Creative Writing' 
               AND subject_id = @subject_math_id 
               AND class_id = @class_8_id 
               AND board_id = @board_msce_id LIMIT 1);
-- Unit 6 – Reading Skills (20%)
SET @ch6 := (SELECT  id FROM chapters WHERE chapter_name='Reading Skills' 
               AND subject_id = @subject_math_id 
               AND class_id = @class_8_id 
               AND board_id = @board_msce_id LIMIT 1);
               
-- Unit 7 – Miscellaneous (4%)
SET @ch7 := (SELECT  id FROM chapters WHERE chapter_name='Miscellaneous' 
               AND subject_id = @subject_math_id 
               AND class_id = @class_8_id 
               AND board_id = @board_msce_id LIMIT 1);
               
update chapters set percent=16 where id=@ch1;
update chapters set percent=8 where id=@ch2;
update chapters set percent=24 where id=@ch3;
update chapters set percent=12 where id=@ch4;
update chapters set percent=16 where id=@ch5;
update chapters set percent=20 where id=@ch6;
update chapters set percent=4 where id=@ch7;

--Std 8 – Marathi Medium – IQ (बुद्धिमत्ता चाचणी)

--PRE UPPER PRIMARY SCHOLARSHIP EXAMINATION – MSCE

SET @sub_iq        = (SELECT subject_id FROM subject WHERE subject_name='IQ – Marathi' LIMIT 1);

--Unit 1 – आकलन (10%)
SET @ch1 := (SELECT  id FROM chapters WHERE chapter_name='आकलन' 
               AND subject_id = @subject_math_id 
               AND class_id = @class_8_id 
               AND board_id = @board_msce_id LIMIT 1);
-- Unit 2 – वर्गीकरण (10%)
SET @ch2 := (SELECT  id FROM chapters WHERE chapter_name='वर्गीकरण' 
               AND subject_id = @subject_math_id 
               AND class_id = @class_8_id 
               AND board_id = @board_msce_id LIMIT 1);
-- Unit 3 – संबंध (10%)
SET @ch3 := (SELECT  id FROM chapters WHERE chapter_name='संबंध' 
               AND subject_id = @subject_math_id 
               AND class_id = @class_8_id 
               AND board_id = @board_msce_id LIMIT 1);
-- Unit 4 – क्रम (12%)
SET @ch4 := (SELECT  id FROM chapters WHERE chapter_name='क्रम' 
               AND subject_id = @subject_math_id 
               AND class_id = @class_8_id 
               AND board_id = @board_msce_id LIMIT 1);
-- Unit 5 – सांकेतिक भाषा (6%)
SET @ch5 := (SELECT  id FROM chapters WHERE chapter_name='सांकेतिक भाषा' 
               AND subject_id = @subject_math_id 
               AND class_id = @class_8_id 
               AND board_id = @board_msce_id LIMIT 1);
-- Unit 6 – लयबद्ध मांडणी (6%)
SET @ch6 := (SELECT  id FROM chapters WHERE chapter_name='लयबद्ध मांडणी' 
               AND subject_id = @subject_math_id 
               AND class_id = @class_8_id 
               AND board_id = @board_msce_id LIMIT 1);
-- Unit 7 – मनोगत (6%)
SET @ch7 := (SELECT  id FROM chapters WHERE chapter_name='मनोगत' 
               AND subject_id = @subject_math_id 
               AND class_id = @class_8_id 
               AND board_id = @board_msce_id LIMIT 1);
-- Unit 8 – प्रतिबिंब / प्रतिमा (6%)
SET @ch8 := (SELECT  id FROM chapters WHERE chapter_name='प्रतिबिंब / प्रतिमा' 
               AND subject_id = @subject_math_id 
               AND class_id = @class_8_id 
               AND board_id = @board_msce_id LIMIT 1);
-- Unit 9 – तर्क व अनुमान (12%)
SET @ch9 := (SELECT  id FROM chapters WHERE chapter_name='तर्क व अनुमान' 
               AND subject_id = @subject_math_id 
               AND class_id = @class_8_id 
               AND board_id = @board_msce_id LIMIT 1);
-- Unit 10 – कूट प्रश्न (12%)
SET @ch10 := (SELECT  id FROM chapters WHERE chapter_name='कूट प्रश्न' 
               AND subject_id = @subject_math_id 
               AND class_id = @class_8_id 
               AND board_id = @board_msce_id LIMIT 1);
-- Unit 11 – आकृतीचे पृथक्करण (10%)
SET @ch11 := (SELECT  id FROM chapters WHERE chapter_name='आकृतीचे पृथक्करण' 
               AND subject_id = @subject_math_id 
               AND class_id = @class_8_id 
               AND board_id = @board_msce_id LIMIT 1);

update chapters set percent=10 where id=@ch1;
update chapters set percent=10 where id=@ch2;
update chapters set percent=10 where id=@ch3;
update chapters set percent=12 where id=@ch4;
update chapters set percent=6 where id=@ch5;
update chapters set percent=6 where id=@ch6;
update chapters set percent=6 where id=@ch7;
update chapters set percent=6 where id=@ch8;
update chapters set percent=12 where id=@ch9;
update chapters set percent=12 where id=@ch10;
update chapters set percent=10 where id=@ch11;

COMMIT;
