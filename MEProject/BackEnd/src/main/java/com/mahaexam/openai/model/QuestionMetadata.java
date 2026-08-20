package com.mahaexam.openai.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Model class to store input metadata for question generation
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionMetadata {
    private Integer boardId;
    private Integer classId;
    private Integer subjectId;
    private Integer chapterId;
    private Integer topicId;

    private String boardName;
    private String className;
    private String subject;
    private String medium;
    private String chapter;
    private String topic;
    private String skillLevel;
    private String questionType;
    private String difficulty;
    private Integer numberOfQuestions;
}
