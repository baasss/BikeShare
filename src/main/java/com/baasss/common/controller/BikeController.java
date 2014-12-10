package com.baasss.common.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.baasss.common.mail.Email;
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
	DBCollection bikeListCollection = db.getCollection("BikeList");
	DBCollection availCollection = db.getCollection("Availability");
	
	
	public static final String ACCOUNT_SID = "ACcb63f40bbffb27bf7881adbce8f4640e";
	public static final String AUTH_TOKEN = "3bda4c016a7d2f05c3b39d983d587ce5";
	    
    JSONParser jsonParser = new JSONParser();
    JSONObject jsonObject = null;
    
    String selectedBikeId = null;
    String selectedDate = null;
    String bikeDate = null;
    String bikeOwnerId = null;
    Message message = null;
	String phoneno = null;
	String userphoneno = null;
	String usermailid = null;
	String bikeaccesscode = null;
	String ownername = null;
	String loggedinuser = null;
    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
	 
@ModelAttribute("locations")
public Frequency[] locations() {
	return Frequency.values();
}

@RequestMapping(value="/home", method = RequestMethod.GET)
public String getHome(Model m) {
	return "home";
	
}
@RequestMapping(value="/bookabike", method = RequestMethod.GET)
public String getBike(Model m) {
    m.addAttribute("location",new Location());
    m.addAttribute("user",new User());
    return "bookabike";
}
@RequestMapping(value="/bookabike", method = RequestMethod.POST)
public String fromLogin(Model m) {
    m.addAttribute("location",new Location());
    m.addAttribute("user",new User());
    return "bookabike";
}
	
@RequestMapping(value="/loadmap", method=RequestMethod.POST)
public String submitForm(Location location, User user, Model m) {
		 	 
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
	        		//location.setPreffered_date(null);
	        		System.out.println("newcode");
	            }
       
	        Date preffered_date = location.getPreffered_date();
	        if(preffered_date != null) {
	        	 selectedDate = sdf.format(preffered_date);
	        }
	        System.out.println("preffered_date-----> "+selectedDate);
	        if(selectedDate != null){
	        	m.addAttribute("preffered_date", selectedDate);
		        	System.out.println("inside if selectedBikeId != null");
		        	
		        	String bikeResult = null;
			        /*BasicDBObject bikeListQuery = new BasicDBObject();
			        BasicDBObject bikeListField = new BasicDBObject();
			        System.out.println("String.valueOf(location.getLocation())-----> "+String.valueOf(location.getLocation()));
			        bikeListField.put(String.valueOf(location.getLocation()), 1);
			        DBCursor bikelistcursor = bikeListCollection.find(bikeListQuery,bikeListField);
			        while (bikelistcursor.hasNext()) {
			        	System.out.println("inside while bikelistcursor has next-----> ");
			            BasicDBObject obj = (BasicDBObject) bikelistcursor.next();
			            bikeResult = obj.getString(String.valueOf(location.getLocation()));	
			        }*/	
			        
			        BasicDBObject bikeListQuery = new BasicDBObject();
			        bikeListQuery.put("Location_name", String.valueOf(location.getLocation()));
			  	  	DBCursor bikelistcursor = bikeListCollection.find(bikeListQuery);
			  	  	while (bikelistcursor.hasNext()) {
				  	  	DBObject theUserObj=bikelistcursor.next();
		                BasicDBObject theBasicUserObject= (BasicDBObject)theUserObj;
		                bikeResult = theBasicUserObject.getString(String.valueOf(location.getLocation()));
		                
				  		//System.out.println("bikelistcursor.next()---->"+bikelistcursor.next());
			  	  }
		        	System.out.println("list of bikes--->"+bikeResult);
		        	if(bikeResult == null){
		        		m.addAttribute("noavailablebikes", "No bikes available at the selected location");
		        	}else{
		        		String[] bikeList = bikeResult.split(",");
			        	for(int j=0; j< bikeList.length; j++){
			        		m.addAttribute("bikesid"+j, bikeList[j]);
			        		System.out.println("bike id--->"+bikeList[j]);
			        	}
			        	
			        	if(user.getSelbike() != null){
			        		
			        		/*DBCursor bikecursor=userCollection.find(searchQuery);
					        while(bikecursor.hasNext())
				            {
				                DBObject theUserObj=bikecursor.next();
				                BasicDBObject theBasicUserObject= (BasicDBObject)theUserObj;
				                user.setBike_id(theBasicUserObject.getString("bike_id"));
				                selectedBikeId = theBasicUserObject.getString("bike_id");
				                bikeOwnerId = theBasicUserObject.getString("username");
				                
				        		m.addAttribute("bikeid", theBasicUserObject.getString("bike_id"));
				            }*/
			        		m.addAttribute("bikeid", user.getSelbike());
			        		selectedBikeId = user.getSelbike();
					        bikeDate = user.getSelbike()+"-"+selectedDate;
			        		
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
					        	System.out.println("inside if(availResult == null)");
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
					        	m.addAttribute("firstSlot", jsonObject.get("fslot"));
						        m.addAttribute("secondSlot", jsonObject.get("sslot"));
						        m.addAttribute("thirdSlot", jsonObject.get("tslot"));
					        }
					        m.addAttribute("enableslot", "true");
			        	}
		        	}
		        	
		        	
		        	
		        //}else{
		        	//System.out.println("inside else selectedBikeId != null");
		        	//m.addAttribute("nobikes", "No bikes available for the selected location.");
		        	//m.addAttribute("preffered_date", "");
		        }
	        //}
	        
	                return "bookabike";
	}

