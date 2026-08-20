package com.mahaexam.common.bean;

import com.mahaexam.papertemplate.model.QuestionPaper;
import com.mahaexam.question.model.QuestionEntity;

import java.util.List;

public final class QuestionPaperMapper {

    private QuestionPaperMapper() {}

    public static QuestionPaper toEntity(QuestionPaperRequestDTO dto) {
        return toEntity(dto, null);
    }

    public static QuestionPaper toEntity(QuestionPaperRequestDTO dto, UserBean user) {
        QuestionPaper questionPaper = new QuestionPaper();
        questionPaper.setQuestionPaperName(dto.getQuestionPaperName());
        questionPaper.setAcademicYear(dto.getAcademicYear());
        questionPaper.setStatus(dto.getStatus());
        questionPaper.setStartDate(dto.getStartDate());
        questionPaper.setEndDate(dto.getEndDate());
        questionPaper.setDescription(dto.getDescription());
        questionPaper.setMetaData(dto.getMetaData());
        if (user != null) {
            questionPaper.setTenantId(user.getTenantId());
        }
        return questionPaper;
    }

    public static QuestionPaperResponseDTO toResponse(QuestionPaper questionPaper) {
        QuestionPaperResponseDTO dto = new QuestionPaperResponseDTO();
        dto.setId(questionPaper.getId());
        dto.setQuestionPaperName(questionPaper.getQuestionPaperName());
        dto.setAcademicYear(questionPaper.getAcademicYear());
        dto.setStatus(questionPaper.getStatus());
        dto.setStartDate(questionPaper.getStartDate());
        dto.setEndDate(questionPaper.getEndDate());
        dto.setDescription(questionPaper.getDescription());
        dto.setCreatedAt(questionPaper.getCreatedAt());
        dto.setMetaData(questionPaper.getMetaData());
        dto.setTenantId(questionPaper.getTenantId());
        dto.setClassId(questionPaper.getClassId());
        dto.setIsTaken(questionPaper.getIsTaken());
        dto.setSummaryId(questionPaper.getSummaryId());
        dto.setAttemptedAt(questionPaper.getAttemptedAt());
        dto.setMarksObtained(questionPaper.getMarksObtained());
        dto.setMaxMarks(questionPaper.getMaxMarks());
        dto.setTotalDuration(questionPaper.getTotalDuration());
        dto.setExamTotalMarks(questionPaper.getExamTotalMarks());
        dto.setTotalQuestions(questionPaper.getTotalQuestions());
        dto.setCorrectAnswers(questionPaper.getCorrectAnswers());
        dto.setWrongAnswers(questionPaper.getWrongAnswers());
        dto.setNotAnswered(questionPaper.getNotAnswered());
        return dto;
    }

    public static QuestionPaperResponseDTO toResponse(QuestionPaper questionPaper, List<QuestionEntity> questions) {
        QuestionPaperResponseDTO dto = toResponse(questionPaper);
        dto.setQuestions(questions);
        return dto;
    }
}
