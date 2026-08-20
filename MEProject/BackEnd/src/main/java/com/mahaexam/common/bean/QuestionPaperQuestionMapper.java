package com.mahaexam.common.bean;

import com.mahaexam.exam.model.QuestionPaperQuestion;

public class QuestionPaperQuestionMapper {

    private QuestionPaperQuestionMapper() {
        // Private constructor to prevent instantiation
    }

    public static QuestionPaperQuestionDTO toDTO(QuestionPaperQuestion entity) {
        if (entity == null) {
            return null;
        }

        return QuestionPaperQuestionDTO.builder()
                .id(entity.getId())
                .questionPaperId(entity.getQuestionPaperId())
                .questionId(entity.getQuestionId())
                .sequenceNumber(entity.getSequenceNumber())
                .build();
    }

    public static QuestionPaperQuestion toEntity(QuestionPaperQuestionDTO dto) {
        if (dto == null) {
            return null;
        }

        return QuestionPaperQuestion.builder()
                .id(dto.getId())
                .questionPaperId(dto.getQuestionPaperId())
                .questionId(dto.getQuestionId())
                .sequenceNumber(dto.getSequenceNumber())
                .build();
    }
}