public void insertAvailability(String bikeDate){
	BasicDBObject document = new BasicDBObject().append("bikeavail", bikeDate)
												.append(bikeDate, new BasicDBObject("fslot", "True")
												.append("sslot", "True")
												.append("tslot", "True"));
	
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

@RequestMapping(value="/returnOrCancel", method = RequestMethod.GET)
public String returnOrCancelBooking(User user, Model m)  {
    m.addAttribute("user",new User());
    m.addAttribute("location",new Location());  
    return "ReturnOrCancel";
}

@RequestMapping(value="/returnOrCancel", method = RequestMethod.POST)
public String afterReturnOrCancelBooking(User user, Model m)  {
	String bike_taken=null;
    m.addAttribute("user",new User());
    m.addAttribute("location",new Location());  
    
    //retrieving bike_taken to check if the user has taken a bike or not
    BasicDBObject searchQuery = new BasicDBObject();
    searchQuery.put("username",user.Loggingusername);
	DBCursor cursor = userCollection.find(searchQuery);
	 while(cursor.hasNext())
     {
 	 DBObject theUserObj=cursor.next();
     BasicDBObject theBasicUserObject= (BasicDBObject)theUserObj;
     bike_taken=theBasicUserObject.getString("bike_taken");
     }
	 //end
	 
	 //checking if the user exists or not
	 if(bike_taken==null)
	 {
		 m.addAttribute("message","PLEASE CHECK THE USERNAME ENTERED!!"); 
	 }
	 //end
	 
	 else{
	
		 if(!(bike_taken.equals("false")))//check if user has taken the bike
         {
    	 //check if the hirer took from first slot
    	 String temp=bike_taken+".fslot";
    	 System.out.println("temp"+temp);
         searchQuery=new BasicDBObject();
         searchQuery.put("bikeavail",bike_taken);
         searchQuery.put(temp,user.Loggingusername);
         BasicDBObject fields = new BasicDBObject();
 	     fields.put(temp, 1);
 	     DBCursor cursorA=availCollection.find(searchQuery,fields);
 	    while(cursorA.hasNext())
       {
       DBObject theUserObj=cursorA.next();
       BasicDBObject theBasicUserObject= (BasicDBObject)theUserObj;		
       DBObject avail=(BasicDBObject) theBasicUserObject.get(bike_taken);
       String u_name=(String)avail.get("fslot");
        if(u_name.equals(user.Loggingusername))
        {
        	searchQuery=new BasicDBObject();
            searchQuery.put("bikeavail",bike_taken);
        	availCollection.update(searchQuery, new BasicDBObject("$set",new BasicDBObject(temp,"True")));
        }
       }
 	  //end
 	    
 	  //check if hirer took from second slot    
 	  temp=bike_taken+".sslot";
 	  searchQuery=new BasicDBObject();
      searchQuery.put("bikeavail",bike_taken);
      searchQuery.put(temp,user.Loggingusername);
      fields = new BasicDBObject();
	  fields.put(temp, 1);
 	  cursorA=availCollection.find(searchQuery,fields); 
	  while(cursorA.hasNext())
    {   
    DBObject theUserObj=cursorA.next();
    BasicDBObject theBasicUserObject= (BasicDBObject)theUserObj;		
    DBObject avail=(BasicDBObject) theBasicUserObject.get(bike_taken);
    String u_name=(String)avail.get("sslot");
    if(u_name.equals(user.Loggingusername))
     {
     	searchQuery=new BasicDBObject();
        searchQuery.put("bikeavail",bike_taken);
     	availCollection.update(searchQuery, new BasicDBObject("$set",new BasicDBObject(temp,"True")));
     }
    }
	//end
	  
	  //check if hirer took from third slot
	    temp=bike_taken+".tslot";
	    searchQuery=new BasicDBObject();
        searchQuery.put("bikeavail",bike_taken);
        searchQuery.put(temp,user.Loggingusername);
        fields = new BasicDBObject();
	    fields.put(temp, 1);
	 	cursorA=availCollection.find(searchQuery,fields);
	    while(cursorA.hasNext())
	    {
		System.out.println("inside while");
	    DBObject theUserObj=cursorA.next();
	    BasicDBObject theBasicUserObject= (BasicDBObject)theUserObj;		
	    DBObject avail=(BasicDBObject) theBasicUserObject.get(bike_taken);
	    String u_name=(String)avail.get("tslot");
	    if(u_name.equals(user.Loggingusername))
	     {
	     	searchQuery=new BasicDBObject();
	        searchQuery.put("bikeavail",bike_taken);
	     	availCollection.update(searchQuery, new BasicDBObject("$set",new BasicDBObject(temp,"True")));
	     }
	    }
        //end
	    
	    //finding the owner and location
    	int startIndex = bike_taken.indexOf('-');
    	System.out.println("indexOf(-) = " + startIndex);
    	int endIndex = bike_taken.lastIndexOf('-');
    	System.out.println("LastIndexOf(-) = " + endIndex);
    	String str1=bike_taken.substring(startIndex + 1, endIndex);
    	String[] splitString = str1.split("-");
		String bike_owner = splitString[0];
		bike_owner=bike_owner.toLowerCase();
    	System.out.println(bike_owner);
    	String location =splitString[1];
    	System.out.println(location);
    	//end

    	//set the bike taken as false in user collection
	    searchQuery=new BasicDBObject();
        searchQuery.put("username",user.Loggingusername);
     	BasicDBObject document = new BasicDBObject(); 
        document.put("bike_taken","false");
        userCollection.update(searchQuery,new BasicDBObject("$set",document));
        //end
  
        //obtain phone number
        searchQuery=new BasicDBObject();
        searchQuery.put("username",bike_owner);
        fields = new BasicDBObject();
	    fields.put("mobileNo", 1);
	    cursorA=userCollection.find(searchQuery,fields);
	    DBObject theUserObj=cursorA.next();
	    BasicDBObject theBasicUserObject= (BasicDBObject)theUserObj;		
	    String phoneno=theBasicUserObject.getString("mobileNo");
	    System.out.println(phoneno);
	    //end
	    
	    //increment bikes in location
	    searchQuery=new BasicDBObject();
        searchQuery.put("Location_name",location);
        fields = new BasicDBObject();
	    fields.put("no_of_bikes_available", 1);
	    cursorA=locationCollection.find(searchQuery,fields);
	    theUserObj=cursorA.next();
	    theBasicUserObject= (BasicDBObject)theUserObj;		
	    int temp_available_bikes=theBasicUserObject.getInt("no_of_bikes_available")+1;
	    document = new BasicDBObject(); 
        document.put("no_of_bikes_available",temp_available_bikes);
        locationCollection.update(searchQuery,new BasicDBObject("$set",document));
        //end

        //send a message to owner that bike is returned
        System.out.println("inside sendmessage");
		Message message = null;
        TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);
        List<NameValuePair> params = new ArrayList<NameValuePair>();
	        params.add(new BasicNameValuePair("Body", "Your bike has been returned"));
	        params.add(new BasicNameValuePair("To", "+1"+phoneno));
	        params.add(new BasicNameValuePair("From", "+19714074127"));
	        MessageFactory messageFactory = client.getAccount().getMessageFactory();
	        try {
				message = messageFactory.create(params);
				m.addAttribute("successmessage","Bike access code is sent to your contact details");
			} catch (TwilioRestException e) {
				e.printStackTrace();
			}
	     //end
	        
     	m.addAttribute("message","SUCCESS!!");     
     }
     else
     {
    	 m.addAttribute("message","U HAVE NOT BOOKED ANY BIKE"); 
     }
	 }
     
	 return "ReturnOrCancel";
}

