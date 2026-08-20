package com.mahaexam.common.bean;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;

@Data
public class QuestionPaperHierarchyResponseDTO extends QuestionPaperResponseDTO {
    private List<PartQuestionsDTO> parts;
    private Integer timeTaken;

    /**
     * Short-lived exam token valid for (totalDuration + 5 min buffer).
     * Only populated when a student starts the exam (hideAnswer = false).
     */
    private String examToken;

    /** Raw instruction strings collected from all linked paper templates. */
    private List<String> instructions;

}



