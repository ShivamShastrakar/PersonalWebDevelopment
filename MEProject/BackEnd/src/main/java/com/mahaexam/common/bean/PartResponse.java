package com.mahaexam.common.bean;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class PartResponse {
    private Long id;
    private String name;
    private Long subjectId;
    private Long paperTemplateId; // Add for batch grouping
    private Boolean displayName;
    private Boolean displaySubject;
    private Integer numberOfSections;
    private String subjectName; // Transient field for response

    private List<SectionResponse> sections = new ArrayList<>();
}