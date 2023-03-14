package com.saitama.microservices.authenticationservice.constant;

public enum Role {
	ADMIN("admin"),
	AUTHOR("author"),
	TEMPORARY_USER("temporary_user");
	
	private final String value;
	
	Role(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
}
