package com.mahaexam.common.controller;

import java.util.List;
import java.util.Objects;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mahaexam.common.bean.CourseBean;
import com.mahaexam.common.bean.UserBean;
import com.mahaexam.common.model.Course;
import com.mahaexam.common.service.CourseService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/courses")
@Tag(name = "Course API", description = "Operations related to courses")
public class CourseController  extends BaseController {

    private static final Logger logger = LogManager.getLogger(CourseController.class);


    @Autowired
    private CourseService service;

    @Operation(summary = "Create a new course")
    @PostMapping
    public ResponseEntity<SuccessResponseBean> create(@RequestBody CourseBean bean) {
        try{
            UserBean user = getUser();
            Course entity = new Course();
            entity.setCourseName(bean.getCourseName());
            entity.setCourseDetails(bean.getCourseDetails());
            entity.setTenantId(user.getTenantId());
            entity.setUpdatedBy(user.getUserId());
            entity.setDeleted(bean.getDeleted());
            entity.setClassIds(bean.getClassIds()); // Set class IDs
            service.createCourse(entity,bean.getSubjectGroupIds());
            return ResponseEntity.status(HttpStatus.OK)
                    .body(SuccessResponseBean.builder().status("success").message(
                            "Course name registered successfully" + entity.getId()).build());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw e;
        }

    }

    @Operation(summary = "Get all courses")
    @GetMapping
    public List<Course> getAll(@RequestParam(value = "classId", required = false) Integer classId) {
        UserBean user = getUser();
        Long tenantId = Objects.nonNull(user) ? user.getTenantId().longValue() : getCurrentTenantId();
        if (classId != null) {
            return service.getAllCoursesByTenantAndClassId(tenantId, classId);
        } else {
            return service.getAllCoursesByTenant(tenantId);
        }
    }

    @Operation(summary = "Get a course by ID")
    @GetMapping("/{id}")
    public Course getById(@PathVariable int id) {
        return service.getCourseById(id);
    }

    @Operation(summary = "Update a course")
    @PutMapping("/{id}")
    public ResponseEntity<SuccessResponseBean> update(@PathVariable int id, @RequestBody CourseBean bean) {
        try{
            Course entity = new Course();
            entity.setId(id);
            entity.setCourseName(bean.getCourseName());
            entity.setCourseDetails(bean.getCourseDetails());
            entity.setTenantId(bean.getTenantId());
            entity.setUpdatedBy(bean.getUpdatedBy());
            entity.setDeleted(bean.getDeleted());
            entity.setClassIds(bean.getClassIds()); // Set class IDs
            service.updateCourse(entity);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(SuccessResponseBean.builder().status("success").message(
                            "Course name updated successfully" + entity.getId()).build());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw e;
        }

    }

    @Operation(summary = "Delete a course")
    @DeleteMapping("/{id}")
    public int delete(@PathVariable int id) {
        return service.deleteCourse(id);
    }
}