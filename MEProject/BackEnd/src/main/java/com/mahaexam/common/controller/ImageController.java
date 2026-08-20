package com.mahaexam.common.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mahaexam.common.config.S3Service;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/api/images")
public class ImageController {

	@Autowired
	private S3Service s3Service;

	@PostMapping("/upload")
	public ResponseEntity<String> upload(@RequestParam("bucket") String bucket,
			@RequestParam("file") MultipartFile file, @RequestParam("fileName") String fileName,
			@RequestParam(value = "folder", required = false) String folder) throws IOException {
		return ResponseEntity.ok(s3Service.uploadFile(bucket, folder, fileName, file));
	}

	@GetMapping("/{bucket}/{key}")
	public ResponseEntity<byte[]> get(@PathVariable String bucket, @PathVariable String key) {
		// For images in folders, use the full key in the URL, e.g.,
		// /api/images/myfolder/image.jpg
		byte[] image = s3Service.getFile(bucket, key);
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + key + "\"")
				.contentType(MediaType.IMAGE_JPEG) // Adjust based on image type
				.body(image);
	}

	@GetMapping("/base64/{bucket}/{key}")
	public ResponseEntity<String> getImageAsBase64(@PathVariable String bucket, @PathVariable String key) {
		String base64Image = s3Service.getImageAsBase64(bucket, key);
		return ResponseEntity.ok(base64Image);
	}

	// New: Decode a Base64 string and return byte[] length (for testing)
	@PostMapping("/decode")
	public ResponseEntity<String> decodeBase64(@RequestBody String base64String) {
		byte[] decodedBytes = s3Service.decodeFromBase64(base64String);
		return ResponseEntity.ok("Decoded byte[] length: " + decodedBytes.length);
	}
}
