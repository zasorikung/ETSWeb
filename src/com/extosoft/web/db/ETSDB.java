package com.extosoft.web.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.Priority;

import com.extosoft.web.bean.StudentRequest;
import com.extosoft.web.bean.StudentResponse;
import com.extosoft.web.bean.UserRequest;
import com.extosoft.web.bean.UserResponse;
import com.extosoft.web.init.ETSConfigInit;
import com.extosoft.web.util.ETSConstant;
import com.extosoft.web.util.LogUtil;

public class ETSDB {
	// JDBC driver name and database URL
	private String JDBC_DRIVER = ETSConfigInit.getConfig("JDBC_DRIVER", "com.mysql.jdbc.Driver");//com.mysql.jdbc.Driver
	private String DB_URL = ETSConfigInit.getConfig("DB_URL", "jdbc:mysql://localhost/extosoft_db");//jdbc:mysql://localhost/fishstoredb
	private String DB_SCHEMA = ETSConfigInit.getConfig("DB_SCHEMA", "extosoft_db");
	private static Logger logger = Logger.getLogger(ETSConstant.LOGGER_NAME_EST_DEBUG);
	
	// Database credentials
	private String USER = ETSConfigInit.getConfig("DB_USER", "extosoft_db");//root
	private String PASS = ETSConfigInit.getConfig("DB_PASS", "1234");//1234
	
