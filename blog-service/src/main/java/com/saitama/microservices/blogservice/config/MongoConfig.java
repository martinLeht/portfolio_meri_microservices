package com.saitama.microservices.blogservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@Configuration
@EnableMongoAuditing
public class MongoConfig {

	
	
//	@Bean
//    public MongoClient mongo() {
//        ConnectionString connectionString = new ConnectionString("mongodb://localhost:27017/test");
//        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
//          .applyConnectionString(connectionString)
//          .build();
//        
//        return MongoClient.create(mongoClientSettings);
//    }
//	
//	@Bean
//    public MongoTemplate mongoTemplate() throws Exception {
//        return new MongoTemplate(mongo(), "test");
//    }
}
