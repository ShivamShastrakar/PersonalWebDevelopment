package com.mahaexam.question.service;

import com.mahaexam.model.StudentQuestionAttempt;
import com.mahaexam.model.StudentSubjectSummary;
import com.mahaexam.question.repository.StudentQuestionAttemptRepository;
import com.mahaexam.exam.model.QuestionPaperQuestion;
import com.mahaexam.exam.repository.QuestionPaperQuestionRepository;
import com.mahaexam.papertemplate.service.PaperTemplateService;
import com.mahaexam.common.bean.SectionResponse;
import com.mahaexam.common.bean.PaperTemplateResponse;
import com.mahaexam.common.bean.PartResponse;
import com.mahaexam.common.bean.UserBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class StudentQuestionAttemptServiceImpl implements StudentQuestionAttemptService {
    @Autowired
    private StudentQuestionAttemptRepository repository;

    @Autowired
    private StudentSubjectSummaryService studentSubjectSummaryService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private QuestionPaperQuestionRepository questionPaperQuestionRepository;

    @Autowired
    private PaperTemplateService paperTemplateService;

    @Transactional
    public StudentSubjectSummary save(List<StudentQuestionAttempt> attempts, Integer timeTaken, UserBean user) {
        if (attempts == null || attempts.isEmpty()) return null;

        Long tenantId = (user != null) ? user.getTenantId() : null;

        Long questionPaperId = attempts.get(0).getQuestionPaperId();
        var questions = questionService.findByQuestionPaperId(questionPaperId);

        // 1. Map questionId -> correctAnswer
        java.util.Map<Long, String> correctAnswerMap = new java.util.HashMap<>();
        for (var q : questions) {
            correctAnswerMap.put(q.getId(), q.getCorrectAnswer());
        }

        // 2. Map questionId -> sectionId
        List<QuestionPaperQuestion> qpqList = questionPaperQuestionRepository.findByQuestionPaperIdOrderBySequenceNumber(questionPaperId);
        java.util.Map<Long, Long> questionToSectionMap = new java.util.HashMap<>();
        for (QuestionPaperQuestion qpq : qpqList) {
            questionToSectionMap.put(qpq.getQuestionId(), qpq.getSectionId());
        }

        // 3. Map sectionId -> Section config (marksPerQuestion, negativeMarks)
        java.util.Map<Long, SectionResponse> sectionMap = new java.util.HashMap<>();
        List<PaperTemplateResponse> templates = paperTemplateService.getFullHierarchyByQuestionPaperId(questionPaperId);
        for (PaperTemplateResponse template : templates) {
            for (PartResponse part : template.getParts()) {
                for (SectionResponse section : part.getSections()) {
                    sectionMap.put(section.getId(), section);
                }
            }
        }

        for (StudentQuestionAttempt attempt : attempts) {
            // Stamp tenant on every attempt
            attempt.setTenantId(tenantId);

            String correctAnswer = correctAnswerMap.get(attempt.getQuestionId());
            if (correctAnswer != null && attempt.getAnswerGiven() != null) {
                Integer correctOption = extractOptionNumber(correctAnswer);
                Integer givenOption   = extractOptionNumber(attempt.getAnswerGiven());
                if (correctOption != null && givenOption != null) {
                    attempt.setIsCorrect(correctOption.equals(givenOption));
                } else {
                    attempt.setIsCorrect(null);
                }
            } else {
                attempt.setIsCorrect(null);
            }

            // Calculate Marks using Section config
            Long sectionId = questionToSectionMap.get(attempt.getQuestionId());
            SectionResponse section = sectionId != null ? sectionMap.get(sectionId) : null;
            if (section != null) {
                double marksPerQuestion = section.getMarksPerQuestion() != null ? section.getMarksPerQuestion() : 0.0;
                double negativeMarks = section.getNegativeMarks() != null ? section.getNegativeMarks() : 0.0;

                if (Boolean.TRUE.equals(attempt.getIsCorrect())) {
                    attempt.setMarksObtained(BigDecimal.valueOf(marksPerQuestion));
                } else if (Boolean.FALSE.equals(attempt.getIsCorrect())) {
                    attempt.setMarksObtained(BigDecimal.valueOf(-Math.abs(negativeMarks)));
                } else {
                    attempt.setMarksObtained(BigDecimal.ZERO);
                }
            } else {
                attempt.setMarksObtained(BigDecimal.ZERO);
            }
        }

        // Build summary and stamp tenant
        StudentSubjectSummary summary = buildSummary(attempts, sectionMap, questionToSectionMap, timeTaken);
        summary.setTenantId(tenantId);

        studentSubjectSummaryService.save(summary);

        Long summaryId = summary.getId();
        if (summaryId != null) {
            for (StudentQuestionAttempt attempt : attempts) {
                attempt.setSummaryId(summaryId);
            }
        }

        repository.batchSave(attempts);
        return summary;
    }

    @Transactional
    public int save(StudentQuestionAttempt attempt, UserBean user) {
        if (user != null) {
            attempt.setTenantId(user.getTenantId());
        }
        StudentSubjectSummary summary = save(java.util.Collections.singletonList(attempt), null, user);
        return summary != null ? 1 : 0;
    }

    @Transactional(readOnly = true)
    public StudentQuestionAttempt findById(Long id) {
        return repository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<StudentQuestionAttempt> findAllByTenantId(Long tenantId) {
        return repository.findAllByTenantId(tenantId);
    }

    @Transactional(readOnly = true)
    public int deleteById(Long id) {
        return repository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<StudentQuestionAttempt> findByQuestionPaperIdAndStudentUserIdAndTenantId(Long questionPaperId, Long studentUserId, Long tenantId) {
        return repository.findByQuestionPaperIdAndStudentUserIdAndTenantId(questionPaperId, studentUserId, tenantId);
    }


    @Transactional(readOnly = true)
    public List<StudentQuestionAttempt> findBySummaryIdAndTenantId(Long summaryId, Long tenantId) {
        return repository.findBySummaryIdAndTenantId(summaryId, tenantId);
    }

    private StudentSubjectSummary buildSummary(List<StudentQuestionAttempt> attempts,
                                               java.util.Map<Long, SectionResponse> sectionMap,
                                               java.util.Map<Long, Long> questionToSectionMap,
                                               Integer timeTaken) {
        if (attempts == null || attempts.isEmpty()) return null;
        StudentSubjectSummary summary = new StudentSubjectSummary();
        summary.setQuestionPaperId(attempts.get(0).getQuestionPaperId());
        summary.setStudentUserId(attempts.get(0).getStudentUserId());
        summary.setSubjectId(attempts.get(0).getSubjectId());
        summary.setTotalQuestions(attempts.size());
        int correct = 0, wrong = 0, notAnswered = 0;
        java.math.BigDecimal marksObtained = java.math.BigDecimal.ZERO;
        java.math.BigDecimal maxMarks = java.math.BigDecimal.ZERO;
        LocalDateTime attemptedAt = null;
        for (StudentQuestionAttempt attempt : attempts) {
            if (Boolean.TRUE.equals(attempt.getIsCorrect())) correct++;
            else if (Boolean.FALSE.equals(attempt.getIsCorrect())) wrong++;
            if (attempt.getAnswerGiven() == null) notAnswered++;

            // Accumulate marks obtained by student
            if (attempt.getMarksObtained() != null) {
                marksObtained = marksObtained.add(attempt.getMarksObtained());
            }

            // Calculate exact maxMarks for the test based on marksPerQuestion
            Long sectionId = questionToSectionMap.get(attempt.getQuestionId());
            SectionResponse section = sectionId != null ? sectionMap.get(sectionId) : null;
            if (section != null && section.getMarksPerQuestion() != null) {
                maxMarks = maxMarks.add(BigDecimal.valueOf(section.getMarksPerQuestion()));
            }

            if (attempt.getAttemptedAt() != null) attemptedAt = attempt.getAttemptedAt();
        }
        summary.setCorrect(correct);
        summary.setWrong(wrong);
        summary.setNotAnswered(notAnswered);
        summary.setMarksObtained(marksObtained);
        summary.setMaxMarks(maxMarks);
        summary.setTimeTaken(timeTaken);
        summary.setAttemptedAt(attemptedAt);
        return summary;
    }

    /**
     * Extracts the numeric option number from any of the known answer formats:
     *
     *   "[2]"                -> 2   (client array format, stored in answerGiven)
     *   "{\"correctOption\": 2}" -> 2   (DB JSON object format, stored in correctAnswer)
     *   "option2"            -> 2   (DB legacy string format)
     *   "2"                  -> 2   (plain number)
     *
     * Returns null if parsing fails, so the caller can leave the attempt unevaluated.
     */
    private Integer extractOptionNumber(String value) {
        if (value == null) return null;
        String v = value.trim();
        try {
            // Format: "[2]" or "[2,3]" — client array, take first element
            if (v.startsWith("[") && v.endsWith("]")) {
                String inner = v.substring(1, v.length() - 1).trim();
                if (inner.contains(",")) inner = inner.split(",")[0].trim();
                return Integer.parseInt(inner);
            }
            // Format: {"correctOption": 2} — DB JSON object
            if (v.startsWith("{") && v.contains("correctOption")) {
                // Extract the number after the colon, e.g. ": 2}"
                String afterColon = v.substring(v.indexOf(':') + 1).replaceAll("[^0-9]", "").trim();
                return Integer.parseInt(afterColon);
            }
            // Format: "option2" — DB legacy string
            if (v.toLowerCase().startsWith("option")) {
                return Integer.parseInt(v.substring(6).trim());
            }
            // Format: plain number "2"
            return Integer.parseInt(v);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
