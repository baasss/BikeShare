package com.baasss.common.model;
import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Location")
public class Location {
	public Frequency location;
	private double longitude;
	private double latitude;
	private int no_of_bikes_available;
	private String location_name;
	private Date preffered_date;
	private String hiddenvalue;
	
	public String getHiddenvalue() {
		return hiddenvalue;
	}

	public void setHiddenvalue(String hiddenvalue) {
		this.hiddenvalue = hiddenvalue;
	}

	public Date getPreffered_date() {
		return preffered_date;
	}

	public void setPreffered_date(Date preffered_date) {
		this.preffered_date = preffered_date;
	}

	public enum Frequency {
		SANTACLARA, SANJOSE, FRUITDALE, SUNNYVALE, MILPITAS,CUPERTINO,SARATOGA,LOSGATOS,PALOALTO,SANCARLOS
	}
	public Frequency getLocation() {
		return location;
	}

	public void setLocation(Frequency location) {
		this.location = location;
	}
	@Override
	public String toString() {
		return "\nStation Name: " + location_name +" \nAvailable Bikes: "+ no_of_bikes_available  ;
				 }
	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public int getNo_of_bikes_available() {
		return no_of_bikes_available;
	}

	public void setNo_of_bikes_available(int no_of_bikes_available) {
		this.no_of_bikes_available = no_of_bikes_available;
	}

	public String getLocation_name() {
		return location_name;
	}

	public void setLocation_name(String location_name) {
		this.location_name = location_name;
	}
	
}

//ssr