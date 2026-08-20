package com.mahaexam.papertemplate.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.mahaexam.common.bean.QuestionPaperMetaData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuestionPaper {

	private Long id;
	private String questionPaperName;
	private String academicYear;
	private String status;
	private LocalDateTime startDate;
	private LocalDateTime endDate;
	private String description;
	private LocalDateTime createdAt;
	private QuestionPaperMetaData metaData;
	private Long tenantId;
	private Integer classId;

	/** Transient — populated only by findExamsByStudentPackageAndMedium */
	private Boolean isTaken;

	/** Transient — summary ID from student_subject_summary (null if not yet attempted) */
	private Long summaryId;

	/** Transient — when the student last attempted this exam (null if not yet attempted) */
	private java.time.LocalDateTime attemptedAt;

	/** Transient — marks obtained by the student (null if not yet attempted) */
	private BigDecimal marksObtained;

	/** Transient — maximum marks for this exam (null if not yet attempted) */
	private BigDecimal maxMarks;

	/** Transient — total duration in minutes from paper_template */
	private Integer totalDuration;

	/** Transient — total marks from paper_template (always present) */
	private Integer examTotalMarks;

	/** Transient — total questions attempted in this exam */
	private Integer totalQuestions;

	/** Transient — number of correct answers */
	private Integer correctAnswers;

	/** Transient — number of wrong answers */
	private Integer wrongAnswers;

	/** Transient — number of questions not attempted */
	private Integer notAnswered;
}
