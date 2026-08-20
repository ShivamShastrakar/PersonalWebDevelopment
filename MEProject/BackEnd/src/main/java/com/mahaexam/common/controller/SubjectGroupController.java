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
import org.springframework.web.bind.annotation.RestController;

import com.mahaexam.common.bean.SubjectGroupBean;
import com.mahaexam.common.bean.UserBean;
import com.mahaexam.common.model.SubjectGroup;
import com.mahaexam.common.service.SubjectGroupService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/subject-groups")
@Tag(name = "Subject Group API", description = "Operations related to subject groups")
public class SubjectGroupController  extends BaseController {

	private static final Logger logger = LogManager.getLogger(SubjectGroupController.class);

	@Autowired
	    private SubjectGroupService service;

	    @Operation(summary = "Create a new subject group")
	    @PostMapping
	    public ResponseEntity<SuccessResponseBean> create(@RequestBody SubjectGroupBean bean) {
			try{
				SubjectGroup entity = new SubjectGroup();
				entity.setGroupName(bean.getGroupName());
				entity.setDescription(bean.getDescription());
				entity.setTenantId(bean.getTenantId());
				entity.setDeleted(bean.getDeleted());
				service.createGroup(entity);
				return ResponseEntity.status(HttpStatus.OK)
						.body(SuccessResponseBean.builder().status("success").message(
								"Subject Group name registered successfully" + entity.getGroupId()).build());
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				throw e;
			}

	    }

	    @Operation(summary = "Get all subject groups")
	    @GetMapping
	    public List<SubjectGroup> getAll() {
	    	UserBean user = getUser();
	        return service.getAllGroupsByTenant(Objects.nonNull(user)? user.getTenantId().longValue():getCurrentTenantId());
	    }

	    @Operation(summary = "Get a subject group by ID")
	    @GetMapping("/{id}")
	    public SubjectGroup getById(@PathVariable int id) {
	        return service.getGroupById(id);
	    }

	    @Operation(summary = "Update a subject group")
	    @PutMapping("/{id}")
	    public ResponseEntity<SuccessResponseBean> update(@PathVariable int id, @RequestBody SubjectGroupBean bean) {
			try{
				SubjectGroup entity = new SubjectGroup();
				entity.setGroupId(id);
				entity.setGroupName(bean.getGroupName());
				entity.setDescription(bean.getDescription());
				entity.setTenantId(bean.getTenantId());
				entity.setDeleted(bean.getDeleted());
				service.updateGroup(entity);
				return ResponseEntity.status(HttpStatus.OK)
						.body(SuccessResponseBean.builder().status("success").message(
								"Subject Group name updated successfully" + entity.getGroupId()).build());
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				throw e;
			}

	    }

	    @Operation(summary = "Delete a subject group")
	    @DeleteMapping("/{id}")
	    public int delete(@PathVariable int id) {
	        return service.deleteGroup(id);
	    }
}