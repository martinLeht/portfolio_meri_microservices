package com.saitama.microservices.apigateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.saitama.microservices.apigateway.security.JwtAuthenticationFilter;

@Configuration
public class ApiGatewayConfig {

	@Autowired
	private JwtAuthenticationFilter authFilter;
	
	@Bean
	public RouteLocator gatewayRouter(RouteLocatorBuilder routeBuilder) {
		/*
		return routeBuilder.routes()
				.route("auth", 
						pred -> pred.path("/auth/**").filters(f -> f.filter(authFilter)).uri("lb://auth"))
				.route("blog", 
						pred -> pred.path("/blog/**").filters(f -> f.filter(authFilter)).uri("lb://blog"))
				.route("storage", 
						pred -> pred.path("/storage/**").filters(f -> f.filter(authFilter)).uri("lb://storage"))
				.build();
		*/
		return routeBuilder.routes()
				.route(pred -> pred.path("/authentication-service/**").filters(f -> f.filter(authFilter)).uri("lb://authentication-serivce"))
				.route(pred -> pred.path("/blog/**")/*.filters(f -> f.filter(authFilter))*/.uri("lb://blog"))
				.route(pred -> pred.path("/image-service/**").filters(f -> f.filter(authFilter)).uri("lb://image-service"))
				.build();
	}
}
