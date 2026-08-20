package com.mahaexam.tenant.management.controller;

import java.util.List;
import java.util.Optional;

import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.mahaexam.common.bean.ErrorResponse;
import com.mahaexam.common.exception.ValidationException;
import com.mahaexam.tenant.management.model.Role;
import com.mahaexam.tenant.management.service.RoleService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/roles")
public class RoleController {
    private static final Logger logger = LoggerFactory.getLogger(RoleController.class);
    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping
    public ResponseEntity<?> createRole(@Valid @RequestBody Role role) {
        try {
            logger.info("Creating role: {}", role.getName());
            Role savedRole = roleService.save(role);
            return ResponseEntity.status(HttpStatus.OK).body(savedRole);
        } catch (IllegalArgumentException e) {
            logger.error("Error creating role: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
        }
    }

    @GetMapping("/{roleId}")
    public ResponseEntity<Role> getRoleById(@PathVariable Long roleId) {
        logger.info("Fetching role with ID: {}", roleId);
        Optional<Role> roleOpt = roleService.findById(roleId);
        return roleOpt.map(ResponseEntity::ok)
            .orElseThrow(() -> new ValidationException("Role not found with ID: " + roleId));
    }

    @GetMapping("/name/{roleName}")
    public ResponseEntity<Role> getRoleByName(@PathVariable String roleName) {
        logger.info("Fetching role with name: {}", roleName);
        Optional<Role> roleOpt = roleService.findByName(roleName);
        return roleOpt.map(ResponseEntity::ok)
            .orElseThrow(() -> new ValidationException("Role not found with name: " + roleName));
    }

    @PutMapping("/{roleId}")
    public ResponseEntity<?> updateRole(@PathVariable Long roleId, @Valid @RequestBody Role role) {
        try {
            logger.info("Updating role with ID: {}", roleId);
            role.setRoleId(roleId);
            Role updatedRole = roleService.update(role);
            return ResponseEntity.ok(updatedRole);
        } catch (IllegalArgumentException e) {
            logger.error("Error updating role: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
        } catch (Exception e) {
            logger.error("Error updating role with ID {}: {}", roleId, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error updating role"));
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getRolesByUserId(@PathVariable Long userId) {
        try {
            logger.info("Fetching roles for user ID: {}", userId);
            List<Role> roles = roleService.findRolesByUserId(userId);
            return ResponseEntity.ok(roles);
        } catch (Exception e) {
            logger.error("Error fetching roles for user ID {}: {}", userId, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error fetching roles"));
        }
    }
    
    @GetMapping
    public ResponseEntity<List<Role>> getAllRoles() {
        logger.info("Fetching all roles");
        List<Role> roles = roleService.findAll();
        return ResponseEntity.ok(roles);
    }

    @Operation(summary = "Delete a role")
    @DeleteMapping("/{roleId}")
    public void delete(@PathVariable Long roleId) {
        roleService.delete(roleId);
    }
}