package com.mahaexam.tenant.management.model;

import lombok.Data;

@Data
public class StudentSubjectGroup {
    private Long id;
    private Long studentId;
    private Integer subjectGroupId;
    
    private String groupName;
    private String description;
}
