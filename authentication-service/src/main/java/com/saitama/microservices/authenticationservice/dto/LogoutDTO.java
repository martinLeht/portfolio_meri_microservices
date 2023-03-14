package com.saitama.microservices.authenticationservice.dto;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LogoutDTO {

	private UUID userId;
	private String username;
}
