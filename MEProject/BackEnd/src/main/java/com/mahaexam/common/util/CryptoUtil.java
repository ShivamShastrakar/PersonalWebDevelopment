package com.mahaexam.common.util;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.spec.KeySpec;
import java.util.Base64;

public class CryptoUtil {
    private static final String SECRET_KEY = "mySuperSecretKey123!@#";
    private static final String SALT = "aRandomSaltValue456$%^";
    private static final int KEY_LENGTH = 256;
    private static final int ITERATION_COUNT = 65536;

    public static String decrypt(String encryptedPassword) {
        try {
            // Use the static IV (same as frontend)
            byte[] iv = javax.xml.bind.DatatypeConverter.parseHexBinary("00000000000000000000000000000000");
            IvParameterSpec ivspec = new IvParameterSpec(iv);

            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            KeySpec spec = new PBEKeySpec(SECRET_KEY.toCharArray(), SALT.getBytes(), ITERATION_COUNT, KEY_LENGTH);
            SecretKey tmp = factory.generateSecret(spec);
            SecretKeySpec secretKeySpec = new SecretKeySpec(tmp.getEncoded(), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivspec);

            // The encryptedPassword is just the ciphertext (no IV prepended)
            byte[] cipherText = Base64.getDecoder().decode(encryptedPassword);
            byte[] decryptedText = cipher.doFinal(cipherText);

            return new String(decryptedText, "UTF-8");
        } catch (Exception e) {
            throw new RuntimeException("Decryption failed: " + e.getMessage());
        }
    }
}
