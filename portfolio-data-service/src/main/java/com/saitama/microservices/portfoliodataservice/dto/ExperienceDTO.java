package com.saitama.microservices.portfoliodataservice.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.saitama.microservices.commonlib.dto.MediaFileDTO;
import com.saitama.microservices.portfoliodataservice.constant.ExperienceType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExperienceDTO {
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private UUID uuid;
	
	@NotNull
	private UUID userId;
	
	@NotEmpty
	private String title;
	
	@NotNull
	private ExperienceType experienceType;
	
	private boolean hidden;
	
	private String shortDescription;
	
	private String content;
	
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate startDate;
	
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate endDate;
	
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private LocalDateTime createdAt;
	
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private LocalDateTime updatedAt;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private MediaFileDTO media;

}
