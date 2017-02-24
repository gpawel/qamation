package org.qamation.utils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.security.MessageDigest;

public class StringUtils {
	static public int convertStringToInt(String str) {
		if (str.isEmpty() || str == null) throw new RuntimeException("Cannot convert empty or null string into an int number");
		return Integer.parseInt(str);
	}
	
	static public String[] convertStringToArray(String s) {
	    String[] arr = new String[s.length()];
	    for(int i = 0; i < s.length(); i++)
	    {
	        arr[i] = String.valueOf(s.charAt(i));
	    }
	    return arr;
	}
	
	static public String getStackTrace(Throwable e) {
		StringWriter sw = new StringWriter();
		e.printStackTrace(new PrintWriter(sw));
		String exceptionAsString = sw.toString();
		return exceptionAsString;
	}

	static public String[] convertCharSequenceToArray(CharSequence... keys) {
		String[] arr = new String[keys.length];
		for (int i=0; i < keys.length; i++) {
			arr[i]=String.valueOf(keys[i]);
		}
		return arr;
	}
	
	static public String readFileIntoString(String path) {
		try {
			Class cl = new StringUtils().getClass();
			InputStream is = cl.getClassLoader().getResourceAsStream(path);
			ByteArrayOutputStream result = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int length;
			while ((length = is.read(buffer)) != -1) {
				result.write(buffer, 0, length);
			}
			return result.toString("UTF-8");
			/*
			URL url = cl.getClassLoader().getResource(path);
			URI uri = url.toURI();
			Path p = Paths.get(uri);
			byte[] bytes = Files.readAllBytes(p);
			String result = new String(bytes);
			return result;
			*/
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Unable to read "+path+" into a string.",e);
		}
	}
	
	static public  String extractContentFromCurlyBruckets(String s) {
		if (s.startsWith("{") && s.endsWith("}")) {
			String result = removeFirstAndLastCharacters(s);
			return result;
		}
		return s;
	}
	
	static public  String extractContentFromQuotes(String s) {
		if (s.startsWith("\"") && s.endsWith("\"")) {
			String result = removeFirstAndLastCharacters(s);
			return result;
		}
		return s;
	}
	
	static public String removeFirstAndLastCharacters(String s) {
		String firstCharacterRemoved = s.substring(1);
		int length = firstCharacterRemoved.length();
		String lastCharacterRemoved = firstCharacterRemoved.substring(0,length-1);
		return lastCharacterRemoved;
	}
	
	static public boolean isPositiveInteger(String maybeNumeric) {		
		    return maybeNumeric != null && maybeNumeric.matches("[0-9]+");	
	}

	public static String getMD5(String input) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(input.getBytes());
			byte byteData[] = md.digest();

			//convert the byte to hex format method 2
			StringBuffer hexString = new StringBuffer();
			for (int i = 0; i < byteData.length; i++) {
				String hex = Integer.toHexString(0xff & byteData[i]);
				if (hex.length() == 1) hexString.append('0');
				hexString.append(hex);
			}
			//System.out.println("Digest(in hex format):: " + hexString.toString());
			return hexString.toString();
		}
		catch (Exception ex) {
			throw new RuntimeException("Unable to get MD5 from input string", ex);
		}

	}
	
	//static public String format(String input, String format)
}
