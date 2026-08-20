package com.mahaexam.common.bean;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Component
@Schema(description = "Chapter Bean")
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChapterBean {
	private Integer id;

	private Integer userId;
	
    private String chapterName;

    private String unit;

    private LocalDateTime createdDate;

    private String status;

    private String examType;

    private Integer subjectId;
    


    private String className;

    private Integer instituteId;

    private LocalDateTime deletedAt;

    private LocalDateTime updatedAt;

    private Long tenantId; // New (or existing) nullable tenant_id (bigint unsigned)

    private List<Long> boaredIds;
    private List<Long> classIds;
}