@RequestMapping(value="/Registration", method = RequestMethod.GET)
public String regUser(Model m) {
	
    m.addAttribute("user",new User());
    return "Registration";
}

@RequestMapping(value="/Registration", method = RequestMethod.POST)
public String regLogin(Model m, User user) {
	
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
	boolean found = false;
	userCollection = db.getCollection("User");
	BasicDBObject document = new BasicDBObject();
	BasicDBObject searchQuery = new BasicDBObject();
	searchQuery.put("username",user.username);
	System.out.println(user.username);
	 DBCursor cursor = userCollection.find(searchQuery);
	 if(cursor.hasNext()) {
		 		
		 found = true;
		 m.addAttribute("found",found);
			
		   return "Registration";
	   }
	document.put("name",user.name);
	document.put("email",user.email);
	document.put("username",user.username);
	document.put("password",user.password);
	document.put("mobileNo",user.mobileNo);
	document.put("bike_taken","false");
	document.put("Location_name",user.locationInput);
	Random rn = new Random();
	int number ;
	number = rn.nextInt(2000);
	document.put("bikeaccesscode",number);
	int id;
	id = rn.nextInt(100);
	String bikeId = ("BIKE-"+user.username+"-"+id);
	System.out.println(bikeId);
	document.put("bike_id",bikeId);
	userCollection.insert(document);

	   return "bookabike";
}	
@RequestMapping(value="/login", method = RequestMethod.GET)
public String userLogin(Model m) {
   
    m.addAttribute("user",new User());
    return "login";
}

