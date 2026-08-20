package com.mahaexam.common.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Request bean for creating or updating a Board–Subject–QuestionType mapping")
public class BoardSubjectQuestionTypeMappingBean {

    @Schema(description = "Board ID", example = "1")
    private int boardId;

    @Schema(description = "Subject ID", example = "39")
    private int subjectId;

    @Schema(description = "Question Type ID", example = "1")
    private int questionTypeId;
}

