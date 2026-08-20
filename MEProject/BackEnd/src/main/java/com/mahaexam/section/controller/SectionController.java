package com.mahaexam.section.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mahaexam.common.model.Section;
import com.mahaexam.exam.service.SectionService;

@RestController
@RequestMapping("/api/parts/{partId}/sections")
public class SectionController {

    private final SectionService sectionService;

    public SectionController(SectionService sectionService) {
        this.sectionService = sectionService;
    }

    @GetMapping
    public List<Section> getSections(@PathVariable Long partId) {
        return sectionService.getSectionsByPart(partId);
    }

    @PostMapping
    public ResponseEntity<String> saveSections(
            @PathVariable Long partId,
            @RequestBody List<Section> sections) {

        sectionService.saveSections(partId, sections);
        return ResponseEntity.ok("Sections saved successfully");
    }
}

