package com.baasss.common.controller;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.baasss.common.config.SpringMongoConfig;

@Controller
@RequestMapping("/bikeshare")
public class BikeController {

	ApplicationContext ctx = 
          new AnnotationConfigApplicationContext(SpringMongoConfig.class);
	MongoOperations mongoOperation = (MongoOperations) ctx.getBean("mongoTemplate");
	
	@RequestMapping(value="/home", method = RequestMethod.GET)
	public String getBike(ModelMap model) {

		return "home";

	}
	
}