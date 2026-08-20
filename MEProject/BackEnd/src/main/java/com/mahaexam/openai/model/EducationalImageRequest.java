package com.mahaexam.openai.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request model for educational image generation
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EducationalImageRequest {
    private String subject; // "maths" or "logical-reasoning"
    private String topic; // e.g., "geometry", "algebra", "patterns", "sequences"
    private String concept; // specific concept like "pythagorean theorem", "venn diagram"
    private String description; // detailed description of what to visualize
    private String difficulty; // "easy", "medium", "hard"
    private String style; // "diagram", "illustration", "graph", "mind-map"
    private boolean includeLabels; // whether to include text labels
    private boolean includeGrid; // for coordinate geometry
    private Integer width; // image width (optional, default 1024)
    private Integer height; // image height (optional, default 1024)
}