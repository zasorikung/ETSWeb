package com.extosoft.web.bean;

import java.io.Serializable;

public class UserRequest implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6637566029522047926L;
	private String trnsID;
	private String userLogin;
	private int userID;
	private String userName;
	private String userPass;
	private String userEmail;
	private String userDttm;
	private String userBy;
	
	public UserRequest(){
		
	}

	public String getUserLogin() {
		return userLogin;
	}

	public void setUserLogin(String userLogin) {
		this.userLogin = userLogin;
	}

	public String getTrnsID() {
		return trnsID;
	}

	public void setTrnsID(String trnsID) {
		this.trnsID = trnsID;
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

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	
	
}
