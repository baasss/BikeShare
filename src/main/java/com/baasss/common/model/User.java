package com.baasss.common.model;

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
	public String bikes_owned;
	public String selslot;
	public String sendcode;
	public String confpassword;
	public String locationInput;
	
	public String getConfpassword() {
		return confpassword;
	}
	public void setConfpassword(String cnfpwd) {
	confpassword = cnfpwd;
	}
	public String getLocationInput() {
		return locationInput;
	}
	public void setLocationInput(String locIn) {
		locationInput = locIn;
		System.out.println(locIn);
		System.out.println(locationInput);
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
	public String getBikes_owned() {
		return bikes_owned;
	}
	public void setBikes_owned(String owned) {
		bikes_owned = owned;
	}
	public String getName() {
		return name;
	}
	public void setName(String n) {
		name = n;
	/*	System.out.println("name n:");
		System.out.println(n);
		System.out.println("name:");
		System.out.println(name);
		System.out.printf("set name::",name);
		System.out.println();
		*/
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String e) {
		
		email = e;
	/*	System.out.println("name e:");
		System.out.println(e);
		System.out.println("email:");
		System.out.println(email);
		System.out.printf("set email::",email);
		System.out.println();
		System.out.printf("this.email======",email);
		System.out.println();
		*/
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
