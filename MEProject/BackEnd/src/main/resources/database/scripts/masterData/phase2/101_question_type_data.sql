--liquibase formatted sql

--changeset dishika:101_question_type_data

-- =====================================================================
-- MASTER DATA: question_type table
-- Matches the question type dropdown values used in the UI
-- Source: FrontEnd/src/app/core/constants/exam.constants.ts
-- Active types (currently enabled in UI):
--   mcq, paragraph-based-mcq
-- Future types (commented out in UI, pre-seeded for readiness):
--   true-false, short-answer, long-answer, fill-in-blank, matching,
--   numerical, descriptive
-- =====================================================================

INSERT INTO question_type (code, name, description) VALUES

-- ── Active in UI ──────────────────────────────────────────────────────
('mcq',
 'Multiple Choice (MCQ)',
 'Standard multiple-choice question with four options and one correct answer'),

('paragraph-based-mcq',
 'Paragraph Based',
 'A reading passage followed by multiple MCQ questions based on the passage content'),

-- ── Future / planned types (commented out in UI dropdown) ─────────────
('true-false',
 'True / False',
 'A statement that the student must identify as true or false'),

('short-answer',
 'Short Answer',
 'A question requiring a brief written response (one to two sentences)'),

('long-answer',
 'Long Answer',
 'A question requiring a detailed written explanation or essay-style response'),

('fill-in-blank',
 'Fill in the Blank',
 'A sentence with one or more missing words that the student must supply'),

('matching',
 'Matching',
 'Two columns of items that the student must correctly pair with each other'),

('numerical',
 'Numerical',
 'A question where the student types a numerical value as the answer'),

('descriptive',
 'Descriptive',
 'An open-ended question requiring a descriptive or analytical written answer');

