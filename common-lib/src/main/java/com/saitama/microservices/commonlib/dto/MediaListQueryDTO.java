package com.saitama.microservices.commonlib.dto;

import java.util.List;

import com.saitama.microservices.commonlib.constant.MediaCategory;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MediaListQueryDTO {
	
	private List<String> fileNames;
	private MediaCategory mediaCategory;

}
