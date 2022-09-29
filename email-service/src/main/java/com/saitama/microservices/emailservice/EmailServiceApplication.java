package com.saitama.microservices.emailservice;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class EmailServiceApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(EmailServiceApplication.class, args);
	}

}
