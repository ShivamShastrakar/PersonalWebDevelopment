package com.mahaexam.packagemanagment.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PackageServiceModel {
	private Integer id;
	private Integer packageId;
	private Integer serviceId;
	private LocalDateTime createdDate;
	private Integer createdBy;
}