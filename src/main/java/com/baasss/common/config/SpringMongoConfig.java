package com.baasss.common.config;

import java.net.UnknownHostException;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;


public class SpringMongoConfig {
   
    public @Bean
    MongoTemplate mongoTemplate() throws Exception{
    	
    	String textUri = "mongodb://bharathnaggowda:m0ngodbPa$$@ds049150.mongolab.com:49150/bikesharedb";
        MongoClientURI uri = new MongoClientURI(textUri);
        
        MongoTemplate mongoTemplate = new MongoTemplate(new MongoClient(uri), "bikesharedb");
    		return mongoTemplate;
     
      
    }
    
    DBCollection DBCollection() {
    	
    	DB db;
    	MongoClient mongoClient = null;
    	DBCollection locationCollection = null; 
    	String link ="mongodb://bharathnaggowda:m0ngodbPa$$@ds049150.mongolab.com:49150/bikesharedb";
    	MongoClientURI uri =new MongoClientURI(link);
    	
    	try{
    		mongoClient = new MongoClient(uri);
    		db = mongoClient.getDB("bikeshared");
    		locationCollection = db.getCollection("Location");
    	}catch(UnknownHostException ex){
    		ex.printStackTrace();
    	}
		return locationCollection;
    }

}
