--liquibase formatted sql
--changeset {narendra}:{id}

-- 1. Remove questions for chapters with class_id is null
--    (must come before topics deletion due to FK: questions.topic_id → topics.topic_id)
DELETE FROM questions
WHERE chapter_id IN (
    SELECT id FROM chapters WHERE class_id IS NULL
);

-- 2. Now safe to remove topics for chapters with class_id is null
DELETE FROM topics
WHERE chapter_id IN (
    SELECT id FROM chapters WHERE class_id IS NULL
);

-- 3. Remove chapter_board_class_mapping for chapters with class_id is null
DELETE FROM chapter_board_class_mapping
WHERE chapter_id IN (
    SELECT id FROM chapters WHERE class_id IS NULL
);

-- 4. Now remove chapters with class_id is null
DELETE FROM chapters WHERE class_id IS NULL;

-- 5. Remove questions for chapters with percent is null
--    (must come before topics deletion)
DELETE FROM questions
WHERE chapter_id IN (
    SELECT id FROM chapters WHERE percent IS NULL
);

-- 6. Now safe to remove topics for chapters with percent is null
DELETE FROM topics
WHERE chapter_id IN (
    SELECT id FROM chapters WHERE percent IS NULL
);

-- 7. Remove chapter_board_class_mapping for chapters with percent is null
DELETE FROM chapter_board_class_mapping
WHERE chapter_id IN (
    SELECT id FROM chapters WHERE percent IS NULL
);

-- 8. Remove chapters with percent is null
DELETE FROM chapters WHERE percent IS NULL;
