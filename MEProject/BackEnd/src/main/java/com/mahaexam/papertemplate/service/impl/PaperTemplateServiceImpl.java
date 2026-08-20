package com.mahaexam.papertemplate.service.impl;

import com.mahaexam.common.bean.PaperTemplateResponse;
import com.mahaexam.common.bean.PartResponse;
import com.mahaexam.common.bean.SectionResponse;
import com.mahaexam.common.bean.UserBean;
import com.mahaexam.common.exception.ValidationException;
import com.mahaexam.common.model.Section;
import com.mahaexam.papertemplate.bean.PaperTemplateRequest;
import com.mahaexam.papertemplate.bean.PartRequest;
import com.mahaexam.papertemplate.model.PaperTemplate;
import com.mahaexam.papertemplate.model.Part;
import com.mahaexam.papertemplate.model.QuestionPaperTemplate;
import com.mahaexam.papertemplate.repository.PaperTemplateRepository;
import com.mahaexam.papertemplate.repository.QuestionPaperTemplateRepository;
import com.mahaexam.papertemplate.service.PaperTemplateService;
import com.mahaexam.part.repository.PartRepository;
import com.mahaexam.section.repository.SectionRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaperTemplateServiceImpl implements PaperTemplateService {

    private final PaperTemplateRepository paperTemplateRepo;
    private final ObjectMapper mapper = new ObjectMapper();
    private final PartRepository partRepo;
    private final SectionRepository sectionRepo;
    private final QuestionPaperTemplateRepository questionPaperTemplateRepository;

    // SAVE
    @Transactional
    @Override
    public void create(PaperTemplateRequest request, UserBean user) {

        PaperTemplate template = request.getPaperTemplate();

        // Set tenantId from user
        template.setTenantId(user.getTenantId());

        // Validate: Check if any template with the same name already exists for this tenant
        if (paperTemplateRepo.existsByNameAndTenantId(template.getName(), user.getTenantId())) {
            throw new IllegalArgumentException(
                    String.format("A paper template with the name '%s' already exists. Please use a different name.", template.getName())
            );
        }

        // Validate: Check if active template with same name, medium, boardId, classId and tenantId already exists
        boolean exists = paperTemplateRepo.existsByNameAndMediumAndBoardIdAndClassIdAndStatusAndTenantId(
                template.getName(),
                template.getMedium(),
                template.getBoardId(),
                template.getClassId(),
                "ACTIVE",
                user.getTenantId()
        );

        if (exists) {
            throw new IllegalArgumentException(
                    String.format("Active paper template already exists with name '%s', medium '%s', board Name %s, and class Name %s",
                            template.getName(), template.getMedium(), template.getBoardName(), template.getClassName())
            );
        }

        Long templateId = paperTemplateRepo.insert(template);

        for (PartRequest pr : request.getParts()) {
            Long partId = partRepo.insert(pr.getPart(), templateId);

            for (Section section : pr.getSections()) {
                sectionRepo.insert(section, partId);
            }
        }
    }

    // UPDATE (delete & reinsert strategy)
    @Override
    @Transactional
	public void update(Long templateId, PaperTemplateRequest request, UserBean user) {
    	
    	 PaperTemplate template = request.getPaperTemplate();

    	    // Set tenantId from user
    	    template.setTenantId(user.getTenantId());

    	    // Validate: Check if any OTHER active template with the same name exists for this tenant
    	    if (paperTemplateRepo.existsByNameAndTenantIdExcludingId(template.getName(), user.getTenantId(), templateId)) {
    	        throw new IllegalArgumentException(
    	            String.format("A paper template with the name '%s' already exists. Please use a different name.", template.getName())
    	        );
    	    }

    	    // Validate: Check if active template with same name, medium, boardId, classId, and tenantId already exists (excluding current)
    	    boolean exists = paperTemplateRepo.existsByNameAndMediumAndBoardIdAndClassIdAndStatusAndTenantIdExcludingId(
    	        template.getName(),
    	        template.getMedium(),
    	        template.getBoardId(),
    	        template.getClassId(),
    	        "ACTIVE",
    	        user.getTenantId(),
    	        templateId
    	    );

    	    if (exists) {
    	        throw new IllegalArgumentException(
    	            String.format("Active paper template already exists with name '%s', medium '%s', board Name %s, and class Name %s",
    	                template.getName(), template.getMedium(), template.getBoardName(), template.getClassName())
    	        );
    	    }

    	    // Update or recreate the template based on its status
    	    if (template.getStatus().equalsIgnoreCase("Draft")) {
    	        paperTemplateRepo.update(template, templateId);
    	    } else {
    	        List<Part> parts = partRepo.findByPaperTemplateId(templateId);
    	        if (!parts.isEmpty()) {
    	            paperTemplateRepo.updateStatus(templateId);
    	        }
    	        create(request, user);
    	    }
	}

	@Override
	public PaperTemplateResponse getFullHierarchy(Long templateId) {

		PaperTemplateResponse template = paperTemplateRepo.findTemplate(templateId);

		List<PartResponse> parts = partRepo.byPaperTemplateId(templateId);

		for (PartResponse part : parts) {
			List<SectionResponse> sections = sectionRepo.byPartId(part.getId());
			part.setSections(sections);
		}

		template.setParts(parts);
		return template;
	}

    @Override
    public List<PaperTemplateResponse> getFullHierarchyByIds(List<Long> templateIds) {
        if (templateIds == null || templateIds.isEmpty()) {
            return List.of();
        }

        // Optimized: Fetch all templates in one query
        List<PaperTemplateResponse> templates = paperTemplateRepo.findTemplatesByIds(templateIds);

        if (templates.isEmpty()) {
            return List.of();
        }

        // Optimized: Fetch all parts for these templates in one query
        List<PartResponse> allParts = partRepo.byPaperTemplateIds(templateIds);

        if (allParts.isEmpty()) {
            return templates; // Return templates with empty parts
        }

        // Extract all part IDs for fetching sections
        List<Long> partIds = allParts.stream()
                .map(PartResponse::getId)
                .toList();

        // Optimized: Fetch all sections for these parts in one query
        List<SectionResponse> allSections = sectionRepo.byPartIds(partIds);

        // Group sections by part ID
        var sectionsByPartId = allSections.stream()
                .collect(java.util.stream.Collectors.groupingBy(SectionResponse::getPartId));

        // Assign sections to their respective parts
        for (PartResponse part : allParts) {
            List<SectionResponse> sections = sectionsByPartId.getOrDefault(part.getId(), List.of());
            part.setSections(sections);
        }

        // Group parts by template ID
        var partsByTemplateId = allParts.stream()
                .collect(java.util.stream.Collectors.groupingBy(PartResponse::getPaperTemplateId));

        // Assign parts to their respective templates
        for (PaperTemplateResponse template : templates) {
            List<PartResponse> parts = partsByTemplateId.getOrDefault(template.getId(), List.of());
            template.setParts(parts);
        }

        return templates;
    }

    @Override
    public List<PaperTemplateResponse> getFullHierarchyByQuestionPaperId(Long questionPaperId) {
        // 1. Get all QuestionPaperTemplate mappings for this question paper
        List<QuestionPaperTemplate> qptList = questionPaperTemplateRepository.findByQuestionPaperId(questionPaperId);
        if (qptList == null || qptList.isEmpty()) {
            return List.of();
        }
        // 2. Extract all paperTemplateIds in order of sequence
        List<Long> templateIds = qptList.stream()
            .sorted(java.util.Comparator.comparing(QuestionPaperTemplate::getSequence))
            .map(QuestionPaperTemplate::getPaperTemplateId)
            .toList();
        // 3. Fetch full hierarchy for these templateIds
        List<PaperTemplateResponse> templates = getFullHierarchyByIds(templateIds);
        return templates;
    }

    @Override
    public PaperTemplate findById(Long id) {
        return paperTemplateRepo.findById(id);
    }

    @Override
	public List<PaperTemplate> findAll(UserBean user) {
		return paperTemplateRepo.findAll(user.getTenantId());
	}

    @Override
    public List<PaperTemplate> findAll(UserBean user, Long boardId, Integer classId) {
        return paperTemplateRepo.findAllByFilter(user.getTenantId(), boardId, classId);
    }


    @Override
    @Transactional
    public void delete(Long id) {
        // Validation: Check if template is referenced in question_paper_template
        if (paperTemplateRepo.isTemplateReferencedInQuestionPaper(id)) {
            throw new ValidationException("Cannot delete: This paper template is in use by a question paper and cannot be deleted.");
        }
        // 1. Delete all sections for this paper template using a JOIN (robust for all part counts)
        sectionRepo.deleteByTemplateId(id);
        // 2. Delete all parts for this paper template
        partRepo.deleteByPaperTemplateId(id);
        // 3. Delete the paper template
        paperTemplateRepo.deleteById(id);
    }


}
