package com.mahaexam.common.model;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class Chapter {
	
	private Integer id;

    private String chapterName;

    private String unit;

    private LocalDateTime createdDate;

    private String status;

    private String examType;

    private Integer subjectId;

    private Integer instituteId;

    private String className;

    private LocalDateTime deletedAt;

    private LocalDateTime updatedAt;

    private Long tenantId; // New nullable tenant_id (bigint unsigned)

    private String boardName;

    private String subjectName;

    private List<Long> boaredIds;
    private List<Long> classIds;
    private Integer coveragePercentage;

}
