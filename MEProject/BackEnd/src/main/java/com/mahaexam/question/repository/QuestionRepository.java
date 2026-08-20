package com.mahaexam.question.repository;

import com.mahaexam.question.model.QuestionEntity;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Repository interface for Question entity operations
 */
public interface QuestionRepository {

    /**
     * Save a new question
     * @param question the question to save
     * @return the saved question with generated ID
     */
    QuestionEntity save(QuestionEntity question);

    /**
     * Save multiple questions in batch
     * @param questions list of questions to save
     * @return list of saved questions with generated IDs
     */
    List<QuestionEntity> saveAll(List<QuestionEntity> questions);

    /**
     * Update an existing question
     * @param question the question to update
     * @return the updated question
     */
    QuestionEntity update(QuestionEntity question);

    /**
     * Find a question by ID
     * @param id the question ID
     * @return Optional containing the question if found
     */
    Optional<QuestionEntity> findById(Long id);

    /**
     * Find all questions scoped by tenant
     * @return list of all questions for the given tenant
     */
    List<QuestionEntity> findAllByTenantId(Long tenantId);

    /**
     * Find questions by subject and class
     * @param subjectId the subject ID
     * @param classId the class ID
     * @return list of questions matching the criteria
     */
    List<QuestionEntity> findBySubjectAndClass(Integer subjectId, Integer classId);

    /**
     * Find questions by multiple criteria
     * @param boardId the board ID (optional)
     * @param classId the class ID
     * @param subjectId the subject ID
     * @param topicId the topic ID (optional)
     * @param questionType the question type (optional)
     * @param skillLevel the skill level (optional)
     * @param difficultyLevel the difficulty level (optional)
     * @return list of questions matching the criteria
     */
    List<QuestionEntity> findByMetadata(Integer boardId, Integer classId, Integer subjectId, Integer topicId,
                                        String questionType, String skillLevel, String difficultyLevel);

    /**
     * Find questions by chapter
     * @param chapterId the chapter ID
     * @return list of questions in the chapter
     */
    List<QuestionEntity> findByChapter(Integer chapterId);

    /**
     * Find questions by topic
     * @param topicId the topic ID
     * @return list of questions in the topic
     */
    List<QuestionEntity> findByTopic(Integer topicId);

    /**
     * Check if a question with the given AI prompt hash exists
     * @param aiPromptHash the AI prompt hash
     * @return true if exists, false otherwise
     */
    boolean existsByAiPromptHash(String aiPromptHash);

    /**
     * Find a question by AI prompt hash
     * @param aiPromptHash the AI prompt hash
     * @return Optional containing the question if found
     */
    Optional<QuestionEntity> findByAiPromptHash(String aiPromptHash);

    /**
     * Delete a question by ID
     * @param id the question ID
     * @return true if deleted, false if not found
     */
    boolean deleteById(Long id);

    /**
     * Count total questions
     * @return total number of questions
     */
    long count();

    /**
     * Count questions by criteria
     * @param classId the class ID
     * @param subjectId the subject ID
     * @param topicId the topic ID (optional)
     * @return count of questions matching the criteria
     */
    long countByMetadata(Integer classId, Integer subjectId, Integer topicId);

    /**
     * Find random questions matching given criteria
     * @param boardId board ID
     * @param classId class ID
     * @param subjectId subject ID
     * @param chapterId chapter ID (nullable)
     * @param topicId topic ID (nullable)
     * @param questionType question type (nullable)
     * @param skillLevel skill level (nullable)
     * @param difficultyLevel difficulty level (nullable)
     * @param medium medium (nullable)
     * @param limit maximum number of questions to return
     * @return list of random questions matching the criteria
     */
    List<QuestionEntity> findRandomQuestions(Integer boardId, Integer classId, Integer subjectId,
                                             Integer chapterId, Integer topicId, String questionType,
                                             String skillLevel, String difficultyLevel, String medium, int limit);
    List<QuestionEntity> findRandomQuestionsForParagraph(Integer boardId, Integer classId, Integer subjectId,
                                                         String skillLevel, String difficultyLevel, String medium, Set<Long> paragraphQuestionIds , int limit);

    List<QuestionEntity> findByQuestionPaperId(Long questionPaperId);

    List<QuestionEntity> findByQuestionPaperIdPaginated(Long questionPaperId, int page, int size);

    List<QuestionEntity> findByQuestions(Long questionPaperId, Integer subjectId, Long partId, Integer sectionId, int numberOfQuestions);


}
