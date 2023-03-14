package com.saitama.microservices.portfoliodataservice.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.saitama.microservices.commonlib.dto.PageRequestDTO;

public class QueryUtils {
	
	/**
	 * Utility method to create a sortable pagination request for JPA repository.
	 * @param fieldName
	 * @param pageDto
	 * @return Pageable request
	 */
	public static Pageable createPageRequestSortedByField(String fieldName, PageRequestDTO pageDto) {
		Pageable sortedByField = null;
		if (pageDto == null || pageDto.getPage() == null || pageDto.getSize() == null) {
			sortedByField = PageRequest.of(PageRequestDTO.DEFAULT_PAGE, PageRequestDTO.DEFAULT_SIZE, Sort.Direction.DESC, fieldName);
		} else {
			if (pageDto.getSize() > PageRequestDTO.MAX_SIZE) {
				sortedByField = PageRequest.of(pageDto.getPage(), PageRequestDTO.MAX_SIZE, Sort.Direction.DESC, fieldName);
			} else {
				sortedByField = PageRequest.of(pageDto.getPage(), pageDto.getSize(), Sort.Direction.DESC, fieldName);
			}
		}
		return sortedByField;
	}

}
