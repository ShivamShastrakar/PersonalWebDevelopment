--liquibase formatted sql
--changeset Narendra:71_student_attempt

CREATE TABLE student_question_attempt (
      id                BIGINT AUTO_INCREMENT PRIMARY KEY,
      question_paper_id BIGINT NOT NULL,
      student_user_id   BIGINT UNSIGNED NOT NULL,
      question_id       BIGINT UNSIGNED NOT NULL,
      subject_id        INT NOT NULL,
      answer_given      VARCHAR(255) DEFAULT NULL,        -- usually useful
      is_correct        BOOLEAN DEFAULT NULL,             -- NULL = not evaluated yet
      marks_obtained    DECIMAL(5,2) DEFAULT NULL,        -- partial marking, negative?
      attempted_at      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
      INDEX idx_paper_student (question_paper_id, student_user_id),
      INDEX idx_question   (question_id),
      INDEX idx_attempted  (attempted_at)
);

ALTER TABLE student_question_attempt
    ADD CONSTRAINT fk_attempt_paper
        FOREIGN KEY (question_paper_id) REFERENCES question_paper(id)
            ON DELETE RESTRICT ON UPDATE CASCADE,
    ADD CONSTRAINT fk_attempt_student
        FOREIGN KEY (student_user_id)   REFERENCES users(user_id)
            ON DELETE RESTRICT ON UPDATE CASCADE,
    ADD CONSTRAINT fk_attempt_question
        FOREIGN KEY (question_id)       REFERENCES questions(id)
            ON DELETE RESTRICT ON UPDATE CASCADE,
    ADD CONSTRAINT fk_attempt_subject
        FOREIGN KEY (subject_id)        REFERENCES subject(subject_id)
            ON DELETE RESTRICT ON UPDATE CASCADE;


CREATE TABLE student_subject_summary (
     id                BIGINT AUTO_INCREMENT PRIMARY KEY,
     question_paper_id BIGINT NOT NULL,
     student_user_id   BIGINT UNSIGNED NOT NULL,
     subject_id        INT NOT NULL,
     total_questions   INT NOT NULL,
     correct           INT NOT NULL DEFAULT 0,
     wrong             INT NOT NULL DEFAULT 0,
     not_answered      INT NOT NULL DEFAULT 0,
     marks_obtained    DECIMAL(6,2) NOT NULL DEFAULT 0.00,
     max_marks         DECIMAL(6,2) NOT NULL,
     attempted_at      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
     UNIQUE KEY uk_student_subject_summary (question_paper_id, student_user_id, subject_id),
     INDEX idx_summary_paper_student (question_paper_id, student_user_id)
);

ALTER TABLE student_subject_summary
    ADD CONSTRAINT fk_student_summary_question_paper
        FOREIGN KEY (question_paper_id) REFERENCES question_paper(id)
            ON DELETE CASCADE ON UPDATE CASCADE,   -- ← usually CASCADE here
    ADD CONSTRAINT fk_student__summary_student
        FOREIGN KEY (student_user_id)   REFERENCES users(user_id)
            ON DELETE CASCADE ON UPDATE CASCADE,
    ADD CONSTRAINT fk_student__summary_subject
        FOREIGN KEY (subject_id)        REFERENCES subject(subject_id)
            ON DELETE RESTRICT ON UPDATE CASCADE;