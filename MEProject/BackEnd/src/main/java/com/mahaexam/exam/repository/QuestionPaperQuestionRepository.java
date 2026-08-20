package com.mahaexam.exam.repository;

import com.mahaexam.exam.model.QuestionPaperQuestion;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionPaperQuestionRepository {

    /**
     * Save a question paper question
     */
    void save(QuestionPaperQuestion questionPaperQuestion);

    /**
     * Batch save multiple question paper questions
     */
    void batchSave(List<QuestionPaperQuestion> questionPaperQuestions);

    /**
     * Find all questions for a specific question paper
     */
    List<QuestionPaperQuestion> findByQuestionPaperIdOrderBySequenceNumber(Long questionPaperId);

    /**
     * Count total questions in a question paper
     */
    Long countByQuestionPaperId(Long questionPaperId);

    /**
     * Delete all questions for a question paper
     */
    void deleteByQuestionPaperId(Long questionPaperId);

    /**
     * Check if a question already exists in the question paper
     */
    boolean existsByQuestionPaperIdAndQuestionId(Long questionPaperId, Long questionId);

    /**
     * Get the maximum sequence number for a question paper
     */
    Integer getMaxSequenceNumber(Long questionPaperId);
}




