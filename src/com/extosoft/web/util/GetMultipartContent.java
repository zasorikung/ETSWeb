package com.extosoft.web.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author Administrator
 */
public class GetMultipartContent {

	public static final String KEY_STATUS = "__STATUS_UPLOAD";
	public static final String KEY_FILE_NAME = "__FILE_NAME";
	public static final String KEY_ELEMENT_FILE = "__FILE_ELEMENT";
	//   
	public static final String STATUS_UPLOAD_COMPLETE = "0";
	public static final String STATUS_UPLOAD_FAIL = "-1";
	public static final String STATUS_FILE_FORMAT_FAIL = "-2";
	public static final String STATUS_FILE_NO_FOUND = "-3";

	public static Hashtable getNameValueField(HttpServletRequest request,
			String path) throws IOException, FileUploadException {
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		Hashtable ret = new Hashtable();
		long serviceTime = 0;
		if (isMultipart) {
			serviceTime = System.currentTimeMillis();
			FileItemFactory factory = new DiskFileItemFactory();
//			System.out.println("new DiskFileItemFactory --> " + (System.currentTimeMillis() - serviceTime) + " ms.");
			serviceTime = System.currentTimeMillis();
			ServletFileUpload upload = new ServletFileUpload(factory);
//			System.out.println("new ServletFileUpload --> " + (System.currentTimeMillis() - serviceTime) + " ms.");
			serviceTime = System.currentTimeMillis();
			List items = upload.parseRequest(request);
//			System.out.println("upload parseRequest --> " + (System.currentTimeMillis() - serviceTime) + " ms.");
			
			Iterator iter = items.iterator();
			String fname = (new SimpleDateFormat("yyyyMMDDHHmmsszzz")).format(new Date());
			
			FileItem item = null;
			String name = "";
			String value = "";
//			String fieldName = "";
			String fileName = "";
			
			while (iter.hasNext()) {
				item = (FileItem) iter.next();
				name = item.getFieldName();
				value = item.getString();
				ret.put(name, value);
				if (!item.isFormField()) {
//					fieldName = item.getFieldName();
					fileName = item.getName();
					if (path != null && !"".equals(path) && fileName != null
							&& !"".equals(fileName)) {
						fname = fname + fileName.substring(fileName.indexOf("."),
										fileName.length());
						File uploadedFile = new File(path + "/" + fname);
						ret.put("getName --> ", "" + fileName);
						ret.put("fName --> ", fname);
						try {
							item.write(uploadedFile);
						} catch (Exception e) {
							e.printStackTrace();
						}
					} else {
//						System.out.println("fileName=" + fileName);
						if(fileName != null && !"".equals(fileName)){
							if (fileName.lastIndexOf("\\") >= 0) {
								fname = fileName.substring(fileName
										.lastIndexOf("\\") + 1);
							} else {
								fname = fileName.substring(fileName
										.lastIndexOf("\\") + 2);
							}
//							String old_img_path = "/image_old.jpg";
//							String new_img_path = "/image_new.jpg";
//							resizeImage(old_img_path, "90%","200x", new_img_path);
							
//							resizeImage(fname, "70%", "200", "/image_new.jpg");
	
							serviceTime = System.currentTimeMillis();
							ret.put("fileName", "" + fname);
//							System.out.println("put fileName --> " + (System.currentTimeMillis() - serviceTime) + " ms.");
							serviceTime = System.currentTimeMillis();
							ret.put("fileInputStream", (InputStream) item
									.getInputStream());
//							System.out.println("put fileInputStream --> " + (System.currentTimeMillis() - serviceTime) + " ms.");
							serviceTime = System.currentTimeMillis();
							ret.put("fileSize", item.getSize());
//							System.out.println("put fileSize --> " + (System.currentTimeMillis() - serviceTime) + " ms.");
							serviceTime = System.currentTimeMillis();
							ret.put("fileContenType", item.getContentType());
//							System.out.println("put fileContenType --> " + (System.currentTimeMillis() - serviceTime) + " ms.");
							//if (fileName.toUpperCase().indexOf(".TXT") > 0) {
							
							serviceTime = System.currentTimeMillis();
							InputStream uploadedStream = item.getInputStream();
//							System.out.println("item getInputStream --> " + (System.currentTimeMillis() - serviceTime) + " ms.");
//							BufferedInputStream bis = new BufferedInputStream(
//									uploadedStream);
//							DataInputStream dis = new DataInputStream(bis);
//							String data = "";
//							while (dis.available() != 0) {
//								data += dis.readLine() + "\n";
//							}
//							DataInputStream in = new DataInputStream(uploadedStream);
//			    	   		BufferedReader rd = new BufferedReader(new InputStreamReader(in));
//			    	   		String data = "";
//			    	   		while((data = rd.readLine()) != null) {
//			    	   			data += data + "\n";
//			    	   		}
			    	   		
			    	   		
							String data = "";
							serviceTime = System.currentTimeMillis();
//				    	   	FileOutputStream fout = new FileOutputStream(yourPathtowriteto);
//				    	   	BufferedOutputStream bout = new BufferedOutputStream(fout);
				    	   	BufferedInputStream bin = new BufferedInputStream(uploadedStream);
//				    	   	System.out.println("new BufferedInputStream --> " + (System.currentTimeMillis() - serviceTime) + " ms.");
				    	   	byte buf[] = new byte[2048];
				    	   	
				    	   	serviceTime = System.currentTimeMillis();
				    	   	while ((bin.read(buf)) != -1){
				    	   		 data = data + new String(buf) + "\n";
				    	   	     //bout.write(buf);
				    	   	 }
//				    	   	bout.close();
//				    	   	System.out.println("data --> " + data);
				    	   	bin.close();
//				    	   	System.out.println("read byte data --> " + (System.currentTimeMillis() - serviceTime) + " ms.");
				    	   	serviceTime = System.currentTimeMillis();
							ret.put("file", data);
//							System.out.println("put file data --> " + (System.currentTimeMillis() - serviceTime) + " ms.");
//							rd.close();
							uploadedStream.close();
							ret.put("isFormatFile", "YES");
							//                  } else {
							//                     ret.put("isFormatFile", "NONE");
							//                  }
						}
					}

				}
			}
		}
		return ret;
	}

	
	private static void close(InputStream uploadedStream, DataInputStream in,
			BufferedReader br) {
		try {
			br.close();
		} catch (Exception ex) {
		}
		try {
			in.close();
		} catch (Exception ex) {
		}
		try {
			uploadedStream.close();
		} catch (Exception ex) {
		}
	}
	
