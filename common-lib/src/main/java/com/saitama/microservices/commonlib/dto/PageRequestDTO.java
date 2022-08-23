package com.saitama.microservices.commonlib.dto;

import lombok.Data;

@Data
public class PageRequestDTO {

	public static Integer MAX_SIZE = 100;
	public static Integer DEFAULT_SIZE = 20;
	public static Integer DEFAULT_PAGE = 0;
	
	private Integer page;
	private Integer size;
	
}
