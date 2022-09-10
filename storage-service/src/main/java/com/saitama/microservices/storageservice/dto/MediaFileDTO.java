package com.saitama.microservices.storageservice.dto;

import com.saitama.microservices.commonlib.constant.MediaType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MediaFileDTO {
	
	private String name;
	private String src;
	private MediaType type;

}
