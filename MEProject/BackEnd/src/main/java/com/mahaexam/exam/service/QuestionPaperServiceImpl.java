package com.mahaexam.exam.service;

import java.util.*;

import com.fasterxml.jackson.core.type.TypeReference;
import com.mahaexam.common.bean.*;
import com.mahaexam.common.exception.ValidationException;
import com.mahaexam.common.model.Chapter;
import com.mahaexam.common.model.Config;
import com.mahaexam.common.model.Subject;
import com.mahaexam.common.service.ChapterService;
import com.mahaexam.common.service.ConfigService;
import com.mahaexam.common.service.SubjectService;
import com.mahaexam.papertemplate.model.Syllabus;
import com.mahaexam.papertemplate.model.SyllabusChapter;
import com.mahaexam.papertemplate.service.PaperTemplateService;
import com.mahaexam.question.model.QuestionEntity;
import com.mahaexam.question.service.QuestionService;
import com.mahaexam.question.service.QuestionServiceImpl;
import com.mahaexam.question.service.QuestionUtils;
import com.mahaexam.syllabus.service.SyllabusService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;

import com.mahaexam.papertemplate.model.QuestionPaper;
import com.mahaexam.papertemplate.repository.QuestionPaperTemplateRepository;
import com.mahaexam.exam.repository.QuestionPaperRepository;
import com.mahaexam.exam.repository.QuestionPaperQuestionRepository;
import com.mahaexam.exam.model.QuestionPaperQuestion;
import com.mahaexam.sqs.service.SimpleSqsService;
import org.springframework.beans.factory.annotation.Autowired;
import lombok.extern.slf4j.Slf4j;
import jakarta.annotation.PostConstruct;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.stream.Collectors;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mahaexam.openai.model.QuestionOptions;

@Service
@Transactional
public class QuestionPaperServiceImpl implements QuestionPaperService {

    private static final Logger log = LoggerFactory.getLogger(QuestionPaperServiceImpl.class);
    @Value("${aws.accountId}")
    private String accountId;

    @Value("${spring.cloud.aws.region.static}")
    private String region;

	private final QuestionPaperRepository questionPaperRepository;
    private final PaperTemplateService paperTemplateService;
    private final QuestionPaperTemplateRepository questionPaperTemplateRepository;
    private final QuestionPaperQuestionRepository questionPaperQuestionRepository;
    private final SyllabusService syllabusService;
    private final QuestionService questionService;
    private final ChapterService chapterService;
    private final ExamTokenService examTokenService;
    private final SubjectService subjectService;
    private final ConfigService configService;

    @Autowired(required = false)
    private SimpleSqsService simpleSqsService;

    // Debug logging for SQS configuration
    @PostConstruct
    public void debugSqsConfiguration() {
        log.info("=== SQS Configuration Debug ===");
        log.info("SimpleSqsService bean: {}", simpleSqsService != null ? "AVAILABLE" : "NULL");
        log.info("SQS Available: {}", simpleSqsService != null ? simpleSqsService.isAvailable() : false);
        log.info("AWS Account ID: {}", accountId);
        log.info("AWS Region: {}", region);
        log.info("===============================");
    }

