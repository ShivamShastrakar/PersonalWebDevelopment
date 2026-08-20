package com.mahaexam.openai.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.File;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

@Service
public class McqGenerator {

    private static final String OPENAI_API_KEY = "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"; // Replace with your key
    private static final String OPENAI_API_URL = "https://api.openai.com/v1/chat/completions";
    private static final String OPENAI_IMAGE_API_URL = "https://api.openai.com/v1/images/generations";
    private static final String S3_BUCKET = "your-s3-bucket";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/your_db";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "password";

    //private final S3Client s3Client;
    private final ObjectMapper objectMapper;

    // Flag to control whether DALL-E should revise prompts
    // When true, DALL-E may modify your prompt for safety/quality
    // When false, DALL-E uses your exact prompt
    private boolean enableRevisedPrompt = true;

    public McqGenerator() {
      //  this.s3Client = S3Client.create(); // Configure with credentials if needed
        this.objectMapper = new ObjectMapper();
    }

    /**
     * Set whether DALL-E should generate revised prompts
     * @param enable true to allow DALL-E to revise prompts (default), false to use exact prompts
     */
    public void setEnableRevisedPrompt(boolean enable) {
        this.enableRevisedPrompt = enable;
    }

    /**
     * Check if revised prompt generation is enabled
     * @return true if enabled, false otherwise
     */
    public boolean isRevisedPromptEnabled() {
        return this.enableRevisedPrompt;
    }

    public void generateAndStoreMcqs(String classLevel, String subject, String chapter, String topic, String medium, String samplePdfPath) throws Exception {
        // Step 1: Generate MCQs with OpenAI using direct HTTP call
        String prompt = buildPrompt(classLevel, subject, chapter, topic, medium);

        // Convert PDF to base64 images if provided
        List<String> base64Images = new ArrayList<>();
        if (samplePdfPath != null && !samplePdfPath.isEmpty()) {
            base64Images = convertPdfToBase64Images(samplePdfPath);
            System.out.println("Loaded " + base64Images.size() + " images from PDF");
        }

        String jsonResponse = callOpenAiChatCompletionWithImages(prompt, base64Images);

        // Parse JSON array from response
        JsonNode questionsArray = objectMapper.readTree(jsonResponse);

        System.out.println("Generated Questions JSON:\n" + objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(questionsArray));

        // Step 2: Insert question set and get set_id
       // int setId = insertQuestionSet(classLevel, subject, chapter, topic, medium);

        Iterator<JsonNode> iterator = questionsArray.elements();
        while (iterator.hasNext()) {
            JsonNode q = iterator.next();

            // Generate images if descriptions exist (with reference images for better quality)
            String qImgDesc = q.path("question_image_desc").asText(null);
            String qImgUrl = generateAndUploadImage(qImgDesc, base64Images);

            String explImgDesc = q.path("explanation_image_desc").asText(null);
            String explImgUrl = generateAndUploadImage(explImgDesc, base64Images);
            /*
            // Insert question and get question_id
            int questionId = insertQuestion(setId, q.path("question_id").asInt(), q.path("question_text").asText(),
                    qImgUrl, q.path("correct_answer").asText().charAt(0));

            // Insert options
            JsonNode options = q.path("options");
            Iterator<JsonNode> optIterator = options.elements();
            while (optIterator.hasNext()) {
                JsonNode opt = optIterator.next();
                String optImgDesc = opt.path("image_desc").asText(null);
                String optImgUrl = generateAndUploadImage(optImgDesc, base64Images);
                insertOption(questionId, opt.path("letter").asText().charAt(0), opt.path("text").asText(), optImgUrl);
            }

            // Insert explanation
            insertExplanation(questionId, q.path("explanation_text").asText(), explImgUrl);

             */
        }
    }

    /**
     * Call OpenAI Chat Completion API directly using HTTP
     */
    private String callOpenAiChatCompletion(String prompt) throws IOException {
        return callOpenAiChatCompletionWithImages(prompt, new ArrayList<>());
    }

