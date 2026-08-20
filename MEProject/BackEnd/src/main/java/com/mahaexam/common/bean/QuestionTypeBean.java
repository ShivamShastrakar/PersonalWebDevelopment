package com.mahaexam.common.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Request bean for creating or updating a Question Type")
public class QuestionTypeBean {

    @Schema(description = "Unique code used in the system (e.g. mcq, paragraph-based-mcq)", example = "mcq")
    private String code;

    @Schema(description = "Display name shown in the UI", example = "Multiple Choice (MCQ)")
    private String name;

    @Schema(description = "Optional description", example = "Standard MCQ with four options")
    private String description;
}

