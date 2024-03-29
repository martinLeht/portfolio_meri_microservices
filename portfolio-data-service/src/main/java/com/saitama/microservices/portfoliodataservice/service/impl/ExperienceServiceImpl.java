package com.saitama.microservices.portfoliodataservice.service.impl;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
import com.saitama.microservices.portfoliodataservice.util.QueryUtils;


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
	public PaginationDTO<ExperienceDTO> getPaginated(PageRequestDTO pageDto) {
		Pageable sortedByCreatedAt = QueryUtils.createPageRequestSortedByField("createdAt", pageDto);	
		
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
	public PaginationDTO<ExperienceDTO> getPublicExperiences(PageRequestDTO pageDto) {
		Pageable sortedByCreatedAt = QueryUtils.createPageRequestSortedByField("createdAt", pageDto);	
		
		Page<Experience> experiences = experienceRepository.findByHiddenWithPagination(false, sortedByCreatedAt);		
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
	public ExperienceDTO getById(UUID id) {
		Experience experience = experienceRepository.findByUuid(id).stream()
				.findFirst()
				.orElseThrow(() -> new EntityNotFoundException("experience-not-found", "Experience not found with id: " + id));
		return experienceMapper.toDto(experience);
	}
	

	@Override
	public ExperienceDTO create(ExperienceDTO experienceDto) {
		
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
	public ExperienceDTO update(UUID id, ExperienceDTO experienceDto) {
		Experience experience = this.experienceRepository.findByUuid(id).stream()
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
	public void delete(UUID id) {
		Experience experience = this.experienceRepository.findByUuid(id).stream()
				.findFirst()
				.orElseThrow(() -> new EntityNotFoundException("experience-not-found", "Experience not found with id: " + id));
		experienceRepository.delete(experience);
	}

}
