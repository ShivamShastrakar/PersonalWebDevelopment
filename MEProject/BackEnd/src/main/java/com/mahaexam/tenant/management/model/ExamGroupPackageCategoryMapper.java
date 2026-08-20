package com.mahaexam.tenant.management.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExamGroupPackageCategoryMapper {
    private Integer id;
    private Integer examGroupId;
    private Integer packageCategoryId;
}
