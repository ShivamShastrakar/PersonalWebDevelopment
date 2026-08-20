package com.mahaexam.tenant.management.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mahaexam.tenant.management.model.OfflinePaymentModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BatchUploadResponse {

    private Long batchId;
    private BigDecimal totalPackageAmount;
    private List<StudentDataLoadBean> validEntities;
    private List<StudentDataLoadBean> invalidEntities;

    private Integer year;
    private Long referenceId;
    private String referenceName;
    private Integer packageId;
    private String fileName;
    private String medium;

    private OfflinePaymentModel offlinePayment;
    private String status;
    private String displayStatus;
    private String paragraphId;
}
