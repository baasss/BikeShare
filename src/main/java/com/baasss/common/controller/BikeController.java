package com.baasss.common.controller;

import java.net.UnknownHostException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.baasss.common.model.Location;
import com.baasss.common.model.Location.Frequency;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;


@Controller
@RequestMapping("/bikeshare")
public class BikeController {
	MongoClient mongoClient;
	MongoClientURI uri;
	DB db;
	DBCollection locationCollection; 
	String link ="mongodb://bharathnaggowda:m0ngodbPa$$@ds049150.mongolab.com:49150/bikesharedb";
	
@ModelAttribute("locations")
public Frequency[] locations() {
	return Frequency.values();
}

@RequestMapping(value="/home", method = RequestMethod.GET)
public String getBike(Model m) {
    m.addAttribute("location",new Location());
    return "home";
}
	
@RequestMapping(value="/home", method=RequestMethod.POST)
public String submitForm(Location location, Model m) {
	try{
			uri=new MongoClientURI(link);
			mongoClient =new MongoClient(uri);
			db =mongoClient.getDB("bikesharedb");
		    locationCollection = db.getCollection("Location");
	        BasicDBObject searchQuery=new BasicDBObject();
	        searchQuery.put("Location_name",String.valueOf(location.getLocation()));
	        DBCursor cursor=locationCollection.find(searchQuery);
	        while(cursor.hasNext())
	            {
	                DBObject theUserObj=cursor.next();
	                BasicDBObject theBasicUserObject= (BasicDBObject)theUserObj;
	                location.latitude=theBasicUserObject.getDouble("latitude");
	                location.longitude=theBasicUserObject.getDouble("longitude");
	                location.no_of_bikes_available=theBasicUserObject.getInt("no_of_bikes_available");
	                location.location_name=theBasicUserObject.getString("Location_name");
	                location.no_of_docks_available=theBasicUserObject.getInt("no_of_docks_available");
	                m.addAttribute("message",location.toString());
	        		m.addAttribute("latitude",location.latitude);
	        		m.addAttribute("longitude",location.longitude);
	            }
	        }
		catch(UnknownHostException ex){ }
	                return "home";
	}
}
	
	
