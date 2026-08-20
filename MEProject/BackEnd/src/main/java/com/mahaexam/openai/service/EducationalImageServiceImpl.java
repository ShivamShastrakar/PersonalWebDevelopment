package com.mahaexam.openai.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mahaexam.openai.model.EducationalImageRequest;
import com.mahaexam.openai.model.EducationalImageResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.UUID;

/**
 * Implementation of EducationalImageService for generating educational images
 * using AI for maths and logical reasoning subjects
 */
@Service
public class EducationalImageServiceImpl implements EducationalImageService {

    private static final Logger logger = LoggerFactory.getLogger(EducationalImageServiceImpl.class);

    @Autowired
    private McqGenerator mcqGenerator;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public EducationalImageResponse generateEducationalImage(EducationalImageRequest request) {
        try {
            // Extract minimal description - just the essential mathematical content
            // This prevents DALL-E from adding unnecessary elements like hands, pens, etc.
//            String description = extractMinimalDescription(request.getDescription());
            String description = request.getDescription();
            if (description == null || description.trim().isEmpty()) {
                throw new IllegalArgumentException("Description cannot be empty");
            }
            // Pass minimal description - McqGenerator's createStrictTechnicalPrompt will add minimal wrapper
            String imageUrl = mcqGenerator.generateAndUploadImage(description);

            return EducationalImageResponse.builder()
                    .success(true)
                    .imageUrl(imageUrl)
                    .imageId(UUID.randomUUID().toString())
                    .promptUsed(description) // Raw description - McqGenerator wraps it internally for DALL-E
                    .width(request.getWidth() != null ? request.getWidth() : 1024)
                    .height(request.getHeight() != null ? request.getHeight() : 1024)
                    .subject(request.getSubject())
                    .topic(request.getTopic())
                    .concept(request.getConcept())
                    .build();

        } catch (Exception e) {
            logger.error("Error generating educational image", e);
            return EducationalImageResponse.builder()
                    .success(false)
                    .errorMessage("Failed to generate image: " + e.getMessage())
                    .subject(request.getSubject())
                    .topic(request.getTopic())
                    .concept(request.getConcept())
                    .build();
        }
    }

    @Override
    public EducationalImageResponse generateMathsDiagram(String topic, String concept, String description, String difficulty) {
        EducationalImageRequest request = EducationalImageRequest.builder()
                .subject("maths")
                .topic(topic)
                .concept(concept)
                .description(description)
                .difficulty(difficulty)
                .style("diagram")
                .includeLabels(true)
                .includeGrid(topic.toLowerCase().contains("coordinate") || topic.toLowerCase().contains("graph"))
                .build();

        return generateEducationalImage(request);
    }

    @Override
    public EducationalImageResponse generateLogicalReasoningDiagram(String topic, String concept, String description, String difficulty) {
        EducationalImageRequest request = EducationalImageRequest.builder()
                .subject("logical-reasoning")
                .topic(topic)
                .concept(concept)
                .description(description)
                .difficulty(difficulty)
                .style("diagram")
                .includeLabels(true)
                .build();

        return generateEducationalImage(request);
    }

    @Override
    public EducationalImageResponse generateCoordinateDiagram(String description, boolean includeGrid, boolean includeLabels) {
        EducationalImageRequest request = EducationalImageRequest.builder()
                .subject("maths")
                .topic("coordinate-geometry")
                .concept("coordinate-system")
                .description(description)
                .difficulty("medium")
                .style("diagram")
                .includeLabels(includeLabels)
                .includeGrid(includeGrid)
                .build();

        return generateEducationalImage(request);
    }

    @Override
    public EducationalImageResponse generateGeometryDiagram(String shapeType, String description, boolean includeMeasurements) {
        String enhancedDescription = description;
        if (includeMeasurements) {
            enhancedDescription += ". Include angle measurements and side lengths where applicable.";
        }

        EducationalImageRequest request = EducationalImageRequest.builder()
                .subject("maths")
                .topic("geometry")
                .concept(shapeType)
                .description(enhancedDescription)
                .difficulty("medium")
                .style("diagram")
                .includeLabels(includeMeasurements)
                .build();

        return generateEducationalImage(request);
    }

