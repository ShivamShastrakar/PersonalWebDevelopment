package com.mahaexam.papertemplate.bean;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class SyllabusChapterDTO {

    private Long chapterId;
    private Integer numberOfQuestions;
    private Integer marks;
    private BigDecimal coveragePercentage;

}

