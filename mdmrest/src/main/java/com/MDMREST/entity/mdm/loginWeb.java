package com.MDMREST.entity.mdm;

public class loginWeb {
	String userName;
	String userPass;
	public loginWeb() {
		
	}
	
	public loginWeb(String userName, String userPass) {
		this.userName = userName;
		this.userPass = userPass;
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
}