package com.saitama.microservices.portfoliodataservice.service;


import java.util.UUID;

import com.saitama.microservices.commonlib.dto.PageRequestDTO;
import com.saitama.microservices.commonlib.dto.PaginationDTO;
import com.saitama.microservices.commonlib.service.IBaseReadWriteService;
import com.saitama.microservices.portfoliodataservice.dto.ExperienceDTO;

public interface IExperienceService extends IBaseReadWriteService<ExperienceDTO, UUID> {
	
	PaginationDTO<ExperienceDTO> getPublicExperiences(PageRequestDTO pageDto);
}
