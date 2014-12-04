package com.baasss.common.config;

import java.net.UnknownHostException;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

public class SpringMongoConfig {
    
    public MongoClient getMongoClient() {
    	MongoClient mongoClient=null ;
    	String link ="mongodb://bharathnaggowda:m0ngodbPa$$@ds049150.mongolab.com:49150/bikesharedb";
    	MongoClientURI uri =new MongoClientURI(link);
    	
    	try{
    		mongoClient = new MongoClient(uri);
    	}catch(UnknownHostException ex){
    		ex.printStackTrace();
    	}
		return mongoClient;
    }

}
