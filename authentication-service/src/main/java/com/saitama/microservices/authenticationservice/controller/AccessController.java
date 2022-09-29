package com.saitama.microservices.authenticationservice.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.saitama.microservices.authenticationservice.dto.AccessVerificationDTO;
import com.saitama.microservices.authenticationservice.dto.TempUserDTO;
import com.saitama.microservices.authenticationservice.service.ITempUserService;

@RestController
@RequestMapping("/access")
public class AccessController {
	
	private final ITempUserService tempUserServiceImpl;
	
	@Autowired
	public AccessController(ITempUserService tempUserService) {
		this.tempUserServiceImpl = tempUserService;
	}
	
	@PostMapping("/temp/verify")
	public TempUserDTO verifyTemporaryUserAccess(@RequestBody @Valid AccessVerificationDTO accessVerificationDto) {
		return tempUserServiceImpl.verifyUserAccess(accessVerificationDto);
	}

}
