package com.uautso.sovs.utils;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.text.ParseException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @author duma-vpn-development team
 * @date Mar 2, 2023
 * @version 1.0.0
 */
public final class CustomGeneratedData {

	private static final Random RANDOM = new SecureRandom();
	private static final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

	public static Long GenerateCertificateId() {

		Long curTime = System.currentTimeMillis();

		return curTime;
	}

	public static long getTimeStamp() {
		return new Date().getTime();
	}

	public static LocalDate stringToLocalDate(String formDate) {
		LocalDate convertedDate = LocalDate.parse(formDate);
		return convertedDate;
	}

	public static LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
		return dateToConvert.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	}

	public static LocalDateTime convertToLocalDateTimeViaInstant(Date dateToConvert) {
		return dateToConvert.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
	}

	public static LocalDateTime convertToLocalDateTimeViaMilisecond(Date dateToConvert) {
		return Instant.ofEpochMilli(dateToConvert.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
	}

	public static LocalDateTime getLocalDate(long valueToConvert) {
		Date currentDate = new Date(valueToConvert);
		return currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
	}

	public static LocalDate stringToDate(String dateToConvert) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		return LocalDate.parse(dateToConvert, formatter);
	}

	public static LocalDate stringToLocalDateFormat(String dateToConvert) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		return LocalDate.parse(dateToConvert, formatter);
	}

	public static LocalDate stringToDateFormat(String dateToConvert) throws ParseException {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("[dd-MM-yyyy][yyyy-MM-dd]");
		DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("dd-MM-yyyy");

		LocalDate receivedDate = LocalDate.parse(dateToConvert, formatter);
		String returnDateStr = receivedDate.format(formatter2);

		return LocalDate.parse(returnDateStr, formatter2);
	}

	public static List<?> convertObjectToList(Object obj) {
		List<?> list = new ArrayList<>();
		if (obj.getClass().isArray()) {
			list = Arrays.asList((Object[]) obj);
		} else if (obj instanceof Collection) {
			list = new ArrayList<>((Collection<?>) obj);
		}
		return list;
	}

	public static boolean isObjectHashMap(Object source) {

		try {
			HashMap<String, Object> result = (HashMap<String, Object>) source;
			return true;
		} catch (ClassCastException e) {
		}

		return false;

	}

	public static boolean isObjectString(Object source) {

		try {
			String result = (String) source;
			return true;
		} catch (ClassCastException e) {
		}

		return false;

	}

	public static String generatePassword(int length) {
		StringBuilder returnValue = new StringBuilder(length);
		for (int i = 0; i < length; i++) {
			returnValue.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
		}
		return new String(returnValue);
	}

	public static String harshMethod(String string) throws Exception {
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(string.getBytes());

		byte[] byteData = md.digest();

		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < byteData.length; i++) {
			sb.append(Integer.toString((byteData[i] & 0xFF) + 256, 16).substring(1));
		}
		return sb.toString();
	}

	public static LocalDateTime getLocalDateTimeFromString(String format, String dateTime) {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
		LocalDateTime newDateTime = LocalDateTime.parse(dateTime, formatter);

		return newDateTime;

	}

	public static LocalDateTime returnLocalDateTimeFromString(String dateTime){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss:SSS");
		LocalDateTime newDateTime = LocalDateTime.parse(dateTime, formatter);
		return newDateTime;
	}

	public static String getStringFromLDT(String format, LocalDateTime localDateTime) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
		return localDateTime.format(formatter);
	}

	public static String getStringFromLT(String format, LocalTime localDateTime) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
		return localDateTime.format(formatter);
	}

}
