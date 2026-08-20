package com.mahaexam.common.controller;

import java.util.List;

import com.mahaexam.common.bean.SuccessResponseBean;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.mahaexam.common.bean.EducationSocietyBean;
import com.mahaexam.common.model.EducationSociety;
import com.mahaexam.common.service.EducationSocietyService;

@RestController
@RequestMapping("/api/education-societies")
public class EducationSocietyController {

    private static final Logger logger = LogManager.getLogger(EducationSocietyController.class);


    @Autowired
    private EducationSocietyService service;

    @PostMapping
    public ResponseEntity<SuccessResponseBean> create(@RequestBody EducationSocietyBean bean) {
        try{
            service.createSociety(bean);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(SuccessResponseBean.builder().status("success").message(
                            "Education Society name registered successfully" + bean.getSocietyName()).build());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw e;
        }
    }

    @GetMapping("/{id}")
    public EducationSociety getById(@PathVariable int id) {
        return service.getSocietyById(id);
    }

    @GetMapping
    public List<EducationSociety> getAll() {
        return service.getAllSocieties();
    }

    @PutMapping("/{id}")
    public ResponseEntity<SuccessResponseBean> update(@PathVariable int id, @RequestBody EducationSocietyBean bean) {
        try{
            service.updateSociety(id, bean);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(SuccessResponseBean.builder().status("success").message(
                            "Education Society name updated successfully" + bean.getSocietyName()).build());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw e;
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id){
        service.delete(id);
    }
}