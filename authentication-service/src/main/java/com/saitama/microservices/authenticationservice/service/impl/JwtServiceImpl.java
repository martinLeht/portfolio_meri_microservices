package com.saitama.microservices.authenticationservice.service.impl;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.saitama.microservices.authenticationservice.entity.Authority;
import com.saitama.microservices.authenticationservice.entity.JwtRefreshToken;
import com.saitama.microservices.authenticationservice.entity.User;
import com.saitama.microservices.authenticationservice.exception.JwtTokenRefreshException;
import com.saitama.microservices.authenticationservice.exception.UserNotFoundException;
import com.saitama.microservices.authenticationservice.jwt.JwtUtil;
import com.saitama.microservices.authenticationservice.repository.JwtRefreshTokenRepository;
import com.saitama.microservices.authenticationservice.repository.UserRepository;
import com.saitama.microservices.authenticationservice.service.IJwtService;

@Service
public class JwtServiceImpl implements IJwtService {
	
	@Autowired
	private JwtRefreshTokenRepository refreshTokenRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Override
	public String getJwtToken(String username, String id, Set<Authority> authorities) {
		return jwtUtil.generateToken(username, id, authorities);
	}

	@Override
	public JwtRefreshToken getRefreshToken(String id) {
		JwtRefreshToken refreshToken= new JwtRefreshToken();
		
		Optional<User> userOpt = userRepository.findByUuid(UUID.fromString(id));
		if (userOpt.isEmpty()) {
			throw new UserNotFoundException("User not found with id: " + id);
		}
		
		refreshToken.setUser(userOpt.get());
		refreshToken.setExpiryDate(jwtUtil.getTokenValidity().plus(10, ChronoUnit.MINUTES));
		refreshToken.setToken(UUID.randomUUID().toString());
		
		refreshToken = refreshTokenRepository.save(refreshToken);
		
		return refreshToken;
	}
	
	public JwtRefreshToken verifyRefreshTokenValidity(JwtRefreshToken token) {
	    if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
			refreshTokenRepository.delete(token);
			throw new JwtTokenRefreshException(token.getToken(), "Refresh token was expired. Please make a new signin request");
	    }

	    return token;
	  }

	@Override
	public Optional<JwtRefreshToken> getRefreshTokenByToken(String token) {
		return refreshTokenRepository.findByToken(token);
	}

	@Override
	public String getUpdatedRefreshToken(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional
	@Override
	public void deleteByUserId(String id) {
		User user = userRepository.findByUuid(UUID.fromString(id)).get();
		this.refreshTokenRepository.deleteByUser(user);
	}
	
	@Transactional
	@Override
	public void deleteByUser(User user) {
		this.refreshTokenRepository.deleteByUser(user);
	}

}
