package com.mahaexam.packagemanagment.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Builder
public class StudentPackageMapping {
    private Integer id;
    private Integer packageId;
    private Long studentId;
    private String subscriptionType; // Monthly, Quarterly, Yearly
    private Date nextInvoiceDate;
    private String status; // Active, Cancelled, Deleted
    private LocalDateTime createdDate;
    private LocalDateTime deletedAt;
    private Long createdBy;
}
