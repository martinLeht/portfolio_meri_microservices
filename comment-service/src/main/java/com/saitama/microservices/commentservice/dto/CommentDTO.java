package com.saitama.microservices.commentservice.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CommentDTO {
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private UUID uuid;
	
	@NotNull
	private UUID userId;
	
	private UUID parent_id;
	
	@NotNull
	private UUID postId;
	
	@NotEmpty
	private String content;
	
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private LocalDateTime createdAt;
	
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private LocalDateTime updatedAt;

}
