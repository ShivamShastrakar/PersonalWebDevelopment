package com.mahaexam.common.controller;

import java.util.List;

import com.mahaexam.common.bean.SuccessResponseBean;
import org.apache.coyote.Response;
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

import com.mahaexam.common.bean.BulkSubjectGroupMappingBean;
import com.mahaexam.common.bean.SubjectGroupMappingBean;
import com.mahaexam.common.model.SubjectGroupMapping;
import com.mahaexam.common.service.SubjectGroupMappingService;

@RestController
@RequestMapping("/api/subject-group-mappings")
public class SubjectGroupMappingController extends BaseController {

	@Autowired
	private SubjectGroupMappingService service;

	@PostMapping("/create")
	public ResponseEntity<?> create(@RequestBody SubjectGroupMappingBean dto) {
		try{
			SubjectGroupMapping mapping = new SubjectGroupMapping();
			mapping.setGroupId(dto.getGroupId());
			mapping.setSubjectId(dto.getSubjectId());
			service.createMapping(mapping);
			return new ResponseEntity<>(mapping, HttpStatus.OK);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/bulk")
	public ResponseEntity<?> bulkCreate(@RequestBody BulkSubjectGroupMappingBean dto) {
		try{
			int BulkSubjectGroup = service.saveGroupWithSubjects(dto.getGroupId(), dto.getSubjectIds());
			return new ResponseEntity<>(BulkSubjectGroup, HttpStatus.OK);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping
	public List<SubjectGroupMapping> getAll() {
		return service.getAllMappings();
	}

	@GetMapping("/{id}")
	public SubjectGroupMapping getById(@PathVariable int id) {
		return service.getMappingById(id);
	}

	@DeleteMapping("/{id}")
	public int delete(@PathVariable int id) {
		return service.deleteMapping(id);
	}
}