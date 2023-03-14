package com.saitama.microservices.commonlib.dto;

import java.util.UUID;

import com.saitama.microservices.commonlib.constant.AccessType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccessKeyDTO {
	
	private UUID accessKey;
	private AccessType type;
	
}
