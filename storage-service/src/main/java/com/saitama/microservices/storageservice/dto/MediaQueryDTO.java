package com.saitama.microservices.storageservice.dto;

import javax.validation.constraints.NotEmpty;

import com.saitama.microservices.storageservice.constant.MediaCategory;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MediaQueryDTO {
	
	@NotEmpty
	private String fileName;
	private MediaCategory mediaCategory;

}
