package com.mahaexam.exam.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mahaexam.papertemplate.model.Part;
import com.mahaexam.part.service.PartService;

@RestController
@RequestMapping("/api/parts")
public class PartController {

    private final PartService partService;

    public PartController(PartService partService) {
        this.partService = partService;
    }

    /* -------------------------------------------------
     * GET PARTS BY PAPER TEMPLATE
     * ------------------------------------------------- */
    @GetMapping("/template/{templateId}")
    public List<Part> getPartsByPaperTemplate(
            @PathVariable Long templateId) {

        return partService.getPartsByPaperTemplateId(templateId);
    }

    /* -------------------------------------------------
     * CREATE / UPDATE PART
     * ------------------------------------------------- */
    @PostMapping
    public Part savePart(@RequestBody Part part) {
        return partService.savePart(part);
    }

    /* -------------------------------------------------
     * DELETE ALL PARTS BY PAPER TEMPLATE
     * ------------------------------------------------- */
    @DeleteMapping("/template/{templateId}")
    public void deletePartsByPaperTemplate(
            @PathVariable Long templateId) {

        partService.deletePartsByPaperTemplateId(templateId);
    }
}
