package com.mahaexam.openai.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Model class to store MCQ options
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionOptions {

    @JsonProperty("option1")
    private String option1;

    @JsonProperty("option1ImageDescription")
    private String option1ImageDescription;

    @JsonProperty("option1ImageUrl")
    private String option1ImageUrl;

    @JsonProperty("option2")
    private String option2;

    @JsonProperty("option2ImageDescription")
    private String option2ImageDescription;

    @JsonProperty("option2ImageUrl")
    private String option2ImageUrl;

    @JsonProperty("option3")
    private String option3;

    @JsonProperty("option3ImageDescription")
    private String option3ImageDescription;

    @JsonProperty("option3ImageUrl")
    private String option3ImageUrl;

    @JsonProperty("option4")
    private String option4;

    @JsonProperty("option4ImageDescription")
    private String option4ImageDescription;

    @JsonProperty("option4ImageUrl")
    private String option4ImageUrl;
}

