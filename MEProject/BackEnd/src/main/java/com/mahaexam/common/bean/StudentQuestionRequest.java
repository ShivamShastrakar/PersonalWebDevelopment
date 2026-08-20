package com.mahaexam.common.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Top-level request body for the POST /api/exam-submissions/submit-exam endpoint.
 *
 * Example payload:
 * {
 *   "questionPaperId": 1,
 *   "studentUserId": 42,
 *   "subjectId": 5,
 *   "attempts": [
 *     { "questionId": 101, "subjectId": 5, "answerGiven": "option1" },
 *     { "questionId": 102, "subjectId": 5, "answerGiven": "option3" }
 *   ]
 * }
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudentQuestionRequest {

    /** ID of the question paper (exam) being submitted */
    private Long questionPaperId;

    /**
     * ID of the student submitting the exam.
     * If omitted, the server fills it from the authenticated session.
     */
    private Long studentUserId;

    /**
     * Default subject ID applied to all attempts that don't specify their own subjectId.
     * Each attempt may override this with its own subjectId field.
     */
    private Integer subjectId;

    /** The list of individual question answers */
    private List<StudentQuestionAttemptRequest> attempts;

    /** Time taken to complete the exam (in seconds) */
    private Integer timeTaken;
}
