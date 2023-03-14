package com.saitama.microservices.authenticationservice.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginDTO {
	
	private String email;
	private String username;
	private String password;
}
