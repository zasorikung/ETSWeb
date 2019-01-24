package com.extosoft.web.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.log4j.Priority;

import com.extosoft.web.bean.UserRequest;
import com.extosoft.web.bean.UserResponse;
import com.extosoft.web.db.ETSDB;
import com.extosoft.web.init.ETSConfigInit;
import com.extosoft.web.util.ETSConstant;
import com.extosoft.web.util.LogUtil;
import com.extosoft.web.util.ParameterValidator;

/**
 * Servlet implementation class for Servlet: RegisterServlet
 *
 */
 public class RegisterServlet extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
   static final long serialVersionUID = 1L;
   
	private static Logger logger = Logger.getLogger(ETSConstant.LOGGER_NAME_EST_DEBUG);
	private static Logger loggerTrans = Logger.getLogger(ETSConstant.LOGGER_NAME_EST_TRNS);
	
    /* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public RegisterServlet() {
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
		
		long requestTime = System.currentTimeMillis();
		long responseTime = 0;
		
		LogUtil.log(logger, Priority.INFO_INT, trnsID, userName, ETSConstant.PROC, "##### Start doGet RegisterServlet #####", null);
		String pageForward = "./register.jsp";
		LogUtil.log(logger, Priority.INFO_INT, trnsID, userName, ETSConstant.PROC, "Redirect PageForward --> " + pageForward, null);
		request.setAttribute(ETSConstant.SESSION_ERROR_MESSAGE, "");
		responseTime = System.currentTimeMillis() - requestTime;
		LogUtil.log(logger, Priority.INFO_INT, trnsID, userName, ETSConstant.PROC, "##### End doGet RegisterServlet " + responseTime + " ms. #####", null);
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
		
		String trnsID = "";
		String userName = "";
		
		long requestTime = System.currentTimeMillis();
		long responseTime = 0;
		long serviceRequestTime = 0;
		long serviceResponseTime = 0;
		
		LogUtil.log(logger, Priority.INFO_INT, trnsID, userName, ETSConstant.PROC, "##### Start doPost RegisterServlet #####", null);
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String repeatPassword = request.getParameter("repeatPassword");
		String emailAddress = request.getParameter("emailAddress");
		String repeatEmailAddress = request.getParameter("repeatEmailAddress");
		String ichecks = request.getParameter("ichecks");
		
		LogUtil.log(logger, Priority.INFO_INT, trnsID, userName, ETSConstant.INPUT, "Request UserName --> " + userName 
				+ ", Password --> " + password
				+ ", RepeatPassword --> " + repeatPassword 
				+ ", EmailAddress --> " + emailAddress 
				+ ", RepeatEmailAddress --> " + repeatEmailAddress 
				+ ", IChecks --> " + ichecks, null);
		
		String pageForward = "./register.jsp";
		String errorCode = "0";
		String errorMessage = "";
		RequestDispatcher view = null;
		
		if(username == null || "".equals(username)){
			errorCode = "-2";
			errorMessage = ETSConfigInit.getConfig(ETSConstant.ERROR_MESSAGE_PLEASE_ENTER_USERNAME, "");
		}else if(password == null || "".equals(password)){
			errorCode = "-2";
			errorMessage = ETSConfigInit.getConfig(ETSConstant.ERROR_MESSAGE_PLEASE_ENTER_PASSWORD, "");
		}else if((password == null || "".equals(password)) || 
				(repeatPassword == null || "".equals(repeatPassword)) || 
					!password.equals(repeatPassword)){
			errorCode = "-2";
			errorMessage = ETSConfigInit.getConfig(ETSConstant.ERROR_MESSAGE_PASSWORD_NOT_MATCH_REPEAT_PASSWORD, "");
		}else if((emailAddress == null || "".equals(emailAddress)) ||
				(repeatEmailAddress == null || "".equals(repeatEmailAddress)) ||
					!emailAddress.equals(repeatEmailAddress)){
			errorCode = "-2";
			errorMessage = ETSConfigInit.getConfig(ETSConstant.ERROR_MESSAGE_EMAIL_ADDRESS_NOT_MATCH_REPEAT_EMAIL_ADDRESS, "");
		}else if(!ParameterValidator.isValidEmail(emailAddress, false)){
			errorCode = "-2";
			errorMessage = ETSConfigInit.getConfig(ETSConstant.ERROR_MESSAGE_INVALID_FORMAT_EMAIL_ADDRESS, "");
		}else if(ichecks == null || "".equals(ichecks)){
			errorCode = "-2";
			errorMessage = ETSConfigInit.getConfig(ETSConstant.ERROR_MESSAGE_ACCEPT_THE_TERMS, "");
		}else{
		
			ETSDB etsDB = new ETSDB();
			UserResponse userResp = null;
			UserRequest userReqs = new UserRequest();
			userReqs.setUserName(username);
			LogUtil.log(logger, Priority.INFO_INT, trnsID, userName, ETSConstant.PROC, "##### Start GetUserDetail #####", null);
			serviceRequestTime = System.currentTimeMillis();
			LogUtil.log(logger, Priority.INFO_INT, trnsID, userName, ETSConstant.INPUT, "GetUserDetail Request UserName --> " + userName, null);
			userResp = etsDB.getUserDetail(userReqs);
			
			if(userResp != null && username.equals(userResp.getUserName())){
				errorCode = "-2";
				errorMessage = ETSConfigInit.getConfig(ETSConstant.ERROR_MESSAGE_USERNAME_ALREADY_EXISTS, "");
				LogUtil.log(logger, Priority.INFO_INT, trnsID, userName, ETSConstant.OUTPUT, "GetUserDetail Response User Already Exists", null);
				serviceResponseTime = (System.currentTimeMillis() - serviceRequestTime);
				LogUtil.logTrns(loggerTrans, trnsID, userName, "RegisterServlet", "GetUserDetail", errorCode, errorMessage, serviceResponseTime);
				LogUtil.log(logger, Priority.INFO_INT, trnsID, userName, ETSConstant.PROC, "##### End GetUserDetail " + serviceResponseTime + " ms. #####", null);
			}else{
				errorCode = "0";
				errorMessage = "User dosen't exists";
				LogUtil.log(logger, Priority.INFO_INT, trnsID, userName, ETSConstant.OUTPUT, "GetUserDetail Response User dosen't exists", null);
				serviceResponseTime = (System.currentTimeMillis() - serviceRequestTime);
				LogUtil.logTrns(loggerTrans, trnsID, userName, "RegisterServlet", "GetUserDetail", errorCode, errorMessage, serviceResponseTime);
				LogUtil.log(logger, Priority.INFO_INT, trnsID, userName, ETSConstant.PROC, "##### End GetUserDetail " + serviceResponseTime + " ms. #####", null);
				userReqs = new UserRequest();
				userReqs.setUserName(username);
				userReqs.setUserPass(password);
				userReqs.setUserEmail(emailAddress);
				userReqs.setUserBy("admin");
				LogUtil.log(logger, Priority.INFO_INT, trnsID, userName, ETSConstant.PROC, "##### Start AddUserDetail #####", null);
				serviceRequestTime = System.currentTimeMillis();
				LogUtil.log(logger, Priority.INFO_INT, trnsID, userName, ETSConstant.INPUT, "AddUserDetail Request UserName --> " + userName
						+ ", Password --> " + password
						+ ", EmailAddress --> " + emailAddress, null);
				
				boolean flag = etsDB.addUserDetail(userReqs);
				if(flag){
					pageForward = "./login.jsp";
					errorCode = "0";
					errorMessage = ETSConfigInit.getConfig(ETSConstant.ERROR_MESSAGE_REGISTER_SUCCESSFULLY, "");
					LogUtil.log(logger, Priority.INFO_INT, trnsID, userName, ETSConstant.OUTPUT, "AddUserDetail Response Register Successfully", null);
				}else{
					errorCode = "-1";
					errorMessage = ETSConfigInit.getConfig(ETSConstant.ERROR_MESSAGE_REGISTER_FAIL, "");
					LogUtil.log(logger, Priority.INFO_INT, trnsID, userName, ETSConstant.OUTPUT, "AddUserDetail Response Register Fail", null);
				}
				serviceResponseTime = (System.currentTimeMillis() - serviceRequestTime);
				LogUtil.logTrns(loggerTrans, trnsID, userName, "RegisterServlet", "AddUserDetail", errorCode, errorMessage, serviceResponseTime);
				LogUtil.log(logger, Priority.INFO_INT, trnsID, userName, ETSConstant.PROC, "##### End AddUserDetail " + serviceResponseTime + " ms. #####", null);
			}
			
		}
		
		LogUtil.log(logger, Priority.INFO_INT, trnsID, userName, ETSConstant.PROC, "Response errorMessage --> " + errorMessage, null);
		LogUtil.log(logger, Priority.INFO_INT, trnsID, userName, ETSConstant.PROC, "Redirect PageForward --> " + pageForward, null);
		request.setAttribute(ETSConstant.SESSION_ERROR_MESSAGE, errorMessage);
		responseTime = System.currentTimeMillis() - requestTime;
		LogUtil.logTrns(loggerTrans, trnsID, userName, "RegisterServlet", "Register", errorCode, "0".equals(errorCode) ? "Success" : "Fail", responseTime);
		LogUtil.log(logger, Priority.INFO_INT, trnsID, userName, ETSConstant.PROC, "##### End doPost RegisterServlet " + responseTime + " ms. #####", null);
		view = request.getRequestDispatcher(pageForward);
		view.forward(request, response);

	}   	  	    
}