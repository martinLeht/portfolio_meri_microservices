package com.saitama.microservices.blogservice.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TagDto {
	
	private long id;
	
	private String postTitle;
	
	private String postIntro;
	
	private AttachmentDto thumbnail;
	
	@JsonFormat(pattern="dd.MM.yyyy HH:mm")
	private LocalDateTime createdAt;
	
	private long postId;
	
	private String userId;
	
	public TagDto() { }

	public TagDto(long id, String postTitle, String postIntro, AttachmentDto thumbnail, LocalDateTime createdAt, long postId, String userId) {
		super();
		this.id = id;
		this.postTitle = postTitle;
		this.postIntro = postIntro;
		this.thumbnail = thumbnail;
		this.createdAt = createdAt;
		this.postId = postId;
		this.userId = userId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getPostTitle() {
		return postTitle;
	}

	public void setPostTitle(String postTitle) {
		this.postTitle = postTitle;
	}

	public String getPostIntro() {
		return postIntro;
	}

	public void setPostIntro(String postIntro) {
		this.postIntro = postIntro;
	}
	

	public AttachmentDto getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(AttachmentDto thumbnail) {
		this.thumbnail = thumbnail;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public long getPostId() {
		return postId;
	}

	public void setPostId(long postId) {
		this.postId = postId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "TagDto [id=" + id + ", postTitle=" + postTitle + ", postIntro=" + postIntro + ", thumbnail=" + thumbnail
				+ ", createdAt=" + createdAt + "]";
	}

	
}
