package com.mahaexam.common.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardSubjectQuestionTypeMapping {
    private int id;
    private Long tenantId;
    private int boardId;
    private int subjectId;
    private int questionTypeId;
    private Integer createdBy;
    private LocalDateTime createdAt;
    private Integer updatedBy;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
    private String deleted;

    // joined fields for display
    private String boardName;
    private String subjectName;
    private String questionTypeCode;
    private String questionTypeName;
}

