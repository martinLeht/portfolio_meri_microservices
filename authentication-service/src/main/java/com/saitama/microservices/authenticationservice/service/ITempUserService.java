package com.saitama.microservices.authenticationservice.service;

import java.util.UUID;

import com.saitama.microservices.authenticationservice.dto.AccessRequestDTO;
import com.saitama.microservices.authenticationservice.dto.LogoutDTO;
import com.saitama.microservices.authenticationservice.dto.TempUserDTO;
import com.saitama.microservices.authenticationservice.dto.TemporaryAccessGrantDTO;

public interface ITempUserService {

	TempUserDTO createTemporaryUser(TempUserDTO dto);
	TemporaryAccessGrantDTO authenticateTemporaryUserAccess(AccessRequestDTO accessRequestDto);
	TemporaryAccessGrantDTO requestTemporaryUserAccess(AccessRequestDTO accessRequestDto);
	TempUserDTO verifyTemporaryUser(UUID verificationToken, UUID userUuid);
	void logoutTemporaryUser(LogoutDTO logoutDto);
}
