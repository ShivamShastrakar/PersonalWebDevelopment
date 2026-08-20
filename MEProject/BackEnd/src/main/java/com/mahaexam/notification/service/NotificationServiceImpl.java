package com.mahaexam.notification.service;

import java.util.List;

//import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.mahaexam.notification.model.Notification;
import com.mahaexam.notification.repo.NotificationRepository;

@Service
public class NotificationServiceImpl implements NotificationService {
	private final NotificationRepository notificationRepository;
//	private final SimpMessagingTemplate messagingTemplate;

	public NotificationServiceImpl(NotificationRepository notificationRepository) {
		this.notificationRepository = notificationRepository;
//		this.messagingTemplate = messagingTemplate;
	}

	@Override
	public List<Notification> getForUser(Long userId) {
		return notificationRepository.findByUserId(userId);
	}

	@Override
	public void markAsRead(Long notificationId) {
		notificationRepository.markAsRead(notificationId);
	}

	@Override
	public void save(Notification notification) {
		notificationRepository.save(notification);
		
	}

//	@Override
//	public void sendNotification(Notification notification) {
//		notificationRepository.save(notification);
//		messagingTemplate.convertAndSend("/topic/notifications/" + notification.getUserId(), notification);
//	}
}
