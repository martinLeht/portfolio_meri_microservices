package com.saitama.microservices.storageservice.dto;

import java.util.List;

import javax.validation.constraints.NotEmpty;

import com.saitama.microservices.storageservice.constant.MediaCategory;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MediaListQueryDTO {
	
	@NotEmpty
	private List<String> fileNames;
	private MediaCategory mediaCategory;

}
