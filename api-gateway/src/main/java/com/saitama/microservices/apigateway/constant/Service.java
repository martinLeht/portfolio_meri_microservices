package com.saitama.microservices.apigateway.constant;

import java.util.HashMap;
import java.util.Map;

public enum Service {
	ACCESS_SERVICE("access"),
	AUTH_SERVICE("auth"),
	BLOG_SERVICE("blog"), 
	COMMENT_SERVICE("comment"), 
	PORTFOLIO_DATA_SERVICE("portfolio"),
	STORAGE_SERVICE("storage");
	
	private final String name;
	
	Service(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	private static final Map<String, Service> servicesMap;
	
    static {
    	servicesMap = new HashMap<String, Service>();
        for (Service v : Service.values()) {
        	servicesMap.put(v.name, v);
        }
    }
    
    public static Service findByName(String name) {
        return servicesMap.get(name);
    }
	
}