    @Override
    public EducationalImageResponse generatePatternVisualization(String patternType, String description, int sequenceLength) {
        String enhancedDescription = String.format("%s. Show a sequence of %d elements to demonstrate the pattern.",
                description, sequenceLength);

        EducationalImageRequest request = EducationalImageRequest.builder()
                .subject("logical-reasoning")
                .topic("patterns")
                .concept(patternType + "-pattern")
                .description(enhancedDescription)
                .difficulty("medium")
                .style("sequence")
                .includeLabels(true)
                .build();

        return generateEducationalImage(request);
    }

    /**
     * Create a specialized educational prompt based on the request parameters
     * Uses ChatGPT UI-style concise prompts for better DALL-E results
     */
    private String createEducationalPrompt(EducationalImageRequest request) {
        // Use the same approach as McqGenerator for ChatGPT-quality results
        return createStrictEducationalPrompt(request);
    }

    /**
     * Create strict technical prompt mimicking ChatGPT UI behavior for educational diagrams
     */
    private String createStrictEducationalPrompt(EducationalImageRequest request) {
        String description = request.getDescription();
        if (description == null || description.trim().isEmpty()) {
            description = "educational diagram";
        }

        String lowerDesc = description.toLowerCase();
        String subject = request.getSubject() != null ? request.getSubject().toLowerCase() : "";

        // Detect diagram type and apply appropriate ChatGPT-style template
        if (lowerDesc.contains("coordinate") || lowerDesc.contains("grid") ||
            lowerDesc.contains("axis") || lowerDesc.contains("point") ||
            lowerDesc.contains("graph") || lowerDesc.contains("plot")) {
            return createCoordinateGridPrompt(description, request);
        } else if (lowerDesc.contains("triangle") || lowerDesc.contains("circle") ||
                   lowerDesc.contains("square") || lowerDesc.contains("rectangle") ||
                   lowerDesc.contains("polygon") || lowerDesc.contains("shape") ||
                   lowerDesc.contains("geometry") || lowerDesc.contains("geometric")) {
            return createGeometricShapePrompt(description, request);
        } else if (lowerDesc.contains("number line") || lowerDesc.contains("numberline")) {
            return createNumberLinePrompt(description, request);
        } else if (lowerDesc.contains("pattern") || lowerDesc.contains("sequence") ||
                   lowerDesc.contains("venn") || lowerDesc.contains("logic")) {
            return createLogicalReasoningPrompt(description, request);
        } else if (lowerDesc.contains("chart") || lowerDesc.contains("bar") ||
                   lowerDesc.contains("pie") || lowerDesc.contains("statistics")) {
            return createChartPrompt(description, request);
        } else {
            // Generic educational diagram - maths or logical reasoning
            if ("logical-reasoning".equals(subject)) {
                return createLogicalReasoningPrompt(description, request);
            } else {
                return createGenericMathsPrompt(description, request);
            }
        }
    }

    /**
     * Create the base educational prompt structure
     */
    private String createBaseEducationalPrompt(EducationalImageRequest request) {
        StringBuilder prompt = new StringBuilder();

        prompt.append("Create an educational diagram for ");
        if (request.getSubject() != null) {
            prompt.append(request.getSubject());
        }
        if (request.getTopic() != null) {
            prompt.append(" - ").append(request.getTopic());
        }
        if (request.getConcept() != null) {
            prompt.append(" (").append(request.getConcept()).append(")");
        }
        prompt.append(": ");

        prompt.append(request.getDescription());

        return prompt.toString();
    }

    /**
     * Coordinate grid/geometry specific prompt (ChatGPT UI style - ultra minimal)
     */
    private String createCoordinateGridPrompt(String description, EducationalImageRequest request) {
        // ChatGPT UI uses extremely minimal prompts - just the essential description
        // Extract key information from description and make it ultra-concise
        String cleanDescription = cleanDescriptionForPrompt(description);
        return "Coordinate grid diagram: " + cleanDescription;
    }

    /**
     * Geometric shapes prompt (ChatGPT UI style - ultra minimal)
     */
    private String createGeometricShapePrompt(String description, EducationalImageRequest request) {
        // Ultra-minimal prompt like ChatGPT UI
        String cleanDescription = cleanDescriptionForPrompt(description);
        return "Geometric diagram: " + cleanDescription;
    }

