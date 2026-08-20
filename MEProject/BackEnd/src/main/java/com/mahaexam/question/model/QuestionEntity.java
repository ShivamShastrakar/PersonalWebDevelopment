package com.mahaexam.question.model;

import com.fasterxml.jackson.annotation.JsonRawValue;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Entity class representing a question in the database
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuestionEntity {

    private Long id;
    private Integer boardId;
    private Integer subjectId;
    private Integer classId;
    private String medium;
    private Integer chapterId;
    private Integer topicId;
    private String questionType;
    private String questionText;
    private String questionImageUrl;
    @JsonRawValue
    private String options;  // JSON stored as String — emitted as raw JSON object in responses
    private String correctAnswer;  // JSON stored as String
    private String answerExplanation;
    private String answerExplanationImageUrl;
    private String skillLevel;
    private String difficultyLevel;
    private String aiPromptHash;
    private LocalDateTime createdAt;
    private Long createdBy;
    private String paragraphId;
    private String paragraphText;
    private Long tenantId;

    // Transient fields to carry student's response alongside the question
    @JsonRawValue
    private String answerGiven;
    private Boolean isCorrect;
    private java.math.BigDecimal marksObtained;
}
