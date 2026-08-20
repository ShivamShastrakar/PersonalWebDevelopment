package com.mahaexam.packagemanagment.bean;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PackageClassBean {
    private Integer id;
    private Integer packageId;
    private Integer classId;
}