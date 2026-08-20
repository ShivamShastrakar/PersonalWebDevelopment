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
public class QuestionType {
    private int id;
    private Long tenantId;
    private String code;
    private String name;
    private String description;
    private LocalDateTime createdAt;
}

