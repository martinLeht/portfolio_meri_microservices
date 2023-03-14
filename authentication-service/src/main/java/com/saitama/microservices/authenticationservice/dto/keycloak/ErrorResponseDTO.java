package com.saitama.microservices.authenticationservice.dto.keycloak;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponseDTO {

	private String error;
	
	@JsonProperty("error_description")
	private String errorDescription;
}
