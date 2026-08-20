package com.mahaexam.packagemanagment.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PackageCategoryModel {
    private Integer id;
    private String name;
    private String description;
    private Long tenantId;
    private LocalDateTime createdDate;
    private Integer createdBy;
}
