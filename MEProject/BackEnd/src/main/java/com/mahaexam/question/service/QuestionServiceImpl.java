package com.mahaexam.question.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.mahaexam.common.bean.QuestionPaperMetaData;
import com.mahaexam.common.bean.UserBean;
import com.mahaexam.common.config.S3Config;
import com.mahaexam.common.exception.ValidationException;
import com.mahaexam.common.model.Chapter;
import com.mahaexam.common.model.Config;
import com.mahaexam.common.model.Subject;
import com.mahaexam.common.model.Topic;
import com.mahaexam.common.repo.AreaOfInterestRepositoryImpl;
import com.mahaexam.common.repo.ChapterRepository;
import com.mahaexam.common.repo.SubjectRepository;
import com.mahaexam.common.repo.TopicRepository;
import com.mahaexam.common.service.ConfigService;
import com.mahaexam.common.service.DiagramGeneratorService;
import com.mahaexam.openai.model.Question;
import com.mahaexam.openai.model.QuestionMetadata;
import com.mahaexam.openai.model.QuestionSet;
import com.mahaexam.openai.service.OpenAIService;
import com.mahaexam.question.model.QuestionEntity;
import com.mahaexam.question.repository.QuestionRepository;
import com.mahaexam.tenant.management.model.Student;
import com.mahaexam.tenant.management.service.StudentService;
import org.jspecify.annotations.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * Service implementation for question management
 */
@Service
public class QuestionServiceImpl implements QuestionService {

    private static final Logger logger = LoggerFactory.getLogger(QuestionServiceImpl.class);

    private final QuestionRepository questionRepository;
    private final ObjectMapper objectMapper;
    private final OpenAIService openAIService;
    private final QuestionPdfService questionPdfService;
    private final StudentService studentService;
    private final TopicRepository topicRepository;
    private final ChapterRepository chapterRepository;
    private final SubjectRepository subjectRepository;
    private final DiagramGeneratorService diagramGeneratorService;
    private final S3Config s3Config;
    private final ConfigService configService;

    @org.springframework.beans.factory.annotation.Value("${openai.maxRetries:3}")
    private int maxRetries;

    @org.springframework.beans.factory.annotation.Value("${openai.mcqBatchSize:10}")
    private int mcqBatchSize;

    @org.springframework.beans.factory.annotation.Value("${openai.paragraphBatchSize:5}")
    private int paragraphBatchSize;

    @org.springframework.beans.factory.annotation.Value("${question.image.s3.bucket}")
    private String questionImageS3Bucket;


    public QuestionServiceImpl(QuestionRepository questionRepository,
                              ObjectMapper objectMapper,
                              OpenAIService openAIService,
                              QuestionPdfService questionPdfService,
                              StudentService studentService,
                              TopicRepository topicRepository,
                              ChapterRepository chapterRepository,
                               SubjectRepository subjectRepository,
                               DiagramGeneratorService diagramGeneratorService,
                               S3Config s3Config,
                               ConfigService configService) {
        this.questionRepository = questionRepository;
        this.objectMapper = objectMapper;
        this.openAIService = openAIService;
        this.questionPdfService = questionPdfService;
        this.studentService = studentService;
        this.topicRepository = topicRepository;
        this.chapterRepository = chapterRepository;
        this.subjectRepository = subjectRepository;
        this.diagramGeneratorService = diagramGeneratorService;
        this.s3Config = s3Config;
        this.configService = configService;
    }

    @Override
    @Transactional
    public List<QuestionEntity> saveQuestionSet(QuestionSet questionSet, UserBean user) {
        QuestionMetadata metadata = questionSet.getMetadata();
        List<QuestionEntity> entities = new ArrayList<>();

        // Generate AI prompt hash for deduplication
        String aiPromptHash = generatePromptHash(metadata);

        // Check if questions with same prompt already exist
        if (questionRepository.existsByAiPromptHash(aiPromptHash)) {
            logger.warn("Questions with same AI prompt hash already exist: {}", aiPromptHash);
            // You can choose to skip or throw exception
            // For now, we'll proceed but log a warning
        }

        // Fix duplicate paragraphIds for different paragraphs (AI sometimes reuses the same UUID)
        fixDuplicateParagraphIds(questionSet.getQuestions());

        // Convert each question to entity and save
        Set<String> existingParaIds = new HashSet<>();
        for (Question question : questionSet.getQuestions()) {
            QuestionEntity entity = convertToEntity(question, metadata, aiPromptHash,  user.getUserId());
            entity.setTenantId(user.getTenantId());
            if(Objects.nonNull(entity.getParagraphText())){
                if(!existingParaIds.add(entity.getParagraphId())){
                    entity.setParagraphText(null);
                }
            }

            // Track whether this question requires any image
            boolean requiresImage = Objects.nonNull(question.getDescriptionImageDescription())
                    || Objects.nonNull(question.getAnswerDescriptionImageDescription())
                    || (Objects.nonNull(question.getOptions()) && (
                            Objects.nonNull(question.getOptions().getOption1ImageDescription())
                         || Objects.nonNull(question.getOptions().getOption2ImageDescription())
                         || Objects.nonNull(question.getOptions().getOption3ImageDescription())
                         || Objects.nonNull(question.getOptions().getOption4ImageDescription())));

            // Atomic flag — set to true if any image future fails
            java.util.concurrent.atomic.AtomicBoolean imageGenerationFailed = new java.util.concurrent.atomic.AtomicBoolean(false);

            // Fire all image generation tasks in parallel
            List<CompletableFuture<Void>> imageFutures = new ArrayList<>();

            if (Objects.nonNull(question.getDescriptionImageDescription())) {
                imageFutures.add(CompletableFuture.runAsync(() -> {
                    String key = generateImageFromDiscription(user, question.getDescriptionImageDescription());
                    entity.setQuestionImageUrl(key);
                }));
            }
            if (Objects.nonNull(question.getAnswerDescriptionImageDescription())) {
                imageFutures.add(CompletableFuture.runAsync(() -> {
                    String key = generateImageFromDiscription(user, question.getAnswerDescriptionImageDescription());
                    entity.setAnswerExplanationImageUrl(key);
                }));
            }
            if (Objects.nonNull(question.getOptions()) && Objects.nonNull(question.getOptions().getOption1ImageDescription())) {
                imageFutures.add(CompletableFuture.runAsync(() -> {
                    String key = generateImageFromDiscription(user, question.getOptions().getOption1ImageDescription());
                    question.getOptions().setOption1ImageUrl(key);
                    question.getOptions().setOption1ImageDescription(null);
                }));
            }
            if (Objects.nonNull(question.getOptions()) && Objects.nonNull(question.getOptions().getOption2ImageDescription())) {
                imageFutures.add(CompletableFuture.runAsync(() -> {
                    String key = generateImageFromDiscription(user, question.getOptions().getOption2ImageDescription());
                    question.getOptions().setOption2ImageUrl(key);
                    question.getOptions().setOption2ImageDescription(null);
                }));
            }
            if (Objects.nonNull(question.getOptions()) && Objects.nonNull(question.getOptions().getOption3ImageDescription())) {
                imageFutures.add(CompletableFuture.runAsync(() -> {
                    String key = generateImageFromDiscription(user, question.getOptions().getOption3ImageDescription());
                    question.getOptions().setOption3ImageUrl(key);
                    question.getOptions().setOption3ImageDescription(null);
                }));
            }
            if (Objects.nonNull(question.getOptions()) && Objects.nonNull(question.getOptions().getOption4ImageDescription())) {
                imageFutures.add(CompletableFuture.runAsync(() -> {
                    String key = generateImageFromDiscription(user, question.getOptions().getOption4ImageDescription());
                    question.getOptions().setOption4ImageUrl(key);
                    question.getOptions().setOption4ImageDescription(null);
                }));
            }

            // Wait for all image generations to complete before proceeding
            if (!imageFutures.isEmpty()) {
                try {
                    CompletableFuture.allOf(imageFutures.toArray(new CompletableFuture[0])).join();
                } catch (java.util.concurrent.CompletionException ce) {
                    Throwable rootCause = ce.getCause() != null ? ce.getCause() : ce;
                    if (rootCause.getMessage() != null && rootCause.getMessage().contains("429")) {
                        logger.warn("⚠️ Image generation failed — API rate limit hit (429). " +
                                "Cause: {}", rootCause.getMessage());
                    } else {
                        logger.warn("⚠️ Image generation failed. Cause: {}", rootCause.getMessage(), rootCause);
                    }
                    imageGenerationFailed.set(true);
                }
            }

            // If this question had an image description but image generation failed,
            // skip saving it — we must not store incomplete questions without their images
            if (requiresImage && imageGenerationFailed.get()) {
                logger.warn("⚠️ Skipping question '{}' — image description was provided but image generation failed. " +
                        "Question will NOT be saved.", question.getDescription());
                continue;
            }
            String optionsJson = null;
            try {
                optionsJson = objectMapper.copy()
                        .setSerializationInclusion(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL)
                        .writeValueAsString(question.getOptions());
            } catch (JsonProcessingException e) {
                logger.error("Error converting options to JSON", e);
            }
            entity.setOptions(optionsJson);
            entities.add(entity);
        }

        // Save all questions in batch
        List<QuestionEntity> savedEntities = questionRepository.saveAll(entities);
        logger.info("Saved {} questions to database", savedEntities.size());

        return savedEntities;
    }

