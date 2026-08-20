package com.mahaexam.openai.service;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.mahaexam.common.service.OpenAIRateLimiter;
import com.mahaexam.openai.model.Question;
import com.mahaexam.openai.model.QuestionMetadata;
import com.mahaexam.openai.model.QuestionSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

@Service
public class OpenAIServiceImpl implements  OpenAIService{
    private static final Logger logger = LoggerFactory.getLogger(OpenAIServiceImpl.class);
    private static final String API_URL = "https://api.openai.com/v1/chat/completions";

    @Value("${openai.apiKey}")
    private String apiKey;

    @Value("${openai.model:gpt-4o}")
    private String model;

    @Value("${openai.maxCompletionTokens:16384}")
    private int defaultMaxCompletionTokens;

    @Autowired
    private OpenAIRateLimiter rateLimiter;

    /**
     * Sets the OpenAI API key
     * @param apiKey the API key to set
     */
    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    public String sendPrompt(String prompt) {
        return sendPrompt(prompt, defaultMaxCompletionTokens);
    }

    @Override
    public String sendPrompt(String prompt, int maxCompletionTokens) {
        try {
            return rateLimiter.executeWithRetry(() -> doSendPrompt(prompt, maxCompletionTokens));
        } catch (OpenAIRateLimiter.RateLimitException e) {
            throw new IllegalArgumentException("OpenAI rate limit exceeded after all retries: " + e.getMessage(), e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalArgumentException("Interrupted while waiting for OpenAI rate limiter", e);
        }
    }

    /**
     * The actual HTTP call — called by the rate limiter's retry wrapper.
     * Throws {@link OpenAIRateLimiter.RateLimitException} on HTTP 429 and
     * transient 5xx / Cloudflare errors so the retry loop backs off and retries.
     */
    private String doSendPrompt(String prompt, int maxCompletionTokens) {
        StringBuilder response = new StringBuilder();

        try {
            apiKey = Objects.isNull(apiKey) ? System.getenv("openai.apiKey}") : apiKey;
            URL url = new URL(API_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", "Bearer " + apiKey);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            // Escape the prompt for JSON
            String escapedPrompt = prompt.replace("\\", "\\\\")
                                         .replace("\"", "\\\"")
                                         .replace("\n", "\\n")
                                         .replace("\r", "\\r")
                                         .replace("\t", "\\t");

            String jsonInputString = "{\"model\": \"" + model + "\", \"messages\": [{\"role\": \"user\", \"content\": \"" + escapedPrompt + "\"}], \"max_completion_tokens\": " + maxCompletionTokens + ", \"temperature\": 1.0}";

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"))) {
                    String responseLine;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                }
            } else if (isTransientError(responseCode)) {
                // 429 rate-limit + transient 5xx/Cloudflare errors — signal for retry with backoff
                String errorSnippet = readErrorSnippet(connection);
                logger.warn("⚠️ OpenAI returned HTTP {} (transient). Will retry with back-off. Body snippet: {}",
                        responseCode, errorSnippet);
                throw new OpenAIRateLimiter.RateLimitException("HTTP " + responseCode + " from OpenAI");
            } else {
                // Permanent error — log snippet only, not the full HTML page
                String errorSnippet = readErrorSnippet(connection);
                logger.error("OpenAI API permanent error HTTP {}: {}", responseCode, errorSnippet);
                throw new IllegalArgumentException("OpenAI API error " + responseCode + ": " + errorSnippet);
            }
        } catch (OpenAIRateLimiter.RateLimitException e) {
            throw e; // let the retry wrapper handle it
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while getting response from AI", e);
        }

        return response.toString();
    }

    /**
     * Returns true for error codes that are transient and worth retrying:
     * 429 (rate limit), 500/502/503/504 (server errors), 520-527 (Cloudflare transient errors).
     */
    private boolean isTransientError(int code) {
        return code == 429
                || code == 500
                || code == 502
                || code == 503
                || code == 504
                || (code >= 520 && code <= 527); // Cloudflare edge errors
    }

