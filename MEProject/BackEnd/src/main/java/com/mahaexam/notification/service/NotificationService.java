package com.mahaexam.notification.service;

import java.util.List;

import com.mahaexam.notification.model.Notification;

public interface NotificationService {
    List<Notification> getForUser(Long userId);
    void markAsRead(Long notificationId);
    void save(Notification notification);
//    void sendNotification(Notification notification);
}
