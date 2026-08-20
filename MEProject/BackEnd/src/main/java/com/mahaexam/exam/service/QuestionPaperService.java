package com.mahaexam.exam.service;

import java.util.List;

import com.mahaexam.common.bean.QuestionPaperHierarchyResponseDTO;
import com.mahaexam.common.bean.QuestionPaperRequestDTO;
import com.mahaexam.common.bean.QuestionPaperResponseDTO;
import com.mahaexam.common.bean.UserBean;
import com.mahaexam.papertemplate.model.QuestionPaper;

public interface QuestionPaperService {

    QuestionPaperResponseDTO createQuestionPaper(QuestionPaperRequestDTO request, UserBean user);

    QuestionPaperResponseDTO getQuestionPaperById(Long questionPaperId, boolean hideAnswer, int page, int size);

    List<QuestionPaperResponseDTO> getAllQuestionPapers();

    List<QuestionPaperResponseDTO> getAllQuestionPapersByTenant(Long tenantId);

    /** Filters by boardId and/or classId (both optional) within the tenant. */
    List<QuestionPaperResponseDTO> getAllQuestionPapersByTenantAndFilter(Long tenantId, Long boardId, Integer classId);

    void updateQuestionPaperStatus(Long questionPaperId, Boolean active);

    QuestionPaperHierarchyResponseDTO getQuestionPaperHierarchyById(Long questionPaperId, boolean hideAnswer, UserBean user);

    /**
     * Returns all ACTIVE question papers available to the student through their
     * active packages, filtered by the student's own class and medium.
     */
    List<QuestionPaperResponseDTO> getExamsByStudentPackageAndMedium(Long studentUserId, Long tenantId);
    
    List<QuestionPaperResponseDTO> getQuestionPapersByClass(Integer classId);
}
