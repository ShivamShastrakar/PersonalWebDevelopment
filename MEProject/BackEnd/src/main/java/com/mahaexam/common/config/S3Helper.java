package com.mahaexam.common.config;

import com.mahaexam.tenant.management.service.StudentServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class S3Helper {
    private static final Logger logger = LoggerFactory.getLogger(S3Helper.class);
    private final S3Service s3Service;
    public S3Helper(S3Service s3Service) {
        this.s3Service = s3Service;
    }
    public void deleteExistingImage(String bucketName, String existingPhotoUrl) {
        // Delete previous image from S3 if exists
        if (existingPhotoUrl != null && !existingPhotoUrl.trim().isEmpty()) {
            try {
                // Assuming S3Service is available - need to inject it
                // Extract S3 key from existing URL (assuming it's stored as S3 key or full URL)
                String s3Key = existingPhotoUrl;
                if (existingPhotoUrl.startsWith("http")) {
                    // If it's a full URL, extract the key part
                    java.net.URI uri = java.net.URI.create(existingPhotoUrl);
                    s3Key = uri.getPath().startsWith("/") ? uri.getPath().substring(1) : uri.getPath();
                }

                // Get S3 bucket name from config

                s3Service.deleteFile(bucketName, s3Key);
                logger.info("Successfully deleted previous image from S3: {}", s3Key);
            } catch (Exception e) {
                // Log the error but don't fail the entire operation
                logger.warn("Failed to delete previous image from S3: {}", e.getMessage());
            }
        }
    }
}
