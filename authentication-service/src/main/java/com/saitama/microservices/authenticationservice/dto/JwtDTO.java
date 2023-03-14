package com.saitama.microservices.authenticationservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtDTO {

	private String accessToken;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String refreshToken;
	
}
