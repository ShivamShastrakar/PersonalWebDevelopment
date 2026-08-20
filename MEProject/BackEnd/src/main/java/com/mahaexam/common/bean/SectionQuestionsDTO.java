package com.mahaexam.common.bean;

import com.mahaexam.question.model.QuestionEntity;
import java.util.List;
import lombok.Data;

@Data
public class SectionQuestionsDTO {
    private String sectionName;
    private String questionType;
    private Boolean displayName;
    private Boolean displaySubject;
    private int totalQuestions;
    private List<QuestionEntity> questions;
}
