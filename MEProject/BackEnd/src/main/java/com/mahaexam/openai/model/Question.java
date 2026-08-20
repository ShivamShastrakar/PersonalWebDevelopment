package com.mahaexam.openai.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Model class to represent a single question
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Question {

    @JsonProperty("description")
    private String description;

    @JsonProperty("descriptionImageDescription")
    private String descriptionImageDescription;

    @JsonProperty("descriptionImageUrl")
    private String descriptionImageUrl;

    @JsonProperty("options")
    private QuestionOptions options;

    @JsonProperty("correctOption")
    private Integer correctOption;

    @JsonProperty("answerDescription")
    private String answerDescription;

    @JsonProperty("answerDescriptionImageDescription")
    private String answerDescriptionImageDescription;

    @JsonProperty("answerDescriptionImageUrl")
    private String answerDescriptionImageUrl;

    @JsonProperty("paragraphId")
    private String paragraphId;

    @JsonProperty("paragraphText")
    private String paragraphText;
}