	public UserResponse getUserDetail(UserRequest userReqs) {
		
		String trnsID = (userReqs.getTrnsID() != null && !"".equals(userReqs.getTrnsID())) ? userReqs.getTrnsID() : "";
		String userLogin = (userReqs.getUserLogin() != null && !"".equals(userReqs.getUserLogin())) ? userReqs.getUserLogin() : "";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		UserResponse userResp = null;
	
		int i = 1;
		try {
			// STEP 2: Register JDBC driver
			Class.forName(JDBC_DRIVER);

			// STEP 3: Open a connection
			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			// STEP 4: Execute a query
			StringBuffer sql = new StringBuffer("SELECT USER_NAME, USER_PASS FROM USER_DETAIL WHERE 1 = 1 ");
			if(userReqs != null && userReqs.getUserName() != null && !"".equals(userReqs.getUserName())){
				sql.append("AND USER_NAME = ? ");
			}
			
			if(userReqs != null && userReqs.getUserPass() != null && !"".equals(userReqs.getUserPass())){
				sql.append("AND USER_PASS = ? ");
			}
			
			LogUtil.log(logger, Priority.INFO_INT, trnsID, userLogin, ETSConstant.PROC, "SQL -- >" + sql.toString(), null);
			pstmt = conn.prepareStatement(sql.toString());
			
			if(userReqs != null && userReqs.getUserName() != null && !"".equals(userReqs.getUserName())){
				pstmt.setString(i++, userReqs.getUserName());
			}
			
			if(userReqs != null && userReqs.getUserPass() != null && !"".equals(userReqs.getUserPass())){
				pstmt.setString(i++, userReqs.getUserPass());
			}
			
			rs = pstmt.executeQuery();
			
			// STEP 5: Extract data from result set
			while (rs.next()) {
				// Retrieve by column name
//				USER_NAME,
//				USER_PASS
				userResp = new UserResponse();
				userResp.setUserName(rs.getString("USER_NAME"));
				userResp.setUserPass(rs.getString("USER_PASS"));
			}
			// STEP 6: Clean-up environment
		} catch (SQLException ex) {
			LogUtil.log(logger, Priority.ERROR_INT, trnsID, userLogin, ETSConstant.PROC, "SQLException Error --> " + ex.getMessage(), ex);
		} catch (Exception ex) {
			LogUtil.log(logger, Priority.ERROR_INT, trnsID, userLogin, ETSConstant.PROC, "Exception Error --> " + ex.getMessage(), ex);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
			} catch (SQLException ex) {
				LogUtil.log(logger, Priority.ERROR_INT, trnsID, userLogin, ETSConstant.PROC, "SQLException ResultSet Error --> " + ex.getMessage(), ex);
			}// nothing we can do
			try {
				if (pstmt != null) {
					pstmt.close();
				}
			} catch (SQLException ex) {
				LogUtil.log(logger, Priority.ERROR_INT, trnsID, userLogin, ETSConstant.PROC, "SQLException Prepare Error --> " + ex.getMessage(), ex);
			}// nothing we can do
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException ex) {
				LogUtil.log(logger, Priority.ERROR_INT, trnsID, userLogin, ETSConstant.PROC, "SQLException Connection Error --> " + ex.getMessage(), ex);
			}
		}
		
		return userResp;
	}
	
	public boolean addUserDetail(UserRequest userReqs) {
		
		String trnsID = (userReqs.getTrnsID() != null && !"".equals(userReqs.getTrnsID())) ? userReqs.getTrnsID() : "";
		String userLogin = (userReqs.getUserLogin() != null && !"".equals(userReqs.getUserLogin())) ? userReqs.getUserLogin() : "";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		boolean flag = false;
		int i = 1;
		try {
			// STEP 2: Register JDBC driver
			Class.forName(JDBC_DRIVER);

			// STEP 3: Open a connection
			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			// STEP 4: Execute a query
			StringBuffer sql = new StringBuffer("INSERT INTO USER_DETAIL(USER_NAME, USER_PASS, USER_EMAIL, USER_DTTM, USER_BY) VALUES(?,?,?,?,?) ");
			
			LogUtil.log(logger, Priority.INFO_INT, trnsID, userLogin, ETSConstant.PROC, "SQL -- >" + sql.toString(), null);
			pstmt = conn.prepareStatement(sql.toString());
			
			pstmt.setString(i++, userReqs.getUserName());
			pstmt.setString(i++, userReqs.getUserPass());
			pstmt.setString(i++, userReqs.getUserEmail());
			pstmt.setTimestamp(i++, new Timestamp(System.currentTimeMillis()));
			pstmt.setString(i++, userReqs.getUserBy());
			
			int result = pstmt.executeUpdate();
			
			if(result > 0){
				LogUtil.log(logger, Priority.INFO_INT, trnsID, userLogin, ETSConstant.PROC, "Add Success", null);
				flag = true;
			}else{
				LogUtil.log(logger, Priority.ERROR_INT, trnsID, userLogin, ETSConstant.PROC, "Add Fail", null);
				flag = false;
			}
			// STEP 5: Extract data from result set
			
			// STEP 6: Clean-up environment
			
		} catch (SQLException ex) {
			LogUtil.log(logger, Priority.ERROR_INT, trnsID, userLogin, ETSConstant.PROC, "SQLException Error --> " + ex.getMessage(), ex);
		} catch (Exception ex) {
			LogUtil.log(logger, Priority.ERROR_INT, trnsID, userLogin, ETSConstant.PROC, "Exception Error --> " + ex.getMessage(), ex);
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
			} catch (SQLException ex) {
				LogUtil.log(logger, Priority.ERROR_INT, trnsID, userLogin, ETSConstant.PROC, "SQLException Prepare Error --> " + ex.getMessage(), ex);
			}// nothing we can do
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException ex) {
				LogUtil.log(logger, Priority.ERROR_INT, trnsID, userLogin, ETSConstant.PROC, "SQLException Connection Error --> " + ex.getMessage(), ex);
			}
		}
		
		return flag;
	}
	
	public List<StudentResponse> getStudentDetail(StudentRequest stdReqs) {
		
		String trnsID = (stdReqs.getTrnsID() != null && !"".equals(stdReqs.getTrnsID())) ? stdReqs.getTrnsID() : "";
		String userLogin = (stdReqs.getUserLogin() != null && !"".equals(stdReqs.getUserLogin())) ? stdReqs.getUserLogin() : "";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StudentResponse stdResp = null;
		List<StudentResponse> stdRespList = new ArrayList<StudentResponse>();
		int i = 1;
		try {
			// STEP 2: Register JDBC driver
			Class.forName(JDBC_DRIVER);

			// STEP 3: Open a connection
			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			// STEP 4: Execute a query
			StringBuffer sql = new StringBuffer("SELECT STUDENT_ID, FULL_NAME, ADDRESS, MOBILE_NO, DATE_OF_BIRTH, POSTCODE, IMAGE_CODE, IMAGE_THUMB, IMAGE_HEIGHT, IMAGE_WIDTH, DEPARTMENT, AGE, DESCRIPTION, GENDER, COUNTRY, CITY, WEB_SITE_URL, EMAIL, PHONE, FACEBOOK_URL, TWITTER_URL, YOUTUBE_URL FROM STUDENT_DETAIL WHERE 1 = 1 ");
			
			if(stdReqs != null && stdReqs.getStudentID() > 0){
				sql.append("AND STUDENT_ID = ? ");
			}
			
			if(stdReqs != null && stdReqs.getFullName() != null && !"".equals(stdReqs.getFullName())){
				sql.append("AND FULL_NAME LIKE ? ");
			}
			
			LogUtil.log(logger, Priority.INFO_INT, trnsID, userLogin, ETSConstant.PROC, "SQL -- >" + sql.toString(), null);
			pstmt = conn.prepareStatement(sql.toString());
			
			if(stdReqs != null && stdReqs.getStudentID() > 0){
				pstmt.setInt(i++, stdReqs.getStudentID());
			}
			
			if(stdReqs != null && stdReqs.getFullName() != null && !"".equals(stdReqs.getFullName())){
				pstmt.setString(i++, "%" + stdReqs.getFullName() + "%");
			}
			
			rs = pstmt.executeQuery();
			
			// STEP 5: Extract data from result set
			while (rs.next()) {
				// Retrieve by column name
//				STUDENT_ID,
//				FULL_NAME, 
//				ADDRESS, 
//				MOBILE_NO, 
//				DATE_OF_BIRTH, 
//				POSTCODE, 
//				IMAGE_CODE, 
//				IMAGE_THUMB, 
//				IMAGE_HEIGHT, 
//				IMAGE_WIDTH, 
//				DEPARTMENT, 
//				AGE, 
//				DESCRIPTION, 
//				GENDER, 
//				COUNTRY, 
//				CITY, 
//				WEB_SITE_URL, 
//				EMAIL, 
//				PHONE, 
//				FACEBOOK_URL, 
//				TWITTER_URL, 
//				YOUTUBE_URL
				stdResp = new StudentResponse();
				stdResp.setStudentID(Integer.parseInt(rs.getString("STUDENT_ID")));
				stdResp.setFullName(rs.getString("FULL_NAME"));
				stdResp.setAddress(rs.getString("ADDRESS"));
				stdResp.setMobileNo(rs.getString("MOBILE_NO"));
				stdResp.setDateOfBirth(rs.getString("DATE_OF_BIRTH"));
				stdResp.setPostCode(rs.getString("POSTCODE"));
				stdResp.setImageCode(rs.getString("IMAGE_CODE"));
				stdResp.setImageThumb(rs.getString("IMAGE_THUMB"));
				stdResp.setImageHeight(Integer.parseInt(rs.getString("IMAGE_HEIGHT")));
				stdResp.setImageWidth(Integer.parseInt(rs.getString("IMAGE_WIDTH")));
				stdResp.setDepartment(rs.getString("DEPARTMENT"));
				stdResp.setAge(Integer.parseInt(rs.getString("AGE")));
				stdResp.setDescription(rs.getString("DESCRIPTION"));
				stdResp.setGender(rs.getString("GENDER"));
				stdResp.setCountry(rs.getString("COUNTRY"));
				stdResp.setCity(rs.getString("CITY"));
				stdResp.setWebsiteURL(rs.getString("WEB_SITE_URL"));
				stdResp.setEmail(rs.getString("EMAIL"));
				stdResp.setPhone(rs.getString("PHONE"));
				stdResp.setFacebookURL(rs.getString("FACEBOOK_URL"));
				stdResp.setTwitterURL(rs.getString("TWITTER_URL"));
				stdResp.setYoutubeURL(rs.getString("YOUTUBE_URL"));
				stdRespList.add(stdResp);
			}
			// STEP 6: Clean-up environment
		} catch (SQLException ex) {
			LogUtil.log(logger, Priority.ERROR_INT, trnsID, userLogin, ETSConstant.PROC, "SQLException Error --> " + ex.getMessage(), ex);
		} catch (Exception ex) {
			LogUtil.log(logger, Priority.ERROR_INT, trnsID, userLogin, ETSConstant.PROC, "Exception Error --> " + ex.getMessage(), ex);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
			} catch (SQLException ex) {
				LogUtil.log(logger, Priority.ERROR_INT, trnsID, userLogin, ETSConstant.PROC, "SQLException ResuletSet Error --> " + ex.getMessage(), ex);
			}
			try {
				if (pstmt != null) {
					pstmt.close();
				}
			} catch (SQLException ex) {
				LogUtil.log(logger, Priority.ERROR_INT, trnsID, userLogin, ETSConstant.PROC, "SQLException Prepare Error --> " + ex.getMessage(), ex);
			}// nothing we can do
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException ex) {
				LogUtil.log(logger, Priority.ERROR_INT, trnsID, userLogin, ETSConstant.PROC, "SQLException Connection Error --> " + ex.getMessage(), ex);
			}
		}
		
		return stdRespList;
	}
	
	public boolean addStudentDetail(StudentRequest stdReqs) {
		
		String trnsID = (stdReqs.getTrnsID() != null && !"".equals(stdReqs.getTrnsID())) ? stdReqs.getTrnsID() : "";
		String userLogin = (stdReqs.getUserLogin() != null && !"".equals(stdReqs.getUserLogin())) ? stdReqs.getUserLogin() : "";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		boolean flag = false;
		int i = 1;
		try {
			// STEP 2: Register JDBC driver
			Class.forName(JDBC_DRIVER);

			// STEP 3: Open a connection
			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			// STEP 4: Execute a query
			StringBuffer sql = new StringBuffer("INSERT INTO STUDENT_DETAIL(FULL_NAME, ADDRESS, MOBILE_NO, DATE_OF_BIRTH, POSTCODE, IMAGE_CODE, IMAGE_THUMB, IMAGE_HEIGHT, IMAGE_WIDTH, DEPARTMENT, AGE, DESCRIPTION, GENDER, COUNTRY, CITY, WEB_SITE_URL, EMAIL, PHONE, FACEBOOK_URL, TWITTER_URL, YOUTUBE_URL, CRTD_DTTM, CRTD_BY, UPDT_DTTM, UPDT_BY) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ");
			
			LogUtil.log(logger, Priority.INFO_INT, trnsID, userLogin, ETSConstant.PROC, "SQL -- >" + sql.toString(), null);
			pstmt = conn.prepareStatement(sql.toString());
			
			//Basic Information
			pstmt.setString(i++, stdReqs.getFullName());
			pstmt.setString(i++, stdReqs.getAddress());
			pstmt.setString(i++, stdReqs.getMobileNo());
			pstmt.setString(i++, stdReqs.getDateOfBirth());
			pstmt.setString(i++, stdReqs.getPostCode());
			pstmt.setString(i++, stdReqs.getImageCode());
			pstmt.setString(i++, stdReqs.getImageThumb());
			pstmt.setInt(i++, stdReqs.getImageHeight());
			pstmt.setInt(i++, stdReqs.getImageWidth());
			pstmt.setString(i++, stdReqs.getDepartment());
			pstmt.setInt(i++, stdReqs.getAge());
			pstmt.setString(i++, stdReqs.getDescription());
			pstmt.setString(i++, stdReqs.getGender());
			pstmt.setString(i++, stdReqs.getCountry());
			pstmt.setString(i++, stdReqs.getCity());
			pstmt.setString(i++, stdReqs.getWebsiteURL());
			
			//Account Information
			pstmt.setString(i++, stdReqs.getEmail());
			pstmt.setString(i++, stdReqs.getPhone());
			
			//Social Information
			pstmt.setString(i++, stdReqs.getFacebookURL());
			pstmt.setString(i++, stdReqs.getTwitterURL());
			pstmt.setString(i++, stdReqs.getYoutubeURL());
			pstmt.setTimestamp(i++, new Timestamp(System.currentTimeMillis()));
			pstmt.setString(i++, stdReqs.getCrtdBy());
			pstmt.setTimestamp(i++, new Timestamp(System.currentTimeMillis()));
			pstmt.setString(i++, stdReqs.getUpdtBy());
			
			int result = pstmt.executeUpdate();
			
			if(result > 0){
				LogUtil.log(logger, Priority.INFO_INT, trnsID, userLogin, ETSConstant.PROC, "Add Success", null);
				flag = true;
			}else{
				LogUtil.log(logger, Priority.ERROR_INT, trnsID, userLogin, ETSConstant.PROC, "Add Fail", null);
				flag = false;
			}
			// STEP 5: Extract data from result set
			
			// STEP 6: Clean-up environment
			
		} catch (SQLException ex) {
			LogUtil.log(logger, Priority.ERROR_INT, trnsID, userLogin, ETSConstant.PROC, "SQLException Error --> " + ex.getMessage(), ex);
		} catch (Exception ex) {
			LogUtil.log(logger, Priority.ERROR_INT, trnsID, userLogin, ETSConstant.PROC, "Exception Error --> " + ex.getMessage(), ex);
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
			} catch (SQLException ex) {
				LogUtil.log(logger, Priority.ERROR_INT, trnsID, userLogin, ETSConstant.PROC, "SQLException Prepare Error --> " + ex.getMessage(), ex);
			}// nothing we can do
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException ex) {
				LogUtil.log(logger, Priority.ERROR_INT, trnsID, userLogin, ETSConstant.PROC, "SQLException Connection Error --> " + ex.getMessage(), ex);
			}
		}
		
		return flag;
	}
	
	public boolean editStudentDetail(StudentRequest stdReqs) {
		
		String trnsID = (stdReqs.getTrnsID() != null && !"".equals(stdReqs.getTrnsID())) ? stdReqs.getTrnsID() : "";
		String userLogin = (stdReqs.getUserLogin() != null && !"".equals(stdReqs.getUserLogin())) ? stdReqs.getUserLogin() : "";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		boolean flag = false;
		int i = 1;
		try {
			// STEP 2: Register JDBC driver
			Class.forName(JDBC_DRIVER);

			// STEP 3: Open a connection
			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			// STEP 4: Execute a query
			StringBuffer sql = new StringBuffer("UPDATE STUDENT_DETAIL SET FULL_NAME = ?, ADDRESS = ?, MOBILE_NO = ?, DATE_OF_BIRTH = ?, POSTCODE = ?, DEPARTMENT = ?, AGE = ?, DESCRIPTION = ?, GENDER = ?, COUNTRY = ?, CITY = ?, WEB_SITE_URL = ?, EMAIL = ?, PHONE = ?, FACEBOOK_URL = ?, TWITTER_URL = ?, YOUTUBE_URL = ? ");
						
			if(stdReqs != null && stdReqs.getImageCode() != null && !"".equals(stdReqs.getImageCode())){
				sql.append(", IMAGE_CODE = ?, IMAGE_THUMB = ?, IMAGE_HEIGHT = ?, IMAGE_WIDTH = ? ");
			}
			
			sql.append(", UPDT_DTTM = ?, UPDT_BY = ? WHERE 1 = 1 AND STUDENT_ID = ? ");
			
			LogUtil.log(logger, Priority.INFO_INT, trnsID, userLogin, ETSConstant.PROC, "SQL -- >" + sql.toString(), null);
			pstmt = conn.prepareStatement(sql.toString());
			
			//Basic Information
			pstmt.setString(i++, stdReqs.getFullName());
			pstmt.setString(i++, stdReqs.getAddress());
			pstmt.setString(i++, stdReqs.getMobileNo());
			pstmt.setString(i++, stdReqs.getDateOfBirth());
			pstmt.setString(i++, stdReqs.getPostCode());
			
			pstmt.setString(i++, stdReqs.getDepartment());
			pstmt.setInt(i++, stdReqs.getAge());
			pstmt.setString(i++, stdReqs.getDescription());
			pstmt.setString(i++, stdReqs.getGender());
			pstmt.setString(i++, stdReqs.getCountry());
			pstmt.setString(i++, stdReqs.getCity());
			pstmt.setString(i++, stdReqs.getWebsiteURL());
			
			//Account Information
			pstmt.setString(i++, stdReqs.getEmail());
			pstmt.setString(i++, stdReqs.getPhone());
			
			//Social Information
			pstmt.setString(i++, stdReqs.getFacebookURL());
			pstmt.setString(i++, stdReqs.getTwitterURL());
			pstmt.setString(i++, stdReqs.getYoutubeURL());
			
			if(stdReqs != null && stdReqs.getImageCode() != null && !"".equals(stdReqs.getImageCode())){
				pstmt.setString(i++, stdReqs.getImageCode());
				pstmt.setString(i++, stdReqs.getImageThumb());
				pstmt.setInt(i++, stdReqs.getImageHeight());
				pstmt.setInt(i++, stdReqs.getImageWidth());
			}
			
			pstmt.setTimestamp(i++, new Timestamp(System.currentTimeMillis()));
			pstmt.setString(i++, stdReqs.getUpdtBy());
			
			pstmt.setInt(i++, stdReqs.getStudentID());
			
			int result = pstmt.executeUpdate();
			
			if(result > 0){
				LogUtil.log(logger, Priority.INFO_INT, trnsID, userLogin, ETSConstant.PROC, "Edit Success", null);
				flag = true;
			}else{
				LogUtil.log(logger, Priority.ERROR_INT, trnsID, userLogin, ETSConstant.PROC, "Edit Fail", null);
				flag = false;
			}
			// STEP 5: Extract data from result set
			
			// STEP 6: Clean-up environment
			
		} catch (SQLException ex) {
			LogUtil.log(logger, Priority.ERROR_INT, trnsID, userLogin, ETSConstant.PROC, "SQLException Error --> " + ex.getMessage(), ex);
		} catch (Exception ex) {
			LogUtil.log(logger, Priority.ERROR_INT, trnsID, userLogin, ETSConstant.PROC, "Exception Error --> " + ex.getMessage(), ex);
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
			} catch (SQLException ex) {
				LogUtil.log(logger, Priority.ERROR_INT, trnsID, userLogin, ETSConstant.PROC, "SQLException Prepare Error --> " + ex.getMessage(), ex);
			}// nothing we can do
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException ex) {
				LogUtil.log(logger, Priority.ERROR_INT, trnsID, userLogin, ETSConstant.PROC, "SQLException Connection Error --> " + ex.getMessage(), ex);
			}
		}
		
		return flag;
	}
	
	public boolean deleteStudentDetail(StudentRequest stdReqs) {
		
		String trnsID = (stdReqs.getTrnsID() != null && !"".equals(stdReqs.getTrnsID())) ? stdReqs.getTrnsID() : "";
		String userLogin = (stdReqs.getUserLogin() != null && !"".equals(stdReqs.getUserLogin())) ? stdReqs.getUserLogin() : "";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		boolean flag = false;
		int i = 1;
		try {
			// STEP 2: Register JDBC driver
			Class.forName(JDBC_DRIVER);

			// STEP 3: Open a connection
			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			// STEP 4: Execute a query
			StringBuffer sql = new StringBuffer("DELETE FROM STUDENT_DETAIL WHERE 1 = 1 AND STUDENT_ID = ? ");
			
			LogUtil.log(logger, Priority.INFO_INT, trnsID, userLogin, ETSConstant.PROC, "SQL -- >" + sql.toString(), null);
			pstmt = conn.prepareStatement(sql.toString());
			
			//Basic Information
			pstmt.setInt(i++, stdReqs.getStudentID());
			
			int result = pstmt.executeUpdate();
			
			if(result > 0){
				LogUtil.log(logger, Priority.INFO_INT, trnsID, userLogin, ETSConstant.PROC, "Delete Success", null);
				flag = true;
			}else{
				LogUtil.log(logger, Priority.ERROR_INT, trnsID, userLogin, ETSConstant.PROC, "Delete Fail", null);
				flag = false;
			}
			// STEP 5: Extract data from result set
			
			// STEP 6: Clean-up environment
			
		} catch (SQLException ex) {
			LogUtil.log(logger, Priority.ERROR_INT, trnsID, userLogin, ETSConstant.PROC, "SQLException Error --> " + ex.getMessage(), ex);
		} catch (Exception ex) {
			LogUtil.log(logger, Priority.ERROR_INT, trnsID, userLogin, ETSConstant.PROC, "Exception Error --> " + ex.getMessage(), ex);
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
			} catch (SQLException ex) {
				LogUtil.log(logger, Priority.ERROR_INT, trnsID, userLogin, ETSConstant.PROC, "SQLException Prepare Error --> " + ex.getMessage(), ex);
			}// nothing we can do
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException ex) {
				LogUtil.log(logger, Priority.ERROR_INT, trnsID, userLogin, ETSConstant.PROC, "SQLException Connection Error --> " + ex.getMessage(), ex);
			}
		}
		
		return flag;
	}
	
	private String addZero(String str, int length){
		String result="";
		if(str == null){
			str = "0";
		}
		for(int i = 0;i < (length-str.length());i++)
			result += "0";
		return result + str;
	}

}
