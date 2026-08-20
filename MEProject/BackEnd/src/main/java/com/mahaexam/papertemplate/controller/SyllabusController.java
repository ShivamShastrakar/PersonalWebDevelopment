package com.mahaexam.papertemplate.controller;

import java.util.List;
import java.util.Objects;

import com.mahaexam.common.bean.UserBean;
import com.mahaexam.common.controller.BaseController;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mahaexam.papertemplate.bean.CreateSyllabusRequestDTO;
import com.mahaexam.papertemplate.bean.SyllabusResponseDTO;
import com.mahaexam.papertemplate.model.Syllabus;
import com.mahaexam.syllabus.service.SyllabusService;

@RestController
@RequestMapping("/api/syllabus")
public class SyllabusController extends BaseController {

    private final SyllabusService syllabusService;

    public SyllabusController(SyllabusService syllabusService) {
        this.syllabusService = syllabusService;
    }

    /* -------------------------------------------------
     * CREATE SYLLABUS (Header + Chapters)
     * ------------------------------------------------- */
    @PostMapping
    public SyllabusResponseDTO createSyllabus(
            @RequestBody CreateSyllabusRequestDTO request) {

        UserBean user = getUser();
        return syllabusService.createSyllabus(request, user.getTenantId(), user.getUserId());
    }

    /* -------------------------------------------------
     * GET SYLLABUS BY ID
     * ------------------------------------------------- */
    @GetMapping("/{id}")
    public Syllabus getSyllabusById(@PathVariable Long id) {
        return syllabusService.getSyllabusById(id);
    }

    /* -------------------------------------------------
     * UPDATE SYLLABUS
     * ------------------------------------------------- */
    @PutMapping("/{id}")
    public void updateSyllabus(
            @PathVariable Long id,
            @RequestBody Syllabus syllabus) {
        syllabus.setId(id);
        syllabusService.updateSyllabus(syllabus);
    }

    /* -------------------------------------------------
     * GET ALL SYLLABI (WITH OPTIONAL FILTERS)
     * ------------------------------------------------- */
    @GetMapping("/list")
    public List<Syllabus> getAllSyllabi(
            @RequestParam(required = false) String status) {
        if(Objects.isNull(status)){
            status = "ACTIVE";
        }
        UserBean user = getUser();
        return syllabusService.getAllSyllabi(user.getTenantId(), status);
    }
    
    @DeleteMapping("/{id}")
    public int deleteSyllabus(@PathVariable Integer id) {
    	return syllabusService.deleteSyllabus(id);
    }
    
    
}