    /**
     * Call OpenAI Chat Completion API with vision capabilities for PDF images
     */
    private String callOpenAiChatCompletionWithImages(String prompt, List<String> base64Images) throws IOException {
        URL url = new URL(OPENAI_API_URL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Authorization", "Bearer " + OPENAI_API_KEY);
        conn.setDoOutput(true);

        // Build request body
        ObjectNode requestBody = objectMapper.createObjectNode();
        requestBody.put("model", "gpt-4o");
        requestBody.put("max_tokens", 4096);
        requestBody.put("temperature", 0.7);

        ArrayNode messages = objectMapper.createArrayNode();
        ObjectNode message = objectMapper.createObjectNode();
        message.put("role", "user");

        // If images are provided, create a multi-part content message
        if (base64Images != null && !base64Images.isEmpty()) {
            ArrayNode contentArray = objectMapper.createArrayNode();

            // Add text content
            ObjectNode textContent = objectMapper.createObjectNode();
            textContent.put("type", "text");
            textContent.put("text", prompt + "\n\nRefer to the sample images provided for visual style and format reference and provide simple image");
            contentArray.add(textContent);

            // Add image contents (limit to first 3 pages to avoid token limits)
            int imageLimit = Math.min(base64Images.size(), 3);
            for (int i = 0; i < imageLimit; i++) {
                ObjectNode imageContent = objectMapper.createObjectNode();
                imageContent.put("type", "image_url");
                ObjectNode imageUrl = objectMapper.createObjectNode();
                imageUrl.put("url", "data:image/png;base64," + base64Images.get(i));
                imageContent.set("image_url", imageUrl);
                contentArray.add(imageContent);
            }

            message.set("content", contentArray);
        } else {
            message.put("content", prompt);
        }

        messages.add(message);
        requestBody.set("messages", messages);

        // Send request
        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = objectMapper.writeValueAsBytes(requestBody);
            os.write(input, 0, input.length);
        }

        // Read response
        int responseCode = conn.getResponseCode();
        if (responseCode != 200) {
            // Read error response
            try (InputStream es = conn.getErrorStream()) {
                if (es != null) {
                    String error = new String(es.readAllBytes());
                    throw new IOException("OpenAI API returned error code: " + responseCode + ", Response: " + error);
                }
            }
            throw new IOException("OpenAI API returned error code: " + responseCode);
        }

        try (InputStream is = conn.getInputStream()) {
            JsonNode response = objectMapper.readTree(is);
            System.out.println("OpenAI response JSON:"+ objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(response));
            String content = response.path("choices").get(0).path("message").path("content").asText();

            // Extract JSON from markdown code blocks if present
            if (content.contains("```json")) {
                content = content.substring(content.indexOf("```json") + 7);
                content = content.substring(0, content.indexOf("```"));
            } else if (content.contains("```")) {
                content = content.substring(content.indexOf("```") + 3);
                content = content.substring(0, content.indexOf("```"));
            }

            return content.trim();
        }
    }

    /**
     * Convert PDF pages to base64 encoded PNG images
     * Requires Apache PDFBox library: org.apache.pdfbox:pdfbox:2.0.29
     */
    public List<String> convertPdfToBase64Images(String pdfPath) throws IOException {
        List<String> base64Images = new ArrayList<>();

        try {
            // Use reflection to avoid compile-time dependency on PDFBox
            // This allows the code to compile even if PDFBox is not in classpath
            Class<?> pdDocumentClass = Class.forName("org.apache.pdfbox.pdmodel.PDDocument");
            Class<?> pdRendererClass = Class.forName("org.apache.pdfbox.rendering.PDFRenderer");
            Class<?> imageIOClass = Class.forName("javax.imageio.ImageIO");

            // PDDocument document = PDDocument.load(new File(pdfPath));
            Object document = pdDocumentClass.getMethod("load", File.class)
                .invoke(null, new File(pdfPath));

            try {
                // PDFRenderer pdfRenderer = new PDFRenderer(document);
                Object pdfRenderer = pdRendererClass.getConstructor(pdDocumentClass)
                    .newInstance(document);

                // int pageCount = document.getNumberOfPages();
                int pageCount = (Integer) pdDocumentClass.getMethod("getNumberOfPages")
                    .invoke(document);

                // Limit to first 5 pages to avoid excessive API usage
                int pagesToProcess = Math.min(pageCount, 1);

                for (int pageIndex = 0; pageIndex < pagesToProcess; pageIndex++) {
                    // BufferedImage image = pdfRenderer.renderImageWithDPI(pageIndex, 150, ImageType.RGB);
                    Object imageTypeRGB = Class.forName("org.apache.pdfbox.rendering.ImageType")
                        .getField("RGB").get(null);
                    Object bufferedImage = pdRendererClass.getMethod("renderImageWithDPI", int.class, float.class,
                        Class.forName("org.apache.pdfbox.rendering.ImageType"))
                        .invoke(pdfRenderer, pageIndex, 150f, imageTypeRGB);

                    // Convert BufferedImage to base64
                    java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
                    imageIOClass.getMethod("write", Class.forName("java.awt.image.RenderedImage"),
                        String.class, java.io.OutputStream.class)
                        .invoke(null, bufferedImage, "png", baos);

                    byte[] imageBytes = baos.toByteArray();
                    String base64Image = Base64.getEncoder().encodeToString(imageBytes);
                    base64Images.add(base64Image);

                    System.out.println("Converted PDF page " + (pageIndex + 1) + " to base64 image");
                }
            } finally {
                // document.close();
                pdDocumentClass.getMethod("close").invoke(document);
            }
        } catch (ClassNotFoundException e) {
            throw new IOException("Apache PDFBox library not found. Please add to pom.xml:\n" +
                "<dependency>\n" +
                "    <groupId>org.apache.pdfbox</groupId>\n" +
                "    <artifactId>pdfbox</artifactId>\n" +
                "    <version>2.0.29</version>\n" +
                "</dependency>", e);
        } catch (Exception e) {
            throw new IOException("Error converting PDF to images: " + e.getMessage(), e);
        }

        return base64Images;
    }

