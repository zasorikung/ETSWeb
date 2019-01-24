package com.extosoft.web.servlet;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileUploadException;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;

import sun.misc.BASE64Encoder;

import com.extosoft.web.bean.StudentRequest;
import com.extosoft.web.bean.StudentResponse;
import com.extosoft.web.db.ETSDB;
import com.extosoft.web.init.ETSConfigInit;
import com.extosoft.web.util.ETSConstant;
import com.extosoft.web.util.GetMultipartContent;
import com.extosoft.web.util.LogUtil;
import com.extosoft.web.util.ParameterValidator;
import com.extosoft.web.util.ResizeImage;

/**
 * Servlet implementation class for Servlet: EditEmployeesServlet
 *
 */
 public class EditStudentServlet extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
   static final long serialVersionUID = 1L;
   
  	private static Logger logger = Logger.getLogger(ETSConstant.LOGGER_NAME_EST_DEBUG);
	private static Logger loggerTrans = Logger.getLogger(ETSConstant.LOGGER_NAME_EST_TRNS);
	
    /* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public EditStudentServlet() {
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
		
		LogUtil.log(logger, Priority.INFO_INT, trnsID, userName, ETSConstant.PROC, "##### Start doGet EditStudentServlet #####", null);
		String errorCode = "";
		String errorMsg = "";
		String errorMessage = "";
		String pageForward = "";
		String mode = request.getParameter("mode");
		String studentID = request.getParameter("studentID");
		LogUtil.log(logger, Priority.INFO_INT, trnsID, userName, ETSConstant.INPUT, "Request Mode --> " + mode 
				+ ", StudentID --> " + studentID, null);
				
		if("edit".equals(mode)){

			ETSDB etsDB = new ETSDB();
			List<StudentResponse> stdRespList = null;
			StudentRequest stdReqs = new StudentRequest();
			stdReqs.setStudentID(Integer.parseInt(studentID));
			LogUtil.log(logger, Priority.INFO_INT, trnsID, userName, ETSConstant.PROC, "##### Start GetStudentDetail #####", null);
			serviceRequestTime = System.currentTimeMillis();
			LogUtil.log(logger, Priority.INFO_INT, trnsID, userName, ETSConstant.INPUT, "GetStudentDetail Request StudentID --> " + studentID, null);
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
			LogUtil.logTrns(loggerTrans, trnsID, userName, "EditStudentsServlet", "GetStudentDetail", errorCode, errorMsg, serviceResponseTime);
			LogUtil.log(logger, Priority.INFO_INT, trnsID, userName, ETSConstant.PROC, "##### End GetStudentDetail " + serviceResponseTime + " ms. #####", null);
			
			pageForward = "./edit-student.jsp";
			request.getSession().setAttribute(ETSConstant.SESSION_DATA_ETS_RESULT_EDIT_LIST,stdRespList);
			
		}else if("delete".equals(mode)){
			ETSDB etsDB = new ETSDB();
			StudentRequest stdReqs = new StudentRequest();
			stdReqs.setStudentID(Integer.parseInt(studentID));
			LogUtil.log(logger, Priority.INFO_INT, trnsID, userName, ETSConstant.PROC, "##### Start DeleteStudentDetail #####", null);
			serviceRequestTime = System.currentTimeMillis();
			LogUtil.log(logger, Priority.INFO_INT, trnsID, userName, ETSConstant.INPUT, "DeleteStudentDetail Request StudentID --> " + studentID, null);
			boolean flag = etsDB.deleteStudentDetail(stdReqs);
			
			if(flag){
				errorCode = "0";
				errorMessage = ETSConfigInit.getConfig(ETSConstant.ERROR_MESSAGE_DELETE_STUDENT_SUCCESSFULLY, "");
				LogUtil.log(logger, Priority.INFO_INT, trnsID, userName, ETSConstant.PROC, "DeleteStudentDetail Response Delete Student Successfully", null);
			}else{
				errorCode = "-1";
				errorMessage = ETSConfigInit.getConfig(ETSConstant.ERROR_MESSAGE_DELETE_STUDENT_FAIL, "");
				LogUtil.log(logger, Priority.INFO_INT, trnsID, userName, ETSConstant.PROC, "DeleteStudentDetail Response Delete Student Fail", null);
			}
			pageForward = "./AllStudentsServlet?message=" + errorMessage;
			serviceResponseTime = System.currentTimeMillis() - serviceRequestTime;
			LogUtil.logTrns(loggerTrans, trnsID, userName, "EditStudentsServlet", "DeleteStudentDetail", errorCode, errorMessage, serviceResponseTime);
			LogUtil.log(logger, Priority.INFO_INT, trnsID, userName, ETSConstant.PROC, "##### End DeleteStudentDetail " + serviceResponseTime + " ms. #####", null);
			
		}
		
		LogUtil.log(logger, Priority.INFO_INT, trnsID, userName, ETSConstant.PROC, "Redirect PageForward --> " + pageForward, null);
		responseTime = System.currentTimeMillis() - requestTime;
		LogUtil.log(logger, Priority.INFO_INT, trnsID, userName, ETSConstant.PROC, "##### End doGet EditStudentServlet " + responseTime + " ms. #####", null);
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
		
		LogUtil.log(logger, Priority.INFO_INT, trnsID, userName, ETSConstant.PROC, "##### Start doPost AddStudentServlet #####", null);
		
		String errorCode = "0";
		String errorMessage = "";
		String pageForward = "./edit-student.jsp";
		
		GetMultipartContent getRequest = new GetMultipartContent();
		Hashtable<String, Object> hashReqs = null;
		try {
			serviceRequestTime = System.currentTimeMillis();
			hashReqs = getRequest.getNameValueField(request, null);
			serviceResponseTime = System.currentTimeMillis() - serviceRequestTime;
			LogUtil.log(logger, Priority.INFO_INT, trnsID, userName, ETSConstant.PROC, "GetNameValueField Time --> " + serviceResponseTime + " ms.", null);
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String studentID = (String)hashReqs.get("studentID");
		String fullname = (String)hashReqs.get("fullname");
		String address = (String)hashReqs.get("address");
		String mobileno = (String)hashReqs.get("mobileno");
		String dateofbirth = (String)hashReqs.get("finish");
		String postcode = (String)hashReqs.get("postcode");
		String department = (String)hashReqs.get("department");
		String age = (String)hashReqs.get("age");
		String description = (String)hashReqs.get("description");
		String gender = (String)hashReqs.get("gender");
		String country = (String)hashReqs.get("country");
		String city = (String)hashReqs.get("city");
		String websiteURL = (String)hashReqs.get("websiteURL");
		
		LogUtil.log(logger, Priority.INFO_INT, trnsID, userName, ETSConstant.PROC, "##### Basic Information #####", null);
		LogUtil.log(logger, Priority.INFO_INT, trnsID, userName, ETSConstant.INPUT, "Request Full Name --> " + fullname 
				+ ", Address --> " + address
				+ ", Mobile No. --> " + mobileno 
				+ ", Date of Birth --> " + dateofbirth 
				+ ", Postcode --> " + postcode 
				+ ", Age --> " + age 
				+ ", Description --> " + description 
				+ ", Gender --> " + gender 
				+ ", Country --> " + country 
				+ ", City --> " + city
				+ ", Website URL --> " + websiteURL, null);
		
		String email = (String)hashReqs.get("email");
		String phone = (String)hashReqs.get("phone");
		
		LogUtil.log(logger, Priority.INFO_INT, trnsID, userName, ETSConstant.PROC, "#### Account Information ####", null);
		LogUtil.log(logger, Priority.INFO_INT, trnsID, userName, ETSConstant.INPUT, "Request Email --> " + email
				+ ", Phone --> " + phone, null);
		
		String facebookURL = (String)hashReqs.get("facebookURL");
		String twitterURL = (String)hashReqs.get("twitterURL");
		String youtubeURL = (String)hashReqs.get("youtubeURL");
		
		LogUtil.log(logger, Priority.INFO_INT, trnsID, userName, ETSConstant.PROC, "#### Social Information ####", null);
		LogUtil.log(logger, Priority.INFO_INT, trnsID, userName, ETSConstant.INPUT, "Request Facebook URL --> " + facebookURL
				+ ", Twitter URL --> " + twitterURL
				+ ", Youtube URL --> " + youtubeURL, null);
		
		if(fullname == null || "".equals(fullname)){
			errorCode = "-2";
			errorMessage = ETSConfigInit.getConfig(ETSConstant.ERROR_MESSAGE_PLEASE_ENTER_FULL_NAME, "");
		}else if(address == null || "".equals(address)){
			errorCode = "-2";
			errorMessage = ETSConfigInit.getConfig(ETSConstant.ERROR_MESSAGE_PLEASE_ENTER_ADDRESS, "");
		}else if(!(ParameterValidator.isValidMobileNumberWithPrefix(mobileno, false) || ParameterValidator.isValidMobileNumber(mobileno, false))){
			errorCode = "-2";
			errorMessage = ETSConfigInit.getConfig(ETSConstant.ERROR_MESSAGE_PLEASE_ENTER_MOBILE_NO, "");
		}else if(dateofbirth == null || "".equals(dateofbirth)){
			errorCode = "-2";
			errorMessage = ETSConfigInit.getConfig(ETSConstant.ERROR_MESSAGE_PLEASE_ENTER_DATE_OF_BIRTH, "");
		}else if(postcode == null || "".equals(postcode)){
			errorCode = "-2";
			errorMessage = ETSConfigInit.getConfig(ETSConstant.ERROR_MESSAGE_PLEASE_ENTER_POSTCODE, "");
		}else if(department == null || "".equals(department)){
			errorCode = "-2";
			errorMessage = ETSConfigInit.getConfig(ETSConstant.ERROR_MESSAGE_PLEASE_ENTER_DEPARTMENT, "");
		}else if(age == null || "".equals(age)){
			errorCode = "-2";
			errorMessage = ETSConfigInit.getConfig(ETSConstant.ERROR_MESSAGE_PLEASE_ENTER_AGE, "");
		}else if(description == null || "".equals(description)){
			errorCode = "-2";
			errorMessage = ETSConfigInit.getConfig(ETSConstant.ERROR_MESSAGE_PLEASE_ENTER_DESCRIPTION, "");
		}else if(gender == null || "".equals(gender) || "none".equals(gender)){
			errorCode = "-2";
			errorMessage = ETSConfigInit.getConfig(ETSConstant.ERROR_MESSAGE_PLEASE_SELECT_GENDER, "");
		}else if(country == null || "".equals(country) || "none".equals(country)){
			errorCode = "-2";
			errorMessage = ETSConfigInit.getConfig(ETSConstant.ERROR_MESSAGE_PLEASE_SELECT_COUNTRY, "");
		}else if(city == null || "".equals(city) || "none".equals(city)){
			errorCode = "-2";
			errorMessage = ETSConfigInit.getConfig(ETSConstant.ERROR_MESSAGE_PLEASE_SELECT_CITY, "");
		}else if(!ParameterValidator.isValidEmail(email, false)){
			errorCode = "-2";
			errorMessage = ETSConfigInit.getConfig(ETSConstant.ERROR_MESSAGE_PLEASE_ENTER_EMAIL, "");
		}else if(!(ParameterValidator.isValidMobileNumberWithPrefix(phone, false) || ParameterValidator.isValidMobileNumber(phone, false))){
			errorCode = "-2";
			errorMessage = ETSConfigInit.getConfig(ETSConstant.ERROR_MESSAGE_PLEASE_ENTER_PHONE, "");
		}else if(facebookURL == null || "".equals(facebookURL)){
			errorCode = "-2";
			errorMessage = ETSConfigInit.getConfig(ETSConstant.ERROR_MESSAGE_PLEASE_ENTER_FACEBOOK_URL, "");
		}else if(twitterURL == null || "".equals(twitterURL)){
			errorCode = "-2";
			errorMessage = ETSConfigInit.getConfig(ETSConstant.ERROR_MESSAGE_PLEASE_ENTER_TWITTER_URL, "");
		}else if(youtubeURL == null || "".equals(youtubeURL)){
			errorCode = "-2";
			errorMessage = ETSConfigInit.getConfig(ETSConstant.ERROR_MESSAGE_PLEASE_ENTER_YOUTUBE_URL, "");
		}else{
		
			InputStream itemPhoto = null;
			String imgstr = null;
			String thumbstr = null;
			long imgSize = 0;
			int height = 0;
			int width = 0;
			if(hashReqs.get("fileInputStream") != null){
				imgSize = (Long)hashReqs.get("fileSize");
				LogUtil.log(logger, Priority.INFO_INT, trnsID, userName, ETSConstant.PROC, "######### imgSize : " + imgSize + " #########", null);
				if(imgSize > 0 && !ParameterValidator.isValidFileSize("" + imgSize, false, 1L, 1024 * 6 * 1000L)){
					errorMessage = ETSConfigInit.getConfig(ETSConstant.ERROR_MESSAGE_UPLOAD_FILE_MAX, "");
					LogUtil.log(logger, Priority.INFO_INT, trnsID, userName, ETSConstant.OUTPUT, "Response errorMessage --> " + errorMessage, null);
					request.setAttribute(ETSConstant.SESSION_ERROR_MESSAGE, errorMessage);
					pageForward = "./edit-student.jsp";
					LogUtil.log(logger, Priority.INFO_INT, trnsID, userName, ETSConstant.PROC, "Redirect PageForward --> " + pageForward, null);
					responseTime = System.currentTimeMillis() - requestTime;
					LogUtil.logTrns(loggerTrans, trnsID, userName, "EditStudentServlet", "EditStudent", errorCode, "0".equals(errorCode) ? "Success" : "Fail", responseTime);
					LogUtil.log(logger, Priority.INFO_INT, trnsID, userName, ETSConstant.PROC, "##### End doPost EditStudentServlet " + responseTime + " ms. #####", null);
					RequestDispatcher view = request.getRequestDispatcher(pageForward);
					view.forward(request, response);
					return;
				}
				
				itemPhoto = (InputStream)hashReqs.get("fileInputStream");
				//long imageSize = (Long)hashReqs.get("fileSize");
				//String photoData = (String)hashReqs.get("file");

				BufferedImage originalImage = ImageIO.read(itemPhoto);
				BufferedImage thumbImage = null;
				
				ResizeImage rsIm = new ResizeImage();
	//			RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR
	//			RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY
	//			RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON
	//			thumbImage = rsIm.getScaledInstance(originalImage, 70, 105, RenderingHints.VALUE_INTERPOLATION_BILINEAR, true);

				thumbImage = rsIm.resizeImage(originalImage, 100, 100);
	//	        BufferedImage originalImage = ImageIO.read(itemPhoto);
	//			int type = originalImage.getType() == 0? BufferedImage.TYPE_INT_ARGB : originalImage.getType();
	//			System.out.println("type --> " + type);
	//			BufferedImage resizeImageJpg = resizeImageWithHint(originalImage, type);
	//			ImageIO.write(resizeImageJpg, "jpg", new File("c:\\image\\mkyong_jpg.jpg"));
	
		        height = originalImage.getHeight();
		        width = originalImage.getWidth();
		        
		        LogUtil.log(logger, Priority.INFO_INT, trnsID, userName, ETSConstant.PROC, "Height --> " + height, null);
		        LogUtil.log(logger, Priority.INFO_INT, trnsID, userName, ETSConstant.PROC, "Width --> " + width, null);
		        
		        imgstr = encodeToString(originalImage, "jpg");
		        thumbstr = encodeToString(thumbImage, "jpg");

	//	        System.out.println("imgstr --> " + imgstr);
	//	        System.out.println("thumbstr --> " + thumbstr);
	//			BufferedImage resizeImageHintJpg = resizeImageWithHint(originalImage, type);
	//			ImageIO.write(resizeImageHintJpg, "jpg", new File("c:\\image\\mkyong_hint_jpg.jpg"));
		        
	//	        BufferedImage newImg = null;
	//	        height = img.getHeight();
	//	        width = img.getWidth();
	//	        imgstr = encodeToString(img, "jpg");
	//	        System.out.println(imgstr);
	//	        newImg = decodeToImage(imgstr);
	//	        ImageIO.write(newImg, "png", new File("files/img/CopyOfTestImage.png"));
		        
		        ETSDB etsDB = new ETSDB();
		        StudentRequest stdReqs = new StudentRequest();
		        stdReqs.setStudentID(Integer.parseInt(studentID));
		        stdReqs.setFullName(fullname);
		        stdReqs.setAddress(address);
		        stdReqs.setMobileNo(mobileno);
		        stdReqs.setDateOfBirth(dateofbirth);
		        stdReqs.setPostCode(postcode);
		        stdReqs.setImageCode(imgstr);
		        stdReqs.setImageThumb(thumbstr);
		        stdReqs.setImageHeight(height);
		        stdReqs.setImageWidth(width);
		        stdReqs.setDepartment(department);
		        stdReqs.setAge(Integer.parseInt(age));
		        stdReqs.setDescription(description);
		        stdReqs.setGender(gender);
		        stdReqs.setCountry(country);
		        stdReqs.setCity(city);
		        stdReqs.setWebsiteURL(websiteURL);
	
		        //Account Information
		        stdReqs.setEmail(email);
		        stdReqs.setPhone(phone);
	
		        //Social Information
		        stdReqs.setFacebookURL(facebookURL);
		        stdReqs.setTwitterURL(twitterURL);
		        stdReqs.setYoutubeURL(youtubeURL);
				stdReqs.setUpdtBy("admin");
				
				LogUtil.log(logger, Priority.INFO_INT, trnsID, userName, ETSConstant.PROC, "##### Start EditStudentDetail #####", null);
				serviceRequestTime = System.currentTimeMillis();
				LogUtil.log(logger, Priority.INFO_INT, trnsID, userName, ETSConstant.INPUT, "EditStudentDetail Request Full Name --> " + fullname 
						+ ", Address --> " + address
						+ ", Mobile No. --> " + mobileno 
						+ ", Date of Birth --> " + dateofbirth 
						+ ", Postcode --> " + postcode 
						+ ", Age --> " + age 
						+ ", Description --> " + description 
						+ ", Gender --> " + gender 
						+ ", Country --> " + country 
						+ ", City --> " + city
						+ ", Website URL --> " + websiteURL
						+ ", Email --> " + email
						+ ", Phone --> " + phone
						+ ", Facebook URL --> " + facebookURL
						+ ", Twitter URL --> " + twitterURL
						+ ", Youtube URL --> " + youtubeURL, null);
				
		        boolean flag = etsDB.editStudentDetail(stdReqs);
		        
				if(flag){
					errorCode = "0";
					errorMessage = ETSConfigInit.getConfig(ETSConstant.ERROR_MESSAGE_EDIT_STUDENT_SUCCESSFULLY, "");
					pageForward = "./AllStudentsServlet?message=" + errorMessage;
					LogUtil.log(logger, Priority.INFO_INT, trnsID, userName, ETSConstant.PROC, "EditStudentDetail Response Edit Student Successfully", null);
				}else{
					errorCode = "-1";
					errorMessage = ETSConfigInit.getConfig(ETSConstant.ERROR_MESSAGE_EDIT_STUDENT_FAIL, "");
					pageForward = "./edit-student.jsp";
					LogUtil.log(logger, Priority.INFO_INT, trnsID, userName, ETSConstant.OUTPUT, "EditStudentDetail Response Edit Student Fail", null);

				}
				serviceResponseTime = (System.currentTimeMillis() - serviceRequestTime);
				LogUtil.logTrns(loggerTrans, trnsID, userName, "EditStudentServlet", "EditStudentDetail", errorCode, errorMessage, serviceResponseTime);
				LogUtil.log(logger, Priority.INFO_INT, trnsID, userName, ETSConstant.PROC, "##### End EditStudentDetail " + serviceResponseTime + " ms. #####", null);
			}else{
				errorCode = "-2";
				errorMessage = ETSConfigInit.getConfig(ETSConstant.ERROR_MESSAGE_PLEASE_UPLOAD_NEW_IMAGE, "");
				pageForward = "./edit-student.jsp";
			}
		}
		
		LogUtil.log(logger, Priority.INFO_INT, trnsID, userName, ETSConstant.PROC, "Response errorMessage --> " + errorMessage, null);
		LogUtil.log(logger, Priority.INFO_INT, trnsID, userName, ETSConstant.PROC, "Redirect PageForward --> " + pageForward, null);
		request.setAttribute(ETSConstant.SESSION_ERROR_MESSAGE, errorMessage);
		responseTime = System.currentTimeMillis() - requestTime;
		LogUtil.logTrns(loggerTrans, trnsID, userName, "EditStudentServlet", "EditStudent", errorCode, "0".equals(errorCode) ? "Success" : "Fail", responseTime);
		LogUtil.log(logger, Priority.INFO_INT, trnsID, userName, ETSConstant.PROC, "##### End doPost EditStudentServlet " + responseTime + " ms. #####", null);
		RequestDispatcher view = request.getRequestDispatcher(pageForward);
		view.forward(request, response);
		
	}
	
	public static String encodeToString(BufferedImage image, String type) {
        String imageString = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        try {
            ImageIO.write(image, type, bos);
            byte[] imageBytes = bos.toByteArray();

            BASE64Encoder encoder = new BASE64Encoder();
            imageString = encoder.encode(imageBytes);

            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageString;
    } 	  	    
}