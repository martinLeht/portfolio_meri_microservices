package com.saitama.microservices.authenticationservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.saitama.microservices.authenticationservice.entity.JwtRefreshToken;
import com.saitama.microservices.authenticationservice.entity.User;

public interface JwtRefreshTokenRepository extends JpaRepository<JwtRefreshToken, Long> {

	@Override
    Optional<JwtRefreshToken> findById(Long id);

    Optional<JwtRefreshToken> findByToken(String token);
    
    Long deleteByUser(User user);
	
}