    private String buildPrompt(String classLevel, String subject, String chapter, String topic, String medium) {
        return """
                You are an expert Question Creator specializing in Board PRE UPPER PRIMARY SCHOLARSHIP EXAMINATIONS.
                
                Your task is to generate {number_of_questions} high-quality assessment questions strictly based on the input data provided below.
                
                INPUT DATA:
                {
                "board": "{Board}",
                "class": "{Class}",
                "subject": "{Subject}",
                "medium": "{Medium}",
                "chapter": "{Chapter}",
                "topic": "{Topic}",
                "skillLevel": "{SkillLevel}",
                "questionType": "{QuestionType}",
                "difficulty": "{Difficulty}"
                }
                
                INSTRUCTIONS:
                
                Questions must strictly align with the given board, class, subject, chapter, topic, skill level, question type, and difficulty.
                
                Use age-appropriate language suitable for PRE UPPER PRIMARY SCHOLARSHIP EXAM students.
                
                Each question must be conceptually accurate and exam-oriented.
                
                Provide exactly four options for each question.
                
                Set "correctOption" as the option number (1–4).
                
                Include a clear and concise "answerDescription".
                
                Additionally, generate visual descriptions to support image generation:
                
                "imageDescription" for the main question
                
                "optionImageDescriptions" for each option
                
                "answerImageDescription" to visually explain the correct answer
                
                Image descriptions must clearly describe visible elements only and must not reveal the correct answer directly.
                convert “calculation steps” into simple visual movements in the image descriptions.
                Create a concise DALL-E prompt (max 380 characters) for the image descriptions.
                Return ONLY valid JSON.
                
                Do NOT include markdown, comments, explanations, or any extra text outside the JSON response.
                
                IMPORTANT:
                
                Ensure proper UTF-8 encoding for all characters including Devanagari script.
                
                All string values must be properly escaped within double quotes.
                
                Return a strictly JSON array of exactly {number_of_questions} objects with the following structure:
                
                [
                {
                "description": "",
                "imageDescription": "",
                "options": {
                "option1": "",
                "option2": "",
                "option3": "",
                "option4": ""
                },
                "optionImageDescriptions": {
                "option1": "",
                "option2": "",
                "option3": "",
                "option4": ""
                },
                "correctOption": 2,
                "answerDescription": "",
                "answerImageDescription": ""
                }
                ]
                """.replace("{Class}", classLevel)
                .replace("{Subject}", subject)
                .replace("{Medium}", medium)
                .replace("{Chapter}", chapter)
                .replace("{Topic}", topic)
                .replace("{SkillLevel}", "Skill")
                .replace("{QuestionType}", "MCQ")
                .replace("{Difficulty}", "Medium")
                .replace("{number_of_questions}", "2");
    }


    /**
     * Generate exam-quality diagram image with strict technical prompt
     * Uses hardcoded battle-tested prompts for consistent quality
     */
    public String generateAndUploadImage(String description) throws IOException {
        return generateAndUploadImage(description, null);
    }