    /**
     * Number line prompt (ChatGPT UI style - ultra minimal)
     */
    private String createNumberLinePrompt(String description, EducationalImageRequest request) {
        String cleanDescription = cleanDescriptionForPrompt(description);
        return "Number line: " + cleanDescription;
    }

    /**
     * Charts and graphs prompt (ChatGPT UI style - ultra minimal)
     */
    private String createChartPrompt(String description, EducationalImageRequest request) {
        String cleanDescription = cleanDescriptionForPrompt(description);
        return "Chart diagram: " + cleanDescription;
    }

    /**
     * Logical reasoning patterns and diagrams (ChatGPT UI style - ultra minimal)
     */
    private String createLogicalReasoningPrompt(String description, EducationalImageRequest request) {
        String cleanDescription = cleanDescriptionForPrompt(description);
        if (description.toLowerCase().contains("venn")) {
            return "Venn diagram: " + cleanDescription;
        } else if (description.toLowerCase().contains("pattern") || description.toLowerCase().contains("sequence")) {
            return "Pattern diagram: " + cleanDescription;
        }
        return "Logic diagram: " + cleanDescription;
    }

    /**
     * Generic maths diagram prompt (ChatGPT UI style - ultra minimal)
     */
    private String createGenericMathsPrompt(String description, EducationalImageRequest request) {
        String cleanDescription = cleanDescriptionForPrompt(description);
        return "Maths diagram: " + cleanDescription;
    }

    /**
     * Extract minimal description - ultra-simple cleaning, preserve original structure
     * ChatGPT UI uses very minimal prompts, so we just do light cleaning
     */
    private String extractMinimalDescription(String description) {
        if (description == null || description.trim().isEmpty()) {
            return "";
        }

        // Ultra-minimal cleaning - just remove obvious verbose words, preserve everything else
        String cleaned = description.trim();
        
        // Only remove the most verbose instruction words
        cleaned = cleaned.replaceAll("(?i)\\bplot\\s+points?\\b", "points");
        cleaned = cleaned.replaceAll("(?i)\\band\\s+connect\\s+them\\s+to\\s+form\\b", "forming");
        cleaned = cleaned.replaceAll("(?i)\\bon\\s+a\\s+coordinate\\s+plane\\b", "");
        
        // Clean up extra spaces
        cleaned = cleaned.replaceAll("\\s+", " ").trim();

        return cleaned;
    }

    /**
     * Extract coordinate geometry content - preserves point labels and key details
     */
    private String extractCoordinateContent(String description) {
        // Extract labeled points like A(2,3), B(5,7), C(-1,4) with labels preserved
        java.util.regex.Pattern labeledPointPattern = java.util.regex.Pattern.compile("\\b[A-Z]\\s*\\([^)]+\\)", java.util.regex.Pattern.CASE_INSENSITIVE);
        java.util.regex.Matcher labeledMatcher = labeledPointPattern.matcher(description);
        StringBuilder labeledPoints = new StringBuilder();
        while (labeledMatcher.find()) {
            if (labeledPoints.length() > 0) labeledPoints.append(", ");
            String point = labeledMatcher.group().replaceAll("\\s+", ""); // Remove spaces
            labeledPoints.append(point);
        }

        // If no labeled points, extract coordinates with parentheses
        if (labeledPoints.length() == 0) {
            java.util.regex.Pattern coordPattern = java.util.regex.Pattern.compile("\\([^)]+\\)");
            java.util.regex.Matcher coordMatcher = coordPattern.matcher(description);
            while (coordMatcher.find()) {
                if (labeledPoints.length() > 0) labeledPoints.append(", ");
                labeledPoints.append(coordMatcher.group());
            }
        }

        // Determine shape type
        String shape = "";
        String lowerDesc = description.toLowerCase();
        if (lowerDesc.contains("triangle")) shape = "triangle";
        else if (lowerDesc.contains("line")) shape = "line";
        else if (lowerDesc.contains("quadrilateral") || lowerDesc.contains("rectangle")) shape = "quadrilateral";

        if (labeledPoints.length() > 0) {
            // Include "coordinate grid" keyword so McqGenerator detects it correctly
            // Preserve point labels and shape information
            if (!shape.isEmpty()) {
                return "coordinate grid with " + shape + " at points " + labeledPoints.toString();
            }
            return "coordinate grid with points " + labeledPoints.toString();
        }

        // Fallback: use cleaned original description
        return cleanDescriptionForPrompt(description);
    }

