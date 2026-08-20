package com.mahaexam.common.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Division {
    private Integer id;
    private String divisionName;
    private Integer divisionCode;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
    private String deleted;
    private Integer stateId;
    private Integer tenantId;
}
