package com.mahaexam.exam.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.mahaexam.common.model.Section;
import com.mahaexam.section.repository.SectionRepository;
import com.mahaexam.papertemplate.repository.PaperTemplateRepository;
import com.mahaexam.part.repository.PartRepository;

@Service
@Transactional
public class SectionServiceImpl implements SectionService {

    private final SectionRepository sectionRepository;
    private final PaperTemplateRepository paperTemplateRepository;
    private final PartRepository partRepository;

    public SectionServiceImpl(
            SectionRepository sectionRepository,
            PaperTemplateRepository paperTemplateRepository,
            PartRepository partRepository) {
        this.sectionRepository = sectionRepository;
        this.paperTemplateRepository = paperTemplateRepository;
        this.partRepository = partRepository;
    }

    @Override
    public List<Section> getSectionsByPart(Long partId) {
        return sectionRepository.findByPartId(partId);
    }

    @Override
    public void saveSections(Long partId, List<Section> sections) {

        Long paperTemplateId = partRepository.getPaperTemplateId(partId);
        BigDecimal paperTotalMarks =
                paperTemplateRepository.getTotalMarks(paperTemplateId);

        BigDecimal existingMarks =
                sectionRepository.getTotalMarksByPart(partId);

        BigDecimal newMarks = BigDecimal.ZERO;

        for (Section section : sections) {

            // UI + backend validation
            if (sectionRepository.existsByPartIdAndName(partId, section.getName())) {
                throw new RuntimeException(
                        "Duplicate section name in same part: " + section.getName());
            }

            BigDecimal sectionTotal =
                    section.getMarksPerQuestion()
                           .multiply(BigDecimal.valueOf(section.getNumberOfQuestions()));

            section.setTotalMarks(sectionTotal);
            section.setPartId(partId);
            section.setStatus("ACTIVE");

            newMarks = newMarks.add(sectionTotal);
        }

        if (existingMarks.add(newMarks).compareTo(paperTotalMarks) > 0) {
            throw new RuntimeException(
                    "Total section marks exceed paper template total marks");
        }

        sections.forEach(sectionRepository::save);
    }
}

