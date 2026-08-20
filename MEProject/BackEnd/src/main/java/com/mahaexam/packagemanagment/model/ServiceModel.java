package com.mahaexam.packagemanagment.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServiceModel {
    private Integer id;
    private String serviceName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
    private String deleted; // Enum: 1, 0
    private Integer updatedBy;
    private String serviceDetails;
    private String serviceType;
    private String options;
    private Long tenantId;
    
    private Integer packageId;
}