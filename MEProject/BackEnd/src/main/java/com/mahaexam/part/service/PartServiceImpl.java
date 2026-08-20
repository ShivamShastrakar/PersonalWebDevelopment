package com.mahaexam.part.service;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mahaexam.papertemplate.model.Part;
import com.mahaexam.part.repository.PartRepository;

@Service
@Transactional
public class PartServiceImpl implements PartService {

    private final PartRepository partRepository;

    public PartServiceImpl(PartRepository partRepository) {
        this.partRepository = partRepository;
    }

    /* -------------------------------------------------
     * GET PARTS BY TEMPLATE
     * ------------------------------------------------- */
    @Override
    @Transactional(readOnly = true)
    public List<Part> getPartsByPaperTemplateId(Long paperTemplateId) {
        return partRepository.findByPaperTemplateId(paperTemplateId);
    }

    /* -------------------------------------------------
     * SAVE PART
     * ------------------------------------------------- */
    @Override
    public Part savePart(Part part) {
    	if(Objects.nonNull(part.getId())) {
    		partRepository.update(part);
    	}
        return partRepository.save(part);
    }

    /* -------------------------------------------------
     * DELETE PARTS BY TEMPLATE
     * ------------------------------------------------- */
    @Override
    public void deletePartsByPaperTemplateId(Long paperTemplateId) {
        partRepository.deleteByPaperTemplateId(paperTemplateId);
    }
}
