package com.saitama.microservices.portfoliodataservice.controller;


import java.util.UUID;

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
import org.springframework.web.bind.annotation.RequestParam;
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
	public PaginationDTO<ExperienceDTO> getExperiences(@RequestParam Integer page, @RequestParam Integer size) {
		PageRequestDTO pageDto = PageRequestDTO.builder()
				.page(page != null ? page : PageRequestDTO.DEFAULT_PAGE)
				.size(size != null ? size : PageRequestDTO.DEFAULT_SIZE)
				.build();
		return experienceService.getPaginated(pageDto);
	}
	
	@GetMapping("/experience/public")
	public PaginationDTO<ExperienceDTO> getPublicExperiences(@RequestParam Integer page, @RequestParam Integer size) {
		PageRequestDTO pageDto = PageRequestDTO.builder()
				.page(page != null ? page : PageRequestDTO.DEFAULT_PAGE)
				.size(size != null ? size : PageRequestDTO.DEFAULT_SIZE)
				.build();
		return experienceService.getPublicExperiences(pageDto);
	}
	
	
	@GetMapping("/experience/{id}")
	public ExperienceDTO getExperienceById(@PathVariable String id) {
		return experienceService.getById(UUID.fromString(id));
	}
	
	@PostMapping("/experience")
	@ResponseStatus(HttpStatus.CREATED)
	public ExperienceDTO createExperience(@RequestBody @Valid ExperienceDTO experienceDto) {
		return experienceService.create(experienceDto);
	}
	
	@PutMapping("/experience/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ExperienceDTO updateExperience(@PathVariable String id, @RequestBody @Valid ExperienceDTO experienceDto) {
		return experienceService.update(UUID.fromString(id), experienceDto);
	}
	
	@DeleteMapping("/experience/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void deleteExperience(@PathVariable String id) {
		experienceService.delete(UUID.fromString(id));
	}
	
}
