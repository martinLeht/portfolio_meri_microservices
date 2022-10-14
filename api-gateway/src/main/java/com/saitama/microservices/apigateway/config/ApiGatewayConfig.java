package com.saitama.microservices.apigateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
	
	@Value("${authentication.api.url}")
	private String authenticationApiUrl;
	
	@Value("${blog.api.url}")
	private String blogApiUrl;
	
	@Value("${storage.api.url}")
	private String storageApiUrl;
	
	@Value("${portfoliodata.api.url}")
	private String portfolioDataApiUrl;
	
	@Value("${comment.api.url}")
	private String commentApiUrl;
	
	
	@Autowired
	private JwtAuthenticationFilter authFilter;
	
	@Bean
	public RouteLocator gatewayRouter(RouteLocatorBuilder routeBuilder) {
		return routeBuilder.routes()
				.route("authentication-service", pred -> { 
					return pred
							.path("/auth/**", "/access/**")
							.filters(f -> f.filter(authFilter))
							.uri(authenticationApiUrl);
				})
				.route("blog-service", pred -> {
					return pred
							.path("/blog/**")
							.filters(f -> f.filter(authFilter))
							.uri(blogApiUrl);
				})
				.route("image-service", pred -> {
					return pred
							.path("/storage/**")
							.filters(f -> f.filter(authFilter))
							.uri(storageApiUrl);
				})
				.route("portfolio-data-service", pred -> {
					return pred
							.path("/portfolio/**")
							.filters(f -> f.filter(authFilter))
							.uri(portfolioDataApiUrl);
				})
				.route("comment-service", pred -> { 
					return pred
							.path("/comment/**")
							.filters(f -> f.filter(authFilter))
							.uri(commentApiUrl);
					
				})
				.build();
	}
}
