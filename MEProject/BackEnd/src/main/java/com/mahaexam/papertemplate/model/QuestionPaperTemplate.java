package com.mahaexam.papertemplate.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuestionPaperTemplate {
    private Long id;
    private Long questionPaperId;
    private Long paperTemplateId;
    private Integer sequence;
    private String paperTemplateName;
    private Long tenantId;
}
