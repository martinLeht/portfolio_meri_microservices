package com.saitama.microservices.apigateway.security;

import java.util.List;
import java.util.function.Predicate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.saitama.microservices.apigateway.error.JwtTokenMalformedException;
import com.saitama.microservices.apigateway.error.JwtTokenMissingException;
import com.saitama.microservices.apigateway.jwt.JwtUtil;

import io.jsonwebtoken.Claims;
import reactor.core.publisher.Mono;

@Component
public class JwtAuthenticationFilter implements GatewayFilter {

	private static final Logger LOG = LoggerFactory.getLogger(JwtAuthenticationFilter.class.getName());

	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		LOG.info("----------IN FILTER---------");
		ServerHttpRequest req = (ServerHttpRequest) exchange.getRequest();
		
		final List<String> apiEndpoints = List.of("/auth/register", "/auth/login", "/storage/upload", "/image-service/storage/delete/files");
		
		Predicate<ServerHttpRequest> isApiSecured = r -> apiEndpoints.stream().noneMatch(uri -> {
			LOG.info("isApiSecured uri:");
			LOG.info(uri);
			return r.getURI().getPath().contains(uri);
		});
		
		LOG.info(Boolean.toString(apiEndpoints.stream().noneMatch(uri -> req.getURI().getPath().contains(uri))));
		
		LOG.info("URI:");
		LOG.info(req.getURI().getPath().toString());
		if (isApiSecured.test(req)) {
			LOG.info("----------AFTER IS API SEC---------");
			if (!req.getHeaders().containsKey("Authorization")) {
				ServerHttpResponse res = (ServerHttpResponse) exchange.getResponse();
				res.setStatusCode(HttpStatus.UNAUTHORIZED);
				
				return res.setComplete();
			}
			
			final String token = req.getHeaders().getOrEmpty("Authorization").get(0);
			
			try {
				LOG.info("VALIDATING TOKEN");
				jwtUtil.validateJwt(token);
			} catch(JwtTokenMalformedException | JwtTokenMissingException e) {
				ServerHttpResponse res = exchange.getResponse();
				res.setStatusCode(HttpStatus.BAD_REQUEST);
				
				return res.setComplete();
			}
			
			Claims claims = jwtUtil.getClaims(token);
			exchange.getRequest().mutate().header("id", String.valueOf(claims.get("id"))).build();
		}
		LOG.info("----------SUORAAN---------");
		
		return chain.filter(exchange);
	}

}
