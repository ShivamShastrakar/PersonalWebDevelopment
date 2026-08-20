package com.mahaexam.content.management.controller;

import com.mahaexam.common.bean.UserBean;
import com.mahaexam.common.config.S3Config;
import com.mahaexam.common.controller.BaseController;
import com.mahaexam.common.model.Config;
import com.mahaexam.common.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/content")
public class ContentController extends BaseController {

    @Autowired
    private S3Config  s3Config;

    @Autowired
    private ConfigService configService;

    private String bucketName;

    private String cloudFrontDomain;

    //TODO will modify as per content management
    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("File is empty");
        }
        UserBean userBean = getUser();
        Long tenantId = userBean.getTenantId();
        // Fetch configs with better error handling
        String bucketName = getConfigValue(ConfigService.STUDY_MATERIAL, "S3 Bucket Name Not Found");
        String cloudFrontDomain = getConfigValue(ConfigService.CLOUD_FRONT_URL, "CloudFront URL Not Found");

        // Optionally prefix key with tenantId for organization
        String key = tenantId + "/" + file.getOriginalFilename();

        PutObjectRequest putRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .contentType("video/mp4")
                .build();

        try {
            s3Config.s3Client().putObject(putRequest, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));
            String cloudFrontUrl = "https://" + cloudFrontDomain + "/" + key;
            return ResponseEntity.ok("File uploaded successfully. Access via: " + cloudFrontUrl);
        } catch (IOException e) {
            // Add logging here, e.g., log.error("Upload failed", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Upload failed: " + e.getMessage());
        } catch (Exception e) {  // Catch AWS-specific errors
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("S3 upload error: " + e.getMessage());
        }
    }

    // Helper method to simplify config fetching
    private String getConfigValue(String configName, String errorMessage) {
        Optional<Config> configOpt = configService.findByName(configName);
        return configOpt.orElseThrow(() -> new IllegalArgumentException(errorMessage + ": " + configName))
                .getValue();
    }

    //TODO will modify as per content management
    @GetMapping("/download/{key}")
    public ResponseEntity<InputStreamResource> downloadFile(@PathVariable String key,
                                                            @RequestHeader(value = HttpHeaders.RANGE, required = false) String rangeHeader) {
        UserBean userBean = getUser();
        key = "101/WhatsApp Video 2025-09-15 at 21.46.58.mp4";
        Optional<Config> configOpt = configService.findByName(ConfigService.STUDY_MATERIAL);
        Config config = configOpt.orElseThrow(() -> new IllegalArgumentException(
                "S3 Bucket Name Not Found : " + ConfigService.STUDY_MATERIAL));
        bucketName = config.getValue();

        configOpt = configService.findByName(ConfigService.CLOUD_FRONT_URL);
        config = configOpt.orElseThrow(() -> new IllegalArgumentException(
                "S3 Bucket Name Not Found : " + ConfigService.CLOUD_FRONT_URL));
        cloudFrontDomain  = config.getValue();
        Long tenantId = userBean.getTenantId();

        GetObjectRequest getRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .range(rangeHeader) // Supports HTTP range for streaming
                .build();

        ResponseInputStream<GetObjectResponse> response = s3Config.s3Client().getObject(getRequest);
        long contentLength = response.response().contentLength();

        return ResponseEntity.ok()
                .contentType(MediaType.valueOf("video/mp4"))
                .contentLength(contentLength)
                .header(HttpHeaders.ACCEPT_RANGES, "bytes") // Enables range requests for streaming
                .body(new InputStreamResource(response));
    }

}
