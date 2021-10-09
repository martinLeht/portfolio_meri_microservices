package com.saitama.microservices.authenticationservice.jwt;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.saitama.microservices.authenticationservice.error.JwtTokenMalformedException;
import com.saitama.microservices.authenticationservice.error.JwtTokenMissingException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtUtil {
	
	private static final Logger LOG = LoggerFactory.getLogger(JwtUtil.class.getName());

//	@Value("${jwt.secret}")
//	private String jwtSecret;
//	
//	@Value("${jwt.token.validity}")
//	private long tokenValidity;
	
	@Autowired
	private Environment env;
	
	
	public Claims getClaims(final String token) {
		try {
			String jwtSecret = env.getProperty("jwt.secret");
			Claims body = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
			return body;
		} catch (Exception e) {
			LOG.debug(e.getMessage() + " => " + e);
		}
		return null;
	}
	
	public String generateToken(String userName) {
		Claims claims = Jwts.claims().setSubject(userName);
		long nowMillis = System.currentTimeMillis();
		long tokenValidity = Long.parseLong(env.getProperty("jwt.token.validity"));
		long expMillis = nowMillis + tokenValidity;
		Date expirationDate = new Date(expMillis);
		String jwtSecret = env.getProperty("jwt.secret");
		return Jwts.builder()
					.setClaims(claims)
					.setIssuedAt(new Date(nowMillis))
					.setExpiration(expirationDate)
					.signWith(SignatureAlgorithm.HS512, jwtSecret)
					.compact();
				
	}
	
	public void validateJwt(final String token) {
		try {
			String jwtSecret = env.getProperty("jwt.secret");
			LOG.info(jwtSecret);
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
		} catch (SignatureException ex) {
			throw new JwtTokenMalformedException("Invalid JWT signature");
		} catch (MalformedJwtException ex) {
			throw new JwtTokenMalformedException("Invalid JWT token");
		} catch (ExpiredJwtException ex) {
			throw new JwtTokenMalformedException("Expired JWT token");
		} catch (UnsupportedJwtException ex) {
			throw new JwtTokenMalformedException("Unsupported JWT token");
		} catch (IllegalArgumentException ex) {
			throw new JwtTokenMissingException("JWT claims string is empty.");
		}
	}
 }
