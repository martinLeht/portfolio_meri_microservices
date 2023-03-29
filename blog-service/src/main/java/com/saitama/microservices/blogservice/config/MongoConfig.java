package com.saitama.microservices.blogservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

import com.saitama.microservices.blogservice.model.UuidIdentifiedEventListener;

@Configuration
@EnableMongoAuditing
public class MongoConfig {

	@Bean
	public UuidIdentifiedEventListener uuidIdentifiedEntityEventListener() {
	    return new UuidIdentifiedEventListener();
	}
}
