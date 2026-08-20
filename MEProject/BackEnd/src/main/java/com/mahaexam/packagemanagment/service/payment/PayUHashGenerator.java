package com.mahaexam.packagemanagment.service.payment;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PayUHashGenerator {
    // Method to generate SHA-512 hash for PayU Payment Link API
    public static String generateHash(String merchantKey, String txnId, String amount, 
                                    String productInfo, String firstName, String email, 
                                    String udf1, String udf2, String udf3, String udf4, 
                                    String udf5, String salt) {
        // Construct the hash string as per PayU's specification
        String hashString = merchantKey + "|" + 
                           txnId + "|" + 
                           amount + "|" + 
                           productInfo + "|" + 
                           firstName + "|" + 
                           email + "|" + 
                           (udf1 != null ? udf1 : "") + "|" +
                           (udf2 != null ? udf2 : "") + "|" +
                           (udf3 != null ? udf3 : "") + "|" +
                           (udf4 != null ? udf4 : "") + "|" +
                           (udf5 != null ? udf5 : "") + "|" +
                           "||||||" + // 6 empty fields for udf6-udf10 and other reserved fields
                           salt;

        return calculateSHA512(hashString);
    }

    // Method to calculate SHA-512 hash
    private static String calculateSHA512(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte[] messageDigest = md.digest(input.getBytes());
            StringBuilder hexString = new StringBuilder();
            
            // Convert byte array to hexadecimal string
            for (byte b : messageDigest) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Example usage
    public static void main(String[] args) {
        // Replace these with your actual merchant key and salt from PayU dashboard
        String merchantKey = "your_merchant_key";
        String salt = "your_salt";
        
        // Transaction details
        String txnId = "TXN" + System.currentTimeMillis();
        String amount = "100.00";
        String productInfo = "Test Product";
        String firstName = "John";
        String email = "john@example.com";
        
        // User-defined fields (optional)
        String udf1 = "";
        String udf2 = "";
        String udf3 = "";
        String udf4 = "";
        String udf5 = "";

        String hash = generateHash(merchantKey, txnId, amount, productInfo, firstName, 
                                 email, udf1, udf2, udf3, udf4, udf5, salt);
        
        System.out.println("Generated Hash: " + hash);
        
        // Example of how to use this hash with the Payment Link API
        String apiEndpoint = "https://uatoneapi.payu.in/payment-links/";
        System.out.println("Use this hash in your POST request to: " + apiEndpoint);
    }
}