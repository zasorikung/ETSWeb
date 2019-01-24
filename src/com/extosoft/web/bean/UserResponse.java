package com.extosoft.web.bean;

import java.io.Serializable;

public class UserResponse implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4421632888194519438L;
	private int userID;
	private String userName;
	private String userPass;
	private String userDttm;
	private String userBy;
	
	public UserResponse(){
		
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPass() {
		return userPass;
	}

	public void setUserPass(String userPass) {
		this.userPass = userPass;
	}

	public String getUserDttm() {
		return userDttm;
	}

	public void setUserDttm(String userDttm) {
		this.userDttm = userDttm;
	}

	public String getUserBy() {
		return userBy;
	}

	public void setUserBy(String userBy) {
		this.userBy = userBy;
	}

	
}