@RequestMapping(value="/login", method = RequestMethod.POST)
public String frmLogout(Model m) {
   
    m.addAttribute("user",new User());
    return "login";
}

@RequestMapping(value="/sessionLogout", method = RequestMethod.POST)
public String userLogout(HttpServletRequest request,HttpServletResponse response, User user , Model m, BindingResult result)
{
	response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
	response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
	response.setDateHeader("Expires", 0); // Proxies.
	
	Cookie cookie = null;
	   Cookie[] cookies = null;
	   // Get an array of Cookies associated with this domain
	   cookies = request.getCookies();
	   if( cookies != null ){
		   System.out.println("<h2> Found Cookies Name and Value</h2>");
	      for (int i = 0; i < cookies.length; i++){
	         cookie = cookies[i];
	        // if((cookie.getName( )).compareTo("user") == 0 ){
	            cookie.setMaxAge(0);
	            response.addCookie(cookie);
	           System.out.print("Deleted cookie: " + 
	            cookie.getName( ) + "<br/>");
	       //  }
	         System.out.print("Name : " + cookie.getName( ) + ",  ");
	         System.out.print("Value: " + cookie.getValue( )+" <br/>");
	      }
	  }else{
		  System.out.println(
	      "<h2>No cookies founds</h2>");
	  }

	
	return "login";
}

@RequestMapping(value="/validating", method = RequestMethod.POST)
public String validateLogin(HttpServletRequest request,HttpServletResponse response, User user , Model m, BindingResult result)  {
	response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
	response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
	response.setDateHeader("Expires", 0); // Proxies.
	
    m.addAttribute("user",new User());
    
    m.addAttribute("location",new Location());  
    loggedinuser = user.Loggingusername;
    System.out.println(user.Loggingusername);
    System.out.println(user.Loggingpassword);
    if (result.hasErrors()) {
		System.out.print("abc");
	    return "login";
	}
	else{

BasicDBObject searchQuery = new BasicDBObject();
searchQuery.put("username",user.Loggingusername);
searchQuery.put("password",user.Loggingpassword);
 DBCursor cursor = userCollection.find(searchQuery);
 if(cursor.hasNext()) {
	 
	 Cookie loginCookie = new Cookie("user",user.Loggingusername);
		loginCookie.setMaxAge(30*60);
		String s = loginCookie.getValue();
		
		response.addCookie(loginCookie);
		m.addAttribute("a",s);
		System.out.println("inside create cookie-->"+s);
	   return "bookabike";
   }
 else 
	 {
	 System.out.println("not found");
	 return "Failure";
	 }
 
	}
    
   // return "bookabike";
}


