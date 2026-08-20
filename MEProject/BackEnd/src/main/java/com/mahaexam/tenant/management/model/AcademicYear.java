package com.mahaexam.tenant.management.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AcademicYear {
    private Long id;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private Long tenantId;
    private Integer boardId;
    private String boardName;
    private String deleted;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
