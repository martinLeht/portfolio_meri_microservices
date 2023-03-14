package com.saitama.microservices.commonlib.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TemporaryAccessGrantDTO {
	
	private AccessKeyDTO accessKey;
	private UUID userId;
	private String username;
}
