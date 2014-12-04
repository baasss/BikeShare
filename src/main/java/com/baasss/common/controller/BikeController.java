package com.baasss.common.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.baasss.common.mail.Email;
import com.baasss.common.model.Bike;
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
import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.MessageFactory;
import com.twilio.sdk.resource.instance.Message;

@Controller
public class BikeController {
	
	SpringMongoConfig mongoConfig=new SpringMongoConfig();
	MongoClient mongoClient=mongoConfig.getMongoClient();
	DB db =mongoClient.getDB("bikesharedb");
	DBCollection locationCollection = db.getCollection("Location");
	DBCollection userCollection = db.getCollection("User");
	DBCollection bikeCollection = db.getCollection("Bike");
	DBCollection availCollection = db.getCollection("Availability");
	public static final String ACCOUNT_SID = "ACcb63f40bbffb27bf7881adbce8f4640e";
	public static final String AUTH_TOKEN = "3bda4c016a7d2f05c3b39d983d587ce5";
	    
    JSONParser jsonParser = new JSONParser();
    JSONObject jsonObject = null;
	 
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
public String submitForm(Location location, Bike bike, User user, Model m) {
		 	 
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
	                
	                m.addAttribute("message",location.toString());
	        		m.addAttribute("latitude",location.getLatitude());
	        		m.addAttribute("longitude",location.getLongitude());
	        		m.addAttribute("loc", location.getLocation_name());
	        		System.out.println("newcode");
	            }

	        String selectedDate = null;
	        Date preffered_date = location.getPreffered_date();
	        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
	        if(preffered_date != null) {
	        	 selectedDate = sdf.format(preffered_date);
	        }
	        System.out.println("preffered_date-----> "+selectedDate);
	        if(selectedDate != null){
	        	m.addAttribute("preffered_date", selectedDate);
		        String selectedBikeId = null;
		        
		        DBCursor bikecursor=bikeCollection.find(searchQuery);
		        while(bikecursor.hasNext())
	            {
	                DBObject theUserObj=bikecursor.next();
	                BasicDBObject theBasicUserObject= (BasicDBObject)theUserObj;
	                bike.setBike_id(theBasicUserObject.getString("bike_id"));
	                selectedBikeId = theBasicUserObject.getString("bike_id");
	                
	        		m.addAttribute("bikeid", bike.toString());
	            }
		        
		        String bikeDate = selectedBikeId+location.getLocation()+"-"+selectedDate;
		        
		        String availResult = null;
		        BasicDBObject availQuery = new BasicDBObject();
		        BasicDBObject availField = new BasicDBObject();
		        availField.put(bikeDate, 1);
		        DBCursor availcursor = availCollection.find(availQuery,availField);
		        while (availcursor.hasNext()) {
		            BasicDBObject obj = (BasicDBObject) availcursor.next();
		            availResult = obj.getString(bikeDate);	
		        }	        
		        
		        if(availResult == null){
		        	insertAvailability(bikeDate);
		        	m.addAttribute("firstSlot", "True");
			        m.addAttribute("secondSlot", "True");
			        m.addAttribute("thirdSlot", "True");
		        }else{
		        	try {
			        	jsonObject = (JSONObject) jsonParser.parse(availResult);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		        	m.addAttribute("firstSlot", jsonObject.get("08-10"));
			        m.addAttribute("secondSlot", jsonObject.get("10-12"));
			        m.addAttribute("thirdSlot", jsonObject.get("12-14"));
		        }
	        }
	        
	                return "home";
	}

public void insertAvailability(String bikeDate){
	BasicDBObject document = new BasicDBObject().append(bikeDate,
            new BasicDBObject("08-10", "True")
    				  .append("10-12", "True")
    				  .append("12-14", "True"));
	
	availCollection.insert(document);
}

@RequestMapping(value="/StationMap", method = RequestMethod.GET)
public String getStationMap(Model m) {
	    
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
	m.addAttribute("user",new User());
		m.addAttribute("location",new Location()); 
	String User = "User";
	if (!db.collectionExists(User)) {
	    db.createCollection(User, new BasicDBObject());
	  }
	userCollection = db.getCollection("User");
	BasicDBObject document = new BasicDBObject();
	document.put("name",user.name);
	document.put("email",user.email);
	document.put("username",user.username);
	document.put("password",user.password);
	document.put("mobileNo",user.mobileNo);
	document.put("bikesOwned",user.bikes_owned);
	
	userCollection.insert(document);

	   return "home";
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
	BasicDBObject searchQuery = new BasicDBObject();
	searchQuery.put("username",user.Loggingusername);
	searchQuery.put("password",user.Loggingpassword);
	 DBCursor cursor = userCollection.find(searchQuery);
	 if(cursor.hasNext()) {
		
	       return "home";
	   }
	 else 
		 {
		 System.out.println("not found");
		 return "Failure";
		 }
}
	

@RequestMapping(value="/sendcode", method=RequestMethod.POST)
public String sendMessage(Location location, User user, Model m) {
	
	System.out.println("inside sendcode");
	ApplicationContext mailContext = 
            new ClassPathXmlApplicationContext("Spring-Mail.xml");
	
	Email mm = (Email) mailContext.getBean("Email");
	Message message = null;
	String phoneno = null;
	String mailid = null;
	BasicDBObject searchQuery = new BasicDBObject();
	searchQuery.put("username", "bharathnaggowda");
	DBCursor cursor = userCollection.find(searchQuery);
	while(cursor.hasNext()){
		DBObject theUserObj=cursor.next();
        BasicDBObject theBasicUserObject= (BasicDBObject)theUserObj;
        phoneno = theBasicUserObject.getString("mobileNo");
        mailid = theBasicUserObject.getString("email");
	}
	System.out.println("inside sendcode---->"+user.getSendcode()+" -- "+phoneno+" -- "+mailid +" -- ");
	if(user.getSendcode().equals("sendmail")){
		System.out.println("inside sendmail");
		mm.sendMail("cmpe273project.baass@gmail.com",
					mailid,
				   "Testing123", 
				   "Testing only \n\n Hello Spring Email Sender");
		m.addAttribute("successmessage","Bike access code is sent to your contact details");
		return "home";
		
	}else if(user.getSendcode().equals("sendmessage")){
		System.out.println("inside sendmessage");
		TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);

			Random randomGenerator = new Random();
			int randomId1 = randomGenerator.nextInt(1000000000);
			int randomId2 = randomGenerator.nextInt(1000000000);
			String code = "bike"+randomId1+randomId2;
	        
	        List<NameValuePair> params = new ArrayList<NameValuePair>();
	        params.add(new BasicNameValuePair("Body", code));
	        params.add(new BasicNameValuePair("To", "+1"+phoneno));
	        params.add(new BasicNameValuePair("From", "+19714074127"));
	     
	        MessageFactory messageFactory = client.getAccount().getMessageFactory();
	        
			try {
				message = messageFactory.create(params);
				m.addAttribute("successmessage","Bike access code is sent to your contact details");
			} catch (TwilioRestException e) {
				 //TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(message == null) return "Failure";
		    else return "home";
	}
	m.addAttribute("successmessage","Bike access code could not be sent");
	return "Failure";
	
	}

}
	
	
