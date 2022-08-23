package com.saitama.microservices.blogservice.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BlogPostDTO {
	
	private long id;
	
	private String title;
	
	@JsonFormat(pattern="dd.MM.yyyy HH:mm")
	private LocalDateTime createdAt;
	
	@JsonFormat(pattern="dd.MM.yyyy HH:mm")
	private LocalDateTime updatedAt;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private TagDTO tag;
	
	private List<ContentBlockDTO> content = new ArrayList<>();
	
	private List<AttachmentDTO> attachments = new ArrayList<>();
	
	private String userId;
	
}
