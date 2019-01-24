package com.extosoft.web.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

public class ParameterValidator {
	private static final Pattern NUMERIC_PATTERN = Pattern.compile("^[0-9]+$");
	private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9_\\.\\-]+@[a-zA-Z0-9_\\.\\-]+\\.[a-zA-Z]{2,4}$");
	private static final Pattern SCRIPT_PATTERN = Pattern.compile("\\<script\\>.*\\<\\/script\\>", Pattern.CASE_INSENSITIVE);
	private static final Pattern NO_SPECIAL_CHAR_PATTERN = Pattern.compile(".*[!#$&*=\';/|\"<>].*");
	private static final Pattern ID_PATTERN = Pattern.compile(".*[!#$&*=\\-\';/|\"<>].*");
	public final static int TEXT_NUMBER_ONLY = 1;
	public final static int TEXT_NO_SPECIAL_CHARS = 2;
	public final static int TEXT_ID = 3;
	public final static int TEXT_ALL = 4;
	public final static int TEXT_INVOICE_INQUIRY_ADDRESS = 5;

	private static boolean isValidLength(String param, int minLength, int maxLength) {
		return (param.length() >= minLength && param.length() <= maxLength);
	}

	private static boolean isNumeric(String param) {
		return NUMERIC_PATTERN.matcher(param).matches();
	}

	private static boolean isOptionalParameter(String param, boolean optional) {
		
		return optional && (param == null || isValidLength(param, 0, 0));
	}

	private static int parseInt(String param) {
		return Integer.parseInt(param);
	}
	
	private static long parseLong(String param) {
		return Long.parseLong(param);
	}

	public static boolean isValidMobileNumber(String param, boolean optional) {
		param = (param != null) ? param.trim() : null;
		return isOptionalParameter(param, optional)
				|| (param != null && isValidLength(param, 10, 10) && isNumeric(param) && param.startsWith("0"));
	}

	public static boolean isValidMobileNumberWithPrefix(String param, boolean optional) {
		param = (param != null) ? param.trim() : null;
		return isOptionalParameter(param, optional)
				|| (param != null && isValidLength(param, 11, 11) && isNumeric(param) && param.startsWith("66"));
	}

	public static boolean isValidEmail(String param, boolean optional) {
		return isOptionalParameter(param, optional) || (param != null && EMAIL_PATTERN.matcher(param).matches());
	}

	public static boolean isValidPassword(String param, boolean optional) {
		param = (param != null) ? param.trim() : null;
		return isOptionalParameter(param, optional) || (param != null && isValidLength(param, 4, 4) && isNumeric(param));
	}
	
	public static boolean isValidPassword(String param, int length, boolean optional) {
		param = (param != null) ? param.trim() : null;
		return isOptionalParameter(param, optional) || (param != null && isValidLength(param, length, length) && isNumeric(param));
	}

	public static boolean isValidNumber(String param, boolean optional, int minValue, int maxValue) {
		param = (param != null) ? param.trim() : null;
		return isOptionalParameter(param, optional)
				|| (param != null && isNumeric(param) && param.length() <= String.valueOf(Integer.MAX_VALUE).length()
						&& parseInt(param) >= minValue && parseInt(param) <= maxValue);
	}
	
	public static boolean isValidFileSize(String param, boolean optional, long minValue, long maxValue) {
		param = (param != null) ? param.trim() : null;
		return isOptionalParameter(param, optional)
				|| (param != null && isNumeric(param) && param.length() <= String.valueOf(Long.MAX_VALUE).length()
						&& parseLong(param) >= minValue && parseLong(param) <= maxValue);
	}

	public static boolean isValidPostCode(String param, boolean optional) {
		param = (param != null) ? param.trim() : null;
		return isOptionalParameter(param, optional) || (param != null && isValidLength(param, 5, 5) && isNumeric(param));
	}

	public static boolean isValidDay(String param, boolean optional) {
		param = (param != null) ? param.trim() : null;
		return isOptionalParameter(param, optional)
				|| (param != null && isNumeric(param) && isValidLength(param, 1, 2) && parseInt(param) >= 1 && parseInt(param) <= 31);
	}

	public static boolean isValidMonth(String param, boolean optional) {
		param = (param != null) ? param.trim() : null;
		return isOptionalParameter(param, optional)
				|| (param != null && isNumeric(param) && isValidLength(param, 1, 2) && parseInt(param) >= 1 && parseInt(param) <= 12);
	}

	public static boolean isValidShortYear(String param, boolean optional) {
		param = (param != null) ? param.trim() : null;
		return isOptionalParameter(param, optional) || (param != null && isValidLength(param, 2, 2) && isNumeric(param));
	}

	public static boolean isValidYear(String param, boolean optional) {
		param = (param != null) ? param.trim() : null;
		return isOptionalParameter(param, optional) || (param != null && isValidLength(param, 4, 4) && isNumeric(param));
	}

