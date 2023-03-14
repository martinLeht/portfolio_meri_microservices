package com.saitama.microservices.authenticationservice.dto;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccessVerificationDTO {
	
	@NotEmpty
	private String userId;
	
	@NotEmpty
	private String verificationToken;
	
	private String callbackUrl;

}
