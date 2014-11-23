package com.baasss.common.controller;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.baasss.common.model.Location;
import com.baasss.common.model.Location.Frequency;
import com.baasss.common.model.User;
import com.baasss.common.config.SpringMongoConfig;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.MessageFactory;
import com.twilio.sdk.resource.instance.Message;
@Controller
public class BikeController {
	MongoClientURI uri;
	DB db;
	SpringMongoConfig mongoConfig=new SpringMongoConfig();
	MongoClient mongoClient=mongoConfig.getMongoClient();
	DBCollection locationCollection;
	String link ="mongodb://bharathnaggowda:m0ngodbPa$$@ds049150.mongolab.com:49150/bikesharedb";
	public static final String ACCOUNT_SID = "ACcb63f40bbffb27bf7881adbce8f4640e";
	public static final String AUTH_TOKEN = "3bda4c016a7d2f05c3b39d983d587ce5";
	 
@ModelAttribute("locations")
public Frequency[] locations() {
	return Frequency.values();
}
@RequestMapping(value="/home", method = RequestMethod.GET)
public String getBike(Model m) {
    m.addAttribute("location",new Location());
    m.addAttribute("user",new User());
    return "home";
}
@RequestMapping(value="/home", method = RequestMethod.POST)
public String fromLogin(Model m) {
    m.addAttribute("location",new Location());
    m.addAttribute("user",new User());
    return "home";
}
	
@RequestMapping(value="/loadmap", method=RequestMethod.POST)
public String submitForm(Location location, User user, Model m) {
		 db =mongoClient.getDB("bikesharedb");
		 locationCollection = db.getCollection("Location");
	     BasicDBObject searchQuery=new BasicDBObject();
	     searchQuery.put("Location_name",String.valueOf(location.getLocation()));
	     DBCursor cursor=locationCollection.find(searchQuery);
	        while(cursor.hasNext())
	            {
	                DBObject theUserObj=cursor.next();
	                BasicDBObject theBasicUserObject= (BasicDBObject)theUserObj;
	                location.setLatitude(theBasicUserObject.getDouble("latitude"));
	                location.setLongitude(theBasicUserObject.getDouble("longitude"));
	                location.setNo_of_bikes_available(theBasicUserObject.getInt("no_of_bikes_available"));
	                location.setLocation_name(theBasicUserObject.getString("Location_name"));
	                
	                //location.latitude=theBasicUserObject.getDouble("latitude");
	                //location.longitude=theBasicUserObject.getDouble("longitude");
	                //location.no_of_bikes_available=theBasicUserObject.getInt("no_of_bikes_available");
	                //location.location_name=theBasicUserObject.getString("Location_name");
	                m.addAttribute("message",location.toString());
	        		m.addAttribute("latitude",location.getLatitude());
	        		m.addAttribute("longitude",location.getLongitude());
	        		m.addAttribute("loc", location.getLocation_name());
	        		System.out.println("newcode");
	            }
	                return "home";
	}
@RequestMapping(value="/StationMap", method = RequestMethod.GET)
public String getStationMap(Model m) {
		db =mongoClient.getDB("bikesharedb");
	    locationCollection = db.getCollection("Location");
        BasicDBObject searchQuery=new BasicDBObject();
   	    BasicDBObject fields = new BasicDBObject();
   	    fields.put("no_of_bikes_available", 1);
        DBCursor cursor=locationCollection.find(searchQuery,fields);
        int no_of_bikes_available[] = new int[10];
        int i=0;
        while(cursor.hasNext())
            {
        	DBObject theUserObj=cursor.next();
            BasicDBObject theBasicUserObject= (BasicDBObject)theUserObj;		
            int x=theBasicUserObject.getInt("no_of_bikes_available");
            no_of_bikes_available[i]=x;
        	i++;
            }
      m.addAttribute("bike1",no_of_bikes_available[0]);
      m.addAttribute("bike2",no_of_bikes_available[1]);
      m.addAttribute("bike3",no_of_bikes_available[2]);
      m.addAttribute("bike4",no_of_bikes_available[3]);
      m.addAttribute("bike5",no_of_bikes_available[4]);
      m.addAttribute("bike6",no_of_bikes_available[5]);
      m.addAttribute("bike7",no_of_bikes_available[6]);
      m.addAttribute("bike8",no_of_bikes_available[7]);
      m.addAttribute("bike9",no_of_bikes_available[8]);
      m.addAttribute("bike10",no_of_bikes_available[9]); 
      return "StationMap";
}
@RequestMapping(value="/Registration", method = RequestMethod.GET)
public String regUser(Model m) {
   
    m.addAttribute("user",new User());
    return "Registration";
}

@RequestMapping(value="/Registration", method = RequestMethod.POST)
public String regLogin(Model m) {
   
    m.addAttribute("user",new User());
    return "Registration";
}


@RequestMapping(value="/registered", method=RequestMethod.POST)
public String register(User user, Model m) 
{
	try{
		m.addAttribute("user",new User());
    		m.addAttribute("location",new Location()); 
		String User = "User";
		uri=new MongoClientURI(link);
		mongoClient = new MongoClient(uri);
		db = mongoClient.getDB("bikesharedb");
		if (!db.collectionExists(User)) {
		    db.createCollection(User, new BasicDBObject());
		  }
		locationCollection = db.getCollection("User");
		BasicDBObject document = new BasicDBObject();
		document.put("name",user.name);
		document.put("email",user.email);
		document.put("username",user.username);
		document.put("password",user.password);
		document.put("mobileNo",user.mobileNo);
		document.put("bikesOwned",user.bikes_owned);
		
		locationCollection.insert(document);
				
	/*	 DBCursor cursor = locationCollection.find();
	        while(cursor.hasNext()) {
	            System.out.println(cursor.next());
	        }
	        */
	       return "home";
		
	}catch(UnknownHostException ex){
		ex.printStackTrace();
		return "Failure";
	}
}	
@RequestMapping(value="/login", method = RequestMethod.GET)
public String userLogin(Model m) {
   
    m.addAttribute("user",new User());
    return "login";
}

@RequestMapping(value="/validating", method = RequestMethod.POST)
public String validateLogin(User user, Model m)  {
   
    m.addAttribute("user",new User());
    m.addAttribute("location",new Location());  
  
    System.out.println(user.Loggingusername);
    System.out.println(user.Loggingpassword);
    try{
   
	uri=new MongoClientURI(link);
	mongoClient = new MongoClient(uri);
	db = mongoClient.getDB("bikesharedb");
	locationCollection = db.getCollection("User");
	BasicDBObject searchQuery = new BasicDBObject();
	searchQuery.put("username",user.Loggingusername);
	searchQuery.put("password",user.Loggingpassword);
	 DBCursor cursor = locationCollection.find(searchQuery);
	 if(cursor.hasNext()) {
		
	       return "home";
	   }
	 else 
		 {
		 System.out.println("not found");
		 return "Failure";
		 }
	 
	 
    }
    catch(UnknownHostException ex){
		ex.printStackTrace();
		return "Failure";
	}
    
   // return "home";
}
	

@RequestMapping(value="/sendcode", method=RequestMethod.POST)
public String sendMessage(Location location, User user, Model m) {
	
	try{
		mongoClient = new MongoClient(uri);
		db = mongoClient.getDB("bikesharedb");
		locationCollection = db.getCollection("Location");
	}catch(UnknownHostException ex){
		ex.printStackTrace();
	}

	BasicDBObject searchQuery = new BasicDBObject();
	searchQuery.put("Location_name", user.getLocation());
	DBCursor cursor = locationCollection.find(searchQuery);
	while(cursor.hasNext()){
		DBObject c =cursor.next();
		int availBikes = (Integer) c.get("no_of_bikes_available");
		int availDocs = (Integer) c.get("no_of_docks_available");
		c.put("no_of_bikes_available", availBikes - 1);
		c.put("no_of_docks_available", availDocs + 1);
		locationCollection.update(searchQuery,c);
	}

	
	TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);

		Random randomGenerator = new Random();
		int randomId1 = randomGenerator.nextInt(1000000000);
		int randomId2 = randomGenerator.nextInt(1000000000);
		String code = "bike"+randomId1+randomId2;
        
        // Build a filter for the MessageList
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("Body", code));
        params.add(new BasicNameValuePair("To", "+1"+user.getPhonenumber()));
        params.add(new BasicNameValuePair("From", "+19714074127"));
     
        MessageFactory messageFactory = client.getAccount().getMessageFactory();
        Message message = null;
		try {
			message = messageFactory.create(params);
		} catch (TwilioRestException e) {
			 //TODO Auto-generated catch block
			e.printStackTrace();
		}
		//m.addAttribute("message", message);
	    if(message == null) return "Failure";
	    else return "Success";
	}

}
	
	