    /**
     * Reads the first 300 characters from the error stream to avoid logging
     * full HTML pages (e.g. Cloudflare 520 error pages).
     */
    private String readErrorSnippet(HttpURLConnection connection) {
        try {
            if (connection.getErrorStream() == null) return "(no error body)";
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(connection.getErrorStream(), "utf-8"))) {
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line.trim());
                    if (sb.length() >= 300) break;
                }
                String snippet = sb.length() > 300 ? sb.substring(0, 300) + "…" : sb.toString();
                // Strip HTML tags for cleaner logs
                return snippet.replaceAll("<[^>]+>", "").replaceAll("\\s+", " ").trim();
            }
        } catch (Exception e) {
            return "(could not read error body: " + e.getMessage() + ")";
        }
    }

    /**
     * Beautifies JSON string with proper indentation
     * @param jsonString the JSON string to beautify
     * @return formatted JSON string
     */
    private static String beautifyJson(String jsonString) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            Object json = mapper.readValue(jsonString, Object.class);
            return mapper.writeValueAsString(json);
        } catch (Exception e) {
            logger.error("Error beautifying JSON", e);
            return jsonString; // Return original if parsing fails
        }
    }

    /**
     * Extracts and beautifies the content field from OpenAI response
     * @param openAIResponse the full OpenAI API response
     * @return formatted content JSON string
     */
    private static String extractAndBeautifyContent(String openAIResponse) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);

            // Parse the OpenAI response
            var responseJson = mapper.readTree(openAIResponse);

            // Extract the content from choices[0].message.content
            var content = responseJson.path("choices").get(0).path("message").path("content").asText();

            // Parse and beautify the content (which is a JSON string)
            Object contentJson = mapper.readValue(content, Object.class);
            return mapper.writeValueAsString(contentJson);
        } catch (Exception e) {
            logger.error("Error extracting and beautifying content", e);
            return openAIResponse; // Return original if parsing fails
        }
    }

    /**
     * Creates a QuestionSet object from OpenAI response and metadata
     * @param openAIResponse the full OpenAI API response
     * @param metadata the question generation metadata
     * @return QuestionSet object with all information
     */
    public QuestionSet createQuestionSet(String openAIResponse, QuestionMetadata metadata) {
        try {
            ObjectMapper mapper = new ObjectMapper();

            // Parse the OpenAI response
            var responseJson = mapper.readTree(openAIResponse);

            // Extract the content from choices[0].message.content
            var content = responseJson.path("choices").get(0).path("message").path("content").asText();

            // Parse the content as a List of Question objects
            List<Question> questions = mapper.readValue(content, new TypeReference<List<Question>>() {});

            // Build and return the QuestionSet
            return QuestionSet.builder()
                    .metadata(metadata)
                    .questions(questions)
                    .totalQuestions(questions.size())
                    .generatedTimestamp(System.currentTimeMillis())
                    .build();
        } catch (Exception e) {
            logger.error("Error creating QuestionSet", e);
            return null;
        }
    }

    public static void main(String[] args) {
        OpenAIService openAIService = new OpenAIServiceImpl();
        String prompt ="";

        // Create metadata object
        QuestionMetadata metadata = QuestionMetadata.builder()
                .className("5")
                .subject("Maths")
                .medium("English")
                .chapter("Algebra")
                .topic("Quadratic Equations")
                .skillLevel("Understanding")
                .questionType("MCQ")
                .difficulty("Medium")
                .numberOfQuestions(5)
                .build();

        try {
            // Read from classpath resources
//            prompt = new String(Files.readAllBytes(Paths.get("src/main/resources/MCQ_FinalPromptText")));
            // Read prompt template
            String promptPath = "prompts/MCQ_FinalPromptText";
            if(metadata.getQuestionType().toLowerCase().contains("paragraph")){
                promptPath = "prompts/Paragraph_FinalPromptText";
            }else {
                promptPath = "prompts/MCQ_FinalPromptText";
            }
            ClassPathResource templateResource = new ClassPathResource(promptPath);
            byte[]  promptBytes = Files.readAllBytes(Paths.get(templateResource.getURI()));
            prompt = new String(promptBytes);
            // Replace placeholders with actual values
            prompt = prompt.replace("{Class}", metadata.getClassName())
                          .replace("{Subject}", metadata.getSubject())
                          .replace("{Medium}", metadata.getMedium())
                          .replace("{Chapter}", metadata.getChapter())
                          .replace("{Topic}", metadata.getTopic())
                          .replace("{SkillLevel}", metadata.getSkillLevel())
                          .replace("{QuestionType}", metadata.getQuestionType())
                          .replace("{Difficulty}", metadata.getDifficulty())
                          .replace("{number_of_questions}", String.valueOf(metadata.getNumberOfQuestions()));
        } catch (Exception e) {
            logger.error("Error reading MCQ_FinalPromptText file", e);
            e.printStackTrace();
        }
        System.out.println("Json prompt :"+prompt);
        ((OpenAIServiceImpl) openAIService).setApiKey("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");
        String jsonOutput =openAIService.sendPrompt(prompt);

        // Print full response (beautified)
        System.out.println("Full OpenAI Response:");
        System.out.println(beautifyJson(jsonOutput));
        System.out.println("\n" + "=".repeat(80) + "\n");

        // Create QuestionSet with metadata and questions
        QuestionSet questionSet = openAIService.createQuestionSet(jsonOutput, metadata);
        System.out.println("questionSet"+questionSet);
        // Extract and beautify the content field
        String beautifiedContent = extractAndBeautifyContent(jsonOutput);
        System.out.println("\n" + "=".repeat(80) + "\n");
        System.out.println("Extracted Questions (Beautified Content):");
        System.out.println(beautifiedContent);
        /*
        if (questionSet != null) {
            System.out.println("Question Set Created Successfully!");
            System.out.println("Metadata: " + questionSet.getMetadata());
            System.out.println("Total Questions: " + questionSet.getTotalQuestions());
            System.out.println("Generated At: " + new java.util.Date(questionSet.getGeneratedTimestamp()));
            System.out.println("\n" + "=".repeat(80) + "\n");

            // Print individual questions
            System.out.println("Questions:");
            questionSet.getQuestions().forEach(q -> {
                System.out.println("\nQ: " + q.getDescription());
                System.out.println("   Options: " + q.getOptions());
                System.out.println("   Correct: Option " + q.getCorrectOption());
                System.out.println("   Answer: " + q.getAnswerDescription());
            });
        }

        // Extract and beautify the content field
        String beautifiedContent = extractAndBeautifyContent(jsonOutput);
        System.out.println("\n" + "=".repeat(80) + "\n");
        System.out.println("Extracted Questions (Beautified Content):");
        System.out.println(beautifiedContent);

        // Write files
        try {
            // Write just the questions array to output file
//            Files.write(Paths.get("src/main/resources/output"), beautifiedContent.getBytes());
//            System.out.println("\n" + "=".repeat(80));
//            System.out.println("Questions written to: src/main/resources/output");

            // Write complete QuestionSet with metadata to a separate file
            if (questionSet != null) {
                ObjectMapper mapper = new ObjectMapper();
                mapper.enable(SerializationFeature.INDENT_OUTPUT);
                String questionSetJson = mapper.writeValueAsString(questionSet);
                Files.write(Paths.get("src/main/resources/questionSet.json"), questionSetJson.getBytes());
                System.out.println("Complete QuestionSet (with metadata) written to: src/main/resources/questionSet.json");
            }
        } catch (Exception e) {
            logger.error("Error writing to output files", e);
        }

         */
    }
}