@RequestMapping(value="/sendcode", method=RequestMethod.POST)
public String sendMessage(Location location, User user, Model m) {
	
	System.out.println("inside sendcode");
	ApplicationContext mailContext = 
            new ClassPathXmlApplicationContext("Spring-Mail.xml");
	
	Email mm = (Email) mailContext.getBean("Email");
	
	BasicDBObject searchQuery = new BasicDBObject();
	searchQuery.put("bike_id", selectedBikeId);
	DBCursor cursor = userCollection.find(searchQuery);
	while(cursor.hasNext()){
		DBObject theUserObj=cursor.next();
        BasicDBObject theBasicUserObject= (BasicDBObject)theUserObj;
        phoneno = theBasicUserObject.getString("mobileNo");
        ownername = theBasicUserObject.getString("username");
        bikeaccesscode = theBasicUserObject.getString("bikeaccesscode");
	}
	
	BasicDBObject searchUserQuery = new BasicDBObject();
	searchUserQuery.put("username", loggedinuser);
	DBCursor usercursor = userCollection.find(searchUserQuery);
	while(usercursor.hasNext()){
		DBObject theUserObj=usercursor.next();
        BasicDBObject theBasicUserObject= (BasicDBObject)theUserObj;
        userphoneno = theBasicUserObject.getString("mobileNo");
        usermailid = theBasicUserObject.getString("email");
        bikeaccesscode = theBasicUserObject.getString("bikeaccesscode");
	}
	System.out.println("inside sendcode---->"+selectedBikeId+user.getSendcode()+bikeDate+" -- "+userphoneno+" -- "+usermailid +" -- ");
    
	if(location.getPreffered_date() != null) {
   	 selectedDate = sdf.format(location.getPreffered_date());
   }
	//bikeDate = user.getSelbike()+"-"+selectedDate;
	
	System.out.println("bikeDate after" +user.getLoggingusername()+"method call ---> "+loggedinuser);
	
	String selectedSlot = user.getSelslot();
	BasicDBObject change = new BasicDBObject("bikeavail", bikeDate);   
	BasicDBObject setDoc = new BasicDBObject();
	setDoc.append(bikeDate+"."+selectedSlot, loggedinuser);                                         
	BasicDBObject account = new BasicDBObject("$set", setDoc);
	availCollection.update(change, account);
	System.out.println("jsonObject in sendcode=--"+selectedSlot+"--->"+jsonObject);
	
	if(user.getSendcode().equals("sendmail")){
		System.out.println("inside sendmail");
		mm.sendMail("cmpe273project.baass@gmail.com",
				usermailid,
				   "Bike access code", 
				   "Use below code to unlock the bike \n\n "+bikeaccesscode);
		m.addAttribute("successmessage","Bike access code is sent to your contact details");
		sendMessageToOwner(bikeDate, phoneno, loggedinuser, ownername);
		return "bookabike";
		
	}else if(user.getSendcode().equals("sendmessage")){
		System.out.println("inside sendmessage");
		TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);
	        
	        List<NameValuePair> params = new ArrayList<NameValuePair>();
	        params.add(new BasicNameValuePair("Body", "Use below code to unlock the bike \n\n "+bikeaccesscode));
	        params.add(new BasicNameValuePair("To", "+1"+userphoneno));
	        params.add(new BasicNameValuePair("From", "+19714074127"));
	     
	        MessageFactory messageFactory = client.getAccount().getMessageFactory();
	        
			try {
				message = messageFactory.create(params);
				m.addAttribute("successmessage","Bike access code is sent to your contact details");
				sendMessageToOwner(bikeDate, phoneno, loggedinuser, ownername);
			} catch (TwilioRestException e) {
				 //TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(message == null) return "Failure";
		    else return "bookabike";
	}
	m.addAttribute("successmessage","Bike access code could not be sent");
	return "Failure";
	
	}

public void sendMessageToOwner(String bikeidDate, String phoneno, String username, String ownername){
	TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);
    
    List<NameValuePair> params = new ArrayList<NameValuePair>();
    params.add(new BasicNameValuePair("Body", "Your bike is rented "+ username));
    params.add(new BasicNameValuePair("To", "+1"+phoneno));
    params.add(new BasicNameValuePair("From", "+19714074127"));
 
    MessageFactory messageFactory = client.getAccount().getMessageFactory();
    
	try {
		message = messageFactory.create(params);
		
		BasicDBObject change = new BasicDBObject("username", username);   
		BasicDBObject setDoc = new BasicDBObject();
		setDoc.append("bike_taken", bikeidDate);                                         
		BasicDBObject account = new BasicDBObject("$set", setDoc);
		userCollection.update(change, account);
		
	} catch (TwilioRestException e) {
		 //TODO Auto-generated catch block
		e.printStackTrace();
	}
}

}
	
	
