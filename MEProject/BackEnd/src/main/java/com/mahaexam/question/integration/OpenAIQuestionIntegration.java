package com.mahaexam.question.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.mahaexam.common.bean.UserBean;
import com.mahaexam.openai.model.Question;
import com.mahaexam.openai.model.QuestionMetadata;
import com.mahaexam.openai.model.QuestionSet;
import com.mahaexam.openai.service.OpenAIService;
import com.mahaexam.question.model.QuestionEntity;
import com.mahaexam.question.service.QuestionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Complete integration example: OpenAI Question Generation + Database Storage
 *
 * This class demonstrates the full workflow:
 * 1. Create metadata for question generation
 * 2. Call OpenAI to generate questions
 * 3. Parse response into QuestionSet
 * 4. Save QuestionSet to database
 * 5. Query saved questions
 */
@Component
public class OpenAIQuestionIntegration {

    private static final Logger logger = LoggerFactory.getLogger(OpenAIQuestionIntegration.class);

    private final OpenAIService openAIService;
    private final QuestionService questionService;
    private final ObjectMapper objectMapper;

    public OpenAIQuestionIntegration(OpenAIService openAIService,
                                     QuestionService questionService,
                                     ObjectMapper objectMapper) {
        this.openAIService = openAIService;
        this.questionService = questionService;
        this.objectMapper = objectMapper;
        this.objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    /**
     * Complete workflow: Generate questions and save to database
     *
     * @param boardId Board ID (from database)
     * @param classId Class ID (from database)
     * @param subjectId Subject ID (from database)
     * @param chapterId Chapter ID (from database)
     * @param topicId Topic ID (from database)
     * @param className Class name (e.g., "5")
     * @param subject Subject name (e.g., "Maths")
     * @param medium Medium (e.g., "English")
     * @param chapter Chapter name (e.g., "Algebra")
     * @param topic Topic name (e.g., "Quadratic Equations")
     * @param skillLevel Skill level (e.g., "Understanding")
     * @param questionType Question type (e.g., "MCQ")
     * @param difficulty Difficulty (e.g., "Medium")
     * @param numberOfQuestions Number of questions to generate
     * @param user User  who is generating these questions
     * @return List of saved question entities
     */
    public List<QuestionEntity> generateAndSaveQuestions(
            Integer boardId,
            Integer classId,
            Integer subjectId,
            Integer chapterId,
            Integer topicId,
            String boardName,
            String className,
            String subject,
            String medium,
            String chapter,
            String topic,
            String skillLevel,
            String questionType,
            String difficulty,
            Integer numberOfQuestions,
            UserBean user) {

        try {
            logger.info("Starting question generation: {} {} questions for Class {}, {}, Topic: {}",
                       numberOfQuestions, questionType, className, subject, topic);

            // Step 1: Create metadata
            QuestionMetadata metadata = QuestionMetadata.builder()
                    .boardId(boardId)
                    .classId(classId)
                    .subjectId(subjectId)
                    .chapterId(chapterId)
                    .topicId(topicId)
                    .boardName(boardName)
                    .className(className)
                    .subject(subject)
                    .medium(medium)
                    .chapter(chapter)
                    .topic(topic)
                    .skillLevel(skillLevel)
                    .questionType(questionType)
                    .difficulty(difficulty)
                    .numberOfQuestions(numberOfQuestions)
                    .build();

            // Step 2: Generate prompt from template
            String prompt = generatePrompt(metadata);
            logger.info("Generated prompt:\n{}", prompt);

            // Step 3: Call OpenAI API
            String openAIResponse = openAIService.sendPrompt(prompt);
            logger.info("Received response from OpenAI");

            // Step 4: Parse response to QuestionSet
            QuestionSet questionSet = parseResponseToQuestionSet(openAIResponse, metadata);

            if (questionSet == null || questionSet.getQuestions() == null || questionSet.getQuestions().isEmpty()) {
                logger.error("Failed to parse OpenAI response or no questions generated");
                return List.of();
            }

            logger.info("Parsed {} questions from OpenAI response", questionSet.getQuestions().size());

            // Step 5: Save to database
            List<QuestionEntity> savedQuestions = questionService.saveQuestionSet(questionSet, user);
            logger.info("Successfully saved {} questions to database", savedQuestions.size());

            // Step 6: Write to file for backup/audit
            writeQuestionSetToFile(questionSet, "src/main/resources/generated-questions.json");

            return savedQuestions;

        } catch (Exception e) {
            logger.error("Error in question generation and save workflow", e);
            return List.of();
        }
    }

    /**
     * Generate prompt from template and metadata
     */
    private String generatePrompt(QuestionMetadata metadata) throws Exception {
        // Read prompt template
        String promptPath = "prompts/MCQ_FinalPromptText";
        if(metadata.getQuestionType().toLowerCase().contains("paragraph")){
            promptPath = "prompts/Paragraph_FinalPromptText";
        }else {
            promptPath = "prompts/MCQ_FinalPromptText";
        }
        ClassPathResource templateResource = new ClassPathResource(promptPath);
        byte[]  promptBytes = Files.readAllBytes(Paths.get(templateResource.getURI()));
        String prompt = new String(promptBytes);

        // Replace placeholders
        prompt = prompt.replace("{Board}", metadata.getBoardName())
                .replace("{Class}", metadata.getClassName())
                .replace("{Subject}", metadata.getSubject())
                .replace("{Medium}", metadata.getMedium())
                .replace("{Chapter}", metadata.getChapter())
                .replace("{Topic}", metadata.getTopic())
                .replace("{SkillLevel}", metadata.getSkillLevel())
                .replace("{QuestionType}", metadata.getQuestionType())
                .replace("{Difficulty}", metadata.getDifficulty())
                .replace("{number_of_questions}", String.valueOf(metadata.getNumberOfQuestions()));
        return prompt;
    }

    /**
     * Parse OpenAI response and create QuestionSet
     */
    private QuestionSet parseResponseToQuestionSet(String openAIResponse, QuestionMetadata metadata) {
        try {
            // Parse the OpenAI response
            var responseJson = objectMapper.readTree(openAIResponse);

            // Extract the content from choices[0].message.content
            var content = responseJson.path("choices").get(0).path("message").path("content").asText();

            // Parse the content as a List of Question objects
            List<Question> questions = objectMapper.readValue(
                content,
                objectMapper.getTypeFactory().constructCollectionType(List.class, Question.class)
            );

            // Build and return the QuestionSet
            return QuestionSet.builder()
                    .metadata(metadata)
                    .questions(questions)
                    .totalQuestions(questions.size())
                    .generatedTimestamp(System.currentTimeMillis())
                    .build();

        } catch (Exception e) {
            logger.error("Error parsing OpenAI response to QuestionSet", e);
            return null;
        }
    }

    /**
     * Write QuestionSet to file for backup/audit
     */
    private void writeQuestionSetToFile(QuestionSet questionSet, String filePath) {
        try {
            String json = objectMapper.writeValueAsString(questionSet);
            Files.write(Paths.get(filePath), json.getBytes());
            logger.info("QuestionSet written to file: {}", filePath);
        } catch (Exception e) {
            logger.error("Error writing QuestionSet to file", e);
        }
    }

    /**
     * Query saved questions by criteria
     */
    public List<QuestionEntity> findSavedQuestions(
            Integer boardId,
            Integer classId,
            Integer subjectId,
            Integer topicId,
            String questionType,
            String skillLevel,
            String difficultyLevel) {

        return questionService.findQuestions(
            boardId, classId, subjectId, topicId,
            questionType, skillLevel, difficultyLevel
        );
    }
}

