package com.saitama.microservices.authenticationservice.controller;

import java.net.URI;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.saitama.microservices.authenticationservice.dto.AccessRequestDTO;
import com.saitama.microservices.authenticationservice.dto.AccessVerificationDTO;
import com.saitama.microservices.authenticationservice.dto.LogoutDTO;
import com.saitama.microservices.authenticationservice.dto.TempUserDTO;
import com.saitama.microservices.authenticationservice.dto.TemporaryAccessGrantDTO;
import com.saitama.microservices.authenticationservice.service.ITempUserService;

@RestController
@RequestMapping("/access")
public class AccessController {
	
	private final ITempUserService tempUserService;
	
	@Value("${verification.redirect.url}")
	private String verificationRedirectUrl;
	
	@Autowired
	public AccessController(ITempUserService tempUserService) {
		this.tempUserService = tempUserService;
	}
	
	@PostMapping("/temp/authenticate")
	public TemporaryAccessGrantDTO authenticateTemporaryUserAccess(@RequestBody @Valid AccessRequestDTO accessRequestDto) {
		return tempUserService.authenticateTemporaryUserAccess(accessRequestDto);
	}
	
	@PostMapping("/temp/request")
	public TemporaryAccessGrantDTO requestTemporaryUserAccess(@RequestBody @Valid AccessRequestDTO accessRequestDto) {
		return tempUserService.requestTemporaryUserAccess(accessRequestDto);
	}
	
	@PostMapping("/temp/verify")
	public ResponseEntity<Void> verifyTemporaryUserAndRedirect(@RequestBody AccessVerificationDTO accessVerificationDto){
	    TempUserDTO verifiedUser = tempUserService.verifyTemporaryUser(
	    		UUID.fromString(accessVerificationDto.getVerificationToken()), UUID.fromString(accessVerificationDto.getUserId()));
	    
	    String redirect = accessVerificationDto.getCallbackUrl() != null && !accessVerificationDto.getCallbackUrl().isBlank() ? accessVerificationDto.getCallbackUrl() : verificationRedirectUrl 
	    		+ "?tempUserId=" + verifiedUser.getUuid().toString()
	    		+ "&tempUsername=" + verifiedUser.getUsername();
	    
	    return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(redirect)).build();
	}
	
	@GetMapping("/temp/verify")
	public ResponseEntity<Void> verifyTemporaryUserAndRedirectByParams(@RequestParam String userId,
																	   @RequestParam String verificationToken,	
																	   @RequestParam(required = false) String callbackUrl){
	    TempUserDTO verifiedUser = tempUserService.verifyTemporaryUser(
	    		UUID.fromString(verificationToken), UUID.fromString(userId));
	    	    
	    String redirect = callbackUrl != null && !callbackUrl.isBlank() ? callbackUrl : verificationRedirectUrl; 
	    redirect = redirect + "?tempUserId=" + verifiedUser.getUuid().toString()
	    		+ "&tempUsername=" + verifiedUser.getUsername();
	    
	    return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(redirect)).build();
	}
	
	@PostMapping("/temp/logout")
	public ResponseEntity<Void> logoutTemporaryUser(@RequestBody LogoutDTO logoutDto){
	    tempUserService.logoutTemporaryUser(logoutDto);
		return ResponseEntity.status(HttpStatus.OK).build();
	}

}
