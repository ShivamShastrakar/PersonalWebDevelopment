package com.mahaexam.packagemanagment.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ServiceBean {
    private Integer id;
    private String serviceName;
    private String serviceDetails;
    private String serviceType;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String options;
    private Integer updatedBy;
    private Long tenantId;
    
    private Integer packageId;
}
