package com.baasss.common.model;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Location")
public class Location {
	public Frequency location;
	public double longitude;
	public double latitude;
	public int no_of_bikes_available;
	public String location_name;
	public int no_of_docks_available;
	
	public enum Frequency {
		SANTACLARA, SANJOSE, FRUITDALE, SUNNYVALE, SANFRANCISCO
	}
	public Frequency getLocation() {
		return location;
	}

	public void setLocation(Frequency location) {
		this.location = location;
	}
	@Override
	public String toString() {
		return "\nStation Name: " + location_name +" \nAvailable Bikes: "+ no_of_bikes_available + "\nAvailable Docks: "+no_of_docks_available ;
				 }
/* getters and setters - can user later to make private
	public int getNo_of_docks_available() {
		return no_of_docks_available;
	}

	public void setNo_of_docks_available(int no_of_docks_available) {
		this.no_of_docks_available = no_of_docks_available;
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
	}*/	
	
}

//ssr