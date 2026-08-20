package com.mahaexam.tenant.management.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TempStudent {

    private Long id;
    private Long studentId;
    private Long batchId;
    private String lastName;
    private String firstName;
    private String middleName;
    private String adharNo;
    private String mobileNumber;
    private String email;
    private String className;
    private Integer classId;
    private String examGroup;
    private String courses;
    private String courseIds; // Comma-separated course IDs
    private Integer subjectGroupId;
    private Integer targetFinalExamYear;
    private Integer packageId;
    private Long referenceId;
    private String errorMessage;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String medium; // NEW FIELD



    // Helper method to get course IDs as List<Long>
    public List<Long> getCourseIdsList() {
        if (courseIds == null || courseIds.trim().isEmpty()) {
            return List.of();
        }
        return List.of(courseIds.split(","))
                .stream()
                .map(String::trim)
                .map(Long::valueOf)
                .toList();
    }

    // Helper method to set course IDs from List<Long>
    public void setCourseIdsList(List<Long> courseIdsList) {
        if (courseIdsList == null || courseIdsList.isEmpty()) {
            this.courseIds = null;
        } else {
            this.courseIds = courseIdsList.stream()
                    .map(String::valueOf)
                    .reduce((a, b) -> a + "," + b)
                    .orElse(null);
        }
    }
}
