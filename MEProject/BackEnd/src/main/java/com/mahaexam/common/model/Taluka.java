package com.mahaexam.common.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Taluka {
    private Integer id;
    private String talukaName;
    private Integer districtId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
    private String deleted;
    private Integer tenantId;

    private String districtName;
    private Integer divisionId;
    private String divisionName;
    private Integer stateId;
    private String stateName;


}
