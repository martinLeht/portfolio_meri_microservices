package com.saitama.microservices.commonlib.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SearchRequestDTO extends PageRequestDTO {

	private final String searchTerm;
	
	@Builder(builderMethodName = "searchRequestBuilder")
	public SearchRequestDTO(String searchTerm, int page, int size) {
		super(page, size);
		this.searchTerm = searchTerm;
	}

}
