package com.mahaexam.tenant.management.util;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;
import java.util.UUID;

import org.apache.commons.lang3.RandomStringUtils;

public final class SessionIdentifierGenerator {
	private SecureRandom random = new SecureRandom();

	public String nextSessionId() {
		String randomStr = new BigInteger(130, random).toString(32);
		randomStr = randomStr + System.currentTimeMillis();
		return randomStr;
	}

	public static String getOTP(int length) {
		Random r = new Random();
		String otp = new String();
		for (int i = 0; i < length; i++) {
			otp += r.nextInt(10);
		}
		return otp;
	}

	private static final Random RANDOM = new SecureRandom();
	/** Length of password. @see #generateRandomPassword() */
	public static final int PASSWORD_LENGTH = 8;

	/**
	 * Generate a random String suitable for use as a temporary password.
	 *
	 * @return String suitable for use as a temporary password
	 * @since 2.4
	 */
	public static String generateRandomPassword() {
		// Pick from some letters that won't be easily mistaken for each
		// other. So, for example, omit o O and 0, 1 l and L.
		String letters = "abcdefghjkmnpqrstuvwxyzABCDEFGHJKMNPQRSTUVWXYZ23456789+@";

		String pw = "";
		for (int i = 0; i < PASSWORD_LENGTH; i++) {
			int index = (int) (RANDOM.nextDouble() * letters.length());
			pw += letters.substring(index, index + 1);
		}
		//TODO will remove on email implementation
//		pw="Pdms@123";
		return pw;
	}
	
	

	public static String generateUniqueId(Integer uniqueIdSize) {
	    return RandomStringUtils.randomAlphanumeric(uniqueIdSize);
	}
	public static String leftPadString(String strinToPaleftPad,Integer uniqueIdSize) {
		Integer prefixLength=uniqueIdSize-strinToPaleftPad.length();
		String finalString =generateUniqueId(prefixLength)+ strinToPaleftPad;
//	    return StringUtils.leftPad(strinToPaleftPad, uniqueIdSize,"x");
		return finalString;  
	}
//	String sendID = "AABB";
//	String output = String.format("%0"+(32-sendID.length())+"d%s", 0, sendID);
//	StringUtils.leftPad(sendID, 32 - sendID.length(), '0');
	public static String generateUniqueKeyUsingUUID() {
		// Static factory to retrieve a type 4 (pseudo randomly generated) UUID
		String crunchifyUUID = UUID.randomUUID().toString();
		return crunchifyUUID;
	}
	public static void main(String[] args) {
		for(int i=0;i<15;i++) {
			String uuId=generateUniqueKeyUsingUUID();
			System.out.println(uuId+"<==uuId==>"+uuId.length());
			String generateUniqueId=generateUniqueId(30);
			System.out.println(generateUniqueId+"<==generateUniqueId==>"+generateUniqueId.length());
		}
	}
}