package com.mahaexam.common.controller;

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

import com.mahaexam.common.bean.RuleTypeBean;
import com.mahaexam.common.bean.UserBean;
import com.mahaexam.common.service.RuleTypeService;

@RestController
@RequestMapping("/api/rule-types")
public class RuleTypeController {

	private final RuleTypeService ruleTypeService;

	public RuleTypeController(RuleTypeService ruleTypeService) {
		this.ruleTypeService = ruleTypeService;
	}

	@PostMapping
	public ResponseEntity<RuleTypeBean> createRuleType(@RequestBody RuleTypeBean ruleType) {
		RuleTypeBean created = ruleTypeService.createRuleType(ruleType);
		return new ResponseEntity<>(created, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<RuleTypeBean> getRuleTypeById(@PathVariable Integer id) {
		Optional<RuleTypeBean> ruleType = ruleTypeService.getRuleTypeById(id);
		return ruleType.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
				.orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
	}

	@GetMapping
	public ResponseEntity<List<RuleTypeBean>> getAllRuleTypes(@RequestBody UserBean user) {
		List<RuleTypeBean> ruleTypes = ruleTypeService.getAllRuleTypes(user);
		return new ResponseEntity<>(ruleTypes, HttpStatus.OK);

	}

	@PutMapping("/{id}")
	public ResponseEntity<RuleTypeBean> updateRuleType(@PathVariable Integer id, @RequestBody RuleTypeBean ruleType) {
		RuleTypeBean updated = ruleTypeService.updateRuleType(id, ruleType);
		return new ResponseEntity<>(updated, HttpStatus.OK);

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteRuleType(@PathVariable Integer id) {
		ruleTypeService.deleteRuleType(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
