package com.mahaexam.common.controller;

import java.util.List;

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

import com.mahaexam.common.bean.InstituteBean;
import com.mahaexam.common.bean.KeywordSearchRequest;
import com.mahaexam.common.bean.SuccessResponseBean;
import com.mahaexam.common.model.Institute;
import com.mahaexam.common.service.InstituteService;

@RestController
@RequestMapping("/api/institutes")
public class InstituteController {

	private static final Logger logger = LogManager.getLogger(InstituteController.class);

	@Autowired
	private InstituteService service;

	@PostMapping
	public ResponseEntity<SuccessResponseBean> create(@RequestBody InstituteBean bean) {
		try {
			int institute = service.createInstitute(bean);
			return ResponseEntity.status(HttpStatus.OK).body(SuccessResponseBean.builder().status("success")
					.message("Institute name registered successfully" + institute).build());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}

	@GetMapping("/{id}")
	public Institute getById(@PathVariable int id) {
		return service.getInstituteById(id);
	}

	@GetMapping
	public List<Institute> getAll() {
		return service.getAllInstitutes();
	}

	@PutMapping("/{id}")
	public ResponseEntity<SuccessResponseBean> update(@PathVariable int id, @RequestBody InstituteBean bean) {
		try {
			int institute = service.updateInstitute(id, bean);
			return ResponseEntity.status(HttpStatus.OK).body(SuccessResponseBean.builder().status("success")
					.message("Institute name updated successfully" + institute).build());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}

	@DeleteMapping("/{id}")
	public int delete(@PathVariable int id) {
		return service.deleteInstitute(id);
	}

	@PostMapping("/search")
	public InstituteBean searchInstitutes(@RequestBody KeywordSearchRequest request) {
		return service.searchByKeywords(request);
	}
}
