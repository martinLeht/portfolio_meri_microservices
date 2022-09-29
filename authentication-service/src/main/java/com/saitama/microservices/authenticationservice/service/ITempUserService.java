package com.saitama.microservices.authenticationservice.service;

import java.util.UUID;

import com.saitama.microservices.authenticationservice.dto.AccessVerificationDTO;
import com.saitama.microservices.authenticationservice.dto.TempUserDTO;
import com.saitama.microservices.commonlib.service.IBaseReadWriteService;

public interface ITempUserService extends IBaseReadWriteService<TempUserDTO, UUID> {

	TempUserDTO verifyUserAccess(AccessVerificationDTO accessVerificationDto);
}