    private @NonNull String generateImageFromDiscription(UserBean user, String imageDescription) {
        DiagramGeneratorService.DiagramResult diagramResult = diagramGeneratorService.generateDiagram(imageDescription);
        Long tenantId = user.getTenantId();
        // Fetch configs with better error handling
//                String bucketName = getConfigValue(ConfigService.STUDY_MATERIAL, "S3 Bucket Name Not Found");
//                String cloudFrontDomain = getConfigValue(ConfigService.CLOUD_FRONT_URL, "CloudFront URL Not Found");
        // Optionally prefix key with tenantId for organization
        String key = tenantId + "/" + UUID.randomUUID() + ".svg";
        byte[] svgBytes = diagramResult.getSvgContent().getBytes(StandardCharsets.UTF_8);
        PutObjectRequest putRequest = PutObjectRequest.builder()
                .bucket(questionImageS3Bucket)
                .key(key)
                .contentType("image/svg+xml; charset=utf-8")
                .contentDisposition("inline")
                .contentLength((long) svgBytes.length)
                .build();

        s3Config.s3Client().putObject(putRequest, RequestBody.fromBytes(svgBytes));
        return key;
    }

    /**
     * Fix duplicate paragraphIds where different paragraphs share the same paragraphId.
     * This can happen when the AI model reuses the same UUID across different paragraph groups.
     *
     * Note: The AI may only include paragraphText on the FIRST question of each paragraph group,
     * so we cannot rely on paragraphText being present on all questions. We group by paragraphId
     * first, then use the first non-null paragraphText per group as the representative text.
     */
    private void fixDuplicateParagraphIds(List<Question> questions) {
        if (questions == null || questions.isEmpty()) {
            return;
        }

        // Step 1: Group ALL questions by paragraphId (include questions with null paragraphText too)
        Map<String, List<Question>> questionsByParaId = new LinkedHashMap<>();
        for (Question q : questions) {
            if (q.getParagraphId() != null && !q.getParagraphId().isEmpty()) {
                questionsByParaId.computeIfAbsent(q.getParagraphId(), k -> new ArrayList<>()).add(q);
            }
        }

        // Step 2: For each paragraphId group, detect different paragraphs sharing the same ID
        for (Map.Entry<String, List<Question>> entry : questionsByParaId.entrySet()) {
            String paraId = entry.getKey();
            List<Question> questionsInGroup = entry.getValue();

            // Collect distinct paragraph texts within this paragraphId group
            Map<String, List<Question>> subGroupsByText = new LinkedHashMap<>();
            for (Question q : questionsInGroup) {
                // Use representative text for questions without paragraphText
                String text = (q.getParagraphText() != null && !q.getParagraphText().isEmpty())
                        ? q.getParagraphText().trim()
                        : "__NO_TEXT__";
                subGroupsByText.computeIfAbsent(text, k -> new ArrayList<>()).add(q);
            }

            // Remove the __NO_TEXT__ group — these questions don't have text to differentiate
            subGroupsByText.remove("__NO_TEXT__");

            if (subGroupsByText.size() > 1) {
                // Multiple distinct paragraphTexts sharing the same paragraphId — fix it
                logger.warn("Found {} distinct paragraphs sharing paragraphId '{}'. Reassigning IDs.",
                        subGroupsByText.size(), paraId);

                boolean first = true;
                for (Map.Entry<String, List<Question>> textGroup : subGroupsByText.entrySet()) {
                    if (first) {
                        // Keep original paragraphId for the first text group
                        first = false;
                        continue;
                    }
                    // Assign a new unique UUID to subsequent text groups
                    String newParaId = UUID.randomUUID().toString();
                    logger.info("Reassigning paragraphId from '{}' to '{}' for paragraph group with {} questions",
                            paraId, newParaId, textGroup.getValue().size());
                    for (Question q : textGroup.getValue()) {
                        q.setParagraphId(newParaId);
                    }
                }
            }

            // Distribute no-text questions to the correct group if possible
            // (They share the same paragraphId, so they belong together — no action needed
            // unless the paragraphId was split above. In that case, they stay with the original.)
        }

        // Step 4: Also ensure paragraphText is propagated to all questions in a paragraph group.
        // The AI sometimes only sets paragraphText on the first question.
        // Propagate it to all questions with the same paragraphId so the save logic works correctly.
        // Re-group after fixes above
        Map<String, String> paraIdToText = new LinkedHashMap<>();
        for (Question q : questions) {
            if (q.getParagraphId() != null && !q.getParagraphId().isEmpty()
                    && q.getParagraphText() != null && !q.getParagraphText().isEmpty()) {
                paraIdToText.putIfAbsent(q.getParagraphId(), q.getParagraphText());
            }
        }
        for (Question q : questions) {
            if (q.getParagraphId() != null && !q.getParagraphId().isEmpty()
                    && (q.getParagraphText() == null || q.getParagraphText().isEmpty())) {
                String text = paraIdToText.get(q.getParagraphId());
                if (text != null) {
                    q.setParagraphText(text);
                    logger.debug("Propagated paragraphText to question with paragraphId '{}'", q.getParagraphId());
                }
            }
        }

        // Step 5: Fix questions with null/empty paragraphId but with paragraphText
        Map<String, String> textToNewId = new HashMap<>();
        for (Question q : questions) {
            if (q.getParagraphText() != null && !q.getParagraphText().isEmpty()
                    && (q.getParagraphId() == null || q.getParagraphId().isEmpty())) {
                String normalizedText = q.getParagraphText().trim();
                String newId = textToNewId.computeIfAbsent(normalizedText, k -> UUID.randomUUID().toString());
                q.setParagraphId(newId);
                logger.info("Assigned new paragraphId '{}' to question with missing paragraphId", newId);
            }
        }
    }

    @Override
    public List<QuestionEntity> findQuestions(Integer boardId, Integer classId, Integer subjectId, Integer topicId,
                                              String questionType, String skillLevel, String difficultyLevel) {
        String cloudFrontDomain = getConfigValue(ConfigService.CLOUD_FRONT_URL, "CloudFront URL Not Found");
        List<QuestionEntity> questionEntities = questionRepository.findByMetadata(boardId, classId, subjectId, topicId,
                questionType, skillLevel, difficultyLevel);
        questionEntities.stream().forEach(q->{
            QuestionUtils.setPrefixImageUlr(q, cloudFrontDomain);
        });
        return questionEntities;
    }



