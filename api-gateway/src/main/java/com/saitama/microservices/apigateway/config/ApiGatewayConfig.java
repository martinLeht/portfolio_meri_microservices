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
				.route("authentication-service", pred -> pred.path("/auth/**").filters(f -> f.filter(authFilter)).uri("lb://authentication-service"))
				.route("blog-service",pred -> pred.path("/blog/**").filters(f -> f.filter(authFilter)).uri("lb://blog-service"))
				.route("image-service", pred -> pred.path("/storage/**").filters(f -> f.filter(authFilter)).uri("lb://image-service"))
				.build();
	}
	
	/*
	@Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedHeaders("*")
                .allowedMethods(ALLOWED_METHODS);
    }

    @Bean
    public CorsWebFilter corsWebFilter() {
    	System.out.println("IN CORS WEB FILTES");
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod(ALLOWED_METHODS);
        corsConfiguration.addAllowedOrigin("*");
        UrlBasedCorsConfigurationSource corsConfigurationSource = new UrlBasedCorsConfigurationSource();
        corsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsWebFilter(corsConfigurationSource);
    }
	
	
	@Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods(ALLOWED_METHODS);
    }

	
	@Bean
	public WebFilter corsFilter() {
		return (ServerWebExchange ctx, WebFilterChain chain) -> {
			ServerHttpRequest request = ctx.getRequest();
			if (CorsUtils.isCorsRequest(request)) {
				ServerHttpResponse response = ctx.getResponse();
		    	HttpHeaders headers = response.getHeaders();
		    	headers.add("Access-Control-Allow-Origin", ALLOWED_ORIGIN);
		    	headers.add("Access-Control-Allow-Methods", ALLOWED_METHODS);
		    	headers.add("Access-Control-Max-Age", MAX_AGE);
		    	if (request.getMethod() == HttpMethod.OPTIONS) {
		    		  response.setStatusCode(HttpStatus.OK);
		    		  return Mono.empty();
		    	}
			}
			return chain.filter(ctx);
	    };
	}
	*/
}
