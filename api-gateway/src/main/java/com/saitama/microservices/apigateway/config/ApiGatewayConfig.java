package com.saitama.microservices.apigateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.saitama.microservices.apigateway.security.JwtAuthenticationFilter;

@Configuration
public class ApiGatewayConfig {

	//private static final String ALLOWED_HEADERS = "x-requested-with, authorization, Content-Type, Authorization, credential, X-XSRF-TOKEN";
	private static final String ALLOWED_METHODS = "GET, PUT, POST, DELETE, OPTIONS";
	private static final String ALLOWED_ORIGIN = "*";
	private static final String MAX_AGE = "3600";
	
	@Autowired
	private JwtAuthenticationFilter authFilter;
	
	@Bean
	public RouteLocator gatewayRouter(RouteLocatorBuilder routeBuilder) {
		return routeBuilder.routes()
				.route("authentication-service", pred -> pred.path("/auth/**").filters(f -> f.filter(authFilter))
						.uri("http://authentication-service.authentication-service.svc.cluster.local:80"))
				.route("blog-service", pred -> pred.path("/blog/**").filters(f -> f.filter(authFilter))
						.uri("http://blog-service.blog-service.svc.cluster.local:80"))
				.route("image-service", pred -> pred.path("/storage/**").filters(f -> f.filter(authFilter))
						.uri("http://storage-service.storage-service.svc.cluster.local:80"))
				.route("portfolio-data-service", pred -> pred.path("/portfolio/**").filters(f -> f.filter(authFilter))
						.uri("http://portfolio-data-service.portfolio-data-service.svc.cluster.local:80"))
				.route("comment-service", pred -> pred.path("/comment/**").filters(f -> f.filter(authFilter))
						.uri("http://comment-service.comment-service.svc.cluster.local:80"))
				.build();
	}
}
