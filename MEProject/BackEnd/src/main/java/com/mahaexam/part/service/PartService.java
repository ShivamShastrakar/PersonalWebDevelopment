package com.mahaexam.part.service;

import java.util.List;

import com.mahaexam.papertemplate.model.Part;

public interface PartService {

    List<Part> getPartsByPaperTemplateId(Long paperTemplateId);

    Part savePart(Part part);

    void deletePartsByPaperTemplateId(Long paperTemplateId);
}

