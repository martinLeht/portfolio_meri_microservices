package com.saitama.microservices.authenticationservice.service.impl;

import java.util.UUID;

import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.UserRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.saitama.microservices.authenticationservice.dto.JwtDTO;
import com.saitama.microservices.authenticationservice.dto.TempUserDTO;
import com.saitama.microservices.authenticationservice.keycloak.KeycloakClient;
import com.saitama.microservices.authenticationservice.service.IKeycloakService;
import com.saitama.microservices.commonlib.exception.CommonInternalException;

@Service
public class KeycloakServiceImpl implements IKeycloakService {

	private static final Logger LOG = LoggerFactory.getLogger(KeycloakServiceImpl.class);

	private final KeycloakClient keycloakClient;
	
	
	@Autowired
	public KeycloakServiceImpl(KeycloakClient keycloakClient) {
		this.keycloakClient = keycloakClient;
	}


	@Override
	public TempUserDTO registerTemporaryUser(String username, String accessUuid) {
		UserRepresentation tempUser = keycloakClient.registerTemporaryUser(username, accessUuid);
		return TempUserDTO.builder()
				.accessUuid(UUID.fromString(accessUuid))
				.keycloakUserId(tempUser.getId())
				.verified(tempUser.isEmailVerified())
				.locked(!tempUser.isEnabled())
				.username(tempUser.getUsername())
				.build();
	}


	@Override
	public void verifyTemporaryUserEmail(String userId, String username) {
		keycloakClient.verifyTemporaryUserEmail(userId, username);
	}


	@Override
	public JwtDTO authenticateTemporaryUser(String username, String accessUuid) {
		AccessTokenResponse jwtResponse = keycloakClient.loginTemporaryUser(username, accessUuid);
		return new JwtDTO(jwtResponse.getToken(), jwtResponse.getRefreshToken());
	}


	@Override
	public void logoutTemporaryUser(String userId, String username) {
		try {
			keycloakClient.logoutTemporaryUser(userId, username);
		} catch (Exception e) {
			throw new CommonInternalException("failed-to-logout", "Error on logout attempt from", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	

}
