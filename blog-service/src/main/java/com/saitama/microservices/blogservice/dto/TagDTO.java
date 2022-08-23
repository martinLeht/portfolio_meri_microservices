package com.saitama.microservices.blogservice.dto;

import java.time.LocalDateTime;

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
	
	private long id;
	private String postTitle;
	private String postIntro;
	private AttachmentDTO thumbnail;
	
	@JsonFormat(pattern="dd.MM.yyyy HH:mm")
	private LocalDateTime createdAt;
	
	private long postId;
	private String userId;
	
}
