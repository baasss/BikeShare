package com.baasss.common.config;

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

}
