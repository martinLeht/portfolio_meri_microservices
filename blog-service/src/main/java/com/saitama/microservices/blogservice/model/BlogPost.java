package com.saitama.microservices.blogservice.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection="blog_post")
public class BlogPost extends UuidIdentified {
	
	private UUID userId;
	
	private String title;
	
	private LocalDateTime createdAt = LocalDateTime.now();
	
	@LastModifiedDate
	private LocalDateTime updatedAt;
	
	private List<ContentBlock> content = new ArrayList<>();
	
	private List<Attachment> attachments = new ArrayList<>();

		
	public BlogPost() {
		
	}


	public BlogPost(UUID id, String title, LocalDateTime createdAt, LocalDateTime updatedAt, List<ContentBlock> content, 
			List<Attachment> attachments, UUID userId) {
		this.id = id;
		this.title = title;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.content = content;
		this.attachments = attachments;
		this.userId = userId;
	}

	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public LocalDateTime getCreatedAt() {
		return createdAt;
	}


	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}


	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}


	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public List<ContentBlock> getContent() {
		return content;
	}


	public void setContent(List<ContentBlock> content) {
		this.content = content;
	}
	
	
	public List<Attachment> getAttachments() {
		return attachments;
	}


	public void setAttachments(List<Attachment> attachments) {
		this.attachments = attachments;
	}


	public UUID getUserId() {
		return userId;
	}


	public void setUserId(UUID userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "BlogPost [id=" + id + ", title=" + title + ", createdAt=" + createdAt
				+ ", updatedAt=" + updatedAt +", content=" + content + ", attachments="
				+ attachments + ", userId=" + userId + "]";
	}
}
