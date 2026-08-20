package com.mahaexam.packagemanagment.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
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
public class PackageCategoryBean {
    private Integer id;
    
    @NotBlank(message = "Package Category Name is required")
    private String name;
    
    @NotBlank(message = "Package Category Description is required")
    private String description;
    
    private Long tenantId;
    private LocalDateTime createdDate;
    private Integer createdBy;
}
