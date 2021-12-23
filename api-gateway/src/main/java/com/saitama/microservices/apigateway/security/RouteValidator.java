package com.saitama.microservices.apigateway.security;

import org.springframework.http.server.reactive.ServerHttpRequest;

public class RouteValidator {

	public static boolean isSecureRoute(ServerHttpRequest req) {
		for (OpenRoute route : OpenRoute.values()) {
			if (req.getURI().getPath().contains(route.getUri()) && req.getMethod() == route.getMethod()) {
				return false;
			}
		}
		return true;
	}
}
