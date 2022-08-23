package com.saitama.microservices.apigateway.security;


import org.springframework.http.HttpMethod;

public enum OpenRoute {
	LOGIN_ROUTE("/auth/login", HttpMethod.POST),
	TOKEN_REFRESH_ROUTE("/auth/token/refresh", HttpMethod.POST),
	BLOG_TAG_ROUTE("/blog/tag", HttpMethod.GET),
	BLOG_ROUTE("/blog", HttpMethod.GET),
	EXPERIENCE_ROUTE("/portfolio/experience", HttpMethod.GET);
	
	private String uri;
	private HttpMethod method;
	
	OpenRoute(String uri, HttpMethod method) {
		this.uri = uri;
		this.method = method;
	}
	
	public String getUri() {
		return uri;
	}
	
	public HttpMethod getMethod() {
		return method;
	}
}
