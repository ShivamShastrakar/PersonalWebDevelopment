package com.mahaexam.common.bean;

import java.util.List;
import lombok.Data;

@Data
public class PartQuestionsDTO {
    private String partName;
    private Boolean displayName;
    private Boolean displaySubject;
    private int subjectId;
    private String subjectName;
    private List<SectionQuestionsDTO> sections;
}

