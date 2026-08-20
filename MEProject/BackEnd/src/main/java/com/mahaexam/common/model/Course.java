package com.mahaexam.common.model;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Course {
    private int id;
    private String courseName;
    private String courseDetails;
    private Long tenantId;
    private Long updatedBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
    private String deleted;
    
    private Integer packageId;

    // List of class IDs associated with this course
    private List<Integer> classIds;
}