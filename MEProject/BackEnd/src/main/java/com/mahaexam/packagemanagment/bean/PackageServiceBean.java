package com.mahaexam.packagemanagment.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PackageServiceBean {
    private Integer id;
    private Integer packageId;
    private Integer serviceId;
    private Integer createdBy;
    private LocalDateTime createdDate;
}