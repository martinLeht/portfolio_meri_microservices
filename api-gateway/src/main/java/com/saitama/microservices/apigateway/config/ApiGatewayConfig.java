package com.saitama.microservices.apigateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
public class ApiGatewayConfig {
	
	@Value("${authentication.api.url}")
	private String authenticationApiUrl;
	
	@Value("${blog.api.url}")
	private String blogApiUrl;
	
	@Value("${storage.api.url}")
	private String storageApiUrl;
	
	@Value("${portfoliodata.api.url}")
	private String portfolioDataApiUrl;
	
	
	@Bean
	public RouteLocator gatewayRouter(RouteLocatorBuilder routeBuilder) {
		
		return routeBuilder.routes()
				.route("authentication-service", pred -> { 
					return pred
							.path("/auth/**", "/access/**")
							.uri(authenticationApiUrl);
				})
				.route("blog-service", pred -> {
					return pred
							.path("/blog/**")
							.uri(blogApiUrl);
				})
				.route("storage-service", pred -> {
					return pred
							.path("/storage/**")
							.uri(storageApiUrl);
				})
				.route("portfolio-data-service", pred -> {
					return pred
							.path("/portfolio/**", "/comment/**")
							.uri(portfolioDataApiUrl);
				})
				.build();
	}
}
