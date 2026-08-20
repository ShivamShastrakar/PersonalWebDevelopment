package com.mahaexam.common.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Represents a single question attempt inside a student exam submission.
 * Kept minimal — only the fields the client needs to send.
 * The service layer will auto-populate isCorrect, marksObtained, etc.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudentQuestionAttemptRequest {

    /** ID of the question being answered */
    private Long questionId;

    /** Subject this question belongs to */
    private Integer subjectId;

    /**
     * The selected option(s) as an array of option numbers.
     * For single-answer MCQ: [2] means the student chose option2.
     * null means the question was not answered.
     * Example: [2], [3], null
     */
    private List<Integer> answerGiven;
}

