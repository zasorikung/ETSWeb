package com.extosoft.web.init;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.PropertyConfigurator;

/**
 * Servlet implementation class for Servlet: Log4jInit
 *
 */
 public class ETSLog4jInit extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
	static final long serialVersionUID = 1L;
   
   	private static final String CONTENT_TYPE = "text/html";
    /* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public ETSLog4jInit() {
		super();
	}   	
	
	/**
	 * @see Servlet#init()
	 */
	public void init() throws ServletException {
		super.init();
		
		File f = new File(System.getProperty("user.home") + File.separator
				+ getInitParameter("ETSLog4jInitPropsFile"));
		if (!f.canRead()) {
			throw new ServletException("Init ETS log4j Exception");
		}
		
		System.out.println("Init ETS log4j path --> " + f.toString());
		//System.out.println("Init FishStore manage = " + System.getProperty("weblogic.Name"));
		//String log4jProp = f.toString() + File.separator + System.getProperty("weblogic.Name") + ".AuctionWebLog4j.properties";
		String log4jProp = f.toString() + File.separator + "ETSLog4j.properties";
		System.out.println("Init ETS log4j file --> " + log4jProp);
		PropertyConfigurator.configureAndWatch(log4jProp);
	}
	
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String mode = request.getParameter("mode");
		if("reload".equals(mode)){
	        response.setContentType(CONTENT_TYPE);
	        PrintWriter out = response.getWriter();
	        out.println("<html>");
	        out.println("<head><title>ETSLog4jInit</title></head>");
	        out.println("<body bgcolor=\"#ffffff\">");
	        init();
	        System.out.println("Init ETS Log4jInit Reload");
	        out.println("<p>The servlet has received a GET. This is the reload.</p>");
	        out.println("</body>");
	        out.println("</html>");
	        out.close();
		}
	}  	
	
    //Clean up resources
    public void destroy() {
    	
    } 	  	  	    
}