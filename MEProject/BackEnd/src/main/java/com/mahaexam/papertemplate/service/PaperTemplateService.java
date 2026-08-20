package com.mahaexam.papertemplate.service;

import java.util.List;

import com.mahaexam.common.bean.PaperTemplateResponse;
import com.mahaexam.common.bean.UserBean;
import com.mahaexam.papertemplate.bean.PaperTemplateRequest;
import com.mahaexam.papertemplate.model.PaperTemplate;

public interface PaperTemplateService {

    PaperTemplate findById(Long id);

    List<PaperTemplate> findAll(UserBean user);

    /** boardId and classId are both optional filters. */
    List<PaperTemplate> findAll(UserBean user, Long boardId, Integer classId);

    void delete(Long id);

	void update(Long id, PaperTemplateRequest request, UserBean user);

	void create(PaperTemplateRequest request, UserBean user);

	PaperTemplateResponse getFullHierarchy(Long templateId);

	List<PaperTemplateResponse> getFullHierarchyByIds(List<Long> templateIds);

    List<PaperTemplateResponse> getFullHierarchyByQuestionPaperId(Long questionPaperId);
}
