package com.extosoft.web.util;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;

public class ETSConstant {
	
	// Configuration file for application setting
	public static final String APP_CONFIGURATION_FILE = getUserHome()+"/conf/ETS/ETSConfig.properties";
	// Configuration file for log setting
	public static final String LOG_CONFIGURATION_FILE = getUserHome()+"/conf/ETS/ETSLog4j.properties";
	
	//Logger - SOAP Message -- Debug
	public static final String LOGGER_NAME_EST_DEBUG = "ETSDebug";
	public static final String LOGGER_NAME_EST_TRNS = "ETSTrns";
	// Logger - SOAP Message -- Debug
	public static DecimalFormat creditFormat = new DecimalFormat("#,##0");
    // Log Message Type
    public static String INPUT = "INPUT";
    public static String OUTPUT = "OUTPUT";
    public static String PROC = "PROC";
	
    public static String ERROR_MESSAGE_USERNAME_NOT_FOUND = "ERROR_MESSAGE_USERNAME_NOT_FOUND";
    public static String ERROR_MESSAGE_USERNAME_OR_PASSWORD_INVALID = "ERROR_MESSAGE_USERNAME_OR_PASSWORD_INVALID";
    
    public static String ERROR_MESSAGE_PLEASE_ENTER_USERNAME = "ERROR_MESSAGE_PLEASE_ENTER_USERNAME";
    public static String ERROR_MESSAGE_PLEASE_ENTER_PASSWORD = "ERROR_MESSAGE_PLEASE_ENTER_PASSWORD";
    public static String ERROR_MESSAGE_PASSWORD_NOT_MATCH_REPEAT_PASSWORD = "ERROR_MESSAGE_PASSWORD_NOT_MATCH_REPEAT_PASSWORD";
    public static String ERROR_MESSAGE_EMAIL_ADDRESS_NOT_MATCH_REPEAT_EMAIL_ADDRESS = "ERROR_MESSAGE_EMAIL_ADDRESS_NOT_MATCH_REPEAT_EMAIL_ADDRESS";
    public static String ERROR_MESSAGE_INVALID_FORMAT_EMAIL_ADDRESS = "ERROR_MESSAGE_INVALID_FORMAT_EMAIL_ADDRESS";
    public static String ERROR_MESSAGE_ACCEPT_THE_TERMS = "ERROR_MESSAGE_ACCEPT_THE_TERMS";
    public static String ERROR_MESSAGE_USERNAME_ALREADY_EXISTS = "ERROR_MESSAGE_USERNAME_ALREADY_EXISTS";
    
    public static String ERROR_MESSAGE_REGISTER_SUCCESSFULLY = "ERROR_MESSAGE_REGISTER_SUCCESSFULLY";
    public static String ERROR_MESSAGE_REGISTER_FAIL = "ERROR_MESSAGE_REGISTER_FAIL";
    public static String ERROR_MESSAGE_ADD_STUDENT_SUCCESSFULLY="ERROR_MESSAGE_ADD_STUDENT_SUCCESSFULLY";
    public static String ERROR_MESSAGE_ADD_STUDENT_FAIL="ERROR_MESSAGE_ADD_STUDENT_FAIL";
    public static String ERROR_MESSAGE_EDIT_STUDENT_SUCCESSFULLY="ERROR_MESSAGE_EDIT_STUDENT_SUCCESSFULLY";
    public static String ERROR_MESSAGE_EDIT_STUDENT_FAIL="ERROR_MESSAGE_EDIT_STUDENT_FAIL";
    public static String ERROR_MESSAGE_DELETE_STUDENT_SUCCESSFULLY = "ERROR_MESSAGE_DELETE_STUDENT_SUCCESSFULLY";
    public static String ERROR_MESSAGE_DELETE_STUDENT_FAIL = "ERROR_MESSAGE_DELETE_STUDENT_FAIL";
    
