package com.saitama.microservices.portfoliodataservice.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private UUID uuid;
	
	@NotNull
	private UUID userId;
	
	@NotEmpty
	private String username;
	
	@NotNull
	private UUID postId;
	
	@NotEmpty
	private String content;
	
	private boolean verified;
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime createdAt;
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime updatedAt;

}
