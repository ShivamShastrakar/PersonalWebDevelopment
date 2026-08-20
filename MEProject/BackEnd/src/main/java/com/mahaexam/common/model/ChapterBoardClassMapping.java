package com.mahaexam.common.model;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChapterBoardClassMapping {
    private int id;
    private Integer chapterId;
    private Integer classId;
    private Integer boardId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
    private String deleted;
    private String className; // optional joined value
    private String boardName; // optional joined value
    private String chapterName; // optional joined value
}
