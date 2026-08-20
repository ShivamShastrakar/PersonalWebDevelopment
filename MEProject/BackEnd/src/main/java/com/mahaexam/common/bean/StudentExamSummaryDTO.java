package com.mahaexam.common.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * DTO representing a student's exam summary per package.
 * Each entry corresponds to one active package the student is enrolled in,
 * showing how many question papers that package contains (matching the
 * student's class + medium) and how many the student has already attempted.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudentExamSummaryDTO {

    private Integer packageId;
    private String packageName;
    private LocalDate packageStartDate;
    private LocalDate packageEndDate;

    private Integer classId;
    private String className;
    private String medium;

    /** Total active question papers in this package matching student's class + medium */
    private Integer totalExams;

    /** Number of those papers the student has already attempted */
    private Integer takenCount;
}
