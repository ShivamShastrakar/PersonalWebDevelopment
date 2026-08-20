package com.mahaexam.tenant.management.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mahaexam.common.bean.SuccessResponseBean;
import com.mahaexam.common.controller.BaseController;
import com.mahaexam.tenant.management.bean.AssignRolesRequest;
import com.mahaexam.tenant.management.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/users")
public class UserRoleController extends BaseController {
	private static final Logger logger = LogManager.getLogger(UserRoleController.class);
	private final UserService userService;

	public UserRoleController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping("/assign-roles")
	public ResponseEntity<SuccessResponseBean> assignRolesToUser(@RequestBody @Valid AssignRolesRequest request) {
		try {
			userService.assignRoles(request.getUserId(), request.getRoleIds());

			return ResponseEntity.status(HttpStatus.OK).body(SuccessResponseBean.builder().status("success")
					.message("Roles assigned successfully to user ID: " + request.getUserId()).build());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}

    @PostMapping("/assign-roles/by-name")
    public ResponseEntity<SuccessResponseBean> assignRolesToUserByName(@RequestBody @Valid AssignRolesRequest request) {
        try {
            userService.assignRolesByName(request.getUserId(), request.getRoleNames());

            return ResponseEntity.status(HttpStatus.OK).body(SuccessResponseBean.builder().status("success")
                    .message("Roles assigned successfully to user ID: " + request.getUserId()).build());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw e;
        }
    }
}