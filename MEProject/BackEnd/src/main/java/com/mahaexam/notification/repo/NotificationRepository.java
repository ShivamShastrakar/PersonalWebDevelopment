package com.mahaexam.notification.repo;

import java.util.List;

import com.mahaexam.notification.model.Notification;

public interface NotificationRepository {
    List<Notification> findByUserId(Long userId);
    void markAsRead(Long notificationId);
    void save(Notification notification);
}
