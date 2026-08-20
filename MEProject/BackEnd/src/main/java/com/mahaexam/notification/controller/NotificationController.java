package com.mahaexam.notification.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mahaexam.common.controller.BaseController;
import com.mahaexam.notification.model.Notification;
import com.mahaexam.notification.service.NotificationService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController extends BaseController {
	private final NotificationService notificationService;

	public NotificationController(NotificationService notificationService) {
		this.notificationService = notificationService;
	}

	@GetMapping("/{userId}")
	public List<Notification> getNotifications(@PathVariable Long userId) {
		return notificationService.getForUser(userId);
	}

	@PostMapping("/mark-as-read/{notifId}")
	public void markAsRead(@PathVariable Long notifId) {
		notificationService.markAsRead(notifId);
	}

	@PostMapping("/")
	public void createNotification(@RequestBody Notification notification) {
		notificationService.save(notification);
	}
}
