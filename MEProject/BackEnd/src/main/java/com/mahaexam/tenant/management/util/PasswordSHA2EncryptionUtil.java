package com.mahaexam.tenant.management.util;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.mahaexam.common.exception.ServiceException;

public class PasswordSHA2EncryptionUtil {
	
	public static String hash(String password, String salt)
			throws ServiceException {
		try {
			MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
			String passWithSalt = password + salt;
			byte[] passBytes = passWithSalt.getBytes();
			byte[] passHash = sha256.digest(passBytes);
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < passHash.length; i++) {
				sb.append(Integer.toString((passHash[i] & 0xff) + 0x100, 16)
						.substring(1));
			}
			String generatedPassword = sb.toString();
			return generatedPassword;
		} catch (NoSuchAlgorithmException e) {
			throw new ServiceException(e);
		}
	}
	
	public static void main(String[] args) {
		String pwd ="0000";
		System.out.println("Hashed Pwd=>"+hash(pwd, ""));
	}
}
