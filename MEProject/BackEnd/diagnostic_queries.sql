-- =====================================================
-- Diagnostic SQL Queries for Question Paper Analysis
-- =====================================================

-- Replace {question_paper_id} with your actual question paper ID

-- =====================================================
-- 1. CHECK CURRENT QUESTION COUNT
-- =====================================================
SELECT COUNT(*) as total_questions
FROM question_paper_question
WHERE question_paper_id = {question_paper_id};
-- Expected: 100
-- Current: 98

-- =====================================================
-- 2. CHECK FOR DUPLICATE QUESTIONS
-- =====================================================
SELECT question_id, COUNT(*) as usage_count
FROM question_paper_question
WHERE question_paper_id = {question_paper_id}
GROUP BY question_id
HAVING COUNT(*) > 1;
-- Expected: 0 rows (no duplicates)

-- =====================================================
-- 3. CHECK SEQUENCE NUMBERS
-- =====================================================
SELECT
    MIN(sequence_number) as min_seq,
    MAX(sequence_number) as max_seq,
    COUNT(DISTINCT sequence_number) as unique_sequences,
    COUNT(*) as total_rows
FROM question_paper_question
WHERE question_paper_id = {question_paper_id};
-- Min should be 1, Max should equal total_rows, unique_sequences = total_rows

-- =====================================================
-- 4. CHECK QUESTION DISTRIBUTION BY CHAPTER
-- =====================================================
SELECT
    q.chapter_id,
    COUNT(*) as question_count
FROM question_paper_question qpq
JOIN question q ON qpq.question_id = q.id
WHERE qpq.question_paper_id = {question_paper_id}
GROUP BY q.chapter_id
ORDER BY q.chapter_id;

-- =====================================================
-- 5. CHECK SUKA (SKILL LEVEL) DISTRIBUTION
-- =====================================================
SELECT
    q.skill_level,
    COUNT(*) as count,
    ROUND(COUNT(*) * 100.0 / (SELECT COUNT(*) FROM question_paper_question WHERE question_paper_id = {question_paper_id}), 2) as percentage
FROM question_paper_question qpq
JOIN question q ON qpq.question_id = q.id
WHERE qpq.question_paper_id = {question_paper_id}
GROUP BY q.skill_level
ORDER BY q.skill_level;
-- Should roughly match your metaData.skillDistribution percentages

-- =====================================================
-- 6. CHECK DIFFICULTY DISTRIBUTION
-- =====================================================
SELECT
    q.difficulty_level,
    COUNT(*) as count,
    ROUND(COUNT(*) * 100.0 / (SELECT COUNT(*) FROM question_paper_question WHERE question_paper_id = {question_paper_id}), 2) as percentage
FROM question_paper_question qpq
JOIN question q ON qpq.question_id = q.id
WHERE qpq.question_paper_id = {question_paper_id}
GROUP BY q.difficulty_level
ORDER BY
    CASE q.difficulty_level
        WHEN 'EASY' THEN 1
        WHEN 'MEDIUM' THEN 2
        WHEN 'HARD' THEN 3
    END;
-- Should roughly match your metaData.difficultyDistribution percentages

-- =====================================================
-- 7. CHECK QUESTION TYPE DISTRIBUTION
-- =====================================================
SELECT
    q.question_type,
    COUNT(*) as count
FROM question_paper_question qpq
JOIN question q ON qpq.question_id = q.id
WHERE qpq.question_paper_id = {question_paper_id}
GROUP BY q.question_type;

-- =====================================================
-- 8. CHECK AVAILABLE QUESTIONS PER CHAPTER
-- =====================================================
-- Check how many questions exist in database for each chapter
-- Replace {board_id}, {class_id}, {subject_id}, {question_type}, {medium} with actual values

SELECT
    c.chapter_id,
    c.chapter_name,
    COUNT(q.id) as total_questions_in_db,
    COUNT(DISTINCT CONCAT(COALESCE(q.skill_level, 'NULL'), '-', COALESCE(q.difficulty_level, 'NULL'))) as unique_combinations,
    -- Count by skill level
    SUM(CASE WHEN q.skill_level = 'SKILL' THEN 1 ELSE 0 END) as skill_count,
    SUM(CASE WHEN q.skill_level = 'UNDERSTANDING' THEN 1 ELSE 0 END) as understanding_count,
    SUM(CASE WHEN q.skill_level = 'KNOWLEDGE' THEN 1 ELSE 0 END) as knowledge_count,
    SUM(CASE WHEN q.skill_level = 'APPLICATION' THEN 1 ELSE 0 END) as application_count,
    -- Count by difficulty
    SUM(CASE WHEN q.difficulty_level = 'EASY' THEN 1 ELSE 0 END) as easy_count,
    SUM(CASE WHEN q.difficulty_level = 'MEDIUM' THEN 1 ELSE 0 END) as medium_count,
    SUM(CASE WHEN q.difficulty_level = 'HARD' THEN 1 ELSE 0 END) as hard_count
