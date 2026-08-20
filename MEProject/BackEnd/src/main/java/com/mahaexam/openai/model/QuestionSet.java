package com.mahaexam.openai.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Model class to combine question metadata with generated questions
 * This represents the complete question set with all context information
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionSet {

    /**
     * Metadata about the question generation parameters
     */
    private QuestionMetadata metadata;

    /**
     * List of generated questions
     */
    private List<Question> questions;

    /**
     * Timestamp when the questions were generated
     */
    private Long generatedTimestamp;

    /**
     * Total number of questions in this set
     */
    private Integer totalQuestions;
}

