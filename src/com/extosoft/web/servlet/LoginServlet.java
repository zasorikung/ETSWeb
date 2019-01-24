package com.extosoft.web.servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.log4j.Priority;

import com.extosoft.web.bean.UserRequest;
import com.extosoft.web.bean.UserResponse;
import com.extosoft.web.db.ETSDB;
import com.extosoft.web.init.ETSConfigInit;
import com.extosoft.web.util.ETSConstant;
import com.extosoft.web.util.LogUtil;

/**
 * Servlet implementation class for Servlet: LoginServlet
 *
 */
 public class LoginServlet extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
	static final long serialVersionUID = 1L;
   
	private static Logger logger = Logger.getLogger(ETSConstant.LOGGER_NAME_EST_DEBUG);
	private static Logger loggerTrans = Logger.getLogger(ETSConstant.LOGGER_NAME_EST_TRNS);
    /* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public LoginServlet() {
		super();
	}   	
	
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		response.setDateHeader("Expires", 0);
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Pragma", "no-cache");
		
		String trnsID = "";
		String userName = "";
		
		if(request.getSession().getAttribute(ETSConstant.SESSION_TRANSACTION_ID) != null){
			trnsID = (String)request.getSession().getAttribute(ETSConstant.SESSION_TRANSACTION_ID);
		}
		if(request.getSession().getAttribute(ETSConstant.SESSION_USER_LOGIN) != null){
			userName = (String)request.getSession().getAttribute(ETSConstant.SESSION_USER_LOGIN);
		}

		long requestTime = System.currentTimeMillis();
		long responseTime = 0;

		LogUtil.log(logger, Priority.INFO_INT, trnsID, userName, ETSConstant.PROC, "##### Start doGet LoginServlet #####", null);
		String mode = request.getParameter("mode");
		LogUtil.log(logger, Priority.INFO_INT, trnsID, userName, ETSConstant.INPUT, "Request mode --> " + mode, null);
		
		String pageForward = "./login.jsp";
		if("logout".equals(mode)){
			LogUtil.log(logger, Priority.INFO_INT, trnsID, userName, ETSConstant.PROC, "##### Logout by " + userName + " #####", null);	
		}
		removeSession(request);
		LogUtil.log(logger, Priority.INFO_INT, trnsID, userName, ETSConstant.PROC, "Redirect PageForward --> " + pageForward, null);
		responseTime = System.currentTimeMillis() - requestTime;
		LogUtil.log(logger, Priority.INFO_INT, trnsID, userName, ETSConstant.PROC, "##### End doPost LoginServlet " + responseTime + " ms. #####", null);
		RequestDispatcher view = request.getRequestDispatcher(pageForward);
		view.forward(request, response);

	}  	
	
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		response.setDateHeader("Expires", 0);
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Pragma", "no-cache");
		
		long requestTime = System.currentTimeMillis();
		long responseTime = 0;
		long serviceRequestTime = 0;
		long serviceResponseTime = 0;
		
		String trnsID = "";
		String userName = "";
		String userPass = "";
		
		LogUtil.log(logger, Priority.INFO_INT, trnsID, userName, ETSConstant.PROC, "##### Start doPost LoginServlet #####", null);
		removeSession(request);

		// 0 --> Success
		// -1 --> System Fail
		// -2 --> Business Fail
		String errorCode = "0";
		String errorMessage = "";
		String pageForward = "./login.jsp";;
		
		userName = request.getParameter("username");
		userPass = request.getParameter("password");
		
		LogUtil.log(logger, Priority.INFO_INT, trnsID, userName, ETSConstant.INPUT, "Request UserName --> " + userName + ", Password --> " + userPass, null);
		if(userName == null || "".equals(userName)){
			errorCode = "-2";
			errorMessage = ETSConfigInit.getConfig(ETSConstant.ERROR_MESSAGE_PLEASE_ENTER_USERNAME, "");
		}else if(userPass == null || "".equals(userPass)){
			errorCode = "-2";
			errorMessage = ETSConfigInit.getConfig(ETSConstant.ERROR_MESSAGE_PLEASE_ENTER_PASSWORD, "");
		}else{
			ETSDB etsDB = new ETSDB();
			UserResponse userResp = null;
			UserRequest userReqs = new UserRequest();
			userReqs.setTrnsID("");
			userReqs.setUserLogin("");
			userReqs.setUserName(userName);
			LogUtil.log(logger, Priority.INFO_INT, trnsID, userName, ETSConstant.PROC, "##### Start GetUserDetail #####", null);
			serviceRequestTime = System.currentTimeMillis();
			LogUtil.log(logger, Priority.INFO_INT, trnsID, userName, ETSConstant.INPUT, "GetUserDetail Request UserName --> " + userName, null);
			userResp = etsDB.getUserDetail(userReqs);
			
			if(userResp != null && userResp.getUserName() != null && userResp.getUserPass() != null){
				if(userName.equals(userResp.getUserName()) && userResp.getUserPass().equals(userPass)){
					
					LogUtil.log(logger, Priority.INFO_INT, trnsID, userName, ETSConstant.PROC, "GetUserDetail Response Login Successfully", null);
					pageForward = "./AllStudentsServlet";
					userName = userResp.getUserName();
					errorCode = "0";
					errorMessage = "Hello " + userName + " Welcome.";
					trnsID = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
					
					request.getSession().setAttribute(ETSConstant.SESSION_USER_LOGIN, userName);
					request.getSession().setAttribute(ETSConstant.SESSION_TRANSACTION_ID, trnsID);
					LogUtil.log(logger, Priority.INFO_INT, trnsID, userName, ETSConstant.PROC, "##### Login by " + userName + " #####", null);
					
				}else{
					userName = "";
					errorCode = "-2";
					errorMessage = ETSConfigInit.getConfig(ETSConstant.ERROR_MESSAGE_USERNAME_OR_PASSWORD_INVALID, "");
					LogUtil.log(logger, Priority.INFO_INT, trnsID, userName, ETSConstant.OUTPUT, "GetUserDetail Response Login Fail", null);
				}
			}else{
				userName = "";
				errorCode = "-1";
				errorMessage = ETSConfigInit.getConfig(ETSConstant.ERROR_MESSAGE_USERNAME_NOT_FOUND, "");
				LogUtil.log(logger, Priority.ERROR_INT, trnsID, userName, ETSConstant.OUTPUT, "GetUserDetail Response Login Fail", null);
			}
			serviceResponseTime = (System.currentTimeMillis() - serviceRequestTime);
			LogUtil.logTrns(loggerTrans, trnsID, userName, "LoginServlet", "GetUserDetail", errorCode, errorMessage, serviceResponseTime);
			LogUtil.log(logger, Priority.INFO_INT, trnsID, userName, ETSConstant.PROC, "##### End GetUserDetail " + serviceResponseTime + " ms. #####", null);
		}
		
		LogUtil.log(logger, Priority.INFO_INT, trnsID, userName, ETSConstant.PROC, "Response errorMessage --> " + errorMessage, null);
		request.setAttribute(ETSConstant.SESSION_ERROR_MESSAGE, errorMessage);
		LogUtil.log(logger, Priority.INFO_INT, trnsID, userName, ETSConstant.PROC, "Redirect PageForward --> " + pageForward, null);
		responseTime = System.currentTimeMillis() - requestTime;
		LogUtil.logTrns(loggerTrans, trnsID, userName, "LoginServlet", "Login", errorCode, "0".equals(errorCode) ? "Success" : "Fail", responseTime);
		LogUtil.log(logger, Priority.INFO_INT, trnsID, userName, ETSConstant.PROC, "##### End doPost LoginServlet " + responseTime + " ms. #####", null);
		RequestDispatcher view = request.getRequestDispatcher(pageForward);
		view.forward(request, response);
		return;
	} 
	
	private void removeSession(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.removeAttribute(ETSConstant.SESSION_ERROR_MESSAGE);
		session.removeAttribute(ETSConstant.SESSION_USER_LOGIN);
		session.removeAttribute(ETSConstant.SESSION_DATA_ETS_RESULT_LIST);
		session.removeAttribute(ETSConstant.SESSION_DATA_ETS_RESULT_EDIT_LIST);
		session.removeAttribute(ETSConstant.SESSION_TRANSACTION_ID);
		session.removeAttribute(ETSConstant.SESSION_DATA_ETS_RESULT_TMP);
		
	}
}