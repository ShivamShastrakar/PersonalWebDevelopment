package com.mahaexam.common.model;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommissionSlab {
	
   private Long id;
//   private Long commission_config_id;
   private Integer fromStudentCount;
   private Integer toStudentCount;
   private BigDecimal percentage;
   private BigDecimal amount;

}
