package com.mahaexam.common.controller;

import com.mahaexam.common.bean.SuccessResponseBean;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mahaexam.common.bean.CourseSubjectGroupMappingBean;
import com.mahaexam.common.service.CourseSubjectGroupMappingService;

@RestController
@RequestMapping("/api/course-subject-group-mappings")
public class CourseSubjectGroupMappingController extends BaseController {

	private static final Logger logger = LogManager.getLogger(CourseSubjectGroupMappingController.class);

	@Autowired
	private CourseSubjectGroupMappingService service;

	@PostMapping("/bulk")
	public ResponseEntity<SuccessResponseBean> bulkCreate(@RequestBody CourseSubjectGroupMappingBean dto) {
		try{
			int CourseSubjectGroup = service.saveMappingsForCourse(dto.getCourseId(), dto.getSubjectGroupIds());
			return ResponseEntity.status(HttpStatus.OK)
					.body(SuccessResponseBean.builder().status("success").message(
							"Course subject group mapping successfully" + CourseSubjectGroup).build());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}
}