package com.saitama.microservices.portfoliodataservice.service.impl;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.saitama.microservices.commonlib.dto.PageRequestDTO;
import com.saitama.microservices.commonlib.dto.PaginationDTO;
import com.saitama.microservices.commonlib.exception.EntityNotFoundException;
import com.saitama.microservices.portfoliodataservice.dto.ExperienceDTO;
import com.saitama.microservices.portfoliodataservice.entity.Experience;
import com.saitama.microservices.portfoliodataservice.entity.Media;
import com.saitama.microservices.portfoliodataservice.mapper.ExperienceMapper;
import com.saitama.microservices.portfoliodataservice.mapper.MediaMapper;
import com.saitama.microservices.portfoliodataservice.repository.ExperienceRepository;
import com.saitama.microservices.portfoliodataservice.service.IExperienceService;


@Service
public class ExperienceServiceImpl implements IExperienceService {

	
	private final ExperienceRepository experienceRepository;
	private final ExperienceMapper experienceMapper;
	private final MediaMapper mediaMapper;
	
	@Autowired
	public ExperienceServiceImpl(
			ExperienceRepository experienceRepository,
			ExperienceMapper experienceMapper,
			MediaMapper mediaMapper) {
		this.experienceRepository = experienceRepository;
		this.experienceMapper = experienceMapper;
		this.mediaMapper = mediaMapper;
	}
	
	@Override
	public PaginationDTO<ExperienceDTO> getExperiences(PageRequestDTO pageDto) {
		Pageable sortedByCreatedAt = null;
		if (pageDto == null || pageDto.getPage() == null || pageDto.getSize() == null) {
			sortedByCreatedAt = PageRequest.of(PageRequestDTO.DEFAULT_PAGE, PageRequestDTO.DEFAULT_SIZE, Sort.Direction.DESC, "createdAt");
		} else {
			if (pageDto.getSize() > PageRequestDTO.MAX_SIZE) {
				sortedByCreatedAt = PageRequest.of(pageDto.getPage(), PageRequestDTO.MAX_SIZE, Sort.Direction.DESC, "createdAt");
			} else {
				sortedByCreatedAt = PageRequest.of(pageDto.getPage(), pageDto.getSize(), Sort.Direction.DESC, "createdAt");
			}
		}
		
		Page<Experience> experiences = experienceRepository.findAll(sortedByCreatedAt);		
		List<ExperienceDTO> experienceDtos = experiences.stream()
				.map(experienceMapper::toDto)
				.collect(Collectors.toList());
		
		if (experiences.getNumber() + 1 < experiences.getTotalPages()) {
			return PaginationDTO.<ExperienceDTO>builder()
					.page(experiences.getNumber())
					.pageSize(experiences.getSize())
					.totalSize(experiences.getTotalElements())
					.nextPage(experiences.getNumber() + 1)
					.data(experienceDtos)
					.build();
		} else {
			return PaginationDTO.<ExperienceDTO>builder()
					.page(experiences.getNumber())
					.pageSize(experiences.getSize())
					.totalSize(experiences.getTotalElements())
					.data(experienceDtos)
					.build();
		}
	}
	

	@Override
	public ExperienceDTO getExperienceById(String id) {
		Experience experience = experienceRepository.findByUuid(UUID.fromString(id)).stream()
				.findFirst()
				.orElseThrow(() -> new EntityNotFoundException("experience-not-found", "Experience not found with id: " + id));
		return experienceMapper.toDto(experience);
	}
	

	@Override
	public ExperienceDTO createExperience(ExperienceDTO experienceDto) {
		
		Experience experience = experienceMapper.fromDto(experienceDto);
		
		UUID uuid = UUID.randomUUID();
		List<Experience> existingExperiences = experienceRepository.findByUuid(uuid);
		
		if (existingExperiences.isEmpty()) {
			experience.setUuid(uuid);
		} else {
			experience.setUuid(UUID.randomUUID());
		}
		
		if (experience.getMedia() != null) {
			experience.getMedia().setExperience(experience);
		}
		
		Experience saved = experienceRepository.save(experience);
		
		return experienceMapper.toDto(saved);
	}

	@Override
	public ExperienceDTO updateExperience(String id, ExperienceDTO experienceDto) {
		Experience experience = this.experienceRepository.findByUuid(UUID.fromString(id)).stream()
				.findFirst()
				.orElseThrow(() -> new EntityNotFoundException("experience-not-found", "Experience not found with id: " + id));
		
		experience.setTitle(experienceDto.getTitle());
		experience.setShortDescription(experienceDto.getShortDescription());
		experience.setContent(experienceDto.getContent());
		experience.setStartDate(experienceDto.getStartDate());
		experience.setEndDate(experienceDto.getEndDate());
		experience.setExperienceType(experienceDto.getExperienceType());
		experience.setHidden(experienceDto.isHidden());
		
		if (experience.getMedia() != null && experienceDto.getMedia() != null) {
			mediaMapper.mapFromDtoToModel(experience.getMedia(), experienceDto.getMedia());
		} else if (experience.getMedia() == null && experienceDto.getMedia() != null) {
			Media newMedia = mediaMapper.fromDto(experienceDto.getMedia());
			newMedia.setExperience(experience);
			experience.setMedia(newMedia);
		} else {
			experience.setMedia(null);
		}
		
		Experience saved = experienceRepository.save(experience);	
		return experienceMapper.toDto(saved);
	}

	@Override
	public void deleteExperience(String id) {
		Experience experience = this.experienceRepository.findByUuid(UUID.fromString(id)).stream()
				.findFirst()
				.orElseThrow(() -> new EntityNotFoundException("experience-not-found", "Experience not found with id: " + id));
		experienceRepository.delete(experience);
	}

}
