package com.mahaexam.common.util;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class StringUtil {
	private static final Map<String, String> MIME_TO_EXTENSION = new HashMap<>();

	static {
		MIME_TO_EXTENSION.put("image/jpeg", ".jpg");
		MIME_TO_EXTENSION.put("image/png", ".png");
		MIME_TO_EXTENSION.put("image/gif", ".gif");
		MIME_TO_EXTENSION.put("image/jpeg", ".jpeg");
		MIME_TO_EXTENSION.put("image/svg+xml", ".svg");
		MIME_TO_EXTENSION.put("image/webp", ".webp");
	}

	private static final Map<String, String> MIME_TYPES = new HashMap<>();
	static {
		MIME_TYPES.put("png", "image/png");
		MIME_TYPES.put("jpg", "image/jpeg");
		MIME_TYPES.put("jpeg", "image/jpeg");
		MIME_TYPES.put("gif", "image/gif");
		MIME_TYPES.put("svg", "image/svg+xml");
		MIME_TYPES.put("webp", "image/webp");
		// Add more as needed
	}

	/**
	 * Extracts the file extension from a Base64 data URI.
	 * 
	 * @param base64Data The Base64 string (e.g., data:image/jpeg;base64,...)
	 * @return The file extension (e.g., .jpg), or null if not found
	 * @throws IllegalArgumentException If input is invalid
	 */
	public static String extractExtensionFromBase64(String base64Data) {
		if (base64Data == null || !base64Data.startsWith("data:")) {
			throw new IllegalArgumentException("Input must be a valid data URI starting with 'data:'");
		}

		// Split header from data
		String[] parts = base64Data.split(",", 2);
		if (parts.length < 1) {
			return null;
		}

		// Extract MIME type from header (e.g., image/jpeg)
		String header = parts[0].substring("data:".length());
		String[] headerParams = header.split(";");
		String mimeType = headerParams[0].trim();

		// Get extension from map
		return MIME_TO_EXTENSION.getOrDefault(mimeType, null); // Return null or a default like ".bin" if unknown
	}

	public static String getMimeTypeByExtension(String extension) {

		String mimeType = MIME_TYPES.getOrDefault(extension, "image/octet-stream");

		return mimeType;
	}

	/**
	 * Extracts filename from a Base64 data URI string. If no filename is found,
	 * generates a unique one.
	 *
	 * @param base64Data       The Base64 string (e.g., data URI)
	 * @param defaultExtension Fallback file extension (e.g., ".jpg")
	 * @return The extracted or generated filename
	 */
	public static String extractFilenameFromBase64(String base64Data) {
		if (base64Data == null || base64Data.isEmpty()) {
			throw new IllegalArgumentException("Base64 data cannot be null or empty");
		}
		String fileExtension = extractExtensionFromBase64(base64Data);
		// Check if it's a data URI
		if (base64Data.startsWith("data:")) {
			// Split the header from the data (before the comma)
			String[] parts = base64Data.split(",", 2);
			if (parts.length < 2) {
				// No valid data; generate default
				return generateUniqueFilename(fileExtension);
			}

			// Parse the header (e.g., data:image/jpeg;filename=myimage.jpg;base64)
			String header = parts[0];
			String[] params = header.split(";");

			// Look for filename parameter
			for (String param : params) {
				if (param.startsWith("filename=")) {
					return param.substring("filename=".length()).trim();
				}
			}
		}

		// No filename found; generate a unique one
		return generateUniqueFilename(fileExtension);
	}

	// Helper to generate a unique filename
	private static String generateUniqueFilename(String extension) {
		return UUID.randomUUID().toString() + (extension.startsWith(".") ? extension : "." + extension);
	}

	/**
	 * Extracts the content type (MIME type) from a Base64 data URI.
	 *
	 * @param base64Data The Base64 string (e.g., data:image/jpeg;base64,...)
	 * @return The content type (e.g., image/jpeg), or null if not found
	 * @throws IllegalArgumentException If input is invalid
	 */
	public static String extractContentTypeFromBase64(String base64DataFirstPart) {
		if (base64DataFirstPart == null || !base64DataFirstPart.startsWith("data:")) {
			throw new IllegalArgumentException("Input must be a valid data URI starting with 'data:'");
		}

		// Extract MIME type from header (e.g., image/jpeg)
		String header = base64DataFirstPart.substring("data:".length());
		String[] headerParams = header.split(";");
		return headerParams[0].trim(); // Returns e.g., image/jpeg
	}

    @NotNull
    public static String getPayUInvoiceNumber(String invoicePrefix) {
        // Take last 12 digits of timestamp to ensure total length <= 16 chars
        String timestamp = String.valueOf(System.currentTimeMillis()).substring(1);
        // Ensure total length is 16 or less by truncating prefix if needed
        int maxPrefixLength = 4; // 16 - 12 (timestamp length)
        if (invoicePrefix.length() > maxPrefixLength) {
            invoicePrefix = invoicePrefix.substring(0, maxPrefixLength);
        }
        String payuTransactionId = invoicePrefix + timestamp;
        return payuTransactionId;
    }
}