    public QuestionPaperServiceImpl(
            QuestionPaperRepository questionPaperRepository,
            PaperTemplateService paperTemplateService,
            QuestionPaperTemplateRepository questionPaperTemplateRepository,
            QuestionPaperQuestionRepository questionPaperQuestionRepository,
            SyllabusService syllabusService,
            QuestionService questionService,
            ChapterService chapterService,
            ExamTokenService examTokenService,
            SubjectService subjectService,
            ConfigService configService){
        this.questionPaperRepository = questionPaperRepository;
        this.paperTemplateService = paperTemplateService;
        this.questionPaperTemplateRepository = questionPaperTemplateRepository;
        this.questionPaperQuestionRepository = questionPaperQuestionRepository;
        this.syllabusService = syllabusService;
        this.questionService = questionService;
        this.chapterService = chapterService;
        this.examTokenService = examTokenService;
        this.subjectService = subjectService;
        this.configService = configService;
    }
    @Override
    @Transactional
    public QuestionPaperResponseDTO createQuestionPaper(QuestionPaperRequestDTO request, UserBean user ) {



        if (user != null && user.getTenantId() != null) {
            if (questionPaperRepository.existsByNameAndTenantId(request.getQuestionPaperName(), user.getTenantId())) {
                throw new ValidationException("A question paper with the name '" + request.getQuestionPaperName() + "' already exists. Please use a different name.");
            }
        } else {
            if (questionPaperRepository.existsByName(request.getQuestionPaperName())) {
                throw new ValidationException("A question paper with the name '" + request.getQuestionPaperName() + "' already exists. Please use a different name.");
            }
        }

        QuestionPaper questionPaper = QuestionPaperMapper.toEntity(request, user);
        QuestionPaper saved = questionPaperRepository.save(questionPaper);

        // Save question-paper template mappings with their sequences
        if (request.getPaperTemplates() != null && !request.getPaperTemplates().isEmpty()) {
            Long tenantId = (user != null) ? user.getTenantId() : null;
            for (QuestionPaperTemplateRequestDTO paperTemplate : request.getPaperTemplates()) {
                questionPaperTemplateRepository.save(
                    saved.getId(),
                    paperTemplate.getPaperTemplateId(),
                    paperTemplate.getSequence(),
                    tenantId
                );
            }
        }
        if(!questionPaper.getStatus().equalsIgnoreCase("ACTIVE")){
            log.info("Question Paper is not active. Skipping paper generation.");
            return QuestionPaperMapper.toResponse(saved);
        }
        generateQuestionPaper(saved.getId());
        // For testing: Skip SQS message sending and return response immediately
        if(true){
            return QuestionPaperMapper.toResponse(saved);
        }
        // Send SQS message using SimpleSqsService
        if (simpleSqsService != null && simpleSqsService.isAvailable()) {
            try {
                log.info("Sending SQS message for question paper created: {}",   saved.getId());
                simpleSqsService.sendExamCreatedMessage(
                        saved.getId(),
                        user != null ? user.getTenantId() : null,
                        user != null ? user.getUserId() : null
                ).whenComplete((messageId, ex) -> {
                    if (ex != null) {
                        log.error("Failed to send SQS message: {}", ex.getMessage());
                    } else {
                        log.info("SQS message sent successfully with ID: {}", messageId);
                    }
                });

            } catch (Exception e) {
                log.error("Error sending SQS message: {}", e.getMessage(), e);
            }
        } else {
            log.debug("SQS Service not available - skipping message");
        }

        return QuestionPaperMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<QuestionPaperResponseDTO> getAllQuestionPapers() {

        return questionPaperRepository.findAll()
                .stream()
                .map(QuestionPaperMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<QuestionPaperResponseDTO> getAllQuestionPapersByTenant(Long tenantId) {

        return questionPaperRepository.findAllByTenantId(tenantId)
                .stream()
                .map(QuestionPaperMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<QuestionPaperResponseDTO> getAllQuestionPapersByTenantAndFilter(Long tenantId, Long boardId, Integer classId) {

        return questionPaperRepository.findAllByTenantIdAndFilter(tenantId, boardId, classId)
                .stream()
                .map(QuestionPaperMapper::toResponse)
                .toList();
    }

    @Override
    public void updateQuestionPaperStatus(Long questionPaperId, Boolean active) {
        questionPaperRepository.updateStatus(questionPaperId, active);
    }

    @Override
    @Transactional(readOnly = true)
    public List<QuestionPaperResponseDTO> getExamsByStudentPackageAndMedium(Long studentUserId, Long tenantId) {
        return questionPaperRepository.findExamsByStudentPackageAndMedium(studentUserId, tenantId)
                .stream()
                .map(QuestionPaperMapper::toResponse)
                .toList();
    }

    public void generateQuestionPaper(Long questionPaperId) {
        log.info("🎯 Starting question paper generation for question paper ID: {}", questionPaperId);

        try {
            // Fetch question paper details
            QuestionPaperResponseDTO questionPaper = getQuestionPaperById(questionPaperId,true,0,0);//To fetch all questions
            if (!questionPaper.getStatus().equals("ACTIVE")) {
                log.info("Question Paper is not active. Skipping paper generation.");
                return;
            }

            // Fetch paper templates with full hierarchy
            List<Long> templateIds = questionPaper.getPaperTemplates().stream()
                    .map(QuestionPaperTemplateResponseDTO::getPaperTemplateId)
                    .toList();
            List<PaperTemplateResponse> paperTemplates = paperTemplateService.getFullHierarchyByIds(templateIds);

            log.info("📋 Processing {} paper template(s)", paperTemplates.size());

            int totalExpectedQuestions = 0;
            int totalGeneratedQuestions = 0;

            // Process each paper template
            for (PaperTemplateResponse pt : paperTemplates) {
                log.info("📄 Paper Template: {} (ID: {})", pt.getName(), pt.getId());
                if(Objects.isNull(pt.getParts()) || pt.getParts().isEmpty()){
                    throw new IllegalStateException(String.format(
                            "No parts found in paper template ID: %s for classId: %s, medium: %s, academicYear: %s",
                            pt.getId(), pt.getClassId(), pt.getMedium(), questionPaper.getAcademicYear()));
                }

                int templateExpectedQuestions = 0;
                int templateGeneratedQuestions = 0;

                // Process each part (subject)
                for (PartResponse part : pt.getParts()) {
                    log.info("  📚 Part: {} - Subject ID: {}", part.getName(), part.getSubjectId());

                    // Fetch syllabus for this part
                    Syllabus syllabus = syllabusService.getSyllabus(
                            pt.getClassId().longValue(),
                            part.getSubjectId(),
                            pt.getMedium(),
                            Integer.parseInt(questionPaper.getAcademicYear()),
                            pt.getTenantId()
                    );

                    log.info("  📖 Found syllabus: {} with {} chapters",
                            syllabus.getName(), syllabus.getChapters().size());
                    if(syllabus.getChapters().isEmpty()){
                        throw new IllegalStateException(String.format(
                                "No chapters found in syllabus for classId: %s, subjectId: %s, medium: %s, academicYear: %s",
                                pt.getClassId(), part.getSubjectId(), pt.getMedium(), questionPaper.getAcademicYear()));
                    }

                    int partExpectedQuestions = 0;
                    int beforePartCount = questionPaperQuestionRepository.getMaxSequenceNumber(questionPaperId);

                    // Process each section in the part
                    for (SectionResponse section : part.getSections()) {
                        log.info("    📝 Section: {} - Question Type: {}, Total Questions: {}",
                                section.getName(), section.getQuestionType(), section.getNumberOfQuestions());

                        partExpectedQuestions += section.getNumberOfQuestions();

                        // Generate questions for this section based on syllabus chapters and metaData
                        generateQuestionsForSection(
                                questionPaper.getId(),
                                pt,
                                part,
                                section,
                                syllabus,
                                questionPaper,
                                questionPaper.getMetaData()
                        );
                    }

                    int afterPartCount = questionPaperQuestionRepository.getMaxSequenceNumber(questionPaperId);
                    int partGeneratedQuestions = afterPartCount - beforePartCount;

                    log.info("  ✅ Part '{}' completed: Expected {} questions, Generated {} questions",
                            part.getName(), partExpectedQuestions, partGeneratedQuestions);

                    templateExpectedQuestions += partExpectedQuestions;
                    templateGeneratedQuestions += partGeneratedQuestions;
                }

                log.info("📄 Template '{}' completed: Expected {} questions, Generated {} questions",
                        pt.getName(), templateExpectedQuestions, templateGeneratedQuestions);

                totalExpectedQuestions += templateExpectedQuestions;
                totalGeneratedQuestions += templateGeneratedQuestions;
            }

            log.info("✅ Question paper generation completed for question paper ID: {}", questionPaperId);
            log.info("📊 FINAL SUMMARY: Total Expected: {}, Total Generated: {}, Difference: {}",
                    totalExpectedQuestions, totalGeneratedQuestions,
                    totalExpectedQuestions - totalGeneratedQuestions);

            if (totalGeneratedQuestions < totalExpectedQuestions) {
                log.error("❌ CRITICAL: Question paper has a shortage of {} questions!",
                        totalExpectedQuestions - totalGeneratedQuestions);
                throw new ValidationException(String.format(
                        "Question paper generation failed: Expected %d questions but only generated %d. Shortage: %d questions.",
                        totalExpectedQuestions, totalGeneratedQuestions,
                        totalExpectedQuestions - totalGeneratedQuestions));
            }
        } catch (ValidationException e) {
            throw e;
        } catch (Exception e) {
            log.error("❌ Failed to generate question paper for question paper ID: {}", questionPaperId, e);
            throw new RuntimeException("Failed to generate question paper: " + e.getMessage(), e);
        }
    }

    /**
     * Generate questions for a section based on syllabus chapters with coverage percentages
     * and metaData (SUKA and Difficulty distributions)
     */
    private void generateQuestionsForSection(
            Long questionPaperId,
            PaperTemplateResponse template,
            PartResponse part,
            SectionResponse section,
            Syllabus syllabus,
            QuestionPaperResponseDTO questionPaper,
            QuestionPaperMetaData metaData) throws Exception {

        log.info("      🔧 Generating questions for section: {}", section.getName());

        int totalQuestionsNeeded = section.getNumberOfQuestions();
        String questionType = section.getQuestionType();

        log.info("      📊 Total questions needed: {}", totalQuestionsNeeded);
        log.info("      📊 Question type: {}", questionType);

        // Log metaData distributions if available
        if (metaData != null) {
            log.info("      📊 MetaData - SUKA Distribution: Skill={}, Understanding={}, Knowledge={}, Application={}",
                    metaData.getSkillDistribution() != null ? metaData.getSkillDistribution().getSkill() : "N/A",
                    metaData.getSkillDistribution() != null ? metaData.getSkillDistribution().getUnderstanding() : "N/A",
                    metaData.getSkillDistribution() != null ? metaData.getSkillDistribution().getKnowledge() : "N/A",
                    metaData.getSkillDistribution() != null ? metaData.getSkillDistribution().getApplication() : "N/A");
            log.info("      📊 MetaData - Difficulty Distribution: Hard={}, Medium={}, Easy={}",
                    metaData.getDifficultyDistribution() != null ? metaData.getDifficultyDistribution().getHard() : "N/A",
                    metaData.getDifficultyDistribution() != null ? metaData.getDifficultyDistribution().getMedium() : "N/A",
                    metaData.getDifficultyDistribution() != null ? metaData.getDifficultyDistribution().getEasy() : "N/A");
        }else{
            throw new IllegalStateException("MetaData is required for question generation but was not provided for section: " + section.getName());
        }

        // Calculate number of questions per chapter based on coverage percentage
        Map<SyllabusChapter, Integer> chapterQuestionDistribution =
                calculateQuestionDistribution(syllabus.getChapters(), totalQuestionsNeeded);

        log.info("      📊 Chapter Distribution: {}",
                chapterQuestionDistribution.entrySet().stream()
                    .map(e -> "Chapter " + e.getKey().getChapterId() + "=" + e.getValue())
                    .collect(Collectors.joining(", ")));

        int totalQuestionsGenerated = 0;
        int globalSequenceNumber = questionPaperQuestionRepository.getMaxSequenceNumber(questionPaperId);

        // Collect all questions for batch insertion
        List<QuestionPaperQuestion> questionsToSave = new java.util.ArrayList<>();

        // Track question IDs to prevent duplicates within this section
        Set<Long> usedQuestionIds = new java.util.HashSet<>();
        boolean isParagraphBasedQuestionAdded = false; // Flag to track if we've already added paragraph-based questions for this section
        // Generate questions for each chapter
        Set<Long> paragraphQuestionIds = new java.util.HashSet<>(); // To track question IDs of paragraph-based questions added for this section

        for (Map.Entry<SyllabusChapter, Integer> entry : chapterQuestionDistribution.entrySet()) {
            SyllabusChapter chapter = entry.getKey();
            int questionsForChapter = entry.getValue();

            if (questionsForChapter == 0) {
                log.info("        ⏭️ Skipping chapter ID: {} (0 questions allocated)", chapter.getChapterId());
                continue;
            }

            log.info("        📖 Chapter ID: {} - Generating {} questions (Coverage: {}%)",
                    chapter.getChapterId(),
                    questionsForChapter,
                    chapter.getCoveragePercentage());

            // Fetch extra questions (50% buffer) to account for potential duplicates across sections
            // Scale up SUKA and Difficulty distributions proportionally
            int questionsToFetch = (int) Math.ceil(questionsForChapter * 1.5);
            log.info("        🔍 Fetching {} questions (with 50% buffer) to ensure {} unique questions",
                    questionsToFetch, questionsForChapter);

            // Calculate proportional SUKA distribution for buffered fetch count
            Map<String, Integer> chapterSukaDistribution = calculateSukaDistribution(questionsToFetch, metaData);

            // Calculate proportional difficulty distribution for buffered fetch count
            Map<String, Integer> chapterDifficultyDistribution = calculateDifficultyDistribution(questionsToFetch, metaData);

            log.info("        📊 Buffered SUKA Distribution (for {} questions): {}", questionsToFetch, chapterSukaDistribution);
            log.info("        📊 Buffered Difficulty Distribution (for {} questions): {}", questionsToFetch, chapterDifficultyDistribution);

            try {
                List<QuestionEntity> chapterQuestions=null;
                if (questionType.equals("paragraph-based-mcq")) {
                    if(isParagraphBasedQuestionAdded){
                        continue;
                    }
                    isParagraphBasedQuestionAdded =true;
                    // For paragraph-based MCQs, we may want to ignore SUKA and Difficulty distributions to ensure we get enough questions related to the passage
                    //TODO : In future, we can consider fetching questions based on passage-level metadata if available to maintain some level of distribution while ensuring relevance
                    chapterQuestions = questionService.findRandomQuestionsForParagraph(
                            template.getBoardId().intValue(),
                            template.getClassId(),
                            part.getSubjectId().intValue(),
                            getSkill(metaData.getSkillDistribution()),
                            getDifficulty(metaData.getDifficultyDistribution()),
                            template.getMedium(),paragraphQuestionIds,
                            1
                    );
                    paragraphQuestionIds.addAll(chapterQuestions.stream().map(cq-> cq.getId()).toList());
                } else {
                    // Fetch questions considering SUKA and Difficulty distributions for this chapter
                     chapterQuestions = questionService.fetchQuestionsWithDistributions(
                            template.getBoardId().intValue(),
                            template.getClassId(),
                            part.getSubjectId().intValue(),
                            chapter.getChapterId().intValue(),
                            questionType,
                            template.getMedium(),
                            questionsToFetch,  // Fetch more than needed to account for duplicates
                            chapterSukaDistribution,
                            chapterDifficultyDistribution,
                             metaData
                    );
            }
                log.info("        ✅ Fetched {} questions for chapter ID: {}",
                        chapterQuestions.size(), chapter.getChapterId());

                if (chapterQuestions.isEmpty()) {
                    String missingSummary = summarizeMissingRequirements(
                        chapterQuestions, chapterSukaDistribution, chapterDifficultyDistribution,
                        questionsForChapter, questionsToFetch);
                    String message = String.format(
                        "Some required fields are missing. Add questions to each of them. %nSubject: '%s', Chapter: '%s' (ID: %s), Question Type: %s, Medium: %s.%n%s",
                        part.getSubjectName() != null ? part.getSubjectName() : "Unknown",
                        chapter.getChapterName() != null ? chapter.getChapterName() : "Unknown",
                        chapter.getChapterId(),
                        questionType,
                        template.getMedium(),
                        missingSummary);
                    log.error(message);
                    throw new ValidationException(message);
                }

                // Prepare questions for batch insertion
                int skippedDuplicatesCount = 0;
                int addedFromChapterCount = 0;

                for (QuestionEntity question : chapterQuestions) {
                    // Stop if we've added enough questions for this chapter
                    if (addedFromChapterCount >= questionsForChapter && !questionType.equals("paragraph-based-mcq")) {
                        log.info("        ℹ️ Reached target of {} questions for chapter ID: {}, stopping",
                                questionsForChapter, chapter.getChapterId());
                        break;
                    }
                    if (addedFromChapterCount >=  section.getNumberOfQuestions() && questionType.equals("paragraph-based-mcq")) {
                        log.info("        ℹ️ Reached target of {} questions for chapter ID: {}, stopping for paragraph-based questions",
                                questionsForChapter, chapter.getChapterId());
                        break;
                    }

                    // Skip if this question was already used in this section
                    if (usedQuestionIds.contains(question.getId())) {
                        log.debug("        ⏭️ Skipping duplicate question ID: {} (already used in this section)", question.getId());
                        skippedDuplicatesCount++;
                        continue;
                    }

                    // Check if question already exists in this question paper (from previous sections)
                    if (questionPaperQuestionRepository.existsByQuestionPaperIdAndQuestionId(questionPaperId, question.getId())) {
                        log.debug("        ⏭️ Skipping duplicate question ID: {} (already exists in question paper)", question.getId());
                        skippedDuplicatesCount++;
                        continue;
                    }

                    globalSequenceNumber++;

                    QuestionPaperQuestion qpq = QuestionPaperQuestion.builder()
                            .questionPaperId(questionPaperId)
                            .questionId(question.getId())
                            .sequenceNumber(globalSequenceNumber)
                            .subjectId(part.getSubjectId())
                            .partId(part.getId())
                            .sectionId(section.getId())
                            .build();

                    questionsToSave.add(qpq);
                    usedQuestionIds.add(question.getId());
                    totalQuestionsGenerated++;
                    addedFromChapterCount++;
                }

                log.info("        ✅ Chapter ID: {} - Fetched: {}, Added: {}, Skipped duplicates: {}, Expected: {}",
                        chapter.getChapterId(),
                        chapterQuestions.size(),
                        addedFromChapterCount,
                        skippedDuplicatesCount,
                        questionsForChapter);

                // Error if we didn't get enough unique questions for this chapter even with buffer
                if (addedFromChapterCount < questionsForChapter) {
                    String missingSummary = summarizeMissingRequirements(
                        chapterQuestions, chapterSukaDistribution, chapterDifficultyDistribution,
                        questionsForChapter, questionsToFetch);
                    String message = String.format(
                        "Some required fields are missing. Add questions to each of them. Subject: '%s', Chapter: '%s' (ID: %s), Question Type: %s, Medium: %s.%n%s",
                        part.getSubjectName() != null ? part.getSubjectName() : "Unknown",
                        chapter.getChapterName() != null ? chapter.getChapterName() : "Unknown",
                        chapter.getChapterId(),
                        questionType,
                        template.getMedium(),
                        missingSummary);
                    log.error(message);
                    throw new ValidationException(message);
                }
            } catch (ValidationException e) {
                log.error("        ❌ Failed to fetch questions for chapter ID: {} - {}",
                        chapter.getChapterId(), e.getMessage(),e);
                // Continue with next chapter instead of failing the entire process
                throw e;
            } catch (Exception e) {

                log.error("        ❌ Failed to fetch questions for chapter ID: {} - {}",
                        chapter.getChapterId(), e.getMessage(),e);
                // Continue with next chapter instead of failing the entire process
                throw new RuntimeException(e);
            }
        }

        // Perform batch save of all collected questions
        if (!questionsToSave.isEmpty()) {
            log.info("      💾 Performing batch save of {} questions...", questionsToSave.size());
            questionPaperQuestionRepository.batchSave(questionsToSave);
            log.info("      ✅ Successfully saved {} questions to database", questionsToSave.size());
        }else{
            throw new ValidationException(String.format(
                    "Section '%s': No questions were generated. " +
                    "Please verify that questions exist in the database matching the section's criteria.",
                    section.getName()));
        }

        log.info("      ✅ Section completed: {} total questions generated (Target: {})",
                totalQuestionsGenerated, totalQuestionsNeeded);

        // Validate that we have the expected number of questions
        if (totalQuestionsGenerated < totalQuestionsNeeded) {
            int shortage = totalQuestionsNeeded - totalQuestionsGenerated;
            log.error("      ❌ QUESTION SHORTAGE: Section '{}' expected {} questions but only generated {}. Shortage: {} questions.",
                    section.getName(), totalQuestionsNeeded, totalQuestionsGenerated, shortage);

            // Build section-level distributions based on the actual target count
            Map<String, Integer> sectionSukaDistribution = calculateSukaDistribution(totalQuestionsNeeded, metaData);
            Map<String, Integer> sectionDifficultyDistribution = calculateDifficultyDistribution(totalQuestionsNeeded, metaData);

            // Build rich message for every chapter in this section
            StringBuilder messageBuilder = new StringBuilder();
            for (Map.Entry<SyllabusChapter, Integer> entry : chapterQuestionDistribution.entrySet()) {
                SyllabusChapter ch = entry.getKey();
                messageBuilder.append(String.format(
                        "Not enough questions found in the database to build the exam paper.%n" +
                        "Subject: '%s' | Chapter: '%s' (ID: %s) | Question Type: %s | Medium: %s%n" +
                        "Please generate or add more questions for the missing combinations below:%n",
                        part.getSubjectName() != null ? part.getSubjectName() : "Unknown",
                        ch.getChapterName() != null ? ch.getChapterName() : "chapter_name",
                        ch.getChapterId(),
                        questionType,
                        template.getMedium()));
            }

            messageBuilder.append(summarizeMissingRequirementsForShortage(
                    sectionSukaDistribution,
                    sectionDifficultyDistribution,
                    totalQuestionsNeeded,
                    totalQuestionsGenerated));

            String message = messageBuilder.toString();
            log.error(message);
            throw new ValidationException(message);
        } else if (totalQuestionsGenerated > totalQuestionsNeeded) {
            log.warn("      ⚠️ Section '{}' generated {} questions but only {} were expected. Excess: {} questions.",
                    section.getName(), totalQuestionsGenerated, totalQuestionsNeeded,
                    totalQuestionsGenerated - totalQuestionsNeeded);
        }
    }

    // Helper to summarize missing requirements — only shows combinations where Missing > 0
    private String summarizeMissingRequirements(
            List<com.mahaexam.question.model.QuestionEntity> chapterQuestions,
            Map<String, Integer> sukaDistribution,
            Map<String, Integer> difficultyDistribution,
            int questionsForChapter,
            int questionsToFetch) {

        Map<String, Map<String, Long>> foundMap = chapterQuestions.stream()
                .filter(q -> q.getSkillLevel() != null && q.getDifficultyLevel() != null)
                .collect(Collectors.groupingBy(
                        q -> q.getSkillLevel().toUpperCase(),
                        Collectors.groupingBy(
                                q -> q.getDifficultyLevel().toUpperCase(),
                                Collectors.counting())));

        StringBuilder sb = new StringBuilder(
                "The following Skill × Difficulty combinations do not have enough questions in the database:\n");

        for (Map.Entry<String, Integer> sukaEntry : sukaDistribution.entrySet()) {
            if (sukaEntry.getValue() == null || sukaEntry.getValue() <= 0) continue;
            String skillKey = sukaEntry.getKey().toUpperCase();
            String skillDisplay = sukaEntry.getKey().substring(0, 1).toUpperCase()
                    + sukaEntry.getKey().substring(1).toLowerCase();

            for (Map.Entry<String, Integer> diffEntry : difficultyDistribution.entrySet()) {
                if (diffEntry.getValue() == null || diffEntry.getValue() <= 0) continue;
                String diffKey = diffEntry.getKey().toUpperCase();
                String diffDisplay = diffEntry.getKey().substring(0, 1).toUpperCase()
                        + diffEntry.getKey().substring(1).toLowerCase();

                // Proportional required count for this specific combination
                int required = (int) Math.max(1, Math.round(
                        (double) sukaEntry.getValue() / questionsToFetch *
                        diffEntry.getValue() / questionsToFetch * questionsForChapter));

                long found = foundMap
                        .getOrDefault(skillKey, java.util.Collections.emptyMap())
                        .getOrDefault(diffKey, 0L);

                long missing = required - found;
                if (missing <= 0) continue; // fully satisfied — skip

                sb.append(String.format(
                        "  • Skill Level: %-15s | Difficulty: %-8s | Required: %d | Found: %d | Missing: %d%n",
                        skillDisplay, diffDisplay, required, found, missing));
            }
        }
        return sb.toString();
    }

    private String buildMissingCombinationsDetail(
            List<com.mahaexam.question.model.QuestionEntity> chapterQuestions,
            Map<String, Integer> sukaDistribution,
            Map<String, Integer> difficultyDistribution,
            int questionsForChapter,
            int questionsToFetch) {

        Map<String, Map<String, Long>> foundMap = chapterQuestions.stream()
                .filter(q -> q.getSkillLevel() != null && q.getDifficultyLevel() != null)
                .collect(Collectors.groupingBy(
                        q -> q.getSkillLevel().toUpperCase(),
                        Collectors.groupingBy(
                                q -> q.getDifficultyLevel().toUpperCase(),
                                Collectors.counting())));

        StringBuilder sb = new StringBuilder("Missing questions for the following SUKA-Difficulty combinations:");
        boolean anyMissing = false;

        for (Map.Entry<String, Integer> sukaEntry : sukaDistribution.entrySet()) {
            if (sukaEntry.getValue() == null || sukaEntry.getValue() <= 0) continue;
            String skill = sukaEntry.getKey().toUpperCase();

            for (Map.Entry<String, Integer> diffEntry : difficultyDistribution.entrySet()) {
                if (diffEntry.getValue() == null || diffEntry.getValue() <= 0) continue;
                String difficulty = diffEntry.getKey().toUpperCase();

                int required = Math.max(1, (int) Math.round(
                        (double) sukaEntry.getValue() / questionsToFetch *
                        (double) diffEntry.getValue() / questionsToFetch * questionsForChapter));

                long found = foundMap.getOrDefault(skill, java.util.Collections.emptyMap())
                        .getOrDefault(difficulty, 0L);

                if (found < required) {
                    sb.append(String.format("%n  Skill: '%s', Difficulty: '%s', Required: %d, Found: %d",
                            skill, difficulty, required, (int) found));
                    anyMissing = true;
                }
            }
        }

        return anyMissing ? sb.toString() : "";
    }

    /**
     * Summarizes Skill × Difficulty combinations that are short when the total
     * generated count is less than needed. Uses only the distribution maps and
     * total counts — no chapter question list required.
     * Only shows rows where Missing > 0.
     */
    private String summarizeMissingRequirementsForShortage(
            Map<String, Integer> sukaDistribution,
            Map<String, Integer> difficultyDistribution,
            int totalQuestionsNeeded,
            int totalQuestionsGenerated) {

        StringBuilder sb = new StringBuilder(
                "The following Skill × Difficulty combinations do not have enough questions in the database:\n");

        for (Map.Entry<String, Integer> sukaEntry : sukaDistribution.entrySet()) {
            if (sukaEntry.getValue() == null || sukaEntry.getValue() <= 0) continue;
            String skillDisplay = sukaEntry.getKey().substring(0, 1).toUpperCase()
                    + sukaEntry.getKey().substring(1).toLowerCase();

            for (Map.Entry<String, Integer> diffEntry : difficultyDistribution.entrySet()) {
                if (diffEntry.getValue() == null || diffEntry.getValue() <= 0) continue;
                String diffDisplay = diffEntry.getKey().substring(0, 1).toUpperCase()
                        + diffEntry.getKey().substring(1).toLowerCase();

                // Required count for this specific Skill × Difficulty combination
                int required = (int) Math.max(1, Math.round(
                        (double) sukaEntry.getValue() / totalQuestionsNeeded *
                        (double) diffEntry.getValue() / totalQuestionsNeeded * totalQuestionsNeeded));

                // Proportional found count — scale down by the shortage ratio
                int found = (int) Math.floor(
                        (double) required * totalQuestionsGenerated / totalQuestionsNeeded);

                int missing = required - found;
                if (missing <= 0) continue; // fully satisfied — skip

                sb.append(String.format(
                        "  * Skill Level: %-15s | Difficulty: %-8s | Required: %d | Found: %d | Missing: %d%n",
                        skillDisplay, diffDisplay, required, found, missing));
            }
        }
        return sb.toString();
    }

    private String getDifficulty(QuestionPaperMetaData.DifficultyDistribution difficultyDistribution) {
        if(difficultyDistribution.getEasy()>0){
            return "EASY";
        } else if(difficultyDistribution.getMedium()>0){
            return "MEDIUM";
        } else if(difficultyDistribution.getHard()>0) {
            return "HARD";
        }
        return null;
    }

    private String getSkill(QuestionPaperMetaData.SkillDistribution skillDistribution) {
        if(skillDistribution.getSkill()>0){
            return "SKILL";
        } else if(skillDistribution.getUnderstanding()>0){
            return "UNDERSTANDING";
        } else if(skillDistribution.getKnowledge()>0){
            return "KNOWLEDGE";
        } else if(skillDistribution.getApplication()>0) {
            return "APPLICATION";
        }
        return null;
    }

    /**
     * Calculate SUKA distribution for questions
     */
    private Map<String, Integer> calculateSukaDistribution(int totalQuestions, QuestionPaperMetaData metaData) {
        Map<String, Integer> distribution = new LinkedHashMap<>();

        // Guard: negative totalQuestions can come from rounding over-allocation — treat as 0
        if (totalQuestions <= 0) {
            distribution.put("SKILL", 0);
            distribution.put("UNDERSTANDING", 0);
            distribution.put("KNOWLEDGE", 0);
            distribution.put("APPLICATION", 0);
            return distribution;
        }

        if (metaData == null || metaData.getSkillDistribution() == null) {
            // Default equal distribution if no metaData provided
            int perCategory = Math.max(1, totalQuestions / 4);
            distribution.put("SKILL", perCategory);
            distribution.put("UNDERSTANDING", perCategory);
            distribution.put("KNOWLEDGE", perCategory);
            distribution.put("APPLICATION", Math.max(1, totalQuestions - (perCategory * 3)));
            return distribution;
        }

        QuestionPaperMetaData.SkillDistribution skillDist = metaData.getSkillDistribution();

        int skillCount       = calculatePercentageCount(totalQuestions, skillDist.getSkill());
        int understandingCount = calculatePercentageCount(totalQuestions, skillDist.getUnderstanding());
        int knowledgeCount   = calculatePercentageCount(totalQuestions, skillDist.getKnowledge());
        int applicationCount = Math.max(0, totalQuestions - skillCount - understandingCount - knowledgeCount);

        // Guard: if rounding caused all counts to be 0 for active skill levels,
        // guarantee at least 1 per active level so the distribution matrix is never empty.
        boolean anyActive = (skillDist.getSkill()       != null && skillDist.getSkill()       > 0)
                         || (skillDist.getUnderstanding()!= null && skillDist.getUnderstanding()> 0)
                         || (skillDist.getKnowledge()    != null && skillDist.getKnowledge()   > 0)
                         || (skillDist.getApplication()  != null && skillDist.getApplication() > 0);

        if (anyActive && (skillCount + understandingCount + knowledgeCount + applicationCount) == 0) {
            // Very small totalQuestions — give at least 1 to each active level
            if (skillDist.getSkill()        != null && skillDist.getSkill()        > 0) skillCount        = 1;
            if (skillDist.getUnderstanding()!= null && skillDist.getUnderstanding()> 0) understandingCount = 1;
            if (skillDist.getKnowledge()    != null && skillDist.getKnowledge()    > 0) knowledgeCount     = 1;
            if (skillDist.getApplication()  != null && skillDist.getApplication()  > 0) applicationCount   = 1;
            log.warn("calculateSukaDistribution: all counts were 0 for totalQuestions={}. " +
                    "Assigned 1 per active skill level.", totalQuestions);
        }

        distribution.put("SKILL",         skillCount);
        distribution.put("UNDERSTANDING", understandingCount);
        distribution.put("KNOWLEDGE",     knowledgeCount);
        distribution.put("APPLICATION",   applicationCount);

        return distribution;
    }

    /**
     * Calculate Difficulty distribution for questions
     */
    private Map<String, Integer> calculateDifficultyDistribution(int totalQuestions, QuestionPaperMetaData metaData) {
        Map<String, Integer> distribution = new LinkedHashMap<>();

        // Guard: negative totalQuestions can come from rounding over-allocation — treat as 0
        if (totalQuestions <= 0) {
            distribution.put("HARD", 0);
            distribution.put("MEDIUM", 0);
            distribution.put("EASY", 0);
            return distribution;
        }

        if (metaData == null || metaData.getDifficultyDistribution() == null) {
            // Default equal distribution if no metaData provided
            int perCategory = Math.max(1, totalQuestions / 3);
            distribution.put("HARD", perCategory);
            distribution.put("MEDIUM", perCategory);
            distribution.put("EASY", Math.max(1, totalQuestions - (perCategory * 2)));
            return distribution;
        }

        QuestionPaperMetaData.DifficultyDistribution diffDist = metaData.getDifficultyDistribution();

        int hardCount   = calculatePercentageCount(totalQuestions, diffDist.getHard());
        int mediumCount = calculatePercentageCount(totalQuestions, diffDist.getMedium());
        int easyCount   = Math.max(0, totalQuestions - hardCount - mediumCount);

        // Guard: if rounding caused all counts to be 0 for active difficulty levels,
        // guarantee at least 1 per active level so the distribution matrix is never empty.
        boolean anyActive = (diffDist.getHard()  != null && diffDist.getHard()  > 0)
                         || (diffDist.getMedium()!= null && diffDist.getMedium()> 0)
                         || (diffDist.getEasy()  != null && diffDist.getEasy()  > 0);

        if (anyActive && (hardCount + mediumCount + easyCount) == 0) {
            if (diffDist.getHard()  != null && diffDist.getHard()  > 0) hardCount   = 1;
            if (diffDist.getMedium()!= null && diffDist.getMedium()> 0) mediumCount = 1;
            if (diffDist.getEasy()  != null && diffDist.getEasy()  > 0) easyCount   = 1;
            log.warn("calculateDifficultyDistribution: all counts were 0 for totalQuestions={}. " +
                    "Assigned 1 per active difficulty level.", totalQuestions);
        }

        distribution.put("HARD",   hardCount);
        distribution.put("MEDIUM", mediumCount);
        distribution.put("EASY",   easyCount);

        return distribution;
    }

    /**
     * Calculate count from percentage
     */
    private int calculatePercentageCount(int total, Double percentage) {
        if (percentage == null) {
            return 0;
        }
        BigDecimal count = BigDecimal.valueOf(percentage)
                .divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(total));
        return count.setScale(0, RoundingMode.HALF_UP).intValue();
    }

    /**
     * Calculate how many questions should be generated for each chapter
     * based on coverage percentage
     */
    private Map<SyllabusChapter, Integer> calculateQuestionDistribution(
            List<SyllabusChapter> chapters, int totalQuestions) {

        Map<SyllabusChapter, Integer> distribution = new LinkedHashMap<>();

        if (chapters == null || chapters.isEmpty()) {
            log.warn("No chapters found in syllabus");
            return distribution;
        }

        log.info("      📊 Calculating question distribution for {} chapters, {} total questions",
                chapters.size(), totalQuestions);

        int allocatedQuestions = 0;

        // Calculate questions for each chapter based on coverage percentage
        for (int i = 0; i < chapters.size(); i++) {
            SyllabusChapter chapter = chapters.get(i);

            int questionsForChapter;

            // For the last chapter, allocate remaining questions to avoid rounding errors
            if (i == chapters.size() - 1) {
                // Clamp to 0 — earlier chapters may have over-allocated due to HALF_UP rounding
                questionsForChapter = Math.max(0, totalQuestions - allocatedQuestions);
            } else {
                // Calculate based on coverage percentage
                BigDecimal percentage = chapter.getCoveragePercentage();
                if (percentage == null) {
                    percentage = BigDecimal.ZERO;
                }

                BigDecimal questionsDecimal = percentage
                        .divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_UP)
                        .multiply(BigDecimal.valueOf(totalQuestions));

                questionsForChapter = questionsDecimal.setScale(0, RoundingMode.HALF_UP).intValue();
                allocatedQuestions += questionsForChapter;
            }

            distribution.put(chapter, questionsForChapter);

            log.info("        Chapter ID: {} - Coverage: {}% - Questions: {}",
                    chapter.getChapterId(),
                    chapter.getCoveragePercentage(),
                    questionsForChapter);
        }

        return distribution;
    }

    /**
     * Get all questions for a specific question paper
     */
    @Deprecated
    public List<QuestionPaperQuestion> getQuestionPaperQuestions(Long questionPaperId) {
        log.info("📋 Fetching all questions for question paper ID: {}", questionPaperId);
        return questionPaperQuestionRepository.findByQuestionPaperIdOrderBySequenceNumber(questionPaperId);
    }


    /**
     * Get total question count for a question paper
     */
    public Long getQuestionCount(Long questionPaperId) {
        return questionPaperQuestionRepository.countByQuestionPaperId(questionPaperId);
    }

    /**
     * Delete all questions for a question paper (useful for regeneration)
     */
    @Transactional
    public void deleteQuestionPaperQuestions(Long questionPaperId) {
        log.info("🗑️ Deleting all questions for question paper ID: {}", questionPaperId);
        questionPaperQuestionRepository.deleteByQuestionPaperId(questionPaperId);
    }

    @Override
    @Transactional(readOnly = true)
    public QuestionPaperResponseDTO getQuestionPaperById(Long questionPaperId,boolean hideAnswer, int page, int size) {

        QuestionPaper questionPaper = questionPaperRepository.findById(questionPaperId)
                .orElseThrow(() ->
                        new RuntimeException("Question Paper not found: " + questionPaperId));
        /*
        List<QuestionEntity> questions = null;
        if(!(page==0 && size==0)){
            // Fetch all questions without pagination
            // Fetch paginated questions for this question paper
            questions = questionService.findByQuestionPaperIdPaginated(questionPaperId, page, size);
            questions.forEach(q-> {;
                if(hideAnswer){
                    q.setCorrectAnswer(null);
                    q.setAnswerExplanation(null);
                }
            });
        }
         */
        QuestionPaperResponseDTO response = QuestionPaperMapper.toResponse(questionPaper, null);

        // Fetch and populate paper templates for this question paper
        List<QuestionPaperTemplateResponseDTO> paperTemplates = questionPaperTemplateRepository.findByQuestionPaperId(questionPaperId)
                .stream()
                .map(QuestionPaperTemplateMapper::toResponse)
                .toList();
        if(!(page==0 && size==0)) {
            paperTemplates.forEach(pt -> {
                // Fetch full hierarchy for each paper template
                PaperTemplateResponse fullHierarchy = paperTemplateService.getFullHierarchy(pt.getPaperTemplateId());
                pt.setPaperTemplateResponse(fullHierarchy);
                //Need to fetch questions for each part and each section in the hierarchy
            });
        }
        response.setPaperTemplates(paperTemplates);

        return response;
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<QuestionPaperResponseDTO> getQuestionPapersByClass(Integer classId) {

        return questionPaperRepository.findByClassId(classId)
                .stream()
                .map(QuestionPaperMapper::toResponse)
                .toList();
    }


    @Override
    @Transactional(readOnly = true)
    public QuestionPaperHierarchyResponseDTO getQuestionPaperHierarchyById(Long questionPaperId, boolean hideAnswer, UserBean user) {
        QuestionPaper questionPaper = questionPaperRepository.findById(questionPaperId)
                .orElseThrow(() -> new RuntimeException("Question Paper not found: " + questionPaperId));

        List<PartQuestionsDTO> partQuestionsList = new java.util.ArrayList<>();

        // Accumulators — derived from paper_template hierarchy (no extra DB calls needed)
        int calculatedTotalDuration  = 0;
        int calculatedTotalQuestions = 0;

        List<PaperTemplateResponse> paperTemplates = paperTemplateService.getFullHierarchyByQuestionPaperId(questionPaperId);
        // Build hierarchy using paperTemplates and partQuestionsList
        List<String> instructions = new ArrayList<>();
        for (PaperTemplateResponse template : paperTemplates) {
            if (template.getInstructions() != null) {
                instructions.addAll(template.getInstructions());
            }
            // Accumulate duration from each linked paper template
            if (template.getTotalDuration() != null) {
                calculatedTotalDuration += template.getTotalDuration();
            }
            for (PartResponse part : template.getParts()) {
                PartQuestionsDTO partDTO = new PartQuestionsDTO();
                partDTO.setPartName(part.getName());
                partDTO.setSubjectId(part.getSubjectId().intValue());
                partDTO.setSubjectName(part.getSubjectName());
                partDTO.setDisplaySubject(part.getDisplaySubject());
                partDTO.setDisplayName(part.getDisplayName());
                List<SectionQuestionsDTO> sectionDTOs = new java.util.ArrayList<>();
                for (SectionResponse section : part.getSections()) {
                    SectionQuestionsDTO sectionDTO = new SectionQuestionsDTO();
                    sectionDTO.setSectionName(section.getName());
                    sectionDTO.setQuestionType(section.getQuestionType());
                    sectionDTO.setTotalQuestions(section.getNumberOfQuestions());
                    sectionDTO.setDisplayName(section.getDisplayName());
                    calculatedTotalQuestions += section.getNumberOfQuestions();
                    // Fetch questions for this section
                    List<QuestionEntity> questions = questionService.fetchQuestionsForSection(
                        questionPaperId,
                        part.getSubjectId().intValue(),
                        part.getId(),
                        section.getId().intValue(),
                        section.getNumberOfQuestions()
                    );
                    if(hideAnswer){
                        // Use single-element arrays so values can be mutated inside the lambda
                        final String[] currentParagraphId   = { null };
                        final String[] currentParagraphText = { null };
                        questions.forEach(q -> {
                            if ("paragraph-based-mcq".equals(q.getQuestionType())) {
                                String qParagraphId = q.getParagraphId();
                                if (qParagraphId != null && qParagraphId.equals(currentParagraphId[0])) {
                                    // Same paragraph — reuse the text captured from the first question
                                    q.setParagraphText(currentParagraphText[0]);
                                } else {
                                    // New paragraph — capture its id and text for subsequent questions
                                    currentParagraphId[0]   = qParagraphId;
                                    currentParagraphText[0] = q.getParagraphText();
                                }
                            }
                            q.setCorrectAnswer(null);
                            q.setAnswerExplanation(null);
                            q.setAnswerExplanationImageUrl(null);
                            // Parse options: handle both single-encoded and double-encoded JSON strings.
                            try {
                                Object parsed = objectMapper.readValue(q.getOptions(), Object.class);
                                if (parsed instanceof String) {
                                    parsed = objectMapper.readValue((String) parsed, Object.class);
                                }
                                q.setOptions(objectMapper.writeValueAsString(parsed));
                            } catch (com.fasterxml.jackson.core.JsonProcessingException e) {
                                log.error("Failed to parse options for question id {}: {}", q.getId(), e.getMessage());
                            }
                        });
                    }
                    sectionDTO.setQuestions(questions);
                    sectionDTOs.add(sectionDTO);
                }
                partDTO.setSections(sectionDTOs);
                partQuestionsList.add(partDTO);
            }
        }
        QuestionPaperHierarchyResponseDTO response = new QuestionPaperHierarchyResponseDTO();
        response.setId(questionPaper.getId());
        response.setQuestionPaperName(questionPaper.getQuestionPaperName());
        response.setAcademicYear(questionPaper.getAcademicYear());
        response.setStatus(questionPaper.getStatus());
        response.setStartDate(questionPaper.getStartDate());
        response.setEndDate(questionPaper.getEndDate());
        response.setDescription(questionPaper.getDescription());
        response.setCreatedAt(questionPaper.getCreatedAt());
        response.setMetaData(questionPaper.getMetaData());
        response.setParts(partQuestionsList);
        // Totals calculated from the paper template hierarchy
        response.setTotalDuration(calculatedTotalDuration);
        response.setExamTotalQuestions(calculatedTotalQuestions);
        response.setInstructions(instructions);

        // Generate a short-lived exam token when a student starts the exam
        // (hideAnswer=false means it's a student attempting the exam, not an admin previewing)
        if (user != null && user.getUserId() != null && calculatedTotalDuration > 0) {
            String examToken = examTokenService.generateExamToken(
                    user.getUserId(), questionPaperId, calculatedTotalDuration);
            response.setExamToken(examToken);
        }

        return response;
    }

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private String getConfigValue(String configName, String errorMessage) {
        Optional<Config> configOpt = configService.findByName(configName);
        return configOpt.orElseThrow(() -> new IllegalArgumentException(errorMessage + ": " + configName))
                .getValue();
    }
}