    @Override
    public QuestionEntity getQuestionById(Long id) {
        QuestionEntity questionEntity = questionRepository.findById(id).orElse(null);
        String cloudFrontDomain = getConfigValue(ConfigService.CLOUD_FRONT_URL, "CloudFront URL Not Found");
        QuestionUtils.setPrefixImageUlr(questionEntity, cloudFrontDomain);
        return questionEntity;
    }

    @Override
    public boolean deleteQuestion(Long id) {
        return questionRepository.deleteById(id);
    }

    @Override
    public boolean isDuplicatePrompt(String aiPromptHash) {
        return questionRepository.existsByAiPromptHash(aiPromptHash);
    }

    @Override
    @Transactional
    public List<QuestionEntity> generateAndSaveQuestions(QuestionMetadata metadata, UserBean user) throws Exception {
        int totalRequested = metadata.getNumberOfQuestions();
        logger.info("Generating {} questions for Class: {}, Subject: {}, Topic: {}",
                   totalRequested,
                   metadata.getClassName(),
                   metadata.getSubject(),
                   metadata.getTopic());

        // Determine batch size based on question type
        boolean isParagraph = metadata.getQuestionType() != null &&
                metadata.getQuestionType().toLowerCase().contains("paragraph");
        int batchSize = isParagraph ? paragraphBatchSize : mcqBatchSize;

        // For paragraph-based questions, round up to the nearest multiple of 5
        // since paragraphs are generated in groups of 5 questions each.
        // This prevents the AI from generating only 1 paragraph (5 questions) when 9 are requested.
        int adjustedTotal = totalRequested;
        if (isParagraph) {
            int questionsPerParagraph = 5;
            adjustedTotal = (int) Math.ceil((double) totalRequested / questionsPerParagraph) * questionsPerParagraph;
            if (adjustedTotal != totalRequested) {
                logger.info("Paragraph-based questions: adjusted request from {} to {} (nearest multiple of {})",
                        totalRequested, adjustedTotal, questionsPerParagraph);
            }
        }

        // If adjusted total is within batch size, generate in a single call with retry
        if (adjustedTotal <= batchSize) {
            // For paragraph: pass adjustedTotal so AI generates full paragraph groups
            QuestionMetadata adjustedMetadata = isParagraph && adjustedTotal != totalRequested
                    ? cloneMetadataWithCount(metadata, adjustedTotal) : metadata;
            return generateBatchWithRetry(adjustedMetadata, user, adjustedTotal);
        }

        // Split into batches for large requests
        logger.info("Splitting request of {} questions into batches of {} (type: {})",
                adjustedTotal, batchSize, metadata.getQuestionType());

        List<QuestionEntity> allSavedQuestions = new ArrayList<>();
        int remaining = adjustedTotal;

        while (remaining > 0) {
            int currentBatchSize = Math.min(remaining, batchSize);

            // For paragraph questions, ensure each batch is a multiple of 5
            if (isParagraph && currentBatchSize % 5 != 0) {
                currentBatchSize = (int) Math.ceil((double) currentBatchSize / 5) * 5;
                currentBatchSize = Math.min(currentBatchSize, batchSize);
            }

            logger.info("Generating batch of {} questions ({} remaining out of {})",
                    currentBatchSize, remaining, adjustedTotal);

            // Clone metadata with updated number of questions for this batch
            QuestionMetadata batchMetadata = cloneMetadataWithCount(metadata, currentBatchSize);

            List<QuestionEntity> batchQuestions = generateBatchWithRetry(batchMetadata, user, currentBatchSize);
            allSavedQuestions.addAll(batchQuestions);
            remaining -= batchQuestions.size();

            // Small delay between batches to avoid rate limiting
            if (remaining > 0) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }

        logger.info("Successfully generated and saved {} out of {} requested questions (adjusted total: {})",
                allSavedQuestions.size(), totalRequested, adjustedTotal);

        if (allSavedQuestions.size() < totalRequested) {
            logger.warn("⚠️ Could only generate {} out of {} requested questions after all batches and retries",
                    allSavedQuestions.size(), totalRequested);
        }

        return allSavedQuestions;
    }

    /**
     * Clone QuestionMetadata with a different numberOfQuestions value.
     */
    private QuestionMetadata cloneMetadataWithCount(QuestionMetadata source, int numberOfQuestions) {
        return QuestionMetadata.builder()
                .boardId(source.getBoardId())
                .classId(source.getClassId())
                .subjectId(source.getSubjectId())
                .chapterId(source.getChapterId())
                .topicId(source.getTopicId())
                .boardName(source.getBoardName())
                .className(source.getClassName())
                .subject(source.getSubject())
                .medium(source.getMedium())
                .chapter(source.getChapter())
                .topic(source.getTopic())
                .skillLevel(source.getSkillLevel())
                .questionType(source.getQuestionType())
                .difficulty(source.getDifficulty())
                .numberOfQuestions(numberOfQuestions)
                .build();
    }

    /**
     * Generate a single batch of questions with retry logic.
     * If OpenAI returns fewer questions than requested, retries with the remaining count.
     */
    private List<QuestionEntity> generateBatchWithRetry(QuestionMetadata metadata, UserBean user, int targetCount) throws Exception {
        List<QuestionEntity> allBatchQuestions = new ArrayList<>();
        int remaining = targetCount;

        for (int attempt = 0; attempt <= maxRetries && remaining > 0; attempt++) {
            if (attempt > 0) {
                logger.info("Retry attempt {} for {} remaining questions (Topic: {})",
                        attempt, remaining, metadata.getTopic());
                // Small delay before retry
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }

            // Update metadata with remaining count for retries
            QuestionMetadata currentMetadata = (attempt == 0) ? metadata : cloneMetadataWithCount(metadata, remaining);

            // Step 1: Generate prompt
            String prompt = generatePromptFromMetadata(currentMetadata);
            if (attempt == 0) {
                logger.info("Generated prompt: {}", prompt);
            }

            // Step 2: Call OpenAI API
            String openAIResponse = openAIService.sendPrompt(prompt);
            logger.info("Received response from OpenAI (attempt {})", attempt + 1);

            // Step 3: Parse response
            QuestionSet questionSet = createQuestionSetFromResponse(openAIResponse, currentMetadata);

            if (questionSet == null || questionSet.getQuestions() == null || questionSet.getQuestions().isEmpty()) {
                logger.error("Failed to create QuestionSet from OpenAI response on attempt {}", attempt + 1);
                if (attempt == maxRetries) {
                    if (allBatchQuestions.isEmpty()) {
                        throw new IllegalStateException("Failed to generate questions from OpenAI response after " + (maxRetries + 1) + " attempts");
                    }
                    break;
                }
                continue;
            }

            int receivedCount = questionSet.getQuestions().size();
            logger.info("Parsed {} questions from OpenAI response (requested {})", receivedCount, remaining);

            // Step 4: Save to database
            List<QuestionEntity> savedQuestions = saveQuestionSet(questionSet, user);
            allBatchQuestions.addAll(savedQuestions);
            logger.info("Saved {} questions to database (total so far: {})", savedQuestions.size(), allBatchQuestions.size());

            remaining = targetCount - allBatchQuestions.size();

            if (remaining <= 0) {
                break;
            }

            logger.warn("⚠️ OpenAI returned fewer questions than requested ({} vs {}). Will retry for remaining {}",
                    receivedCount, remaining + receivedCount, remaining);
        }

        return allBatchQuestions;
    }

