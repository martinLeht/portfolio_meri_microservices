package com.saitama.microservices.commonlib.service;

import com.saitama.microservices.commonlib.dto.PageRequestDTO;
import com.saitama.microservices.commonlib.dto.PaginationDTO;

public interface IBaseReadWriteService<Dto, Id> {

	PaginationDTO<Dto> getPaginated(PageRequestDTO pageDto);
	
	Dto getById(Id id);
	
	Dto create(Dto dto);
	
	Dto update(Id id, Dto dto);
	
	void delete(Id id);

}
