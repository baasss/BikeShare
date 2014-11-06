package com.baasss.common.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "User")
public class User {
	
	private String phonenumber;
	
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
