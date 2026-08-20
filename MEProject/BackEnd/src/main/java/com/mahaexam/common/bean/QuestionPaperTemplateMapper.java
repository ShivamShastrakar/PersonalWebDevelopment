package com.mahaexam.common.bean;

import com.mahaexam.papertemplate.model.QuestionPaperTemplate;

public final class QuestionPaperTemplateMapper {

    private QuestionPaperTemplateMapper() {}

    public static QuestionPaperTemplate toEntity(QuestionPaperTemplateRequestDTO dto) {
        if (dto == null) return null;

        return QuestionPaperTemplate.builder()
                .questionPaperId(dto.getQuestionPaperId())
                .paperTemplateId(dto.getPaperTemplateId())
                .sequence(dto.getSequence())
                .build();
    }

    public static QuestionPaperTemplateResponseDTO toResponse(QuestionPaperTemplate entity) {
        if (entity == null) return null;

        return QuestionPaperTemplateResponseDTO.builder()
                .id(entity.getId())
                .questionPaperId(entity.getQuestionPaperId())
                .paperTemplateId(entity.getPaperTemplateId())
                .sequence(entity.getSequence())
                .paperTemplateName(entity.getPaperTemplateName())
                .build();
    }
}
