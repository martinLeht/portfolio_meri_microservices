package com.saitama.microservices.portfoliodataservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class PortfolioDataServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PortfolioDataServiceApplication.class, args);
	}

}
