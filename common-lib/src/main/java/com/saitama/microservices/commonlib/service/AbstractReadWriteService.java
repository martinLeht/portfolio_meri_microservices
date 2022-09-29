package com.saitama.microservices.commonlib.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.saitama.microservices.commonlib.dto.PageRequestDTO;

public abstract class AbstractReadWriteService<Dto, Id> implements IBaseReadWriteService<Dto, Id> {
	
	
	protected Pageable createPageRequestSortedByField(String fieldName, PageRequestDTO pageDto) {
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
