package com.mahaexam.tenant.management.bean;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AssignRolesRequest {
	@NotNull(message = "User ID cannot be null")
    private Long userId;


    private List<Long> roleIds;

    private List<String> roleNames;
}