    /**
     * Generate prompt from template file and metadata
     */
    private String generatePromptFromMetadata(QuestionMetadata metadata) throws Exception {
        // Read prompt template
        String promptPath = "prompts/MCQ_FinalPromptText";
        if(metadata.getQuestionType().toLowerCase().contains("paragraph")){
            if (metadata.getSubject().toLowerCase().contains("iq")) {
                promptPath = "prompts/IQ_Paragraph_FinalPromptText";
            } else {
                promptPath = "prompts/Paragraph_FinalPromptText";
            }
        } else if(metadata.getSubject().toLowerCase().contains("mathematics")
                || metadata.getSubject().toLowerCase().contains("math")) {
            promptPath = "prompts/Math_MCQ_FinalPromptText";
        } else if (metadata.getSubject().toLowerCase().contains("iq")) {
            promptPath = "prompts/IQ_MCQ_FinalPromptText";
        }
        logger.info("Using prompt template: {}", promptPath);
        ClassPathResource templateResource = new ClassPathResource(promptPath);
        byte[]  promptBytes = Files.readAllBytes(Paths.get(templateResource.getURI()));

        String prompt = new String(promptBytes);
        String medium = metadata.getMedium();
        if(medium==null || medium.isEmpty()){
            medium="English";
        }
        if(medium.equalsIgnoreCase("Semi English") || medium.equalsIgnoreCase("Semi-English")){
            medium="English";
        }
        // Replace placeholders with metadata values
        prompt = prompt.replace("{Class}", metadata.getClassName())
                      .replace("{Subject}", metadata.getSubject())
                      .replace("{Medium}", medium)
                      .replace("{Chapter}", metadata.getChapter())
                      .replace("{Topic}", metadata.getTopic())
                      .replace("{SkillLevel}", metadata.getSkillLevel())
                      .replace("{QuestionType}", metadata.getQuestionType())
                      .replace("{Difficulty}", metadata.getDifficulty())
                      .replace("{number_of_questions}", String.valueOf(metadata.getNumberOfQuestions()));

        return prompt;
    }

    /**
     * Create QuestionSet from OpenAI response and metadata
     */
    private QuestionSet createQuestionSetFromResponse(String openAIResponse, QuestionMetadata metadata) {
        try {
            // Parse the OpenAI response JSON
            var responseJson = objectMapper.readTree(openAIResponse);

            logger.info("OpenAI response JSON:\n{}", objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(responseJson));

            // Check if response was truncated due to token limit
            String finishReason = responseJson.path("choices").get(0).path("finish_reason").asText();
            if ("length".equals(finishReason)) {
                logger.error("OpenAI response was truncated due to token limit. Please increase max_completion_tokens.");
                throw new IllegalStateException("OpenAI response truncated - increase max_completion_tokens in OpenAIService");
            }

            // Extract the content from choices[0].message.content
            String content = responseJson.path("choices").get(0).path("message").path("content").asText();

            // Log the raw content for debugging
            logger.info("Extracted content length: {} chars", content.length());
            logger.info("Extracted content (first 1000 chars):\n{}", content.length() > 1000 ? content.substring(0, 1000) + "..." : content);

            // Clean the content - remove markdown code blocks if present
            String cleanedContent = content.trim();

            // Remove markdown JSON code blocks (```json ... ``` or ``` ... ```)
            if (cleanedContent.startsWith("```")) {
                int firstNewLine = cleanedContent.indexOf('\n');
                int lastCodeBlock = cleanedContent.lastIndexOf("```");
                if (firstNewLine != -1 && lastCodeBlock > firstNewLine) {
                    cleanedContent = cleanedContent.substring(firstNewLine + 1, lastCodeBlock).trim();
                    logger.info("Removed markdown code blocks from content");
                }
            }

            // Additional validation - check if content starts with '['
            if (!cleanedContent.startsWith("[")) {
                logger.error("Content does not start with '['. First 100 chars: {}",
                    cleanedContent.length() > 100 ? cleanedContent.substring(0, 100) : cleanedContent);

                // Try to find JSON array in the content
                int arrayStart = cleanedContent.indexOf('[');
                if (arrayStart != -1) {
                    cleanedContent = cleanedContent.substring(arrayStart);
                    logger.info("Found array start at position {}, extracted from there", arrayStart);
                }
            }

            // Validate JSON completeness - check if response ends properly
            if (!cleanedContent.trim().endsWith("]")) {
                logger.warn("⚠️ Truncated JSON detected — response does not end with ']'. " +
                        "OpenAI likely hit the token limit (finish_reason=length). " +
                        "Attempting to repair and salvage complete questions...");
                logger.warn("Content tail (last 200 chars): {}",
                        cleanedContent.substring(Math.max(0, cleanedContent.length() - 200)));

                // Attempt to repair: truncate to the last complete question object
                cleanedContent = repairTruncatedJsonArray(cleanedContent);

                if (cleanedContent == null || cleanedContent.equals("[]")) {
                    logger.error("Could not salvage any complete questions from truncated response. " +
                            "Please increase openai.maxCompletionTokens in application properties.");
                    throw new IllegalStateException(
                            "OpenAI response was truncated and no complete questions could be recovered. " +
                            "Increase openai.maxCompletionTokens (currently set too low).");
                }
                logger.info("Repaired JSON: salvaged content length {} chars", cleanedContent.length());
            }

            // Log cleaned content
            logger.info("Cleaned content length: {} chars", cleanedContent.length());
            logger.debug("Cleaned content (first 1000 chars):\n{}", cleanedContent.length() > 1000 ? cleanedContent.substring(0, 1000) + "..." : cleanedContent);

            // Parse the content as a List of Question objects
            List<Question> questions;
            try {
                // Ensure UTF-8 encoding
                byte[] utf8Bytes = cleanedContent.getBytes(StandardCharsets.UTF_8);
                String utf8Content = new String(utf8Bytes, StandardCharsets.UTF_8);

                // Sanitize common AI JSON issues (e.g. unescaped apostrophes inside string values)
                utf8Content = sanitizeAiJson(utf8Content);

                // Use a lenient mapper that tolerates minor JSON deviations from the AI
                ObjectMapper lenientMapper = JsonMapper.builder()
                        .enable(JsonReadFeature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER)
                        .enable(JsonReadFeature.ALLOW_SINGLE_QUOTES)
                        .enable(JsonReadFeature.ALLOW_UNESCAPED_CONTROL_CHARS)
                        .build();

                questions = lenientMapper.readValue(
                    utf8Content,
                    new TypeReference<List<Question>>() {}
                );
            } catch (JsonProcessingException e) {
                logger.error("Failed to parse questions JSON.");
                logger.error("Content length: {}", cleanedContent.length());
                logger.error("Full cleaned content:\n{}", cleanedContent);
                logger.error("JSON parsing error details", e);

                // Log byte information for debugging encoding issues
                byte[] bytes = cleanedContent.getBytes(StandardCharsets.UTF_8);
                logger.error("Content as UTF-8 bytes length: {}", bytes.length);

                throw e;
            }

            if (questions == null || questions.isEmpty()) {
                logger.error("Parsed questions list is null or empty");
                return null;
            }

            logger.info("Successfully parsed {} questions from OpenAI response", questions.size());

            // Build and return the QuestionSet
            return QuestionSet.builder()
                    .metadata(metadata)
                    .questions(questions)
                    .totalQuestions(questions.size())
                    .generatedTimestamp(System.currentTimeMillis())
                    .build();

        } catch (Exception e) {
            logger.error("Error creating QuestionSet from OpenAI response", e);
            return null;
        }
    }

