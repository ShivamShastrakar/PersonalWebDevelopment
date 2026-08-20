package com.mahaexam.common.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuestionPaperTemplateRequestDTO {

    private Long questionPaperId;
    private Long paperTemplateId;
    private Integer sequence;
}
