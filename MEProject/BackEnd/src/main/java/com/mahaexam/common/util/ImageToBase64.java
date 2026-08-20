package com.mahaexam.common.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

public class ImageToBase64 {
    public static void main(String[] args) {
        // Path to your image file (replace with your actual file path)
        String imagePath = "D:\\Downloads\\package.png"; // e.g., "C:\\images\\example.png"

        try {
            // Read the image file into a byte array
            byte[] imageBytes = Files.readAllBytes(Paths.get(imagePath));

            // Encode the byte array to Base64 string
            String base64Encoded = Base64.getEncoder().encodeToString(imageBytes);

            // Output the Base64 string
            System.out.println("Base64 Encoded Image: " + base64Encoded);
        } catch (IOException e) {
            System.err.println("Error reading the image file: " + e.getMessage());
        }
    }
}

