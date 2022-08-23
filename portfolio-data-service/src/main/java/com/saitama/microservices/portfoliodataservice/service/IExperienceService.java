package com.saitama.microservices.portfoliodataservice.service;

import com.saitama.microservices.commonlib.dto.PageRequestDTO;
import com.saitama.microservices.commonlib.dto.PaginationDTO;
import com.saitama.microservices.portfoliodataservice.dto.ExperienceDTO;

public interface IExperienceService {

	PaginationDTO<ExperienceDTO> getExperiences(PageRequestDTO pageDto);
	
	ExperienceDTO getExperienceById(String id);
	
	ExperienceDTO createExperience(ExperienceDTO experienceDto);
	
	ExperienceDTO updateExperience(String id, ExperienceDTO experienceDto);
	
	void deleteExperience(String id);
}
