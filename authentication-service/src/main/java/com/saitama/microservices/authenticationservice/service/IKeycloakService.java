package com.saitama.microservices.authenticationservice.service;

import com.saitama.microservices.authenticationservice.dto.JwtDTO;
import com.saitama.microservices.authenticationservice.dto.TempUserDTO;

public interface IKeycloakService {
	
	TempUserDTO registerTemporaryUser(String username, String accessUuid);
	
	void verifyTemporaryUserEmail(String userId, String username);
	
	JwtDTO authenticateTemporaryUser(String username, String accessUuid);
	
	void logoutTemporaryUser(String userId, String username);
	

}
