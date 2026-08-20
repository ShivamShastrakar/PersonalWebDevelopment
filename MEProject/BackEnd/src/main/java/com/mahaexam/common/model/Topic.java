package com.mahaexam.common.model;

import java.time.LocalDateTime;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Topic {
    private int topicId;
    private String topicName;
    private Integer chapterId; // Nullable
    private Integer subjectId; // Nullable
    private LocalDateTime createdAt;
    private Integer createdBy; // Nullable
    private Integer updatedBy; // Nullable
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
    private Integer classId; // Nullable
    private Integer boardId; // Nullable
    private Long tenantId; // New (or existing) nullable tenant_id (bigint unsigned)
    private String chapterName;
    private String subjectName;
    private String className;
    private String boardName;
}