    /**
     * Extract geometric shape content - just shape type and key info
     */
    private String extractGeometricContent(String description) {
        String cleaned = description.trim();
        
        // Extract shape type
        String shape = "";
        String lowerDesc = description.toLowerCase();
        if (lowerDesc.contains("right triangle")) shape = "right triangle";
        else if (lowerDesc.contains("triangle")) shape = "triangle";
        else if (lowerDesc.contains("circle")) shape = "circle";
        else if (lowerDesc.contains("square")) shape = "square";
        else if (lowerDesc.contains("rectangle")) shape = "rectangle";

        // Extract measurements (numbers with units like "3cm", "4 units")
        java.util.regex.Pattern measurementPattern = java.util.regex.Pattern.compile("\\d+\\s*(cm|units?|in|m)\\b", java.util.regex.Pattern.CASE_INSENSITIVE);
        java.util.regex.Matcher measMatcher = measurementPattern.matcher(description);
        StringBuilder measurements = new StringBuilder();
        while (measMatcher.find() && measurements.length() < 50) {
            if (measurements.length() > 0) measurements.append(", ");
            measurements.append(measMatcher.group());
        }

        if (!shape.isEmpty()) {
            if (measurements.length() > 0) {
                return shape + " with " + measurements.toString();
            }
            return shape;
        }

        return cleanDescriptionForPrompt(description);
    }

    /**
     * Clean and simplify description for minimal prompt (ChatGPT UI style)
     * Removes only verbose instructions while preserving key details and structure
     */
    private String cleanDescriptionForPrompt(String description) {
        if (description == null || description.trim().isEmpty()) {
            return "";
        }

        String cleaned = description.trim();

        // Light cleaning - only remove overly verbose phrases, preserve structure and labels
        cleaned = cleaned.replaceAll("(?i)\\bplot\\s+points?\\b", "points");
        cleaned = cleaned.replaceAll("(?i)\\bon\\s+a\\s+coordinate\\s+plane\\b", "on coordinate grid");
        cleaned = cleaned.replaceAll("(?i)\\band\\s+connect\\s+them\\s+to\\s+form\\b", "forming");
        cleaned = cleaned.replaceAll("(?i)\\bshow\\s+movement\\s+with\\b", "");
        cleaned = cleaned.replaceAll("(?i)\\bclearly\\b", "");
        cleaned = cleaned.replaceAll("(?i)\\bmark\\s+points\\b", "points");
        cleaned = cleaned.replaceAll("(?i)\\bwith\\s+simple\\s+arrows\\b", "");
        
        // Clean up extra spaces but preserve structure
        cleaned = cleaned.replaceAll("\\s+", " ").trim();
        cleaned = cleaned.replaceAll("\\s*,\\s*", ", "); // Normalize comma spacing
        cleaned = cleaned.replaceAll("\\s*\\.\\s*", ". "); // Normalize period spacing

        return cleaned;
    }

    /**
     * Legacy enhanceMathsPrompt method (kept for backward compatibility)
     * @deprecated Use createStrictEducationalPrompt instead for better quality
     */
    @Deprecated
    private String enhanceMathsPrompt(String basePrompt, EducationalImageRequest request) {
        if (basePrompt == null) {
            basePrompt = "";
        }
        if (request == null) {
            return basePrompt;
        }

        StringBuilder enhancedPrompt = new StringBuilder(basePrompt);

        // Add maths-specific instructions
        enhancedPrompt.append(". Use clean, precise mathematical notation. ");

        Boolean includeGrid = request.isIncludeGrid();
        if (includeGrid != null && includeGrid) {
            enhancedPrompt.append("Include coordinate grid with proper scaling. ");
        }

        Boolean includeLabels = request.isIncludeLabels();
        if (includeLabels != null && includeLabels) {
            enhancedPrompt.append("Label all points, angles, and measurements clearly. ");
        }

        // Difficulty-based adjustments
        String difficulty = request.getDifficulty();
        if ("easy".equalsIgnoreCase(difficulty)) {
            enhancedPrompt.append("Keep the diagram simple and clear for beginners. ");
        } else if ("hard".equalsIgnoreCase(difficulty)) {
            enhancedPrompt.append("Include detailed mathematical annotations and complex elements. ");
        }

        return enhancedPrompt.toString();
    }

