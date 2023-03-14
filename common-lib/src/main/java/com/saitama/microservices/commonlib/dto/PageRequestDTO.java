package com.saitama.microservices.commonlib.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class PageRequestDTO {

	public static final Integer MAX_SIZE = 100;
	public static final Integer DEFAULT_SIZE = 20;
	public static final Integer DEFAULT_PAGE = 0;
	
	private final Integer page;
	private final Integer size;
	
}
