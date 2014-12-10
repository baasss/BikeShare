package com.baasss.common.model;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Bike")
public class Bike {
	public Frequency location;
	private String bike_id;
	private String location_name;
	
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
		return bike_id  ;
				 }

	public String getBike_id() {
		return bike_id;
	}

	public void setBike_id(String bike_id) {
		this.bike_id = bike_id;
	}

	public String getLocation_name() {
		return location_name;
	}

	public void setLocation_name(String location_name) {
		this.location_name = location_name;
	}
	
}
