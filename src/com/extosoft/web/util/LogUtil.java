package com.extosoft.web.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.apache.log4j.Priority;

public class LogUtil {

	private static Logger tempLoggerDebug = Logger.getLogger("tempDebug");
//##LOG DEBUG##	
//[DTTM]|[TRNS TYPE]|[TRNS ID]|[SUBR NUMB]|[CUST NUMB]|[MESSAGE TYPE]|[MESSAGE]
//#LOG DESCRIPTION#
//DTTM				Date Time
//TRNS TYPE			[   INFO] , [WARNING], [  ERROR] (fix length 7 digits)
//TRNS ID			Transaction ID [DTTM][MSISDN][SDP PRODUCT] {yyyyMMddHHmmssSSS66XXXXXXXX12345678}
//SUBR NUM			Subscriber Number
//CUST NUM			Customer Number
//MESSAGE TYPE		[ INPUT] , [OUTPUT] , [  PROC] (fix length 6digits)
//MESSAGE			Information which need to explain function process
	
	public static void log(Logger loggerDebug, int priorityInt, String trnsID, String userName, String messageType, String message, Throwable t) {
//		 Example: [2013/04/01 10:06:59] | [  DEBUG],[   INFO],[WARNING],[  ERROR] | [YYYYMMDDHHMM[XXXXX]] | [668XXXXXXXX] | [CUSTNUMB] | [ INPUT],[OUTPUT],[  PROC] | [MESSAGE]
            if (loggerDebug == null) {
			//String logDateStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());
			System.out.println("eService used tempLogger = " + tempLoggerDebug);
                        if (t == null) {
                            	String messageFormat = "" + addSpace(trnsID, 17)  + " | " + addSpace(userName, 8) + " | " + addSpace(messageType,6) + " | " + message + "";
                                switch(priorityInt){
                                    case Priority.DEBUG_INT:{
                                    	tempLoggerDebug.debug(" | " + addSpace("DEBUG", 7) + " | " + messageFormat);
                                        return;
                                    }
                                    case Priority.INFO_INT:{
                                    	tempLoggerDebug.info(" | " + addSpace("INFO", 7) + " | " + messageFormat);
                                        return;
                                    }
                                    case Priority.WARN_INT:{
                                    	tempLoggerDebug.warn(" | " + addSpace("WARNING", 7) + " | " + messageFormat);
                                    }
                                    case Priority.ERROR_INT:{
                                    	tempLoggerDebug.error(" | " + addSpace("ERROR", 7) + " | " + messageFormat);
                                    }
                                }
			} else {
				String messageFormat = "" + addSpace(trnsID, 17)  + " | " + addSpace(userName, 8) + " | " + addSpace(messageType,6) + " | " + message + "";
                                switch(priorityInt){
                                    case Priority.DEBUG_INT:{
                                    	tempLoggerDebug.debug(" | " + addSpace("DEBUG", 7) + " | " + messageFormat, t);
                                        return;
                                    }
                                    case Priority.INFO_INT:{
                                    	tempLoggerDebug.info(" | " + addSpace("INFO", 7) + " | " + messageFormat, t);
                                        return;
                                    }
                                    case Priority.WARN_INT:{
                                    	tempLoggerDebug.warn(" | " + addSpace("WARNING", 7) + " | " + messageFormat, t);
                                        return;
                                    }
                                    case Priority.ERROR_INT:{
                                    	tempLoggerDebug.error(" | " + addSpace("ERROR", 7) + " | " + messageFormat, t);
                                        return;
                                    }
                                }
			}

//	                        if (t != null) {
//					t.printStackTrace(System.out);
//				}
		} else {
			if (t == null) {
				String messageFormat = "" + addSpace(trnsID, 17)  + " | " + addSpace(userName, 8) + " | " + addSpace(messageType,6) + " | " + message + "";
                                switch(priorityInt){
                                    case Priority.DEBUG_INT:{
                                    	loggerDebug.debug(" | " + addSpace("DEBUG", 7) + " | " + messageFormat);
                                        return;
                                    }
                                    case Priority.INFO_INT:{
                                    	loggerDebug.info(" | " + addSpace("INFO", 7) + " | " + messageFormat);
                                        return;
                                    }
                                    case Priority.WARN_INT:{
                                    	loggerDebug.warn(" | " + addSpace("WARNING", 7) + " | " + messageFormat);
                                    }
                                    case Priority.ERROR_INT:{
                                    	loggerDebug.error(" | " + addSpace("ERROR", 7) + " | " + messageFormat);
                                    }
                                }
			} else {
				String messageFormat = "" + trnsID  + " | " + userName + " | " + addSpace(messageType,6) + " | " + message + "]";
                                switch(priorityInt){
                                    case Priority.DEBUG_INT:{
                                    	loggerDebug.debug(" | " + addSpace("DEBUG", 7) + " | " + messageFormat, t);
                                        return;
                                    }
                                    case Priority.INFO_INT:{
                                    	loggerDebug.info(" | " + addSpace("INFO", 7) + " | " + messageFormat, t);
                                        return;
                                    }
                                    case Priority.WARN_INT:{
                                    	loggerDebug.warn(" | " + addSpace("WARNING", 7) + " | " + messageFormat, t);
                                        return;
                                    }
                                    case Priority.ERROR_INT:{
                                    	loggerDebug.error(" | " + addSpace("ERROR", 7) + " | " + messageFormat, t);
                                        return;
                                    }
                                }
			}

		}
	}
	
	
	private static String addSpace(String str, int length){
		String result="";
		if(str == null){
			str = " ";
		}
		for(int i = 0;i < (length-str.length());i++)
			result += " ";
		return result + str;
	}
	
//##LOG TRANSACTION##
//App Tier:
//[DTTM]|[TRNS ID]|[MSISDN]|[ACTION]|[RESULT CODE1]|[RESULT CODE2]|[RESP TIME]|[STATE]

//#LOG DESCRIPTION#
//DTTM			Date Time
//TRNS ID		Transaction ID [DTTM][MSISDN][SDP PRODUCT] {yyyyMMddHHmmssSSS66XXXXXXXX12345678}
//MSISDN			Subscriber Number
//ACTION			1 = Subscription
//RESULT CODE1		Result Code1  
//			 		[00000000 Success , 22007201 Duplicated Subscription]
//RESULT CODE2		Result Code2  
//	 				[00000000 Success , 22007201 Duplicated Subscription]
//RESPONSE TIME		Response Time in ms.
//STATE				Normal, Delay


//Format: [DTTM]|[TRNS ID]|[USER_NUMB]|[SRVC_NAME]|[FUNC_NAME]|[ERR CODE]|[ERR MSG]|[RESP TIME]
	//yyyyMMddHHmmssSSS6689199740511000206
	public static void logTrns(Logger loggerTrans, String trnsID, String userName, String funcName, String srvcName, String errCode, String errMsg, long respTime) {
		String logDateStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		String messageFormat = "" + logDateStr + " | " + addSpace(trnsID, 17) + " | " + addSpace(userName, 8)
			+ " | " + funcName + " | " + srvcName + " | " + errCode + " | " + errMsg + " | " + respTime + "";
		switch(Priority.INFO_INT){
		    case Priority.INFO_INT:{
		    	loggerTrans.info(messageFormat);
		        return;
		    }
		}

	}

}
