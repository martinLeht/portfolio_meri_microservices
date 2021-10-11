package com.saitama.microservices.authenticationservice.service;

import java.util.Optional;

import com.saitama.microservices.authenticationservice.entity.JwtRefreshToken;

public interface IJwtService {
	
	public String getJwtToken(String id);

	public JwtRefreshToken getRefreshToken(String id);
	
	public Optional<JwtRefreshToken> getRefreshTokenByToken(String token);
	
	public String getUpdatedRefreshToken(String id);
	
	public JwtRefreshToken verifyRefreshTokenValidity(JwtRefreshToken token);
	
	public void deleteByUserId(String id);
}
