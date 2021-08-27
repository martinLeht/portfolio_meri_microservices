package com.saitama.microservices.blogservice;

import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BlogServiceApplication {
	
    @PostConstruct
    public void init(){
        TimeZone.setDefault(TimeZone.getTimeZone("Europe/Helsinki"));
    }

	public static void main(String[] args) {
		SpringApplication.run(BlogServiceApplication.class, args);
	}

}
