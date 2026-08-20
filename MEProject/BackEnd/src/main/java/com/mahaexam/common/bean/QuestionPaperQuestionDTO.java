package com.mahaexam.common.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionPaperQuestionDTO {

    private Long id;
    private Long questionPaperId;
    private Long questionId;
    private Integer sequenceNumber;

    // Additional fields for detailed view
    private String questionText;
    private String questionType;
    private String skillLevel;
    private String difficultyLevel;
}