    /**
     * Enhance prompt specifically for logical reasoning content
     */
    private String enhanceLogicalReasoningPrompt(String basePrompt, EducationalImageRequest request) {
        if (basePrompt == null) {
            basePrompt = "";
        }
        if (request == null) {
            return basePrompt;
        }

        StringBuilder enhancedPrompt = new StringBuilder(basePrompt);

        // Add logical reasoning specific instructions
        enhancedPrompt.append(". Use clear visual elements to show relationships and patterns. ");

        Boolean includeLabels = request.isIncludeLabels();
        if (includeLabels != null && includeLabels) {
            enhancedPrompt.append("Label all elements and show clear connections between them. ");
        }

        // Topic-specific enhancements
        String topic = request.getTopic();
        if ("patterns".equalsIgnoreCase(topic)) {
            enhancedPrompt.append("Show clear progression and relationships in the pattern. ");
        } else if ("sequences".equalsIgnoreCase(topic)) {
            enhancedPrompt.append("Arrange elements in clear sequential order. ");
        } else if ("venn-diagrams".equalsIgnoreCase(topic)) {
            enhancedPrompt.append("Use proper Venn diagram format with clear overlapping regions. ");
        }

        return enhancedPrompt.toString();
    }

    /**
     * Add style-specific formatting to the prompt
     */
    private String addStyleFormatting(String basePrompt, EducationalImageRequest request) {
        if (basePrompt == null) {
            basePrompt = "";
        }
        if (request == null) {
            return basePrompt;
        }

        StringBuilder styledPrompt = new StringBuilder(basePrompt);

        // Add educational style specifications
        styledPrompt.append("Create in clean educational textbook style with black lines on white background. ");

        String style = request.getStyle();
        if ("diagram".equalsIgnoreCase(style)) {
            styledPrompt.append("Focus on clear, technical diagram representation. ");
        } else if ("illustration".equalsIgnoreCase(style)) {
            styledPrompt.append("Create an illustrative representation suitable for understanding. ");
        } else if ("graph".equalsIgnoreCase(style)) {
            styledPrompt.append("Present as a clear graph with proper axes and scaling. ");
        } else if ("mind-map".equalsIgnoreCase(style)) {
            styledPrompt.append("Organize as a mind map with connected concepts. ");
        }

        // Add final quality instructions
        styledPrompt.append("Ensure high clarity and educational value. No decorative elements.");

        return styledPrompt.toString();
    }