    /**
     * Generate exam-quality diagram with strict technical constraints
     * UPDATED: Uses direct strict prompts instead of GPT-4 rewriting
     * This approach produces results closer to ChatGPT UI quality
     */
    public String generateAndUploadImage(String description, List<String> referenceImages) throws IOException {
        if (description == null || description.isEmpty()) {
            return null;
        }

        // Use direct strict prompt for better diagram quality
        // Skip GPT-4 rewriting as it can shorten prompts and reduce quality
        String strictPrompt = createStrictTechnicalPrompt(description);
        System.out.println("Using strict technical prompt: " + strictPrompt);

        // Generate image using DALL-E 3
        return generateImageWithDallE(strictPrompt);
    }

    /**
     * Create adaptive prompt for exam-style diagrams
     * Intelligently adapts based on diagram type, mimicking ChatGPT UI behavior
     */
    private String createStrictTechnicalPrompt(String description) {
        String lowerDesc = description.toLowerCase();

        // Detect diagram type and apply appropriate template
        if (lowerDesc.contains("coordinate") || lowerDesc.contains("grid") ||
            lowerDesc.contains("axis") || lowerDesc.contains("point")) {
            return createCoordinateGridPrompt(description);
        } else if (lowerDesc.contains("shape") || lowerDesc.contains("triangle") ||
                   lowerDesc.contains("circle") || lowerDesc.contains("rectangle") ||
                   lowerDesc.contains("square") || lowerDesc.contains("polygon")) {
            return createGeometricShapePrompt(description);
        } else if (lowerDesc.contains("number line") || lowerDesc.contains("numberline")) {
            return createNumberLinePrompt(description);
        } else if (lowerDesc.contains("bar") || lowerDesc.contains("chart") ||
                   lowerDesc.contains("graph") || lowerDesc.contains("plot")) {
            return createChartPrompt(description);
        } else if (lowerDesc.contains("step") || lowerDesc.contains("process") ||
                   lowerDesc.contains("flow")) {
            return createStepDiagramPrompt(description);
        } else {
            // Generic educational diagram
            return createGenericEducationalPrompt(description);
        }
    }

    /**
     * Coordinate grid/geometry specific prompt (like ChatGPT UI handles math diagrams)
     */
    private String createCoordinateGridPrompt(String description) {
        return String.format(
            "Create a clean, exam-standard educational diagram suitable for a Class 4 mathematics scholarship examination. " +
            "The image must contain ONE single coordinate grid only. " +
            "Diagram requirements: " +
            "• Monochrome black-and-white only (no colors, no shading, no gradients) " +
            "• Thin, precise grid lines " +
            "• Clear x-axis and y-axis with arrowheads " +
            "• Simple sans-serif font for numbers and labels " +
            "• No title, no captions, no decorative elements " +
            "• Plain white background " +
            "• Print-friendly and worksheet-ready " +
                    "• Textbook style " +
            "Diagram content: %s",
            description
        );
    }

    /**
     * Geometric shapes prompt (triangles, circles, etc.)
     */
    private String createGeometricShapePrompt(String description) {
        return String.format(
            "A simple geometric diagram showing %s. " +
            "Clean black lines on white background. " +
            "Label vertices, sides, and angles clearly. " +
            "Textbook style.",
            description
        );
    }

    /**
     * Number line prompt
     */
    private String createNumberLinePrompt(String description) {
        return String.format(
            "A simple number line showing %s. " +
            "Clean horizontal line with tick marks and labels. " +
            "Black on white background. " +
            "Textbook style.",
            description
        );
    }

    /**
     * Charts and graphs prompt
     */
    private String createChartPrompt(String description) {
        return String.format(
            "A simple chart showing %s. " +
            "Clean axes and bars with clear labels. " +
            "Black on white background. " +
            "Textbook style.",
            description
        );
    }

    /**
     * Step-by-step process diagrams
     */
    private String createStepDiagramPrompt(String description) {
        return String.format(
            "A simple step-by-step diagram showing %s. " +
            "Use arrows or numbers to show sequence. " +
            "Clean and minimal. " +
            "Textbook style.",
            description
        );
    }

    /**
     * Generic educational diagram prompt (fallback)
     */
    private String createGenericEducationalPrompt(String description) {
        return String.format(
            "A simple educational diagram showing %s. " +
            "Clean, minimal, black on white background. " +
            "Textbook style.",
            description
        );
    }

