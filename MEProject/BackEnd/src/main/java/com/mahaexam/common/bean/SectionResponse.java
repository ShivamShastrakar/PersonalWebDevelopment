package com.mahaexam.common.bean;

import lombok.Data;

@Data
public class SectionResponse {
    private Long id;
    private Long partId; // Add for batch grouping
    private String name;
    private Boolean displayName;
    private String questionType;
    private Integer numberOfQuestions;
    private Double marksPerQuestion;
    private Double negativeMarks;
    private Integer totalMarks;
}
