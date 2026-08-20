package com.mahaexam.common.bean;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class PaperTemplateResponse {
    private Long id;
    private String name;
    private String medium;
    private Long boardId;
    private String boardName;
    private Integer classId;
    private String className;
    private Integer totalDuration;
    private Integer totalMarks;
    private Integer numberOfParts;
    private String status;
    private Long tenantId;
    private List<String> instructions;

    private List<PartResponse> parts = new ArrayList<>();
}
