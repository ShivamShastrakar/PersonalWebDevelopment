package com.mahaexam.packagemanagment.bean;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class PackageSearchRequest {
    @Positive(message = "Class ID must be positive")
    private Integer classId;
    
    @Positive(message = "Service ID must be positive")
    private Integer serviceId;
    
    @Min(value = 0, message = "Page must be non-negative")
    private int page = 0;
    
    @Min(value = 1, message = "Size must be at least 1")
    private int size = 10;
    
    // Additional search fields
    private String packageName;
    private String packageType;
    private String packageMode;
    private String packageTargetStudents;
    private String targetYear;
    private Boolean isArchived;
    private Boolean isTestingPackage;
} 