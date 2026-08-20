package com.mahaexam.openai.service;

import com.mahaexam.openai.model.EducationalImageRequest;
import com.mahaexam.openai.model.EducationalImageResponse;

/**
 * Service interface for generating educational images using AI
 * Supports maths diagrams and logical reasoning visualizations
 */
public interface EducationalImageService {

    /**
     * Generate an educational image based on the request parameters
     * @param request the educational image request containing subject, topic, concept, etc.
     * @return EducationalImageResponse with image URL and metadata
     */
    EducationalImageResponse generateEducationalImage(EducationalImageRequest request);

    /**
     * Generate a maths-specific diagram
     * @param topic maths topic (e.g., "geometry", "algebra", "trigonometry")
     * @param concept specific concept (e.g., "pythagorean theorem", "quadratic equation")
     * @param description detailed description of what to visualize
     * @param difficulty difficulty level
     * @return EducationalImageResponse with generated image
     */
    EducationalImageResponse generateMathsDiagram(String topic, String concept, String description, String difficulty);

    /**
     * Generate a logical reasoning visualization
     * @param topic logical reasoning topic (e.g., "patterns", "sequences", "venn-diagrams")
     * @param concept specific concept (e.g., "number patterns", "shape sequences")
     * @param description detailed description of what to visualize
     * @param difficulty difficulty level
     * @return EducationalImageResponse with generated image
     */
    EducationalImageResponse generateLogicalReasoningDiagram(String topic, String concept, String description, String difficulty);

    /**
     * Generate a coordinate geometry diagram
     * @param description description of the coordinate system and points to plot
     * @param includeGrid whether to include coordinate grid
     * @param includeLabels whether to include point labels
     * @return EducationalImageResponse with coordinate diagram
     */
    EducationalImageResponse generateCoordinateDiagram(String description, boolean includeGrid, boolean includeLabels);

    /**
     * Generate a geometry diagram
     * @param shapeType type of geometric shape (triangle, circle, polygon, etc.)
     * @param description detailed description of the diagram
     * @param includeMeasurements whether to include measurements/angles
     * @return EducationalImageResponse with geometry diagram
     */
    EducationalImageResponse generateGeometryDiagram(String shapeType, String description, boolean includeMeasurements);

    /**
     * Generate a pattern or sequence visualization
     * @param patternType type of pattern (number, shape, color, etc.)
     * @param description description of the pattern to visualize
     * @param sequenceLength how many elements to show in the sequence
     * @return EducationalImageResponse with pattern visualization
     */
    EducationalImageResponse generatePatternVisualization(String patternType, String description, int sequenceLength);
}