package com.extosoft.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.log4j.Priority;

import com.extosoft.web.bean.StudentRequest;
import com.extosoft.web.bean.StudentResponse;
import com.extosoft.web.db.ETSDB;
import com.extosoft.web.util.ETSConstant;
import com.extosoft.web.util.LogUtil;

/**
 * Servlet implementation class for Servlet: SearchEmployeesServlet
 *
 */
 public class AllStudentsServlet extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
   static final long serialVersionUID = 1L;
   
	private static Logger logger = Logger.getLogger(ETSConstant.LOGGER_NAME_EST_DEBUG);
	private static Logger loggerTrans = Logger.getLogger(ETSConstant.LOGGER_NAME_EST_TRNS);
   
    /* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public AllStudentsServlet() {
		super();
	}   	
	
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		response.setDateHeader("Expires", 0);
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Pragma", "no-cache");


		if(request.getSession().getAttribute(ETSConstant.SESSION_TRANSACTION_ID)== null){
			RequestDispatcher view = request.getRequestDispatcher("./LoginServlet");
			view.forward(request, response);
			return;
		}
		
		long requestTime = System.currentTimeMillis();
		long responseTime = 0;
		long serviceRequestTime = 0;
		long serviceResponseTime = 0;
		
		String userName = (String)request.getSession().getAttribute(ETSConstant.SESSION_USER_LOGIN);
		String trnsID = (String)request.getSession().getAttribute(ETSConstant.SESSION_TRANSACTION_ID);
		
		LogUtil.log(logger, Priority.INFO_INT, trnsID, userName, ETSConstant.PROC, "##### Start doGet AllStudentServlet #####", null);
		
		String pageForward = "./all-students.jsp";
		String errorCode = "0";
		String errorMsg = "";
		String errorMessage = request.getParameter("message");
		
		ETSDB etsDB = new ETSDB();
		List<StudentResponse> stdRespList = null;
		StudentRequest stdReqs = new StudentRequest();
		
		LogUtil.log(logger, Priority.INFO_INT, trnsID, userName, ETSConstant.PROC, "##### Start GetStudentDetail #####", null);
		LogUtil.log(logger, Priority.INFO_INT, trnsID, userName, ETSConstant.INPUT, "GetStudentDetail Request All", null);
		serviceRequestTime = System.currentTimeMillis();
		stdRespList = etsDB.getStudentDetail(stdReqs);
		if(stdRespList != null && stdRespList.size() > 0){
			errorCode = "0";
			errorMsg = "GetStudentDetail Data found";
			LogUtil.log(logger, Priority.INFO_INT, trnsID, userName, ETSConstant.OUTPUT, "GetStudentDetail Response stdRespList size --> " + stdRespList.size(), null);
		}else{
			errorCode = "-1";
			errorMsg = "GetStudentDetail Data not found";
			LogUtil.log(logger, Priority.INFO_INT, trnsID, userName, ETSConstant.OUTPUT, "GetStudentDetail Response stdRespList size --> 0", null);
		}
		serviceResponseTime = System.currentTimeMillis() - serviceRequestTime;
		LogUtil.logTrns(loggerTrans, trnsID, userName, "AllStudentsServlet", "GetStudentDetail", errorCode, errorMsg, serviceResponseTime);
		LogUtil.log(logger, Priority.INFO_INT, trnsID, userName, ETSConstant.PROC, "##### End GetStudentDetail " + serviceResponseTime + " ms. #####", null);

		LogUtil.log(logger, Priority.INFO_INT, trnsID, userName, ETSConstant.PROC, "Redirect PageForward --> " + pageForward, null);
		request.setAttribute(ETSConstant.SESSION_DATA_ETS_RESULT_LIST, stdRespList);
		request.setAttribute(ETSConstant.SESSION_ERROR_MESSAGE, errorMessage);
		responseTime = System.currentTimeMillis() - requestTime;
		LogUtil.log(logger, Priority.INFO_INT, trnsID, userName, ETSConstant.PROC, "##### End doGet AllStudentServlet " + responseTime + " ms. #####", null);
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

		if(request.getSession().getAttribute(ETSConstant.SESSION_TRANSACTION_ID)== null){
			RequestDispatcher view = request.getRequestDispatcher("./LoginServlet");
			view.forward(request, response);
			return;
		}
		
		long requestTime = System.currentTimeMillis();
		long responseTime = 0;
		long serviceRequestTime = 0;
		long serviceResponseTime = 0;
		
		String userName = (String)request.getSession().getAttribute(ETSConstant.SESSION_USER_LOGIN);
		String trnsID = (String)request.getSession().getAttribute(ETSConstant.SESSION_TRANSACTION_ID);
		
		LogUtil.log(logger, Priority.INFO_INT, trnsID, userName, ETSConstant.PROC, "##### Start doPost AllStudentServlet #####", null);
		
		String pageForward = "./all-students.jsp";
		String errorCode = "0";
		String errorMessage = "";
		
		String searchName = request.getParameter("searchName");
		
		ETSDB etsDB = new ETSDB();
		List<StudentResponse> stdRespList = null;
		StudentRequest stdReqs = new StudentRequest();
		stdReqs.setTrnsID(trnsID);
		stdReqs.setUserLogin(userName);
		stdReqs.setFullName(searchName);
		LogUtil.log(logger, Priority.INFO_INT, trnsID, userName, ETSConstant.PROC, "##### Start GetStudentDetail #####", null);
		LogUtil.log(logger, Priority.INFO_INT, trnsID, userName, ETSConstant.INPUT, "GetStudentDetail Request Fullname --> " + searchName, null);
		stdRespList = etsDB.getStudentDetail(stdReqs);
		
		if(stdRespList != null && stdRespList.size() > 0){
			errorCode = "0";
			errorMessage = "GetStudentDetail Data found";
			LogUtil.log(logger, Priority.INFO_INT, trnsID, userName, ETSConstant.OUTPUT, "GetStudentDetail Response stdRespList size --> " + stdRespList.size(), null);
		}else{
			errorCode = "-1";
			errorMessage = "GetStudentDetail Data not found";
			LogUtil.log(logger, Priority.INFO_INT, trnsID, userName, ETSConstant.OUTPUT, "GetStudentDetail Response stdRespList size --> 0", null);
		}
		serviceResponseTime = System.currentTimeMillis() - serviceRequestTime;
		LogUtil.logTrns(loggerTrans, trnsID, userName, "AllStudentsServlet", "GetStudentDetail", errorCode, errorMessage, serviceResponseTime);
		LogUtil.log(logger, Priority.INFO_INT, trnsID, userName, ETSConstant.PROC, "##### End GetStudentDetail " + serviceResponseTime + " ms. #####", null);

		LogUtil.log(logger, Priority.INFO_INT, trnsID, userName, ETSConstant.PROC, "Redirect PageForward --> " + pageForward, null);
		request.setAttribute(ETSConstant.SESSION_DATA_ETS_RESULT_LIST, stdRespList);
		responseTime = System.currentTimeMillis() - requestTime;
		LogUtil.logTrns(loggerTrans, trnsID, userName, "AllStudentsServlet", "SearchStudent", errorCode, "0".equals(errorCode) ? "Success" : "Fail", responseTime);
		LogUtil.log(logger, Priority.INFO_INT, trnsID, userName, ETSConstant.PROC, "##### End doPost AllStudentServlet " + responseTime + " ms. #####", null);
		RequestDispatcher view = request.getRequestDispatcher(pageForward);
		view.forward(request, response);
		
	}   	  	    
}