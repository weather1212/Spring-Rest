package com.hoseong.spring.vo.message;

public class UserVO {

	private String userid; // 사용자ID
	private String userpw; // 사용자PW
	private String username; // 사용자이름
	private int userpoint; // 사용자적립포인트

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getUserpw() {
		return userpw;
	}

	public void setUserpw(String userpw) {
		this.userpw = userpw;
	}

	public String getUsername() { 
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getUserpoint() {
		return userpoint;
	}

	public void setUserpoint(int userpoint) {
		this.userpoint = userpoint;
	}

}
