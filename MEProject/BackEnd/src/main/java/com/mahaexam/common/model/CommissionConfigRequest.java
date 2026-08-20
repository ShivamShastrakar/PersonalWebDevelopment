package com.mahaexam.common.model;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommissionConfigRequest {
	private Long tenantId;
	private Long id; // for update
    private Long hierarchyLevelId;
    private String packageType; //Enum Package Type 'Premium, Super,Supreme'
    private String  commissionType; // PERCENTAGE, FIXED
    private Integer packageCategoryId;
    private Integer examGroupId;
    private Boolean active;
    private List<CommissionSlab> slabs;
    private LocalDateTime created_date;
    private Long  created_by;
    private LocalDateTime updated_at;   
    private Long updated_by; 
    

}
