package com.mahaexam.packagemanagment.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubPackageMappingBean {
    private Integer id;
    private Integer parentPackageId;
    private Integer childPackageId;
}
