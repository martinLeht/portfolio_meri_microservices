package com.saitama.microservices.commonlib.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class PaginationDTO<T> {

	private List<T> data;
	private int page;
	private int pageSize;
	private long totalSize;
	
	@JsonInclude(Include.NON_NULL)
	private Integer nextPage;
	
	
}
