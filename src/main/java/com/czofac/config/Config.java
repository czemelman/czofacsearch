package com.czofac.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.czofac.persistence.PersistOfacData;
import com.czofac.persistence.PersistOfacDataMongo;

@Configuration
//@ComponentScan({"com.czofac","com.czofac.persistence","com.czofac.data.elements", "com.czofac.file"})
public class Config {
	@Bean() 
    public PersistOfacData persistOfacData() {
		//returning default PersistOfac implementation
		//for MongoDb
        return new PersistOfacDataMongo();
    }
}