    /**
     * Convert OpenAI Question model to database QuestionEntity
     */
    private QuestionEntity convertToEntity(Question question, QuestionMetadata metadata,
                                          String aiPromptHash, Long createdBy) {
        // Convert options to JSON string
        String optionsJson = null;
        try {
            optionsJson = objectMapper.copy()
                    .setSerializationInclusion(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL)
                    .writeValueAsString(question.getOptions());
        } catch (JsonProcessingException e) {
            logger.error("Error converting options to JSON", e);
        }

        // Convert correct answer to JSON string
        String correctAnswerJson = null;
        try {
            // Create a simple object with the correct option
            correctAnswerJson = objectMapper.writeValueAsString(
                new CorrectAnswerWrapper(question.getCorrectOption())
            );
        } catch (JsonProcessingException e) {
            logger.error("Error converting correct answer to JSON", e);
        }

        return QuestionEntity.builder()
                .boardId(metadata.getBoardId())
                .subjectId(metadata.getSubjectId())
                .classId(metadata.getClassId())
                .medium(metadata.getMedium())
                .chapterId(metadata.getChapterId())
                .topicId(metadata.getTopicId())
                .questionType(metadata.getQuestionType())
                .questionText(question.getDescription())
                .options(optionsJson)
                .correctAnswer(correctAnswerJson)
                .answerExplanation(question.getAnswerDescription())
                .skillLevel(metadata.getSkillLevel())
                .difficultyLevel(metadata.getDifficulty())
                .aiPromptHash(aiPromptHash)
                .createdAt(LocalDateTime.now())
                .createdBy(createdBy)
                .paragraphId(question.getParagraphId())
                .paragraphText(question.getParagraphText())
                .build();
    }

