package com.mahaexam.packagemanagment.controller;

import java.util.List;
import java.util.Optional;

import com.mahaexam.common.bean.SuccessResponseBean;
import com.mahaexam.common.controller.BoardController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
import com.mahaexam.packagemanagment.bean.FeeRuleBean;
import com.mahaexam.packagemanagment.service.FeeRuleService;

@RestController
@RequestMapping("/api/fee-rules")
public class FeeRuleController {

	private static final Logger logger = LogManager.getLogger(FeeRuleController.class);

	private final FeeRuleService feeRuleService;

	public FeeRuleController(FeeRuleService feeRuleService) {
		this.feeRuleService = feeRuleService;
	}

	@PostMapping
	public ResponseEntity<SuccessResponseBean> createFeeRule(@RequestBody FeeRuleBean feeRule) {
		try {
			FeeRuleBean created = feeRuleService.createFeeRule(feeRule);
			return ResponseEntity.status(HttpStatus.OK)
					.body(SuccessResponseBean.builder().status("success").message(
							"Fee Rule created successfully" + created.getId()).build());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<FeeRuleBean> getFeeRuleById(@PathVariable Integer id) {
		try {
			Optional<FeeRuleBean> feeRule = feeRuleService.getFeeRuleById(id);
			return feeRule.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
					.orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping
	public ResponseEntity<List<FeeRuleBean>> getAllFeeRules(@RequestBody UserBean user) {
		try {
			List<FeeRuleBean> feeRules = feeRuleService.getAllFeeRules(user);
			return new ResponseEntity<>(feeRules, HttpStatus.OK);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<SuccessResponseBean> updateFeeRule(@PathVariable Integer id,
															 @RequestBody FeeRuleBean feeRule) {
		try {
			FeeRuleBean updated = feeRuleService.updateFeeRule(id, feeRule);
			return ResponseEntity.status(HttpStatus.OK)
					.body(SuccessResponseBean.builder().status("success").message(
							"Fee Rule updated successfully" + updated.getId()).build());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteFeeRule(@PathVariable Integer id) {
		try {
			feeRuleService.deleteFeeRule(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
}