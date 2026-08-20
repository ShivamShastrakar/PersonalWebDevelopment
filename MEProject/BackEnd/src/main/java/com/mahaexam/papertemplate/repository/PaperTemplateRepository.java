package com.mahaexam.papertemplate.repository;

import java.math.BigDecimal;
import java.util.List;

import com.mahaexam.common.bean.PaperTemplateResponse;
import com.mahaexam.papertemplate.model.PaperTemplate;


public interface PaperTemplateRepository {

	    Long insert(PaperTemplate paperTemplate);
	   
	    void update(PaperTemplate paperTemplate, Long templateId);

	    PaperTemplate findById(Long id);

	    List<PaperTemplate> findAll(Long tenantId);

    /** boardId and classId are both optional; only non-null values are applied as filters. */
    List<PaperTemplate> findAllByFilter(Long tenantId, Long boardId, Integer classId);

    /** Checks if any active template with the same name already exists for this tenant. */
    boolean existsByNameAndTenantId(String name, Long tenantId);

    /** Same check but excludes the template being updated. */
    boolean existsByNameAndTenantIdExcludingId(String name, Long tenantId, Long excludeId);

	    void deleteById(Long id);
	    
	    BigDecimal getTotalMarks(Long paperTemplateId);

		PaperTemplateResponse findTemplate(Long templateId);

		List<PaperTemplateResponse> findTemplatesByIds(List<Long> templateIds);

		boolean existsByNameAndMediumAndBoardIdAndClassIdAndStatusAndTenantId(
				String name, String medium, Long boardId, Long classId, String status, Long tenantId);

		boolean existsByNameAndMediumAndBoardIdAndClassIdAndStatusAndTenantIdExcludingId(
				String name, String medium, Long boardId, Long classId, String status, Long tenantId, Long excludeId);
		
	    void updateStatus(Long id);

	    boolean isTemplateReferencedInQuestionPaper(Long templateId);

}