    public static String ERROR_MESSAGE_PLEASE_ENTER_FULL_NAME = "ERROR_MESSAGE_PLEASE_ENTER_FULL_NAME";
    public static String ERROR_MESSAGE_PLEASE_ENTER_ADDRESS = "ERROR_MESSAGE_PLEASE_ENTER_ADDRESS";
    public static String ERROR_MESSAGE_PLEASE_ENTER_MOBILE_NO = "ERROR_MESSAGE_PLEASE_ENTER_MOBILE_NO";
    public static String ERROR_MESSAGE_PLEASE_ENTER_DATE_OF_BIRTH = "ERROR_MESSAGE_PLEASE_ENTER_DATE_OF_BIRTH";
    public static String ERROR_MESSAGE_PLEASE_ENTER_POSTCODE = "ERROR_MESSAGE_PLEASE_ENTER_POSTCODE";
    public static String ERROR_MESSAGE_PLEASE_ENTER_DEPARTMENT = "ERROR_MESSAGE_PLEASE_ENTER_DEPARTMENT";
    public static String ERROR_MESSAGE_PLEASE_ENTER_AGE = "ERROR_MESSAGE_PLEASE_ENTER_AGE";
    public static String ERROR_MESSAGE_PLEASE_ENTER_DESCRIPTION = "ERROR_MESSAGE_PLEASE_ENTER_DESCRIPTION";
    public static String ERROR_MESSAGE_PLEASE_SELECT_GENDER = "ERROR_MESSAGE_PLEASE_SELECT_GENDER";
    public static String ERROR_MESSAGE_PLEASE_SELECT_COUNTRY = "ERROR_MESSAGE_PLEASE_SELECT_COUNTRY";
    public static String ERROR_MESSAGE_PLEASE_SELECT_CITY = "ERROR_MESSAGE_PLEASE_SELECT_CITY";
    public static String ERROR_MESSAGE_PLEASE_ENTER_WEBSITE_URL = "ERROR_MESSAGE_PLEASE_ENTER_WEBSITE_URL";
    public static String ERROR_MESSAGE_PLEASE_ENTER_EMAIL = "ERROR_MESSAGE_PLEASE_ENTER_EMAIL";
    public static String ERROR_MESSAGE_PLEASE_ENTER_PHONE = "ERROR_MESSAGE_PLEASE_ENTER_PHONE";
    public static String ERROR_MESSAGE_PLEASE_ENTER_FACEBOOK_URL = "ERROR_MESSAGE_PLEASE_ENTER_FACEBOOK_URL";
    public static String ERROR_MESSAGE_PLEASE_ENTER_TWITTER_URL = "ERROR_MESSAGE_PLEASE_ENTER_TWITTER_URL";
    public static String ERROR_MESSAGE_PLEASE_ENTER_YOUTUBE_URL = "ERROR_MESSAGE_PLEASE_ENTER_YOUTUBE_URL";
    public static String ERROR_MESSAGE_PLEASE_UPLOAD_NEW_IMAGE = "ERROR_MESSAGE_PLEASE_UPLOAD_NEW_IMAGE";
    public static String ERROR_MESSAGE_UPLOAD_FILE_MAX = "ERROR_MESSAGE_UPLOAD_FILE_MAX";
    
	public static final String PATH_CONFIG_DIR = getUserHome() + "/conf/ETS";
	public static final String PATH_DATA_DIR = getUserHome() + "/data/ETS";
	public static final String PATH_LOG_DIR = getUserHome() + "/log/ETS";
	public static final String PATH_TEMP_DATA_DIR = PATH_DATA_DIR + "/tmp";
	public static final String DATETIME_PATTERN_EST_EXPORT = "yyyyMMddHHmmss";
	public static final String DATETIME_PATTERN_DATE_FORMAT = "dd/MM/yyyy";
	
    public static String format(int number){
    	return creditFormat.format(number);
    }
    
	public static String getUserHome() {
		return System.getProperty("user.home");
	}
	
	public static final String SESSION_TRANSACTION_ID = "SESSION_TRANSACTION_ID";
	public static final String SESSION_DATA_ETS_RESULT_LIST = "SESSION_DATA_ETS_RESULT_LIST";
	public static final String SESSION_DATA_ETS_RESULT_EDIT_LIST = "SESSION_DATA_ETS_RESULT_EDIT_LIST";
	public static final String SESSION_USER_LOGIN = "SESSION_USER_LOGIN";
	public static final String SESSION_ERROR_MESSAGE = "SESSION_ERROR_MESSAGE";
	public static final String SESSION_DATA_ETS_RESULT_TMP = "SESSION_DATA_ETS_RESULT_TMP";
	
	public static String convertISO88591ToTis620(String text) throws UnsupportedEncodingException {
        return new String(text.getBytes("ISO-8859-1"), "TIS-620");
    }
	
}