//	public static boolean isValidFile(BufferedInputStream inputStream, String allowedExtensions, int maxSize) {
//		try {
//			if (maxSize > 0 && inputStream.available() > maxSize) {
//				return false;
//			}
//	
//			String[] allowedExtArray = allowedExtensions.split(",");
//			ArrayList allowedExtList = new ArrayList();
//			ArrayList mimeTypeList = new ArrayList(MimeUtil.getMimeTypes(inputStream));
//			for (int i = 0; i < allowedExtArray.length; i++) {
//				allowedExtList.add(allowedExtArray[i]);
//			}
//			for (int i = 0; i < mimeTypeList.size(); i++) {
//				String mimeType = mimeTypeList.get(i).toString();
//				if (mimeType.indexOf("/") >= 0) {
//					mimeType = mimeType.substring(0, mimeType.indexOf("/"));
//				}
//				if (allowedExtList.contains(mimeType)) {
//					return true;
//				}
//			}
//			return false;
//		} catch (Exception e) {
//			e.printStackTrace();
//			return false;
//		}
//	}

	public static boolean isValidIDCard(String param, boolean optional) {
		param = (param != null) ? param.trim() : null;
		return isOptionalParameter(param, optional)
				|| (param != null && isValidLength(param, 13, 13) && isNumeric(param) && isValidIDCardCheckSum(param));
	}

	private static boolean isValidIDCardCheckSum(String param) {
		if (param == null || param.length() < 13) {
			return false;
		}
		int sum = 0;
		for (int i = 0; i < 12; i++) {
			sum += Integer.parseInt("" + param.charAt(i)) * (13 - i);
		}
		return Integer.parseInt("" + param.charAt(12)) == ((11 - (sum % 11)) % 10);
	}

	public static boolean isValidValue(String param, boolean optional, List<String> allowedValues) {
		return isOptionalParameter(param, optional) || (param != null && allowedValues.contains(param));
	}

	public static boolean isValidValue(String param, boolean optional, String allowedValues, String separator) {
		if (isOptionalParameter(param, optional)) {
			return true;
		}
		String[] allowedValuesArray = allowedValues.split(separator);
		for (int i = 0; i < allowedValuesArray.length; i++) {
			if (allowedValuesArray[i].equals(param)) {
				return true;
			}
		}
		return false;
	}

	public static boolean isValidText(String param, boolean optional, int minLength, int maxLength, int textType) {
		return isOptionalParameter(param, optional)
				|| (param != null && param.length() >= minLength && param.length() <= maxLength && checkText(param, textType));
	}

	private static boolean checkText(String param, int textType) {
		switch (textType) {
		case TEXT_NUMBER_ONLY:
			return isNumeric(param);
		case TEXT_NO_SPECIAL_CHARS:
			return !NO_SPECIAL_CHAR_PATTERN.matcher(param).matches();
		case TEXT_ID:
			return !ID_PATTERN.matcher(param).matches();
		case TEXT_ALL:
			return true;
		case TEXT_INVOICE_INQUIRY_ADDRESS:
			for (int i = 0; i < param.length(); i++){
				if((int)param.charAt(i) == 13
					|| (int)param.charAt(i) == 124 || (int)param.charAt(i) == 92
					|| (int)param.charAt(i) == 62 || (int)param.charAt(i) == 60
					|| (int)param.charAt(i) == 43 || (int)param.charAt(i) == 61
					|| (int)param.charAt(i) == 125 || (int)param.charAt(i) == 123
					|| (int)param.charAt(i) == 91 || (int)param.charAt(i) == 93
					|| (int)param.charAt(i) == 40 || (int)param.charAt(i) == 41
					|| (int)param.charAt(i) == 94 || (int)param.charAt(i) == 36
					|| (int)param.charAt(i) == 35 || (int)param.charAt(i) == 39
					|| (int)param.charAt(i) == 34 || (int)param.charAt(i) == 37
					|| (int)param.charAt(i) == 63 || (int)param.charAt(i) == 3675
					|| (int)param.charAt(i) == 3663) {
				
					return false;
				}
			}
			return true;
		default:
			return false;
		}
	}

	public static boolean hasNoJavaScript(String param) {
		if (param == null) {
			return true;
		}
		return !SCRIPT_PATTERN.matcher(param).matches();
	}

	public static boolean isValidDate(String param, boolean optional, String dateFormat) {
		if (isOptionalParameter(param, optional)) {
			return true;
		}

		if (param == null) {
			return false;
		}

		try {
			new SimpleDateFormat(dateFormat, Locale.ENGLISH).parse(param);
			return true;
		} catch (ParseException e) {
			return false;
		}
	}

	public static boolean isValidDateOfMonth(String year, String month, String date) {
		int yearInt;
		int monthInt;
		int dateInt;

		try {
			yearInt = Integer.parseInt(year);
			monthInt = Integer.parseInt(month);
			dateInt = Integer.parseInt(date);
		} catch (Exception e) {
			return false;
		}

		if (dateInt < 1 || monthInt < 1) {
			return false;
		}

		if (monthInt == 1 || monthInt == 3 || monthInt == 5 || monthInt == 7 || monthInt == 8 || monthInt == 10 || monthInt == 12) {
			return (dateInt <= 31);
		} else if (monthInt == 4 || monthInt == 6 || monthInt == 9 || monthInt == 11) {
			return (dateInt <= 30);
		} else if (monthInt == 2) {
			if (yearInt % 4 != 0) {
				return (dateInt <= 28);
			} else if (yearInt % 100 != 0) {
				return (dateInt <= 29);
			} else if (yearInt % 400 != 0) {
				return (dateInt <= 28);
			} else if (yearInt % 400 == 0) {
				return (dateInt <= 29);
			}
		}

		return false;
	}
}
