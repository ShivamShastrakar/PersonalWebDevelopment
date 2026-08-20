package com.mahaexam.question.service;

import com.mahaexam.common.bean.QuestionPaperMetaData;
import com.mahaexam.common.bean.UserBean;
import com.mahaexam.openai.model.QuestionMetadata;
import com.mahaexam.openai.model.QuestionSet;
import com.mahaexam.question.model.QuestionEntity;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Service interface for question management operations
 */
public interface QuestionService {

    /**
     * Save a QuestionSet (with metadata and questions) to the database
     * @param questionSet the question set to save
     * @param user user bean who created these questions
     * @return list of saved question entities
     */
    List<QuestionEntity> saveQuestionSet(QuestionSet questionSet, UserBean user);

    /**
     * Find questions by various criteria
     * @param boardId board ID (optional)
     * @param classId class ID
     * @param subjectId subject ID
     * @param topicId topic ID (optional)
     * @param questionType question type (optional)
     * @param skillLevel skill level (optional)
     * @param difficultyLevel difficulty level (optional)
     * @return list of matching questions
     */
    List<QuestionEntity> findQuestions(Integer boardId, Integer classId, Integer subjectId, Integer topicId,
                                       String questionType, String skillLevel, String difficultyLevel);

    /**
     * Get a question by ID
     * @param id question ID
     * @return question entity if found, null otherwise
     */
    QuestionEntity getQuestionById(Long id);

    /**
     * Delete a question by ID
     * @param id question ID
     * @return true if deleted, false if not found
     */
    boolean deleteQuestion(Long id);

    /**
     * Check if questions with same AI prompt hash already exist
     * @param aiPromptHash the AI prompt hash
     * @return true if exists, false otherwise
     */
    boolean isDuplicatePrompt(String aiPromptHash);

    /**
     * Generate questions using OpenAI based on metadata and save to database
     * This method handles the complete workflow:
     * 1. Generate prompt from metadata
     * 2. Call OpenAI API
     * 3. Parse response to QuestionSet
     * 4. Save to database
     *
     * @param metadata the question metadata
     * @param user user bean who is generating these questions
     * @return list of saved question entities
     * @throws Exception if any step in the workflow fails
     */
    List<QuestionEntity> generateAndSaveQuestions(QuestionMetadata metadata, UserBean user) throws Exception;

    /**
     * Get random questions as PDF
     * @param metadata question metadata with IDs, names, and filters
     * @param user user bean to get student's preferred medium
     * @return PDF byte array with questions and answers
     */
    byte[] getRandomQuestionsAsPdf(QuestionMetadata metadata, UserBean user);

    /**
     * Generate questions for each topic of a given chapter
     * This method fetches all topics for the specified chapter and generates
     * the specified number of questions for each topic.
     *
     * @param metadata QuestionMetadata containing:
     *                 - boardId, classId, subjectId, chapterId (required)
     *                 - numberOfQuestions: questions per topic (required)
     *                 - medium: medium of instruction (required)
     *                 - questionType: type of questions (optional, defaults to "MCQ")
     *                 - skillLevel: skill level (optional, defaults to "Intermediate")
     *                 - difficulty: difficulty level (optional, defaults to "Medium")
     * @param user user bean who is generating these questions
     * @return map of topic ID to list of generated question entities
     * @throws Exception if any step in the workflow fails
     */
    Map<Integer, List<QuestionEntity>> generateQuestionsForChapterTopics(
            QuestionMetadata metadata,
            UserBean user) throws Exception;

    /**
     * Fetch existing questions from database for a chapter based on criteria
     * This is used for exam paper generation to avoid regenerating questions
     *
     * @param boardId board ID
     * @param classId class ID
     * @param subjectId subject ID
     * @param chapterId chapter ID
     * @param questionType question type (MCQ, Numerical, etc.)
     * @param medium medium of instruction
     * @param numberOfQuestions number of questions to fetch
     * @return list of random questions matching criteria
     */
    List<QuestionEntity> fetchQuestionsForChapter(
            Integer boardId,
            Integer classId,
            Integer subjectId,
            Integer chapterId,
            String questionType,
            String medium,
            int numberOfQuestions
    );

    List<QuestionEntity> findRandomQuestionsForParagraph(Integer boardId, Integer classId, Integer subjectId,
                                                         String skillLevel, String difficultyLevel, String medium, Set<Long> paragraphQuestionIds , int limit);
    /**
     * Fetch existing questions from database for a chapter with SUKA and Difficulty distributions
     * This is used for exam paper generation with specific skill and difficulty level requirements
     *
     * @param boardId board ID
     * @param classId class ID
     * @param subjectId subject ID
     * @param chapterId chapter ID
     * @param questionType question type (MCQ, Numerical, etc.)
     * @param medium medium of instruction
     * @param numberOfQuestions total number of questions to fetch
     * @param sukaDistribution map of SUKA levels to question counts (SKILL, UNDERSTANDING, KNOWLEDGE, APPLICATION)
     * @param difficultyDistribution map of difficulty levels to question counts (HARD, MEDIUM, EASY)
     * @return list of questions matching criteria with specified distributions
     */
    List<QuestionEntity> fetchQuestionsWithDistributions(
            Integer boardId,
            Integer classId,
            Integer subjectId,
            Integer chapterId,
            String questionType,
            String medium,
            int numberOfQuestions,
            Map<String, Integer> sukaDistribution,
            Map<String, Integer> difficultyDistribution,
            QuestionPaperMetaData metaData
    );

    /**
     * Find questions by a list of IDs
     * @param questionPaperId question Paper Id
     * @return list of matching question entities
     */
    List<QuestionEntity> findByQuestionPaperId(Long questionPaperId);

    /**
     * Find questions by a list of IDs with pagination
     * @param questionPaperId question Paper Id
     * @param page page number (0-based)
     * @param size page size
     * @return list of matching question entities
     */
    List<QuestionEntity> findByQuestionPaperIdPaginated(Long questionPaperId, int page, int size);

    /**
     *      * Fetch existing questions from database for a section based on criteria
     *      * This is used for exam paper generation to avoid regenerating questions
     *
     * @param questionPaperId question Paper Id
     * @param subjectId subject ID
     * @param partId
     * @param sectionId
     * @param numberOfQuestions  number of questions to fetch
     * @return list of random questions matching criteria
     */
    List<QuestionEntity> fetchQuestionsForSection(Long questionPaperId,  Integer subjectId,Long partId, Integer sectionId, int numberOfQuestions);

    List<QuestionEntity> findAllByTenantId(Long tenantId);
}
