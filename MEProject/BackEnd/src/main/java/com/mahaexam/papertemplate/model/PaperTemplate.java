package com.mahaexam.papertemplate.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaperTemplate {

    private Long id;
    private String name;
    private String medium;
    private Long boardId;
    private Long classId;
    private Integer totalDuration;
    private Integer totalMarks;
    private String partDisplayName;
    private Integer numberOfParts;
    private List<String> instructions;
    private String status;
    private Long tenantId;
    private String boardName; // Transient field for response
    private String className; // Transient field for response
}

