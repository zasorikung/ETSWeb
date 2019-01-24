package com.extosoft.web.util;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Utility {

	public static String getLang(HttpServletRequest req) {
		String lang = req.getParameter("lang");
		if ((!"E".equalsIgnoreCase(lang)) && (!"T".equalsIgnoreCase(lang))) {
			return "T";
		}
		return lang.toUpperCase();
	}
    
	public static Object nullToEmptyString(Object obj){
		if(obj==null){
			return "";
		}else{
			return obj;
		}
	}
	
//	public static String convertToPrefix668(String subrNumb) {
//		if (subrNumb != null && subrNumb.length() == 10 && subrNumb.charAt(0) == '0') {
//			try {
//				Long.parseLong(subrNumb);
//				return "66" + subrNumb.substring(1);
//			} catch (Exception e) {
//			}
//		}
//		return subrNumb;
//	}

//	public static String convertToPrefix08(String subrNumb) {
//		if (subrNumb != null && subrNumb.length() == 11 && "66".equals(subrNumb.substring(0, 2))) {
//			try {
//				Long.parseLong(subrNumb);
//				return "0" + subrNumb.substring(2);
//			} catch (Exception e) {
//			}
//		}
//		return subrNumb;
//	}
	
	public static String convertToPrefix66X(String subrNumb) {
		if (subrNumb != null && subrNumb.length() == 10 && subrNumb.charAt(0) == '0') {
			try {
				Long.parseLong(subrNumb);
				return "66" + subrNumb.substring(1);
			} catch (Exception e) {
			}
		}
		return subrNumb;
	}

	public static String convertToPrefix0X(String subrNumb) {
		if (subrNumb != null && subrNumb.length() == 11 && "66".equals(subrNumb.substring(0, 2))) {
			try {
				Long.parseLong(subrNumb);
				return "0" + subrNumb.substring(2);
			} catch (Exception e) {
			}
		}
		return subrNumb;
	}

	public static String listToString(Collection<? extends Object> list) {
		if (list == null) {
			return "null";
		}

		String output = "";
		int i = 0;
		for (Object obj : list) {
			output += (i == 0 ? "" : ", ") + obj.toString();
			i++;
		}
		return "[" + output + "]";
	}

	public static String arrayToString(Object[] objList) {
		if (objList == null) {
			return "null";
		}

		String output = "";
		int i = 0;
		for (Object obj : objList) {
			output += (i == 0 ? "" : ", ") + obj.toString();
			i++;
		}
		return "[" + output + "]";
	}

	public static Collection<String> trim(Collection<String> input) {
		if (input == null) {
			return null;
		}

		Collection<String> output = new ArrayList<String>();
		for (String obj : input) {
			output.add(obj == null ? null : asciiToUnicode(obj).trim());
		}

		return output;
	}

	public static String trim(String input) {
		if (input == null) {
			return null;
		}

		return asciiToUnicode(input).trim();
	}

	public static boolean isExceptionNestedInside(Throwable root, Class<? extends Object> c) {
		Throwable t = root.getCause();
		while (t != null) {
			if (c.isInstance(t)) {
				return true;
			}
			t = t.getCause();
		}

		return false;
	}

	public static boolean isStringNullOrBlank(String str) {
		if (str == null || "".equalsIgnoreCase(str)) {
			return true;
		} else {
			return false;
		}
	}

	public static String asciiToUnicode(String ascii) {
		StringBuffer unicode = new StringBuffer(ascii);
		int code;
		for (int i = 0; i < ascii.length(); i++) {
			code = (int) ascii.charAt(i);
			if ((0xA1 <= code) && (code <= 0xFB)) {
				unicode.setCharAt(i, (char) (code + 0xD60));
			}
		}
		return unicode.toString();
	}

	public static String unicodeToAscii(String unicode) {
		if (Utility.isStringNullOrBlank(unicode)) {
			return unicode;
		}

		StringBuffer ascii = new StringBuffer(unicode);
		for (int i = 0; i < unicode.length(); i++) {
			int code = unicode.charAt(i);
			if (3585 <= code && code <= 3675) {
				ascii.setCharAt(i, (char) (code - 3424));
			}
		}
		return ascii.toString();
	}

	public static String formatMoneyTwoDigits(Double money) {
		if (money != null) {
			try {
				NumberFormat numberFormat = DecimalFormat.getInstance();
				numberFormat.setMinimumFractionDigits(2);
				return numberFormat.format(money);
			} catch (Exception e) {
			}
		}
		return "";
	}

	public static String replace(String source, String pattern, String replace) {
		if (source != null) {
			final int len = pattern.length();
			StringBuffer sb = new StringBuffer();
			int found = -1;
			int start = 0;

			while ((found = source.indexOf(pattern, start)) != -1) {
				sb.append(source.substring(start, found));
				sb.append(replace);
				start = found + len;
			}
			sb.append(source.substring(start));
			return sb.toString();
		} else
			return "";
	}
	
	public static void writeHttpResponse(HttpServletRequest httpReqs, HttpServletResponse httpResp, String respText) throws IOException {
		if (!"html".equalsIgnoreCase(httpReqs.getParameter("type"))) {
			httpResp.setCharacterEncoding("UTF-8");
			httpResp.setContentType("text/html; charset=\"UTF-8\"");
			httpResp.setDateHeader("Expires", 0);
			httpResp.setHeader("Cache-Control", "no-cache");
			httpResp.setHeader("Pragma", "no-cache");
			httpResp.getWriter().write(respText);
			httpResp.getWriter().flush();
		} else {
			httpResp.setCharacterEncoding("UTF-8");
			httpResp.setDateHeader("Expires", 0);
			httpResp.setHeader("Cache-Control", "no-cache");
			httpResp.setHeader("Pragma", "no-cache");
			httpResp.setContentType("text/html; charset=\"UTF-8\"");
			httpResp.getWriter().write("<html><body><pre>\n");
			httpResp.getWriter().write(respText);
			httpResp.getWriter().write("\n</pre></body></html>");
			httpResp.getWriter().flush();
		}
	}

}
