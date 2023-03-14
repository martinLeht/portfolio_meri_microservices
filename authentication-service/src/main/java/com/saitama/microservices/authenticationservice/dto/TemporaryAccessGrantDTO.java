package com.saitama.microservices.authenticationservice.dto;

import java.util.UUID;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TemporaryAccessGrantDTO {
	
	private String accessToken;
	private UUID userId;
	private String username;
}
