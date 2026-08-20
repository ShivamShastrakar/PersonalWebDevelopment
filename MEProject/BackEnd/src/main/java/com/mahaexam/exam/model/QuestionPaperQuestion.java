package com.mahaexam.exam.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionPaperQuestion {

    private Long id;
    private Long questionPaperId;
    private Long questionId;
    private Integer sequenceNumber;
    private LocalDateTime createdAt;
    private Long createdBy;
    private Long updatedBy;
    private Long subjectId;
    private Long partId;
    private Long sectionId;
}
