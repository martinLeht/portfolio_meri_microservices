package com.saitama.microservices.portfoliodataservice.controller;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.saitama.microservices.commonlib.dto.PageRequestDTO;
import com.saitama.microservices.commonlib.dto.PaginationDTO;
import com.saitama.microservices.portfoliodataservice.dto.ExperienceDTO;
import com.saitama.microservices.portfoliodataservice.service.IExperienceService;

@RestController
@RequestMapping("/portfolio")
public class PortfolioDataController {

	
	private final IExperienceService experienceService;
	
	
	@Autowired
	public PortfolioDataController(IExperienceService experienceService) {
		this.experienceService = experienceService;
	}
	
	
	@GetMapping("/experience")
	public PaginationDTO<ExperienceDTO> getExperiences(@RequestBody(required = false) PageRequestDTO pageDto) {
		return experienceService.getExperiences(pageDto);
	}
	
	
	@GetMapping("/experience/{id}")
	public ExperienceDTO getExperienceById(@PathVariable String id) {
		return experienceService.getExperienceById(id);
	}
	
	@PostMapping("/experience")
	@ResponseStatus(HttpStatus.CREATED)
	public ExperienceDTO createExperience(@RequestBody @Valid ExperienceDTO experienceDto) {
		return experienceService.createExperience(experienceDto);
	}
	
	@PutMapping("/experience/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ExperienceDTO updateExperience(@PathVariable String id, @RequestBody @Valid ExperienceDTO experienceDto) {
		return experienceService.updateExperience(id, experienceDto);
	}
	
	@DeleteMapping("/experience/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void deleteExperience(@PathVariable String id) {
		experienceService.deleteExperience(id);
	}
	
}
