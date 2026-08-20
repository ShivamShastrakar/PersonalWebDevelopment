package com.mahaexam.section.repository;

import java.math.BigDecimal;
import java.util.List;

import com.mahaexam.common.bean.SectionResponse;
import com.mahaexam.common.model.Section;

public interface SectionRepository {

    void save(Section section);

    List<Section> findByPartId(Long partId);

    boolean existsByPartIdAndName(Long partId, String name);

    BigDecimal getTotalMarksByPart(Long partId);

	void deleteByTemplateId(Long templateId);
	
	void insert(Section section, Long partId);

	List<SectionResponse> byPartId(Long id);

	List<SectionResponse> byPartIds(List<Long> partIds);

	void deleteByPartIds(List<Long> partIds);
}
