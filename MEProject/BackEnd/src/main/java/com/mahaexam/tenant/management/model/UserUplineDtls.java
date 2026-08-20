package com.mahaexam.tenant.management.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class UserUplineDtls {
    private Long id;
    private Long userLevel1Id;
    private Long userLevel2Id;
    private Long userLevel3Id;
    private Long userLevel4Id;
    private Long userLevel5Id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