    /**
     * Sanitizes JSON produced by AI models to fix common issues before parsing.
     *
     * <p>The AI sometimes emits:
     * <ul>
     *   <li>Unescaped apostrophes/single-quotes inside double-quoted string values
     *       (e.g. {@code "it's"} written as {@code it's} without escaping the single quote
     *       in a context where the parser expects only valid JSON characters).</li>
     *   <li>Raw control characters inside strings.</li>
     * </ul>
     *
     * <p>This method walks the JSON character by character, tracking whether it is
     * inside a double-quoted string, and escapes any bare single-quote ({@code '}) it
     * encounters there.  Backslash-escaped characters are skipped over so existing
     * escape sequences are not double-escaped.
     */
    private String sanitizeAiJson(String json) {
        if (json == null || json.isEmpty()) {
            return json;
        }
        StringBuilder sb = new StringBuilder(json.length());
        boolean inString = false;
        for (int i = 0; i < json.length(); i++) {
            char c = json.charAt(i);
            if (inString) {
                if (c == '\\') {
                    // Skip the escaped character — copy both chars unchanged
                    sb.append(c);
                    if (i + 1 < json.length()) {
                        sb.append(json.charAt(++i));
                    }
                } else if (c == '"') {
                    // End of string
                    inString = false;
                    sb.append(c);
                } else if (c == '\'') {
                    // Unescaped single-quote inside a JSON string — escape it
                    sb.append("\\'");
                } else {
                    sb.append(c);
                }
            } else {
                if (c == '"') {
                    inString = true;
                }
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * Attempts to repair a truncated JSON array from OpenAI by finding the last
     * complete top-level object and closing the array there.
     * Salvages all complete question objects from a partially cut-off response.
     *
     * @param truncated the incomplete JSON string starting with '['
     * @return a valid JSON array containing only complete objects, or "[]" if none found
     */
    private String repairTruncatedJsonArray(String truncated) {
        if (truncated == null || truncated.isBlank()) return "[]";

        // Walk through the string tracking brace depth at the top level of the array.
        // Every time depth goes from 1 → 0 we have just closed a complete top-level object.
        int depth = 0;
        boolean inString = false;
        int lastCompleteObjectEnd = -1; // index of '}' that closed the last complete top-level object

        for (int i = 0; i < truncated.length(); i++) {
            char c = truncated.charAt(i);

            if (inString) {
                if (c == '\\') {
                    i++; // skip escaped char
                } else if (c == '"') {
                    inString = false;
                }
                continue;
            }

            switch (c) {
                case '"' -> inString = true;
                case '{' -> depth++;
                case '}' -> {
                    depth--;
                    // depth == 1 means we just closed a top-level object inside the outer array '[...]'
                    if (depth == 1) {
                        lastCompleteObjectEnd = i;
                    }
                }
                default -> { /* ignore */ }
            }
        }

        if (lastCompleteObjectEnd == -1) {
            logger.warn("repairTruncatedJsonArray: no complete top-level object found in truncated response");
            return "[]";
        }

        // Build repaired array: everything up to and including the last complete object + ']'
        String repaired = truncated.substring(0, lastCompleteObjectEnd + 1).trim();

        // Strip any trailing comma left after the last object (e.g. "...}, " → "...}")
        if (repaired.endsWith(",")) {
            repaired = repaired.substring(0, repaired.length() - 1).trim();
        }

        repaired = repaired + "]";
        logger.info("repairTruncatedJsonArray: repaired — salvaged up to char {}/{} of original response",
                lastCompleteObjectEnd + 1, truncated.length());
        return repaired;
    }

    /**
     * Generate a hash from question metadata for deduplication
     */
    private String generatePromptHash(QuestionMetadata metadata) {
        try {
            // Create a unique string from metadata
            String promptString = String.format("%s|%s|%s|%s|%s|%s|%s|%s",
                    metadata.getClassName(),
                    metadata.getSubject(),
                    metadata.getMedium(),
                    metadata.getChapter(),
                    metadata.getTopic(),
                    metadata.getSkillLevel(),
                    metadata.getQuestionType(),
                    metadata.getDifficulty()
            );

            // Generate SHA-256 hash
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(promptString.getBytes(StandardCharsets.UTF_8));

            // Convert to hex string
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            logger.error("Error generating prompt hash", e);
            return null;
        }
    }

    @Override
    public byte[] getRandomQuestionsAsPdf(QuestionMetadata metadata, UserBean user) {
        logger.info("Fetching {} random questions for PDF generation for user: {}",
                    metadata.getNumberOfQuestions(), user.getUserId());

        // Fetch student's preferred medium from StudentService if not provided in metadata
        if (metadata.getMedium() == null || metadata.getMedium().isEmpty()) {
            try {
                Student student = studentService.findByUserId(user.getUserId()).orElse(null);
                if (student != null && student.getMedium() != null) {
                    metadata.setMedium(student.getMedium());
                    if(metadata.getMedium().equalsIgnoreCase("Semi English") || metadata.getMedium().equalsIgnoreCase("Semi-English")){
                        metadata.setMedium("English");
                    }
                    logger.info("Set medium from student profile: {}", student.getMedium());
                }
            } catch (Exception e) {
                logger.warn("Could not fetch student profile for user ID: {}", user.getUserId(), e);
            }
        }

        // Fetch random questions from repository
        List<QuestionEntity> questions = questionRepository.findRandomQuestions(
                metadata.getBoardId(),
                metadata.getClassId(),
                metadata.getSubjectId(),
                metadata.getChapterId(), // Pass chapterId if available
                metadata.getTopicId(),
                metadata.getQuestionType(),
                metadata.getSkillLevel(),
                metadata.getDifficulty(),
                metadata.getMedium(),
                metadata.getNumberOfQuestions()
        );

        if (questions.isEmpty()) {
            logger.warn("No questions found matching the criteria");
            throw new IllegalStateException("No questions found matching the criteria");
        }

        logger.info("Found {} questions, generating PDF", questions.size());

        String cloudFrontDomain = getConfigValue(ConfigService.CLOUD_FRONT_URL, "CloudFront URL Not Found");
        questions.forEach(q->{
            QuestionUtils.setPrefixImageUlr(q, cloudFrontDomain);
        });

        // Generate PDF with provided metadata (names already included from request)
        return questionPdfService.generateQuestionPdf(questions, metadata);
    }

    @Override
    @Transactional
    public Map<Integer, List<QuestionEntity>> generateQuestionsForChapterTopics(QuestionMetadata metadataReq,
            UserBean user) throws Exception {
        Integer boardId = metadataReq.getBoardId();
        Integer classId = metadataReq.getClassId();
        Integer subjectId = metadataReq.getSubjectId();
        Integer chapterId = metadataReq.getChapterId();
        Integer noOfQuestionsPerTopic = metadataReq.getNumberOfQuestions();
        String medium = metadataReq.getMedium();
        String questionType = metadataReq.getQuestionType() != null ? metadataReq.getQuestionType() : "MCQ"; // Use from input or default to MCQ
        String skillLevel = metadataReq.getSkillLevel() != null ? metadataReq.getSkillLevel() : "Intermediate"; // Use from input or default
        String difficulty = metadataReq.getDifficulty() != null ? metadataReq.getDifficulty() : "Medium"; // Use from input or default

        logger.info("Starting generation of {} {} questions per topic for Chapter ID: {}, Board: {}, Class: {}, Subject: {}, Medium: {}, SkillLevel: {}, Difficulty: {}",
                noOfQuestionsPerTopic, questionType, chapterId, boardId, classId, subjectId, medium, skillLevel, difficulty);

        // Step 1: Fetch all topics for the given chapter
        List<Topic> topics = topicRepository.findByBoardClassSubjectAndChapter(
                boardId, classId, subjectId, chapterId, user.getTenantId());

        if (topics == null || topics.isEmpty()) {
            logger.warn("No topics found for Chapter ID: {}", chapterId);
            throw new IllegalArgumentException("No topics found for the specified chapter");
        }

        logger.info("Found {} topics for Chapter ID: {}", topics.size(), chapterId);

        // Step 2: Generate questions for each topic
        Map<Integer, List<QuestionEntity>> topicQuestionsMap = new LinkedHashMap<>();
        int totalQuestionsGenerated = 0;

        for (Topic topic : topics) {
            try {
                logger.info("Generating {} {} questions for Topic: {} (ID: {})",
                        noOfQuestionsPerTopic, questionType, topic.getTopicName(), topic.getTopicId());

                // Build metadata for this topic
                QuestionMetadata metadata = QuestionMetadata.builder()
                        .boardId(boardId)
                        .classId(classId)
                        .subjectId(subjectId)
                        .chapterId(chapterId)
                        .topicId(topic.getTopicId())
                        .boardName(topic.getBoardName())
                        .className(topic.getClassName())
                        .subject(topic.getSubjectName())
                        .medium(medium)
                        .chapter(topic.getChapterName())
                        .topic(topic.getTopicName())
                        .skillLevel(skillLevel) // Use from input
                        .questionType(questionType) // Use from input
                        .difficulty(difficulty) // Use from input
                        .numberOfQuestions(noOfQuestionsPerTopic)
                        .build();

                // Generate questions for this topic
                List<QuestionEntity> generatedQuestions = generateAndSaveQuestions(metadata, user);

                if (generatedQuestions != null && !generatedQuestions.isEmpty()) {
                    topicQuestionsMap.put(topic.getTopicId(), generatedQuestions);
                    totalQuestionsGenerated += generatedQuestions.size();
                    logger.info("Successfully generated {} questions for Topic: {} (ID: {})",
                            generatedQuestions.size(), topic.getTopicName(), topic.getTopicId());
                } else {
                    logger.warn("No questions generated for Topic: {} (ID: {})",
                            topic.getTopicName(), topic.getTopicId());
                }

            } catch (Exception e) {
                logger.error("Error generating questions for Topic: {} (ID: {}). Skipping this topic.",
                        topic.getTopicName(), topic.getTopicId(), e);
                // Continue with next topic instead of failing completely
            }
        }

        logger.info("Completed question generation. Total topics processed: {}, Total questions generated: {}",
                topics.size(), totalQuestionsGenerated);

        if (topicQuestionsMap.isEmpty()) {
            throw new IllegalStateException("Failed to generate questions for any topic in the chapter");
        }

        return topicQuestionsMap;
    }

    /**
     * Fetch existing questions from database for a chapter
     * Used for exam paper generation to reuse already generated questions
     */
    @Override
    @Transactional(readOnly = true)
    public List<QuestionEntity> fetchQuestionsForChapter(
            Integer boardId,
            Integer classId,
            Integer subjectId,
            Integer chapterId,
            String questionType,
            String medium,
            int numberOfQuestions) {

        logger.info("📖 Fetching {} existing {} questions for Chapter ID: {}, Board: {}, Class: {}, Subject: {}, Medium: {}",
                numberOfQuestions, questionType, chapterId, boardId, classId, subjectId, medium);

        // Use the findRandomQuestions method to get random questions from the database, filtered by chapter
        List<QuestionEntity> questions = questionRepository.findRandomQuestions(
                boardId,
                classId,
                subjectId,
                chapterId, // Pass chapterId for efficient DB filtering
                null, // topicId - null to get from all topics in the chapter
                questionType,
                null, // skillLevel - null to get any skill level
                null, // difficultyLevel - null to get any difficulty
                medium,
                numberOfQuestions
        );

        logger.info("✅ Fetched {} existing questions for Chapter ID: {}", questions.size(), chapterId);

        if (questions.size() < numberOfQuestions) {
            logger.warn("⚠️ Only {} questions found in database, but {} were requested for Chapter ID: {}",
                    questions.size(), numberOfQuestions, chapterId);
        }

        return questions;
    }

    @Override
    public List<QuestionEntity> findRandomQuestionsForParagraph(Integer boardId, Integer classId, Integer subjectId,
                                                               String skillLevel, String difficultyLevel, String medium,Set<Long> paragraphQuestionIds , int limit){
        logger.info("📖 Fetching {} random questions for Paragraphs for Board: {}, Class: {}, Subject: {}, SkillLevel: {}, Difficulty: {}, Medium: {}",
                limit, boardId, classId, subjectId, skillLevel, difficultyLevel, medium);
        List<QuestionEntity> questions = questionRepository.findRandomQuestionsForParagraph(
                boardId,
                classId,
                subjectId,
                skillLevel,
                difficultyLevel,
                medium,
                paragraphQuestionIds,
                limit
        );
        logger.info("✅ Fetched {} random questions for Paragraphs", questions.size());
        return questions;
    }

    /**
     * Fetch questions with specific SUKA and Difficulty distributions
     * Used for exam paper generation with metaData requirements
     */
    @Override
    @Transactional(readOnly = true)
    public List<QuestionEntity> fetchQuestionsWithDistributions(
            Integer boardId,
            Integer classId,
            Integer subjectId,
            Integer chapterId,
            String questionType,
            String medium,
            int numberOfQuestions,
            Map<String, Integer> sukaDistribution,
            Map<String, Integer> difficultyDistribution,
            QuestionPaperMetaData metaData) {

        logger.info("📖 Fetching questions with distributions for Chapter ID: {}", chapterId);
        logger.info("   SUKA Distribution: {}", sukaDistribution);
        logger.info("   Difficulty Distribution: {}", difficultyDistribution);

        List<QuestionEntity> selectedQuestions = new ArrayList<>();
        Set<Long> selectedQuestionIds = new HashSet<>();

        // Calculate total SUKA and Difficulty counts to verify distributions
        int totalSukaCount = sukaDistribution.values().stream().mapToInt(Integer::intValue).sum();
        int totalDifficultyCount = difficultyDistribution.values().stream().mapToInt(Integer::intValue).sum();

        logger.info("   Total SUKA count: {}, Total Difficulty count: {}, Target questions: {}",
                totalSukaCount, totalDifficultyCount, numberOfQuestions);

        // Create a matrix to track how many questions we need for each SUKA-Difficulty combination
        Map<String, Map<String, Integer>> distributionMatrix = new LinkedHashMap<>();
        List<Map.Entry<String, String>> allCombinations = new ArrayList<>();

        // Collect all valid combinations first
        for (Map.Entry<String, Integer> sukaEntry : sukaDistribution.entrySet()) {
            String skillLevel = sukaEntry.getKey();
            int sukaCount = sukaEntry.getValue();
            if (sukaCount <= 0) continue;

            for (Map.Entry<String, Integer> diffEntry : difficultyDistribution.entrySet()) {
                String difficultyLevel = diffEntry.getKey();
                int diffCount = diffEntry.getValue();
                if (diffCount <= 0) continue;

                allCombinations.add(Map.entry(skillLevel, difficultyLevel));
            }
        }

        // If no valid combinations, cannot proceed
        if (allCombinations.isEmpty()) {
            String msg = String.format(
                "Cannot build question distribution for Chapter ID %d — " +
                "all SUKA and/or Difficulty counts are 0 after percentage calculation.%n" +
                "  SUKA distribution received:       %s%n" +
                "  Difficulty distribution received:  %s%n" +
                "  Total questions requested:         %d%n" +
                "Possible causes:%n" +
                "  1. The exam paper has very few questions (e.g. 1-2 per chapter) and rounding " +
                "drove all percentage-based counts to zero.%n" +
                "  2. All percentages in skillDistribution or difficultyDistribution are null/0 in the metadata.%n" +
                "  3. The metaData was not sent in the request body.%n" +
                "Fix: Check the metaData.skillDistribution and metaData.difficultyDistribution " +
                "fields in the request and ensure at least one value is > 0 per category.",
                chapterId,
                sukaDistribution,
                difficultyDistribution,
                numberOfQuestions);
            logger.error("   ❌ No valid SUKA-Difficulty combinations found. {}", msg);
            throw new ValidationException(msg);
        }

        // First pass: Calculate proportional distribution using floor
        int totalAllocated = 0;
        Map<Map.Entry<String, String>, Double> exactProportions = new LinkedHashMap<>();

        for (Map.Entry<String, Integer> sukaEntry : sukaDistribution.entrySet()) {
            String skillLevel = sukaEntry.getKey();
            int sukaCount = sukaEntry.getValue();
            if (sukaCount <= 0) continue;

            Map<String, Integer> difficultyMap = new LinkedHashMap<>();

            for (Map.Entry<String, Integer> diffEntry : difficultyDistribution.entrySet()) {
                String difficultyLevel = diffEntry.getKey();
                int diffCount = diffEntry.getValue();
                if (diffCount <= 0) continue;

                // Calculate exact proportion (keep decimals for later adjustment)
                double proportion = ((double) sukaCount / totalSukaCount) *
                                   ((double) diffCount / totalDifficultyCount);
                double exactQuestions = proportion * numberOfQuestions;

                Map.Entry<String, String> combo = Map.entry(skillLevel, difficultyLevel);
                exactProportions.put(combo, exactQuestions);

                // For initial allocation: use floor to avoid over-allocation
                int questionsForCombo = (int) Math.floor(exactQuestions);

                difficultyMap.put(difficultyLevel, questionsForCombo);
                totalAllocated += questionsForCombo;
            }

            if (!difficultyMap.isEmpty()) {
                distributionMatrix.put(skillLevel, difficultyMap);
            }
        }

        // Adjust if floor caused under-allocation (distribute remaining by highest fractional parts)
        if (totalAllocated < numberOfQuestions) {
            int remaining = numberOfQuestions - totalAllocated;
            logger.info("   Allocating remaining {} questions to best-fit combinations", remaining);

            // Sort combinations by their fractional parts (descending)
            // This gives priority to combinations that were closest to rounding up
            List<Map.Entry<Map.Entry<String, String>, Double>> sortedByFraction = exactProportions.entrySet()
                .stream()
                .sorted((a, b) -> {
                    double fracA = a.getValue() - Math.floor(a.getValue());
                    double fracB = b.getValue() - Math.floor(b.getValue());
                    return Double.compare(fracB, fracA); // Descending
                })
                .toList();

            // Distribute remaining questions to combinations with highest fractional parts
            for (int i = 0; i < remaining && i < sortedByFraction.size(); i++) {
                Map.Entry<String, String> combo = sortedByFraction.get(i).getKey();
                String skill = combo.getKey();
                String diff = combo.getValue();

                // Ensure the skill map exists
                distributionMatrix.putIfAbsent(skill, new LinkedHashMap<>());

                // Add 1 question to this combination
                int current = distributionMatrix.get(skill).getOrDefault(diff, 0);
                distributionMatrix.get(skill).put(diff, current + 1);
            }
        } else if (totalAllocated > numberOfQuestions) {
            // This should rarely happen with floor, but handle it just in case
            int excess = totalAllocated - numberOfQuestions;
            logger.info("   Removing {} excess questions from combinations", excess);

            // Sort by lowest fractional parts (those closest to rounding down)
            List<Map.Entry<Map.Entry<String, String>, Double>> sortedByFraction = exactProportions.entrySet()
                .stream()
                .filter(e -> {
                    String skill = e.getKey().getKey();
                    String diff = e.getKey().getValue();
                    return distributionMatrix.containsKey(skill) &&
                           distributionMatrix.get(skill).containsKey(diff) &&
                           distributionMatrix.get(skill).get(diff) > 0;
                })
                .sorted((a, b) -> {
                    double fracA = a.getValue() - Math.floor(a.getValue());
                    double fracB = b.getValue() - Math.floor(b.getValue());
                    return Double.compare(fracA, fracB); // Ascending
                })
                .toList();

            for (int i = 0; i < excess && i < sortedByFraction.size(); i++) {
                Map.Entry<String, String> combo = sortedByFraction.get(i).getKey();
                String skill = combo.getKey();
                String diff = combo.getValue();

                if (distributionMatrix.containsKey(skill) &&
                    distributionMatrix.get(skill).containsKey(diff)) {
                    int current = distributionMatrix.get(skill).get(diff);
                    if (current > 0) {
                        distributionMatrix.get(skill).put(diff, current - 1);
                    }
                }
            }
        }

        // Remove zero entries
        distributionMatrix.entrySet().removeIf(skillEntry -> {
            skillEntry.getValue().entrySet().removeIf(diffEntry -> diffEntry.getValue() <= 0);
            return skillEntry.getValue().isEmpty();
        });

        // Final verification
        int finalTotal = distributionMatrix.values().stream()
            .flatMap(m -> m.values().stream())
            .mapToInt(Integer::intValue)
            .sum();

        if (finalTotal != numberOfQuestions) {
            logger.error("   ❌ Distribution matrix total ({}) does not match target ({})",
                finalTotal, numberOfQuestions);
            throw new ValidationException("Failed to properly distribute questions across SUKA-Difficulty matrix");
        }

        logger.info("   📊 Distribution Matrix:");
        distributionMatrix.forEach((skill, diffMap) ->
                diffMap.forEach((diff, count) ->
                        logger.info("      {} × {} = {} questions", skill, diff, count)));

        // Fetch questions for each SUKA-Difficulty combination
        for (Map.Entry<String, Map<String, Integer>> skillEntry : distributionMatrix.entrySet()) {
            String skillLevel = skillEntry.getKey();

            for (Map.Entry<String, Integer> diffEntry : skillEntry.getValue().entrySet()) {
                String difficultyLevel = diffEntry.getKey();
                int questionsNeeded = diffEntry.getValue();

                if (questionsNeeded <= 0) {
                    continue;
                }

                logger.info("   Fetching {} questions: Skill={}, Difficulty={}",
                        questionsNeeded, skillLevel, difficultyLevel);

                // Fetch questions with specific skill and difficulty level, filtered by chapter at DB level
                List<QuestionEntity> chapterQuestions = questionRepository.findRandomQuestions(
                        boardId,
                        classId,
                        subjectId,
                        chapterId, // Now passing chapterId for efficient DB filtering
                        null, // topicId - null to get from all topics in the chapter
                        questionType,
                        skillLevel,
                        difficultyLevel,
                        medium,
                        questionsNeeded * 2 // Fetch more to allow deduplication
                );

                // Add unique questions up to the needed count
                int added = 0;
                for (QuestionEntity question : chapterQuestions) {
                    if (added >= questionsNeeded) {
                        break;
                    }
                    if (selectedQuestionIds.add(question.getId())) {
                        selectedQuestions.add(question);
                        added++;
                    }
                }

                logger.info("   ✅ Added {} questions for Skill={}, Difficulty={}, Chapter ID: {}",
                        added, skillLevel, difficultyLevel, chapterId);
            }
        }

        // If we don't have enough questions, fill with any available questions
        if (selectedQuestions.size() < numberOfQuestions && false) {
            int remaining = numberOfQuestions - selectedQuestions.size();
            logger.warn("⚠️ Only {} questions found with distributions, fetching {} more without restrictions",
                    selectedQuestions.size(), remaining);

            // Fetch additional questions from the chapter without SUKA/Difficulty restrictions
            List<QuestionEntity> additionalQuestions = questionRepository.findRandomQuestions(
                    boardId,
                    classId,
                    subjectId,
                    chapterId, // Filter by chapter at DB level
                    null,
                    questionType,
                    null,
                    null,
                    medium,
                    remaining * 2 // Fetch extra to allow deduplication
            );

            // Filter out already selected questions and limit
            int added = 0;
            for (QuestionEntity question : additionalQuestions) {
                if (added >= remaining) {
                    break;
                }
                if (selectedQuestionIds.add(question.getId())) {
                    selectedQuestions.add(question);
                    added++;
                }
            }

            logger.info("   ✅ Added {} additional questions without distribution restrictions", added);
        }

        logger.info("✅ Total questions fetched: {} (Target: {})",
                selectedQuestions.size(), numberOfQuestions);

        if (selectedQuestions.size() < numberOfQuestions) {
            // Build a per-combination breakdown: for each Skill × Difficulty in the matrix,
            // show exactly how many are required and how many were found in the DB.
            StringBuilder missingSummary = new StringBuilder();
            missingSummary.append("The following Skill × Difficulty combinations do not have enough questions in the database:\n");

            for (Map.Entry<String, Map<String, Integer>> skillEntry : distributionMatrix.entrySet()) {
                String skillLevel = skillEntry.getKey();
                String skillDisplay = skillLevel.substring(0, 1).toUpperCase() + skillLevel.substring(1).toLowerCase();

                for (Map.Entry<String, Integer> diffEntry : skillEntry.getValue().entrySet()) {
                    String diffLevel = diffEntry.getKey();
                    int required = diffEntry.getValue();
                    if (required <= 0) continue;

                    long found = selectedQuestions.stream()
                            .filter(q -> skillLevel.equalsIgnoreCase(q.getSkillLevel())
                                    && diffLevel.equalsIgnoreCase(q.getDifficultyLevel()))
                            .count();

                    long missing = required - found;
                    if (missing <= 0) continue; // skip combinations that are fully satisfied

                    String diffDisplay = diffLevel.substring(0, 1).toUpperCase() + diffLevel.substring(1).toLowerCase();
                    missingSummary.append(String.format(
                            "  • Skill Level: %-15s | Difficulty: %-8s | Required: %d | Found: %d | Missing: %d%n",
                            skillDisplay, diffDisplay, required, found, missing));
                }
            }

            // Fetch subject, chapter, and other context for the message
            String chapterName = "Unknown";
            Chapter chapter = chapterRepository.findById(chapterId != null ? chapterId.intValue() : 0);
            if (chapter != null && chapter.getChapterName() != null) {
                chapterName = chapter.getChapterName();
            }
            String subjectName = "Unknown";
            Subject subject = subjectRepository.findById(subjectId != null ? subjectId.intValue() : 0);
            if (subject != null && subject.getSubjectName() != null) {
                subjectName = subject.getSubjectName();
            }
            String message = String.format(
                "Not enough questions found in the database to build the exam paper.%n" +
                "Subject: '%s' | Chapter: '%s' (ID: %s) | Question Type: %s | Medium: %s%n" +
                "Please generate or add more questions for the missing combinations below:%n%s",
                subjectName, chapterName, chapterId, questionType, medium, missingSummary
            );
            logger.error(message);
            throw new ValidationException(message);
        }

        // Trim to exact count if we somehow got more
        if (selectedQuestions.size() > numberOfQuestions) {
            logger.warn("   ⚠️ Got {} questions, trimming to {}", selectedQuestions.size(), numberOfQuestions);
            return selectedQuestions.subList(0, numberOfQuestions);
        }

        return selectedQuestions;
    }

    /**
     * Inner class to wrap correct answer option number
     */
    private static class CorrectAnswerWrapper {
        public int correctOption;

        public CorrectAnswerWrapper(int correctOption) {
            this.correctOption = correctOption;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<QuestionEntity> findByQuestionPaperId(Long questionPaperId) {
        logger.info("Fetching questions for Question Paper ID: {}", questionPaperId);
        List<QuestionEntity> questions = questionRepository.findByQuestionPaperId(questionPaperId);
        logger.info("Found {} questions for Question Paper ID: {}", questions.size(), questionPaperId);
        return questions;
    }

    @Override
    public List<QuestionEntity> findByQuestionPaperIdPaginated(Long questionPaperId, int page, int size) {
        logger.info("Fetching questions for Question Paper ID: {}", questionPaperId);
        List<QuestionEntity> questions = questionRepository.findByQuestionPaperIdPaginated(questionPaperId,page,size);
        logger.info("Found {} questions for Question Paper ID: {}", questions.size(), questionPaperId);
        return questions;
    }

    @Override
    public List<QuestionEntity> fetchQuestionsForSection(Long questionPaperId, Integer subjectId, Long partId, Integer sectionId, int numberOfQuestions) {
        List<QuestionEntity> byQuestions = questionRepository.findByQuestions(questionPaperId, subjectId, partId, sectionId, numberOfQuestions);
        String cloudFrontDomain = getConfigValue(ConfigService.CLOUD_FRONT_URL, "CloudFront URL Not Found");
        byQuestions.forEach(q->{
            QuestionUtils.setPrefixImageUlr(q, cloudFrontDomain);
        });
        return byQuestions;
    }

//    @Override
//    public List<QuestionEntity> fetchQuestionsForSection(Long questionPaperId, Long boardId, Integer classId, Integer subjectId, String questionType, String medium, int numberOfQuestions) {
//
//        //TODO Narendra - Optimize this method to fetch filtered questions directly from the database instead of fetching all and filtering in memory
//        // Fetch all questions associated with the given questionPaperId
//        List<QuestionEntity> allQuestions = questionRepository.findByQuestionPaperId(questionPaperId);
//        // Filter by board, class, subject, questionType, and medium (if provided)
//        return allQuestions.stream()
//                .filter(q -> (boardId == null || boardId.intValue() == q.getBoardId())
//                        && (classId == null || classId.equals(q.getClassId()))
//                        && (subjectId == null || subjectId.equals(q.getSubjectId()))
//                        && (questionType == null || questionType.equalsIgnoreCase(q.getQuestionType()))
//                        && (medium == null || medium.equalsIgnoreCase(q.getMedium())))
//                .limit(numberOfQuestions)
//                .collect(java.util.stream.Collectors.toList());
//    }

    /**
     * Fetch a set of paragraph-based MCQ questions, ignoring SUKA and Difficulty
     */
    @Transactional(readOnly = true)
    public List<QuestionEntity> fetchParagraphBasedMcqQuestions(int numberOfQuestions) {
        String questionType = "paragraph-based-mcq";
        // Fetch random questions of the specified type, ignoring SUKA and Difficulty
        List<QuestionEntity> questions = questionRepository.findRandomQuestions(
            null, // boardId
            null, // classId
            null, // subjectId
            null, // chapterId
            null, // topicId
            questionType,
            null, // skillLevel
            null, // difficultyLevel
            null, // medium
            numberOfQuestions
        );
        return questions;
    }

    @Override
    @Transactional(readOnly = true)
    public List<QuestionEntity> findAllByTenantId(Long tenantId) {
        return questionRepository.findAllByTenantId(tenantId);
    }
        private String getConfigValue(String configName, String errorMessage) {
            Optional<Config> configOpt = configService.findByName(configName);
            return configOpt.orElseThrow(() -> new IllegalArgumentException(errorMessage + ": " + configName))
                    .getValue();
        }
}
