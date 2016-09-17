package util;

import java.io.UnsupportedEncodingException;

/**
 * StringValidator v1.1
 * 
 * @author tosebro
 *
 */
public class StringValidator {

	public static final String CHARSET_SJIS = "SJIS";
	public static final String CHARSET_WINDOWS31J = "Windows-31j";
	public static final String CHARSET_EUCJP = "euc-jp";
	public static final String CHARSET_UTF8 = "UTF-8";

	public static String byteArrayToString(byte[] bytes) {
		if (bytes == null) {
			return null;
		}

		String text = "";

		try {
			if (StringValidator.isSJIS(bytes)) {
				text = new String(bytes, CHARSET_SJIS);
			} else if (StringValidator.isWindows31j(bytes)) {
				text = new String(bytes, CHARSET_WINDOWS31J);
			} else if (StringValidator.isEUC(bytes)) {
				text = new String(bytes, CHARSET_EUCJP);
			} else if (StringValidator.isUTF8(bytes)) {
				text = new String(bytes, CHARSET_UTF8);
			} else {
				text = new String(bytes, CHARSET_UTF8);
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

		return text;
	}

	private static boolean checkCharacterCode(String str, String encoding) {
		if (str == null) {
			return true;
		}

		try {
			byte[] bytes = str.getBytes(encoding);
			return str.equals(new String(bytes, encoding));
		} catch (UnsupportedEncodingException ex) {
			throw new RuntimeException("invalid encoding.", ex);
		}
	}

	public static boolean isWindows31j(String str) {
		return checkCharacterCode(str, CHARSET_WINDOWS31J);
	}

	public static boolean isSJIS(String str) {
		return checkCharacterCode(str, CHARSET_SJIS);
	}

	public static boolean isEUC(String str) {
		return checkCharacterCode(str, CHARSET_EUCJP);
	}

	public static boolean isUTF8(String str) {
		return checkCharacterCode(str, CHARSET_UTF8);
	}

	public static boolean isWindows31j(byte[] bytes) throws UnsupportedEncodingException {
		return isWindows31j(new String(bytes, CHARSET_WINDOWS31J));
	}

	public static boolean isSJIS(byte[] bytes) throws UnsupportedEncodingException {
		return isSJIS(new String(bytes, CHARSET_SJIS));
	}

	public static boolean isEUC(byte[] bytes) throws UnsupportedEncodingException {
		return isEUC(new String(bytes, CHARSET_EUCJP));
	}

	public static boolean isUTF8(byte[] bytes) throws UnsupportedEncodingException {
		return isUTF8(new String(bytes, CHARSET_UTF8));
	}
}