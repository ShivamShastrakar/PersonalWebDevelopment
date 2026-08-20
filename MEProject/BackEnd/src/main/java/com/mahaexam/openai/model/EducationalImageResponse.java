package com.mahaexam.openai.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Response model for educational image generation
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EducationalImageResponse {
    private boolean success;
    private String imageUrl;
    private String imageId;
    private String promptUsed;
    private String revisedPrompt; // if DALL-E revised the prompt
    private String errorMessage;
    private Integer width;
    private Integer height;
    private String subject;
    private String topic;
    private String concept;
}