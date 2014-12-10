package com.baasss.common.model;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "User")
public class User {
	
	private String phonenumber;
	public String Loggingusername;
	public String Loggingpassword;
	public String name;
	public String email;
	public String username;
	public String password;
	public String mobileNo;
	public String selslot;
	public String sendcode;
	private String bike_id;
	private Date preffered_date;
	private String selbike;
	public String confpassword;
	public String locationInput;
	
	public String getConfpassword() {
		return confpassword;
	}
	public void setConfpassword(String confpassword) {
		this.confpassword = confpassword;
	}
	public String getLocationInput() {
		return locationInput;
	}
	public void setLocationInput(String locationInput) {
		this.locationInput = locationInput;
	}


	
	public String getSelbike() {
		return selbike;
	}
	public void setSelbike(String selbike) {
		this.selbike = selbike;
	}
	public Date getPreffered_date() {
		return preffered_date;
	}
	public void setPreffered_date(Date preffered_date) {
		this.preffered_date = preffered_date;
	}
	public String getBike_id() {
		return bike_id;
	}
	public void setBike_id(String bike_id) {
		this.bike_id = bike_id;
	}
	public String getSelslot() {
		return selslot;
	}
	public void setSelslot(String selslot) {
		this.selslot = selslot;
	}
	public String getSendcode() {
		return sendcode;
	}
	public void setSendcode(String sendcode) {
		this.sendcode = sendcode;
	}
	
	public String getLoggingusername() {
		return Loggingusername;
	}
	public void setLoggingusername(String userLogin) {
		Loggingusername = userLogin;
	}
	public String getLoggingpassword() {
		return Loggingpassword;
	}
	public void setLoggingpassword(String pwdLogin) {
		Loggingpassword = pwdLogin;
	}

	public String getName() {
		return name;
	}
	public void setName(String n) {
		name = n;

	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String e) {
		
		email = e;

	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String uname) {
	username = uname;
		//System.out.printf("set username::",username);
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String pwd) {
		password = pwd;
		// System.out.printf("set password::",password);
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mNo) {
		mobileNo = mNo;
	}

	
	
	public String getPhonenumber() {
		return phonenumber;
	}
	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}
	
	private String location;

	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	
}
