package com.saitama.microservices.authenticationservice.dto;

public class JwtDTO {

	private String accessToken;
	
	private String refreshToken;
	
	public JwtDTO() { }

	public JwtDTO(String accessToken, String refreshToken) {
		super();
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	@Override
	public String toString() {
		return "JwtDto [accessToken=" + accessToken + ", refreshToken=" + refreshToken + "]";
	}
	
}
