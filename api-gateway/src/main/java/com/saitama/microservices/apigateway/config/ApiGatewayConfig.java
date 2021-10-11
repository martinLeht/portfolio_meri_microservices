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
		return routeBuilder.routes()
				.route("authentication-service", pred -> pred.path("/auth/**").filters(f -> f.filter(authFilter)).uri("lb://authentication-service"))
				.route("blog-service",pred -> pred.path("/blog/**").filters(f -> f.filter(authFilter)).uri("lb://blog-service"))
				.route("image-service", pred -> pred.path("/storage/**").filters(f -> f.filter(authFilter)).uri("lb://image-service"))
				.build();
	}
}