	public static Hashtable getNameValueField(HttpServletRequest request)
			throws IOException, FileUploadException {
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		Hashtable<String, Object> ret = new Hashtable<String, Object>();
		if (isMultipart) {
			FileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			List items = upload.parseRequest(request);
			Iterator iter = items.iterator();
//			String fname = (new SimpleDateFormat("yyyyMMDDHHmmsszzz")).format(new Date());

			FileItem item = null;
			String name = "";
			String value = "";
			
			InputStream uploadedStream = null;
			DataInputStream in = null;
			BufferedReader br = null;
			
			String fileName = item.getName();
			ArrayList<String> lEleFile = null;
			String strLine = "";
			while (iter.hasNext()) {
				item = (FileItem) iter.next();
				name = item.getFieldName();
				value = item.getString();

				ret.put(KEY_STATUS, STATUS_FILE_NO_FOUND);

				if (!item.isFormField()) {
					fileName = item.getName();
					if (fileName.toUpperCase().endsWith(".TXT")) {
						lEleFile = new ArrayList<String>();
						try {
							uploadedStream = item.getInputStream();
							in = new DataInputStream(uploadedStream);
							br = new BufferedReader(new InputStreamReader(in));

							while ((strLine = br.readLine()) != null) {
								lEleFile.add(strLine);
							}
							ret.put(KEY_FILE_NAME, fileName);
							ret.put(KEY_STATUS, STATUS_UPLOAD_COMPLETE);
							ret.put(KEY_ELEMENT_FILE, lEleFile);
						} catch (Exception ex) {
							ex.printStackTrace();
							ret.put(KEY_STATUS, STATUS_UPLOAD_FAIL);
							return ret;
						} finally {
							close(uploadedStream, in, br);
						}
					} else {
						ret.put(KEY_STATUS, STATUS_UPLOAD_FAIL);
						return ret;
					}
				} else {
					ret.put(name, value);
				}
			}
		}
		return ret;
	}
}
