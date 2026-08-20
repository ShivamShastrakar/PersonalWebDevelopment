package com.mahaexam.packagemanagment.controller;

import java.util.List;
import java.util.Optional;

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

import com.mahaexam.common.bean.UserBean;
import com.mahaexam.packagemanagment.bean.PackageCourseBean;
import com.mahaexam.packagemanagment.service.PackageCourseService;

@RestController
@RequestMapping("/api/package-courses")
public class PackageCourseController {

	private final PackageCourseService mappingService;

	public PackageCourseController(PackageCourseService mappingService) {
		this.mappingService = mappingService;
	}

	@PostMapping
	public ResponseEntity<PackageCourseBean> createMapping(@RequestBody PackageCourseBean mapping) {
		try {
			PackageCourseBean created = mappingService.createMapping(mapping);
			return new ResponseEntity<>(created, HttpStatus.OK);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<PackageCourseBean> getMappingById(@PathVariable Integer id) {
		try {
			Optional<PackageCourseBean> mapping = mappingService.getMappingById(id);
			return mapping.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
					.orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping
	public ResponseEntity<List<PackageCourseBean>> getAllMappings(@RequestBody UserBean user) {
		try {
			List<PackageCourseBean> mappings = mappingService.getAllMappings(user);
			return new ResponseEntity<>(mappings, HttpStatus.OK);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<PackageCourseBean> updateMapping(@PathVariable Integer id,
			@RequestBody PackageCourseBean mapping) {
		try {
			PackageCourseBean updated = mappingService.updateMapping(id, mapping);
			return new ResponseEntity<>(updated, HttpStatus.OK);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteMapping(@PathVariable Integer id) {
		try {
			mappingService.deleteMapping(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
}