package com.saitama.microservices.commonlib.dto;

import com.saitama.microservices.commonlib.constant.MediaCategory;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MediaQueryDTO {
	
	private String fileName;
	private MediaCategory mediaCategory;

}
