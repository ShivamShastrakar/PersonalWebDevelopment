package com.mahaexam.exam.service;

import java.util.List;

import com.mahaexam.common.model.Section;

public interface SectionService {

    List<Section> getSectionsByPart(Long partId);

    void saveSections(Long partId, List<Section> sections);
}