    /**
     * Generate image using DALL-E 3 API
     * Configured to match ChatGPT UI behavior for educational diagrams
     * - No 'style' parameter (lets DALL-E interpret naturally)
     * - Standard quality (often better for simple diagrams)
     * - Clean, descriptive prompts
     * - Respects enableRevisedPrompt flag for prompt handling
     */
    private String generateImageWithDallE(String prompt) throws IOException {
        URL url = new URL(OPENAI_IMAGE_API_URL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Authorization", "Bearer " + OPENAI_API_KEY);
        conn.setDoOutput(true);

        ObjectNode requestBody = objectMapper.createObjectNode();
        requestBody.put("model", "dall-e-3");
//        requestBody.put("model", "gpt-4o");
        requestBody.put("prompt", prompt);
        requestBody.put("n", 1);
        requestBody.put("size", "1024x1024");
        requestBody.put("quality", "standard"); // Standard works well for clean diagrams
        // No 'style' parameter - lets DALL-E interpret the prompt naturally

        if (enableRevisedPrompt) {
            System.out.println("DALL-E Original Prompt (revised prompt enabled): " + prompt);
        } else {
            System.out.println("DALL-E Prompt (exact mode - revised prompt disabled): " + prompt);
        }

        try (OutputStream os = conn.getOutputStream()) {
            os.write(objectMapper.writeValueAsBytes(requestBody));
        }

        if (conn.getResponseCode() != 200) {
            try (InputStream es = conn.getErrorStream()) {
                String error = new String(es.readAllBytes());
                System.err.println("DALL-E API error: " + error);
                throw new IOException("OpenAI Image API error: " + conn.getResponseCode() + " - " + error);
            }
        }

        String tempUrl;
        String revisedPrompt;
        try (InputStream is = conn.getInputStream()) {
            JsonNode response = objectMapper.readTree(is);
            tempUrl = response.path("data").get(0).path("url").asText();
            revisedPrompt = response.path("data").get(0).path("revised_prompt").asText("");

            // Only log revised prompt if flag is enabled and it exists
            if (enableRevisedPrompt && !revisedPrompt.isEmpty()) {
                System.out.println("DALL-E Revised Prompt: " + revisedPrompt);
                System.out.println("Note: DALL-E modified your prompt for safety/quality. To use exact prompts, call setEnableRevisedPrompt(false)");
            } else if (!enableRevisedPrompt && !revisedPrompt.isEmpty()) {
                System.out.println("DALL-E attempted to revise prompt (ignored - using your exact prompt)");
                System.out.println("Revised version (not used): " + revisedPrompt);
            }
        }

        // Download image
        URL imageUrl = new URL(tempUrl);
        Path tempFile = Files.createTempFile("mcq_image_", ".png");
        try (InputStream in = imageUrl.openStream()) {
            Files.copy(in, tempFile, StandardCopyOption.REPLACE_EXISTING);
        }

        System.out.println("Downloaded image: " + tempFile);

        // TODO: upload to S3 and return correct key
        return tempFile.toString();
    }



    public String generateAndUploadImage1(String description) throws IOException {
        if (description == null || description.isEmpty()) {
            return null;
        }

        // Call OpenAI DALL-E API directly
        URL url = new URL(OPENAI_IMAGE_API_URL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Authorization", "Bearer " + OPENAI_API_KEY);
        conn.setDoOutput(true);

        // Build request body for image generation
        ObjectNode requestBody = objectMapper.createObjectNode();
        requestBody.put("model", "dall-e-3");
        requestBody.put("prompt", "Clean black-and-white line diagram for an upper primary (Class 4) scholarship examination question without any title just a image , Board MSCE PRE UPPER PRIMARY SCHOLARSHIP EXAMINATION diagram description: " + description);
        requestBody.put("n", 1);
        requestBody.put("style","omit");
        requestBody.put("size", "1024x1024");
//        requestBody.put("quality", "standard");

        // Send request
        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = objectMapper.writeValueAsBytes(requestBody);
            os.write(input, 0, input.length);
        }

        // Read response
        int responseCode = conn.getResponseCode();
        if (responseCode != 200) {
            throw new IOException("OpenAI Image API returned error code: " + responseCode);
        }

        String tempUrl;
        try (InputStream is = conn.getInputStream()) {
            JsonNode response = objectMapper.readTree(is);
            tempUrl = response.path("data").get(0).path("url").asText();
        }

        // Download image from temporary URL
        URL imageUrl = new URL(tempUrl);
        HttpURLConnection imageConn = (HttpURLConnection) imageUrl.openConnection();
        imageConn.setRequestMethod("GET");
        InputStream in = new BufferedInputStream(imageConn.getInputStream());
        Path tempFile = Files.createTempFile("mcq_image", ".png");
        Files.copy(in, tempFile, StandardCopyOption.REPLACE_EXISTING);
        in.close();
        System.out.println("Downloaded image to temp file: " + tempFile.toString());
        /*
        // Upload to S3
        String key = "mcq_images/" + tempFile.getFileName().toString();
        PutObjectRequest putRequest = PutObjectRequest.builder()
                .bucket(S3_BUCKET)
                .key(key)
                .build();
        s3Client.putObject(putRequest, RequestBody.fromFile(tempFile.toFile()));
        Files.delete(tempFile); // Cleanup

         */

        return "https://" + S3_BUCKET + ".s3.amazonaws.com/" + "key";
    }

    // Database insertion methods (using JDBC)
    private int insertQuestionSet(String classLevel, String subject, String chapter, String topic, String medium) throws SQLException {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
            String sql = "INSERT INTO question_sets (class, subject, chapter, topic, medium) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
                pstmt.setString(1, classLevel);
                pstmt.setString(2, subject);
                pstmt.setString(3, chapter);
                pstmt.setString(4, topic);
                pstmt.setString(5, medium);
                pstmt.executeUpdate();
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getInt(1);
                    }
                }
            }
        }
        return -1; // Error
    }

    private int insertQuestion(int setId, int localQid, String questionText, String questionImageUrl, char correctAnswer) throws SQLException {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
            String sql = "INSERT INTO questions (set_id, local_qid, question_text, question_image_url, correct_answer) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
                pstmt.setInt(1, setId);
                pstmt.setInt(2, localQid);
                pstmt.setString(3, questionText);
                pstmt.setString(4, questionImageUrl);
                pstmt.setString(5, String.valueOf(correctAnswer));
                pstmt.executeUpdate();
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getInt(1);
                    }
                }
            }
        }
        return -1;
    }

    private void insertOption(int questionId, char letter, String optionText, String optionImageUrl) throws SQLException {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
            String sql = "INSERT INTO options (question_id, letter, option_text, option_image_url) VALUES (?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, questionId);
                pstmt.setString(2, String.valueOf(letter));
                pstmt.setString(3, optionText);
                pstmt.setString(4, optionImageUrl);
                pstmt.executeUpdate();
            }
        }
    }

    private void insertExplanation(int questionId, String explanationText, String explanationImageUrl) throws SQLException {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
            String sql = "INSERT INTO explanations (question_id, explanation_text, explanation_image_url) VALUES (?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, questionId);
                pstmt.setString(2, explanationText);
                pstmt.setString(3, explanationImageUrl);
                pstmt.executeUpdate();
            }
        }
    }

    public static void main(String[] args) throws Exception {
        McqGenerator generator = new McqGenerator();

        // Example 1: Generate MCQs without sample PDF
//         generator.generateAndStoreMcqs("4", "Math", "Geometry", "Coordinate Geometry", "English", null);
/*
        // Example 2: Generate MCQs with sample PDF for reference
        String samplePdfPath = "/Users/dishikachouhan/Documents/GitHub/MahaExam/BackEnd/src/main/resources/Question Template.pdf"; // Update with your PDF path
//        generator.generateAndStoreMcqs("4", "Math", "Geometry", "Coordinate Geometry", "English", samplePdfPath);
        List<String> base64Images = new ArrayList<>();
        if (samplePdfPath != null && !samplePdfPath.isEmpty()) {
            base64Images = generator.convertPdfToBase64Images(samplePdfPath);
            System.out.println("Loaded " + base64Images.size() + " images from PDF");
        }


 */
        String prompt = "Coordinate grid with point at (2, 5) moving left to (-1, 5) and then up to (-1, 7) for maths standard 4th for scholarship exam should be shown one diagram.";
//        generator.generateAndUploadImage(prompt,base64Images);
        generator.setEnableRevisedPrompt(false);
        generator.generateAndUploadImage(prompt);

    }
}