package com.mahaexam.common.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.mahaexam.common.bean.SuccessResponseBean;
import com.mahaexam.common.exception.ValidationException;
import com.mahaexam.common.model.SubjectBoardClassMapping;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mahaexam.common.bean.SubjectBean;
import com.mahaexam.common.bean.UserBean;
import com.mahaexam.common.model.Subject;
import com.mahaexam.common.service.SubjectService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/subjects")
@Tag(name = "Subject API", description = "Operations related to subjects")
public class SubjectController  extends BaseController {

    private static final Logger logger = LogManager.getLogger(SubjectController.class);


    @Autowired
    private SubjectService service;

    @Operation(summary = "Create a new subject")
    @PostMapping
    public ResponseEntity<SuccessResponseBean> create(@RequestBody SubjectBean bean) {
        try{
            UserBean userBean =getUser();
            Subject entity = new Subject();
            String subjectName = bean.getSubjectName();
            if (subjectName == null || subjectName.trim().length() < 3 || subjectName.trim().length() > 100) {
                throw new ValidationException("Subject Name must be between 3 and 100 characters.");
            }
            entity.setSubjectName(subjectName.toUpperCase());
            entity.setTenantId(userBean.getTenantId());
            entity.setDeleted(bean.getDeleted());
            if(bean.getBoardIds()==null || bean.getBoardIds().isEmpty() || bean.getClassIds()==null || bean.getClassIds().isEmpty()) {
                throw new ValidationException("Board and Class are required");
            }
            // Build SubjectBoardClassMapping list
            List<SubjectBoardClassMapping> subjectBoardClassMappings = new ArrayList<>();
            for (Integer boardId : bean.getBoardIds()) {
                for (Integer classId : bean.getClassIds()) {
                    for (String medium : bean.getMediums()) {
                        SubjectBoardClassMapping mapping = new SubjectBoardClassMapping();
                        mapping.setBoardId(boardId);
                        mapping.setClassId(classId);
                        mapping.setMedium(medium);
                        subjectBoardClassMappings.add(mapping);
                    }
                }
            }
            entity.setSubjectBoardClassMappings(subjectBoardClassMappings);
            service.createSubject(entity);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(SuccessResponseBean.builder().status("success").message(
                            "Subject name registered successfully" + entity.getSubjectId()).build());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw e;
        }
    }

    @Operation(summary = "Get all subjects")
    @GetMapping
    public List<Subject> getAll(@RequestParam(required = false) Integer boardId,
                                @RequestParam(required = false) Integer classId,
                                @RequestParam(required = false) String medium) {
        UserBean user = getUser();
        Long tenantId = Objects.nonNull(user)? user.getTenantId().longValue():getCurrentTenantId();

        // If boardId and classId are provided, use filtered methods
        if (boardId != null && classId != null) {
            if (medium != null && !medium.isBlank()) {
                return service.getSubjectsByBoardAndClassAndMedium(boardId, classId, medium, tenantId);
            }
            return service.getSubjectsByBoardAndClass(boardId, classId, tenantId);
        }

        // Default: return all subjects for tenant
        return service.getAllSubjectsByTenant(tenantId);
    }

    @Operation(summary = "Get a subject by ID")
    @GetMapping("/{id}")
    public Subject getById(@PathVariable int id) {
        return service.getSubjectById(id);
    }

    @Operation(summary = "Update a subject")
    @PutMapping("/{id}")
    public ResponseEntity<SuccessResponseBean> update(@PathVariable int id, @RequestBody SubjectBean bean) {
        try{
            UserBean userBean =getUser();
            Subject entity = new Subject();
            entity.setSubjectId(id);
            String subjectName = bean.getSubjectName();
            if (subjectName == null || subjectName.trim().length() < 3 || subjectName.trim().length() > 100) {
                throw new ValidationException("Subject Name must be between 3 and 100 characters.");
            }
            entity.setSubjectName(subjectName.toUpperCase());
            entity.setTenantId(userBean.getTenantId());
            entity.setDeleted(bean.getDeleted());
            if(bean.getBoardIds()==null || bean.getBoardIds().isEmpty() || bean.getClassIds()==null || bean.getClassIds().isEmpty()) {
                throw new ValidationException("Board and Class are required");
            }
            // Build SubjectBoardClassMapping list
            List<SubjectBoardClassMapping> subjectBoardClassMappings = new ArrayList<>();
            for (Integer boardId : bean.getBoardIds()) {
                for (Integer classId : bean.getClassIds()) {
                    for (String medium : bean.getMediums()) {
                        SubjectBoardClassMapping mapping = new SubjectBoardClassMapping();
                        mapping.setSubjectId(id);
                        mapping.setBoardId(boardId);
                        mapping.setClassId(classId);
                        mapping.setMedium(medium);
                        subjectBoardClassMappings.add(mapping);
                    }
                }
            }
            entity.setSubjectBoardClassMappings(subjectBoardClassMappings);
            service.updateSubject(entity);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(SuccessResponseBean.builder().status("success").message(
                            "Subject name updated successfully" + entity.getSubjectName()).build());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw e;
        }

    }

    @Operation(summary = "Delete a subject")
    @DeleteMapping("/{id}")
    public int delete(@PathVariable int id) {
        return service.deleteSubject(id);
    }
}