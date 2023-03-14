package com.saitama.microservices.commonlib.dto;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import lombok.Data;

@Data
public class UserDTO {

	private UUID id;
	private String email;
	private String username;
	private Set<RoleDTO> authorities;
	private LocalDateTime createdAt;
}
