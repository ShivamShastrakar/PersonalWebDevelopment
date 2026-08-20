package com.mahaexam.common.controller;

import java.util.List;
import java.util.Objects;

import com.mahaexam.common.bean.ClassesDeleteBean;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mahaexam.common.bean.ClassBean;
import com.mahaexam.common.bean.UserBean;
import com.mahaexam.common.model.ClassEntity;
import com.mahaexam.common.service.ClassService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/classes")
@Tag(name = "Class API", description = "Operations related to classes")
public class ClassController  extends BaseController {

    private static final Logger logger = LogManager.getLogger(ClassController.class);


    @Autowired
    private ClassService service;

    @Operation(summary = "Create a new class")
    @PostMapping
    public ResponseEntity<SuccessResponseBean> create(@RequestBody ClassBean bean) {
        try {
            UserBean userBean = getUser();
            ClassEntity entity = new ClassEntity();
            entity.setClassName(bean.getClassName());
            entity.setTenantId(userBean.getTenantId());
            entity.setDeleted(bean.getDeleted());
            entity.setIsExamGroupRequired(bean.getIsExamGroupRequired());
            service.createClass(entity);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(SuccessResponseBean.builder().status("success").message(
                            "Class name registered successfully").build());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw e;
        }
    }

    @Operation(summary = "Get all classes")
    @GetMapping
    public List<ClassEntity> getAll() {
        UserBean user = getUser();
        return service.getAllClassesByTenant(Objects.nonNull(user) && Objects.nonNull(user.getTenantId()) ? user.getTenantId() : getCurrentTenantId());
    }

    @Operation(summary = "Get a class by ID")
    @GetMapping("/{id}")
    public ClassEntity getById(@PathVariable int id) {
        return service.getClassById(id);
    }

    @Operation(summary = "Update a class")
    @PutMapping("/{id}")
    public ResponseEntity<SuccessResponseBean> update(@PathVariable int id, @RequestBody ClassBean bean) {
        try {
            ClassEntity entity = new ClassEntity();
            entity.setId(id);
            entity.setClassName(bean.getClassName());
            entity.setTenantId(bean.getTenantId());
            entity.setDeleted(bean.getDeleted());
            entity.setIsExamGroupRequired(bean.getIsExamGroupRequired());
            service.updateClass(entity);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(SuccessResponseBean.builder().status("success").message(
                            "Class name updated successfully").build());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw e;
        }
    }

    @Operation(summary = "Delete classes")
    @DeleteMapping
    public int[] delete(@RequestBody ClassesDeleteBean deleteBean) {
        return service.deleteClass(deleteBean);
    }

    @Operation(summary = "Delete a class by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessResponseBean> deleteById(@PathVariable int id) {
        try {
            ClassesDeleteBean deleteBean = new ClassesDeleteBean();
            deleteBean.setIdsToDelete(List.of(id));
            service.deleteClass(deleteBean);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(SuccessResponseBean.builder().status("success").message(
                            "Class deleted successfully").build());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw e;
        }
    }
}