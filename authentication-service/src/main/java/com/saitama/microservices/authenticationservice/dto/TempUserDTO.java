package com.saitama.microservices.authenticationservice.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TempUserDTO {

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private UUID uuid;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private UUID accessUuid;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String keycloakUserId;
	
	@NotNull
	private String username;
	
	private boolean verified;
	
	private boolean locked;
	
	private boolean requireVerificationOnEveryAccess;
	
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private LocalDateTime verifiedAt;
	
}
