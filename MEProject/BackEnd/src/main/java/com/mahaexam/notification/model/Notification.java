package com.mahaexam.notification.model;

import java.sql.Timestamp;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Notification {
    private Long id;
    private Long userId;
    private String title;
    private String message;
    private Timestamp createdAt;
    private boolean isRead;

}