FROM chapter c
LEFT JOIN question q ON q.chapter_id = c.chapter_id
    AND q.board_id = {board_id}
    AND q.class_id = {class_id}
    AND q.subject_id = {subject_id}
    AND q.question_type = '{question_type}'
    AND q.medium = '{medium}'
WHERE c.board_id = {board_id}
  AND c.class_id = {class_id}
  AND c.subject_id = {subject_id}
GROUP BY c.chapter_id, c.chapter_name
ORDER BY c.sequence;

-- Each chapter should have at least 2x the questions needed (to account for buffer and duplicates)

-- =====================================================
-- 9. FIND WHICH 2 QUESTIONS ARE MISSING
-- =====================================================
-- This helps identify patterns in what's missing

-- Get paper template and section info
SELECT
    pt.id as template_id,
    pt.name as template_name,
    p.id as part_id,
    p.name as part_name,
    s.id as section_id,
    s.name as section_name,
    s.number_of_questions as expected_questions
FROM paper_template pt
JOIN part p ON p.paper_template_id = pt.id
JOIN section s ON s.part_id = p.id
WHERE pt.id = {paper_template_id}
ORDER BY p.sequence, s.sequence;

-- Compare with actual questions added
-- This will help identify which section(s) are short

-- =====================================================
-- 10. CHECK SYLLABUS COVERAGE PERCENTAGES
-- =====================================================
SELECT
    sc.chapter_id,
    c.chapter_name,
    sc.coverage_percentage,
    sc.sequence
FROM syllabus_chapter sc
JOIN chapter c ON c.chapter_id = sc.chapter_id
WHERE sc.syllabus_id = {syllabus_id}
ORDER BY sc.sequence;

-- Verify that percentages:
-- 1. Sum to approximately 100%
-- 2. Don't result in too many fractional questions
-- 3. Are balanced across chapters

-- =====================================================
-- 11. DETAILED QUESTION PAPER ANALYSIS
-- =====================================================
-- Get complete breakdown with all metadata
SELECT
    qpq.sequence_number,
    qpq.question_id,
    q.question_type,
    q.chapter_id,
    c.chapter_name,
    q.skill_level,
    q.difficulty_level,
    SUBSTRING(q.question_text, 1, 50) as question_preview
FROM question_paper_question qpq
JOIN question q ON qpq.question_id = q.id
JOIN chapter c ON c.chapter_id = q.chapter_id
WHERE qpq.question_paper_id = {question_paper_id}
ORDER BY qpq.sequence_number;

-- Review this to identify:
-- 1. Which chapters are represented
-- 2. Distribution of SUKA/Difficulty levels
-- 3. Any patterns in missing questions

-- =====================================================
-- 12. COMPARE TEMPLATE STRUCTURE WITH ACTUAL
-- =====================================================
-- Get expected structure from template
WITH template_structure AS (
    SELECT
        p.name as part_name,
        s.name as section_name,
        s.number_of_questions as expected
    FROM paper_template pt
    JOIN part p ON p.paper_template_id = pt.id
    JOIN section s ON s.part_id = p.id
    WHERE pt.id = {paper_template_id}
    ORDER BY p.sequence, s.sequence
),
actual_counts AS (
    SELECT
        'Part A' as part_name,
        'Section 1' as section_name,
        COUNT(*) as actual
    FROM question_paper_question qpq
    JOIN question q ON qpq.question_id = q.id
    WHERE qpq.question_paper_id = {question_paper_id}
    -- Add logic to identify which section based on sequence_number or other criteria
)
SELECT * FROM template_structure;
-- Manual comparison needed

-- =====================================================
-- SUMMARY QUERY - RUN THIS FIRST
-- =====================================================
SELECT
    'Total Questions' as metric,
    COUNT(*) as value
FROM question_paper_question
WHERE question_paper_id = {question_paper_id}

UNION ALL

SELECT
    'Unique Questions' as metric,
    COUNT(DISTINCT question_id) as value
FROM question_paper_question
WHERE question_paper_id = {question_paper_id}

UNION ALL

SELECT
    'Min Sequence' as metric,
    MIN(sequence_number) as value
FROM question_paper_question
WHERE question_paper_id = {question_paper_id}

UNION ALL

SELECT
    'Max Sequence' as metric,
    MAX(sequence_number) as value
FROM question_paper_question
WHERE question_paper_id = {question_paper_id}

UNION ALL

SELECT
    'Expected Questions' as metric,
    100 as value;

-- Expected Results:
-- Total Questions: 100
-- Unique Questions: 100
-- Min Sequence: 1
-- Max Sequence: 100
-- Expected Questions: 100

