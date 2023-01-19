package com.spring.ex02;

public class LoginVO {
	private String userID;
	private String userName;
	private String email;
	
	//email 추가해서, 결과 출력해보기.
	// 여러가지 설절을 추가로 더해야 함.
	
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
}
