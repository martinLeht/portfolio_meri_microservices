package com.saitama.microservices.authenticationservice.dto.keycloak;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtTokenDTO {
	
	@JsonProperty("access_token")
	private String accessToken;
	
	@JsonProperty("expires_in")
	private Integer expiresIn;
	
	@JsonProperty("refresh_expires_in")
	private Integer refreshExpiresIn;
	
	@JsonProperty("refresh_token")
	private String refreshToken;
	
	@JsonProperty("token_type")
	private String tokenType;
	
	@JsonProperty("id_token")
	private String idToken;
	
	@JsonProperty("not-before-policy")
	private Integer notBeforePolicy;
	
	@JsonProperty("session_state")
	private String sessionState;
	
	private String scope;

}
