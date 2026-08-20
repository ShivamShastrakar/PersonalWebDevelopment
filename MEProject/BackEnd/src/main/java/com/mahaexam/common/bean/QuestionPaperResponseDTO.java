package com.mahaexam.common.bean;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class QuestionPaperResponseDTO {

	private Long id;
    private String questionPaperName;
    private List<QuestionPaperTemplateResponseDTO> paperTemplates;
    private String academicYear;
    private String status;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String description;
    private LocalDateTime createdAt;
    private QuestionPaperMetaData metaData;
    private Long tenantId;
    private Integer classId;
    private java.util.List<com.mahaexam.question.model.QuestionEntity> questions;

    /** Indicates whether the logged-in student has already attempted this exam */
    private Boolean isTaken;

    /** Summary ID from student_subject_summary — use this to fetch per-subject breakdown (null if not yet attempted) */
    private Long summaryId;

    /** When the student last attempted this exam (null if not yet attempted) */
    private LocalDateTime attemptedAt;

    /** Marks obtained by the student in this exam (null if not yet attempted) */
    private BigDecimal marksObtained;

    /** Maximum marks for this exam (null if not yet attempted) */
    private BigDecimal maxMarks;

    /** Total duration in minutes from the linked paper template (always present) */
    private Integer totalDuration;

    /** Total marks from the linked paper template (always present) */
    private Integer examTotalMarks;

    /** Total number of questions defined in the paper template (always present) */
    private Integer examTotalQuestions;

    /** Total questions in the exam (null if not yet attempted) */
    private Integer totalQuestions;

    /** Number of correct answers (null if not yet attempted) */
    private Integer correctAnswers;

    /** Number of wrong answers (null if not yet attempted) */
    private Integer wrongAnswers;

    /** Number of questions not attempted (null if not yet attempted) */
    private Integer notAnswered;

    // getters & setters
}
