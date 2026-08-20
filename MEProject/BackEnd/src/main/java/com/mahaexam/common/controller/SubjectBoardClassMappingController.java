package com.mahaexam.common.controller;

import java.util.List;
import java.util.stream.Collectors;

import com.mahaexam.common.bean.SuccessResponseBean;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mahaexam.common.bean.SubjectBoardClassMappingBean;
import com.mahaexam.common.model.SubjectBoardClassMapping;
import com.mahaexam.common.service.SubjectBoardClassMappingService;

@RestController
@RequestMapping("/api/subject-board-class-mappings")
public class SubjectBoardClassMappingController extends BaseController {

    private static final Logger logger = LogManager.getLogger(SubjectBoardClassMappingController.class);

    @Autowired
    private SubjectBoardClassMappingService service;

    @PostMapping("/create")
    public ResponseEntity<SuccessResponseBean> create(@RequestBody SubjectBoardClassMappingBean dto) {
        try{
            SubjectBoardClassMapping mapping = new SubjectBoardClassMapping();
            mapping.setSubjectId(dto.getSubjectId());
            mapping.setClassId(dto.getClassId());
            mapping.setBoardId(dto.getBoardId());
            mapping.setMedium(dto.getMedium());
            service.createMapping(mapping);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(SuccessResponseBean.builder().status("success").message(
                            "Subject Board Class registered successfully").build());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw e;
        }
    }

    @PostMapping("/bulk")
    public int bulkCreate(@RequestBody List<SubjectBoardClassMappingBean> dtos) {
        List<SubjectBoardClassMapping> mappings = dtos.stream().map(dto -> {
            SubjectBoardClassMapping m = new SubjectBoardClassMapping();
            m.setSubjectId(dto.getSubjectId());
            m.setClassId(dto.getClassId());
            m.setBoardId(dto.getBoardId());
            m.setMedium(dto.getMedium());
            return m;
        }).collect(Collectors.toList());
        return service.saveMappingsForSubjects(mappings);
    }

    @GetMapping
    public List<SubjectBoardClassMapping> getAll() {
        return service.getAllMappings();
    }

    @GetMapping("/{id}")
    public SubjectBoardClassMapping getById(@PathVariable int id) {
        return service.getMappingById(id).orElse(null);
    }

    @DeleteMapping("/{id}")
    public int delete(@PathVariable int id) {
        return service.deleteMapping(id);
    }
}