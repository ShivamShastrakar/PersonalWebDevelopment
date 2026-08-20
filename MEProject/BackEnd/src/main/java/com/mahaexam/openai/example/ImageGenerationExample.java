package com.mahaexam.openai.example;

import com.mahaexam.openai.service.McqGenerator;

import java.util.List;

/**
 * Example demonstrating improved image generation for scholarship exam diagrams
 */
public class ImageGenerationExample {

    public static void main(String[] args) {
        try {
            McqGenerator generator = new McqGenerator();

            // Path to your sample exam paper PDF with diagram examples
            String samplePdfPath = "/Users/dishikachouhan/Documents/GitHub/MahaExam/BackEnd/src/main/resources/Question Template.pdf";

            System.out.println("=== Scholarship Exam Diagram Generation Examples ===\n");

            // Example 1: Generate with reference images for better quality
            example1WithReferenceImages(generator, samplePdfPath);

            // Example 2: Generate without reference (fallback mode)
            example2WithoutReference(generator);

            // Example 3: Complete MCQ generation with images
            example3CompleteGeneration(generator, samplePdfPath);

            // Example 4: Different diagram types
            example4VariousDiagramTypes(generator, samplePdfPath);

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Example 1: Generate diagram with reference images for style matching
     */
    private static void example1WithReferenceImages(McqGenerator generator, String samplePdfPath) throws Exception {
        System.out.println("--- Example 1: With Reference Images ---");

        // Load reference images from sample PDF
        List<String> referenceImages = generator.convertPdfToBase64Images(samplePdfPath);
        System.out.println("Loaded " + referenceImages.size() + " reference images from PDF\n");

        // Generate coordinate geometry diagram
        String description1 = "Coordinate grid with x-axis from -5 to 5 and y-axis from -5 to 5. " +
                "Show point A at coordinates (3, 2) marked with a solid black dot and labeled 'A'. " +
                "Show point B at coordinates (-2, 4) marked with a solid black dot and labeled 'B'. " +
                "Draw a dashed line connecting A and B. " +
                "Include axis arrows and grid lines at every unit.";

        String imageUrl1 = generator.generateAndUploadImage(description1, referenceImages);
        System.out.println("Generated coordinate diagram: " + imageUrl1);
        System.out.println();

        // Generate transformation diagram
        String description2 = "Two coordinate grids side by side. " +
                "Left grid: Triangle with vertices at (1,1), (3,1), and (2,3) in blue outline. " +
                "Right grid: Same triangle reflected across y-axis with vertices at (-1,1), (-3,1), and (-2,3) in red outline. " +
                "Arrow between grids labeled 'Reflection across y-axis'. " +
                "Both grids from -4 to 4 on each axis.";

        String imageUrl2 = generator.generateAndUploadImage(description2, referenceImages);
        System.out.println("Generated transformation diagram: " + imageUrl2);
        System.out.println();
    }

    /**
     * Example 2: Generate without reference (uses standard prompt)
     */
    private static void example2WithoutReference(McqGenerator generator) throws Exception {
        System.out.println("--- Example 2: Without Reference Images (Standard Mode) ---");

        String description = "Simple number line from 0 to 10, with marks at each integer. " +
                "Highlight position 7 with a red dot. " +
                "Show arrow pointing from 3 to 7 labeled '+4'.";

        String imageUrl = generator.generateAndUploadImage(description);
        System.out.println("Generated number line: " + imageUrl);
        System.out.println();
    }

    /**
     * Example 3: Complete MCQ generation workflow
     */
    private static void example3CompleteGeneration(McqGenerator generator, String samplePdfPath) throws Exception {
        System.out.println("--- Example 3: Complete MCQ Generation ---");

        generator.generateAndStoreMcqs(
            "Class 5",                  // Class level
            "Mathematics",              // Subject
            "Coordinate Geometry",      // Chapter
            "Points and Lines",         // Topic
            "English",                  // Medium
            samplePdfPath              // Sample PDF for reference
        );

        System.out.println("Complete MCQ set generated with images");
        System.out.println();
    }

    /**
     * Example 4: Various diagram types for scholarship exams
     */
    private static void example4VariousDiagramTypes(McqGenerator generator, String samplePdfPath) throws Exception {
        System.out.println("--- Example 4: Various Diagram Types ---");

        List<String> referenceImages = generator.convertPdfToBase64Images(samplePdfPath);

        // Math: Fraction visualization
        String fractionDesc = "Circle divided into 8 equal parts. " +
                "5 parts shaded in light gray, 3 parts unshaded. " +
                "Label below: '5/8 shaded'. " +
                "Simple black outline, white background.";
        String fractionImg = generator.generateAndUploadImage(fractionDesc, referenceImages);
        System.out.println("Fraction diagram: " + fractionImg);

        // Math: Bar graph
        String graphDesc = "Simple bar graph with 4 bars. " +
                "X-axis labels: 'Mon', 'Tue', 'Wed', 'Thu'. " +
                "Y-axis from 0 to 10 with marks at every 2. " +
                "Bar heights: Mon=6, Tue=8, Wed=4, Thu=9. " +
                "Title: 'Books Read'. " +
                "Grid lines in background, bars in solid black.";
        String graphImg = generator.generateAndUploadImage(graphDesc, referenceImages);
        System.out.println("Bar graph: " + graphImg);

        // Science: Water cycle
        String scienceDesc = "Simple water cycle diagram. " +
                "Sun in top right with rays. " +
                "Ocean at bottom with wavy lines. " +
                "Cloud in center with raindrops falling. " +
                "Arrows showing: Evaporation (ocean to cloud), Condensation (in cloud), Precipitation (cloud to ocean). " +
                "Simple line drawing, black and white, labeled arrows.";
        String scienceImg = generator.generateAndUploadImage(scienceDesc, referenceImages);
        System.out.println("Science diagram: " + scienceImg);

        // Geometry: Angle measurement
        String angleDesc = "Two rays meeting at point O forming an angle. " +
                "One ray horizontal pointing right, other ray at 45 degrees upward. " +
                "Arc between rays near point O. " +
                "Label '45°' inside arc. " +
                "Label rays as 'OA' and 'OB'. " +
                "Simple black lines on white background.";
        String angleImg = generator.generateAndUploadImage(angleDesc, referenceImages);
        System.out.println("Geometry diagram: " + angleImg);

        System.out.println();
    }

    /**
     * Tips for writing effective image descriptions
     */
    public static void printTips() {
        System.out.println("\n=== Tips for Better Image Generation ===");
        System.out.println("1. Be specific about coordinates and measurements");
        System.out.println("2. Mention line styles (solid, dashed, dotted)");
        System.out.println("3. Specify colors if needed (prefer black & white for exams)");
        System.out.println("4. Include all text labels that should appear");
        System.out.println("5. Mention background (white, plain)");
        System.out.println("6. Specify arrow directions and types");
        System.out.println("7. Mention grid/axis requirements");
        System.out.println("8. Keep it technical, avoid artistic descriptions");
        System.out.println("9. Provide reference images when possible");
        System.out.println("10. Test with simple diagrams first");
    }
}

