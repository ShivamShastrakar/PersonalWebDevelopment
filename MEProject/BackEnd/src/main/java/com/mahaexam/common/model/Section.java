package com.mahaexam.common.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Section {

    private Long id;
    private Long partId;
    private String name;
    private Boolean displayName;
    private String questionType; // MCQ, PASSAGE_BASED, IMAGE_BASED, ESSAY
    private Integer numberOfQuestions;
    private BigDecimal marksPerQuestion;
    private BigDecimal negativeMarks;
    private BigDecimal totalMarks;
    private String status;

}

