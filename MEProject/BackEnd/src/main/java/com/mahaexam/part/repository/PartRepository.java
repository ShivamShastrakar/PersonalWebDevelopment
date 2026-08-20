package com.mahaexam.part.repository;

import java.util.List;

import com.mahaexam.common.bean.PartResponse;
import com.mahaexam.papertemplate.model.Part;

public interface PartRepository {
	
	List<Part> findByPaperTemplateId(Long templateId);
	
    Part save(Part part);
    
    Part update(Part part);
    
    void deleteByPaperTemplateId(Long templateId);
    
	Part findById(Long id);
	
	Long getPaperTemplateId(Long partId);
	
	Long insert(Part part, Long paperTemplateId);

	List<PartResponse> byPaperTemplateId(Long templateId);

	List<PartResponse> byPaperTemplateIds(List<Long> templateIds);

}
