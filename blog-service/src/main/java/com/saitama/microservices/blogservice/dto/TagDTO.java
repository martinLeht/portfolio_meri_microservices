package com.saitama.microservices.blogservice.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TagDTO {
	
	private UUID id;
	
	private UUID userId;
	
	private String postTitle;
	
	private String postIntro;
	
	private String contentFlat;
	
	private AttachmentDTO thumbnail;
	
	@JsonFormat(pattern="dd.MM.yyyy HH:mm")
	private LocalDateTime createdAt;
		
}
