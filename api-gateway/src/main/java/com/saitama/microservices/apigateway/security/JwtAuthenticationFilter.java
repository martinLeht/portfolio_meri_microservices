package com.saitama.microservices.apigateway.security;


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

import com.saitama.microservices.apigateway.error.JwtTokenExpiredException;
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
		ServerHttpRequest req = (ServerHttpRequest) exchange.getRequest();
		
		if (RouteValidator.isSecureRoute(req)) {			
			if (!headerContainsAuthorizationToken(req)) {
				ServerHttpResponse res = (ServerHttpResponse) exchange.getResponse();
				res.setStatusCode(HttpStatus.UNAUTHORIZED);
				
				return res.setComplete();
			}
			
			String token = this.extractTokenFromAuthorizationHeader(req);
			
			try {
				jwtUtil.validateJwt(token);
			} catch(JwtTokenMalformedException | JwtTokenMissingException e) {
				ServerHttpResponse res = exchange.getResponse();
				res.setStatusCode(HttpStatus.BAD_REQUEST);
				
				return res.setComplete();
			} catch (JwtTokenExpiredException e) {
				ServerHttpResponse res = exchange.getResponse();
				res.setStatusCode(HttpStatus.UNAUTHORIZED);
				
				return res.setComplete();
			}
			
			Claims claims = jwtUtil.getClaims(token);
			exchange.getRequest().mutate().header("id", String.valueOf(claims.get("id"))).build();
		}
		
		return chain.filter(exchange);
	}
	
	private boolean headerContainsAuthorizationToken(ServerHttpRequest req) {
		if (!req.getHeaders().containsKey("Authorization")) {
			return false;
		}
		
		String authHeader = req.getHeaders().getOrEmpty("Authorization").get(0);
		return authHeader != null && authHeader.startsWith("Bearer");
	}
	
	private String extractTokenFromAuthorizationHeader(ServerHttpRequest req) {
		String authHeader = req.getHeaders().getOrEmpty("Authorization").get(0);
		final String token = authHeader.substring(7);
		return token;	
	}

}
