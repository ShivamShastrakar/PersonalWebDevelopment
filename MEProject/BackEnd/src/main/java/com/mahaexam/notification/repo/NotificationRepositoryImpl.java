package com.mahaexam.notification.repo;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mahaexam.notification.model.Notification;

@Repository
public class NotificationRepositoryImpl implements NotificationRepository {
    private final JdbcTemplate jdbcTemplate;

    public NotificationRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Notification> findByUserId(Long userId) {
        String sql = "SELECT * FROM notifications WHERE user_id = ? ORDER BY created_at DESC LIMIT 15";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Notification n = Notification.builder().build();
            n.setId(rs.getLong("id"));
            n.setUserId(rs.getLong("user_id"));
            n.setTitle(rs.getString("title"));
            n.setMessage(rs.getString("message"));
            n.setCreatedAt(rs.getTimestamp("created_at"));
            n.setRead(rs.getBoolean("is_read"));
            return n;
        }, userId);
    }

    @Override
    public void markAsRead(Long notificationId) {
        String sql = "UPDATE notifications SET is_read = TRUE WHERE id = ?";
        jdbcTemplate.update(sql, notificationId);
    }

    @Override
    public void save(Notification notification) {
        String sql = "INSERT INTO notifications (user_id, title, message, created_at, is_read) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, 
            notification.getUserId(), 
            notification.getTitle(), 
            notification.getMessage(), 
            notification.getCreatedAt(), 
            notification.isRead()
        );
    }
}
