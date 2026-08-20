package com.mahaexam.tenant.management.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UploadBatch {
    private Long batchId;
    private LocalDateTime uploadTime;
    private String entityType;
    private String status;
    private String originalFilePath;
    private String errorFilePath;
    private Long createdBy;
    private Long tenantId;
    private Long paymentTransactionId;

    private Integer totalCount;
    private Integer successCount;
    // Indicates whether there is an offline payment associated with this batch
    private Boolean isOffline;

    private String displayStatus;
}