    /**
     * Main method for testing the Educational Image Service
     * Now uses ChatGPT UI-style concise prompts for better image quality!
     */
    public static void main(String[] args) {
        System.out.println("=== Educational Image Service Test (ChatGPT-style Prompts) ===\n");

        // Initialize the service
        EducationalImageServiceImpl service = new EducationalImageServiceImpl();
        service.mcqGenerator = new McqGenerator();
        service.mcqGenerator.setEnableRevisedPrompt(false);
        // Test 1: Generate a basic maths coordinate diagram (ChatGPT-style prompt)
        System.out.println("Test 1: Generating Maths Coordinate Diagram (ChatGPT-style)");
        try {
            EducationalImageResponse response1 = service.generateMathsDiagram(
                "coordinate-geometry",
                "point-plotting",
                "Plot points A(2,3), B(5,7), and C(-1,4) on a coordinate plane and connect them to form a triangle",
                "medium"
            );
            System.out.println("✓ Maths diagram generated successfully!");
            System.out.println("  Image URL: " + response1.getImageUrl());
            System.out.println("  Prompt used: " + response1.getPromptUsed());
        } catch (Exception e) {
            System.out.println("✗ Error generating maths diagram: " + e.getMessage());
        }

        System.out.println();
        if(true){ return; }

        // Test 2: Generate a geometry diagram (ChatGPT-style prompt)
        System.out.println("Test 2: Generating Geometry Diagram (ChatGPT-style)");
        try {
            EducationalImageResponse response2 = service.generateGeometryDiagram(
                "right-triangle",
                "right triangle with legs of length 3 and 4 units",
                true
            );
            System.out.println("✓ Geometry diagram generated successfully!");
            System.out.println("  Image URL: " + response2.getImageUrl());
            System.out.println("  Subject: " + response2.getSubject());
            System.out.println("  Concept: " + response2.getConcept());
        } catch (Exception e) {
            System.out.println("✗ Error generating geometry diagram: " + e.getMessage());
        }

        System.out.println();

        // Test 3: Generate a logical reasoning pattern (ChatGPT-style prompt)
        System.out.println("Test 3: Generating Pattern Visualization (ChatGPT-style)");
        try {
            EducationalImageResponse response3 = service.generatePatternVisualization(
                "shape-pattern",
                "Circle, Square, Triangle, Circle, Square, Triangle...",
                6
            );
            System.out.println("✓ Pattern visualization generated successfully!");
            System.out.println("  Image URL: " + response3.getImageUrl());
            System.out.println("  Topic: " + response3.getTopic());
        } catch (Exception e) {
            System.out.println("✗ Error generating pattern visualization: " + e.getMessage());
        }

        System.out.println();

        // Test 4: Generate using full request object (ChatGPT-style prompt)
        System.out.println("Test 4: Generating with Full Request Object (ChatGPT-style)");
        try {
            EducationalImageRequest request = EducationalImageRequest.builder()
                    .subject("maths")
                    .topic("algebra")
                    .concept("linear-equations")
                    .description("Graph the linear equation y = 2x + 1 on a coordinate plane")
                    .difficulty("easy")
                    .style("graph")
                    .includeLabels(true)
                    .includeGrid(true)
                    .width(1024)
                    .height(1024)
                    .build();

            EducationalImageResponse response4 = service.generateEducationalImage(request);
            System.out.println("✓ Full request diagram generated successfully!");
            System.out.println("  Image URL: " + response4.getImageUrl());
            System.out.println("  Dimensions: " + response4.getWidth() + "x" + response4.getHeight());
        } catch (Exception e) {
            System.out.println("✗ Error generating with full request: " + e.getMessage());
        }

        System.out.println();

        // Test 5: Generate logical reasoning Venn diagram (ChatGPT-style prompt)
        System.out.println("Test 5: Generating Logical Reasoning Venn Diagram (ChatGPT-style)");
        try {
            EducationalImageResponse response5 = service.generateLogicalReasoningDiagram(
                "venn-diagrams",
                "set-intersection",
                "Venn diagram showing intersection of set A (numbers 1-10) and set B (even numbers)",
                "medium"
            );
            System.out.println("✓ Venn diagram generated successfully!");
            System.out.println("  Image URL: " + response5.getImageUrl());
            System.out.println("  Topic: " + response5.getTopic());
        } catch (Exception e) {
            System.out.println("✗ Error generating Venn diagram: " + e.getMessage());
        }

        System.out.println();

        // Test 6: Coordinate diagram with specific parameters (ChatGPT-style prompt)
        System.out.println("Test 6: Generating Coordinate Diagram with Custom Settings (ChatGPT-style)");
        try {
            EducationalImageResponse response6 = service.generateCoordinateDiagram(
                "A point starting at origin (0,0) moves right 3 units to (3,0), then up 4 units to (3,4), forming a right triangle",
                true, // include grid
                true  // include labels
            );
            System.out.println("✓ Coordinate diagram generated successfully!");
            System.out.println("  Image URL: " + response6.getImageUrl());
            System.out.println("  Subject: " + response6.getSubject());
        } catch (Exception e) {
            System.out.println("✗ Error generating coordinate diagram: " + e.getMessage());
        }

        System.out.println("\n=== Test Summary ===");
        System.out.println("Educational Image Service testing completed!");
        System.out.println("Check the console output above for individual test results.");
        System.out.println("Generated images are uploaded to S3 and URLs are provided.");
        System.out.println("Note: Using ChatGPT UI-style concise prompts for better image quality!");
    }
}