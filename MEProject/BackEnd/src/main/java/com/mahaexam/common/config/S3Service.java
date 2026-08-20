package com.mahaexam.common.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mahaexam.common.util.StringUtil;

import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.DeleteObjectResponse;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Response;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

@Service
public class S3Service {

	@Autowired
	private S3Client s3Client;

	// Method to check if a folder (prefix) exists
	public boolean doesFolderExist(String bucket, String folderPrefix) {
		// Ensure prefix ends with '/' for folder simulation
		String prefix = folderPrefix.endsWith("/") ? folderPrefix : folderPrefix + "/";
		// Limit to 1 for efficiency; we just need to know if any exist
		ListObjectsV2Request request = ListObjectsV2Request.builder().bucket(bucket).prefix(prefix).maxKeys(1).build();

		ListObjectsV2Response response = s3Client.listObjectsV2(request);
		return !response.contents().isEmpty() || !response.commonPrefixes().isEmpty();
	}

	// Upload (Store) Image to S3 with optional folder prefix
	public String uploadFile(String bucket, String folder, String fileName, MultipartFile file) throws IOException {
		// Construct the full key with folder prefix (e.g., "myfolder/image.jpg")
		String key = (folder != null && !folder.isEmpty()) ? folder + "/" + fileName : fileName;

		PutObjectRequest putObjectRequest = PutObjectRequest.builder().bucket(bucket).key(key)
				.contentType(file.getContentType()).contentLength(file.getSize()).build();

		PutObjectResponse response = s3Client.putObject(putObjectRequest,
				RequestBody.fromInputStream(file.getInputStream(), file.getSize()));
		return "Image uploaded successfully to: " + key;
	}
    public String uploadFile(String bucket, String folder, String fileName, byte[] fileBytes, String contentType) {
        String key = (folder != null && !folder.isEmpty()) ? folder + "/" + fileName : fileName;

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .contentType(contentType)
                .contentLength((long) fileBytes.length)
                .build();

        PutObjectResponse response = s3Client.putObject(putObjectRequest,
                RequestBody.fromBytes(fileBytes));

        return "File uploaded successfully to: " + key;
    }
    public String uploadFile(String bucket, String folder, String fileName, InputStream inputStream, long contentLength, String contentType) throws IOException {
        String key = (folder != null && !folder.isEmpty()) ? folder + "/" + fileName : fileName;

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .contentType(contentType)
                .contentLength(contentLength)
                .build();

        PutObjectResponse response = s3Client.putObject(putObjectRequest,
                RequestBody.fromInputStream(inputStream, contentLength));

        return "File uploaded successfully to: " + key;
    }

	// Decode Base64 and upload to S3, return the S3 URL
	public void uploadBase64ImageToS3(String bucket, String folder, String fileName, String base64Image) {
		if (base64Image == null || base64Image.isEmpty()) {
			throw new IllegalArgumentException("Base64 image string is required");
		}
		try {

			// Split header from data
			String[] parts = base64Image.split(",", 2);
			if (parts.length < 1) {
				throw new IllegalArgumentException("Invalid Image String Provided.");
			}
			// Decode Base64 to byte[]
			String imgHeader = parts[0];
			String imgString = parts[1];
			byte[] imageBytes = Base64.getDecoder().decode(imgString);

			// Generate a unique key (e.g., folder/uuid.jpg)
			String key = (folder != null ? folder + "/" : "") + fileName;

			PutObjectRequest putObjectRequest = PutObjectRequest.builder().bucket(bucket).key(key)
					.contentType(StringUtil.extractContentTypeFromBase64(imgHeader))
					.contentLength((long) imageBytes.length).build();

			s3Client.putObject(putObjectRequest, RequestBody.fromBytes(imageBytes));
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	// Retrieve (Get) Image from S3 (unchanged, but you can add folder logic if
	// needed)
	public byte[] getFile(String bucket, String keyName) {
		// If retrieving from a folder, pass the full key (e.g., "myfolder/image.jpg")
		GetObjectRequest getObjectRequest = GetObjectRequest.builder().bucket(bucket).key(keyName).build();

		ResponseBytes<GetObjectResponse> objectBytes = s3Client.getObjectAsBytes(getObjectRequest);
		return objectBytes.asByteArray();
	}

	// Example: Get image from S3 and return as Base64 string
	public String getImageAsBase64(String bucket, String key) {
		byte[] imageBytes = getFile(bucket, key);
		return encodeToBase64(imageBytes);
	}

	// New: Encode byte[] to Base64 string
	public String encodeToBase64(byte[] bytes) {
		if (bytes == null || bytes.length == 0) {
			throw new IllegalArgumentException("Byte array cannot be null or empty");
		}
		return Base64.getEncoder().encodeToString(bytes);
	}

	// New: Decode Base64 string to byte[]
	public byte[] decodeFromBase64(String base64String) {
		if (base64String == null || base64String.isEmpty()) {
			throw new IllegalArgumentException("Base64 string cannot be null or empty");
		}
		return Base64.getDecoder().decode(base64String);
	}

	// Delete file from S3
	public boolean deleteFile(String bucket, String key) {
		try {
			if (key == null || key.trim().isEmpty()) {
				throw new IllegalArgumentException("S3 key cannot be null or empty");
			}

			DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
					.bucket(bucket)
					.key(key)
					.build();

			DeleteObjectResponse response = s3Client.deleteObject(deleteObjectRequest);
			return true;
		} catch (Exception e) {
			// Log the error but return false instead of throwing
			System.err.println("Failed to delete file from S3: " + e.getMessage());
			return false;
		}
	}

}
