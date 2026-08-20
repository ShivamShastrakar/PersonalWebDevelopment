package com.mahaexam.common.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuestionPaperTemplateResponseDTO {

    private Long id;
    private Long questionPaperId;
    private Long paperTemplateId;
    private Integer sequence;

    // Optional: Can include related entity names for better readability
    private String questionPaperName;
    private String paperTemplateName;

    private PaperTemplateResponse paperTemplateResponse;
}
