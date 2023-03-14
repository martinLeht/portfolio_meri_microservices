package com.saitama.microservices.authenticationservice.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageDTO {
	
	private String to;
	private String subject;
	private String content;
	private LocalDateTime createdAt;

}
