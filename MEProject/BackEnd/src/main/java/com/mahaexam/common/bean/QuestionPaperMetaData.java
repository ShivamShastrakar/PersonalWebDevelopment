package com.mahaexam.common.bean;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Metadata for question paper containing SUKA and Difficulty Level distributions
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionPaperMetaData {

    @JsonProperty("skillDistribution")
    private SkillDistribution skillDistribution;

    @JsonProperty("difficultyDistribution")
    private DifficultyDistribution difficultyDistribution;


    /**
     * SUKA Distribution - Skill, Understanding, Knowledge, Application
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class SkillDistribution {

        @JsonProperty("skill")
        private Double skill;

        @JsonProperty("understanding")
        private Double understanding;

        @JsonProperty("knowledge")
        private Double knowledge;

        @JsonProperty("application")
        private Double application;
    }

    /**
     * Difficulty Level Distribution
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class DifficultyDistribution {

        @JsonProperty("hard")
        private Double hard;

        @JsonProperty("medium")
        private Double medium;

        @JsonProperty("easy")
        private Double easy;
    }
}
