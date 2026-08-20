package com.mahaexam.common.bean;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Component
@Schema(description = "Subject Bean")
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SubjectBean {
    @Schema(description = "Subject Name", example = "Mathematics")
    private String subjectName;

    @Schema(description = "Tenant ID", example = "1001")
    private Long tenantId;

    @Schema(description = "Deleted status", example = "0")
    private String deleted;

    List<SubjectBoardClassMappingBean> subjectBoardClassMappings;

    private List<Integer> boardIds;
    private List<Integer> classIds;
    private List<String> mediums;
}